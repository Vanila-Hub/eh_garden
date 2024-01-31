package v1_0;

public class Arbol {
	private int id;
	private  String nombreComun;
	private  String nombreCientefico;
	private  int habitat_id;
	private  int altura;
	private  String origen;
	private Boolean singular;
	
	public Arbol(int id,String nombreComun,String nombreCientefico,int habitat,int altura,String origen) {
		this.id = id;
		this.altura = altura;
		this.habitat_id = habitat;
		this.nombreCientefico = nombreCientefico;
		this.nombreComun = nombreComun;
	}

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

	public int getHabitat_id() {
		return habitat_id;
	}

	public void setHabitat_id(int habitat_id) {
		this.habitat_id = habitat_id;
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

	public Boolean getSingular() {
		return singular;
	}

	public void setSingular(Boolean singular) {
		this.singular = singular;
	}

	public Arbol() {

	}

	@Override
	public String toString() {
		return "Arbol [id=" + id + ", nombreComun=" + nombreComun + ", nombreCientefico=" + nombreCientefico
				+ ", habitat_id=" + habitat_id + ", altura=" + altura + ", origen=" + origen + ", singular=" + singular
				+ "]";
	}
	
	/*getter y setter*/
	
}
