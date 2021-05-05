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
									test="${ emailView.emailId == null || emailView.emailId == 0 }">
									<h2>
										<spring:message text="??email.label.nuovaEmail??"
											code="email.label.nuovaEmail" />
									</h2>
								</c:if>
								<c:if
									test="${ emailView.emailId  != null && emailView.emailId  > 0 }">
									<h2>
										<spring:message text="??email.label.modificaEmail??"
											code="email.label.modificaEmail" />
									</h2>
								</c:if>
							</div>
							<div class="card-body">

								<form:form name="emailForm" method="post"
									modelAttribute="emailView" action="salva.action"
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
									<form:hidden path="emailId" />
									<form:hidden path="op" id="op" value="salvaEmail" />

									 

									<div class="list-group lg-alt">
										<!--OGGETTO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="oggetto" class="col-sm-2 control-label"><spring:message
															text="??email.label.oggetto??"
															code="email.label.oggetto" /></label>
													<div class="col-sm-10">
														<form:input path="oggetto" name="oggetto" class="form-control"  maxlength="500"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div class="list-group lg-alt">
										<!--CONTENUTO BREVE-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??email.label.contenutoBreve??"
															code="email.label.contenutoBreve" /></label>
													<div class="col-sm-10">
														<form:input path="contenutoBreve" name="contenutoBreve" class="form-control" id="contenutoBreve" maxlength="500"/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div class="list-group lg-alt">
										<!--CONTENUTO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??email.label.contenuto??"
															code="email.label.contenuto" /></label>
													<div class="col-sm-10">
														<form:textarea path="contenuto" cssClass="form-control" id="contenuto" />
													</div>
												</div>
											</div>
										</div>
									</div>
									 
									<div class="list-group lg-alt">
										<!--DATA EMAIL -->
										<div class="list-group-item media">
											<div class="media-body media-body-datetimepiker">
												<div class="form-group">
													<label class="col-md-2 control-label" for="dataEmail"><spring:message
															text="??email.label.dataEmail??"
															code="email.label.dataEmail" /></label>
													<div class="col-md-10">
														<form:input id="txtDataEmail" path="dataEmail"
															cssClass="form-control date-time-picker" />
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="list-group lg-alt">
										<!--CATEGORIA-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="categoria" class="col-sm-2 control-label"><spring:message
															text="??email.label.categoria??"
															code="email.label.categoria" /></label>
													<div class="col-sm-10">
														<form:select path="categoriaCode"
															cssClass="form-control" onchange="selezionaCategoria(this.value)">
															<form:option value="">
																<spring:message
																	text="??email.label.selezionaCategoria??"
																	code="email.label.selezionaCategoria" />
															</form:option>
															<c:if
																test="${ emailView.categorie != null }">
																<c:forEach items="${ emailView.categorie}"
																	var="oggetto"> 
																	<form:option value="${ oggetto.vo.codGruppoLingua }">
																		<c:out
																			value="${oggetto.vo.nomeCategoria}"></c:out>
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
										<!--SOTTOCATEGORIA-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="categoria" class="col-sm-2 control-label"><spring:message
															text="??email.label.sottocategoria??"
															code="email.label.sottocategoria" /></label>
													<div class="col-sm-10">
														<form:select path="sottoCategoriaCode" id="sottoCategoriaCode"
															cssClass="form-control">
															<form:option value="">
																<spring:message
																	text="??email.label.selezionaCategoria??"
																	code="email.label.selezionaCategoria" />
															</form:option>
															<c:if
																test="${ emailView.sottocategorie != null }">
																<c:forEach items="${ emailView.sottocategorie}"
																	var="oggetto"> 
																	<form:option value="${ oggetto.vo.codGruppoLingua }">
																		<c:out
																			value="${oggetto.vo.nomeCategoria}"></c:out>
																	</form:option> 
																</c:forEach>
															</c:if>
														</form:select>
													</div>
												</div>
											</div>
										</div>
									</div>

									<c:if test="${ emailView.filesPresenti != null }">
									<div class="list-group lg-alt">
										<!--FILE PRESENTI-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??email.label.file??" code="email.label.file" /></label>
													<div class="col-sm-10">
														<table id="fileEliminaTable">
														<c:forEach items="${ emailView.filesPresenti}" var="file"> 
															<tr>
															<td style="padding-right: 10px;padding-bottom:8px;">${ file.nome }</td>
																<td style="padding-right: 10px;padding-bottom:8px;">
																<input type="checkbox" data-toggle="toggle" data-on="Eliminato" data-off="Elimina" data-onstyle="danger" data-offstyle="success" name="documentiDaEliminare" id="documentiDaEliminare" value="${ file.uuid }">
																</td>
															</tr>
														</c:forEach>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>
									</c:if>
									<div class="list-group lg-alt">
										<!--FILE UPLOAD-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??email.label.aggiungiFile??" code="email.label.aggiungiFile" /></label>
													<div class="col-sm-10">
														<!-- <input id="addFile" class="btn btn-primary btn-lg" value="Aggiungi File"/> -->
														<button type="button" class="btn btn-primary" id="addFile">
															<spring:message text="??email.label.aggiungi??"
																code="email.label.aggiungi" />
														</button>
														<table id="fileTable">
															<tr>
															<br><br>
																<td><input name="files[0]" type="file" /></td>
															</tr>
														</table>
													</div>
												</div>
											</div>
										</div>
									</div>

								</form:form>

								<button form="emailForm" onclick="salvaEmail()"
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
		src="<%=request.getContextPath()%>/portal/js/controller/email.js"></script>
	
 	<script type="text/javascript">
	if( document.getElementById("contenuto") ){
	   CKEDITOR.replace( 'contenuto' );
	}
	

			$(document)
					.ready(
							function() {
								
								$("label").toggleClass( "waves-effect" );
								$("span").toggleClass( "waves-effect" );
								//add more file components if Add is clicked
								$('#addFile')
										.click(
												function() {
													var fileIndex = $('#fileTable tbody').children().length;
													
													$('#fileTable')
															.append(
																	'<tr><td>'
																			+ '	<input type="file" name="files['+ fileIndex +']" />'
																			+ '</td></tr>');
												});

							});
		</script>
 	
  
</body>
</html>
