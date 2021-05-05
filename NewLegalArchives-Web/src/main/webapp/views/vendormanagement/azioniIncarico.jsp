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
		String coloreVoto = (String) request.getAttribute("coloreVoto");
		IncaricoService service = (IncaricoService) SpringUtil.getBean("incaricoService");
		IncaricoView view = service.leggi(idIncarico);
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		boolean isGestoreVendor = utenteConnesso.isGestoreVendor();
%>
	
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/incarico/dettaglioAll.action?id=<%=idIncarico%>')}"
			class="edit"> <i class="fa fa-eye" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.dettaglio??"
					code="fascicolo.label.dettaglio" />
		</a></li>
		
		<%
		if(!isGestoreVendor){
		%>
	
			<c:if test="${empty coloreVoto}">
					
					<li><a href="javascript:void(0)"
				onclick="votazioni_apriVotazioniModal(<%=idIncarico %>); return false;"
					class="edit"> <i class="fa fa-hand-peace-o" aria-hidden="true"></i> 
					<spring:message text="??vendormanagement.vota??" code="vendormanagement.vota" />
				</a></li>
			</c:if>
		
			<c:if test="${!empty coloreVoto && coloreVoto == 'Orange'}">
				
				<li><a href="javascript:void(0)"
					onclick="votazioni_apriVotazioniModalPerModifica(<%=idIncarico %>); return false;"
					class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> 
					<spring:message text="??vendormanagement.modificaVotazione??" code="vendormanagement.modificaVotazione" />
					</a>
				</li>
				
				
				<li>
					<a href="javascript:void(0)" data-target="#panelConfirmVotazione" data-idincarico="<%=idIncarico%>" data-toggle="modal" class="edit"> 
						<i class="fa fa-check-circle-o " aria-hidden="true"></i> 
						<spring:message text="??vendormanagement.confermaVotazione??" code="vendormanagement.confermaVotazione" />
					</a>
				</li>
			</c:if>
<%
		}
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>
