package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    private static Conexion Pfinal;

    private static Connection conn;

    public Conexion() {
    }

    public static Conexion getInstance() {
        if (Pfinal == null) {
            Pfinal = new Conexion();
        }
        return Pfinal;
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (conn == null) {
            Class.forName("oracle.jdbc.OracleDriver");

            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String user = "Pfinal";
            String password = "root";
            // No imprimir la contraseña por razones de seguridad en un entorno de producción

            if (user == null || user.isEmpty() || password == null || password.isEmpty()) {
                throw new SQLException("Usuario o contraseña no pueden ser nulos o vacíos.");
            }

            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }

    public void realizarConsulta(String consulta) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión desde la clase Conexion
            Connection conn = Conexion.getInstance().getConnection();
            // Crear una declaración
            stmt = conn.createStatement();
            // Ejecutar la consulta
            rs = stmt.executeQuery(consulta);

            // Obtener información de las columnas del ResultSet
            int columnCount = rs.getMetaData().getColumnCount();

            // Verificar si hay resultados en el ResultSet
            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron resultados para la consulta: " + consulta);
                return;
            }

            // Procesar el resultado de la consulta
            while (rs.next()) {
                // Recorrer cada fila del ResultSet
                for (int i = 1; i <= columnCount; i++) {
                    // Obtener el valor de la columna actual
                    String columnValue = rs.getString(i);
                    // Imprimir el valor de la columna
                    System.out.print(columnValue + " - ");
                }
                System.out.println(); // Nueva línea después de cada fila
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Cerrar ResultSet y Statement, pero no Connection
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Método para cerrar la conexión cuando la aplicación termina
    public static void cerrarConexion() {
        if (conn != null) {
            try {
                conn.close();
                conn = null; // Asegurarse de que la conexión esté completamente cerrada
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
