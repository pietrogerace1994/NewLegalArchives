if (!String.prototype.startsWith) {
  String.prototype.startsWith = function(searchString, position) {
    position = position || 0;
    return this.indexOf(searchString, position) === position;
  };
}


if (!String.prototype.endsWith) {
	String.prototype.endsWith = function(suffix) {
	    return this.indexOf(suffix, this.length - suffix.length) !== -1;
	};
}


$("#btnClear").click(function(event) {
	puliscoCampiInsert();
});

function puliscoCampiInsert() {

	$("#txtCodiceSocieta").val('');
	$("#txtDenominazione").val('');
	$("#txtCapitaleSottoscritto").val('');
	$("#txtCapitaleSociale").val('');
	$("#txtDataCostituzione").val('');
	$("#txtDataUscita").val('');
	$("#txtDenominazioneBreve").val('');
	$("#txtSiglaFormaGiuridica").val('');
	$("#txtFormaGiuridica").val('');
	$("#txtIdNazione").val('');
	$("#txtSiglaStatoProvincia").val('');
	$("#txtSiglaProvincia").val('');
	$("#txtItaliaEstero").val('');
	$("#txtUeExstraue").val('');
	$("#txtSedeLegale").val('');
	$("#txtIndirizzo").val('');
	$("#txtCap").val('');
	$("#txtCodiceFiscale").val('');
	$("#txtPartitaIva").val('');
	$("#txtDataRea").val('');
	$("#txtNumeroRea").val('');
	$("#txtComuneRea").val('');
	$("#txtSiglaProvinciaRea").val('');
	$("#txtProvinciaRea").val('');
	$("#txtQuotazione").val('');
	$("#txtInLiquidazione").val('');
	$("#txtModelloDiGovernance").val('');
	$("#txtNComponentiCDA").val('');
	$("#txtNComponentiCollegioSindacale").val('');
	$("#txtNComponentiOdv").val('');
	$("#txtCodiceNazione").val('');
	$("#txtSocietaDiRevisione").val('');
	$("#txtDataIncarico").val('');
	$("#txtIdControllante").val('');
	$("#txtPercentualeControllante").val('');
	$("#txtPercentualeTerzi").val('');
	$("#txtDataCostituzione").val('');

}

function salvaAffariSocietari(){
	var form = document.getElementById("affariSocietariForm");
	var op = document.getElementById("op");
	op.value = "salvaAffariSocietari";
	form.submit(); 
}


function modificaAffariSocietari(){
	var form = document.getElementById("affariSocietariForm");
	var op = document.getElementById("op");
	op.value = "modificaAffariSocietari";
	form.submit(); 
}

function eliminaAffariSocietari(id) {
	console.log("elimino affariSocietari con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		//initTabellaRicercaAffariSocietari();
		  $('#data-table-repertoriostandard').bootstrapTable('refresh');

	};
	
	var fnCallBackError = function(data){
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};

	var url = WEBAPP_BASE_URL + "affariSocietari/eliminaAffariSocietari.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, fnCallBackError);
}

function aggiungiAllegatoGenerico(id){
	var file = $('#fileAllegatoGenerico')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idAffariSocietari', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "affariSocietari/uploadAllegatoGenerico.action";
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
	
	var url = WEBAPP_BASE_URL + "affariSocietari/rimuoviAllegatoGenerico.action?uuid="+uuid;
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

//SOCIETA

function addSocietaPro(fromCreation){
	var exists=0;
	
	var societaText="",societaId="";
	var percentuale="";
	
	societaId=$('#idControllante').val();
	societaText=$("#idControllante option:selected").text();
	percentuale=$('#txtPercentualeControllante').val();
	
	societaText=$.trim(societaText);
	societaId=$.trim(societaId);
	percentuale=$.trim(percentuale);
	
	if(societaId=="")
		exists=1;
	if(percentuale=="")
		exists=1;
	if(percentuale.endsWith('%')){
		exists=1;
	}
	
	$("input[name='idControllante']").each(function(e){
	if($(this).val()==societaId)
		exists=1;
	})
	
	$('#idControllante').val("");
	$('#txtPercentualeControllante').val("");
	
	if(!exists){
		
		var form = document.getElementById("affariSocietariForm");
		var op = document.getElementById("op");
		op.value = "aggiungiDemoninazionePercentualeSocio";
		
		var input1 = document.createElement("INPUT");
		input1.setAttribute("type", "hidden");
		input1.setAttribute("value", societaId);
		input1.setAttribute("name", "rSocietaAffariIdSocieta");
		
		var input2 = document.createElement("INPUT");
		input2.setAttribute("type", "hidden");
		input2.setAttribute("value", percentuale);
		input2.setAttribute("name", "rSocietaAffariPercentuale");
		
		var input3 = document.createElement("INPUT");
		input3.setAttribute("type", "hidden");
		input3.setAttribute("value", societaText);
		input3.setAttribute("name", "rSocietaAffariDescrizione");
		
		var input4 = document.createElement("INPUT");
		input4.setAttribute("type", "hidden");
		input4.setAttribute("value", fromCreation);
		input4.setAttribute("name", "fromCreation");
		
		form.appendChild(input1);
		form.appendChild(input2);
		form.appendChild(input3);
		form.appendChild(input4);
		
		waitingDialog.show('Loading...');
		
		form.submit();
	}
}

function removeSocietaPro(idDaRimuovere, formCreation){
	
	var form = document.getElementById("affariSocietariForm");
	var op = document.getElementById("op");
	op.value = "rimuoviDemoninazionePercentualeSocio";
	
	var input = document.createElement("INPUT");
	input.setAttribute("type", "hidden");
	input.setAttribute("value", "" + idDaRimuovere);
	input.setAttribute("name", "idDaRimuovere");
	
	var input2 = document.createElement("INPUT");
	input2.setAttribute("type", "hidden");
	input2.setAttribute("value", "" + formCreation);
	input2.setAttribute("name", "formCreation");
	
	form.appendChild(input);
	form.appendChild(input2);
	
	waitingDialog.show('Loading...');
	
	form.submit(); 
}
	





