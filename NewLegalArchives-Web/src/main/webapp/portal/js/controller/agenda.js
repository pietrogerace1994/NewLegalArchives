var g_arrAgendaFascicoli = null;
var g_arrAgendaListaTipoScadenza = null;
var g_selectTipoScadenzaValNessuno = "0";
var g_agendaTipologia=null;
var g_modalAgendaSuccess=false;
var primaDopo = "1";

$(document).ready(function () {
	loadListaTipoScadenza();
	initCalendar();
		
	$("#agendaEventoDelayAvviso, #agendaScandenzaDelayAvviso, #agendaEventoFrequenzaAvviso, #agendaScandenzaFrequenzaAvviso").keypress(function (e) {
		if ($.inArray(e.keyCode, [49,50,51,52,53,54,55,56,57,48]) == -1 )
			e.preventDefault();
	});
	
	
	$("#agendaScandenzaDataInserimento, #agendaScandenzaTempoAdisposizione").focusout(function(event) {	
				
		var agendaScandenzaDataInserimentoVal=$("#agendaScandenzaDataInserimento").val();
		var agendaScandenzaTempoAdisposizioneVal=$("#agendaScandenzaTempoAdisposizione").val();
		
		if(agendaScandenzaDataInserimentoVal != "" && agendaScandenzaTempoAdisposizioneVal != "") {
			
			calcolaDataScadenzaAsync(agendaScandenzaDataInserimentoVal,agendaScandenzaTempoAdisposizioneVal,primaDopo);
		} 
		
	});
	
	$("#btnAgenda").click(function(event){	
		event.stopPropagation();
		
		pulisciSuccessModalAgenda();
		pulisciCampiModalAgenda();
		pulisciErrors();
		apriModalAgenda();
	});
	
	$("#btnAgendaClose").click(function(event){	
		event.stopPropagation();
		if(g_modalAgendaSuccess) {
			$('#cw-body').fullCalendar( 'refetchEvents' );
		}
		pulisciCampiModalAgenda();
		chiudiModalAgenda();
	});
	
	
	
	$('#modalAgenda').on('hide.bs.modal', function (e) {
		e.stopPropagation();
		if(g_modalAgendaSuccess) {
			$('#cw-body').fullCalendar( 'refetchEvents' );
		}
		pulisciCampiModalAgenda();
		//chiudiModalAgenda();
	});

	$('#agendaTipologia').on('change', function() {
		  var sel=this.value;
		  g_agendaTipologia=sel;
		  
		  if(sel==0) {
			  eventoFieldsOff();
			  scadenzaFieldsOff();
		  }
		  else if(sel==1) {
			  eventoFieldsOn();
			  scadenzaFieldsOff();
		  }
		  else if(sel==2) {
			  eventoFieldsOff();
			  scadenzaFieldsOn();
		  }
	
	});
	
	
	$("#btnAgendaAdd").click(function(event){	
		event.stopPropagation();
		
		//fascicolo
		var agendaFascicoliId = $("#agendaFascicoliId").val();
		
		//tipologia
		var agendaTipologiaEle = document.getElementById("agendaTipologia");
		var agendaTipologiaSelIndex = agendaTipologiaEle.selectedIndex;
		var agendaTipologiaLbl;
		var agendaTipologiaVal;
		if(agendaTipologiaSelIndex>=0) {
			agendaTipologiaLbl = agendaTipologiaEle[agendaTipologiaSelIndex].label;
			agendaTipologiaVal = agendaTipologiaEle[agendaTipologiaSelIndex].value;
		}
		
		var hasErrors=false;
		if(agendaFascicoliId=="") {
			hasErrors=true;
			$("#errorMsgAgendaFascicolo").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaFascicolo").hasClass("hidden")) {
				$("#errorMsgAgendaFascicolo").addClass("hidden"); 
			}
		}
		if(g_selectTipoScadenzaValNessuno==agendaTipologiaVal || agendaTipologiaSelIndex==-1) {
			hasErrors=true;
			$("#errorMsgAgendaTipologia").removeClass("hidden");
		} 
		else {
			if(!$("#errorMsgAgendaTipologia").hasClass("hidden")) {
				$("#errorMsgAgendaTipologia").addClass("hidden"); 
			}
		}
		var agendaEventoData=$("#agendaEventoData").val();
		if(agendaEventoData=="" && g_agendaTipologia==1) {
			hasErrors=true;
			$("#errorMsgAgendaEventoData").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaEventoData").hasClass("hidden")) {
				$("#errorMsgAgendaEventoData").addClass("hidden"); 
			}
		}
		var agendaEventoOggetto=$("#agendaEventoOggetto").val();
		if(agendaEventoOggetto=="" && g_agendaTipologia==1) {
			hasErrors=true;
			$("#errorMsgAgendaEventoOggetto").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaEventoOggetto").hasClass("hidden")) {
				$("#errorMsgAgendaEventoOggetto").addClass("hidden"); 
			}
		}
		var agendaEventoDescrizione=$("#agendaEventoDescrizione").val();
		if(agendaEventoDescrizione=="" && g_agendaTipologia==1) {
			hasErrors=true;
			$("#errorMsgAgendaEventoDescrizione").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaEventoDescrizione").hasClass("hidden")) {
				$("#errorMsgAgendaEventoDescrizione").addClass("hidden"); 
			}
		}
		var agendaEventoDelayAvviso=$("#agendaEventoDelayAvviso").val();
		if(agendaEventoDelayAvviso=="" && g_agendaTipologia==1) {
			hasErrors=true;
			$("#errorMsgAgendaEventoDelayAvviso").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaEventoDelayAvviso").hasClass("hidden")) {
				$("#errorMsgAgendaEventoDelayAvviso").addClass("hidden"); 
			}
		}
		var agendaEventoFrequenzaAvviso=$("#agendaEventoFrequenzaAvviso").val();
		if(agendaEventoFrequenzaAvviso==null)  agendaEventoFrequenzaAvviso="0";
		if(agendaEventoFrequenzaAvviso=="0" && g_agendaTipologia==1) {
			hasErrors=true;
			$("#errorMsgAgendaEventoFrequenzaAvviso").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaEventoFrequenzaAvviso").hasClass("hidden")) {
				$("#errorMsgAgendaEventoFrequenzaAvviso").addClass("hidden"); 
			}
		}
		var agendaScandenzaDataInserimento=$("#agendaScandenzaDataInserimento").val();
		if(agendaScandenzaDataInserimento=="" && g_agendaTipologia==2) {
			hasErrors=true;
			$("#errorMsgAgendaScandenzaDataInserimento").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaScandenzaDataInserimento").hasClass("hidden")) {
				$("#errorMsgAgendaScandenzaDataInserimento").addClass("hidden"); 
			}
		}
		var agendaScandenzaOggetto=$("#agendaScandenzaOggetto").val();
		if($("#agendaScandenzaOggetto").val()=="" && g_agendaTipologia==2) {
			hasErrors=true;
			$("#errorMsgAgendaScandenzaOggetto").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaScandenzaOggetto").hasClass("hidden")) {
				$("#errorMsgAgendaScandenzaOggetto").addClass("hidden"); 
			}
		}
		var agendaScandenzaDescrizione=$("#agendaScandenzaDescrizione").val();
		if(agendaScandenzaDescrizione=="" && g_agendaTipologia==2) {
			hasErrors=true;
			$("#errorMsgAgendaScandenzaDescrizione").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaScandenzaDescrizione").hasClass("hidden")) {
				$("#errorMsgAgendaScandenzaDescrizione").addClass("hidden"); 
			}
		}
		var agendaScandenzaTempoAdisposizione=$("#agendaScandenzaTempoAdisposizione").val();
		if(agendaScandenzaTempoAdisposizione=="" && g_agendaTipologia==2) {
			hasErrors=true;
			$("#errorMsgAgendaScandenzaTempoAdisposizione").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaScandenzaTempoAdisposizione").hasClass("hidden")) {
				$("#errorMsgAgendaScandenzaTempoAdisposizione").addClass("hidden"); 
			}
		}
		var agendaScandenzaTipo=$("#agendaScandenzaTipo").val();
		if(agendaScandenzaTipo=="" && g_agendaTipologia==2) {
			hasErrors=true;
			$("#errorMsgAgendaScandenzaTipo").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaScandenzaTipo").hasClass("hidden")) {
				$("#errorMsgAgendaScandenzaTipo").addClass("hidden"); 
			}
		}
		var agendaScandenzaDelayAvviso=$("#agendaScandenzaDelayAvviso").val();
		if(agendaScandenzaDelayAvviso=="" && g_agendaTipologia==2) {
			hasErrors=true;
			$("#errorMsgAgendaScandenzaDelayAvviso").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaScandenzaDelayAvviso").hasClass("hidden")) {
				$("#errorMsgAgendaScandenzaDelayAvviso").addClass("hidden"); 
			}
		}
		var agendaScandenzaFrequenzaAvviso=$("#agendaScandenzaFrequenzaAvviso").val();
		if(agendaScandenzaFrequenzaAvviso==null)  agendaScandenzaFrequenzaAvviso="0";
		if(agendaScandenzaFrequenzaAvviso=="0" && g_agendaTipologia==2) {
			hasErrors=true;
			$("#errorMsgAgendaScandenzaFrequenzaAvviso").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaScandenzaFrequenzaAvviso").hasClass("hidden")) {
				$("#errorMsgAgendaScandenzaFrequenzaAvviso").addClass("hidden"); 
			}
		}
		var agendaScandenzaDataScandenza=$("#agendaScandenzaDataScandenza").val();
		if(agendaScandenzaDataScandenza=="" && g_agendaTipologia==2) {
			hasErrors=true;
			$("#errorMsgAgendaScandenzaDataScandenza").removeClass("hidden");
		}
		else {
			if(!$("#errorMsgAgendaScandenzaDataScandenza").hasClass("hidden")) {
				$("#errorMsgAgendaScandenzaDataScandenza").addClass("hidden"); 
			}
		}
		if(hasErrors) {	
			$("#errorMsgAgendaDiv").removeClass("hidden");
			 $("#modalAgenda").animate({ scrollTop: 0 }, "slow");
			return;
		}
		
		if(agendaTipologiaVal=="1") {
			salvaEvento();
		}
		else if(agendaTipologiaVal=="2") {
			salvaScadenza();
		}
		
	});
	
	
	
	
});

function aggiungiEventoScadenza(id, nome){
	event.stopPropagation();
	
	pulisciSuccessModalAgenda();
	pulisciCampiModalAgenda2(id, nome);
	pulisciErrors();
	apriModalAgenda2(id);
}

function apriModalAgenda() {
	console.log("apriModalAgenda()");
	popolaSelectFascicoli();
	popolaSelectTipoScadenza();
	
	$("#agendaFascicoliId").val('');
	
	var agendaScandenzaTipoEle = document.getElementById("agendaTipologia");
	agendaScandenzaTipoEle.selectedIndex=-1;
	
	$('#agendaFascicoliNome').removeAttr("disabled");
	$('#btnAgendaScegliFascicolo').removeClass();
	$('#btnAgendaScegliFascicolo').addClass("btn btn-primary waves-effect");
	$('#btnAgendaScegliFascicoloIcon').removeClass();
	$('#btnAgendaScegliFascicoloIcon').addClass("fa fa-search");
	
	$('#modalAgenda').modal('show');
}

function apriModalAgenda2(id) {
	console.log("apriModalAgenda()");
	popolaSelectFascicoli();
	popolaSelectTipoScadenza();
	
	$("#agendaFascicoliId").val(id);
	
	var agendaScandenzaTipoEle = document.getElementById("agendaTipologia");
	agendaScandenzaTipoEle.selectedIndex=-1;
	
	$('#agendaFascicoliNome').attr("disabled","disabled");
	$('#btnAgendaScegliFascicolo').removeClass();
	$('#btnAgendaScegliFascicolo').addClass("btn btn-success");
	$('#btnAgendaScegliFascicoloIcon').removeClass();
	$('#btnAgendaScegliFascicoloIcon').addClass("fa fa-check ");
	
	$('#modalAgenda').modal('show');
}

function chiudiModalAgenda() {
	console.log("chiudiModalAgenda()");
	$('#modalAgenda').modal('hide');
}

function loadListaTipoScadenza() {
	var callBackFn = function(data, stato) {
		g_arrAgendaListaTipoScadenza = JSON.parse(data.jsonArrayListaTipoScadenza);  
	};
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "agenda/loadListaTipoScadenza.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, null, "get", "text/html", callBackFn, null);
}


function eventoFieldsOn() {
	$("#agendaEventoDataDiv").css("display", "block");
	$("#agendaEventoOggettoDiv").css("display", "block");
	$("#agendaEventoDescrizioneDiv").css("display", "block");
	$("#agendaEventoDelayAvvisoDiv").css("display", "block");
	$("#agendaEventoFrequenzaAvvisoDiv").css("display", "block");
	pulisciCampiEvento();
}

function eventoFieldsOff() {
	$("#agendaEventoDataDiv").css("display", "none");
	$("#agendaEventoOggettoDiv").css("display", "none");
	$("#agendaEventoDescrizioneDiv").css("display", "none");
	$("#agendaEventoDelayAvvisoDiv").css("display", "none");
	$("#agendaEventoFrequenzaAvvisoDiv").css("display", "none");
}

function scadenzaFieldsOn() {
	
	$("#agendaScandenzaDataInserimentoDiv").css("display", "block");
	$("#agendaScandenzaTempoAdisposizioneDiv").css("display", "block");
	$("#agendaScandenzaTempoAdisposizionePrimaDopoDiv").css("display", "block");
	$("#agendaScandenzaTipoDiv").css("display", "block");
	$("#agendaScandenzaOggettoDiv").css("display", "block");
	$("#agendaScandenzaDescrizioneDiv").css("display", "block");
	$("#agendaScandenzaDelayAvvisoDiv").css("display", "block");
	$("#agendaScandenzaFrequenzaAvvisoDiv").css("display", "block");
	$("#agendaScandenzaDataScandenzaDiv").css("display", "block");
	$("#agendaScandenzaDataScandenzaModificaDiv").css("display", "block");
	pulisciCampiScadenza();
}

function scadenzaFieldsOff() {
	
	$("#agendaScandenzaDataInserimentoDiv").css("display", "none");
	$("#agendaScandenzaTempoAdisposizioneDiv").css("display", "none");
	$("#agendaScandenzaTempoAdisposizionePrimaDopoDiv").css("display", "none");
	$("#agendaScandenzaTipoDiv").css("display", "none");
	$("#agendaScandenzaOggettoDiv").css("display", "none");
	$("#agendaScandenzaDescrizioneDiv").css("display", "none");
	$("#agendaScandenzaDelayAvvisoDiv").css("display", "none");
	$("#agendaScandenzaFrequenzaAvvisoDiv").css("display", "none");
	$("#agendaScandenzaDataScandenzaDiv").css("display", "none");
	$("#agendaScandenzaDataScandenzaModificaDiv").css("display", "none");
}

function pulisciCampiModalAgenda() {
	
	$("#agendaFascicoliNome").val('');
	$("#agendaFascicoliId").val('');
	
	eventoFieldsOff();
	scadenzaFieldsOff();
	
	pulisciCampiScadenza();
	pulisciCampiEvento();
}

function pulisciCampiModalAgenda2(id, nome) {
	
	$("#agendaFascicoliNome").val(nome);
	$("#agendaFascicoliId").val(id);
	
	eventoFieldsOff();
	scadenzaFieldsOff();
	
	pulisciCampiScadenza();
	pulisciCampiEvento();
}

function pulisciCampiScadenza() {
	$("#agendaScandenzaDataInserimento").val("");
	$("#agendaScandenzaTempoAdisposizione").val("");
	$("#agendaScandenzaTipo").val("");
	$("#agendaScandenzaOggetto").val("");
	$("#agendaScandenzaDescrizione").val("");
	$("#agendaScandenzaDelayAvviso").val("");
	$("#agendaScandenzaFrequenzaAvviso").val("");
	$("#agendaScandenzaDataScandenza").val("");
	$('input[name=primadopo]').attr('checked',false);
	$("#agendaScandenzaDataScandenzaModifica").attr("checked", false);
	$('#agendaScandenzaDataScandenza').attr("readonly","readonly");
	
}

function pulisciCampiEvento() {
	$("#agendaEventoData").val("");
	$("#agendaEventoOggetto").val("");
	$("#agendaEventoDescrizione").val("");
	$("#agendaEventoDelayAvviso").val("");
	$("#agendaEventoFrequenzaAvviso").val("");
}

function salvaEvento() {
	var idFascicolo=$("#agendaFascicoliId").val();
	
	var agendaData=$('#agendaEventoData').val();
	var agendaOggetto=$('#agendaEventoOggetto').val();
	var agendaDescrizione=$('#agendaEventoDescrizione').val();
	var agendaDelayAvviso=$('#agendaEventoDelayAvviso').val();
	var agendaFrequenzaAvviso=$('#agendaEventoFrequenzaAvviso').val();
	
	if(agendaFrequenzaAvviso=="1")
		agendaFrequenzaAvviso="6";
	if(agendaFrequenzaAvviso=="2")
		agendaFrequenzaAvviso="12";
	if(agendaFrequenzaAvviso=="3")
		agendaFrequenzaAvviso="24";
	
	//salva 
	var callBackFn = function(data, stato) {
		$.ajaxSetup({
	    	async: true
		});
		
		mostraSuccessModalAgenda();
	};
    $.ajaxSetup({
    	async: false
	});
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "agenda/salvaEvento.action";
	var params = { 
			idfascicolo: idFascicolo, 
			data: agendaData,
			oggetto: agendaOggetto,
			descrizione: agendaDescrizione,
			delayAvviso: agendaDelayAvviso,
			frequenzaAvviso: agendaFrequenzaAvviso,
			CSRFToken:legalSecurity.getToken()
	}; 
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null);
	
}

function salvaScadenza() {
	var idFascicolo=$("#agendaFascicoliId").val();
	
	var agendaScandenzaDataInserimento=$("#agendaScandenzaDataInserimento").val();
	var agendaScandenzaTempoAdisposizione=$("#agendaScandenzaTempoAdisposizione").val();
	
	
	var agendaScandenzaTipoEle = document.getElementById("agendaScandenzaTipo");
	var agendaScandenzaTipoSelIndex = agendaScandenzaTipoEle.selectedIndex;
	var agendaScandenzaTipoLbl;
	var agendaScandenzaTipoVal;
	if(agendaScandenzaTipoSelIndex>=0) {
		agendaScandenzaTipoLbl = agendaScandenzaTipoEle[agendaScandenzaTipoSelIndex].label;
		agendaScandenzaTipoVal = agendaScandenzaTipoEle[agendaScandenzaTipoSelIndex].value;
	}
	var idTipoScadenza=null;
	if(agendaScandenzaTipoVal>=0)
		idTipoScadenza=g_arrAgendaListaTipoScadenza[agendaScandenzaTipoVal].id;
	
	
	var agendaScandenzaOggetto=$("#agendaScandenzaOggetto").val();
	var agendaScandenzaDescrizione=$("#agendaScandenzaDescrizione").val();
	var agendaScandenzaDelayAvviso=$("#agendaScandenzaDelayAvviso").val();
	var agendaScandenzaFrequenzaAvviso=$("#agendaScandenzaFrequenzaAvviso").val();
	
	if(agendaScandenzaFrequenzaAvviso=="1")
		agendaScandenzaFrequenzaAvviso="6";
	if(agendaScandenzaFrequenzaAvviso=="2")
		agendaScandenzaFrequenzaAvviso="12";
	if(agendaScandenzaFrequenzaAvviso=="3")
		agendaScandenzaFrequenzaAvviso="24";
	
	var agendaScandenzaDataScandenza=$("#agendaScandenzaDataScandenza").val();
	
	//salva 
	var callBackFn = function(data, stato) {
		$.ajaxSetup({
	    	async: true
		});
		
		mostraSuccessModalAgenda();
		
	};
    $.ajaxSetup({
    	async: false
	});
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "agenda/salvaScadenza.action";
	var params = { 
			idfascicolo: idFascicolo, 
			datainserimento: agendaScandenzaDataInserimento,
			tempoadisposizione: agendaScandenzaTempoAdisposizione,
			tipo: idTipoScadenza,
			oggetto: agendaScandenzaOggetto,
			descrizione: agendaScandenzaDescrizione,
			delayavviso: agendaScandenzaDelayAvviso,
			frequenzaavviso: agendaScandenzaFrequenzaAvviso,
			datascandenza: agendaScandenzaDataScandenza,
			CSRFToken:legalSecurity.getToken()
	}; 
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null);
}

function popolaSelectFascicoli() { 
	g_selectTipoScadenzaValNessuno="-1";
}

function popolaSelectTipoScadenza() { 
	var str = '<option value="-1"></option>';
	for(var i=0; i<g_arrAgendaListaTipoScadenza.length; i++) {
		var obj=g_arrAgendaListaTipoScadenza[i];
		var id=obj["id"];
		var nome=obj["nome"];
		var codGruppoLingua=obj["codGruppoLingua"];
		str += '<option value="'+i+'">'+nome+'</option>';
	}
	$("#agendaScandenzaTipo").html('');
	$("#agendaScandenzaTipo").html(str);
	
	selectTipoScadenzaValNessuno="0";
}
function mostraSuccessModalAgenda() { 
	$("#errorMsgAgendaSuccess").removeClass("hidden");
	$("#agendaForm").addClass("hidden");
	$("#btnAgendaAdd").addClass("hidden");
	g_modalAgendaSuccess=true;
}
function pulisciSuccessModalAgenda() { 
	$("#errorMsgAgendaSuccess").addClass("hidden");
	$("#agendaForm").removeClass("hidden");
	$("#btnAgendaAdd").removeClass("hidden");
	g_modalAgendaSuccess=false;
}
function pulisciErrors() {
	if(!$("#errorMsgAgendaTipologia").hasClass("hidden"))
		$("#errorMsgAgendaTipologia").addClass("hidden");
	if(!$("#errorMsgAgendaFascicolo").hasClass("hidden"))
		$("#errorMsgAgendaFascicolo").addClass("hidden");
	if(!$("#errorMsgAgendaDiv").hasClass("hidden"))
		$("#errorMsgAgendaDiv").addClass("hidden");
}

function calcolaDataScadenzaAsync(agendaScandenzaDataInserimentoVal,agendaScandenzaTempoAdisposizioneVal, primaDopo) {
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		$("#agendaScandenzaDataScandenza").val(data.dataScandenzaStr);
	};
	
	var url = WEBAPP_BASE_URL + "agenda/calcolaDataScadenza.action?dataInserimento="+agendaScandenzaDataInserimentoVal+"&tempoAdisposizione=" + agendaScandenzaTempoAdisposizioneVal + "&when=" + primaDopo;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "application/x-www-form-urlencoded", callBackFn, null);
}

function selectRadioPrimaDopo(value){
	//DOPO
	if(value == "1"){
		var agendaScandenzaDataInserimentoVal=$("#agendaScandenzaDataInserimento").val();
		var agendaScandenzaTempoAdisposizioneVal=$("#agendaScandenzaTempoAdisposizione").val();
		primaDopo = "1";
		
		if(agendaScandenzaDataInserimentoVal != "" && agendaScandenzaTempoAdisposizioneVal != "") {
			calcolaDataScadenzaAsync(agendaScandenzaDataInserimentoVal,agendaScandenzaTempoAdisposizioneVal,primaDopo);
		} 
	}
	//PRIMA
	else {
		var agendaScandenzaDataInserimentoVal=$("#agendaScandenzaDataInserimento").val();
		var agendaScandenzaTempoAdisposizioneVal=$("#agendaScandenzaTempoAdisposizione").val();
		primaDopo = "0";
		
		if(agendaScandenzaDataInserimentoVal != "" && agendaScandenzaTempoAdisposizioneVal != "") {
			calcolaDataScadenzaAsync(agendaScandenzaDataInserimentoVal,agendaScandenzaTempoAdisposizioneVal,primaDopo);
		} 
	}
}

function modificaDataScadenza(){
	 cbObj = document.getElementById("agendaScandenzaDataScandenzaModifica");
     if (cbObj.checked)
    	 $('#agendaScandenzaDataScandenza').removeAttr("readonly");
     else
    	 $('#agendaScandenzaDataScandenza').attr("readonly","readonly");
}
