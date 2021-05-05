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
									<a href="<%=request.getContextPath()%>/fascicolo/contenuto.action?CSRFToken=<%= request.getParameter("CSRFToken") %>&id=${proformaDettaglioView.fascicoloRiferimento.vo.id }">Fascicolo ${proformaDettaglioView.fascicoloRiferimento.vo.nome }</a>
									/
									<a href="<%=request.getContextPath()%>/incarico/dettaglio.action?CSRFToken=<%= request.getParameter("CSRFToken") %>&id=${proformaDettaglioView.incaricoRiferimento.vo.id }">Incarico ${proformaDettaglioView.incaricoRiferimento.vo.nomeIncarico }</a>
								</div>
								<!-- FINE Aggiunta breadcrumb MASSIMO CARUSO -->
								<h2>
									<spring:message text="??proforma.label.dettaglioProforma??"
										code="proforma.label.dettaglioProforma" />
								</h2>
							</div>
							<div class="card-body">

								<form:form name="proformaForm" method="post"
									modelAttribute="proformaDettaglioView" action="salva.action"
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
									<form:hidden path="proformaId" />
									<form:hidden path="schedaValutazione.vo.id"  id="idSchedaValutazione"/>
									<form:hidden path="penale"  id="isp"/>
									<form:hidden path="timeout"  id="timeout"/>
									<form:hidden path="tipoProformaMod" id="tipoProformaMod" />
									<form:hidden path="isCPADisabled" id="isCPADisabled" />
									<form:hidden path="totaleForce" id="totaleForce" />
									
									<div class="list-group lg-alt">
										<!--NOME FASCICOLO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="fascicolo" class="col-sm-2 control-label"><spring:message
															text="??proforma.label.fascicolo??"
															code="proforma.label.fascicolo" /></label>
													<div class="col-sm-10">
														<input type="text" class="form-control" readonly
															value="${proformaDettaglioView.fascicoloRiferimento.vo.nome }" />
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
													<label for="nomeIncarico" class="col-sm-2 control-label"><spring:message
															text="??proforma.label.nomeIncarico??"
															code="proforma.label.nomeIncarico" /></label>
													<div class="col-sm-10">
														<input type="text" class="form-control" readonly
															value="${proformaDettaglioView.incaricoRiferimento.vo.nomeIncarico }" /> 
														<form:hidden path="incaricoId" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--LEGALE INTERNO INCARICO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="legaleInterno" class="col-sm-2 control-label"><spring:message
															text="??proforma.label.legaleInterno??"
															code="proforma.label.legaleInterno" /></label>
													<div class="col-sm-10">
													
														<input type="text" class="form-control" disabled name="legaleInternoDesc"
															value="${proformaDettaglioView.legaleInternoDesc }" />
														<input type="hidden" class="form-control" disabled name="legaleInterno"
															value="${proformaDettaglioView.legaleInterno }" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!-- UNITA LEGALE INCARICO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="unitaLegale" class="col-sm-2 control-label"><spring:message
															text="??proforma.label.unitaLegale??"
															code="proforma.label.unitaLegale" /></label>
													<div class="col-sm-10"> 
														<input type="text" class="form-control" disabled name="unitaLegaleDesc"
															value="${proformaDettaglioView.unitaLegaleDesc }" />
														<input type="hidden" class="form-control" disabled name="unitaLegale"
															value="${proformaDettaglioView.unitaLegale }" />
													</div>
												</div>
											</div>
										</div>
									</div>


									<div class="list-group lg-alt">
										<!-- PROFESSIONISTA ESTERNO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="professionistaEsterno"
														class="col-sm-2 control-label"><spring:message
															text="??proforma.label.professionistaEsterno??"
															code="proforma.label.professionistaEsterno" /></label>
													<div class="col-sm-10">

														<input type="text" class="form-control" readonly
															value="${proformaDettaglioView.professionistaEsterno }" />

													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--NUMERO PROFORMA-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??proforma.label.numero??"
															code="proforma.label.numero" /></label>
													<div class="col-sm-10">
														<form:input path="numero" cssClass="form-control"
															readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div> 
									<div class="list-group lg-alt">
										<!--NOME PROFORMA-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??proforma.label.nome??"
															code="proforma.label.nome" /></label>
													<div class="col-sm-10">
														<form:input path="nome" cssClass="form-control"
															readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div> 
									
									<div class="list-group lg-alt">
										<!--STATO PROFORMA-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for=stato class="col-sm-2 control-label"><spring:message
															text="??proforma.label.stato??"
															code="proforma.label.stato" /></label>
													<div class="col-sm-10">
														<form:input path="stato" cssClass="form-control"
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
																	test="${ not empty proformaDettaglioView.listaSocietaAddebitoAggiunte  }">
																	<c:forEach
																		items="${proformaDettaglioView.listaSocietaAddebitoAggiunte}"
																		var="oggetto">
																		<tr>
																			<td colspan="2">${oggetto.vo.ragioneSociale}"</td>																					
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if
																	test="${   empty proformaDettaglioView.listaSocietaAddebitoAggiunte }">
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
										<!--DATA INSERIMENTO -->
										<div class="list-group-item media">
											<div class="media-body media-body-datetimepiker">
												<div class="form-group">
													<label class="col-md-2 control-label" for="dataInserimento"><spring:message
															text="??proforma.label.dataInserimento??"
															code="proforma.label.dataInserimento" /></label>
													<div class="col-md-10">
														<form:input id="txtDataInserimento" path="dataInserimento"
															cssClass="form-control date-picker" readonly="true" />
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
															text="??proforma.label.dataRichiestaAutorizzazione??"
															code="proforma.label.dataRichiestaAutorizzazione" /></label>
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
										<!--ULTIMO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" for="ultimo"><spring:message
															text="??proforma.label.ultimo??"
															code="proforma.label.ultimo" /></label>
													<div class="col-md-2">
														<form:checkbox path="ultimo"
															disabled="true" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--VALUTA-->

										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" for="valuta"><spring:message
															text="??proforma.label.valuta??"
															code="proforma.label.valuta" /></label>
													<div class="col-md-10">
														<c:if test="${ not empty proformaDettaglioView.listaTipoValuta  }">
															<c:forEach items="${proformaDettaglioView.listaTipoValuta}"
																var="oggetto">
																<form:radiobutton disabled="true" path="valutaId"
																	value="${oggetto.vo.id }" label="${oggetto.vo.nome }"
																	 />
															</c:forEach>
														</c:if>
													</div>
												</div>
											</div>
										</div>
									</div>
									
																		
									<div class="list-group lg-alt">
										<!--TIPO PROFORMA-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group" id="formTipo">
													<label class="col-md-2 control-label" for="idEsito"><spring:message
															text="??proforma.label.tipo??" code="proforma.label.tipo" /></label>
													<div class="col-md-2">
														<select id="idTipoProforma" name="idTipoProforma"
															class="form-control" disabled="true">
															<c:if test="${ proformaDettaglioView.listaTipoProforma != null }">
																<c:forEach items="${proformaDettaglioView.listaTipoProforma}"
																	var="oggetto">
																	<option value="${ oggetto.vo.id }"
																		id="${oggetto.vo.codGruppoLingua }">
																		<c:out value="${oggetto.vo.descrizione}"></c:out>
																	</option>
																</c:forEach>
															</c:if>
														</select>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt" id="divDiritti">
										<!--DIRITTI-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" for="diritti"><spring:message
															text="??proforma.label.diritti??"
															code="proforma.label.diritti" /></label>
													<div class="col-md-10">
														<input type="number" name="diritti" id="diritti"
															value="${proformaDettaglioView.diritti }" class="form-control"
															readonly />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt" id="divOnorari">
										<!--ONORARI-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" for="onorari" id="labelOnorari"><spring:message
															text="??proforma.label.onorari??"
															code="proforma.label.onorari" /></label>
													<div class="col-md-10">
														<input type="number" name="onorari" id="onorari"
															value="${proformaDettaglioView.onorari }" class="form-control"
															readonly />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt" id="divSpeseImponibili">
										<!--SPESE IMPONIBILI-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" for="speseImponibili"><spring:message
															text="??proforma.label.speseImponibili??"
															code="proforma.label.speseImponibili" /></label>
													<div class="col-md-10">
														<input type="number" name="speseImponibili"  id="speseImponibili"
															value="${proformaDettaglioView.speseImponibili }"
															class="form-control" readonly  />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt" style="display:block" id="divCPA">
										<!--CPA-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" for="cpa"><spring:message
															text="??proforma.label.cpa??" code="proforma.label.cpa" /></label>
													<div class="col-md-10">
														<form:input type="number" path="cpa" readonly="true"
															class="form-control" />
													</div>
													<div class="col-md-4" style="visibility: hidden;">
														<form:checkbox path="disableCPA" id="disableCPA"
															onclick="ricalcola();" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt" id="divTotaleImponibile">
										<!--TOTALE IMPONIBILE-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label"
														for="totaleImponibile"><spring:message
															text="??proforma.label.totaleImponibile??"
															code="proforma.label.totaleImponibile" /></label>
													<div class="col-md-10">
														<form:input type="number" path="totaleImponibile"
															readonly="true" class="form-control" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt" id="divSpeseNonImponibile">
										<!--SPESE NON IMPONIBILE-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" id="labelSpeseNonImponibile"
														for="speseNonImponibili"><spring:message
															text="??proforma.label.speseNonImponibili??"
															code="proforma.label.speseNonImponibili" /></label>
													<div class="col-md-10">
														<input type="number" name="speseNonImponibili" id="speseNonImponibili"
															value="${proformaDettaglioView.speseNonImponibili }"
															class="form-control" readonly  />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--TOTALE DA AUTORIZZARE/TO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" for="totale"  id="labelTotale"><spring:message
															text="??proforma.label.totale??"
															code="proforma.label.totale" /></label>
													<div class="col-md-10">
														<form:input path="totale" 
															class="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--NOTE-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" for="note"><spring:message
															text="??proforma.label.note??" code="proforma.label.note" /></label>
													<div class="col-md-10">
														<form:input path="note" class="form-control"
															readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--ANNO ESERCIZIO FINANZIARIO-->
										<div class="list-group-item media">
											<div class="media-body media-body-datetimepiker">
												<div class="form-group">
													<label class="col-md-2 control-label"
														for="annoEsercizioFinanziario"><spring:message
															text="??proforma.label.annoEsercizioFinanziario??"
															code="proforma.label.annoEsercizioFinanziario" /></label>
													<div class="col-md-10">
														<form:input id="txtAnnoEsercizioFinanziario"
															readonly="true" path="annoEsercizioFinanziario"
															cssClass="form-control date-YYYY-picker" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--CENTRO DI COSTO-->
										<div class="list-group-item media">
											<div class="media-body ">
												<div class="form-group">
													<label class="col-md-2 control-label" for="centroDiCosto"><spring:message
															text="??proforma.label.centroDiCosto??"
															code="proforma.label.centroDiCosto" /></label>
													<div class="col-md-10">

														<div class="input-group dropdown">
															<form:input path="centroDiCosto" readonly="true"
																cssClass="form-control dropdown-toggle" />
															<ul class="dropdown-menu">
																<c:if
																	test="${ not empty proformaDettaglioView.listaCentroDiCosto  }">
																	<c:forEach items="${proformaDettaglioView.listaCentroDiCosto}"
																		var="oggetto">
																		<li><a href="#" data-value="${oggetto.vo.cdc }">${oggetto.vo.cdc }
																				- ${oggetto.vo.descrizione }</a></li>
																	</c:forEach>
																</c:if>
															</ul>
															<span role="button"
																class="input-group-addon dropdown-toggle"
																data-toggle="dropdown" aria-haspopup="true"
																aria-expanded="false"><span class="caret"></span></span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--VOCE DI CONTO-->
										<div class="list-group-item media">
											<div class="media-body ">
												<div class="form-group">
													<label class="col-md-2 control-label" for="voceDiConto"><spring:message
															text="??proforma.label.voceDiConto??"
															code="proforma.label.voceDiConto" /></label>
													<div class="col-md-10">

														<div class="input-group dropdown">
															<form:input path="voceDiConto" readonly="true"
																cssClass="form-control dropdown-toggle" />
															<ul class="dropdown-menu">
																<c:if
																	test="${ not empty proformaDettaglioView.listaVoceDiConto  }">
																	<c:forEach items="${proformaDettaglioView.listaVoceDiConto}"
																		var="oggetto">
																		<li><a href="#" data-value="${oggetto.vo.vdc }">${oggetto.vo.vdc }
																				- ${oggetto.vo.descrizione }</a></li>
																	</c:forEach>
																</c:if>
															</ul>
															<span role="button"
																class="input-group-addon dropdown-toggle"
																data-toggle="dropdown" aria-haspopup="true"
																aria-expanded="false"><span class="caret"></span></span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<c:if
										test="${proformaDettaglioView.proformaId != null && proformaDettaglioView.proformaId > 0 }">
										<div class="list-group lg-alt">
											<!--DATA RICHIESTA AUTORIZZAZIONE-->
											<div class="list-group-item media">
												<div class="media-body media-body-datetimepiker">
													<div class="form-group">
														<label class="col-md-2 control-label" for="selectbasic"><spring:message
																text="??proforma.label.dataRichiestaAutorizzazione??"
																code="proforma.label.dataRichiestaAutorizzazione" /></label>
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
																text="??proforma.label.dataAutorizzazione??"
																code="proforma.label.dataAutorizzazione" /></label>
														<div class="col-md-10">
															<form:input id="txtDataAutorizzazione"
																path="dataAutorizzazione"
																cssClass="form-control date-picker" readonly="true" />
														</div>
													</div>
												</div>
											</div>
										</div>


										<div class="list-group lg-alt">
											<!--DATA COMPOSIZIONE-->
											<div class="list-group-item media">
												<div class="media-body media-body-datetimepiker">
													<div class="form-group">
														<label class="col-md-2 control-label" for="selectbasic"><spring:message
																text="??proforma.label.dataComposizione??"
																code="proforma.label.dataComposizione" /></label>
														<div class="col-md-10">
															<form:input id="txtDataComposizione"
																path="dataComposizione"
																cssClass="form-control date-picker" readonly="true" />
														</div>
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<!--DATA INVIO AMMINISTRATORE-->
											<div class="list-group-item media">
												<div class="media-body media-body-datetimepiker">
													<div class="form-group">
														<label class="col-md-2 control-label" for="selectbasic"><spring:message
																text="??proforma.label.dataInvioAmministratore??"
																code="proforma.label.dataInvioAmministratore" /></label>
														<div class="col-md-10">
															<form:input id="txtDataInvioAmministratore"
																path="dataInvioAmministratore"
																cssClass="form-control date-picker" readonly="true" />
														</div>
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<!--AUTORIZZATORE-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label class="col-md-2 control-label" for="autorizzatore"><spring:message
																text="??proforma.label.autorizzatore??"
																code="proforma.label.autorizzatore" /></label>
														<div class="col-md-10">
															<form:input path="autorizzatore" cssClass="form-control"
																readonly="true" />
														</div>
													</div>
												</div>
											</div>
										</div>

										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="allegati" class="col-sm-2 control-label"><spring:message
															text="??proforma.label.allegati??"
															code="proforma.label.allegati" /></label>
													<div class="col-sm-10">
														<div class="list-group-item media">
															<div class="media-body">
																<div id="accordion" role="tablist"
																	aria-multiselectable="true">
																	<!-- SCHEDA VALUTAZIONE-->
																	<div class="panel panel-default">
																		<div class="panel-heading" role="tab"
																			id="headingLetteraIncarico">
																			<h4 class="panel-title">
																				<button type="button" data-toggle="collapse"
																					data-target="#boxSchedaValutazione"
																					class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini collapsed"
																					style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																					aria-expanded="false">
																					<i class="zmdi zmdi-collection-text icon-mini"></i>
																				</button>
																				<div id="SchedaValutazioneDownload"></div>
																				<div id="SchedaValutazioneFirmataDownload"></div>
																				<a data-toggle="collapse" data-parent="#accordion"
																					href="#boxSchedaValutazione" aria-expanded="true"
																					aria-controls="collapseOne"> <spring:message
																						text="??proforma.label.schedaValutazione??"
																						code="proforma.label.schedaValutazione" />
																				</a>
																			</h4>
																		</div>
																		<div id="boxSchedaValutazione"
																			class="panel-collapse collapse" role="tabpanel"
																			aria-labelledby="headingLetteraIncarico">
																			<jsp:include
																				page="/subviews/proforma/schedaValutazioneDettaglio.jsp">
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
																			<jsp:include
																				page="/subviews/proforma/allegatiGenericiDettaglio.jsp"></jsp:include>

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
								
								
								<!-- New -->
								<div class="col-md-12" style="position:absolute;margin-top:-120px;width:100%;">
							 
								<legarc:isAuthorized idEntita="${proformaDettaglioView.proformaId }" tipoEntita="<%=Costanti.TIPO_ENTITA_PROFORMA %>"
								 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
							 
							 
							 	<c:choose>
								<c:when test="${proformaDettaglioView.vo.daProfEsterno  eq 'T' && proformaDettaglioView.vo.statoProforma.codGruppoLingua eq 'B' }">
								 <button form="proformaForm" onclick="modificaProforma(${proformaDettaglioView.proformaId});"
									class="btn palette-Green-SNAM bg waves-effect waves-float" style="position:relative!important;float:right;margin-top:10px;margin-right:80px;">
									<i class="zmdi zmdi-edit"></i> &nbsp;&nbsp; <spring:message text="??proforma.label.continua??" code="proforma.label.continua" /> &nbsp;&nbsp;
								</button> 
								<button onclick="openModalRespingiMotivazione(${proformaDettaglioView.proformaId}); " class="btn palette-Green-SNAM bg" style="float:right;margin-right:40px;margin-top:10px;background:#FF0000;color:#FFFFFF">
								
								<spring:message text="??proforma.label.respingProforma??" code="proforma.label.respingProforma" />
								</button>
								</c:when>
								<c:otherwise>
								<c:if test="${proformaDettaglioView.vo.statoProforma.codGruppoLingua eq 'B' }">
								<button form="proformaForm" onclick="modificaProforma(${proformaDettaglioView.proformaId});"
									 class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float" style="position:relative!important;float:right;margin-right:80px;">
									<i class="zmdi zmdi-edit"></i>
								</button>
								</c:if>
								</c:otherwise>
								</c:choose>
							 					
								</legarc:isAuthorized>
								<jsp:include page="avviaWFProforma.jsp"></jsp:include>
								
								
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


	<script
		src="<%=request.getContextPath()%>/portal/js/controller/proforma.js"></script>
		
	<!-- DARIO ****************************************************************************************** -->
    <script	src="<%=request.getContextPath()%>/portal/js/controller/lista_assegnatari.js"></script>
	<!-- ************************************************************************************************ -->	
		
	<script type="text/javascript">
		<%-- DARIO ********
		$('#panelConfirmAvviaWorkFlowProforma').on('show.bs.modal',function(e) {
			$(this).find("#btnRichiediAvvioWorkflowProforma").attr('onclick','avviaWorkFlowProformaDaForm('+<%=request.getParameter("id")%> + ')');
		}); --%>
		$('#panelConfirmAvviaWorkFlowProforma').on(
				'show.bs.modal',
				function(e) {
					
					gestisci_tasto_confirm_workflow($(this), function(resp_code){
						
						avviaWorkFlowProformaDaForm(<%= request.getParameter("id") %> , resp_code);
						
					});
					
					
				});	
		
		/* *********************** */
	</script>

	<div class="modal fade" id="modalRespingiMotivazione" role="dialog">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-success">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<spring:message text="??proforma.label.respingProforma??"
							code="proforma.label.respingProforma" />
					</h4>
				</div>
				<div class="modal-body">
						<p><spring:message text="??proforma.label.respingi.motivazione??"
							code="proforma.label.respingi.motivazione" /></p>
					<form id="formMotivazione" action="./respingi.action" method="post" style="width:100%">
					<engsecurity:token regenerate="false"/>
					<input type="hidden" name="id" value="${proformaDettaglioView.proformaId}">
					 <textarea id="motivazione" name="motivazione" rows="4" cols="62" style="width:100%"></textarea>   
					 <input type="submit" value="save" style="display:none;"> 	
				</form>		 	
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" onclick="respingiConMotivazione()"><spring:message text="??proforma.label.respingProforma??"
							code="proforma.label.respingProforma" /></button>
					 
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message text="??fascicolo.label.chiudi??"
							code="fascicolo.label.chiudi" /></button>
				</div>
				 
			</div>
		</div>
	</div>
	
	<script>  
	function openModalRespingiMotivazione(a){
		 ////respingiProforma(11268); 
		  $("#modalRespingiMotivazione").modal('show');
	}
	
	function respingiConMotivazione(){
	
		$("#formMotivazione").submit();
		
	}
	
	
	</script>		
		
		
</body>
</html>
