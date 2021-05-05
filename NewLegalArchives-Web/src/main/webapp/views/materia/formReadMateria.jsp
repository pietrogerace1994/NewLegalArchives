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
  										<spring:message text="??materia.label.visualizza??"  code="materia.label.visualizza" />  
									</h2>
								
							</div>
							<div class="card-body">

								<form:form name="materiaReadForm" method="post"
									modelAttribute="materiaView" action="salva.action"
									class="form-horizontal la-form">
							        <engsecurity:token regenerate="false"/>		
									
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
																onchange="caricaSettoreGiuridicoVis(this.value)"
																
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
																onchange="caricaAlberaturaMaterieVis(this.value)"
																
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
														<input  type="text" name="nome[${ling.vo.id}]" class="form-control"/>
														<input type="hidden" name="materiaNomeId[${ling.vo.id}]" value="" />
														<input type="hidden" name="materiaIdPadre[${ling.vo.id}]" value="" />
													</div>
												</div>
											</div>
										</div>
												
											</c:forEach>
										
										</c:if>
										
										
									
										
									
										
									
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
	
	<script type="text/javascript">
			$(document).ready(function() 
			{
				 var treeContainerMaterie = $('#treeContainerMaterie').easytree();

			});
	</script>
	<script
		src="<%=request.getContextPath()%>/portal/js/controller/materia.js"></script>
</body>

</html>

