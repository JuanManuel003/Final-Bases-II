package Controlador;

import java.io.IOException;

import javax.swing.JOptionPane;

import Aplicacion.Main;
import Modelo.Docente;
import Modelo.Grupo;
import Modelo.Tema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CrearExamenController {

	private Main aplicacion;
	private String correo;

	@FXML
	private ComboBox<String> comboBoxGrupo;

	@FXML
	private ComboBox<String> comboBoxTemaExamen;

	@FXML
	private TextArea txtDescripcion;

	@FXML
	private TextField txtNombreExamen;

	@FXML
	private TextField txtnombreDocente;

	// Variable para verificar si la configuracion fue creada
	private boolean configCreada = false;
	private ObservableList<Tema> listTemaExamen;
	private ObservableList<Grupo> listGrupo;
	private Docente docente;

	@FXML
	void AgregarPreguntas(ActionEvent event) {
		int idConfig = aplicacion.obtenerIdconfig();

		if (camposRellenos() &&  configCreada) {

			String nombreExamen = this.txtNombreExamen.getText();
			String descripcion = this.txtDescripcion.getText();
			int idTemaExamen = obtenerIdTemaSeleccionado();
			int idDocente = docente.getId();
			int idGrupo = obtenerIdGrupoSeleccionado();

			if (idTemaExamen != -1 && idGrupo != -1) {
				int idExamenGenerado = aplicacion.CrearExamen(nombreExamen, descripcion, idTemaExamen, idGrupo,
						idDocente, idConfig, idGrupo);

				if (idExamenGenerado != -1) {
					System.out.println("Examen creado con ID: " + idExamenGenerado);

					aplicacion.showCrearPreguntas(event, idTemaExamen, docente.getId(), idExamenGenerado, correo);
				} else {
					JOptionPane.showMessageDialog(null, "Error al crear el examen.");
				}

			} else {
				JOptionPane.showMessageDialog(null, "El tema o el grupo seleccionado no es válido");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Por favor llenar los campos y cree la configuracion");
		}

	}

	private int obtenerIdGrupoSeleccionado() {
		// Obtener la descripción del grupo seleccionado
		String descripcionGrupo = comboBoxGrupo.getSelectionModel().getSelectedItem();
		// Buscar el grupo correspondiente en la lista de grupos
		for (Grupo grupo : listGrupo) {
			if (grupo.getNombre().equals(descripcionGrupo)) {
				return grupo.getId();
			}
		}
		// Retornar -1 si el grupo seleccionado no se encuentra en la lista
		return -1;
	}

	private int obtenerIdTemaSeleccionado() {
		// Obtener la descripción del tema seleccionado
		String descripcionTema = comboBoxTemaExamen.getSelectionModel().getSelectedItem();
		// Buscar el tema correspondiente en la lista de temas
		for (Tema tema : listTemaExamen) {
			if (tema.getDescripcion().equals(descripcionTema)) {
				return tema.getId();
			}
		}
		// Retornar -1 si el tema seleccionado no se encuentra en la lista
		return -1;
	}

	private boolean camposRellenos() {
		if (this.txtNombreExamen.getText().isEmpty()) {
			return false;
		}
		if (this.txtDescripcion.getText().isEmpty()) {
			return false;
		}
		if (this.comboBoxGrupo.getSelectionModel().getSelectedItem() == null) {
			return false;
		}
		if (this.comboBoxTemaExamen.getSelectionModel().getSelectedItem() == null) {
			return false;
		}
		return true;
	}

	@FXML
	void CrearConfig(ActionEvent event) {
		//cambiar el valor de cconfiguracion creada para validar que ya se creo
		this.configCreada = true;
		//crear ventana
		aplicacion.showCargarConfig(correo);
	}

	@FXML
	void volver(ActionEvent event) {
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

	public void setMainApp(Main main, String correo, boolean bandera) {
		this.aplicacion = main;
		this.correo = correo;
		this.configCreada  = bandera;

		// Inicializar listas
		this.listTemaExamen = FXCollections.observableArrayList(aplicacion.cargarTemasExamen());
		this.listGrupo = FXCollections.observableArrayList(aplicacion.cargarGrupos());

		// obtener nombre del docente
		this.docente = aplicacion.obtenerDocente(correo);

		// Extraer descripciones y agregar al comboBox
		ObservableList<String> descripcionesTema = FXCollections.observableArrayList();
		for (Tema tema : listTemaExamen) {
			descripcionesTema.add(tema.getDescripcion());
		}

		ObservableList<String> nombreGrupos = FXCollections.observableArrayList();
		for (Grupo grupo : listGrupo) {
			nombreGrupos.add(grupo.getNombre());
		}

		// inicializar los comboBox
		comboBoxTemaExamen.setItems(descripcionesTema);
		comboBoxGrupo.setItems(nombreGrupos);

		// setear el nombre del docente
		String nombreDocente = docente.getNombre() + " " + docente.getApellido();
		this.txtnombreDocente.setText(nombreDocente);

	}

}
