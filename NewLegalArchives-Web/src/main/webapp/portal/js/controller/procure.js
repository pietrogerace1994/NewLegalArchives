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

	$("#txtDataConferimento").val('');
	$("#txtDataRevoca").val('');
	$("#txtNomeProcuratore").val('');
	$("#txtTipologia").val('');
	$("#txtNumeroRepertorio").val('');
	$('#fileTable tr:gt(0)').remove();
	$('#fileTable input').val('');
}

function salvaProcure(){
	var form = document.getElementById("procureForm");
	var op = document.getElementById("op");
	op.value = "salvaProcure";
	form.submit(); 
}

function modificaProcure(){
	var form = document.getElementById("procureForm");
	var op = document.getElementById("op");
	op.value = "modificaProcure";
	form.submit(); 
}

function eliminaProcure(id) {
	console.log("elimino procure con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		//initTabellaRicercaProcure();
		  $('#data-table-procure').bootstrapTable('refresh');

	};
	
	var fnCallBackError = function(data){
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};

	var url = WEBAPP_BASE_URL + "procure/eliminaProcure.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, fnCallBackError);
}

function aggiungiAllegatoGenerico(id){
	var file = $('#fileAllegatoGenerico')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idProcure', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "procure/uploadAllegatoGenerico.action";
	 
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
	
	var url = WEBAPP_BASE_URL + "procure/rimuoviAllegatoGenerico.action?uuid="+uuid;
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

function caricaGrigliaPermessiProcure(idProcure){
	console.log("caricaGrigliaPermessiProcure con id: " + idProcure);
	var tbody = document.getElementById("tBodyPermessiProcure");
	tbody.innerHTML = '';
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		var tbody = document.getElementById("tBodyPermessiProcure");
		tbody.innerHTML = data;
		
		waitingDialog.hide();
	};

	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
	};
	
	var url = WEBAPP_BASE_URL + "procure/caricaGrigliaPermessiProcure.action?id=" + idProcure;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr );
}

function estendiPermessiProcure(idProcure){
	console.log("estendiPermessiProcure con id: " + idProcure);
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
	
	var url = WEBAPP_BASE_URL + "procure/estendiPermessiProcure.action?idProcure=" + idProcure;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}







