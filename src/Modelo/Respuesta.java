package Modelo;

public class Respuesta {

	private int id;
	private String descripcion;
	private String respuestaCorrecta;
	private int idPregunta;

	public Respuesta(){}

	public Respuesta(int id, String descripcion, String respuestaCorrecta, int idPregunta) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.respuestaCorrecta = respuestaCorrecta;
		this.idPregunta = idPregunta;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}
	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}
	public int getIdPregunta() {
		return idPregunta;
	}
	public void setIdPregunta(int idPregunta) {
		this.idPregunta = idPregunta;
	}

}
