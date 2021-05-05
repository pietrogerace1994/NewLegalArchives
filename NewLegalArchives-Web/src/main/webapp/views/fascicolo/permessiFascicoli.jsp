<%@page import="eng.la.persistence.CostantiDAO"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="eng.la.model.view.AutorizzazioneView"%>
<%@page import="java.util.List"%>
<%@page import="eng.la.business.AutorizzazioneService"%>
<%@page import="eng.la.model.view.UtenteView"%>
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
	try {
		String fascicoli = request.getAttribute("idFascicoli") + "";
		
		String[] idFascicoli = fascicoli.split("-");
		
		FascicoloService service = (FascicoloService) SpringUtil.getBean("fascicoloService");
		AutorizzazioneService autorizzazioneService = (AutorizzazioneService) SpringUtil.getBean("autorizzazioneService");
		UtenteView utenteConnesso = (UtenteView)session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
		List<UtenteView> listaUtenti = utenteService.leggiUtenti();
		
		for(int i=0; i<idFascicoli.length; i++){
			
			Long idFascicolo = NumberUtils.toLong(idFascicoli[i] +"");
			
			FascicoloView view = service.leggi(idFascicolo);
			
			List<AutorizzazioneView> listaPermessiCorrenti = autorizzazioneService.leggiAutorizzazioni(idFascicolo, Costanti.TIPO_ENTITA_FASCICOLO);
			
			%>
			<tr><td>
			<table class="table table-striped table-responsive table-hover">
							
				<tr>
					<th style="width:60%">
						<spring:message text="??fascicolo.label.nome??" code="fascicolo.label.nome" /> : 
						<%= view.getVo().getNome() %>	
					</th>
					<th></th>
					<th></th>
				</tr>
				
				<tr>
					<th style="width:60%"><spring:message text="??fascicolo.label.utente??"
							code="fascicolo.label.utente" /></th>
					<th><spring:message text="??fascicolo.label.permessoLettura??"
							code="fascicolo.label.permessoLettura" /></th>
					<th><spring:message text="??fascicolo.label.permessoScrittura??"
							code="fascicolo.label.permessoScrittura" /></th>
				</tr>
					
				<tbody>
			<%
			if( listaUtenti != null ){
				for(UtenteView utenteView : listaUtenti ){
					if( !utenteView.isAmministratore() && !utenteView.getVo().getUseridUtil().equals(view.getVo().getLegaleInterno())){
						
						if(utenteView.getVo().getNominativoUtil() != null && !utenteView.getVo().getNominativoUtil().isEmpty()){
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
								<td><input type="checkbox" name="chkPermessiLettura" value="<%=utenteView.getVo().getUseridUtil()%>-<%=view.getVo().getId()%>" <%=checkedLettura %>/></td>
								<td><input type="checkbox" name="chkPermessiScrittura" value="<%=utenteView.getVo().getUseridUtil()%>-<%=view.getVo().getId()%>" <%=checkedScrittura %>/></td>
							</tr>
						<%
						}
					}
				}
				%>
				</tbody>

			</table>
			</td></tr>
			<%
			}
			
		}
	} catch (Throwable e) {
		e.printStackTrace();
	}
%>