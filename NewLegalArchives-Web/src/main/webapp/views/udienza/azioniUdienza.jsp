<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.UdienzaView"%>
<%@page import="eng.la.business.UdienzaService"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="java.lang.String"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<%
	try {
		Long idUdienza = NumberUtils.toLong(request.getAttribute("idUdienza") + "");
		UdienzaService service = (UdienzaService) SpringUtil.getBean("udienzaService");
		UdienzaView view = service.leggi(idUdienza);
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		boolean isAmministratore = utenteConnesso.isAmministratore();
%>
 	<legarc:isAuthorized idEntita="<%=idUdienza %>" tipoEntita="<%=Costanti.TIPO_ENTITA_UDIENZA %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_LETTURA %>">
	
		<li><a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/udienza/dettaglio.action?id=<%=idUdienza%>')}"
			class="edit"> <i class="fa fa-eye" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.dettaglio??"
					code="fascicolo.label.dettaglio" />
		</a></li>
	</legarc:isAuthorized>
	
	<legarc:isAuthorized idEntita="<%=idUdienza %>" tipoEntita="<%=Costanti.TIPO_ENTITA_UDIENZA %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
		
		<li><a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/udienza/modifica.action?id=<%=idUdienza%>')}"
			class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.modifica??" code="fascicolo.label.modifica" />
		</a></li>
	</legarc:isAuthorized>

	<legarc:isAuthorized idEntita="<%=idUdienza %>" tipoEntita="<%=Costanti.TIPO_ENTITA_UDIENZA %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idudienza="<%=idUdienza%>" data-toggle="modal"
			data-target="#panelConfirmDeleteUdienza" class="delete"> <i
				class="fa fa-trash-o" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.elimina??" code="fascicolo.label.elimina" />
		</a></li>
		
	</legarc:isAuthorized>
	
	<li>
		<a href="javascript:void(0)" onclick="stampaUdienza('<%=idUdienza%>')" class="edit">
			<i class="fa fa-print" aria-hidden="true"></i>
			<spring:message text="??udienza.button.stampa??" code="udienza.button.stampa" />
		</a>
	</li>
	
	<%
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>
