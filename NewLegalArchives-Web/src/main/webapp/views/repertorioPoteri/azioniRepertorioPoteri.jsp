<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.RepertorioPoteriView"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.RepertorioPoteriService"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>

<%@page import="eng.la.persistence.CostantiDAO"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>




<%
	try {

		String idRepertorioPoteri = request.getAttribute("idRepertorioPoteri") + "";
		String nomeRepertorioPoteri = request.getAttribute("nomeRepertorioPoteri") + "";
		RepertorioPoteriService service = (RepertorioPoteriService) SpringUtil
				.getBean("repertorioPoteriService");
		Long idRepertorioPoteriL = NumberUtils.toLong(request.getAttribute("idRepertorioPoteri") + "");
		RepertorioPoteriView view = service.leggi(idRepertorioPoteriL);
%>
<div class="pull-right">
	
	<ul class="actions">
		<li class="dropdown"><a href="" data-toggle="dropdown"
			aria-expanded="false"> <i class="zmdi zmdi-more-vert"></i>
		</a>
			<ul class="dropdown-menu dropdown-menu-right">

				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/repertorioPoteri/visualizza.action?id=<%=idRepertorioPoteri%>')}"
					class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
							text="??repertorioPoteri.label.menuVisualizza??"
							code="repertorioPoteri.label.menuVisualizza" />
				</a></li>

				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.REPERTORIO_POTERI_W%>">
						<a
							href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/repertorioPoteri/modifica.action?id=<%=idRepertorioPoteri%>')}"
							class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
								text="??repertorioPoteri.label.menuModifica??"
								code="repertorioPoteri.label.menuModifica" />
						</a>
					</legarc:isAuthorized></li>

				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.REPERTORIO_POTERI_W%>">
						<a href="javascript:void(0)"
							data-idrepertorioPoteri="<%=idRepertorioPoteri%>"
							data-toggle="modal"
							data-target="#panelConfirmDeleteRepertorioPoteri" class="edit">
							<i class="fa fa-edit" aria-hidden="true"></i> <spring:message
								text="??repertorioPoteri.label.menuElimina??"
								code="repertorioPoteri.label.menuElimina" />
						</a>
					</legarc:isAuthorized></li>


			</ul></li>
	</ul>


</div>


<%
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>

