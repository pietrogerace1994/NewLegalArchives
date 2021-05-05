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
									<spring:message text="??presidionormativo.label.gestioneCategorie??"
										code="presidionormativo.label.gestioneCategorie" />
								</h2>
							</div>

							<!-- FORM CATEGORIE -->
							<div class="card-body">
								<form:form name="categorieForm" method="post"
									modelAttribute="categoriaView" action="salva.action"
									class="form-horizontal la-form" enctype="multipart/form-data">
									 <engsecurity:token regenerate="false"/>
									<form:errors path="*" cssClass="alert alert-danger"
										element="div"></form:errors>
									<c:if test="${categoriaView.categoriaId ne '0'}">
										<form:hidden path="categoriaId" id="categoriaId" value="${categoriaView.categoriaId}" />
									</c:if>
									
									<c:if test="${categoriaView.tabAttiva eq '1' }">
										<c:set var="tab1StyleAttiva" scope="page" value="active" />
										<form:hidden path="insertMode" id="insertMode" value="true" />
										<form:hidden path="editMode" id="editMode" value="false" />
										<form:hidden path="deleteMode" id="deleteMode" value="false" />
									</c:if>

									<c:if test="${categoriaView.tabAttiva eq '2' }">
										<c:set var="tab2StyleAttiva" scope="page" value="active" />
										<form:hidden path="insertMode" id="insertMode" value="false" />
										<form:hidden path="editMode" id="editMode" value="true" />
										<form:hidden path="deleteMode" id="deleteMode" value="false" />
										<form:hidden path="categoriaId" id="categoriaId" value="${categoriaView.categoriaId}" />
									</c:if>

									<form:hidden path="op" id="op" value="salvaCategoria" />
									<form:hidden path="tabAttiva" id="tabAttiva" />

									<ul class="tab-nav tn-justified tn-icon" role="tablist">
										<li role="presentation" style="position: relative"
											class='${tab1StyleAttiva}' onclick="javascript:insertCheck()">

											<a class="col-sx-6" href="#tab-1" aria-controls="tab-1"
											role="tab" data-toggle="tab"> <small> <spring:message
														text="??presidionormativo.label.crea??"
														code="presidionormativo.label.crea" />
											</small>
										</a>
										</li>

										<li role="presentation" style="position: relative"
											class='${tab2StyleAttiva}'
											><a
											class="col-xs-6" href="#tab-2" aria-controls="tab-2"
											role="tab" data-toggle="tab"> <small> <spring:message
														text="??presidionormativo.label.modificacancellazione??"
														code="presidionormativo.label.modificacancellazione" />
											</small>
										</a></li>
									</ul>

									<div class="tab-content p-20">

										<div id="tab-1" role="tabpanel"
											class="tab-pane animated fadeIn ${tab1StyleAttiva}">
											<div class="card-body">
											<form:form name="rubricaForm" method="post"
												modelAttribute="categoriaView" action="salva.action"
												class="form-horizontal la-form">
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
												<%-- <form:hidden path="rubricaId" /> --%>
												<%-- <form:hidden path="op" id="op" value="" /> --%>



													<div class="list-group lg-alt">
														<!--NOME CATEGORIA-->
														<div class="list-group-item media">
															<div class="media-body">
																<div class="form-group">
																	<label for="nomeCategoria"
																		class="col-sm-2 control-label">
																		<div id="labelCat"
																			<c:if test="${categoriaView.isSottoCategoria}">
														style='display: none;'
															</c:if>
																			<c:if test="${!categoriaView.isSottoCategoria}">
														style='display: block;'
															</c:if>>
																			<spring:message
																				text="??presidionormativo.label.nomeCategoria??"
																				code="presidionormativo.label.nomeCategoria" />
																		</div>
																		<div id="labelSot"
																			<c:if test="${categoriaView.isSottoCategoria}">
														style='display: block;'
															</c:if>
																			<c:if test="${!categoriaView.isSottoCategoria}">
														style='display: none;'
															</c:if>>
																			<spring:message
																				text="??presidionormativo.label.nomeSottoCategoria??"
																				code="presidionormativo.label.nomeSottoCategoria" />
																		</div>
																	</label>
																	<div class="col-sm-10">
																		<form:input path="nomeCategoria"
																			cssClass="form-control" />
																	</div>
																</div>
															</div>
														</div>

														<c:if test="${!categoriaView.haFigli}">
															<div class="list-group lg-alt">
																<!--E'SOTTO-->
																<div class="list-group-item media">
																	<div class="media-body">
																		<div class="form-group">
																			<label for="sottoCategoria"
																				class="col-sm-2 control-label">E' una
																				sottoCategoria?</label>
																			<div class="col-sm-10">
																				<form:checkbox path="isSottoCategoria"
																					onclick="addSottoCategoria();" />
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</c:if>

														<div class="list-group lg-alt" id="catPadre"
															<c:if test="${categoriaView.isSottoCategoria}">
														style='display: block;'
															</c:if>
															<c:if test="${!categoriaView.isSottoCategoria}">
														style='display: none;'
															</c:if>>
															<!--CATEGORIA-->
															<div class="list-group-item media">
																<div class="media-body">
																	<div class="form-group">
																		<label for="sottoCategoria"
																			class="col-sm-2 control-label"><spring:message
																				text="??presidionormativo.label.sottoCategoria??"
																				code="presidionormativo.label.sottoCategoria" /></label>
																		<div class="col-sm-10">
																			<form:select path="categoriaPadre"
																				cssClass="form-control"
																				onchange="selezionaCategoria(this.value)">
																				<c:if
																					test="${ categoriaView.sottoCategorie != null }">
																					<c:forEach items="${ categoriaView.sottoCategorie}"
																						var="oggetto">
																						<c:if
																							test="${ oggetto.vo.id != categoriaView.categoriaId}">
																							<form:option value="${ oggetto.vo.id }">
																								<c:out value="${oggetto.vo.nomeCategoria}"></c:out>
																							</form:option>
																						</c:if>
																					</c:forEach>
																				</c:if>
																			</form:select>
																		</div>
																	</div>
																</div>
															</div>
														</div>
														<!-- COLORE  -->
														<div class="list-group-item media">
															<div class="media-body" style="height: 90px;">
																<div class="form-group">
																	<label for="email" class="col-sm-2 control-label"><spring:message
																			text="??presidionormativo.label.coloreCategoria??"
																			code="presidionormativo.label.coloreCategoria" /></label>
																	<div class="col-sm-10" style="margin-top: 10px;">
																		<form:select path="colorselector">
																			<c:if
																				test="${ categoriaView.stileCategorie != null }">
																				<c:forEach items="${ categoriaView.stileCategorie}"
																					var="oggetto">
																					<option value="${ oggetto.vo.id }"
																						data-color="#${oggetto.vo.colore}"></option>
																				</c:forEach>
																			</c:if>
																		</form:select>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</form:form>

											<button form="categoriaForm" onclick="salvaCategoria()"
												class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float">
												<i class="zmdi zmdi-save"></i>
											</button>
											</div>
										</div>
										<!-- end tab 1 -->

										<div id="tab-2" role="tabpanel"
											class="tab-pane animated fadeIn ${tab2StyleAttiva}">

											<div id="modifyDeleteDiv">

												<div class="card">

													<div class="card-body">
														<div class="table-responsive">
															<table id="tabellaRicercaCategoria"
																class="table table-striped table-responsive">

															</table>
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
	

	<!-- FINE PANEL RICERCA MODALE -->
 

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>

	<jsp:include page="/parts/script-end.jsp"></jsp:include>
	<!-- si carica il js -->

	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/categoriaNewsletter.js"></script>
		
	<script>
	initTabellaRicercaCategoria(); 
	</script>
	
	<c:if test="${ categoriaView.colorselector != null }">
	<script>
	$('#colorselector').colorselector("setValue", ${ categoriaView.colorselector});
	
															</script>
	</c:if>
	
	<c:if test="${ categoriaView.colorselector == null }">
	<script>
	$('#colorselector').colorselector();
	</script>
	</c:if>
</body>

</html>