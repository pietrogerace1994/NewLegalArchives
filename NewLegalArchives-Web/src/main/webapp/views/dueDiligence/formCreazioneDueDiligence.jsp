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
	
	<style>
		.disabled {
		    pointer-events: none;
		    cursor: not-allowed;
		}
	</style>
</head>

<body data-ma-header="teal">

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
									<h2>
										<spring:message text="??duediligence.label.gestione??"
											code="duediligence.label.gestione" />
									</h2>
							</div>
							
							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form  name="duediligenceForm" method="post" modelAttribute="dueDiligenceView" 
											action="salva.action" class="form-horizontal la-form" enctype="multipart/form-data" >
											
									<engsecurity:token regenerate="false"/>
									
									<form:errors path="*" cssClass="alert alert-danger" element="div"></form:errors>
										
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
									
									<form:hidden path="dueDiligenceId"  id="dueDiligenceId" value="${dueDiligenceView.vo.id}"/>
									<c:if test="${dueDiligenceView.tabAttiva eq '1' }">
										<c:set var="tab1StyleAttiva" scope="page" value="active" />
										<form:hidden path="insertMode" id="insertMode" value="true" />
										<form:hidden path="editMode"   id="editMode"   value="false" />
										<form:hidden path="deleteMode" id="deleteMode" value="false" />
									</c:if>
									
									<c:if test="${dueDiligenceView.tabAttiva eq '2' }">
										<c:set var="tab2StyleAttiva" scope="page" value="active" />
										<form:hidden path="insertMode" id="insertMode" value="false" />
										<form:hidden path="editMode"   id="editMode"   value="true" />
										<form:hidden path="deleteMode" id="deleteMode" value="false" />
									</c:if>
									
									<form:hidden path="op" id="op" value="salvaDueDiligence"/>
									<form:hidden path="tabAttiva"  id="tabAttiva" />
									
									<ul class="tab-nav tn-justified tn-icon" role="tablist">
										<li role="presentation" style="position:relative" class='${tab1StyleAttiva}'
											onclick="javascript:insertCheck()">
											
											<a class="col-sx-6" href="#tab-1" aria-controls="tab-1"
											   role="tab" data-toggle="tab">
												<small>
													<spring:message text="??duediligence.label.crea??"
														code="duediligence.label.crea" />
												</small>
											</a>
										</li>
										
										<li role="presentation" style="position:relative" class='${tab2StyleAttiva}'
											onclick="editCheck(); puliscoAutocompletePcMod();">
											
											<a class="col-xs-6" href="#tab-2" aria-controls="tab-2"
											   role="tab" data-toggle="tab">
												<small>
													<spring:message text="??duediligence.label.modificacancellazione??"
														code="duediligence.label.modificacancellazione" />
												</small>
											</a>
										</li>
									</ul>
									
									<div class="tab-content p-20">
										
										<div id="tab-1" role="tabpanel" class="tab-pane animated fadeIn ${tab1StyleAttiva}">
											
											<div id="creaDiv">
											   
												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="dataApertura" class="col-sm-2 control-label">
																<spring:message text="??duediligence.label.dataApertura??"
																	code="duediligence.label.dataApertura" />
															</label>
															<div class="col-sm-10">
																 <form:input id="txtDataApertura" path="dataApertura" cssClass="form-control date-picker"
																    value="${dueDiligenceView.getVo().dataApertura}"/>
															</div>
														</div>
													</div>
												</div>
												
												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="dataChiusura" class="col-sm-2 control-label">
																<spring:message text="??duediligence.label.dataChiusura??"
																	code="duediligence.label.dataChiusura" />
															</label>
															<div class="col-sm-10">
																 <form:input id="txtDataChiusura" path="dataChiusura" cssClass="form-control date-picker"
																    value="${dueDiligenceView.getVo().dataChiusura}"/>
															</div>
														</div>
													</div>
												</div>
												
												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="comboProfessionista" class="col-sm-2 control-label"><spring:message
																	text="??duediligence.label.professionista??"
																	code="duediligence.label.professionista" /></label>
															<div class="col-sm-10"> 
																<form:select path="professionistaCode" cssClass="form-control" id="comboProfessionista">
																	<form:option value="">
																		<spring:message
																			text="??duediligence.label.selezionaProfessionista??"
																			code="duediligence.label.selezionaProfessionista" />
																	</form:option>
																	<c:if test="${ dueDiligenceView.professionistaEsternoList != null }">
																		<c:forEach items="${dueDiligenceView.professionistaEsternoList}" var="oggetto">
																			<form:option value="${oggetto.vo.id }">
																				<c:out value="${oggetto.vo.cognomeNome}"></c:out>
																			</form:option>																		
																		</c:forEach>																
																	</c:if>
																</form:select> 
															</div>
														</div>
													</div> 
												</div>
												
												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="comboStatoDueDiligence" class="col-sm-2 control-label"><spring:message
																	text="??duediligence.label.stato??"
																	code="duediligence.label.stato" /></label>
															<div class="col-sm-10">
																<form:select path="statoDueDiligenceCode" cssClass="form-control disabled" id="comboStatoDueDiligence" 
																             readonly="true">
																	<%-- <form:option value="">
																		<spring:message
																			text="??duediligence.label.selezionastato??"
																			code="duediligence.label.selezionastato" />
																	</form:option> --%>
																	<c:if test="${ dueDiligenceView.statoDueDiligenceList != null }">
																		<c:forEach items="${dueDiligenceView.statoDueDiligenceList}" var="oggetto">
																			<form:option value="${oggetto.vo.id }">
																				<c:out value="${oggetto.vo.descrizione}"></c:out>
																			</form:option>																		
																		</c:forEach>																
																	</c:if>
																</form:select> 
															</div>
														</div>
													</div> 
												</div>
												
												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="fileAssegnazione" class="col-sm-2 control-label"><spring:message
																			text="??duediligence.label.fileStep1??"
																			code="duediligence.label.fileStep1" /></label>
															<div class="col-sm-10">
																<input type="file" name="fileAssegnazione" 
																       id="fileAssegnazione" class="isupdate" />
															</div>
														</div>
													</div>
												</div>
												
											</div> <!-- creaDiv -->
										</div> <!-- end tab 1 -->
										
										<div id="tab-2" role="tabpanel" class="tab-pane animated fadeIn ${tab2StyleAttiva}">
											
											<div id="modifyDeleteDiv">
											   
											   <div class="list-group-item">
													<div class="form-group">
														<label for="dueDiligenceIdMod" class="col-sm-2 control-label">
															<i class="zmdi zmdi-search hs-reset" data-ma-action="search-clear" style="cursor: default !important; color:black !important; left:100px;"></i>
														</label>
														<div class="col-sm-10">
															<div id="" style="max-height:250px;overflow:auto;-ms-overflow-style: auto;">
																<input id="filtroListaDueDiligence" type="text" class="form-control">
															</div> 
														</div>
													</div>
												</div>
												
												<!-- LISTA -->
												<div class="list-group-item">
													<div class="form-group">
														<label for="dueDiligenceIdMod" class="col-sm-2 control-label"><spring:message
																text="??duediligence.label.lista??"
																code="duediligence.label.lista" /></label>
														<div class="col-sm-10">
															
															<div id="dueDiligenceIdDivMod" style="max-height:250px;overflow:auto;-ms-overflow-style: auto;">
																<form:select 
																	size="5"
																	path="dueDiligenceIdMod"
																	onchange="caricaDueDiligenceMod(this.value)"
																	onfocus="editCheck()"
																	cssClass="form-control">
																	
																	<c:if test="${dueDiligenceView.dueDiligenceViewList != null}">
																		<c:forEach items="${dueDiligenceView.dueDiligenceViewList}"
																			       var="oggetto">
																			
																			<form:option value="${ oggetto.vo.id }">
																				<c:out value="${oggetto.vo.professionistaEsterno.cognomeNome}"></c:out>
																			</form:option>
																		</c:forEach>
																	</c:if>
																	
																</form:select>
															</div>
																														
														</div>
													</div>
												</div>
												
												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="dataAperturaMod" class="col-sm-2 control-label">
																<spring:message text="??duediligence.label.dataApertura??"
																	code="duediligence.label.dataApertura" />
															</label>
															<div class="col-sm-10">
																 <form:input id="txtDataAperturaMod" path="dataAperturaMod" cssClass="form-control date-picker"
																    value="${dueDiligenceView.dataAperturaMod}"/>
															</div>
														</div>
													</div>
												</div>
												
												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="dataChiusuraMod" class="col-sm-2 control-label">
																<spring:message text="??duediligence.label.dataChiusura??"
																	code="duediligence.label.dataChiusura" />
															</label>
															<div class="col-sm-10">
																 <form:input id="txtDataChiusuraMod" path="dataChiusuraMod" cssClass="form-control date-picker"
																    value="${dueDiligenceView.dataChiusuraMod}"/>
															</div>
														</div>
													</div>
												</div>
												
												<div class="list-group lg-alt">
													<div class="list-group-item">
														<div class="form-group">
															<label for="comboProfessionistaMod" class="col-sm-2 control-label"><spring:message
																	text="??duediligence.label.professionista??"
																	code="duediligence.label.professionista" /></label>
															<div class="col-sm-10"> 
																<form:select path="professionistaCodeMod" cssClass="form-control" id="comboProfessionistaMod">
																	<form:option value="">
																		<spring:message
																			text="??duediligence.label.selezionaProfessionista??"
																			code="duediligence.label.selezionaProfessionista" />
																	</form:option>
																	<c:if test="${ dueDiligenceView.professionistaEsternoList != null }">
																		<c:forEach items="${dueDiligenceView.professionistaEsternoList}" var="oggetto">
																		
																			<c:choose>
																				<c:when test="${oggetto.vo.id eq dueDiligenceView.vo.professionistaEsterno.id}">
																					<form:option value="${oggetto.vo.id}" selected="true">
																						<c:out value="${oggetto.vo.cognomeNome}"></c:out>
																					</form:option>		
																				</c:when>
																				<c:otherwise>
																					<form:option value="${oggetto.vo.id }">
																						<c:out value="${oggetto.vo.cognomeNome}"></c:out>
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
													
													<div class="list-group lg-alt">
														<div class="list-group-item">
															<div class="form-group">
																<label for="comboStatoDueDiligenceMod" class="col-sm-2 control-label"><spring:message
																		text="??duediligence.label.stato??"
																		code="duediligence.label.stato" /></label>
																<div class="col-sm-10">
																	<%-- <form:select path="statoDueDiligenceCodeMod" cssClass="form-control disabled" id="comboStatoDueDiligenceMod"
														                 readonly="true"> --%>
																	<form:select path="statoDueDiligenceCodeMod" cssClass="form-control" id="comboStatoDueDiligenceMod">
															          <form:option value="">
																		<spring:message
																			text="??duediligence.label.selezionastato??"
																			code="duediligence.label.selezionastato" />
																		</form:option>
																		<!-- Abilitato solo nello stato 1 e visualizzo stati 1, 2 -->
																		<c:if test="${ dueDiligenceView.statoDueDiligenceList != null }">
																			<c:forEach items="${dueDiligenceView.statoDueDiligenceList}" var="oggetto">
																				
																				<c:choose>
																					<c:when test="${oggetto.vo.id eq dueDiligenceView.vo.statoDueDiligence.id}">
																						<form:option value="${oggetto.vo.id}" selected="true">
																							<c:out value="${oggetto.vo.descrizione}"></c:out>
																						</form:option>		
																					</c:when>
																					<c:otherwise>
																						<form:option value="${oggetto.vo.id}">
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
													
													<!-- Step 1 -->
													<div class="list-group lg-alt" id="step1Attach">
														<div class="list-group-item">
															<div class="form-group">
																<label for="fileAssegnazioneMod" class="col-sm-2 control-label"><spring:message
																				text="??duediligence.label.fileStep1??"
																				code="duediligence.label.fileStep1" /></label>
																<div class="col-sm-10">
																	<input type="file" name="fileAssegnazioneMod" 
																	       id="fileAssegnazioneMod" class="isupdate" />
																</div>
															</div>
														</div>
													</div>
													
													<!-- Step 2 -->
													<div class="list-group lg-alt" id="step2AttachObj1" style="display: none">
														<div class="list-group-item">
															<div class="form-group">
																<label class="col-sm-2 control-label">
																	<spring:message
																		text="??duediligence.label.fileStep1??"
																		code="duediligence.label.fileStep1" /></label>
																<span class="col-sm-10" id="nomeFile" />
															</div>
														</div>
													</div>
													<div class="list-group lg-alt" id="step2AttachObj2" style="display: none">
														<div class="list-group-item">
															<div class="form-group">
																<label for="fileVerificaMod" class="col-sm-2 control-label"><spring:message
																				text="??duediligence.label.fileStep2??"
																				code="duediligence.label.fileStep2" /></label>
																<div class="col-sm-10">
																	<input type="file" name="fileVerificaMod" 
																	       id="fileVerificaMod" class="isupdate" />
																</div>
															</div>
														</div>
													</div>
													
													<div class="list-group lg-alt" id="step2AttachObj3" style="display: none">
														<div class="list-group-item">
															<div class="form-group">
																<label for="nazione" class="col-sm-2 control-label">
																	<spring:message
																		text="??duediligence.label.documentiVerificaAllegati??" 
																		code="duediligence.label.documentiVerificaAllegati" />
																</label>
																<div class="col-sm-10">
																	<div class="table-responsive" style="clear:both;">
																		<table class="table table-striped table-responsive" >
																			<thead>
																				<tr style="border:1px solid #e0e0e0">
																					<th data-column-id="01" style="width:50px"> 
																						<button  type="button" data-toggle="collapse" data-target="#boxDocumentiVerifica" 
																							class="btn bg waves-effect waves-circle waves-float btn-circle-mini"  
																							style="float: left;position: relative !important;">
																							<i class="zmdi zmdi-collection-text icon-mini"></i>
																						</button>
																					</th> 
																					<th data-column-id="id"><spring:message
																							text="??duediligence.label.elimina??"
																							code="duediligence.label.elimina" />
																					</th>
																				</tr>
																			</thead>
																			<tbody id="boxDocumentiVerifica" class="collapse in">
																			</tbody>
																		</table>
																	</div>			
																	
																</div>
															</div>
														</div>
													</div>
													
													<!-- Step 3 -->
													<div class="list-group lg-alt" id="step3AttachObj" style="display: none">
														<div class="list-group-item">
															<div class="form-group">
																<label for="fileEsitoVerificaMod" class="col-sm-2 control-label"><spring:message
																				text="??duediligence.label.fileStep3??"
																				code="duediligence.label.fileStep3" /></label>
																<div class="col-sm-10">
																	<input type="file" name="fileEsitoVerificaMod" 
																	       id="fileEsitoVerificaMod" class="isupdate" />
																</div>
															</div>
														</div>
													</div>
													 
													<!-- Step 4 -->
													<div class="list-group lg-alt" id="step4AttachObj" style="display: none">
														<div class="list-group-item">
															<div class="form-group">
																<label class="col-sm-2 control-label">
																	<spring:message
																		text="??duediligence.label.fileStep3??"
																		code="duediligence.label.fileStep3" /></label>
																<span class="col-sm-10" id="step3FileName" > </span>
															</div>
														</div>
													</div>
											 
											</div>
											
										</div>
										
									</div>
								</form:form>
								
								<button form="duediligenceForm" onclick="cancellaDueDiligence()" 
									class="btn palette-Green-SNAM bg btn-float2 waves-effect waves-circle waves-float btnCancella" 
									style="display: none">
									<i class="zmdi zmdi-delete"></i>
								</button>
								
								<button form="duediligenceForm" onclick="salvaDueDiligence()"
									class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float btnSalva">
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
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>
	
	<jsp:include page="/parts/script-end.jsp"></jsp:include>
	<!-- si carica il js -->
	
	<script charset="UTF-8" type="text/javascript">
		<c:if test="${ empty dueDiligenceView.jsonArrayDueDiligenceMod }">
			var jsonArrayDueDiligenceMod = '';
		</c:if>
	
		<c:if test="${ not empty dueDiligenceView.jsonArrayDueDiligenceMod }">
			var jsonArrayDueDiligenceMod = JSON.parse('${dueDiligenceView.jsonArrayDueDiligenceMod}');
		</c:if>
	</script>

	<script src="<%=request.getContextPath()%>/portal/js/controller/dueDiligence.js"></script>
	
</body>

</html>
