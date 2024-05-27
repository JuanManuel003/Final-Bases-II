package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BD.Conexion;
import Modelo.Alumno;

public class AlumnoService {

	public static boolean ingresar(String correo, String password) {
		boolean valido = false;
        String query = "SELECT * FROM alumno WHERE correo = ? AND password = ?";
        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, correo);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    valido = true;
                } else {
                    System.out.println("No se encontraron resultados para la consulta.");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return valido;
	}

	public static Alumno obtenerAlumno(String correo) {
        Alumno alumno = null;
        String query = "SELECT id, nombre, apellido, numidentificacion, direccion, telefono, correo, password, id_grupo FROM alumno WHERE correo = ?";

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, correo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String numidentificacion = rs.getString("numidentificacion");
                    String direccion = rs.getString("direccion");
                    String telefono = rs.getString("telefono");
                    String email = rs.getString("correo");
                    String password = rs.getString("password");
                    int idGrupo = rs.getInt("id_grupo");

                    alumno = new Alumno(id, nombre, apellido, numidentificacion, direccion, telefono, email, password, idGrupo);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return alumno;
    }
}
