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
									test="${ udienzaView.udienzaId == null || udienzaView.udienzaId == 0 }">
									<h2>
										<spring:message text="??udienza.label.nuovaUdienza??"
											code="udienza.label.nuovaUdienza" />
									</h2>
								</c:if>
								<c:if
									test="${ udienzaView.udienzaId != null && udienzaView.udienzaId > 0 }">
									<h2>
										<spring:message text="??udienza.label.modificaUdienza??"
											code="udienza.label.modificaUdienza" />
									</h2>
								</c:if>
							</div>
							<div class="card-body">

								<form:form name="udienzaForm" method="post"
									modelAttribute="udienzaView" action="salva.action"
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
											<spring:message code="${param.errorMessage}"
												text="??${param.errorMessage}??"></spring:message>
										</div>
									</c:if>

									<div id="sezioneMessaggiApplicativi"></div>

									<form:hidden path="udienzaId" />
									<form:hidden path="op" id="op" value="salvaUdienza" />

									<!--NOME FASCICOLO -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="fascicolo" class="col-sm-2 control-label">
														<spring:message text="??udienza.label.fascicolo??" code="udienza.label.fascicolo" />
													</label>
													<div class="col-sm-10">
														<form:input path="nomeFascicolo" cssClass="form-control" readonly="true" />
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<!-- DATA CREAZIONE -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="dataCreazione" class="col-sm-2 control-label">
														<spring:message text="??udienza.label.dataCreazione??" code="udienza.label.dataCreazione" />
													</label>
													<div class="col-sm-10">
														<input type="text" class="form-control" value="${udienzaView.dataCreazione}" disabled="true"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<!-- DATA UDIENZA -->
									<div class="list-group lg-alt">
										<div class="list-group-item media">
											<div class="media-body media-body-datetimepiker">
												<div class="form-group">
													<label for="dataUdienza" class="col-md-2 control-label">
														<spring:message text="??udienza.label.dataUdienza??" code="udienza.label.dataUdienza" />
													</label>
													<div class="col-md-10">
														<form:input id="dataUdienza" path="dataUdienza" cssClass="form-control date-picker"/>
													</div>
												</div>
											</div>
										</div>
									</div>

									<!-- OGGETTO -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="descrizione" class="col-sm-2 control-label">
													<spring:message text="??udienza.label.oggetto??" code="udienza.label.oggetto" />
												</label>
												<div class="col-sm-10">
													<form:textarea path="descrizione" cols="70" rows="5" cssClass="form-control" />
												</div>
											</div>
										</div>
									</div>
									
								</form:form>

								<div class="col-md-12" style="position: absolute; margin-top: -120px; width: 100%;">

									<button form="udienzaForm" onclick="salvaUdienza()"
										class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float"
										style="position: relative !important;float: right;margin-right: 17px;">
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

	<script src="<%=request.getContextPath()%>/portal/js/controller/udienza.js"></script>

</body>
</html>
