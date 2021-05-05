<%@page import="eng.la.business.workflow.StepWfService"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.StepWfView"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="java.util.List"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>

<%@page import="eng.la.business.UtentePecService"%>
<%@page import="eng.la.model.view.UtentePecView"%>

<%
UtenteView utenteConnesso = (UtenteView ) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
try{
 
	UtentePecService utentePecService = (UtentePecService) SpringUtil.getBean("utentePecService");
	List<UtentePecView> listaNotifichePecMail = utentePecService.leggiPecMail(utenteConnesso.getVo().getUseridUtil());
	request.getSession().setAttribute("listaNotifichePecMail",listaNotifichePecMail);

%>

<input type="hidden" name="hdnNroPec" id="hdnNroPec" value='<c:out value="${listaNotifichePecMail.size()}"/>'>

<c:if test="${not empty listaNotifichePecMail}">	
	<c:forEach items="${ listaNotifichePecMail }" var="notifichePec">
	
		<a id="notificaPec_<c:out value="${notifichePec.vo.id}"/>" href="#" class="list-group-item media" style="background-color: #eeeeee;">
		
			<div class="media-body" >
			
				<div class="lgi-heading" data-toggle="tooltip">
				<c:out value="${notifichePec.vo.pecMittente}"/>"
				</div>
				<small class="lgi-text" data-toggle="tooltip">
				<c:out value="${notifichePec.vo.pecOggetto}"/>"
				</small>
				<div class="notifiche-action" data-toggle="tooltip" title="">
						<button class="btn btn-icon" style="visibility:hidden">
							<span class="zmdi zmdi-check"></span>
						</button>
						<button class="btn btn-icon command-edit" data-toggle="modal" onclick="processaPec('<c:out value="${notifichePec.vo.id}"/>','<c:out value="${notifichePec.vo.pecMittente}"/>','<c:out value="${notifichePec.vo.pecDestinatario}"/>'
							,'<c:out value="${notifichePec.vo.pecOggetto}"/>','<c:out value="${notifichePec.vo.UUId}"/>')"
							data-target="#panelFormPec">
						<span class="zmdi zmdi-check"></span>
					</button>
				</div>
			</div>
		
		</a> 
	</c:forEach>	
</c:if>	
	
<%
}catch(Throwable e){
	e.printStackTrace(); 
}
%>


