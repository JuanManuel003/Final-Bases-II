package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import BD.Conexion;
import Modelo.PrivacidadPregunta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CrearPreguntaService {

	public static ObservableList<PrivacidadPregunta> obtenerPrivacidad() {
		ObservableList<PrivacidadPregunta> privacidadPregunta = FXCollections.observableArrayList();
		String query = "SELECT * FROM privacidadpregunta";
		try (Connection conn = Conexion.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt(1);
				String descripcion = rs.getString(2);

				privacidadPregunta.add(new PrivacidadPregunta(id, descripcion));
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return privacidadPregunta;
	}

	public static int CrearPregunta(String descripcion, int porcentaje, int tipoPregunta, int idTema, int idPrivacidad,
			int idDocente, int idExamen) {
		// La consulta SQL para obtener el último ID de la tabla pregunta
		String getLastIdQuery = "SELECT COALESCE(MAX(id), 0) FROM pregunta";

		// La consulta SQL para insertar una nueva pregunta
		String insertQuery = "INSERT INTO pregunta (id, descripcion, porcentaje, id_tipo_pregunta, tema_id, privacidadpregunta_id, docente_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		// Variable para almacenar el ID generado
		int newIdPregunta = -1;

		// Establecer la conexión y ejecutar la consulta
		try (Connection conn = Conexion.getInstance().getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(getLastIdQuery);
				PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

			// Obtener el último ID de la tabla pregunta y sumarle 1
			if (rs.next()) {
				newIdPregunta = rs.getInt(1) + 1;
			}

			// Asignar valores a los parámetros del PreparedStatement
			pstmt.setInt(1, newIdPregunta);
			pstmt.setString(2, descripcion);
			pstmt.setInt(3, porcentaje);
			pstmt.setInt(4, tipoPregunta);
			pstmt.setInt(5, idTema);
			pstmt.setInt(6, idPrivacidad);
			pstmt.setInt(7, idDocente);

			// Ejecutar la consulta
			pstmt.executeUpdate();

			// asocia las preguntas con un examen creado
			insertarPreguntaEnExamen(idExamen, newIdPregunta);

			// asocia las preguntas con un examen y los alumnos del grupo
			insertarPreguntasAlumno(idExamen, newIdPregunta);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return newIdPregunta;
	}

	public static void CrearRespuesta(String opc, String rb, int idPreguntaCreada) {
		// Consulta SQL para obtener el último ID de la tabla respuesta
		String getLastIdQuery = "SELECT COALESCE(MAX(id), 0) FROM respuesta";

		// Consulta SQL para insertar una nueva respuesta
		String insertQuery = "INSERT INTO respuesta (id, descripcion, respuesta_correcta, id_pregunta) "
				+ "VALUES (?, ?, ?, ?)";

		// Variable para almacenar el nuevo ID generado
		int newIdRespuesta = -1;

		// Establecer la conexión y ejecutar la consulta
		try (Connection conn = Conexion.getInstance().getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(getLastIdQuery);
				PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

			// Obtener el último ID de la tabla respuesta y sumarle 1
			if (rs.next()) {
				newIdRespuesta = rs.getInt(1) + 1;
			}

			// Asignar valores a los parámetros del PreparedStatement
			pstmt.setInt(1, newIdRespuesta);
			pstmt.setString(2, opc);
			pstmt.setString(3, rb);
			pstmt.setInt(4, idPreguntaCreada);

			// Ejecutar la consulta
			pstmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void insertarPreguntasAlumno(int idExamen, int idPreguntaCreada) {
		int idGrupo = obtenerGrupoExamen(idExamen);

		if (idGrupo != -1) {
			List<Integer> listaAlumnos = obtenerAlumnosPorGrupo(idGrupo);

			if (listaAlumnos != null && !listaAlumnos.isEmpty()) {
				insertarPreguntasAlumno(idExamen, idPreguntaCreada, listaAlumnos);
				JOptionPane.showMessageDialog(null,
						"Pregunta agregada exitosamente al examen para " + listaAlumnos.size() + " alumnos.");
			} else {
				JOptionPane.showMessageDialog(null, "No se encontraron alumnos en el grupo con id: " + idGrupo);
			}
		} else {
			JOptionPane.showMessageDialog(null, "No se pudo encontrar un grupo para el examen con id: " + idExamen);
			return;
		}

	}

	private static void insertarPreguntasAlumno(int idExamen, int idPreguntaCreada, List<Integer> listaAlumnos) {
		String insertarPreguntaAlumnoQuery = "INSERT INTO preguntasalumno (alumno_examen_examen_id, alumno_examen_alumno_id, pregunta_id) VALUES (?, ?, ?)";

		try (Connection conn = Conexion.getInstance().getConnection();
				PreparedStatement pstmtPreguntaAlumno = conn.prepareStatement(insertarPreguntaAlumnoQuery)) {

			for (int alumnoId : listaAlumnos) {
				pstmtPreguntaAlumno.setInt(1, idExamen);
				pstmtPreguntaAlumno.setInt(2, alumnoId);
				pstmtPreguntaAlumno.setInt(3, idPreguntaCreada);
				pstmtPreguntaAlumno.addBatch();
			}

			pstmtPreguntaAlumno.executeBatch();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static List<Integer> obtenerAlumnosPorGrupo(int idGrupo) {
		String obtenerAlumnosQuery = "SELECT alumno_id FROM alumno_grupo WHERE grupo_id = ?";
		List<Integer> listaAlumnos = new ArrayList<>();

		try (Connection conn = Conexion.getInstance().getConnection();
				PreparedStatement pstmtAlumnos = conn.prepareStatement(obtenerAlumnosQuery)) {

			pstmtAlumnos.setInt(1, idGrupo);
			try (ResultSet rs = pstmtAlumnos.executeQuery()) {
				while (rs.next()) {
					listaAlumnos.add(rs.getInt("alumno_id"));
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return listaAlumnos;
	}

	private static int obtenerGrupoExamen(int idExamen) {
		String obtenerGrupoIdQuery = "SELECT grupo_id FROM examen WHERE id = ?";
		int idGrupo = -1;

		try (Connection conn = Conexion.getInstance().getConnection();
				PreparedStatement pstmtGrupoId = conn.prepareStatement(obtenerGrupoIdQuery)) {

			// Obtener el id_grupo usando el id_examen
			pstmtGrupoId.setInt(1, idExamen);
			try (ResultSet rs = pstmtGrupoId.executeQuery()) {
				if (rs.next()) {
					idGrupo = rs.getInt("grupo_id");
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return idGrupo;
	}

	// Método para insertar datos en la tabla preguntasexamen
	public static void insertarPreguntaEnExamen(int idExamen, int idPregunta) {
		String insertPreguntaExamenQuery = "INSERT INTO preguntasexamen (id_pregunta, id_examen) VALUES (?, ?)";
		try (Connection conn = Conexion.getInstance().getConnection();
				PreparedStatement pstmt = conn.prepareStatement(insertPreguntaExamenQuery)) {
			pstmt.setInt(1, idPregunta);
			pstmt.setInt(2, idExamen);
			pstmt.executeUpdate();

			// JOptionPane.showMessageDialog(null, "Preguntas examen creado
			// exitosamente");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static int obtenerNumPreguntas(int idExamen) {
        int numPreguntas = 0;
        String countQuery = "SELECT COUNT(*) FROM preguntasexamen WHERE id_examen = ?";

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(countQuery)) {

            pstmt.setInt(1, idExamen);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    numPreguntas = rs.getInt(1);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return numPreguntas;
    }

}
