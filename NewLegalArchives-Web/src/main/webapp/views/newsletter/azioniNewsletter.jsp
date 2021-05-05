<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.NewsletterView"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.business.NewsletterService"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<%
	try {
		Long idNewsletter =  NumberUtils.toLong(request.getAttribute("idNewsletter") + "");
		NewsletterService service = (NewsletterService) SpringUtil.getBean("newsletterService");
		NewsletterView view = service.leggiNewsletter(idNewsletter);

		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
		boolean set = false;

		if (utenteConnesso.isAmministratore() || utenteService.leggiSeGestorePresidioNormativo(utenteConnesso))
			set = true;
%>
<li><a data-idnewsletter="<%=idNewsletter%>" data-toggle="modal"
	data-target="#panelAnteprimaNewsletter" class="delete"> <i
		class="fa fa-desktop" aria-hidden="true"></i> <spring:message
			text="??newsletter.label.anteprima??"
			code="newsletter.label.anteprima" />
</a></li>
<%
	if(set) {
%>


<li><a data-idnewsletter="<%=idNewsletter%>" data-toggle="modal"
	data-target="#panelInviaAnteprimaNewsletter" class="delete"> <i
		class="fa fa-paper-plane-o" aria-hidden="true"></i> <spring:message
			text="??newsletter.label.inviaanteprima??"
			code="newsletter.label.inviaanteprima" />
</a></li>

<%
		if (view.getVo().getStato().getCodGruppoLingua().equals(Costanti.NEWSLETTER_STATO_ATTIVA)) {
%>
<li><a data-idnewsletter="<%=idNewsletter%>" data-toggle="modal"
	data-target="#panelInviaNewsletter" class="delete"> <i
		class="fa fa-paper-plane" aria-hidden="true"></i> <spring:message
			text="??newsletter.label.invia??" code="newsletter.label.invia" />
</a></li>

<%
		}
		if (view.getVo().getStato().getCodGruppoLingua().equals(Costanti.NEWSLETTER_STATO_BOZZA)) {
%>

<li><a data-idnewsletter="<%=idNewsletter%>" data-toggle="modal"
	data-target="#panelAttivaNewsletter" class="delete"> <i
		class="fa fa-check-square" aria-hidden="true"></i> <spring:message
			text="??newsletter.label.attiva??" code="newsletter.label.attiva" />
</a></li>

<%}
		
		if (view.getVo().getStato().getCodGruppoLingua().equals(Costanti.NEWSLETTER_STATO_BOZZA) || 
				view.getVo().getStato().getCodGruppoLingua().equals(Costanti.NEWSLETTER_STATO_ATTIVA)) {	
		
		%>

<li><a data-idnewsletter="<%=idNewsletter%>" data-toggle="modal"
	data-target="#panelConfirmDeleteNewsletter" class="delete"> <i
		class="fa fa-trash-o" aria-hidden="true"></i> <spring:message
			text="??fascicolo.label.elimina??" code="fascicolo.label.elimina" />
</a></li>

<li><a
	href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/newsletter/modifica.action?id=<%=idNewsletter%>')}"
	class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
			text="??fascicolo.label.modifica??" code="fascicolo.label.modifica" />
</a></li>


<%
		}
		}
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>
