<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.ProformaView"%>
<%@page import="eng.la.business.ProformaService"%>
<%@page import="eng.la.model.RIncaricoProformaSocieta"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.lang.String"%>
<%@page import="eng.la.model.Incarico"%>
<%@page import="eng.la.model.Fascicolo"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<%
	try {
		Long idProforma =  NumberUtils.toLong(request.getAttribute("idProforma") + "");
		ProformaService service = (ProformaService) SpringUtil.getBean("proformaService");
		ProformaView view = service.leggi( idProforma);
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		boolean isAmministratore = utenteConnesso.isAmministratore();
		boolean isLegaleInterno = utenteConnesso.isLegaleInterno();
		/* DARIO********************************************************************************************* */
		boolean displayOtherManagers = !utenteConnesso.isTopResponsabile() && !utenteConnesso.isTopHead();
		/* ************************************************************************************************** */
%>


<legarc:isAuthorized idEntita="<%=idProforma %>" tipoEntita="<%=Costanti.TIPO_ENTITA_PROFORMA%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_LETTURA %>">

	<li><a
		href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/proforma/dettaglio.action?id=<%=idProforma%>')}"
		class="edit"> <i class="fa fa-eye" aria-hidden="true"></i> <spring:message
				text="??fascicolo.label.dettaglio??" code="fascicolo.label.dettaglio" />
	</a></li>
</legarc:isAuthorized>
<%
	if (view.getVo().getStatoProforma().getCodGruppoLingua().equals(Costanti.PROFORMA_STATO_BOZZA)) {
%>
<legarc:isAuthorized idEntita="<%=idProforma %>" tipoEntita="<%=Costanti.TIPO_ENTITA_PROFORMA%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">

	<li><a
		href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/proforma/modifica.action?id=<%=idProforma%>')}"
		class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
				text="??fascicolo.label.modifica??" code="fascicolo.label.modifica" />
	</a></li>
	<%
	if (!view.getVo().getDaProfEsterno().equalsIgnoreCase("T")) {
	%>
	<li><a data-idproforma="<%=idProforma%>" data-toggle="modal"
		data-target="#panelConfirmDeleteProforma" class="delete"> <i
			class="fa fa-trash-o" aria-hidden="true"></i> <spring:message
				text="??fascicolo.label.elimina??" code="fascicolo.label.elimina" />
	</a></li>
	<% } %>
	
	<%
		// Controllo owner fascicolo
		Set<RIncaricoProformaSocieta> rIncaricoProformaSocietas = view.getVo().getRIncaricoProformaSocietas();
		Iterator<RIncaricoProformaSocieta> iter = rIncaricoProformaSocietas.iterator();
		RIncaricoProformaSocieta rIncaricoProformaSocieta = iter.hasNext() ? iter.next() : null;
		Incarico incarico = rIncaricoProformaSocieta.getIncarico();
		Fascicolo fascicolo = incarico.getFascicolo();
		String ownerFascicolo = fascicolo.getLegaleInterno();
		
		if(ownerFascicolo.equals(utenteConnesso.getVo().getUseridUtil())){
		
	%>
	
		<!-- DARIO *************************************************************************** -->
		<%-- <li><a data-idproforma="<%=idProforma%>" data-toggle="modal"
			data-target="#panelConfirmAvviaWorkFlowProforma" class="delete"> <i
				class="fa fa-line-chart" aria-hidden="true"></i> <spring:message
					text="??incarico.label.richiediAvvioWorkflow??"
					code="incarico.label.richiediAvvioWorkflow" />
		</a></li> --%>
		<li><a onclick="workflowAvviaAutomatico('panelConfirmAvviaWorkFlowProforma',<%=idProforma%>)"
			 data-toggle="modal" class="delete"> <i
				class="fa fa-line-chart" aria-hidden="true"></i> 
				<spring:message	text="??ctxmenu.label.AvviaWorkflow??"
							code="ctxmenu.label.AvviaWorkflow" /></a>
			<%
			if (displayOtherManagers){
			%>
			<ul style="list-style-type:none">
				<li><a onclick="workflowAvviaManuale('panelConfirmAvviaWorkFlowProforma',<%=idProforma%>)" style="color:black" 
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
	if (!view.getVo().getStatoProforma().getCodGruppoLingua().equals(Costanti.PROFORMA_STATO_BOZZA) && isAmministratore) {
%>
<legarc:isAuthorized idEntita="<%=idProforma %>" tipoEntita="<%=Costanti.TIPO_ENTITA_PROFORMA%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
	<li><a data-idproforma="<%=idProforma%>" data-toggle="modal"
	data-target="#panelConfirmRiportaWorkFlowBozzaProforma" class="delete"> <i
		class="fa fa-fast-backward" aria-hidden="true"></i> <spring:message
			text="??incarico.label.richiediRiportaWorkflowBozza??"
			code="incarico.label.richiediRiportaWorkflowBozza" />
	</a></li>
</legarc:isAuthorized>
<%
	}

		if (!view.getVo().getStatoProforma().getCodGruppoLingua().equals(Costanti.PROFORMA_STATO_BOZZA) && !view
				.getVo().getStatoProforma().getCodGruppoLingua().equals(Costanti.PROFORMA_STATO_AUTORIZZATO)
				&& isAmministratore) {
%>
<legarc:isAuthorized idEntita="<%=idProforma %>" tipoEntita="<%=Costanti.TIPO_ENTITA_PROFORMA%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
	<li><a data-idproforma="<%=idProforma%>" data-toggle="modal"
		data-target="#panelConfirmArretraWorkFlowProforma" class="delete"> <i
			class="fa fa-step-backward" aria-hidden="true"></i> <spring:message
				text="??incarico.label.richiediArretraWorkflow??"
				code="incarico.label.richiediArretraWorkflow" />
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
