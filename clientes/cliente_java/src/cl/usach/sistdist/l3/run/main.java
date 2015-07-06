package cl.usach.sistdist.l3.run;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import cl.usach.sistdist.l3.vo.Trabajador;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class main {

	public static void main(String[] args) {
		
		//Leer archivo de configuracion.
		Gson gson = new Gson();//parser json
		Path path = FileSystems.getDefault().getPath("", "server.json");
		try {
			List<String> file = Files.readAllLines(path , StandardCharsets.UTF_8);
			Type stringToMap = new TypeToken<Map<String, String>>(){}.getType();
			Map<String, String> map = gson.fromJson(file.get(0), stringToMap);
			
			if(map.get("url")!=null){
				boolean fin = false;
				String url = map.get("url");
				while(fin==false){
				System.out.println("CLIENTE RRHH - REST");
				System.out.println("<<MENU>>");
				System.out.println("1.- Listar todos los trabajadores");
				System.out.println("2.- Buscar un trabajador por RUT");
				System.out.println("3.- Salir");
				System.out.print("Ingrese una opcion: ");
				Scanner s = new Scanner(System.in);
				String opc = s.next();
				
				
					switch (opc) {
					case "1":
						System.out.println("-------------------------------------------------------");
						listarTrabajadores(url);
						break;
						
					case "2":
						System.out.print("Ingrese Rut del trabajador: ");
						s = new Scanner(System.in);
						String rut = s.next();
						System.out.println(rut);
						System.out.println("-------------------------------------------------------");
						buscartrabajador(url, rut);
						break;
						
					default:
						System.out.println("<<SALIR>>");
						System.exit(0);
						break;
					}	
					System.out.println("-------------------------------------------------------");
				}
				
			}else{
				System.out.println("Error en el archivo de servicios.");
			}
		} catch (IOException e) {
			System.out.println("No se encuentra el archivo servers.json");
		}

	}
	
	private static void listarTrabajadores(String url){
		try {
			URL url_trabajadores = new URL(url + "rrhh/trabajadores/");
			HttpURLConnection conn = (HttpURLConnection) url_trabajadores.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if(conn.getResponseCode()!=200){
				throw new RuntimeException("Error : HTTP error code : "+ conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		 	String output;
		 	String json="";
			while ((output = br.readLine()) != null) {
				json = output;
			} 
			conn.disconnect();
			
			Gson gson = new Gson();//parser json
			Trabajador[] trabajadores = gson.fromJson(json, Trabajador[].class);
			System.out.println("<<LISTADO TRABAJADORES>>");
			for (int i = 0; i < trabajadores.length; i++) {
				if(trabajadores[i].getError()==null){
					System.out.println("-------------------------------------------------------");
					printTrabajador(trabajadores[i]);
				}else{
					System.out.println(trabajadores[i].getMensaje());
				}
			}
			
			
		} catch (MalformedURLException e) {
			System.out.println("ERROR EN LA URL");
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERROR AL ABRIR LA CONEXION");
			//e.printStackTrace();
		}
	}
	
	private static void buscartrabajador(String url, String rut){
		try {
			//^(\d+)-([0-9]|k|K) expresion regular para validar rut (formato 11111111-1)
			
			
			URL url_buscar_rut = new URL(url + "rrhh/trabajadores/"+rut);
			HttpURLConnection conn = (HttpURLConnection) url_buscar_rut.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if(conn.getResponseCode()!=200){
				throw new RuntimeException("Error : HTTP error code : "+ conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		 	String output;
		 	String json="";
			while ((output = br.readLine()) != null) {
				json = output;
			}
			conn.disconnect();
			
			Gson gson = new Gson();//parser json
			Trabajador trabajador = gson.fromJson(json, Trabajador.class);
			if(trabajador.getError()==null){
				printTrabajador(trabajador);
			}else{
				System.out.println(trabajador.getMensaje());
			}
			
		} catch (MalformedURLException e) {
			System.out.println("ERROR EN LA URL");
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERROR AL ABRIR LA CONEXION");
			//e.printStackTrace();
		}
	}
	
	private static void printTrabajador(Trabajador trabajador){
		System.out.println("NOMBRE: "+trabajador.getNombre() + " " + trabajador.getApellido());
		System.out.println("RUT: "+ trabajador.getRut());
		System.out.println("CARGO: "  +trabajador.getCargo());
		System.out.println("DEPARTAMENTO: "  +trabajador.getDepartamento());
		System.out.println("EMAIL: " + trabajador.getEmail());
	}

}
