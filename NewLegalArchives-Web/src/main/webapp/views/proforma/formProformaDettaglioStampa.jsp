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

<style>
.form-control[disabled], .form-control[readonly], fieldset[disabled] .form-control {
    background-color: #ffffff!important;
    opacity: 1;}
.form-control:not(.fc-alt) {
    border-left: 0;
    border-right: 0;
    border: 0px !important;
    appearance: none;
    padding: 0;
    paddingleft: 10px;
}
#proformaStampa td{ padding-top:5px;padding-bottom:5px;}
#proformaStampa label,input{ text-align:left;}
</style>
<base target="_blank"/>
</head>
<body data-ma-header="teal">
	<!-- SECION MAIN -->
	<section id="main">
 
		<!-- SECTION CONTENT -->
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						<div class="card">
							<div class="card-header ch-dark palette-Green-SNAM bg" style="background:#b7b7b7;">

								<h2>
									<spring:message text="??proforma.label.dettaglioProforma??"
										code="proforma.label.dettaglioProforma" />
								</h2>
							<div id="box-stampa" style="width:100%;padding-bottom:20px"> </div>	
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
									 <form:hidden path="proformaId" />
									
								<table id="proformaStampa" style="width: 100%" border="1px"> <!--INIT TABLE -->
								<tr><td style="width:25%">
									<label for="fascicolo" class="col-sm-12 control-label"><spring:message
															text="??proforma.label.fascicolo??"
															code="proforma.label.fascicolo" /></label>
									</td>
									<td style="width:75%">
										<div class="col-sm-12">
											<input type="text" class="form-control" readonly
												value="${proformaDettaglioView.fascicoloRiferimento.vo.nome }" />
										</div>
									
									</td>
									</tr>
									<tr><td style="width:25%">
											<label for="nomeIncarico" class="col-sm-12 control-label"><spring:message
															text="??proforma.label.nomeIncarico??"
															code="proforma.label.nomeIncarico" /></label>
									</td><td style="width:75%">
									<div class="col-sm-12">
														<input type="text" class="form-control" readonly
															value="${proformaDettaglioView.incaricoRiferimento.vo.nomeIncarico }" /> 
														<form:hidden path="incaricoId" />
													</div>
									</td></tr>
									<tr><td style="width:25%">
										<label for="legaleInterno" class="col-sm-12 control-label"><spring:message
												text="??proforma.label.legaleInterno??"
										code="proforma.label.legaleInterno" /></label>
									</td><td style="width:75%">
										<div class="col-sm-12">
									
										<input type="text" class="form-control" disabled name="legaleInternoDesc"
											value="${proformaDettaglioView.legaleInternoDesc }" />
										<input type="hidden" class="form-control" disabled name="legaleInterno"
											value="${proformaDettaglioView.legaleInterno }" />
													</div>
									</td></tr>
									<tr><td  style="width:25%">
										<label for="unitaLegale" class="col-sm-12 control-label"><spring:message
															text="??proforma.label.unitaLegale??"
															code="proforma.label.unitaLegale" /></label>
									</td><td style="width:75%">
											<div class="col-sm-12"> 
														<input type="text" class="form-control" disabled name="unitaLegaleDesc"
															value="${proformaDettaglioView.unitaLegaleDesc }" />
														<input type="hidden" class="form-control" disabled name="unitaLegale"
															value="${proformaDettaglioView.unitaLegale }" />
													</div>
									</td></tr>
									<tr><td style="width:25%">
										<label for="professionistaEsterno"
														class="col-sm-12 control-label"><spring:message
															text="??proforma.label.professionistaEsterno??"
															code="proforma.label.professionistaEsterno" /></label>
									</td><td style="width:75%">
										<div class="col-sm-12">

														<input type="text" class="form-control" readonly
															value="${proformaDettaglioView.professionistaEsterno }" />

													</div>
									</td></tr>
									<tr><td style="width:25%">
										<label for="nome" class="col-sm-12 control-label"><spring:message
															text="??proforma.label.numero??"
															code="proforma.label.numero" /></label>
									</td><td style="width:75%">
											<div class="col-sm-12">
														<form:input path="numero" cssClass="form-control"
															readonly="true" />
													</div>
									</td></tr>
									<tr><td style="width:25%">
									<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??proforma.label.nome??"
															code="proforma.label.nome" /></label>
									</td><td style="width:75%">
											<div class="col-sm-10">
														<form:input path="nome" cssClass="form-control"
															readonly="true" />
													</div>
									</td></tr>
									<tr><td style="width:25%">
										<label for=stato class="col-sm-12 control-label"><spring:message
															text="??proforma.label.stato??"
															code="proforma.label.stato" /></label>
									</td><td style="width:75%">
											<div class="col-sm-12">
														<form:input path="stato" cssClass="form-control"
															readonly="true" />
													</div>
									</td></tr>
									<tr><td style="width:25%">	<!-- SOCIETA DI ADDEBITO-->
										<label for="societaAddebito" class="col-sm-12 control-label"><spring:message
														text="??fascicolo.label.societaAddebito??"
														code="fascicolo.label.societaAddebito" /></label>
									</td><td style="width:75%">
															<div class="col-sm-12">
															
															<c:if
																	test="${ not empty proformaDettaglioView.listaSocietaAddebitoAggiunte  }">
																	<c:forEach
																		items="${proformaDettaglioView.listaSocietaAddebitoAggiunte}"
																		var="oggetto">
																		<label for="societaAddebito" class="col-sm-12 control-label">
																		${oggetto.vo.ragioneSociale}	
																		</label>
																		
																	</c:forEach>
																</c:if>
																<c:if
																	test="${   empty proformaDettaglioView.listaSocietaAddebitoAggiunte }">
																	 
																		<label for="societaAddebito" class="col-sm-12 control-label">
																		<spring:message
																				code="fascicolo.label.tabella.no.dati"
																				text="??fascicolo.label.tabella.no.dati??">
																			</spring:message>
																			</label>
																	
																	
																</c:if>
												 
												</div>
									
									</td></tr>
									<tr><td style="width:25%">
										<label class="col-md-12 control-label" for="dataInserimento"><spring:message
															text="??proforma.label.dataInserimento??"
															code="proforma.label.dataInserimento" /></label>
									</td><td style="width:75%">
											<div class="col-md-12">
														<form:input id="txtDataInserimento" path="dataInserimento"
															cssClass="form-control date-picker" readonly="true" />
													</div>
									</td></tr>
									<tr><td style="width:25%">
									<label class="col-md-12 control-label" for="selectbasic"><spring:message
															text="??proforma.label.dataRichiestaAutorizzazione??"
															code="proforma.label.dataRichiestaAutorizzazione" /></label>
									</td><td style="width:75%">
									<div class="col-md-12">
														<form:input id="txtDataRichiestaAutorizzazione"
															path="dataRichiestaAutorizzazione"
															cssClass="form-control date-picker" readonly="true" />
													</div>
									</td></tr>
									<tr><td style="width:25%">
											<label class="col-md-12 control-label" for="ultimo"><spring:message
															text="??proforma.label.ultimo??"
															code="proforma.label.ultimo" /></label>
									</td><td style="width:75%">
										<div class="col-md-12">
														<form:checkbox path="ultimo"
															disabled="true" />
													</div>
									</td></tr>
									<tr><td style="width:25%">
										<label class="col-md-12 control-label" for="valuta"><spring:message
															text="??proforma.label.valuta??"
															code="proforma.label.valuta" /></label>
									</td><td style="width:75%">
											<div class="col-md-12">
														<c:if test="${ not empty proformaDettaglioView.listaTipoValuta  }">
															<c:forEach items="${proformaDettaglioView.listaTipoValuta}"
																var="oggetto">
																<form:radiobutton disabled="true" path="valutaId"
																	value="${oggetto.vo.id }" label="${oggetto.vo.nome }"
																	 />
															</c:forEach>
														</c:if>
													</div>
									</td></tr>
									<tr><td style="width:25%">
										<label class="col-md-12 control-label" for="diritti"><spring:message
															text="??proforma.label.diritti??"
															code="proforma.label.diritti" /></label>
									</td><td style="width:75%">
											<div class="col-md-12">
														<input type="number" name="diritti"
															value="${proformaDettaglioView.diritti }" class="form-control"
															readonly />
													</div>
									</td></tr>
									<tr><td style="width:25%">
										<label class="col-md-12 control-label" for="onorari"><spring:message
															text="??proforma.label.onorari??"
															code="proforma.label.onorari" /></label>
									</td><td style="width:75%">
											<div class="col-md-12">
														<input type="number" name="onorari"
															value="${proformaDettaglioView.onorari }" class="form-control"
															readonly />
													</div>
									</td></tr>
									<tr><td style="width:25%">
										<label class="col-md-12 control-label" for="speseImponibili"><spring:message
															text="??proforma.label.speseImponibili??"
															code="proforma.label.speseImponibili" /></label>
									</td><td style="width:75%">
												<div class="col-md-12">
														<input type="number" name="speseImponibili"
															value="${proformaDettaglioView.speseImponibili }"
															class="form-control" readonly  />
													</div>
									</td></tr>
									<tr><td style="width:25%">
									<label class="col-md-12 control-label" for="cpa"><spring:message
															text="??proforma.label.cpa??" code="proforma.label.cpa" /></label>
									</td><td style="width:75%">
											<div class="col-md-12">
														<form:input type="number" path="cpa" readonly="true"
															class="form-control" />
											</div>
									</td></tr>
									<tr><td style="width:25%">
											<label class="col-md-12 control-label"
														for="totaleImponibile"><spring:message
															text="??proforma.label.totaleImponibile??"
															code="proforma.label.totaleImponibile" /></label>
									</td><td style="width:75%">
										<div class="col-md-12">
														<form:input type="number" path="totaleImponibile"
															readonly="true" class="form-control" />
													</div>
									</td></tr>
									<tr><td style="width:25%">
										<label class="col-md-12 control-label"
														for="speseNonImponibili"><spring:message
															text="??proforma.label.speseNonImponibili??"
															code="proforma.label.speseNonImponibili" /></label>
									</td><td style="width:75%">
											<div class="col-md-12">
														<input type="number" name="speseNonImponibili"
															value="${proformaDettaglioView.speseNonImponibili }"
															class="form-control" readonly  />
													</div>
									</td></tr>
									<tr><td style="width:25%">
									<label class="col-md-2 control-label" for="totale"><spring:message
															text="??proforma.label.totale??"
															code="proforma.label.totale" /></label>
									</td><td style="width:75%">
											<div class="col-md-10">
														<form:input path="totale" 
															class="form-control" readonly="true" />
													</div>
									</td></tr>
									<tr><td style="width:25%">
									<label class="col-md-12 control-label" for="note"><spring:message
															text="??proforma.label.note??" code="proforma.label.note" /></label>
									</td><td style="width:75%">
										<div class="col-md-12">
										${proformaDettaglioView.note }	
													</div>
									</td></tr>
									<tr><td style="width:25%">
											<label class="col-md-12 control-label"
														for="annoEsercizioFinanziario"><spring:message
															text="??proforma.label.annoEsercizioFinanziario??"
															code="proforma.label.annoEsercizioFinanziario" /></label>
									</td><td style="width:75%">
										<div class="col-md-12">
														<form:input id="txtAnnoEsercizioFinanziario"
															readonly="true" path="annoEsercizioFinanziario"
															cssClass="form-control date-YYYY-picker" />
													</div>
									</td></tr>
									<tr><td style="width:25%">
										<label class="col-md-12 control-label" for="centroDiCosto"><spring:message
															text="??proforma.label.centroDiCosto??"
															code="proforma.label.centroDiCosto" /></label>
									</td><td style="width:75%">
											<div class="col-md-12">

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
									</td></tr>
									<tr><td style="width:25%">
									<label class="col-md-12 control-label" for="voceDiConto"><spring:message
															text="??proforma.label.voceDiConto??"
															code="proforma.label.voceDiConto" /></label>
									</td><td style="width:75%">
											<div class="col-md-12">

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
									
									</td></tr>
					 

									<c:if
										test="${proformaDettaglioView.proformaId != null && proformaDettaglioView.proformaId > 0 }">
										
										
									<tr><td style="width:25%">
											<label class="col-md-12 control-label" for="selectbasic"><spring:message
																text="??proforma.label.dataRichiestaAutorizzazione??"
																code="proforma.label.dataRichiestaAutorizzazione" /></label>
									</td><td style="width:75%">
									<div class="col-md-12">
															<form:input id="txtDataRichiestaAutorizzazione"
																path="dataRichiestaAutorizzazione"
																cssClass="form-control date-picker" readonly="true" />
														</div>
									</td></tr>	 
							
									<tr><td style="width:25%">
										<label class="col-md-12 control-label" for="selectbasic"><spring:message
																text="??proforma.label.dataAutorizzazione??"
																code="proforma.label.dataAutorizzazione" /></label>
									</td><td style="width:75%">
										<div class="col-md-12">
															<form:input id="txtDataAutorizzazione"
																path="dataAutorizzazione"
																cssClass="form-control date-picker" readonly="true" />
														</div>
									</td></tr>
									<tr><td style="width:25%">
									<label class="col-md-2 control-label" for="selectbasic"><spring:message
																text="??proforma.label.dataComposizione??"
																code="proforma.label.dataComposizione" /></label>
									</td><td style="width:75%">
										<div class="col-md-10">
															<form:input id="txtDataComposizione"
																path="dataComposizione"
																cssClass="form-control date-picker" readonly="true" />
														</div>
									
									</td></tr>
									<tr><td style="width:25%">
									<label class="col-md-12 control-label" for="selectbasic"><spring:message
																text="??proforma.label.dataInvioAmministratore??"
																code="proforma.label.dataInvioAmministratore" /></label>
									</td><td style="width:75%">
										<div class="col-md-12">
															<form:input id="txtDataInvioAmministratore"
																path="dataInvioAmministratore"
																cssClass="form-control date-picker" readonly="true" />
														</div>
									</td></tr>
							 		<tr><td style="width:25%">
							 			<label class="col-md-2 control-label" for="autorizzatore"><spring:message
																text="??proforma.label.autorizzatore??"
																code="proforma.label.autorizzatore" /></label>
							 		</td><td style="width:75%">
							 			<div class="col-md-10">
															<form:input path="autorizzatore" cssClass="form-control"
																readonly="true" />
														</div>
							 		</td></tr>
								 
							 <tr><td style="width:25%">
							 <label for="allegati" class="col-sm-2 control-label"><spring:message
															text="??proforma.label.allegati??"
															code="proforma.label.allegati" /></label>
							 </td><td style="width:75%">
							
													<div class="col-sm-12">
													
													<table style="width:100%">
													<tr>
													<td> 
													<div class="col-sm-12">									
													<jsp:include page="/subviews/proforma/schedaValutazioneDettaglio.jsp">
													</jsp:include>
													</div>
													</td>
													</tr>
													<tr>
													<td>
													
													<label for="allegati" class="col-sm-12 control-label">
													<spring:message
																						text="??incarico.label.allegatiGenerici??"
																						code="incarico.label.allegatiGenerici" />
																						</label>
													<div class="col-sm-12">									
													<jsp:include page="/subviews/proforma/allegatiGenericiDettaglio.jsp"></jsp:include>
													</div>
													
													</td>
													</tr>
													</table>
													
													
														
													</div>
							
							 
							 </td></tr>
									</c:if>

								</table>
								</form:form>
								
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--/ fine col-1 -->
		</section>

	</section>


	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>
	
	<!-- LegalSecurityToken -->
	<div id="box-secutity" style="display:none;">
    	<form:form name="legalSecurityForm" id="legalSecurityForm" method="get" action="index.action" cssStyle="display:none">
            <engsecurity:token regenerate="false"/>
        </form:form>
    </div><!-- / LegalSecurityToken -->

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/proforma.js"></script>

</body>
</html>
