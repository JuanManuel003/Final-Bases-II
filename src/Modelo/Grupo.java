package Modelo;

public class Grupo {

	private int id;
	private String nombre;
	private int numGrupo;
	private int idCurso;

	public Grupo(){}

	public Grupo(int id, String nombre, int numGrupo, int idCurso) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.numGrupo = numGrupo;
		this.idCurso = idCurso;
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
	public int getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}
}
