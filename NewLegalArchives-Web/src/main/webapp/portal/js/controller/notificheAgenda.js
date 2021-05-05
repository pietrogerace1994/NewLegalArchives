var g_agenda_ultimeNotifiche=null;
var g_agenda_badgeNotificheNumero=0;
//var g_dettaglioNotifica_arr=new Array();
$(document).ready(function () {
	
	agenda_loadUltimeNotifiche("default");

	$("#agendaMessaggi").click(function(event){	
		
		pulisciNotificaAgendaContainer();
		
		//popola
		for(var x=0; x<g_agenda_ultimeNotifiche.length; x++) {
			var obj=g_agenda_ultimeNotifiche[x];
			obj.indiceArr = x;
			addNotificaAgenda(obj);
		}
		
	});
	
	//dettaglio
	$("#btnAgendaDettaglioClose").click(function(event){	
		event.stopPropagation();
		$('#modalAgendaDettaglioNotifica').modal('hide');
	});
	
	$("#btnAgendaDettaglioApriFascicolo").click(function(event){	
		event.stopPropagation();
		var idFascicolo=$('#agendaDettaglioFascicoloId').val();
		var url=WEBAPP_BASE_URL+"fascicolo/dettaglio.action?id="+idFascicolo;
		url=legalSecurity.verifyToken(url);
		window.open(url, "_self");
	});
	
	$("#btnAgendaDettaglioCancella").click(function(event){	
		event.stopPropagation();
		var id=$('#agendaDettaglioId').val();
		var tipo=$('#agendaDettaglioTipologiaHidden').val();
		if(tipo=="E") {
			agenda_cancellaEvento(id);
		}
		else if(tipo=="S") {
			agenda_cancellaScadenza(id);
		}
		
	});
	
});

function afterLoadUltimeNotifiche() {
	showBadgeNotificheAgenda();
}

function nascondiBadgeNotificheAgenda() {
	$("#badgeNotificheCampanaAgenda").html("");
}

function showBadgeNotificheAgenda(){
	if(g_agenda_badgeNotificheNumero>0) {
		$("#badgeNotificheCampanaAgenda").html(g_agenda_badgeNotificheNumero);
	}
	if(g_agenda_badgeNotificheNumero==0) {
		$("#badgeNotificheCampanaAgenda").html("");
	}
}

function pulisciNotificaAgendaContainer() {
	var container = document.getElementById("mCSB_1_container");
	while (container.firstChild) {
		container.removeChild(container.firstChild);
	}
}

function agenda_loadUltimeNotifiche(tipologia) {
	var callBackFn = function(data, stato) {
		g_agenda_ultimeNotifiche = data;
		
		//numero
		g_agenda_badgeNotificheNumero=0;
		for(var i=0;i<g_agenda_ultimeNotifiche.length;i++) {
			var obj=g_agenda_ultimeNotifiche[i];
			g_agenda_badgeNotificheNumero++;
		}
		
		afterLoadUltimeNotifiche();
	};
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "agenda/loadUltimeNotifiche.action?type="+tipologia;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, null, "get", "application/json", callBackFn, null);
}


function addNotificaAgenda(obj) {
	
	var tipo="";
	if(obj.dataEvento !== undefined && obj.dataEvento != null) {
		tipo="E";
	}
	if(obj.dataScadenza !== undefined && obj.dataScadenza != null) {
		tipo="S";
	}	
	obj.tipo=tipo;
	
	//pallino
	var pallino = document.createElement("DIV");
	pallino.setAttribute("class","notifiche-pallino");
	
	var a1 = document.createElement("A");
	a1.setAttribute("href","PleaseEnableJavascript.html");
	
	var onclickStr="agenda_apriDettaglioNotifica("+obj.idFascicolo+","+obj.id+","+obj.id+",'"+tipo+"',this,"+obj.indiceArr+"); return false;";
	a1.setAttribute("onclick",onclickStr);
	var a1Class="list-group-item media";
	if(obj.displayed == "N") {
		a1Class+=" notifiche-font-bold";
	}
	if(obj.displayed == "Y") {
		a1Class+="";
	}
	a1.setAttribute("class",a1Class);
	
	var div1 = document.createElement("DIV");
	div1.setAttribute("class","media-body");
	
	var div2 = document.createElement("DIV");
	div2.setAttribute("class","lgi-heading");
	var oggettoNode = document.createTextNode(obj.oggetto);
	div2.appendChild(oggettoNode);
	
	var small1 = document.createElement("SMALL");
	small1.setAttribute("class","pull-right");
	var dataEventoNode=null;
	if(obj.dataEvento!==undefined && obj.dataEvento!=null) {
		dataEventoNode = document.createTextNode(obj.dataEvento);
	}
	else if(obj.dataScadenza!==undefined && obj.dataScadenza!=null) {
		dataEventoNode = document.createTextNode(obj.dataScadenza);
	}
	
	small1.appendChild(dataEventoNode);
	
	var small2 = document.createElement("SMALL");
	small2.setAttribute("class","lgi-text");
	var descrizioneNode = document.createTextNode(obj.descrizione);
	small2.appendChild(descrizioneNode);
	
	//---append---
	
	//pallino
	if(obj.displayed == "N") {
		a1.appendChild(pallino);
	}
	a1.appendChild(div1);
	div1.appendChild(div2);
	div1.appendChild(small2);
	div2.appendChild(small1);
	
	var container = document.getElementById("mCSB_1_container");
	if( container.childNodes.length == 0 ){ 
		container.appendChild(a1);
	}else{
		container.insertBefore(a1, container.childNodes[0]); 
	}
}

function chiudiSidebar() {
	/* Close Sidebar */
    $('[data-ma-action="sidebar-open"]').removeClass('toggled');
    $('.sidebar').removeClass('toggled');
    $('.sidebar-backdrop').remove();
    $('body').removeClass('o-hidden');   
}

function agenda_apriDettaglioNotifica(idFascicolo,idEvento,idScadenza,tipo, eleThis,indiceArr) {
	var params = { 
			idevento: idEvento, 
			idscadenza: idScadenza,
			tipo: tipo
	}; 
	var callBackFn = function(data, stato) {
		chiudiSidebar();
		
		//remove pallino
		eleThis.childNodes[0].remove();
		
		if(tipo=="E") tipologia="Evento";
		if(tipo=="S") tipologia="Scadenza";
		
		$('#agendaDettaglioTipologia').val(tipologia);
		$('#agendaDettaglioOggetto').val(g_agenda_ultimeNotifiche[indiceArr].oggetto);
		$('#agendaDettaglioDescrizione').val(g_agenda_ultimeNotifiche[indiceArr].descrizione);
		$('#agendaDettaglioFascicoloNome').val(g_agenda_ultimeNotifiche[indiceArr].fascicoloNome);
		$('#agendaDettaglioFascicoloId').val(g_agenda_ultimeNotifiche[indiceArr].idFascicolo);
		if(g_agenda_ultimeNotifiche[indiceArr].dataEvento !== undefined) {
			$('#agendaDettaglioData').val(g_agenda_ultimeNotifiche[indiceArr].dataEvento);
		}
		else if(g_agenda_ultimeNotifiche[indiceArr].dataScadenza !== undefined) {
			$('#agendaDettaglioData').val(g_agenda_ultimeNotifiche[indiceArr].dataScadenza);
		}
		
		$('#modalAgendaDettaglioNotifica').modal('show');
		
		//$(eleThis).remove();
		//g_agenda_ultimeNotifiche.splice( indiceArr, 1 );
		$(eleThis).removeClass("notifiche-font-bold");
		// g_agenda_badgeNotificheNumero--;
		g_agenda_ultimeNotifiche[indiceArr].displayed="Y";
		
		// showBadgeNotificheAgenda();
		
	};
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "agenda/setNotificaLetta.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null);
}

function agenda_apriDettaglioEvento(idEvento) {
	var params = null;
	var callBackFn = function(data, stato) {
		$('#agendaDettaglioTipologia').val("Evento");
		$('#agendaDettaglioOggetto').val(data.eventoOggetto);
		$('#agendaDettaglioDescrizione').val(data.eventoDescrizione);
		$('#agendaDettaglioFascicoloNome').val(data.fascicoloNome);
		$('#agendaDettaglioData').val(data.eventoData);
		$('#agendaDettaglioFascicoloId').val(data.idFascicolo);
		$('#agendaDettaglioFascicoloNome').val(data.fascicoloNome);
		$('#agendaDettaglioId').val(idEvento);
		$('#agendaDettaglioTipologiaHidden').val("E");
		$('#modalAgendaDettaglioNotifica').modal('show');
	};
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "agenda/loadEventoById.action?idEvento="+idEvento;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "get", "application/x-www-form-urlencoded", callBackFn, null);
}

function agenda_apriDettaglioScadenza(idScadenza) {
	var params = null;
	var callBackFn = function(data, stato) {
		$('#agendaDettaglioTipologia').val("Scadenza");
		$('#agendaDettaglioOggetto').val(data.scadenzaOggetto);
		$('#agendaDettaglioDescrizione').val(data.scadenzaDescrizione);
		$('#agendaDettaglioFascicoloNome').val(data.fascicoloNome);
		$('#agendaDettaglioData').val(data.scadenzaData);
		$('#agendaDettaglioFascicoloId').val(data.idFascicolo);
		$('#agendaDettaglioFascicoloNome').val(data.fascicoloNome);
		$('#agendaDettaglioId').val(idScadenza);
		$('#agendaDettaglioTipologiaHidden').val("S");
		$('#modalAgendaDettaglioNotifica').modal('show');
	};
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "agenda/loadScadenzaById.action?idScadenza="+idScadenza;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "get", "application/x-www-form-urlencoded", callBackFn, null);
}


function agenda_cancellaEvento(id) {
	var params = { 
			id: id 
	}; 
	var callBackFn = function(data, stato) {
		$.ajaxSetup({
	    	async: true
		});
		$('#modalAgendaDettaglioNotifica').modal('hide');
		$('#cw-body').fullCalendar( 'refetchEvents' );
	};
	$.ajaxSetup({
    	async: false
	});
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "agenda/cancellaEvento.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null);
}
function agenda_cancellaScadenza(id) {
	var params = { 
			id: id 
	}; 
	var callBackFn = function(data, stato) {
		$.ajaxSetup({
	    	async: true
		});
		$('#modalAgendaDettaglioNotifica').modal('hide');
		$('#cw-body').fullCalendar( 'refetchEvents' );
	};
	$.ajaxSetup({
    	async: false
	});
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "agenda/cancellaScadenza.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null);
}