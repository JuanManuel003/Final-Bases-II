package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BD.Conexion;

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
}
