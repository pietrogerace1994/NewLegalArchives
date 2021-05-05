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
	
	#beautyContestStampa td{ padding-top:5px;padding-bottom:5px;}
	#beautyContestStampa label,input{ text-align:left;}	
</style>

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
						<div class="card-header ch-dark palette-Green-SNAM">
							 
					        <div id="box-stampa" style="width:100%;padding-bottom:20px;position:relative;"> 
					        	<a href="javascript:void(0)" onclick="javascript:window.print()" 
					        	style="position:absolute;top:10px;right: 20px;z-index: 1000;color: #000000;font-weight: bold;">
									<spring:message text="??beautyContest.button.stampa??" code="beautyContest.button.stampa" />
								</a>
					        </div>	
						</div>
						<div class="card-body">
						
							<!-- Area Dettaglio Beauty Contest-->
				
							<div class="table-responsive">
					
								<div class="col-md-12">
									<div class="col-md-12">
										<div class="col-md-12" style="margin-bottom: 20px;margin-top: 20px;border-bottom: 1px">
											<h4><spring:message text="??beautyContest.label.dettaglioBeautyContest??" code="beautyContest.label.dettaglioBeautyContest" /></h4> 
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
												<table id="beautyContestStampa" style="width: 100%" border="1px"> 
											    	
											    	<!-- TITOLO -->
											    	<tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="titolo">
																<spring:message text="??beautyContest.label.titolo??" code="beautyContest.label.titolo" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${beautyContestView.titolo}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- STATO -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="stato">
																<spring:message text="??beautyContest.label.stato??" code="beautyContest.label.stato" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${beautyContestView.statoBeautyContest}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- DATA EMISSIONE -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="dataEmissione">
																<spring:message text="??beautyContest.label.dataEmissione??" code="beautyContest.label.dataEmissione" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${beautyContestView.dataEmissione}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													  <!-- DATA CHIUSURA -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="dataChiusura">
																<spring:message text="??beautyContest.label.dataChiusura??" code="beautyContest.label.dataChiusura" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${beautyContestView.dataChiusura}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- MATERIE -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for=materie>
																<spring:message text="??beautyContest.label.materie??" code="beautyContest.label.materie" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:if test="${ beautyContestView.listaMaterieAggiunteDesc != null }">
																	<c:forEach items="${beautyContestView.listaMaterieAggiunteDesc}" var="oggetto">
																		<c:out value="${oggetto}"></c:out>
																	</c:forEach>
																</c:if>
															</div>
														</td>
													 </tr>
													 
													 <!-- NAZIONE -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="nazione">
																<spring:message text="??beautyContest.label.nazione??" code="beautyContest.label.nazione" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${beautyContestView.nazioneDesc}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- UNITA LEGALE -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="nazione">
																<spring:message text="??beautyContest.label.unitaLegale??" code="beautyContest.label.unitaLegale" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${beautyContestView.unitaLegale}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- CENTRO DI COSTO -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="nazione">
																<spring:message text="??beautyContest.label.centroDiCosto??" code="beautyContest.label.centroDiCosto" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${beautyContestView.cdc}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- INCARICO/ACCORDO QUADRO -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="nazione">
																<spring:message text="??beautyContest.label.incaricoAccordoQuadro??" code="beautyContest.label.incaricoAccordoQuadro" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${beautyContestView.incaricoAccordoQuadro}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- DESCRIZIONE SOW -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="nazione">
																<spring:message text="??beautyContest.label.descrizioneSow??" code="beautyContest.label.descrizioneSow" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${beautyContestView.descrizioneSow}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!--DATA RICHIESTA AUTORIZZAZIONE -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="dataRischiestaAutorizzazione">
																<spring:message text="??beautyContest.label.dataRichiestaAutorizzazione??" code="beautyContest.label.dataRichiestaAutorizzazione" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${beautyContestView.dataRichiestaAutorizzazione}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!--DATA AUTORIZZAZIONE -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="dataAutorizzazione">
																<spring:message text="??beautyContest.label.dataAutorizzazione??" code="beautyContest.label.dataAutorizzazione" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${beautyContestView.dataAutorizzazione}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- CANDIDATI-->
													 <tr>
													 	<td style="width:30%">			
															<label class="col-md-12 control-label" for="societa">
																<spring:message text="??beautyContest.label.candidati??" code="beautyContest.label.candidati" />
															</label>
													    </td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:if test="${ beautyContestView.listaPartecipantiAggiunti != null }">
																	<c:forEach items="${beautyContestView.listaPartecipantiAggiunti}" var="oggetto">
																		<c:out value="${oggetto.vo.cognomeNome}"></c:out>
																	</c:forEach>
																</c:if>
															</div>
														</td>
													 </tr>
													 
													 
													 <!-- VINCITORE -->
													<c:if test="${ beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">
									
														<c:if test="${ beautyContestView.statoBeautyContestCode eq 'A' }">
															
															<c:if test="${ beautyContestView.vincitoreSelezionato != null }">
															
																<tr>
																	<td style="width:30%">			
																		<label class="col-md-12 control-label" for="vincitore">
																			<spring:message text="??beautyContest.label.vincitore??" code="beautyContest.label.vincitore" />
																		</label>
																 	</td>
																    <td style="width:70%">		
																		<div class="col-md-12">
																			<c:out value="${beautyContestView.vincitoreSelezionato.vo.cognomeNome}"></c:out> 
																		</div>
																	</td>
																 </tr>
																 
															 </c:if>
														</c:if>
													</c:if>
												</table> 
									        </fieldset>
					
											<div class="modal-footer">
												<div class="col-md-12 column">
										        	<div class="btn-group dropup pull-right ">
														<div class="btn-group pull-right space-to-left"> </div>
														<!-- pulsante esporta senza opzioni -->
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
<script src="<%=request.getContextPath()%>/portal/js/controller/beautyContest.js"></script>

</body>
</html>