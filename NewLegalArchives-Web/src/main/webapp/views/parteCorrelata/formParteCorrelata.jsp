<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
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
								<c:if
									test="${ parteCorrelataView.vo == null || parteCorrelataView.vo.id == 0 }">
									<h2> 
										<spring:message text="??parteCorrelata.label.creaParteCorrelata??"
											code="parteCorrelata.label.creaParteCorrelata" />
									</h2>
								</c:if>
								<c:if
									test="${ parteCorrelataView.vo != null && parteCorrelataView.vo.id > 0 }">
									<h2>
										<spring:message text="??parteCorrelata.label.modificaParteCorrelata??"
											code="parteCorrelata.label.modificaParteCorrelata" />
									</h2>
								</c:if>
							</div>
							
							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form  name="parteCorrelataForm" method="post" modelAttribute="parteCorrelataView" 
											action="salva.action" class="form-horizontal la-form">
									<engsecurity:token regenerate="false"/> 
											
									<!-- gestione del messaggio di ritorno -->
									<c:if test = "${successMsg != null }">
										<div class="alert alert-success">${successMsg}</div>
									</c:if>
									
									<c:if test = "${successMsg == null }">
										<form:errors path="*" cssClass="alert alert-danger" element="div"></form:errors>
									</c:if>
	
									<form:hidden path="parteCorrelataId" value="${parteCorrelataView.vo.id}"/>
									<form:hidden path="op" id="op" value="salvaParteCorrelata"/>
									
									<!-- DENOMINAZIONE --> 	
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="denominazione" class="col-sm-2 control-label"><spring:message
															text="??parteCorrelata.label.denominazione??"
															code="parteCorrelata.label.denominazione" /></label>
													<div class="col-sm-10">
														 <form:input path="denominazione" cssClass="form-control" 
														 value="${parteCorrelataView.getVo().denominazione}"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<!-- CODICE FISCALE -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="codFiscale" class="col-sm-2 control-label"><spring:message
															text="??parteCorrelata.label.codFiscale??"
															code="parteCorrelata.label.codFiscale" /></label>
													<div class="col-sm-10">
														 <form:input path="codFiscale" cssClass="form-control" 
														 			 value="${parteCorrelataView.getVo().codFiscale}"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<!-- PARTITA IVA -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="partitaIva" class="col-sm-2 control-label"><spring:message
															text="??parteCorrelata.label.partitaIva??"
															code="parteCorrelata.label.partitaIva" /></label>
													<div class="col-sm-10">
														 <form:input path="partitaIva" cssClass="form-control" 
														 			 value="${parteCorrelataView.getVo().partitaIva}"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<!-- NAZIONE -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nazione" class="col-sm-2 control-label"><spring:message
															text="??parteCorrelata.label.nazione??"
															code="parteCorrelata.label.nazione" /></label>
													<div class="col-sm-10"> 
														<form:select path="nazioneCode" cssClass="form-control">
															<form:option value="">
																<spring:message
																	text="??parteCorrelata.label.selezionaNazione??"
																	code="parteCorrelata.label.selezionaNazione" />
															</form:option>
															<c:if test="${ parteCorrelataView.listaNazioni != null }">
																<c:forEach items="${parteCorrelataView.listaNazioni}" var="oggetto">
																	<c:choose>
																		<c:when test="${oggetto.vo.codGruppoLingua eq parteCorrelataView.getVo().nazione.codGruppoLingua}">
																			<form:option value="${oggetto.vo.codGruppoLingua }" selected="true">
																				<c:out value="${oggetto.vo.descrizione}"></c:out>
																			</form:option>
																		</c:when>
																		<c:otherwise>
																			<form:option value="${oggetto.vo.codGruppoLingua }">
																				<c:out value="${oggetto.vo.descrizione}"></c:out>
																			</form:option>																		
																		</c:otherwise>
																	</c:choose>
																</c:forEach>																
															</c:if>
														</form:select> 
													</div>
												</div>
											</div>
										</div> 
									</div>

									<!-- TIPO CORRELAZIONE -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="tipoCorrelazione" class="col-sm-2 control-label"><spring:message
															text="??parteCorrelata.label.tipoCorrelazione??"
															code="parteCorrelata.label.tipoCorrelazione"/></label>
													<div class="col-sm-10"> 
														<form:select id="comboTipoCorrelazione" path="tipoCorrelazioneCode" 
																	 onchange="selezionaTipoCorrelazione(this.value)" 
																	 cssClass="form-control">
															<form:option value="">
																<spring:message
																	text="??parteCorrelata.label.selezionaTipoCorrelazione??"
																	code="parteCorrelata.label.selezionaTipoCorrelazione" /></form:option>
															<c:if test="${ parteCorrelataView.listaTipoCorrelazione != null }">
																<c:forEach items="${parteCorrelataView.listaTipoCorrelazione}" var="oggetto">
																	<c:choose>
																		<c:when test="${oggetto.vo.codGruppoLingua eq parteCorrelataView.getVo().tipoCorrelazione.codGruppoLingua}">
																			<form:option value="${oggetto.vo.codGruppoLingua }" selected="true">
																				<c:out value="${oggetto.vo.descrizione}"></c:out>
																			</form:option>
																		</c:when>
																		<c:otherwise>
																			<form:option value="${oggetto.vo.codGruppoLingua }">
																				<c:out value="${oggetto.vo.descrizione}"></c:out>
																			</form:option>																		
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</c:if>															 
														</form:select> 
													</div>
												</div>
											</div>
										</div> 
									 
									 	<!-- FAMILIARE -->
									 	<div class="list-group lg-alt" id="divFamiliareInput" style="display: none">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="familiare" class="col-sm-2 control-label"><spring:message
																text="??parteCorrelata.label.familiare??"
																code="parteCorrelata.label.familiare" /></label>
														<div class="col-sm-10">
															 <form:input path="familiare" cssClass="form-control" 
															 			 value="${parteCorrelataView.getVo().familiare}"/>
														</div>
													</div>
												</div>
											</div>
										</div>

									 	<!-- RAPPORTO -->
									 	<div class="list-group lg-alt" id="divRapportoInput" style="display: none">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="rapporto" class="col-sm-2 control-label"><spring:message
																text="??parteCorrelata.label.rapporto??"
																code="parteCorrelata.label.rapporto" /></label>
														<div class="col-sm-10">
															 <form:input path="rapporto" cssClass="form-control" 
															 			 value="${parteCorrelataView.getVo().rapporto}"/>
														</div>
													</div>
												</div>
											</div>
										</div>

									 	<!-- CONSIGLIERI SINDACI -->
									 	<div class="list-group lg-alt" id="divConsiglieriSindaciInput" style="display: none">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="consiglieriSindaci" class="col-sm-2 control-label"><spring:message
																text="??parteCorrelata.label.consiglieriSindaci??"
																code="parteCorrelata.label.consiglieriSindaci" /></label>
														<div class="col-sm-10">
															 <form:input path="consiglieriSindaci" cssClass="form-control" 
															 			 value="${parteCorrelataView.getVo().consiglieriSindaci}"/>
														</div>
													</div>
												</div>
											</div>
										</div>					 
									</div>
								</form:form>
								<button form="parteCorrelataForm" onclick="salvaParteCorrelata()"
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
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>
	
	<jsp:include page="/parts/script-end.jsp"></jsp:include>
	<!-- si carica il js -->
	<script src="<%=request.getContextPath()%>/portal/js/controller/parteCorrelata.js"></script>
	<!-- esegue il js per impostazione campi per combo tipoCorrelaione impostata -->
	<script type="text/javascript">
		selezionaTipoCorrelazione("${parteCorrelataView.getVo().tipoCorrelazione.codGruppoLingua}");
	</script>
	
</body>

</html>
