<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ page import="eng.la.util.costants.Costanti"%>
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
							<div class="card-header">
								<h1>
									<spring:message text="??schedaFondoRischi.label.PFR??" code="schedaFondoRischi.label.PFR" />
								</h1> 
							</div>
							<div class="card-body">
								
								<div class="action-header palette-LightGreen-SNAM bg clearfix">
									<div class="ah-label hidden-xs palette-White text">
										<spring:message text="??schedaFondoRischi.label.scegliIlSemestreDiRiferimento??"
											code="schedaFondoRischi.label.scegliIlSemestreDiRiferimento" />
									</div>
									<div class="ah-search">
										<input type="text" placeholder="Scegli il trimestre di riferimento"
											class="ahs-input"> <i
											class="ah-search-close zmdi zmdi-long-arrow-left"
											data-ma-action="ah-search-close"></i>
									</div>
									<ul class="ah-actions actions a-alt">
	
										<li class="dropdown"><a href="" data-toggle="dropdown"
											aria-expanded="true" id="dropdownNumRighe" style="width:100px !important;">
											<strong style="color: white; font-size: 140%; line-height: 20px;"
												id="trimestreBadge">-</strong></a>
											<ul class="dropdown-menu dropdown-menu-right" id="trimestriDisponibili">
												
											</ul>
										</li>
									</ul>
								</div>
								
								<table id="tabellaRicercaSchedaFondoRischi" class="table table-striped table-responsive"></table>
								
								<div class="modal-footer" style="padding:15px">
								<button id="generaPFR" disabled type="button" class="btn btn-primary waves-effect" onclick="generaPFR()">
									<spring:message text="??schedaFondoRischi.button.generaPFR??" code="schedaFondoRischi.button.generaPFR" />
								</button>
								
								<button id="generaPFRModifiche" disabled type="button" class="btn btn-primary waves-effect" onclick="generaPFRModifiche()">
									<spring:message text="schedaFondoRischi.button.generaPFRModifiche" code="schedaFondoRischi.button.generaPFRModifiche" />
								</button>
								 
							</div>
								
								
								<div style="float:left;">
									<ul style="list-style: none; margin:0px; padding-left: 0px;">
									    <li style="margin-top: 5px; font-size: 80%;">
									    	<span style="border: 1px solid #ccc; float: left; width: 12px; height: 12px; margin: 2px; background-color: blue;"></span> 
									    		<spring:message text="??schedaFondoRischi.label.schedeCreateSemestreSelezionato??"
											code="schedaFondoRischi.label.schedeCreateSemestreSelezionato" />
									    </li>
									    <li style="margin-top: 5px; font-size: 80%;">
									    	<span style="border: 1px solid #ccc; float: left; width: 12px; height: 12px; margin: 2px; background-color: red;"></span>
									    		 <spring:message text="??schedaFondoRischi.label.schedeModificateSemestreSelezionato??"
											code="schedaFondoRischi.label.schedeModificateSemestreSelezionato" />
									    </li>
									    <li style="margin-top: 5px; font-size: 80%;">
									    	<span style="border: 1px solid #ccc; float: left; width: 12px; height: 12px; margin: 2px; background-color: green;"></span>
									    		 <spring:message text="??schedaFondoRischi.label.schedeNonModificateSemestreSelezionato??"
											code="schedaFondoRischi.label.schedeNonModificateSemestreSelezionato" />
									    </li>
									    <li style="margin-top: 5px; font-size: 80%;">
									    	<span style="border: 1px solid #ccc; float: left; width: 12px; height: 12px; margin: 2px; background-color: black;"></span>
									    		 <spring:message text="??schedaFondoRischi.label.schedeEliminateSemestreSelezionato??"
											code="schedaFondoRischi.label.schedeEliminateSemestreSelezionato" />
									    </li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>
	
	<!-- MODAL Dettaglio Stampa -->
	<div class="modal fade" id="modalDettaglioStampa" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content" style="height:570px"> 
				<div class="modal-header">
					<h4 class="modal-title"> </h4>
				</div>
				<div class="modal-body" style="overflow-x: auto;height:90%">
				<iframe id="bodyDettaglioStampa" src="" style="width:100%;height:100%;border:0px"></iframe>
	
				</div>
				 
			</div>
		</div>
	</div>
	<!-- FINE MODAL Dettaglio Stampa -->
	
	<!-- MODAL Dettaglio Esito Generazione PFR -->
	<div class="modal fade" id="modalDettaglioEsitoPFR" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content" style="height:100px"> 
				<div class="modal-header">
					<h4 class="modal-title"> </h4>
				</div>
				<div class="modal-body" style="overflow-x: auto;height:90%">
				<iframe id="bodyDettaglioEsitoPFR" src="" style="width:100%;height:100%;border:0px"></iframe>
	
				</div>
				 
			</div>
		</div>
	</div>
	<!-- FINE MODAL Dettaglio Stampa -->
	
	
	<script>
	function stampaScheda(id){
		var url="<%=request.getContextPath()%>/schedaFondoRischi/stampa.action?id="+id+"&azione=visualizza";
		waitingDialog.show('Loading...');
		var ifr=$("#bodyDettaglioStampa").attr("src",url);
		ifr.load(function(){
		  	 waitingDialog.hide();
			$("#modalDettaglioStampa").modal("show");
		})
	}
	
	
	</script>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>

	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/schedaFondoRischi.js"></script>
	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/schedaFondoRischiGestione.js"></script>

</body>
</html>
