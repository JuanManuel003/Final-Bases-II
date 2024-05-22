package Modelo;

public class Tema {

	private int id;
	private String descipcion;

	public Tema(){}

	public Tema(int id, String descipcion) {
		super();
		this.id = id;
		this.descipcion = descipcion;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescipcion() {
		return descipcion;
	}
	public void setDescipcion(String descipcion) {
		this.descipcion = descipcion;
	}
}
