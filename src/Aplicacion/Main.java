package Aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import BD.Conexion;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Vista/LoginView.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            System.out.println("Hay errores");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Agregar un shutdown hook para cerrar la conexión cuando la aplicación termine
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Conexion.cerrarConexion();
            System.out.println("conexion cerrada");
        }));

        launch(args);
    }
}
