$(document).ready(function() {  
	
	$( "#liAgendaBtn" ).on( "click", function() {		 
		  $('#main').append('<div data-ma-action="sidebar-close" class="sidebar-backdrop animated fadeIn" />');                
		  $('#s-user-alerts').addClass('toggled');
		  

			pulisciNotificaAgendaContainer();
			
			//popola
			for(var x=0; x<g_agenda_ultimeNotifiche.length; x++) {
				var obj=g_agenda_ultimeNotifiche[x];
				obj.indiceArr = x;
				addNotificaAgenda(obj);
			}
			
	});
	
});


