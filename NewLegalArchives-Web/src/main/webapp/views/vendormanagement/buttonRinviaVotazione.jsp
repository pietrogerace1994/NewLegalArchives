<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.IncaricoView"%>
<%@page import="eng.la.business.IncaricoService"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.FascicoloView"%>
<%@page import="java.lang.String"%>
<%@page import="eng.la.model.Fascicolo"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.FascicoloService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<%
	try {
		Long idIncarico =NumberUtils.toLong(request.getAttribute("idIncarico") + "");
		String title = (String) request.getAttribute("title");
		
		UtenteView utenteConnesso = (UtenteView ) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		
		if(!utenteConnesso.isGestoreVendor()){
%>

			<c:if test="${title == 'Rinviabile'}">
				<div class="pull-left">
					<a class="btn btn-primary" style="background-color:#e7d785;" 
					onclick="rinviaVotazione(<%=idIncarico %>); return false;"
					class="edit"> <i class="fa fa-forward" aria-hidden="true"></i> 
					<spring:message text="??vendormanagement.rinviaVotazione??" code="vendormanagement.rinviaVotazione" />
					</a>
				</div>
			</c:if>
			
			<c:if test="${title == 'Rinviata'}">
				<div class="pull-left">
					<a class="btn btn-primary" style="background-color:#e7d785; cursor:not-allowed;" 
					onclick="return false;" class="edit"> <i class="fa fa-pause" aria-hidden="true"></i> 
					<spring:message text="??vendormanagement.votazioneRinviata??" code="vendormanagement.votazioneRinviata" />
					</a>
				</div>
			</c:if>
<%
		}
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>
