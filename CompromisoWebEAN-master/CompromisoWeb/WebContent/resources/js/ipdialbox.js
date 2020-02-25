function guardarNuevaGestion(codigoTipificacion) {	
	console.log("cod: " + codigoTipificacion);
	var tipificacion = codigoTipificacion;
	
	$.getJSON('http://localhost:80/apiagentbox?action=chur&cod='
			+ tipificacion +'&comm=FIN_LLAMADA', function(res) {
		res = JSON.stringify(res);
		var status = JSON.parse(res);
	});
}

function fun_include_dial(telefono, tipo) {
	var telNuevo;
	console.log("telNuevo " + telNuevo);

	if (tipo == "Fijo") {
		console.log("entro fijo");

		if (telefono.toString().includes("456")) {
			telNuevo = telefono.toString().replace("456", "9");
		} else {
			telNuevo = "92" + telefono;
		}

		console.log("telNuevo entro fijo : " + telNuevo);
	} else {
		console.log("no entro fijo");
		telNuevo = "9" + telefono;
		console.log("telNuevo no entro fijo : " + telNuevo);
	}

	$.getJSON('http://localhost:80/apiagentbox?action=dial&phone=' + telNuevo,
			function(res) {
				res = JSON.stringify(res);
				var status = JSON.parse(res);

			});
}

function fun_include_aux(letra) {

	$.getJSON('http://localhost:80/apiagentbox?action=' + letra, function(res) {
		res = JSON.stringify(res);
		var status = JSON.parse(res);
	});
}

function fun_include_hold(letra) {

	$.getJSON('http://localhost:80/apiagentbox?action=hold', function(res) {
		res = JSON.stringify(res);

	});
}

function fun_include_mute(letra) {

	$.getJSON('http://localhost:80/apiagentbox?action=mute', function(res) {
		res = JSON.stringify(res);
	});
}

function fun_include_haup(letra) {

	$.getJSON('http://localhost:80/apiagentbox?action=haup', function(res) {
		res = JSON.stringify(res);
	});
}