<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<%
//aggiunta token CSRF per il corretto reindirizzamento in caso di annullamento operazione MASSIMO CARUSO
String CSRFToken = request.getParameter("CSRFToken");
%>

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
						 
							<h2>
							<spring:message text="??atto.label.visualizza??" code="atto.label.visualizza" />
							</h2>
			 
					</div>
					<div class="card-body">
					
					<!-- Area Crea Atto-->
			
		<div class="table-responsive">

			<div class="col-md-12">
				<div class="col-md-12">
					<div class="col-md-12" style="margin-bottom: 20px;margin-top: 20px;border-bottom: 1px">
						<h4><spring:message text="??atto.label.visualizzaDati??" code="atto.label.visualizzaDati" /></h4> 
						Protocollo Num. <c:out value="${atto.numeroProtocollo}"></c:out>
					</div>
					<div class="col-md-12">
					<form:form name="attoView" method="post" modelAttribute="attoView" action="./modifica.action"  enctype="multipart/form-data"  class="form-horizontal" onsubmit="return validaForm(this)">
						        <engsecurity:token regenerate="false"/>
						          <c:if test="${ not empty listError }">
										<div class="alert alert-danger box-error">
										<c:forEach items="${listError}" var="value">
								         <c:out value="${value}"></c:out><br>
						   				</c:forEach>
										</div>
								</c:if>
						 	<div class="alert alert-danger box-error" style="display:none"></div>
							<fieldset>
							<input type="hidden" name="numeroProtocollo" id="numeroProtocollo" value='<c:out value="${atto.numeroProtocollo}"></c:out>' >
							<input type="hidden" id="idAtto" name="idAtto" value="<c:out value="${atto.id}"></c:out>">
							<input type="hidden" id="operazioneCorrente" name="operazioneCorrente" value="modifica" > 
										<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="idSocieta"><spring:message text="??atto.label.societa??" code="atto.label.societa" /></label>
									<div class="col-md-8">
									<select id="idSocieta" name="idSocieta" class="form-control isupdate">
											<option></option>
											<c:if test="${ listaSocieta != null }">
											<c:forEach items="${listaSocieta}" var="oggetto">
												<option value="${ oggetto.id }" <c:if test="${ oggetto.id eq atto.societa.id }">selected</c:if> >
													<c:out value="${oggetto.ragioneSociale}"></c:out>
												</option>
											</c:forEach>
											</c:if>
										</select>  
									</div>
								</div>
								<!-- Text input-->
									<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="parteNotificante"><spring:message text="??atto.label.parteNotificante??" code="atto.label.parteNotificante" /></label>
									<div class="col-md-8">
										<input id="parteNotificante" name="parteNotificante"
											type="text" class="typeahead form-control input-md isupdate" value="<c:out value="${atto.parteNotificante}"></c:out>" >  
									</div>
								</div>
								<!-- Text input-->
								
								<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="idCategoriaAtto"><spring:message text="??atto.label.categoria??" code="atto.label.categoria" /></label>
									<div class="col-md-8">
									<select id="idCategoriaAtto" name="idCategoriaAtto" class="form-control isupdate">
									<option></option>
												<c:if test="${ listaCategorie != null }">
											<c:forEach items="${listaCategorie}" var="oggetto">
												<option value="${ oggetto.id }" id="${oggetto.codGruppoLingua }" <c:if test="${ oggetto.id eq atto.categoriaAtto.id }">selected</c:if> >
													<c:out value="${oggetto.nome}"></c:out>
												</option>
											</c:forEach>
											</c:if>
										</select>
									</div>
								</div>
								<!-- Text input-->
								<!-- Text input-->
								
								<!-- ESITO-->
			<div class="form-group" id="formEsito" 
			<c:if test="${ atto.categoriaAtto.codGruppoLingua eq 'TPAT_3' }">
			style="display: block;"
			</c:if>
			<c:if test="${ atto.categoriaAtto.codGruppoLingua ne 'TPAT_3' }">
			style="display: none;"
			</c:if>
			>
				<label class="col-md-3 control-label"
					for="idEsito"><spring:message text="??atto.label.esito??" code="atto.label.esito" /></label>
				<div class="col-md-8">
				<select id="idEsito" name="idEsito" class="form-control isupdate">
				<option value="0" selected="selected"></option>
							<c:if test="${ listaEsitoAtto != null }">
						<c:forEach items="${listaEsitoAtto}" var="oggetto">
							<option value="${ oggetto.id }" id="${oggetto.codGruppoLingua }" <c:if test="${ oggetto.id eq atto.esitoAtto.id }">selected</c:if> >
								<c:out value="${oggetto.descrizione}"></c:out>
							</option>
						</c:forEach>
						</c:if>
					</select>
				</div>
			</div>

														<!--PAGAMENTO DOVUTO-->
														<div class="form-group" id="formPagamentoDovuto" 
														<c:if test="${ atto.categoriaAtto.codGruppoLingua eq 'TPAT_3' }">
														style="display: block;"
														</c:if>
														<c:if test="${ atto.categoriaAtto.codGruppoLingua ne 'TPAT_3' }">
														style="display: none;"
														</c:if>			
														>
															<label class="col-md-3 control-label"
																for="pagamentoDovuto"><spring:message
																	text="??atto.label.pagamentodovuto??"
																	code="atto.label.pagamentodovuto" /></label>
															<div class="col-md-8">
																<input type="number" id="pagamentoDovuto" min="0" name="pagamentoDovuto"
																	value="${atto.pagamentoDovuto }"
																	class="form-control isupdate" />
															</div>
														</div>
														<!-- Text input-->
														<!--SPESE DI LITE A CARICO-->
														<div class="form-group" id="speseCarico"
														<c:if test="${ atto.categoriaAtto.codGruppoLingua eq 'TPAT_3' }">
														style="display: block;"
														</c:if>
														<c:if test="${ atto.categoriaAtto.codGruppoLingua ne 'TPAT_3' }">
														style="display: none;"
														</c:if>	
														>
															<label class="col-md-3 control-label"
																for="speseCarico"><spring:message
																	text="??atto.label.speseCarico??"
																	code="atto.label.speseCarico" /></label>
															<div class="col-md-8">
																<input type="number" id="speseCarico" min="0" name="speseCarico"
																	value="${atto.speseCarico }"
																	class="form-control isupdate" />
															</div>
														</div>
														<!-- Text input-->
														<!--SPESE DI LITE A FAVORE-->
														<div class="form-group" id="speseFavore"
														<c:if test="${ atto.categoriaAtto.codGruppoLingua eq 'TPAT_3' }">
														style="display: block;"
														</c:if>
														<c:if test="${ atto.categoriaAtto.codGruppoLingua ne 'TPAT_3' }">
														style="display: none;"
														</c:if>			
														>
															<label class="col-md-3 control-label"
																for="speseFavore"><spring:message
																	text="??atto.label.speseFavore??"
																	code="atto.label.speseFavore" /></label>
															<div class="col-md-8">
																<input type="number" id="speseFavore" min="0" name="speseFavore"
																	value="${atto.speseFavore }"
																	class="form-control isupdate" />
															</div>
														</div>
														<!-- Text input-->
														<!-- Text input-->
																																									<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="destinatario"><spring:message text="??atto.label.destinatario??" code="atto.label.destinatario" /></label>
									<div class="col-md-8">
										<select id="destinatario" name="destinatario"
											class="form-control isupdate">
											<option></option>
											<c:if test="${ listaDestinatario != null }">
											<c:forEach items="${listaDestinatario}" var="oggetto">
												<option value="${ oggetto.matricolaUtil }"  <c:if test="${ oggetto.matricolaUtil eq atto.destinatario }">selected</c:if> >
													<c:out value="${oggetto.nominativoUtil}"></c:out>
												</option>
											</c:forEach>
											</c:if>
											
										</select>
									</div>
								</div>
						
								<!-- Text input-->
						
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="tipoAtto"><spring:message text="??atto.label.tipoAtto??" code="atto.label.tipoAtto" /></label>
									<div class="col-md-8">
										<input id="tipoAtto" name="tipoAtto"
											type="text" class="typeahead form-control input-md isupdate" value="<c:out value="${atto.tipoAtto}"></c:out>" >  
									</div>
								</div>
										<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="foroCompetente"><spring:message text="??atto.label.foroCompetente??" code="atto.label.foroCompetente" /></label>
									<div class="col-md-8">
										<input id="foroCompetente" name="foroCompetente"
											type="text" class="typeahead form-control input-md isupdate" value="<c:out value="${atto.foroCompetente}"></c:out>" >  
									</div>
								</div>
								<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="dataNotifica"><spring:message text="??atto.label.dataNotifica??" code="atto.label.dataNotifica" /></label>
									<div class="col-md-8">
										<input id="dataNotifica" name="dataNotifica"
											type="text" class="typeahead form-control input-md date-picker isupdate" value='<fmt:formatDate pattern="dd/MM/yyyy" value="${atto.dataNotifica}"/>' >  
									</div>
								</div>
								<!-- Text input-->		<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="note"><spring:message text="??atto.label.note??" code="atto.label.note" /></label>
									<div class="col-md-8">
										<input id="note" name="note"
											type="text" class="typeahead form-control input-md isupdate" value="<c:out value="${atto.note}"></c:out>" >  
									</div>
								</div>
								<!-- Text input-->
							<div class="form-group">
									<label class="col-md-3 control-label"
										for="creatoDa"><spring:message text="??atto.label.creatoDa??" code="atto.label.creatoDa" /></label>
									<div class="col-md-8">
										    <input id="creatoDa" name="creatoDa" type="hidden"  value="<c:out value="${atto.creatoDa}"></c:out>" >  
											<input type="text" class="typeahead form-control input-md" disabled="disabled" value="<c:out value="${creatoDaNome}"></c:out>" >  
									</div>
								</div>
								<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="dataCreazione"><spring:message text="??atto.label.dataCreazione??" code="atto.label.dataCreazione" /></label>
									<div class="col-md-8">
										<input type="text" class="typeahead form-control input-md" disabled="disabled" value='<fmt:formatDate pattern="dd/MM/yyyy" value="${atto.dataCreazione}"/>' > 
										<input id="dataCreazione" name="dataCreazione" type="hidden" class="typeahead form-control input-md" value='<fmt:formatDate pattern="dd/MM/yyyy" value="${atto.dataCreazione}"/>' >   
									</div>
								</div>
								<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="dataUltimaModifica"><spring:message text="??atto.label.dataUltimaModifica??" code="atto.label.dataUltimaModifica" /></label>
									<div class="col-md-8">
									<input type="text" class="typeahead form-control input-md" disabled="disabled" value='<fmt:formatDate pattern="dd/MM/yyyy" value="${atto.dataUltimaModifica}"/>' >  
									<input id="dataUltimaModifica" name="dataUltimaModifica" type="hidden" class="typeahead form-control input-md"  value='<fmt:formatDate pattern="dd/MM/yyyy" value="${atto.dataUltimaModifica}"/>' > 
									</div>
								</div>
								<!-- Text input-->	
									<div class="form-group">
									<label class="col-md-3 control-label"
										for="stato"><spring:message text="??atto.label.stato??" code="atto.label.stato" /></label>
									<div class="col-md-8">
										<input id="stato" name="stato"
											type="text" class="typeahead form-control input-md" disabled="disabled"  value="<c:out value="${atto.statoAtto.descrizione}"></c:out>" >  
									</div>
								</div>
								<!-- Text input-->	
									<div class="form-group">
									<label class="col-md-3 control-label"
										for="dataUdienza"><spring:message text="??atto.label.dataUdienza??" code="atto.label.dataUdienza" /></label>
									<div class="col-md-8">
										<input id="dataUdienza"  name="dataUdienza"
											type="text" class="typeahead form-control input-md date-picker isupdate" value='<fmt:formatDate pattern="dd/MM/yyyy" value="${atto.dataUdienza}"/>' >  
									</div>
								</div>
								<!-- Text input-->	
									<div class="form-group">
									<label class="col-md-3 control-label"
										for="rilevante"><spring:message text="??atto.label.rilevante??" code="atto.label.rilevante" /></label>
									<div class="col-md-8">
											<select id="rilevante" name="rilevante"
											class="form-control isupdate">
											<option></option>
											<option value="T" <c:if test="${atto.rilevante eq 'T' }">selected</c:if> >SI</option>
											<option value="F" <c:if test="${atto.rilevante eq 'F' }">selected</c:if> >NO</option>
										</select> 
									</div>
								</div>
								<!-- Text input-->	
								<div class="form-group">
								<label class="col-md-3 control-label"
									for="rilevante"><spring:message text="??atto.button.modificaAllegato??" code="atto.button.modificaAllegato" /></label>
								<div class="form-group"> 
								<div class="col-md-8">
									<input type="file" name="file" id="file" class="isupdate">
								</div>
								</div>
							
							    </div>
								<c:choose>
									<c:when test="${azione eq 'visualizza' }">
										<!-- Text input-->
										<div class="form-group ">
											<label class="col-md-3 control-label"
												for="utenteInvioAltriUffici"><spring:message
													text="??atto.label.utenteInvioAltriUffici??"
													code="atto.label.utenteInvioAltriUffici" /></label>
											<div class="col-md-8">
												<input id="utenteInvioAltriUffici"
													name="utenteInvioAltriUffici" type="text"
													class="typeahead form-control input-md isupdate"
													value="<c:out value="${atto.utenteInvioAltriUffici}"></c:out>">
											</div>
										</div>
										<!-- Text input-->
										<div class="form-group ">
											<label class="col-md-3 control-label"
												for="emailInvioAltriUffici"><spring:message
													text="??atto.label.emailInvioAltriUffici??"
													code="atto.label.emailInvioAltriUffici" /></label>
											<div class="col-md-8">
												<input id="emailInvioAltriUffici"
													name="emailInvioAltriUffici" type="text"
													class="typeahead form-control input-md isupdate"
													value="<c:out value="${atto.emailInvioAltriUffici}"></c:out>">
											</div>
										</div>
										<!-- Text input-->
										<div class="form-group ">
											<label class="col-md-3 control-label"
												for="unitaLegInvioAltriUffici"><spring:message
													text="??atto.label.unitaLegInvioAltriUffici??"
													code="atto.label.unitaLegInvioAltriUffici" /></label>
											<div class="col-md-8">
												<input id="unitaLegInvioAltriUffici"
													name="unitaLegInvioAltriUffici" type="text"
													class="typeahead form-control input-md isupdate"
													value="<c:out value="${atto.unitaLegInvioAltriUffici}"></c:out>">
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<!-- Text input-->
										<div class="form-group isAltri">
											<label class="col-md-3 control-label"
												for="utenteInvioAltriUffici"><spring:message
													text="??atto.label.utenteInvioAltriUffici??"
													code="atto.label.utenteInvioAltriUffici" /></label>
											<div class="col-md-8">
												<input id="utenteInvioAltriUffici"
													name="utenteInvioAltriUffici" type="text"
													class="typeahead form-control input-md isAltri"
													value="<c:out value="${atto.utenteInvioAltriUffici}"></c:out>">
											</div>
										</div>
										<!-- Text input-->
										<div class="form-group isAltri">
											<label class="col-md-3 control-label"
												for="emailInvioAltriUffici"><spring:message
													text="??atto.label.emailInvioAltriUffici??"
													code="atto.label.emailInvioAltriUffici" /></label>
											<div class="col-md-8">
												<input id="emailInvioAltriUffici"
													name="emailInvioAltriUffici" type="text"
													class="typeahead form-control input-md isAltri"
													value="<c:out value="${atto.emailInvioAltriUffici}"></c:out>">
											</div>
										</div>
										<!-- Text input-->
										<div class="form-group isAltri">
											<label class="col-md-3 control-label"
												for="unitaLegInvioAltriUffici"><spring:message
													text="??atto.label.unitaLegInvioAltriUffici??"
													code="atto.label.unitaLegInvioAltriUffici" /></label>
											<div class="col-md-8">
												<input id="unitaLegInvioAltriUffici"
													name="unitaLegInvioAltriUffici" type="text"
													class="typeahead form-control input-md isAltri"
													value="<c:out value="${atto.unitaLegInvioAltriUffici}"></c:out>">
											</div>
										</div>
									</c:otherwise>
								</c:choose>

								<!-- Text input-->	
								<c:if test="${ atto.documento != null && not empty atto.documento.uuid}">												
								<div class="form-group">
									<label class="col-md-3 control-label">
										  </label>
										  <div class="col-md-8">
										<button style="float:left" type="button" data-toggle="dropdown" id="download-atto" uuid="<c:out value="${atto.documento.uuid}"></c:out>"
											class="btn btn-success dropdown-toggle">
								<spring:message text="??atto.button.scaricaAllegato??" code="atto.button.scaricaAllegato" /> <i class="fa fa-arrow-circle-down"></i>
										</button>
										 <div style="float:left;margin-left:10px;">
										<c:out value="${atto.documento.nomeFile}"></c:out>
										 </div>
										 </div>
								</div>
								</c:if>
								
							</fieldset>

								<div class="modal-footer">
								<div class="col-md-12 column">
				     <div class="btn-group dropup pull-right ">
					<div class="btn-group pull-right space-to-left">
					<c:if test="${azione eq 'modifica' ||azione eq 'inviauffici' }">	
						<button id="save-atto" type="submit"
							class="btn btn-primary dropdown-toggle"
							style="margin-left: 5px">
							<spring:message text="??atto.button.save??" code="atto.button.save" /><i class="fa fa-save"></i>
						</button>
					</c:if>
					</div>
					<!-- pulsante esporta senza opzioni -->
					<div class="btn-group pull-right">
					<!--  aggiunta pulsante di richiesta registrazione atto validato MASSIMO CARUSO -->
					<c:if test="${azione eq 'nuovo' || (azione eq 'visualizza' && (atto.statoAtto.codGruppoLingua eq 'B' || (atto.statoAtto.codGruppoLingua eq 'VAL' && atto.destinatario ne '0000000000')))}">	
					<button type="button" id="btn-richiedi-registrazione-atto" isatto="<c:out value="${atto.id}"></c:out>"
						class="btn btn-warning" style="margin-left: 5px">
						<spring:message text="??atto.label.menuRichiediRegAtto??" code="atto.label.menuRichiediRegAtto" /> <i class="fa"></i>
					</button>
					 </c:if>
					<c:if test="${azione eq 'modifica' }">	
					<button type="button" id="btn-modifica"
						class="btn btn-warning" style="margin-left: 5px">
						<spring:message text="??atto.button.modifica??" code="atto.button.modifica" /> <i class="fa"></i>
					</button>
					 </c:if>
					<c:if test="${azione eq 'inviauffici' }">		
						<button type="button" id="invia-altri"
							class="btn btn-success" style="margin-left: 5px">
							<spring:message text="??atto.button.inviaaltri??" code="atto.button.inviaaltri" /> <i class="fa"></i>
						</button>
					 </c:if>
					</div>
						<div class="btn-group pull-right">
						<!--  Aggiunta del Token CSRF nel link in caso di annullamento MASSIMO CARUSO -->
						<c:if test="${atto.statoAtto.codGruppoLingua ne 'VAL'}">
						<button type="button" onclick="javascript:location.href='./ricerca.action?CSRFToken=${CSRFToken}';"
							class="btn btn-danger">
							<spring:message text="??atto.button.annulla??" code="atto.button.annulla" />   <i class="fa"></i>
						</button>
						</c:if>
						<c:if test="${atto.statoAtto.codGruppoLingua eq 'VAL'}">
						<button type="button" onclick="javascript:location.href='./valida.action?CSRFToken=${CSRFToken}';"
							class="btn btn-danger">
							<spring:message text="??atto.button.annulla??" code="atto.button.annulla" />   <i class="fa"></i>
						</button>
						</c:if>
					 <button type="button" onclick="stampaAtto('<c:out value="${atto.id}"></c:out>')"
							class="btn btn-default">
							<i class="fa fa-print" aria-hidden="true"></i><spring:message text="??atto.button.stampa??" code="atto.button.stampa" />
						</button>
					</div>
				</div>
			</div>
				</div>
				
			</form:form>						
						 
					</div>

	
		</div>
			</div>
			 	</div>

		 

			 
			</div>
		</div><!--/ fine card -->
		</div>
	</div>
</div>
<!--/ fine col-1 -->
	</section>
<form id="downloadAtto" action="./download.action" method="get" style="display:none">	
<engsecurity:token regenerate="false"/>
<input type="hidden" name="uuid" id="uuid" value="">
<input type="submit" value="" style="display:none">
</form>
</section>


<footer>
	<jsp:include page="/parts/footer.jsp"></jsp:include>
</footer>
<jsp:include page="/parts/script-end.jsp"></jsp:include>


<!-- MODAL Dettaglio Stampa -->
<div class="modal fade" id="modalDettaglioStampa" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content" style="height:570px"> 
			<div class="modal-header">
				<h4 class="modal-title"> </h4>
			</div>
			<div class="modal-body" style="overflow-x: auto;height:90%">
			<iframe id="bodyDettaglioStampa" src="" style="width:100%;height:100%;border:0px"></iframe>

			</div>
			 
		</div>
	</div>
</div>
<!-- FINE MODAL Dettaglio Stampa -->
<script>
function stampaAtto(id){
	var url="<%=request.getContextPath()%>/atto/stampa.action?id="+id+"&azione=visualizza";
	url=legalSecurity.verifyToken(url);
	waitingDialog.show('Loading...');
	var ifr=$("#bodyDettaglioStampa").attr("src",url);
	ifr.load(function(){
	  	 waitingDialog.hide();
		$("#modalDettaglioStampa").modal("show");
})
}
</script>



<script src="<%=request.getContextPath()%>/portal/js/controller/fascicolo.js"></script>
<script src="<%=request.getContextPath()%>/portal/js/controller/atto.js?_=a1"></script>




</body>
</html>
