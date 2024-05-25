package BD;

//import java.sql.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    private static Conexion Pfinal;
    private static Connection conn;

    public Conexion() {}

    public static Conexion getInstance() {
        if (Pfinal == null) {
            Pfinal = new Conexion();
        }
        return Pfinal;
    }

    public Connection getConetion() throws ClassNotFoundException, SQLException {

        if (conn == null) {
            Class.forName("oracle.jdbc.OracleDriver");

            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String user = "JOHAN";
            String password = "root";

            if (user == null || user.isEmpty() || password == null || password.isEmpty()) {
                throw new SQLException("Usuario o contraseña no pueden ser nulos o vacíos.");
            }

            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }

    // Método para ejecutar consultas SELECT
    public void realizarConsulta(String consulta) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = Conexion.getInstance().getConetion();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta);

            int columnCount = rs.getMetaData().getColumnCount();

            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron resultados para la consulta: " + consulta);
                return;
            }

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + " - ");
                }
                System.out.println();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Metodo de consulta terminado");
    }

    // Método para ejecutar actualizaciones (INSERT, UPDATE, DELETE)
    public void ejecutarActualizacion(String sql) {
        Statement stmt = null;

        try {
            Connection conn = Conexion.getInstance().getConetion();
            stmt = conn.createStatement();
            int affectedRows = stmt.executeUpdate(sql);

            System.out.println("Filas afectadas: " + affectedRows);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void cerrarConexion() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
