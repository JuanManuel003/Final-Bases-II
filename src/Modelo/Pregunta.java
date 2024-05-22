package Modelo;

public class Pregunta {

	private int id;
	private String descripcion;
	private int procentaje;
	private int idTipoPregunta;
	private int idTema;
	private int idPrivacidadPregunta;
	private int idDocente;

	public Pregunta(){}

	public Pregunta(int id, String descripcion, int procentaje, int idTipoPregunta, int idTema,
			int idPrivacidadPregunta, int idDocente) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.procentaje = procentaje;
		this.idTipoPregunta = idTipoPregunta;
		this.idTema = idTema;
		this.idPrivacidadPregunta = idPrivacidadPregunta;
		this.idDocente = idDocente;
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
	public int getProcentaje() {
		return procentaje;
	}
	public void setProcentaje(int procentaje) {
		this.procentaje = procentaje;
	}
	public int getIdTipoPregunta() {
		return idTipoPregunta;
	}
	public void setIdTipoPregunta(int idTipoPregunta) {
		this.idTipoPregunta = idTipoPregunta;
	}
	public int getIdTema() {
		return idTema;
	}
	public void setIdTema(int idTema) {
		this.idTema = idTema;
	}
	public int getIdPrivacidadPregunta() {
		return idPrivacidadPregunta;
	}
	public void setIdPrivacidadPregunta(int idPrivacidadPregunta) {
		this.idPrivacidadPregunta = idPrivacidadPregunta;
	}
	public int getIdDocente() {
		return idDocente;
	}
	public void setIdDocente(int idDocente) {
		this.idDocente = idDocente;
	}

}
