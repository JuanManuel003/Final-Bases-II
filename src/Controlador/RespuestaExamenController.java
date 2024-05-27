package Controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Aplicacion.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class RespuestaExamenController {

	private Main aplicacion;
	private int idExamen;
	private int idAlumno;

	@FXML
	private RadioButton opc1Preg1;

	@FXML
	private RadioButton opc1Preg2;

	@FXML
	private RadioButton opc1Preg3;

	@FXML
	private RadioButton opc1Preg4;

	@FXML
	private RadioButton opc1Preg5;

	@FXML
	private RadioButton opc2Preg1;

	@FXML
	private RadioButton opc2Preg2;

	@FXML
	private RadioButton opc2Preg3;

	@FXML
	private RadioButton opc2Preg4;

	@FXML
	private RadioButton opc2Preg5;

	@FXML
	private RadioButton opc3Preg1;

	@FXML
	private RadioButton opc3Preg2;

	@FXML
	private RadioButton opc3Preg3;

	@FXML
	private RadioButton opc3Preg4;

	@FXML
	private RadioButton opc3Preg5;

	@FXML
	private RadioButton opc4Preg1;

	@FXML
	private RadioButton opc4Preg2;

	@FXML
	private RadioButton opc4Preg3;

	@FXML
	private RadioButton opc4Preg4;

	@FXML
	private RadioButton opc4Preg5;

	@FXML
	private TextField txtNombreExamen;

	@FXML
	private TextField txtPregunta1;

	@FXML
	private TextField txtPregunta2;

	@FXML
	private TextField txtPregunta3;

	@FXML
	private TextField txtPregunta4;

	@FXML
	private TextField txtPregunta5;

	@FXML
	void Enviar(ActionEvent event) {
		int idPregunta1 = aplicacion.obtenerIdPregunta(txtPregunta1.getText());
		int idPregunta2 = aplicacion.obtenerIdPregunta(txtPregunta2.getText());
		int idPregunta3 = aplicacion.obtenerIdPregunta(txtPregunta3.getText());
		int idPregunta4 = aplicacion.obtenerIdPregunta(txtPregunta4.getText());
		int idPregunta5 = aplicacion.obtenerIdPregunta(txtPregunta5.getText());

		procesarRespuestas(opc1Preg1, opc2Preg1, opc3Preg1, opc4Preg1, idPregunta1);
		procesarRespuestas(opc1Preg2, opc2Preg2, opc3Preg2, opc4Preg2, idPregunta2);
		procesarRespuestas(opc1Preg3, opc2Preg3, opc3Preg3, opc4Preg3, idPregunta3);
		procesarRespuestas(opc1Preg4, opc2Preg4, opc3Preg4, opc4Preg4, idPregunta4);
		procesarRespuestas(opc1Preg5, opc2Preg5, opc3Preg5, opc4Preg5, idPregunta5);

		JOptionPane.showMessageDialog(null, "Respuestas guardadas");
		VolverLogin(event);
	}

	private void procesarRespuestas(RadioButton opc1, RadioButton opc2, RadioButton opc3, RadioButton opc4,
			int idPregunta) {
		crearRespuestaAlumno(idAlumno, idExamen, idPregunta, opc1.isSelected());
		crearRespuestaAlumno(idAlumno, idExamen, idPregunta, opc2.isSelected());
		crearRespuestaAlumno(idAlumno, idExamen, idPregunta, opc3.isSelected());
		crearRespuestaAlumno(idAlumno, idExamen, idPregunta, opc4.isSelected());
	}

	// Método auxiliar para crear una respuesta
	private void crearRespuestaAlumno(int idAlumno, int idExamen, int idPregunta, boolean esCorrecta) {
		String estado = esCorrecta ? "Correcta" : "Incorrecta";
		aplicacion.CrearRespuestasAlumno(idAlumno, idExamen, idPregunta, estado);
	}

	@FXML
	void Volver(ActionEvent event) {
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

	private void VolverLogin(ActionEvent event) {
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

	@FXML
	void initialize() {
		// crear un grupo con los Radio Button para que solo seleccione 1
		ToggleGroup tg = new ToggleGroup();

		this.opc1Preg1.setToggleGroup(tg);
		this.opc2Preg1.setToggleGroup(tg);
		this.opc3Preg1.setToggleGroup(tg);
		this.opc4Preg1.setToggleGroup(tg);

		ToggleGroup tg2 = new ToggleGroup();

		this.opc1Preg2.setToggleGroup(tg2);
		this.opc2Preg2.setToggleGroup(tg2);
		this.opc3Preg2.setToggleGroup(tg2);
		this.opc4Preg2.setToggleGroup(tg2);

		ToggleGroup tg3 = new ToggleGroup();

		this.opc1Preg3.setToggleGroup(tg3);
		this.opc2Preg3.setToggleGroup(tg3);
		this.opc3Preg3.setToggleGroup(tg3);
		this.opc4Preg3.setToggleGroup(tg3);

		ToggleGroup tg4 = new ToggleGroup();

		this.opc1Preg4.setToggleGroup(tg4);
		this.opc2Preg4.setToggleGroup(tg4);
		this.opc3Preg4.setToggleGroup(tg4);
		this.opc4Preg4.setToggleGroup(tg4);

		ToggleGroup tg5 = new ToggleGroup();

		this.opc1Preg5.setToggleGroup(tg5);
		this.opc2Preg5.setToggleGroup(tg5);
		this.opc3Preg5.setToggleGroup(tg5);
		this.opc4Preg5.setToggleGroup(tg5);
	}

	public void setMainApp(Main main, int idExamen, int idAlumno) {
		this.aplicacion = main;
		this.idExamen = idExamen;
		this.idAlumno = idAlumno;

		String nombreExamen = aplicacion.obtenerNombreExamen(idExamen);
		this.txtNombreExamen.setText(nombreExamen);

		ArrayList<String> preguntasExamen = aplicacion.obtenerPreguntasExamen(idExamen);

		this.txtPregunta1.setText(preguntasExamen.get(0));
		this.txtPregunta2.setText(preguntasExamen.get(1));
		this.txtPregunta3.setText(preguntasExamen.get(2));
		this.txtPregunta4.setText(preguntasExamen.get(3));
		this.txtPregunta5.setText(preguntasExamen.get(4));

		ArrayList<String> respuestasPreguntas = aplicacion.obtenerNombreRespuestas(preguntasExamen.get(0));

		this.opc1Preg1.setText(respuestasPreguntas.get(0));
		this.opc2Preg1.setText(respuestasPreguntas.get(1));
		this.opc3Preg1.setText(respuestasPreguntas.get(2));
		this.opc4Preg1.setText(respuestasPreguntas.get(3));

		ArrayList<String> respuestasPreguntas2 = aplicacion.obtenerNombreRespuestas(preguntasExamen.get(1));

		this.opc1Preg2.setText(respuestasPreguntas2.get(0));
		this.opc2Preg2.setText(respuestasPreguntas2.get(1));
		this.opc3Preg2.setText(respuestasPreguntas2.get(2));
		this.opc4Preg2.setText(respuestasPreguntas2.get(3));

		ArrayList<String> respuestasPreguntas3 = aplicacion.obtenerNombreRespuestas(preguntasExamen.get(2));

		this.opc1Preg3.setText(respuestasPreguntas3.get(0));
		this.opc2Preg3.setText(respuestasPreguntas3.get(1));
		this.opc3Preg3.setText(respuestasPreguntas3.get(2));
		this.opc4Preg3.setText(respuestasPreguntas3.get(3));

		ArrayList<String> respuestasPreguntas4 = aplicacion.obtenerNombreRespuestas(preguntasExamen.get(3));

		this.opc1Preg4.setText(respuestasPreguntas4.get(0));
		this.opc2Preg4.setText(respuestasPreguntas4.get(1));
		this.opc3Preg4.setText(respuestasPreguntas4.get(2));
		this.opc4Preg4.setText(respuestasPreguntas4.get(3));

		ArrayList<String> respuestasPreguntas5 = aplicacion.obtenerNombreRespuestas(preguntasExamen.get(4));

		this.opc1Preg5.setText(respuestasPreguntas5.get(0));
		this.opc2Preg5.setText(respuestasPreguntas5.get(1));
		this.opc3Preg5.setText(respuestasPreguntas5.get(2));
		this.opc4Preg5.setText(respuestasPreguntas5.get(3));

	}
}
