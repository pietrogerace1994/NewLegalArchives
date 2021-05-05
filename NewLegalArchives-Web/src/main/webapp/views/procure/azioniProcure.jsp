<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.ProcureView"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="eng.la.model.Fascicolo"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="eng.la.business.ProcureService"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>

<%@page import="eng.la.persistence.CostantiDAO"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>




<%
try{
	
	String idProcure = request.getAttribute("idProcure") + "";
	String nomeProcure = request.getAttribute("nomeProcure") + "";
	ProcureService service = (ProcureService) SpringUtil.getBean("procureService");
	Long idProcureL = NumberUtils.toLong(request.getAttribute("idProcure") + "");
	ProcureView view = service.leggi(idProcureL);
	List<ProcureView> listaProcure = service.leggi();
	UtenteView utenteConnesso = (UtenteView)session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);


	
%> 
<div class="pull-right" style="position:absolute;">

	<ul class="actions">
		<li class="dropdown">
			<a href="" data-toggle="dropdown" aria-expanded="false">
				<i class="zmdi zmdi-more-vert"></i>
			</a>
			<ul class="dropdown-menu dropdown-menu-right">

				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/procure/visualizza.action?id=<%=idProcure%>')}"
					class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
							text="??procure.label.menuVisualizza??"
							code="procure.label.menuVisualizza" />
				</a></li>

				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.PROCURE_CONFERITE_W%>">
						<a
							href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/procure/modifica.action?id=<%=idProcure%>')}"
							class="edit"> <i class="fa fa-edit" aria-hidden="true"></i> <spring:message
								text="??procure.label.menuModifica??"
								code="procure.label.menuModifica" />
						</a>
					</legarc:isAuthorized>
				</li>

				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.PROCURE_CONFERITE_W%>"><a href="javascript:void(0)"
					data-idprocure="<%=idProcure%>" data-toggle="modal"
					data-target="#panelConfirmDeleteProcure" class="edit"> <i
						class="fa fa-edit" aria-hidden="true"></i> <spring:message
							text="??procure.label.menuElimina??"
							code="procure.label.menuElimina" />
				</a></legarc:isAuthorized></li>
				
				<%
					if (utenteConnesso.isAmministratore()
								|| utenteConnesso.getVo().getUseridUtil().equals(view.getVo().getLegaleInterno())) {
				%>

				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.PROCURE_CONFERITE_W%>"><a href="javascript:void(0)"
					data-idprocure="<%=idProcure%>" data-toggle="modal"
					data-target="#panelGestionePermessiProcure" class="delete"> <i
						class="fa fa-user-plus" aria-hidden="true"></i> <spring:message
							text="??procure.label.estendiPermessi??"
							code="procure.label.estendiPermessi" />
				</a></legarc:isAuthorized></li>
				
				
				<%
					}
				%>
				
				<%
					if (utenteConnesso.isAmministratore()
								|| utenteConnesso.getVo().getUseridUtil().equals(view.getVo().getLegaleInterno())) {
						
						if(view.getVo().getFascicolo() != null){
							
					%>		
					<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/fascicolo/dettaglio.action?id=<%=view.getVo().getFascicolo().getId()%>')}"
						class="edit"> <i class="fa fa-eye" aria-hidden="true"></i> <spring:message
								text="??fascicolo.label.dettaglioFascicolo??"
								code="fascicolo.label.dettaglioFascicolo" />
					</a></li>
					<%
						}
					}
				%>
				
				<%
				
				boolean associabile = false;
				boolean checkSocieta=true;
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String dataCancellazione = "";
				if (view.getVo().getSocieta().getDataCancellazione()!=null) {
					dataCancellazione = df.format(view.getVo().getSocieta().getDataCancellazione());
					if ("31/12/2999".equalsIgnoreCase(dataCancellazione)) {
						checkSocieta = false;
					}
				}
				long idProcura = view.getVo().getId();
				boolean hasFascicolo = (view.getVo().getFascicolo() != null)?true:false;
				long notaio = view.getVo().getNotaio().getId();
				List<Fascicolo> fascicoliDaAssociare = new ArrayList<Fascicolo>();
				
				if(!hasFascicolo){
					
					for(ProcureView procuraView : listaProcure){
						
						if(procuraView.getVo().getId() != idProcura){
							
							if(procuraView.getVo().getNotaio().getId() == notaio){
								
								if(procuraView.getVo().getFascicolo() != null){
									associabile = true;
									
									boolean duplicato = false;
									for(Fascicolo f : fascicoliDaAssociare){
										
										if(f.getId() == procuraView.getVo().getFascicolo().getId()){
											
											duplicato = true;
										}
									}
									
									if(!duplicato){
										
										fascicoliDaAssociare.add(procuraView.getVo().getFascicolo());
									}
								}
							}
						}
							
					}
				}
				
				if(associabile){
					if (checkSocieta) {
					JSONArray jsonArray = new JSONArray();

					for(Fascicolo f : fascicoliDaAssociare){
						
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("idFascicolo", f.getId());
						jsonObject.put("nomeFascicolo", f.getNome());
						jsonArray.put(jsonObject);
					}
					
					String fascicoli = jsonArray.toString();
					fascicoli = fascicoli.replaceAll("\"", "*");
				
				
				%>
				
				<li>
					<legarc:isAuthorized nomeFunzionalita="<%=Costanti.PROCURE_CONFERITE_W%>">
						<a href="javascript:void(0)" onclick="visualizzaModalAssociazioni(<%=idProcura%>, '<%=fascicoli%>');"> 
							<i class="fa fa-external-link" aria-hidden="true"></i> 
							<spring:message text="??procure.label.associaFascicolo??" code="procure.label.associaFascicolo"/>
						</a>
					</legarc:isAuthorized>
				</li>
				
				<%
				}
}
				%>


			</ul>
		</li>
	</ul>
	
 
</div>


<%
}catch(Throwable e){
   e.printStackTrace();
}
 %>
 
