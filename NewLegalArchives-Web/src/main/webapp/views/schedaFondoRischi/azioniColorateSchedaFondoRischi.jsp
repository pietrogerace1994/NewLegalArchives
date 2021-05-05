<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.model.view.SchedaFondoRischiView"%>
<%@page import="eng.la.business.SchedaFondoRischiService"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.FascicoloView"%>
<%@page import="java.lang.String"%>
<%@page import="eng.la.model.Fascicolo"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.FascicoloService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<%
	try {
		Long idScheda = NumberUtils.toLong(request.getAttribute("idScheda") + "");
		String coloreScheda = (String) request.getAttribute("coloreScheda");
		SchedaFondoRischiService service = (SchedaFondoRischiService) SpringUtil.getBean("schedaFondoRischiService");
		SchedaFondoRischiView view = service.leggi(idScheda);
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		boolean isAmministratore = utenteConnesso.isAmministratore();
%>

 	<legarc:isAuthorized idEntita="<%=idScheda %>" tipoEntita="<%=Costanti.TIPO_ENTITA_SCHEDA_FONDO_RISCHI %>"
								 	 tipoPermesso="<%=Costanti.TIPO_PERMESSO_LETTURA %>">
			<li><a
				href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/schedaFondoRischi/dettaglio.action?id=<%=idScheda%>')}"
				class="edit"> <i class="fa fa-eye" aria-hidden="true"></i> <spring:message
						text="??fascicolo.label.dettaglio??"
						code="fascicolo.label.dettaglio" />
			</a></li>
	</legarc:isAuthorized>


		<li>
			<a href="javascript:void(0)" onclick="stampaScheda('<%=idScheda%>')" class="edit">
				<i class="fa fa-print" aria-hidden="true"></i>
				<spring:message text="??schedaFondoRischi.button.stampa??" code="schedaFondoRischi.button.stampa" />
			</a>
		</li>
	<%
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>
