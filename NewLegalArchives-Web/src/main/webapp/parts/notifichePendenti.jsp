<%@page import="eng.la.business.workflow.StepWfService"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.StepWfView"%>
<%@page import="eng.la.model.ArchivioProtocollo"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>

<%@page import="eng.la.business.NotificaWebService"%>
<%@page import="eng.la.business.NotificaPecService"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="eng.la.business.ArchivioProtocolloService"%>
<%@page import="eng.la.model.view.NotificaWebView"%>
<%@page import="eng.la.model.view.NotificaPecView"%>

<%@page import="ibm.la.util.connection.lucy.LucyConnectionManager" %>
<%@page import="java.sql.ResultSet" %>


<%
UtenteView utenteConnesso = (UtenteView ) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
/* DARIO********************************************************************************************* */
boolean displayOtherManagers = !utenteConnesso.isTopResponsabile() && !utenteConnesso.isTopHead();

/* ************************************************************************************************** */

try{
	
	/**
	* Sistema di notifica fatture inconsistenti
	* @author MASSIMO CARUSO
	*/
	//----------------------INIZIO---------------------------------
// 	ArrayList<String> fatture = new ArrayList<String>();
	
	// abilito l'operazione solo per l'utenza di Villa Elisabetta
// 	if(utenteConnesso.getVo().getMatricolaUtil().equals("0910004812") && request.getSession().getAttribute("listaFatture") == null){
// 		String query = "SELECT * FROM LA_DOCUMENTS WHERE (STATUS = 0 or STATUS = 1) and MESSAGE not like '%Arkdocs%'";
// 		LucyConnectionManager lucy = new LucyConnectionManager();
// 		lucy.startConnection();
// 		ResultSet result = lucy.executeQuery(query);
// 		String temp = null;
	
// 		while(result.next()){
// 			temp = 	result.getString("DATADOC") + " | " +
// 			   	result.getString("CODFORN") + " | " +
// 				result.getString("ID") + " | " +
// 				result.getString("NUMDOC") + " | " +
// 				result.getInt("STATUS") + " | " +
// 				result.getString("MESSAGE");
// 			fatture.add(temp);
			
// 		}
// 		lucy.stopConnection();
// 		request.getSession().setAttribute("listaFatture",fatture);
// 		request.getSession().setAttribute("numeroFatture",fatture.size());
// 	}
	
	//----------------------FINE-----------------------------------
	
	StepWfService stepWfService = (StepWfService) SpringUtil.getBean("stepWfService");
	List<StepWfView> listaAttivitaPendenti = stepWfService.leggiAttivitaPendenti(utenteConnesso.getVo().getMatricolaUtil(),request.getLocale().getLanguage().toUpperCase());
	request.getSession().setAttribute("listaAttivitaPendenti",listaAttivitaPendenti);
 
	NotificaWebService notificaWebService = (NotificaWebService) SpringUtil.getBean("notificaWebService");
	List<NotificaWebView> listaNotifichePendenti = notificaWebService.leggi(utenteConnesso.getVo().getMatricolaUtil());
	request.getSession().setAttribute("listaNotifichePendenti",listaNotifichePendenti);

	UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
	List<NotificaPecView> listaNotifichePec = new ArrayList<NotificaPecView>();
	if(utenteService.leggiSeOperatoreSegreteria(utenteConnesso)){
		NotificaPecService notificaPecService = (NotificaPecService) SpringUtil.getBean("notificaPecService");
		listaNotifichePec =  notificaPecService.leggi(utenteConnesso.getVo().getMatricolaUtil());
	}
	request.getSession().setAttribute("listaNotifichePec",listaNotifichePec);
	
	
	List<ArchivioProtocollo> listaProtocollo = new ArrayList<ArchivioProtocollo>();
	List<ArchivioProtocollo> listaProtocolloGestori = new ArrayList<ArchivioProtocollo>();
	List<ArchivioProtocollo> listaProtocolloGestoriDump = new ArrayList<ArchivioProtocollo>();
	
	if(utenteConnesso.getVo().getMatricolaUtil().equals(utenteService.leggiResponsabileTop().getVo().getMatricolaUtil()))
	{
		ArchivioProtocolloService archivioProtocolloService = (ArchivioProtocolloService) SpringUtil.getBean("archivioProtocolloService");
		listaProtocollo = archivioProtocolloService.leggiProtocolliDaAssegnare();
	
	}
	else
	{
		ArchivioProtocolloService archivioProtocolloService = (ArchivioProtocolloService) SpringUtil.getBean("archivioProtocolloService");
		listaProtocolloGestori = archivioProtocolloService.leggiProtocolliAssegnati(utenteConnesso.getVo());
	}

	int prot=0;

	int protGest=0;

	if(listaProtocollo.size()!=0)
		prot=1;

	if(listaProtocolloGestori.size()!=0)
		protGest=1;


	request.getSession().setAttribute("listaProtocollo",listaProtocollo);
	request.getSession().setAttribute("prot",prot);

	request.getSession().setAttribute("listaProtocolloGestori",listaProtocolloGestori);
	request.getSession().setAttribute("protGest",protGest);

	for(ArchivioProtocollo archivioProtocollo : listaProtocolloGestori){
	
		if(archivioProtocollo.getFascicoloAssociato() == null){
			listaProtocolloGestoriDump.add(archivioProtocollo);
		}
	}

	/* listaProtocolloGestoriDump.addAll(listaProtocolloGestori);

	listaProtocolloGestoriDump.removeIf(o -> o.getFascicoloAssociato() != null); */

	int senzaFascicolo=listaProtocolloGestoriDump.size();

	request.getSession().setAttribute("senzaFascicolo",senzaFascicolo);

%>



<c:if test="${not empty listaAttivitaPendenti or not empty listaNotifichePendenti or not empty listaNotifichePec}">
<input type="hidden" name="hdnNroStep" id="hdnIdStep"  value='<c:out value="${listaAttivitaPendenti.size()+listaNotifichePendenti.size()+listaNotifichePec.size()+prot+protGest}"/>'>
	<script type="text/javascript">
     //function refreshBadgeNotifiche(){
		 var badge = document.getElementById("badgeNotificheCampana");   
		 var badgeMd = document.getElementById("badgeNotificheCampanaMd"); 
		 if(badge != null && badgeMd != null){
			 badge.innerHTML='';
			 badgeMd.innerHTML=''; 
			 var badgeTesto = document.createTextNode(document.getElementById("hdnIdStep").value);
			 var badgeTestoMd = document.createTextNode(document.getElementById("hdnIdStep").value);
			 badge.appendChild(badgeTesto); 
			 badgeMd.appendChild(badgeTestoMd);  
     }
	 //} 
	</script>	
</c:if>	


<!--<c:if test="${not empty listaFatture}">-->
	<!--  Inserimento download elenco fatture incongruenti @author MASSIMO CARUSO -->
	<!--<div class="list-group-item media">
		<div class="media-body">
			<div class="lgi-heading">
				<h4>
					<strong>Numero di fatture incongruenti: <c:out value="${numeroFatture}" /></strong>
				</h4>
				<input type="button" id="dwn-btn" value="Scarica Report" />
			</div>
    	</div>
	</div>
	<script>	
		function download(filename, text) {
    		var element = document.createElement('a');
    		element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    		element.setAttribute('download', filename);

    		element.style.display = 'none';
    		document.body.appendChild(element);

    		element.click();

    		document.body.removeChild(element);
		}
		
		// Start file download.
		document.getElementById("dwn-btn").addEventListener("click", function(){
		    <% 
// 		    	String output = "";
// 		    	ArrayList<String> listaFatture = (ArrayList<String>)request.getSession().getAttribute("listaFatture");
// 				if(listaFatture != null){
// 					for(String fattura : listaFatture){
// 						output += "\\r\\n"+fattura.replace("\n", "");
// 					}
// 				}
		    %>
		    var text = "DATADOC | CODFORN | ID | NUMDOC | STATUS | MESSAGE<%//=output%>";
		    var filename = "fatture.txt";
		    
		    download(filename, text);
		}, false);
	</script>-->
	<!-- FINE Inserimento download elenco fatture -->
<!--</c:if>-->

<c:if test="${not empty listaNotifichePendenti}">	
	<c:forEach items="${ listaNotifichePendenti }" var="notifiche">
	
		<a id="notificaWeb_<c:out value="${notifiche.vo.id}"/>" href="#" class="list-group-item media" style="background-color: #eeeeee;">
		
			<div class="media-body" >
			
				<div class="lgi-heading" data-toggle="tooltip" title="<spring:message
							text="??${notifiche.vo.keyMessage}??"
							code="${notifiche.vo.keyMessage}" arguments="${notifiche.vo.jsonParam}$" argumentSeparator="$"/>"><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${notifiche.vo.dataNotifica}"/></div>
				<small class="lgi-text" data-toggle="tooltip" title="<spring:message
							text="??${notifiche.vo.keyMessage}??"
							code="${notifiche.vo.keyMessage}" arguments="${notifiche.vo.jsonParam}$" argumentSeparator="$"/>"><spring:message
							text="??${notifiche.vo.keyMessage}??"
							code="${notifiche.vo.keyMessage}" arguments="${notifiche.vo.jsonParam}$" argumentSeparator="$"/>
					</small>
					<div class="notifiche-action" data-toggle="tooltip" title="<spring:message
							text="??${notifiche.vo.keyMessage}??"
							code="${notifiche.vo.keyMessage}" arguments="${notifiche.vo.jsonParam}$" argumentSeparator="$"/>">
						<button class="btn btn-icon" style="visibility:hidden">
							<span class="zmdi zmdi-check"></span>
						</button>
						<button class="btn btn-icon command-edit" onclick="marcaNotificaWebLetta('<c:out value="${notifiche.vo.id}"/>')" data-toggle="tooltip" title="<spring:message
							text="??${notifiche.vo.keyMessage}??"
							code="${notifiche.vo.keyMessage}" arguments="${notifiche.vo.jsonParam}$" argumentSeparator="$"/>">
						<span class="zmdi zmdi-close"></span>
					</button>
				</div>
			</div>
		
		</a> 
	</c:forEach>	
</c:if>	

<c:if test="${not empty listaNotifichePec}">	

	<c:forEach items="${ listaNotifichePec }" var="notPec">
	
		<a href="#" class="list-group-item media">
		
			<div class="media-body">
			
				<div class="lgi-heading"><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${notPec.vo.dataNotifica}"/></div>
				<small class="lgi-text"><strong><spring:message text="??pecMail.label.notifica??" code="pecMail.label.notifica" /></strong><br />
				 ${notPec.vo.utentePec.pecMittente} 
				<br />
				${notPec.vo.utentePec.pecOggetto}</small>
				<div class="notifiche-action">
					<button class="btn btn-icon" style="visibility:hidden">
						<span class="zmdi zmdi-check"></span>
					</button>
					<button class="btn btn-icon command-edit" data-toggle="modal" onclick="processaPecOp('<c:out value="${notPec.vo.id}"/>','<c:out value="${notPec.vo.utentePec.id}"/>','<c:out value="${notPec.vo.utentePec.pecMittente}"/>','<c:out value="${notPec.vo.utentePec.pecDestinatario}"/>'
							,'<c:out value="${notPec.vo.utentePec.pecOggetto}"/>','<c:out value="${notPec.vo.utentePec.UUId}"/>')"
							data-target="#panelFormPecOp">
					<span class="zmdi zmdi-check"></span>
					</button>
				</div>
			</div>
		
		</a> 
	</c:forEach>	
</c:if>

<c:if test="${not empty listaProtocollo}">	
	
		<a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/protocollo/gestioneProtocollo.action?filter=DAS')}" class="list-group-item media">
		
			<div class="media-body">
			<div class="lgi-heading"><spring:message text="??protocollo.label.pagProtocollo??" code="protocollo.label.pagProtocollo" /></div>
				<small class="lgi-text"><spring:message text="??protocollo.label.notificaci??" code="protocollo.label.notificaci" />
				<strong><c:out value="${listaProtocollo.size()}"/></strong>
				<spring:message text="??protocollo.label.notificada??" code="protocollo.label.notificada" /></small>
			</div>
		
		</a> 
</c:if>

<c:if test="${senzaFascicolo ne 0}">	
	
		<a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/protocollo/gestioneProtocollo.action?filter=ASS')}" class="list-group-item media">
		
			<div class="media-body">
			<div class="lgi-heading"><spring:message text="??protocollo.label.pagProtocollo??" code="protocollo.label.pagProtocollo" /></div>
				<small class="lgi-text"><spring:message text="??protocollo.label.notificaci??" code="protocollo.label.notificaci" />
				<strong><c:out value="${senzaFascicolo}"/></strong>
				<spring:message text="??protocollo.label.notificaass??" code="protocollo.label.notificaass" /></small>
			</div>
		
		</a> 
</c:if>

<c:if test="${not empty listaAttivitaPendenti}">	
	<c:forEach items="${ listaAttivitaPendenti }" var="attivita">
	
		<a href="#" class="list-group-item media">
		
			<div class="media-body">
			
				<div class="lgi-heading"><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${attivita.vo.dataCreazione}"/></div>
				<small class="lgi-text"><strong>${attivita.descLinguaCorrente}</strong><br /> ${attivita.noteSpecifiche} 
				<c:if test="${not empty attivita.noteSpecifiche}">
					<br />
				</c:if> 
				${attivita.fascicoloConValore}</small>
				<div class="notifiche-action">
				<c:if test="${attivita.vo.configurazioneStepWf.classeWf.codice eq 'CHIUSURA_FASC' }">
					<c:set var="stringaObject" scope="page" value="${attivita.vo.fascicoloWf.fascicolo.id}" />
					<c:set var="stringaWorkflow" scope="page" value="${attivita.vo.fascicoloWf.id}" />
					<button class="btn btn-icon" style="visibility:hidden">
						<span class="zmdi zmdi-check"></span>
					</button>
					<!-- DARIO ********************************************************************************** -->
					<%-- <button class="btn btn-icon command-edit" data-toggle="modal" onclick="processaWorkflow('<c:out value="${attivita.vo.id}"/>','<c:out value="${stringaWorkflow}"/>','<c:out value="${attivita.vo.configurazioneStepWf.classeWf.codice}"/>'
							,'<c:out value="${stringaObject}"/>','<c:out value="${attivita.descLinguaCorrente}"/>')"
							data-target="#panelFormWorkflow">
						<span class="zmdi zmdi-check"></span>
					</button> --%>
					<c:set var="fun_param" scope="page" value="'${attivita.vo.id}','${stringaWorkflow}','${attivita.vo.configurazioneStepWf.classeWf.codice}','${stringaObject}','${attivita.descLinguaCorrente}'"/>
					<div style="float: right; position:relative;">
				      	 
				      	 <%
						if (displayOtherManagers){
						%>
				      		 <button class="btn btn-icon-10 command-edit" data-toggle="modal" onclick="workflowAutorizzaManuale(${fun_param})">
				          		<span class="zmdi zmdi-account-add"></span>
				      		</button>
				      	<%
						}
		    			%>
						<button class="btn btn-icon command-edit" data-toggle="modal" onclick="workflowAutorizzaAutomatico(${fun_param})">
							<span class="zmdi zmdi-check"></span>
						</button>
				   	</div>
					<!-- **************************************************************************************** -->
				</c:if>
				<c:if test="${attivita.vo.configurazioneStepWf.classeWf.codice eq 'AUT_INCARICO' }">
					<c:set var="stringaObject" scope="page" value="${attivita.vo.incaricoWf.incarico.id}" />
					<c:set var="stringaWorkflow" scope="page" value="${attivita.vo.incaricoWf.id}" />
					<button class="btn btn-icon" style="visibility:hidden">
						<span class="zmdi zmdi-check"></span>
					</button>
					<!-- DARIO ********************************************************************************** -->
					<%-- <button class="btn btn-icon command-edit" data-toggle="modal" onclick="processaWorkflow('<c:out value="${attivita.vo.id}"/>','<c:out value="${stringaWorkflow}"/>','<c:out value="${attivita.vo.configurazioneStepWf.classeWf.codice}"/>'
							,'<c:out value="${stringaObject}"/>','<c:out value="${attivita.descLinguaCorrente}"/>')"
							data-target="#panelFormWorkflow">
						<span class="zmdi zmdi-check"></span>
					</button> --%>
					<c:set var="fun_param" scope="page" value="'${attivita.vo.id}','${stringaWorkflow}','${attivita.vo.configurazioneStepWf.classeWf.codice}','${stringaObject}','${attivita.descLinguaCorrente}'"/>
					<div style="float: right; position:relative;">
				      	<%
						if (displayOtherManagers){
						%>
					      	 <button class="btn btn-icon-10 command-edit" data-toggle="modal" onclick="workflowAutorizzaManuale(${fun_param})">
					          	<span class="zmdi zmdi-account-add"></span>
					      	</button>
				      	<%
						}
		    			%>
						<button class="btn btn-icon command-edit" data-toggle="modal" onclick="workflowAutorizzaAutomatico(${fun_param})">
							<span class="zmdi zmdi-check"></span>
						</button>
				   	</div>
					<!-- ***************************************************************************************** -->
				</c:if>
				<c:if test="${attivita.vo.configurazioneStepWf.classeWf.codice eq 'AUT_SCHEDA_FR' }">
					<c:set var="stringaObject" scope="page" value="${attivita.vo.schedaFondoRischiWf.schedaFondoRischi.id}" />
					<c:set var="stringaWorkflow" scope="page" value="${attivita.vo.schedaFondoRischiWf.id}" />
					<button class="btn btn-icon" style="visibility:hidden">
						<span class="zmdi zmdi-check"></span>
					</button>
					<!-- DARIO ********************************************************************************** -->
					<%-- <button class="btn btn-icon command-edit" data-toggle="modal" onclick="processaWorkflow('<c:out value="${attivita.vo.id}"/>','<c:out value="${stringaWorkflow}"/>','<c:out value="${attivita.vo.configurazioneStepWf.classeWf.codice}"/>'
							,'<c:out value="${stringaObject}"/>','<c:out value="${attivita.descLinguaCorrente}"/>')"
							data-target="#panelFormWorkflow">
						<span class="zmdi zmdi-check"></span>
					</button> --%>
					<c:set var="fun_param" scope="page" value="'${attivita.vo.id}','${stringaWorkflow}','${attivita.vo.configurazioneStepWf.classeWf.codice}','${stringaObject}','${attivita.descLinguaCorrente}'"/>
					<div style="float: right; position:relative;">
				      	<%
						if (displayOtherManagers){
						%>
				      	 	<button class="btn btn-icon-10 command-edit" data-toggle="modal" onclick="workflowAutorizzaManuale(${fun_param})">
				          		<span class="zmdi zmdi-account-add"></span>
				      		</button>
				      	<%
						}
		    			%>	
						<button class="btn btn-icon command-edit" data-toggle="modal" onclick="workflowAutorizzaAutomatico(${fun_param})">
							<span class="zmdi zmdi-check"></span>
						</button>
				   	</div>
					<!-- **************************************************************************************** -->
				</c:if>
				<c:if test="${attivita.vo.configurazioneStepWf.classeWf.codice eq 'AUT_BEAUTY_CONTEST' }">
					<c:set var="stringaObject" scope="page" value="${attivita.vo.beautyContestWf.beautyContest.id}" />
					<c:set var="stringaWorkflow" scope="page" value="${attivita.vo.beautyContestWf.id}" />
					<button class="btn btn-icon" style="visibility:hidden">
						<span class="zmdi zmdi-check"></span>
					</button>
					<!-- DARIO ********************************************************************************** -->
					<%-- <button class="btn btn-icon command-edit" data-toggle="modal" onclick="processaWorkflow('<c:out value="${attivita.vo.id}"/>','<c:out value="${stringaWorkflow}"/>','<c:out value="${attivita.vo.configurazioneStepWf.classeWf.codice}"/>'
							,'<c:out value="${stringaObject}"/>','<c:out value="${attivita.descLinguaCorrente}"/>')"
							data-target="#panelFormWorkflow">
						<span class="zmdi zmdi-check"></span>
					</button> --%>
					<c:set var="fun_param" scope="page" value="'${attivita.vo.id}','${stringaWorkflow}','${attivita.vo.configurazioneStepWf.classeWf.codice}','${stringaObject}','${attivita.descLinguaCorrente}'"/>
					<div style="float: right; position:relative;">
				      	 <%
						if (displayOtherManagers){
						%>
				      	 	<button class="btn btn-icon-10 command-edit" data-toggle="modal" onclick="workflowAutorizzaManuale(${fun_param})">
				          		<span class="zmdi zmdi-account-add"></span>
				      		</button>
				      	<%
						}
		    			%>	
						<button class="btn btn-icon command-edit" data-toggle="modal" onclick="workflowAutorizzaAutomatico(${fun_param})">
							<span class="zmdi zmdi-check"></span>
						</button>
				   	</div>
					<!-- **************************************************************************************** -->
				</c:if>
				<c:if test="${attivita.vo.configurazioneStepWf.classeWf.codice eq 'AUT_PROFORMA' }">
					<c:set var="stringaObject" scope="page" value="${attivita.vo.proformaWf.proforma.id}" />
					<c:set var="stringaWorkflow" scope="page" value="${attivita.vo.proformaWf.id}" />
					<button class="btn btn-icon" style="visibility:hidden">
						<span class="zmdi zmdi-check"></span>
					</button>
					<!-- DARIO ********************************************************************************** -->
					<%-- <button class="btn btn-icon command-edit" data-toggle="modal" onclick="processaWorkflow('<c:out value="${attivita.vo.id}"/>','<c:out value="${stringaWorkflow}"/>','<c:out value="${attivita.vo.configurazioneStepWf.classeWf.codice}"/>'
							,'<c:out value="${stringaObject}"/>','<c:out value="${attivita.descLinguaCorrente}"/>')"
							data-target="#panelFormWorkflow">
						<span class="zmdi zmdi-check"></span>
					</button> --%>
					<c:set var="fun_param" scope="page" value="'${attivita.vo.id}','${stringaWorkflow}','${attivita.vo.configurazioneStepWf.classeWf.codice}','${stringaObject}','${attivita.descLinguaCorrente}'"/>
					<div style="float: right; position:relative;">
				      	<%
						if (displayOtherManagers){
						%>
				      	 	<button class="btn btn-icon-10 command-edit" data-toggle="modal" onclick="workflowAutorizzaManuale(${fun_param})">
				          		<span class="zmdi zmdi-account-add"></span>
				      		</button>
				      	<%
						}
		    			%>	
						<button class="btn btn-icon command-edit" data-toggle="modal" onclick="workflowAutorizzaAutomatico(${fun_param})">
							<span class="zmdi zmdi-check"></span>
						</button>
				   	</div>
					<!-- **************************************************************************************** -->
				</c:if>
				<c:if test="${attivita.vo.configurazioneStepWf.classeWf.codice eq 'AUT_PROF_EST' }">
					<c:set var="stringaObject" scope="page" value="${attivita.vo.professionistaEsternoWf.professionistaEsterno.id}" />
					<c:set var="stringaWorkflow" scope="page" value="${attivita.vo.professionistaEsternoWf.id}" />
					<button class="btn btn-icon" style="visibility:hidden">
						<span class="zmdi zmdi-check"></span>
					</button>
					<!-- DARIO ********************************************************************************** -->
					<%-- <button class="btn btn-icon command-edit" data-toggle="modal" onclick="processaWorkflow('<c:out value="${attivita.vo.id}"/>','<c:out value="${stringaWorkflow}"/>','<c:out value="${attivita.vo.configurazioneStepWf.classeWf.codice}"/>'
							,'<c:out value="${stringaObject}"/>','<c:out value="${attivita.descLinguaCorrente}"/>')"
							data-target="#panelFormWorkflow">
						<span class="zmdi zmdi-check"></span>
					</button> --%>
					<c:set var="fun_param" scope="page" value="'${attivita.vo.id}','${stringaWorkflow}','${attivita.vo.configurazioneStepWf.classeWf.codice}','${stringaObject}','${attivita.descLinguaCorrente}'"/>
					<div style="float: right; position:relative;">
				      	<%
						if (displayOtherManagers){
						%>
					      	 <button class="btn btn-icon-10 command-edit" data-toggle="modal" onclick="workflowAutorizzaManuale(${fun_param})">
					          	<span class="zmdi zmdi-account-add"></span>
					      	</button>
				      	<%
						}
		    			%>
						<button class="btn btn-icon command-edit" data-toggle="modal" onclick="workflowAutorizzaAutomatico(${fun_param})">
							<span class="zmdi zmdi-check"></span>
						</button>
				   	</div>
					<!-- **************************************************************************************** -->
				</c:if>
				<c:if test="${attivita.vo.configurazioneStepWf.classeWf.codice eq 'REGISTR_ATTO' }">
					<c:set var="stringaObject" scope="page" value="${attivita.vo.attoWf.atto.id}" />
					<c:set var="stringaWorkflow" scope="page" value="${attivita.vo.attoWf.id}" />
					<button class="btn btn-icon" style="visibility:hidden">
						<span class="zmdi zmdi-check"></span>
					</button>
					<!-- DARIO ********************************************************************************** -->
					<%-- <button class="btn btn-icon command-edit" data-toggle="modal" onclick="processaWorkflowAtto('<c:out value="${attivita.vo.id}"/>','<c:out value="${stringaWorkflow}"/>'
							,'<c:out value="${stringaObject}"/>','<c:out value="${attivita.descLinguaCorrente}"/>','<c:out value="${attivita.idFascicolo}"/>', '<c:out value="${attivita.vo.motivoRifiutoStepPrecedente}"/>' )"
							data-target="#panelFormWorkflowAtto">
						<span class="zmdi zmdi-check"></span>
					</button> --%>
					<c:set var="fun_param" scope="page" value="'${attivita.vo.id}','${stringaWorkflow}','${stringaObject}','${attivita.descLinguaCorrente}','${attivita.idFascicolo}','${attivita.vo.motivoRifiutoStepPrecedente}'"/>
					<div style="float: right; position:relative;">
				      	 <%
						if (utenteConnesso.isResponsabile()){
						%>
				      	 	<button class="btn btn-icon-10 command-edit" data-toggle="modal" onclick="workflowAutorizzaAttoManuale(${fun_param})">
				          		<span class="zmdi zmdi-account-add"></span>
				      		</button>
				      	<%
						}
		    			%>
						<button class="btn btn-icon command-edit" data-toggle="modal" onclick="workflowAutorizzaAttoAutomatico(${fun_param})">
							<span class="zmdi zmdi-check"></span>
						</button>
				   	</div>
					<!-- **************************************************************************************** -->
				</c:if>
				
				
				</div>
			</div>
		
		</a> 
	</c:forEach>	
	<script type="text/javascript">
     //function refreshBadgeNotifiche(){
		 var badge = document.getElementById("badgeNotificheCampana");   
		 var badgeMd = document.getElementById("badgeNotificheCampanaMd"); 
		 if(badge != null && badgeMd != null){
			 badge.innerHTML='';
			 badgeMd.innerHTML=''; 
			 var badgeTesto = document.createTextNode(document.getElementById("hdnIdStep").value);
			 var badgeTestoMd = document.createTextNode(document.getElementById("hdnIdStep").value);
			 badge.appendChild(badgeTesto); 
			 badgeMd.appendChild(badgeTestoMd);  
     }
	 //} 
	</script>										
</c:if>	

<c:if test="${empty listaAttivitaPendenti && empty listaNotifichePendenti && empty listaNotifichePec && empty listaProtocollo}">
	<script type="text/javascript">
		 var badge = document.getElementById("badgeNotificheCampana");   
		 var badgeMd = document.getElementById("badgeNotificheCampanaMd"); 
		 if(badge != null && badgeMd != null){
			 badge.innerHTML='';
			 badgeMd.innerHTML=''; 
		 }
	 </script>		
</c:if>		
<%
}catch(Throwable e){
	e.printStackTrace(); 
}
%>
