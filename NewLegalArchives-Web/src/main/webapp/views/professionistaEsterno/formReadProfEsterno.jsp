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
									<h2>
										<spring:message text="??professionistaEsterno.label.visualizza??"
											code="professionistaEsterno.label.visualizza" />
									</h2>
								
							</div>
							<div class="card-body">

								<form:form name="professionistaEsternoReadForm" method="post"
									modelAttribute="professionistaEsternoView" action="salva.action"
									class="form-horizontal la-form">

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
									<form:hidden path="op" id="op" value="salvaProfessionistaEsterno" />
									<form:hidden path="editMode" id="editMode" value="false" />
									<form:hidden path="viewMode" id="viewMode" value="true" />
									
									<div class="tab-content p-20">
										
										<!-- RICERCA NELLA LISTA DEI PROFESSIONISTI ESTERNI -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="professionistaEsternoId" class="col-sm-2 control-label">
														<i class="zmdi zmdi-search hs-reset" data-ma-action="search-clear" style="cursor: default !important; color:black !important; left:100px;"></i>
													</label>
													<div class="col-sm-10">
														
														<div id="" style="max-height:250px;overflow:auto;-ms-overflow-style: auto;">
															<input id="filtroListaProfessionistaEsterno" type="text" class="form-control">
															
														</div> 
															
															
													</div>
												</div>
											</div>
										</div>

										<!-- LISTA PROFESSIONISTI ESTERNI -->
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="listProfEst"
															class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.lista??"
																code="professionistaEsterno.label.lista" /></label>
														<div class="col-sm-10">
															<form:select size="5" path="professionistaEsternoId"
																onchange="caricaDescrizioniProfEst(this.value); pulisciVotazioniProfEst();"
																cssClass="form-control">
																<c:if test="${ professionistaEsternoView.listaProfEst != null }">
																	<c:forEach
																		items="${professionistaEsternoView.listaProfEst}"
																		var="oggetto">
																		<form:option value="${ oggetto.vo.id }">
																			<c:out value="${oggetto.vo.cognome} ${oggetto.vo.nome} ${oggetto.vo.codiceFiscale}"></c:out>
																		</form:option>
																	</c:forEach>
																</c:if>
															</form:select>
														</div>
													</div>
												</div>
											</div>
										</div>
										
										<!-- NOME -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??professionistaEsterno.label.nome??" code="professionistaEsterno.label.nome" /></label>
													<div class="col-sm-10">
														<form:input path="nome" cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
										
										<!-- COGNOME -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="cognome" class="col-sm-2 control-label"><spring:message
															text="??professionistaEsterno.label.cognome??" code="professionistaEsterno.label.cognome" /></label>
													<div class="col-sm-10">
														<form:input path="cognome" cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
										
										<!-- CODICE FISCALE -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="codiceFiscale" class="col-sm-2 control-label"><spring:message
															text="??professionistaEsterno.label.codiceFiscale??" code="professionistaEsterno.label.codiceFiscale" /></label>
													<div class="col-sm-10">
														<form:input path="codiceFiscale" cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
										
										<!-- TELEFONO -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="telefono" class="col-sm-2 control-label"><spring:message
															text="??professionistaEsterno.label.telefono??" code="professionistaEsterno.label.telefono" /></label>
													<div class="col-sm-10">
														<form:input path="telefono" cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
										
										<!-- FAX -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="fax" class="col-sm-2 control-label"><spring:message
															text="??professionistaEsterno.label.fax??" code="professionistaEsterno.label.fax" /></label>
													<div class="col-sm-10">
														<form:input path="fax" cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
										
										<!-- EMAIL -->
										<div id="divEmail">
											<div class="list-group-item media" >
												<div class="media-body">
													<div class="form-group">
														<label for="fax" class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.email??" code="professionistaEsterno.label.email" /></label>
														<div class="col-sm-10">
															<form:input path="email[0]" cssClass="form-control" readonly="true" />
														</div>
													</div>
												</div>
											</div>
										</div>
										
										<!-- LISTA NAZIONI -->
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="listNazioni"
															class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.nazione??"
																code="professionistaEsterno.label.nazione" /></label>
														<div class="col-sm-10">
															<form:select size="5" path="nazioneDesc"
																cssClass="form-control" readonly="true">
															</form:select>
														</div>
													</div>
												</div>
											</div>
										</div>
										
										<!-- LISTA SPECIALIZZAZIONI -->
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="listSpec"
															class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.specializzazione??"
																code="professionistaEsterno.label.specializzazione" /></label>
														<div class="col-sm-10">
															<form:select size="5" path="specDesc"
																cssClass="form-control" readonly="true">
															</form:select>
														</div>
													</div>
												</div>
											</div>
										</div>
										
										<!-- TIPO PROFESSIONISTA -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="fax" class="col-sm-2 control-label"><spring:message
															text="??professionistaEsterno.label.tipoProfessionista??" 
															code="professionistaEsterno.label.tipoProfessionista" /></label>
													<div class="col-sm-10">
														<form:input path="tipoProfessionista" cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
										
										<!-- STATO RICHIESTA -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="fax" class="col-sm-2 control-label"><spring:message
															text="??professionistaEsterno.label.statoRichiesta??" 
															code="professionistaEsterno.label.statoRichiesta" /></label>
													<div class="col-sm-10">
														<form:input path="statoProfessionista" cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
										
										<!-- MOTIVAZIONE RICHIESTA -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="motivazioneRichiesta" class="col-sm-2 control-label"><spring:message
															text="??professionistaEsterno.label.motivazione??" code="professionistaEsterno.label.motivazione" /></label>
													<div class="col-sm-10">
														<form:textarea path="motivazioneRichiesta" cssClass="form-control" cols="50" rows="3" readonly="true"/>
													</div>
												</div>
											</div>
										</div>
											
										<!-- STATO ESITO VALIDAZIONE -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="fax" class="col-sm-2 control-label"><spring:message
															text="??professionistaEsterno.label.statoEsitoValutazioneProf??" 
															code="professionistaEsterno.label.statoEsitoValutazioneProf" /></label>
													<div class="col-sm-10">
														<form:input path="statoEsitoValutazioneProf" cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
											
										<!-- STUDIO LEGALE -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="fax" class="col-sm-2 control-label"></label>
													<div class="col-sm-10">
														<jsp:include page="nuovoStudioLegale.jsp">
															<jsp:param name="initialClass" value="" />
															<jsp:param name="readOnly" value="true" />
														</jsp:include>
													</div>
												</div>
											</div>
										</div>
										
										<!-- GIUDIZIO -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="giudizio" class="col-sm-2 control-label">Giudizio</label>
													<div class="col-sm-10">
														<form:select id="giudizio" path="giudizio" cssClass="form-control" disabled="true">
															<form:option value="">
																Esprimi un giudizio
															</form:option>
															<c:if test="${ professionistaEsternoView.listaGiudizio != null }">
																<c:forEach items="${professionistaEsternoView.listaGiudizio}" var="oggetto">
																	<form:option value="${ oggetto.giudizioVal }">
																		<c:out value="${oggetto.giudizioDesc}"></c:out>
																	</form:option>
																</c:forEach>
															</c:if>
														</form:select>
													</div>
												</div>
											</div>
										</div>
											
										<!-- CATEGORIA CONTEST -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="fax" class="col-sm-2 control-label"><spring:message
															text="??professionistaEsterno.label.contest??" 
															code="professionistaEsterno.label.contest" /></label>
													<div class="col-sm-10">
														<form:input path="categoriaContest" cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
										
										<!-- VOTAZIONI -->
										<div class="list-group-item media" >
											<div class="media-body">
												<div class="form-group" >
													
													<label for="fax" class="col-sm-2 control-label">Votazioni</label>
													<div class="col-sm-10" >
														<img id="visProfEstVotazioniGifLoading" src="<%=request.getContextPath()%>/vendors/jquery/loading.gif" width="32" height="32" class="hidden">
														<button type="button" class="btn btn-primary hidden" style="background-color:orange !important;" id="btnVotazioniApri" >Apri</button>
													</div>
												
												</div>
											</div>
										</div>
										<!-- VOTAZIONI TABELLE SEMESTRALI -->
										<div class="list-group-item media" >
											<div class="media-body">
												<div class="form-group" id="visProfEstVotazioni">
												
												</div>
											</div>
										</div>

										<div class="initiallyHidden" id="divDocument" >
											<div class="list-group-item media " >
												<div class="media-body">
													<div class="form-group">
														<label for="document" id="documentLabel" class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.documenti??" code="professionistaEsterno.label.documenti" /></label>
														<div class="col-sm-10">
																<div id="firstDocument">
																</div>
														</div>
													</div>
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
		</section>

	</section>
<form id="downloadProf" action="./download.action" method="get" style="display:none">	
<engsecurity:token regenerate="false"/>
<input type="hidden" name="uuid" id="uuid" value="">
<input type="submit" value="" style="display:none">
</form>
	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>
	
	<script charset="UTF-8" type="text/javascript">
		<c:if test="${ empty professionistaEsternoView.jsonArrayProfessionistaEsterno }">
			var jsonArrayProfessionistaEsterno = '';
		</c:if>
	
		<c:if test="${ not empty professionistaEsternoView.jsonArrayProfessionistaEsterno }">
			var jsonArrayProfessionistaEsterno = JSON.parse('${professionistaEsternoView.jsonArrayProfessionistaEsterno}');
		</c:if>
	</script>

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/professionistaEsterno.js"></script>
		
	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/vendormanagement/visualizza.js"></script>
	
										
</body>
</html>
