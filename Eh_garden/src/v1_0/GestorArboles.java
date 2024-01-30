package v1_0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/*COLUMNAS DE LA BASE DE DATOS
 * nombre_comun
	nombre_cientifico
	altura
	habitat
	origen
 * 
*/
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
		ArrayList<Arbol> arboles = cargarArbolesBBDD();
		do {
			Menus();
			opcion = Integer.parseInt(scan.nextLine());
			switch (opcion) {
			case INSERTAR:
				insert(scan);
				break;
			case ELIMINAR:
				delete(arboles,scan);
				break;
			case MODIFICAR:
				update(arboles,scan);
				break;
			case VISUALIZAR_ARBOLES:
				for (Arbol arbol : arboles) {
					System.out.println(arbol.toString() + "\n");
				}
				break;
			case SALIR:
				System.out.println("Saliendo....");
				break;
			default:
				break;
			}
		} while (opcion!=SALIR);
	}
	
	public static void insert(Scanner scan) {
		int opcion = 0;
		Arbol arbol = new Arbol();
		
		System.out.println("Ingrese el nombre comun del arbol: ");
		arbol.setNombreComun(scan.nextLine());
		System.out.println("Ingrese el nombre Cientifico del arbol: ");
		arbol.setNombreCientefico(scan.nextLine());
		System.out.println("Ingrese el Habitat del arbol: ");
		arbol.setHabitat(scan.nextLine());
		System.out.println("Ingrese la altura del arbol: ");
		arbol.setAltura(Integer.parseInt(scan.nextLine()));
		System.out.println("Ingrese el origen del arbol: ");
		arbol.setOrigen(scan.nextLine());
		uploadBBDD(arbol);
    }

    private static void uploadBBDD(Arbol arbol) {
          try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + BBDD,Usuario,contraseña);
              String crear_arbol = "INSERT INTO arboles (nombre_comun, nombre_cientifico,habitat,altura,origen) VALUES (?, ?, ?, ?, ?)";
              PreparedStatement prst = conexion.prepareStatement(crear_arbol);
              prst.setString(1, arbol.getNombreComun());
              prst.setString(2, arbol.getNombreCientefico());
              prst.setString(3, arbol.getHabitat());
              prst.setInt(4, arbol.getAltura());
              prst.setString(5, arbol.getOrigen());
              prst.executeUpdate();
              
              prst.close();
              conexion.close();
              System.out.println("Arbol creado!");
          } catch (ClassNotFoundException | SQLException e) {
              e.printStackTrace();
              System.out.println("No se ha podido cargar la Base de datos");
          }
      }

	public static void update(ArrayList<Arbol> arboles,Scanner scan) {
	    	int id = 0;
	    	System.out.println("Introduzca el id del arbol a modificar: ");
	    	id = Integer.parseInt(scan.nextLine());
	    	for (int i = 0; i < arboles.size(); i++) {
				if(arboles.get(i).getId() == id){
					System.out.println("Ingrese el nombre comun del arbol: ");
					arboles.get(i).setNombreComun(scan.nextLine());
					System.out.println("Ingrese el nombre Cientifico del arbol: ");
					arboles.get(i).setNombreCientefico(scan.nextLine());
					System.out.println("Ingrese el Habitat del arbol: ");
					arboles.get(i).setHabitat(scan.nextLine());
					System.out.println("Ingrese la altura del arbol: ");
					arboles.get(i).setAltura(Integer.parseInt(scan.nextLine()));
					System.out.println("Ingrese el origen del arbol: ");
					arboles.get(i).setOrigen(scan.nextLine());
					updateArbolBBDD(i,arboles);
				}
			}
        }
    

    private static void updateArbolBBDD(int id, ArrayList<Arbol> arboles) {
    	String query = "UPDATE arboles SET nombre_comun = ?, nombre_cientifico = ?, habitat = ?, altura = ?, origen = ? WHERE arboles.id = ?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("No se ha podido cargar la clase SQl java");
		}
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + BBDD,Usuario,contraseña);
			
			PreparedStatement prst = conexion.prepareStatement(query);
            prst.setString(1, arboles.get(id).getNombreComun());
            prst.setString(2, arboles.get(id).getNombreCientefico());
            prst.setString(3, arboles.get(id).getHabitat());
            prst.setInt(4, arboles.get(id).getAltura());
            prst.setString(5, arboles.get(id).getOrigen());
            prst.setInt(6, arboles.get(id).getId());
            prst.executeUpdate();
			System.out.println("Arbol actualizado");
	
		} catch (SQLException e) {
			System.out.println("NO se ha podido establecer la conexion a la base de Datos");
			e.printStackTrace();
		}
	}

	public static void delete(ArrayList<Arbol> arboles, Scanner scan) {
    	int id = 0;
    	
    	System.out.println("Introduzca el id del arbol a borrar: ");
    	id = Integer.parseInt(scan.nextLine());
    	for (int i = 0; i < arboles.size(); i++) {
			if(arboles.get(i).getId() == id){
				removeIDBBDD(id,arboles);
				arboles.remove(id);
			}
		}
    }
    
    private static void removeIDBBDD(int id, ArrayList<Arbol> arboles) {
    	String consulta = "DELETE FROM arboles WHERE ID = " + id;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("No se ha podido cargar la clase SQl java");
		}
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + BBDD,Usuario,contraseña);
			java.sql.Statement st = conexion.createStatement();
			boolean resultado = st.execute(consulta);
			
			if (resultado==false) {
				System.out.println("1 arbol se ha visto afectada _ID: " + id);
			}else {
				System.out.println("Error al inentar eliminar al alumno con _id " + id);
			}
		} catch (SQLException e) {
			System.out.println("NO se ha podido establecer la conexion a la base de Datos");
			e.printStackTrace();
		}
	}

	private static ArrayList<Arbol> cargarArbolesBBDD() {
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
				arbol.setOrigen(resultado.getString("origen"));
				arboles.add(arbol);
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
        System.out.println("Ingrese su opción: ");
    }
}
