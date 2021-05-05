<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld" %>
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
							<div class="card-header ch-dark palette-White bg">
									<h2 style="font-size:33px !important; color:black;"> 
										<spring:message 
											text="??ricerca.label.ricerca??"
											code="ricerca.label.ricerca" />
									</h2>
							</div>
							
							<c:if test="${ empty ricercaView.jsonArrayFascicolo }">
								<c:set var="displayFascicolo" scope="page" value="none" />  
							</c:if>
							<c:if test="${ not empty ricercaView.jsonArrayFascicolo }">
								<c:set var="displayFascicolo" scope="page" value="block" />  
							</c:if>
							
							<c:if test="${ empty ricercaView.jsonArrayAtto }">
								<c:set var="displayAtto" scope="page" value="none" />  
							</c:if>
							<c:if test="${ not empty ricercaView.jsonArrayAtto }">
								<c:set var="displayAtto" scope="page" value="block" />  
							</c:if>
							
							<c:if test="${ empty ricercaView.jsonArrayIncarico }">
								<c:set var="displayIncarico" scope="page" value="none" />  
							</c:if>
							<c:if test="${ not empty ricercaView.jsonArrayIncarico }">
								<c:set var="displayIncarico" scope="page" value="block" />  
							</c:if>
							
							<c:if test="${ empty ricercaView.jsonArrayCollegioArbitrale }">
								<c:set var="displayCollegioArbitrale" scope="page" value="none" />  
							</c:if>
							<c:if test="${ not empty ricercaView.jsonArrayCollegioArbitrale }">
								<c:set var="displayCollegioArbitrale" scope="page" value="block" />  
							</c:if>
							
							<c:if test="${ empty ricercaView.jsonArrayCosti }">
								<c:set var="displayCosti" scope="page" value="none" />  
							</c:if>
							<c:if test="${ not empty ricercaView.jsonArrayCosti }">
								<c:set var="displayCosti" scope="page" value="block" />  
							</c:if>
							
							<c:if test="${ empty ricercaView.jsonArrayFile }">
								<c:set var="displayFile" scope="page" value="none" />  
							</c:if>
							<c:if test="${ not empty ricercaView.jsonArrayFile }">
								<c:set var="displayFile" scope="page" value="block" />  
							</c:if>
							
							<div class="card-body">
								
								<form:form 
									id="ricercaForm" 
									name="ricercaForm" 
									method="post" 
									modelAttribute="ricercaView" 
									action="ricerca.action" 
									class="form-horizontal la-form">
									
									<engsecurity:token regenerate="false"/> 
									
										<form:hidden path="oggetto" id="oggetto" value="${ricercaView.oggetto}" />
									
										<!-- RISULTATI -->
										<%--<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="tipologiaFascicoloId" class="col-sm-2 control-label"><spring:message
															text="??ricerca.label.risultati??"
															code="ricerca.label.risultati" /></label> 
													<div class="col-lg-12">--%>
													
									<div class="tab-content p-20">
									
										<form:errors path="*" cssClass="alert alert-danger" element="div"></form:errors>
										<c:if test="${ not empty param.errorMessage }">									
											<div class="alert alert-danger">
												<spring:message code="${param.errorMessage}" text="??${param.errorMessage}??"></spring:message>
											</div>											
										</c:if>	
									
										
										<!-- FASCICOLO -->
										<fieldset class="scheduler-border" style="display: ${displayFascicolo}">
											<legend class="scheduler-border"> Fascicoli <span class="badge badge-info" style="border-radius:10px !important;" >${ricercaView.numFascicoliTroviati}</span> </legend>	
											<div class="table-responsive" >
												<table id="tabellaRisultatiFascicolo" 
												data-search="true"
												 data-pagination="true"
												 class="table table-striped table-responsive">
							                               
							                    </table>
							                </div>
										</fieldset>
										
										<!-- INCARICO -->
										<fieldset class="scheduler-border" style="display: ${displayIncarico}">
											<legend class="scheduler-border"> Incarichi <span class="badge badge-info" style="border-radius:10px !important;" >${ricercaView.numIncarichiTroviati}</span> </legend>	
											<div class="table-responsive" >
												<table id="tabellaRisultatiIncarico" 
												data-search="true"
												 data-pagination="true"
												 class="table table-striped table-responsive">
							                               
							                    </table>
							               </div>
										</fieldset>
										
										<!-- COLLEGIO ARBITRALE -->
										<fieldset class="scheduler-border" style="display: ${displayCollegioArbitrale}">
											<legend class="scheduler-border"> Collegio Arbitrale <span class="badge badge-info" style="border-radius:10px !important;" >${ricercaView.numCollegioArbitraleTroviati}</span> </legend>	
											<div class="table-responsive" >
												<table id="tabellaRisultatiCollegioArbitrale" 
												data-search="true"
												 data-pagination="true"
												 class="table table-striped table-responsive">
							                               
							                    </table>
							               </div>
										</fieldset>
										
										<!-- ATTO -->
										<fieldset class="scheduler-border" style="display: ${displayAtto}">
											<legend class="scheduler-border"> Atti <span class="badge badge-info" style="border-radius:10px !important;" >${ricercaView.numAttiTroviati}</span> </legend>	
											<div class="table-responsive" >
												<table id="tabellaRisultatiAtto" 
												data-search="true"
												 data-pagination="true"
												 class="table table-striped table-responsive">
							                               
							                    </table>
							               </div>
										</fieldset>
										
										<!-- COSTI -->
										<fieldset class="scheduler-border" style="display: ${displayCosti}">
											<legend class="scheduler-border"> Proforma <span class="badge badge-info" style="border-radius:10px !important;" >${ricercaView.numCostiTroviati}</span> </legend>	
											<div class="table-responsive" >
												<table id="tabellaRisultatiCosti" 
												data-search="true"
												 data-pagination="true"
												 class="table table-striped table-responsive">
							                               
							                    </table>
							               </div>
										</fieldset>
										
										<fieldset class="scheduler-border" style="display: ${displayFile}">
											<legend class="scheduler-border"> File <span class="badge badge-info" style="border-radius:10px !important;" >${ricercaView.numFileTroviati}</span> </legend>	
											<div class="table-responsive" >
												<table id="tabellaRisultatiFile" 
												data-search="true"
												 data-pagination="true"
												 class="table table-striped table-responsive">
							                               
							                    </table>
							               </div>
										</fieldset>
										
										
										<!-- Nessun elemento trovato -->
										<c:if test="${ empty ricercaView.jsonArrayFascicolo }">
											<c:if test="${ ricercaView.oggetto eq 'T' }">
												<span class="label label-info">Nessun Fascicolo trovato.</span>
											</c:if>
											<c:if test="${ ricercaView.oggetto eq 'F' }">
												<span class="label label-info">Nessun Fascicolo trovato.</span>
											</c:if>
										</c:if>
										
										<c:if test="${ empty ricercaView.jsonArrayAtto }">
											<c:if test="${ ricercaView.oggetto eq 'T' }">
												<span class="label label-info">Nessun Atto trovato.</span>
											</c:if>
											<c:if test="${ ricercaView.oggetto eq 'A' }">
												<span class="label label-info">Nessun Atto trovato.</span>
											</c:if>
										</c:if>
										
										<c:if test="${ empty ricercaView.jsonArrayIncarico }">
											<c:if test="${ ricercaView.oggetto eq 'T' }">
												<span class="label label-info">Nessun Incarico trovato.</span>
											</c:if>
											<c:if test="${ ricercaView.oggetto eq 'I' }">
												<span class="label label-info">Nessun Incarico trovato.</span>
											</c:if>
										</c:if>
										
										<c:if test="${ empty ricercaView.jsonArrayCollegioArbitrale }">
											<c:if test="${ ricercaView.oggetto eq 'T' }">
												<span class="label label-info">Nessun Collegio Arbitrale trovato.</span>
											</c:if>
											<c:if test="${ ricercaView.oggetto eq 'I' }">
												<span class="label label-info">Nessun Collegio Arbitrale trovato.</span>
											</c:if>
										</c:if>
										
										<c:if test="${ empty ricercaView.jsonArrayCosti }">
											<c:if test="${ ricercaView.oggetto eq 'T' }">
												<span class="label label-info">Nessun Proforma trovato.</span>
											</c:if>
											<c:if test="${ ricercaView.oggetto eq 'C' }">
												<span class="label label-info">Nessun Proforma trovato.</span>
											</c:if>
										</c:if>
										
										<c:if test="${ empty ricercaView.jsonArrayFile }">
											<c:if test="${ ricercaView.oggetto eq 'T' }">
												<span class="label label-info">Nessun File trovato.</span>
											</c:if>
											<c:if test="${ ricercaView.oggetto eq 'F' }">
												<span class="label label-info">Nessun File trovato.</span>
											</c:if>
											<c:if test="${ ricercaView.oggetto eq 'A' }">
												<span class="label label-info">Nessun File trovato.</span>
											</c:if>
											<c:if test="${ ricercaView.oggetto eq 'I' }">
												<span class="label label-info">Nessun File trovato.</span>
											</c:if>
											<c:if test="${ ricercaView.oggetto eq 'C' }">
												<span class="label label-info">Nessun File trovato.</span>
											</c:if>
										</c:if>
										
									</div>
										
								</form:form>
								
							</div>
						</div>
					</div>
				</div>
			</div>		
			<!--/ fine col-1 -->
		</section>

	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>
	
	<jsp:include page="/parts/script-end.jsp"></jsp:include>
	
	
<script>

</script>
	
	<script type="text/javascript">
		<c:if test="${ empty ricercaView.jsonArrayFascicolo }">
			var jsonArrayFascicolo = '';
		</c:if>
		<c:if test="${ not empty ricercaView.jsonArrayFascicolo }">
			var jsonArrayFascicolo = JSON.parse('${ricercaView.jsonArrayFascicolo}');
		</c:if>
		
		<c:if test="${ empty ricercaView.jsonArrayAtto }">
			var jsonArrayAtto = '';
		</c:if>
		<c:if test="${ not empty ricercaView.jsonArrayAtto }">
			var jsonArrayAtto = JSON.parse('${ricercaView.jsonArrayAtto}');
		</c:if>
		
		<c:if test="${ empty ricercaView.jsonArrayIncarico }">
			var jsonArrayIncarico = '';
		</c:if>
		<c:if test="${ not empty ricercaView.jsonArrayIncarico }">
			var jsonArrayIncarico = JSON.parse('${ricercaView.jsonArrayIncarico}');
		</c:if>
		
		<c:if test="${ empty ricercaView.jsonArrayCollegioArbitrale }">
			var jsonArrayCollegioArbitrale = '';
		</c:if>
		<c:if test="${ not empty ricercaView.jsonArrayCollegioArbitrale }">
			var jsonArrayCollegioArbitrale = JSON.parse('${ricercaView.jsonArrayCollegioArbitrale}');
		</c:if>
		
		
		<c:if test="${ empty ricercaView.jsonArrayCosti }">
			var jsonArrayCosti = '';
		</c:if>
		<c:if test="${ not empty ricercaView.jsonArrayCosti }">
			var jsonArrayCosti = JSON.parse('${ricercaView.jsonArrayCosti}');
		</c:if>
		
		<c:if test="${ empty ricercaView.jsonArrayFile }">
			var jsonArrayFile = '';
		</c:if>
		<c:if test="${ not empty ricercaView.jsonArrayFile }">
			var jsonArrayFile = JSON.parse('${ricercaView.jsonArrayFile}');
		</c:if>
	</script>	
		
	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/ricerca.js"></script>
	
</body>
	
</html>