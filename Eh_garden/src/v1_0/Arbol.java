package v1_0;

public class Arbol {
	private int id;
	private  String nombreComun;
	private  String nombreCientefico;
	private  String habitat;
	private  int altura;
	private  String origen;
	
	public Arbol(int id,String nombreComun,String nombreCientefico,String habitat,int altura,String origen) {
		this.id = id;
		this.altura = altura;
		this.habitat = habitat;
		this.nombreCientefico = nombreCientefico;
		this.nombreComun = nombreComun;
	}

	public Arbol() {

	}
	
	/*getter y setter*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombreComun() {
		return nombreComun;
	}
	public void setNombreComun(String nombreComun) {
		this.nombreComun = nombreComun;
	}
	public String getNombreCientefico() {
		return nombreCientefico;
	}
	public void setNombreCientefico(String nombreCientefico) {
		this.nombreCientefico = nombreCientefico;
	}
	public String getHabitat() {
		return habitat;
	}
	public void setHabitat(String habitat) {
		this.habitat = habitat;
	}
	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	@Override
	public String toString() {
		return "Arbol [id=" + id + ", nombreComun=" + nombreComun + ", nombreCientefico=" + nombreCientefico
				+ ", habitat=" + habitat + ", altura=" + altura + ", origen=" + origen + "]";
	}
}
