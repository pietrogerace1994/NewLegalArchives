<%@page import="eng.la.util.costants.Costanti"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@page import="eng.la.persistence.CostantiDAO"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<header id="header" class="media" >
 

	<div class="pull-left h-logo">
	 <!-- engsecurity VA -->
	 
	   <a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>')}" class="hidden-xs">
			<img alt="" src="<%=request.getContextPath()%>/portal/image/logo.png" class="logoImage">	
			<span> </span>
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

						<legarc:isAuthorized  nomeFunzionalita="<%=CostantiDAO.NASCONDI_A_LEG_ARC_GESTORE_ANAGRAFICA_PROCURE %>">
	<ul class="pull-right h-menu">
		<li class="hm-search-trigger"><a href=""
			data-ma-action="search-open"> <i class="hm-icon zmdi zmdi-search"></i>
		</a></li>



		<li class="dropdown hidden-xs hidden-sm h-apps" id="ricercaDropDown" >

			<button id="btnCercaIn" class="btn btn-success btn-lg btn-icon-text" type="button"
				><spring:message text="??menu.label.cerca??"
										code="menu.label.cerca" /> In</button>

			<ul class="dropdown-menu pull-right" >

				<li>
					<a href="PleaseEnableJavascript.html" onclick="submitRicerca('FASCICOLI'); return false;">
						<i class="palette-Red-400 bg fa fa-folder-open-o"></i>
						<small><spring:message text="??menu.label.fascicoli??"
										code="menu.label.fascicoli" /></small>
					</a>
				</li>
				<li>
					<a href="PleaseEnableJavascript.html" onclick="submitRicerca('ATTI'); return false;">
						<i class="palette-Green-SNAM bg fa fa-gavel"></i>
						<small><spring:message text="??menu.label.atti??"
										code="menu.label.atti" /></small>
					</a>
				</li>
				<li>
					<a href="PleaseEnableJavascript.html" onclick="submitRicerca('INCARICHI'); return false;">
						<i class="palette-Light-Blue bg fa fa-user"></i>
						<small><spring:message text="??menu.label.incarichi??"
										code="menu.label.incarichi" /></small>
					</a>
				</li>
				<li>
					<a href="PleaseEnableJavascript.html" onclick="submitRicerca('COSTI'); return false;">
						<i class="palette-Orange-400 bg fa fa-line-chart"></i>
						<small><spring:message text="??menu.label.costi??"
										code="menu.label.costi" /></small>
					</a>
				</li>
				<li>
					<a href="PleaseEnableJavascript.html" onclick="submitRicerca('TUTTO'); return false;">
						<i class="palette-Blue-Grey bg fa fa-modx"></i>
						<small><spring:message text="??menu.label.tutto??"
										code="menu.label.tutto" /></small>
					</a>
				</li>

			</ul>
		</li>



		<li class="dropdown hidden-xs"><a data-toggle="dropdown" href=""><i
				class="hm-icon fa fa-cogs"></i></a>
			<ul class="dropdown-menu dm-icon pull-right">
				<li class="hidden-xs"><a
					href="javascript:cambialingua('it_IT')"><i class="zmdi "
						style="margin-top: 4px;"><img
							src="<%=request.getContextPath()%>/portal/image/it.png"></i>
						ITALIANO</a></li>
				<li class="hidden-xs"><a
					href="javascript:cambialingua('en_US')"><i class="zmdi "
						style="margin-top: 4px;"><img
							src="<%=request.getContextPath()%>/portal/image/en.png"></i>
						INGLESE</a></li>
				<li class="hidden-xs"><a data-action="fullscreen" href=""><i
						class="zmdi zmdi-fullscreen"></i> modalità schermo intero</a></li>
				<!-- Aggiunta bottone di Logout MASSIMO CARUSO -->
				<!-- <li class="hidden-xs"><a href="<%=request.getContextPath()%>/LALogout"><i class="zmdi "
						style="margin-top: 4px;"><img
							src="<%=request.getContextPath()%>/portal/image/logout.png" style="width:16px;height:16px;"></i>Logout</a></li>-->
				<!--<li><a data-action="clear-localstorage" href=""><i
						class="zmdi zmdi-delete"></i> Cancella Cronologia del Browser</a></li>
				<!--<li><a href=""><i class="zmdi zmdi-settings"></i>
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
					</div></li> -->
			</ul></li>
		<li class="hm-alerts" data-user-alert="sua-messages"
			data-ma-action="sidebar-open" data-ma-target="user-alerts"><a
			href=""> <span id="badgeNotificheCampanaMd"
				class="badge badge-notifiche"></span> <i
				class="hm-icon zmdi zmdi-notifications"></i>
		</a></li>
		<li class="hm-alerts" data-user-alert="sua-mail"
			data-ma-action="sidebar-open" data-ma-target="user-alerts"><a
			href=""> <span id="badgeNotifichePecMailMd"
				class="badge badge-notifiche-pecMailMd"></span> <i
				class="hm-icon zmdi zmdi-email"></i>
		</a></li>
	</ul>

	<div class="media-body h-search">
		<c:set var="urlCompleto"
			value="${pageContext.request.contextPath}/ricerca.action"></c:set>
		<form:form class="p-relative" style="margin-top: 5px"
			id="headerRicercaForm" name="headerRicercaForm" method="post"
			modelAttribute="ricercaView" action="${urlCompleto}">
			<input type="text" class="hs-input" id="headerRicercaTxtBox"
				style="padding: 10px 20px 10px 30px !important;"
				placeholder=<spring:message text="??menu.label.cerca??"
										code="menu.label.cerca" /> name="testo">
			 <engsecurity:token regenerate="false"/>

			<input type="hidden" name="oggetto" id="ricercaOggetto" />

		</form:form>
	</div>
	
					</legarc:isAuthorized>




</header>

<div class="modal fade"  role="dialog" id="modalRicerca">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <b class="modal-title" style="font-size:90%;"><spring:message text="??ricerca.label.ricerca??" code="ricerca.label.ricerca" /></b>
      </div>
      <div class="modal-body">
        
        <div class="alert alert-danger" role="alert">
  			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  			<span class="sr-only">Error:</span>
  				<span style="font-size: 120%;">
  					<spring:message text="??errore.campo.obbligatorio.parolaChiave??" code="errore.campo.obbligatorio.parolaChiave" />
				</span>
		</div>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn  btn-primary" data-dismiss="modal"><spring:message text="??fascicolo.label.chiudi??" code="fascicolo.label.chiudi" /></button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div id="box-secutity" style="display:none;">
<form:form name="legalSecurityForm" id="legalSecurityForm" method="get" action="index.action" cssStyle="display:none">
	<engsecurity:token regenerate="false"/>
</form:form>
</div><!-- / LegalSecurityToken -->
