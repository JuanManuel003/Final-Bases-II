package Modelo;

public class Curso {

	private int id;
	private String nombre;
	private String planEstudio;

	public Curso(){}

	public Curso(int id, String nombre, String planEstudio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.planEstudio = planEstudio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPlanEstudio() {
		return planEstudio;
	}

	public void setPlanEstudio(String planEstudio) {
		this.planEstudio = planEstudio;
	}



}
