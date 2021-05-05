<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
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
									<spring:message text="??societa.label.gestione??"
										code="societa.label.gestione" />
								</h2>
							</div>
							<div class="card-body">

								<form:form name="societaEditForm" method="post"
									modelAttribute="societaView" action="salva.action"
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
									<form:hidden path="op" id="op" value="salvaNazione" />
									<form:hidden path="editMode" id="editMode" value="true" />
									<form:hidden path="insertMode" id="insertMode" value="false" />
									<form:hidden path="deleteMode" id="deleteMode" value="false" />
									<form:hidden path="countEmailInsert" id="countEmailInsert" value="0" />
									<form:hidden path="countEmailEdit" id="countEmailEdit" value="0" />
										
						<div class="tab-content p-20">
									<fieldset class="scheduler-border">

										<legend class="scheduler-border">
											<spring:message text="??societa.label.modifica??"
												code="societa.label.modifica" />
										</legend>
										
										<!-- LISTA SOCIETA -->
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">

														
														<label for="idSocieta" class="col-sm-2 control-label">
															<spring:message text="??societa.label.lista??"
																code="societa.label.lista" />
														</label>
														<div class="col-sm-10">

															<form:select size="1" path="idSocieta"
																onchange="caricaDettaglioSocieta(this.value)"
																onfocus="editCheck()"
																cssClass="form-control">
																		<form:option value="0">
																			<c:out value="Nessuna selezione ..."></c:out>
																		</form:option>
																<c:if test="${ societaView.listaSocieta != null }">
																	<c:forEach
																		items="${societaView.listaSocieta}"
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

										<!-- NOME -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??societa.label.nome??"
															code="societa.label.nome" /></label>
													<div class="col-sm-10">
														<form:input 
															path="nome"
															cssClass="form-control"
															onfocus="editCheck()"
														/>
													</div>
												</div>
											</div>
										</div>

										<!-- RAGIONE SOCIALE -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="ragioneSociale" class="col-sm-2 control-label"><spring:message
															text="??societa.label.ragioneSociale??"
															code="societa.label.ragioneSociale" /></label>
													<div class="col-sm-10">
														<form:input 
															path="ragioneSociale"
															cssClass="form-control"
															onfocus="editCheck()"
														/>
													</div>
												</div>
											</div>
										</div>

										<!-- TIPO SOCIETA -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="idTipoSocieta" class="col-sm-2 control-label"><spring:message
															text="??societa.label.tipoSocieta??"
															code="societa.label.tipoSocieta" /></label>
													<div class="col-sm-10">
														
														<form:select size="1" path="idTipoSocieta"
																onchange=""
																onfocus="editCheck()"
																cssClass="form-control">
																		<form:option value="0">
																			<c:out value="Nessuna selezione ..."></c:out>
																		</form:option>
																<c:if test="${ societaView.listaTipoSocieta != null }">
																	<c:forEach
																		items="${societaView.listaTipoSocieta}"
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
										
										<!-- EMAIL AMMINISTRAZIONE -->
										<div id="divEmailEdit">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
												
													<label for="emailAmministrazione" class="col-sm-2 control-label"><spring:message
															text="??societa.label.emailAmministrazione??"
															code="societa.label.emailAmministrazione" /></label>
													<div class="col-sm-9">
														<form:input 
															path="emailAmministrazione[0]"
															cssClass="form-control"
															onfocus="editCheck()"
														/>
													</div>
													<button type="button" onclick="addEmailEdit()" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">
																<span class="glyphicon glyphicon-plus"></span>
       												</button>
												</div>
											</div>
										</div>
										</div>
										
										<!-- INDIRIZZO -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="indirizzo" class="col-sm-2 control-label"><spring:message
															text="??societa.label.indirizzo??"
															code="societa.label.indirizzo" /></label>
													<div class="col-sm-10">
													
															
														<form:input 
															path="indirizzo"
															cssClass="form-control"
															onfocus="editCheck()"
														/>
													</div>
												</div>
											</div>
										</div>
									
										<!-- CAP -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="cap" class="col-sm-2 control-label"><spring:message
															text="??societa.label.cap??"
															code="societa.label.cap" /></label>
													<div class="col-sm-10">
														<form:input 
															path="cap"
															cssClass="form-control"
															onfocus="editCheck()"
														/>
													</div>
												</div>
											</div>
										</div>
										
										<!-- CITTA -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="citta" class="col-sm-2 control-label"><spring:message
															text="??societa.label.citta??"
															code="societa.label.citta" /></label>
													<div class="col-sm-10">
														<form:input 
															path="citta"
															cssClass="form-control"
															onfocus="editCheck()"
														/>
													</div>
												</div>
											</div>
										</div>
										
										<!-- NAZIONE -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nazione" class="col-sm-2 control-label"><spring:message
															text="??societa.label.nazione??"
															code="societa.label.nazione" /></label>
													<div class="col-sm-10">
														
															<form:select size="1" path="nazioneCode"
																onchange=""
																onfocus="editCheck()"
																cssClass="form-control">
																		<form:option value="0">
																			<c:out value="Nessuna selezione ..."></c:out>
																		</form:option>
																
																<c:if test="${ societaView.listaNazioni != null }">
																	<c:forEach
																		items="${societaView.listaNazioni}"
																		var="oggetto">
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
									
									
									</fieldset>
									<fieldset class="scheduler-border">
										<legend class="scheduler-border">
											<spring:message text="??societa.label.inserimento??"
												code="societa.label.inserimento" />
										</legend>


										<!-- NOME -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nomeIns" 
														class="col-sm-2 control-label"><spring:message
															text="??societa.label.nome??"
															code="societa.label.nome" /></label>
													<div class="col-sm-10">
														<form:input 
															path="nomeIns"
															cssClass="form-control"
															onfocus="insertCheck()"
														/>
													</div>
												</div>
											</div>
										</div>

										<!-- RAGIONE SOCIALE -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="ragioneSocialeIns"
														 class="col-sm-2 control-label"><spring:message
															text="??societa.label.ragioneSociale??"
															code="societa.label.ragioneSociale" /></label>
													<div class="col-sm-10">
														<form:input 
															path="ragioneSocialeIns"
															cssClass="form-control"
															onfocus="insertCheck()"
														/>
													</div>
												</div>
											</div>
										</div>

										<!-- TIPO SOCIETA -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="idTipoSocietaIns" 
															class="col-sm-2 control-label"><spring:message
															text="??societa.label.tipoSocieta??"
															code="societa.label.tipoSocieta" /></label>
													<div class="col-sm-10">
														
														<form:select size="1" 
															path="idTipoSocietaIns"
																onchange=""
																onfocus="insertCheck()"
																cssClass="form-control">
																	<form:option value="0">
																			<c:out value="Nessuna selezione ..."></c:out>
																		</form:option>
																<c:if test="${ societaView.listaTipoSocieta != null }">
																	<c:forEach
																		items="${societaView.listaTipoSocieta}"
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
										
										<!-- EMAIL AMMINISTRAZIONE -->
										<div id="divEmailInsert">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="emailAmministrazioneIns" class="col-sm-2 control-label"><spring:message
															text="??societa.label.emailAmministrazione??"
															code="societa.label.emailAmministrazione" /></label>
													<div class="col-sm-9">
														<form:input 
															path="emailAmministrazioneIns[0]" 
															cssClass="form-control"
															onfocus="insertCheck()"
														/>
													</div>
													<button type="button" onclick="addEmailInsert()" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">
														<span class="glyphicon glyphicon-plus"></span>
       												</button>
												</div>
											</div>
										</div>
										</div>
										
										<!-- INDIRIZZO -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="indirizzoIns" 
															class="col-sm-2 control-label"><spring:message
															text="??societa.label.indirizzo??"
															code="societa.label.indirizzo" /></label>
													<div class="col-sm-10">
														<form:input 
															path="indirizzoIns"
															cssClass="form-control"
															onfocus="insertCheck()"
														/>
													</div>
												</div>
											</div>
										</div>
									
										<!-- CAP -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="capIns" 
															class="col-sm-2 control-label"><spring:message
															text="??societa.label.cap??"
															code="societa.label.cap" /></label>
													<div class="col-sm-10">
														<form:input 
															path="capIns"
															cssClass="form-control"
															onfocus="insertCheck()"
														/>
													</div>
												</div>
											</div>
										</div>
										
										<!-- CITTA -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="cittaIns" 
															class="col-sm-2 control-label"><spring:message
															text="??societa.label.citta??"
															code="societa.label.citta" /></label>
													<div class="col-sm-10">
														<form:input 
															path="cittaIns"
															cssClass="form-control"
															onfocus="insertCheck()"
														/>
													</div>
												</div>
											</div>
										</div>
										
										<!-- NAZIONE -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nazioneCodeIns" 
															class="col-sm-2 control-label"><spring:message
															text="??societa.label.nazione??"
															code="societa.label.nazione" /></label>
													<div class="col-sm-10">
														
															<form:select 
																size="1" 
																path="nazioneCodeIns"
																onchange=""
																onfocus="insertCheck()"
																cssClass="form-control">
																	<form:option value="0">
																			<c:out value="Nessuna selezione ..."></c:out>
																		</form:option>
																<c:if test="${ societaView.listaNazioni != null }">
																	<c:forEach
																		items="${societaView.listaNazioni}"
																		var="oggetto">
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

									</fieldset>
					   </div> <!-- END tab -->


								</form:form>

								
								<button form="societaEditForm" onclick="cancellaSocieta()"
									class="btn palette-Green-SNAM bg btn-float2 waves-effect waves-circle waves-float">
									<i class="zmdi zmdi-delete"></i>
								</button>
								<button form="societaEditForm" onclick="salvaSocieta()"
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
		src="<%=request.getContextPath()%>/portal/js/controller/societa.js"></script>
</body>
</html>
