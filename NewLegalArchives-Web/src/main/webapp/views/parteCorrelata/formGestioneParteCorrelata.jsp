<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld" %>
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
#tabellaPartiCorrelate {
    font-size: 80%;
    max-height:600px;overflow:auto;-ms-overflow-style: auto;
}
</style>

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
									<h2> 
										<spring:message text="??parteCorrelata.label.gestione??"
											code="parteCorrelata.label.gestione" />
									</h2>
							</div>
							
							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form  name="parteCorrelataForm" method="post" modelAttribute="parteCorrelataView" 
											action="salva.action" class="form-horizontal la-form">
									<engsecurity:token regenerate="false"/>
									
									
									<form:errors path="*" cssClass="alert alert-danger"
										element="div"></form:errors>
									<c:if test="${ not empty param.successMessage }">									
										<div class="alert alert-info">
											<spring:message code="messaggio.operazione.ok" text="??messaggio.operazione.ok??"></spring:message>
										</div>											
									</c:if>	
									<c:if test="${ not empty param.errorMessage }">									
										<div class="alert alert-danger">
											<spring:message code="${param.errorMessage}" text="??${param.errorMessage}??"></spring:message>
										</div>											
									</c:if>	
									
								
											
									<form:hidden path="parteCorrelataId" value="${parteCorrelataView.vo.id}"/>
									<form:hidden path="op" id="op" value="salvaParteCorrelata"/>
									<form:hidden path="tabAttiva" id="tabAttiva" />
									<form:hidden path="insertMode" id="insertMode" value="true" />
									<form:hidden path="editMode" id="editMode" value="false" />
									<form:hidden path="deleteMode" id="deleteMode" value="false" />
											
									<c:if test="${parteCorrelataView.tabAttiva eq '1' }">
										<c:set var="tab1StyleAttiva" scope="page" value="active" />
									</c:if>
									<c:if test="${parteCorrelataView.tabAttiva eq '2' }">
										<c:set var="tab2StyleAttiva" scope="page" value="active" />
									</c:if>
									<c:if test="${parteCorrelataView.tabAttiva eq '3' }">
										<c:set var="tab3StyleAttiva" scope="page" value="active" />
									</c:if>
									
									<ul class="tab-nav tn-justified tn-icon" role="tablist">
										<li role="presentation" class='${tab1StyleAttiva}'
											onclick="javascript:insertCheck()">
										<a class="col-sx-4" href="#tab-1" aria-controls="tab-1"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-star icon-tab" aria-hidden="true"></i>-->
												<small><spring:message
														text="??parteCorrelata.label.crea??"
														code="parteCorrelata.label.crea" /></small>
										</a></li>
										<li role="presentation" class='${tab2StyleAttiva}'
											onclick="editCheck(); puliscoAutocompletePcMod();"><a
											class="col-xs-4" href="#tab-2" aria-controls="tab-2"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-search icon-tab" aria-hidden="true"></i>-->
												<small><spring:message
														text="??parteCorrelata.label.modificacancellazione??"
														code="parteCorrelata.label.modificacancellazione" /></small>
										</a></li>
										<li role="presentation" class='${tab3StyleAttiva}'
											onclick="visCheck(); puliscoFiltroRicercaFormVis();"><a
											class="col-xs-4" href="#tab-3" aria-controls="tab-3"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-tags icon-tab" aria-hidden="true"></i>-->
												<small><spring:message
														text="??parteCorrelata.label.visualizza??"
														code="parteCorrelata.label.visualizza" /></small>
										</a></li>
									</ul>
									
									<div class="tab-content p-20">
										
										<div role="tabpanel"
											class="tab-pane animated fadeIn in ${tab1StyleAttiva}"
											id="tab-1">
											
											
											<div id="creaDiv">
											
												<!-- DENOMINAZIONE --> 	
												<div class="list-group lg-alt">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="denominazione" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.denominazione??"
																		code="parteCorrelata.label.denominazione" /></label>
																<div class="col-sm-10">
																	 <form:input path="denominazione" cssClass="form-control" 
																	 value="${parteCorrelataView.getVo().denominazione}"/>
																</div>
															</div>
														</div>
													</div>
												</div>
											
												<!-- CODICE FISCALE -->
												<div class="list-group lg-alt">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="codFiscale" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.codFiscale??"
																		code="parteCorrelata.label.codFiscale" /></label>
																<div class="col-sm-10">
																	 <form:input path="codFiscale" cssClass="form-control" 
																	 			 value="${parteCorrelataView.getVo().codFiscale}"/>
																</div>
															</div>
														</div>
													</div>
												</div>
												
												<!-- PARTITA IVA -->
												<div class="list-group lg-alt">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="partitaIva" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.partitaIva??"
																		code="parteCorrelata.label.partitaIva" /></label>
																<div class="col-sm-10">
																	 <form:input path="partitaIva" cssClass="form-control" 
																	 			 value="${parteCorrelataView.getVo().partitaIva}"/>
																</div>
															</div>
														</div>
													</div>
												</div>
												
												<!-- NAZIONE -->
												<div class="list-group lg-alt">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="nazione" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.nazione??"
																		code="parteCorrelata.label.nazione" /></label>
																<div class="col-sm-10"> 
																	<form:select path="nazioneCode" cssClass="form-control" >
																		<form:option value="">
																			<spring:message
																				text="??parteCorrelata.label.selezionaNazione??"
																				code="parteCorrelata.label.selezionaNazione" />
																		</form:option>
																		<c:if test="${ parteCorrelataView.listaNazioni != null }">
																			<c:forEach items="${parteCorrelataView.listaNazioni}" var="oggetto">
																				<c:choose>
																					<c:when test="${oggetto.vo.codGruppoLingua eq parteCorrelataView.getVo().nazione.codGruppoLingua}">
																						<form:option value="${oggetto.vo.codGruppoLingua }" selected="true">
																							<c:out value="${oggetto.vo.descrizione}"></c:out>
																						</form:option>
																					</c:when>
																					<c:otherwise>
																						<form:option value="${oggetto.vo.codGruppoLingua }">
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
												</div>
											
												<!-- TIPO CORRELAZIONE -->
												<div class="list-group lg-alt">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="tipoCorrelazione" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.tipoCorrelazione??"
																		code="parteCorrelata.label.tipoCorrelazione"/></label>
																<div class="col-sm-10"> 
																	<form:select id="comboTipoCorrelazione" path="tipoCorrelazioneCode" 
																				 onchange="selezionaTipoCorrelazione(this.value)" 
																				 cssClass="form-control"
																				 >
																		<form:option value="">
																			<spring:message
																				text="??parteCorrelata.label.selezionaTipoCorrelazione??"
																				code="parteCorrelata.label.selezionaTipoCorrelazione" /></form:option>
																		<c:if test="${ parteCorrelataView.listaTipoCorrelazione != null }">
																			<c:forEach items="${parteCorrelataView.listaTipoCorrelazione}" var="oggetto">
																				<c:choose>
																					<c:when test="${oggetto.vo.codGruppoLingua eq parteCorrelataView.getVo().tipoCorrelazione.codGruppoLingua}">
																						<form:option value="${oggetto.vo.codGruppoLingua }" selected="true">
																							<c:out value="${oggetto.vo.descrizione}"></c:out>
																						</form:option>
																					</c:when>
																					<c:otherwise>
																						<form:option value="${oggetto.vo.codGruppoLingua }">
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
												</div>
												
												<!-- FAMILIARE -->
											 	<div class="list-group lg-alt divFamiliareInput" style="display: none">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="familiare" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.familiare??"
																		code="parteCorrelata.label.familiare" /></label>
																<div class="col-sm-10">
																	 <form:input path="familiare" cssClass="form-control" 
																	 			 value="${parteCorrelataView.getVo().familiare}"/>
																</div>
															</div>
														</div>
													</div>
												</div>
											
												<!-- RAPPORTO -->
												<c:if test="${ parteCorrelataView.rapporto != null }">
													<c:set var="displayRapporto" scope="page" value="block" />
												</c:if>
												<c:if test="${ parteCorrelataView.rapporto == null }">
													<c:set var="displayRapporto" scope="page" value="none" />
												</c:if>
												
											 	<div class="list-group lg-alt divRapportoInput" style="display: ${displayRapporto}">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="rapporto" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.rapporto??"
																		code="parteCorrelata.label.rapporto" /></label>
																<div class="col-sm-10">
																	 <form:input path="rapporto" cssClass="form-control" 
																	 			 value="${parteCorrelataView.getVo().rapporto}"/>
																</div>
															</div>
														</div>
													</div>
												</div>
										
												<!-- CONSIGLIERI SINDACI -->
												<c:if test="${ parteCorrelataView.consiglieriSindaci != null }">
													<c:set var="displayConsiglieriSindaci" scope="page" value="block" />
												</c:if>
												<c:if test="${ parteCorrelataView.consiglieriSindaci == null }">
													<c:set var="displayConsiglieriSindaci" scope="page" value="none" />
												</c:if>
												
											 	<div class="list-group lg-alt divConsiglieriSindaciInput" style="display: ${displayConsiglieriSindaci}">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="consiglieriSindaci" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.consiglieriSindaci??"
																		code="parteCorrelata.label.consiglieriSindaci" /></label>
																<div class="col-sm-10">
																	 <form:input path="consiglieriSindaci" cssClass="form-control" 
																	 			 value="${parteCorrelataView.getVo().consiglieriSindaci}"/>
																</div>
															</div>
														</div>
													</div>
												</div>
														
											</div><!-- creaDiv -->
														
										</div><!-- tab1 -->
										
										
										<div role="tabpanel"
											class="tab-pane animated fadeIn in ${tab2StyleAttiva}"
											id="tab-2">
											
											<div id="divModCanc">
											
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label for="parteCorrelataIdMod" class="col-sm-2 control-label">
																<i class="zmdi zmdi-search hs-reset" data-ma-action="search-clear" style="cursor: default !important; color:black !important; left:100px;"></i>
															</label>
															<div class="col-sm-10">
																
																<div id="" style="max-height:250px;overflow:auto;-ms-overflow-style: auto;">
																	<input id="filtroListaParteCorrelata" type="text" class="form-control">
																	
																</div> 
																	
																	
															</div>
														</div>
													</div>
												</div>
											
												
												<!-- LISTA -->
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label for="parteCorrelataIdMod" class="col-sm-2 control-label"><spring:message
																	text="??parteCorrelata.label.lista??"
																	code="parteCorrelata.label.lista" /></label>
															<div class="col-sm-10">
																
																<div id="parteCorrelataIdDivMod" style="max-height:250px;overflow:auto;-ms-overflow-style: auto;">
																<form:select 
																		size="5"
																		path="parteCorrelataIdMod"
																		onchange="caricaParteCorrelataMod(this.value)"
																		onfocus="editCheck()"
																		cssClass="form-control">
																		<c:if test="${ parteCorrelataView.listaParteCorrelata != null }">
																			<c:forEach
																				items="${parteCorrelataView.listaParteCorrelata}"
																				var="oggetto">
																				<form:option value="${ oggetto.vo.id }">
																					<c:out value="${oggetto.vo.denominazione}"></c:out>
																				</form:option>
																			</c:forEach>
																		</c:if>
																	</form:select>
																	</div> 
																	
																	
															</div>
														</div>
													</div>
												</div>
											
										
												
												<!-- DENOMINAZIONE --> 	
												<div class="list-group lg-alt">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="denominazioneMod" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.denominazione??"
																		code="parteCorrelata.label.denominazione" /></label>
																<div class="col-sm-10">
																	 <form:input path="denominazioneMod" cssClass="form-control" 
																	 value="${parteCorrelataView.getVo().denominazione}"/>
																</div>
															</div>
														</div>
													</div>
												</div>
											
												<!-- CODICE FISCALE -->
												<div class="list-group lg-alt">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="codFiscaleMod" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.codFiscale??"
																		code="parteCorrelata.label.codFiscale" /></label>
																<div class="col-sm-10">
																	 <form:input path="codFiscaleMod" cssClass="form-control" 
																	 			 value="${parteCorrelataView.getVo().codFiscale}"/>
																</div>
															</div>
														</div>
													</div>
												</div>
												
												<!-- PARTITA IVA -->
												<div class="list-group lg-alt">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="partitaIvaMod" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.partitaIva??"
																		code="parteCorrelata.label.partitaIva" /></label>
																<div class="col-sm-10">
																	 <form:input path="partitaIvaMod" cssClass="form-control" 
																	 			 value="${parteCorrelataView.getVo().partitaIva}"/>
																</div>
															</div>
														</div>
													</div>
												</div>
													
												<!-- NAZIONE -->
												<div class="list-group lg-alt">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="nazioneCodeMod" class="col-sm-2 control-label"><spring:message
																		text="??parteCorrelata.label.nazione??"
																		code="parteCorrelata.label.nazione" /></label>
																<div class="col-sm-10"> 
																	<form:select path="nazioneCodeMod" cssClass="form-control" >
																		<form:option value="">
																			<spring:message
																				text="??parteCorrelata.label.selezionaNazione??"
																				code="parteCorrelata.label.selezionaNazione" />
																		</form:option>
																		<c:if test="${ parteCorrelataView.listaNazioni != null }">
																			<c:forEach items="${parteCorrelataView.listaNazioni}" var="oggetto">
																				<c:choose>
																					<c:when test="${oggetto.vo.codGruppoLingua eq parteCorrelataView.getVo().nazione.codGruppoLingua}">
																						<form:option value="${oggetto.vo.codGruppoLingua }" selected="true">
																							<c:out value="${oggetto.vo.descrizione}"></c:out>
																						</form:option>
																					</c:when>
																					<c:otherwise>
																						<form:option value="${oggetto.vo.codGruppoLingua }">
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
													</div>

													<!-- TIPO CORRELAZIONE -->
													<div class="list-group lg-alt">
														<div class="list-group-item media">
															<div class="media-body">
																<div class="form-group">
																	<label for="tipoCorrelazioneCodeMod" class="col-sm-2 control-label"><spring:message
																			text="??parteCorrelata.label.tipoCorrelazione??"
																			code="parteCorrelata.label.tipoCorrelazione"/></label>
																	<div class="col-sm-10"> 
																		<form:select id="comboTipoCorrelazioneMod" path="tipoCorrelazioneCodeMod" 
																					 onchange="selTipoCorrelazioneMod(this.value)" 
																					 cssClass="form-control">
																			<form:option value="">
																				<spring:message
																					text="??parteCorrelata.label.selezionaTipoCorrelazione??"
																					code="parteCorrelata.label.selezionaTipoCorrelazione" /></form:option>
																			<c:if test="${ parteCorrelataView.listaTipoCorrelazione != null }">
																				<c:forEach items="${parteCorrelataView.listaTipoCorrelazione}" var="oggetto">
																					<c:choose>
																						<c:when test="${oggetto.vo.codGruppoLingua eq parteCorrelataView.getVo().tipoCorrelazione.codGruppoLingua}">
																							<form:option value="${oggetto.vo.codGruppoLingua }" selected="true">
																								<c:out value="${oggetto.vo.descrizione}"></c:out>
																							</form:option>
																						</c:when>
																						<c:otherwise>
																							<form:option value="${oggetto.vo.codGruppoLingua }">
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
													</div>
											
													<!-- FAMILIARE -->
												 	<div class="list-group lg-alt divFamiliareMod" style="display: none">
														<div class="list-group-item media">
															<div class="media-body">
																<div class="form-group">
																	<label for="familiareMod" class="col-sm-2 control-label"><spring:message
																			text="??parteCorrelata.label.familiare??"
																			code="parteCorrelata.label.familiare" /></label>
																	<div class="col-sm-10">
																		 <form:input path="familiareMod" cssClass="form-control" 
																		 			 value="${parteCorrelataView.getVo().familiare}"/>
																	</div>
																</div>
															</div>
														</div>
													</div>
											
													<!-- RAPPORTO -->
												 	<div class="list-group lg-alt divRapportoMod" style="display: none">
														<div class="list-group-item media">
															<div class="media-body">
																<div class="form-group">
																	<label for="rapportoMod" class="col-sm-2 control-label"><spring:message
																			text="??parteCorrelata.label.rapporto??"
																			code="parteCorrelata.label.rapporto" /></label>
																	<div class="col-sm-10">
																		 <form:input path="rapportoMod" cssClass="form-control" 
																		 			 value="${parteCorrelataView.getVo().rapporto}"/>
																	</div>
																</div>
															</div>
														</div>
													</div>
											
													<!-- CONSIGLIERI SINDACI -->
												 	<div class="list-group lg-alt divConsiglieriSindaciMod" style="display: none">
														<div class="list-group-item media">
															<div class="media-body">
																<div class="form-group">
																	<label for="consiglieriSindaciMod" class="col-sm-2 control-label"><spring:message
																			text="??parteCorrelata.label.consiglieriSindaci??"
																			code="parteCorrelata.label.consiglieriSindaci" /></label>
																	<div class="col-sm-10">
																		 <form:input path="consiglieriSindaciMod" cssClass="form-control" 
																		 			 value="${parteCorrelataView.getVo().consiglieriSindaci}"/>
																	</div>
																</div>
															</div>
														</div>
													</div>
													
												</div><!-- divModCanc -->
										
											</div><!-- tab2 -->
										
										<div role="tabpanel"
											class="tab-pane animated fadeIn in ${tab3StyleAttiva}"
											id="tab-3">
											
											<div id="divVisc" class="container">
												
											<div class="row  text-right">
											  <div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
											  	
											  	<table style="margin:0; padding:0; width:100%; border:0px;">
											  		<tr>
											  			<td  style=" vertical-align: top;">
											  			<div class="visible-lg visible-md visible-xs visible-sm text-left">
															Non hai trovato quello che cercavi? Prova a
															<a data-toggle="modal" href="#panelRicerca" class=""> inserire ulteriori parametri per affinare la ricerca
															</a>
														</div>
											  			</td>
											  			<td  style="text-align:right">
											  				<ol class="breadcrumb" style="float:right">
															  <li id="estraiUno" onclick="estraiSel(1)">
															    <a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/parteCorrelata/download.action?tipoEstrazione=T')}">Estrai Tutto</a>
															    
															  </li>
															  <li id="estraiDue" onclick="estraiSel(2)"><a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/parteCorrelata/download.action?tipoEstrazione=A')}">Estrai Attive</a></li>
															  <li id="estraiTre" onclick="estraiSel(3)"><a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/parteCorrelata/download.action?tipoEstrazione=N')}">Estrai Non Attive</a></li>
															</ol>
											  			</td>
											  		</tr>
											  	</table>
											  	
											  
												
											   </div>
											</div>
											<div class="row">
											   <div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
												<div class="table-responsive" >
													<table id="tabellaPartiCorrelate" data-search="true"
													 data-pagination="true"
													 class="table table-striped table-responsive">
								                            
								                               
								                    </table>
								               </div>
											  </div>
											</div>						
												
											
											</div><!-- divVisc -->
										</div><!-- tab3 -->
										
									</div>
									
								</form:form>
								<button form="parteCorrelataForm" onclick="cancellaParteCorrelata()" 
									style="display: none"
									class="btn palette-Green-SNAM bg btn-float2 waves-effect waves-circle waves-float btnCancella">
									<i class="zmdi zmdi-delete"></i>
								</button>
								<button form="parteCorrelataForm" onclick="salvaParteCorrelata()"
									class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float btnSalva">
									<i class="zmdi zmdi-save"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>		
			<!--/ fine col-1 -->
		</section>

	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>
	
	
<!-- PANEL RICERCA MODALE -->
<div class="modal fade"  role="dialog" id="panelRicerca">
	<div class="modal-dialog" role="document">
    	<div class="modal-content">
      		<div class="modal-header">
        		<b class="modal-title" style="font-size:90%;"><spring:message text="??ricerca.label.ricerca??" code="ricerca.label.ricerca" /></b>
      		</div>
      		<div class="modal-body">
        		<form:form  name="parteCorrelataRicercaVisForm" method="post" modelAttribute="parteCorrelataView" action="" class="form-horizontal la-form">
				
					<!-- DENOMINAZIONE --> 	
					<div class="list-group lg-alt">
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="denominazioneRic" class="col-sm-4 control-label"><spring:message
											text="??parteCorrelata.label.denominazione??"
											code="parteCorrelata.label.denominazione" /></label>
									<div class="col-sm-8">
										 <input type="text" id="denominazioneRic" name="denominazioneRic" class="form-control" 
										 >
									</div>
								</div>
							</div>
						</div>
					</div>
												
					<!-- CODICE FISCALE -->
					<div class="list-group lg-alt">
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="codFiscaleRic" class="col-sm-4 control-label"><spring:message
											text="??parteCorrelata.label.codFiscale??"
											code="parteCorrelata.label.codFiscale" /></label>
									<div class="col-sm-8">
										<input type="text" id="codFiscaleRic" name="codFiscaleRic" class="form-control" >
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<!-- PARTITA IVA -->
					<div class="list-group lg-alt">
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="partitaIvaRic" class="col-sm-4 control-label"><spring:message
											text="??parteCorrelata.label.partitaIva??"
											code="parteCorrelata.label.partitaIva" /></label>
									<div class="col-sm-8">
										<input type="text" id="partitaIvaRic" name="partitaIvaRic" class="form-control" >
									</div>
								</div>
							</div>
						</div>
					</div>
					
					
					<!-- STATO -->
					<div class="list-group lg-alt">
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="statoRic" class="col-sm-4 control-label"><spring:message
											text="??parteCorrelata.label.stato??"
											code="parteCorrelata.label.stato" /></label>
									<div class="col-sm-8"> 
										<select id="statoRic" name="statoRic" class="form-control">
											<option value="">
												Seleziona Stato ...
											</option>
											<option value="attiva">
												<spring:message text="??parteCorrelata.label.statoAttivo??" code="parteCorrelata.label.statoAttivo" />
											</option>
											<option value="non attiva">
												<spring:message text="??parteCorrelata.label.statoNonAttivo??" code="parteCorrelata.label.statoNonAttivo" />
											</option>
										</select> 
									</div>
								</div>
							</div>
						</div> 
					</div>
					
													
					<!-- NAZIONE -->
					<div class="list-group lg-alt">
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="nazioneRic" class="col-sm-4 control-label"><spring:message
											text="??parteCorrelata.label.nazione??"
											code="parteCorrelata.label.nazione" /></label>
									<div class="col-sm-8"> 
										<form:select id="nazioneRic" path="nazioneCode" cssClass="form-control" size="5">
											<form:option value="0">
												Seleziona Nazione ...
											</form:option>
											<c:if test="${ parteCorrelataView.listaNazioni != null }">
												<c:forEach items="${parteCorrelataView.listaNazioni}" var="oggetto">
													<c:choose>
														<c:when test="${oggetto.vo.codGruppoLingua eq parteCorrelataView.getVo().nazione.codGruppoLingua}">
															<form:option value="${oggetto.vo.codGruppoLingua }" selected="true">
																<c:out value="${oggetto.vo.descrizione}"></c:out>
															</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${oggetto.vo.codGruppoLingua }">
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
					</div>
											
					<!-- TIPO CORRELAZIONE -->
					<div class="list-group lg-alt">
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="comboTipoCorrelazioneRic" class="col-sm-4 control-label"><spring:message
											text="??parteCorrelata.label.tipoCorrelazione??"
											code="parteCorrelata.label.tipoCorrelazione"/></label>
									<div class="col-sm-8"> 
										<form:select id="comboTipoCorrelazioneRic" path="tipoCorrelazioneCode" 
													 onchange="selezionaTipoCorrelazione(this.value)" 
													 cssClass="form-control"
													 >
											<form:option value="0">
												Seleziona Tipo Correlazione ...
													</form:option>
											<c:if test="${ parteCorrelataView.listaTipoCorrelazione != null }">
												<c:forEach items="${parteCorrelataView.listaTipoCorrelazione}" var="oggetto">
													<c:choose>
														<c:when test="${oggetto.vo.codGruppoLingua eq parteCorrelataView.getVo().tipoCorrelazione.codGruppoLingua}">
															<form:option value="${oggetto.vo.codGruppoLingua }" selected="true">
																<c:out value="${oggetto.vo.descrizione}"></c:out>
															</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${oggetto.vo.codGruppoLingua }">
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
					</div>
						
					<!-- RAPPORTO -->
				 	<div class="list-group lg-alt" >
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="rapportoRic" class="col-sm-4 control-label"><spring:message
											text="??parteCorrelata.label.rapporto??"
											code="parteCorrelata.label.rapporto" /></label>
									<div class="col-sm-8">
										 <input id="rapportoRic" name="rapportoRic" class="form-control" >
									</div>
								</div>
							</div>
						</div>
					</div>	
					
					<!-- FAMILIARE -->
				 	<div class="list-group lg-alt">
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="familiareRic" class="col-sm-4 control-label"><spring:message
											text="??parteCorrelata.label.familiare??"
											code="parteCorrelata.label.familiare" /></label>
									<div class="col-sm-8">
										 <input id="familiareRic" name="familiareRic" class="form-control"> 
									</div>
								</div>
							</div>
						</div>
					</div>						
							
					<!-- CONSIGLIERI SINDACI -->
				 	<div class="list-group lg-alt">
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="consiglieriSindaciRic" class="col-sm-4 control-label"><spring:message
											text="??parteCorrelata.label.consiglieriSindaci??"
											code="parteCorrelata.label.consiglieriSindaci" /></label>
									<div class="col-sm-8">
										 <input id="consiglieriSindaciRic" name="consiglieriSindaciRic" class="form-control"> 
									</div>
								</div>
							</div>
						</div>
					</div>					
										
					<!-- DATA INSERIMENTO -->
					<div class="list-group lg-alt">
						<div class="list-group-item media">
							<div class="media-body media-body-datetimepiker">
								<div class="form-group">
									<label for="dataInserimentoRic" class="col-lg-4 control-label"><spring:message
											text="??parteCorrelata.label.dataInserimento??"
											code="parteCorrelata.label.dataInserimento" /></label>
									<div class="col-lg-3">
										<input  id="dataInserimentoRic" name="dataInserimentoRic" 
											class="form-control date-picker" >
									</div>

								</div>
							</div>
						</div>
					</div>				
											
				</form:form>								
												
      		</div>
			<div class="modal-footer">
			  <button type="button" class="btn btn-primary" data-dismiss="modal" id="btnmodalAffinareRicerca">Affina la ricerca</button>
			  <button type="button" class="btn btn-default" data-dismiss="modal" style="background-color: #FF9801;color:white;">Chiudi</button>
			</div>
    	</div><!-- /.modal-content -->
  	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- PANEL RICERCA MODALE -->
	
<jsp:include page="/parts/script-end.jsp"></jsp:include>
<!-- si carica il js -->

<script charset="UTF-8" type="text/javascript">
	<c:if test="${ empty parteCorrelataView.jsonArrayParteCorrelataMod }">
		var jsonArrayParteCorrelataMod = '';
	</c:if>

	<c:if test="${ not empty parteCorrelataView.jsonArrayParteCorrelataMod }">
		var jsonArrayParteCorrelataMod = JSON.parse('${parteCorrelataView.jsonArrayParteCorrelataMod}');
	</c:if>
</script>

<script src="<%=request.getContextPath()%>/portal/js/controller/parteCorrelataNew.js"></script>
	
</body>
</html>
