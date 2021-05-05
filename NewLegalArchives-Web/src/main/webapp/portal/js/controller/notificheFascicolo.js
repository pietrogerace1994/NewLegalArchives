var g_fascicoli_avanzamento=null;
var g_agenda_badgeNotificheNumero=0;


$(document).ready(function () {
	
	$("#linkFascicolo").click(function(event){
		$( "#avanzamentoFascicoli" ).trigger( "click" );
	})
	
	fascicolo_loadAvanzamentoFascicolo();

	$("#avanzamentoFascicoli").click(function(event){	
		console.log("sono in gestioneAvanzamentoFascicolo");
		
		
		pulisciAvanzamentoFascicoloContainer();

		
		//popola
		for(var i=0; i<g_fascicoli_avanzamento.total; i++) {
			var obj=g_fascicoli_avanzamento.rows[i];
			
			var container = document.getElementById("containerAvanzamentoFascicolo");
			var url = WEBAPP_BASE_URL + "fascicolo/cronologia.action?id="+obj.id;
			url=legalSecurity.verifyToken(url);
			var a="<a href=\""+url+"\" id=\"open-fascicolo\" class=\"\">";
				a = a + "<div class=\"list-group-item\">";
				a = a + "<div class=\"lgi-heading m-b-5\">";
				a = a +  obj.nomeFascicolo;
				a = a  + "</div>";
				a = a + "<div class=\"progress\">";

				
				if(obj.stato == "red")
				{
					a = a + "<div class=\"progress-bar progress-bar-danger\" role=\"progressbar\"";
					a = a + "aria-valuenow=\"25\" aria-valuemin=\"0\" aria-valuemax=\"100\"";
					a = a + "style=\"width: 25%\">";
					a = a + "<span class=\"sr-only\">25% Complete (danger)</span>";
					a = a + "</div>";
				}
				else if(obj.stato == "orange")
				{
					a = a + "<div class=\"progress-bar progress-bar-warning\" role=\"progressbar\"";
					a = a + "aria-valuenow=\"35\" aria-valuemin=\"0\" aria-valuemax=\"100\"";
					a = a + "style=\"width: 35%\">";
					a = a + "<span class=\"sr-only\">35% Complete (warning)</span>";
					a = a + "</div>";
				}
				else if(obj.stato == "yellow")
				{
					a = a + "<div class=\"progress-bar progress-bar-warning2\" role=\"progressbar\"";
					a = a + "aria-valuenow=\"50\" aria-valuemin=\"0\" aria-valuemax=\"100\"";
					a = a + "style=\"width: 50%\">";
					a = a + "<span class=\"sr-only\">50% Complete (warning2)</span>";
					a = a + "</div>";
				}
				else if(obj.stato == "blue")
				{
					a = a + "<div class=\"progress-bar progress-bar-info\" role=\"progressbar\"";
					a = a + "aria-valuenow=\"65\" aria-valuemin=\"0\" aria-valuemax=\"100\"";
					a = a + "style=\"width: 65%\">";
					a = a + "<span class=\"sr-only\">65% Complete (info)</span>";
					a = a + "</div>";
				}
				else if(obj.stato == "green")
				{
					a = a + "<div class=\"progress-bar progress-bar-success\" role=\"progressbar\"";
					a = a + "aria-valuenow=\"80\" aria-valuemin=\"0\" aria-valuemax=\"100\"";
					a = a + "style=\"width: 80%\">";
					a = a + "<span class=\"sr-only\">80% Complete (danger)</span>";
					a = a + "</div>";
				}
				a = a + "</div><small class=\"pull-right\" style=\"font-size: 11px;color: #C5C5C5;margin-left: 11px;\">"+obj.data+"</small>";
				a = a + "</div></a>";
				
				var div = document.createElement('div');
				div.innerHTML = a;
				
				container.insertBefore(div, container.childNodes[0]);
			
			
			
		}
		
	});
	
});

function afterLoadUltimeNotifiche() {
	showBadgeNotificheAgenda();
}

//function nascondiBadgeNotificheAgenda() {
//	$("#badgeNotificheFascicolo").html("");
//}

//function showBadgeNotificheAgenda(){
//	if(g_agenda_badgeNotificheNumero>0) {
//		$("#badgeNotificheFascicolo").html(g_agenda_badgeNotificheNumero);
//	}
//	if(g_agenda_badgeNotificheNumero==0) {
//		$("#badgeNotificheFascicolo").html("");
//	}
//}

//function pulisciAvanzamentoFascicoloContainer() {
//	//var container = document.getElementById("mCSB_3_container");
//	$("#mCSB_3_container").html("");
//}

function pulisciAvanzamentoFascicoloContainer() {
	var container = document.getElementById("containerAvanzamentoFascicolo");
	while (container.firstChild) {
		container.removeChild(container.firstChild);
	}
}


function fascicolo_loadAvanzamentoFascicolo() {
	var callBackFn = function(data, stato) {
		
		console.log(data);
		g_fascicoli_avanzamento = data;
		
		//afterLoadUltimeNotifiche();
	};
	var ajaxUtil = new AjaxUtil();
	var url = WEBAPP_BASE_URL + "rest/fascicolo/getFascicoliPerAvanzamento.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, null, "get", "application/json", callBackFn, null);
}


function chiudiSidebar() {
	/* Close Sidebar */
    $('[data-ma-action="sidebar-open"]').removeClass('toggled');
    $('.sidebar').removeClass('toggled');
    $('.sidebar-backdrop').remove();
    $('body').removeClass('o-hidden');   
}

//function agenda_apriDettaglioNotifica(idFascicolo,idEvento,idScadenza,tipo, eleThis,indiceArr) {
//	var params = { 
//			idevento: idEvento, 
//			idscadenza: idScadenza,
//			tipo: tipo
//	}; 
//	var callBackFn = function(data, stato) {
//		chiudiSidebar();
//		
//		//remove pallino
//		eleThis.childNodes[0].remove();
//		
//		if(tipo=="E") tipologia="Evento";
//		if(tipo=="S") tipologia="Scadenza";
//		
//		$('#agendaDettaglioTipologia').val(tipologia);
//		$('#agendaDettaglioOggetto').val(g_agenda_ultimeNotifiche[indiceArr].oggetto);
//		$('#agendaDettaglioDescrizione').val(g_agenda_ultimeNotifiche[indiceArr].descrizione);
//		$('#agendaDettaglioFascicolo').val(g_agenda_ultimeNotifiche[indiceArr].fascicoloDescrizione);
//		if(g_agenda_ultimeNotifiche[indiceArr].dataEvento !== undefined) {
//			$('#agendaDettaglioData').val(g_agenda_ultimeNotifiche[indiceArr].dataEvento);
//		}
//		else if(g_agenda_ultimeNotifiche[indiceArr].dataScadenza !== undefined) {
//			$('#agendaDettaglioData').val(g_agenda_ultimeNotifiche[indiceArr].dataScadenza);
//		}
//		
//		$('#modalAgendaDettaglioNotifica').modal('show');
//		
//		//$(eleThis).remove();
//		//g_agenda_ultimeNotifiche.splice( indiceArr, 1 );
//		$(eleThis).removeClass("notifiche-font-bold");
//		g_agenda_badgeNotificheNumero--;
//		g_agenda_ultimeNotifiche[indiceArr].displayed="Y";
//		
//		showBadgeNotificheAgenda();
//		
//	};
//	var ajaxUtil = new AjaxUtil();
//	var url = WEBAPP_BASE_URL + "agenda/setNotificaLetta.action";
//	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null);
//}