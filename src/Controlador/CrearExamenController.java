package Controlador;

import Aplicacion.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CrearExamenController {

    @FXML
    private TextField txtNombreExamen;

    @FXML
    private ComboBox<?> comboBoxAlumno;

    @FXML
    private ComboBox<?> comboBoxTemaExamen;

    @FXML
    private ComboBox<?> comboBoxCategoria;

    @FXML
    private ComboBox<?> comboBoxDocente;

    @FXML
    private ComboBox<?> comboBoxConfig;

    @FXML
    private TextField txtDescripcion;

    @FXML
    void Agregar(ActionEvent event) {

    }

    @FXML
    void volver(ActionEvent event) {

    }

	public void setMainApp(Main main, String correo) {
		// TODO Auto-generated method stub
		
	}

}
