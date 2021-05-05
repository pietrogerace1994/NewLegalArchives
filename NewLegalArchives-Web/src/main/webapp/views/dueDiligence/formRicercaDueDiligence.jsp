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
	#tabellaRicercaRisultati {
		font-size: 80%;
		max-height: 600px;
		overflow: auto;
		-ms-overflow-style: auto;
	}
	#tabellaRicercaRisultati td {
		/* cursor: pointer; */
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
								<h2><spring:message text="??duediligence.label.ricercaDueDiligence??"
										            code="duediligence.label.ricercaDueDiligence" />
								</h2>
							</div>

							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form id="dueDiligenceForm" name="dueDiligenceForm"
									method="post" modelAttribute="dueDiligenceView"
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
											<legend class="scheduler-border"> 
												<spring:message code="duediligence.label.ricerca" 
												                text="??duediligence.label.ricerca??">
												</spring:message>
											</legend>
											
											<!-- DENOMINAZIONE -->
											<div class="list-group lg-alt">
												<div class="list-group-item">
													<div class="form-group">
														<label for="comboProfessionista" class="col-sm-2 control-label"><spring:message
																text="??duediligence.label.professionista??"
																code="duediligence.label.professionista" /></label>
														<div class="col-sm-10"> 
															<form:select path="professionistaCode" cssClass="form-control" id="comboProfessionista">
																<form:option value="">
																	<spring:message
																		text="??duediligence.label.selezionaProfessionista??"
																		code="duediligence.label.selezionaProfessionista" />
																</form:option>
																<c:if test="${ dueDiligenceView.professionistaEsternoList != null }">
																	<c:forEach items="${dueDiligenceView.professionistaEsternoList}" var="oggetto">
																		<form:option value="${oggetto.vo.id }">
																			<c:out value="${oggetto.vo.cognomeNome}"></c:out>
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
														<label for="comboStatoDueDiligence" class="col-sm-2 control-label"><spring:message
																text="??duediligence.label.stato??"
																code="duediligence.label.stato" /></label>
														<div class="col-sm-10">
															<form:select path="statoDueDiligenceCode" cssClass="form-control" id="comboStatoDueDiligence">
																<form:option value="">
																	<spring:message
																		text="??duediligence.label.selezionastato??"
																		code="duediligence.label.selezionastato" />
																</form:option>
																<c:if test="${ dueDiligenceView.statoDueDiligenceList != null }">
																	<c:forEach items="${dueDiligenceView.statoDueDiligenceList}" var="oggetto">
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
											
											<div class="list-group lg-alt">
												<div class="list-group-item">
													<div class="form-group">
														<label for="dataAperturaDal" class="col-sm-2 control-label">
															<spring:message text="??duediligence.label.dataAperturaDal??"
																code="duediligence.label.dataAperturaDal" />
														</label>
														<div class="col-sm-10">
															 <form:input id="txtDataAperturaDal" path="dataAperturaDal" cssClass="form-control date-picker"/>
														</div>
													</div>
												</div>
											</div>
										
											<div class="list-group lg-alt">
												<div class="list-group-item">
													<div class="form-group">
														<label for="dataAperturaAl" class="col-sm-2 control-label">
															<spring:message text="??duediligence.label.dataAperturaAl??"
																code="duediligence.label.dataAperturaAl" />
														</label>
														<div class="col-sm-10">
															 <form:input id="txtDataAperturaAl" path="dataAperturaAl" cssClass="form-control date-picker"/>
														</div>
													</div>
												</div>
											</div>
											
											<div class="list-group lg-alt">
												<div class="list-group-item">
													<div class="form-group">
														<label for="dataChiusuraDal" class="col-sm-2 control-label">
															<spring:message text="??duediligence.label.dataChiusuraDal??"
																code="duediligence.label.dataChiusuraDal" />
														</label>
														<div class="col-sm-10">
															 <form:input id="txtDataChiusuraDal" path="dataChiusuraDal" cssClass="form-control date-picker"/>
														</div>
													</div>
												</div>
											</div>
										
											<div class="list-group lg-alt">
												<div class="list-group-item">
													<div class="form-group">
														<label for="dataChiusuraAl" class="col-sm-2 control-label">
															<spring:message text="??duediligence.label.dataChiusuraAl??"
																code="duediligence.label.dataChiusuraAl" />
														</label>
														<div class="col-sm-10">
															 <form:input id="txtDataChiusuraAl" path="dataChiusuraAl" cssClass="form-control date-picker"/>
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

											<!-- RICERCA RISULTATO -->
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
		<c:if test="${ empty dueDiligenceView.dueDiligenceSearchJson }">
			var dueDiligenceSearchJson = '';
		</c:if>
		
		<c:if test="${ dueDiligenceView.dueDiligenceSearchJson != null }">
			var dueDiligenceSearchJson = ${dueDiligenceView.dueDiligenceSearchJson};
		</c:if>
	</script>

	<script src="<%=request.getContextPath()%>/portal/js/controller/dueDiligenceRicerca.js"></script>

</body>

</html>
