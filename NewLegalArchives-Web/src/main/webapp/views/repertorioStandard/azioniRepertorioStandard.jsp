<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.RepertorioStandardView"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.RepertorioStandardService"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>

<%@page import="eng.la.persistence.CostantiDAO"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

 


<%
try{
	
	String idRepertorioStandard = request.getAttribute("idRepertorioStandard") + "";
	String nomeRepertorioStandard = request.getAttribute("nomeRepertorioStandard") + "";
	RepertorioStandardService service = (RepertorioStandardService) SpringUtil.getBean("repertorioStandardService");
	Long idRepertorioStandardL = NumberUtils.toLong(request.getAttribute("idRepertorioStandard") + "");
	RepertorioStandardView view = service.leggi(idRepertorioStandardL);


	
%> 
<div class="pull-right">
	<ul class="actions">
		<li class="dropdown">
			<a href="" data-toggle="dropdown" aria-expanded="false">
				<i class="zmdi zmdi-more-vert"></i>
			</a>
			<ul class="dropdown-menu dropdown-menu-right">

				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/repertorioStandard/visualizza.action?id=<%=idRepertorioStandard%>')}"
					class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
							text="??repertorioStandard.label.menuVisualizza??"
							code="repertorioStandard.label.menuVisualizza" />
				</a></li>

				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.REPERTORIO_STANDARD_W%>"><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/repertorioStandard/modifica.action?id=<%=idRepertorioStandard%>')}"
						class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
								text="??repertorioStandard.label.menuModifica??"
								code="repertorioStandard.label.menuModifica" />
				</a></legarc:isAuthorized></li>
					
				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.REPERTORIO_STANDARD_W%>"><a href="javascript:void(0)"
					data-idrepertorioStandard="<%=idRepertorioStandard%>" data-toggle="modal"
					data-target="#panelConfirmDeleteRepertorioStandard" class="edit"> <i
						class="fa fa-edit" aria-hidden="true"></i> <spring:message
							text="??repertorioStandard.label.menuElimina??"
							code="repertorioStandard.label.menuElimina" />
				</a></legarc:isAuthorized></li>


			</ul>
		</li>
	</ul>	
</div>


<%
}catch(Throwable e){
   e.printStackTrace();
}
 %>
