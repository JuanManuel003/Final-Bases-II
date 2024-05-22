package Modelo;

public class Grupo {

	private int id;
	private String nombre;
	private int numGrupo;
	private int idGrupo;

	public Grupo(){}

	public Grupo(int id, String nombre, int numGrupo, int idGrupo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.numGrupo = numGrupo;
		this.idGrupo = idGrupo;
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
	public int getNumGrupo() {
		return numGrupo;
	}
	public void setNumGrupo(int numGrupo) {
		this.numGrupo = numGrupo;
	}
	public int getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
}
