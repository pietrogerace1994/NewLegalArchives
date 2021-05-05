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
							 	<h2>
									<spring:message
										text="??incarico.label.dettaglioCollegioArbitrale??"
										code="incarico.label.dettaglioCollegioArbitrale" />
								</h2> 
							</div>
							<div class="card-body">

								<form:form name="collegioArbitraleForm" method="post"
									modelAttribute="collegioArbitraleView" 
									class="form-horizontal la-form">
									<form:errors path="*" cssClass="alert alert-danger"
										htmlEscape="false" element="div"></form:errors>
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
									<form:hidden path="collegioArbitraleId" />  
									<div class="list-group lg-alt">
										<!--NOME INCARICO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="incarico" class="col-sm-2 control-label"><spring:message
															text="??incarico.label.nome??" code="incarico.label.nome" /></label>
													<div class="col-sm-10">
														${collegioArbitraleView.incaricoRiferimento.vo.nomeIncarico}
														<form:hidden path="incaricoRiferimentoId" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--NOME INCARICO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="collegioArbitrale"
														class="col-sm-2 control-label"><spring:message
															text="??incarico.label.nomeCollegioArbitrale??"
															code="incarico.label.nomeCollegioArbitrale" /></label>
													<div class="col-sm-10">
														<form:input path="nomeCollegioArbitrale"
															cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--STATO -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for=stato class="col-sm-2 control-label"><spring:message
															text="??incarico.label.statoCollegioArbitrale??"
															code="incarico.label.statoCollegioArbitrale" /></label>
													<div class="col-sm-10">
														<form:input path="statoCollegioArbitrale"
															cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div> 

									<!-- COLLEGIO ARBITRALE-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="nomeCollegioArbitrale"
													class="col-sm-2 control-label"><spring:message
														text="??incarico.label.nomeCollegioArbitrale??"
														code="incarico.label.nomeCollegioArbitrale" /></label>
												<div class="col-sm-10">
													<form:input path="nomeCollegioArbitrale"
														cssClass="form-control" readonly="true" />
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--PROFESSIONISTA ARBITRO DI PARTE-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="arbitroDiParte" class="col-sm-2 control-label"><spring:message
															text="??incarico.label.arbitroDiParte??"
															code="incarico.label.arbitroDiParte" /></label>
													<div class="col-sm-10">
														<form:select path="arbitroDiParteId" disabled="true"
															cssClass="form-control">
															<form:option value="">
																<spring:message
																	text="??incarico.label.selezionaArbitroDiParte??"
																	code="incarico.label.selezionaArbitroDiParte" />
															</form:option>
															<c:if
																test="${ collegioArbitraleView.listaProfessionista != null }">
																<c:forEach
																	items="${collegioArbitraleView.listaProfessionista}"
																	var="oggetto">
																			<form:option value="${ oggetto.vo.id }">
																			<c:out
																				value="${oggetto.vo.studioLegale.denominazione} ${oggetto.cognomeNome}"></c:out>
																		</form:option>
																</c:forEach>
															</c:if>
														</form:select>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- NOME COGNOME ARBITRO CONTROPARTE -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="nominativoArbitroControparte"
													class="col-sm-2 control-label"><spring:message
														text="??incarico.label.nominativoArbitroControparte??"
														code="incarico.label.nominativoArbitroControparte" /></label>
												<div class="col-sm-10">
													<form:input path="nominativoArbitroControparte" readonly="true"
														cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>

									<!-- DENOMINAZIONE SOCIETA ARBITRO CONTROPARTE -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="denominazioneStudioArbitroControparte"
													class="col-sm-2 control-label"><spring:message
														text="??incarico.label.denominazioneStudioArbitroControparte??"
														code="incarico.label.denominazioneStudioArbitroControparte" /></label>
												<div class="col-sm-10">
													<form:input path="denominazioneStudioArbitroControparte" readonly="true"
														cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>

									<!-- INDIRIZZO ARBITRO CONTROPARTE -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="indirizzoArbitroControparte"
													class="col-sm-2 control-label"><spring:message
														text="??incarico.label.indirizzoArbitroControparte??"
														code="incarico.label.indirizzoArbitroControparte" /></label>
												<div class="col-sm-10">
													<form:input path="indirizzoArbitroControparte" readonly="true"
														cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>

									<!-- NOME COGNOME ARBITRO SEGRATARIO -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="nominativoArbitroSegretario"
													class="col-sm-2 control-label"><spring:message
														text="??incarico.label.nominativoArbitroSegretario??"
														code="incarico.label.nominativoArbitroSegretario" /></label>
												<div class="col-sm-10">
													<form:input path="nominativoArbitroSegretario" readonly="true"
														cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>

									<!-- DENOMINAZIONE SOCIETA ARBITRO SEGRETARIO -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="denominazioneStudioArbitroSegretario"
													class="col-sm-2 control-label"><spring:message
														text="??incarico.label.denominazioneStudioArbitroSegretario??"
														code="incarico.label.denominazioneStudioArbitroSegretario" /></label>
												<div class="col-sm-10">
													<form:input path="denominazioneStudioArbitroSegretario" readonly="true"
														cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>

									<!-- INDIRIZZO ARBITRO SEGRETARIO -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="indirizzoArbitroSegretario"
													class="col-sm-2 control-label"><spring:message
														text="??incarico.label.indirizzoArbitroSegretario??"
														code="incarico.label.indirizzoArbitroSegretario" /></label>
												<div class="col-sm-10">
													<form:input path="indirizzoArbitroSegretario" readonly="true"
														cssClass="form-control" /> 
												</div>
											</div>
										</div>
									</div>

									<!-- NOME COGNOME ARBITRO PRESIDENTE -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="nominativoArbitroPresidente"
													class="col-sm-2 control-label"><spring:message
														text="??incarico.label.nominativoArbitroPresidente??"
														code="incarico.label.nominativoArbitroPresidente" /></label>
												<div class="col-sm-10">
													<form:input path="nominativoArbitroPresidente" readonly="true"
														cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>

									<!-- DENOMINAZIONE SOCIETA ARBITRO PRESIDENTE -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="denominazioneStudioArbitroPresidente"
													class="col-sm-2 control-label"><spring:message
														text="??incarico.label.denominazioneStudioArbitroPresidente??"
														code="incarico.label.denominazioneStudioArbitroPresidente" /></label>
												<div class="col-sm-10">
													<form:input path="denominazioneStudioArbitroPresidente" readonly="true"
														cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>

									<!-- INDIRIZZO ARBITRO PRESIDENTE -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="indirizzoArbitroPresidente"
													class="col-sm-2 control-label"><spring:message
														text="??incarico.label.indirizzoArbitroPresidente??"
														code="incarico.label.indirizzoArbitroPresidente" /></label>
												<div class="col-sm-10">
													<form:input path="indirizzoArbitroPresidente" readonly="true"
														cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>


									<div class="list-group lg-alt">
										<!--DATA RICHIESTA AUTORIZZAZIONE-->
										<div class="list-group-item media">
											<div class="media-body media-body-datetimepiker">
												<div class="form-group">
													<label class="col-md-2 control-label" for="selectbasic"><spring:message
															text="??incarico.label.dataRichiestaAutorizzazione??"
															code="incarico.label.dataRichiestaAutorizzazione" /></label>
													<div class="col-md-10">
														<form:input id="txtDataRichiestaAutorizzazione"
															path="dataRichiestaAutorizzazione"
															cssClass="form-control date-picker" readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--DATA AUTORIZZAZIONE-->
										<div class="list-group-item media">
											<div class="media-body media-body-datetimepiker">
												<div class="form-group">
													<label class="col-md-2 control-label" for="selectbasic"><spring:message
															text="??incarico.label.dataAutorizzazione??"
															code="incarico.label.dataAutorizzazione" /></label>
													<div class="col-md-10">
														<form:input id="txtDataAutorizzazione"
															path="dataAutorizzazione"
															cssClass="form-control date-picker" readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div>
								</form:form>

								<button form="collegioArbitraleForm" onclick="modificaCollegioArbitrale(${ collegioArbitraleView.collegioArbitraleId })"
									class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float">
									<i class="zmdi zmdi-edit"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--/ fine col-1 -->
		</section>

	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>


	<script
		src="<%=request.getContextPath()%>/portal/js/controller/incarico.js"></script>

</body>
</html>

