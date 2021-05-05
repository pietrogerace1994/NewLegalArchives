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
									test="${ fascicoloView.fascicoloId == null || fascicoloView.fascicoloId == 0 }">
									<h2>
										<spring:message text="??fascicolo.label.nuovoFascicolo??"
											code="fascicolo.label.nuovoFascicolo" />
									</h2>
								</c:if>
								<c:if
									test="${ fascicoloView.fascicoloId != null && fascicoloView.fascicoloId > 0 }">
									<h2>
										<spring:message text="??fascicolo.label.modificaFascicolo??"
											code="fascicolo.label.modificaFascicolo" />
									</h2>
								</c:if>
							</div>
							<div class="card-body">

								<form:form name="fascicoloForm" method="post"
									modelAttribute="fascicoloView" action="salva.action"
									class="form-horizontal la-form">
									<engsecurity:token regenerate="false"/>
									
									<c:if test="${ not empty param.errorMessage }">
										<div class="alert alert-danger">
											<spring:message code="${param.errorMessage}"
												text="??${param.errorMessage}??"></spring:message>
										</div>
									</c:if>
									<form:errors path="*" cssClass="alert alert-danger" htmlEscape="false"
										element="div"></form:errors>
									<form:hidden path="fascicoloId"  id="fascicoloId"/>
									<form:hidden path="op" id="op" value="salvaFascicolo" />
									<form:hidden path="tabAttiva" id="tabAttiva" />
									<input type="hidden" id="idAtto" name="idAtto" value="<c:out value="${idAtto}"></c:out>">
									<c:set var="tab1StyleAttiva" scope="page" value="" />
									<c:set var="tab2StyleAttiva" scope="page" value="" />
									<c:set var="tab3StyleAttiva" scope="page" value="" />
									<c:if
										test="${fascicoloView.tipologiaFascicoloCode eq null or fascicoloView.tipologiaFascicoloCode eq '' }">
										<c:set var="tab3StyleAttiva" scope="page" value="tab-hidden" />
										<c:set var="tab2StyleAttiva" scope="page" value="tab-hidden" />
									</c:if>
									<c:if
										test="${fascicoloView.tipologiaFascicoloCode ne null and fascicoloView.tipologiaFascicoloCode ne '' }">
										<c:set var="tab2StyleAttiva" scope="page" value="" />
										<c:set var="tab3StyleAttiva" scope="page" value="" />
									</c:if>

									<c:if test="${fascicoloView.tabAttiva eq '1' }">
										<c:set var="tab1StyleAttiva" scope="page" value="active" />
									</c:if>
									<c:if test="${fascicoloView.tabAttiva eq '2' }">
										<c:set var="tab2StyleAttiva" scope="page" value="active" />
									</c:if>
									<c:if test="${fascicoloView.tabAttiva eq '3' }">
										<c:set var="tab3StyleAttiva" scope="page" value="active" />
									</c:if>

									<ul class="tab-nav tn-justified tn-icon" role="tablist">
										<li role="presentation" class='${tab1StyleAttiva }'
											onclick="document.getElementById('tabAttiva').value='1'"><a
											class="col-sx-4" href="#tab-1" aria-controls="tab-1"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-star icon-tab" aria-hidden="true"></i>-->
												<small><spring:message
														text="??fascicolo.label.datiIniziali??"
														code="fascicolo.label.datiIniziali" /></small>
										</a></li>
										<li role="presentation" class='${tab2StyleAttiva }'
											onclick="document.getElementById('tabAttiva').value='2'"><a
											class="col-xs-4" href="#tab-2" aria-controls="tab-2"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-search icon-tab" aria-hidden="true"></i>-->
												<small><spring:message
														text="??fascicolo.label.datiPrincipali??"
														code="fascicolo.label.datiPrincipali" /></small>
										</a></li>
										<li role="presentation" class='${tab3StyleAttiva }'
											onclick="document.getElementById('tabAttiva').value='3'"><a
											class="col-xs-4" href="#tab-3" aria-controls="tab-3"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-tags icon-tab" aria-hidden="true"></i>-->
												<small><spring:message
														text="??fascicolo.label.altriDati??"
														code="fascicolo.label.altriDati" /></small>
										</a></li>
									</ul>

									<div class="tab-content p-20">
										<div role="tabpanel"
											class="tab-pane animated fadeIn in ${tab1StyleAttiva }"
											id="tab-1">
											<c:if
												test="${ fascicoloView.fascicoloId != null && fascicoloView.fascicoloId > 0 }">

												<div class="list-group lg-alt">
													<!--STATO FASCICOLO-->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for=stato class="col-sm-2 control-label"><spring:message
																		text="??fascicolo.label.stato??"
																		code="fascicolo.label.stato" /></label>
																<div class="col-sm-10">
																	<form:input path="statoFascicolo"
																		cssClass="form-control" readonly="true" />
																</div>
															</div>
														</div>
													</div>
												</div>
											

												<div class="list-group lg-alt">
													<!--NOME FASCICOLO-->
													<div class="list-group-item media">
														<div class="media-body">
															<div class="form-group">
																<label for="nome" class="col-sm-2 control-label"><spring:message
																		text="??fascicolo.label.nome??"
																		code="fascicolo.label.nome" /></label>
																<div class="col-sm-10">
																	<form:input path="nome" cssClass="form-control"
																		readonly="true" />
																</div>
															</div>
														</div>
													</div>
												</div>
											</c:if>
											<!-- TIPOLOGIA FASCICOLO -->
											<div class="list-group lg-alt">

												<!--TIPOLOGIA FASCICOLO-->
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label for="tipologiaFascicolo"
																class="col-sm-2 control-label"><spring:message
																	text="??fascicolo.label.tipologiaFascicolo??"
																	code="fascicolo.label.tipologiaFascicolo" /></label>
															<div class="col-sm-10">
																<c:if
																	test="${ fascicoloView.fascicoloId ne null && fascicoloView.fascicoloId ne 0 }">
																	<form:select path="tipologiaFascicoloCode"
																		disabled="true"
																		onchange="selezionaTipologiaFascicolo(this.value)"
																		cssClass="form-control">
																		<form:option value="">
																			<spring:message
																				text="??fascicolo.label.selezionaTipologiaFascicolo??"
																				code="fascicolo.label.selezionaTipologiaFascicolo" />
																		</form:option>

																		<c:if
																			test="${ fascicoloView.listaTipologiaFascicolo != null }">
																			<c:forEach
																				items="${fascicoloView.listaTipologiaFascicolo}"
																				var="oggetto">
																				<form:option value="${ oggetto.vo.codGruppoLingua }">
																					<c:out value="${oggetto.vo.descrizione}"></c:out>
																				</form:option>
																			</c:forEach>
																		</c:if>
																	</form:select>
																</c:if>
																<c:if
																	test="${ fascicoloView.fascicoloId eq null || fascicoloView.fascicoloId eq 0 }">
																	<form:select path="tipologiaFascicoloCode"
																		onchange="selezionaTipologiaFascicolo(this.value)"
																		cssClass="form-control">
																		<form:option value="">
																			<spring:message
																				text="??fascicolo.label.selezionaTipologiaFascicolo??"
																				code="fascicolo.label.selezionaTipologiaFascicolo" />
																		</form:option>

																		<c:if
																			test="${ fascicoloView.listaTipologiaFascicolo != null }">
																			<c:forEach
																				items="${fascicoloView.listaTipologiaFascicolo}"
																				var="oggetto">
																				<form:option value="${ oggetto.vo.codGruppoLingua }">
																					<c:out value="${oggetto.vo.descrizione}"></c:out>
																				</form:option>
																			</c:forEach>
																		</c:if>
																	</form:select>
																</c:if>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div> 
 
										 
										<c:if test="${ fascicoloView.tipologiaFascicoloCode ne null }">
											<c:if
												test="${fascicoloView.tipologiaFascicoloCode eq 'TFSC_1' }">
												<jsp:include page="/subviews/fascicolo/giudiziale.jsp"></jsp:include>
											</c:if>
											<c:if
												test="${fascicoloView.tipologiaFascicoloCode eq 'TFSC_2' }">
												<jsp:include page="/subviews/fascicolo/stragiudiziale.jsp"></jsp:include>
											</c:if>
											<c:if
												test="${fascicoloView.tipologiaFascicoloCode eq 'TFSC_4' }">
												<jsp:include page="/subviews/fascicolo/notarile.jsp"></jsp:include>
											</c:if>

										</c:if>

									</div>
								</form:form>



								<button form="fascicoloForm" onclick="salvaFascicolo()"
									class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float">
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
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>


	<div class="modal fade" id="panelFormRicorso" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.ricorso??"
							code="fascicolo.label.ricorso" />
					</h4>
				</div>
				<div class="modal-body">
					<div id="containerFormRicorso"></div>
				</div>

				<div class="modal-footer">

					<button id="btnAggiungiRicorso"
						name="btnAggiungiRicorso" onclick="aggiungiRicorso()"
						 type="button"
						class="btn btn-primary">
						<spring:message text="??fascicolo.label.ok??"
							code="fascicolo.label.ok" />
					</button>
					<button name="btnChiudiRicorso" id="btnChiudiPanelRicorso" type="button"
						data-dismiss="modal" class="btn btn-warning">
						<spring:message text="??fascicolo.label.chiudi??"
							code="fascicolo.label.chiudi" />
					</button>

				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="panelFormGiudizio" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.giudizio??"
							code="fascicolo.label.giudizio" />
					</h4>
				</div>
				<div class="modal-body">
					<div id="containerFormGiudizio"></div>
				</div>

				<div class="modal-footer">

					<button id="btnAggiungiGiudizio"
						name="btnAggiungiGiudizio" onclick="aggiungiGiudizio()"
						  type="button"
						class="btn btn-primary">
						<spring:message text="??fascicolo.label.ok??"
							code="fascicolo.label.ok" />
					</button>
					<button name="btnChiudiGiudizio" id="btnChiudiPanelGiudizio" type="button"
						data-dismiss="modal" class="btn btn-warning">
						<spring:message text="??fascicolo.label.chiudi??"
							code="fascicolo.label.chiudi" />
					</button>

				</div>
			</div>
		</div>
	</div>

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/fascicolo.js"></script>
	<script type="text/javascript">
			var jsonDataMaterie = null;
	</script>

	<c:if test="${ not empty fascicoloView.jsonAlberaturaMaterie }">
		<script type="text/javascript">
			var jsonDataMaterie = ${fascicoloView.jsonAlberaturaMaterie }
			
			var materieDaSelezionare = new Array();
		</script>
	</c:if>
 

	<c:if test="${ not empty fascicoloView.materie }">
		<c:forEach items="${fascicoloView.materie}" var="materiaCodice">
			<script type="text/javascript">
				materieDaSelezionare.push("${materiaCodice}");
			</script>
		</c:forEach>
	</c:if>

	<script type="text/javascript"> 

		$(document)
				.ready(
						function() {
						
							if ($("#txtControparte")) {
								autocompleteNomeControparte("txtControparte");
							}

							if ($("#txtTerzoChiamatoInCausa")) {
								autocompleteTerzoChiamatoInCausa("txtTerzoChiamatoInCausa");
							}

							if ($("#txtAutoritaEmanante")) {
								autocompleteAutoritaEmanante("txtAutoritaEmanante");
							}

							if ($("#txtControinteressato")) {
								autocompleteControinteressato("txtControinteressato");
							}

							if ($("#txtAutoritaGiudiziaria")) {
								autocompleteAutoritaGiudiziaria("txtAutoritaGiudiziaria");
							}

							if ($("#txtSoggettoIndagato")) {
								autocompleteSoggettoIndagato("txtSoggettoIndagato");
							}

							if ($("#txtPersonaOffesa")) {
								autocompletePersonaOffesa("txtPersonaOffesa");
							}

							if ($("#txtParteCivile")) {
								autocompleteParteCivile("txtParteCivile");
							}

							if ($("#txtResponsabileCivile")) {
								autocompleteResponsabileCivile("txtResponsabileCivile");
							}
							
							if ($('#treeContainerMaterie')
									&& jsonDataMaterie != null
									&& jsonDataMaterie != undefined) {
								$('#treeContainerMaterie').easytree({
									data : jsonDataMaterie,
									built : selezionaMaterie
								});
							}

						});
		
		
		function selezionaMaterie() {
			if (materieDaSelezionare) {
				for (i = 0; i < materieDaSelezionare.length; i++) {
					document.getElementById(materieDaSelezionare[i]).checked = true;
				}
			}
		}
	</script>

	<div class="modal fade" id="panelCercaSelezionaFascicoli" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.cercaSelezionaFascicoli??"
							code="fascicolo.label.cercaSelezionaFascicoli" />
					</h4>
				</div>
				<div class="modal-body">
					 <div id="containerRicercaModaleFascicoli" class="col-md-16"></div>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal fade" id="panelCercaSelezionaPadreFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.cercaSelezionaFascicoli??"
							code="fascicolo.label.cercaSelezionaFascicoli" />
					</h4>
				</div>
				<div class="modal-body">
					 <div id="containerRicercaModaleFascicoloPadre" class="col-md-16"></div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$('#panelCercaSelezionaFascicoli').on('show.bs.modal', function(e) {
	    var div = document.getElementById("containerRicercaModaleFascicoli");
	    div.innerHTML="";
	    var fnCallbackSuccess = function(data){
	    	div.innerHTML=data; 
    		initTabellaRicercaFascicoliModale(true);
	    };
	    var ajaxUtil = new AjaxUtil();
	    ajaxUtil.ajax("<%=request.getContextPath()%>/fascicolo/cercaModale.action?multiple=true&tipologiaFascicoloCode=${fascicoloView.tipologiaFascicoloCode}", null, "GET", "text/html", fnCallbackSuccess, null, null)
	});
	
	$('#panelCercaSelezionaPadreFascicolo').on('show.bs.modal', function(e) {
	    var div = document.getElementById("containerRicercaModaleFascicoloPadre");
	    div.innerHTML="";
	    var fnCallbackSuccess = function(data){
	    	div.innerHTML=data; 
    		initTabellaRicercaFascicoliModale(false);
	    };
	    var ajaxUtil = new AjaxUtil();
	    ajaxUtil.ajax("<%=request.getContextPath()%>/fascicolo/cercaModale.action?multiple=false&tipologiaFascicoloCode=${fascicoloView.tipologiaFascicoloCode}", null, "GET", "text/html", fnCallbackSuccess, null, null)
	});
	<%if( request.getAttribute("anchorName") != null ) {%>
		goToAncora('<%=request.getAttribute("anchorName")%>');
	<%}%>
	</script>
	<!-- PANEL CONFIRM RICHIEDI CHIUSURA-->
</body>
</html>
