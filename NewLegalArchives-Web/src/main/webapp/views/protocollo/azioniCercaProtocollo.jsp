<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.ProtocolloView"%>
<%@page import="eng.la.business.ArchivioProtocolloService"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.FascicoloView"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.FascicoloService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<%
try{
	String idProtocollo = request.getAttribute("idProtocollo") + "";
	ArchivioProtocolloService service = (ArchivioProtocolloService) SpringUtil.getBean("archivioProtocolloService");
	ProtocolloView view = service.leggi(Long.parseLong(idProtocollo));
	UtenteView utenteConnesso = ( UtenteView )session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
	boolean isAmministratore = utenteConnesso.isAmministratore();
%> 
<div class="pull-left" style="position:absolute;">
	<ul class="actions">
		<li class="dropdown">
			<a href=""  class=" palette-Green-200 bg"  data-toggle="dropdown" aria-expanded="false">
				<i class="zmdi zmdi-more-vert"></i>
			</a>
			<ul class="dropdown-menu dropdown-menu-left">
				 <jsp:include page="azioniProtocollo.jsp"> </jsp:include> 
			</ul>
		</li>
	</ul>
	
</div>

<%
}catch(Throwable e){
	System.err.println("Errore in azioniCercaProtocollo.jsp\n"+e.getMessage());
	e.printStackTrace();
}
 %>
