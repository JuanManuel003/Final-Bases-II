package Controlador;

import Aplicacion.Main;
import Modelo.Docente;
import Modelo.Grupo;
import Modelo.Tema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CrearExamenController {

	private Main aplicacion;

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

	private ObservableList<Tema> listTemaExamen;
	private ObservableList<Grupo> listGrupo;

	@FXML
	void AgregarPreguntas(ActionEvent event) {

	}

	@FXML
	void CrearConfig(ActionEvent event) {

	}

	@FXML
	void volver(ActionEvent event) {

	}

	public void setMainApp(Main main, String correo) {
		this.aplicacion = main;

		// Inicializar listas
		this.listTemaExamen = FXCollections.observableArrayList(aplicacion.cargarTemasExamen());
		this.listGrupo = FXCollections.observableArrayList(aplicacion.cargarGrupos());

		// obtener nombre del docente
		Docente docente = aplicacion.obtenerDocente(correo);

		// Extraer descripciones y agregar al comboBox
		ObservableList<String> descripcionesTema = FXCollections.observableArrayList();
		for (Tema tema : listTemaExamen) {
			descripcionesTema.add(tema.getDescripcion());
		}

		ObservableList<String> nombreGrupos = FXCollections.observableArrayList();
		for (Grupo grupo : listGrupo) {
			nombreGrupos.add(grupo.getNombre());
		}

		comboBoxTemaExamen.setItems(descripcionesTema);
		comboBoxGrupo.setItems(nombreGrupos);

		// setear el nombre del docente
		this.txtnombreDocente.setText(docente.getNombre());

	}

}
