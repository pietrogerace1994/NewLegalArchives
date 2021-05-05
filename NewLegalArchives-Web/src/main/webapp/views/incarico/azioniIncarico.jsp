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
		IncaricoService service = (IncaricoService) SpringUtil.getBean("incaricoService");
		IncaricoView view = service.leggi(idIncarico);
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		boolean isAmministratore = utenteConnesso.isAmministratore();
		/* DARIO********************************************************************************************* */
		boolean displayOtherManagers = !utenteConnesso.isTopResponsabile() && !utenteConnesso.isTopHead();
		/* ************************************************************************************************** */
		
%>
   <%-- <!-- engsecurity VA --><form method="post" name="formToken"><engsecurity:token regenerate="false"/> --%>
 	<legarc:isAuthorized idEntita="<%=idIncarico %>" tipoEntita="<%=Costanti.TIPO_ENTITA_INCARICO %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_LETTURA %>">
	
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/incarico/dettaglio.action?id=<%=idIncarico%>')}"
			class="edit"> <i class="fa fa-eye" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.dettaglio??"
					code="fascicolo.label.dettaglio" />
		</a></li>
	</legarc:isAuthorized>
	<%
		if (view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_BOZZA) || view
					.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_AUTORIZZATO)) {
	%> 
	<legarc:isAuthorized idEntita="<%=idIncarico %>" tipoEntita="<%=Costanti.TIPO_ENTITA_INCARICO %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
		
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/incarico/modifica.action?id=<%=idIncarico%>')}"
			class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.modifica??" code="fascicolo.label.modifica" />
		</a></li>
	</legarc:isAuthorized>

	<%
		}
	%>

	<%
		if (view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_BOZZA)) {
	%>
	<legarc:isAuthorized idEntita="<%=idIncarico %>" tipoEntita="<%=Costanti.TIPO_ENTITA_INCARICO %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idincarico="<%=idIncarico%>" data-toggle="modal"
			data-target="#panelConfirmDeleteIncarico" class="delete"> <i
				class="fa fa-trash-o" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.elimina??" code="fascicolo.label.elimina" />
		</a></li>
		
		<%
			// Controllo owner fascicolo
			Fascicolo fascicolo = view.getVo().getFascicolo();
			String ownerFascicolo = fascicolo.getLegaleInterno();
			
			if(ownerFascicolo.equals(utenteConnesso.getVo().getUseridUtil())){
		%>
	
		<!-- DARIO *************************************************************************** -->
		<%-- <li><a href="javascript:void(0)"
			data-idincarico="<%=idIncarico%>" data-toggle="modal"
			data-target="#panelConfirmAvviaWorkFlowIncarico" class="delete"> <i
				class="fa fa-line-chart" aria-hidden="true"></i> 
				<spring:message	text="??incarico.label.richiediAvvioWorkflow??"
							code="incarico.label.richiediAvvioWorkflow" /></a>
		</li> --%>
		
		 <li>
		 	<a onclick="workflowAvviaAutomatico('panelConfirmAvviaWorkFlowIncarico',<%=idIncarico%>)"
			 data-toggle="modal" class="delete"> <i
				class="fa fa-line-chart" aria-hidden="true"></i> 
				<spring:message	text="??ctxmenu.label.AvviaWorkflow??"
							code="ctxmenu.label.AvviaWorkflow" /></a>
							
			<%
			if (displayOtherManagers){
			%>				
							
			<ul style="list-style-type:none">
				<li><a onclick="workflowAvviaManuale('panelConfirmAvviaWorkFlowIncarico',<%=idIncarico%>)" style="color:black" 
					data-toggle="modal" class="delete"> <i
					class=""  aria-hidden="true"></i> 
					<spring:message	text="??ctxmenu.label.AvviaWorkflow.selezionaResponsabile??" 
						code="ctxmenu.label.AvviaWorkflow.selezionaResponsabile" />
					</a>
				</li> 
			</ul>
			<%
			}
		    %>
		</li>
		<!-- ********************************************************************************** -->
		
		
		<%
			}
		%>
	
	</legarc:isAuthorized>
	<%
		}
	%>

	<%
		if (!view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_BOZZA)
					&& isAmministratore) {
	%>
	<legarc:isAuthorized idEntita="<%=idIncarico %>" tipoEntita="<%=Costanti.TIPO_ENTITA_INCARICO %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idincarico="<%=idIncarico%>" data-toggle="modal"
			data-target="#panelConfirmRiportaWorkFlowBozzaIncarico" class="delete"> <i
				class="fa fa-fast-backward" aria-hidden="true"></i> <spring:message
					text="??incarico.label.richiediRiportaWorkflowBozza??"
					code="incarico.label.richiediRiportaWorkflowBozza" />
		</a></li>
	</legarc:isAuthorized>
	<%
		}

			if (!view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_BOZZA) && !view
					.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_AUTORIZZATO)
					&& isAmministratore) {
	%>
	<legarc:isAuthorized idEntita="<%=idIncarico %>" tipoEntita="<%=Costanti.TIPO_ENTITA_INCARICO %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idincarico="<%=idIncarico%>" data-toggle="modal"
			data-target="#panelConfirmArretraWorkFlowIncarico" class="delete"> <i
				class="fa fa-step-backward" aria-hidden="true"></i> <spring:message
					text="??incarico.label.richiediArretraWorkflow??"
					code="incarico.label.richiediArretraWorkflow" />
		</a></li>
	</legarc:isAuthorized>
	<%
		}

			if (view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_AUTORIZZATO)
					&& view.getVo().getFascicolo().getTipologiaFascicolo().getCodGruppoLingua()
							.equals(Costanti.TIPOLOGIA_FASCICOLO_GIUDIZIALE_COD)
					&& view.getVo().getFascicolo().getSettoreGiuridico().getCodGruppoLingua()
							.equals(Costanti.SETTORE_GIURIDICO_ARBITRALE_CODE)) {
	%>
	<legarc:isAuthorized idEntita="<%=idIncarico %>" tipoEntita="<%=Costanti.TIPO_ENTITA_INCARICO %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/incarico/creaCollegioArbitrale.action?incaricoId=<%=idIncarico%>')}"
			class="edit"> <i class="fa fa-plus" aria-hidden="true"></i> <spring:message
					text="??incarico.label.aggiungiCollegioArbitrale??"
					code="incarico.label.aggiungiCollegioArbitrale" />
		</a></li>
	</legarc:isAuthorized>
	<%
		}
			if (view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_AUTORIZZATO)) {
	%>
	<legarc:isAuthorized idEntita="<%=idIncarico %>" tipoEntita="<%=Costanti.TIPO_ENTITA_INCARICO %>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
		
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/proforma/crea.action?incaricoId=<%=idIncarico%>')}"
			class="edit"> <i class="fa fa-plus" aria-hidden="true"></i> <spring:message
					text="??incarico.label.aggiungiProforma??"
					code="incarico.label.aggiungiProforma" />
		</a></li>
	</legarc:isAuthorized>
	<%
		}
	%> 
<%
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>
