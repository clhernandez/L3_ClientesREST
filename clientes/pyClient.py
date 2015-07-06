import json, sys, requests
url_servicio = ""

def listarTrabajadores():
	try:
		print "Consultando url: {0}".format(url_servicio)

		r = requests.get(url_servicio)
		if r.status_code == 200:
			trabajadores = r.json();

			print "Cantidad de trabajadores: {0}".format(len(trabajadores))

			for trabajador in trabajadores:
				print "-------------------------------------------------------"
				printTrabajador(trabajador)

	except requests.exceptions.ConnectionError:
		print "Error al conectarse con el servicio"

def buscarTrabajador(rut):
	try:
		url_servicio_rut = url_servicio+"/"+rut
		print "Consultando url: {0}".format(url_servicio_rut)
		r2 = requests.get(url_servicio_rut);	
		if r2.status_code==200:
			trabajador_rut = r2.json()
			print "-------------------------------------------------------"
			if( "mensaje" not in trabajador_rut ):
				printTrabajador(trabajador_rut)
			else:
				print trabajador_rut["mensaje"]

	except requests.exceptions.ConnectionError:
		print "Error al conectarse con el servicio"
def printTrabajador(trabajador):

	print "NOMBRE: " + trabajador["nombre"] + " " + trabajador["apellido"]
	print "RUT: " + trabajador["rut"]
	print "CARGO: " + trabajador["cargo"]
	print "DEPARTAMENTO: "   + trabajador["departamento"]
	print "EMAIL: " + trabajador["email"]


def main():
	fin = False

	while(fin==False):
		print "Cliente REST - RRHH/Trabajadores"
		print "<<MENU>>"
		print "1.- Listar todos los trabajadores"
		print "2.- Buscar un trabajador por RUT"
		print "3.- Salir"
		op = raw_input("Opcion: ")
		if(op=="1"):
			listarTrabajadores()
		else:
			if(op=="2"):
				rut = raw_input("Rut Trabajador: ")
				buscarTrabajador(rut)
			else:
				fin=True
		print "-------------------------------------------------------"

try:
	f = open("server.json");
	json = json.load(f)
	url_servicio = json["url"]+"rrhh/trabajadores"
	main()
	print "<<FIN>>"
except IOError:
	print "El archivo {0} no existe.".format("server.json")
