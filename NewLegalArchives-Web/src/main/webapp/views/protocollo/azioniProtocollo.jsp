<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.ProtocolloView"%>
<%@page import="eng.la.business.ArchivioProtocolloService"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.FascicoloView"%>
<%@page import="java.lang.String"%>
<%@page import="eng.la.model.Fascicolo"%>
<%@page import="eng.la.model.StatoProtocollo"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.FascicoloService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>




<%
	String num_protocollo = "";
	try {
		String idProtocollo = request.getAttribute("idProtocollo") + "";
		ArchivioProtocolloService service = (ArchivioProtocolloService) SpringUtil.getBean("archivioProtocolloService");
		UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService"); 
		ProtocolloView view = service.leggi(NumberUtils.toLong(idProtocollo));
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		UtenteView top = utenteService.leggiResponsabileTop();
		

		
		//aggiunta num_protocollo per report errore MASSIMO CARUSO
		num_protocollo = view.getVo().getNumProtocollo();
		//FINE aggiunta num_protocollo per report errore MASSIMO CARUSO
		boolean isAmministratore = utenteConnesso.getVo().getMatricolaUtil().equals(top.getVo().getMatricolaUtil());
		boolean isPrimoRiporto = utenteService.leggiSePrimoRiporto(utenteConnesso);
		boolean isResponsabile = utenteConnesso.isResponsabile();
		
		//RIDP6U9
		boolean isGruppoAmministratore = utenteConnesso.isAmministratore();
		
		String matUC = utenteConnesso.getVo().getMatricolaUtil();
		String matOW = view.getVo().getOwner().getMatricolaUtil();
		String matAS = "";
		
		if(view.getVo().getAssegnatario()!=null)
			matAS = view.getVo().getAssegnatario().getMatricolaUtil();
		
		
		String stato = view.getVo().getStatoProtocollo().getCodGruppoLingua();
		
		//Aggiunta del tipo del protocollo MASSIMO CARUSO
		String tipo = view.getVo().getTipo();
		//FINE Aggiunta del tipo del protocollo MASSIMO CARUSO

		if(stato.equalsIgnoreCase("BOZ") 
				&& matUC.equalsIgnoreCase(matOW)
				//aggiunta check per rimuovere tipo = ACT MASSIMO CARUSO
				&& !tipo.equalsIgnoreCase("ACT")
				//FINE aggiunta check per rimuovere tipo = ACT MASSIMO CARUSO
				){
%>
		<li><a data-toggle="modal" 
			href="#panelUpload" data-id="<%=idProtocollo%>" data-name="<%=view.getVo().getNumProtocollo()%>"
			class="edit"> <i class="fa fa-upload" aria-hidden="true"></i> <spring:message
					text="??protocollo.label.upload??"
					code="protocollo.label.upload" />
		</a></li>
<% 
		}
		
		if(!stato.equalsIgnoreCase("BOZ")
				//aggiunta check per rimuovere tipo = ACT MASSIMO CARUSO
				&& !tipo.equals("ACT")
				//FINE aggiunta check per rimuovere tipo = ACT MASSIMO CARUSO
				){ 
%>	
	
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/protocollo/download.action?id=<%=idProtocollo%>')}"
			class="edit"> <i class="fa fa-download" aria-hidden="true"></i> <spring:message
					text="??protocollo.label.download??"
					code="protocollo.label.download" />
		</a></li>
<% 
		//aggiunta check download per tipo = ACT MASSIMO CARUSO
		}		
	
		if(!stato.equalsIgnoreCase("BOZ") && tipo.equalsIgnoreCase("ACT")){ 
			
%>	
	
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/atto/download.action?uuid=<%=view.getVo().getDocumento().getUuid()%>')}"
			class="edit"> <i class="fa fa-download" aria-hidden="true"></i> <spring:message
					text="??protocollo.label.download??"
					code="protocollo.label.download" />
		</a></li>
<% 
		}
		//FINE aggiunta check download per tipo = ACT MASSIMO CARUSO
		
		//RIDP6U9
		if((isAmministratore || isGruppoAmministratore) && stato.equalsIgnoreCase("DAS")
				//aggiunta check per rimuovere tipo = ACT MASSIMO CARUSO
				&& !tipo.equalsIgnoreCase("ACT")
				//FINE aggiunta check per rimuovere tipo = ACT MASSIMO CARUSO
				){
	%>
		<li><a data-toggle="modal" 
			href="#panelAssegna" data-id="<%=idProtocollo%>"
			<%
			if(view.getVo().getTipo().equalsIgnoreCase("IN")){
			%>
			data-name="<%=view.getVo().getDestinatarioMat().getMatricolaUtil()%>"
			<%
			}
			else{
			%>
			data-name="<%=view.getVo().getMittenteMat().getMatricolaUtil()%>"
			<%} %>
			data-nome="<%=view.getVo().getNumProtocollo()%>"
			<%//aggiunta tipo protocollo MASSIMO CARUSO %>
			data-tipoprotocollo="<%=tipo %>"
			<%//FINE aggiunta tipo protocollo MASSIMO CARUSO %>
			class="edit"> <i class="fa fa-user-plus" aria-hidden="true"></i> <spring:message
					text="??protocollo.label.assegna??"
					code="protocollo.label.assegna" />
		</a></li>
		
		
	<% 
		} 
		//aggiunta caso ACT MASSIMO CARUSO
		if(isAmministratore && tipo.equalsIgnoreCase("ACT") && stato.equalsIgnoreCase("DAS")){%>
		<!-- Aggiunta pannello per atti protocollati da validare MASSIMO CARUSO -->
		<li><a data-toggle="modal" 
			href="#panelAssegna" data-id="<%=idProtocollo%>"
			data-name="<%=view.getVo().getDestinatarioMat().getMatricolaUtil()%>"
			data-nome="<%=view.getVo().getNumProtocollo()%>"
			data-iddocumento="<%=view.getVo().getDocumento().getId() %>"
			data-tipoprotocollo="<%=tipo %>"
			class="edit"> <i class="fa fa-user-plus" aria-hidden="true"></i> <spring:message
					text="??protocollo.label.assegna??"
					code="protocollo.label.assegna" />
		</a></li>
		<!-- FINE Aggiunta pannello per atti protocollati da validare MASSIMO CARUSO -->		
	
	<%} if(matUC.equalsIgnoreCase(matAS) && stato.equalsIgnoreCase("ASS") && view.getVo().getFascicoloAssociato()==null
			//aggiunta check per rimuovere tipo = ACT MASSIMO CARUSO
			&& !tipo.equalsIgnoreCase("ACT")
			//FINE aggiunta check per rimuovere tipo = ACT MASSIMO CARUSO
			){
	%>
	
		<li><a data-toggle="modal" 
			href="#protocolloPanelCercaSelezionaPadreFascicolo" data-id="<%=idProtocollo%>" data-name="<%=view.getVo().getNumProtocollo()%>"
			class="edit"> <i class="fa fa-stack-overflow" aria-hidden="true"></i> <spring:message
					text="??protocollo.label.sposta??"
					code="protocollo.label.sposta" />
		</a></li>
		<li><a onclick="lasciaInArchivioProtocollo(<%=idProtocollo%>)"
			class="edit"> <i class="fa fa-stack-exchange" aria-hidden="true"></i> <spring:message
					text="??protocollo.label.lascia??"
					code="protocollo.label.lascia" />
		</a></li>
		
<% 	}	
	
		if(isResponsabile
				//aggiunta check per rimuovere tipo = ACT MASSIMO CARUSO
				&& !tipo.equalsIgnoreCase("ACT")
				//FINE aggiunta check per rimuovere tipo = ACT MASSIMO CARUSO
				){
	%>		
		
		<li><a data-toggle="modal" 
			href="#panelRiassegna" data-id="<%=idProtocollo%>"
			<%
			if(view.getVo().getTipo().equalsIgnoreCase("IN")){
			%>
			data-name="<%=view.getVo().getDestinatarioMat().getMatricolaUtil()%>"
			<%
			}
			else{
			%>
			data-name="<%=view.getVo().getMittenteMat().getMatricolaUtil()%>"
			<%} %>
			data-nome="<%=view.getVo().getNumProtocollo()%>"
			class="edit"> <i class="fa fa-users" aria-hidden="true"></i> <spring:message
					text="??protocollo.label.riassegna??"
					code="protocollo.label.riassegna" />
		</a></li>
	
		

<%
		}
	} catch (Throwable e) {
		System.err.println("Errore in azioniProtocollo.jsp\n"+e.getMessage()+" per "+num_protocollo);
		e.printStackTrace();
	}
%>

	