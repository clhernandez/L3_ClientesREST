import json, sys, requests

print "Cliente REST - RRHH/Trabajadores"
if len(sys.argv) > 1:
	try:
		url_servicio = "http://192.168.1.35:3001/rrhh/trabajadores"
		
		print "Consultando url: {0}".format(url_servicio)

		r = requests.get(url_servicio)
		if r.status_code == 200:
			trabajadores = r.json();

			print "Cantidad de trabajadores: {0}".format(len(trabajadores))

			for trabajador in trabajadores:
				print "RUT: " + trabajador["rut"]
				url_servicio_rut = url_servicio+"/"+trabajador["rut"]

				print "Consultando url: {0}".format(url_servicio_rut)

				r2 = requests.get(url_servicio_rut);
				if r2.status_code==200:
					trabajador_rut = r2.json()
					print "nombre: {0}, apellido:{1}".format(trabajador_rut["nombre"], trabajador_rut["apellido"])

	except requests.exceptions.ConnectionError:
		print "Error al conectarse con el servicio"
	except KeyError:
		print "Error en la estructura del archivo {0}".format(sys.argv[1])
	except IOError:
		print "El archivo {0} no existe.".format(sys.argv[1])

else:

	print "Se espera recibir la ubicacion del archivo de configuracion de servicios."