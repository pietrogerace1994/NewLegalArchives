<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
										<spring:message text="??professionistaEsterno.label.inserimento??"
											code="professionistaEsterno.label.inserimento" />
									</h2>
								
							</div>
							<div class="card-body">

								<form:form name="professionistaEsternoForm" method="post"
									modelAttribute="professionistaEsternoView" action="salva.action"
									class="form-horizontal la-form" enctype="multipart/form-data" >

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
									<form:hidden path="professionistaEsternoId" />
									<form:hidden path="op" id="op" value="salvaProfessionistaEsterno" />
									<form:hidden path="countEmail" id="countEmail" value="0" />
									<form:hidden path="editMode" id="editMode" value="false" />
									<form:hidden path="deleteMode" id="deleteMode" value="false" />
									<form:hidden path="insertMode" id="insertMode" value="true" />
									
									<div class="tab-content p-20">

										<div class="list-group lg-alt">

											<!-- NOME -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="nome" class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.nome??" code="professionistaEsterno.label.nome" /></label>
														<div class="col-sm-10">
															<form:input path="nome" cssClass="form-control" />
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
															<form:input path="cognome" cssClass="form-control" />
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
															<form:input path="codiceFiscale" cssClass="form-control" />
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
															<form:input path="telefono" cssClass="form-control" />
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
															<form:input path="fax" cssClass="form-control" />
														</div>
													</div>
												</div>
											</div>
											
											<!-- EMAIL -->
											<div id="divEmail">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label for="email" class="col-sm-2 control-label"><spring:message
																	text="??professionistaEsterno.label.email??" code="professionistaEsterno.label.email" /></label>
															<div class="col-sm-9">
																<form:input path="email[0]" cssClass="form-control" />
															</div>
															<button type="button" onclick="addEmail()" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">
																<span class="glyphicon glyphicon-plus"></span>
       														</button>
														</div>
													</div>
												</div>
											</div>
											
											<!-- NAZIONE-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="nazione" class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.nazione??" code="professionistaEsterno.label.nazione" /></label>
														<div class="col-sm-10">
															<div class="table-responsive" style="clear:both;">
																<table class="table table-striped table-responsive" >
																	<thead>
																		<tr style="border:1px solid #e0e0e0">
																			<th data-column-id="01" style="width:50px"> 
																				<button  type="button"  data-toggle="collapse" data-target="#boxNazione" 
																					class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"  style="float: left;position: relative !important;">
																					<i class="zmdi zmdi-collection-text icon-mini"></i>
																				</button>
																			</th> 
																			<th data-column-id="id"><spring:message
																					text="??professionistaEsterno.label.nazione??"
																					code="professionistaEsterno.label.nazione" /></th>
																			
																		</tr>
																	</thead>
																	<tbody id="boxNazione" class="collapse ">
																		<c:if test="${ professionistaEsternoView.listaNazioni != null }">
																			<c:forEach items="${professionistaEsternoView.listaNazioni}" var="oggetto">
																			    <tr>
																					<td>
																						<form:checkbox value="${ oggetto.vo.codGruppoLingua }"
																							path="nazioniAggiunte"></form:checkbox>
																					</td>	
																					<td>
																						${oggetto.vo.descrizione}
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
											
											<!-- SPECIALIZZAZIONE-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="specializzazione" class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.specializzazione??" code="professionistaEsterno.label.specializzazione" /></label>
														<div class="col-sm-10">
															<div class="table-responsive" style="clear:both;">
																<table class="table table-striped table-responsive" >
																	<thead>
																		<tr style="border:1px solid #e0e0e0">
																			<th data-column-id="01" style="width:50px"> 
																				<button  type="button"  data-toggle="collapse" data-target="#boxSpecializzazione" 
																					class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"  style="float: left;position: relative !important;">
																					<i class="zmdi zmdi-collection-text icon-mini"></i>
																				</button>
																			</th> 
																			<th data-column-id="id"><spring:message
																					text="??professionistaEsterno.label.specializzazione??"
																					code="professionistaEsterno.label.specializzazione" /></th>
																			
																		</tr>
																	</thead>
																	<tbody id="boxSpecializzazione" class="collapse ">
																		<c:if test="${ professionistaEsternoView.listaSpecializzazioni != null }">
																			<c:forEach items="${professionistaEsternoView.listaSpecializzazioni}" var="oggetto">
																			    <tr>
																					<td>
																						<form:checkbox value="${ oggetto.vo.codGruppoLingua }"
																							path="specializzazioniAggiunte"></form:checkbox>
																					</td>	
																					<td>
																						${oggetto.vo.descrizione}
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
											
											<!-- TIPO PROFESSIONISTA -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="tipoProfessionistaCode" class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.tipoProfessionista??" code="professionistaEsterno.label.tipoProfessionista" /></label>
														<div class="col-sm-10">
															<form:select id="tipoProfessionistaCode" path="tipoProfessionistaCode"
																cssClass="form-control">
																<form:option value="">
																	<spring:message text="??professionistaEsterno.label.selezionaTipoProfessionista??"
																		code="professionistaEsterno.label.selezionaTipoProfessionista" />
																</form:option>
																<c:if test="${ professionistaEsternoView.listaTipoProfessionista != null }">
																	<c:forEach items="${professionistaEsternoView.listaTipoProfessionista}" var="oggetto">
																		<form:option value="${ oggetto.vo.codGruppoLingua }">
																			<c:out value="${oggetto.vo.descrizione}"></c:out>
																		</form:option>
																	</c:forEach>
																</c:if>
										
															</form:select>
														</div>
													</div>
												</div>
											</div>
											
											<!-- STATO ESITO VALIDAZIONE -->
											<!-- 
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="statoEsitoValutazioneProfCode" class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.statoEsitoValutazioneProf??" code="professionistaEsterno.label.statoEsitoValutazioneProf" /></label>
														<div class="col-sm-10">
															<form:select id="statoEsitoValutazioneProfCode" path="statoEsitoValutazioneProfCode"
																cssClass="form-control">
																<form:option value="">
																	<spring:message text="??professionistaEsterno.label.selezionaStatoEsitoValutazioneProf??"
																		code="professionistaEsterno.label.selezionaStatoEsitoValutazioneProf" />
																</form:option>
																<c:if test="${ professionistaEsternoView.listaStatoEsitoValutazioneProf != null }">
																	<c:forEach items="${professionistaEsternoView.listaStatoEsitoValutazioneProf}" var="oggetto">
																		<form:option value="${ oggetto.vo.codGruppoLingua }">
																			<c:out value="${oggetto.vo.descrizione}"></c:out>
																		</form:option>
																	</c:forEach>
																</c:if>
															</form:select>
														</div>
													</div>
												</div>
											</div>
											-->
											
											
											<div class="list-group-item media">
												<div class="media-body">
											 		<div class="form-group">
														<label for="uploadFile" class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.uploadFile??" code="professionistaEsterno.label.uploadFile" /></label>
														<div class="col-sm-10">														
															<c:if test="${ not empty professionistaEsternoView.fileProfEst}">
																${ professionistaEsternoView.fileProfEst.name }
																<button type="button" onclick="rimuoviAllegatoProfEst()"
																class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																style="float: right; position: relative !important;">
																<i class="fa fa-trash icon-mini"></i>
																</button>
															</c:if>
															<c:if test="${ empty professionistaEsternoView.fileProfEst}">															
															     <input type="file" name="fileProfEst" id="fileProfEst" />
															</c:if>
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
															<form:textarea path="motivazioneRichiesta" cssClass="form-control" cols="50" rows="3"/>
														</div>
													</div>
												</div>
											</div>
											
											<!-- STUDIO LEGALE -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="tipoStudioLegale" class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.inserimentoStudioLegale??" code="professionistaEsterno.label.inserimentoStudioLegale" /></label>
														<div class="col-sm-10">
														
															<label for="tipoStudioLegaleNuovo" class="col-sm-1 control-label">
																<spring:message	text="??professionistaEsterno.label.studioLegale.nuovo??" 
																code="professionistaEsterno.label.studioLegale.nuovo"/>
															</label> 
															<div class="col-sm-1 control-label">
																<form:radiobutton path="tipoStudioLegale" value="1" onclick="selectRadio(this.value)" />
															</div>
															
															<label for="tipoStudioLegaleEsistente" class="col-sm-1 control-label">
																<spring:message	text="??professionistaEsterno.label.studioLegale.esistente??" 
																code="professionistaEsterno.label.studioLegale.esistente" />
															</label> 
															<div class="col-sm-1 control-label">
																<form:radiobutton path="tipoStudioLegale" value="0" onclick="selectRadio(this.value)"/>
															</div>
														</div>
													</div>
												</div>
											</div>
		
											<!-- STUDIO LEGALE ESISTENTE -->
											<div class="list-group-item media initiallyHidden" id="studioLegaleEsistente" >
												<div class="media-body">
													<div class="form-group">
														<label for="studioLegaleId" class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.studioLegale??" code="professionistaEsterno.label.studioLegale" /></label>
														<div class="col-sm-10">
															<form:select id="studioLegaleId" path="studioLegaleId"
																cssClass="form-control">
																<form:option value="">
																	<spring:message text="??professionistaEsterno.label.selezionaStudioLegale??"
																		code="professionistaEsterno.label.selezionaStudioLegale" />
																</form:option>
																<c:if test="${ professionistaEsternoView.listaStudioLegale != null }">
																	<c:forEach items="${professionistaEsternoView.listaStudioLegale}" var="oggetto">
																		<form:option value="${ oggetto.vo.id }">
																			<c:out value="${oggetto.vo.denominazione} - ${oggetto.vo.indirizzo} ${oggetto.vo.cap} ${oggetto.vo.citta}"></c:out>
																		</form:option>
																	</c:forEach>
																</c:if>
															</form:select>
														</div>
													</div>
												</div>
											</div>
											
											<!-- NUOVO STUDIO LEGALE -->
											<div id="boxContainerStudioLegale" class="list-group-item media hidden">
												<div class="media-body">
													<div class="form-group">
														<label for="tipoStudioLegale" class="col-sm-2 control-label"></label>
														<div class="col-sm-10">
															<jsp:include page="nuovoStudioLegale.jsp">
																<jsp:param name="initialClass" value="initiallyHidden" />
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
																									
															<c:if test="${ professionistaEsternoView.vendorManagement != null }">
																<form:select id="giudizio" path="giudizio"
																	cssClass="form-control">
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
															</c:if>
															<c:if test="${ professionistaEsternoView.vendorManagement == null }">
																<form:select id="giudizio" path="giudizio"
																	cssClass="form-control" disabled="true">
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
															</c:if>
														
														</div>
													</div>
												</div>
											</div>
											
											<!-- CATEGORIA CONTEST -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="categoriaContestCode" class="col-sm-2 control-label"><spring:message
																text="??professionistaEsterno.label.contest??" code="professionistaEsterno.label.contest" /></label>
														<div class="col-sm-10">
															<form:select id="categoriaContestCode" path="categoriaContestCode"
																cssClass="form-control">
																<form:option value="">
																	<spring:message text="??professionistaEsterno.label.selezionaPartecipazioneContest??"
																		code="professionistaEsterno.label.selezionaPartecipazioneContest" />
																</form:option>
																<c:if test="${ professionistaEsternoView.listaCategoriaContest != null }">
																	<c:forEach items="${professionistaEsternoView.listaCategoriaContest}" var="oggetto">
																		<form:option value="${ oggetto.vo.codGruppoLingua }">
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
									</div>
								</form:form>
								<button form="professionistaEsternoForm" onclick="salvaProfessionistaEsterno()"
									class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float">
									<i class="zmdi zmdi-save"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>

	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>
	<script
		src="<%=request.getContextPath()%>/portal/js/controller/professionistaEsterno.js"></script>
		
		<c:if test="${professionistaEsternoView.email != null && fn:length(professionistaEsternoView.email) > 1 }">
		<c:set var="emailToAdd" value='${professionistaEsternoView.email}' scope="page"/>
		<c:set var="sizeEmail" value='${fn:length(professionistaEsternoView.email)}' scope="page"/>
			<script>
		        addEmailForm("${sizeEmail}", "${emailToAdd}");
		    </script>
		</c:if>
</body>

</html>

