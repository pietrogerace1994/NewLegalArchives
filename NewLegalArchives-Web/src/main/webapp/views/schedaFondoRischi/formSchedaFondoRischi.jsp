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
									<a href="<%=request.getContextPath()%>/fascicolo/contenuto.action?CSRFToken=<%= request.getParameter("CSRFToken") %>&id=${schedaFondoRischiView.fascicoloRiferimento.vo.id }">Fascicolo ${schedaFondoRischiView.fascicoloRiferimento.vo.nome }</a>
								</div>
								<!-- FINE Aggiunta breadcrumb MASSIMO CARUSO -->
								<c:if
									test="${ schedaFondoRischiView.schedaFondoRischiId == null || schedaFondoRischiView.schedaFondoRischiId == 0 }">
									<h2>
										<spring:message text="??schedaFondoRischi.label.nuovaScheda??"
											code="schedaFondoRischi.label.nuovaScheda" />
									</h2>
								</c:if>
								<c:if
									test="${ schedaFondoRischiView.schedaFondoRischiId != null && schedaFondoRischiView.schedaFondoRischiId > 0 }">
									<h2>
										<spring:message text="??schedaFondoRischi.label.modificaScheda??"
											code="schedaFondoRischi.label.modificaScheda" />
									</h2>
								</c:if>
							</div>
							<div class="card-body">

								<form:form name="schedaFondoRischiForm" method="post"
									modelAttribute="schedaFondoRischiView" action="salva.action"
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

									<form:hidden path="schedaFondoRischiId" />
									<form:hidden path="op" id="op" value="salvaSchedaFondoRischi" />
									<form:hidden path="timeout"  id="timeout"/>

									<div class="list-group lg-alt">
										<!--NOME FASCICOLO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="fascicolo" class="col-sm-2 control-label"><spring:message
															text="??schedaFondoRischi.label.fascicolo??"
															code="schedaFondoRischi.label.fascicolo" /></label>
													<div class="col-sm-10">
														<form:input path="nomeFascicolo" cssClass="form-control"
															readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div class="list-group lg-alt">
										<!-- DATA CREAZIONE -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="dataCreazione" class="col-sm-2 control-label"><spring:message
															text="??schedaFondoRischi.label.dataCreazione??"
															code="schedaFondoRischi.label.dataCreazione" /></label>
													<div class="col-sm-10">
														<input type="text" class="form-control"   
														value="${schedaFondoRischiView.dataCreazione}" disabled="true"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<c:if test="${schedaFondoRischiView.schedaFondoRischiId != null && schedaFondoRischiView.schedaFondoRischiId > 0 }">
										
										<div class="list-group lg-alt">
											<!--STATO SCHEDA FONDO RISCHI-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for=stato class="col-sm-2 control-label"><spring:message
																text="??schedaFondoRischi.label.stato??"
																code="schedaFondoRischi.label.stato" /></label>
														<div class="col-sm-10">
															<form:input path="statoSchedaFondoRischi" cssClass="form-control"
																readonly="true" />
														</div>
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<!-- TIPOLOGIA -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="tipologia" class="col-sm-2 control-label"><spring:message
																text="??schedaFondoRischi.label.tipologia??"
																code="schedaFondoRischi.label.tipologia" /></label>
														<div class="col-sm-10">
															<input type="text" class="form-control"   
															value="${schedaFondoRischiView.tipologiaSchedaSelezionata.vo.descrizione}" disabled="true"/>
														</div>
													</div>
												</div>
											</div>
										</div>
									</c:if>
									
									<c:if test="${schedaFondoRischiView.schedaFondoRischiId == null || schedaFondoRischiView.schedaFondoRischiId == 0 }">
										
										<div class="list-group lg-alt">
											<!-- TIPOLOGIA-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="tipologia" class="col-sm-2 control-label"><spring:message
																text="??schedaFondoRischi.label.tipologia??" code="schedaFondoRischi.label.tipologia"/></label>
														<div class="col-sm-10">
															<form:select id="tipologiaSchedaFondoRischiCode" path="tipologiaSchedaFondoRischiCode"
																cssClass="form-control">
																<form:option value="">
																	<spring:message text="??schedaFondoRischi.label.selezionaTipologia??"
																		code="schedaFondoRischi.label.selezionaTipologia" />
																</form:option>
																<c:if test="${ schedaFondoRischiView.listaTipologiaScheda != null }">
																	<c:forEach items="${schedaFondoRischiView.listaTipologiaScheda}" var="oggetto">
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
										</div>
									</c:if>
										
									<!-- CONTROPARTE -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="controparte" class="col-sm-2 control-label"><spring:message
																text="??schedaFondoRischi.label.controparte??"
																code="schedaFondoRischi.label.controparte" /></label>
														<div class="col-sm-10">
													<div class="table-responsive" style="clear: both;">
														<table class="table table-striped table-responsive">
															<thead>
																<tr style="border: 1px solid #e0e0e0">
																	<th data-column-id="01" style="width: 50px">
																		<button type="button" data-toggle="collapse"
																			data-target="#boxControparte"
																			class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																			style="float: left; position: relative !important;">
																			<i class="zmdi zmdi-collection-text icon-mini"></i>
																		</button>
																	</th>
																	<th data-column-id="id"><spring:message
																			text="??schedaFondoRischi.label.controparte??"
																			code="schedaFondoRischi.label.controparte" /></th>

																</tr>
															</thead>
															<tbody id="#boxControparte" class="collapse in">
																<c:if
																	test="${ not empty schedaFondoRischiView.listaControparteDesc  }">
																	<c:forEach
																		items="${schedaFondoRischiView.listaControparteDesc}"
																		var="oggetto">
																		<tr>
																			<td colspan="2">${oggetto}</td>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if
																	test="${   empty schedaFondoRischiView.listaControparteDesc }">
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

									<!-- SOCIETA DI ADDEBITO-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="societa" class="col-sm-2 control-label"><spring:message
														text="??fascicolo.label.societaAddebito??"
														code="fascicolo.label.societaAddebito" /></label>
												<div class="col-sm-10">
													<div class="table-responsive" style="clear: both;">
														<table class="table table-striped table-responsive">
															<thead>
																<tr style="border: 1px solid #e0e0e0">
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
																	test="${ not empty schedaFondoRischiView.listaSocietaAddebitoAggiunteDesc  }">
																	<c:forEach
																		items="${schedaFondoRischiView.listaSocietaAddebitoAggiunteDesc}"
																		var="oggetto">
																		<tr>
																			<td colspan="2">${oggetto}</td>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if
																	test="${   empty schedaFondoRischiView.listaSocietaAddebitoAggiunteDesc }">
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
									
									<!-- GIUDIZIO INSTAURATO -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="giudizio" class="col-sm-2 control-label"><spring:message
														text="??schedaFondoRischi.label.giudizioInstaurato??"
														code="schedaFondoRischi.label.giudizioInstaurato" /></label>
												<div class="col-sm-10">
													<div class="table-responsive" style="clear: both;">
														<table class="table table-striped table-responsive">
															<thead>
																<tr style="border: 1px solid #e0e0e0">
																	<th data-column-id="01" style="width: 50px">
																		<button type="button" data-toggle="collapse"
																			data-target="#boxGiudizio"
																			class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																			style="float: left; position: relative !important;">
																			<i class="zmdi zmdi-collection-text icon-mini"></i>
																		</button>
																	</th>
																	<th data-column-id="id"><spring:message
																			text="??schedaFondoRischi.label.giudizioInstaurato??"
																			code="schedaFondoRischi.label.giudizioInstaurato" /></th>

																</tr>
															</thead>
															<tbody id="boxGiudizio" class="collapse in">
																<c:if
																	test="${ not empty schedaFondoRischiView.listaGiudizioDesc  }">
																	<c:forEach
																		items="${schedaFondoRischiView.listaGiudizioDesc}"
																		var="oggetto">
																		<tr>
																			<td colspan="2">${oggetto}</td>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if
																	test="${   empty schedaFondoRischiView.listaGiudizioDesc }">
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
									
									<!-- ORGANO GIUDICANTE -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="organoGiudicante" class="col-sm-2 control-label"><spring:message
														text="??schedaFondoRischi.label.organoGiudicante??"
														code="schedaFondoRischi.label.organoGiudicante" /></label>
												<div class="col-sm-10">
													<div class="table-responsive" style="clear: both;">
														<table class="table table-striped table-responsive">
															<thead>
																<tr style="border: 1px solid #e0e0e0">
																	<th data-column-id="01" style="width: 50px">
																		<button type="button" data-toggle="collapse"
																			data-target="#boxOrganoGiudicante"
																			class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																			style="float: left; position: relative !important;">
																			<i class="zmdi zmdi-collection-text icon-mini"></i>
																		</button>
																	</th>
																	<th data-column-id="id"><spring:message
																			text="??schedaFondoRischi.label.organoGiudicante??"
																			code="schedaFondoRischi.label.organoGiudicante" /></th>

																</tr>
															</thead>
															<tbody id="boxOrganoGiudicante" class="collapse in">
																<c:if
																	test="${ not empty schedaFondoRischiView.listaOrganoGiudicanteDesc  }">
																	<c:forEach
																		items="${schedaFondoRischiView.listaOrganoGiudicanteDesc}"
																		var="oggetto">
																		<tr>
																			<td colspan="2">${oggetto}</td>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if
																	test="${   empty schedaFondoRischiView.listaOrganoGiudicanteDesc }">
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
									
									<div class="list-group lg-alt">
										<!--VALORE DELLA DOMANDA-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="valoreDomanda" class="col-sm-2 control-label"><spring:message
															text="??schedaFondoRischi.label.valoreDomanda??"
															code="schedaFondoRischi.label.valoreDomanda" /></label>
													<div class="col-sm-10">
														<form:input id="txtValoreDomanda" path="valoreDomanda" class="form-control" value=""/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									
									<!-- PROFESSIONISTI -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="professionistaEsterno" class="col-sm-2 control-label"><spring:message
														text="??schedaFondoRischi.label.professionista??"
														code="schedaFondoRischi.label.professionista" /></label>
												<div class="col-sm-10">
													<div class="table-responsive" style="clear: both;">
														<table class="table table-striped table-responsive">
															<thead>
																<tr style="border: 1px solid #e0e0e0">
																	<th data-column-id="01" style="width: 50px">
																		<button type="button" data-toggle="collapse"
																			data-target="#boxPrefessionistaEsterno"
																			class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																			style="float: left; position: relative !important;">
																			<i class="zmdi zmdi-collection-text icon-mini"></i>
																		</button>
																	</th>
																	<th data-column-id="id"><spring:message
																			text="??schedaFondoRischi.label.professionista??"
																			code="schedaFondoRischi.label.professionista" /></th>

																</tr>
															</thead>
															<tbody id="boxPrefessionistaEsterno" class="collapse in">
																<c:if
																	test="${ not empty schedaFondoRischiView.listaProfessionistiEsterni  }">
																	<c:forEach
																		items="${schedaFondoRischiView.listaProfessionistiEsterni}"
																		var="oggetto">
																		<tr>
																			<td colspan="2">${oggetto.vo.studioLegale.denominazione} ${oggetto.vo.cognomeNome}</td>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if
																	test="${   empty schedaFondoRischiView.listaProfessionistiEsterni }">
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
									
									<div class="list-group lg-alt">
										<!--DATA RICHIESTA AUTORIZZAZIONE-->
										<div class="list-group-item media">
											<div class="media-body media-body-datetimepiker">
												<div class="form-group">
													<label class="col-md-2 control-label" for="selectbasic"><spring:message
															text="??schedaFondoRischi.label.dataRichiestaAutorizzazione??"
															code="schedaFondoRischi.label.dataRichiestaAutorizzazione" /></label>
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
															text="??schedaFondoRischi.label.dataAutorizzazione??"
															code="schedaFondoRischi.label.dataAutorizzazione" /></label>
													<div class="col-md-10">
														<form:input id="txtDataAutorizzazione"
															path="dataAutorizzazione"
															cssClass="form-control date-picker" readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<!-- TESTO ESPLICATIVO-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="testoEsplicativo" class="col-sm-2 control-label"><spring:message
														text="??schedaFondoRischi.label.testoEsplicativo??"
														code="schedaFondoRischi.label.testoEsplicativo" /></label>
												<div class="col-sm-10">
													<form:textarea path="testoEsplicativo" cols="70" rows="5"
														cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>
									
									<div class="list-group lg-alt">
										<!-- RISCHIO SOCCOMBENZA -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="rischioSoccombenza" class="col-sm-2 control-label"><spring:message
															text="??schedaFondoRischi.label.rischioSoccombenza??" code="schedaFondoRischi.label.rischioSoccombenza"/></label>
													<div class="col-sm-10">
														<form:select id="rischioSoccombenzaCode" path="rischioSoccombenzaCode"
															cssClass="form-control">
															<form:option value="">
																<spring:message text="??schedaFondoRischi.label.selezionaRischioSoccombenza??"
																	code="schedaFondoRischi.label.selezionaRischioSoccombenza" />
															</form:option>
															<c:if test="${ schedaFondoRischiView.listaRischioSoccombenza != null }">
																<c:forEach items="${schedaFondoRischiView.listaRischioSoccombenza}" var="oggetto">
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
									</div>
									
									<!-- COPERTURA ASSICURATIVA -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="coperturaAssicurativa" class="col-sm-2 control-label"><spring:message
														text="??schedaFondoRischi.label.coperturaAssicurativa??"
														code="schedaFondoRischi.label.coperturaAssicurativa" /></label>
												<div class="col-sm-2">
													<form:select id="coperturaAssicurativaFlag" path="coperturaAssicurativaFlag"
														cssClass="form-control">
														<c:if test="${schedaFondoRischiView.schedaFondoRischiId == null || schedaFondoRischiView.schedaFondoRischiId == 0 }">
															<form:option value="">
																<spring:message text="??schedaFondoRischi.label.seleziona??"
																	code="schedaFondoRischi.label.seleziona" />
															</form:option>
														</c:if>
														<form:option value="1">
															<c:out value="Si"></c:out>
														</form:option>
														<form:option value="0">
															<c:out value="No"></c:out>
														</form:option>
													</form:select>
												</div>
												<div id="coperturaAssicurativaValue" class="col-sm-8" style="display: none;">
													<form:input path="coperturaAssicurativa" cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>
									
									<!-- MANLEVA -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="manleva" class="col-sm-2 control-label"><spring:message
														text="??schedaFondoRischi.label.manleva??"
														code="schedaFondoRischi.label.manleva" /></label>
												<div class="col-sm-2">
													<form:select id="manlevaFlag" path="manlevaFlag"
														cssClass="form-control">
														<c:if test="${schedaFondoRischiView.schedaFondoRischiId == null || schedaFondoRischiView.schedaFondoRischiId == 0 }">
															<form:option value="">
																<spring:message text="??schedaFondoRischi.label.seleziona??"
																	code="schedaFondoRischi.label.seleziona" />
															</form:option>
														</c:if>
														<form:option value="1">
															<c:out value="Si"></c:out>
														</form:option>
														<form:option value="0">
															<c:out value="No"></c:out>
														</form:option>
													</form:select>
												</div>
												<div id="manlevaValue" class="col-sm-8" style="display: none;">
													<form:input path="manleva" cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>
									
									<!-- COMMESSA DI INVESTIMENTO -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="commessaInvestimento" class="col-sm-2 control-label"><spring:message
														text="??schedaFondoRischi.label.commessaInvestimento??"
														code="schedaFondoRischi.label.commessaInvestimento" /></label>
												<div class="col-sm-2">
													<form:select id="commessaDiInvestimentoFlag" path="commessaDiInvestimentoFlag"
														cssClass="form-control">
														<c:if test="${schedaFondoRischiView.schedaFondoRischiId == null || schedaFondoRischiView.schedaFondoRischiId == 0 }">
															<form:option value="">
																<spring:message text="??schedaFondoRischi.label.seleziona??"
																	code="schedaFondoRischi.label.seleziona" />
															</form:option>
														</c:if>
														<form:option value="1">
															<c:out value="Si"></c:out>
														</form:option>
														<form:option value="0">
															<c:out value="No"></c:out>
														</form:option>
													</form:select>
												</div>
												<div id="commessaDiInvestimentoValue" class="col-sm-8" style="display: none;">
													<form:input path="commessaDiInvestimento" cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>
									
									<!-- PASSIVITA' STIMATA -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="passivitaStimata" class="col-sm-2 control-label"><spring:message
														text="??schedaFondoRischi.label.passivitaStimata??"
														code="schedaFondoRischi.label.passivitaStimata" /></label>
												<div class="col-sm-10">
													<form:input path="passivitaStimata" cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>
									
									<!-- MOTIVAZIONE -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="motivazione" class="col-sm-2 control-label"><spring:message
														text="??schedaFondoRischi.label.motivazione??"
														code="schedaFondoRischi.label.motivazione" /></label>
												<div class="col-sm-10">
													<form:textarea path="motivazione" cols="70" rows="5"
														cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>
									
									<c:if test="${schedaFondoRischiView.schedaFondoRischiId != null && schedaFondoRischiView.schedaFondoRischiId > 0 }">

										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="allegati" class="col-sm-2 control-label"><spring:message
															text="??schedaFondoRischi.label.allegatiLegaleEsterno??"
															code="schedaFondoRischi.label.allegatiLegaleEsterno" /></label>
													<div class="col-sm-10">
														<div class="list-group-item media">
															<div class="media-body">
																<div id="accordion" role="tablist"
																	aria-multiselectable="true">
																	
																	<!-- ALLEGATI PROSEFFIONISTA ESTERNO -->
																	<div class="panel panel-default">
																		<div class="panel-heading" role="tab"
																			id="headingAllegatiProfessionista">
																			<h4 class="panel-title">
																				<button type="button" data-toggle="collapse"
																					data-target="#boxAllegatiProfessionista"
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
																					href="#boxAllegatiProfessionista"> <spring:message
																						text="??schedaFondoRischi.label.allegaComunicazioneLegaleEsterno??"
																						code="schedaFondoRischi.label.allegaComunicazioneLegaleEsterno" />
																				</a>

																			</h4>
																		</div>
																		<div id="boxAllegatiProfessionista"
																			class="panel-collapse collapse" role="tabpanel"
																			aria-labelledby="headingAllegatiProfessionista">
																			<jsp:include
																				page="/subviews/schedaFondoRischi/allegatiProfessionista.jsp"></jsp:include>

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

									<button form="schedaFondoRischiForm" onclick="salvaScheda()"
										class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float"
										style="position: relative !important;float: right;margin-right: 17px;">
										<i class="zmdi zmdi-save"></i>
									</button>
									
									<jsp:include page="avviaWFScheda.jsp"></jsp:include>
									
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
		src="<%=request.getContextPath()%>/portal/js/controller/schedaFondoRischi.js"></script>

	<!-- DARIO ****************************************************************************************** -->
    <script	src="<%=request.getContextPath()%>/portal/js/controller/lista_assegnatari.js"></script>
	<!-- ************************************************************************************************ -->
		
	<script type="text/javascript">
		/*
		// funzione per mostrare il modal racchiuso nel boxAllegati
		// Apre il boxAllegati e visualizza il modal per caricare un nuovo allegato
		 */
		function openModalAllegatoGenerico() {
			$("#boxAllegatiProfessionista").collapse('show');
			$("#panelAggiungiAllegatoProfessionista").modal('show');
		}
		
		<%-- DARIO *********
		$('#panelConfirmAvviaWorkFlowSchedaFondoRischi').on('show.bs.modal',function(e) {
			$(this).find("#btnRichiediAvvioWorkflowSchedaFR").attr('onclick','avviaWorkFlowSchedaFRDaForm('+ <%= request.getParameter("id") %>+ ')');
		}); --%>
		$('#panelConfirmAvviaWorkFlowSchedaFondoRischi').on(
				'show.bs.modal',
				function(e) {
					
					gestisci_tasto_confirm_workflow($(this), function(resp_code){
						
						avviaWorkFlowSchedaFRDaForm(<%= request.getParameter("id") %> , resp_code);
						
					});
					
					
				});	
		
		/* ******************************** */
		
	</script>
</body>
</html>
