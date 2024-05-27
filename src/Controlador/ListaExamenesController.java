package Controlador;

import java.io.IOException;

import javax.swing.JOptionPane;

import Aplicacion.Main;
import Modelo.Alumno;
import Modelo.Examen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ListaExamenesController {

	private Main aplicacion;
	private Alumno alumno;

	@FXML
	private TextField txtNombreAlumno;
	@FXML
	private ComboBox<String> comboBoxNombresExamenes;
	
	private ObservableList<Examen> listaExamenes;

	// Event Listener on Button.onAction
	@FXML
	public void Responder(ActionEvent event) {
		if(camposRellenos()){
			int idExamen = obtenerIdExamenSeleccionado();
			
			aplicacion.showResponderExamen(idExamen, alumno.getId());
		}else{
			JOptionPane.showMessageDialog(null, "Seleccione un examen ");
		}
	}
	
	private int obtenerIdExamenSeleccionado() {
		// Obtener la descripción del grupo seleccionado
		String nombreExamen = comboBoxNombresExamenes.getSelectionModel().getSelectedItem();
		// Buscar el grupo correspondiente en la lista de grupos
		for (Examen examen : this.listaExamenes) {
			if (examen.getNombre().equals(nombreExamen)) {
				return examen.getId();
			}
		}
		// Retornar -1 si el grupo seleccionado no se encuentra en la lista
		return -1;
	}

	private boolean camposRellenos() {
		if (comboBoxNombresExamenes.getSelectionModel().getSelectedItem() == null) {
			return false;
		}
		return true;
	}

	// Event Listener on Button.onAction
	@FXML
	public void Salir(ActionEvent event) {
		try {
			// Cargar la vista del login desde el archivo FXML
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/LoginView.fxml"));
			Parent root = loader.load();

			// Obtener el controlador del login para pasarle el contexto de la
			// aplicación si es necesario
			LoginController loginController = loader.getController();
			loginController.setMainApp(this.aplicacion);

			// Obtener la escena actual a partir del botón que disparó el evento
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			// Establecer la escena del login en el stage
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Examenes en linea");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMainApp(Main main, String correo) {
		this.aplicacion = main;
		
		// buscamos el nombre en la base de datos con el correo
		this.alumno = aplicacion.obtenerNombreAlumno(correo);
		String nombreAlumno = alumno.getNombre() + " " + alumno.getApellido();
		this.txtNombreAlumno.setText(nombreAlumno);

		// cargamos los exámenes de la base de datos
		this.listaExamenes = aplicacion.cargarExamenesAlumno(alumno.getId());

		// Extraer descripciones y agregar al comboBox
		ObservableList<String> nombresExamen = FXCollections.observableArrayList();
		for (Examen examen : listaExamenes) {
			nombresExamen.add(examen.getNombre());
		}

		// configuramos el ComboBox para mostrar los exámenes
		comboBoxNombresExamenes.setItems(nombresExamen);
	}
}
