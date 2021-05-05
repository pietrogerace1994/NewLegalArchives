<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>
<%
	String f = request.getParameter("f");
	String action = "/NewLegalArchives";
	String id = null;
	String azione = null;
	String pa = null;
	String onlyfn = null;
	String isp = null;
	String uuid = null;
	String filter = null;
	String idProf = null;
	
	if(f!=null){
		if(f.equalsIgnoreCase("va")){
			id = request.getParameter("id");
			azione = request.getParameter("azione");
			action = "/NewLegalArchives/atto/visualizza.action";
		} else if(f.equalsIgnoreCase("ma")){
			id = request.getParameter("id");
			azione = request.getParameter("azione");
			action = "/NewLegalArchives/atto/visualizza.action";
		} else if(f.equalsIgnoreCase("df")){
			id = request.getParameter("id");
			action = "/NewLegalArchives/fascicolo/dettaglio.action";
		} else if(f.equalsIgnoreCase("di")){
			id = request.getParameter("id");
			action = "/NewLegalArchives/incarico/dettaglio.action";
		} else if(f.equalsIgnoreCase("dca")){
			id = request.getParameter("id");
			action = "/NewLegalArchives/incarico/dettaglioCollegioArbitrale.action";
		} else if(f.equalsIgnoreCase("dp")){
			onlyfn = request.getParameter("onlyfn");
			isp = request.getParameter("isp");
			uuid = request.getParameter("uuid");
			action = "/NewLegalArchives/download";
		} else if(f.equalsIgnoreCase("dpe")){
			idProf = request.getParameter("idProf");
			action = "/NewLegalArchives/professionistaEsterno/visualizzaProfEst.action";
		} else if(f.equalsIgnoreCase("dpr")){
			id = request.getParameter("id");
			action = "/NewLegalArchives/proforma/dettaglio.action";
		} else if(f.equalsIgnoreCase("dpa")){
			pa = request.getParameter("pa");
			action = "/NewLegalArchives/parcella/index.action";
		} else if(f.equalsIgnoreCase("mprot")){
			filter = request.getParameter("filter");
			action = "/NewLegalArchives/protocollo/gestioneProtocollo.action";
		} else if(f.equalsIgnoreCase("dsfr")){
			id = request.getParameter("id");
			action = "/NewLegalArchives/schedaFondoRischi/dettaglio.action";
		} else if(f.equalsIgnoreCase("bc")){
			id = request.getParameter("id");
			action = "/NewLegalArchives/beautyContest/dettaglio.action";
		} 
	}
%>
<script language="JavaScript">
function forw(){
	document.la.submit(); 
}
window.onload = forw;
</script>

 <form action="<%=action%>" name="la" method="get">
 	  <engsecurity:token />
	  <%if(id!=null){%>
	  	<input type="hidden" name="id" value="<%=id%>">
	  <%}
	  if(azione!=null){%>
	  <input type="hidden" name="azione" value="<%=azione%>">
	  <%}
	  if(pa!=null){%>
	  <input type="hidden" name="pa" value="<%=pa%>">
	  <%}
	  if(onlyfn!=null){%>
	  <input type="hidden" name="onlyfn" value="<%=onlyfn%>">
	  <%}
	  if(isp!=null){%>
	  <input type="hidden" name="isp" value="<%=isp%>">
	  <%}
	  if(uuid!=null){%>
	  <input type="hidden" name="uuid" value="<%=uuid%>">
	  <%}
	  if(filter!=null){%>
	  <input type="hidden" name="filter" value="<%=filter%>">
	  <%}
	  if(idProf!=null){%>
	  <input type="hidden" name="idProf" value="<%=idProf%>">
	  <%}%>
</form>