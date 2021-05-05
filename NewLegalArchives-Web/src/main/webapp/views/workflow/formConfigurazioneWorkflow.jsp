<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
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
								<h2 style="font-size: 150%;">
									Configurazione Workflow
								</h2>
							</div>
							<div class="card-body">

								<form:form name="configurazioneWorkflowForm" 
									id="configurazioneWorkflowForm"
									method="post"
									modelAttribute="configurazioneWorkflowView" action="salva.action"
									class="form-horizontal la-form">
									<engsecurity:token regenerate="false"/>
							
									<c:if test="${ not empty successMessage }">									
										<div class="alert alert-info">
											<spring:message code="messaggio.operazione.ok" text="??messaggio.operazione.ok??"></spring:message>
										</div>											
									</c:if>	
									<c:if test="${ not empty param.errorMessage }">									
										<div class="alert alert-danger">
											<spring:message code="${param.errorMessage}" text="??${param.errorMessage}??"></spring:message>
										</div>											
									</c:if>	
									
									<form:hidden path="topResponsabileMatricola" id="topResponsabileMatricola" />
									<form:hidden path="matricolaApprovatore" id="matricolaApprovatore" />
									<form:hidden path="idApprovatore" id="idApprovatore" />
									<form:hidden path="idAutorizzatore" id="idAutorizzatore" />
									<form:hidden path="emailAutorizzatore" id="emailAutorizzatore" />
									<form:hidden path="emailApprovatore" id="emailApprovatore" />
									<form:hidden path="idAutorizzatoreRigaWf1" id="idAutorizzatoreRigaWf1" />
									<form:hidden path="idAutorizzatoreRigaWf2" id="idAutorizzatoreRigaWf2" />
									<form:hidden path="idAutorizzatoreRigaWf3" id="idAutorizzatoreRigaWf3" />
									<form:hidden path="idAutorizzatoreRigaWf4" id="idAutorizzatoreRigaWf4" />
									<form:hidden path="idApprovatoreRigaWf1" id="idApprovatoreRigaWf1" />
									<form:hidden path="idApprovatoreRigaWf2" id="idApprovatoreRigaWf2" />
									<form:hidden path="idApprovatoreRigaWf3" id="idApprovatoreRigaWf3" />
									<form:hidden path="idApprovatoreRigaWf4" id="idApprovatoreRigaWf4" />
									
						<div class="tab-content p-20">
									<fieldset class="scheduler-border">

										<!-- Autorizzatore -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="autorizzatore" class="col-sm-4 control-label">Autorizzatore</label>
													<div class="col-sm-8">
														<form:input path="topResponsabileNomeCognome" id="topResponsabileNomeCognome" cssClass="form-control" />
														<i id="topResponsabileNomeCognomeIcon" class="zmdi zmdi-edit animated infinite wobble zmdi-hc-fw hidden" style="position: absolute;top: 10px;right: 20px;"></i>
													</div>
												</div>
											</div>
										</div>
										
										<!-- Approvatore -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="approvatore" class="col-sm-4 control-label">Approvatore in seconda firma</label>
													<div class="col-sm-8">
														<form:input path="approvatoreNomeCognome" id="approvatoreNomeCognome" cssClass="form-control" />
														<i id="approvatoreNomeCognomeIcon" class="zmdi zmdi-edit animated infinite wobble zmdi-hc-fw hidden" style="position: absolute;top: 10px;right: 20px;"></i>
													</div>
												</div>
											</div>
										</div>
										
										
									</fieldset>
									
					   </div> <!-- END tab -->


								</form:form>
								
								<button form="configurazioneWorkflowForm" type="submit" id="btnSalva"
									class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float hidden">
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

	<script type="text/javascript">
	
	<c:if test="${ configurazioneWorkflowView.autorizzatoreStatoWf == true }">
		var autorizzatoreIsInattesa = true;
	</c:if>
	<c:if test="${ configurazioneWorkflowView.autorizzatoreStatoWf == false }">
		var autorizzatoreIsInattesa = false;
	</c:if>
	
	<c:if test="${ empty configurazioneWorkflowView.listaAutorizzatoriJson }">
		var listaAutorizzatoriJson = '';
	</c:if>
	<c:if test="${ not empty configurazioneWorkflowView.listaAutorizzatoriJson }">
		var listaAutorizzatoriJson = JSON.parse('${configurazioneWorkflowView.listaAutorizzatoriJson}');
	</c:if>
	
	var topResponsabileMatricola = '${configurazioneWorkflowView.topResponsabileMatricola}';
	
	<c:if test="${ empty configurazioneWorkflowView.listaAutorizzatoriJson }">
		var listaApprovatoriJson = '';
	</c:if>
	<c:if test="${ not empty configurazioneWorkflowView.listaApprovatoriJson }">
		var listaApprovatoriJson = JSON.parse('${configurazioneWorkflowView.listaApprovatoriJson}');
	</c:if>
	
	var matricolaApprovatore = '${configurazioneWorkflowView.matricolaApprovatore}';
	
	<c:if test="${ configurazioneWorkflowView.approvatoreStatoWf == true }">
		var approvatoreIsInattesa = true;
	</c:if>
	<c:if test="${ configurazioneWorkflowView.approvatoreStatoWf == false }">
		var approvatoreIsInattesa = false;
	</c:if>

	</script>
	
	<script	charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/configurazioneWorkflow.js"></script>
	
	
<!-- MODAL AUTORIZZATORE AVVISO -->	
<div class="modal fade"  role="dialog" id="modalAutorizzatoreAvviso">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <b class="modal-title" style="font-size:90%;">Avviso</b>
      </div>
      <div class="modal-body" style="text-align:center;">
        <span class="label label-danger" style="font-size: 100%;">
        	Impossibile cambiare autorizzatore.
        </span>
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- MODAL AUTORIZZATORE SCELTA -->	
<div class="modal fade"  role="dialog" id="modalAutorizzatoreScelta">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <b class="modal-title" style="font-size:90%;">Autorizzatore</b>
      </div>
      <div class="modal-body">
        
        	<div class="alert alert-danger hidden" id="errorMsgNessunaSelezioneAutorizzatore">
					Nessuna selezione
			</div>
        
        	<form:form name="configurazioneWorkflowForm" method="post"
									modelAttribute="configurazioneWorkflowView" action=""
									class="form-horizontal la-form">
									
									
									<!-- LISTA AUTORIZZATORI -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="sceltaAutorizzatore" class="col-sm-4 control-label">Scegli un nuovo autorizzatore</label>
													<div class="col-sm-8">
														
									
									
												<form:select 
													size="1" 
													path="sceltaAutorizzatore" id="sceltaAutorizzatore"
													cssClass="form-control">
														 
												</form:select> 
				
				
													</div>
												</div>
											</div>
										</div>
				
				
				
				</form:form>
				
				

      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary" data-dismiss="modal" style="background-color:orange !important;" id="btnApplicaAutorizzatore">Applica</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">Chiudi</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<!-- MODAL APPROVATORE AVVISO -->	
<div class="modal fade"  role="dialog" id="modalApprovatoreAvviso">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <b class="modal-title" style="font-size:90%;">Avviso</b>
      </div>
      <div class="modal-body"  style="text-align:center;">
        <span class="label label-danger"  style="font-size: 100%;">
        	Impossibile cambiare approvatore in seconda firma.
		</span>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Chiudi</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- MODAL APPROVATORE SCELTA -->	
<div class="modal fade"  role="dialog" id="modalApprovatoreScelta">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <b class="modal-title" style="font-size:90%;">Approvatore</b>
      </div>
      <div class="modal-body">
        
        	<div class="alert alert-danger hidden" id="errorMsgNessunaSelezioneApprovatore">
					Nessuna selezione
			</div>
        
        
        	<form:form name="configurazioneWorkflowForm" method="post"
									modelAttribute="configurazioneWorkflowView" action=""
									class="form-horizontal la-form">
									
									
									<!-- LISTA APPROVATORE -->
										<div class="list-group-item media">
											<div class="media-body">
												<div class="form-group">
													<label for="sceltaApprovatore" class="col-sm-4 control-label">Scegli un nuovo approvatore</label>
													<div class="col-sm-8">
														
												<form:select 
													size="1" 
													path="sceltaApprovatore" id="sceltaApprovatore"
													cssClass="form-control">
														 
												</form:select> 
				
				
													</div>
												</div>
											</div>
										</div>
				
				
				</form:form>
				
				

      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary" data-dismiss="modal" style="background-color:orange !important;" id="btnApplicaApprovatore">Applica</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">Chiudi</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
	
</body>
</html>
