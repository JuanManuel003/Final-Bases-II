package Controlador;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JOptionPane;

import Aplicacion.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CrearConfigController {
	
	private Main aplicacion;
	private String correo;
	@FXML
	private TextField txtHora;
	@FXML
    private TextField txtHoraPresentacion;
	@FXML
	private TextField txtPeso;
	@FXML
	private TextField txtMinutos;
	@FXML
	private TextField txtUmbral;
	@FXML
	private DatePicker datePicker;

	// Event Listener on Button.onAction
	@FXML
	public void GuardarConfiguracion(ActionEvent event) {
		try {
            // Obtener los valores de los TextField
            int horas = Integer.parseInt(txtHora.getText());
            int minutos = Integer.parseInt(txtMinutos.getText());
            int peso = Integer.parseInt(txtPeso.getText());
            int umbral = Integer.parseInt(txtUmbral.getText());
            LocalDate fechaLocal = datePicker.getValue();
            int horaPresent = Integer.parseInt(txtHoraPresentacion.getText());

         // Crear el intervalo de tiempo en formato de INTERVAL 'X' HOUR TO MINUTE
            String intervaloTiempo = String.format("INTERVAL '%d' HOUR + INTERVAL '%d' MINUTE", horas, minutos);
           

            // Comprobar que la fecha no es nula
            if (fechaLocal == null) {
            	JOptionPane.showMessageDialog(null, "La fecha de presentación no puede estar vacía");
            }

            // Combinar la fecha y la hora en un LocalDateTime
            LocalTime horaPresentacion = LocalTime.of(horaPresent, 0);
            LocalDateTime fechaYTiempoLocal = LocalDateTime.of(fechaLocal, horaPresentacion);

            // Convertir LocalDateTime a Timestamp
            Timestamp fechaPresentacion = Timestamp.valueOf(fechaYTiempoLocal);

            // Aquí puedes continuar con el almacenamiento o procesamiento de los datos
            
            aplicacion.guardarConfig(5,intervaloTiempo, peso, umbral, fechaPresentacion, 0);
            
            //volver a la interfaz de crear examen luego de crear la configuracion
            boolean bandera = true;
            aplicacion.VolverCrearExamen(event, correo, bandera);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Manejo de error en caso de que los valores de hora o minutos no sean válidos
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // Manejo de error en caso de que la fecha sea nula
        }
	}

	/*private void VolverCrearExamen(ActionEvent event) {
		try {
	        // Cargar la vista del login desde el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/CrearExamen.fxml"));
	        Parent root = loader.load();

	        // Obtener el controlador del login para pasarle el contexto de la aplicación si es necesario
	        CrearExamenController examenController = loader.getController();
	        examenController.setMainApp(this.aplicacion, correo);

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
		
	}*/

	public void setMainApp(Main aplicacion, String correo) {
		this.aplicacion = aplicacion;
		this.correo = correo;
	}
}
