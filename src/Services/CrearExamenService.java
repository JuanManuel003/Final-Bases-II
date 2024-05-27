package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
	
	public static int crearExamen(String nombreExamen, String descripcion, int idTemaExamen, int idDocente, int idConfig, int idGrupo) {
        int newIdExamen = -1;
        try (Connection conn = Conexion.getInstance().getConnection()) {
            conn.setAutoCommit(false);  // Iniciar una transacción

            newIdExamen = insertarExamen(conn, nombreExamen, descripcion, idTemaExamen, idDocente, idConfig, idGrupo);
            Timestamp fechaPresentacion = obtenerFechaPresentacion(conn, idConfig);
            asignarExamenAAlumnos(conn, newIdExamen, idGrupo, fechaPresentacion);

            conn.commit();  // Confirmar la transacción
            JOptionPane.showMessageDialog(null, "Examen creado exitosamente y asignado a los alumnos.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            try (Connection conn = Conexion.getInstance().getConnection()) {
                conn.rollback();  // Revertir la transacción en caso de error
            } catch (SQLException | ClassNotFoundException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        return newIdExamen;
    }
	
	private static int insertarExamen(Connection conn, String nombreExamen, String descripcion, int idTemaExamen, int idDocente, int idConfig, int idGrupo) throws SQLException {
        String getLastIdQuery = "SELECT COALESCE(MAX(id), 0) FROM examen";
        String insertQuery = "INSERT INTO examen (id, nombre, descripcion, id_tema_examen, id_docente, id_configuracion, grupo_id, estado_examen) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int newIdExamen = -1;
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(getLastIdQuery); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            if (rs.next()) {
                newIdExamen = rs.getInt(1) + 1;
            }
            pstmt.setInt(1, newIdExamen);
            pstmt.setString(2, nombreExamen);
            pstmt.setString(3, descripcion);
            pstmt.setInt(4, idTemaExamen);
            pstmt.setInt(5, idDocente);
            pstmt.setInt(6, idConfig);
            pstmt.setInt(7, idGrupo);
            pstmt.setInt(8, 0);  // Estado examen por defecto: 0
            pstmt.executeUpdate();
        }
        return newIdExamen;
    }
	
	private static void asignarExamenAAlumnos(Connection conn, int newIdExamen, int idGrupo, Timestamp fechaPresentacion) throws SQLException {
        String insertAlumnoExamenQuery = "INSERT INTO alumno_examen (examen_id, alumno_id, fechainicio) VALUES (?, ?, ?)";
        List<Integer> listaAlumnos = obtenerAlumnosPorGrupo(conn, idGrupo);
        try (PreparedStatement pstmtAlumnoExamen = conn.prepareStatement(insertAlumnoExamenQuery)) {
            for (int alumnoId : listaAlumnos) {
                pstmtAlumnoExamen.setInt(1, newIdExamen);
                pstmtAlumnoExamen.setInt(2, alumnoId);
                pstmtAlumnoExamen.setTimestamp(3, fechaPresentacion);
                pstmtAlumnoExamen.addBatch();
            }
            pstmtAlumnoExamen.executeBatch();
        }
    }
	
	private static Timestamp obtenerFechaPresentacion(Connection conn, int idConfig) throws SQLException {
        String getFechaPresentacionQuery = "SELECT fecha_presentacion FROM configuracion WHERE id = ?";
        Timestamp fechaPresentacion = null;
        try (PreparedStatement pstmtFechaPresentacion = conn.prepareStatement(getFechaPresentacionQuery)) {
            pstmtFechaPresentacion.setInt(1, idConfig);
            try (ResultSet rsFechaPresentacion = pstmtFechaPresentacion.executeQuery()) {
                if (rsFechaPresentacion.next()) {
                    fechaPresentacion = rsFechaPresentacion.getTimestamp("fecha_presentacion");
                }
            }
        }
        return fechaPresentacion;
    }

	private static List<Integer> obtenerAlumnosPorGrupo(Connection conn, int idGrupo) throws SQLException {
        String query = "SELECT alumno_id FROM alumno_grupo WHERE grupo_id = ?";
        List<Integer> listaAlumnos = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idGrupo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    listaAlumnos.add(rs.getInt("alumno_id"));
                }
            }
        }
        return listaAlumnos;
    }
	
	//metodo para guardar los datos en la tabla preguntas alumno
	public static void crearPreguntasAlumno(){
		
	}

	public static void actualizarEstadoExamen(int idExamen) {
		// Consulta SQL para actualizar el estado del examen
		String updateQuery = "UPDATE examen SET estado_examen = ? WHERE id = ?";

		// Establecer la conexión y ejecutar la consulta
		try (Connection conn = Conexion.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

			// Asignar valores a los parámetros del PreparedStatement
			pstmt.setInt(1, 2); // Nuevo estado del examen: 2
			pstmt.setInt(2, idExamen);

			// Ejecutar la consulta
			pstmt.executeUpdate();

			JOptionPane.showMessageDialog(null, "Preguntas generadas exitosamente: " + pstmt.executeUpdate());
			System.out.println("Estado del examen actualizado");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
