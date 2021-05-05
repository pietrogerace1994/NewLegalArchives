<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<%
String CSRFToken = request.getParameter("CSRFToken");
%>

<form:form name="workflowForm" method="post"
	modelAttribute="stepWfView" action="salva.action"
	cssClass="form-horizontal la-form">
	<engsecurity:token regenerate="false"/>
	
	<form:errors path="*" cssClass="alert alert-danger" 
			element="div"></form:errors>
			<div class="alert alert-info" id="successOperationAtto" style="margin-bottom: 0px">
				<spring:message code="messaggio.operazione.ok" text="??messaggio.operazione.ok??"></spring:message>
			</div>											
			<div class="alert alert-danger" id="faultOperationAtto" style="margin-top: -40px">
				<spring:message code="errore.generico" text="??errore.generico??"></spring:message>
			</div>	
			<div class="alert alert-warning" id="rejectionReasonAtto" style="margin-top: -40px">
				<spring:message code="workflow.motivazione.obbligatoria" text="??workflow.motivazione.obbligatoria??"></spring:message>
			</div>											
	

	<div class="form-group">
		<div class="col-md-12" id="divStepViewAtto">
			<label for="note" class="col-sm-2 control-label"><spring:message
					text="??worflow.label.notaRifiuto??" code="worflow.label.notaRifiuto" /></label>
			<div class="col-sm-10">
				<form:textarea id="noteRifiutoWsAtto" path="note" cols="50" rows="5"
					cssClass="form-control" />
			</div>
		</div>
	</div>
	<c:if test="${ stepWfView.collaboratoriResponsabili != null }">
			<div class="form-group" id="destinatarioAttoResponsabile">
				<label class="col-md-3 control-label"
					for="destinatario"><spring:message text="??workflow.label.resp.presa.in.carico??" code="workflow.label.resp.presa.in.carico" /></label>
				<div class="col-md-8">
					<select id="destinatarioResponsabile" name="destinatarioResponsabile" 
						class="form-control">
						<c:forEach items="${stepWfView.collaboratoriResponsabili}" var="oggetto">
							<c:choose>
							<c:when test="${oggetto.vo.assente  eq 'T'}">
							<option value="${ oggetto.vo.matricolaUtil }" style="background:#ff0000;color:#ffffff">
								<c:out value="${oggetto.vo.nominativoUtil}"></c:out>   (ASSENTE)
							</option> 
							</c:when>						 
							<c:otherwise>
								<option value="${ oggetto.vo.matricolaUtil }" >
								<c:out value="${oggetto.vo.nominativoUtil}"></c:out>
							</option>
							</c:otherwise>
							</c:choose>
						</c:forEach>
						
					</select>
				</div>
			</div>
	</c:if>
	<c:if test="${ stepWfView.legaliInterni != null }">
			<div class="form-group" id="destinatarioAttoLegaleInterno">
				<label class="col-md-3 control-label"
					for="destinatario"><spring:message text="??workflow.label.li.presa.in.carico??" code="workflow.label.li.presa.in.carico" /></label>
				<div class="col-md-8">
					<select id="destinatarioLegaleInterno" name="destinatarioLegaleInterno" 
						class="form-control">
						<c:forEach items="${stepWfView.legaliInterni}" var="oggetto">
							<c:choose>
							<c:when test="${oggetto.vo.assente  eq 'T'}">
							<option value="${ oggetto.vo.matricolaUtil }" style="background:#ff0000;color:#ffffff">
								<c:out value="${oggetto.vo.nominativoUtil}"></c:out>   (ASSENTE)
							</option> 
							</c:when>						 
							<c:otherwise>
								<option value="${ oggetto.vo.matricolaUtil }" >
								<c:out value="${oggetto.vo.nominativoUtil}"></c:out>
							</option>
							</c:otherwise>
							</c:choose>
						</c:forEach>
						
					</select>
				</div>
			</div>
	</c:if>
	<c:if test="${ stepWfView.GC != null }">
			<div class="form-group" id="destinatarioAttoGC">
				<label class="col-md-3 control-label"
					for="destinatario"><spring:message text="??workflow.label.GC??" code="workflow.label.GC" /></label>
				<div class="col-md-8">
					<select id="destinatarioGC" name="destinatarioGC" 
						class="form-control">
						<c:forEach items="${stepWfView.GC}" var="oggetto">
						<c:choose>
							<c:when test="${oggetto.vo.assente  eq 'T'}">
							<option value="${ oggetto.vo.matricolaUtil }" style="background:#ff0000;color:#ffffff">
								<c:out value="${oggetto.vo.nominativoUtil}"></c:out>   (ASSENTE)
							</option> 
							</c:when>						 
							<c:otherwise>
								<option value="${ oggetto.vo.matricolaUtil }" >
								<c:out value="${oggetto.vo.nominativoUtil}"></c:out>
							</option>
							</c:otherwise>
							</c:choose>
						</c:forEach>
						
					</select>
				</div>
			</div>
	</c:if>
	
	<c:if test="${ stepWfView.idAtto  != 0 }">			
		<div class="form-group">	
			<a href="<%=request.getContextPath() %>/atto/visualizza.action?id=${stepWfView.idAtto }&azione=visualizza&CSRFToken=${CSRFToken}" class="edit" target="_blank">
									<i class="fa fa-eye" aria-hidden="true"></i>
									<spring:message text="??atto.label.visualizzaDati??"
													code="atto.label.visualizzaDati" />
			</a>
		</div>
	</c:if>
</form:form>
 