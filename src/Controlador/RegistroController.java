package Controlador;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

import javafx.scene.control.PasswordField;

public class RegistroController {
	@FXML
	private TextField txtCorreo;
	@FXML
	private PasswordField txtPass;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellido;
	@FXML
	private TextField txtNumId;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtTelefono;
	@FXML
	private ComboBox comboBoxGrupo;

	// Event Listener on Button.onAction
	@FXML
	public void registrar(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button.onAction
	@FXML
	public void volver(ActionEvent event) {
		// TODO Autogenerated
	}
}
