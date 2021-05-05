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
</style>

<header id="header" class="media" >

	<div class="pull-left h-logo">
		<a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath() %>')}" class="hidden-xs"> Legal Archives <small>your
				daily desktop</small>
		</a>

		<div class="menu-collapse" data-ma-action="sidebar-open"
			data-ma-target="main-menu">
			<div class="mc-wrap">
				<div class="mcw-line top palette-White bg"></div>
				<div class="mcw-line center palette-White bg"></div>
				<div class="mcw-line bottom palette-White bg"></div>
			</div>
		</div>
	</div>
	
	<ul class="pull-right h-menu">
		<li class="hm-search-trigger"><a href=""
			data-ma-action="search-open"> <i class="hm-icon zmdi zmdi-search"></i>
		</a></li>
  
 			
		<li class="dropdown hidden-xs"><a data-toggle="dropdown" href=""><i
				class="hm-icon fa fa-cogs"></i></a>
			<ul class="dropdown-menu dm-icon pull-right">
				<li class="hidden-xs"><a href="javascript:cambialingua('it_IT')"><i
						class="zmdi " style="margin-top:4px;"><img src="<%=request.getContextPath()%>/portal/image/it.png"></i> ITALIANO</a></li>
				<li class="hidden-xs"><a href="javascript:cambialingua('en_US')"><i
						class="zmdi " style="margin-top:4px;"><img src="<%=request.getContextPath()%>/portal/image/en.png"></i> INGLESE</a></li> 
				 <li class="hidden-xs"><a data-action="fullscreen" href=""><i
						class="zmdi zmdi-fullscreen"></i> modalità schermo intero</a></li>
					<!--<li><a data-action="clear-localstorage" href=""><i
						class="zmdi zmdi-delete"></i> Cancella Cronologia del Browser</a></li>
				<li><a href=""><i class="zmdi zmdi-settings"></i>
						Personalizza il tema</a>
					<div class="header-colors">
						<div class="hc-item palette-Teal bg selected"
							data-ma-header-value="teal"></div>
						<div class="hc-item palette-Blue bg" data-ma-header-value="blue"></div>
						<div class="hc-item palette-Cyan bg"
							data-ma-header-value="cyan-600"></div>
						<div class="hc-item palette-Green bg" data-ma-header-value="green"></div>
						<div class="hc-item palette-LightGreen-SNAM bg"
							data-ma-header-value="lightgreen"></div>
						<div class="hc-item palette-Blue-Grey bg"
							data-ma-header-value="bluegrey"></div>
						<div class="hc-item palette-Orange bg"
							data-ma-header-value="orange"></div>
						<div class="hc-item palette-Purple-400 bg"
							data-ma-header-value="purple-400"></div>
						<div class="hc-item palette-Red-400 bg"
							data-ma-header-value="red-400"></div>
						<div class="hc-item palette-Pink-400 bg"
							data-ma-header-value="pink-400"></div>
						<div class="hc-item palette-Brown bg" data-ma-header-value="brown"></div>
						<div class="hc-item palette-Grey-600 bg"
							data-ma-header-value="grey-600"></div>
					</div></li>-->
			</ul>
		</li>
		<li class="hm-alerts" data-user-alert="sua-messages"
			data-ma-action="sidebar-open" data-ma-target="user-alerts">
			<a	href="">
				<span id="badgeNotificheCampanaMd" class="badge badge-notifiche"></span>	
				<i class="hm-icon zmdi zmdi-notifications"></i>
			</a>
		</li>
		<li class="hm-alerts" data-user-alert="sua-mail"
			data-ma-action="sidebar-open" data-ma-target="user-alerts">
			<a	href="">
				<span id="badgeNotifichePecMailMd" class="badge badge-notifiche-pecMailMd"></span>	
				<i class="hm-icon zmdi zmdi-email"></i>
			</a>
		</li>
	</ul>
 

</header>

<div id="box-secutity" style="display:none;">
<form:form name="legalSecurityForm" id="legalSecurityForm" method="get" action="index.action" cssStyle="display:none">
	<engsecurity:token regenerate="false"/>
</form:form>
</div><!-- / LegalSecurityToken -->
