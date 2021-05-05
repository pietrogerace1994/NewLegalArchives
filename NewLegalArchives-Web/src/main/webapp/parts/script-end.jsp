<!-- Javascript Libraries -->
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.UtenteView"%>

	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery-ui.min.js"></script>
		
	<script
		src="<%=request.getContextPath()%>/vendors/jquery/jquery.easytree.min.js"></script>	
		
	<script
		src="<%=request.getContextPath()%>/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.js"></script>

	<script src="<%=request.getContextPath()%>/vendors/jquery/waves.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/jquery/bootstrap-growl.min.js"></script> 
		
		
	<script	src="<%=request.getContextPath()%>/vendors/moment/moment.min.js" charset="utf-8" ></script>
	<script	src="<%=request.getContextPath()%>/vendors/moment/moment-with-locale.js" charset="utf-8" ></script>
		
	<script
		src="<%=request.getContextPath()%>/vendors/fullcalendar-2.8.0/fullcalendar.min.js"></script>
		
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap-toggle-master/js/bootstrap-toggle.min.js"></script>
	
	<script
		src="<%=request.getContextPath()%>/vendors/bootstrap-color-selector/bootstrap-colorselector.min.js"></script>	
	
	<script>	
		moment.locale('<%=request.getLocale().getLanguage()%>');
		 //Display Current Date as Calendar widget header
         
        var mYear = moment().format('YYYY');
        var mDay = moment().format('dddd, MMM D');
        $('#calendar-widget .cwh-year').html(mYear);
        $('#calendar-widget .cwh-day').html(mDay);
	</script>
	  
	<script charset="UTF-8" src='<%=request.getContextPath()%>/vendors/fullcalendar-2.8.0/lang-all.js'></script>
	
	<script
		src="<%=request.getContextPath()%>/vendors/bootgrid/jquery.bootgrid.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/sweetalert-master/dist/sweetalert.min.js"></script>

	<script src="<%=request.getContextPath()%>/vendors/flot/jquery.flot.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/flot/jquery.flot.resize.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/flot/jquery.flot.tooltip.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/vendors/flot/jquery.flot.pie.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/flot/pie-chart.js"></script>
	
	<script src="<%=request.getContextPath()%>/vendors/bootstrap-table-master/dist/bootstrap-table.js"></script>
	
	<%
		if( request.getLocale() != null && request.getLocale().getLanguage().equalsIgnoreCase("IT") ){
	%>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap-table-master/dist/locale/bootstrap-table-it-IT.min.js"></script>
	<%		
		}else{
	%>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap-table-master/dist/locale/bootstrap-table-en-US.min.js"></script>
	<%		
		}
	%> 
	
	<script src="<%=request.getContextPath()%>/vendors/bootstrap-datetimepicker-master/build/js/bootstrap-datetimepicker.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/bootstrap-treeview/bootstrap-treeview.min.js"></script>
	<script src="<%=request.getContextPath()%>/vendors/ckeditor/ckeditor.js"></script>


	<!-- Placeholder for IE9 -->
	<!--[if IE 9 ]>
        <script src="<%=request.getContextPath()%>/vendors/jquery-placeholder/jquery.placeholder.min.js"></script>
        <![endif]-->

	
	<script src="<%=request.getContextPath()%>/portal/js/config.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/functions.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/actions.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/demo.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/ajax-util.js"></script> 
	<script src="<%=request.getContextPath()%>/portal/js/ajax-util-v2.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/controller/messaggi.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/waiting-dialog.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/controller/notifiche.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/controller/notificheAgenda.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/controller/notifichePecMail.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/controller/notificheFascicolo.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/jalert.js"></script>  
	<script src="<%=request.getContextPath()%>/portal/js/menu.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/webSocketClient.js"></script> 
	<script src="<%=request.getContextPath()%>/portal/js/header.js"></script> 
	
	<%
	UtenteView utenteConn = (UtenteView ) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
  
	%>
	
	<% if( utenteConn != null && request.getSession().getAttribute(Costanti.RICERCA_PARTE_CORRELATA_ALL) != null && request.getSession().getAttribute(Costanti.RICERCA_PARTE_CORRELATA_ALL).equals("OFF") ) { %>
	<script type="text/javascript">
	
	initDashboard('<%=utenteConn.getVo().getUseridUtil()%>', '<%=java.lang.System.getProperty("websocket.host")%>');
	</script> 
	<%}%><%-- RICERCA_PARTE_CORRELATA_ALL --%>
	
	
<% if( request.getSession().getAttribute(Costanti.RICERCA_PARTE_CORRELATA_ALL) != null && request.getSession().getAttribute(Costanti.RICERCA_PARTE_CORRELATA_ALL).equals("OFF") ) { %>	
<% if( session.getAttribute("MSG_BENVENUTO") != null ){ %>

<script type="text/javascript"> 
	$(window).load(function(){
	    //Welcome Message (not for login page)
	    function notify(message, type){
	        $.growl({
	            message: message
	        },{
	            type: type,
	            allow_dismiss: false,
	            label: 'Chiudi',
	            className: 'btn-xs btn-inverse',
	            placement: {
	                from: 'bottom',
	                align: 'right'
	            },
	            delay: 2500,
	            animate: {
	                    enter: 'animated fadeInRight',
	                    exit: 'animated fadeOutRight'
	            },
	            offset: {
	                x: 30,
	                y: 30
	            }
	        });
	    };
	
		    notify('Bentornato <%=((UtenteView)session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO)).getCodiceDescrizioneUtente()%>', 'inverse');
	
	});


</script>
<%session.removeAttribute("MSG_BENVENUTO");%>
<%}%>
<%}%><%-- RICERCA_PARTE_CORRELATA_ALL --%>