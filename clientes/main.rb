require 'rest-client'
require 'json'

def listarTrabajadores()
	#Consumo de URL GET
	resp = JSON.parse( RestClient.get "http://192.168.1.39:3001/rrhh/trabajadores" , {:accept =>:json} )
	#Recorrer todas los trabajdores
	resp.each do |json|
		puts "-------------------------------------------------------"
		if json["mensaje"] ==nil
			printTrabajador(json)
		else
			puts json["mensaje"]
		end

	end

end

def buscarTrabajador(rut)
	resp = JSON.parse( RestClient.get "http://192.168.1.39:3001/rrhh/trabajadores/"+rut, {:accept => :json} )
	#Validar que exista el trabajador
	if resp["error"] == nil
		puts "-------------------------------------------------------"
		#SI existe, lo imprimo
		printTrabajador(resp)
	else
		puts "-------------------------------------------------------"
		puts resp["mensaje"]
	end
end

def printTrabajador(trabajador)
	puts "NOMBRE: " + trabajador["nombre"]
	puts "RUT: " + trabajador["rut"]
	puts "CARGO: " + trabajador["cargo"]
	puts "DEPARTAMENTO: "   + trabajador["departamento"]
	puts "EMAIL: " + trabajador["email"]

end

def main()
	fin = false
	while fin ==false
		puts "CLIENTE RRHH - REST"
		puts "<<MENU>>"
		puts "1.- Listar todos los trabajadores"
		puts "2.- Buscar un trabajador por RUT"
		puts "3.- Salir"
		#leer opcion desde la consola
		op = gets.chomp

		case op
			when "1"
				listarTrabajadores()
			when "2"
				puts "Ingrese Rut del trabajador"
				rut = gets.chomp	
				buscarTrabajador(rut)
			else
				fin=true
		end
		puts "---------------------------------------------------"
	end
end

#Ejecutor del programa principal
main()