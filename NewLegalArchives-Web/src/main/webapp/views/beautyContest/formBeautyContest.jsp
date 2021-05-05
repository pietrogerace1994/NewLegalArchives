<%@page import="eng.la.util.costants.Costanti"%>
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
								<c:if
									test="${ beautyContestView.beautyContestId == null || beautyContestView.beautyContestId == 0 }">
									<h2>
										<spring:message text="??beautyContest.label.nuovoBeautyContest??"
											code="beautyContest.label.nuovoBeautyContest" />
									</h2>
								</c:if>
								<c:if
									test="${ beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">
									<h2>
										<spring:message text="??beautyContest.label.modificaBeautyContest??"
											code="beautyContest.label.modificaBeautyContest" />
									</h2>
								</c:if>
							</div>
							
							<div class="card-body">

								<form:form name="beautyContestForm" method="post" 
									modelAttribute="beautyContestView" action="salva.action" 
									class="form-horizontal la-form">
									<engsecurity:token regenerate="false"/>
									
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
									
									<form:errors path="*" cssClass="alert alert-danger" htmlEscape="false" element="div"></form:errors>
									
									<div id="sezioneMessaggiApplicativi"></div>
									
									<form:hidden path="beautyContestId"  id="beautyContestId"/>
									<form:hidden path="op" id="op" value="salvaBeautyContest" />
									
									<c:if test="${ beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">
									
									<!-- TITOLO -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="titolo" class="col-sm-2 control-label"><spring:message
															text="??beautyContest.label.titolo??"
															code="beautyContest.label.titolo" /></label>
													<div class="col-sm-10">
														<form:input path="titolo" cssClass="form-control" readonly="${beautyContestView.isAutorizzato }"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									</c:if>
									
									<c:if test="${ beautyContestView.beautyContestId eq null || beautyContestView.beautyContestId eq 0 }">
									
									<!-- TITOLO -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="titolo" class="col-sm-2 control-label"><spring:message
															text="??beautyContest.label.titolo??"
															code="beautyContest.label.titolo" /></label>
													<div class="col-sm-10">
														<form:input path="titolo" cssClass="form-control"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									</c:if>
									
									<c:if test="${ beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">
									
									<!--STATO FASCICOLO-->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for=stato class="col-sm-2 control-label"><spring:message
														text="??beautyContest.label.stato??" code="beautyContest.label.stato" /></label>
													<div class="col-sm-10">
														<form:input path="statoBeautyContest" cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div>
									</c:if>
									
									<!-- DATA EMISSIONE -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="dataEmissione" class="col-sm-2 control-label"><spring:message
															text="??beautyContest.label.dataEmissione??"
															code="beautyContest.label.dataEmissione" /></label>
													<div class="col-sm-10">
														<form:input path="dataEmissione" cssClass="form-control" disabled="true"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<!-- DATA CHIUSURA -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body media-body-datetimepiker">
												<div class="form-group">
													<label for="dataEmissione" class="col-sm-2 control-label"><spring:message
															text="??beautyContest.label.dataChiusura??"
															code="beautyContest.label.dataChiusura" /></label>
													<div class="col-sm-10">
														<form:input path="dataChiusura" cssClass="form-control date-picker"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									
									<c:if test="${ beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">
									
										<c:if test="${ beautyContestView.statoBeautyContestCode eq 'A' }">
									
										<!-- MATERIA -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="comboMaterie" class="col-sm-2 control-label"><spring:message
															text="??beautyContest.label.materie??" code="beautyContest.label.materie" /></label>
													<div class="col-sm-10">
														<div class="table-responsive" style="clear: both;">
															<table class="table table-striped table-responsive">
																<thead>
																	<tr style="border: 1px solid #e0e0e0">
																		<th data-column-id="01" style="width: 50px">
																			<button type="button" data-toggle="collapse"
																				data-target="#boxMateria"
																				class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																				style="float: left; position: relative !important;">
																				<i class="zmdi zmdi-collection-text icon-mini"></i>
																			</button>
																		</th>
																		<th data-column-id="id"><spring:message
																				text="??fascicolo.label.materia??"
																				code="fascicolo.label.materia" /></th>
									
																	</tr>
																</thead>
																<tbody id="boxMateria" class="collapse in">
																	<c:if test="${ beautyContestView.listaMaterieAggiunteDesc != null }">
																		<c:forEach items="${beautyContestView.listaMaterieAggiunteDesc}"
																			var="descrizione">
																			<tr>
																				<td colspan="2">${descrizione}</td>
																			</tr>
																		</c:forEach>
																	</c:if>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
										</div>
										</c:if>
										
										<c:if test="${ beautyContestView.statoBeautyContestCode ne 'A' }">
											<!-- MATERIA -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="comboMaterie" class="col-sm-2 control-label"><spring:message
																text="??beautyContest.label.materie??" code="beautyContest.label.materie" /></label>
														<div class="col-sm-10">
															 <div id="treeContainerMaterie" style="max-height:250px;overflow:auto;">
															 	
															 </div> 
														</div>
													</div>
												</div>
											</div>
										</c:if>
									
									</c:if>
									
									<c:if test="${ beautyContestView.beautyContestId eq null || beautyContestView.beautyContestId eq 0 }">
									
									<!-- MATERIA -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="comboMaterie" class="col-sm-2 control-label"><spring:message
														text="??beautyContest.label.materie??" code="beautyContest.label.materie" /></label>
												<div class="col-sm-10">
													 <div id="treeContainerMaterie" style="max-height:250px;overflow:auto;">
													 	
													 </div> 
												</div>
											</div>
										</div>
									</div>
									
									</c:if>
								
									<!-- NAZIONE-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="nazioneCode" class="col-sm-2 control-label"><spring:message
														text="??beautyContest.label.nazione??" code="beautyContest.label.nazione" /></label>
												<div class="col-sm-10">
													<form:select id="nazioneCode" path="nazioneCode" disabled="${beautyContestView.isAutorizzato }"
														cssClass="form-control">
														<form:option value="">
															<spring:message text="??fascicolo.label.selezionaNazione??"
																code="beautyContest.label.selezionaNazione" />
														</form:option>
														<c:if test="${ beautyContestView.listaNazioni != null }">
															<c:forEach items="${beautyContestView.listaNazioni}" var="oggetto">
																<c:if test="${ oggetto.vo.soloParteCorrelata eq 'F' }">
																	<form:option value="${ oggetto.vo.codGruppoLingua }">
																		<c:out value="${oggetto.vo.descrizione}"></c:out>
																	</form:option>
																</c:if>
															</c:forEach>
														</c:if>
													</form:select>
												</div>
											</div>
										</div>
									</div>
									
									<!-- UNITA LEGALE -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="unitaLegale" class="col-sm-2 control-label"><spring:message
															text="??beautyContest.label.unitaLegale??"
															code="beautyContest.label.unitaLegale" /></label>
													<div class="col-sm-10">
														<form:input path="unitaLegale" cssClass="form-control" disabled="true"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<c:if test="${ beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">
									
									<!-- CENTRO DI COSTO -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="centroDiCosto" class="col-sm-2 control-label">
													<spring:message text="??beautyContest.label.centroDiCosto??" code="beautyContest.label.centroDiCosto" />
												</label>
												<div class="col-sm-10">
													<form:input path="cdc" id="cdc" cssClass="form-control" readonly="${beautyContestView.isAutorizzato }"/>
												</div>
											</div>
										</div>
									</div>
									</c:if>
									
									<c:if test="${ beautyContestView.beautyContestId eq null || beautyContestView.beautyContestId eq 0 }">
										
										<!-- CENTRO DI COSTO -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="centroDiCosto" class="col-sm-2 control-label">
														<spring:message text="??beautyContest.label.centroDiCosto??" code="beautyContest.label.centroDiCosto" />
													</label>
													<div class="col-sm-10">
														<form:input path="cdc" id="cdc" cssClass="form-control"/>
													</div>
												</div>
											</div>
										</div>
									</c:if>
									
									<c:if test="${ beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">
									
									<!-- INCARICO/ACCORDO QUADRO -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="incaricoAccordoQuadro" class="col-sm-2 control-label">
													<spring:message text="??beautyContest.label.incaricoAccordoQuadro??" code="beautyContest.label.incaricoAccordoQuadro" />
												</label>
												<div class="col-sm-10">
													<label class="col-sm-2 control-label"> <spring:message
														text="??beautyContest.label.incarico??" code="beautyContest.label.incarico" />
													</label>
													<div class="col-sm-1">
														<form:radiobutton path="incaricoAccordoQuadro" id="incaricoAccordoQuadro" value="incarico" disabled="${beautyContestView.isAutorizzato }"/>
													</div>
													<label class="col-sm-2 control-label"> <spring:message
														text="??beautyContest.label.accordoQuadro??" code="beautyContest.label.accordoQuadro" />
													</label>
													<div class="col-sm-1">
														<form:radiobutton path="incaricoAccordoQuadro" id="incaricoAccordoQuadro" value="accordoQuadro" disabled="${beautyContestView.isAutorizzato }"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									</c:if>
									
									<c:if test="${ beautyContestView.beautyContestId eq null || beautyContestView.beautyContestId eq 0 }">
									
										<!-- INCARICO/ACCORDO QUADRO -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="incaricoAccordoQuadro" class="col-sm-2 control-label">
														<spring:message text="??beautyContest.label.incaricoAccordoQuadro??" code="beautyContest.label.incaricoAccordoQuadro" />
													</label>
													<div class="col-sm-10">
														<label class="col-sm-2 control-label"> <spring:message
															text="??beautyContest.label.incarico??" code="beautyContest.label.incarico" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="incaricoAccordoQuadro" id="incaricoAccordoQuadro" value="incarico"/>
														</div>
														<label class="col-sm-2 control-label"> <spring:message
															text="??beautyContest.label.accordoQuadro??" code="beautyContest.label.accordoQuadro" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="incaricoAccordoQuadro" id="incaricoAccordoQuadro" value="accordoQuadro"/>
														</div>
													</div>
												</div>
											</div>
										</div>
									</c:if>
									
									<c:if test="${ beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">
									
									<!-- DESCRIZIONE SOW -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="descrizioneSow" class="col-sm-2 control-label">
													<spring:message text="??beautyContest.label.descrizioneSow??" code="beautyContest.label.descrizioneSow" />
												</label>
												<div class="col-sm-10">
													<form:textarea path="descrizioneSow" cssClass="form-control" rows="8" cols="70" id="descrizioneSow" disabled="${beautyContestView.isAutorizzato }"/>
												</div>
											</div>
										</div>
									</div>
									</c:if>
									
									<c:if test="${ beautyContestView.beautyContestId eq null || beautyContestView.beautyContestId eq 0 }">
									
									<!-- DESCRIZIONE SOW -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="descrizioneSow" class="col-sm-2 control-label">
													<spring:message text="??beautyContest.label.descrizioneSow??" code="beautyContest.label.descrizioneSow" />
												</label>
												<div class="col-sm-10">
													<form:textarea path="descrizioneSow" cssClass="form-control" rows="8" cols="70" id="descrizioneSow" />
												</div>
											</div>
										</div>
									</div>
									
									</c:if>
									
									<!--DATA RICHIESTA AUTORIZZAZIONE-->
									<div class="list-group-item media">
										<div class="media-body media-body-datetimepiker">
											<div class="form-group">
												<label class="col-md-2 control-label" for="selectbasic"><spring:message
														text="??beautyContest.label.dataRichiestaAutorizzazione??"
														code="beautyContest.label.dataRichiestaAutorizzazione" /></label>
												<div class="col-md-10">
													<form:input id="txtDataRichiestaAutorizzazione"
														path="dataRichiestaAutorizzazione"
														cssClass="form-control date-picker" readonly="true" />
												</div>
											</div>
										</div>
									</div>

									<!--DATA AUTORIZZAZIONE-->
									<div class="list-group-item media">
										<div class="media-body media-body-datetimepiker">
											<div class="form-group">
												<label class="col-md-2 control-label" for="selectbasic"><spring:message
														text="??beautyContest.label.dataAutorizzazione??"
														code="beautyContest.label.dataAutorizzazione" /></label>
												<div class="col-md-10">
													<form:input id="txtDataAutorizzazione"
														path="dataAutorizzazione"
														cssClass="form-control date-picker" readonly="true" />
												</div>
											</div>
										</div>
									</div>
									
									<!-- CANDIDATI -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="societa" class="col-sm-2 control-label"><spring:message
														text="??beautyContest.label.candidati??"
														code="beautyContest.label.candidati" /></label>
												<div class="col-sm-10">
													<div class="table-responsive" style="clear:both;">
														<table class="table table-striped table-responsive" >
															<thead>
																<tr style="border:1px solid #e0e0e0">
																	<th data-column-id="01" style="width:50px"> 
																		<button  type="button"  data-toggle="collapse" data-target="#boxCandidati" 
																			class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"  style="float: left;position: relative !important;">
																			<i class="zmdi zmdi-collection-text icon-mini"></i>
																		</button>
																	</th> 
																	<th data-column-id="id"><spring:message
																			text="??beautyContest.label.candidati??"
																			code="beautyContest.label.candidati" /></th>
																	
																</tr>
															</thead>
															<tbody id="boxCandidati" class="collapse in">
																<c:if test="${ beautyContestView.listaProfessionistiEsterni != null }">
																	<c:forEach items="${beautyContestView.listaProfessionistiEsterni}" var="oggetto">
																	    <tr>
																			<td>
																				<form:checkbox value="${ oggetto.vo.id }"
																					path="partecipantiAggiunti"></form:checkbox>
																			</td>	
																			<td>
																				${oggetto.vo.cognomeNome}
																			</td>											
																		</tr>	
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>			
												</div>
											</div>
										</div>
									</div>
									
									<!-- VINCITORE -->
									<c:if test="${ beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">
									
										<c:if test="${ beautyContestView.statoBeautyContestCode eq 'A' }">
										
											<c:if test="${ not empty beautyContestView.vincitoreId}">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label for="vincitore" class="col-sm-2 control-label"><spring:message
																	text="??beautyContest.label.vincitore??" code="beautyContest.label.vincitore" /></label>
															<div class="col-sm-10">
																<input id="vincitoreId" class="form-control" readonly="true"
																value="${beautyContestView.vincitoreSelezionato.cognomeNome}"/>
															</div>
														</div>
													</div>
												</div>
											</c:if>
											
											<c:if test="${ empty beautyContestView.vincitoreId}">
												<div class="list-group-item media">
													<a name="anchorVincitore"></a>
													<div class="media-body">
														<div class="form-group">
															<label for="vincitore" class="col-sm-2 control-label"><spring:message
																	text="??beautyContest.label.vincitore??" code="beautyContest.label.vincitore" /></label>
															<div class="col-sm-10">
																<form:select id="vincitoreId" path="vincitoreId"
																	onchange="selezionaVincitore(this.value)"
																	cssClass="form-control">
																	<form:option value="">
																		<spring:message text="??beautyContest.label.selezionaVincitore??"
																			code="beautyContest.label.selezionaVincitore" />
																	</form:option>
																	<c:if test="${ beautyContestView.listaPartecipantiAggiunti != null }">
																		<c:forEach items="${beautyContestView.listaPartecipantiAggiunti}" var="oggetto">
																			<form:option value="${ oggetto.vo.id }">
																				<c:out value="${oggetto.vo.cognomeNome}"></c:out>
																			</form:option>
																		</c:forEach>
																	</c:if>
																</form:select>
															</div>
														</div>
													</div>
												</div>
											</c:if>
											
											<c:if test="${ not empty beautyContestView.vincitoreId}">
												<div id="boxNotaAggiudicazioneFirmata">
											
													<jsp:include
														page="/subviews/beautyContest/notaAggiudicazioneFirmata.jsp">
													</jsp:include>
												
												</div>
											</c:if>
										</c:if>
									</c:if>
									
									<c:if test="${ beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">
									
										<c:if test="${ beautyContestView.statoBeautyContestCode eq 'A' }">
										
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="societa" class="col-sm-2 control-label"><spring:message
																text="??beautyContest.label.elencoRisposte??"
																code="beautyContest.label.elencoRisposte" /></label>
														<div class="col-sm-10">
															<div class="table-responsive" style="clear:both;">
																<table class="table table-striped table-responsive" >
																	<thead>
																		<tr style="border:1px solid #e0e0e0">
																			<th data-column-id="01" class="col-sm-1"> 
																				<button  type="button"  data-toggle="collapse" data-target="#boxElencoRisposte" 
																					class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"  style="float: left;position: relative !important;">
																					<i class="zmdi zmdi-collection-text icon-mini"></i>
																				</button>
																			</th> 
																			<th data-column-id="02" class="col-sm-8"><spring:message
																					text="??beautyContest.label.elencoRisposte??"
																					code="beautyContest.label.elencoRisposte" />
																			</th>
																			<th data-column-id="03" class="col-sm-3">
																			</th>
																		</tr>
																	</thead>
																	<tbody id="boxElencoRisposte" class="collapse in">
																		<c:if test="${ beautyContestView.listaBeautyContestReplyView != null }">
																		<c:if test="${ not empty beautyContestView.listaBeautyContestReplyView  }">
																			
																			<c:forEach items="${beautyContestView.listaBeautyContestReplyView}" var="oggetto">
																			    <tr>
																			    	<td class="col-sm-1">
																			    		<button  type="button"  data-idbcr="${ oggetto.vo.id }" data-toggle="modal"
																						data-target="#panelDettaglioRispostaBeautyContest"
																						class="btn palette-Green-SNAM bg waves-effect waves-circle waves-float btn-circle-mini"  
																						style="float: left; position: relative !important;">
																							<i class="fa fa-eye" aria-hidden="true"></i>
																						</button>
																					</td>
																			    	
																					<td class="col-sm-8">
																						${oggetto.vo.professionista.cognomeNome}
																					</td>
																															
																					<td class="col-sm-3">
																						<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${oggetto.vo.dataInvio}"/>
																					</td>	
																				</tr>	
																			</c:forEach>
																		</c:if>
																		<c:if test="${ empty beautyContestView.listaBeautyContestReplyView }">
																			<tr>	
																				<td colspan="3"><spring:message code="fascicolo.label.tabella.no.dati"
																					text="??fascicolo.label.tabella.no.dati??"></spring:message>
																				</td>
																			</tr>
																		</c:if>
																		</c:if>
																	</tbody>
																</table>
															</div>			
														</div>
													</div>
												</div>
											</div>
										</c:if>
									</c:if>
									
									<c:if test="${beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">

										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="allegati" class="col-sm-2 control-label"><spring:message
															text="??beautyContest.label.allegati??"
															code="beautyContest.label.allegati" /></label>
													<div class="col-sm-10">
														<div class="list-group-item media">
															<div class="media-body">
																<div id="accordion" role="tablist"
																	aria-multiselectable="true">
																	
																	<!-- ALLEGATI -->
																	<div class="panel panel-default">
																		<div class="panel-heading" role="tab"
																			id="headingAllegati">
																			<h4 class="panel-title">
																				<button type="button" data-toggle="collapse"
																					data-target="#boxAllegati"
																					class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini collapsed"
																					style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																					aria-expanded="false">
																					<i class="zmdi zmdi-collection-text icon-mini"></i>
																				</button>

																				<button type="button" data-toggle="modal"
																					onclick="openModalAllegato()"
																					class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																					style="float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																					aria-expanded="true">
																					<i class="zmdi zmdi-plus icon-mini"></i>
																				</button>

																				<a id="allegatiGraffa"
																					style="display: none; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																					aria-expanded="true"> <i
																					class="fa fa-paperclip "></i>
																				</a> <a data-toggle="collapse" data-parent="#accordion"
																					href="#boxAllegati"> <spring:message
																						text="??beautyContest.label.allegaComunicazione??"
																						code="beautyContest.label.allegaComunicazione" />
																				</a>

																			</h4>
																		</div>
																		<div id="boxAllegati"
																			class="panel-collapse collapse" role="tabpanel"
																			aria-labelledby="headingAllegati">
																			<jsp:include
																				page="/subviews/beautyContest/allegati.jsp"></jsp:include>

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

								<div class="col-md-12" style="position: absolute; margin-top: -120px; width: 100%;">

									<button form="beautyContestForm" onclick="salvaBeautyContest()"
										class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float"
										style="position: relative !important;float: right;margin-right: 17px;">
										<i class="zmdi zmdi-save"></i>
									</button>
									
								<c:if test="${ beautyContestView.beautyContestId != null && beautyContestView.beautyContestId > 0 }">
									<jsp:include page="avviaWFBeautyContest.jsp"></jsp:include>
								</c:if>
									
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--/ fine col-1 -->
		</section>
	</section>
	
	<div class="modal fade" id="panelAggiungiNotaAggiudicazioneFirmata" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??beautyContest.label.aggiungiNotaAggiudicazioneFirmata??"
							code="beautyContest.label.aggiungiNotaAggiudicazioneFirmata" />
					</h4>
				</div>
				<div class="modal-body">
					<form id="formAggiungiNotaAggiudicazioneFirmata" method="post"
						enctype="application/x-www-form-urlencoded" class="form-horizontal">
	 					<engsecurity:token regenerate="false"/>
	 					<div class="form-group">
							<div class="col-md-8">
								<input type="file" name="fileNotaAggiudicazioneFirmata" id="fileNotaAggiudicazioneFirmata" />
							</div>
						</div>
						<!-- Button -->
						<div class="form-group">
							<div class="col-md-8">
								<button id="btnAggiungiNotaAggiudicazioneFirmata" name="btnAggiungiNotaAggiudicazioneFirmata"
									data-dismiss="modal" type="button" onclick="aggiungiNotaAggiudicazioneFirmata(${beautyContestView.beautyContestId})"
									class="btn btn-primary">
									<spring:message text="??beautyContest.label.ok??"
										code="beautyContest.label.ok" />
								</button>
								<button name="singlebutton" type="button" data-dismiss="modal"
									class="btn btn-warning">
									<spring:message text="??beautyContest.label.chiudi??"
										code="beautyContest.label.chiudi" />
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>
	
	<script src="<%=request.getContextPath()%>/portal/js/controller/beautyContest.js"></script>
	
	<!-- DARIO ****************************************************************************************** -->
    <script	src="<%=request.getContextPath()%>/portal/js/controller/lista_assegnatari.js"></script>
	<!-- ************************************************************************************************ -->
	
	<script type="text/javascript">
			var jsonDataMaterie = null;
	</script>

	<c:if test="${ not empty beautyContestView.jsonAlberaturaMaterie }">
		<script type="text/javascript">
			var jsonDataMaterie = ${beautyContestView.jsonAlberaturaMaterie }
			
			var materieDaSelezionare = new Array();
		</script>
	</c:if>
 

	<c:if test="${ not empty beautyContestView.materie }">
		<c:forEach items="${beautyContestView.materie}" var="materiaCodice">
			<script type="text/javascript">
				materieDaSelezionare.push("${materiaCodice}");
			</script>
		</c:forEach>
	</c:if>
	
	<script type="text/javascript">
	
		$(document).ready(function () {
			
			if ($('#treeContainerMaterie')
					&& jsonDataMaterie != null
					&& jsonDataMaterie != undefined) {
				$('#treeContainerMaterie').easytree({
					data : jsonDataMaterie,
					built : selezionaMaterie
				});
			}
		});
	
		/*
		// funzione per mostrare il modal racchiuso nel boxAllegati
		// Apre il boxAllegati e visualizza il modal per caricare un nuovo allegato
		 */
		function openModalAllegato() {
			$("#boxAllegati").collapse('show');
			$("#panelAggiungiAllegato").modal('show');
		}

		//DARIO ***************************************
		
		// $('#panelConfirmAvviaWorkFlowBeautyContest').on('show.bs.modal',function(e) {
		//	$(this).find("#btnRichiediAvvioWorkflowBeautyContest").attr("onclick","avviaWorkFlowBeautyContestDaForm("+ $(e.relatedTarget).data('idbc') +")");
		// }); 
		
		$('#panelConfirmAvviaWorkFlowBeautyContest').on(
			'show.bs.modal',
			function(e) {
				
				gestisci_tasto_confirm_workflow($(this), function(resp_code,flag_resp,code){
					
					avviaWorkFlowBeautyContestDaForm(code , resp_code);
					
				});
				
				
				
			});	
		
		
		//*********************************************
		
		
		<%if( request.getAttribute("anchorName") != null ) {%>
			goToAncora('<%=request.getAttribute("anchorName")%>');
		<%}%>
	</script>
	
	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>

</body>
</html>
