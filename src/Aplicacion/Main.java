package Aplicacion;

import java.sql.Connection;
import java.sql.SQLException;

import BD.Conexion;

public class Main {
    public static void main(String[] args) {
        Conexion conexion = Conexion.getInstacen();
        Connection conn;

        try {
            conn = conexion.getConetion();
            System.out.println("Estado de la conexión: " + (conn != null ? "Conectado" : "No conectado"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
