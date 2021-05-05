<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>

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
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


									<%
	try {
		Long idIncarico =NumberUtils.toLong(request.getParameter("id") + "");
		IncaricoService service = (IncaricoService) SpringUtil.getBean("incaricoService");
		IncaricoView view = service.leggi(idIncarico);
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		boolean isAmministratore = utenteConnesso.isAmministratore();
		
		/* DARIO********************************************************************************************* */
		boolean displayOtherManagers = !utenteConnesso.isTopResponsabile() && !utenteConnesso.isTopHead();
		/* ************************************************************************************************** */
		
		/* System.out.println("idIncarico "+ idIncarico);
		//System.out.println("view.getVo().getStatoIncarico().getCodGruppoLingua() "+ view.getVo().getStatoIncarico().getCodGruppoLingua());
		//System.out.println("Costanti.INCARICO_STATO_BOZZA "+ Costanti.INCARICO_STATO_BOZZA);
		*/
		
		if (view.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_BOZZA)) {
	%>
									<legarc:isAuthorized idEntita="<%=idIncarico %>"
										tipoEntita="<%=Costanti.TIPO_ENTITA_INCARICO %>"
										tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">


										<%
			// Controllo owner fascicolo
			Fascicolo fascicolo = view.getVo().getFascicolo();
			String ownerFascicolo = fascicolo.getLegaleInterno();
			
			
			//System.out.println("ownerFascicolo "+ ownerFascicolo);
			//System.out.println("utenteConnesso.getVo().getUseridUtil()) "+ utenteConnesso.getVo().getUseridUtil());

			if(ownerFascicolo.equals(utenteConnesso.getVo().getUseridUtil())){
		%>

			<!-- DARIO ****************************************************** 
			<button id="btnAvviaWorkFlowIncaricoForm" form="incaricoForm"
				onclick="javascript:void(0)" data-toggle="modal"
				data-target="#panelConfirmAvviaWorkFlowIncarico"
				class="btn palette-Green-SNAM bg waves-effect"
				style="float: right; margin-right: 40px; margin-top: 10px;">
				Avvia Workflow</button> 
			-->
		    
		    <div class="dropup btn-group palette-Green-SNAM bg" id="btnAvviaWorkFlowIncaricoForm" form="incaricoForm" style="float: right; margin-right: 40px; margin-top: 10px;">
				<button type="button" class="btn palette-Green-SNAM bg waves-effect"
						onclick="workflowAvviaAutomatico('panelConfirmAvviaWorkFlowIncarico')">
		     			<spring:message text="??pulsante.label.AvviaWorkflow??" code="pulsante.label.AvviaWorkflow" />
				</button>
				
				<%
				if (displayOtherManagers){
				%>
				
					<button type="button" class="btn palette-Green-SNAM bg waves-effect dropdown-toggle dropdown-toggle-split" data-toggle="dropdown">
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu palette-Green-SNAM bg" role="menu" style="cursor:pointer";>
						<li><a onclick="workflowAvviaManuale('panelConfirmAvviaWorkFlowIncarico')">
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


<div class="modal fade" id="panelConfirmAvviaWorkFlowIncarico" 
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
							text="??incarico.label.confermaRichiestaAvvioWorkflow??"
							code="incarico.label.confermaRichiestaAvvioWorkflow" />

						<!-- Button -->
						
						 <div class="form-group">
							<label class="col-md-4 control-label"
								for="btnRichiediAvvioWorkflowIncarico">
								
								
								</label>
							<div class="col-md-8">
								
								<!-- DARIO - cambiato name da btnRichiediAvvioWorkflowIncarico a BTN_START_WORKFLOW********-->
								<button id = "btnRichiediAvvioWorkflowIncarico" name="BTN_START_WORKFLOW" type="button"
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


