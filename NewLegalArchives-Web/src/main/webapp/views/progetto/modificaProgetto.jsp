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

<jsp:include page="/parts/script-init.jsp"></jsp:include>

<style>
.disabled {
	pointer-events: none;
	cursor: not-allowed;
}

.paddMultifile {
	padding-top: 5px;
}
</style>
</head>

<body data-ma-header="teal">
	<% String disableInVis = request.getAttribute("disableInVis")==null?"false":"true";  %>

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
								<% if ("true".equals(disableInVis)){ %>							
									<h2>
										<spring:message text="??progetto.label.visualizza??"
											code="progetto.label.visualizza" />
									</h2>
								<% }else{ %>
									<h2>
										<spring:message text="??progetto.label.modifica??"
											code="progetto.label.modifica" />
									</h2>
								<% } %>							
							</div>

							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form id="progettoForm" name="progettoForm" method="post"
									modelAttribute="progettoView" action="salva.action"
									class="form-horizontal la-form" enctype="multipart/form-data">
									
									<engsecurity:token regenerate="false"/>

									<form:errors path="*" cssClass="alert alert-danger"
										element="div"></form:errors>
									<form:hidden path="op" id="op" value="modificaProgetto" />


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


									<div id="creaDiv">

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataApertura" class="col-sm-2 control-label">
														<spring:message text="??progetto.label.dataApertura??"
															code="progetto.label.dataApertura" />
													</label>
													<div class="col-sm-10">
														<form:input id="txtDataApertura" path="dataCreazione"
															cssClass="form-control date-picker"
															value="${progettoView.getVo().dataCreazione}"
															disabled="true" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataChiusura" class="col-sm-2 control-label">
														<spring:message text="??progetto.label.dataChiusura??"
															code="progetto.label.dataChiusura" />
													</label>
													<div class="col-sm-10">
														<form:input id="txtDataChiusura" path="dataChiusura"
															cssClass="form-control date-picker"
															disabled="<%=  disableInVis %>"
															value="${progettoView.getVo().dataChiusura}" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="oggetto" class="col-sm-2 control-label"><spring:message
															text="??progetto.label.oggetto??"
															code="progetto.label.oggetto" /></label>
													<div class="col-sm-10">
														<form:input path="oggetto" cssClass="form-control"
															id="txtOggetto" disabled="<%=  disableInVis %>"
															value="${progettoView.getVo().oggetto}" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??progetto.label.nome??" code="progetto.label.nome" /></label>
													<div class="col-sm-10">
														<form:input path="nome" cssClass="form-control"
															id="txtNome" disabled="<%=  disableInVis %>"
															value="${progettoView.getVo().nome}" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="descrizione" class="col-sm-2 control-label"><spring:message
															text="??progetto.label.descrizione??"
															code="progetto.label.descrizione" /></label>
													<div class="col-sm-10">
														<form:input path="descrizione" cssClass="form-control"
															id="txtDescrizione" disabled="<%=  disableInVis %>"
															value="${progettoView.getVo().descrizione}" />
													</div>
												</div>
											</div>
										</div>


										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="descrizione" class="col-sm-2 control-label"></label>
													<div class="col-sm-10">

														<!-- ALLEGATI GENERICI -->
														<div class="panel panel-default">
															<div class="panel-heading" role="tab"
																id="headingAllegatiGenerici">
																<h4 class="panel-title">
																	<button type="button" data-toggle="collapse"
																		data-target="#boxAllegatiGenerici"
																		class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini collapsed"
																		style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																		aria-expanded="false">
																		<i class="zmdi zmdi-collection-text icon-mini"></i>
																	</button>
																	
																	<% if ("false".equals(disableInVis)){ %>
																	
																		<button type="button" data-toggle="modal"
																			onclick="openModalAllegatoGenerico()"
																			class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																			style="float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																			aria-expanded="true">
																			<i class="zmdi zmdi-plus icon-mini"></i>
																		</button>
																	<% } %>
																		

																	<a id="allegatiGenericiGraffa"
																		style="display: none; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																		aria-expanded="true"> <i class="fa fa-paperclip "></i>
																	</a> <a data-toggle="collapse" data-parent="#accordion"
																		href="#boxAllegatiGenerici"> <spring:message
																			text="??incarico.label.allegatiGenerici??"
																			code="incarico.label.allegatiGenerici" />
																	</a>

																</h4>
															</div>
															<div id="boxAllegatiGenerici"
																class="panel-collapse collapse" role="tabpanel"
																aria-labelledby="headingAllegatiGenerici">
																<jsp:include
																	page="/subviews/progetto/allegatiGenerici.jsp">
																		<jsp:param name="disableInVis" value="<%=  disableInVis %>"/>
																	</jsp:include>

															</div>
														</div>



													</div>
												</div>
											</div>
										</div>

									</div>
							</div>
							<!-- creaDiv -->

							<% if ("false".equals(disableInVis)){ %>
								<div class="row">
									<div class="col-sm-10"></div>
									<div class="col-sm-1">
										<button form="progettoForm" onclick="salvaProgetto()"
											class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float btnSalva">
											<i class="zmdi zmdi-save"></i>
										</button>
									</div>
									<div class="col-sm-1"></div>
								</div>
							<% } %>

							</form:form>

						</div>
					</div>
				</div>
			</div>
			</div>

		</section>

	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>

	<jsp:include page="/parts/script-end.jsp"></jsp:include>
	<!-- si carica il js -->

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/progetto.js"></script>

	<script type="text/javascript">
		if (document.getElementById("descrizioneProtocollo")) {
			CKEDITOR.replace('descrizioneProtocollo');
		}

		/*
		// funzione per mostrare il modal racchiuso nel boxAllegati
		// Apre il boxAllegati e visualizza il modal per caricare un nuovo allegato
		 */
		function openModalAllegatoGenerico() {
			$("#boxAllegatiGenerici").collapse('show');
			$("#panelAggiungiAllegatoGenerico").modal('show');
		}
	</script>

</body>

</html>