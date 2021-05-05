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

<jsp:include page="/parts/script-init.jsp"></jsp:include>

<style>
.disabled {
	pointer-events: none;
	cursor: not-allowed;
}

.paddMultifile {
	padding-top: 5px;
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
									<spring:message text="??repertorioPoteri.label.gestione??"
										code="repertorioPoteri.label.gestione" />
								</h2>
							</div>

							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form id="repertorioPoteriForm" name="repertorioPoteriForm"
									method="post" modelAttribute="repertorioPoteriView"
									action="salva.action" class="form-horizontal la-form"
									enctype="multipart/form-data">
									<engsecurity:token regenerate="false"/>

									<form:errors path="*" cssClass="alert alert-danger"
										htmlEscape="false" element="div"></form:errors>
									<form:hidden path="op" id="op" value="salvaRepertorioPoteri" />


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


									<div id="creaDiv">

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="codice" class="col-sm-2 control-label"><spring:message
															text="??repertorioPoteri.label.codice??"
															code="repertorioPoteri.label.codice" /></label>
													<div class="col-sm-10">
														<form:input path="codice" cssClass="form-control" maxlength="10"
															id="txtCodice"
															value="${repertorioPoteriView.getVo().codice}" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="codice" class="col-sm-2 control-label"><spring:message
															text="??repertorioPoteri.label.descrizione??"
															code="repertorioPoteri.label.descrizione" /></label>
													<div class="col-sm-10">
														<form:input path="descrizione" cssClass="form-control" maxlength="200"
															id="txtDescrizione"
															value="${repertorioPoteriView.getVo().descrizione}" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="codice" class="col-sm-2 control-label"><spring:message
															text="??repertorioPoteri.label.testo??"
															code="repertorioPoteri.label.testo" /></label>
													<div class="col-sm-10">
														<form:textarea path="testo" cssClass="form-control" cols="70" rows="8" 
															id="txtTesto"
															value="${repertorioPoteriView.getVo().testo}" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<!--tipologia-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="idCategoria" class="col-sm-2 control-label"><spring:message
																text="??repertorioPoteri.label.categoria??"
																code="repertorioPoteri.label.categoria" /></label>
														<div class="col-sm-10">
															<form:select path="idCategoria" disabled="false"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??repertorioPoteri.label.selezionaCategoria??"
																		code="repertorioPoteri.label.selezionaCategoria" />
																</form:option>
																<c:if
																	test="${ repertorioPoteriView.listaCategorie != null }">
																	<c:forEach
																		items="${repertorioPoteriView.listaCategorie}"
																		var="oggetto">

																		<form:option value="${ oggetto.id }">
																			<c:out value="${oggetto.descrizione}"></c:out>
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
											<!--tipologia-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="idSubcategoria" class="col-sm-2 control-label"><spring:message
																text="??repertorioPoteri.label.subCategoria??"
																code="repertorioPoteri.label.subCategoria" /></label>
														<div class="col-sm-10">
															<form:select path="idSubcategoria" disabled="false"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??repertorioPoteri.label.selezionaSubCategoria??"
																		code="repertorioPoteri.label.selezionaSubCategoria" />
																</form:option>
																<c:if
																	test="${ repertorioPoteriView.listaSubCategorie != null }">
																	<c:forEach
																		items="${repertorioPoteriView.listaSubCategorie}"
																		var="oggetto2">

																		<form:option value="${ oggetto2.id }">
																			<c:out value="${oggetto2.descrizione}"></c:out>
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
											<div class="list-group-item">

												<div class="row">
													<div class="col-sm-9"></div>
													<div class="col-sm-1">
														<button id="btnClear" type="button"
															onclick="puliscoCampiInsert()"
															class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float">
															<i class="fa fa-eraser"></i>
														</button>
													</div>
													<div class="col-sm-1">
														<button form="repertorioPoteriForm"
															onclick="salvaRepertorioPoteri()"
															class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float btnSalva">
															<i class="zmdi zmdi-save"></i>
														</button>
													</div>
													<div class="col-sm-1"></div>
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
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>

	<jsp:include page="/parts/script-end.jsp"></jsp:include>
	<!-- si carica il js -->

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/repertorioPoteri.js"></script>



</body>

</html>