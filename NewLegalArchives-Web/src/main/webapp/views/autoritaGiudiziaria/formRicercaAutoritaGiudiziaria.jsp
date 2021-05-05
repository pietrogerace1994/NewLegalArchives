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
	vertical-align: middle !important;
}

</style>

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
								<h2><spring:message text="??autoritaGiudiziaria.label.ricercaAutoritaGiudiziaria??"
										            code="autoritaGiudiziaria.label.ricercaAutoritaGiudiziaria" />
								</h2>
							</div>

							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form id="autoritaGiudiziariaForm" name="autoritaGiudiziariaForm"
									method="post" modelAttribute="autoritaGiudiziariaView"
									action="ricerca.action" class="form-horizontal la-form">
									
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
													
									<form:hidden path="resetWizard" id="resetWizard" value="N" />

									<div class="tab-content p-20">
										<fieldset class="scheduler-border">
											<legend class="scheduler-border"> Ricerca </legend>

											<!-- DENOMINAZIONE -->
											<div class="list-group lg-alt">
												<div class="list-group-item">
													<div class="form-group">
														<label for="autoritaGiudiziaria" class="col-sm-2 control-label"><spring:message
																text="??autoritaGiudiziaria.label.autoritaGiudiziaria??"
																code="autoritaGiudiziaria.label.autoritaGiudiziaria" /></label>
														<div class="col-sm-10">
															 <form:input path="autoritaGiudiziaria" cssClass="form-control" 
															 value="${autoritaGiudiziariaView.getVo().autoritaGiudiziaria}"/>
														</div>
													</div>
												</div>
											</div>
											
											<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="fornitore"
																class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.fornitore??"
																	code="autoritaGiudiziaria.label.fornitore" /></label>
															<div class="col-sm-10">
																<form:input path="fornitore"
																	cssClass="form-control"
																	value="${autoritaGiudiziariaView.getVo().fornitore}" />
															</div>
														</div>
													</div>
												</div>
											
											<div class="list-group lg-alt">
												<div class="list-group-item">
													<div class="form-group">
														<label for="oggetto" class="col-sm-2 control-label"><spring:message
																text="??autoritaGiudiziaria.label.annoRichiesta??"
																code="autoritaGiudiziaria.label.annoRichiesta" /></label>
														<div class="col-sm-10">
															 <form:input path="annoRichiesta" cssClass="form-control" 
															   value="${autoritaGiudiziariaView.getVo().annoRichiesta}"/>
														</div>
													</div>
												</div>
											</div>
											
											<div class="list-group lg-alt">
												<div class="list-group-item">
													<div class="form-group">
														<label for="dataInserimento" class="col-sm-2 control-label">
															<spring:message text="??autoritaGiudiziaria.label.dataInserimento??"
																code="autoritaGiudiziaria.label.dataInserimento" />
														</label>
														<div class="col-sm-10">
															 <form:input id="txtDataInserimento" path="dataInserimento" cssClass="form-control date-picker"
															    value="${autoritaGiudiziariaView.getVo().dataInserimento}"/>
														</div>
													</div>
												</div>
											</div>
											
											<div class="list-group lg-alt">
												<div class="list-group-item">
													<div class="form-group">
														<label for="idSocieta" class="col-sm-2 control-label"><spring:message
																text="??autoritaGiudiziaria.label.societa??"
																code="autoritaGiudiziaria.label.societa" /></label>
														<div class="col-sm-10"> 
															<form:select path="idSocieta" cssClass="form-control" id="comboSocieta">
																<form:option value="">
																	<spring:message
																		text="??autoritaGiudiziaria.label.selezionasocieta??"
																		code="autoritaGiudiziaria.label.selezionasocieta" />
																</form:option>
																<c:if test="${ autoritaGiudiziariaView.societaList != null }">
																	<c:forEach items="${autoritaGiudiziariaView.societaList}" var="oggetto">
																		<form:option value="${oggetto.vo.id }">
																			<c:out value="${oggetto.vo.nome}"></c:out>
																		</form:option>																		
																	</c:forEach>																
																</c:if>
															</form:select> 
														</div>
													</div>
												</div> 
										    </div>
											
											<div class="list-group lg-alt">
												<div class="list-group-item">
													<div class="form-group">
														<label for="oggetto" class="col-sm-2 control-label"><spring:message
																text="??autoritaGiudiziaria.label.oggetto??"
																code="autoritaGiudiziaria.label.oggetto" /></label>
														<div class="col-sm-10">
															 <form:input path="oggetto" cssClass="form-control" 
															   value="${autoritaGiudiziariaView.getVo().oggetto}"/>
														</div>
													</div>
												</div>
											</div>
											
											<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="tipologiaRichiesta" class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.tipologiarichiesta??"
																	code="autoritaGiudiziaria.label.tipologiarichiesta" /></label>
															<div class="col-sm-10"> 
																<form:select path="tipologiaRichiestaCode" cssClass="form-control" id="comboTipoRichiesta">
																	<form:option value="">
																		<spring:message
																			text="??autoritaGiudiziaria.label.selezionatipologiarichiesta??"
																			code="autoritaGiudiziaria.label.selezionatipologiarichiesta" />
																	</form:option>
																	<c:if test="${ autoritaGiudiziariaView.tipologiaRichiestaList != null }">
																		<c:forEach items="${autoritaGiudiziariaView.tipologiaRichiestaList}" var="oggetto">
																			<form:option value="${oggetto.vo.id }">
																				<c:out value="${oggetto.vo.nome}"></c:out>
																			</form:option>																		
																		</c:forEach>																
																	</c:if>
																</form:select> 
															</div>
														</div>
													</div> 
												</div>
												
												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="statoRichiesta" class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.stato??"
																	code="autoritaGiudiziaria.label.stato" /></label>
															<div class="col-sm-10"> 
																<form:select path="statoRichiestaCode" cssClass="form-control" id="comboStatoRichiesta">
																	<form:option value="">
																		<spring:message
																			text="??autoritaGiudiziaria.label.selezionastato??"
																			code="autoritaGiudiziaria.label.selezionastato" />
																	</form:option>
																	<c:if test="${ autoritaGiudiziariaView.statoRichAutGiudList != null }">
																		<c:forEach items="${autoritaGiudiziariaView.statoRichAutGiudList}" var="oggetto">
																			<form:option value="${oggetto.vo.id }">
																				<c:out value="${oggetto.vo.descrizione}"></c:out>
																			</form:option>																		
																		</c:forEach>																
																	</c:if>
																</form:select> 
															</div>
														</div>
													</div> 
												</div>
											

											<!-- button -->
											<div class="list-group lg-alt">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<div class="col-lg-9"></div>
															<div class="col-lg-1">
																<button type="button" id="btnClear"
																	class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float">
																	<i class="fa fa-eraser"></i>
																</button>
															</div>
															
															<div class="col-lg-1">
																<button type="submit"
																	class="btn palette-Green-SNAM bg btn-float2 waves-effect waves-circle waves-float btnCerca">
																	<i class="zmdi zmdi-search"></i>
																</button>
															</div>
															<div class="col-lg-1"></div>

														</div>
														<br/> <br/>
														
													</div>
												</div>
											</div>
											
											<div class="list-group lg-alt">
												<div class="list-group-item">
														<div class="form-group">
															<div class="col-lg-12 text-center" id="msgEmptyResult" style="display:none">
																<span class="label label-danger" 
																      style="background-color: white !important; 
																      color: red !important;">
																	Nessun Risultato
																</span>
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
																		class="table table-striped table-responsive hidden"  >
																	</table>
																</div>
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
		</section>

	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>

	<jsp:include page="/parts/script-end.jsp"></jsp:include>
	<!-- si carica il js -->

	<script>
		<c:if test="${ empty autoritaGiudiziariaView.autoritaGiudiziariaSearchJson }">
			var autoritaGiudiziariaSearchJson = '';
		</c:if>
		
		<c:if test="${ autoritaGiudiziariaView.autoritaGiudiziariaSearchJson != null }">
			var autoritaGiudiziariaSearchJson = ${autoritaGiudiziariaView.autoritaGiudiziariaSearchJson};
		</c:if>
	</script>

	<script src="<%=request.getContextPath()%>/portal/js/controller/autoritaGiudiziariaRicerca.js"></script>

</body>

</html>