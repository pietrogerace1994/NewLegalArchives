<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.RubricaView"%>
<%@page import="eng.la.business.RubricaService"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>




<%
	try {
		Long idRubrica =  NumberUtils.toLong(request.getAttribute("idRubrica") + "");
		RubricaService service = (RubricaService) SpringUtil.getBean("rubricaService");
		RubricaView view = service.leggiRubrica( idRubrica);
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		boolean isAmministratore = utenteConnesso.isAmministratore();
%>


	<li><a
		href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/presidionormativo/modifica.action?id=<%=idRubrica%>')}"
		class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
				text="??fascicolo.label.modifica??" code="fascicolo.label.modifica" />
	</a></li>
	
	<li><a data-idrubrica="<%=idRubrica%>" data-toggle="modal"
		data-target="#panelConfirmDeleteRubrica" class="delete"> <i
			class="fa fa-trash-o" aria-hidden="true"></i> <spring:message
				text="??fascicolo.label.elimina??" code="fascicolo.label.elimina" />
	</a></li>

<%
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>

