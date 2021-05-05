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
	String idAtto =request.getAttribute("idAtto") + "";
	
	
%> 

<div class="pull-right">
	 
			<ul class="dropdown-menu dropdown-menu-right" style="display:block">
				<li>
					<a href="javascript:void(0)" onclick="openAtto('<%=idAtto %>','visualizza')" class="edit">
						<i class="fa fa-eye" aria-hidden="true"></i>
						 <spring:message text="??atto.label.menuVisualizza??" code="atto.label.menuVisualizza" />
					</a>
				</li> <!-- IN BOZZA -->
			
			</ul>
</div>

<%
}catch(Throwable e){
	e.printStackTrace();
}
 %>
