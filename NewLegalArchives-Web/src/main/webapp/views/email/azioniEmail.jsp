<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="java.util.Set"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.RUtenteGruppo"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<%
	try {
		Long idEmail =  NumberUtils.toLong(request.getAttribute("idEmail") + "");
		UtenteView utenteConnesso = (UtenteView)session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
		boolean set = false;
		
		if(utenteConnesso.isAmministratore() || utenteService.leggiSeGestorePresidioNormativo(utenteConnesso))
			set=true;
%>
	
	<li><a
		href="<%=request.getContextPath()%>/articolo/dettaglio.action?id=<%=idEmail%>"
		class="edit"> <i class="fa fa-eye" aria-hidden="true"></i> <spring:message
				text="??fascicolo.label.dettaglio??" code="fascicolo.label.dettaglio" />
	</a></li>
	<% 
	if(set){
		%>
	<li><a
		href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/articolo/modifica.action?id=<%=idEmail%>')}"
		class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
				text="??fascicolo.label.modifica??" code="fascicolo.label.modifica" />
	</a></li>
	
	<li><a data-idemail="<%=idEmail%>" data-toggle="modal"
		data-target="#panelConfirmDeleteEmail" class="delete"> <i
			class="fa fa-trash-o" aria-hidden="true"></i> <spring:message
				text="??fascicolo.label.elimina??" code="fascicolo.label.elimina" />
	</a></li>
	<%} %>

<%
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>
