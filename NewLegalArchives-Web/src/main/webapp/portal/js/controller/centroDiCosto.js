function salvaCentroDiCosto(){
	console.log('salva Centro Di Costo');
	var form = document.getElementById("centroDiCostoView"); 
	var op = document.getElementById("op");
	op.value="salvaCentroDiCosto";
	form.submit(); 
}

function caricaDescrizioniCdc(id) {
	console.log('caricaDescrizioniCdc: '+id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		var cdc = document.getElementById("cdc");  
		cdc.value = checkUndefined(data, "cdc")? "": data.getElementsByTagName("cdc")[0].firstChild.data;
		var settoreGiuridico = document.getElementById("settoreGiuridico");  
		settoreGiuridico.value = checkUndefined(data, "settoreGiuridico")? "": data.getElementsByTagName("settoreGiuridico")[0].firstChild.data;
		var tipologiaFascicolo = document.getElementById("tipologiaFascicolo");  
		tipologiaFascicolo.value = checkUndefined(data, "tipologiaFascicolo")? "": data.getElementsByTagName("tipologiaFascicolo")[0].firstChild.data;
		var societa = document.getElementById("societa");  
		societa.value = checkUndefined(data, "societa")? "": data.getElementsByTagName("societa")[0].firstChild.data;
	};
	var url = WEBAPP_BASE_URL + "centroDiCosto/caricaDescrizioniCdc.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function checkUndefined(data, value){
	if(data.getElementsByTagName(value)[0] !== undefined){
		return false;
	} else {
		return true;
	}
}