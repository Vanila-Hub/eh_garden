package v1_0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	final static String BBDD = "eh-garden2.0";
	final static String Usuario = "root";
	final static String contraseña = "";
	final static String host = "localhost";
	final static int INSERTAR = 4;
	final static int ELIMINAR = 3;
	final static int MODIFICAR = 2;
	final static int VISUALIZAR_ARBOLES = 1;
	final static int SALIR = 0;
	static ArrayList<Habitad> habitads = new ArrayList<Habitad>();
	static ArrayList<Arbol> arboles = new ArrayList<Arbol>();
	SimpleDateFormat formato_date = new SimpleDateFormat("dd/MM/yyyy");
	public static void main(String[] args) {
		run();
	}

	private static void run() {
		Scanner scan = new Scanner(System.in);
		int opcion = 0;

		ArrayList<Arbol> arboles = visualizar();
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
				//update(arboles,scan);
				break;
			case VISUALIZAR_ARBOLES:
				arboles.clear();
				arboles = visualizar();
			for (Arbol arbol : arboles) {
					System.out.println(arbol);
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
	
	private static ArrayList<Arbol> visualizar() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("No se ha podido cargar la clase SQl java");
		}
		habitads.clear();
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + BBDD,Usuario,contraseña);
			java.sql.Statement st = conexion.createStatement();
			
			ResultSet resultado = st.executeQuery("SELECT * FROM arboles a inner join habitads h on a.habitat = h.id ");
			
			while (resultado.next()) {
				Arbol arbol = new Arbol();
				Habitad habitad = new Habitad();
				
				/*añadimos habitadas*/
				habitad.set_idHabitad(resultado.getInt("h.id"));
				habitad.setNombre(resultado.getString("h.nombre"));
				
				arbol.setId(resultado.getInt("a.id"));
				arbol.setNombreCientefico(resultado.getString("a.nombre_comun"));
				arbol.setNombreComun(resultado.getString("a.nombre_cientifico"));
				arbol.setAltura(resultado.getInt("a.altura"));
				arbol.setHabitat(resultado.getString("h.nombre"));
				arbol.set_idHabitad(resultado.getInt("h.id"));
				arbol.setOrigen(resultado.getString("a.origen"));
				arbol.setSingular(resultado.getBoolean("a.singular"));
				arbol.setFecha_encontrado(resultado.getString("a.fecha_encontrado"));
				
				arboles.add(arbol);
				habitads.add(habitad);
			}
		} catch (SQLException e) {
			System.out.println("NO se ha podido establecer la conexion a la base de Datos");
			e.printStackTrace();
		}
		return arboles;
	}

	public static void insert(Scanner scan) {
		String opcion = "";
		Arbol arbol = new Arbol();
		
		System.out.println("Ingrese el nombre comun del arbol: ");
		arbol.setNombreComun(scan.nextLine());
		
		System.out.println("Ingrese el nombre Cientifico del arbol: ");
		arbol.setNombreCientefico(scan.nextLine());
		
	    opcion = imprimirHabitads(scan);
	     
		for (int i = 0; i < habitads.size(); i++) {
			if (opcion.equalsIgnoreCase(habitads.get(i).getNombre())) {
				System.out.println(habitads.get(i).get_idHabitad());
				arbol.setIdHabitat(habitads.get(i).get_idHabitad());
			}
		}
		
		System.out.println("Ingrese la altura del arbol: ");
		arbol.setAltura(Integer.parseInt(scan.nextLine()));
		
		System.out.println("Ingrese el origen del arbol: ");
		arbol.setOrigen(scan.nextLine());
		
		System.out.println("Ingrese si el arbol es (1) singular o no (0): ");
		arbol.setSingular(Boolean.parseBoolean(scan.nextLine()));
		
		System.out.println("Ingrese la fecha en la que se encontro el arbol (yyyy/mm/dd): ");
		arbol.setFecha_encontrado(scan.nextLine());
		
		uploadBBDD(arbol);
    }

    private static String imprimirHabitads(Scanner scan) {
    	String opcion = "";
    	
    	System.out.println("Elija una de las siguientes opciones de Habitads");
    	
		for (Habitad habitad : habitads) {
			System.out.println(habitad);
		}
		opcion = scan.nextLine();
		return opcion;
	}

	private static void uploadBBDD(Arbol arbol) {
          try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + BBDD,Usuario,contraseña);
              String crear_arbol = "INSERT INTO arboles (nombre_comun, nombre_cientifico, habitat, altura, origen, singular, fecha_encontrado) VALUES (?, ?, ?, ?, ?,? ,?)";
     
              PreparedStatement prst = conexion.prepareStatement(crear_arbol);
              prst.setString(1, arbol.getNombreComun());
              prst.setString(2, arbol.getNombreCientefico());
              prst.setInt(3, arbol.getIdHabitat());
              prst.setInt(4, arbol.getAltura());
              prst.setString(5, arbol.getOrigen());
              prst.setBoolean(6, arbol.getSingular());
              prst.setString(7, arbol.getFecha_encontrado());
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
					//arboles.get(i).setHabitat_id(scan.nextLine());
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
				arboles.remove(i);
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
