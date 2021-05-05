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

		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						<div class="card">
							
							<input type="hidden" id="idFascicoloDaAssociare" value="<%= request.getSession().getAttribute("idFascicoloDaAssociare")!=null?request.getSession().getAttribute("idFascicoloDaAssociare").toString():"" %>">
						
							<% if (!request.getAttribute("nomeProgettoAssociato").toString().equals("nonAssociato")) { %>
						
								<div id="FascicoloGiaAssociato"
								class="alert alert-info">
								<spring:message code="fascicolo.fascicoloGiaAssociato"
									text="??fascicolo.fascicoloGiaAssociato??"><%= " <strong>"+request.getAttribute("nomeProgettoAssociato").toString()+"</strong> " %></spring:message>
								</div>

							<% } %>
							
							<div style="display: none;" id="AssociaFascicoloAProgetto_OK"
							class="alert alert-info">
							<spring:message code="messaggio.operazione.ok"
								text="??messaggio.operazione.ok??"></spring:message>
							</div>
							<div style="display: none;" id="AssociaFascicoloAProgetto_KO"
							class="alert alert-danger">
							<spring:message code="errore.generico" text="??errore.generico??"></spring:message>
							</div>
							
						
							<div class="card-header">
								 	<p class="text-left" style="display:block">
										<spring:message text="??atto.label.ricercaCustomAttoStart??" code="atto.label.ricercaCustomAttoStart" /> 
										<a data-toggle="modal" href="#popupmodalRicercaProgetto" class="" >
										   <spring:message text="??atto.label.ricercaCustomAttoEnd??" code="atto.label.ricercaCustomAttoEnd" />
										</a>
									</p>
							</div>
							
							<div class="card-body">
								<div id="sezioneMessaggiApplicativi"></div>
								<div class="table-responsive">
									<table id="data-table-progetto" class="table table-striped table-responsive"></table>
								</div>
							
							</div>

							
						</div>
						
					</div>
					
				</div>
				
			</div>

	   
	   </section>
		
	   
	</section>


</body>
</html>
