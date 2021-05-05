<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
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

<style>
#tabellaRicercaRisultati, #tabellaRicercaRisultatiMatch {
	font-size: 80%;
	max-height: 600px;
	overflow: auto;
	-ms-overflow-style: auto;
}
#tabellaRicercaRisultati td, #tabellaRicercaRisultatiMatch td {
	cursor: pointer;
}
</style>

</head>
<body data-ma-header="teal">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
						<div class="card">
							<div class="card-header ch-dark palette-Blue-400 bg">
								<h2>Ricerca Parti Correlate</h2>
							</div>

							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form id="parteCorrelataForm" name="parteCorrelataForm"
									method="post" modelAttribute="parteCorrelataView"
									action="ricercaAll.action" class="form-horizontal la-form">
									<engsecurity:token regenerate="false"/> 


									<form:errors path="*" cssClass="alert alert-danger"
										element="div"></form:errors>
									<c:if test="${ not empty param.successMessage }">
										<div class="alert alert-info">
											<spring:message code="messaggio.operazione.ok"
												text="??messaggio.operazione.ok??"></spring:message>
										</div>
									</c:if>
									<c:if test="${ not empty param.errorMessage }">
										<div class="alert alert-danger">
											<spring:message code="${param.errorMessage}"
												text="??${param.errorMessage}??"></spring:message>
										</div>
									</c:if>


									<form:hidden path="op" id="op" value="ricercaParteCorrelata" />

									<form:hidden path="stepMsg" id="stepMsg" />
									<form:hidden path="resetWizard" id="resetWizard" value="N" />

									<c:if test="${parteCorrelataView.stepMsg eq '0' }">
										<c:set var="msgRed" scope="page" value="none" />
										<c:set var="msgGreen" scope="page" value="none" />
									</c:if>

									<c:if test="${parteCorrelataView.stepMsg eq '1' }">
										<c:set var="msgRed" scope="page" value="block" />
										<c:set var="msgGreen" scope="page" value="none" />
									</c:if>
									<c:if test="${parteCorrelataView.stepMsg eq '2' }">
										<c:set var="msgRed" scope="page" value="none" />
										<c:set var="msgGreen" scope="page" value="block" />
									</c:if>



									<div class="tab-content p-20">
										<fieldset class="scheduler-border">

											<legend class="scheduler-border"> Ricerca </legend>




											<!-- DENOMINAZIONE -->
											<div class="list-group lg-alt">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label for="denominazione" class="col-lg-2 control-label"><spring:message
																	text="??parteCorrelata.label.denominazione??"
																	code="parteCorrelata.label.denominazione" /></label>
															<div class="col-lg-8">
																<form:input id="denominazione" path="denominazione"
																	cssClass="form-control" />
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- CODICE FISCALE -->
											<div class="list-group lg-alt">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label for="codFiscale" class="col-lg-2 control-label"><spring:message
																	text="??parteCorrelata.label.codFiscale??"
																	code="parteCorrelata.label.codFiscale" /></label>
															<div class="col-lg-8">
																<form:input id="codFiscale" path="codFiscale"
																	cssClass="form-control" />
															</div>
														</div>
													</div>
												</div>
											</div>
											
											<!-- PARTITA IVA -->
											<div class="list-group lg-alt">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label for="partitaIva" class="col-lg-2 control-label"><spring:message
																	text="??parteCorrelata.label.partitaIva??"
																	code="parteCorrelata.label.partitaIva" /></label>
															<div class="col-lg-8">
																<form:input id="partitaIva" path="partitaIva"
																	cssClass="form-control" />
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- DATA VALIDITA -->
											<%--<div class="list-group lg-alt">
												<div class="list-group-item media">
													<div class="media-body media-body-datetimepiker">
														<div class="form-group">
															<label for="dataValidita" class="col-lg-2 control-label"><spring:message
																	text="??parteCorrelata.label.dataValidita??"
																	code="parteCorrelata.label.dataValidita" /></label>
															<div class="col-lg-3">
																<form:input id="dataValidita" path="dataValidita"
																	cssClass="form-control date-picker" />
															</div>

														</div>
													</div>
												</div>
											</div> --%>

											<!-- button -->
											<div class="list-group lg-alt">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label class="col-lg-9 control-label"></label>
															<div class="col-lg-3">
																<button type="submit" id="btnSubmitRicerca"
																	class="btn palette-Blue-400 bg btn-float2 waves-effect waves-circle waves-float btnCerca">
																	<i class="zmdi zmdi-search"></i>
																</button>
															</div>


														</div>
														<br /> <br />

													</div>
												</div>
											</div>

											<!-- messaggio rosso -->
											<div class="list-group lg-alt" id="msgRed1"
												style="display:${msgRed}">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<div class="col-lg-12 text-center">
																<span class="label label-danger"
																	style="background-color: white !important; color: red !important; font-size: 150%">PARTE
																	NON CORRELATA</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<div class="list-group lg-alt" id="msgRed2"
												style="display:${msgRed}">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<div class="col-lg-12 text-center">
																<span class="label label-danger"
																	style="background-color: white !important; color: red !important;">Verificare
																	di aver inserito la denominazione corretta e completa
																	da ricerca e controllare se, tra i suggerimenti
																	proposti, sia presente il soggetto ricercato</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											

											<!-- messaggio verde -->
											<div class="list-group lg-alt" id="msgGreen1"
												style="display:${msgGreen}">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<div class="col-lg-12 text-center">
																<span class="label label-success"
																	style="background-color: white !important; color: green !important; font-size: 150%">PARTE
																	CORRELATA</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<div class="list-group lg-alt" id="msgGreen2"
												style="display:${msgGreen}">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<div class="col-lg-12 text-center">
																<span class="label label-success"
																	style="background-color: white !important; color: green !important;">Eseguire
																	adempimenti coma da procedura:</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<div class="list-group lg-alt" id="msgGreen3"
												style="display:${msgGreen}">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<div class="col-lg-12 text-center">
																<a href="#" role="presentation" class="active">Procedura</a>
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- RICERCA RISULTATO Match -->
											<div class="list-group lg-alt">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">

															<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
																<div class="table-responsive">
																	<table id="tabellaRicercaRisultatiMatch" data-search="true"
																		data-pagination="true" data-click-to-select="true"
																		class="table table-striped table-responsive hidden" >

																	</table>
																</div>
															</div>

														</div>
													</div>
												</div>
											</div>
											
											<div class="list-group lg-alt" style="display:none" id="msgReportMatch">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">

															<div class="col-lg-3">
																<a href="#"
																	onclick="window.open('<%=request.getContextPath()%>/public/parteCorrelata/downloadRicerca.action','_blank'); return false;">Visualizza
																	Report</a>
															</div>
														</div>
													</div>
												</div>
											</div>
											
											<div class="list-group lg-alt" id="msgSuggerimenti"
												style="display:none">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<div class="col-lg-12 text-left">
																<span class="label label-success"
																	style="background-color: white !important; color: black !important;">Suggerimenti
																	proposti:</span>
															</div>
														</div>
													</div>
												</div>
											</div>
											
											<!-- RICERCA RISULTATO Suggerimenti -->
											<div class="list-group lg-alt">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">

															<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
																<div class="table-responsive">
																	<table id="tabellaRicercaRisultati" data-search="true"
																		data-pagination="true" data-click-to-select="true"
																		class="table table-striped table-responsive hidden" >

																	</table>
																</div>
															</div>

														</div>
													</div>
												</div>
											</div>

											<div class="list-group lg-alt" style="display:none" id="msgReportSuggerimenti">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">

															<div class="col-lg-3">
																<a href="#"
																	onclick="window.open('<%=request.getContextPath()%>/public/parteCorrelata/downloadRicerca.action','_blank'); return false;">Visualizza
																	Report</a>
															</div>
														</div>
													</div>
												</div>
											</div>

										</fieldset>
									</div>

								</form:form>

							</div>
						</div>
					</div>
				</div>
			</div>
			<!--/ fine col-1 -->


	<jsp:include page="/parts/script-end-amministrativo.jsp"></jsp:include>
	<!-- si carica il js -->


	<script>
		
		<c:if test="${ empty parteCorrelataView.listaRicercaRisultatiJson }">
			var listaRicercaRisultatiJson = '';
		</c:if>
		
		<c:if test="${ parteCorrelataView.listaRicercaRisultatiJson != null }">
			var listaRicercaRisultatiJson = JSON.parse('${parteCorrelataView.listaRicercaRisultatiJson}');
		</c:if>
		
		<c:if test="${ empty parteCorrelataView.listaRicercaRisultatiMatchJson }">
			var listaRicercaRisultatiMatchJson = '';
		</c:if>
	
		<c:if test="${ parteCorrelataView.listaRicercaRisultatiMatchJson != null }">
			var listaRicercaRisultatiMatchJson = JSON.parse('${parteCorrelataView.listaRicercaRisultatiMatchJson}');
		</c:if>
		
		var esitoRic = '${parteCorrelataView.esitoRic}';
		
	</script>

	<script 
		src="<%=request.getContextPath()%>/portal/js/controller/parteCorrelataRicercaAll.js"></script>

</body>

</html>
