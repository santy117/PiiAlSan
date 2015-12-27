package Proyecto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Scanner;

import  Proyecto.Avisos;

public class Persona {
	private String Nombre = new String();
	private String Apellidos = new String();
	private String DNI = new String();
	private GregorianCalendar FechaNacimiento = new GregorianCalendar();
	private String Perfil = new String();
	
	protected Persona(){
	}
	
	protected Persona(String Nombre, String Apellidos, String DNI, GregorianCalendar FechaNacimiento){
		
		this.Nombre = Nombre;
		this.Apellidos = Apellidos;
		this.DNI = DNI;
		this.FechaNacimiento = FechaNacimiento;
	}
	
	protected Persona(String Nombre, String Apellidos, String DNI, GregorianCalendar FechaNacimiento, String Perfil){
		
		this.Nombre = Nombre;
		this.Apellidos = Apellidos;
		this.DNI = DNI;
		this.FechaNacimiento = FechaNacimiento;
		this.Perfil = Perfil;
	}
	//En datos recibimos toda la linea del fichero de ejecucion. En perfil recibimos el el tipo (al/prof)
	public static void InsertaPersona(String datos, String perfil) throws IOException{  
																						
		String[] arrayDatos = datos.trim().split("\"");
		
		String[] lineaDatos = arrayDatos[0].trim().split("\\s+");
			
		//Comprobamos que el numero de parametros sea el correcto
		if(arrayDatos.length!=7 && arrayDatos.length!=5){
			Avisos.avisosFichero("Numero de parametros incorrecto");
			return;
		}
		
		if(Avisos.ComprobarDNI(lineaDatos[2]) == false){
			Avisos.avisosFichero("DNI incorrecto");
		}

		String tipo = perfil.trim();
		
		if(tipo.equals("alumno")){
		Alumno.InsertaAlumno(arrayDatos);
		   	
		}else {
		//Profesor.InsertaProfesor(lineaDatos);
		}
		
		/* En la especificacion no se dice que se tenga en cuenta campo perfil incorrecto
		   }else
			Avisos.avisosFichero("Campo perfil incorrecto: <" +lineaDatos[1] +">");
		*/
	}
	
	public String getDNI(){
		return DNI;
	}
	public String getNombre(){
		return Nombre;
	}
	public String getApellidos(){
		return Apellidos;
	}

	
	
	public static void cargaPersonasAMapa(String nombreArchivo) throws IOException{
		
		try {
			Scanner input = new Scanner(new FileInputStream(nombreArchivo));
			
			while(input.hasNextLine()){
				
				if(input.nextLine().trim().equalsIgnoreCase("alumno")){
					
					//Variables que tienen todas las personas
					String perfil = input.nextLine().trim();
					String dni = input.nextLine().trim();
					String nombre = input.nextLine().trim();
					String apellidos = input.nextLine().trim();
					
					String aux = input.nextLine().trim();
					GregorianCalendar FechaNacimiento = Util.PasarAGregorianCalendar(aux);
					
					//Ahora vamos con las variables particulares de alumno y profesor
					if(perfil.trim().equals("alumno")){
						String aux1 = input.nextLine().trim();
						GregorianCalendar fechaIng = Util.PasarAGregorianCalendar(aux1);
						
						String AsignaturasSuperadas = input.nextLine().trim();
						String DocenciaRecibida = input.nextLine().trim();
						
						Alumno cargaAlumno = new Alumno(nombre.trim(), apellidos.trim(),dni.trim(), FechaNacimiento, fechaIng, 
								AsignaturasSuperadas, DocenciaRecibida);
						//A�ade el nuevo alumno leido del fichero al mapa de alumnos
						Proyecto.mapAlumnos.put(dni, cargaAlumno);
						
						//Para saltar el asterisco
						if(input.hasNextLine()){
							input.nextLine();
						}
						
					}else if(perfil.trim().equals("profesor")){
						String Categoria = input.nextLine().trim();
						String Departamento = input.nextLine().trim();
						int HorasAsignables = Integer.parseInt(input.nextLine().trim() );
						String DocenciaImpartida = input.nextLine().trim();
						
						Profesor cargaProfesor = new Profesor(dni.trim(), nombre.trim(), apellidos.trim(), FechaNacimiento,
								Categoria, Departamento, HorasAsignables, DocenciaImpartida);
						//Carga profesor a mapa
						Proyecto.mapProfesores.put(dni, cargaProfesor);
						
						if(input.hasNextLine()){
							input.nextLine();
						}
					}
				}
				
			}//Cierra el while
					
			input.close();
				
		}catch (FileNotFoundException e){
			  Avisos.avisosFichero("Error fichero: " +nombreArchivo);
			  System.exit(1);
		}
	}
	
	
	
}

