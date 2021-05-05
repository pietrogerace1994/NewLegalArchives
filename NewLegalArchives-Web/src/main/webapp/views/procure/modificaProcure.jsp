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

<jsp:include page="/parts/script-init.jsp"></jsp:include>

<style>
.disabled {
	pointer-events: none;
	cursor: not-allowed;
}

.paddMultifile {
	padding-top: 5px;
}
</style>
</head>

<body data-ma-header="teal">
	<% String disableInVis = request.getAttribute("disableInVis")==null?"false":"true";  %>

	<jsp:include page="/parts/header.jsp"></jsp:include>

	<!-- SECION MAIN -->
	<section id="main">
		<jsp:include page="/parts/aside.jsp"></jsp:include>

		<!-- SECTION CONTENT -->
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
						<div class="card">
							<div class="card-header ch-dark palette-Green-SNAM bg">
								<% if ("true".equals(disableInVis)){ %>							
									<h2>
										<spring:message text="??procure.label.visualizza??"
											code="procure.label.visualizza" />
									</h2>
								<% }else{ %>
									<h2>
										<spring:message text="??procure.label.modifica??"
											code="procure.label.modifica" />
									</h2>
								<% } %>							
							</div>

							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form id="procureForm" name="procureForm" method="post"
									modelAttribute="procureView" action="salva.action"
									class="form-horizontal la-form" enctype="multipart/form-data">
                                    <engsecurity:token regenerate="false"/>

									<form:errors path="*" cssClass="alert alert-danger" htmlEscape="false"
										element="div"></form:errors>
									<form:hidden path="op" id="op" value="modificaProcure" />


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


									<div id="creaDiv">

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="nomeProcuratore" class="col-sm-2 control-label"><spring:message
															text="??procure.label.nomeProcuratore??"
															code="procure.label.nomeProcuratore" /></label>
													<div class="col-sm-10">
														<form:input path="nomeProcuratore" cssClass="form-control" disabled="<%= disableInVis %>"
															id="txtNomeProcuratore"
															value="${procureView.getVo().nomeProcuratore}" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<!--tipologia-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="tipologia"
															class="col-sm-2 control-label"><spring:message
																text="??procure.label.tipologia??"
																code="procure.label.tipologia" /></label>
														<div class="col-sm-10">
															<form:select path="tipologia" disabled="<%= disableInVis %>"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??procure.label.tipologia??"
																		code="procure.label.tipologia" />
																</form:option>
																<c:if test="${ procureView.listaTipologie != null }">
																	<c:forEach items="${procureView.listaTipologie}"
																		var="oggetto">

																		<form:option value="${ oggetto.vo.id }">
																			<c:out value="${oggetto.vo.descrizione}"></c:out>
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
											<div class="list-group-item">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??procure.label.numeroRepertorio??"
															code="procure.label.numeroRepertorio" /></label>
													<div class="col-sm-10">
														<form:input path="numeroRepertorio"
															cssClass="form-control" id="txtNumeroRepertorio" disabled="<%= disableInVis %>"
															value="${procureView.getVo().numeroRepertorio}" />
													</div>
												</div>
											</div>
										</div>


										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataConferimento"
														class="col-sm-2 control-label"> <spring:message
															text="??procure.label.dataConferimento??"
															code="procure.label.dataConferimento" />
													</label>
													<div class="col-sm-10">
														<form:input id="txtDataConferimento" disabled="true"
															path="dataConferimento"
															cssClass="form-control date-picker"
															value="${procureView.getVo().dataConferimento}" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataRevoca" class="col-sm-2 control-label">
														<spring:message text="??procure.label.dataRevoca??"
															code="procure.label.dataRevoca" />
													</label>
													<div class="col-sm-10">
														<form:input id="txtDataRevoca" path="dataRevoca" disabled="<%= disableInVis %>"
															cssClass="form-control date-picker"
															value="${procureView.getVo().dataRevoca}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="utente" class="col-sm-2 control-label"><spring:message
															text="??procure.label.utente??"
															code="procure.label.utente" /></label>
													<div class="col-sm-10">
														<form:input path="utente"
															cssClass="form-control" id="txtUtente" disabled="<%= disableInVis %>"
															value="${procureView.getVo().utente}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<!--posizione organizzativa-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="posizioneOrganizzativa"
															class="col-sm-2 control-label"><spring:message
																text="??procure.label.posizioneOrganizzativa??"
																code="procure.label.posizioneOrganizzativa" /></label>
														<div class="col-sm-10">
															<form:select path="posizioneOrganizzativa" disabled="<%= disableInVis %>"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??procure.label.seleziona??"
																		code="procure.label.seleziona" />
																</form:option>
																<c:if test="${ procureView.listaPosizioneOrganizzativa != null }">
																	<c:forEach items="${procureView.listaPosizioneOrganizzativa}"
																		var="oggetto">

																		<form:option value="${ oggetto.vo.id }">
																			<c:out value="${oggetto.vo.descrizione}"></c:out>
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
											<!--primo livello attribuzioni-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="livelloAttribuzioniI"
															class="col-sm-2 control-label"><spring:message
																text="??procure.label.livelloAttribuzioniI??"
																code="procure.label.livelloAttribuzioniI" /></label>
														<div class="col-sm-10">
															<form:select path="livelloAttribuzioniI" disabled="<%= disableInVis %>"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??procure.label.seleziona??"
																		code="procure.label.seleziona" />
																</form:option>
																<c:if test="${ procureView.listaLivelloAttribuzioniI != null }">
																	<c:forEach items="${procureView.listaLivelloAttribuzioniI}"
																		var="oggetto">

																		<form:option value="${ oggetto.vo.id }">
																			<c:out value="${oggetto.vo.descrizione}"></c:out>
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
											<!--secondo livello attribuzioni-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="livelloAttribuzioniII"
															class="col-sm-2 control-label"><spring:message
																text="??procure.label.livelloAttribuzioniII??"
																code="procure.label.livelloAttribuzioniII" /></label>
														<div class="col-sm-10">
															<form:select path="livelloAttribuzioniII" disabled="<%= disableInVis %>"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??procure.label.seleziona??"
																		code="procure.label.seleziona" />
																</form:option>
																<c:if test="${ procureView.listaLivelloAttribuzioniII != null }">
																	<c:forEach items="${procureView.listaLivelloAttribuzioniII}"
																		var="oggetto">

																		<form:option value="${ oggetto.vo.id }">
																			<c:out value="${oggetto.vo.descrizione}"></c:out>
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
											<!--PROFESSIONISTA-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="professionista" class="col-sm-2 control-label"><spring:message
																text="??procure.label.professionista??"
																code="incarico.label.professionista" /></label>
														<div class="col-sm-10">
															<form:select path="idNotaio" disabled="<%= disableInVis %>"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??procure.label.selezionaProfessionista??"
																		code="incarico.label.selezionaProfessionista" />
																</form:option>
																<c:if
																	test="${ procureView.listaProfessionista != null }">
																	<c:forEach items="${procureView.listaProfessionista}"
																		var="oggetto">
																		<c:if
																			test="${oggetto.vo.statoEsitoValutazioneProf.codGruppoLingua eq 'TEVP_1'}">
																			<form:option value="${ oggetto.vo.id }">
																				<c:out
																					value="${oggetto.vo.studioLegale.denominazione} ${oggetto.cognomeNome}"></c:out>
																			</form:option>
																		</c:if>
																	</c:forEach>
																</c:if>
															</form:select>
														</div>
													</div>
												</div>
											</div>
										</div>



										<div class="list-group lg-alt">
											<!--societa appartenenza-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="societaAppartenenza"
															class="col-sm-2 control-label"><spring:message
																text="??procure.label.societaAppartenenza??"
																code="procure.label.societaAppartenenza" /></label>
														<div class="col-sm-10">
															<form:select path="idSocieta" disabled="<%= disableInVis %>"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??procure.label.selezionaSocieta??"
																		code="procure.label.selezionaSocieta" />
																</form:option>
																<c:if test="${ procureView.listaSocieta != null }">
																	<c:forEach items="${procureView.listaSocieta}"
																		var="oggetto">

																		<form:option value="${ oggetto.vo.id }">
																			<c:out value="${oggetto.vo.nome}"></c:out>
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
											<div class="list-group-item">
												<div class="form-group">
													<label for="descrizione" class="col-sm-2 control-label"></label>
													<div class="col-sm-10">

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
																	
																	<% if ("false".equals(disableInVis)){ %>
																	
																		<button type="button" data-toggle="modal"
																			onclick="openModalAllegatoGenerico()"
																			class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																			style="float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																			aria-expanded="true">
																			<i class="zmdi zmdi-plus icon-mini"></i>
																		</button>
																	<% } %>
																		

																	<a id="allegatiGenericiGraffa"
																		style="display: none; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																		aria-expanded="true"> <i class="fa fa-paperclip "></i>
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
																	page="/subviews/procure/allegatiGenerici.jsp">
																		<jsp:param name="disableInVis" value="<%=  disableInVis %>"/>
																	</jsp:include>

															</div>
														</div>



													</div>
												</div>
											</div>
										</div>

										
										<% if ("false".equals(disableInVis)){ %>
										<div class="row">
										<div class="col-sm-10"></div>
										<div class="col-sm-1">
										<button form="procureForm" onclick="salvaProcure()"
										class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float btnSalva">
										<i class="zmdi zmdi-save"></i>
										</button>
										</div>
										<div class="col-sm-1"></div>
										</div>
										<% } %>
										
									</div>
									
									

									
									
							</div>
							<!-- creaDiv -->



							</form:form>

						</div>
					</div>
				</div>
			</div>
			</div>

		</section>

	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>

	<jsp:include page="/parts/script-end.jsp"></jsp:include>
	<!-- si carica il js -->

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/procure.js"></script>

	<script type="text/javascript">
		if (document.getElementById("descrizioneProtocollo")) {
			CKEDITOR.replace('descrizioneProtocollo');
		}

		/*
		// funzione per mostrare il modal racchiuso nel boxAllegati
		// Apre il boxAllegati e visualizza il modal per caricare un nuovo allegato
		 */
		function openModalAllegatoGenerico() {
			$("#boxAllegatiGenerici").collapse('show');
			$("#panelAggiungiAllegatoGenerico").modal('show');
		}
	</script>

</body>

</html>
