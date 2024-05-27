package Aplicacion;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import BD.Conexion;
import Controlador.CrearConfigController;
import Controlador.CrearExamenController;
import Controlador.CrearPreguntasController;
import Controlador.ListaExamenesController;
import Controlador.LoginController;
import Controlador.RespuestaExamenController;
import Modelo.Alumno;
import Modelo.Docente;
import Modelo.Examen;
import Modelo.Grupo;
import Modelo.PrivacidadPregunta;
import Modelo.Tema;
import Services.AdministradorService;
import Services.AlumnoService;
import Services.CrearExamenService;
import Services.CrearPreguntaService;
import Services.DocenteService;
import Services.ListaExamenService;
import Services.RespuestaExamenService;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

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

	public static void main(String[] args) {
		// Agregar un shutdown hook para cerrar la conexión cuando la aplicación
		// termine
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			Conexion.cerrarConexion();
			System.out.println("conexion cerrada");
		}));

		launch(args);
	}

	public void showListaExamenes(String correo) {
		try {
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
		try {
			// Carga del fxml de eleccion de modulo.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Vista/CrearExamen.fxml"));
			Parent root = loader.load();

			CrearExamenController controller = loader.getController();
			controller.setMainApp(this, correo, false);

			Scene scene = new Scene(root);
			this.primaryStage.setTitle("Examenes en linea");
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			System.out.println("Error al inicial la vista de crear examen");
			e.printStackTrace();
		}

	}

	public void showCargarConfig(String correo) {
		try {
			// Carga del fxml de eleccion de modulo.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Vista/CrearConfig.fxml"));
			Parent root = loader.load();

			CrearConfigController configController = loader.getController();
			configController.setMainApp(this, correo);

			Scene scene = new Scene(root);
			this.primaryStage.setTitle("Examenes en linea");
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			System.out.println("Error al inicial la vista de configuracion");
			e.printStackTrace();
		}
	}

	public void VolverCrearExamen(ActionEvent event, String correo, boolean bandera) {
		try {
			// Carga del fxml de eleccion de modulo.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Vista/CrearExamen.fxml"));
			Parent root = loader.load();

			// Obtener el controlador del login para pasarle el contexto de la
			// aplicación si es necesario
			CrearExamenController examenController = loader.getController();
			examenController.setMainApp(this, correo, bandera);

			Scene scene = new Scene(root);
			this.primaryStage.setTitle("Examenes en linea");
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			System.out.println("Error al inicial la vista de crear examen");
			e.printStackTrace();
		}

	}

	public void showCrearPreguntas(ActionEvent event, int idTemaExamen, int idDocente, int idExamenGenerado, String correo) {
		try {
			// Carga del fxml de eleccion de modulo.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Vista/CrearPreguntas.fxml"));
			Parent root = loader.load();

			CrearPreguntasController preguntasController = loader.getController();
			preguntasController.setMainApp(this, idTemaExamen, idDocente, idExamenGenerado, correo);

			Scene scene = new Scene(root);
			this.primaryStage.setTitle("Examenes en linea");
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			System.out.println("Error al inicial la vista de crear Preguntas");
			e.printStackTrace();
		}
	}
	
	public void showResponderExamen(int idExamen, int idAlumno) {
		try {
			// Carga del fxml de eleccion de modulo.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/Vista/RespuestaExamen.fxml"));
			Parent root = loader.load();

			RespuestaExamenController respuestaExamen = loader.getController();
			respuestaExamen.setMainApp(this, idExamen, idAlumno);

			Scene scene = new Scene(root);
			this.primaryStage.setTitle("Examenes en linea");
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			System.out.println("Error al inicial la vista de crear responder examen");
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

	public ObservableList<Tema> cargarTemasExamen() {
		return CrearExamenService.obtenerTemas();
	}

	public ObservableList<Grupo> cargarGrupos() {
		return CrearExamenService.obtenerGrupos();
	}

	public Docente obtenerDocente(String correo) {
		return CrearExamenService.obtenerDocente(correo);
	}

	public void guardarConfig(int numPreguntas, String intervaloTiempo, int peso, int umbral,
			Timestamp fechaPresentacion, int numPreguntasBanco) {
		CrearExamenService.guardarConfig(numPreguntas, intervaloTiempo, peso, umbral, fechaPresentacion,
				numPreguntasBanco);

	}

	public int obtenerIdconfig() {
		return CrearExamenService.obtenerIdConfig();
	}

	public int CrearExamen(String nombreExamen, String descripcion, int idTemaExamen, int idGrupo, int idDocente,
			int idConfig, int idGrupo2) {
		return CrearExamenService.crearExamen(nombreExamen, descripcion, idTemaExamen, idDocente, idConfig, idGrupo);

	}

	public ObservableList<PrivacidadPregunta> cargarPrivacidad() {
		return CrearPreguntaService.obtenerPrivacidad();
	}

	public int CrearPregunta(String descripcion, int porcentaje, int tipoPregunta, int idTema, int idPrivacidad,
			int idDocente, int idExamen) {
		return CrearPreguntaService.CrearPregunta(descripcion, porcentaje, tipoPregunta, idTema, idPrivacidad,
				idDocente, idExamen);
	}

	public void CrearRespuestas(String opc, String rb, int idPreguntaCreada) {
		CrearPreguntaService.CrearRespuesta(opc, rb, idPreguntaCreada);

	}

	public void actualizarEstadoExamen(int idExamen) {
		CrearExamenService.actualizarEstadoExamen(idExamen);

	}

	public int obtenerNumeroPreguntasExamen(int idExamen) {
		return CrearPreguntaService.obtenerNumPreguntas(idExamen);
	}

	public Alumno obtenerNombreAlumno(String correo) {
		return AlumnoService.obtenerAlumno(correo);
	}

	public ObservableList<Examen> cargarExamenesAlumno(int idAlumno) {
		return ListaExamenService.obtenerExamenes(idAlumno);
	}

	public String obtenerNombreExamen(int idExamen) {
		return RespuestaExamenService.obtenerNombreExamen(idExamen);
	}

	public ArrayList<String> obtenerPreguntasExamen(int idExamen) {
		return RespuestaExamenService.obtenerNombresPreguntas(idExamen);
	}

	public ArrayList<String> obtenerNombreRespuestas(String nombrePregunta) {
		return RespuestaExamenService.obtenerNombresRespuestas(nombrePregunta);
	}

	public int obtenerIdPregunta(String nombrePregunta) {
		return RespuestaExamenService.obtenerIdPregunta(nombrePregunta);
	}

	public void CrearRespuestasAlumno(int idAlumno, int idExamen, int idPregunta, String estado) {
		RespuestaExamenService.crearRespuestaAlumno(idAlumno, idExamen, idPregunta, estado);
		
	}

}
