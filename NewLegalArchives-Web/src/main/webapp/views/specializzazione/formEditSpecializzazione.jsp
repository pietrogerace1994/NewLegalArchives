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
										<spring:message text="??specializzazione.label.gestione??"
											code="specializzazione.label.gestione" />
									</h2>
								
							</div>
							<div class="card-body">

								<form:form name="specializzazioneEditForm" method="post"
									modelAttribute="specializzazioneView" action="salva.action"
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
									<form:hidden path="specializzazioneId" />
									<form:hidden path="op" id="op" value="salvaSpecializzazione" />
									<form:hidden path="flagCode" id="flagCode" value="false" />
									<form:hidden path="editMode" id="editMode" value="true" />
									<form:hidden path="insertMode" id="insertMode" value="false" />
									<form:hidden path="deleteMode" id="deleteMode" value="false" />
									
									<div class="tab-content p-20">

									<fieldset class="scheduler-border">
    									<legend class="scheduler-border"><spring:message text="??specializzazione.label.modifica??"
											code="specializzazione.label.modifica" /></legend>
										<div class="list-group lg-alt">

											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="listSpecializzazioni"
															class="col-sm-2 control-label"><spring:message
																text="??specializzazione.label.lista??"
																code="specializzazione.label.lista" /></label>
														<div class="col-sm-10">
															<form:select size="5" path="specializzazioneCode"
																onchange="caricaDescrizioniSpec(this.value)"
																onfocus="editCheck()"
																cssClass="form-control">
																<c:if test="${ specializzazioneView.listaSpecializzazioni != null }">
																	<c:forEach
																		items="${specializzazioneView.listaSpecializzazioni}"
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
										
										<div class="list-group lg-alt">
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<c:if test="${ specializzazioneView.listaLingua != null }">
																<c:forEach items="${specializzazioneView.listaLingua}" var="lin" varStatus="status">
																	<label for="specializzazioneDesc[${status.index}]" class="col-sm-2 control-label">${lin.vo.descrizione}</label>
																	<div class="col-sm-10">
																		<input onfocus="editCheck()" type="text" name="specializzazioneDesc[${status.index}]" class="form-control"/>
																	</div>
																</c:forEach>
															</c:if>
														</div>
													</div>
												</div>
										</div>
									</fieldset>	
									<fieldset class="scheduler-border">
    									<legend class="scheduler-border"><spring:message text="??specializzazione.label.inserimento??"
											code="specializzazione.label.inserimento" /></legend>
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<c:if test="${ specializzazioneView.listaLingua != null }">
															<c:forEach items="${specializzazioneView.listaLingua}" var="ling" varStatus="status">
																<label for="specializzazioneIns[${status.index}]" class="col-sm-2 control-label">${ling.vo.descrizione}</label>
																<div class="col-sm-10">
																	<input onfocus="insertCheck()" type="text" name="specializzazioneIns[${status.index}]" class="form-control"/>
																</div>
															</c:forEach>
														</c:if>
													</div>
												</div>
											</div>
										</div>	
									</fieldset>
									</div>
								</form:form>
								
								<button form="specializzazioneEditForm" onclick="cancellaSpecializzazione()"
									class="btn palette-Green-SNAM bg btn-float2 waves-effect waves-circle waves-float">
									<i class="zmdi zmdi-delete"></i>
								</button>
								<button form="specializzazioneEditForm" onclick="salvaSpecializzazione()"
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
		src="<%=request.getContextPath()%>/portal/js/controller/specializzazione.js"></script>
</body>
</html>
