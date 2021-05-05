idProforma = $('#proformaId').val();
timeout = $('#timeout').val();
totaleForce = $('#totaleForce').val();

var oldLabelOnorari = $('#labelOnorari').text();
var oldLabelSpeseNonImponibile = $('#labelSpeseNonImponibile').text();
var oldLabelTotale = $('#labelTotale').text();
var tipoProformaMod = $('#tipoProformaMod').val();
var isCPADisbled = $('#isCPADisabled').val();


$(document).ready(function () {
	
	if(typeof idProforma!== 'undefined' && typeof timeout!== 'undefined' && idProforma!="" && timeout!=""){
		$request = new Request(timeout,idProforma);
		$request.activatePoll();
	}
});

$(document).end(function(){
	$request.disablePoll();
});

function Request(time, id) {
	this.pollTimer = null;
	this.interval = time;
	this.url = legalSecurity.verifyToken(WEBAPP_BASE_URL + 'proforma/checkfile.action?id='+id);
}

Request.prototype.disablePoll = function () {
	clearInterval(this.pollTimer); 
	this.pollTimer = null;
};

//Request.prototype.activatePoll = function () {
//	$.getJSON(this.url).then(response => setIcon(response.stato));
//	this.pollTimer = setInterval(() => {
//		$.getJSON(this.url).then(response => setIcon(response.stato))
//	}, this.interval);
//};
//Visto che Internet Explorer è scemo:

Request.prototype.activatePoll = function () {
	var _this = this;

	$.getJSON(this.url).then(function (response) {
		return setIcon(response.stato);
	});
	this.pollTimer = setInterval(function () {
		$.getJSON(_this.url).then(function (response) {
			return setIcon(response.stato);
		});
	}, this.interval);
};

function setIcon(status){
	if(status=='WARN'){
		$( "#SchedaValutazioneDownload" ).html('<a id="schedaValutazioneGraffa" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;color:red;" aria-expanded="true"> <i class="fa fa-exclamation-triangle " title="Errore nella creazione della Scheda di Valutazione"></i></a>');
	}
	if(status=='OK'){
		$( "#SchedaValutazioneDownload" ).html('<a id="schedaValutazioneGraffa" href="'+WEBAPP_BASE_URL + 'proforma/downloadSchedaValutazione.action?id='+idProforma+'&CSRFToken='+legalSecurity.getToken()+'" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;" aria-expanded="true"> <i class="fa fa-paperclip " title="Scarica la Scheda di Valutazione"></i></a>');
	}
	else if(status=='KO'){
		$( "#SchedaValutazioneDownload" ).html('<a id="schedaValutazioneGraffa" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;" aria-expanded="true"> <i class="fa fa-gear fa-spin " title="Generazione PDF Scheda di Valutazione in corso"></i></a>');
	}
}

function salvaProforma(force) {	
	console.log('salva proforma');
	if( force == undefined || !force ){
		
		refreshModalRicalcola();
	    var checked = checkTotale(); 
		if( !checked ){
			$('#modalConfirmSave').modal('show'); 
			return;
		}
		
	}
	var totale = document.getElementById("totale").value;
	if( totale == undefined || totale == 0){
		ricalcola();
	}
	var form = document.getElementById("proformaView");
	var op = document.getElementById("op");
	op.value = "salvaProforma";
	waitingDialog.show('Loading...');
	
	form.submit();
}

function checkTotale(){

	var dirittiInput = document.getElementById('diritti');
	var onorariInput = document.getElementById('onorari');
	var speseImponibiliInput = document.getElementById('speseImponibili');
	var speseNonImponibiliInput = document.getElementById('speseNonImponibili');
	var totaleImponibileInput = document.getElementById('totaleImponibile');
	var totaleInput = document.getElementById('totale');
	var cpaInput = document.getElementById('cpa');
 	var diritti = parseFloat(dirittiInput.value).toFixed(2);
	var onorari = parseFloat(onorariInput.value).toFixed(2);
	var speseImponibili = parseFloat(speseImponibiliInput.value).toFixed(2);
	var cpa =  parseFloat(( parseFloat(diritti) + parseFloat(onorari) + parseFloat(speseImponibili) ) * 0.04).toFixed(2);
	var speseNonImponibili = parseFloat(speseNonImponibiliInput.value);
	var totaleImponibile =  parseFloat(parseFloat(diritti) + parseFloat(onorari) + parseFloat(speseImponibili) + parseFloat(cpa)).toFixed(2);
	var totale = parseFloat(parseFloat(totaleImponibile) + parseFloat(speseNonImponibili)).toFixed(2);
	
	var controllTotale = parseFloat(totaleInput.value).toFixed(2);
	
	if( totale == controllTotale) {
		return true;
	}else{
		return false;
	}
}

function refreshModalRicalcola(){
	var dirittiInput = document.getElementById('diritti').value;
	var onorariInput = document.getElementById('onorari').value;
	var speseImponibiliInput = document.getElementById('speseImponibili').value;
	var speseNonImponibiliInput = document.getElementById('speseNonImponibili').value;
	var totaleImponibileInput = document.getElementById('totaleImponibile').value;
	var totaleInput = document.getElementById('totale').value;
	var cpaInput = document.getElementById('cpa').value;

	
	
	var dirittiLbl = document.getElementById('lblDiritti');
	var onorariLbl  = document.getElementById('lblOnorari');
	var speseImponibiliLbl = document.getElementById('lblSpeseImponibili');
	var speseNonImponibiliLbl = document.getElementById('lblSpeseNonImponibili');
	var totaleImponibileLbl = document.getElementById('lblTotaleImponibile');
	var totaleLbl = document.getElementById('lblTotale');
	var cpaLbl = document.getElementById('lblCpa');
	
	var dirittiLblN = document.getElementById('lblDirittiN');
	var onorariLblN  = document.getElementById('lblOnorariN');
	var speseImponibiliLblN = document.getElementById('lblSpeseImponibiliN');
	var speseNonImponibiliLblN = document.getElementById('lblSpeseNonImponibiliN');
	var totaleImponibileLblN = document.getElementById('lblTotaleImponibileN');
	var totaleLblN = document.getElementById('lblTotaleN');
	
	var feesLbl = document.getElementById('lblFees');
	var decLbl  = document.getElementById('lblDec');
	var totalLbl = document.getElementById('lblTotal');
	
	
	if(dirittiLbl!=null)
		dirittiLbl.innerHTML = dirittiInput;
	if(onorariLbl!=null)
		onorariLbl.innerHTML = onorariInput;
	if(speseImponibiliLbl!=null)
		speseImponibiliLbl.innerHTML = speseImponibiliInput;
	if(speseNonImponibiliLbl!=null)
		speseNonImponibiliLbl.innerHTML = speseNonImponibiliInput;
	if(totaleImponibileLbl!=null)
		totaleImponibileLbl.innerHTML = totaleImponibileInput;
	if(totaleLbl!=null)
		totaleLbl.innerHTML = totaleInput;
	if(cpaLbl!=null)
		cpaLbl.innerHTML = cpaInput; 
	
	
	if(dirittiLblN!=null)
		dirittiLblN.innerHTML = dirittiInput;
	if(onorariLblN!=null)
		onorariLblN.innerHTML = onorariInput;
	if(speseImponibiliLblN!=null)
		speseImponibiliLblN.innerHTML = speseImponibiliInput;
	if(speseNonImponibiliLblN!=null)
		speseNonImponibiliLblN.innerHTML = speseNonImponibiliInput;
	if(totaleImponibileLblN!=null)
		totaleImponibileLblN.innerHTML = totaleImponibileInput;
	if(totaleLblN!=null)
		totaleLblN.innerHTML = totaleInput;
	
	if(feesLbl!=null)
		feesLbl.innerHTML = onorariInput;
	if(decLbl!=null)
		decLbl.innerHTML = speseNonImponibiliInput;
	if(totalLbl!=null)
		totalLbl.innerHTML = totaleInput;
}

function ricalcola(){
	calcolaTotaliProforma();
}
 
function modificaIncarico(id){
  location.href= legalSecurity.verifyToken(WEBAPP_BASE_URL+"proforma/modifica.action?id="+id);
}
 

function aggiungiSchedaValutazione(id){
	var file = $('#fileSchedaValutazione')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idProforma', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "proforma/uploadSchedaValutazione.action";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxSchedaValutazione');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("schedaValutazioneGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("schedaValutazioneGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
	
}

function rimuoviSchedaValutazione(id){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "proforma/rimuoviSchedaValutazione.action";
	url=legalSecurity.verifyToken(url);
	var params =  "&idProforma="+id;
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxSchedaValutazione');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("schedaValutazioneGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("schedaValutazioneGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
}

function aggiungiAllegatoGenerico(id){
	var file = $('#fileAllegatoGenerico')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idIncarico', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "proforma/uploadAllegatoGenerico.action";
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
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, null, fnCallBackError);
} 

 
function rimuoviAllegatoGenerico(uuid){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	uuid = uuid.replace('{','');
	uuid = uuid.replace('}','');
	
	var url = WEBAPP_BASE_URL + "proforma/rimuoviAllegatoGenerico.action?uuid="+uuid;
	url=legalSecurity.verifyToken(url);
	var params =  "";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxAllegatiGenerici');
		container.innerHTML=data;
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
}  


function modificaProforma(id){
	location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL + "proforma/modifica.action?id="+id);
}

function respingiProforma(id){
	location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL + "proforma/respingi.action?id="+id);
}

function caricaAzioniProforma(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuProforma(id);
		}

	}
} 

function cercaProforma(){
	//document.getElementById('btnApplicaFiltri').click();
	var $table = $('#tabellaRicercaProforma');
	$table.bootstrapTable('refresh');
}

function caricaAzioniSuProforma(id) {
	var containerAzioni = document.getElementById("containerAzioniRigaProforma" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "proforma/caricaAzioniProforma.action?idProforma=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}


function initTabellaRicercaProforma() {
	
	var $table = $('#tabellaRicercaProforma').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'proforma/ricerca.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniProforma,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					var nome = encodeURIComponent(document
							.getElementById("txtNomeProforma").value); 
					var dal = encodeURIComponent(document
							.getElementById("txtDataDal").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAl").value); 
					var nomeFascicolo = encodeURIComponent(document
							.getElementById("txtNomeFascicolo").value); 
					var nomeIncarico = encodeURIComponent(document
							.getElementById("txtNomeIncarico").value); 
					var stato = document.getElementById("comboStato").value;
					var societaAddebito = document.getElementById("comboSocietaAddebito").value;
					var fatturato = document.getElementById("fatturato").value;
					var contabilizzato = document.getElementById("contabilizzato").value;
				
					params.nomeProforma = nome; 
					params.nomeFascicolo = nomeFascicolo; 
					params.nomeIncarico = nomeIncarico; 
					params.societaAddebito = societaAddebito; 
					params.dal = dal; 
					params.al = al;
					params.statoCode = stato;
					params.fatturato = fatturato;
					params.contabilizzato = contabilizzato;
					return params;
				},
				columns : [  {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				},{
					field : 'nomeProforma',
					title : 'NOME PROFORMA',
					align : 'left',
					valign : 'top',
					sortable : true

				}, {
					field : 'fattura',
					title : 'FATTURA',
					align : 'left',
					valign : 'top',
					sortable : false
					
				}, {
					field : 'contabilizzata',
					title : 'CONTABILIZZATA',
					align : 'left',
					valign : 'top',
					sortable : false
					
				}, {
					field : 'stato',
					title : 'STATO',
					align : 'center',
					valign : 'top',
					sortable : true
					
				}, {
					field : 'annoEsercizioFinanziario',
					title : 'ANNO ESER. FINAN.',
					align : 'center',
					valign : 'top',
					sortable : true
				}, {
					field : 'dataInserimento',
					title : 'DATA INSERIMENTO',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'nomeIncarico',
					title : 'INCARICO',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'nomeSocieta',
					title : 'NOME SOCIETA',
					align : 'center',
					valign : 'top',
					sortable : false
				}]
			});

	$('#btnApplicaFiltri').click(function() {
		//$table.bootstrapTable('refresh');
	});
}
 

function eliminaProforma(id){
	console.log("elimino proforma con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaProforma();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "proforma/eliminaProforma.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
}
//DARIO ***********************************************************
//function avviaWorkFlowProforma(id){
function avviaWorkFlowProforma(id,matricola_dest){
//******************************************************************	
	//DARIO ***********************************************************
	//console.log("avvio workflow proforma con id: " + id);
	console.log("avviaWorkFlowProforma(" + id + "," + matricola_dest+ ")");
	//*****************************************************************	
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaProforma();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	
	
	//DARIO***********************************************************************
	//var url = WEBAPP_BASE_URL + "proforma/avviaWorkFlowProforma.action?id=" + id;
	var url = WEBAPP_BASE_URL + "proforma/avviaWorkFlowProforma.action?id=" + id+"&matricola_dest="+matricola_dest;
	//****************************************************************************
	
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
}
//DARIO ***********************************************************
//function avviaWorkFlowProformaDaForm(id){
function avviaWorkFlowProformaDaForm(id,matricola_dest){
//******************************************************************	
	//DARIO ***********************************************************
	//console.log("avvio workflow proforma con id: " + id);
	console.log("avviaWorkFlowProformaDaForm(" + id + "," + matricola_dest+ ")");
	//*****************************************************************
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		$("#btnAvviaWorkFlowProformaForm").hide();
		cercaProforma();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	
	
	//DARIO***********************************************************************
	//var url = WEBAPP_BASE_URL + "proforma/avviaWorkFlowProforma.action?id=" + id;
	var url = WEBAPP_BASE_URL + "proforma/avviaWorkFlowProforma.action?id=" + id+"&matricola_dest="+matricola_dest;
	//****************************************************************************
	
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
}
 
function riportaInBozzaWorkFlowProforma(id){
	console.log("riporto in bozza workflow Proforma con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaProforma();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "proforma/riportaInBozzaWorkFlowProforma.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
} 

function arretraWorkFlowProforma(id){
	console.log("arretra workflow Proforma con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaProforma();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "proforma/arretraWorkFlowProforma.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null,  callBackFnErr);
}

function calcolaTotaliProforma(){
	
	var dirittiInput = document.getElementById('diritti');
	var onorariInput = document.getElementById('onorari');
	var speseImponibiliInput = document.getElementById('speseImponibili');
	var speseNonImponibiliInput = document.getElementById('speseNonImponibili');
	var totaleImponibileInput = document.getElementById('totaleImponibile');
	var totaleInput = document.getElementById('totale');
	var cpaInput = document.getElementById('cpa');
 	var diritti = parseFloat(dirittiInput.value).toFixed(2);
	var onorari = parseFloat(onorariInput.value).toFixed(2);
	var speseImponibili = parseFloat(speseImponibiliInput.value).toFixed(2);


	var disableCPA = $("#disableCPA").is(':checked');
	
	var cpa = 0;
	
	if(!disableCPA && tipoProformaMod=='ITA')
		cpa =  parseFloat(( parseFloat(diritti) + parseFloat(onorari) + parseFloat(speseImponibili) ) * 0.04).toFixed(2);
	
	
	
	var speseNonImponibili = parseFloat(speseNonImponibiliInput.value);
	var totaleImponibile =  parseFloat(parseFloat(diritti) + parseFloat(onorari) + parseFloat(speseImponibili) + parseFloat(cpa)).toFixed(2);
	var totale = parseFloat(parseFloat(totaleImponibile) + parseFloat(speseNonImponibili)).toFixed(2);
	
	cpaInput.value = cpa;
	totaleImponibileInput.value = totaleImponibile;
	totaleInput.value = totale; 
	

	refreshModalRicalcola();
	
}


function selezionaSocieta(idSocieta, settoreId, tipologiaId, unitaLegale){
	console.log("selezionaSocieta "+ idSocieta);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {  
		waitingDialog.hide();
		var comboVDC = document.getElementById("comboVDC");
		comboVDC.innerHTML='';
		var comboCDC = document.getElementById("comboCDC");
		comboCDC.innerHTML='';
		var arrayVDC = data.VDC;
		var arrayCDC = data.CDC;
		
		for( i = 0; i < arrayVDC.length; i++ ){
			var vdc = arrayVDC[i].vdc;
			var vdcDesc = arrayVDC[i].descrizione;
			var li = document.createElement("LI");
			var a = document.createElement("A");
			a.setAttribute("href", "javascript:void(0)");
			a.setAttribute("id", "aVdc"+arrayVDC[i].id );
			a.setAttribute("onclick", "javascript: document.getElementById('voceDiConto').value=document.getElementById('aVdc"+arrayVDC[i].id+"').getAttribute('data-value')"); 
			a.setAttribute("data-value", arrayVDC[i].vdc );
			var aText = document.createTextNode(arrayVDC[i].vdc + " - " + arrayVDC[i].descrizione);
			a.appendChild(aText);
			li.appendChild(a);
			comboVDC.appendChild(li); 
		}

		for( i = 0; i < arrayCDC.length; i++ ){
			var cdc = arrayCDC[i].cdc;
			var cdcDesc = arrayCDC[i].descrizione;
			var li = document.createElement("LI");
			var a = document.createElement("A"); 
			a.setAttribute("href", "javascript:void(0)");
			a.setAttribute("id", "aCdc"+arrayCDC[i].id );
			a.setAttribute("onclick", "javascript: document.getElementById('centroDiCosto').value=document.getElementById('aCdc"+arrayCDC[i].id+"').getAttribute('data-value')"); 
			a.setAttribute("data-value", arrayCDC[i].cdc );
			var aText = document.createTextNode(arrayCDC[i].cdc + " - " + arrayCDC[i].descrizione);
			a.appendChild(aText);
			li.appendChild(a);
			comboCDC.appendChild(li); 
		}
	};

	var url = WEBAPP_BASE_URL + "proforma/selezionaSocieta.action?unitaLegale="+unitaLegale+"&tipologiaFascicoloId="+tipologiaId+"&settoreGiuridicoId="+settoreId+"&idSocieta="+idSocieta;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "get", "text/html", callBackFn, null);
}


$(document).on('change','#idTipoProforma',function(){
	
	if($(this).children(":selected").attr("id")=='NOT'){
		$('#divCPA').css("display", "none");
		$('#disableCPA').prop('checked', true);
		
		$('#divDiritti').css("display", "block");
		$('#divSpeseImponibili').css("display", "block");
		$('#divTotaleImponibile').css("display", "block");
		
		$('#divNOT').css("display", "block");
		$('#divITA').css("display", "none");
		$('#divEST').css("display", "none");
		
		$('#labelOnorari').text(oldLabelOnorari);
		$('#labelSpeseNonImponibile').text(oldLabelSpeseNonImponibile);
		$('#labelTotale').text(oldLabelTotale);
	}
	else if($(this).children(":selected").attr("id")=='ITA'){
		$('#divCPA').css("display", "block");
		$('#disableCPA').prop('checked', false);
		
		$('#divDiritti').css("display", "block");
		$('#divSpeseImponibili').css("display", "block");
		$('#divTotaleImponibile').css("display", "block");
		
		$('#divNOT').css("display", "none");
		$('#divITA').css("display", "block");
		$('#divEST').css("display", "none");
		
		$('#labelOnorari').text(oldLabelOnorari);
		$('#labelSpeseNonImponibile').text(oldLabelSpeseNonImponibile);
		$('#labelTotale').text(oldLabelTotale);
	}
	else if($(this).children(":selected").attr("id")=='EST'){
		$('#divCPA').css("display", "none");
		$('#disableCPA').prop('checked', true);
		
		$('#divNOT').css("display", "none");
		$('#divITA').css("display", "none");
		$('#divEST').css("display", "block");
		
		$('#diritti').val(0);
		$('#divDiritti').css("display", "none");
		
		$('#labelOnorari').text('Fees');
		
		$('#speseImponibili').val(0);
		$('#divSpeseImponibili').css("display", "none");
		
		$('#totaleImponibile').val(0);
		$('#divTotaleImponibile').css("display", "none");
		
		$('#labelSpeseNonImponibile').text('Disbursements & Charges');
		
		$('#labelTotale').text('Total');
	}
	ricalcola();
});


$(document).ready(function () {
	if(typeof tipoProformaMod!== 'undefined' && tipoProformaMod!=""){

		$('#idTipoProforma option[id='+tipoProformaMod+']').attr("selected",true);

		$( "#idTipoProforma" ).trigger( "change" );
		
		if(isCPADisbled=='true' && tipoProformaMod=='ITA')
					$('#disableCPA').trigger("click");

	}
	else
		tipoProformaMod='ITA';
	
	if(typeof totaleForce!== 'undefined' && totaleForce!=""){
		$('#totale').val(totaleForce);
	}
	
});


function aggiungiSchedaValutazioneFirmata(id){
	var file = $('#fileSchedaFirmata')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idProforma', id);
        data.append('CSRFToken', legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "proforma/uploadSchedaValutazioneFirmata.action";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxSchedaValutazione');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("schedaValutazioneGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("schedaValutazioneGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
}

function rimuoviSchedaValutazioneFirmata(id){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "proforma/rimuoviSchedaValutazioneFirmata.action";
url=legalSecurity.verifyToken(url);
	var params =  "&idProforma="+id;
	var fnCallBackSuccess = function(data){ 
		
		waitingDialog.hide();
		var container = document.getElementById('boxSchedaValutazione');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("schedaValutazioneGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("schedaValutazioneGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
}