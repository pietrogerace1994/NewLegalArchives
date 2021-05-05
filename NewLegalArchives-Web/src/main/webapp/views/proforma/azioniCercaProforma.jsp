<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.ProformaView"%>
<%@page import="eng.la.business.ProformaService"%> 
<%@page import="eng.la.util.costants.Costanti"%> 
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<%
try{
	String idProforma = request.getAttribute("idProforma") + "";
	ProformaService service = (ProformaService) SpringUtil.getBean("proformaService");
	ProformaView view = service.leggi(NumberUtils.toLong(idProforma));
	UtenteView utenteConnesso = ( UtenteView )session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
	boolean isAmministratore = utenteConnesso.isAmministratore();
	String isFatturato =  request.getAttribute("isFatturato") + "";
	String isContabilizzato =  request.getAttribute("isContabilizzato") + "";
%> 
<div class="pull-left" style="position:absolute;">
	<ul class="actions">
		<li class="dropdown">
		<% if(isFatturato.equals("T") && isContabilizzato.equals("F")){ %>
		<a href=""  class=" palette-Green-200" style="background:rgb(255, 190, 7);"  data-toggle="dropdown" aria-expanded="false">
				<i class="zmdi zmdi-more-vert"></i>
			</a>
		<% } %>
		<% if(isFatturato.equals("T") && isContabilizzato.equals("T")){ %>
		<a href=""  class=" palette-Green-200" style="background:rgb(243, 0, 0);"  data-toggle="dropdown" aria-expanded="false">
				<i class="zmdi zmdi-more-vert"></i>
			</a>
		<% } %>
		<% if(isFatturato.equals("F") && isContabilizzato.equals("F")){ %>
		<a href=""  class=" palette-Green-200 bg"  data-toggle="dropdown" aria-expanded="false">
				<i class="zmdi zmdi-more-vert"></i>
			</a>
		<% } %>
			 
			<ul class="dropdown-menu dropdown-menu-left">
				<jsp:include page="azioniProforma.jsp"> </jsp:include> 
			</ul>
		</li>
	</ul>
	
</div>
<%
}catch(Throwable e){
	e.printStackTrace();
}
 %>
