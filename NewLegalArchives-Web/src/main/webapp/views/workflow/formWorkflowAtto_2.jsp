<!-- ******************************************************** -->
<!-- DARIO ************************************************** -->
<!-- ******************************************************** -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<%
String CSRFToken = request.getParameter("CSRFToken");
%>
<div>

	<engsecurity:token regenerate="false"/>

 	<div  name='lista_assegnatari'>

		<jsp:include page="/views/gestioneUtenti/lista_assegnatari.jsp">
	
		</jsp:include>

	</div>

	<div class="alert alert-info" id="successOperationAtto" style="margin-bottom: 0px">
		<spring:message code="messaggio.operazione.ok" text="??messaggio.operazione.ok??"></spring:message>
	</div>											
	<div class="alert alert-danger" id="faultOperationAtto" style="margin-top: -40px">
		<spring:message code="errore.generico" text="??errore.generico??"></spring:message>
	</div>	
	<div class="alert alert-warning" id="rejectionReasonAtto" style="margin-top: -40px">
		<spring:message code="workflow.motivazione.obbligatoria" text="??workflow.motivazione.obbligatoria??"></spring:message>
	</div>	 	
	
	<div  id="divStepViewAtto">
        <label class="control-label" for="noteRifiutoWsAtto">
        	<spring:message	text="??worflow.label.notaRifiuto??" code="worflow.label.notaRifiuto" />
        </label>
       	<textarea class="form-control" rows="2" id="noteRifiutoWsAtto"></textarea>
    </div>

	<div  name="divSelezionaCollaboratoreAtto">
	
		<c:if test="${ stepWfView.collaboratoriResponsabili != null }">
				<div class="form-group" id="destinatarioAttoResponsabile">
					<label class="col-md-4 control-label"
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
					<label class="col-md-4 control-label"
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
					<label class="col-md-4 control-label"
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
		<br><br>
	</div>
	
	<c:if test="${ stepWfView.idAtto  != 0 }">			
		
		<div>
			<a  class="col-md-12" href="<%=request.getContextPath() %>/atto/visualizza.action?id=${stepWfView.idAtto }&azione=visualizza&CSRFToken=${CSRFToken}" class="edit" target="_blank">
									<i class="fa fa-eye" aria-hidden="true"></i>
									<spring:message text="??atto.label.visualizzaDati??"
													code="atto.label.visualizzaDati" />
			</a>
		</div>
	</c:if>


</div>


 