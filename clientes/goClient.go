package main

import "fmt"
import "net/http"
import "io/ioutil"
import "os"
import "encoding/json"

type Server struct {
	URL string `json:"url"`
}

type Trabajador struct {
	Apellido string `json:"apellido"`
	Cargo string `json:"cargo"`
	Departamento string `json:"departamento"`
	Email string `json:"email"`
	FechaContratacion string `json:"fecha_contratacion"`
	Nombre string `json:"nombre"`
	Password string `json:"password"`
	Rut string `json:"rut"`
	Sueldo string `json:"sueldo"`
	Telefono string `json:"telefono"`
}

func main() {

	file, err := os.Open("server.json")
	if err != nil {
		fmt.Printf("%s", err)
	}else{
		data := make([]byte, 100)
		count, err := file.Read(data)
		if err != nil{
			fmt.Printf("%s", err)	
		}else{

			fmt.Printf("read %d bytes: %q\n", count, data[:count])
			json_url := data[:count]
			var m Server

			err := json.Unmarshal(json_url, &m)

			if err != nil{
				fmt.Printf("%s", err)	
			}

			url := m.URL
			// fmt.Println("%s\n", url)
			for{

				fmt.Println("Cliente REST - RRHH/Trabajadores")
				fmt.Println("<<MENU>>")
				fmt.Println("1.- Listar todos los trabajadores")
				fmt.Println("2.- Buscar un trabajador por RUT")
				fmt.Println("3.- Salir")
				fmt.Print("Ingrese Opcion: ")

				
				var in string
				fmt.Scanln(&in)

				if in=="1" {
					fmt.Println("-------------------------------------------------------")
					listarTrabajadores(url)
				}else{
					if in=="2"{
						var rut string
						fmt.Print("Ingrese RUT: ")
						fmt.Scanln(&rut)
						fmt.Println("-------------------------------------------------------")
						buscarTrabajador(url, rut)
					}else{
						fmt.Println("<<FIN>>")
						os.Exit(0)
					}
				}
				
				
			}
			
		}
	}
}

func buscarTrabajador(url string, rut string){
	url += "rrhh/trabajadores/"+rut;
	fmt.Println("Buscando Trabajador.")

    resp, err := http.Get(url)
    if err != nil {
		fmt.Printf("%s", err)
        os.Exit(1)
	}else{		
		defer resp.Body.Close()
		body, err := ioutil.ReadAll(resp.Body)
		if err != nil {
	        fmt.Printf("%s", err)
	        os.Exit(1)
	    }else{
		    var trabajador Trabajador

	   		err := json.Unmarshal(body, &trabajador)

	   		if err != nil{
	   			fmt.Printf("%s", err)
	   		}else{
	   			if(trabajador.Nombre != ""){
   				printTranajador(trabajador)
   				}else{
   					fmt.Println("No existe el trabajador")
   				}
	   			
   				fmt.Println("-------------------------------------------------------")
	   		}
	    }
	}
}

func listarTrabajadores(url string){
	url += "rrhh/trabajadores";
	fmt.Println("Consultando Listado Trabajadores.")

    resp, err := http.Get(url)
    if err != nil {
		fmt.Printf("%s", err)
        os.Exit(1)
	}else{		
		defer resp.Body.Close()
		body, err := ioutil.ReadAll(resp.Body)
		if err != nil {
	        fmt.Printf("%s", err)
	        os.Exit(1)
	    }else{
		    var trabajadores []Trabajador

	   		err := json.Unmarshal(body, &trabajadores)

	   		if err != nil{
	   			fmt.Printf("%s", err)
	   		}else{
	   			for i := 0; i < len(trabajadores); i++ {
	   				printTranajador(trabajadores[i])
	   				fmt.Println("-------------------------------------------------------")
	   			}
	   		}
	    }
	}
}

func printTranajador(trabajador Trabajador){
	fmt.Println("Nombre: " + trabajador.Nombre + " " + trabajador.Apellido)
	fmt.Println("RUT: " + trabajador.Rut)
	fmt.Println("Cargo: " + trabajador.Cargo)
	fmt.Println("Departamento: " + trabajador.Departamento)
	fmt.Println("Email: " + trabajador.Email)
}