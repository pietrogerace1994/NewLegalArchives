<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>
  
<form:form name="pecOpForm" method="post"
	modelAttribute="notificaPecView" action="salva.action"
	cssClass="form-horizontal">
	<engsecurity:token regenerate="false"/>
	
	<form:errors path="*" cssClass="alert alert-danger" 
			element="div"></form:errors>
			<div class="alert alert-info" id="successOperationOp" style="margin-bottom: 0px">
				<spring:message code="messaggio.operazione.ok" text="??messaggio.operazione.ok??"></spring:message>
			</div>											
			<div class="alert alert-danger" id="faultOperationOp" style="margin-top: -40px">
				<spring:message code="errore.generico" text="??errore.generico??"></spring:message>
			</div>	
			<div class="alert alert-warning" id="rejectionReasonOp" style="margin-top: -40px">
				<spring:message code="pecMail.motivazione.obbligatoria" text="??pecMail.motivazione.obbligatoria??"></spring:message>
			</div>											

			<div class="form-group">
				<div class="form-group">
					<label class="col-md-2 control-label" for="txtMittenteOp">
						<spring:message text="??pecMail.label.mittente??"
							code="pecMail.label.mittente" />
					</label>
					<div class="col-md-8">
					<form:input path="txtMittenteOp" id="txtMittenteOp" cssClass="typeahead form-control input-md" readonly="true" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="txtDestinatarioOp">
						<spring:message text="??pecMail.label.destinatario??"
							code="pecMail.label.destinatario" />
					</label>
					<div class="col-md-8">
					<form:input path="txtDestinatarioOp" id="txtDestinatarioOp" cssClass="typeahead form-control input-md" readonly="true" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="txtOggettoOp">
						<spring:message text="??pecMail.label.oggetto??"
							code="pecMail.label.oggetto" />
					</label>
					<div class="col-md-8">
					<form:input path="txtOggettoOp" id="txtOggettoOp" cssClass="typeahead form-control input-md" readonly="true" />
					</div>
				</div>
				
				<div class="form-group" id="pecAttachDivOp" style="display: none;">
					<label class="col-md-2 control-label"></label>
					<div class="col-md-8">
						<button style="float:left" type="button" data-toggle="dropdown" id="download-pecAttachOp" uuid=""	class="btn btn-success dropdown-toggle">
						<spring:message text="??pecMail.button.scaricaPec??" code="pecMail.button.scaricaPec" /> 
						<i class="fa fa-arrow-circle-down"></i>
						</button>
					</div>
				</div>

				<div class="form-group" id="divPecOpView">
					<label for="noteOp" class="col-sm-2 control-label"><spring:message
							text="??pecMail.label.notaRifiuto??" code="pecMail.label.notaRifiuto" /></label>
					<div class="col-sm-8">
						<form:textarea id="noteRifiutoPecOp" path="noteOp" cols="50" rows="3"
							cssClass="form-control" />
					</div>
				</div>
			</div>
	
</form:form>
 