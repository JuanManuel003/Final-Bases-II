package Modelo;

public class Tema {

	private int id;
	private String descripcion;

	public Tema(){}

	public Tema(int id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
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
	public void setDescripcion(String descipcion) {
		this.descripcion = descipcion;
	}
}
