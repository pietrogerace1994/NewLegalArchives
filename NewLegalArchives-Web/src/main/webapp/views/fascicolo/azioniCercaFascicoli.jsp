<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.FascicoloView"%>
<%@page import="org.apache.commons.lang.math.NumberUtils"%>
<%@page import="eng.la.business.FascicoloService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<%
try{
	String idFascicolo = request.getAttribute("idFascicolo") + "";
	FascicoloService service = (FascicoloService) SpringUtil.getBean("fascicoloService");
	FascicoloView view = service.leggi(NumberUtils.toLong(idFascicolo));
%> 
<div class="pull-left" style="position:absolute;">
	<ul class="actions">
		<li class="dropdown"> 
			<a href="" class=" palette-Green-200 bg"  data-toggle="dropdown" aria-expanded="false">
				<i class="zmdi zmdi-more-vert"></i>
			</a> 
			<ul class="dropdown-menu dropdown-menu-left">
				<jsp:include page="azioniFascicolo.jsp"> </jsp:include> 
			</ul>
		</li>
	</ul>
	
</div>
<%
}catch(Throwable e){
	e.printStackTrace();
}
 %>
