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

	$("#txtCognome").val('');
	$("#txtNome").val('');
	$("#txtCarica").val('');
	$("#txtDataNomina").val('');
	$("#txtDataCessazione").val('');
	$("#txtDataScadenza").val('');
	$("#txtDataAccettazioneCarica").val('');
	$("#txtEmolumento").val('');
	$("#txtDataNascita").val('');
	$("#txtLuogoNascita").val('');
	$("#txtCodiceFiscale").val('');
	$("#txtNote").val('');
	$("#tipoOrganoSociale").val('0');
	$("#idSocietaAffari").val('0');
}

function salvaOrganoSociale(){
	var form = document.getElementById("organoSocialeForm");
	var op = document.getElementById("op");
	op.value = "salvaOrganoSociale";
	form.submit(); 
}

function modificaOrganoSociale(){
	var form = document.getElementById("organoSocialeForm");
	var op = document.getElementById("op");
	op.value = "modificaOrganoSociale";
	form.submit(); 
}

function eliminaOrganoSociale(id) {
	console.log("elimino organoSociale con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		//initTabellaRicercaOrganoSociale();
		  $('#data-table-organosociale').bootstrapTable('refresh');

	};
	
	var fnCallBackError = function(data){
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};

	var url = WEBAPP_BASE_URL + "organoSociale/eliminaOrganoSociale.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, fnCallBackError);
}

function exportOrganoSociale(){
	console.log("Esporto organoSociali presenti a sistema");
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		//initTabellaRicercaOrganoSociale();

	};
	
	var fnCallBackError = function(data){
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};

	var url = WEBAPP_BASE_URL + "organoSociale/exportOrganoSociale.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, fnCallBackError);
	
	
}




