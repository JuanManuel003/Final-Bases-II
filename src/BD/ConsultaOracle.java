package BD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultaOracle {

	public void realizarConsulta(String consulta) {
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Obtener la conexi�n desde la clase Conexion
            conn = Conexion.getInstacen().getConetion();
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
            // Cerrar ResultSet, Statement y Connection en el orden inverso de su creaci�n
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
	}


	public static void main(String[] args) {
		ConsultaOracle consultaOracle = new ConsultaOracle();
		// Reemplaza con tu consulta SQL
		String consulta = "select * from alumno";
		consultaOracle.realizarConsulta(consulta);
		System.out.println("consulta terminada");
	}

}
