<%@page import="eng.la.persistence.CostantiDAO"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="eng.la.model.view.AutorizzazioneView"%>
<%@page import="java.util.List"%>
<%@page import="eng.la.business.AutorizzazioneService"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.ProcureView"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.ProcureService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<%
	try {
		Long idProcure = NumberUtils.toLong(request.getAttribute("idProcure") + "");
		ProcureService service = (ProcureService) SpringUtil.getBean("procureService");
		ProcureView view = service.leggi(idProcure);
		AutorizzazioneService autorizzazioneService = (AutorizzazioneService) SpringUtil.getBean("autorizzazioneService");
		UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
		
		UtenteView utenteConnesso = (UtenteView)session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		List<AutorizzazioneView> listaPermessiCorrenti = autorizzazioneService.leggiAutorizzazioni(idProcure, Costanti.TIPO_ENTITA_PROCURE);
		List<UtenteView> listaUtenti = utenteService.leggiUtentiAlesocr();
		if( listaUtenti != null ){
			for(UtenteView utenteView : listaUtenti ){
				if( !utenteView.isAmministratore() && !utenteView.getVo().getUseridUtil().equals(view.getVo().getLegaleInterno())){
				%>
					<tr>
						<td><%=utenteView.getVo().getNominativoUtil() %></td> 
						<%
						boolean isCheckedLettura = false;
						boolean isCheckedScrittura = false;
						if( listaPermessiCorrenti != null ){
							for( AutorizzazioneView autorizzazioneView : listaPermessiCorrenti ){
								if( autorizzazioneView.getVo().getUtente() != null 
										&& autorizzazioneView.getVo().getUtente().getUseridUtil().equals(utenteView.getVo().getUseridUtil())){
									if( autorizzazioneView.getVo().getTipoAutorizzazione().getCodGruppoLingua().contains(Costanti.TIPO_PERMESSO_SCRITTURA)){
										isCheckedScrittura = true;																					
									}
									
									if( autorizzazioneView.getVo().getTipoAutorizzazione().getCodGruppoLingua().contains(Costanti.TIPO_PERMESSO_LETTURA)){
										isCheckedLettura = true;
									}
								}
							}
						}
						
						String checkedLettura = isCheckedLettura ? "checked":"";
						String checkedScrittura = isCheckedScrittura ? "checked":"";
						%>
						<td><input type="checkbox" name="chkPermessiLettura" value="<%=utenteView.getVo().getUseridUtil() %>" <%=checkedLettura %>/></td>
						<td><input type="checkbox" name="chkPermessiScrittura" value="<%=utenteView.getVo().getUseridUtil() %>" <%=checkedScrittura %>/></td>
					</tr>
				<%
				}
			}
		}
%> 
	 
<%
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>

