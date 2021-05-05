<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
									<spring:message text="??mailingList.label.inserimento??"
										code="mailingList.label.inserimento" />
								</h2>

							</div>
							<div class="card-body">

								<form:form name="mailingListForm" method="post"
									modelAttribute="mailingListView" action="salva.action"
									class="form-horizontal la-form" enctype="multipart/form-data">
                                    <engsecurity:token regenerate="false"/>
                                    
									<form:errors path="*" cssClass="alert alert-danger"
										element="div"></form:errors>
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
									<form:hidden path="mailingListId" />
									<form:hidden path="op" id="op" value="salvaMailingList" />
									<form:hidden path="editMode" id="editMode" value="false" />
									<form:hidden path="deleteMode" id="deleteMode" value="false" />
									<form:hidden path="insertMode" id="insertMode" value="true" />

									<div class="tab-content p-20">

										<div class="list-group lg-alt">

											<!-- NOME LISTA -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="nome" class="col-sm-2 control-label"><spring:message
																text="??mailingList.label.nome??"
																code="mailingList.label.nome" /></label>
														<div class="col-sm-10">
															<form:input path="nome" cssClass="form-control" />
														</div>
													</div>
												</div>
											</div>

											<!-- CATEGORIA -->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="categoriaCode" class="col-sm-2 control-label"><spring:message
																text="??mailingList.label.categoria??"
																code="mailingList.label.categoria" /></label>
														<div class="col-sm-10">
															<form:select id="categoriaCode" path="categoriaCode"
																cssClass="form-control" onchange="selezionaCategoria(this.value)">
																<form:option value="">
																	<spring:message
																		text="??mailingList.label.selezionaCategoria??"
																		code="mailingList.label.selezionaCategoria" />
																</form:option>
																<c:if
																	test="${ mailingListView.listaCategoriaMailingList != null }">
																	<c:forEach
																		items="${mailingListView.listaCategoriaMailingList}"
																		var="oggetto">
																		<form:option value="${ oggetto.vo.codGruppoLingua }">
																			<c:out value="${oggetto.vo.nomeCategoria}"></c:out>
																		</form:option>
																	</c:forEach>
																</c:if>

															</form:select>
														</div>
													</div>
												</div>
											</div>
											
										
									</div>

											<!-- RUBRICA-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="rubrica" class="col-sm-2 control-label"><spring:message
																text="??mailingList.label.rubrica??"
																code="mailingList.label.rubrica" /></label>
														<div class="col-sm-10">
															<div class="table-responsive" style="clear: both;">
																<table class="table table-striped table-responsive">
																	<thead>
																		<tr style="border: 1px solid #e0e0e0">
																			<th data-column-id="01" style="width: 50px">
																				<button type="button" data-toggle="collapse"
																					data-target="#boxRubrica"
																					class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																					style="float: left; position: relative !important;">
																					<i class="zmdi zmdi-collection-text icon-mini"></i>
																				</button>
																			</th>
																			<th data-column-id="id"><spring:message
																					text="??mailingList.label.rubrica??"
																					code="mailingList.label.rubrica" /></th>
																			<th></th>
																		</tr>
																	</thead>
																	<tbody id="boxRubrica" class="collapse ">
																		<c:if test="${ mailingListView.listaRubrica != null }">
																			<c:forEach items="${mailingListView.listaRubrica}"
																				var="oggetto">
																				<tr>
																					<td><form:checkbox value="${ oggetto.vo.id }"
																							path="nominativiAggiunti"></form:checkbox></td>
																					<td>${oggetto.vo.nominativo}</td>
																					<td>${oggetto.vo.email}</td>
																				</tr>
																			</c:forEach>
																		</c:if>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</form:form>
								<button form="mailingiListForm" onclick="salvaMailingList()"
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
		src="<%=request.getContextPath()%>/portal/js/controller/mailinglist.js"></script>
</body>
</html>
