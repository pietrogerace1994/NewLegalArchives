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
									<spring:message text="??materia.label.gestione??"
										code="materia.label.gestione" />
								</h2>
							</div>
							<div class="card-body">

								<form:form name="materiaEditForm" method="post"
									modelAttribute="materiaView" action="salva.action"
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
									<form:hidden path="op" id="op" value="salvaMateria" />
									<form:hidden path="editMode" id="editMode" value="true" />
									<form:hidden path="insertMode" id="insertMode" value="false" />
									<form:hidden path="deleteMode" id="deleteMode" value="false" />
									<form:hidden path="localeStr" id="localeStr" />
									<form:hidden path="materiaCodGruppoLingua" id="materiaCodGruppoLingua" value="0"  />
									<form:hidden path="materiaPadreCodGruppoLinguaIns" id="materiaPadreCodGruppoLinguaIns" value="0"  />
									<form:hidden path="settoreGiuridicoCodGruppoLingua" id="settoreGiuridicoCodGruppoLingua" value=""/>
									
									
									<!-- Lingue disponibili -->
										<c:if test="${ materiaView.listaLingua != null }">
										<script type="text/javascript">
											var lingueDisponibili = null;
										</script>
										</c:if>
										<c:if test="${ materiaView.listaLingua != null }">
										
										   <script type="text/javascript">
										   	   var lingueDisponibili = new Array();
										   	   var lingua;
										   		
											  <c:forEach
												items="${materiaView.listaLingua}"
												var="ling"
												varStatus="status">
												
												lingua = new Object();
												lingua.id = ${ling.vo.id};
												lingua.lang = "${ling.vo.lang}";
												
												lingueDisponibili.push(lingua);
												
											  </c:forEach>
											  
										   </script>
										
										</c:if>
									
			
		
									
						<div class="tab-content p-20">
									<fieldset class="scheduler-border">

										<legend class="scheduler-border">
											<spring:message text="??materia.label.modifica??"
												code="materia.label.modifica" />
										</legend>
										
										
										
										
										
										
										<!-- TIPOLOGIA FASCICOLO -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="tipologiaFascicoloId" class="col-sm-2 control-label"><spring:message
															text="??materia.label.tipologiaFascicolo??"
															code="materia.label.tipologiaFascicolo" /></label>
													<div class="col-sm-10">
														
														<form:select 
																size="1"
																path="tipologiaFascicoloId"
																onchange="caricaSettoreGiuridicoEdit(this.value)"
																onfocus="editCheck()"
																cssClass="form-control">
																		<form:option value="0">
																			<c:out value="Nessuna selezione ..."></c:out>
																		</form:option>
																<c:if test="${ materiaView.listaTipologiaFascicolo != null }">
																	<c:forEach
																		items="${materiaView.listaTipologiaFascicolo}"
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
										
										<!-- SETTORE GIURIDICO -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="sottoMateriaNome" class="col-sm-2 control-label"><spring:message
															text="??materia.label.settoreGirudico??"
															code="materia.label.settoreGirudico" /></label>
													<div class="col-sm-10">
														
														<form:select 
																size="1" 
																path="settoreGiuridicoId"
																onchange="caricaAlberaturaMaterieEdit(this.value)"
																onfocus="editCheck()"
																cssClass="form-control">
																		<form:option value="0">
																			<c:out value="Nessuna selezione ..."></c:out>
																		</form:option>
																
																
															</form:select>
														
													</div>
												</div>
											</div>
										</div>
										
										
										<!-- ALBERO MATERIA -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="comboMaterie" class="col-sm-2 control-label"><spring:message
															text="??materia.label.materia??" code="materia.label.materia" /></label>
													<div class="col-sm-10">
														 <div id="treeContainerMaterie" style="max-height:250px;overflow:auto;-ms-overflow-style: auto;">
														 	
														 </div> 
													</div>
												</div>
											</div>
										</div>

										<!-- NOME -->
										<c:if test="${ materiaView.listaLingua != null }">
											<c:forEach
												items="${materiaView.listaLingua}"
												var="ling"
												varStatus="status">
												
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nome[${ling.vo.lang}]" class="col-sm-2 control-label"><spring:message
															text="??materia.label.nome??"
															code="materia.label.nome" />&nbsp;(${ling.vo.descrizione})</label>
													<div class="col-sm-10">
														<input onfocus="editCheck()" type="text" name="nome[${ling.vo.id}]" class="form-control"/>
														<input type="hidden" name="materiaNomeId[${ling.vo.id}]" value="" />
														<input type="hidden" name="materiaIdPadre[${ling.vo.id}]" value="" />
													</div>
												</div>
											</div>
										</div>
												
											</c:forEach>
										
										</c:if>
										
										
									
										
										
									
									</fieldset>
									<fieldset class="scheduler-border">
										<legend class="scheduler-border">
											<spring:message text="??materia.label.inserimento??"
												code="materia.label.inserimento" />
										</legend>
										
										<!-- TIPOLOGIA FASCICOLO -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="sottoMateriaNome" class="col-sm-2 control-label"><spring:message
															text="??materia.label.tipologiaFascicolo??"
															code="materia.label.tipologiaFascicolo" /></label>
													<div class="col-sm-10">
														
														<form:select 
																size="1"
																path="tipologiaFascicoloIdIns"
																onchange="caricaSettoreGiuridico(this.value)"
																onfocus="insertCheck()"
																cssClass="form-control">
																		<form:option value="0">
																			<c:out value="Nessuna selezione ..."></c:out>
																		</form:option>
																<c:if test="${ materiaView.listaTipologiaFascicolo != null }">
																	<c:forEach
																		items="${materiaView.listaTipologiaFascicolo}"
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
										
										<!-- SETTORE GIURIDICO -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="sottoMateriaNome" class="col-sm-2 control-label"><spring:message
															text="??materia.label.settoreGirudico??"
															code="materia.label.settoreGirudico" /></label>
													<div class="col-sm-10">
														
														<form:select 
																size="1" 
																path="settoreGiuridicoIdIns"
																onchange="caricaAlberaturaMaterie(this.value)"
																onfocus="insertCheck()"
																cssClass="form-control">
																		<form:option value="0">
																			<c:out value="Nessuna selezione ..."></c:out>
																		</form:option>
																
																
															</form:select>
														
													</div>
												</div>
											</div>
										</div>
										
										
										<!-- ALBERO MATERIA -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="comboMaterie" class="col-sm-2 control-label"><spring:message
															text="??materia.label.materiaPadre??" code="materia.label.materiaPadre" /></label>
													<div class="col-sm-10">
														 <div id="treeContainerMateriePadre" style="max-height:250px;overflow:auto;-ms-overflow-style: auto;">
														 	
														 </div> 
													</div>
												</div>
											</div>
										</div>

										<!-- NOME -->
										<c:if test="${ materiaView.listaLingua != null }">
											<c:forEach
												items="${materiaView.listaLingua}"
												var="ling"
												varStatus="status">
												
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nomeIns[${ling.vo.lang}]" class="col-sm-2 control-label"><spring:message
															text="??materia.label.nome??"
															code="materia.label.nome" />&nbsp;(${ling.vo.descrizione})</label>
													<div class="col-sm-10">
														<input onfocus="insertCheck()" type="text" name="nomeIns[${ling.vo.id}]" class="form-control"/>
													</div>
												</div>
											</div>
										</div>
												
											</c:forEach>
										
										</c:if>


										



									</fieldset>
					   </div> <!-- END tab -->


								</form:form>

								
								<button form="materiaEditForm" onclick="cancellaMateria()"
									class="btn palette-Green-SNAM bg btn-float2 waves-effect waves-circle waves-float">
									<i class="zmdi zmdi-delete"></i>
								</button>
								<button form="materiaEditForm" onclick="salvaMateria()"
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

	
	<script type="text/javascript">
			$(document).ready(function() 
			{

				 var treeContainerMateriePadre =  $('#treeContainerMateriePadre').easytree();
				 var treeContainerMaterie = $('#treeContainerMaterie').easytree();
//				if ( $('#treeContainerMaterie') && jsonDataMaterie != null && jsonDataMaterie != undefined) {
//					
//					$('#treeContainerMaterie').easytree({
//						data : jsonDataMaterie,
//						built : selezionaMaterie
//					});
//					
//				}

			});
	</script>
	<script
		src="<%=request.getContextPath()%>/portal/js/controller/materia.js"></script>
</body>
</html>
