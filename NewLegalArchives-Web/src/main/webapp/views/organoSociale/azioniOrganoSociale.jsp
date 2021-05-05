<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.OrganoSocialeView"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.OrganoSocialeService"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>

<%@page import="eng.la.persistence.CostantiDAO"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<%
	try {

		String idOrganoSociale = request.getAttribute("idOrganoSociale") + "";
		String nomeOrganoSociale = request.getAttribute("nomeOrganoSociale") + "";
		OrganoSocialeService service = (OrganoSocialeService) SpringUtil
				.getBean("organoSocialeService");
		Long idOrganoSocialeL = NumberUtils.toLong(request.getAttribute("idOrganoSociale") + "");
		OrganoSocialeView view = service.leggi(idOrganoSocialeL);
%>
<div class="pull-right">

	<ul class="actions">
		<li class="dropdown"><a href="" data-toggle="dropdown"
			aria-expanded="false"> <i class="zmdi zmdi-more-vert"></i>
		</a>
			<ul class="dropdown-menu dropdown-menu-right">

				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/organoSociale/visualizza.action?id=<%=idOrganoSociale%>')}"
					class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
							text="??organoSociale.label.menuVisualizza??"
							code="organoSociale.label.menuVisualizza" />
				</a></li>

				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.ORGANO_SOCIALE_W%>">
						<a
							href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/organoSociale/modifica.action?id=<%=idOrganoSociale%>')}"
							class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
								text="??organoSociale.label.menuModifica??"
								code="organoSociale.label.menuModifica" />
						</a>
					</legarc:isAuthorized></li>

				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.ORGANO_SOCIALE_W%>">
						<a href="javascript:void(0)"
							data-idorganoSociale="<%=idOrganoSociale%>"
							data-toggle="modal"
							data-target="#panelConfirmDeleteOrganoSociale" class="edit">
							<i class="fa fa-edit" aria-hidden="true"></i> <spring:message
								text="??organoSociale.label.menuElimina??"
								code="organoSociale.label.menuElimina" />
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
