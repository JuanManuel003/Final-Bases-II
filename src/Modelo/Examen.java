package Modelo;

public class Examen {

	private int id;
	private String nombre;
	private String descripcion;
	private int idTemaExamen;
	private int idCategoria;
	private int idAlumno;
	private int idConfiguracion;
	private int idGrupo;

	public Examen(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Examen(int id, String nombre, String descripcion, int idTemaExamen, int idCategoria, int idAlumno,
			int idConfiguracion, int idGrupo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.idTemaExamen = idTemaExamen;
		this.idCategoria = idCategoria;
		this.idAlumno = idAlumno;
		this.idConfiguracion = idConfiguracion;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIdTemaExamen() {
		return idTemaExamen;
	}
	public void setIdTemaExamen(int idTemaExamen) {
		this.idTemaExamen = idTemaExamen;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public int getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}
	public int getIdConfiguracion() {
		return idConfiguracion;
	}
	public void setIdConfiguracion(int idConfiguracion) {
		this.idConfiguracion = idConfiguracion;
	}
	public int getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}

}
