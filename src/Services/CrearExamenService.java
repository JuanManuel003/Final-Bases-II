package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.swing.JOptionPane;

import BD.Conexion;
import Modelo.Docente;
import Modelo.Grupo;
import Modelo.Tema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CrearExamenService {

	public static ObservableList<Tema> obtenerTemas() {
		ObservableList<Tema> temasExamen = FXCollections.observableArrayList();
		String query = "SELECT * FROM tema";
		try (Connection conn = Conexion.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt(1);
				String descripcion = rs.getString(2);

				temasExamen.add(new Tema(id, descripcion));
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return temasExamen;
	}

	public static ObservableList<Grupo> obtenerGrupos() {
		ObservableList<Grupo> grupos = FXCollections.observableArrayList();
		String query = "SELECT * FROM grupo";

		try (Connection conn = Conexion.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt(1);
				String nombre = rs.getString(2);
				int numGrupo = rs.getInt(3);
				int idCurso = rs.getInt(4);

				grupos.add(new Grupo(id, nombre, numGrupo, idCurso));
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return grupos;
	}

	public static Docente obtenerDocente(String correo) {
		Docente docente = new Docente();
		String query = "SELECT * FROM docente WHERE correo = ?";

		try (Connection conn = Conexion.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, correo);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					int id = rs.getInt(1);
					String nombre = rs.getString(2);
					String apellido = rs.getString(3);
					String cedula = rs.getString(4);
					String telefono = rs.getString(5);
					String correoDoc = rs.getString(6);
					String contrasena = rs.getString(7);

					docente = new Docente(id, nombre, apellido, cedula, telefono, correoDoc, contrasena);
				} else {
					System.out.println("No se encontraron resultados para la consulta.");
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return docente;
	}

	public static void guardarConfig(int numPreguntas, String intervaloTiempo, int peso, int umbral,
			Timestamp fechaPresentacion, int numPreguntasBanco) {
		String query = "INSERT INTO configuracion (numpreguntas, tiempo, peso, umbral_aprobado, fecha_presentacion, numeropreguntasbanco) VALUES (?, "
				+ intervaloTiempo + ", ?, ?, ?, ?)";
		try (Connection conn = Conexion.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			// Asignar valores a los parámetros del PreparedStatement
			pstmt.setInt(1, numPreguntas);
			pstmt.setInt(2, peso);
			pstmt.setInt(3, umbral);
			pstmt.setTimestamp(4, fechaPresentacion);
			pstmt.setInt(5, numPreguntasBanco);

			// Ejecutar la consulta
			pstmt.executeUpdate();

			JOptionPane.showMessageDialog(null, "Configuracion agregada exitosamente: " + pstmt.executeUpdate());
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static int obtenerIdConfig() {
		String query = "SELECT MAX(id) FROM configuracion";
		int ultimoId = -1; // Valor predeterminado en caso de que no se
							// encuentre ningún ID

		try (Connection conn = Conexion.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				ultimoId = rs.getInt(1);
			} else {
				System.out.println("No se encontró ningún ID generado por la secuencia.");
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return ultimoId;
	}

	public static int crearExamen(String nombreExamen, String descripcion, int idTemaExamen, int idDocente,
	        int idConfig, int idGrupo) {

	    // La consulta SQL para obtener el último ID de la tabla examen
	    String getLastIdQuery = "SELECT COALESCE(MAX(id), 0) FROM examen";

	    // La consulta SQL para insertar un nuevo examen
	    String insertQuery = "INSERT INTO examen (id, nombre, descripcion, id_tema_examen, id_docente, id_configuracion, grupo_id, estado_examen) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    // Variable para almacenar el ID generado
	    int newIdExamen = -1;

	    // Establecer la conexión y ejecutar la consulta
	    try (Connection conn = Conexion.getInstance().getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(getLastIdQuery);
	         PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

	        // Obtener el último ID de la tabla examen y sumarle 1
	        if (rs.next()) {
	            newIdExamen = rs.getInt(1) + 1;
	        }

	        // Asignar valores a los parámetros del PreparedStatement
	        pstmt.setInt(1, newIdExamen);
	        pstmt.setString(2, nombreExamen);
	        pstmt.setString(3, descripcion);
	        pstmt.setInt(4, idTemaExamen);
	        pstmt.setInt(5, idDocente);
	        pstmt.setInt(6, idConfig);
	        pstmt.setInt(7, idGrupo);
	        pstmt.setInt(8, 0); // Estado examen por defecto: 0

	        // Ejecutar la consulta
	        pstmt.executeUpdate();

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return newIdExamen;
	}


}
