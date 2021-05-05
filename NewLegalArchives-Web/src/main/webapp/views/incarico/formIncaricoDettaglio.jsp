<%@page import="eng.la.util.costants.Costanti"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
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
								<!-- Aggiunta breadcrumb MASSIMO CARUSO -->
								<div class="card">
									<a href="<%=request.getContextPath()%>/fascicolo/contenuto.action?CSRFToken=<%= request.getParameter("CSRFToken") %>&id=${incaricoDettaglioView.fascicoloRiferimento.vo.id }">Fascicolo ${incaricoDettaglioView.fascicoloRiferimento.vo.nome }</a>
								</div>
								<!-- FINE Aggiunta breadcrumb MASSIMO CARUSO -->
								<h2>
									<spring:message text="??incarico.label.dettaglioIncarico??"
										code="incarico.label.dettaglioIncarico" />
								</h2>

							</div>
							<div class="card-body">
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
								
								<div id="sezioneMessaggiApplicativi"></div>
								
								<form:form name="incaricoForm" modelAttribute="incaricoDettaglioView"
									class="form-horizontal la-form">
									<engsecurity:token regenerate="false"/>

									<form:errors path="*" cssClass="alert alert-danger"
										element="div"></form:errors>



									<form:hidden path="incaricoId" />
									<form:hidden path="letteraIncaricoId"  id="idLettera"/>
									<form:hidden path="timeout"  id="timeout"/>
									<form:hidden path="notaIncaricoId"  id="idNotaProp"/>
									<form:hidden path="timeoutNot"  id="timeoutNot"/>

									<div class="list-group lg-alt">
										<!--NOME FASCICOLO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="fascicolo" class="col-sm-2 control-label"><spring:message
															text="??incarico.label.fascicolo??"
															code="incarico.label.fascicolo" /></label>
													<div class="col-sm-10">
														<form:input path="nomeFascicolo" cssClass="form-control"
															readonly="true" />
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
													<label for="fascicolo" class="col-sm-2 control-label"><spring:message
															text="??incarico.label.nome??" code="incarico.label.nome" /></label>
													<div class="col-sm-10">
														<form:input path="nomeIncarico" cssClass="form-control"
															readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--STATO INCARICO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for=stato class="col-sm-2 control-label"><spring:message
															text="??incarico.label.stato??"
															code="incarico.label.stato" /></label>
													<div class="col-sm-10">
														<form:input path="statoIncarico" cssClass="form-control"
															readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<!-- SOCIETA DI ADDEBITO-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="societaAddebito" class="col-sm-2 control-label"><spring:message
														text="??fascicolo.label.societaAddebito??"
														code="fascicolo.label.societaAddebito" /></label>
												<div class="col-sm-10">
													<div class="table-responsive" style="clear: both; border: 1px solid #e0e0e0;">
														<table class="table table-striped table-responsive">
															<thead>
																<tr>
																	<th data-column-id="01" style="width: 50px">
																		<button type="button" data-toggle="collapse"
																			data-target="#boxSocietaAddebito"
																			class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																			style="float: left; position: relative !important;">
																			<i class="zmdi zmdi-collection-text icon-mini"></i>
																		</button>
																	</th>
																	<th data-column-id="id"><spring:message
																			text="??fascicolo.label.societaAddebito??"
																			code="fascicolo.label.societaAddebito" /></th>

																</tr>
															</thead>
															<tbody id="boxSocietaAddebito" class="collapse in">
																<c:if
																	test="${ not empty incaricoDettaglioView.listaSocietaAddebitoAggiunteDesc }">
																	<c:forEach
																		items="${incaricoDettaglioView.listaSocietaAddebitoAggiunteDesc}"
																		var="oggetto">
																		<tr>
																			<td colspan="2">${oggetto}</td>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if
																	test="${ empty incaricoDettaglioView.listaSocietaAddebitoAggiunteDesc }">
																	<tr>
																		<td colspan="2"><spring:message
																				code="fascicolo.label.tabella.no.dati"
																				text="??fascicolo.label.tabella.no.dati??">
																			</spring:message></td>
																	</tr>
																</c:if>
															</tbody>
														</table>
													</div>

												</div>
											</div>
										</div>
									</div>
									
									<!-- NAZIONE E SPECIALIZZAZIONE-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="nazioneCode" class="col-sm-2 control-label"><spring:message
														text="??incarico.label.nazione??" code="incarico.label.nazione" /></label>
												<div class="col-sm-4">
													<input type="text" class="form-control"  value="${incaricoDettaglioView.nazione.vo.descrizione}" disabled="true"/>
												</div>
												<label for="specializzazioneCode" class="col-sm-2 control-label"><spring:message
														text="??incarico.label.specializzazione??" code="incarico.label.specializzazione" /></label>
												<div class="col-sm-4">
													<input type="text" class="form-control"  value="${incaricoDettaglioView.specializzazione.vo.descrizione}" disabled="true"/>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--PROFESSIONISTA-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="professionista" class="col-sm-2 control-label"><spring:message
															text="??incarico.label.professionista??"
															code="incarico.label.professionista" /></label>
													
													<div class="col-sm-10">
														<input type="text" class="form-control"   
														value="${incaricoDettaglioView.professionistaSelezionato.vo.studioLegale.denominazione} ${incaricoDettaglioView.professionistaSelezionato.vo.cognomeNome}" disabled="true"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<c:if test="${ not empty incaricoDettaglioView.listaProcure }">
									<!-- PROCURE-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="procure" class="col-sm-2 control-label"><spring:message
														text="??incarico.label.procure??"
														code="incarico.label.procure" /></label>
												<div class="col-sm-10">
													<div class="table-responsive" style="clear: both; border: 1px solid #e0e0e0;">
														<table class="table table-striped table-responsive">
															<thead>
																<tr>
																	<th data-column-id="01" style="width: 50px">
																		<button type="button" data-toggle="collapse"
																			data-target="#boxProcure"
																			class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																			style="float: left; position: relative !important;">
																			<i class="zmdi zmdi-collection-text icon-mini"></i>
																		</button>
																	</th>
																	<th data-column-id="id"><spring:message
																			text="??incarico.label.procure??"
																			code="incarico.label.procure" /></th>
																</tr>
															</thead>
															<tbody id="boxProcure" class="collapse in">
																<c:if
																	test="${ not empty incaricoDettaglioView.listaProcure }">
																	<tr>
																		<td colspan="2">PROCURATORE</td>
																		<td colspan="2">REPERTORIO</td>
																		<td colspan="2">TIPOLOGIA</td>
																		<td colspan="2">CONFERIMENTO</td>
																		<td colspan="2">REVOCA</td>
																	</tr>
																	<c:forEach
																		items="${incaricoDettaglioView.listaProcure}"
																		var="oggetto">
																		<tr>
																			<td colspan="2">${oggetto.vo.nomeProcuratore}</td>
																			<td colspan="2">${oggetto.vo.numeroRepertorio}</td>
																			<td colspan="2">${oggetto.vo.tipoProcure.descrizione}</td>
																			<td colspan="2">${oggetto.vo.dataConferimento}</td>
																			<td colspan="2">${oggetto.vo.dataRevoca}</td>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if
																	test="${   empty incaricoDettaglioView.listaProcure }">
																	<tr>
																		<td colspan="2"><spring:message
																				code="fascicolo.label.tabella.no.dati"
																				text="??fascicolo.label.tabella.no.dati??">
																			</spring:message></td>
																	</tr>
																</c:if>
															</tbody>
														</table>
													</div>

												</div>
											</div>
										</div>
									</div>
									</c:if>

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
															path="dataRichiestaAutorizzazione" readonly="true"
															cssClass="form-control date-picker" />
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
															path="dataAutorizzazione" readonly="true"
															cssClass="form-control date-picker" />
													</div>
												</div>
											</div>
										</div>
									</div>


									<!-- COMMENTO-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="commento" class="col-sm-2 control-label"><spring:message
														text="??incarico.label.commento??"
														code="incarico.label.commento" /></label>
												<div class="col-sm-10">
													<form:textarea path="commento" cols="70" rows="8"
														readonly="true" cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>


									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="allegati" class="col-sm-2 control-label"><spring:message
														text="??incarico.label.allegati??"
														code="incarico.label.allegati" /></label>
												<div class="col-sm-10">
													<div class="list-group-item media">
														<div class="media-body">
															<div id="accordion" role="tablist"
																aria-multiselectable="true">
																<!-- LETTEDA D'INCARICO -->
																<div class="panel panel-default">
																	<div class="panel-heading" role="tab"
																		id="headingLetteraIncarico">
																		<h4 class="panel-title">
																			<button type="button" data-toggle="collapse"
																				data-target="#boxLetteraIncarico"
																				class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini collapsed"
																				style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																				aria-expanded="false">
																				<i class="zmdi zmdi-collection-text icon-mini"></i>
																			</button>
																			<div id="LetteraDownload"></div>
																			<div id="LetteraFirmataDownload"></div>
																			<a data-toggle="collapse" data-parent="#accordion"
																				href="#boxLetteraIncarico" aria-expanded="true"
																				aria-controls="collapseOne"> <spring:message
																					text="??incarico.label.letteraIncarico??"
																					code="incarico.label.letteraIncarico" />
																			</a>
																		</h4>
																	</div>
																	<div id="boxLetteraIncarico"
																		class="panel-collapse collapse incaricodettaglio" role="tabpanel"
																		aria-labelledby="headingLetteraIncarico" style="text-align: justify;">
																		<jsp:include
																			page="/subviews/incarico/letteraIncaricoDettaglio.jsp">
																		</jsp:include>
																		<c:if test="${ not empty incaricoDettaglioView.letteraFirmataDoc }">
																			<div id="letteraFirmataDiv" style="display: block;">
																			</c:if>
																			<c:if test="${ empty incaricoDettaglioView.letteraFirmataDoc }">
																			<div id="letteraFirmataDiv" style="display: block;">
																			</c:if>
																				<jsp:include page="/subviews/incarico/letteraIncaricoFirmataDettaglio.jsp">
																				</jsp:include>
																			</div>
																	</div>
																</div>
																<!-- NOTA PROPROSTA INCARICO -->
																<div class="panel panel-default">
																	<div class="panel-heading" role="tab"
																		id="headingNotaPropostaIncarico">
																		<h4 class="panel-title">
																			<button type="button" data-toggle="collapse"
																				data-target="#boxNotaPropostaIncarico"
																				class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini collapsed"
																				style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																				aria-expanded="false">
																				<i class="zmdi zmdi-collection-text icon-mini"></i>
																			</button>
																			<div id="NotaPropostaDownload">
																			</div>
																			<div id="NotaPropostaFirmataDownload"></div>
																			<a data-toggle="collapse" data-parent="#accordion"
																				href="#boxNotaPropostaIncarico"
																				aria-expanded="false"
																				aria-controls="boxNotaPropostaIncarico"> <spring:message
																					text="??incarico.label.notaPropostaIncarico??"
																					code="incarico.label.notaPropostaIncarico" />
																			</a>
																		</h4>
																	</div>
																	<div id="boxNotaPropostaIncarico"
																		class="panel-collapse collapse" role="tabpanel"
																		aria-labelledby="headingNotaPropostaIncarico">
																		<jsp:include
																			page="/subviews/incarico/notaPropostaIncaricoDettaglio.jsp">
																		</jsp:include>
																		<c:if test="${ not empty incaricoDettaglioView.letteraFirmataDocNota}">
																			<div id="notaFirmataDiv" style="display: block;">
																			</c:if>
																			<c:if test="${ empty incaricoDettaglioView.letteraFirmataDocNota }">
																			<div id="notaFirmataDiv" style="display: block;">
																			</c:if>
																				<jsp:include page="/subviews/incarico/notaPropostaFirmataDettaglio.jsp">
																				</jsp:include>
																			</div>
																	</div>
																</div>

																<!-- PROCURA -->
																<div class="panel panel-default">
																	<div class="panel-heading" role="tab"
																		id="headingProcura">
																		<h4 class="panel-title">
																			<button type="button" data-toggle="collapse"
																				data-target="#boxProcura"
																				class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini collapsed"
																				style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																				aria-expanded="false">
																				<i class="zmdi zmdi-collection-text icon-mini"></i>
																			</button>
																			<a id="procuraGraffa" style="display:none;float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																				aria-expanded="true"> <i
																				class="fa fa-paperclip "></i>
																			</a>
																			<a data-toggle="collapse" data-parent="#accordion"
																				href="#boxProcura" aria-expanded="false"
																				aria-controls="boxProcura"> <spring:message
																					text="??incarico.label.procura??"
																					code="incarico.label.procura" />
																			</a>
																		</h4>
																	</div>
																	<div id="boxProcura" class="panel-collapse collapse"
																		role="tabpanel" aria-labelledby="headingProcura">
																		<jsp:include
																			page="/subviews/incarico/procuraDettaglio.jsp">
																		</jsp:include>
																	</div>
																</div>


																<!-- VERIFICA ANTICORRUZIONE -->
																<div class="panel panel-default">
																	<div class="panel-heading" role="tab"
																		id="headingVerificaAnticorruzione">
																		<h4 class="panel-title">
																			<button type="button" data-toggle="collapse"
																				data-target="#boxVerificaAnticorruzione"
																				class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini collapsed"
																				style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																				aria-expanded="false">
																				<i class="zmdi zmdi-collection-text icon-mini"></i>
																			</button>
																			<a id="verificaAnticorruzioneGraffa" style="display:none;float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																				aria-expanded="true"> <i
																				class="fa fa-paperclip "></i>
																			</a>
																			<a data-toggle="collapse" data-parent="#accordion"
																				href="#boxVerificaAnticorruzione"
																				aria-expanded="false"
																				aria-controls="boxVerificaAnticorruzione"> <spring:message
																					text="??incarico.label.verificaAnticorruzione??"
																					code="incarico.label.verificaAnticorruzione" />
																			</a>
																		</h4>
																	</div>
																	<div id="boxVerificaAnticorruzione"
																		class="panel-collapse collapse" role="tabpanel"
																		aria-labelledby="headingVerificaAnticorruzione">
																		<jsp:include
																			page="/subviews/incarico/verificaAnticorruzioneDettaglio.jsp">
																		</jsp:include>
																	</div>
																</div>

																<!-- VERIFICA PARTI CORRELATE -->
																<div class="panel panel-default">
																	<div class="panel-heading" role="tab"
																		id="headingVerificaPartiCorrelate">
																		<h4 class="panel-title">
																			<button type="button" data-toggle="collapse"
																				data-target="#boxVerificaPartiCorrelate"
																				class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini collapsed"
																				style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																				aria-expanded="false">
																				<i class="zmdi zmdi-collection-text icon-mini"></i>
																			</button>
																			<a id="verificaPartiCorrelateGraffa" style="display:none;float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																				aria-expanded="true"> <i
																				class="fa fa-paperclip "></i>
																			</a>
																			<a data-toggle="collapse" data-parent="#accordion"
																				href="#boxVerificaPartiCorrelate"> <spring:message
																					text="??incarico.label.verificaPartiCorrelate??"
																					code="incarico.label.verificaPartiCorrelate" />
																			</a>
																		</h4>
																	</div>
																	<div id="boxVerificaPartiCorrelate"
																		class="panel-collapse collapse" role="tabpanel"
																		aria-labelledby="headingVerificaPartiCorrelate">
																		<jsp:include
																			page="/subviews/incarico/verificaPartiCorrelateDettaglio.jsp">
																		</jsp:include>
																	</div>
																</div>

																<!-- LISTE DI RIFERIMENTO -->
																<div class="panel panel-default">
																	<div class="panel-heading" role="tab"
																		id="headingListeRiferimento">
																		<h4 class="panel-title">
																			<button type="button" data-toggle="collapse"
																				data-target="#boxListeRiferimento"
																				class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini collapsed"
																				style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																				aria-expanded="false">
																				<i class="zmdi zmdi-collection-text icon-mini"></i>
																			</button>
																			<a id="listeRiferimentoGraffa" style="display:none;float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																				aria-expanded="true"> <i
																				class="fa fa-paperclip "></i>
																			</a>
																			<a data-toggle="collapse" data-parent="#accordion"
																				href="#boxListeRiferimento"> <spring:message
																					text="??incarico.label.listeRiferimento??"
																					code="incarico.label.listeRiferimento" />
																			</a>
																		</h4>
																	</div>
																	<div id="boxListeRiferimento"
																		class="panel-collapse collapse" role="tabpanel"
																		aria-labelledby="headingListeRiferimento">
																		<jsp:include
																			page="/subviews/incarico/listeRiferimentoDettaglio.jsp">
																		</jsp:include>
																	</div>
																</div>
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
																			<a id="allegatiGenericiGraffa" style="display:none;float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																				aria-expanded="true"> <i
																				class="fa fa-paperclip "></i>
																			</a>
																			<a data-toggle="collapse" data-parent="#accordion"
																				href="#boxAllegatiGenerici"> <spring:message
																					text="??incarico.label.allegatiGenerici??"
																					code="incarico.label.allegatiGenerici" />
																			</a>
																		</h4>
																	</div>
																	<div id="boxAllegatiGenerici"
																		class="panel-collapse collapse" role="tabpanel"
																		aria-labelledby="headingAllegatiGenerici">
																		<jsp:include page="/subviews/incarico/allegatiGenericiDettaglio.jsp"></jsp:include>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

								</form:form>
								
								<div class="col-md-12" style="position:absolute;margin-top:-120px;width:100%;">

								
								<legarc:isAuthorized idEntita="${incaricoDettaglioView.incaricoId }" tipoEntita="<%=Costanti.TIPO_ENTITA_INCARICO %>"
									 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
								
									<button form="incaricoForm"
										onclick="modificaIncarico(${incaricoDettaglioView.incaricoId})"
										class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float" style="position: relative !important;float: right;margin-right: 17px;">
										<i class="zmdi zmdi-edit"></i>
									</button>
								</legarc:isAuthorized>
								
								<c:if test="${ incaricoDettaglioView.statoIncaricoCode eq 'A' }">
									<button
										onclick="richiediProforma(${incaricoDettaglioView.incaricoId}); "
										class="btn palette-Green-SNAM bg"
										style="position: absolute;bottom: 10px;right: 108px;">
										<spring:message text="??incarico.label.richiediProforma??"
											code="incarico.label.richiediProforma" />
									</button>
								</c:if>
								
								
								<jsp:include page="avviaWFIncarico.jsp"></jsp:include>
								
								
								</div>							
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


	<script	src="<%=request.getContextPath()%>/portal/js/controller/incarico.js"></script>

	<!-- DARIO ****************************************************************************************** -->
    <script	src="<%=request.getContextPath()%>/portal/js/controller/lista_assegnatari.js"></script>
	<!-- ************************************************************************************************ -->

<script type="text/javascript">
		if (document.getElementById("descrizioneProtocollo")) {
			CKEDITOR.replace('descrizioneProtocollo');
		}
		
		if( document.getElementById("infoCompenso") ){
			   CKEDITOR.replace( 'infoCompenso' );
		}
		
		if( document.getElementById("infoCompensoNotaProp") ){
			   CKEDITOR.replace( 'infoCompensoNotaProp' );
		}
			
		if( document.getElementById("attivita") ){
				   CKEDITOR.replace( 'attivita' );
		}
		
		<%-- DARIO **********************************************
		
			$('#panelConfirmAvviaWorkFlowIncarico').on('show.bs.modal',function(e) {
			
			
			$(this).find("#btnRichiediAvvioWorkflowIncarico").attr('onclick','avviaWorkFlowIncaricoDaForm('+ <%= request.getParameter("id") %>+ ')'); 
			
			
		}); --%>
		$('#panelConfirmAvviaWorkFlowIncarico').on(
				'show.bs.modal',
				function(e) {
					
					gestisci_tasto_confirm_workflow($(this), function(resp_code){
						
						avviaWorkFlowIncaricoDaForm(<%= request.getParameter("id") %> , resp_code);
						
					});
					
					
					
				});	
		
		
		

	</script>
</body>
</html>
