/*
 * Calendar Widget
 */
function agenda_loading(isLoading,view) { 
    
	if (isLoading) {
		$('#btnAgendaIcon').removeClass();
    	$('#btnAgendaIcon').addClass("zmdi zmdi-spinner zmdi-hc-spin");
    }
    else {
    	$('#btnAgendaIcon').removeClass();
    	$('#btnAgendaIcon').addClass("zmdi zmdi-plus");
    }
    
} 

function initCalendar(){
	initCalendarWidget();
	caricaTipologieAgendaDisponibili();
}


function initCalendarWidget() {
	
	var tipologia = $("#tipologiaVisualizzazione").val();
	var valoreTipologia = $("#tipologiaVisualizzazioneVal").val();
	
	var url = WEBAPP_BASE_URL + "agenda/loadEventSources.action?type=" + valoreTipologia;
	url=legalSecurity.verifyToken(url);
  	var calendarwidget0=$('#calendar-widget')[0];
    if(calendarwidget0) {
    	
            $('#cw-body').fullCalendar({
		        contentHeight: 'auto',
		        theme: true,
		        lang: 'it',
                header: {
                    right: 'next',
                    center: 'title, ',
                    left: 'prev'
                },
                eventLimit: true,
                eventLimitText: "",
                views: {
                	month: {
                        eventLimit: 2
                    }
                },
                eventClick: function( event, jsEvent, view ) { 
                	var id = event.id;
                	var cssClassName=event.className[0];
                	var tipo = "";
                	if( cssClassName == "bgm-green" ) {
                		tipo = "E";
                	}
                	if( cssClassName == "bgm-red" ) {
                		tipo = "S";
                	}
                	
                	if(tipo == "E") {
                		agenda_apriDettaglioEvento(id);
                	}
                	else if(tipo == "S") {
                		agenda_apriDettaglioScadenza(id);
                	}
                	
                },
                
                editable: false,
                displayEventTime: false,
                events: url,
                loading: agenda_loading
            });
    }
}

function caricaTipologieAgendaDisponibili() {
	
	$("#tipologieDisponibili").empty();
	$("#tipologieDisponibili").html('<small class="lgi-text">Loading...</small>');

	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		$("#tipologieDisponibili").empty();
		
		if(data != null && data != ''){
			var arrJson = JSON.parse(data);
			
			for (var i = 0; i < arrJson.length; i++) {
			
				var ele = arrJson[i];
			
				var str = "";
				str = "<li><a href=\"javascript:void(0)\" onclick=\"changeTipologiaAgenda('"+ele.value+"', '"+ele.tipologia+"')\">" +ele.tipologia+ "</a></li>";
			
				$(str).appendTo("#tipologieDisponibili");
			}
		}
		 
	};
	var url = WEBAPP_BASE_URL + "agenda/caricaTipologieAgendaDisponibili.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
	
}

function changeTipologiaAgenda(valore, tipologia) {
	$("#tipologiaVisualizzazione").empty();
	$("#tipologiaVisualizzazione").html(tipologia);
	$("#tipologiaVisualizzazioneVal").val(valore);
	$('#cw-body').fullCalendar('destroy');
	initCalendarWidget();
	agenda_loadUltimeNotifiche(valore);
	
}