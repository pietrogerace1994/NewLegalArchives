<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>

<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.ProformaView"%>
<%@page import="eng.la.business.ProformaService"%>
<%@page import="eng.la.model.RIncaricoProformaSocieta"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.lang.String"%>
<%@page import="eng.la.model.Incarico"%>
<%@page import="eng.la.model.Fascicolo"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<%
	try {
		Long idProforma = NumberUtils.toLong(request.getParameter("id") + "");
		ProformaService service = (ProformaService) SpringUtil.getBean("proformaService");
		ProformaView view = service.leggi(idProforma);
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		boolean isAmministratore = utenteConnesso.isAmministratore();
		boolean isLegaleInterno = utenteConnesso.isLegaleInterno();

		/* DARIO********************************************************************************************* */
		boolean displayOtherManagers = !utenteConnesso.isTopResponsabile() && !utenteConnesso.isTopHead();
		/* ************************************************************************************************** */
		
		if (view.getVo().getStatoProforma().getCodGruppoLingua().equals(Costanti.PROFORMA_STATO_BOZZA)
				&& view.getVo().getAnnoEsercizioFinanziario().compareTo(BigDecimal.ZERO) > 0) {
%>
<legarc:isAuthorized idEntita="<%=idProforma%>"
	tipoEntita="<%=Costanti.TIPO_ENTITA_PROFORMA%>"
	tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA%>">

	<%
		// Controllo owner fascicolo
					Set<RIncaricoProformaSocieta> rIncaricoProformaSocietas = view.getVo()
							.getRIncaricoProformaSocietas();
					Iterator<RIncaricoProformaSocieta> iter = rIncaricoProformaSocietas.iterator();
					RIncaricoProformaSocieta rIncaricoProformaSocieta = iter.hasNext() ? iter.next() : null;
					Incarico incarico = rIncaricoProformaSocieta.getIncarico();
					Fascicolo fascicolo = incarico.getFascicolo();
					String ownerFascicolo = fascicolo.getLegaleInterno();

					if (ownerFascicolo.equals(utenteConnesso.getVo().getUseridUtil())) {
	%>
	
		
		
		<!-- DARIO ****************************************************** 
		<button id="btnAvviaWorkFlowProformaForm" form="proformaForm"
			onclick="javascript:void(0)" data-toggle="modal"
			data-target="#panelConfirmAvviaWorkFlowProforma"
			class="btn palette-Green-SNAM bg waves-effect"
			style="float: right; margin-right: 40px; margin-top: 10px;">
			Avvia Workflow</button>
		-->
	    
	    <div class="dropup btn-group palette-Green-SNAM bg" id="btnAvviaWorkFlowProformaForm" form="proformaForm" style="float: right; margin-right: 40px; margin-top: 10px;">
			<button type="button" class="btn palette-Green-SNAM bg waves-effect"
					onclick="workflowAvviaAutomatico('panelConfirmAvviaWorkFlowProforma')">
	     			<spring:message text="??pulsante.label.AvviaWorkflow??" code="pulsante.label.AvviaWorkflow" />
			</button>
			
		<%
		if (displayOtherManagers){
		%>
			
			<button type="button" class="btn palette-Green-SNAM bg waves-effect dropdown-toggle dropdown-toggle-split" data-toggle="dropdown">
					<span class="caret"></span>
			</button>
			<ul class="dropdown-menu palette-Green-SNAM bg" role="menu" style="cursor:pointer";>
				<li><a onclick="workflowAvviaManuale('panelConfirmAvviaWorkFlowProforma')">
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


<div class="modal fade" id="panelConfirmAvviaWorkFlowProforma"
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
					text="??proforma.label.confermaRichiestaAvvioWorkflow??"
					code="proforma.label.confermaRichiestaAvvioWorkflow" />

				<!-- Button -->
				<div class="form-group">
					<label class="col-md-4 control-label"
						for="btnRichiediAvvioWorkflowProforma"></label>
					<div class="col-md-8">
						
						<!-- DARIO - cambiato name da btnRichiediAvvioWorkflowProforma a BTN_START_WORKFLOW********-->
											
						<button id ="btnRichiediAvvioWorkflowProforma"
							name="BTN_START_WORKFLOW" type="button"
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

