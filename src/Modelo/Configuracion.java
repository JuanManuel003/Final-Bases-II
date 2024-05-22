package Modelo;

public class Configuracion {

	private int id;
	private int numeroPreguntas;
	private String tiempo;
	private String peso;
	private int umbralAprobado;
	private String fechaPresentacion;
	private int numPreguntasBanco;

	public Configuracion(){}

	public Configuracion(int id, int numeroPreguntas, String tiempo, String peso, int umbralAprobado,
			String fechaPresentacion, int numPreguntasBanco) {
		super();
		this.id = id;
		this.numeroPreguntas = numeroPreguntas;
		this.tiempo = tiempo;
		this.peso = peso;
		this.umbralAprobado = umbralAprobado;
		this.fechaPresentacion = fechaPresentacion;
		this.numPreguntasBanco = numPreguntasBanco;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumeroPreguntas() {
		return numeroPreguntas;
	}

	public void setNumeroPreguntas(int numeroPreguntas) {
		this.numeroPreguntas = numeroPreguntas;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public int getUmbralAprobado() {
		return umbralAprobado;
	}

	public void setUmbralAprobado(int umbralAprobado) {
		this.umbralAprobado = umbralAprobado;
	}

	public String getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public int getNumPreguntasBanco() {
		return numPreguntasBanco;
	}

	public void setNumPreguntasBanco(int numPreguntasBanco) {
		this.numPreguntasBanco = numPreguntasBanco;
	}


}
