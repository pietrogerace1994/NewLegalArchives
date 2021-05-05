<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>
<%@ page contentType="text/html; charset=UTF-8" %>


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
									test="${ newsletterView.newsletterId == null || newsletterView.newsletterId == 0 }">
									<h2>
										<spring:message text="??newsletter.label.nuovaNewsletter??"
											code="newsletter.label.nuovaNewsletter" />
									</h2>
								</c:if>
								<c:if
									test="${ newsletterView.newsletterId  != null && newsletterView.newsletterId  > 0 }">
									<h2>
										<spring:message text="??newsletter.label.modificaNewsletter??"
											code="newsletter.label.modificaNewsletter" />
									</h2>
								</c:if>
							</div>
							<div class="card-body">

								<form:form name="newsletterForm" method="post"
									modelAttribute="newsletterView" action="salva.action"
									class="form-horizontal la-form" enctype="multipart/form-data">
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
									<form:hidden path="newsletterId" />
									<form:hidden path="op" id="op" value="salvaNewsletter" />

									<div class="list-group lg-alt">
										<!--NUMERO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for=numero class="col-sm-2 control-label"><spring:message
															text="??newsletter.label.numero??"
															code="newsletter.label.numero" /></label>
													<div class="col-sm-10">
														<form:textarea path="numero" name="numero"
															class="form-control" maxlength="500" readonly="true"/>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--TITOLO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="titolo" class="col-sm-2 control-label"><spring:message
															text="??newsletter.label.titolo??"
															code="newsletter.label.titolo" /></label>
													<div class="col-sm-10">
														<form:textarea path="titolo" name="titolo"
															class="form-control" maxlength="500" readonly="true"/>
													</div>
												</div>
											</div>
										</div>
									</div>


<%-- 									<div class="list-group lg-alt">
										<!--FILE UPLOAD-->
										<div class="list-group-item media">
											<div class="media-body">
												<c:if
													test="${ newsletterView.newsletterId == null || newsletterView.newsletterId == 0 }">
													<div class="form-group">
														<label for="copertina" class="col-sm-2 control-label"><spring:message
																text="??newsletter.label.aggiungiFile??"
																code="newsletter.label.aggiungiFile" /></label>
														<div class="col-sm-10">
															<input name="copertina" type="file" id="copertina"
																accept="image/*" /> <img id="copertinaImg" src="#"
																style="visibility: hidden" />
														</div>
													</div>
												</c:if>
												<c:if
													test="${ newsletterView.newsletterId  != null && newsletterView.newsletterId  > 0 }">
													<div class="form-group">
														<label for="copertina" class="col-sm-2 control-label"><spring:message
																text="??newsletter.label.modificaFile??"
																code="newsletter.label.modificaFile" /></label>
														<div class="col-sm-10">
															<input name="copertina" type="file" id="copertina"
																accept="image/*" /> <img id="copertinaImg" src="${newsletterView.copertinaMod}" style="width:300px;padding-top: 15px;"/>
														</div>
													</div>
												</c:if>
											</div>
										</div>
									</div> --%>

									<!-- ARTICOLI-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="articoli" class="col-sm-2 control-label"><spring:message
														text="??newsletter.label.elencoarticoli??"
														code="newsletter.label.elencoarticoli" /></label>
												<div class="col-sm-10">
													<div class="table-responsive" style="clear: both;">
														<table class="table table-striped table-responsive">
															<thead>
																<tr style="border: 1px solid #e0e0e0">
																	<th data-column-id="01" style="width: 50px">
																		<button type="button" data-toggle="collapse"
																			data-target="#boxArticoli"
																			class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																			style="float: left; position: relative !important;">
																			<i class="zmdi zmdi-collection-text icon-mini"></i>
																		</button>
																	</th>
																	<th data-column-id="id"><spring:message
																			text="??newsletter.label.articoli??"
																			code="newsletter.label.articoli" /></th>
																	<th></th>
																</tr>
															</thead>
															<tbody id="boxArticoli" class="collapse ">
																<c:if test="${ newsletterView.listaArticoli != null }">
																	<c:forEach items="${newsletterView.listaArticoli}"
																		var="oggetto">
																		<tr>
																			<td><form:checkbox value="${ oggetto.vo.id }"
																					path="articoliAggiunti" onclick="addArticolo('${ oggetto.vo.id }', '${fn:escapeXml( oggetto.vo.oggetto) }');"></form:checkbox></td>
																			<td>${oggetto.vo.oggetto}</td>
																			<td><font style="color: #${oggetto.vo.categoria.colore}; font-weight: bold">${oggetto.vo.categoria.nomeCategoria}</font></td>
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

									<!-- SCEGLI HIGHLIGHTS-->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="articoli" class="col-sm-2 control-label"><spring:message
														text="??newsletter.label.sceglihigh??"
														code="newsletter.label.sceglihigh" /></label>
												<div class="col-sm-5">
													<select class="form-control" id="comboHigh" name="comboHigh">
																	<option value="0">NESSUNO</option>
																	<c:forEach items="${newsletterView.articoliAggiunti}"
																		var="articolo">
																		<c:forEach items="${newsletterView.listaArticoli}" var="oggetto">
																		<c:if test="${oggetto.vo.id == articolo }">
																			<option value='${ oggetto.vo.id }'
																			<c:if test="${ oggetto.vo.id==newsletterView.comboHigh}">
																			selected
																			</c:if>
																			>${ oggetto.vo.oggetto }</option>
																		</c:if>
																		</c:forEach>
																	</c:forEach>
													</select>

												</div>
											</div>
										</div>
									</div>

									<!-- EMAIL -->
									<div class="list-group-item media">
										<div class="media-body">
											<div class="form-group">
												<label for="email" class="col-sm-2 control-label"><spring:message
														text="??newsletter.label.aggiungiEmail??"
														code="newsletter.label.aggiungiEmail" /></label>
												<div class="col-sm-10">
													<div class="table-responsive" style="clear: both;">
														<div id="divEmail">
															<div class="list-group-item media">
																<div class="media-body">
																	<div class="form-group">
																		<label for="email" class="col-sm-2 control-label"><spring:message
																				text="??professionistaEsterno.label.email??"
																				code="professionistaEsterno.label.email" /></label>
																		<div class="col-sm-9">
																			<form:input path="email[0]" cssClass="form-control" />
																		</div>
																		<button type="button" onclick="addEmail()"
																			class="btn palette-Green-SNAM bg waves-effect waves-float">
																			<span class="glyphicon glyphicon-plus"></span>
																		</button>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

								</form:form>
								
								

								<button form="newsletterForm" onclick="salvaNewsletter()"
									class="btn palette-Green-SNAM bg btn-float waves-circle waves-float">
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
		src="<%=request.getContextPath()%>/portal/js/controller/newsletter.js"></script>
	<script type="text/javascript">
	 $("#copertina").change(function(){
        readURL(this);
    });
	 </script>
	<c:if test="${newsletterView.email != null && fn:length(newsletterView.email) > 1 }">
		<c:set var="emailToAdd" value='${newsletterView.email}' scope="page"/>
		<c:set var="sizeEmail" value='${fn:length(newsletterView.email)}' scope="page"/>
			<script>
		        addEmailForm("${sizeEmail}", "${emailToAdd}");
		    </script>
		</c:if>
</body>
</html>
