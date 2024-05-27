package Controlador;

import javax.swing.JOptionPane;

import Aplicacion.Main;
import Modelo.PrivacidadPregunta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class CrearPreguntasController {

	private Main aplicacion;
	private String correo;

	@FXML
	private TextArea txtDescripcion;
	@FXML
	private TextField txtPorcentaje;
	@FXML
	private ComboBox<String> ComboBoxPrivacidad;
	@FXML
	private RadioButton RbOpcion1;
	@FXML
	private TextField txtOpcion1;
	@FXML
	private RadioButton RbOpcion2;
	@FXML
	private TextField txtOpcion2;
	@FXML
	private RadioButton RbOpcion3;
	@FXML
	private TextField txtOpcion3;
	@FXML
	private RadioButton RbOpcion4;
	@FXML
	private TextField txtOpcion4;

	// Variable para verificar si la pregunta fue creada
	private boolean preguntaCreada = false;
	private int idTema;
	private int idDocente;
	private ObservableList<PrivacidadPregunta> listaPrivacidad;
	private int idPreguntaCreada;
	private int idExamen;
	private int numeroPreguntas;

	// Event Listener on Button[#idBtnCrearPregunta].onAction
	@FXML
	public void CrearPregunta(ActionEvent event) {
		
		if (camposRellenos()) {
			
			numeroPreguntas = aplicacion.obtenerNumeroPreguntasExamen(idExamen);
			
			if(numeroPreguntas < 5){
				// Cambiar el valor de la variable para indicar que la pregunta fue
				// creada
				preguntaCreada = true;

				String descripcion = this.txtDescripcion.getText();
				int porcentaje = Integer.parseInt(this.txtPorcentaje.getText());
				int idPrivacidad = obtenerIdPrivacidadSeleccionado();

				idPreguntaCreada = aplicacion.CrearPregunta(descripcion, porcentaje, 1, idTema, idPrivacidad, idDocente, idExamen);

				JOptionPane.showMessageDialog(null, "Pregunta creada, crea las opciones de la pregunta");
			}else{
				JOptionPane.showMessageDialog(null, "Ya tiene el maximo de preguntas creadas");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Llenar todos los campos necesarios para crear pregunta");
		}

	}

	private int obtenerIdPrivacidadSeleccionado() {
		// Obtener la descripción del tema seleccionado
		String descripcionPriv = this.ComboBoxPrivacidad.getSelectionModel().getSelectedItem();
		// Buscar el tema correspondiente en la lista de temas
		for (PrivacidadPregunta privacidad : listaPrivacidad) {
			if (privacidad.getDescripcion().equals(descripcionPriv)) {
				return privacidad.getId();
			}
		}
		// Retornar -1 si el tema seleccionado no se encuentra en la lista
		return -1;
	}

	private boolean camposRellenos() {
		if (this.txtDescripcion.getText().isEmpty()) {
			return false;
		}
		if (this.txtPorcentaje.getText().isEmpty()) {
			return false;
		}
		if (this.ComboBoxPrivacidad.getSelectionModel().getSelectedItem() == null) {
			return false;
		}
		return true;
	}

	// Event Listener on Button.onAction
	@FXML
	public void CrearOpcionesRespuesta(ActionEvent event) {
		// Verificar si la pregunta ya fue creada
		if (preguntaCreada) {
			// Verificar si alguna opción está seleccionada
			if (validarSeleccion()) {
				if (camposRellenosRespuesta()) {
					// Crear las respuestas
					crearRespuesta(this.txtOpcion1.getText(), this.RbOpcion1.isSelected(), idPreguntaCreada);
					crearRespuesta(this.txtOpcion2.getText(), this.RbOpcion2.isSelected(), idPreguntaCreada);
					crearRespuesta(this.txtOpcion3.getText(), this.RbOpcion3.isSelected(), idPreguntaCreada);
					crearRespuesta(this.txtOpcion4.getText(), this.RbOpcion4.isSelected(), idPreguntaCreada);
					
					JOptionPane.showMessageDialog(null, "Opciones creadas");
					limpiarCampos();
				} else {
					JOptionPane.showMessageDialog(null, "Llenar todos los campos necesarios para cada opcion");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione la opción correcta de las respuestas");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Primero debe crear la pregunta.");
		}
	}
	
	@FXML
    void CrearPreguntaAleatoria(ActionEvent event) {
		if(numeroPreguntas < 5){
			aplicacion.actualizarEstadoExamen(idExamen);
			
			aplicacion.showCrearExamen(correo);
		}else{
			JOptionPane.showMessageDialog(null, "Ya tiene el maximo de preguntas creadas");
		}
		
    }

	private boolean camposRellenosRespuesta() {
		if(this.txtOpcion1.getText().isEmpty()){
			return false;
		}
		if(this.txtOpcion2.getText().isEmpty()){
			return false;
		}
		if(this.txtOpcion3.getText().isEmpty()){
			return false;
		}
		if(this.txtOpcion4.getText().isEmpty()){
			return false;
		}
		return true;
	}

	// Método auxiliar para crear una respuesta
	private void crearRespuesta(String opcion, boolean esCorrecta, int idPregunta) {
		String estado = esCorrecta ? "Correcta" : "Incorrecta";
		aplicacion.CrearRespuestas(opcion, estado, idPregunta);
	}

	// Método para validar que al menos una opción esté seleccionada
	private boolean validarSeleccion() {
		return this.RbOpcion1.isSelected() || this.RbOpcion2.isSelected() || this.RbOpcion3.isSelected()
				|| this.RbOpcion4.isSelected();
	}
	
	// Método para limpiar todos los campos
    private void limpiarCampos() {
        this.txtDescripcion.clear();
        this.txtPorcentaje.clear();
        this.ComboBoxPrivacidad.getSelectionModel().clearSelection();
        this.txtOpcion1.clear();
        this.txtOpcion2.clear();
        this.txtOpcion3.clear();
        this.txtOpcion4.clear();
        
        ToggleGroup tg = this.RbOpcion1.getToggleGroup();
        if (tg != null) {
            tg.selectToggle(null);  // Deseleccionar todos los RadioButton
        }
        
        preguntaCreada = false;  // Resetear el estado de la pregunta creada
    }
    
    @FXML
    void Volver(ActionEvent event) {
    	if(numeroPreguntas <= 4){
    		JOptionPane.showMessageDialog(null, "Aun no tiene el maximo de preguntas creadas");
    	}else{
    		aplicacion.showCrearExamen(correo);
    	}
    	
    }

	@FXML
	void initialize() {
		// crear un grupo con los Radio Button para que solo seleccione 1
		ToggleGroup tg = new ToggleGroup();

		this.RbOpcion1.setToggleGroup(tg);
		this.RbOpcion2.setToggleGroup(tg);
		this.RbOpcion3.setToggleGroup(tg);
		this.RbOpcion4.setToggleGroup(tg);
	}

	public void setMainApp(Main aplicacion, int idTemaExamen, int idDocente, int idExamenGenerado, String correo2) {
		this.aplicacion = aplicacion;
		this.idTema = idTemaExamen;
		this.idDocente = idDocente;
		this.idExamen = idExamenGenerado;
		this.correo = correo2;

		this.listaPrivacidad = FXCollections.observableArrayList(aplicacion.cargarPrivacidad());

		// Extraer descripciones y agregar al comboBox
		ObservableList<String> descripcionesPriv = FXCollections.observableArrayList();
		for (PrivacidadPregunta privacidad : listaPrivacidad) {
			descripcionesPriv.add(privacidad.getDescripcion());
		}

		this.ComboBoxPrivacidad.setItems(descripcionesPriv);

	}
}
