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
									<a href="<%=request.getContextPath()%>/fascicolo/contenuto.action?CSRFToken=<%= request.getParameter("CSRFToken") %>&id=${proformaView.fascicoloRiferimento.vo.id }">Fascicolo ${proformaView.fascicoloRiferimento.vo.nome }</a>
									/
									<a href="<%=request.getContextPath()%>/incarico/dettaglio.action?CSRFToken=<%= request.getParameter("CSRFToken") %>&id=${proformaView.incaricoRiferimento.vo.id }">Incarico ${proformaView.incaricoRiferimento.vo.nomeIncarico }</a>
								</div>
								<!-- FINE Aggiunta breadcrumb MASSIMO CARUSO -->
								<c:if
									test="${ proformaView.proformaId == null || proformaView.proformaId == 0 }">
									<h2>
										<spring:message text="??proforma.label.nuovaProforma??"
											code="proforma.label.nuovaProforma" />
									</h2>
								</c:if>
								<c:if
									test="${ proformaView.proformaId != null && proformaView.proformaId > 0 }">
									<h2>
										<spring:message text="??proforma.label.modificaProforma??"
											code="proforma.label.modificaProforma" />
									</h2>
								</c:if>
							</div>
							<div class="card-body">

								<form:form name="proformaForm" method="post"
									modelAttribute="proformaView" action="salva.action"
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
									<form:hidden path="op" id="op" value="salvaProforma" />
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
															value="${proformaView.fascicoloRiferimento.vo.nome }" />
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
															code="proforma.label.nomeIncarico" />
															
															</label>
													<div class="col-sm-10">
														<input type="text" class="form-control" readonly
															value="${proformaView.incaricoRiferimento.vo.nomeIncarico }" />
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
														<input type="text" class="form-control" readonly
															name="legaleInternoDesc"
															value="${proformaView.legaleInternoDesc }" /> <input
															type="hidden" class="form-control" readonly
															name="legaleInterno"
															value="${proformaView.legaleInterno }" />
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
														<input type="text" class="form-control" readonly
															name="unitaLegaleDesc"
															value="${proformaView.unitaLegaleDesc }" /> <input
															type="hidden" class="form-control" readonly
															name="unitaLegale" value="${proformaView.unitaLegale }" />
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
															name="professionistaEsterno"
															value="${proformaView.professionistaEsterno }" />

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
															code="proforma.label.numero" />
														<a href="javascript:void(0)"
															   title='<spring:message text="??proforma.label.infoNumero??" code="proforma.label.infoNumero" />'
															   alt='<spring:message text="??proforma.label.infoNumero??" code="proforma.label.infoNumero" />' >
														</a>
													</label>
															
													<div class="col-sm-10">
													<c:if test="${proformaView.vo.daProfEsterno  eq 'T' }"> 
													<form:input path="numero" cssClass="form-control" readonly="true" />
													</c:if>
													<c:if test="${ empty proformaView.vo.daProfEsterno or proformaView.vo.daProfEsterno  eq 'F' }">
														<form:input path="numero" cssClass="form-control" />
													</c:if>
													</div>
												</div>
											</div>
										</div>
									</div>

									<c:if
										test="${proformaView.proformaId != null && proformaView.proformaId > 0 }">
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
									</c:if>

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
																	test="${ not empty proformaView.listaSocietaAddebitoAggiunte  }">
																	<c:forEach
																		items="${proformaView.listaSocietaAddebitoAggiunte}"
																		var="oggetto">
																		<tr>
																			<td colspan="2"><form:radiobutton
																					path="societaAddebitoScelta"
																					label="${oggetto.vo.ragioneSociale}"
																					value="${oggetto.vo.id}"
																					onclick="selezionaSocieta(${oggetto.vo.id},${proformaView.fascicoloRiferimento.vo.settoreGiuridico.id }, ${proformaView.fascicoloRiferimento.vo.tipologiaFascicolo.id}, '${proformaView.unitaLegale }')" /></td>
																		</tr>
																	</c:forEach>
																</c:if>
																<c:if
																	test="${   empty proformaView.listaSocietaAddebitoAggiunte }">
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
										<!--ULTIMO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" for="ultimo"><spring:message
															text="??proforma.label.ultimo??"
															code="proforma.label.ultimo" /></label>
													<div class="col-md-10">
													<c:if test="${proformaView.vo.daProfEsterno  eq 'T' }"> 
													<form:checkbox path="ultimo" readonly="true"/>
													</c:if>
													<c:if test="${ empty proformaView.vo.daProfEsterno or proformaView.vo.daProfEsterno  eq 'F' }">
														<form:checkbox path="ultimo" />
													</c:if>
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
														
														
														<c:if test="${proformaView.vo.daProfEsterno  eq 'T' }"> 
													 
														<c:if test="${ not empty proformaView.listaTipoValuta  }">
															<c:forEach items="${proformaView.listaTipoValuta}"
																var="oggetto">
																<form:radiobutton path="valutaId"
																	value="${oggetto.vo.id }" label="${oggetto.vo.nome }" readonly="true" />
															</c:forEach>
														</c:if>
													</c:if>
													<c:if test="${ empty proformaView.vo.daProfEsterno or proformaView.vo.daProfEsterno  eq 'F' }">
														<c:if test="${ not empty proformaView.listaTipoValuta  }">
															<c:forEach items="${proformaView.listaTipoValuta}"
																var="oggetto">
																<form:radiobutton path="valutaId"
																	value="${oggetto.vo.id }" label="${oggetto.vo.nome }" />
															</c:forEach>
														</c:if>
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
															class="form-control">
															<c:if test="${ proformaView.listaTipoProforma != null }">
																<c:forEach items="${proformaView.listaTipoProforma}"
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

														<input type="number" id="diritti" min="0" name="diritti"
															value="${proformaView.diritti }" class="form-control" <c:if test="${proformaView.vo.daProfEsterno  eq 'T' }"> readonly="true"</c:if> />
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
														<input type="number" id="onorari" min="0" name="onorari"
															value="${proformaView.onorari }" class="form-control" <c:if test="${proformaView.vo.daProfEsterno  eq 'T' }"> readonly="true"</c:if> />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt"  id="divSpeseImponibili">
										<!--SPESE IMPONIBILI-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" for="speseImponibili"><spring:message
															text="??proforma.label.speseImponibili??"
															code="proforma.label.speseImponibili" /></label>
													<div class="col-md-10">
														<input type="number" id="speseImponibili" min="0"
															name="speseImponibili"
															value="${proformaView.speseImponibili }"
															class="form-control" <c:if test="${proformaView.vo.daProfEsterno  eq 'T' }"> readonly="true"</c:if> />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt"  id="divCPA">
										<!--CPA-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label class="col-md-2 control-label" for="cpa"><spring:message
															text="??proforma.label.cpa??" code="proforma.label.cpa" /></label>
													<div class="col-md-4">
														<form:input path="cpa" id="cpa" readonly="true"
															class="form-control" />
													</div>
													<label class="col-md-2 control-label" for="cpa"><spring:message
															text="??proforma.label.disablecpa??" code="proforma.label.disablecpa" /></label>
													<div class="col-md-4">
														<form:checkbox path="disableCPA" id="disableCPA"
															  onclick="ricalcola();"/>
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
												<form:input path="totaleImponibile" id="totaleImponibile"
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
													<label class="col-md-2 control-label"
														for="speseNonImponibili" id="labelSpeseNonImponibile"><spring:message
															text="??proforma.label.speseNonImponibili??"
															code="proforma.label.speseNonImponibili" /></label>
													<div class="col-md-10">
														<input type="number" id="speseNonImponibili" min="0"
															name="speseNonImponibili"
															value="${proformaView.speseNonImponibili }"
															class="form-control" <c:if test="${proformaView.vo.daProfEsterno  eq 'T' }"> readonly="true"</c:if> />
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
													<label class="col-md-2 control-label" for="totale" id="labelTotale"><spring:message
															text="??proforma.label.totale??"
															code="proforma.label.totale" /></label>
													<div class="col-md-6">
														<input type="number" name="totale" id="totale" class="form-control"
														value="${proformaView.totale }"  <c:if test="${proformaView.vo.daProfEsterno  eq 'T' }"> readonly="true"</c:if> />
														
													</div>
													<div class="col-md-4">
														<spring:message var="calcolaTotale"
															text="??proforma.label.calcolaTotale??"
															code="proforma.label.calcolaTotale" /> 
															<input type="button" value="${calcolaTotale }"
															onclick="calcolaTotaliProforma()"  class="btn-success" <c:if test="${proformaView.vo.daProfEsterno  eq 'T' }"> disabled="disabled"</c:if>  />
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
														<form:input path="note" class="form-control" maxlength="250"/>
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
															path="annoEsercizioFinanziario"
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
													<div class="col-md-8">

														<div class="input-group dropdown" style="width: 100%;">
															<form:input path="centroDiCosto" id="centroDiCosto"
																cssClass="form-control dropdown-toggle" />
															<ul class="dropdown-menu"
																style="position: relative; width: 100%;" id="comboCDC">
																<c:if
																	test="${ not empty proformaView.listaCentroDiCosto  }">
																	<c:forEach items="${proformaView.listaCentroDiCosto}"
																		var="oggetto">
																		<li><a id="aCdc${oggetto.vo.id}"
																			onclick="javascript: document.getElementById('centroDiCosto').value=document.getElementById('aCdc${oggetto.vo.id}').getAttribute('data-value')"
																			data-value="${oggetto.vo.cdc }">${oggetto.vo.cdc }
																				- ${oggetto.vo.descrizione }</a></li>
																	</c:forEach>
																</c:if>
															</ul>
															<span role="button"
																style="position: relative; display: list-item;"
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
													<div class="col-md-8">

														<div class="input-group dropdown" style="width: 100%;">
															<form:input path="voceDiConto" id="voceDiConto"
																cssClass="form-control dropdown-toggle" />
															<ul class="dropdown-menu"
																style="position: relative; width: 100%;" id="comboVDC">
																<c:if
																	test="${ not empty proformaView.listaVoceDiConto  }">
																	<c:forEach items="${proformaView.listaVoceDiConto}"
																		var="oggetto">
																		<li><a id="aVdc${oggetto.vo.id}"
																			onclick="javascript: document.getElementById('voceDiConto').value=document.getElementById('aVdc${oggetto.vo.id}').getAttribute('data-value')"
																			data-value="${oggetto.vo.vdc }">${oggetto.vo.vdc }
																				- ${oggetto.vo.descrizione }</a></li>
																	</c:forEach>
																</c:if>
															</ul>
															<span role="button"
																class="input-group-addon dropdown-toggle"
																style="position: relative; display: list-item;"
																data-toggle="dropdown" aria-haspopup="true"
																aria-expanded="false"><span class="caret"></span></span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<c:if
										test="${proformaView.proformaId != null && proformaView.proformaId > 0 }">

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
										<!-- SEZIONE ALLEGATI -->
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
																			id="headingSchedaValutazione">
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
																			aria-labelledby="headingSchedaValutazione">
																			<jsp:include
																				page="/subviews/proforma/schedaValutazione.jsp">
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
																				<c:if test="${ empty proformaView.vo.daProfEsterno or proformaView.vo.daProfEsterno  eq 'F' }">
																				<button type="button" data-toggle="modal"
																					onclick="openModalAllegatoGenerico()"
																					class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																					style="float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																					aria-expanded="true">
																					<i class="zmdi zmdi-plus icon-mini"></i>
																				</button>
																				</c:if>
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
																				page="/subviews/proforma/allegatiGenerici.jsp"></jsp:include>

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
								<!-- 
								<button form="proformaForm" onclick="salvaProforma()"
									class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float">
									<i class="zmdi zmdi-save"></i>
								</button>
								 -->
								<!--INIZIO -->
								
								<!-- New -->
								<div class="col-md-12" style="position:absolute;margin-top:-120px;width:100%;">
							 	
								<button form="proformaForm" onclick="salvaProforma()"
									class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float" style="position:relative!important;float:right;margin-right:80px;">
									<i class="zmdi zmdi-save"></i>
								</button>
								
								<c:if test="${ proformaView.vo.daProfEsterno eq 'T' && proformaView.vo.statoProforma.codGruppoLingua eq 'B' }">
								<button onclick="openModalRespingiMotivazione(${proformaView.proformaId}); " class="btn palette-Green-SNAM bg" style="float:right;margin-right:40px;margin-top:10px;background:#FF0000;color:#FFFFFF">
								<spring:message text="??proforma.label.respingProforma??"
							code="proforma.label.respingProforma" />
								</button>
								</c:if>
									<c:if test="${ proformaView.isPresentaAvviaWF()}">
										<jsp:include page="avviaWFProforma.jsp"></jsp:include>
									</c:if> 
								</div>
								
								
								<!-- FINE -->
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
function openModalAllegatoGenerico(){
	  $("#boxAllegatiGenerici").collapse('show');
	  $("#panelAggiungiAllegatoGenerico").modal('show');
}

<%--  DARIO **************************
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

/* *************************************** */





</script>

	<div class="modal fade" id="modalConfirmSave" role="dialog">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<spring:message text="??proforma.label.attenzione??"
							code="proforma.label.attenzione" />
					</h4>
				</div>
					<!--ITA-->
				<div class="modal-body" id="divITA" style="display: block;">
					<p><spring:message text="??proforma.label.confermaRicalcolaTotale??"
							code="proforma.label.confermaRicalcolaTotale" /></p>
					<p>
					    <spring:message text="??proforma.label.diritti??"
							code="proforma.label.diritti" />: <label id="lblDiritti"></label><br>
						<spring:message text="??proforma.label.onorari??"
							code="proforma.label.onorari" />: <label id="lblOnorari"></label><br>
						<spring:message text="??proforma.label.speseImponibili??"
							code="proforma.label.speseImponibili" />: <label id="lblSpeseImponibili"></label><br>
						<spring:message text="??proforma.label.speseNonImponibili??"
							code="proforma.label.speseNonImponibili" />: <label id="lblSpeseNonImponibili"></label><br>
						<spring:message text="??proforma.label.totaleImponibile??"
							code="proforma.label.totaleImponibile" />: <label id="lblTotaleImponibile"></label><br>
						<spring:message text="??proforma.label.cpa??"
							code="proforma.label.cpa" />: <label id="lblCpa"></label><br>
						<spring:message text="??proforma.label.totale??"
							code="proforma.label.totale" />: <label id="lblTotale"></label>
					</p>		
							
				</div>
								
				<!--EST-->
				<div class="modal-body" id="divEST" style="display: none;">
					<p><spring:message text="??proforma.label.confermaRicalcolaTotale??"
							code="proforma.label.confermaRicalcolaTotale" /></p>
					<p>
						<spring:message text="??proforma.label.fees??"
							code="proforma.label.fees" />: <label id="lblFees"></label><br>
						<spring:message text="??proforma.label.dec??"
							code="proforma.label.dec" />: <label id="lblDec"></label><br>

						<spring:message text="??proforma.label.total??"
							code="proforma.label.total" />: <label id="lblTotal"></label>
					</p>		
							
				</div>
				
				<!--NOT-->
				<div class="modal-body" id="divNOT" style="display: none;">
					<p><spring:message text="??proforma.label.confermaRicalcolaTotale??"
							code="proforma.label.confermaRicalcolaTotale" /></p>
					<p>
					    <spring:message text="??proforma.label.diritti??"
							code="proforma.label.diritti" />: <label id="lblDirittiN"></label><br>
						<spring:message text="??proforma.label.onorari??"
							code="proforma.label.onorari" />: <label id="lblOnorariN"></label><br>
						<spring:message text="??proforma.label.speseImponibili??"
							code="proforma.label.speseImponibili" />: <label id="lblSpeseImponibiliN"></label><br>
						<spring:message text="??proforma.label.speseNonImponibili??"
							code="proforma.label.speseNonImponibili" />: <label id="lblSpeseNonImponibiliN"></label><br>
						<spring:message text="??proforma.label.totaleImponibile??"
							code="proforma.label.totaleImponibile" />: <label id="lblTotaleImponibileN"></label><br>					
							
						<spring:message text="??proforma.label.totale??"
							code="proforma.label.totale" />: <label id="lblTotaleN"></label>
					</p>		
							
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-success" onclick="salvaProforma(true)"><spring:message text="??proforma.label.prosegui??"
							code="proforma.label.prosegui" /></button>
					<button type="button" class="btn btn-warning" onclick="ricalcola()"><spring:message text="??proforma.label.ricalcola??"
							code="proforma.label.ricalcola" /></button>
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message text="??fascicolo.label.chiudi??"
							code="fascicolo.label.chiudi" /></button>
				</div>
			</div>
		</div>
	</div>
	
	
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
					<input type="hidden" name="id" value="${proformaView.proformaId}">
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
	function openModalRespingiMotivazione(){
		 
		  $("#modalRespingiMotivazione").modal('show');
	}
	
	function respingiConMotivazione(){
	
		$("#formMotivazione").submit();
		
	}
	
	
	</script>
	
	
</body>
</html>
