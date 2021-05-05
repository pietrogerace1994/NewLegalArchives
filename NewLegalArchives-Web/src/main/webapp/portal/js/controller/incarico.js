bookIndex = $('#sizeA').val();
bonusIndex = $('#sizeB').val() - 1;
disabled = $('#dis').val();
idLettera = $('#idLettera').val();
idNotaProp = $('#idNotaProp').val();
timeout = $('#timeout').val();
timeoutNot = $('#timeoutNot').val();


$(".incaricodettaglio :input").attr("disabled", true);

if(disabled=='true'){
	$('.disabilitaaut').attr("disabled", true);
	$('.disabilitaaut').attr("readonly", true);
}


$(document).ready(function () {
	
		
	if($('#quadroC1').is(":checked")){
		addQuadro1();
	}
});

$(document).ready(function () {
	if($('#quadroC2').is(":checked")){
		removeQuadro1();
	}
});

$(document).ready(function () {
if($('#quadroC').is(":checked")){
	addQuadro();
}


updateSaldo();
aggiornaValoreIncarico();

if(typeof idLettera!== 'undefined' && typeof timeout!== 'undefined' && idLettera!="" && timeout!=""){
	$request = new Request(timeout,idLettera);
	$request.activatePoll();
}

if(typeof idNotaProp!== 'undefined' && typeof timeoutNot!== 'undefined' && idNotaProp!="" && timeoutNot!=""){
	$requestNot = new RequestNot(timeoutNot,idNotaProp);
	$requestNot.activatePoll();
}

$('#specializzazioneCode').attr("disabled","disabled");
$('#professionistaId').attr("disabled","disabled");
$('#nazioneCode').on('change', function() {
	  var sel=this.value;
	  var specializzazioneSel = document.getElementById("specializzazioneCode");
	  
	  if(sel != null && sel != "") {
		  specializzazioneSel.value = "";
		  $('#specializzazioneCode').removeAttr("disabled");
	  }
	  else {
		  specializzazioneSel.value = "";
		  $('#specializzazioneCode').attr("disabled","disabled");
	  }

});

$('#specializzazioneCode').on('change', function() {
	  var sel=this.value;
	  var profEstSel = document.getElementById("professionistaId");
	  var nazCod = document.getElementById("nazioneCode").value;
	  
	  if(sel != null && sel != "") {
		  waitingDialog.show('Loading...');
		  var ajaxUtil = new AjaxUtil();
		  var callBackFn = function(data, stato) {
			  	
			  waitingDialog.hide();
			  	$('#professionistaId').removeAttr("disabled");
				$("#professionistaId").empty();
				
				var str = "";
				str += "<option value=\"\">Seleziona professionista esterno</option>";
				
				if(data.length != 0){
					
					var arrJson = JSON.parse(data);
					
					for (var i = 0; i < arrJson.length; i++) {
						
						var ele = arrJson[i];
						str += "<option value=\""+ ele.id +"\">"+ ele.denominazione + " " + ele.cognomeNome + "</option>";
					}
				}
				$(str).appendTo("#professionistaId");
			};
			var url = WEBAPP_BASE_URL + "incarico/cercaProfessionistaEsterno.action?nazione=" + nazCod + "&spec=" + sel;
			url=legalSecurity.verifyToken(url);
			ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
	  }
	  else {
		  profEstSel.value = "";
		  $('#professionistaId').attr("disabled","disabled");
	  }
});

});



if($( "input[name*='acconto[0].anno']" ).val()=='')
	$( "input[name*='acconto[0].anno']" ).val( new Date().getFullYear() );



$(document).end(function(){
	$request.disablePoll();
	$requestNot.disablePoll();
});

//LETTERA INCARICO

function Request(time, id) {
	this.pollTimer = null;
	this.interval = time;
	this.url = legalSecurity.verifyToken(WEBAPP_BASE_URL + 'incarico/checkfile.action?id='+id);
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
		$( "#LetteraDownload" ).html('<a id="letteraIncaricoGraffa" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;color:red;" aria-expanded="true"> <i class="fa fa-exclamation-triangle " title="Errore nella creazione della Lettera D\'Incarico"></i></a>');
	}
	if(status=='OK'){
		$( "#LetteraDownload" ).html('<a id="letteraIncaricoGraffa" href="'+WEBAPP_BASE_URL + 'incarico/downloadLetteraIncarico.action?id='+idLettera+'&CSRFToken='+legalSecurity.getToken()+'" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;" aria-expanded="true"> <i class="fa fa-paperclip " title="Scarica la Lettera D\'Incarico"></i></a>');
		$("#letteraFirmataDiv").css("display", "block");
	}
	else if(status=='KO'){
		$( "#LetteraDownload" ).html('<a id="letteraIncaricoGraffa" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;" aria-expanded="true"> <i class="fa fa-gear fa-spin " title="Generazione PDF Lettera D\'Incarico in corso"></i></a>');
	}
}

//NOTA PROPOSTA INCARICO

function RequestNot(time, id) {
	this.pollTimer = null;
	this.interval = time;
	this.url = legalSecurity.verifyToken(WEBAPP_BASE_URL + 'incarico/checkfileNotaProposta.action?id='+id);
}

RequestNot.prototype.disablePoll = function () {
	clearInterval(this.pollTimer); 
	this.pollTimer = null;
};

RequestNot.prototype.activatePoll = function () {
	var _this = this;

	$.getJSON(this.url).then(function (response) {
		return setIconNot(response.stato);
	});
	this.pollTimer = setInterval(function () {
		$.getJSON(_this.url).then(function (response) {
			return setIconNot(response.stato);
		});
	}, this.interval);
};

function setIconNot(status){
	
	if(status=='WARN'){
		$( "#NotaPropostaDownload" ).html('<a id="notaPropostaGraffa" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;color:red;" aria-expanded="true"> <i class="fa fa-exclamation-triangle " title="Errore nella creazione della Nota Proposta D\'incarico"></i></a>');
	}
	if(status=='OK'){
		console.log(idNotaProp);
		$( "#NotaPropostaDownload" ).html('<a id="notaPropostaGraffa" href="'+WEBAPP_BASE_URL + 'incarico/downloadNotaProposta.action?id='+idNotaProp+'&CSRFToken='+legalSecurity.getToken()+'" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;" aria-expanded="true"> <i class="fa fa-paperclip " title="Scarica la Nota Proposta D\'incarico"></i></a>');
		$("#notaFirmataDiv").css("display", "block");
	}
	else if(status=='KO'){
		$( "#NotaPropostaDownload" ).html('<a id="notaPropostaGraffa" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;" aria-expanded="true"> <i class="fa fa-gear fa-spin " title="Generazione PDF Nota Proposta D\'incarico in corso"></i></a>');
	}
}

function salvaIncarico() {
	console.log('salva incarico');
	var form = document.getElementById("incaricoView");
	var op = document.getElementById("op");
	op.value = "salvaIncarico";
	waitingDialog.show('Loading...');
	
	form.submit();
}

function modificaCollegioArbitrale(id){ 
  location.href=WEBAPP_BASE_URL+"incarico/modificaCollegioArbitrale.action?id="+id;
}

function modificaIncarico(id){
  location.href=WEBAPP_BASE_URL+"incarico/modifica.action?id="+id;
}

function aggiungiProcura(id){
	var file = $('#fileProcura')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idIncarico', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/uploadProcura.action";
	 
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxProcura');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("procuraGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("procuraGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
	
}

function aggiungiLetteraFirmata(id){
	var file = $('#fileLetteraFirmata')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idIncarico', id);
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/uploadletteraincaricofirmata.action";
url=legalSecurity.verifyToken(url);
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('letteraFirmataDiv');
		container.innerHTML=data;
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
		
	};
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
	
}

//nota proposta - aggiungi
function aggiungiNotaPropostaFirmata(id){
	var file = $('#fileNotaPropostaFirmata')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idIncarico', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/uploadnotapropostafirmata.action";
url=legalSecurity.verifyToken(url);
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('notaFirmataDiv');
		container.innerHTML=data;
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
	
}

//nota proposta - rimuovi
function rimuoviNotaPropostaFirmata(id){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/rimuoviNotaPropostaFirmata.action";
url=legalSecurity.verifyToken(url);
	var params =  "&idIncarico="+id;
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('notaFirmataDiv');
		container.innerHTML=data;
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
}


function aggiungiVerificaAnticorruzione(id){
	var file = $('#fileVerificaAnticorruzione')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idIncarico', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/uploadVerificaAnticorruzione.action";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxVerificaAnticorruzione');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("verificaAnticorruzioneGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("verificaAnticorruzioneGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
} 

function aggiungiVerificaPartiCorrelate(id){
	var file = $('#fileVerificaPartiCorrelate')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idIncarico', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/uploadVerificaPartiCorrelate.action";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxVerificaPartiCorrelate');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("verificaPartiCorrelateGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("verificaPartiCorrelateGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
}

function aggiungiListeRiferimento(id){
	var file = $('#fileListeRiferimento')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idIncarico', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/uploadListeRiferimento.action";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxListeRiferimento');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("listeRiferimentoGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("listeRiferimentoGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
} 


function aggiungiAllegatoGenerico(id){
	var file = $('#fileAllegatoGenerico')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idIncarico', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/uploadAllegatoGenerico.action";
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


function rimuoviListeRiferimento(id){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/rimuoviListeRiferimento.action";
	url=legalSecurity.verifyToken(url);
	var params =  "&idIncarico="+id;
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxListeRiferimento');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("listeRiferimentoGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("listeRiferimentoGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
}


function rimuoviVerificaPartiCorrelate(id){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/rimuoviVerificaPartiCorrelate.action";
	url=legalSecurity.verifyToken(url);
	var params =  "&idIncarico="+id;
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxVerificaPartiCorrelate');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("verificaPartiCorrelateGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("verificaPartiCorrelateGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
}




function rimuoviVerificaAnticorruzione(id){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/rimuoviVerificaAnticorruzione.action";
	url=legalSecurity.verifyToken(url);
	var params =  "&idIncarico="+id;
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxVerificaAnticorruzione');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("verificaAnticorruzioneGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("verificaAnticorruzioneGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
} 


function rimuoviAllegatoGenerico(uuid){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	uuid = uuid.replace('{','');
	uuid = uuid.replace('}','');
	
	var url = WEBAPP_BASE_URL + "incarico/rimuoviAllegatoGenerico.action?uuid="+uuid;
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


function rimuoviProcura(id){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/rimuoviProcura.action";
	url=legalSecurity.verifyToken(url);
	var params =  "&idIncarico="+id;
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxProcura');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("procuraGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("procuraGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
}

function rimuoviLetteraFirmata(id){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/rimuoviLetteraFirmata.action";
url=legalSecurity.verifyToken(url);
	var params =  "&idIncarico="+id;
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('letteraFirmataDiv');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("procuraGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("procuraGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
}

function salvaCollegioArbitrale(){
	console.log('salva collegio arbitrale');
	var form = document.getElementById("collegioArbitraleView");
	var op = document.getElementById("op");
	op.value = "salvaCollegioArbitrale";
	waitingDialog.show('Loading...');
	
	form.submit();
}


function caricaAzioniIncarico(data) {
	waitingDialog.hide();
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuIncarico(id);
		}

	}
}

function aggiornaValoreIncarico() {
	
	var compenso = parseFloat($("#compenso").val());
	
	var out = "";
	
	if (compenso != null ) {
		
		out = compenso + " EURO";
		
		for (i = 0; i < bonusIndex+1; i++) {
			
			if($("[name='bonus["+i+"].importo']").val()!="0")
				out = out +" + " + $("[name='bonus["+i+"].importo']").val()+ " EURO : "+ $("[name='bonus["+i+"].descrizione']").val();
		}
			
		$("#valoreIncarico").val(out);
	}
}


function cercaIncarico(){
	document.getElementById('btnApplicaFiltri').click();

}

function caricaAzioniSuIncarico(id) {
	var containerAzioni = document.getElementById("containerAzioniRigaIncarico" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "incarico/caricaAzioniIncarico.action?idIncarico=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}


function initTabellaRicercaIncarico() {

	var $table = $('#tabellaRicercaIncarico').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'incarico/ricerca.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniIncarico,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					var nome = encodeURIComponent(document
							.getElementById("txtNomeIncarico").value); 
					var dal = encodeURIComponent(document
							.getElementById("txtDataDal").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAl").value); 
					var statoCode = document
							.getElementById("statoIncaricoCode").value
					var nomeFascicolo = encodeURIComponent( document.getElementById("txtNomeFascicolo").value) ;
					params.nomeIncarico = nome; 
					params.dal = dal;
					params.al = al;
					params.statoIncaricoCode = statoCode;
					params.nomeFascicolo = nomeFascicolo;
					return params;
				},
				columns : [ {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				} ,{
					field : 'nomeIncarico',
					title : 'NOME INCARICO',
					align : 'left',
					valign : 'top',
					sortable : true

				}, {
					field : 'nomeFascicolo',
					title : 'FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : false
					
				},{
					field : 'dataCreazione',
					title : 'DATA REG.',
					align : 'left',
					valign : 'top',
					sortable : true
				}, {
					field : 'stato',
					title : 'STATO INCARICO',
					align : 'center',
					valign : 'top',
					sortable : true
					
				}, {
					field : 'anno',
					title : 'ANNO',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'dataCreazione',
					title : 'DATA INCARICO',
					align : 'center',
					valign : 'top',
					sortable : false
				}  ]
			});

	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}



//ARBITRALE RICERCA

function caricaAzioniArbitrale(data) {
	waitingDialog.hide();
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuIncaricoArbitrale(id);
		}

	}
}


function cercaIncaricoArbitrale(){
	document.getElementById('btnApplicaFiltriArbitrale').click();

}

function caricaAzioniSuIncaricoArbitrale(id) {
	var containerAzioni = document.getElementById("containerAzioniRigaArbitrale" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "incarico/caricaAzioniArbitrale.action?idIncarico=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}


function initTabellaRicercaIncaricoArbitrale() {

	var $table = $('#tabellaRicercaArbitrale').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'incarico/ricercaArbitrale.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniArbitrale,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					var nome = encodeURIComponent(document
							.getElementById("txtNomeCollegioArbitrale").value); 
					var dal = encodeURIComponent(document
							.getElementById("txtDataDalArbitrale").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAlArbitrale").value); 
					var statoCode = document
							.getElementById("statoIncaricoArbitraleCode").value
					var nomeFascicolo = encodeURIComponent( document.getElementById("txtNomeFascicoloArbitrale").value) ;
					params.nomeCollegioArbitrale = nome; 
					params.dal = dal;
					params.al = al;
					params.statoIncaricoArbitraleCode = statoCode;
					params.nomeFascicolo = nomeFascicolo;
					return params;
				},
				columns : [  {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				} ,{
					field : 'nomeCollegioArbitrale',
					title : 'NOME COLLEGIO ARBITRALE',
					align : 'left',
					valign : 'top',
					sortable : true

				}, {
					field : 'nomeIncarico',
					title : 'INCARICO',
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
					align : 'center',
					valign : 'top',
					sortable : true
					
				}, {
					field : 'anno',
					title : 'ANNO',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'dataCreazione',
					title : 'DATA CREAZIONE',
					align : 'center',
					valign : 'top',
					sortable : false
				}]
			});

	$('#btnApplicaFiltriArbitrale').click(function() {
		$table.bootstrapTable('refresh');
	});
}


function eliminaArbitrale(id){
	console.log("elimino collegio con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaIncaricoArbitrale();
	};

	var url = WEBAPP_BASE_URL + "incarico/eliminaArbitrale.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function eliminaIncarico(id){
	console.log("elimino incarico con id: " + id);
	//waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		//waitingDialog.hide();
		cercaIncarico();
	};

	var url = WEBAPP_BASE_URL + "incarico/eliminaIncarico.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

//DARIO ***********************************************************
//function avviaWorkFlowIncarico(id){
function avviaWorkFlowIncarico(id, matricola_dest){
	//DARIO ***********************************************************
	//console.log("avvio workflow incarico con id: " + id);
	console.log("avviaWorkFlowIncarico(" + id + "," + matricola_dest+ ")");
	//*****************************************************************
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaIncarico();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	
	//DARIO***********************************************************************
	//var url = WEBAPP_BASE_URL + "incarico/avviaWorkFlowIncarico.action?id=" + id;
	var url = WEBAPP_BASE_URL + "incarico/avviaWorkFlowIncarico.action?id=" + id+"&matricola_dest="+matricola_dest;
	//****************************************************************************
	
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

//DARIO ***********************************************************
//function avviaWorkFlowIncaricoDaForm(id){	
function avviaWorkFlowIncaricoDaForm(id, matricola_dest){	
//*****************************************************************	
	//DARIO ***********************************************************
	//console.log("avvio workflow incarico con id: " + id);
	console.log("avviaWorkFlowIncaricoDaForm(" + id + "," + matricola_dest+ ")");
	//*****************************************************************
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		$("#btnAvviaWorkFlowIncaricoForm").hide();
		cercaIncarico();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	//DARIO***********************************************************************
	//var url = WEBAPP_BASE_URL + "incarico/avviaWorkFlowIncarico.action?id=" + id;
	var url = WEBAPP_BASE_URL + "incarico/avviaWorkFlowIncarico.action?id=" + id +"&matricola_dest="+matricola_dest;
	//****************************************************************************
	
	//+"&flagAssegnatari="+flagAssegnatari
	
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

//DARIO ***********************************************************
//function avviaWorkFlowIncaricoArbitrale(id){
function avviaWorkFlowIncaricoArbitrale(id, matricola_dest){
//*****************************************************************	
	//DARIO ***********************************************************
	//console.log("avvio workflow incarico arbitrale con id: " + id);
	console.log("avviaWorkFlowIncaricoArbitrale(" + id + "," + matricola_dest+ ")");
	//*****************************************************************
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaIncaricoArbitrale();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	
	
	//DARIO***********************************************************************
	//var url = WEBAPP_BASE_URL + "incarico/avviaWorkFlowIncaricoArbitrale.action?id=" + id;
	var url = WEBAPP_BASE_URL + "incarico/avviaWorkFlowIncaricoArbitrale.action?id=" + id+"&matricola_dest="+matricola_dest;
	//****************************************************************************
	
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
}

function riportaInBozzaWorkFlowIncaricoArbitrale(id){
	console.log("riporto in bozza workflow incarico arbitrale con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaIncaricoArbitrale();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "incarico/riportaInBozzaWorkFlowIncaricoArbitrale.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
}


function riportaInBozzaWorkFlowIncarico(id){
	console.log("riporto in bozza workflow incarico con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaIncarico();
	};

	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "incarico/riportaInBozzaWorkFlowIncarico.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null,  callBackFnErr);
}

function arretraWorkFlowIncaricoArbitrale(id){
	console.log("arretra workflow arbitrale con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaIncaricoArbitrale();
	};

	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "incarico/arretraWorkFlowIncaricoArbitrale.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn,null,  callBackFnErr);
}


function arretraWorkFlowIncarico(id){
	console.log("arretra workflow incarico con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaIncarico();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "incarico/arretraWorkFlowIncarico.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn,null,  callBackFnErr);
}



$(document).on('click', '#addButton', function() {
	addAcconto();
});

$(document).on('click', '#addButtonB', function() {
	addBonus();
});



//Remove button click handler
$(document).on('click', '#removeButton', function() {

	if(bookIndex!=0){

		var $row  = $(document).find('[importi-index='+ bookIndex + ']');

		$row.remove();
		
		var myDate = $( "input[name*='acconto["+ bookIndex +"].anno']" ).val();
		
		$('#saldoAnno').val(myDate);

		bookIndex--;
		
		updateSaldo();
	}
});

function addAcconto(){
	bookIndex++;
	var $template = $('#importiTemplate'),
	$clone    = $template
	.clone()
	.removeClass('hide')
	.removeAttr('id')
	.attr('importi-index', bookIndex)
	.insertBefore($template);
	
	var myDate = $( "input[name*='acconto[0].anno']" ).val();
	myDate=parseInt(myDate) + bookIndex;
	
	$('#saldoAnno').val(myDate+1);
	
	// Update the name attributes
	$clone
	.find('#impI').attr('name', 'acconto[' + bookIndex + '].importo').end()
	.find('#annI').val(myDate).end()
	.find('#annI').attr('name', 'acconto[' + bookIndex + '].anno').end()
}

function addBonus(){
	bonusIndex++;
	var $template = $('#bonusTemplate'),
	$clone    = $template
	.clone()
	.removeClass('hide')
	.removeAttr('id')
	.attr('bonus-index', bonusIndex)
	.insertBefore($template);
	
	// Update the name attributes
	$clone
	.find('#impB').attr('name', 'bonus[' + bonusIndex + '].importo').end()
	.find('#descB').attr('name', 'bonus[' + bonusIndex + '].descrizione').end()
}

function updateSaldo(){
	var compensoTotale = parseFloat($("#compenso").val());
	var bonus = 0.00; 
	var importi  = 0.00;
	var saldoImporto = 0.00;

//	$('.bonus').each(function() {
//		bonus = Math.max(parseFloat($(this).val()), bonus);
//	});
	
	$('.importo').each(function() {
		importi += parseFloat($(this).val());
	});
	
//	saldoImporto = (compensoTotale + bonus)- importi;
	
	saldoImporto = compensoTotale - importi;
	
	$('.saldo').val(saldoImporto.toFixed(2)); 
	
	if(saldoImporto<0)
		$('.saldo').css("color", "red");
		else
		$('.saldo').css("color", "black");
	
}

//Remove button click handler
$(document).on('click', '#removeButtonB', function() {
	var $row  = $(this).parents('.bonusdiv'),
	index = $row.attr('bonus-index');

	// Remove element containing the fields
	$row.remove();
	
	bonusIndex--;
	
	updateSaldo();
	
	aggiornaValoreIncarico();
});



$(document).on('change','.bonus',function(){
	if($(this).val()=='')
		$(this).val(0);
	updateSaldo();
	aggiornaValoreIncarico();
});

$(document).on('change','.descrizione',function(){
	aggiornaValoreIncarico();
});

$(document).on('change','.importo',function(){
	if($(this).val()=='')
		$(this).val(0);
	updateSaldo();
});

$(document).on('change','#compenso',function(){
	updateSaldo();
	aggiornaValoreIncarico();
});


function addQuadro(){
	
	if($('#quadro').is(':visible')){
		$('#quadro').hide();
	}
	else{
		$('#quadro').show();
	}
}

function addQuadro1(){
	$('#quadro1').show();
}

function removeQuadro1(){
	if($('#quadro1').is(':visible')){
		$('#quadro1').hide();
	}
}



function richiediProforma(id){
	location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL + "incarico/richiediProforma.action?id="+id);
}


function aggiungiFileId(){
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/exportFileFromFilenet.action";
	url=legalSecurity.verifyToken(url);
	downloadFileXLS(url);
}

function downloadFileXLS(urlToSend) {
	
    var req = new XMLHttpRequest();
    req.open("GET", urlToSend, true);
    req.responseType = "document";
    req.onload = function (event) {
        var link=document.createElement('a');
        link.href=urlToSend;
        link.target="_blank";
        link.click();
        waitingDialog.hide();
    };
    req.send();
}

function aggiungiIncarichi(){
	var file = $('#fileIncarichi')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "incarico/changeClassFileOnFilenet.action";
	 
	var fnCallBackSuccess = function(data){ 
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};
	var fnCallBackError = function(){
		waitingDialog.hide();
	};
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
}




