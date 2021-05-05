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
									<spring:message text="??repertorioStandard.label.gestione??"
										code="repertorioStandard.label.gestione" />
								</h2>
							</div>

							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form id="repertorioStandardForm" name="repertorioStandardForm"
									method="post" modelAttribute="repertorioStandardView"
									action="salva.action" class="form-horizontal la-form"
									enctype="multipart/form-data">
									<engsecurity:token regenerate="false"/>

									<form:errors path="*" cssClass="alert alert-danger"
										htmlEscape="false" element="div"></form:errors>
									<form:hidden path="op" id="op" value="salvaRepertorioStandard" />


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
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??repertorioStandard.label.nome??"
															code="repertorioStandard.label.nome" /></label>
													<div class="col-sm-10">
														<form:input path="nome" cssClass="form-control" maxlength="200"
															id="txtNome"
															value="${repertorioStandardView.getVo().nome}" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="nota" class="col-sm-2 control-label"><spring:message
															text="??repertorioStandard.label.nota??"
															code="repertorioStandard.label.nota" /></label>
													<div class="col-sm-10">
														<form:input path="nota" cssClass="form-control" maxlength="1000"
															id="txtNota"
															value="${repertorioStandardView.getVo().nota}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<!--tipologia-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="idSocieta" class="col-sm-2 control-label"><spring:message
																text="??repertorioStandard.label.societa??"
																code="repertorioStandard.label.societa" /></label>
														<div class="col-sm-10">
															<form:select path="societaSelezionata" disabled="false"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??repertorioStandard.label.selezionaSocieta??"
																		code="repertorioStandard.label.selezionaSocieta" />
																</form:option>
																<c:if
																	test="${ repertorioStandardView.lstSocieta != null }">
																	<c:forEach
																		items="${repertorioStandardView.lstSocieta}"
																		var="oggetto">

																		<form:option value="${ oggetto }">
																			<c:out value="${ oggetto}"></c:out>
																		</form:option>

																	</c:forEach>
																</c:if>
															</form:select>
														</div>
													</div>
												</div>
											</div>
										</div>


										<div class="list-group lg-alt">
											<!--tipologia-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="idPosizioneOrganizzativa" class="col-sm-2 control-label"><spring:message
																text="??repertorioStandard.label.posizioneOrganizzativa??"
																code="repertorioStandard.label.posizioneOrganizzativa" /></label>
														<div class="col-sm-10">
															<form:select path="idPosizioneOrganizzativa" disabled="false"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??repertorioStandard.label.selezionaPosizioneOrganizzativa??"
																		code="repertorioStandard.label.selezionaPosizioneOrganizzativa" />
																</form:option>
																<c:if
																	test="${ repertorioStandardView.listaPosizioneOrganizzativa != null }">
																	<c:forEach
																		items="${repertorioStandardView.listaPosizioneOrganizzativa}"
																		var="oggetto_po">

																		<form:option value="${ oggetto_po.id }">
																			<c:out value="${oggetto_po.descrizione}"></c:out>
																		</form:option>

																	</c:forEach>
																</c:if>
															</form:select>
														</div>
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<!--tipologia-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="idPrimoLivelloAttribuzioni" class="col-sm-2 control-label"><spring:message
																text="??repertorioStandard.label.primoLivelloAttribuzioni??"
																code="repertorioStandard.label.primoLivelloAttribuzioni" /></label>
														<div class="col-sm-10">
															<form:select path="idPrimoLivelloAttribuzioni" disabled="false"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??repertorioStandard.label.selezionaPrimoLivelloAttribuzioni??"
																		code="repertorioStandard.label.selezionaPrimoLivelloAttribuzioni" />
																</form:option>
																<c:if
																	test="${ repertorioStandardView.listaPrimoLivelloAttribuzioni != null }">
																	<c:forEach
																		items="${repertorioStandardView.listaPrimoLivelloAttribuzioni}"
																		var="oggetto_pl">

																		<form:option value="${ oggetto_pl.id }">
																			<c:out value="${oggetto_pl.descrizione}"></c:out>
																		</form:option>

																	</c:forEach>
																</c:if>
															</form:select>
														</div>
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<!--tipologia-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="idSecondoLivelloAttribuzioni" class="col-sm-2 control-label"><spring:message
																text="??repertorioStandard.label.secondoLivelloAttribuzioni??"
																code="repertorioStandard.label.secondoLivelloAttribuzioni" /></label>
														<div class="col-sm-10">
															<form:select path="idSecondoLivelloAttribuzioni" disabled="false"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??repertorioStandard.label.selezionaSecondoLivelloAttribuzioni??"
																		code="repertorioStandard.label.selezionaSecondoLivelloAttribuzioni" />
																</form:option>
																<c:if
																	test="${ repertorioStandardView.listaSecondoLivelloAttribuzioni != null }">
																	<c:forEach
																		items="${repertorioStandardView.listaSecondoLivelloAttribuzioni}"
																		var="oggetto_sl">

																		<form:option value="${ oggetto_sl.id }">
																			<c:out value="${oggetto_sl.descrizione}"></c:out>
																		</form:option>

																	</c:forEach>
																</c:if>
															</form:select>
														</div>
													</div>
												</div>
											</div>
										</div>
										
										
										<div class="list-group lg-alt">
											<!--Allegato-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
													 <label for="tipologia" class="col-sm-2 control-label"></label>
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

																	<button type="button" data-toggle="modal"
																		onclick="openModalAllegatoGenerico()"
																		class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																		style="float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																		aria-expanded="true">
																		<i class="zmdi zmdi-plus icon-mini"></i>
																	</button>

																	<a id="allegatiGenericiGraffa"
																		style="display: none; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																		aria-expanded="true"> <i class="fa fa-paperclip "></i>
																	</a> <a data-toggle="collapse" data-parent="#accordion"
																		href="#boxAllegatiGenerici"> <spring:message
																			text="??repertorioStandard.label.template??"
																			code="repertorioStandard.label.template" />
																	</a>

																</h4>
															</div>
															<div id="boxAllegatiGenerici"
																class="panel-collapse collapse" role="tabpanel"
																aria-labelledby="headingAllegatiGenerici">
																<jsp:include
																	page="/subviews/repertorioStandard/allegatiGenerici.jsp"></jsp:include>

															</div>
														</div>
													</div>
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">

												<div class="row">
													<div class="col-sm-9"></div>
													<div class="col-sm-1">
														<button id="btnClear" type="button"
															onclick="puliscoCampiInsert()"
															class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float">
															<i class="fa fa-eraser"></i>
														</button>
													</div>
													<div class="col-sm-1">
														<button form="repertorioStandardForm"
															onclick="salvaRepertorioStandard()"
															class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float btnSalva">
															<i class="zmdi zmdi-save"></i>
														</button>
													</div>
													<div class="col-sm-1"></div>
												</div>
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
		src="<%=request.getContextPath()%>/portal/js/controller/repertorioStandard.js"></script>
		<script type="text/javascript">
		/*
		// funzione per mostrare il modal racchiuso nel boxAllegati
		// Apre il boxAllegati e visualizza il modal per caricare un nuovo allegato
		 */
			function openModalAllegatoGenerico() {
				$("#boxAllegatiGenerici").collapse('show');
				if ($("#boxAllegatiGenerici > div.table-responsive > table > tbody > tr  button").length == 0)
					$("#panelAggiungiAllegatoGenerico").modal('show');
			}
	</script>

</body>

</html>