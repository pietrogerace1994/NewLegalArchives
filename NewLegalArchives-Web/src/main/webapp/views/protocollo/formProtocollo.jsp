<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<%
UtenteView utenteConnesso = (UtenteView ) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
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
							<div class="card-header">
								<h1>
									<spring:message text="??protocollo.label.pagProtocollo??"
										code="protocollo.label.pagProtocollo" />
								</h1> 
							</div>
							<div class="card-body">
								<div id="sezioneMessaggiApplicativi"></div>
								<div class="table-responsive">
									<ul class="tab-nav tn-justified tn-icon" role="tablist">
										<li role="presentation" class='active' ><a
											class="col-sx-4" href="#tab-1" aria-controls="tab-1"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-star icon-tab" aria-hidden="true"></i>-->
												<small>IN</small>
										</a></li>
										<li role="presentation" class='' ><a
											class="col-xs-4" href="#tab-2" aria-controls="tab-2"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-search icon-tab" aria-hidden="true"></i>-->
												<small>OUT</small>
										</a></li>
										<!-- Aggiunta voce Atti da assegnare per Top Responsabile MASSIMO CARUSO -->
										<% if(utenteConnesso.isTopResponsabile()){ %>
										<li role="presentation" class='' ><a
											class="col-xs-4" href="#tab-3" aria-controls="tab-3"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-search icon-tab" aria-hidden="true"></i>-->
												<small>ATTI</small>
										</a></li>
										<% } %>
										<!-- FINE Aggiunta voce Atti da assegnare per Top Responsabile MASSIMO CARUSO -->
									</ul>

									<div class="tab-content p-20">
										<div role="tabpanel"
											class="tab-pane animated fadeIn in active"
											id="tab-1">
<% if (utenteConnesso.isOperatoreSegreteria()){ %>
											<h2>
												<spring:message text="??protocollo.label.generaIN??"
													code="protocollo.label.generaIN" />
											</h2>

											<form:form name="protocolloForm"
												style="padding-bottom: 0px;"
												modelAttribute="protocolloView"
												class="form-horizontal la-form">
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

												<div class="list-group lg-alt">
													<!--MITTENTE-->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="mittente" class="col-sm-2 control-label"><spring:message
																		text="??protocollo.label.mittente??"
																		code="protocollo.label.mittente" /></label>
																<div class="col-sm-10">
																	<form:input path="mittente" cssClass="form-control" onfocus="$(this).css('background','azure');" />
																</div>
															</div>
														</div>
													</div>
												</div>

												<!-- DESTINATARIO -->
												<div class="list-group lg-alt">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="listDest" class="col-sm-2 control-label"><spring:message
																		text="??protocollo.label.destinatario??"
																		code="protocollo.label.destinatario" /></label>
																<div class="col-sm-10">
																	<form:select size="5" path="destINid" onfocus="$(this).css('background','azure');"
																		cssClass="form-control">
																		<c:if test="${ protocolloView.utentiIN != null }">
																			<c:forEach items="${protocolloView.utentiIN}"
																				var="oggetto">
																				<form:option value="${ oggetto.matricolaUtil }" 
																					id="${fn:substring(oggetto.codiceUnitaUtil,5, fn:length(oggetto.codiceUnitaUtil)) }">
																					<c:out value="${oggetto.nominativoUtil}"></c:out>
																				</form:option>
																			</c:forEach>
																		</c:if>
																	</form:select>
																</div>
															</div>
														</div>
													</div>
												</div>

												<div class="list-group lg-alt">
													<!--UNITA-->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="mittente" class="col-sm-2 control-label"><spring:message
																		text="??protocollo.label.unitaap??"
																		code="protocollo.label.unitaap" /></label>
																<div class="col-sm-10">
																	<form:input path="unitaApp" cssClass="form-control"
																		disabled="true" />
																</div>
															</div>
														</div>
													</div>
												</div>

												<div class="list-group lg-alt">
													<!--OGGETTO-->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="oggetto" class="col-sm-2 control-label"><spring:message
																		text="??protocollo.label.oggetto??"
																		code="protocollo.label.oggetto" /></label>
																<div class="col-sm-10">
																	<form:input path="oggetto" cssClass="form-control" onfocus="$(this).css('background','azure');" />
																</div>
															</div>
														</div>
													</div>
												</div>

												<div class="list-group lg-alt">
													<!--GENERA-->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="protocollo" class="col-sm-2 control-label"><spring:message
																		text="??protocollo.label.numeroprotocollo??"
																		code="protocollo.label.numeroprotocollo" /></label>
																<div class="col-md-2">
																	<input style="font-size: x-large; font-weight: bold;"
																		id="numeroProtocollo" name="numeroProtocollo"
																		class="form-control" type="text" value="" readonly>
																</div>
																<div class="col-md-2">
																	<i class="fa fa-spinner fa-pulse fa-fw" id="load" style="visibility: collapse;"></i>
																	<button type="button"
																		onclick="generaProtocollo('IN'); "
																		class="btn palette-LightBlue-SNAM bg">
																		<spring:message
																			text="??protocollo.label.generaProtocollo??"
																			code="protocollo.label.generaProtocollo" />
																	</button>
																</div>
															</div>
														</div>
													</div>
												</div>

											</form:form>
		<%} %>

											<p
													class="visible-lg visible-md visible-xs visible-sm text-left text-left">
													<spring:message
														text="??fascicolo.label.nonHaiTrovatoCercavi??"
														code="fascicolo.label.nonHaiTrovatoCercavi" />
													<a data-toggle="modal" href="#panelRicerca" class=""> <spring:message
															text="??fascicolo.label.affinaRicerca??"
															code="fascicolo.label.affinaRicerca" />
													</a>
												</p>

												<table id="tabellaRicercaProtocolloIN"
													class="table table-striped table-responsive">

												</table>
										</div> 
										<div role="tabpanel"
											class="tab-pane animated fadeIn in "
											id="tab-2">  
	<% if (utenteConnesso.isOperatoreSegreteria()){ %>										
											
											<h2>
												<spring:message text="??protocollo.label.generaOUT??"
													code="protocollo.label.generaOUT" />
											</h2>

											<form:form name="protocolloForm"
												style="padding-bottom: 0px;"
												modelAttribute="protocolloView"
												class="form-horizontal la-form">
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

												<div class="list-group lg-alt">
													<!--MITTENTE-->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="listDest" class="col-sm-2 control-label"><spring:message
																		text="??protocollo.label.mittente??"
																		code="protocollo.label.mittente" /></label>
																<div class="col-sm-10">
																	<form:select size="5" path="mittOUTid" onfocus="$(this).css('background','azure');"
																		cssClass="form-control">
																		<c:if test="${ protocolloView.utentiOUT != null }">
																			<c:forEach items="${protocolloView.utentiOUT}"
																				var="oggetto">
																				<form:option value="${ oggetto.matricolaUtil }" 
																					id="${fn:substring(oggetto.codiceUnitaUtil,5, fn:length(oggetto.codiceUnitaUtil)) }">
																					<c:out value="${oggetto.nominativoUtil}"></c:out>
																				</form:option>
																			</c:forEach>
																		</c:if>
																	</form:select>
																</div>
															</div>
														</div>
													</div>
												</div>

												<!-- DESTINATARIO -->
												<div class="list-group lg-alt">
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="mittente" class="col-sm-2 control-label"><spring:message
																		text="??protocollo.label.destinatario??"
																		code="protocollo.label.destinatario" /></label>
																<div class="col-sm-10">
																	<form:input path="destinatarioOut" cssClass="form-control" onfocus="$(this).css('background','azure');" />
																</div>
															</div>
														</div>
													</div>
												</div>

												<div class="list-group lg-alt">
													<!--UNITA-->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="mittente" class="col-sm-2 control-label"><spring:message
																		text="??protocollo.label.unitaap??"
																		code="protocollo.label.unitaap" /></label>
																<div class="col-sm-10">
																	<form:input path="unitaAppOut" cssClass="form-control"
																		disabled="true" />
																</div>
															</div>
														</div>
													</div>
												</div>

												<div class="list-group lg-alt">
													<!--OGGETTO-->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="oggetto" class="col-sm-2 control-label"><spring:message
																		text="??protocollo.label.oggetto??"
																		code="protocollo.label.oggetto" /></label>
																<div class="col-sm-10">
																	<form:input path="oggettoOut" cssClass="form-control" onfocus="$(this).css('background','azure');" />
																</div>
															</div>
														</div>
													</div>
												</div>

												<div class="list-group lg-alt">
													<!--GENERA-->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="protocollo" class="col-sm-2 control-label"><spring:message
																		text="??protocollo.label.numeroprotocollo??"
																		code="protocollo.label.numeroprotocollo" /></label>
																<div class="col-md-2">
																	<input style="font-size: x-large; font-weight: bold;"
																		id="numeroProtocolloOut" name="numeroProtocolloOut"
																		class="form-control" type="text" value="" readonly>
																</div>
																<div class="col-md-2">
																	<i class="fa fa-spinner fa-pulse fa-fw" id="loadOut" style="visibility: collapse;"></i>
																	<button type="button"
																		onclick="generaProtocollo('OUT'); "
																		class="btn palette-LightBlue-SNAM bg">
																		<spring:message
																			text="??protocollo.label.generaProtocollo??"
																			code="protocollo.label.generaProtocollo" />
																	</button>
																</div>
															</div>
														</div>
													</div>
												</div>

											</form:form>
		<%} %>

											<p
													class="visible-lg visible-md visible-xs visible-sm text-left text-left">
													<spring:message
														text="??fascicolo.label.nonHaiTrovatoCercavi??"
														code="fascicolo.label.nonHaiTrovatoCercavi" />
													<a data-toggle="modal" href="#panelRicercaOut" class=""> <spring:message
															text="??fascicolo.label.affinaRicerca??"
															code="fascicolo.label.affinaRicerca" />
													</a>
												</p>

												<table id="tabellaRicercaProtocolloOUT"
													class="table table-striped table-responsive">

												</table>
											</div>	
												
											<!-- Aggiunta tabella Atti Portocollati da assegnare MASSIMO CARUSO -->
											<% if (utenteConnesso.isTopResponsabile()){ %>
												<div role="tabpanel" class="tab-pane animated fadeIn in" id="tab-3">
													<p class="visible-lg visible-md visible-xs visible-sm text-left text-left">
														<spring:message text="??fascicolo.label.nonHaiTrovatoCercavi??" code="fascicolo.label.nonHaiTrovatoCercavi" />
														<a data-toggle="modal" href="#panelRicercaAttiProtocollati" class=""> 
															<spring:message text="??fascicolo.label.affinaRicerca??" code="fascicolo.label.affinaRicerca" />
														</a>
													</p>
													<table id="tabellaRicercaAttiProtocollati" class="table table-striped table-responsive"></table>
												</div> 
											<%} %>
											<!-- FINE Aggiunta tabella Atti Portocollati da assegnare MASSIMO CARUSO -->
												
										
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>
	
	
	<!-- PANEL ASSOCIA A FASCICOLO-->
<div class="modal fade" id="protocolloPanelCercaSelezionaPadreFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??protocollo.label.spostasu??"
							code="protocollo.label.spostasu" />
						<p id="modalNumASSO"></p>
						<input type="hidden" id="modalidProtocollo" value="">
					</h4>
				</div>
				<div class="modal-body">
					<div id="containerRicercaModaleFascicoloPadre" class="col-md-16"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL ASSEGNA-->
	<div class="modal fade" id="panelAssegna" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content" style="width: 50%; margin-left: 24%;">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??protocollo.label.assegnaper??"
							code="protocollo.label.assegnaper" />
					</h4>
					<p id="modalNumA"></p>
				</div>
				<div class="modal-body">
					<form:form name="protocolloFormAssegna"
												style="padding-bottom: 0px;"
												modelAttribute="protocolloView"
												class="form-horizontal la-form" id="assegnaForm">
						<fieldset>
									<!--ASSEGNA A-->
							<div class="form-group" style="margin-left: 0; margin-right: 0;">
								<input type="hidden" id="idProtA" value="">
								<form:select size="5" path="utenteAss" id="utenteAss"
									cssClass="form-control">
									<c:if test="${ protocolloView.utentiOUT != null }">
										<c:forEach items="${protocolloView.utentiOUT}" var="oggetto">
											<form:option value="${oggetto.matricolaUtil}"
												id="${fn:substring(oggetto.codiceUnitaUtil,5, fn:length(oggetto.codiceUnitaUtil)) }">
												<c:out value="${oggetto.nominativoUtil}"></c:out>
											</form:option>
										</c:forEach>
									</c:if>
								</form:select>
								<br>
								<spring:message text="??protocollo.label.commento??"
									code="protocollo.label.commento" />
								<form:textarea path="commento" cssClass="form-control"
									id="commento" />
								<!-- Aggiunta tipo protocollo ed id documento per gestione atti da assegnare MASSIMO CARUSO -->
								<input type="hidden" id="tipoProtocollo" value="">
								<input type="hidden" id="idDocumentoAtto" value="">
								<!-- FINE Aggiunta tipo protocollo ed id documento per gestione atti da assegnare MASSIMO CARUSO -->
							</div>


							<br>
							<!-- Button -->
							<div class="form-group">
								<div class="row">
									<div class="col-sm-5">
										<button type="submit" value="" class="btn btn-primary" id="assegnaGo">
											<spring:message text="??protocollo.label.assegna??"
												code="protocollo.label.assegna" />
										</button>
										<button name="singlebutton" type="button" data-dismiss="modal"
										class="btn btn-warning">
										<spring:message text="??fascicolo.label.chiudi??"
											code="fascicolo.label.chiudi" />
									</button>
									</div>
									<div class="col-sm-2">
										<i class="fa fa-spinner fa-pulse fa-2x" aria-hidden="true" id="progress2"
											style="display: none;"></i> 
										<i class="fa fa-check fa-2x"
											aria-hidden="true" id="done2"
											style="display: none; color: green;"></i>
									</div>
									<div class="col-sm-5">
									<p id="incorso2" style="display: none;"><spring:message text="??protocollo.label.incorso??"
												code="protocollo.label.incorso" /></p>
									<p id="completato2" style="display: none;"><spring:message text="??protocollo.label.completato??"
												code="protocollo.label.completato"/></p>
									</div>
			
								</div>
							</div>


						</fieldset>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	
		<!-- PANEL RIASSEGNA-->
	<div class="modal fade" id="panelRiassegna" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content" style="width: 50%; margin-left: 24%;">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??protocollo.label.assegnaper??"
							code="protocollo.label.assegnaper" />
					</h4>
					<p id="modalNumA"></p>
				</div>
				<div class="modal-body">
					<form:form name="protocolloFormRiassegna"
												style="padding-bottom: 0px;"
												modelAttribute="protocolloView"
												class="form-horizontal la-form" id="riassegnaForm">
						<fieldset>
									<!--ASSEGNA A-->
							<div class="form-group" style="margin-left: 0; margin-right: 0;">
								<input type="hidden" id="idProtARi" value="">
								<select size="5" id="utenteRiass"
									class="form-control">
								</select>
								<br>
								<spring:message text="??protocollo.label.commento??"
									code="protocollo.label.commento" />
								<form:textarea path="commentoRi" cssClass="form-control"
									id="commentoRi" />

							</div>


							<br>
							<!-- Button -->
							<div class="form-group">
								<div class="row">
									<div class="col-sm-5">
										<button type="submit" value="" class="btn btn-primary" id="assegnaGo">
											<spring:message text="??protocollo.label.assegna??"
												code="protocollo.label.assegna" />
										</button>
										<button name="singlebutton" type="button" data-dismiss="modal"
										class="btn btn-warning">
										<spring:message text="??fascicolo.label.chiudi??"
											code="fascicolo.label.chiudi" />
									</button>
									</div>
									<div class="col-sm-2">
										<i class="fa fa-spinner fa-pulse fa-2x" aria-hidden="true" id="progress3"
											style="display: none;"></i> 
										<i class="fa fa-check fa-2x"
											aria-hidden="true" id="done3"
											style="display: none; color: green;"></i>
									</div>
									<div class="col-sm-5">
									<p id="incorso3" style="display: none;"><spring:message text="??protocollo.label.incorso??"
												code="protocollo.label.incorso" /></p>
									<p id="completato3" style="display: none;"><spring:message text="??protocollo.label.completato??"
												code="protocollo.label.completato"/></p>
									</div>
			
								</div>
							</div>


						</fieldset>
					</form:form>
				</div>
			</div>
		</div>
	</div>

	<!-- PANEL UPLOAD-->
	<div class="modal fade" id="panelUpload" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content" style="width: 50%; margin-left: 24%;">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??protocollo.label.caricaper??"
							code="protocollo.label.caricaper" />
					</h4>
					<p id="modalNum"></p>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" enctype="multipart/form-data" method="post" id="uploadForm">
						<fieldset>
							<div class="input-group">
								<input type="hidden" id="idProt" value="">
								<label class="input-group-btn"> <span
									class="btn btn-primary"> Scegli file: <input type="file"
										style="display: none;" name="file" id="file">
								</span>
								</label> <input type="text" id="nomefile" class="form-control" readonly>


							</div>
							<br>
							<!-- Button -->
							<div class="form-group">
								<div class="row">
									<div class="col-sm-5">
										<button type="submit" value="" class="btn btn-primary" id="sendfile" disabled>
											<spring:message text="??protocollo.label.carica??"
												code="protocollo.label.carica" />
										</button>
										<button name="singlebutton" type="button" data-dismiss="modal"
										class="btn btn-warning">
										<spring:message text="??fascicolo.label.chiudi??"
											code="fascicolo.label.chiudi" />
									</button>
									</div>
									<div class="col-sm-2">
										<i class="fa fa-spinner fa-pulse fa-2x" aria-hidden="true" id="progress"
											style="display: none;"></i> 
										<i class="fa fa-check fa-2x"
											aria-hidden="true" id="done"
											style="display: none; color: green;"></i>
									</div>
									<div class="col-sm-5">
									<p id="incorso" style="display: none;"><spring:message text="??protocollo.label.incorso??"
												code="protocollo.label.incorso" /></p>
									<p id="completato" style="display: none;"><spring:message text="??protocollo.label.completato??"
												code="protocollo.label.completato"/></p>
									</div>
								</div>
							</div>


						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>


	<!-- PANEL RICERCA MODALE IN-->
	<div class="modal fade" id="panelRicerca" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.miglioraRicerca??"
							code="fascicolo.label.miglioraRicerca" />
					</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<fieldset>
							<div class="form-group">
								<label class="col-md-4 control-label" for="nomeIncarico"><spring:message
										text="??protocollo.label.numeroprotocollo??"
										code="protocollo.label.numeroprotocollo" /></label>
								<div class="col-md-4">
									<input id="txtNumProtocollo" type='text' class="form-control"
										placeholder="">
								</div>
							</div>

							<!-- DAL...AL -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic"><spring:message
										text="??fascicolo.label.dataDal??"
										code="fascicolo.label.dataDal" /></label>
								<div class="col-md-4">
									<input id="txtDataDal" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic"><spring:message
										text="??fascicolo.label.dataAl??"
										code="fascicolo.label.dataAl" /></label>
								<div class="col-md-4">
									<input id="txtDataAl" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="statoProtocollo"><spring:message
										text="??protocollo.label.statoProtocollo??"
										code="protocollo.label.statoProtocollo" /></label>
								<div class="col-md-4">
									<select id="statoProtocolloCode" class="form-control">
										<option value="">
											<spring:message
												text="??incarico.label.selezionaStatoIncarico??"
												code="incarico.label.selezionaStatoIncarico" />
										</option>
										<c:if
											test="${ protocolloView.listaStatoProtocollo != null }">
											<c:forEach items="${protocolloView.listaStatoProtocollo}"
												var="oggetto">
												<option value="${ oggetto.vo.codGruppoLingua }" 
												<c:if test="${protocolloView.filtro eq oggetto.vo.codGruppoLingua}">selected</c:if>
												>
													<c:out value="${oggetto.vo.descrizione}"></c:out>
												</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>
							<!-- Dal...AL -->
							
							<div class="form-group">
								<label class="col-md-4 control-label" for="nomeFascicolo"><spring:message
										text="??incarico.label.nomeFascicolo??"
										code="incarico.label.nomeFascicolo" /></label>
								<div class="col-md-4">
									<input id="txtNomeFascicolo" type='text' class="form-control"
										placeholder="">
								</div>
							</div>
							
							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltri" name="singlebutton" type="button"
										onclick="cercaIncarico()" data-dismiss="modal"
										class="btn btn-primary">
										<spring:message text="??fascicolo.label.eseguiRicerca??"
											code="fascicolo.label.eseguiRicerca" />
									</button>
									<button name="singlebutton" type="button" data-dismiss="modal"
										class="btn btn-warning">
										<spring:message text="??fascicolo.label.chiudi??"
											code="fascicolo.label.chiudi" />
									</button>
								</div>
							</div>
							
							
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- FINE PANEL RICERCA MODALE IN-->
  

	<!-- PANEL RICERCA MODALE OUT -->
		<div class="modal fade" id="panelRicercaOut" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.miglioraRicerca??"
							code="fascicolo.label.miglioraRicerca" />
					</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<fieldset>
							<div class="form-group">
								<label class="col-md-4 control-label" for="nomeIncarico"><spring:message
										text="??protocollo.label.numeroprotocollo??"
										code="protocollo.label.numeroprotocollo" /></label>
								<div class="col-md-4">
									<input id="txtNumProtocolloOut" type='text' class="form-control"
										placeholder="">
								</div>
							</div>

							<!-- DAL...AL -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic"><spring:message
										text="??fascicolo.label.dataDal??"
										code="fascicolo.label.dataDal" /></label>
								<div class="col-md-4">
									<input id="txtDataDalOut" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic"><spring:message
										text="??fascicolo.label.dataAl??"
										code="fascicolo.label.dataAl" /></label>
								<div class="col-md-4">
									<input id="txtDataAlOut" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="statoProtocollo"><spring:message
										text="??protocollo.label.statoProtocollo??"
										code="protocollo.label.statoProtocollo" /></label>
								<div class="col-md-4">
									<select id="statoProtocolloCodeOUT" class="form-control">
										<option value="${protocolloView.filtro}">
											<spring:message
												text="??incarico.label.selezionaStatoIncarico??"
												code="incarico.label.selezionaStatoIncarico" />
										</option>
										<c:if test="${ protocolloView.listaStatoProtocollo != null }">
											<c:forEach items="${protocolloView.listaStatoProtocollo}"
												var="oggetto">
												<option value="${ oggetto.vo.codGruppoLingua }"
												<c:if test="${protocolloView.filtro eq oggetto.vo.codGruppoLingua}">selected</c:if>
												>
													<c:out value="${oggetto.vo.descrizione}"></c:out>
												</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>
							<!-- Dal...AL -->
							
							<div class="form-group">
								<label class="col-md-4 control-label" for="nomeFascicolo"><spring:message
										text="??incarico.label.nomeFascicolo??"
										code="incarico.label.nomeFascicolo" /></label>
								<div class="col-md-4">
									<input id="txtNomeFascicoloOut" type='text' class="form-control"
										placeholder="">
								</div>
							</div>
							
							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltri" name="singlebutton" type="button"
										onclick="cercaIncarico()" data-dismiss="modal"
										class="btn btn-primary">
										<spring:message text="??fascicolo.label.eseguiRicerca??"
											code="fascicolo.label.eseguiRicerca" />
									</button>
									<button name="singlebutton" type="button" data-dismiss="modal"
										class="btn btn-warning">
										<spring:message text="??fascicolo.label.chiudi??"
											code="fascicolo.label.chiudi" />
									</button>
								</div>
							</div>
							
							
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- FINE PANEL RICERCA MODALE OUT -->
	
	<!-- Aggiunta pannello ricerca atti protocollati da assegnare MASSIMO CARUSO -->
	<!-- PANEL RICERCA MODALE ATTI -->
	<div class="modal fade" id="panelRicercaAttiProtocollati" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.miglioraRicerca??" code="fascicolo.label.miglioraRicerca" />
					</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<fieldset>
							<div class="form-group">
								<label class="col-md-4 control-label" for="nomeIncarico">
									<spring:message text="??protocollo.label.numeroprotocollo??" code="protocollo.label.numeroprotocollo" />
								</label>
								<div class="col-md-4">
									<input id="txtNumProtocolloATTI" type='text' class="form-control" placeholder="">
								</div>
							</div>

							<!-- DAL...AL -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic">
									<spring:message text="??fascicolo.label.dataDal??" code="fascicolo.label.dataDal" />
								</label>
								<div class="col-md-4">
									<input id="txtDataDalATTI" type='text' class="form-control date-picker" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic">
									<spring:message text="??fascicolo.label.dataAl??" code="fascicolo.label.dataAl" />
								</label>
								<div class="col-md-4">
									<input id="txtDataAlATTI" type='text' class="form-control date-picker" placeholder="">
								</div>
							</div>
						
							<!-- Dal...AL -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="nomeFascicolo">
									<spring:message text="??incarico.label.nomeFascicolo??" code="incarico.label.nomeFascicolo" />
								</label>
								<div class="col-md-4">
									<input id="txtNomeFascicoloATTI" type='text' class="form-control" placeholder="">
								</div>
							</div>
							
							<!-- Stato protocollo -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="statoProtocollo"><spring:message
										text="??protocollo.label.statoProtocollo??"
										code="protocollo.label.statoProtocollo" /></label>
								<div class="col-md-4">
									<select id="statoProtocolloCodeATTI" class="form-control">
										<option value="${protocolloView.filtro}">
											<spring:message
												text="??incarico.label.selezionaStatoIncarico??"
												code="incarico.label.selezionaStatoIncarico" />
										</option>
										<c:if test="${ protocolloView.listaStatoProtocollo != null }">
											<c:forEach items="${protocolloView.listaStatoProtocollo}"
												var="oggetto">
												<option value="${ oggetto.vo.codGruppoLingua }"
												<c:if test="${protocolloView.filtro eq oggetto.vo.codGruppoLingua}">selected</c:if>
												>
													<c:out value="${oggetto.vo.descrizione}"></c:out>
												</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>
							
							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltri" name="singlebutton" type="button" onclick="cercaIncarico()" data-dismiss="modal" class="btn btn-primary">
										<spring:message text="??fascicolo.label.eseguiRicerca??" code="fascicolo.label.eseguiRicerca" />
									</button>
									<button name="singlebutton" type="button" data-dismiss="modal" class="btn btn-warning">
										<spring:message text="??fascicolo.label.chiudi??" code="fascicolo.label.chiudi" />
									</button>
								</div>
							</div>
							
							
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- FINE Aggiunta pannello ricerca atti protocollati da assegnare MASSIMO CARUSO -->

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>
		<script
		src="<%=request.getContextPath()%>/portal/js/controller/protocollo.js"></script>


	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>
 	

	<script type="text/javascript">
		initTabellaRicercaProtocolloIN();
		initTabellaRicercaProtocolloOUT();
		//inserimento funzione Javascript per la popolazione della tabella per gli atti MASSIMO CARUSO
		<%if(utenteConnesso.isTopResponsabile()){%>
			initTabellaAttiProtocollati();
		<% } %>
		//FINE inserimento funzione Javascript per la popolazione della tabella per gli atti MASSIMO CARUSO
	</script>
 

</body>
</html>
