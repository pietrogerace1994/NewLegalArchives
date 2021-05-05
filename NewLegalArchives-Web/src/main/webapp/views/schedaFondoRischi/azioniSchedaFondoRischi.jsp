<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.SchedaFondoRischiView"%>
<%@page import="eng.la.business.SchedaFondoRischiService"%>
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
		Long idScheda = NumberUtils.toLong(request.getAttribute("idScheda") + "");
		SchedaFondoRischiService service = (SchedaFondoRischiService) SpringUtil.getBean("schedaFondoRischiService");
		SchedaFondoRischiView view = service.leggi(idScheda);
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		boolean isAmministratore = utenteConnesso.isAmministratore();
		/* DARIO********************************************************************************************* */
		boolean displayOtherManagers = !utenteConnesso.isTopResponsabile() && !utenteConnesso.isTopHead();
		/* ************************************************************************************************** */
%>
 	<legarc:isAuthorized idEntita="<%=idScheda %>" tipoEntita="<%=Costanti.TIPO_ENTITA_SCHEDA_FONDO_RISCHI %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_LETTURA %>">
	
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/schedaFondoRischi/dettaglio.action?id=<%=idScheda%>')}"
			class="edit"> <i class="fa fa-eye" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.dettaglio??"
					code="fascicolo.label.dettaglio" />
		</a></li>
	</legarc:isAuthorized>
	<%
		if (view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_BOZZA) || view
					.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO)) {
	%> 
	<legarc:isAuthorized idEntita="<%=idScheda %>" tipoEntita="<%=Costanti.TIPO_ENTITA_SCHEDA_FONDO_RISCHI %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
		
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/schedaFondoRischi/modifica.action?id=<%=idScheda%>')}"
			class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.modifica??" code="fascicolo.label.modifica" />
		</a></li>
	</legarc:isAuthorized>

	<%
		}
	%>
	
	<%
		if (view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_BOZZA) || view
					.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO)) {
	%> 
	<legarc:isAuthorized idEntita="<%=idScheda %>" tipoEntita="<%=Costanti.TIPO_ENTITA_SCHEDA_FONDO_RISCHI %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idscheda="<%=idScheda%>" data-toggle="modal"
			data-target="#panelConfirmDeleteSchedaFondoRischi" class="delete"> <i
				class="fa fa-trash-o" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.elimina??" code="fascicolo.label.elimina" />
		</a></li>
		
	</legarc:isAuthorized>
	<%
		}
	%>

	<%
		if (view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_BOZZA)) {
			// Controllo owner fascicolo
			Fascicolo fascicolo = view.getVo().getFascicolo();
			String ownerFascicolo = fascicolo.getLegaleInterno();
			
			if(ownerFascicolo.equals(utenteConnesso.getVo().getUseridUtil())){
		%>
		<!-- DARIO *************************************************************************** -->
		<%-- <li><a href="javascript:void(0)"
			data-idscheda="<%=idScheda%>" data-toggle="modal"
			data-target="#panelConfirmAvviaWorkFlowSchedaFondoRischi" class="delete"> <i
				class="fa fa-line-chart" aria-hidden="true"></i> <spring:message
					text="??schedaFondoRischi.label.richiediAvvioWorkflow??"
					code="schedaFondoRischi.label.richiediAvvioWorkflow" />
		</a></li> --%>
		 <li><a onclick="workflowAvviaAutomatico('panelConfirmAvviaWorkFlowSchedaFondoRischi',<%=idScheda%>)"
				data-toggle="modal" class="delete"> <i
				class="fa fa-line-chart" aria-hidden="true"></i> 
				<spring:message	text="??ctxmenu.label.AvviaWorkflow??"
							code="ctxmenu.label.AvviaWorkflow" /></a>
			<%
			if (displayOtherManagers){
			%>
			<ul style="list-style-type:none">
				<li><a onclick="workflowAvviaManuale('panelConfirmAvviaWorkFlowSchedaFondoRischi',<%=idScheda%>)" style="color:black" 
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
		}
	%>

	<%
		if (!view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_BOZZA)
					&& isAmministratore) {
	%>
	<legarc:isAuthorized idEntita="<%=idScheda %>" tipoEntita="<%=Costanti.TIPO_ENTITA_SCHEDA_FONDO_RISCHI %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idscheda="<%=idScheda%>" data-toggle="modal"
			data-target="#panelConfirmRiportaWorkFlowBozzaSchedaFondoRischi" class="delete"> <i
				class="fa fa-fast-backward" aria-hidden="true"></i> <spring:message
					text="??schedaFondoRischi.label.richiediRiportaWorkflowBozza??"
					code="schedaFondoRischi.label.richiediRiportaWorkflowBozza" />
		</a></li>
	</legarc:isAuthorized>
	<%
		}

			if (!view.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_BOZZA) && !view
					.getVo().getStatoSchedaFondoRischi().getCodGruppoLingua().equals(Costanti.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO)
					&& isAmministratore) {
	%>
	<legarc:isAuthorized idEntita="<%=idScheda %>" tipoEntita="<%=Costanti.TIPO_ENTITA_SCHEDA_FONDO_RISCHI %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idscheda="<%=idScheda%>" data-toggle="modal"
			data-target="#panelConfirmArretraWorkFlowSchedaFondoRischi" class="delete"> <i
				class="fa fa-step-backward" aria-hidden="true"></i> <spring:message
					text="??schedaFondoRischi.label.richiediArretraWorkflow??"
					code="schedaFondoRischi.label.richiediArretraWorkflow" />
		</a></li>
	</legarc:isAuthorized>
	<%
		}
	%>
		<li>
			<a href="javascript:void(0)" onclick="stampaScheda('<%=idScheda%>')" class="edit">
				<i class="fa fa-print" aria-hidden="true"></i>
				<spring:message text="??schedaFondoRischi.button.stampa??" code="schedaFondoRischi.button.stampa" />
			</a>
		</li>
	<%
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>
