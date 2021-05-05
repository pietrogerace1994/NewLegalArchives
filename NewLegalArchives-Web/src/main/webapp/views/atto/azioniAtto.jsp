<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.FascicoloView"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.FascicoloService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%
try{
	String idFascicolo =request.getAttribute("idFascicolo") + "";
	String idAtto =request.getAttribute("idAtto") + "";
	Long idAttoL = NumberUtils.toLong(request.getAttribute("idAtto") + "");
	String statoCodice= request.getAttribute("statoCodice")+""; 
	String amministratore= request.getAttribute("amministratore")+""; 
	String modifica= request.getAttribute("modifica")+""; 
	boolean validato = false;
	if(request.getAttribute("validato") != null)
		validato = request.getAttribute("validato").equals("true")? true : false;
	if(amministratore==null){ amministratore=""; }
	if(modifica==null){ modifica=""; }
	
%> 
<div class="pull-right">
 
	<ul class="actions">
		<li class="dropdown">
			<a href="" data-toggle="dropdown" aria-expanded="false">
				<i class="zmdi zmdi-more-vert"></i>
			</a>
			<ul class="dropdown-menu dropdown-menu-right">
				<li>
					<a href="javascript:void(0)" onclick="openAtto('<%=idAtto %>','visualizza')" class="edit">
						<i class="fa fa-eye" aria-hidden="true"></i>
						 <spring:message text="??atto.label.menuVisualizza??" code="atto.label.menuVisualizza" />
					</a>
				</li> <!-- IN BOZZA -->
				<li>
					<a href="javascript:void(0)" onclick="stampaAtto('<%=idAtto %>')" class="edit">
						<i class="fa fa-print" aria-hidden="true"></i>
						<spring:message text="??atto.button.stampa??" code="atto.button.stampa" />
					</a>
				</li>
				
				<%if(statoCodice!=null && statoCodice.equalsIgnoreCase("S") ){ %>
				<%if(idFascicolo!=null && !idFascicolo.equalsIgnoreCase("") ){ %>
				<li>
					<a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/fascicolo/dettaglio.action?id=<%=idFascicolo %>')}"  class="edit">
						<i class="fa fa-eye" aria-hidden="true"></i>
						 <spring:message text="??menu.label.fascicoli??" code="menu.label.fascicoli" /> <spring:message text="??fascicolo.label.dettaglio??" code="fascicolo.label.dettaglio" />
					</a>
				</li>
				<li>
					<a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/fascicolo/contenuto.action?id=<%=idFascicolo %>')}"  class="edit">
						<i class="fa fa-eye" aria-hidden="true"></i>
						 <spring:message text="??menu.label.fascicoli??" code="menu.label.fascicoli" /> <spring:message text="??fascicolo.label.alberatura??" code="fascicolo.label.alberatura" />
					</a>
				</li>
				<% } %>
				<% } %>
				<!-- aggiunta pulsante modifica all'atto da validare MASSIMO CARUSO -->
				<%if(statoCodice!=null && (statoCodice.equalsIgnoreCase("B") || statoCodice.equalsIgnoreCase("VAL")) ){ %>
				<%if(modifica!=null && modifica.equalsIgnoreCase("yes") ){ %>
				<li>
					<a href="javascript:void(0)" onclick="openAtto('<%=idAtto %>','modifica')" class="edit">
						<i class="fa fa-edit" aria-hidden="true"></i>
						 
						<spring:message text="??atto.label.menuModifica??" code="atto.label.menuModifica" />
					</a>
				</li> 
				<% } %>
				
				<legarc:isAuthorized idEntita="<%=idAttoL %>" tipoEntita="<%=Costanti.TIPO_ENTITA_ATTO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
				
				<!-- aggiunta pulsante richiesta validazione per atti da validare MASSIMO CARUSO -->
				<%if(!statoCodice.equalsIgnoreCase("VAL") || (statoCodice.equalsIgnoreCase("VAL") && validato)){ %>
				<li>
					<a href="javascript:void(0)" data-idAtto="<%=idAtto %>" onclick="registraAtto('<%=idAtto %>')" class="edit">
						<i class="fa fa-archive" aria-hidden="true"></i>
						<spring:message text="??atto.label.menuRichiediRegAtto??" code="atto.label.menuRichiediRegAtto" />		
					</a>
				</li>
				<% } %>
				
				<!-- rimozione pulsante invia altri uffici all'atto da validare MASSIMO CARUSO -->
				<%if(!statoCodice.equalsIgnoreCase("VAL")){ %>
				<li>
					<a href="javascript:void(0)" onclick="openAtto('<%=idAtto %>','inviauffici')" class="edit">
						<i class="fa fa-edit" aria-hidden="true"></i>
						<spring:message text="??atto.label.menuInviaAltriUff??" code="atto.label.menuInviaAltriUff" />
					</a>
				</li>
				<% } %>
				</legarc:isAuthorized>
				<% } %>
				
			   <!-- REGISTRATO -->
				<%if(statoCodice!=null && statoCodice.equalsIgnoreCase("R")){ %>
				<legarc:isAuthorized idEntita="<%=idAttoL %>" tipoEntita="<%=Costanti.TIPO_ENTITA_ATTO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
				<li>
					<a href="javascript:void(0)" data-idAtto="<%=idAtto %>" onclick="openModalListFascicoli('<%=idAtto %>')" class="edit">
						<i class="fa fa-plus" aria-hidden="true"></i>
						<spring:message text="??atto.label.menuAssociaAttoFascicolo??" code="atto.label.menuAssociaAttoFascicolo" />				
					</a>
				</li> 
				</legarc:isAuthorized>
				<%if(amministratore!=null && amministratore.equalsIgnoreCase("yes")){ %>
				<li>
				<a data-idAtto="<%=idAtto %>"  onclick="riportaInBozza('<%=idAtto %>')" class="edit" >
						<i class="fa fa-edit" aria-hidden="true"></i>
						<spring:message text="??atto.label.menuRiportaInBozza??" code="atto.label.menuRiportaInBozza" />				
					</a>
				</li>
				<% } } %>
						 <!-- IN ATTESA DI REGISTRAZIONE - IN ATTESA DI PRESA IN CARICO - IN ATTESA DI PRESA IN CARICO LEGALE INTERNO -->
				<%if(statoCodice!=null){ %>
				<%if(statoCodice.equalsIgnoreCase("ADR") || statoCodice.equalsIgnoreCase("APIC") || statoCodice.equalsIgnoreCase("APICL")){ %>
				<%if(amministratore!=null && amministratore.equalsIgnoreCase("yes")){ %>
				<li>
					<a data-idAtto="<%=idAtto %>" onclick="discardWorkLow('<%=idAtto %>')" class="edit">
						<i class="fa fa-edit" aria-hidden="true"></i>
						<spring:message text="??atto.label.menuDiscardWorkLow??" code="atto.label.menuDiscardWorkLow" />			
					</a>
				</li> 
						
				<li>
					<a data-idAtto="<%=idAtto %>" onclick="riportaInBozza('<%=idAtto %>')" class="edit">
						<i class="fa fa-edit" aria-hidden="true"></i>
						<spring:message text="??atto.label.menuRiportaInBozza??" code="atto.label.menuRiportaInBozza" />					
					</a>
				</li> 
				
				<% } } } %>
			 
		
				
			</ul>
		</li>
	</ul>
 
</div>
<%
}catch(Throwable e){
	e.printStackTrace();
}
 %>
