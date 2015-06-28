var express = require('express');
var request = require('request');
var router = express.Router();

router.get('/trabajadores/', function (req, res){
	res.setHeader("Access-Control-Allow-Origin", "*");

	console.log("Listar Trabajadores");
	var trabajadores = [];
	var flagfind=false; //bloquear peticion asincrona.
	
	
	req.db.usuarios.find({}).each(function(err, trabajador) {
		if(trabajador!=null){
			//Agregar los trabajadores obtenidos a un array
			trabajadores.push(trabajador);
		}else{
			//al no encontrar mas trabajadores retorna la respuesta.
			if(trabajadores.length==0){
				trabajadores.push({error:1, mensaje:'No existen trabajadores'});
			}
			res.json(trabajadores);
		}
	});
});

router.get('/trabajadores/:rut', function (req, res){
	res.setHeader("Access-Control-Allow-Origin", "*");
	
	console.log("Get Trabajador by Rut");
	var rut = req.params.rut;
	console.log(rut);	

	var trabajadores = [];
	var flagfind=false; //bloquear peticion asincrona.

	req.db.usuarios.findOne({rut:rut},function(err, result) {
		if (err){
	    	result = err;
	    	res.json(500, result);
	    }else{
	    	if(result==null){
				result = {error:1, mensaje:'No existe trabajador'};
				res.json(result);
			}else{
				console.log("Trabajador: "+result);
	    		res.json(result);
			}
	    }
	});
});

module.exports = router;
