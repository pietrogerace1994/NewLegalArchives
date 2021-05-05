<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
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
								<h2>
									<spring:message text="??email.label.modificaEmail??"
										code="email.label.dettaglioEmail" />
								</h2> 
							</div>
							<div class="card-body">

							<form:form name="emailForm" method="post"
									modelAttribute="emailView" action="salva.action"
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
									<form:hidden path="emailId" />
									<form:hidden path="op" id="op" value="salvaEmail" />
  
									<div class="list-group lg-alt">
										<!--OGGETTO-->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="nome" class="col-sm-2 control-label"><spring:message
															text="??email.label.oggetto??"
															code="email.label.oggetto" /></label>
													<div class="col-sm-10">
														<form:textarea path="oggetto" cssClass="form-control" disabled="true" />
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
														<form:textarea path="contenutoBreve" class="form-control"  maxlength="500" disabled="true"/>
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
														<form:textarea path="contenuto" cssClass="form-control" id="contenuto" disabled="true" />
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
															cssClass="form-control date-picker" readonly="true" />
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
														<form:select path="categoriaCode" disabled="true"
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
														<form:select path="sottoCategoriaCode" id="sottoCategoriaCode"  disabled="true"
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


								</form:form>

<%-- 								<button form="emailForm" data-idemail="${emailView.emailId }" data-toggle="modal"
									data-target="#panelInviaEmail" 
									class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float">
									<i class="fa fa-paper-plane-o"></i>
								</button> --%>
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
	   CKEDITOR.config.readOnly = true;
	   CKEDITOR.replace( 'contenuto' );
	   
	}
	</script>
 	
	<div class="modal fade" id="panelInviaEmail" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title"> 
						<spring:message text="??email.label.inviaEmail??"
							code="email.label.inviaEmail" />
					</h4>
				</div>
				<div class="modal-body" style="min-height: 200px; overflow: auto;height: 80%;">
					<div class="form-group">
						<div class="col-md-12">
							<table class="table table-striped table-responsive table-hover" id="tableInviaEmail">
								<tr>
									<th style="width:10%"><input type="checkbox" id="checkAll" onclick="checkAll('checkRubrica', 'checkAll' )"></th>
									<th><spring:message text="??email.label.nominativo??"
											code="email.label.nominativo" /></th>
									<th><spring:message text="??email.label.indirizzo.email??"
											code="email.label.indirizzo.email?" /></th>
									<th><spring:message text="??email.label.categoria??"
											code="email.label.categoria" /></th>
								</tr>
									
								<tbody id="tBodyInviaEmail"> 							
								</tbody> 
							</table>
						</div>
					</div>
					 
					<!-- Button -->
					<div class="form-group"> 
						<div class="col-md-8">
							<button id="btnInviaEmail" name="btnInviaEmail" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
 	<script type="text/javascript">
		$('#panelInviaEmail').on('show.bs.modal', function(e) { 
			caricaMailingList($(e.relatedTarget).data('idemail'));
		    $(this).find("#btnInviaEmail").attr('onclick', "inviaEmail()");
		});
	</script>
</body>
</html>
