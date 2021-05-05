<%@page import="eng.la.model.view.IncaricoView"%>
<%@page import="java.util.List"%>
<%@page import="eng.la.business.IncaricoService"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.FascicoloView"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.FascicoloService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="java.lang.String"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<%
	try {
		Long idFascicolo = NumberUtils.toLong(request.getAttribute("idFascicolo") + "");
		FascicoloService service = (FascicoloService) SpringUtil.getBean("fascicoloService");
		FascicoloView view = service.leggi(idFascicolo);
		
		String nomeProgetto=null;
		if (view.getVo() != null && view.getVo().getProgetto()!= null && view.getVo().getProgetto().getId() > 0){
			 nomeProgetto = view.getVo().getProgetto().getNome();
		}
		System.out.println("nome progetto da associato --------------"+nomeProgetto);
		UtenteView utenteConnesso = (UtenteView)session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
%> 
	<legarc:isAuthorized idEntita="<%=idFascicolo %>" tipoEntita="<%=Costanti.TIPO_ENTITA_FASCICOLO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_LETTURA %>">
		
			<li><a
		href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/fascicolo/dettaglio.action?id=<%=idFascicolo%>')}" 
			class="edit"> <i class="fa fa-eye" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.dettaglio??"
					code="fascicolo.label.dettaglio" />
		</a></li>
		 <input name="id" type="hidden" value='<%=idFascicolo%>'> 
	</legarc:isAuthorized>
	<legarc:isAuthorized idEntita="<%=idFascicolo %>" tipoEntita="<%=Costanti.TIPO_ENTITA_FASCICOLO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_LETTURA %>">
	
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/fascicolo/contenuto.action?id=<%=idFascicolo%>')}"
			class="edit"> <i class="fa fa-eye" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.alberatura??"
					code="fascicolo.label.alberatura" />
		</a></li>
	</legarc:isAuthorized>
	<%
		if (!view.getVo().getStatoFascicolo().getCodGruppoLingua()
					.equals(Costanti.FASCICOLO_STATO_ATTESA_AUTORIZZAZIONE_CHIUSURA)
					&& !view.getVo().getStatoFascicolo().getCodGruppoLingua()
							.equals(Costanti.FASCICOLO_STATO_CHIUSO)) {
	%>
	<legarc:isAuthorized idEntita="<%=idFascicolo %>" tipoEntita="<%=Costanti.TIPO_ENTITA_FASCICOLO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/fascicolo/modifica.action?id=<%=idFascicolo%>')}"
			class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.modifica??" code="fascicolo.label.modifica" />
		</a></li>
	</legarc:isAuthorized>	
	<%
		}
	%>
	
	<li>
		<a href="javascript:void(0)" onclick="stampaFascicoloDettaglio('<%=idFascicolo%>')" class="edit">
			<i class="fa fa-print" aria-hidden="true"></i>
			<spring:message text="??generic.label.stampa??" code="generic.label.stampa" />
		</a>
	</li>
	
	<%
		if (view.getVo().getNome() != null) {
			String nomeFascicolo = view.getVo().getNome();
	%>
	
		<legarc:isAuthorized idEntita="<%=idFascicolo %>" tipoEntita="<%=Costanti.TIPO_ENTITA_FASCICOLO%>"
										 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
			<%
			// Controllo owner fascicolo
			String ownerFascicolo = view.getVo().getLegaleInterno();
			if(ownerFascicolo.equals(utenteConnesso.getVo().getUseridUtil())){
			%>
			
				<li><a href="javascript:void(0)" onclick="aggiungiEventoScadenza(<%=idFascicolo%>, '<%=nomeFascicolo%>');"> 
						<i class="fa fa-calendar-plus-o" aria-hidden="true"></i> 
						<spring:message text="??fascicolo.label.aggiungiEventoScadenza??" code="fascicolo.label.aggiungiEventoScadenza"/>
				</a></li>
			<%
			}
			%>
		</legarc:isAuthorized>
	<%
	
		}
	%>
	<%
		if (!view.getVo().getStatoFascicolo().getCodGruppoLingua()
					.equals(Costanti.FASCICOLO_STATO_ATTESA_AUTORIZZAZIONE_CHIUSURA)) {
	%>
	<legarc:isAuthorized idEntita="<%=idFascicolo %>" tipoEntita="<%=Costanti.TIPO_ENTITA_FASCICOLO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idfascicolo="<%=idFascicolo%>" data-toggle="modal"
			data-target="#panelConfirmDeleteFascicolo" class="delete"> <i
				class="fa fa-trash-o" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.elimina??" code="fascicolo.label.elimina" />
		</a></li>
	</legarc:isAuthorized>
	<%
		}
	%>

	<%
		if (view.getVo().getStatoFascicolo().getCodGruppoLingua().equals(Costanti.FASCICOLO_STATO_COMPLETATO)) {
	%>
	<legarc:isAuthorized idEntita="<%=idFascicolo %>" tipoEntita="<%=Costanti.TIPO_ENTITA_FASCICOLO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<%
			// Controllo owner fascicolo
			String ownerFascicolo = view.getVo().getLegaleInterno();
			if(ownerFascicolo.equals(utenteConnesso.getVo().getUseridUtil())){
		%>
	
		<li><a href="javascript:void(0)"
			data-idfascicolo="<%=idFascicolo%>" data-toggle="modal"
			data-target="#panelConfirmRichiediChiusuraFascicolo" class="delete"> <i
				class="fa fa-line-chart" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.richiediChiusura??"
					code="fascicolo.label.richiediChiusura" />
		</a></li>
		
		<%
			}
		%>
		
	</legarc:isAuthorized>
	<%
		}
	%>


	<%
		if (!view.getVo().getStatoFascicolo().getCodGruppoLingua()
					.equals(Costanti.FASCICOLO_STATO_ATTESA_AUTORIZZAZIONE_CHIUSURA)
					&& !view.getVo().getStatoFascicolo().getCodGruppoLingua()
							.equals(Costanti.FASCICOLO_STATO_CHIUSO)) {
	%>
	<legarc:isAuthorized idEntita="<%=idFascicolo %>" tipoEntita="<%=Costanti.TIPO_ENTITA_FASCICOLO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/incarico/crea.action?fascicoloId=<%=idFascicolo%>')}"
			class="edit"> <i class="fa fa-plus" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.aggiungiIncarico??"
					code="fascicolo.label.aggiungiIncarico" />
		</a></li>
		
		<li><a href="javascript:void(0)"
			data-idfascicolo="<%=idFascicolo%>" class="edit" data-toggle="modal"
			data-target="#panelAggiungiDocFascicolo"> <i class="fa fa-upload"
				aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.aggiungiDocumento??"
					code="fascicolo.label.aggiungiDocumento" />
		</a></li>
	
	
	<%
		if (!view.getVo().getStatoFascicolo().getCodGruppoLingua().equals(Costanti.FASCICOLO_STATO_CHIUSO)) {
			
			if(view.getVo().getSchedaFondoRischi() != null){
				
				long idSchedaFondoRischi = view.getVo().getSchedaFondoRischi().getId();
				
				%>
					<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/schedaFondoRischi/modifica.action?id=<%=idSchedaFondoRischi%>')}"
						class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
								text="??fascicolo.label.modificaSchedaFondoRischi??"
								code="fascicolo.label.modificaSchedaFondoRischi" />
					</a></li>
				<%
			}
			else{
				%>
					<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/schedaFondoRischi/crea.action?fascicoloId=<%=idFascicolo%>')}"
						class="edit"> <i class="fa fa-plus" aria-hidden="true"></i> <spring:message
								text="??fascicolo.label.aggiungiSchedaFondoRischi??"
								code="fascicolo.label.aggiungiSchedaFondoRischi" />
					</a></li>
				<%
			}
		}
	%>
	</legarc:isAuthorized>
	<%
	}
	%>
	
	<legarc:isAuthorized idEntita="<%=idFascicolo %>" tipoEntita="<%=Costanti.TIPO_ENTITA_FASCICOLO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_LETTURA %>">
	<%
		if (!view.getVo().getStatoFascicolo().getCodGruppoLingua().equals(Costanti.FASCICOLO_STATO_CHIUSO)) {
			
			%>
				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/udienza/crea.action?fascicoloId=<%=idFascicolo%>')}"
					class="edit"> <i class="fa fa-plus" aria-hidden="true"></i> <spring:message
							text="??fascicolo.label.aggiungiUdienza??"
							code="fascicolo.label.aggiungiUdienza" />
				</a></li>
			<%
		}
	%>
	</legarc:isAuthorized>
	
	<legarc:isAuthorized idEntita="<%=idFascicolo %>" tipoEntita="<%=Costanti.TIPO_ENTITA_FASCICOLO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">


		<li><a href="javascript:void(0)"
			data-idfascicolo="<%=idFascicolo%>" class="edit" data-toggle="modal"
			data-target="#panelConfirmArchiviaFascicolo"> <i class="fa fa-archive"
				aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.richiediArchiviazione??"
					code="fascicolo.label.richiediArchiviazione" />
		</a></li>
		
	</legarc:isAuthorized>
	<%
		if (view.getVo().getStatoFascicolo().getCodGruppoLingua().equals(Costanti.FASCICOLO_STATO_APERTO)
				) {
	%>
	<legarc:isAuthorized idEntita="<%=idFascicolo %>" tipoEntita="<%=Costanti.TIPO_ENTITA_FASCICOLO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idfascicolo="<%=idFascicolo%>" data-toggle="modal"
			data-target="#panelConfirmCompletaFascicolo" class="delete"> <i
				class="fa fa-check-circle-o " aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.richiediCompletaFascicolo??"
					code="fascicolo.label.richiediCompletaFascicolo" />
		</a></li>
	</legarc:isAuthorized>
	<%
		}
	%>

	<%
		if (view.getVo().getStatoFascicolo().getCodGruppoLingua().equals(Costanti.FASCICOLO_STATO_COMPLETATO)
					|| view.getVo().getStatoFascicolo().getCodGruppoLingua()
							.equals(Costanti.FASCICOLO_STATO_CHIUSO)) {
	%>
	<legarc:isAuthorized idEntita="<%=idFascicolo %>" tipoEntita="<%=Costanti.TIPO_ENTITA_FASCICOLO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
	
		<li><a href="javascript:void(0)"
			data-idfascicolo="<%=idFascicolo%>" data-toggle="modal"
			data-target="#panelConfirmRiapriFascicolo" class="delete"> <i
				class="fa fa-fast-backward" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.richiediRiapriFascicolo??"
					code="fascicolo.label.richiediRiapriFascicolo" />
		</a></li>
	</legarc:isAuthorized>
	<%
		}
	%> 

	
	<%if( utenteConnesso.isAmministratore() || utenteConnesso.getVo().getUseridUtil().equals(view.getVo().getLegaleInterno())) {%>
	 
		<li><a href="javascript:void(0)"
			data-idfascicolo="<%=idFascicolo%>" data-toggle="modal"
			data-target="#panelGestionePermessiFascicolo" class="delete"> <i
				class="fa fa-user-plus" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.estendiPermessi??"
					code="fascicolo.label.estendiPermessi" />
		</a></li> 
		
		<li><a href="javascript:void(0)"
			data-idfascicolo="<%=idFascicolo%>" data-nomeprogetto="<%=(nomeProgetto == null ? "" : nomeProgetto.toString())%>" data-toggle="modal"
			data-target="#panelGestioneAssociaFascicoloAProgetto" class="delete"> <i
				class="fa fa-user-plus" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.associaAProgetto??"
					code="fascicolo.label.associaAProgetto" />
		</a></li> 
		
	<%} %>	
	
	<%if( utenteConnesso.isAmministratore() && view.getVo().getStatoFascicolo().getCodGruppoLingua()
			.equals(Costanti.FASCICOLO_STATO_ATTESA_AUTORIZZAZIONE_CHIUSURA)) {%>
	 
		<li><a href="javascript:void(0)"
			data-idfascicolo="<%=idFascicolo%>" data-toggle="modal"
			data-target="#panelConfirmRiportaInCompletatoFascicolo" class="delete"> <i
				class="fa fa-check-circle-o" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.riportaInCompletato??"
					code="fascicolo.label.riportaInCompletato" />
		</a></li> 
		
	<%} %>	
	
	<%if(utenteConnesso.getVo().getUseridUtil().equals(view.getVo().getLegaleInterno())) {%>
	 
		<li><a href="javascript:void(0)"
			data-idfascicolo="<%=idFascicolo%>" data-toggle="modal"
			data-target="#panelCambiaOwnerFascicolo" class="delete"> <i
				class="fa fa-user-plus" aria-hidden="true"></i> <spring:message
					text="??fascicolo.label.cambiaOwner??"
					code="fascicolo.label.cambiaOwner" />
		</a></li> 
		
	<%} %>	
	<%
			IncaricoService incService = (IncaricoService)SpringUtil.getBean("incaricoService");
			List<IncaricoView> incarichi = incService.cerca("", "", 0, "", "", Costanti.INCARICO_STATO_AUTORIZZATO, view.getVo().getNome(), 1000, 1, "id", "asc");
			if( incarichi != null ){
				%>   
					<% for( IncaricoView incView : incarichi) { 
					  	  if (incView.getVo().getStatoIncarico().getCodGruppoLingua().equals(Costanti.INCARICO_STATO_AUTORIZZATO)) {%>
					
						    <legarc:isAuthorized idEntita="<%=incView.getVo().getId() %>" tipoEntita="<%=Costanti.TIPO_ENTITA_INCARICO %>"
							 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
			
								<li><a
									href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/proforma/crea.action?incaricoId=<%=incView.getVo().getId()%>')}"
									class="edit"> <i class="fa fa-plus"  ></i> <spring:message
							text="??fascicolo.label.incarichiAddProforma??"
							code="fascicolo.label.incarichiAddProforma" /> - <b><%=incView.getVo().getNomeIncarico()%></b>
								</a></li>
						    </legarc:isAuthorized>
					<%	  }
				 	  } %>	  
				<%
			}
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>
