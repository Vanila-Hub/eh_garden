package v1_0;

import java.text.SimpleDateFormat;

public class Habitad {
	private int _idHabitad;
	private String nombre;
	public Habitad() {

	}
	public int get_idHabitad() {
		return _idHabitad;
	}
	public void set_idHabitad(int _idHabitad) {
		this._idHabitad = _idHabitad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "Habitad -> " + nombre + "]";
	}
	
}
