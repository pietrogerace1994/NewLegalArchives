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
									<spring:message text="??organoSociale.label.gestione??"
										code="organoSociale.label.gestione" />
								</h2>
							</div>

							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form id="organoSocialeForm" name="organoSocialeForm"
									method="post" modelAttribute="organoSocialeView"
									action="salva.action" class="form-horizontal la-form"
									enctype="multipart/form-data">
									<engsecurity:token regenerate="false"/>

									<form:errors path="*" cssClass="alert alert-danger"
										htmlEscape="false" element="div"></form:errors>
									<form:hidden path="op" id="op" value="salvaOrganoSociale" />


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
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="idSocietaAffari" class="col-sm-2 control-label"><spring:message
																text="??organoSociale.label.societaAffari??"
																code="organoSociale.label.societaAffari" /></label>
														<div class="col-sm-10">
															<form:select path="idSocietaAffari" disabled="false"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??organoSociale.label.selezionaSocietaAffari??"
																		code="organoSociale.label.selezionaSocietaAffari" />
																</form:option>
																<c:if
																	test="${ organoSocialeView.listaSocietaAffari != null }">
																	<c:forEach
																		items="${organoSocialeView.listaSocietaAffari}"
																		var="oggetto">

																		<form:option value="${ oggetto.id }">
																			<c:out value="${oggetto.descrizione}"></c:out>
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
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="tipoOrganoSociale" class="col-sm-2 control-label"><spring:message
																text="??organoSociale.label.tipoOrganoSociale??"
																code="organoSociale.label.tipoOrganoSociale" /></label>
														<div class="col-sm-10">
															<form:select path="tipoOrganoSociale" disabled="false"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??organoSociale.label.selezionatipoOrganoSociale??"
																		code="organoSociale.label.selezionatipoOrganoSociale" />
																</form:option>
																<c:if
																	test="${ organoSocialeView.listaOrganoSociale != null }">
																	<c:forEach
																		items="${organoSocialeView.listaOrganoSociale}"
																		var="oggetto2">

																		<form:option value="${ oggetto2.id }">
																			<c:out value="${oggetto2.descrizione}"></c:out>
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
											<div class="list-group-item">
												<div class="form-group">
													<label for="cognome" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.cognome??"
															code="organoSociale.label.cognome" /></label>
													<div class="col-sm-10">
														<form:input path="cognome" cssClass="form-control" maxlength="100" 
															id="txtCognome"
															value="${organoSocialeView.getVo().cognome}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.nome??"
															code="organoSociale.label.nome" /></label>
													<div class="col-sm-10">
														<form:input path="nome" cssClass="form-control" maxlength="100" 
															id="txtNome"
															value="${organoSocialeView.getVo().nome}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="carica" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.carica??"
															code="organoSociale.label.carica" /></label>
													<div class="col-sm-10">
														<form:input path="carica" cssClass="form-control" maxlength="200" 
															id="txtCarica"
															value="${organoSocialeView.getVo().carica}" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataNomina" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.dataNomina??"
															code="organoSociale.label.dataNomina" /></label>
													<div class="col-sm-10">
														<form:input path="dataNomina" cssClass="form-control date-picker" maxlength="10"
															id="txtDataNomina"
															value="${organoSocialeView.getVo().dataNomina}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataCessazione" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.dataCessazione??"
															code="organoSociale.label.dataCessazione" /></label>
													<div class="col-sm-10">
														<form:input path="dataCessazione" cssClass="form-control date-picker" maxlength="10"
															id="txtDataCessazione"
															value="${organoSocialeView.getVo().dataCessazione}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataScadenza" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.dataScadenza??"
															code="organoSociale.label.dataScadenza" /></label>
													<div class="col-sm-10">
														<form:input path="dataScadenza" cssClass="form-control date-picker" maxlength="10"
															id="txtDataScadenza"
															value="${organoSocialeView.getVo().dataScadenza}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataAccettazioneCarica" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.dataAccettazioneCarica??"
															code="organoSociale.label.dataAccettazioneCarica" /></label>
													<div class="col-sm-10">
														<form:input path="dataAccettazioneCarica" cssClass="form-control date-picker" maxlength="10"
															id="txtDataAccettazioneCarica"
															value="${organoSocialeView.getVo().dataAccettazioneCarica}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="emolumento" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.emolumento??"
															code="organoSociale.label.emolumento" /></label>
													<div class="col-sm-10">
														<form:input path="emolumento" cssClass="form-control" maxlength="10"
															id="txtEmolumento"
															value="${organoSocialeView.getVo().emolumento}" />
													</div>
												</div>
											</div>
										</div>
										

										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataNascita" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.dataNascita??"
															code="organoSociale.label.dataNascita" /></label>
													<div class="col-sm-10">
														<form:input path="dataNascita" cssClass="form-control date-picker" maxlength="10"
															id="txtDataNascita"
															value="${organoSocialeView.getVo().dataNascita}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="luogoNascita" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.luogoNascita??"
															code="organoSociale.label.luogoNascita" /></label>
													<div class="col-sm-10">
														<form:input path="luogoNascita" cssClass="form-control" maxlength="100"
															id="txtLuogoNascita"
															value="${organoSocialeView.getVo().luogoNascita}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="codiceFiscale" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.codiceFiscale??"
															code="organoSociale.label.codiceFiscale" /></label>
													<div class="col-sm-10">
														<form:input path="codiceFiscale" cssClass="form-control"  maxlength="16"
															id="txtCodiceFiscale"
															value="${organoSocialeView.getVo().codiceFiscale}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="note" class="col-sm-2 control-label"><spring:message
															text="??organoSociale.label.note??"
															code="organoSociale.label.note" /></label>
													<div class="col-sm-10">
														<form:textarea path="note" cssClass="form-control" maxlength="1000"
															id="txtNote"
															value="${organoSocialeView.getVo().note}" />
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
														<button form="organoSocialeForm"
															onclick="salvaOrganoSociale()"
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
		src="<%=request.getContextPath()%>/portal/js/controller/organoSociale.js"></script>



</body>

</html>
