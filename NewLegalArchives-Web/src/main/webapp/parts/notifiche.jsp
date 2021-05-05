<%@page import="eng.la.business.workflow.StepWfService"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.StepWfView"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<%
UtenteView utenteConnesso = (UtenteView ) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
if( utenteConnesso != null ){
%>
<script src="<%=request.getContextPath()%>/portal/js/controller/workflow.js"></script>
		
<!-- DARIO ************************************** -->
<script src="<%=request.getContextPath()%>/vendors/jquery/jquery.min.js"></script>
<script  src="<%=request.getContextPath()%>/portal/js/controller/lista_assegnatari.js"></script>

<!-- ******************************************* -->
		
	<ul class="tab-nav tn-justified tn-icon m-t-10" data-tab-color="teal">
		<li >
			<a class="sua-notifications" href="#sua-notifications"		
				data-toggle="tab"><i class="fa fa-bell-o" aria-hidden="true"></i>				
			</a>		
		</li>
		<li><a id="avanzamentoFascicoli" class="sua-tasks" href="#sua-tasks" data-toggle="tab"><i
				class="fa fa-tasks" aria-hidden="true"></i></a></li>
		<li><a id="agendaMessaggi" class="sua-messages" href="#sua-messages"
			data-toggle="tab"><i class="fa fa-calendar-o" aria-hidden="true"></i></a></li>
		<li><a id="pecMail" class="sua-mail" href="#sua-mail"
			data-toggle="tab"><i class="fa fa-envelope" aria-hidden="true"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade" id="sua-messages">
			<ul class="sua-menu list-inline list-unstyled palette-Light-Blue bg">
				<li><a href=""><i class="fa fa-flag-checkered"
						aria-hidden="true"></i> seleziona tutti</a></li>
				<li><a href=""><i class="fa fa-eye" aria-hidden="true"></i>
						vedi tutte</a></li>
				<li><a href="" data-ma-action="sidebar-close"><i
						class="fa fa-times-circle" aria-hidden="true"></i> chiudi</a></li>
			</ul>
			<div id="containerNotificheAgenda" class="list-group lg-alt c-overflow">
			
			</div>
		</div>
		<div class="tab-pane fade" id="sua-notifications">
			<ul class="sua-menu list-inline list-unstyled palette-Orange bg">
				<li><a href=""><i class="fa fa-microphone-slash"
						aria-hidden="true"></i> non disturbare</a></li>
				<li><a href=""><i class="fa fa-eye" aria-hidden="true"></i>
						vedi tutte</a></li>
				<li><a href="" data-ma-action="sidebar-close"><i
						class="fa fa-times-circle" aria-hidden="true"></i> chiudi</a></li>
			</ul>
			<div class="list-group lg-alt c-overflow" >
			 	<div id="containerAttivitaPendenti">	
					<jsp:include page="/parts/notifichePendenti.jsp"></jsp:include>
				</div>
			
			</div>
		</div>
		<div class="tab-pane fade" id="sua-tasks">
			<ul class="sua-menu list-inline list-unstyled palette-Green-SNAM bg">
				<li><a href=""><i class="fa fa-flag-checkered"
						aria-hidden="true"></i> completati</a></li>
				<li><a href=""><i class="fa fa-check-square-o"
						aria-hidden="true"></i> seleziona tutti</a></li>
				<li><a href="" data-ma-action="sidebar-close"><i
						class="fa fa-times-circle" aria-hidden="true"></i> chiudi</a></li>
			</ul>

			<div class="list-group lg-alt" id="containerAvanzamentoFascicolo" style="position: relative; overflow-wrap: normal;white-space: pre;">
				<a href="#" id="open-fascicolo" class="">
					<div class="list-group-item">
						<div class="lgi-heading m-b-5">
							Fascicolo N.234570 <small class="pull-right">entro il 28
								luglio</small>
						</div>
						<div class="progress">
							<div class="progress-bar progress-bar-danger" role="progressbar"
								aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
								style="width: 80%">
								<span class="sr-only">80% Complete (danger)</span>
							</div>
						</div>
					</div>
				</a>
				<div class="list-group-item">
					<div class="lgi-heading m-b-5">
						Fascicolo N.234567 <small class="pull-right">entro il 30
							giugno</small>
					</div>
					<div class="progress">
						<div class="progress-bar" role="progressbar" aria-valuenow="30"
							aria-valuemin="0" aria-valuemax="100" style="width: 30%">
							<span class="sr-only">30% Complete (success)</span>
						</div>
					</div>
				</div>
				<div class="list-group-item">
					<div class="lgi-heading m-b-5">
						Fascicolo N.234568 <small class="pull-right">entro il 30
							giugno</small>
					</div>
					<div class="progress">
						<div class="progress-bar progress-bar-success" role="progressbar"
							aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
							style="width: 80%">
							<span class="sr-only">80% Complete (success)</span>
						</div>
					</div>
				</div>
				<div class="list-group-item">
					<div class="lgi-heading m-b-5">
						Fascicolo N.234569 <small class="pull-right">entro il 2
							luglio</small>
					</div>

					<div class="progress">
						<div class="progress-bar progress-bar-warning" role="progressbar"
							aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
							style="width: 20%">
							<span class="sr-only">20% Complete</span>
						</div>
					</div>
				</div>

			</div>
		</div>
		
		<div class="tab-pane fade" id="sua-mail">
			<ul class="sua-menu list-inline list-unstyled palette-Green-SNAM bg">
				<li><a href="" data-ma-action="sidebar-close"><i
						class="fa fa-times-circle" aria-hidden="true"></i> chiudi</a></li>
			</ul>
			 
			<div class="list-group lg-alt c-overflow" >
			 	<div id="containerPecMail">	
					<jsp:include page="/parts/notifichePecMail.jsp"></jsp:include>
				</div>
			
			</div>

		</div>
		
		
<!-- 		<div class="tab-pane fade" id="sua-shortcuts">
			<ul class="sua-menu list-inline list-unstyled palette-Green-SNAM bg">
				<li><a href="" data-ma-action="sidebar-close"><i
						class="fa fa-times-circle" aria-hidden="true"></i> chiudi</a></li>
			</ul>
			 
			<a href="" class="list-group-item media">
				<div class="media-body">
					<div class="lgi-heading">Fast Track</div>
					<small class="lgi-text">Avvia l'iter di un codice 700</small>
				</div>
			</a>

		</div> -->

	</div>
	
	<!-- PANEL NOTIFICHE PEC -->
	
	<div class="modal fade" id="panelFormPec" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						 <spring:message text="??pecMail.label.finestra??"
							code="pecMail.label.finestra" />
					</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<input type="hidden" name="hdnIdPec" id="hdnIdPec"  value = ''/>
						
						<fieldset>
							<div class="form-group">
								<label class="col-md-4 control-label" for="PecMittente">
									<spring:message text="??pecMail.label.mittente??"
										code="pecMail.label.mittente" />
								</label>
								<div class="col-md-8">
									<input id="txtMittente" name="PecMittente" type="text" disabled
										class="typeahead form-control input-md">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="PecDestinatario">
									<spring:message text="??pecMail.label.destinatario??"
										code="pecMail.label.destinatario" />
								</label>
								<div class="col-md-8">
									<input id="txtDestinatario" name="PecDestinatario" type="text" disabled
										class="typeahead form-control input-md">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="PecOggetto">
									<spring:message text="??pecMail.label.oggetto??"
										code="pecMail.label.oggetto" />
								</label>
								<div class="col-md-8">
									<input id="txtOggetto" name="PecOggetto" type="text" disabled
										class="typeahead form-control input-md">
								</div>
							</div>
							
							<div class="form-group" id="pecAttachDiv" style="display: none;">
								<label class="col-md-4 control-label"></label>
								<div class="col-md-8">
									<button style="float:left" type="button" data-toggle="dropdown" id="download-pecAttach" uuid=""	class="btn btn-success dropdown-toggle">
									<spring:message text="??pecMail.button.scaricaPec??" code="pecMail.button.scaricaPec" /> 
									<i class="fa fa-arrow-circle-down"></i>
									</button>
								</div>
							</div>
							
							<div class="form-group" id="divAltriUffView">
								<label for="utenteAltriUff" class="col-sm-4 control-label"><spring:message
										text="??pecMail.label.utenteInvioAltriUffici??" code="pecMail.label.utenteInvioAltriUffici" /></label>
								<div class="col-sm-8">
									<input id="txtUtenteAltriUff" name="utenteAltriUff" type="text"
										class="typeahead form-control input-md" />
								</div>
								<label for="emailAltriUff" class="col-sm-4 control-label"><spring:message
										text="??pecMail.label.emailInvioAltriUffici??" code="pecMail.label.emailInvioAltriUffici" /></label>
								<div class="col-sm-8">
									<input id="txtEmailAltriUff" name="emailAltriUff" type="text"
										class="typeahead form-control input-md" />
								</div>
								
							</div>
							
						</fieldset>
					</form>
				</div>
 			    <div class="modal-footer">
				<!-- Button -->  
					<button id="btnTrasformaPec" name="btnTrasformaPec" type="button" onclick="trasformaPec()" data-dismiss="modal"
						class="btn btn-primary">
						<spring:message text="??pecMail.label.trasforma??"
							code="pecMail.label.trasforma" />
					</button>
					<button id="btnSpostaProtPec" name="btnSpostaProtPec" type="button" onclick="spostProtPec()" data-dismiss="modal"
						class="btn btn-primary">
						<spring:message text="??pecMail.label.spostaProt??"
							code="pecMail.label.spostaProt" />
					</button>
					<button id="btnInviaAltriUffPec" name="btnInviaAltriUffPec" type="button" onclick="inviaAltriUffPec()"
						class="btn btn-primary">
						<spring:message text="??pecMail.label.inviaAltriUff??"
							code="pecMail.label.inviaAltriUff" />
					</button>
					<button id="btnAnnullaPec" name="btnAnnullaPec" type="button" onclick="annullaPec()" data-dismiss="modal"
						class="btn btn-danger">
						<spring:message text="??generici.label.annulla??"
							code="generici.label.annulla" />
					</button>
<%-- 					<button name="btnChiudiPanelPec" type="button" onclick="chiudiPanelPec()" data-dismiss="modal"
						class="btn btn-warning">
						<spring:message text="??generici.label.chiudi??"
							code="generici.label.chiudi" />
					</button> --%>
					 
		</div>
			</div>
		</div>
	</div>
	
	<form id="downloadPecAttach" action="<%=request.getContextPath() %>/download" method="get" style="display:none">	
	<engsecurity:token regenerate="false"/>
	<input type="hidden" name="uuid" id="uuid" value="">
	<input type="hidden" name="onlyfn" id="onlyfn" value="1">
	<input type="hidden" name="isp" id="isp" value="1">
	<input type="submit" value="" style="display:none">
	</form>
	
	<!-- FINE PANEL NOTIFICHE PEC -->
	
	<!-- PANEL NOTIFICHE PEC OP -->
	
	<div class="modal fade" id="panelFormPecOp" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??pecMail.label.finestraOp??"
							code="pecMail.label.finestraOp" />
					</h4>
				</div>
				<div class="modal-body">
				<input type="hidden" name="hdnIdPecOp" id="hdnIdPecOp"  value = ''/>
				<input type="hidden" name="hdnIdNotificaPecOp" id="hdnIdNotificaPecOp"  value = ''/>
				<input type="hidden" name="txtUuidPec" id="txtUuidPec"  value = ''/>
					<div  id="containerFormPecOp">
						
					</div>
				</div>
 			    <div class="modal-footer">
				<!-- Button -->  
					<button id="btnTrasformaPecOp" name="btnTrasformaPecOp" type="button"  onclick="trasformaPecOp()"
						class="btn btn-primary">
						<spring:message text="??pecMail.label.conferma??"
							code="pecMail.label.conferma" />
					</button>
					<button id="btnRifiutaPecOp" name="btnRifiutaPecOp" type="button" onclick="rifiutaPecOp()" 
						class="btn btn-danger">
						<spring:message text="??pecMail.label.rifiuta??"
							code="pecMail.label.rifiuta" />
					</button>
					<button name="btnChiudiPanelPecOp" type="button" onclick="chiudiPanelPecOp()" data-dismiss="modal"
						class="btn btn-warning">
						<spring:message text="??generici.label.chiudi??"
							code="generici.label.chiudi" />
					</button>

	</div>
			</div>
		</div>
	</div>
	
	<!-- engsecurity VA -->
	<form id="downloadPecAttachOp" action="<%=request.getContextPath() %>/download" method="get" style="display:none">	
	<engsecurity:token regenerate="false"/>
	<input type="hidden" name="uuid" id="uuid" value="">
	<input type="hidden" name="onlyfn" id="onlyfn" value="1">
	<input type="hidden" name="isp" id="isp" value="1">
	<input type="submit" value="" style="display:none">
	</form>
	
	<form id="redirectAttoPec" action="<%=request.getContextPath() %>/atto/creaDaPec.action" method="get" style="display:none">
    <engsecurity:token regenerate="false"/>
	<input type="hidden" name="uuidPec" id="uuidPec" value="">
	<input type="submit" value="" style="display:none">
	</form>
	
	<!-- FINE PANEL NOTIFICHE PEC OP -->
	
	<!-- PANEL WORKFLOW -->
	
	<div class="modal fade" id="panelFormWorkflow" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<!-- <spring:message text="??worflow.label.finestra??"
							code="worflow.label.finestra" />-->
							<label id="nomeStep"></label>
					</h4>
				</div>
				<div class="modal-body">
				
					<input type="hidden" name="hdnIdStep" id="hdnIdStep"  value = ''/>
					<input type="hidden" name="hdnIdWorkflow" id="hdnIdWorkflow"  value = ''/>
					<input type="hidden" name="hdnClasseWorkflow" id="hdnClasseWorkflow"  value = ''/>
					<input type="hidden" name="hdnIdObject" id="hdnIdObject"  value = ''/>
					<input type="hidden" name="hdnDoCommit" id="hdnDoCommit"  value = '0'/>
					
					<!-- DARIO*********************************************************** -->
						<!-- Aggiunta matricola responsabile MASSIMO CARUSO -->
						 <input type="hidden" name="wf_user_code_sel" value="<%=utenteConnesso.getVo().getMatricolaRespUtil()%>"/>
					<!-- **************************************************************** -->				
					
					<div  id="containerFormWorkflow" >
						
					</div>
					
					
								
				</div>
 			    <div class="modal-footer">
				<!-- Button  -->  
						<!-- DARIO ********************************************************************************* -->
						<%-- <button id="btnAvanzaWorkflow" name="btnAvanzaWorkflow" type="button" onclick="avanzaWorkflow()" 
							class="btn btn-primary">
							<spring:message text="??workflow.label.avanza??"
								code="workflow.label.avanza" />
						</button> --%>
						<button id="btnAvanzaWorkflow" name="BTN_START_WORKFLOW" type="button"  
							class="btn btn-primary">
							<spring:message text="??workflow.label.avanza??"
								code="workflow.label.avanza" />
						</button>
						<!-- ************************************************************************************** -->
						<!--
						<button id="btnRifiutaWorkflow" name="btnRifiutaWorkflow" type="button" data-dismiss="modal"
							class="btn btn-danger">
							<spring:message text="??workflow.label.rifiuta??"
								code="workflow.label.rifiuta" />
						</button>
						-->
						
						<button id="btnRifiutaWorkflow" name="btnRifiutaWorkflow" type="button" onclick="rifiutaWorkflow()" 
							class="btn btn-danger">
							<spring:message text="??workflow.label.rifiuta??"
								code="workflow.label.rifiuta" />
						</button>
						<button name="btnChiudiPanelWorkflow" type="button" onclick="chiudiPanelWorkflow()" data-dismiss="modal"
							class="btn btn-warning">
							<spring:message text="??generici.label.chiudi??"
								code="generici.label.chiudi" />
						</button>
					
				</div>
			</div>
		</div>
	</div>
	
	<!-- FINE PANEL WORKFLOW -->
	
	<!-- PANEL WORKFLOW ATTO-->
	
	<div class="modal fade" id="panelFormWorkflowAtto" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<!-- <spring:message text="??worflow.label.finestra??"
							code="worflow.label.finestra" />-->
							<label id="nomeStepAtto"></label>
					</h4>
				</div>
				<style>
				modal-body-custom{ heigth:350px!important;}
				</style>
				<div class="modal-body">
				<input type="hidden" name="hdnIdStepAtto" id="hdnIdStepAtto"  value = ''/>
				<input type="hidden" name="hdnIdWorkflowAtto" id="hdnIdWorkflowAtto"  value = ''/>
				<input type="hidden" name="hdnIdObjectAtto" id="hdnIdObjectAtto"  value = ''/>
				<input type="hidden" name="hdnDoCommitAtto" id="hdnDoCommitAtto"  value = '0'/>
				<input type="hidden" name="hdnDoCommitAttoRegistrato" id="hdnDoCommitAttoRegistrato"  value = '0'/>
					
					<!-- DARIO*********************************************************** -->
						 <input type="hidden" name="wf_user_code_sel" value=""/>
						 <input type="hidden" name="wf_user_resp_sel" value=""/>
					<!-- **************************************************************** -->				
					
					<div  id="containerFormWorkflowAtto">
					
					</div>
				</div>
 			    <div class="modal-footer">
				<!-- Button --> <!--FIX  as-is_2 UAT - R5 22/02/2017 --> <!-- ABILITO IL PULSANTE REGISTRA A TUTTI GLI UTENTI inserendo con la condizione || 1==1 :) -->															
					<%if( utenteConnesso.isPrimoRiporto() || utenteConnesso.isLegaleInterno() || utenteConnesso.isResponsabileSenzaCollaboratori()|| 1==1) {%>
						<button id="btnRegistraAtto" name="btnRegistraAtto" type="button"  onclick="registraWfAtto()"
								class="btn btn-primary">
						<spring:message text="??workflow.label.registra??"
							code="workflow.label.registra" />
						</button>
					<%} %>
					<%if( utenteConnesso.isResponsabileFoglia()) {%>
					
					<!-- DARIO ********************************************************************************* -->
					<%-- <button id="btnAvanzaWorkflowAtto" name="btnAvanzaWorkflowAtto" type="button"  onclick="assegnaIncaricoLegaleInterno()"
							class="btn btn-primary">
						<spring:message text="??workflow.label.presa.in.carico??"
							code="workflow.label.presa.in.carico" />
					</button> --%>
					<button id="btnAvanzaWorkflowAtto" data-function="assegnaIncaricoLegaleInterno" name="BTN_START_WORKFLOW" type="button"  
						class="btn btn-primary">
						<spring:message text="??workflow.label.presa.in.carico??"
							code="workflow.label.presa.in.carico" />
					</button>
					<!-- ************************************************************************************** -->
					
					<%} %>
					<%if( utenteConnesso.isResponsabile() && !utenteConnesso.isResponsabileFoglia()) {%>
					
					<!-- DARIO ********************************************************************************* -->
					<%-- <button id="btnAvanzaWorkflowAtto" name="btnAvanzaWorkflowAtto" type="button"  onclick="assegnaIncarico()"
							class="btn btn-primary">
						<spring:message text="??workflow.label.presa.in.carico??"
							code="workflow.label.presa.in.carico" />
					</button> --%>
					<button id="btnAvanzaWorkflowAtto" data-function="assegnaIncarico" name="BTN_START_WORKFLOW" type="button"  
						class="btn btn-primary">
						<spring:message text="??workflow.label.presa.in.carico??"
							code="workflow.label.presa.in.carico" />
					</button>
					<!-- ************************************************************************************** -->
					
					<%} %>
					<button id="btnRifiutaWorkflowAtto" name="btnRifiutaWorkflowAtto" type="button" onclick="rifiutaWorkflowAtto()" 
						class="btn btn-danger">
						<spring:message text="??workflow.label.rifiuta??"
							code="workflow.label.rifiuta" />
					</button>
					<button name="btnChiudiPanelWorkflowAtto" type="button" onclick="chiudiPanelWorkflowAtto()" data-dismiss="modal"
						class="btn btn-warning">
						<spring:message text="??generici.label.chiudi??"
							code="generici.label.chiudi" />
					</button>
					 
				</div>
			</div>
		</div>
	</div>
	
	<!-- FINE PANEL WORKFLOW ATTO -->
	
 <!-- PANEL MOTIVAZIONE RIFIUTO -->
	
	
	<div class="modal fade" id="panelFormMotivazioneRifiuto" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??worflow.label.finestra??"
							code="worflow.label.finestra" />
					</h4>
				</div>
				<div class="modal-body">
					<div  id="containerFormRifiutaWorkflow">
					
					</div>
				</div>
 			    <div class="modal-footer">
				<!-- Button -->  
					<button id="btnRifiutaConNotaWorkflow" name="btnRifiutaConNotaWorkflow" type="button" data-dismiss="modal"
						class="btn btn-danger">
						<spring:message text="??workflow.label.rifiuta??"
							code="workflow.label.rifiuta" />
					</button>
					<button name="btbChiudiPanelFormMotivazioneRifiuto" type="button" data-dismiss="modal"
						class="btn btn-warning">
						<spring:message text="??generici.label.chiudi??"
							code="generici.label.chiudi" />
					</button>
				</div>
			</div>
		</div>
	</div>

<%
}
%>	
	
	
	
	<!--
	<script type="text/javascript">
	var numOfCalls = 0;
	var auto_refresh = setInterval(
        function ()
        {
			$('#containerAttivitaPendenti').load(WEBAPP_BASE_URL + '/parts/notifichePendenti.jsp' + '#containerAttivitaPendenti');
			numOfCalls++;
			  
		  if(numOfCalls == 4)
		     clearInterval(auto_refresh);
       }, 3000); // autorefresh the content of the div every 3000 milliseconds(3sec) per 4 volte
        
	</script>
	-->
	
	<!-- FINE PANEL MOTIVAZIONE RIFIUTO -->

	