package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	public static int CrearPregunta(String descripcion, int porcentaje, int tipoPregunta, int idTema, int idPrivacidad, int idDocente) {
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

}
