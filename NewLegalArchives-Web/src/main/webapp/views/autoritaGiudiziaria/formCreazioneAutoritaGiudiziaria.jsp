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
									<spring:message text="??autoritaGiudiziaria.label.gestione??"
										code="autoritaGiudiziaria.label.gestione" />
								</h2>
							</div>
							<input type="hidden" id="idAutGiud"
								value="<c:out value="${idAutGiud}"></c:out>">
							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form name="autoritaGiudiziariaForm" method="post"
									modelAttribute="autoritaGiudiziariaView" action="salva.action"
									class="form-horizontal la-form" enctype="multipart/form-data">
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

									<form:hidden path="autoritaGiudiziariaId"
										id="autoritaGiudiziariaId"
										value="${autoritaGiudiziariaView.vo.id}" />
									<c:if test="${autoritaGiudiziariaView.tabAttiva eq '1' }">
										<c:set var="tab1StyleAttiva" scope="page" value="active" />
										<form:hidden path="insertMode" id="insertMode" value="true" />
										<form:hidden path="editMode" id="editMode" value="false" />
										<form:hidden path="deleteMode" id="deleteMode" value="false" />
									</c:if>

									<c:if test="${autoritaGiudiziariaView.tabAttiva eq '2' }">
										<c:set var="tab2StyleAttiva" scope="page" value="active" />
										<form:hidden path="insertMode" id="insertMode" value="false" />
										<form:hidden path="editMode" id="editMode" value="true" />
										<form:hidden path="deleteMode" id="deleteMode" value="false" />
									</c:if>

									<form:hidden path="op" id="op" value="salvaAutoritaGiudiziaria" />
									<form:hidden path="tabAttiva" id="tabAttiva" />

									<ul class="tab-nav tn-justified tn-icon" role="tablist">
										<li role="presentation" style="position: relative"
											class='${tab1StyleAttiva}' onclick="javascript:insertCheck()">

											<a class="col-sx-6" href="#tab-1" aria-controls="tab-1"
											role="tab" data-toggle="tab"> <small> <spring:message
														text="??autoritaGiudiziaria.label.crea??"
														code="autoritaGiudiziaria.label.crea" />
											</small>
										</a>
										</li>

										<li role="presentation" style="position: relative"
											class='${tab2StyleAttiva}'
											onclick="editCheck(); puliscoAutocompletePcMod();"><a
											class="col-xs-6" href="#tab-2" aria-controls="tab-2"
											role="tab" data-toggle="tab"> <small> <spring:message
														text="??autoritaGiudiziaria.label.modificacancellazione??"
														code="autoritaGiudiziaria.label.modificacancellazione" />
											</small>
										</a></li>
									</ul>

									<div class="tab-content p-20">

										<div id="tab-1" role="tabpanel"
											class="tab-pane animated fadeIn ${tab1StyleAttiva}">

											<div id="creaDiv">

												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="idSocieta" class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.societa??"
																	code="autoritaGiudiziaria.label.societa" /></label>
															<div class="col-sm-10">
																<form:select path="idSocieta" cssClass="form-control"
																	id="comboSocieta">
																	<form:option value="">
																		<spring:message
																			text="??autoritaGiudiziaria.label.selezionasocieta??"
																			code="autoritaGiudiziaria.label.selezionasocieta" />
																	</form:option>
																	<c:if
																		test="${ autoritaGiudiziariaView.societaList != null }">
																		<c:forEach
																			items="${autoritaGiudiziariaView.societaList}"
																			var="oggetto">
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
															<label for="autoritaGiudiziaria"
																class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.autoritaGiudiziaria??"
																	code="autoritaGiudiziaria.label.autoritaGiudiziaria" /></label>
															<div class="col-sm-10">
																<form:input path="autoritaGiudiziaria"
																	cssClass="form-control"
																	value="${autoritaGiudiziariaView.getVo().autoritaGiudiziaria}" />
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
															<label for="dataInserimento"
																class="col-sm-2 control-label"> <spring:message
																	text="??autoritaGiudiziaria.label.dataInserimento??"
																	code="autoritaGiudiziaria.label.dataInserimento" />
															</label>
															<div class="col-sm-10">
																<form:input id="txtDataInserimento"
																	path="dataInserimento"
																	cssClass="form-control date-picker"
																	value="${autoritaGiudiziariaView.getVo().dataInserimento}" />
															</div>
														</div>
													</div>
												</div>

												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="dataRicezione" class="col-sm-2 control-label">
																<spring:message
																	text="??autoritaGiudiziaria.label.dataRicezione??"
																	code="autoritaGiudiziaria.label.dataRicezione" />
															</label>
															<div class="col-sm-10">
																<form:input id="txtDataRicezione" path="dataRicezione"
																	cssClass="form-control date-picker"
																	value="${autoritaGiudiziariaView.getVo().dataRicezione}" />
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
																	value="${autoritaGiudiziariaView.getVo().oggetto}" />
															</div>
														</div>
													</div>
												</div>

												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="tipologiaRichiesta"
																class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.tipologiarichiesta??"
																	code="autoritaGiudiziaria.label.tipologiarichiesta" /></label>
															<div class="col-sm-10">
																<form:select path="tipologiaRichiestaCode"
																	cssClass="form-control" id="comboTipoRichiesta">
																	<form:option value="">
																		<spring:message
																			text="??autoritaGiudiziaria.label.selezionatipologiarichiesta??"
																			code="autoritaGiudiziaria.label.selezionatipologiarichiesta" />
																	</form:option>
																	<c:if
																		test="${ autoritaGiudiziariaView.tipologiaRichiestaList != null }">
																		<c:forEach
																			items="${autoritaGiudiziariaView.tipologiaRichiestaList}"
																			var="oggetto">
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
															<label for="statoRichiesta"
																class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.stato??"
																	code="autoritaGiudiziaria.label.stato" /></label>
															<div class="col-sm-10">
																<form:select path="statoRichiestaCode"
																	cssClass="form-control disabled"
																	id="comboStatoRichiesta" readonly="true">
																	<%-- <form:option value="">
																			<spring:message
																				text="??autoritaGiudiziaria.label.selezionastato??"
																				code="autoritaGiudiziaria.label.selezionastato" />
																		</form:option> --%>
																	<c:if
																		test="${ autoritaGiudiziariaView.statoRichAutGiudList != null }">
																		<c:forEach
																			items="${autoritaGiudiziariaView.statoRichAutGiudList}"
																			var="oggetto">
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
															<label for="fileRichiestaInfoRepartoLegale"
																class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.fileStep1??"
																	code="autoritaGiudiziaria.label.fileStep1" /></label>
															<div class="col-sm-10">
																<input type="file" name="fileRichiestaInfoRepartoLegale"
																	id="fileRichiestaInfoRepartoLegale" class="isupdate" />
															</div>
														</div>
													</div>
												</div>

											</div>
											<!-- creaDiv -->
										</div>
										<!-- end tab 1 -->

										<div id="tab-2" role="tabpanel"
											class="tab-pane animated fadeIn ${tab2StyleAttiva}">

											<div id="modifyDeleteDiv">

												<div class="list-group-item">
													<div class="form-group">
														<label for="autoritaGiudiziariaIdMod"
															class="col-sm-2 control-label"> <i
															class="zmdi zmdi-search hs-reset"
															data-ma-action="search-clear"
															style="cursor: default !important; color: black !important; left: 100px;"></i>
														</label>
														<div class="col-sm-10">
															<div id=""
																style="max-height: 250px; overflow: auto; -ms-overflow-style: auto;">
																<input id="filtroListaAutGiudiziaria" type="text"
																	class="form-control">
															</div>
														</div>
													</div>
												</div>

												<!-- LISTA -->
												<div class="list-group-item">
													<div class="form-group">
														<label for="autoritaGiudiziariaIdMod"
															class="col-sm-2 control-label"><spring:message
																text="??autoritaGiudiziaria.label.lista??"
																code="autoritaGiudiziaria.label.lista" /></label>
														<div class="col-sm-10">

															<div id="autoritaGiudiziariaIdDivMod"
																style="max-height: 250px; overflow: auto; -ms-overflow-style: auto;">
																<form:select size="5" path="autoritaGiudiziariaIdMod"
																	onchange="caricaRichiestaAutGiudMod(this.value)"
																	onfocus="editCheck()" cssClass="form-control">

																	<c:if
																		test="${autoritaGiudiziariaView.autoritaGiudiziariaViewList != null}">
																		<c:forEach
																			items="${autoritaGiudiziariaView.autoritaGiudiziariaViewList}"
																			var="oggetto">

																			<form:option value="${ oggetto.vo.id }">
																				<c:out value="${oggetto.vo.autoritaGiudiziaria}"></c:out>
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
															<label for="idSocietaMod" class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.societa??"
																	code="autoritaGiudiziaria.label.societa" /></label>
															<div class="col-sm-10">
																<form:select path="idSocietaMod" cssClass="form-control"
																	id="comboSocietaMod">
																	<form:option value="">
																		<spring:message
																			text="??autoritaGiudiziaria.label.selezionasocieta??"
																			code="autoritaGiudiziaria.label.selezionasocieta" />
																	</form:option>
																	<c:if
																		test="${ autoritaGiudiziariaView.societaList != null }">
																		<c:forEach
																			items="${autoritaGiudiziariaView.societaList}"
																			var="oggetto">

																			<c:choose>
																				<c:when
																					test="${oggetto.vo.id eq autoritaGiudiziariaView.getVo().societa.id}">
																					<form:option value="${oggetto.vo.id}"
																						selected="true">
																						<c:out value="${oggetto.vo.nome}"></c:out>
																					</form:option>
																				</c:when>
																				<c:otherwise>
																					<form:option value="${oggetto.vo.id }">
																						<c:out value="${oggetto.vo.nome}"></c:out>
																					</form:option>
																				</c:otherwise>
																			</c:choose>

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
															<label for="autoritaGiudiziaria"
																class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.autoritaGiudiziaria??"
																	code="autoritaGiudiziaria.label.autoritaGiudiziaria" /></label>
															<div class="col-sm-10">
																<form:input path="autoritaGiudiziariaMod"
																	cssClass="form-control"
																	value="${autoritaGiudiziariaView.getVo().autoritaGiudiziaria}" />
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
																<form:input path="fornitoreMod"
																	cssClass="form-control"
																	value="${autoritaGiudiziariaView.getVo().fornitore}" />
															</div>
														</div>
													</div>
												</div>

												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="dataInserimento"
																class="col-sm-2 control-label"> <spring:message
																	text="??autoritaGiudiziaria.label.dataInserimento??"
																	code="autoritaGiudiziaria.label.dataInserimento" />
															</label>
															<div class="col-sm-10">
																<form:input id="txtDataInserimentoMod"
																	path="dataInserimentoMod"
																	cssClass="form-control date-picker"
																	value="${autoritaGiudiziariaView.dataInserimentoMod}" />
															</div>
														</div>
													</div>
												</div>

												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="dataRicezione" class="col-sm-2 control-label">
																<spring:message
																	text="??autoritaGiudiziaria.label.dataRicezione??"
																	code="autoritaGiudiziaria.label.dataRicezione" />
															</label>
															<div class="col-sm-10">
																<form:input id="txtDataRicezioneMod"
																	path="dataRicezioneMod"
																	cssClass="form-control date-picker"
																	value="${autoritaGiudiziariaView.dataRicezioneMod}" />
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
																<form:input path="oggettoMod" cssClass="form-control"
																	value="${autoritaGiudiziariaView.getVo().oggetto}" />
															</div>
														</div>
													</div>
												</div>

												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="tipologiaRichiesta"
																class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.tipologiarichiesta??"
																	code="autoritaGiudiziaria.label.tipologiarichiesta" /></label>
															<div class="col-sm-10">
																<form:select path="tipologiaRichiestaCodeMod"
																	cssClass="form-control" id="comboTipoRichiestaMod">
																	<form:option value="">
																		<spring:message
																			text="??autoritaGiudiziaria.label.selezionatipologiarichiesta??"
																			code="autoritaGiudiziaria.label.selezionatipologiarichiesta" />
																	</form:option>
																	<c:if
																		test="${ autoritaGiudiziariaView.tipologiaRichiestaList != null }">
																		<c:forEach
																			items="${autoritaGiudiziariaView.tipologiaRichiestaList}"
																			var="oggetto">

																			<c:choose>
																				<c:when
																					test="${oggetto.vo.id eq autoritaGiudiziariaView.getVo().tipologiaRichiesta.id}">
																					<form:option value="${oggetto.vo.id}"
																						selected="true">
																						<c:out value="${oggetto.vo.nome}"></c:out>
																					</form:option>
																				</c:when>
																				<c:otherwise>
																					<form:option value="${oggetto.vo.id }">
																						<c:out value="${oggetto.vo.nome}"></c:out>
																					</form:option>
																				</c:otherwise>
																			</c:choose>

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
															<label for="statoRichiesta"
																class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.stato??"
																	code="autoritaGiudiziaria.label.stato" /></label>
															<div class="col-sm-10">
																<form:select path="statoRichiestaCodeMod"
																	cssClass="form-control disabled"
																	id="comboStatoRichiestaMod" readonly="true">
																	<form:option value="">
																		<%-- <spring:message
																				text="??autoritaGiudiziaria.label.selezionastato??"
																				code="autoritaGiudiziaria.label.selezionastato" /> --%>
																	</form:option>
																	<c:if
																		test="${ autoritaGiudiziariaView.statoRichAutGiudList != null }">
																		<c:forEach
																			items="${autoritaGiudiziariaView.statoRichAutGiudList}"
																			var="oggetto">

																			<c:choose>
																				<c:when
																					test="${oggetto.vo.id eq autoritaGiudiziariaView.getVo().statoRichAutGiud.id}">
																					<form:option value="${oggetto.vo.id}"
																						selected="true">
																						<c:out value="${oggetto.vo.descrizione}"></c:out>
																					</form:option>
																				</c:when>
																				<c:otherwise>
																					<form:option value="${oggetto.vo.id}">
																						<c:out value="${oggetto.vo.descrizione}"></c:out>
																					</form:option>
																				</c:otherwise>
																			</c:choose>

																		</c:forEach>
																	</c:if>
																</form:select>
															</div>
														</div>
													</div>
												</div>

												<!-- Step 1 -->
												<div class="list-group lg-alt" id="step1Attach">
													<div class="list-group-item">
														<div class="form-group">
															<label for="fileRichiestaInfoRepartoLegaleMod"
																class="col-sm-2 control-label"><spring:message
																	text="??autoritaGiudiziaria.label.fileStep1??"
																	code="autoritaGiudiziaria.label.fileStep1" /></label>
															<div class="col-sm-10">
																<input type="file"
																	name="fileRichiestaInfoRepartoLegaleMod"
																	id="fileRichiestaInfoRepartoLegaleMod" class="isupdate" />
															</div>
														</div>
													</div>
												</div>

												<!-- Step 2 -->
												<div class="list-group lg-alt" id="step2AttachObj1"
													style="display: none">
													<div class="list-group-item">
														<div class="form-group">
															<label class="col-sm-2 control-label"> <spring:message
																	text="??autoritaGiudiziaria.label.fileStep1??"
																	code="autoritaGiudiziaria.label.fileStep1" /></label>
															<div class="col-sm-10">
																<table>
																	<tbody>
																		<tr>
																			<td style="padding-right: 5px;"><span
																				id="nomeFile"></span></td>
																			<td><a id="hrefDocumentoStep1"
																				href="<%=request.getContextPath() %>/download?onlyfn=0&isp=0&uuid=${oggetto.vo.documentoStep1.uuid}"
																				style="float: left; position: relative !important; background-color: #d9d9d9;"
																				class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
																				target="_BLANK"> <i
																					class="zmdi zmdi-download icon-mini"></i>
																			</a></td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="list-group lg-alt" id="step2AttachObj2"
												style="display: none">
												<div class="list-group-item">
													<div class="form-group">
														<label for="fileRichiestaInfoUnitaInterneMod"
															class="col-sm-2 control-label"><spring:message
																text="??autoritaGiudiziaria.label.fileStep2??"
																code="autoritaGiudiziaria.label.fileStep2" /></label>
														<div class="col-sm-10">
															<input type="file"
																name="fileRichiestaInfoUnitaInterneMod"
																id="fileRichiestaInfoUnitaInterneMod" class="isupdate" />
														</div>
													</div>
												</div>
											</div>

											<!-- Step 3 -->
											<div class="list-group lg-alt" id="step3AttachObj1"
												style="display: none">
												<div class="list-group-item">
													<div class="form-group">
														<label class="col-sm-2 control-label"> <spring:message
																text="??autoritaGiudiziaria.label.fileStep2??"
																code="autoritaGiudiziaria.label.fileStep2" /></label>
														<div class="col-sm-10">
															<table>
																<tbody>
																	<tr>
																		<td style="padding-right: 5px;"><span
																			id="step2FileName"> </span></td>
																		<td><a id="hrefDocumentoStep2"
																			href="<%=request.getContextPath() %>/download?onlyfn=0&isp=0&uuid=${oggetto.vo.documentoStep2.uuid}"
																			style="float: left; position: relative !important; background-color: #d9d9d9;"
																			class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
																			target="_BLANK"> <i
																				class="zmdi zmdi-download icon-mini"></i>
																		</a></td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
											<div class="list-group lg-alt" id="step3AttachObj2"
												style="display: none">
												<div class="list-group-item">
													<div class="form-group">
														<label for="fileRichiestaInfoUnitaInterneMod"
															class="col-sm-2 control-label"><spring:message
																text="??autoritaGiudiziaria.label.fileStep3??"
																code="autoritaGiudiziaria.label.fileStep3" /></label>
														<div class="col-sm-10">
															<input type="file" name="fileLetteraTrasmissioneMod"
																id="fileLetteraTrasmissioneMod" class="isupdate" />
														</div>
													</div>
												</div>
											</div>

											<!-- Step 4 -->
											<div class="list-group lg-alt" id="step4AttachObj"
												style="display: none">
												<div class="list-group-item">
													<div class="form-group">
														<label class="col-sm-2 control-label"> <spring:message
																text="??autoritaGiudiziaria.label.fileStep3??"
																code="autoritaGiudiziaria.label.fileStep3" /></label>
														<div class="col-sm-10">
															<table>
																<tbody>
																	<tr>
																		<td style="padding-right: 5px;"><span
																			id="step3FileName"> </span></td>
																		<td><a id="hrefDocumentoStep3"
																			href="<%=request.getContextPath() %>/download?onlyfn=0&isp=0&uuid=${oggetto.vo.documentoStep3.uuid}"
																			style="float: left; position: relative !important; background-color: #d9d9d9;"
																			class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
																			target="_BLANK"> <i
																				class="zmdi zmdi-download icon-mini"></i>
																		</a></td>
																	</tr>
																</tbody>
															</table>
														</div>

													</div>
												</div>
											</div>


										</div>


							
							</div>
														</form:form>
							<button form="autoritaGiudiziariaForm"
								onclick="cancellaAutoritaGiudiziaria()"
								class="btn palette-Green-SNAM bg btn-float2 waves-effect waves-circle waves-float btnCancella"
								style="display: none">
								<i class="zmdi zmdi-delete"></i>
							</button>

							<button form="autoritaGiudiziariaForm"
								onclick="salvaAutoritaGiudiziaria()"
								class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float btnSalva">
								<i class="zmdi zmdi-save"></i>
							</button>

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

	<script charset="UTF-8" type="text/javascript">
		<c:if test="${ empty autoritaGiudiziariaView.jsonArrayAutoritaGiudiziariaMod }">
			var jsonArrayAutoritaGiudiziariaMod = '';
		</c:if>
	
		<c:if test="${ not empty autoritaGiudiziariaView.jsonArrayAutoritaGiudiziariaMod }">
			var jsonArrayAutoritaGiudiziariaMod = JSON.parse('${autoritaGiudiziariaView.jsonArrayAutoritaGiudiziariaMod}');
		</c:if>
	</script>

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/autoritaGiudiziaria.js"></script>

</body>

</html>
