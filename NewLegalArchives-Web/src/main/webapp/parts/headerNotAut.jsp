<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring" %>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld" %>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>
<!-- engsecurity VA ?? -->


<style>
.formRicerca {
 	margin-top: 5px;
}
body{ background:#edecec;}
</style>

<header id="header" class="media" >

	<div class="pull-left h-logo">
		<img alt="" src="<%=request.getContextPath()%>/portal/image/logo.png" class="logoImage">	
			<small></small>

		<div class="menu-collapse" data-ma-action="sidebar-open"
			data-ma-target="main-menu">
			<div class="mc-wrap">
				<div class="mcw-line top palette-White bg"></div>
				<div class="mcw-line center palette-White bg"></div>
				<div class="mcw-line bottom palette-White bg"></div>
			</div>
		</div>
	</div>
 

</header>


<div id="box-secutity" style="display:none;">
<form:form name="legalSecurityForm" id="legalSecurityForm" method="get" action="index.action" cssStyle="display:none">
	<engsecurity:token regenerate="false"/>
</form:form>
</div><!-- / LegalSecurityToken -->