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
	body{
	background-color:#ffffff;
	}
	.card-header{color:#000000!important;}
	.card-header.ch-dark h2, .card-header.ch-dark small{color:#000000!important;}
	
	[data-ma-header="teal"]:before, [data-ma-header="teal"] #header {
	    background-color: #ffffff!important;
		}
	
	#schedaFondoRischiStampa td{ padding-top:5px;padding-bottom:5px;}
	#schedaFondoRischiStampa label,input{ text-align:left;}	
</style>

</head>

<body data-ma-header="teal">

<!-- SECION MAIN -->
	<section id="newMain">
		<!-- SECTION CONTENT -->
		<section id="content">
		<div class="container">
			<div class="row">
				<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
	
					<div class="card">
						
						<div class="card-body">
						
							<!-- Area Dettaglio Scheda Fondo Rischi-->
				
							<div class="table-responsive">
					
								<div class="col-md-12">
									<div class="col-md-12">
										<div class="col-md-12" style="margin-bottom: 20px;margin-top: 20px;border-bottom: 1px">
											<h4><spring:message text="??schedaFondoRischi.label.dettaglioSchedaFondoRischi??" code="schedaFondoRischi.label.dettaglioSchedaFondoRischi" /></h4> 
											<spring:message text="??schedaFondoRischi.label.fascicolo??" code="schedaFondoRischi.label.fascicolo" /> 
											<c:out value="${schedaFondoRischiView.nomeFascicolo}"></c:out>
										</div>
										<div class="col-md-12">
										
											        
								        <c:if test="${ not empty listError }">
											<div class="alert alert-danger box-error">
												<c:forEach items="${listError}" var="value">
										         <c:out value="${value}"></c:out><br>
								   				</c:forEach>
											</div>
										</c:if>
									 	<div class="alert alert-danger box-error" style="display:none"></div>
									 		
									 		<!--INIT TABLE -->
											<fieldset>
												<table id="schedaFondoRischiStampa" style="width: 100%" border="1px">
													
													<tr> 
														<td style="width:30%">
															<label class="col-md-12 control-label" for="campo">
																<spring:message text="??schedaFondoRischi.label.campo??" code="schedaFondoRischi.label.campo" />
															</label>
														</td>
														<td style="width:35%">
															<label class="col-md-12 control-label" for="valoreAttuale">
																<spring:message text="??schedaFondoRischi.label.valoreAttuale??" code="schedaFondoRischi.label.valoreAttuale" />
															</label>
														</td>
														<td style="width:35%">
															<label class="col-md-12 control-label" for="valorePrecedente">
																<spring:message text="??schedaFondoRischi.label.valorePrecedente??" code="schedaFondoRischi.label.valorePrecedente" />
															</label>
														</td>
														
											    	
													 <!-- GIUDIZIO INSTAURATO -->
													 <tr>
													 	<td style="width:30%">			
															<label class="col-md-12 control-label" for="giudizio">
																<spring:message text="??schedaFondoRischi.label.giudizioInstaurato??" code="schedaFondoRischi.label.giudizioInstaurato" />
															</label>
													    </td>
													    <td style="width:35%">		
															<div class="col-md-12">
																<c:if test="${ schedaFondoRischiView.listaGiudizioDesc != null }">
																	<c:forEach items="${schedaFondoRischiView.listaGiudizioDesc}" var="oggetto">
																		<c:out value="${oggetto}"></c:out>
																	</c:forEach>
																</c:if>
															</div>
														</td>
														<td style="width:35%">		
															<div class="col-md-12"> </div>
														</td>
													 </tr>
													 
													 <!-- ORGANO GIUDICANTE -->
													 <tr>
													 	<td style="width:30%">			
															<label class="col-md-12 control-label" for="organoGiudicante">
																<spring:message text="??schedaFondoRischi.label.organoGiudicante??" code="schedaFondoRischi.label.organoGiudicante" />
															</label>
													    </td>
													    <td style="width:35%">		
															<div class="col-md-12">
																<c:if test="${ schedaFondoRischiView.listaOrganoGiudicanteDesc != null }">
																	<c:forEach items="${schedaFondoRischiView.listaOrganoGiudicanteDesc}" var="oggetto">
																		<c:out value="${oggetto}"></c:out>
																	</c:forEach>
																</c:if>
															</div>
														</td>
														<td style="width:35%">		
															<div class="col-md-12"> </div>
														</td>
													 </tr>
													
													 <!-- VALORE DELLA DOMANDA -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="valoreDomanda">
																<spring:message text="??schedaFondoRischi.label.valoreDomanda??" code="schedaFondoRischi.label.valoreDomanda" />
															</label>
													 	</td>
													    <td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.valoreDomanda}"></c:out> 
															</div>
														</td>
														<td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.valoreDomandaPrecedente}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- PROFESSIONISTI -->
													 <tr>
													 	<td style="width:30%">			
															<label class="col-md-12 control-label" for="professionisti">
																<spring:message text="??schedaFondoRischi.label.professionista??" code="schedaFondoRischi.label.professionista" />
															</label>
													    </td>
													    <td style="width:35%">		
															<div class="col-md-12">
																<c:if test="${ schedaFondoRischiView.listaProfessionistiEsterni != null }">
																	<c:forEach items="${schedaFondoRischiView.listaProfessionistiEsterni}" var="oggetto">
																		<c:out value="${oggetto.vo.studioLegale.denominazione} ${oggetto.vo.cognomeNome}"></c:out>
																	</c:forEach>
																</c:if>
															</div>
														</td>
														<td style="width:35%">		
															<div class="col-md-12"> </div>
														</td>
													 </tr>
													 
													 <!-- TESTO ESPLICATIVO -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="testoEsplicativo">
																<spring:message text="??schedaFondoRischi.label.testoEsplicativo??" code="schedaFondoRischi.label.testoEsplicativo" />
															</label>
													 	</td>
													    <td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.testoEsplicativo}"></c:out> 
															</div>
														</td>
														<td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.testoEsplicativoPrecedente}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- RISCHIO SOCCOMBENZA -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="rischioSoccombenza">
																<spring:message text="??schedaFondoRischi.label.rischioSoccombenza??" code="schedaFondoRischi.label.rischioSoccombenza" />
															</label>
													 	</td>
													    <td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.rischioSoccombenzaDesc}"></c:out> 
															</div>
														</td>
														<td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.rischioSoccombenzaPrecDesc}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- COPERTURA ASSICURATIVA -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="coperturaAssicurativa">
																<spring:message text="??schedaFondoRischi.label.coperturaAssicurativa??" code="schedaFondoRischi.label.coperturaAssicurativa" />
															</label>
													 	</td>
													    <td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.coperturaAssicurativaDesc}"></c:out> 
															</div>
														</td>
														<td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.coperturaAssicurativaPrecDesc}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- MANLEVA -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="manleva">
																<spring:message text="??schedaFondoRischi.label.manleva??" code="schedaFondoRischi.label.manleva" />
															</label>
													 	</td>
													    <td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.manlevaDesc}"></c:out> 
															</div>
														</td>
														<td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.manlevaPrecDesc}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- COMMESSA DI INVESTIMENTO -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="commessaInvestimento">
																<spring:message text="??schedaFondoRischi.label.commessaInvestimento??" code="schedaFondoRischi.label.commessaInvestimento" />
															</label>
													 	</td>
													    <td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.commessaInvestimentoDesc}"></c:out> 
															</div>
														</td>
														<td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.commessaInvestimentoPrecDesc}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- PASSIVITA' STIMATA -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="passivitaStimata">
																<spring:message text="??schedaFondoRischi.label.passivitaStimata??" code="schedaFondoRischi.label.passivitaStimata" />
															</label>
													 	</td>
													    <td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.passivitaStimataDesc}"></c:out> 
															</div>
														</td>
														<td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.passivitaStimataPrecDesc}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- MOTIVAZIONE -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="motivazione">
																<spring:message text="??schedaFondoRischi.label.motivazione??" code="schedaFondoRischi.label.motivazione" />
															</label>
													 	</td>
													    <td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.motivazione}"></c:out> 
															</div>
														</td>
														<td style="width:35%">		
															<div class="col-md-12">
																<c:out value="${schedaFondoRischiView.motivazionePrecedente}"></c:out> 
															</div>
														</td>
													 </tr>
												</table> 
									        </fieldset>
					
											<div class="modal-footer">
												<div class="col-md-12 column">
										        	<div class="btn-group dropup pull-right ">
														<div class="btn-group pull-right space-to-left"> </div>
														<div class="btn-group pull-right"> </div>
														<div class="btn-group pull-right"> </div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!--/ fine card -->
				</div>
			</div>
		</div>
		</section>
	</section>
<jsp:include page="/parts/script-end.jsp"></jsp:include>
</body>
</html>