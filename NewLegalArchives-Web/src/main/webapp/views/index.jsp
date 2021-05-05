<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@page import="eng.la.persistence.CostantiDAO"%>
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
				<div class="row-eq-height">
					<div id="col-1" class="col-lg-8 col-md-8 col-sm-12 col-sx-12">
						<!-- Tabs -->
						<div class="card">
						 <legarc:isAuthorized  nomeFunzionalita="<%=CostantiDAO.NASCONDI_A_LEG_ARC_GESTORE_ANAGRAFICA_PROCURE %>">
						
					 		<form:form id="preferitiForm" name="preferitiForm" method="post"
								modelAttribute="indexView" action="index.action">
						    <engsecurity:token regenerate="false"/>  
									
									<div class="card-header cw-header palette-Green-SNAM bg">
										<div class="cwh-day"><spring:message text="??index.label.fascicoliRecenti.iMieiPreferiti??" code="index.label.fascicoliRecenti.iMieiPreferiti"/></div>
									</div>
									<div class="card-body">
										<ul class="tab-nav tn-justified tn-icon" role="tablist">
											<li role="presentation" class="active"><a
												class="col-sx-4" href="#tab-1" aria-controls="tab-1"
												role="tab" data-toggle="tab"> <!--<i class="fa fa-star icon-tab" aria-hidden="true"></i>-->
													<small><spring:message text="??index.label.fascicoliRecenti.fascicoliRecenti??" code="index.label.fascicoliRecenti.fascicoliRecenti"/></small>
											</a></li>
											<li role="presentation"><a class="col-xs-4" href="#"
												onclick="return false;" aria-controls="tab-2"
												> <!--<i class="fa fa-search icon-tab" aria-hidden="true"></i>-->
													<small><spring:message text="??index.label.fascicoliRecenti.ricerche??" code="index.label.fascicoliRecenti.ricerche"/></small>
											</a></li>
											<li role="presentation"><a class="col-xs-4" href="#"
												onclick="return false;" aria-controls="tab-3" 
												> <!--<i class="fa fa-tags icon-tab" aria-hidden="true"></i>-->
													<small><spring:message text="??index.label.fascicoliRecenti.report??" code="index.label.fascicoliRecenti.report"/></small>
											</a></li>
										</ul>
	
										<div class="tab-content p-20">
											<div role="tabpanel"
												class="tab-pane animated fadeIn in active" id="tab-1">
												<!--documenti -->
	
	
												<div class="list-group lg-alt lg-even-black"
													id="divFascicoliRecenti">
	<%--
													<c:if test="${ indexView.listaUltimiFascicoli != null }">
	
														<c:if test="${ not empty indexView.listaUltimiFascicoli }">
															<c:forEach items="${indexView.listaUltimiFascicoli}"
																var="oggetto">
	
																<div class="list-group-item media">
																	<div class="pull-right">
																		<ul class="actions">
																			<li class="dropdown">
																				<a href="#" onclick="goToCercaFascicolo(${oggetto.vo.id}); return false;"
																					aria-expanded="false"><i
																					class="zmdi zmdi-more-vert"></i></a>
																			</li>
																		</ul>
																	</div>
																	<div class="media-body">
																		<div class="lgi-heading">
																			<c:out value="${oggetto.vo.nome}"></c:out>
																		</div>
																		<small class="lgi-text"><c:out
																				value="${oggetto.vo.descrizione}"></c:out></small>
																	</div>
																</div>
															</c:forEach>
														</c:if>
	
														<c:if test="${ empty indexView.listaUltimiFascicoli }">
															<div class="list-group-item media">
																<div class="media-body">
																	<small class="lgi-text">La lista non contiene
																		fascicoli recenti</small>
																</div>
															</div>
														</c:if>
	
													</c:if>
	 --%>
	
												</div>
	
	
	
												<!--documenti -->
											</div>
	
											<div role="tabpanel" class="tab-pane animated fadeIn"
												id="tab-2">
												<!-- ricerche -->
												<!--
												<div class="list-group lg-alt lg-even-black">
													<div class="list-group-item media">
														<div class="checkbox pull-left lgi-checkbox">
															<label> <input type="checkbox" value=""> <i
																class="input-helper"></i>
															</label>
														</div>
														<div class="pull-right">
															<ul class="actions">
																<li class="dropdown"><a href=""
																	data-toggle="dropdown" aria-expanded="false"><i
																		class="zmdi zmdi-more-vert"></i></a>
																	<ul class="dropdown-menu dropdown-menu-right">
																		<li><a href="#" class="delete">Elimina</a></li>
																		<li><a href="#" class="edit">Modifica</a></li>
																		<li><a data-toggle="modal" href="#modalDefault">Condividi</a></li>
																	</ul></li>
															</ul>
														</div>
	
														<div class="media-body">
															<div class="lgi-heading">
																<a href="ricerca_fascicolo.html">Contenziosi aperti
																	con A2A</a>
															</div>
															<small class="lgi-text">Quisque non tortor
																ultricies, posuere elit id, lacinia purus curabitur.</small>
														</div>
													</div>
													<div class="list-group-item media">
														<div class="checkbox pull-left lgi-checkbox">
															<label> <input type="checkbox" value=""> <i
																class="input-helper"></i>
															</label>
														</div>
														<div class="pull-right">
															<ul class="actions">
																<li class="dropdown"><a href=""
																	data-toggle="dropdown" aria-expanded="false"><i
																		class="zmdi zmdi-more-vert"></i></a>
																	<ul class="dropdown-menu dropdown-menu-right">
																		<li><a href="#" class="delete">Elimina</a></li>
																		<li><a href="#" class="edit">Modifica</a></li>
																		<li><a data-toggle="modal" href="#modalDefault">Condividi</a></li>
																	</ul></li>
															</ul>
														</div>
	
														<div class="media-body">
															<div class="lgi-heading">
																<a href="">Trova tutti i fascicoli aperti</a>
															</div>
															<small class="lgi-text">Quisque non tortor
																ultricies, posuere elit id, lacinia purus curabitur.</small>
														</div>
													</div>
													<div class="list-group-item media">
														<div class="checkbox pull-left lgi-checkbox">
															<label> <input type="checkbox" value=""> <i
																class="input-helper"></i>
															</label>
														</div>
														<div class="pull-right">
															<ul class="actions">
																<li class="dropdown"><a href=""
																	data-toggle="dropdown" aria-expanded="false"><i
																		class="zmdi zmdi-more-vert"></i></a>
																	<ul class="dropdown-menu dropdown-menu-right">
																		<li><a href="#" class="delete">Elimina</a></li>
																		<li><a href="#" class="edit">Modifica</a></li>
																		<li><a data-toggle="modal" href="#modalDefault">Condividi</a></li>
																	</ul></li>
															</ul>
														</div>
	
														<div class="media-body">
															<div class="lgi-heading">
																<a href="">Trova tutti gli atti del settore giuridico
																	penale</a>
															</div>
															<small class="lgi-text">Quisque non tortor
																ultricies, posuere elit id, lacinia purus curabitur.</small>
														</div>
													</div>
												</div>
		 -->
	
												<!--/ricerche -->
											</div>
	
											<div role="tabpanel" class="tab-pane animated fadeIn"
												id="tab-3">
												<!--report -->
	
												<!-- 
												<div class="list-group lg-alt lg-even-black">
													<div class="list-group-item media">
														<div class="checkbox pull-left lgi-checkbox">
															<label> <input type="checkbox" value=""> <i
																class="input-helper"></i>
															</label>
														</div>
														<div class="pull-right">
															<ul class="actions">
																<li class="dropdown"><a href=""
																	data-toggle="dropdown" aria-expanded="false"><i
																		class="zmdi zmdi-more-vert"></i></a>
																	<ul class="dropdown-menu dropdown-menu-right">
																		<li><a href="#" class="delete">Elimina</a></li>
																		<li><a href="#" class="edit">Modifica</a></li>
																		<li><a data-toggle="modal" href="#modalDefault">Condividi</a></li>
																	</ul></li>
															</ul>
														</div>
	
														<div class="media-body">
															<div class="lgi-heading">
																<a href="">Parcelle su CDC WN00005</a>
															</div>
															<small class="lgi-text">Quisque non tortor
																ultricies, posuere elit id, lacinia purus curabitur.</small>
														</div>
													</div>
													<div class="list-group-item media">
														<div class="checkbox pull-left lgi-checkbox">
															<label> <input type="checkbox" value=""> <i
																class="input-helper"></i>
															</label>
														</div>
														<div class="pull-right">
															<ul class="actions">
																<li class="dropdown"><a href=""
																	data-toggle="dropdown" aria-expanded="false"><i
																		class="zmdi zmdi-more-vert"></i></a>
																	<ul class="dropdown-menu dropdown-menu-right">
																		<li><a href="#" class="delete">Elimina</a></li>
																		<li><a href="#" class="edit">Modifica</a></li>
																		<li><a data-toggle="modal" href="#modalDefault">Condividi</a></li>
																	</ul></li>
															</ul>
														</div>
	
														<div class="media-body">
															<div class="lgi-heading">
																<a href="">Lettere di incarico approvate</a>
															</div>
															<small class="lgi-text">Quisque non tortor
																ultricies, posuere elit id, lacinia purus curabitur.</small>
														</div>
													</div>
													<div class="list-group-item media">
														<div class="checkbox pull-left lgi-checkbox">
															<label> <input type="checkbox" value=""> <i
																class="input-helper"></i>
															</label>
														</div>
														<div class="pull-right">
															<ul class="actions">
																<li class="dropdown"><a href=""
																	data-toggle="dropdown" aria-expanded="false"><i
																		class="zmdi zmdi-more-vert"></i></a>
																	<ul class="dropdown-menu dropdown-menu-right">
																		<li><a href="#" class="delete">Elimina</a></li>
																		<li><a href="#" class="edit">Modifica</a></li>
																		<li><a data-toggle="modal" href="#modalDefault">Condividi</a></li>
																	</ul></li>
															</ul>
														</div>
	
														<div class="media-body">
															<div class="lgi-heading">
																<a href="">Rating Legale Esterno Tribunale di Milano</a>
															</div>
															<small class="lgi-text">Quisque non tortor
																ultricies, posuere elit id, lacinia purus curabitur.</small>
														</div>
													</div>
												</div>
												-->
											</div>
										</div>
									</div>
	
	
									<div class="action-header palette-LightGreen-SNAM bg clearfix" style="min-height: 73px !important;">
										<div class="ah-label hidden-xs palette-White text">
											<spring:message text="??index.label.fascicoliRecenti.gestisciITuoiPreferiti??" 
											code="index.label.fascicoliRecenti.gestisciITuoiPreferiti" />
										</div>
										<div class="ah-search">
											<input type="text" placeholder="Cerca nei documenti salvati"
												class="ahs-input"> <i
												class="ah-search-close zmdi zmdi-long-arrow-left"
												data-ma-action="ah-search-close"></i>
										</div>
										<ul class="ah-actions actions a-alt">
	
												<li class="dropdown"><a href="" data-toggle="dropdown"
													aria-expanded="true" id="dropdownNumRighe"><strong
														style="color: white; font-size: 140%; line-height: 20px;"
														id="fascicoliRecentiNumRigheBadge">5</strong></a>
													<ul class="dropdown-menu dropdown-menu-right">
														<li><a href="#"
															onclick="changeFascicoliRecentiMaxSize(5)">5</a></li>
														<li><a href="#"
															onclick="changeFascicoliRecentiMaxSize(10)">10</a></li>
														<li><a href="#"
															onclick="changeFascicoliRecentiMaxSize(15)">15</a></li>
														<li><a href="#"
															onclick="changeFascicoliRecentiMaxSize(20)">20</a></li>
													</ul></li>
	
										</ul>
									</div>
								 </form:form>  
								
								
								</legarc:isAuthorized> 


							</div>
						</div>
						<div id="col-2 " class="col-lg-4 col-md-4 col-sm-12 col-sx-12 ">

						<!-- CALENDAR -->
						<style>
							.fc-content {
								cursor:pointer;
							}
						</style>
						<legarc:isAuthorized  nomeFunzionalita="<%=CostantiDAO.NASCONDI_A_LEG_ARC_GESTORE_ANAGRAFICA_PROCURE %>">
						
						<div class="card" id="calendar-widget">
							<div class="card-header cw-header palette-Green-SNAM bg">
								<!--<div class="cwh-year"></div>-->
								<div class="cwh-day"></div>
								<button class="btn palette-LightGreen-SNAM bg btn-float" id="btnAgenda">
									<i class="zmdi zmdi-plus" id="btnAgendaIcon"></i>
								</button>
							</div>
							<div class="card-body card-padding-sm" style="height: 75%;">
								<div id="cw-body"></div>
							</div>
							
							<legarc:isAuthorized nomeFunzionalita="<%=Costanti.FUNZIONALITA_RESPONSABILE %>">
							<div class="action-header palette-LightGreen-SNAM bg clearfix">
								<div class="ah-label hidden-xs palette-White text">
									<spring:message text="??index.label.agenda.visualizzazione??" 
									code="index.label.agenda.visualizzazione" /> :
								</div>
								<ul class="ah-actions actions a-alt">
									<li class="dropdown">
										<a href="" data-toggle="dropdown" aria-expanded="true" id="dropdownNumRighe" style="width:100% !important;">
										<strong style="color: white; font-size: 90%; line-height: 15px;" id="tipologiaVisualizzazione">
										 <spring:message text="??index.label.agenda.iMieiEventiScadenze??" code="index.label.agenda.iMieiEventiScadenze" /> </strong></a>
										<ul class="dropdown-menu dropdown-menu-right" id="tipologieDisponibili">
											
										</ul>
									</li>
								</ul>
							</div>
							</legarc:isAuthorized>
							<input type="hidden"  name="tipologiaVisualizzazioneVal" id="tipologiaVisualizzazioneVal" value="">
						</div>
						</legarc:isAuthorized>
						<!--/ CALENDAR -->
					</div>
				</div>
				<div class="row-eq-height">
					<div id="col-1" class="col-lg-8 col-md-8 col-sm-12 col-sx-12">			
							<!-- BUDGET LEGALE/FASCICOLO -->
							<legarc:isAuthorized nomeFunzionalita="<%=Costanti.FUNZIONALITA_FASCICOLI_NUOVO %>">
							<div class="card">
								<div class="card-header cw-header palette-Green-SNAM bg">
									<div class="cwh-day">
										<spring:message text="??index.label.budget.andamentoCostiEventoFascicolo??" 
										code="index.label.budget.andamentoCostiEventoFascicolo" />
									</div>
								</div>
								<div class="card-body card-padding">
									<br>
									
									<div id="torta-budget" class="flot-chart-pie" style="width:70%;"></div>
									<div id="torta-legend" class="flc-torta-budget hidden-xs" style="text-align:center;width:70%;"></div>
									<div id="flot-memo" style="text-align:center;height:30px;width:70%;height:20px;text-align:center;margin:0"></div>
									
									<div id="menu-torta" style="position: absolute; top: 45%; left: 70%; width: 25%;"></div>
								</div>
							</div>
							</legarc:isAuthorized>
							<!-- BUDGET LEGALE/FASCICOLO -->


						<!-- MY DOCS-->
						<!-- TO DO LIST -->
						<!-- 
						<div class="card" id="todo-lists">
							<div class="card-header cw-header palette-Green-SNAM bg">
								<div class="cwh-day">Le mie attività</div>
								 <ul class="actions a-alt">
									<li class="dropdown"><a href="" data-toggle="dropdown">
											<i class="zmdi zmdi-more-vert"></i>
									</a>
										<ul class="dropdown-menu dropdown-menu-right">
											<li><a href="">Change Date Range</a></li>
											<li><a href="">Change Graph Type</a></li>
											<li><a href="">Other Settings</a></li>
										</ul></li>
								</ul>
							</div>
							<div class="card-body">
								 <div class="list-group lg-alt">
									<div class="list-group-item-header palette-Purple text">Oggi</div>
									<div class="list-group-item media">
										<div class="pull-left">
											<div class="avatar-char ac-check">
												<input class="acc-check" type="checkbox" checked> <span
													class="acc-helper palette-Green-SNAM bg">C</span>
											</div>
										</div>
										<div class="media-body">
											<div class="lgi-heading">Consectetur Sem Sollicitudin</div>
											<small class="lgi-text">08:55 AM</small>
										</div>
									</div>
									<div class="list-group-item media">
										<div class="pull-left">
											<div class="avatar-char ac-check">
												<input class="acc-check" type="checkbox" checked> <span
													class="acc-helper palette-Green-SNAM bg">E</span>
											</div>
										</div>
										<div class="media-body">
											<div class="lgi-heading">Morbi leo risus, porta ac
												consectetur ac, vestibulum at eros.</div>
											<small class="lgi-text">07:32 AM</small>
										</div>
									</div>
									<div class="list-group-item-header palette-Light-Blue text">Domani</div>
									<div class="list-group-item media">
										<div class="pull-left">
											<div class="avatar-char ac-check">
												<input class="acc-check" type="checkbox"> <span
													class="acc-helper palette-Light-Blue bg">P</span>
											</div>
										</div>
										<div class="media-body">
											<div class="lgi-heading">Porta Venenatis Quam</div>
											<small class="lgi-text">10:30 P</small>
										</div>
									</div>
									<div class="list-group-item media">
										<div class="pull-left">
											<div class="avatar-char ac-check">
												<input class="acc-check" type="checkbox"> <span
													class="acc-helper palette-Light-Blue bg">N</span>
											</div>
										</div>
										<div class="media-body">
											<div class="lgi-heading">Nullam quis risus eget urna
												mollis ornare vel eu leo</div>
											<small class="lgi-text">11:02 PM</small>
										</div>
									</div>
									<a href="" class="list-group-item view-more"><i
										class="zmdi zmdi-long-arrow-right"></i> Vedi tutte le attività</a>
								</div> 
								 <button
									class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float">
									<i class="zmdi zmdi-plus"></i>
								</button> 
							</div>
						</div>
						 -->
						<!-- TO DO LIST -->


					</div>
					<!--/ fine col-1 -->
					<div id="col-2 " class="col-lg-4 col-md-4 col-sm-12 col-sx-12 ">

						<!-- BUDGET RESPONSABILE -->
						<legarc:isAuthorized nomeFunzionalita="<%=Costanti.FUNZIONALITA_RESPONSABILE %>">
						<div class="card">
							<div class="card-header cw-header palette-Green-SNAM bg">
								<div class="cwh-day">
									<spring:message text="??index.label.budget.andamentoCostiResponsabile??" code="index.label.budget.andamentoCostiResponsabile" />
								</div>
							</div>

							<div class="card-body card-padding">
								<br>
								<div id="torta-responsabile-budget" class="flot-chart-pie"></div>
								<div id="torta-responsabile-legend" class="flc-torta-responsabile-budget hidden-xs"></div>
								<div id="flot-memo-responsabile" style="text-align:center;height:30px;text-align:center;margin:0"></div>
							</div>
						</div>
						</legarc:isAuthorized>
						<!-- BADGET RESPONSABILE -->

					</div>
				</div>
			</div>	
		</section>

	</section>
	
 	<jsp:include page="/views/agenda/modalAgenda.jsp"></jsp:include>
 	<jsp:include page="/views/incarico/modalIncarico.jsp"></jsp:include>  
 	
 	
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
	function stampaFascicoloDettaglio(id){
		var url="<%=request.getContextPath()%>/fascicolo/stampaDettaglio.action?id="+id+"&azione=visualizza";
		waitingDialog.show('Loading...');
		var ifr=$("#bodyDettaglioStampa").attr("src",url);
		ifr.load(function(){
		  	 waitingDialog.hide();
			$("#modalDettaglioStampa").modal("show");
		})
	}
	</script>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>
	
	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>
	
	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/fascicoliRecenti.js"></script>
	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/agenda.js"></script>  
	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/agendaSelezioneFascicolo.js"></script>
	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/agendaCalendarWidget.js"></script>
	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/indexBudget.js"></script>

	<script>
	function caricaAzioniSuFascicoloContenuto(id) {
		var containerAzioni = document.getElementById("containerAzioniFascicolo"+ id);
		
		containerAzioni.innerHTML="<li><img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'></li>";
		var ajaxUtil = new AjaxUtil();
		var callBackFn = function(data, stato) {
	
			containerAzioni.innerHTML = data;
	
		};
	
		var url = WEBAPP_BASE_URL
				+ "fascicolo/caricaAzioniFascicolo.action?onlyContent=1&idFascicolo=" + id;
		url=legalSecurity.verifyToken(url);
		ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
	
	}	
	</script>
	
	<script type="text/javascript">
        var strings = new Array();
		strings['fascicolo.label.utente'] = "<spring:message code='fascicolo.label.utente' javaScriptEscape='true'/>";
		strings['calendar.label.fascicolo'] = "<spring:message code='calendar.label.fascicolo' javaScriptEscape='true'/>";
		strings['index.label.budget.nessunDatoDisponibile'] = "<spring:message code='index.label.budget.nessunDatoDisponibile' javaScriptEscape='true'/>";
		strings['index.label.budget.totale'] = "<spring:message code='index.label.budget.totale' javaScriptEscape='true'/>";
		strings['index.label.budget.nessunFascicolo'] = "<spring:message code='index.label.budget.nessunFascicolo' javaScriptEscape='true'/>";
 	</script>
</body>
</html>
