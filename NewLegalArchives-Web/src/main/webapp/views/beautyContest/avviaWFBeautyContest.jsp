<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>

<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.BeautyContestView"%>
<%@page import="eng.la.business.BeautyContestService"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="java.lang.String"%>
<%@page import="eng.la.model.BeautyContest"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


									<%
	try {
		Long idBeautyContest = NumberUtils.toLong(request.getParameter("id") + "");
		BeautyContestService service = (BeautyContestService) SpringUtil.getBean("beautyContestService");
		BeautyContestView view = service.leggi(idBeautyContest);
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		boolean isAmministratore = utenteConnesso.isAmministratore();

		/* DARIO********************************************************************************************* */
		boolean displayOtherManagers = !utenteConnesso.isTopResponsabile() && !utenteConnesso.isTopHead();
		/* ************************************************************************************************** */
		
		if (view.getVo().getStatoBeautyContest().getCodGruppoLingua().equals(Costanti.BEAUTYCONTEST_STATO_BOZZA)) {
			%>
			<legarc:isAuthorized idEntita="<%=idBeautyContest %>"
				tipoEntita="<%=Costanti.TIPO_ENTITA_BEAUTYCONTEST %>"
				tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
			<%
			// Controllo owner beauty contest
			String owner = view.getVo().getLegaleInterno();
		
			if(owner.equals(utenteConnesso.getVo().getUseridUtil())){
			%>
					<!-- DARIO ****************************************************** 
					<button id="btnAvviaWorkFlowBeauryContestForm" form="beautyContestForm"
						onclick="javascript:void(0)" data-idbc="<%=idBeautyContest%>" data-toggle="modal"
						data-target="#panelConfirmAvviaWorkFlowBeautyContest"
						class="btn palette-Green-SNAM bg waves-effect"
						style="float: right; margin-right: 40px; margin-top: 10px;">
						Avvia Workflow</button>
					-->

									    
				     <div class="dropup btn-group palette-Green-SNAM bg" id="btnAvviaWorkFlowBeauryContestForm" data-idbc="<%=idBeautyContest%>" form="beautyContestForm" style="float: right; margin-right: 40px; margin-top: 10px;">
						<button type="button" class="btn palette-Green-SNAM bg waves-effect"
								onclick="workflowAvviaAutomatico('panelConfirmAvviaWorkFlowBeautyContest',<%=idBeautyContest%>)">
				     			<spring:message text="??pulsante.label.AvviaWorkflow??" code="pulsante.label.AvviaWorkflow" />
						</button>
						
					<%
					if (displayOtherManagers){
					%>
						
						<button type="button" class="btn palette-Green-SNAM bg waves-effect dropdown-toggle dropdown-toggle-split" data-toggle="dropdown">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu palette-Green-SNAM bg" role="menu" style="cursor:pointer";>
							<li><a onclick="workflowAvviaManuale('panelConfirmAvviaWorkFlowBeautyContest',<%=idBeautyContest%>)">
								<span class="palette-White text">
									<spring:message text="??pulsante.label.AvviaWorkflow.selezionaResponsabile??" code="pulsante.label.AvviaWorkflow.selezionaResponsabile" />
								</span>
							</a></li>
				    	</ul>
				    	
			    	<%
					}
			    	%>
				    	
				    </div>
				    
				    <!-- ****************************************************************** -->
					
	
			<%
			}
			%>
			</legarc:isAuthorized>
		<%
		}
	} catch (Throwable e) {
	
		e.printStackTrace();
	}
%>



<div class="modal fade" id="panelConfirmAvviaWorkFlowBeautyContest"
			tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header alert alert-warning">
				<h4 class="modal-title">
					<spring:message text="??fascicolo.label.attenzione??"
						code="fascicolo.label.attenzione" />
				</h4>
			</div>
			<div class="modal-body">
				<!-- DARIO*********************************************************** -->
					<div  name='lista_assegnatari'>
					</div>
					 <input name="wf_user_code_sel" value=""  style="display:none"/>
					 <input name="wf_code_sel" value=""  style="display:none"/>
				<!-- **************************************************************** -->
				<spring:message
					text="??beautyContest.label.confermaRichiestaAvvioWorkflow??"
					code="beautyContest.label.confermaRichiestaAvvioWorkflow" />

				<!-- Button -->
				<div class="form-group">
					<label class="col-md-4 control-label"
						for="btnRichiediAvvioWorkflowBeautyContest"></label>
					<div class="col-md-8">
						<!-- DARIO - cambiato name da btnRichiediAvvioWorkflowBeautyContest a BTN_START_WORKFLOW********-->
						<button id="btnRichiediAvvioWorkflowBeautyContest" name="BTN_START_WORKFLOW" type="button"
							data-dismiss="modal" onclick="" class="btn btn-primary">
							<spring:message text="??fascicolo.label.ok??"
								code="fascicolo.label.ok" />
						</button>
						<button name="singlebutton" type="button" data-dismiss="modal"
							class="btn btn-warning">
							<spring:message text="??fascicolo.label.chiudi??"
								code="fascicolo.label.chiudi" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


