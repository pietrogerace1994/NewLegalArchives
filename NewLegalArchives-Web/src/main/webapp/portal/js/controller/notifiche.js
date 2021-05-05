var EVENT_TYPE_NOTIFICHE = "NOTIFICHE";
var EVENT_TYPE_CALENDARIO = "CALENDARIO"; 
var EVENT_TYPE_NOTIFICHE_INCARICO = "NOTIFICHE_INCARICO";
var EVENT_TYPE_NOTIFICHE_PROFESSIONISTA = "NOTIFICHE_PROFESSIONISTA";
var EVENT_TYPE_NOTIFICHE_PROFORMA = "NOTIFICHE_PROFORMA";
var EVENT_TYPE_NOTIFICHE_ATTO = "NOTIFICHE_ATTO";
var AUTORIZZAZIONE_INCARICO = "AUT_INCARICO";
var AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO = "AUT_PROF_EST";
var AUTORIZZAZIONE_PROFORMA = "AUT_PROFORMA";


$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	//DARIO*********************************************************************************
	
	$('#panelFormWorkflow').on('show.bs.modal',function(e) {
		gestisci_tasto_confirm_workflow($(this), function(resp_code){
			
			avanzaWorkflow(resp_code);
			
		});
	});
	
	$('#panelFormWorkflowAtto').on('show.bs.modal',function(e) {
		gestisci_tasto_confirm_workflow($(this), function(coll_code, flag_resp){
			
			if (coll_code==''){
				
								
				var _function = $('#btnAvanzaWorkflowAtto').data('function');
				
								
				window[_function](coll_code);
				
				
				
			}else{
				
				if (flag_resp=='Y'){
					assegnaIncarico(coll_code);
				}else{
					assegnaIncaricoLegaleInterno(coll_code);
				}
			}
			
		});
	});
	
	
	

	//****************************************************************************************
	
	
});


function initDashboard(userId, hostname){
	var fnCallback = function(messaggio){
		gestioneMessaggiWebSocket(messaggio);
	};
	var webSocketClient = new WebSocketClient(fnCallback, userId ,hostname);
	webSocketClient.connectSocket();
 	//refreshBadgeNotifiche();
	$('#containerAttivitaPendenti').load(WEBAPP_BASE_URL + '/parts/notifichePendenti.jsp' + '#containerAttivitaPendenti');
}

function gestioneMessaggiWebSocket(evento){
	if( evento.data == "connect" ) return;
	var json = JSON.parse(evento.data);
	
	if( json.type == EVENT_TYPE_NOTIFICHE )
		$('#containerAttivitaPendenti').load(WEBAPP_BASE_URL + '/parts/notifichePendenti.jsp' + '#containerAttivitaPendenti');

	/*if( json.type == EVENT_TYPE_NOTIFICHE_INCARICO 
			|| json.type == EVENT_TYPE_NOTIFICHE_PROFESSIONISTA 
			|| json.type == EVENT_TYPE_NOTIFICHE_PROFORMA
			|| json.type == EVENT_TYPE_NOTIFICHE_ATTO ){
		 var badge = document.getElementById("badgeNotificheCampana");   
		 var badgeMd = document.getElementById("badgeNotificheCampanaMd"); 
		 badge.innerHTML='';
		 badgeMd.innerHTML=''; 
		 var badgeTesto = document.createTextNode(json.message.numeroStep);
		 var badgeTestoMd = document.createTextNode(json.message.numeroStep);
		 badge.appendChild(badgeTesto); 
		 badgeMd.appendChild(badgeTestoMd);    
		 badgeMd.appendChild(badgeTestoMd);  
		if( json.type == EVENT_TYPE_NOTIFICHE_INCARICO 
			|| json.type == EVENT_TYPE_NOTIFICHE_PROFESSIONISTA 
			|| json.type == EVENT_TYPE_NOTIFICHE_PROFORMA
			|| json.type == EVENT_TYPE_NOTIFICHE_FASCICOLO  ){
			 gestioneEventoNotificheStandard(json);
		}
		else{
			 gestioneEventoNotificheAtto(json);	 
		}

	}*/
		else if( json.type == EVENT_TYPE_CALENDARIO ){
		gestioneEventoCalendario(evento);
	}
}

function gestioneEventoNotifiche(evento){
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {  
		 var container = document.getElementById("containerAttivitaPendenti");
		 container.innerHTML=data; 
	};

	var url = WEBAPP_BASE_URL + "/parts/notifichePendenti.jsp";
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function gestioneEventoCalendario(evento){ 
	 console.log("gestioneEventoCalendario");
	 
	 var json = JSON.parse(evento.data);
	 var msg=json.message;
	 	 
	 var obj=new Object();
	 obj.oggetto=msg.oggetto;
	 obj.descrizione=msg.descrizione;
	 obj.dataEvento=msg.dataEvento;
	 obj.dataScadenza=msg.dataScadenza;
	 obj.dataEvento=msg.dataEvento;
	 obj.id=msg.id;
	 obj.idFascicolo=msg.idFascicolo;
	 obj.nomeFascicolo=msg.nomeFascicolo;
	 obj.displayed="N";
	 g_agenda_ultimeNotifiche.push(obj);

	 g_agenda_badgeNotificheNumero++;
	 showBadgeNotificheAgenda();
	 
	 addNotificaAgenda(obj);
} 


function gestioneEventoNotificheStandard(evento){ 
	
		
	var container = document.getElementById("containerAttivitaPendenti");
	
	var aItem = document.createElement("A");
	aItem.setAttribute("href","javascript:void(0)")
	aItem.setAttribute("class","list-group-item media notifica-evidenza")
	
	var divMediaBody = document.createElement("DIV");
	divMediaBody.setAttribute("class","media-body")
	var divLgi = document.createElement("DIV");
	divLgi.setAttribute("class","lgi-heading")
	var dataCreazioneText = document.createTextNode(evento.message.dataCreazione);
	divLgi.appendChild(dataCreazioneText);
	divMediaBody.appendChild(divLgi);

	var smallLgi = document.createElement("SMALL");
	smallLgi.setAttribute("class","lgi-text");
	var strong = document.createElement("STRONG"); 
	var descLinguaCorrente = document.createTextNode(evento.message.descLinguaCorrente);
	strong.appendChild(descLinguaCorrente);
	smallLgi.appendChild(strong);
	var br = document.createElement("BR");
	smallLgi.appendChild(br); 
	smallLgi.appendChild(br); 
	var noteSpecifiche = document.createTextNode(evento.message.noteSpecifiche);
	smallLgi.appendChild(noteSpecifiche);
	if(evento.message.fascicoloConValore.replace(/\s+$|^\s+/g,"") != "")
	{
		br = document.createElement("BR");
		smallLgi.appendChild(br); 
		var fascicoloConValore = document.createTextNode(evento.message.fascicoloConValore); 
		smallLgi.appendChild(fascicoloConValore);
	}
	
	divMediaBody.appendChild(smallLgi);

	var divNotificheAction = document.createElement("DIV");
	divNotificheAction.setAttribute("class","notifiche-action")
	var btn1 = document.createElement("BUTTON");
	btn1.setAttribute("class","btn btn-icon waves-effect waves-circle waves-float");
	btn1.setAttribute("style","visibility:hidden");
	var spanBtn1 = document.createElement("SPAN");
	spanBtn1.setAttribute("class","zmdi zmdi-check")
	btn1.appendChild(spanBtn1);
	divNotificheAction.appendChild(btn1);
	
	var btn2 = document.createElement("BUTTON");
	btn2.setAttribute("class","btn btn-icon command-edit waves-effect waves-circle waves-float");
	btn2.setAttribute("data-toggle","modal");
	var methodInvoke = "processaWorkflow('"+ evento.message.id + "','$1','$2','$3','$4')";
	if(evento.message.codice == AUTORIZZAZIONE_INCARICO)

	{ 
		methodInvoke = methodInvoke.replace("$1",  evento.message.idIncaricoWf);
		methodInvoke = methodInvoke.replace("$2", "AUT_INCARICO" );
		methodInvoke = methodInvoke.replace("$3", evento.message.idIncarico);  

	}
	else if (evento.message.codice == AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO)
	{
		methodInvoke = methodInvoke.replace("$1",  evento.message.idProfessionistaEsternoWf);
		methodInvoke = methodInvoke.replace("$2", "AUT_PROF_EST" );
		methodInvoke = methodInvoke.replace("$3", evento.message.idProfessionista);   
	}
	else if (evento.message.codice == AUTORIZZAZIONE_PROFORMA)
	{
		methodInvoke = methodInvoke.replace("$1",  evento.message.idProformaWf);
		methodInvoke = methodInvoke.replace("$2", "AUT_PROFORMA" );
		methodInvoke = methodInvoke.replace("$3", evento.message.idProforma);   
	}

	methodInvoke = methodInvoke.replace("$4", evento.message.descLinguaCorrente);   
	btn2.setAttribute("onclick",methodInvoke);
	btn2.setAttribute("data-target","#panelFormWorkflow");
	
	var spanBtn2 = document.createElement("SPAN");
	spanBtn2.setAttribute("class","zmdi zmdi-check")
	btn2.appendChild(spanBtn2);

	divNotificheAction.appendChild(btn2);
	divMediaBody.appendChild(divNotificheAction);
	aItem.appendChild(divMediaBody);
	  
	if( container.childNodes.length == 0 ){ 
		container.appendChild(aItem);
	}else{
		container.insertBefore(aItem, container.childNodes[0]); 
	}
		  
}

 
function gestioneEventoNotificheAtto(evento){ 
	var container = document.getElementById("containerAvanzamentoFascicolo");
	var a="<a href=\"#\" class=\"list-group-item media\">"
		a = a + "<div class=\"media-body\">";
		a = a + "<div class=\"lgi-heading\">";
		a = a +  evento.message.dataCreazione;
		a = a  + "</div>";
		a = a + "<small class=\"lgi-text\"><strong>";
		a = a + evento.message.descLinguaCorrente;
		a = a + "</strong><br> ";
		a = a + evento.message.noteSpecifiche; 
		if(evento.message.fascicoloConValore.replace(/\s+$|^\s+/g,"") != "")
		{
			a = a + "<br>";
			a = a + evento.message.fascicoloConValore;
		}
		a = a + "</small>";
		a = a + "<div class=\"notifiche-action\">";



		a = a + "<button class=\"btn btn-icon waves-effect waves-circle waves-float\" style=\"visibility:hidden\">";
		a = a + "<span class=\"zmdi zmdi-check\"></span>";
		a = a + "</button>";
		a = a + "<button class=\"btn btn-icon command-edit waves-effect waves-circle waves-float\" data-toggle=\"modal\" onclick=\"processaWorkflowAtto('";
		a = a + evento.message.id;
		a = a + "','";
		a = a + evento.message.idAttoWf;
		a = a + "','";
		a = a + REGISTR_ATTO;
		a = a + "','";
		a = a + evento.message.idAtto;
		a = a + "','";
		a = a + "0";
		a = a + "','";
		a = a + "0";
		a = a + "','";
		a = a + evento.message.motivoRifiutoPrecedente;
		a = a + "')\" data-target=\"#panelFormWorkflowAtto\">";
		a = a + "<span class=\"zmdi zmdi-edit\"></span>";
		a = a + "</button>";
		//$("#containerAttivitaPendenti").insertBefore(a);
		container.insertBefore(a, container.childNodes[0]);
 
}

function gestioneAvanzamentoFascicolo(fascicoli){ 
	
	
 
}


function marcaNotificaWebLetta(id){
	console.log("marcaNotificaWebLetta con id: " + id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		$("#notificaWeb_"+id).hide();
		$("#hdnIdStep").val($("#hdnIdStep").val() - 1);
		
		 var badge = document.getElementById("badgeNotificheCampana");   
		 var badgeMd = document.getElementById("badgeNotificheCampanaMd"); 
		 if(badge != null && badgeMd != null){
			 badge.innerHTML='';
			 badgeMd.innerHTML=''; 
			 var badgeTesto = document.createTextNode(document.getElementById("hdnIdStep").value);
			 var badgeTestoMd = document.createTextNode(document.getElementById("hdnIdStep").value);
			 badge.appendChild(badgeTesto); 
			 badgeMd.appendChild(badgeTestoMd); 
		}			 

		
	};

	var callBackFnErr = function(data, stato) {  
	};
	
	var url = WEBAPP_BASE_URL + "notificaWeb/marcaNotificaWebLetta.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr );
}