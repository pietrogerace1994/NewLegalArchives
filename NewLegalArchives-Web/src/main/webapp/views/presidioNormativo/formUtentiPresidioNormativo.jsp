<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html lang="${language}">
<!--[if IE 9 ]><html class="ie9"><![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Legal Archives</title>

<jsp:include page="/parts/script-init.jsp"></jsp:include>

<style>
.disabled {
	pointer-events: none;
	cursor: not-allowed;
}
</style>
</head>

<body data-ma-header="teal">

	<jsp:include page="/parts/header.jsp"></jsp:include>

	<!-- SECION MAIN -->
	<section id="main">
		<jsp:include page="/parts/aside.jsp"></jsp:include>

		<!-- SECTION CONTENT -->
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
						<div class="card">
							<div class="card-header ch-dark palette-Green-SNAM bg">
								<h2>
									<spring:message text="??presidionormativo.label.gestione??"
										code="presidionormativo.label.gestione" />
								</h2>
							</div>

							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form name="rubricaForm" method="post"
									modelAttribute="rubricaView" action="salva.action"
									class="form-horizontal la-form" enctype="multipart/form-data">
									<engsecurity:token regenerate="false"/>

									<form:errors path="*" cssClass="alert alert-danger"
										element="div"></form:errors>
									<c:if test="${rubricaView.rubricaId ne '0'}">
										<form:hidden path="rubricaId" id="rubricaId" value="${rubricaView.rubricaId}" />
									</c:if>
									
									<c:if test="${rubricaView.tabAttiva eq '1' }">
										<c:set var="tab1StyleAttiva" scope="page" value="active" />
										<form:hidden path="insertMode" id="insertMode" value="true" />
										<form:hidden path="editMode" id="editMode" value="false" />
										<form:hidden path="deleteMode" id="deleteMode" value="false" />
									</c:if>

									<c:if test="${rubricaView.tabAttiva eq '2' }">
										<c:set var="tab2StyleAttiva" scope="page" value="active" />
										<form:hidden path="insertMode" id="insertMode" value="false" />
										<form:hidden path="editMode" id="editMode" value="true" />
										<form:hidden path="deleteMode" id="deleteMode" value="false" />
										<form:hidden path="rubricaId" id="rubricaId" value="${rubricaView.rubricaId}" />
									</c:if>

									<form:hidden path="op" id="op" value="salvaRubrica" />
									<form:hidden path="tabAttiva" id="tabAttiva" />

									<ul class="tab-nav tn-justified tn-icon" role="tablist">
										<li role="presentation" style="position: relative"
											class='${tab1StyleAttiva}' onclick="javascript:insertCheck()">

											<a class="col-sx-6" href="#tab-1" aria-controls="tab-1"
											role="tab" data-toggle="tab"> <small> <spring:message
														text="??presidionormativo.label.crea??"
														code="presidionormativo.label.crea" />
											</small>
										</a>
										</li>

										<li role="presentation" style="position: relative"
											class='${tab2StyleAttiva}'
											onclick="editCheck(); puliscoAutocompletePcMod();"><a
											class="col-xs-6" href="#tab-2" aria-controls="tab-2"
											role="tab" data-toggle="tab"> <small> <spring:message
														text="??presidionormativo.label.modificacancellazione??"
														code="presidionormativo.label.modificacancellazione" />
											</small>
										</a></li>
									</ul>

									<div class="tab-content p-20">

										<div id="tab-1" role="tabpanel"
											class="tab-pane animated fadeIn ${tab1StyleAttiva}">
											<div class="card-body">
											<form:form name="rubricaForm" method="post"
												modelAttribute="rubricaView" action="salva.action"
												class="form-horizontal la-form">
												<engsecurity:token regenerate="false"/>
												
												<c:if test="${ not empty param.successMessage }">
													<div class="alert alert-info">
														<spring:message code="messaggio.operazione.ok"
															text="??messaggio.operazione.ok??"></spring:message>
													</div>
												</c:if>
												<c:if test="${ not empty param.errorMessage }">
													<div class="alert alert-danger">
														<spring:message code="${errorMessage}"
															text="??${errorMessage}??"></spring:message>
													</div>
												</c:if>
												<%-- <form:hidden path="rubricaId" /> --%>
												<%-- <form:hidden path="op" id="op" value="salvaRubrica" /> --%>



												<div class="list-group lg-alt">
													<!--NOMINATIVO-->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="nominativo" class="col-sm-2 control-label"><spring:message
																		text="??rubrica.label.nominativo??"
																		code="rubrica.label.nominativo" /></label>
																<div class="col-sm-10">
																	<form:textarea path="nominativo"
																		cssClass="form-control" />
																</div>
															</div>
														</div>
													</div>

													<!-- EMAIL  -->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="email" class="col-sm-2 control-label"><spring:message
																		text="??rubrica.label.emailNominativo??"
																		code="rubrica.label.emailNominativo" /></label>
																<div class="col-sm-10">
																	<form:textarea path="email" cssClass="form-control" />
																</div>
															</div>
														</div>
													</div>
												</div>
											</form:form>

											<button form="rubricaForm" onclick="salvaNominativo()"
												class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float">
												<i class="zmdi zmdi-save"></i>
											</button>
											</div>
										</div>
										<!-- end tab 1 -->

										<div id="tab-2" role="tabpanel"
											class="tab-pane animated fadeIn ${tab2StyleAttiva}">

											<div id="modifyDeleteDiv">

												<div class="card">
													<div class="card-header">
														<p
															class="visible-lg visible-md visible-xs visible-sm text-left">
															<spring:message
																text="??fascicolo.label.nonHaiTrovatoCercavi??"
																code="fascicolo.label.nonHaiTrovatoCercavi" />
															<a data-toggle="modal" href="#panelRicerca" class="">
																<spring:message text="??fascicolo.label.affinaRicerca??"
																	code="fascicolo.label.affinaRicerca" />
															</a>
														</p>
													</div>

													<div class="card-body">
														<div class="table-responsive">
															<table id="tabellaRicercaRubrica"
																class="table table-striped table-responsive">

															</table>
														</div>
													</div>
												</div>

											</div>

										</div>

									</div>
								</form:form>

							</div>
						</div>
					</div>
				</div>
			</div>

		</section>

	</section>
	
	<!-- PANEL RICERCA MODALE -->
	<div class="modal fade" id="panelRicerca" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.miglioraRicerca??"
							code="fascicolo.label.miglioraRicerca" />
					</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<fieldset>
							 
							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="nominativo">
									<spring:message text="??rubrica.label.nominativo??"
										code="rubrica.label.nominativo" />
								</label>
								<div class="col-md-4"> 
									<input id="txtNominativo" name="nominativo" type="text" 
										class="typeahead form-control input-md">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="email">
									<spring:message text="??rubrica.label.email??"
										code="rubrica.label.email" />
								</label>
								<div class="col-md-4"> 
									<input id="txtEmail" name="email" type="text" 
										class="typeahead form-control input-md">
								</div>
							</div>
							 
							 
							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltri" name="singlebutton" type="button"
										onclick="cercaRubrica()" data-dismiss="modal"
										class="btn btn-primary">
										<spring:message text="??fascicolo.label.eseguiRicerca??"
											code="fascicolo.label.eseguiRicerca" />
									</button>
									<button name="singlebutton" type="button" data-dismiss="modal"
										class="btn btn-warning">
										<spring:message text="??fascicolo.label.chiudi??"
											code="fascicolo.label.chiudi" />
									</button>
								</div>
							</div>

						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- FINE PANEL RICERCA MODALE -->
 

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>

	<jsp:include page="/parts/script-end.jsp"></jsp:include>
	<!-- si carica il js -->

	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/rubrica.js"></script>
		
	<script>
	initTabellaRicercaRubrica(); 
	</script>

</body>

</html>
