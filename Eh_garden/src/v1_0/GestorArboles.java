package v1_0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.cj.xdevapi.Statement;

public class GestorArboles {
	final static String BBDD = "eh-garden";
	final static String Usuario = "root";
	final static String contraseña = "";
	final static String host = "localhost";
	public static void main(String[] args) {
		run();
	}

	private static void run() {
		Scanner scan = new Scanner(System.in);
		int opcion = 0;
		final int INSERTAR = 0;
		final int ELIMINAR = 1;
		final int MODIFICAR = 2;
		final int VISUALIZAR_ARBOLES = 3;
		final int SALIR = 4;
		
		do {
			Menus();
			opcion = Integer.parseInt(scan.nextLine());
			
			switch (opcion) {
			case INSERTAR:
				
				break;
			case ELIMINAR:
				
				break;
			case MODIFICAR:
				
				break;
			case VISUALIZAR_ARBOLES:
				Visualizar();
				break;
			case SALIR:
				System.out.println("Saliendo....");
				break;
			default:
				break;
			}
		} while (opcion!=SALIR);
	}
	
	public void insert(Arbol arbol) {
    }

    public void update(Arbol arbol) {
        }
    

    public void delete(int idArbol) {

    }
    
    private static ArrayList<Arbol> Visualizar() {
    	ArrayList<Arbol> arboles = new ArrayList<Arbol>();
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("No se ha podido cargar la clase SQl java");
		}
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + BBDD,Usuario,contraseña);
			java.sql.Statement st = conexion.createStatement();
			
			ResultSet resultado = st.executeQuery("SELECT * FROM arboles ");
			
			while (resultado.next()) {
				Arbol arbol = new Arbol();
				arbol.setId(resultado.getInt("id"));
				arbol.setNombreCientefico(resultado.getString("nombre_comun"));
				arbol.setNombreComun(resultado.getString("nombre_cientifico"));
				arbol.setAltura(resultado.getInt("altura"));
				arbol.setHabitat(resultado.getString("habitat"));
				arbol.setOrigen(resultado.getString("altura"));
				arbol.setOrigen(resultado.getString("origen"));
				arboles.add(arbol);
			}
			
			for (Arbol arbol2 : arboles) {
				System.out.println(arbol2.toString());
			}
		} catch (SQLException e) {
			System.out.println("NO se ha podido establecer la conexion a la base de Datos");
			e.printStackTrace();
		}
    	return arboles;
    }
    
    public void selectArbol(int idArbol) {

    }
    
    public static void Menus() {
		final int INSERTAR = 0;
		final int ELIMINAR = 1;
		final int MODIFICAR = 2;
		final int VISUALIZAR_ARBOLES = 3;
		final int SALIR = 4;
		
        // Menú principal
        System.out.println("Menú:");
        System.out.println(INSERTAR +".Insertar árbol");
        System.out.println(ELIMINAR +".Eliminar árbol");
        System.out.println(MODIFICAR + ".Modificar información del árbol");
        System.out.println(VISUALIZAR_ARBOLES + ".Visualizar árboles");
        System.out.println(SALIR + ".Salir");
        System.out.print("Ingrese su opción: ");
    }
}
