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

	<jsp:include page="/parts/script-init.jsp"></jsp:include>
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
										<div class="alert alert-info">${successMsg}</div>
									</c:if>
									<c:if test = "${successMsg == null }">
										<form:errors path="*" cssClass="alert alert-danger" element="div"></form:errors>
									</c:if>
	
									<form:hidden path="parteCorrelataId"/>
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
														 <form:input path="denominazione" cssClass="form-control"/>
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
														 <form:input path="codFiscale" cssClass="form-control"/>
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
														 <form:input path="partitaIva" cssClass="form-control"/>
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
														<form:select id="comboNazione" path="nazioneCode" cssClass="form-control">
															<form:option value=""><spring:message
																	text="??parteCorrelata.label.selezionaNazione??"
																	code="parteCorrelata.label.selezionaNazione" /></form:option>
															<c:if test="${ parteCorrelataView.listaNazioni != null }">
																<c:forEach items="${parteCorrelataView.listaNazioni}"
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
															<form:option value=""><spring:message
																	text="??parteCorrelata.label.selezionaTipoCorrelazione??"
																	code="parteCorrelata.label.selezionaTipoCorrelazione" /></form:option>
															<c:if test="${ parteCorrelataView.listaTipoCorrelazione != null }">
																<c:forEach items="${parteCorrelataView.listaTipoCorrelazione}"
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
									 
									 	<!-- FAMILIARE -->
									 	<div class="list-group lg-alt" id="familiareInput" style="display: none">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="familiare" class="col-sm-2 control-label"><spring:message
																text="??parteCorrelata.label.familiare??"
																code="parteCorrelata.label.familiare" /></label>
														<div class="col-sm-10">
															 <form:input path="familiare" cssClass="form-control"/>
														</div>
													</div>
												</div>
											</div>
										</div>

									 	<!-- RAPPORTO -->
									 	<div class="list-group lg-alt" id="rapportoInput" style="display: none">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="rapporto" class="col-sm-2 control-label"><spring:message
																text="??parteCorrelata.label.rapporto??"
																code="parteCorrelata.label.rapporto" /></label>
														<div class="col-sm-10">
															 <form:input path="rapporto" cssClass="form-control"/>
														</div>
													</div>
												</div>
											</div>
										</div>

									 	<!-- CONSIGLIERI SINDACI -->
									 	<div class="list-group lg-alt" id="ammSindaciInput" style="display: none">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="consiglieriSindaci" class="col-sm-2 control-label"><spring:message
																text="??parteCorrelata.label.consiglieriSindaci??"
																code="parteCorrelata.label.consiglieriSindaci" /></label>
														<div class="col-sm-10">
															 <form:input path="consiglieriSindaci" cssClass="form-control"/>
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
		</section>
			
		
		<!--  ESEMPIO DI TABELLA PAGINATA  -->
		<div class="container">
			<div class="row">
		      <div class="table-responsive">
		        <table class="table table-hover">
		          <thead>
		            <tr>
		              <th>#</th>
		              <th>Table heading</th>
		              <th>Table heading</th>
		              <th>Table heading</th>
		              <th>Table heading</th>
		              <th>Table heading</th>
		              <th>Table heading</th>
		            </tr>
		          </thead>
		          <tbody id="myTable">
		            <tr>
		              <td>1</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		            </tr>
		            <tr>
		              <td>2</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		            </tr>
		            <tr>
		              <td>3</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		            </tr>
		            <tr>
		              <td>4</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		            </tr>
		            <tr class="success">
		              <td>5</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		            </tr>
		            <tr>
		              <td>6</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		            </tr>
		            <tr>
		              <td>7</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		            </tr>
		             <tr>
		              <td>8</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		            </tr>
		            <tr>
		              <td>9</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		            </tr>
		            <tr>
		              <td>10</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		              <td>Table cell</td>
		            </tr>
		          </tbody>
		        </table>   
		      </div>
		      <div class="col-md-12 text-center">
		      <ul class="pagination pagination-lg pager" id="myPager"></ul>
		      </div>
			</div>
		</div>
		
		<div class="container">
			<div class="row">
				<div class="table-responsive">
					<table id="data-table-selection" class="table table-striped table-responsive">
                                <thead>
                                    <tr>
                                        <th data-column-id="id">Numero Fascicolo</th>
                                        <th data-column-id="01">Legale Esterno Incaricato</th>
                                        <th data-column-id="02">Controparte</th>
                                        <th data-column-id="03">Anno</th>
                                        <th data-column-id="04">Tipologia Atto</th>
                                        <th data-column-id="05">Tipo Azione</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>10010</td>
                                        <td>Giuseppe Gallo</td>
                                        <td>ACEA</td>
                                        <td>2016</td>
                                        <td>atto di chiamata in causa</td>
                                        <td>contratto/distribuzione</td>
                                    </tr>
                                    <tr>
                                        <td>110238</td>
                                        <td>Giuseppe Caia</td>
                                        <td>RM Elettroservice Sas</td>
                                        <td>2016</td>
                                        <td>Atto di pignoramento o presso terzi</td>
                                        <td>Esecuzione ignoramento</td>
                                    </tr>
                                    <tr>
                                        <td>10239</td>
                                        <td>Fabio Todarello </td>
                                        <td>Roma capitale</td>
                                        <td>2016</td>
                                        <td>-</td>
                                        <td>Obbligazione Fonti</td>
                                    </tr>
                                     <tr>
                                        <td>9999</td>
                                        <td>Fabio Todarello</td>
                                        <td>Natural Gas S.r.l, Eni SpA</td>
                                        <td>2016</td>
                                        <td>Ricorso per consulenza tecnica preventiva Ex ATR 696-BIS</td>
                                        <td>Procedimenti Cautelari</td>
                                    </tr>                                    
                                </tbody>
                            </table>
                        </div>
					</div>
				</div>
		
		<!-- ELENCO DELLE PARTI CORRELATE -->
		<section id="elenco">
			<div class=container>
			<div class="list-group-item media">
				<div class="media-body">
					<div class="form-group">
						<!--  
						<label for="listaPartiCorrelate" class="col-sm-2 control-label">
							<spring:message
								text="??parteCorrelata.label.partiCorrelate??"
								code="parteCorrelata.label.partiCorrelate" />
						</label>
						-->
						<div class="col-sm-20">
							<div class="container" style="clear:both;">
								  <h2>
								  	<spring:message text="??parteCorrelata.label.partiCorrelate??"
													code="parteCorrelata.label.partiCorrelate" />
								  </h2>												  
								  <table class="table table-striped">
								    <thead>
								      <tr>
								        <th>Denominazione</th>
								        <th>Codice Fiscale</th>
								        <th>Partita Iva</th>
								        <th>Nazione</th>
								        <th>Tipo correlazione</th>
								        <th>Rapporto</th>
								        <th>Familiare</th>
								        <th>Consiglieri sindaci</th>
								        <th>Data Inserimento</th>
								      </tr>
								    </thead>
								    <tbody>
								      <tr>
								        <td>Società A</td>
								        <th>-</th>
								        <td>0001234567</td>
								        <td>Italia</td>
								        <th>Parte correlata</th>
								        <th>Rapporto1</th>
								        <th>-</th>
								        <th>-</th>
								        <th>14/07/2016</th>
								      </tr>
								      <tr>
								        <td>Società B</td>
								        <th>-</th>
								        <td>0001234569</td>
								        <td>Italia</td>
								        <th>Dichiaranti e loro stretti familiari</th>
								        <th>-</th>
								        <th>Parente</th>
								        <th>-</th>
								        <th>14/07/2016</th>
								      </tr>
								      <tr>
								        <td>Società C</td>
								        <th>-</th>
								        <td>0001234569</td>
								        <td>Italia</td>
								        <th>Soggetti d'interesse</th>
								        <th>-</th>
								        <th>-</th>
								        <th>Consigliere1</th>
								        <th>14/07/2016</th>
								      </tr>
								    </tbody>
								  </table>
								</div>
							</div>			
						</div>
					</div>
				<!-- FINE ELENCO PARTI CORRELATE -->
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


	<script src="<%=request.getContextPath()%>/portal/js/controller/parteCorrelata.js"></script>
</body>
</html>
