<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.ProgettoView"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.ProgettoService"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<%
try{
	/* String idFascicolo = request.getAttribute("idFascicolo") + "";
	String statoCodice = request.getAttribute("statoCodice")+"";  */
	
	String idProgetto = request.getAttribute("idProgetto") + "";
	String nomeProgetto = request.getAttribute("nomeProgetto") + "";
	String amministratore= request.getAttribute("amministratore")+""; 
	String modifica = request.getAttribute("modifica")+""; 
	ProgettoService service = (ProgettoService) SpringUtil.getBean("progettoService");
	Long idProgettoL = NumberUtils.toLong(request.getAttribute("idProgetto") + "");
	ProgettoView view = service.leggi(idProgettoL);

	
	if(amministratore == null){ amministratore = ""; }
	if(modifica == null){ modifica = ""; }
	UtenteView utenteConnesso = (UtenteView)session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

	
%> 
<div class="pull-right">
	<ul class="actions">
		<li class="dropdown">
			<a href="" data-toggle="dropdown" aria-expanded="false">
				<i class="zmdi zmdi-more-vert"></i>
			</a>
			<ul class="dropdown-menu dropdown-menu-right">

				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/progetto/visualizza.action?id=<%=idProgetto%>')}"
					class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
							text="??progetto.label.menuVisualizza??"
							code="progetto.label.menuVisualizza" />
				</a></li>
				
				<legarc:isAuthorized idEntita="<%=idProgettoL %>" tipoEntita="<%=Costanti.TIPO_ENTITA_PROGETTO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
				<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/progetto/modifica.action?id=<%=idProgetto%>')}"
						class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
								text="??progetto.label.menuModifica??"
								code="progetto.label.menuModifica" />
				</a></li>
				</legarc:isAuthorized>
					
				<legarc:isAuthorized idEntita="<%=idProgettoL %>" tipoEntita="<%=Costanti.TIPO_ENTITA_PROGETTO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
				<li><a href="javascript:void(0)"
					data-idprogetto="<%=idProgetto%>" data-toggle="modal"
					data-target="#panelConfirmDeleteProgetto" class="edit"> <i
						class="fa fa-edit" aria-hidden="true"></i> <spring:message
							text="??progetto.label.menuElimina??"
							code="progetto.label.menuElimina" />
				</a></li>
				</legarc:isAuthorized>
				
				<legarc:isAuthorized idEntita="<%=idProgettoL %>" tipoEntita="<%=Costanti.TIPO_ENTITA_PROGETTO%>"
									 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_SCRITTURA %>">
				<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/fascicolo/crea.action?idProgetto=<%=idProgetto%>&nomeProgetto=<%=nomeProgetto%>')}"
						class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
								text="??progetto.label.creaFascicolo??"
								code="progetto.label.creaFascicolo" />
				</a></li>
				</legarc:isAuthorized>

				<%
					if (utenteConnesso.isAmministratore()
								|| utenteConnesso.getVo().getUseridUtil().equals(view.getVo().getLegaleInterno())) {
				%>

				<li><a href="javascript:void(0)"
					data-idprogetto="<%=idProgetto%>" data-toggle="modal"
					data-target="#panelGestionePermessiProgetto" class="delete"> <i
						class="fa fa-user-plus" aria-hidden="true"></i> <spring:message
							text="??progetto.label.estendiPermessi??"
							code="progetto.label.estendiPermessi" />
				</a></li>
				
				
				<%
					}
				%>

			</ul>
		</li>
	</ul>
	
	
</div>


<%
}catch(Throwable e){
   e.printStackTrace();
}
 %>
