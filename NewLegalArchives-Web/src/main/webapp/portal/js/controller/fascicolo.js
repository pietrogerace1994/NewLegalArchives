$(document).ready(function() {
	checkPosizioneSocieta();
	$.getScript('/NewLegalArchives/portal/js/controller/ricercaProgetto.js', function() {
		console.log('/NewLegalArchives/portal/js/controller/ricercaProgetto.js: loaded');
	});
	
	$("#panelGestioneAssociaFascicoloAProgetto").on( "click", function(event) {
		if (event.target.id==="panelGestioneAssociaFascicoloAProgetto"){
			$('#popupmodalRicercaProgetto').modal('hide');
		}
	});
});



function selezionaTipologiaFascicolo(codice) {
	console.log('tipologia selezionata: ' + codice);
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "selezionaTipologiaFascicolo";
	waitingDialog.show('Loading...');
	form.submit();
}

function selezionaTipologiaFascicoloRicerca(codice){
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "fascicolo/selezionaTipologiaFascicoloRicerca.action?tipologiaFascicoloCode="+codice;
	url=legalSecurity.verifyToken(url);
	var callBackFn = function(data){
		var comboSettoreGiuridicoRic = document.getElementById("settoreGiuridicoCodeModal"); 
		comboSettoreGiuridicoRic.options.length = 1; 
		
		for( i = 0; i < data.length; i++ ){
			var option = document.createElement("OPTION");
			option.setAttribute("value",data[i].codGruppoLingua);
			var optionText = document.createTextNode(data[i].nome);
			option.appendChild(optionText);
			comboSettoreGiuridicoRic.appendChild(option);
		}
	}
	
	ajaxUtil.ajax(url, null, "get", null, callBackFn, null, null);
	
}

function selezionaTipologiaFascicoloRicercaIncarico(codice){
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "fascicolo/selezionaTipologiaFascicoloRicerca.action?tipologiaFascicoloCode="+codice;
	url=legalSecurity.verifyToken(url);
	var callBackFn = function(data){
		var comboSettoreGiuridicoRic = document.getElementById("settoreGiuridicoCodeModalIncarico"); 
		comboSettoreGiuridicoRic.options.length = 1; 
		
		for( i = 0; i < data.length; i++ ){
			var option = document.createElement("OPTION");
			option.setAttribute("value",data[i].codGruppoLingua);
			var optionText = document.createTextNode(data[i].nome);
			option.appendChild(optionText);
			comboSettoreGiuridicoRic.appendChild(option);
		}
	}
	
	ajaxUtil.ajax(url, null, "get", null, callBackFn, null, null);
	
}

function selezionaSettoreGiuridico(codice) {
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "selezionaSettoreGiuridico";
	waitingDialog.show('Loading...');
	form.submit();
}

function aggiungiRicorso(){
	var foro = document.getElementById("txtForo") ? document.getElementById("txtForo").value :'';
	var numeroRegistroCausa = document.getElementById("numeroRegistroCausa")? document.getElementById("numeroRegistroCausa").value:'';
	var note = document.getElementById("note")? document.getElementById("note").value:'';
	var organoGiudicanteCode = document.getElementById("comboOrganoGiudicante")?document.getElementById("comboOrganoGiudicante").value:'';
	var ricorsoCode = document.getElementById("comboRicorso")? document.getElementById("comboRicorso").value:'';
	if( ricorsoCode == '' ){
		visualizzaMessaggio({stato:"WARN", messaggio:"Il campo ricorso Ã¨ obbligatorio"});
		return;
	}
	
	var form = document.getElementById("fascicoloView");
	var input1 = document.createElement("INPUT");
	input1.setAttribute("type", "hidden");
	input1.setAttribute("value", foro);
	input1.setAttribute("name", "foro");
	
	var input2 = document.createElement("INPUT");
	input2.setAttribute("type", "hidden");
	input2.setAttribute("value", numeroRegistroCausa);
	input2.setAttribute("name", "numeroRegistroCausa");
	
	var input3 = document.createElement("INPUT");
	input3.setAttribute("type", "hidden");
	input3.setAttribute("value", note);
	input3.setAttribute("name", "note");
	
	var input4 = document.createElement("INPUT");
	input4.setAttribute("type", "hidden");
	input4.setAttribute("value", organoGiudicanteCode);
	input4.setAttribute("name", "organoGiudicanteCode");
	
	var input5 = document.createElement("INPUT");
	input5.setAttribute("type", "hidden");
	input5.setAttribute("value", ricorsoCode);
	input5.setAttribute("name", "ricorsoCode");
	

	form.appendChild(input1);
	form.appendChild(input2);
	form.appendChild(input3);
	form.appendChild(input4);
	form.appendChild(input5);
	var op = document.getElementById("op");
	op.value = "aggiungiRicorso";
 
	waitingDialog.show('Loading...');
	
	form.submit();
	
}

function rimuoviGiudizioAggiunto (indice){
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "rimuoviGiudizioAggiunto";
	var input = document.createElement("INPUT");
	input.setAttribute("type", "hidden");
	input.setAttribute("value", "" + indice);
	input.setAttribute("name", "indexGiudizioAggiunto");
	form.appendChild(input);
	form.submit(); 
}
 
function rimuoviRicorsoAggiunto (indice){
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "rimuoviRicorsoAggiunto";
	var input = document.createElement("INPUT");
	input.setAttribute("type", "hidden");
	input.setAttribute("value", "" + indice);
	input.setAttribute("name", "indexRicorsoAggiunto");
	form.appendChild(input);
	form.submit(); 
}

function editaGiudizioAggiunto(indice){
	var container = document.getElementById("containerFormGiudizio");
	console.log("editaGiudizioAggiunto: " + indice);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		container.innerHTML=data;
		waitingDialog.hide(); 
	};
	
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
	};

	

	var params = "indexGiudizioAggiunto="+indice+"&CSRFToken="+legalSecurity.getToken(); 
	var url = WEBAPP_BASE_URL + "fascicolo/selezionaGiudizio.action";
	
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}

function editaRicorsoAggiunto(indice){
	var container = document.getElementById("containerFormRicorso");
	console.log("editaRicorsoAggiunto: " + indice);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		container.innerHTML=data;
		waitingDialog.hide(); 
	};
	
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
	};

	

	var params = "indexRicorsoAggiunto="+indice+"&CSRFToken="+legalSecurity.getToken(); 
	var url = WEBAPP_BASE_URL + "fascicolo/selezionaRicorso.action";
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}


function aggiungiGiudizio(){
	var foro = document.getElementById("txtForo") ? document.getElementById("txtForo").value :'';
	var numeroRegistroCausa = document.getElementById("numeroRegistroCausa")? document.getElementById("numeroRegistroCausa").value:'';
	var note = document.getElementById("note")? document.getElementById("note").value:'';
	var organoGiudicanteCode = document.getElementById("comboOrganoGiudicante")?document.getElementById("comboOrganoGiudicante").value:'';
	var giudizioCode = document.getElementById("comboGiudizio")?document.getElementById("comboGiudizio").value:'';
	if( giudizioCode == '' ){
		visualizzaMessaggio({stato:"WARN", messaggio:"Il campo giudizio Ã¨ obbligatorio"});
		return;
	}
	var form = document.getElementById("fascicoloView");
 
	
	
	var op = document.getElementById("op");
	op.value = "aggiungiGiudizio";

	 
	waitingDialog.show('Loading...');
	
	form.submit();
	
}

function selezionaRicorso(codice) {
	var container = document.getElementById("containerFormRicorso");
	console.log("selezionaRicorso: " + codice);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		container.innerHTML=data;
		waitingDialog.hide(); 
	};

	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
	};

	var foro = document.getElementById("txtForo") ? document.getElementById("txtForo").value :'';
	var numeroRegistroCausa = document.getElementById("numeroRegistroCausa")? document.getElementById("numeroRegistroCausa").value:'';
	var note = document.getElementById("note")? document.getElementById("note").value:'';
	var organoGiudicanteCode = document.getElementById("comboOrganoGiudicante")?document.getElementById("comboOrganoGiudicante").value:'';
	var ricorsoCode = document.getElementById("comboRicorso")? document.getElementById("comboRicorso").value:'';

	var params = "foro="+encodeURIComponent(foro)+"&"+
	"numeroRegistroCausa="+encodeURIComponent(numeroRegistroCausa)+"&"+
	"note="+encodeURIComponent(note)+"&"+
	"organoGiudicanteCode="+organoGiudicanteCode+"&"+
	"ricorsoCode="+ricorsoCode+"&CSRFToken="+legalSecurity.getToken();
	
	var url = WEBAPP_BASE_URL + "fascicolo/selezionaRicorso.action";
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
	
}

function selezionaGiudizio(codice) {
	var container = document.getElementById("containerFormGiudizio");
	console.log("selezionaGiudizio: " + codice);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		container.innerHTML=data;
		waitingDialog.hide(); 
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
	};

	var foro = document.getElementById("txtForo") ? document.getElementById("txtForo").value :'';
	var numeroRegistroCausa = document.getElementById("numeroRegistroCausa")? document.getElementById("numeroRegistroCausa").value:'';
	var note = document.getElementById("note")? document.getElementById("note").value:'';
	var organoGiudicanteCode = document.getElementById("comboOrganoGiudicante")?document.getElementById("comboOrganoGiudicante").value:'';
	var giudizioCode = document.getElementById("comboGiudizio")?document.getElementById("comboGiudizio").value:'';


	var params = "foro="+encodeURIComponent(foro)+"&"+
	"numeroRegistroCausa="+encodeURIComponent(numeroRegistroCausa)+"&"+
	"note="+encodeURIComponent(note)+"&"+
	"organoGiudicanteCode="+organoGiudicanteCode+"&"+
	"giudizioCode="+giudizioCode+"&CSRFToken="+legalSecurity.getToken();
	
	var url = WEBAPP_BASE_URL + "fascicolo/selezionaGiudizio.action";
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}

function salvaFascicolo() {
	console.log('salva fascicolo');
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "salvaFascicolo";
	waitingDialog.show('Loading...');
	
	form.submit();
}

function aggiungiTerzoChiamatoCausa() {
	var terzoChiamatoCausa = document.getElementById("txtTerzoChiamatoInCausa").value;
	if (terzoChiamatoCausa != null && terzoChiamatoCausa.length != '') {
		var form = document.getElementById("fascicoloView");
		var op = document.getElementById("op");
		op.value = "aggiungiTerzoChiamatoCausa";
		form.submit();
	}
}

function rimuoviTerzoChiamatoCausa(indiceArray) {
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "rimuoviTerzoChiamatoCausa";
	var input = document.createElement("INPUT");
	input.setAttribute("type", "hidden");
	input.setAttribute("value", "" + indiceArray);
	input.setAttribute("name", "terzoChiamatoCausaIndex");
	form.appendChild(input);
	form.submit();
}

function aggiungiControparte() {
	var controparte = document.getElementById("txtControparte").value;
	if (controparte != null && controparte.length != '') {
		var form = document.getElementById("fascicoloView");
		var op = document.getElementById("op");
		op.value = "aggiungiControparte";
		form.submit();
	}
}

function rimuoviControparte(indiceArray) {
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "rimuoviControparte";
	var input = document.createElement("INPUT");
	input.setAttribute("type", "hidden");
	input.setAttribute("value", "" + indiceArray);
	input.setAttribute("name", "controparteIndex");
	form.appendChild(input);
	form.submit();
}

function aggiungiSoggettoIndagato() {
	var controparte = document.getElementById("txtSoggettoIndagato").value;
	if (controparte != null && controparte.length != '') {
		var form = document.getElementById("fascicoloView");
		var op = document.getElementById("op");
		op.value = "aggiungiSoggettoIndagato";
		form.submit();
	}
}

function rimuoviSoggettoIndagato(indiceArray) {
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "rimuoviSoggettoIndagato";
	var input = document.createElement("INPUT");
	input.setAttribute("type", "text");
	input.setAttribute("value", "" + indiceArray);
	input.setAttribute("name", "soggettoIndagatoIndex");
	form.appendChild(input);
	form.submit();
}

function aggiungiPersonaOffesa() {
	var persona = document.getElementById("txtPersonaOffesa").value;
	if (persona != null && persona.length != '') {
		var form = document.getElementById("fascicoloView");
		var op = document.getElementById("op");
		op.value = "aggiungiPersonaOffesa";
		form.submit();
	}
}

function rimuoviPersonaOffesa(indiceArray) {
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "rimuoviPersonaOffesa";
	var input = document.createElement("INPUT");
	input.setAttribute("type", "hidden");
	input.setAttribute("value", "" + indiceArray);
	input.setAttribute("name", "personaOffesaIndex");
	form.appendChild(input);
	form.submit();
}

function aggiungiParteCivile() {
	var persona = document.getElementById("txtParteCivile").value;
	if (persona != null && persona.length != '') {
		var form = document.getElementById("fascicoloView");
		var op = document.getElementById("op");
		op.value = "aggiungiParteCivile";
		form.submit();
	}
}

function rimuoviParteCivile(indiceArray) {
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "rimuoviParteCivile";
	var input = document.createElement("INPUT");
	input.setAttribute("type", "text");
	input.setAttribute("value", "" + indiceArray);
	input.setAttribute("name", "parteCivileIndex");
	form.appendChild(input);
	form.submit();
}


function rimuoviFascicoloCorrelato(indiceArray) {
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "rimuoviFascicoliCorrelati";
	var input = document.createElement("INPUT");
	input.setAttribute("type", "hidden");
	input.setAttribute("value", "" + indiceArray);
	input.setAttribute("name", "fascicoliCorrelatiIndex");
	form.appendChild(input);
	waitingDialog.show('Loading...');
	form.submit();
}


function rimuoviFascicoloPadre() {
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "rimuoviFascicoloPadre";
	
	waitingDialog.show('Loading...');
	form.submit();
}


var checkedRows = [];

function aggiungiFascicoloPadre(){
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "aggiungiFascicoloPadre";
	var fascicoloPadre = checkedRows;
	if( fascicoloPadre ){  
		var input = document.createElement("INPUT");
		input.setAttribute("type", "hidden");
		input.setAttribute("name", "fascicoloPadreSelezionatoId");
		input.setAttribute("value", fascicoloPadre[0].id); 
		form.appendChild(input);  
	} 
	waitingDialog.show('Loading...');
	form.submit();
}

function aggiungiFascicoliCorrelati(){
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "aggiungiFascicoliCorrelati";
	var fascicoliCorrelati = checkedRows;
	if( fascicoliCorrelati ){ 
		for( i = 0; i < fascicoliCorrelati.length; i++ ){
		
			var input = document.createElement("INPUT");
			input.setAttribute("type", "hidden");
			input.setAttribute("name", "fascicoliCorrelati");
			input.setAttribute("value", fascicoliCorrelati[i].id); 
			form.appendChild(input);
		
		}
	} 
	waitingDialog.show('Loading...');
	form.submit();
}

function aggiungiResponsabileCivile() {
	var persona = document.getElementById("txtResponsabileCivile").value;
	if (persona != null && persona.length != '') {
		var form = document.getElementById("fascicoloView");
		var op = document.getElementById("op");
		op.value = "aggiungiResponsabileCivile";
		form.submit();
	}
}

function rimuoviResponsabileCivile(indiceArray) {
	var form = document.getElementById("fascicoloView");
	var op = document.getElementById("op");
	op.value = "rimuoviResponsabileCivile";
	var input = document.createElement("INPUT");
	input.setAttribute("type", "text");
	input.setAttribute("value", "" + indiceArray);
	input.setAttribute("name", "responsabileCivileIndex");
	form.appendChild(input);
	form.submit();
}

function selezionaValoreCausa() {
	var selectValoreCause = document.getElementById("valoreCausa");
	if (selectValoreCause.value != '') {
		var option = null;
		var options = selectValoreCause.childNodes;
		for (i = 0; i < options.length; i++) {
			if (options[i].value && options[i].value == selectValoreCause.value) {
				option = options[i];
				break;
			}
		}

		var codice = option.value;
		var txtValore = document.getElementById("txtValore");
		if (codice == 'VC_2') {
			txtValore.value = "";
			txtValore.readOnly = true;
		} else {
			txtValore.readOnly = false;
		}
	}

}

function autocompleteNomeControparte(idCampo) {
	$("#" + idCampo).autocomplete({
		source : "autoCompleteControparte.action",
		minLength : 1,
		disableIcons : true,
		select : function(event, ui) {

			$("#" + idCampo).text(ui.item.value);
		}
	});
}

function autocompleteTerzoChiamatoInCausa(idCampo) {
	$("#" + idCampo).autocomplete({
		source : "autocompleteTerzoChiamatoInCausa.action",
		minLength : 1,
		disableIcons : true,
		select : function(event, ui) {

			$("#" + idCampo).text(ui.item.value);
		}
	});
}

function autocompleteForo(idCampo) {
	$("#" + idCampo).autocomplete({
		source : "autocompleteForo.action",
		minLength : 1,
		disableIcons : true,
		select : function(event, ui) {

			$("#" + idCampo).text(ui.item.value);
		}
	});
}

function autocompleteAutoritaEmanante(idCampo) {
	$("#" + idCampo).autocomplete({
		source : "autocompleteAutoritaEmanante.action",
		minLength : 1,
		disableIcons : true,
		select : function(event, ui) {

			$("#" + idCampo).text(ui.item.value);
		}
	});
}

function autocompleteControinteressato(idCampo) {
	$("#" + idCampo).autocomplete({
		source : "autocompleteControinteressato.action",
		minLength : 1,
		disableIcons : true,
		select : function(event, ui) {

			$("#" + idCampo).text(ui.item.value);
		}
	});
}

function autocompleteAutoritaGiudiziaria(idCampo) {
	$("#" + idCampo).autocomplete({
		source : "autocompleteAutoritaGiudiziaria.action",
		minLength : 1,
		disableIcons : true,
		select : function(event, ui) {

			$("#" + idCampo).text(ui.item.value);
		}
	});
}

function autocompleteSoggettoIndagato(idCampo) {
	$("#" + idCampo).autocomplete({
		source : "autocompleteSoggettoIndagato.action",
		minLength : 1,
		disableIcons : true,
		select : function(event, ui) {

			$("#" + idCampo).text(ui.item.value);
		}
	});
}

function autocompletePersonaOffesa(idCampo) {
	$("#" + idCampo).autocomplete({
		source : "autocompletePersonaOffesa.action",
		minLength : 1,
		disableIcons : true,
		select : function(event, ui) {

			$("#" + idCampo).text(ui.item.value);
		}
	});
}

function autocompleteParteCivile(idCampo) {
	$("#" + idCampo).autocomplete({
		source : "autocompleteParteCivile.action",
		minLength : 1,
		disableIcons : true,
		select : function(event, ui) {

			$("#" + idCampo).text(ui.item.value);
		}
	});
}

function autocompleteResponsabileCivile(idCampo) {
	$("#" + idCampo).autocomplete({
		source : "autocompleteResponsabileCivile.action",
		minLength : 1,
		disableIcons : true,
		select : function(event, ui) {

			$("#" + idCampo).text(ui.item.value);
		}
	});
}

function completaFascicolo(id){
	console.log("completa fascicolo con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaFascicoli();
		$(".modal-backdrop").remove();
	};

	var url = WEBAPP_BASE_URL + "fascicolo/completaFascicolo.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function riportaInCompletatoFascicolo(id){
	console.log("riporto in completato fascicolo con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaFascicoli();
		$(".modal-backdrop").remove();
	};

	var url = WEBAPP_BASE_URL + "fascicolo/riportaInCompletatoFascicolo.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function riapriFascicolo(id){
	console.log("riapri fascicolo con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaFascicoli();
	};

	var url = WEBAPP_BASE_URL + "fascicolo/riapriFascicolo.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}


function eseguiArchiviazione(id){
	console.log("riapri fascicolo con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaFascicoli();
	};

	var url = WEBAPP_BASE_URL + "fascicolo/archiviaFascicolo.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}


function richiediChiusuraFascicolo(id){
	console.log("richiedi chiusura fascicolo con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaFascicoli();
		$(".modal-backdrop").remove();
	};

	var url = WEBAPP_BASE_URL + "fascicolo/richiediChiusura.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}


function archiviaFascicolo(id){
	console.log("richiedi chiusura fascicolo con id: " + id);
	var numeroArchivio = document.getElementById("txtNumeroArchivio").value;
	var numeroArchivioContenitore = document.getElementById("txtNumeroArchivioContenitore").value;
	if( numeroArchivio == '' || numeroArchivioContenitore == '' ){
		return;
	}
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		document.getElementById("txtNumeroArchivio").value='';
		document.getElementById("txtNumeroArchivioContenitore").value='';
		cercaFascicoli();
	};
	
	var url = WEBAPP_BASE_URL + "fascicolo/archiviaFascicolo.action?id=" + id + "&numeroArchivio=" + encodeURIComponent(numeroArchivio) + "&numeroArchivioContenitore="+encodeURIComponent(numeroArchivioContenitore);
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function eliminaFascicolo(id) {
	console.log("elimino fascicolo con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		waitingDialog.hide();
		visualizzaMessaggio(data);
		cercaFascicoli();
		$(".modal-backdrop").remove();
	};
	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
	};

	var url = WEBAPP_BASE_URL + "fascicolo/elimina.action?id=" + id;
url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}

function caricaAzioniFascicolo(data) {
	waitingDialog.hide();
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuFascicolo(id);
		}

	}
}

function cercaFascicoli(){
	document.getElementById('btnApplicaFiltri').click();

}

function cercaFascicoliDaIcarico(){
	document.getElementById('btnApplicaFiltriIncarico').click();

}

function caricaAzioniSuFascicolo(id) {
	var containerAzioni = document.getElementById("containerAzioniRigaFascicolo" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "fascicolo/caricaAzioniFascicolo.action?idFascicolo=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}

 
function aggiungiDocumento(id) {
	var file = $('#file')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idFascicolo', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var categoriaCodice = document.getElementById('categoriaDocumentaleCode').value;
	data.append('categoriaDocumentaleCode', categoriaCodice);
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "fascicolo/uploadDocumento.action";
	var fnCallBackSuccess = function(data){ 
		if( location.href.indexOf("contenuto.action") ){
			if (data.stato == "KO") { 
				location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL+"fascicolo/contenuto.action?errorMessage=messaggio.operazione.ko&id="+id);
			}else{ 
				location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL+"fascicolo/contenuto.action?successMessage=messaggio.operazione.ok&id="+id);
			}
		}else{ 
			visualizzaMessaggio(data);
			waitingDialog.hide();
		}
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
}

function eliminaDocumento(fascicoloId, uuid) {
	 
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "fascicolo/eliminaDocumento.action?fascicoloId="+fascicoloId+"&uuid="+uuid;
	url=legalSecurity.verifyToken(url);
	var fnCallBackSuccess = function(data){ 
		if( location.href.indexOf("contenuto.action") ){
			if (data.stato == "KO") { 
				location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL+"fascicolo/contenuto.action?errorMessage=messaggio.operazione.ko&id="+fascicoloId);
			}else{ 
				location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL+"fascicolo/contenuto.action?successMessage=messaggio.operazione.ok&id="+fascicoloId);
			}
		}else{ 
			visualizzaMessaggio(data);
			waitingDialog.hide();
		}
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	
	ajaxUtil.ajaxUpload(url, "", fnCallBackSuccess, fnCallBackError);
}

function dettaglioFascicolo(id){
	window.open(legalSecurity.verifyToken(WEBAPP_BASE_URL+"fascicolo/dettaglio.action?id="+id), "_BLANK")
}

function initTabellaRicercaFascicoli() {

	var $table = $('#tabellaRicercaFascicoli').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'fascicolo/ricerca.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniFascicolo,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					waitingDialog.show('Loading...');
					var nome = encodeURIComponent(document
							.getElementById("txtNome").value);
					var oggetto = encodeURIComponent(document
							.getElementById("txtOggetto").value);
					var legaleEsterno = encodeURIComponent(document
							.getElementById("txtLegaleEsterno").value);
					var controparte = encodeURIComponent(document
							.getElementById("txtControparte").value);
					var dal = encodeURIComponent(document
							.getElementById("txtDataDal").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAl").value);
					var stato = encodeURIComponent(document
							.getElementById("comboStato").value);
					var owner = encodeURIComponent(document
							.getElementById("comboOwner").value);
					var tipologiaFascicoloCode = encodeURIComponent(document
							.getElementById("tipologiaFascicoloCode").value);
					var settoreGiuridicoCode = encodeURIComponent(document
							.getElementById("settoreGiuridicoCode").value);
					
					params.nome = nome;
					params.oggetto = oggetto;
					params.legaleEsterno = legaleEsterno;
					params.controparte = controparte;
					params.settoreGiuridicoCode = settoreGiuridicoCode;
					params.tipologiaFascicoloCode = tipologiaFascicoloCode;
					params.dal = dal;
					params.al = al;
					params.stato = stato;
					params.owner = owner;
					params.societaAddebito = null;
					if(document.getElementById("societaAddebito") != null){
						params.societaAddebito = document.getElementById("societaAddebito").value; 
					}
					if(document.getElementById("societaProcedimento") != null){
						params.societaProcedimento = document.getElementById("societaProcedimento").value; 
					}
					return params;
				},
				columns : [  {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				},{
					field : 'nome',
					title : 'NUMERO FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : true
				},{
					field : 'dataCreazione',
					title : 'DATA REG.',
					align : 'left',
					valign : 'top',
					sortable : true
				}, {
					field : 'stato',
					title : 'STATO',
					align : 'left',
					valign : 'top',
					sortable : true

				},{
					field : 'legaleEsterno',
					title : 'LEGALE ESTERNO',
					align : 'center',
					valign : 'top',
					sortable : false

				}, {
					field : 'controparte',
					title : 'CONTROPARTE',
					align : 'left',
					valign : 'top',
					sortable : false
				}, {
					field : 'anno',
					title : 'ANNO',
					align : 'center',
					valign : 'top',
					sortable : false
				}
				, {
					field : 'societaAddebito',
					title : 'SOCIETA DI ADDEBITO',
					align : 'left',
					valign : 'top',
					sortable : false
				},{
					field : 'societaProcedimento',
					title : 'SOCIETA DEL PROCEDIMENTO',
					align : 'left',
					valign : 'top',
					sortable : false
				}, {
					field : 'oggetto',
					title : 'OGGETTO',
					align : 'left',
					valign : 'top',
					class: 'bootstap-table-column-150w',
					sortable : false
				}]
			});

	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
	
	var tipologiaFascicoloCode = encodeURIComponent(document
			.getElementById("tipologiaFascicoloCode").value);
	
	if(tipologiaFascicoloCode=='TFSC_1')
		$('#tabellaRicercaFascicoli tr:eq(0)').find("[data-field='controparte']").html('<div class="th-inner ">CONTROPARTE/RICORRENTE</div><div class="fht-cell"></div>');

	
}

function initTabellaRicercaFascicoliModale(isMultiple) {

	var $table = $('#tabellaRicercaFascicoli').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'fascicolo/ricerca.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) { 
					if(isMultiple && document.getElementsByName("btSelectAll") ){
						document.getElementsByName("btSelectAll")[0].style.display="none";
					}
					var nome = encodeURIComponent(document
							.getElementById("txtNomeModal").value);
					var oggetto = encodeURIComponent(document
							.getElementById("txtOggettoModal").value);
					var legaleEsterno = encodeURIComponent(document
							.getElementById("txtLegaleEsternoModal").value);
					var controparte = encodeURIComponent(document
							.getElementById("txtControparteModal").value);
					var dal = encodeURIComponent(document
							.getElementById("txtDataDalModal").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAlModal").value);
					var tipologiaFascicoloCode = encodeURIComponent(document
							.getElementById("tipologiaFascicoloCodeModal").value);
					var settoreGiuridicoCode = encodeURIComponent(document
							.getElementById("settoreGiuridicoCodeModal").value);
					params.nome = nome;
					params.oggetto = oggetto;
					params.legaleEsterno = legaleEsterno;
					params.controparte = controparte;
					params.settoreGiuridicoCode = settoreGiuridicoCode;
					params.tipologiaFascicoloCode = tipologiaFascicoloCode;
					params.dal = dal;
					params.al = al;
					params.fascicoloCorrenteId = document.getElementById("fascicoloId").value;
					params.societaAddebito = null;
					if(document.getElementById("societaAddebito") != null){
						params.societaAddebito = document.getElementById("societaAddebito").value; 
					}
					return params;
				},
				columns : 
				[ {
					checkbox: isMultiple, 
					radio: !isMultiple,
					title : '',
					align : 'center',
					valign : 'top',
					sortable : true

				}, {
					field : 'nome',
					title : 'NUMERO FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : true

				},{
					field : 'dataCreazione',
					title : 'DATA REG.',
					align : 'left',
					valign : 'top',
					sortable : true
				}, {
					field : 'stato',
					title : 'STATO FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : true

				}, {
					field : 'legaleEsterno',
					title : 'LEGALE ESTERNO INCAR.',
					align : 'center',
					valign : 'top',
					sortable : true

				}, {
					field : 'controparte',
					title : 'CONTROPARTE',
					align : 'left',
					valign : 'top',
					sortable : false
				}, {
					field : 'anno',
					title : 'ANNO',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'societaAddebito',
					title : 'SOCIETA ADDEBITO',
					align : 'center',
					valign : 'top',
					sortable : false
				},  {
					field : 'oggetto',
					title : 'OGGETTO',
					align : 'left',
					valign : 'top',
					class: 'bootstap-table-column-150w',
					sortable : false
				}, {
					field : 'societaAddebito',
					title : 'SOCIETA DI ADDEBITO',
					align : 'left',
					valign : 'top',
					sortable : false
				}]
			});

			$('#tabellaRicercaFascicoli').on('check.bs.table', function (e, row) {
			  if( isMultiple ) {
				 checkedRows.push({id: row.id});
			  }else{
				 checkedRows = [{id: row.id}]; 
			  }
			  console.log(checkedRows);
			});
			
			if( isMultiple ){
				$('#tabellaRicercaFascicoli').on('uncheck.bs.table', function (e, row) {
				  $.each(checkedRows, function(index, value) {
					if (value.id === row.id) {
					  checkedRows.splice(index,1);
					}
				  });
				  console.log(checkedRows);
				});
			}
	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
	$('#btnApplicaFiltriIncarico').click(function() {
		$table.bootstrapTable('refresh');
	});

}


function estendiPermessiFascicolo(idFascicolo){
	console.log("estendiPermessiFascicolo con id: " + idFascicolo);
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
	
	var url = WEBAPP_BASE_URL + "fascicolo/estendiPermessiFascicolo.action?idFascicolo=" + idFascicolo;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}

function caricaGrigliaPermessiFascicolo(idFascicolo){
	console.log("caricaGrigliaPermessiFascicolo con id: " + idFascicolo);
	var tbody = document.getElementById("tBodyPermessi");
	tbody.innerHTML = '';
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		var tbody = document.getElementById("tBodyPermessi");
		tbody.innerHTML = data;
		
		waitingDialog.hide();
	};

	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
	};
	
	var url = WEBAPP_BASE_URL + "fascicolo/caricaGrigliaPermessiFascicolo.action?id=" + idFascicolo;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr );
}

function caricaPopupSelezioneProgetto(idFascicolo,nomeProgetto){
	
	if (idFascicolo===undefined) return;
	if (nomeProgetto===undefined || nomeProgetto==='') nomeProgetto="nonAssociato";
	
	console.log("caricaGrigliaPermessiFascicolo con id: " + idFascicolo);
	var tbody = document.getElementById("pageAssociaFascicoloAProgetto");
	tbody.innerHTML = '';
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		var tbody = document.getElementById("pageAssociaFascicoloAProgetto");
		tbody.innerHTML = data;
		initTabellaRicercaProgettoAssocia(idFascicolo);
		$("#AssociaFascicoloAProgetto_OK").hide();
		$("#AssociaFascicoloAProgetto_KO").hide();
		
//		var idProgetto = $("#data-table-progetto input[type='radio']:checked").val();
//		if (idProgetto != undefined){
//			$("#FascicoloGiaAssociato").show();
//		}

		waitingDialog.hide();
	};

	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
	};
	
	var url;
		 url = WEBAPP_BASE_URL + "fascicolo/caricaPopupSelezioneProgetto.action?id=" + idFascicolo+"&nomeProgetto="+nomeProgetto;
		 url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "get", "text/html", callBackFn, null, callBackFnErr );
}
 
function caricaComboOwnerRiassegnaFascicolo(idFascicolo){
	console.log("caricaComboOwnerRiassegnaFascicolo con id: " + idFascicolo);
	var combo = document.getElementById("comboNuovoOwner");
	combo.options.length = 1;
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		var combo = document.getElementById("comboNuovoOwner");	
		waitingDialog.hide();
		for( i = 0; i < data.length; i++ ){
			var option = document.createElement("OPTION");
			option.setAttribute("value",data[i].idOwner);
			var optionText = document.createTextNode(data[i].owner);
			option.appendChild(optionText);
			combo.appendChild(option);
		}
		
	};

	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
	};
	
	var url = WEBAPP_BASE_URL + "fascicolo/caricaComboOwnerRiassegnaFascicolo.action?id=" + idFascicolo;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", null, callBackFn, null, callBackFnErr );
}

function cambiaOwnerFascicolo(id){
	console.log("cambiaOwnerFascicolo fascicolo con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		cercaFascicoli();
		waitingDialog.hide();
	};

	var newOwner = document.getElementById("comboNuovoOwner").value;
	var oldOwner =  document.getElementById("txtOwner").value;
	if( newOwner == null || newOwner == undefined || newOwner == '' ){
		alert("Selezionare il nuovo owner");
		return;
	}
	
	var url = WEBAPP_BASE_URL + "fascicolo/cambiaOwnerFascicolo.action?id=" + id+"&oldOwner="+oldOwner+"&newOwner="+newOwner;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", null, callBackFn, null);
}


function associaFascicoloAProgetto(idFascicolo){
	
	if (idFascicolo===undefined) idFascicolo=	$("#idFascicoloDaAssociare").val();
	console.log("associaFascicoloAProgetto con idFascicolo: " + idFascicolo);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {   
		waitingDialog.hide();
		$("#AssociaFascicoloAProgetto_OK").show();
		$("#FascicoloGiaAssociato").hide();
	};
	
	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
		$("#AssociaFascicoloAProgetto_KO").show();
		$("#FascicoloGiaAssociato").hide();
	};
	
	var idProgetto = $("#data-table-progetto input[type='radio']:checked").val();

	var url = WEBAPP_BASE_URL + "fascicolo/associaFascicoloAProgetto.action?idFascicolo=" + idFascicolo+"&idProgetto="+idProgetto;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}

$(document).on('change','#posizioneSocietaAddebito',function(){
	checkPosizioneSocieta();
});

function checkPosizioneSocieta(){
	  if($('#posizioneSocietaAddebito').val()=='PSSC_11'){
		  $('#divContro').css('display','none');
		  $('#divResis').css('display','block');
		  $('#divRicor').css('display','block');
	  }
	  else if($('#posizioneSocietaAddebito').val()=='PSSC_7'){
		  $('#divContro').css('display','block');
		  $('#divResis').css('display','none');
		  $('#divRicor').css('display','block');
	  }
	  else if($('#posizioneSocietaAddebito').val()=='PSSC_8'){
		  $('#divContro').css('display','block');
		  $('#divResis').css('display','block');
		  $('#divRicor').css('display','none');
	  }
	  else{
		  $('#divContro').css('display','block');
		  $('#divResis').css('display','none');
		  $('#divRicor').css('display','none');
	  }
}

 