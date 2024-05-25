package Aplicacion;

import java.io.IOException;

import BD.Conexion;
import Controlador.CrearExamenController;
import Controlador.ListaExamenesController;
import Controlador.LoginController;
import Services.AdministradorService;
import Services.AlumnoService;
import Services.DocenteService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        // Agregar un shutdown hook para cerrar la conexión cuando la aplicación termine
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Conexion.cerrarConexion();
            System.out.println("conexion cerrada");
        }));

        launch(args);
    }
	
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Vista/LoginView.fxml"));
			Parent root = loader.load();

			LoginController controller = loader.getController();
			controller.setMainApp(this);

			Scene scene = new Scene(root);
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Examenes en linea");
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			System.out.println("Errores al iniciar la aplicación");
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void showListaExamenes(String correo) {
		try{
			// Carga del fxml de eleccion de modulo.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("/Vista/ListaExamenes.fxml"));
	        Parent root = loader.load();
	        
	        ListaExamenesController controller = loader.getController();
			controller.setMainApp(this, correo);
			
			Scene scene = new Scene(root);
			this.primaryStage.setTitle("Examenes en linea");
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
    	} catch (IOException e) {
    		System.out.println("Error al inicial la lista de examenes");
    		e.printStackTrace();
    	}		
		
	}
	
	public void showCrearExamen(String correo) {
		try{
			// Carga del fxml de eleccion de modulo.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("/Vista/CrearExamen.fxml"));
	        Parent root = loader.load();
	        
	        CrearExamenController controller = loader.getController();
			controller.setMainApp(this, correo);
			
			Scene scene = new Scene(root);
			this.primaryStage.setTitle("Examenes en linea");
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
    	} catch (IOException e) {
    		System.out.println("Error al inicial la vista de crear examen");
    		e.printStackTrace();
    	}		
		
	}

	public boolean ingresarAlumno(String correo, String password) {
		return AlumnoService.ingresar(correo, password);
	}

	public boolean ingresarDocente(String correo, String password) {
		return DocenteService.ingresar(correo, password);
	}

	public boolean ingresarAdmin(String correo, String password) {
		return AdministradorService.ingresar(correo, password);
	}

}
