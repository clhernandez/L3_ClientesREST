
<?php
	include("httpful.phar");
	// Create the handler
	$json_handler = new \Httpful\Handlers\JsonHandler(array('decode_as_array' => true));
	// Register it with Httpful
	\Httpful\Httpful::register('application/json', $json_handler);
	//leer archivo de configuracion
	$fin = true;

	$server = "server.json";
	$url ="";
	if(file_exists($server)){
		//leer archivo de configuracion del servidor
		$val = file_get_contents($server);
		$servidor = json_decode($val, true);
		$url = $servidor["url"] . "/rrhh/trabajadores";

		$fin=false;
	}else{
		echo "NO SE ENCUENTRA ARCHIVO 'server.json'.".PHP_EOL;
	}

	while($fin==false){
			echo "CLIENTE RRHH - REST", PHP_EOL ;
			echo "<<MENU>>", PHP_EOL ;
			echo "1.- Listar todos los trabajadores", PHP_EOL ;
			echo "2.- Buscar un trabajador por RUT", PHP_EOL ;
			echo "3.- Salir" , PHP_EOL ;
			$opc = readline("OPCION: ");

		switch ($opc) {
			case '1':
					//peticion get del servicio web con respuesta json
					$response = \Httpful\Request::get($url)
						->expects('application/json')
						->send();
					//decodificar JSON
					$array = json_decode($response);
					ECHO "<<LISTAR TRABAJADORES>>". PHP_EOL;
					if (count($array)>0) {
						//recorrer el arreglo de trabajadores y enviarlo a su impresion
						for ($i=0; $i <count($array) ; $i++) { 
							echo printTrabajador($array[$i]);
						}
					}else{
						echo "NO EXISTEN TRABAJADORES".PHP_EOL;
					}
					
				break;
			case '2':
				$rut = readline("RUT: ");
				//concatenacion de la url con el rut para cumplir con REST
				$url_sig = $url. "/".$rut;
				//peticion get del servicio web con respuesta json
				$response = \Httpful\Request::get($url_sig)
						->expects('application/json')
						->send();
					$array = json_decode($response);
					ECHO "<<BUSCAR TRABAJADOR RUT: ".$rut.">>". PHP_EOL;
					//si existe el trabajador se imprime; caso contrario, mensaje de error
					if ($array->error==null) {
						printTrabajador($array);
					}else{
						echo "NO EXISTE EL TRABAJADOR".PHP_EOL;
					}
					
				break;
			default:
				$fin = true;
				echo "<<FIN>>" , PHP_EOL;
				break;
		}
		echo "---------------------------------------------------". PHP_EOL;
	}

function printTrabajador($trabajador){
	echo "---------------------------------------------------". PHP_EOL;
	echo "NOMBRE: ", $trabajador->nombre, PHP_EOL;
	echo "RUT: ", $trabajador->rut, PHP_EOL;
	echo "CARGO: ", $trabajador->cargo, PHP_EOL;
	echo "DEPARTAMENTO: ", $trabajador->departamento, PHP_EOL;
	echo "EMAIL: ", $trabajador->email, PHP_EOL;
}
?>
