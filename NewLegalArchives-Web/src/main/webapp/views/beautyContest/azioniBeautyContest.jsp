<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.BeautyContestView"%>
<%@page import="eng.la.business.BeautyContestService"%>
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
		Long idBc = NumberUtils.toLong(request.getAttribute("idBc") + "");
		BeautyContestService service = (BeautyContestService) SpringUtil.getBean("beautyContestService");
		BeautyContestView view = service.leggi(idBc);
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		boolean isAmministratore = utenteConnesso.isAmministratore();
		/* DARIO********************************************************************************************* */
		boolean displayOtherManagers = !utenteConnesso.isTopResponsabile() && !utenteConnesso.isTopHead();
		/* ************************************************************************************************** */
%>
 	<legarc:isAuthorized idEntita="<%=idBc %>" tipoEntita="<%=Costanti.TIPO_ENTITA_BEAUTYCONTEST %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_LETTURA %>">
	
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/beautyContest/dettaglio.action?id=<%=idBc%>')}"
			class="edit"> <i class="fa fa-eye" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.dettaglio??"
					code="fascicolo.label.dettaglio" />
		</a></li>
	</legarc:isAuthorized>
	
	<%
		if (view.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_AUTORIZZATO)) {
	%> 
		<legarc:isAuthorized idEntita="<%=idBc %>" tipoEntita="<%=Costanti.TIPO_ENTITA_BEAUTYCONTEST %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_LETTURA %>">
		<li>
			<a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/beautyContest/dettaglioConElencoRisposte.action?id=<%=idBc%>')}"
			 class="edit"> 
			<i class="fa fa-reply" aria-hidden="true"></i> 
			<spring:message text="??beautyContest.label.visualizzaElencoRisposte?" code="beautyContest.label.visualizzaElencoRisposte" />
		</a></li>
		</legarc:isAuthorized>
	<%
		}
	%>
	
	<%
		if (view.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_BOZZA) || view
					.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_AUTORIZZATO)) {
	%> 
	<legarc:isAuthorized idEntita="<%=idBc %>" tipoEntita="<%=Costanti.TIPO_ENTITA_BEAUTYCONTEST %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
		
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/beautyContest/modifica.action?id=<%=idBc%>')}"
			class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.modifica??" code="fascicolo.label.modifica" />
		</a></li>
	</legarc:isAuthorized>

	<%
		}
	%>
	
	<%
		if (view.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_BOZZA)) {
	%>
	<legarc:isAuthorized idEntita="<%=idBc %>" tipoEntita="<%=Costanti.TIPO_ENTITA_BEAUTYCONTEST %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idbc="<%=idBc%>" data-toggle="modal"
			data-target="#panelConfirmDeleteBeautyContest" class="delete"> <i
				class="fa fa-trash-o" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.elimina??" code="fascicolo.label.elimina" />
		</a></li>
		
	</legarc:isAuthorized>
	<%
		}
	%>
	
	<%if( utenteConnesso.isAmministratore() || utenteConnesso.getVo().getUseridUtil().equals(view.getVo().getLegaleInterno())) {%>
	 
		<li><a href="javascript:void(0)"
			data-idbc="<%=idBc%>" data-toggle="modal"
			data-target="#panelGestionePermessiBeautyContest" class="delete"> <i
				class="fa fa-user-plus" aria-hidden="true"></i> <spring:message
					text="??beautyContest.label.estendiPermessi??"
					code="beautyContest.label.estendiPermessi" />
		</a></li> 
		
	<%} %>
	
	<%
		if (view.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_AUTORIZZATO)) {
			
			if(view.getVo().getVincitore() == null) {
	%> 
	<legarc:isAuthorized idEntita="<%=idBc %>" tipoEntita="<%=Costanti.TIPO_ENTITA_BEAUTYCONTEST %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
		
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/beautyContest/dettaglioConVincitore.action?id=<%=idBc%>')}"
			class="edit"> <i class="fa fa-thumbs-o-up" aria-hidden="true"></i> <spring:message
					text="??beautyContest.label.segliIlVincitore?" code="beautyContest.label.segliIlVincitore" />
		</a></li>
	</legarc:isAuthorized>
	<%
			}
		}
	%>
	
	<%
		if (view.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_BOZZA)) {
	%>
	<legarc:isAuthorized idEntita="<%=idBc %>" tipoEntita="<%=Costanti.TIPO_ENTITA_BEAUTYCONTEST %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
		<%
			// Controllo owner BC
			String owner = view.getVo().getLegaleInterno();
			
			if(owner.equals(utenteConnesso.getVo().getUseridUtil())){
		%>
		<!-- DARIO *************************************************************************** -->
		<%-- <li><a href="javascript:void(0)"
			data-idbc="<%=idBc%>" data-toggle="modal"
			data-target="#panelConfirmAvviaWorkFlowBeautyContest" class="delete"> <i
				class="fa fa-line-chart" aria-hidden="true"></i> <spring:message
					text="??beautyContest.label.richiediAvvioWorkflow??"
					code="beautyContest.label.richiediAvvioWorkflow" />
		</a></li> --%>
		 <li><a onclick="workflowAvviaAutomatico('panelConfirmAvviaWorkFlowBeautyContest',<%=idBc%>)"
			 data-toggle="modal" class="delete"> <i
				class="fa fa-line-chart" aria-hidden="true"></i> 
				<spring:message	text="??ctxmenu.label.AvviaWorkflow??"
							code="ctxmenu.label.AvviaWorkflow" /></a>
			<%
			if (displayOtherManagers){
			%>
			<ul style="list-style-type:none">
				<li><a onclick="workflowAvviaManuale('panelConfirmAvviaWorkFlowBeautyContest',<%=idBc%>)" style="color:black" 
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
		if (!view.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_BOZZA)
					&& isAmministratore) {
	%>
	<legarc:isAuthorized idEntita="<%=idBc %>" tipoEntita="<%=Costanti.TIPO_ENTITA_BEAUTYCONTEST %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idbc="<%=idBc%>" data-toggle="modal"
			data-target="#panelConfirmRiportaWorkFlowBozzaBeautyContest" class="delete"> <i
				class="fa fa-fast-backward" aria-hidden="true"></i> <spring:message
					text="??beautyContest.label.richiediRiportaWorkflowBozza??"
					code="beautyContest.label.richiediRiportaWorkflowBozza" />
		</a></li>
	</legarc:isAuthorized>
	<%
		}

			if (!view.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_BOZZA) && !view
					.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_AUTORIZZATO)
					&& isAmministratore) {
	%>
	<legarc:isAuthorized idEntita="<%=idBc %>" tipoEntita="<%=Costanti.TIPO_ENTITA_BEAUTYCONTEST %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idbc="<%=idBc%>" data-toggle="modal"
			data-target="#panelConfirmArretraWorkFlowBeautyContest" class="delete"> <i
				class="fa fa-step-backward" aria-hidden="true"></i> <spring:message
					text="??beautyContest.label.richiediArretraWorkflow??"
					code="beautyContest.label.richiediArretraWorkflow" />
		</a></li>
	</legarc:isAuthorized>
	<%
		}
	%>
		<li>
			<a href="javascript:void(0)" onclick="stampaBeautyContest('<%=idBc%>')" class="edit">
				<i class="fa fa-print" aria-hidden="true"></i>
				<spring:message text="??beautyContest.button.stampa??" code="beautyContest.button.stampa" />
			</a>
		</li>
	<%
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>
