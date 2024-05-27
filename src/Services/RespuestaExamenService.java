package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BD.Conexion;

public class RespuestaExamenService {

	public static String obtenerNombreExamen(int idExamen) {
        String nombreExamen = null;
        String query = "SELECT nombre FROM examen WHERE id = ?";

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idExamen);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nombreExamen = rs.getString("nombre");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return nombreExamen;
    }

	public static ArrayList<String> obtenerNombresPreguntas(int idExamen) {
        ArrayList<String> nombresPreguntas = new ArrayList<>();
        String query = "SELECT p.descripcion " +
                       "FROM preguntasalumno pa " +
                       "JOIN pregunta p ON pa.pregunta_id = p.id " +
                       "WHERE pa.alumno_examen_examen_id = ?";

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idExamen);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    nombresPreguntas.add(rs.getString("descripcion"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return nombresPreguntas;
    }

	public static ArrayList<String> obtenerNombresRespuestas(String nombrePregunta) {
        ArrayList<String> nombresRespuestas = new ArrayList<>();
        String query = "SELECT r.descripcion " +
                       "FROM respuesta r " +
                       "JOIN pregunta p ON r.id_pregunta = p.id " +
                       "WHERE p.descripcion = ?";

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombrePregunta);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    nombresRespuestas.add(rs.getString("descripcion"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return nombresRespuestas;
    }

	public static int obtenerIdPregunta(String nombrePregunta) {
		int idPregunta = 0;
        String query = "SELECT id FROM pregunta WHERE descripcion = ?";

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombrePregunta);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    idPregunta = rs.getInt("id");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return idPregunta;
	}

	public static void crearRespuestaAlumno(int idAlumno, int idExamen, int idPregunta, String estado) {
        String query = "INSERT INTO respuesta_alumno (pregunta_alumnoExamen_alumno, pregunta_alumnoExamen_examen, preguntasalumno_pregunta_id, respuesta) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idAlumno);
            pstmt.setInt(2, idExamen);
            pstmt.setInt(3, idPregunta);
            pstmt.setString(4, estado);

            pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
