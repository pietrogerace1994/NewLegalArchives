<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld" %>
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

<style>
#tabellaStoricoRisultati {
    font-size: 80%;
    max-height:600px;overflow:auto;-ms-overflow-style: auto;
}
.onclickReport {
	cursor: pointer;
}
</style>

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
										Storico Parti Correlate
									</h2>
							</div>
							
							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
								<form:form id="parteCorrelataForm" name="parteCorrelataForm" method="post" modelAttribute="parteCorrelataView" 
											action="storico.action" class="form-horizontal la-form">
											
									<engsecurity:token regenerate="false"/>
									<form:errors path="*" cssClass="alert alert-danger" element="div"></form:errors>
									
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
											
									<form:hidden path="op" id="op" value="storicoParteCorrelata"/>
									
									<div class="tab-content p-20">
										<fieldset class="scheduler-border">

										<legend class="scheduler-border">
											Storico
										</legend>
									
										<!-- DATA 1 -->
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<div class="media-body media-body-datetimepiker">
													<div class="form-group">
														<label for="dataValidita" class="col-lg-2 control-label"><spring:message
																text="??parteCorrelata.label.dataInizio??"
																code="parteCorrelata.label.dataInizio" /></label>
														<div class="col-lg-3">
															 <form:input id="dataInizio"  path="dataInizio" cssClass="form-control date-picker"  />
														</div>
													</div>
												</div>
											</div>
										</div>
													
										<!-- DATA 2 -->
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<div class="media-body media-body-datetimepiker">
													<div class="form-group">
														<label for="dataValidita" class="col-lg-2 control-label"><spring:message
																text="??parteCorrelata.label.dataFine??"
																code="parteCorrelata.label.dataFine" /></label>
														<div class="col-lg-3">
															 <form:input id="dataFine"  path="dataFine" cssClass="form-control date-picker"  />
														</div>
														
														
													</div>
												</div>
											</div>
										</div>	
													
										<!-- button -->
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label class="col-lg-9 control-label"></label>
														<div class="col-lg-3">
															 <button data-loading-text="Carico..." type="submit"
															  class="btn palette-Green-SNAM bg btn-float2 waves-effect waves-circle waves-float btnCerca" >
															 	<i class="zmdi zmdi-search"></i>
															 </button>
														</div>
													</div>
													<br/>
													<br/>
												</div>
											</div>
										</div>
														
														
													
													
													
										<!-- STORICO RISULTATO -->
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														
		               								 <div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
														<div class="table-responsive" >
															<table id="tabellaStoricoRisultati" 
															data-search="true"
															data-pagination="true" 
															class="table table-striped table-responsive">
															 
										                    </table>
										               </div>
													  </div>
					               
													</div>
												</div>
											</div>
										</div>
													
									</fieldset>
								</div>		
								
							</form:form>
								
							</div>
					</div>
				</div>
			</div>
		</div>		
		<!--/ fine col-1 -->
	</section>

</section>

<footer>
	<jsp:include page="/parts/footer.jsp"></jsp:include>
</footer>
	
<jsp:include page="/parts/script-end.jsp"></jsp:include>
<!-- si carica il js -->
	
<script>
	
	<c:if test="${ empty parteCorrelataView.listaStoricoRisultatiJson }">
		var listaStoricoRisultatiJson = '';
	</c:if>
	
	<c:if test="${ parteCorrelataView.listaStoricoRisultatiJson != null }">
		var listaStoricoRisultatiJson = JSON.parse('${parteCorrelataView.listaStoricoRisultatiJson}');
	</c:if>
	
	var urlServer = '<%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>';
	
</script>
	
<script src="<%=request.getContextPath()%>/portal/js/controller/parteCorrelataStorico.js"></script>
	
</body>
	
</html>
