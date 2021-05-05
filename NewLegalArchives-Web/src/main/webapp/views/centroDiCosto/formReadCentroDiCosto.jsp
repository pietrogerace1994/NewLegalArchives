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
										<spring:message text="??centroDiCosto.label.visualizza??"
											code="centroDiCosto.label.visualizza" />
									</h2>
								
							</div>
							<div class="card-body">

								<form:form name="centroDiCostoReadForm" method="post"
									modelAttribute="centroDiCostoView" action="salva.action"
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
									<form:hidden path="op" id="op" value="salvaCentroDiCosto" />
									
									<div class="tab-content p-20">

										<!-- LISTA CENTRI DI COSTO -->
										<div class="list-group lg-alt">

											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="listCentriDiCosto"
															class="col-sm-2 control-label"><spring:message
																text="??centroDiCosto.label.lista??"
																code="centroDiCosto.label.lista" /></label>
														<div class="col-sm-10">
															<form:select size="5" path="centroDiCostoId"
																onchange="caricaDescrizioniCdc(this.value)"
																cssClass="form-control">
																<c:if test="${ centroDiCostoView.listaCentroDiCosto != null }">
																	<c:forEach
																		items="${centroDiCostoView.listaCentroDiCosto}"
																		var="oggetto">
																		<form:option value="${ oggetto.vo.id }">
																			<c:out value="${oggetto.vo.unitaLegale} - ${oggetto.vo.cdc} "></c:out>
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
										
											<!-- SETTORE GIURIDICO -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="cdc" class="col-sm-2 control-label"><spring:message
																text="??centroDiCosto.label.cdc??" 
																code="centroDiCosto.label.cdc" /></label>
														<div class="col-sm-10">
															<form:input path="cdc" cssClass="form-control" readonly="true" />
														</div>
													</div>
												</div>
											</div>
										
											<!-- SETTORE GIURIDICO -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="settoreGiuridico" class="col-sm-2 control-label"><spring:message
																text="??centroDiCosto.label.settoreGiuridico??" 
																code="centroDiCosto.label.settoreGiuridico" /></label>
														<div class="col-sm-10">
															<form:input path="settoreGiuridico" cssClass="form-control" readonly="true" />
														</div>
													</div>
												</div>
											</div>
											
											<!-- TIPOLOGIA FASCICOLO -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="tipologiaFascicolo" class="col-sm-2 control-label"><spring:message
																text="??centroDiCosto.label.tipologiaFascicolo??" 
																code="centroDiCosto.label.tipologiaFascicolo" /></label>
														<div class="col-sm-10">
															<form:input path="tipologiaFascicolo" cssClass="form-control" readonly="true" />
														</div>
													</div>
												</div>
											</div>
											
											<!-- SOCIETA -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="societa" class="col-sm-2 control-label"><spring:message
																text="??centroDiCosto.label.societa??" 
																code="centroDiCosto.label.societa" /></label>
														<div class="col-sm-10">
															<form:input path="societa" cssClass="form-control" readonly="true" />
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

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>


	<script
		src="<%=request.getContextPath()%>/portal/js/controller/centroDiCosto.js"></script>
</body>

</html>

