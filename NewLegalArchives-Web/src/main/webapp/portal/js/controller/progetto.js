if (!String.prototype.startsWith) {
  String.prototype.startsWith = function(searchString, position) {
    position = position || 0;
    return this.indexOf(searchString, position) === position;
  };
}

$("#btnClear").click(function(event) {
	puliscoCampiInsert();
});

function puliscoCampiInsert() {
//	$('form[name="progettoView"] input[name=dataCreazione]').val('');
//	$('form[name="progettoView"] input[name=oggetto]').val('');
//	$('form[name="progettoView"] input[name=dataChiusura]').val('');
//	$('form[name="progettoView"] input[name=nome]').val('');
//	$('form[name="progettoView"] input[name=descrizione]').val('');
//	
	$("#txtDataApertura").val('');
	$("#txtDataChiusura").val('');
	$("#txtOggetto").val('');
	$("#txtNome").val('');
	$("#txtDescrizione").val('');
	$('#fileTable tr:gt(0)').remove();
	$('#fileTable input').val('');
}

function salvaProgetto(){
	var form = document.getElementById("progettoForm");
	var op = document.getElementById("op");
	op.value = "salvaProgetto";
	form.submit(); 
}

function modificaProgetto(){
	var form = document.getElementById("progettoForm");
	var op = document.getElementById("op");
	op.value = "modificaProgetto";
	form.submit(); 
}

function eliminaProgetto(id) {
	console.log("elimino progetto con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		//initTabellaRicercaProgetto();
		  $('#data-table-progetto').bootstrapTable('refresh');

	};
	
	var fnCallBackError = function(data){
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};

	var url = WEBAPP_BASE_URL + "progetto/eliminaProgetto.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, fnCallBackError);
}

function aggiungiAllegatoGenerico(id){
	var file = $('#fileAllegatoGenerico')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idProgetto', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "progetto/uploadAllegatoGenerico.action";
	 
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxAllegatiGenerici');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("allegatiGenericiGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("allegatiGenericiGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
} 

function rimuoviAllegatoGenerico(uuid){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	uuid = uuid.replace('{','');
	uuid = uuid.replace('}','');
	
	var url = WEBAPP_BASE_URL + "progetto/rimuoviAllegatoGenerico.action?uuid="+uuid;
	url=legalSecurity.verifyToken(url);
	var params =  "";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxAllegatiGenerici');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("allegatiGenericiGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("allegatiGenericiGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
} 

function estendiPermessiProgetto(idProgetto){
	console.log("estendiPermessiProgetto con id: " + idProgetto);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {   
		waitingDialog.hide();
		visualizzaMessaggio(data);
	};
	
	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
	};
	
	var params = "";
	var checksLettura = document.getElementsByName("chkPermessiLettura");
	var checksScrittura = document.getElementsByName("chkPermessiScrittura");

	var paramsLettura ="";
	var paramsScrittura = "";
	for( i = 0; i < checksLettura.length; i++ ){
		if( checksLettura[i].checked ){
			paramsLettura += "permessoLettura="+checksLettura[i].value+"&";
		}
	}
	
	for( i = 0; i < checksScrittura.length; i++ ){
		if( checksScrittura[i].checked ){
			paramsScrittura += "permessoScrittura="+checksScrittura[i].value+"&";
		}
	}
	
	params = paramsLettura+paramsScrittura; 
	
	var url = WEBAPP_BASE_URL + "progetto/estendiPermessiProgetto.action?idProgetto=" + idProgetto;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}

function caricaGrigliaPermessiProgetto(idProgetto){
	console.log("caricaGrigliaPermessiProgetto con id: " + idProgetto);
	var tbody = document.getElementById("tBodyPermessiProgetto");
	tbody.innerHTML = '';
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		var tbody = document.getElementById("tBodyPermessiProgetto");
		tbody.innerHTML = data;
		
		waitingDialog.hide();
	};

	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
	};
	
	var url = WEBAPP_BASE_URL + "progetto/caricaGrigliaPermessiProgetto.action?id=" + idProgetto;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr );
}


