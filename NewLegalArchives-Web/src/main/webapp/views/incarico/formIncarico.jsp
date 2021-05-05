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
								<!-- Aggiunta breadcrumb MASSIMO CARUSO -->
								<div class="card">
									<a href="<%=request.getContextPath()%>/fascicolo/contenuto.action?CSRFToken=<%= request.getParameter("CSRFToken") %>&id=${incaricoView.fascicoloRiferimento.vo.id }">Fascicolo ${incaricoView.fascicoloRiferimento.vo.nome }</a>
								</div>
								<!-- FINE Aggiunta breadcrumb MASSIMO CARUSO -->
								<c:if
									test="${ incaricoView.incaricoId == null || incaricoView.incaricoId == 0 }">
									<h2>
										<spring:message text="??incarico.label.nuovoIncarico??"
											code="incarico.label.nuovoIncarico" />
									</h2>
								</c:if>
								<c:if
									test="${ incaricoView.incaricoId != null && incaricoView.incaricoId > 0 }">
									<h2>
										<spring:message text="??incarico.label.modificaIncarico??"
											code="incarico.label.modificaIncarico" />
									</h2>
								</c:if>
							</div>
							<div class="card-body">

								<form:form name="incaricoForm" method="post"
									modelAttribute="incaricoView" action="salva.action"
									class="form-horizontal la-form">
									<engsecurity:token regenerate="false"/>
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

									<div id="sezioneMessaggiApplicativi"></div>

									<form:hidden path="incaricoId" />
									<form:hidden path="op" id="op" value="salvaIncarico" />
									<form:hidden path="letteraIncaricoId"  id="idLettera"/>
									<form:hidden path="notaIncaricoId"  id="idNotaProp"/>
									<form:hidden path="timeout"  id="timeout"/>
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
									<c:if
										test="${incaricoView.incaricoId != null && incaricoView.incaricoId > 0 }">
										<div class="list-group lg-alt">
											<!--NOME INCARICO-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="fascicolo" class="col-sm-2 control-label"><spring:message
																text="??incarico.label.nome??"
																code="incarico.label.nome" /></label>
														<div class="col-sm-10">
															<form:input path="nomeIncarico" cssClass="form-control"
																readonly="true" />
														</div>
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<!--STATO FASCICOLO-->
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
									</c:if>

									<!-- SOCIETA DI ADDEBITO-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="societaAddebito" class="col-sm-2 control-label"><spring:message
														text="??fascicolo.label.societaAddebito??"
														code="fascicolo.label.societaAddebito" /></label>
												<div class="col-sm-10">
													<div class="table-responsive" style="clear: both;border: 1px solid #e0e0e0;">
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
																	test="${ not empty incaricoView.listaSocietaAddebitoAggiunteDesc  }">
																	<c:forEach
																		items="${incaricoView.listaSocietaAddebitoAggiunteDesc}"
																		var="oggetto">
																		<tr>
																			<td colspan="2">${oggetto}</td>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if
																	test="${   empty incaricoView.listaSocietaAddebitoAggiunteDesc }">
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
									
									<c:if test="${incaricoView.incaricoId != null && incaricoView.incaricoId > 0 }">
										<!-- NAZIONE E SPECIALIZZAZIONE-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nazioneCode" class="col-sm-2 control-label"><spring:message
															text="??incarico.label.nazione??" code="incarico.label.nazione" /></label>
													<div class="col-sm-4">
														<input type="text" class="form-control"  value="${incaricoView.nazione.vo.descrizione}" disabled="true"/>
													</div>
													<label for="specializzazioneCode" class="col-sm-2 control-label"><spring:message
															text="??incarico.label.specializzazione??" code="incarico.label.specializzazione" /></label>
													<div class="col-sm-4">
														<input type="text" class="form-control"  value="${incaricoView.specializzazione.vo.descrizione}" disabled="true"/>
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
															value="${incaricoView.professionistaSelezionato.vo.studioLegale.denominazione} ${incaricoView.professionistaSelezionato.vo.cognomeNome}" disabled="true"/>
														</div>
													</div>
												</div>
											</div>
										</div>
									</c:if>
									<c:if test="${incaricoView.incaricoId == null || incaricoView.incaricoId == 0 }">
										<!-- NAZIONE E SPECIALIZZAZIONE-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nazioneCode" class="col-sm-2 control-label"><spring:message
															text="??incarico.label.nazione??" code="incarico.label.nazione" /></label>
													<div class="col-sm-4">
														<form:select id="nazioneCode" path="nazioneCode"
															cssClass="form-control">
															<form:option value="">
																<spring:message text="??incarico.label.selezionaNazione??"
																	code="incarico.label.selezionaNazione" />
															</form:option>
															<c:if test="${ incaricoView.listaNazioni != null }">
																<c:forEach items="${incaricoView.listaNazioni}" var="oggetto">
																	<c:if test="${ oggetto.vo.soloParteCorrelata eq 'F' }">
																		<form:option value="${ oggetto.vo.codGruppoLingua }">
																			<c:out value="${oggetto.vo.descrizione}"></c:out>
																		</form:option>
																	</c:if>
																</c:forEach>
															</c:if>
														</form:select>
													</div>
													<label for="specializzazioneCode" class="col-sm-2 control-label"><spring:message
															text="??incarico.label.specializzazione??" code="incarico.label.specializzazione" /></label>
													<div class="col-sm-4">
														<form:select id="specializzazioneCode" path="specializzazioneCode"
															cssClass="form-control">
															<form:option value="">
																<spring:message text="??incarico.label.selezionaSpecializzazione??"
																	code="incarico.label.selezionaSpecializzazione" />
															</form:option>
															<c:if test="${ incaricoView.listaSpecializzazioni != null }">
																<c:forEach items="${incaricoView.listaSpecializzazioni}" var="oggetto">
																	<form:option value="${ oggetto.vo.codGruppoLingua }">
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
											<!--PROFESSIONISTA-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="professionista" class="col-sm-2 control-label"><spring:message
																text="??incarico.label.professionista??"
																code="incarico.label.professionista" /></label>
														<div class="col-sm-10">
															<form:select path="professionistaId"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??incarico.label.selezionaProfessionista??"
																		code="incarico.label.selezionaProfessionista" />
																</form:option>
																<c:if
																	test="${ incaricoView.listaProfessionista != null }">
																	<c:forEach items="${incaricoView.listaProfessionista}"
																		var="oggetto">
																		<c:if
																			test="${oggetto.vo.statoEsitoValutazioneProf.codGruppoLingua eq 'TEVP_1'}">
																			<form:option value="${ oggetto.vo.id }">
																				<c:out
																					value="${oggetto.vo.studioLegale.denominazione} ${oggetto.cognomeNome}"></c:out>
																			</form:option>
																		</c:if>
																	</c:forEach>
																</c:if>
															</form:select>
														</div>
													</div>
												</div>
											</div>
										</div>
									</c:if>
									
									<c:if test="${ not empty incaricoView.listaProcure }">
									<!-- PROCURE-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="procure" class="col-sm-2 control-label"><spring:message
														text="??incarico.label.procure??"
														code="incarico.label.procure" /></label>
												<div class="col-sm-10">
													<div class="table-responsive" style="clear: both; border:1px solid #e0e0e0;">
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
																	test="${ not empty incaricoView.listaProcure }">
																	<tr>
																		<td colspan="2">PROCURATORE</td>
																		<td colspan="2">REPERTORIO</td>
																		<td colspan="2">TIPOLOGIA</td>
																		<td colspan="2">CONFERIMENTO</td>
																		<td colspan="2">REVOCA</td>
																	</tr>
																	<c:forEach
																		items="${incaricoView.listaProcure}"
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
																	test="${   empty incaricoView.listaProcure }">
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


									<!-- COMMENTO-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="commento" class="col-sm-2 control-label"><spring:message
														text="??incarico.label.commento??"
														code="incarico.label.commento" /></label>
												<div class="col-sm-10">
													<form:textarea path="commento" cols="70" rows="8"
														cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>

									<c:if
										test="${incaricoView.incaricoId != null && incaricoView.incaricoId > 0 }">






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
																			class="panel-collapse collapse" role="tabpanel"
																			aria-labelledby="headingLetteraIncarico" style="text-align: justify;">
																			<jsp:include
																				page="/subviews/incarico/letteraIncarico.jsp">
																			</jsp:include>
																			<c:if test="${ not empty incaricoView.letteraFirmataDoc}">
																			<div id="letteraFirmataDiv" style="display: block;">
																			</c:if>
																			<c:if test="${ empty incaricoView.letteraFirmataDoc }">
																			<div id="letteraFirmataDiv" style="display: block;">
																			</c:if>
																				<jsp:include page="/subviews/incarico/letteraIncaricoFirmata.jsp">
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
																				page="/subviews/incarico/notaPropostaIncarico.jsp">
																			</jsp:include>
																			<c:if test="${ not empty incaricoView.letteraFirmataDocNota}">
																			<div id="notaFirmataDiv" style="display: block;">
																			</c:if>
																			<c:if test="${ empty incaricoView.letteraFirmataDocNota }">
																			<div id="notaFirmataDiv" style="display: block;">
																			</c:if>
																				<jsp:include page="/subviews/incarico/notaPropostaFirmata.jsp">
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
																				<a id="procuraGraffa"
																					style="display: none; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																					aria-expanded="true"> <i
																					class="fa fa-paperclip "></i>
																				</a> <a data-toggle="collapse" data-parent="#accordion"
																					href="#boxProcura" aria-expanded="false"
																					aria-controls="boxProcura"> <spring:message
																						text="??incarico.label.procura??"
																						code="incarico.label.procura" />
																				</a>

																			</h4>
																		</div>
																		<div id="boxProcura" class="panel-collapse collapse"
																			role="tabpanel" aria-labelledby="headingProcura">
																			<jsp:include page="/subviews/incarico/procura.jsp">
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
																				<a id="verificaAnticorruzioneGraffa"
																					style="display: none; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																					aria-expanded="true"> <i
																					class="fa fa-paperclip "></i>
																				</a> <a data-toggle="collapse" data-parent="#accordion"
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
																				page="/subviews/incarico/verificaAnticorruzione.jsp">
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
																				<a id="verificaPartiCorrelateGraffa"
																					style="display: none; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																					aria-expanded="true"> <i
																					class="fa fa-paperclip "></i>
																				</a> <a data-toggle="collapse" data-parent="#accordion"
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
																				page="/subviews/incarico/verificaPartiCorrelate.jsp">
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
																				<a id="listeRiferimentoGraffa"
																					style="display: none; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																					aria-expanded="true"> <i
																					class="fa fa-paperclip "></i>
																				</a> <a data-toggle="collapse" data-parent="#accordion"
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
																				page="/subviews/incarico/listeRiferimento.jsp">
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

																				<button type="button" data-toggle="modal"
																					onclick="openModalAllegatoGenerico()"
																					class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																					style="float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																					aria-expanded="true">
																					<i class="zmdi zmdi-plus icon-mini"></i>
																				</button>

																				<a id="allegatiGenericiGraffa"
																					style="display: none; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																					aria-expanded="true"> <i
																					class="fa fa-paperclip "></i>
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
																				page="/subviews/incarico/allegatiGenerici.jsp"></jsp:include>

																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</c:if>
								</form:form>
								



								<div class="col-md-12"
									style="position: absolute; margin-top: -120px; width: 100%;">

									<button form="incaricoForm" onclick="salvaIncarico()"
										class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float"
										style="position: relative !important;float: right;margin-right: 17px;">
										<i class="zmdi zmdi-save"></i>
									</button>
									
								<c:if test="${ incaricoView.statoIncaricoCode eq 'A' }">
								<button onclick="richiediProforma(${incaricoView.incaricoId}); " class="btn palette-Green-SNAM bg" style="position: absolute;bottom: 10px;right: 108px;">
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

		/*
		// funzione per mostrare il modal racchiuso nel boxAllegati
		// Apre il boxAllegati e visualizza il modal per caricare un nuovo allegato
		 */
		function openModalAllegatoGenerico() {
			$("#boxAllegatiGenerici").collapse('show');
			$("#panelAggiungiAllegatoGenerico").modal('show');
		}
		
		<%-- DARIO *********************
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
		
		
		
		/* ******************** */
		
	</script>
</body>
</html>
