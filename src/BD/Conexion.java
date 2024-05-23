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
            // No imprimir la contrase�a por razones de seguridad en un entorno de producci�n

            if (user == null || user.isEmpty() || password == null || password.isEmpty()) {
                throw new SQLException("Usuario o contrase�a no pueden ser nulos o vac�os.");
            }

            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }

    public void realizarConsulta(String consulta) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Obtener la conexi�n desde la clase Conexion
            Connection conn = Conexion.getInstance().getConnection();
            // Crear una declaraci�n
            stmt = conn.createStatement();
            // Ejecutar la consulta
            rs = stmt.executeQuery(consulta);

            // Obtener informaci�n de las columnas del ResultSet
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
                System.out.println(); // Nueva l�nea despu�s de cada fila
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

    // M�todo para cerrar la conexi�n cuando la aplicaci�n termina
    public static void cerrarConexion() {
        if (conn != null) {
            try {
                conn.close();
                conn = null; // Asegurarse de que la conexi�n est� completamente cerrada
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
