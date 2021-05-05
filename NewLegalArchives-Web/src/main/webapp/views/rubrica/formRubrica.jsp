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
								<c:if
									test="${ rubricaView.rubricaId == null || rubricaView.rubricaId == 0 }">
									<h2>
										<spring:message text="??rubrica.label.nuovoNominativo??"
											code="rubrica.label.nuovoNominativo" />
									</h2>
								</c:if>
								<c:if
									test="${ rubricaView.rubricaId  != null && rubricaView.rubricaId  > 0 }">
									<h2>
										<spring:message text="??rubrica.label.modificaNominativo??"
											code="rubrica.label.modificaNominativo" />
									</h2>
								</c:if>
							</div>
							<div class="card-body">

								<form:form name="rubricaForm" method="post"
									modelAttribute="rubricaView" action="salva.action"
									class="form-horizontal la-form">
									<engsecurity:token regenerate="false"/>
									<form:errors path="*" cssClass="alert alert-danger"
										htmlEscape="false" element="div"></form:errors>
									<c:if test="${ not empty param.successMessage }">
										<div class="alert alert-info">
											<spring:message code="messaggio.operazione.ok"
												text="??messaggio.operazione.ok??"></spring:message>
										</div>
									</c:if>
									<c:if test="${ not empty param.errorMessage }">
										<div class="alert alert-danger">
											<spring:message code="${errorMessage}"
												text="??${errorMessage}??"></spring:message>
										</div>
									</c:if>
									<form:hidden path="rubricaId" />
									<form:hidden path="op" id="op" value="salvaRubrica" />

									 

									<div class="list-group lg-alt">
										<!--NOMINATIVO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nominativo" class="col-sm-2 control-label"><spring:message
															text="??rubrica.label.nominativo??"
															code="rubrica.label.nominativo" /></label>
													<div class="col-sm-10">
														<form:textarea path="nominativo" cssClass="form-control" />
													</div>
												</div>
											</div>
										</div>
									
									<!-- EMAIL  -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="email" class="col-sm-2 control-label"><spring:message
															text="??rubrica.label.email??"
															code="rubrica.label.email" /></label>
													<div class="col-sm-10">
														<form:textarea 
															path="email"
															cssClass="form-control"
														/>
													</div>
												</div>
											</div>
										</div>
									</div>
								</form:form>

								<button form="rubricaForm" onclick="salvaNominativo()"
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
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/rubrica.js"></script>
 	
  
</body>

</html>
