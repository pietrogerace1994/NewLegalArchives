<!-- ******************************************************** -->
<!-- DARIO ************************************************** -->
<!-- ******************************************************** -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
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
	

	<div class="alert alert-info" id="successOperation" style="margin-bottom: 0px">
		<spring:message code="messaggio.operazione.ok" text="??messaggio.operazione.ok??"></spring:message>
	</div>											
	<div class="alert alert-danger" id="faultOperation" style="margin-top: -40px">
		<spring:message code="errore.generico" text="??errore.generico??"></spring:message>
	</div>	
	<div class="alert alert-warning" id="rejectionReason" style="margin-top: -40px">
		<spring:message code="workflow.motivazione.obbligatoria" text="??workflow.motivazione.obbligatoria??"></spring:message>
	</div>	 	

 	<div  id="divStepView">
        <label class="control-label" for="noteRifiutoWs">
        	<spring:message	text="??worflow.label.notaRifiuto??" code="worflow.label.notaRifiuto" />
        </label>
       	<textarea class="form-control" rows="2" id="noteRifiutoWs"></textarea>
    </div>
	
<br>

	<c:if test="${ stepWfView.idProforma  != 0 }">		
		<a href="<%=request.getContextPath() %>/proforma/dettaglio.action?id=${stepWfView.idProforma }&CSRFToken=${CSRFToken}" class="edit" target="_blank">
			<i class="fa fa-eye" aria-hidden="true"></i>
			<spring:message text="??proforma.label.dettaglioProforma??" code="proforma.label.dettaglioProforma" />
		</a>&nbsp
	</c:if>
	<c:if test="${ stepWfView.idArbitrato != 0 }">									
		<a href="<%=request.getContextPath() %>/incarico/dettaglioCollegioArbitrale.action?id=${stepWfView.idArbitrato }&CSRFToken=${CSRFToken}" class="edit" target="_blank">
			<i class="fa fa-eye" aria-hidden="true"></i>
			<spring:message text="??incarico.label.dettaglioCollegioArbitrale??" code="incarico.label.dettaglioCollegioArbitrale" />
		</a>&nbsp
	</c:if>
	<c:if test="${ stepWfView.idIncarico != 0 }">									
		<a href="<%=request.getContextPath() %>/incarico/dettaglio.action?id=${stepWfView.idIncarico }&CSRFToken=${CSRFToken}" class="edit" target="_blank">
			<i class="fa fa-eye" aria-hidden="true"></i>
			<spring:message text="??incarico.label.dettaglioIncarico??" code="incarico.label.dettaglioIncarico" />
		</a>&nbsp
	</c:if>
	<c:if test="${ stepWfView.idSchedaFr != 0 }">
		<c:if test="${ !stepWfView.verificatore }">									
			<a href="<%=request.getContextPath() %>/schedaFondoRischi/dettaglio.action?id=${stepWfView.idSchedaFr }&CSRFToken=${CSRFToken}" class="edit" target="_blank">
				<i class="fa fa-eye" aria-hidden="true"></i>
				<spring:message text="??schedaFondoRischi.label.dettaglioSchedaFondoRischi??" code="schedaFondoRischi.label.dettaglioSchedaFondoRischi" />
			</a>&nbsp
		</c:if>
		<c:if test="${ stepWfView.verificatore }">									
			<a href="<%=request.getContextPath() %>/schedaFondoRischi/modifica.action?id=${stepWfView.idSchedaFr }&CSRFToken=${CSRFToken}" class="edit" target="_blank">
				<i class="fa fa-edit" aria-hidden="true"></i>
				<spring:message text="??schedaFondoRischi.label.modificaScheda??" code="schedaFondoRischi.label.modificaScheda" />
			</a>&nbsp
		</c:if>
		<c:if test="${ stepWfView.storico }">
			
			<a href="<%=request.getContextPath()%>/schedaFondoRischi/stampaSchedaModifiche.action?id=${stepWfView.idSchedaFr }&CSRFToken=${CSRFToken}" class="edit" target="_blank">
				<i class="fa fa-edit" aria-hidden="true"></i>
				<spring:message text="??schedaFondoRischi.label.visualizzaVersionePrecedente??" code="schedaFondoRischi.label.visualizzaVersionePrecedente" />
			</a>&nbsp
		</c:if>
	</c:if>	
	<c:if test="${ stepWfView.idBeautyContest != 0 }">									
	<a href="<%=request.getContextPath() %>/beautyContest/dettaglio.action?id=${stepWfView.idBeautyContest }&CSRFToken=${CSRFToken}" class="edit" target="_blank">
		<i class="fa fa-eye" aria-hidden="true"></i>
		<spring:message text="??beautyContest.label.dettaglioBeautyContest??" code="beautyContest.label.dettaglioBeautyContest" />
	</a>&nbsp
	</c:if>
	<c:if test="${ stepWfView.idFascicolo  != 0 }">									
		<a href="<%=request.getContextPath() %>/fascicolo/dettaglio.action?id=${stepWfView.idFascicolo }&CSRFToken=${CSRFToken}" class="edit" target="_blank">
			<i class="fa fa-eye" aria-hidden="true"></i>
			<spring:message text="??fascicolo.label.dettaglioFascicolo??" code="fascicolo.label.dettaglioFascicolo" />
		</a>&nbsp
	</c:if>
	<c:if test="${ stepWfView.idProfessionista  != 0 }">									
		<a href="<%=request.getContextPath() %>/professionistaEsterno/visualizzaProfEst.action?idProf=${stepWfView.idProfessionista }&CSRFToken=${CSRFToken}" class="edit" target="_blank">
			<i class="fa fa-eye" aria-hidden="true"></i>
			<spring:message text="??professionistaEsterno.label.dettaglio??" code="professionistaEsterno.label.dettaglio" />
		</a>&nbsp
	</c:if> 

</div>

