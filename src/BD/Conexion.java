package BD;

//import java.sql.Connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Conexion Pfinal;

    private Conexion() {
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
}
