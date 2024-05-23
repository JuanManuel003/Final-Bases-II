package BD;

//import java.sql.Connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    private static Conexion Pfinal;

    public Conexion() {
    }

    public static Conexion getInstacen() {
        if (Pfinal == null) {
            Pfinal = new Conexion();
        }
        return Pfinal;
    }

    private static Connection conn;

    public static Connection getConetion() throws ClassNotFoundException, SQLException {
        if (conn == null) {
            System.out.println("Cargando el driver de Oracle...");
            Class.forName("oracle.jdbc.OracleDriver");

            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String user = "Pfinal";
            String password = "root";

            System.out.println("URL: " + url);
            System.out.println("Usuario: " + user);
            // No imprimir la contraseña por razones de seguridad en un entorno de producción

            if (user == null || user.isEmpty() || password == null || password.isEmpty()) {
                throw new SQLException("Usuario o contraseña no pueden ser nulos o vacíos.");
            }

            System.out.println("Intentando conectar a la base de datos...");
            //conn = DriverManager.getConnection(url, user, password);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa.");
        }
        return conn;
    }

    public void realizarConsulta(String consulta) {
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión desde la clase Conexion
            conn = Conexion.getInstacen().getConetion();
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
            // Cerrar ResultSet, Statement y Connection en el orden inverso de su creación
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
	}
}
