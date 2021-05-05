<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.AffariSocietariView"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.AffariSocietariService"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>

<%@page import="eng.la.persistence.CostantiDAO"%>
<%@page import="java.lang.Boolean"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<%
try{
	
	String idAffariSocietari = request.getAttribute("idAffariSocietari") + "";
	boolean cancellato = (Boolean)request.getAttribute("cancellato");
	String nomeAffariSocietari = request.getAttribute("nomeAffariSocietari") + "";
	AffariSocietariService service = (AffariSocietariService) SpringUtil.getBean("affariSocietariService");
	Long idAffariSocietariL = NumberUtils.toLong(request.getAttribute("idAffariSocietari") + "");
	AffariSocietariView view = service.leggi(idAffariSocietariL);


	
%> 
<div class="pull-right">

	<ul class="actions">
		<li class="dropdown">
			<a href="" data-toggle="dropdown" aria-expanded="false">
				<i class="zmdi zmdi-more-vert"></i>
			</a>
			<ul class="dropdown-menu dropdown-menu-right">

				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/affariSocietari/visualizza.action?id=<%=idAffariSocietari%>')}"
					class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
							text="??affariSocietari.label.menuVisualizza??"
							code="affariSocietari.label.menuVisualizza" />
				</a></li>


				<% if (cancellato){%>
				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.AFFARI_SOCIETARI_W%>"><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/affariSocietari/modifica.action?id=<%=idAffariSocietari%>')}"
						class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
								text="??affariSocietari.label.menuModifica??"
								code="affariSocietari.label.menuModifica" />
				</a></legarc:isAuthorized></li>
					
				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.AFFARI_SOCIETARI_W%>"><a href="javascript:void(0)"
					data-idaffariSocietari="<%=idAffariSocietari%>" data-toggle="modal"
					data-target="#panelConfirmDeleteAffariSocietari" class="edit"> <i
						class="fa fa-edit" aria-hidden="true"></i> <spring:message
							text="??affariSocietari.label.menuElimina??"
							code="affariSocietari.label.menuElimina" />
				</a></legarc:isAuthorized></li>
				<% }%>


			</ul>
		</li>
	</ul>
	

</div>


<%
}catch(Throwable e){
   e.printStackTrace();
}
 %>
 
