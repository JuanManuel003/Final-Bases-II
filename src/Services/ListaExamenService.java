package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BD.Conexion;
import Modelo.Examen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListaExamenService {

	public static ObservableList<Examen> obtenerExamenes(int idAlumno) {
        ObservableList<Examen> listaExamenes = FXCollections.observableArrayList();
        String query = "SELECT examen.id, examen.nombre FROM examen INNER JOIN preguntasalumno ON examen.id = preguntasalumno.alumno_examen_examen_id WHERE preguntasalumno.alumno_examen_alumno_id = ?";

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idAlumno);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int idExamen = rs.getInt("id");
                    String nombreExamen = rs.getString("nombre");
                    Examen examen = new Examen(idExamen, nombreExamen);
                    listaExamenes.add(examen);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return listaExamenes;
    }

}
