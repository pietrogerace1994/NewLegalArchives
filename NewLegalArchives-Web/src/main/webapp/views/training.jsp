<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<!DOCTYPE html lang="${language}">
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Legal Archives</title>

<jsp:include page="/parts/script-init.jsp">
</jsp:include>

<link rel="stylesheet" href="<%=request.getContextPath()%>/portal/css/video-js.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/portal/css/videojs.markers.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/portal/css/collapse.css"/>
<!-- <script src="<%=request.getContextPath()%>/portal/js/jquery-2.0.3.min.js"></script> -->
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">-->

</head>
<body data-ma-header="teal">
	<jsp:include page="/parts/header.jsp">
	</jsp:include>
	<!-- SECION MAIN -->
	<section id="main">

		<jsp:include page="/parts/aside.jsp">
		</jsp:include>
		<!-- SECTION CONTENT -->
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						
						<div class="card">	
							<div class="card-header ch-dark palette-Green-SNAM bg">
								<h2>Video Tutorial</h2>
							</div>
							<div class="card-body">
								<button class="collapsible">Richiesta Presa In Carico Registrazione Atto</button>
								<div class="content-div-video" style="margin-left: auto;margin-right: auto;width: 100%;text-align: -moz-center;text-align: -webkit-center;text-align: -ms-center;">	
									<video 
										id="registrazione_atto_1" 
										class="video-js vjs-default-skin"
										controls 
										preload="auto" 
										width="800" 
										height="400"
	
										data-setup='{"width": 800, "height": 400}'>
										<source src="<%=request.getContextPath()%>/portal/video/Richiesta_Presa_In_Carico_Registrazione_Atto.mp4" type='video/mp4'/>
									</video>
								</div>
		
								<button class="collapsible">Creazione Fascicolo</button>
								<div class="content-div-video" style="margin-left: auto;margin-right: auto;width: 100%;text-align: -moz-center;text-align: -webkit-center;text-align: -ms-center;">	
									<video 
										id="creazione_fascicolo" 
										class="video-js vjs-default-skin"
										controls 
										preload="auto" 
										width="800" 
										height="400"
						
										data-setup='{"width": 800, "height": 400}'>
										<source src="<%=request.getContextPath()%>/portal/video/Creazione_Fascicolo.mp4" type='video/mp4'/>
									</video>
								</div>
		
								<button class="collapsible">Scheda Fondo Rischi</button>
								<div class="content-div-video" style="margin-left: auto;margin-right: auto;width: 100%;text-align: -moz-center;text-align: -webkit-center;text-align: -ms-center;">	
									<video 
										id="scheda_fondo_rischi" 
										class="video-js vjs-default-skin"
										controls 
										preload="auto" 
										width="800" 
										height="400"
						
										data-setup='{"width": 800, "height": 400}'>
										<source src="<%=request.getContextPath()%>/portal/video/Scheda_Fondo_Rischi.mp4" type='video/mp4'/>
									</video>
								</div>
			
								<button class="collapsible">Creazione Incarico</button>
								<div class="content-div-video" style="margin-left: auto;margin-right: auto;width: 100%;text-align: -moz-center;text-align: -webkit-center;text-align: -ms-center;">	
									<video 	
										id="creazione_incarico" 
										class="video-js vjs-default-skin"
										controls 
										preload="auto" 
										width="800" 
										height="400"
							
										data-setup='{"width": 800, "height": 400}'>
										<source src="<%=request.getContextPath()%>/portal/video/Creazione_Incarico.mp4" type='video/mp4'/>
									</video>
								</div>	
							</div>						
						</div>
					</div>
				</div>
			</div>
		
		</section>

	</section>
	
 	
 	<script src="<%=request.getContextPath()%>/portal/js/video.min.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/videojs-markers.min.js"></script>
	
	<script type="text/javascript">
							
		var video_registrazione_atto_1 = videojs('registrazione_atto_1');
					
		video_registrazione_atto_1.markers({
			markers: [
				{time: 4.7, text: "Avvio procedura"},
				{time: 15, text: "Dettaglio Atto"},
				{time: 23.7, text: "Download atto"},
				{time: 34.2, text: "Selezione delega"}
			]
		});
						
						
		var video_creazione_fascicolo = videojs('creazione_fascicolo');
						
		video_creazione_fascicolo.markers({
			markers: [
				{time: 15, text: "Creazione Fascicolo"},
				{time: 131, text: "Inserimento Ricorso"},
				{time: 202, text: "Associazione Fascicolo"},
				{time: 218, text: "Salva Fascicolo"},
				{time: 229, text: "Ricerca Fascicolo"},
				{time: 240, text: "Modifica Fascicolo"},
				{time: 299, text: "Inserisci Allegati"}
			]
		});
							
		var video_scheda_fondo_rischi = videojs('scheda_fondo_rischi');
						
		video_scheda_fondo_rischi.markers({
			markers: [
				{time: 10, text: "Creazione Scheda"},
				{time: 107, text: "Comunicazione Legale Esterno"}
			]
		});
					
		var video_creazione_incarico = videojs('creazione_incarico');
						
		video_creazione_incarico.markers({
			markers: [
				{time: 6, text: "Creazione Incarico"},
				{time: 45, text: "Lettera Incarico"},
				{time: 225, text: "Allega Documenti"},
				{time: 325, text: "Avvio Workflow"}
			]
		});
						
	</script>
					
	<script>
		var coll = document.getElementsByClassName("collapsible");
		var i;
					
		for (i = 0; i < coll.length; i++) {
			coll[i].addEventListener("click", function() {
				this.classList.toggle("active-video");
				var content = this.nextElementSibling;
				if (content.style.display === "block") {
					content.style.display = "none";
				} else {
					content.style.display = "block";
				}
				if (content.style.maxHeight){
					content.style.maxHeight = null;
				} else {
					content.style.maxHeight = content.scrollHeight + "px";
				}
			});
		} 
	</script>
 
	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>
	
	
	
</body>
</html>
