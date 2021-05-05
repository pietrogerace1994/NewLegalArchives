<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring" %>
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
  										<spring:message text="??societa.label.visualizza??"  code="societa.label.visualizza" />  
									</h2>
								
							</div>
							<div class="card-body">

								<form:form name="societaReadForm" method="post"
									modelAttribute="societaView" action="salva.action"
									class="form-horizontal la-form">

                                   
									<engsecurity:token regenerate="false"/>
									
									<div class="tab-content p-20">

										<!-- LISTA SOCIETA -->
										<div class="list-group lg-alt">

											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="idSocieta"
															class="col-sm-2 control-label"><spring:message
																text="??societa.label.lista??"
																code="societa.label.lista" /></label>
														<div class="col-sm-10">
														
														  
															<form:select size="1" path="idSocieta"
																onchange="caricaDettaglioSocietaVisualizzazione(this.value)"
																onfocus=""
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
															cssClass="form-control" readonly="true"
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
															cssClass="form-control" readonly="true"
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
																onfocus=""
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
										<div id="divEmail">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="emailAmministrazione" class="col-sm-2 control-label"><spring:message
															text="??societa.label.emailAmministrazione??"
															code="societa.label.emailAmministrazione" /></label>
													<div class="col-sm-10">
														<form:input 
															path="emailAmministrazione[0]"
															cssClass="form-control" readonly="true"
														/>
													</div>
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
															cssClass="form-control" readonly="true"
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
															cssClass="form-control" readonly="true"
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
															cssClass="form-control" readonly="true"
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
																onfocus=""
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
									
									</div>
									
								</form:form>

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
