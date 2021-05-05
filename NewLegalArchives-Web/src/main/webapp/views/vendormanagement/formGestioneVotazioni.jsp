<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ page import="eng.la.util.costants.Costanti"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!DOCTYPE html lang="${language}">
<!--[if IE 9 ]><html class="ie9"><![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Legal Archives</title>

<jsp:include page="/parts/script-init.jsp">
</jsp:include>

</head>
<body data-ma-header="teal">
	<jsp:include page="/parts/header.jsp">
	</jsp:include>
	<!-- SECION MAIN -->
	<section id="main">

		<jsp:include page="/parts/aside.jsp">
		</jsp:include>
		<!-- SECTION CONTENT -->
		<section id="content">
			<div class="container">
				<div class="row">
					
					<div id="col-1" class="col-lg-8 col-md-8 col-sm-12 col-sx-12">
					
						<div class="card">
							<div class="card-header cw-header palette-Green-SNAM bg">
		 						<div class="cwh-day">Grafico Votazioni</div>
							</div>
							<div class="card-body card-padding">
								<br>
    							<div>
        							<button id="generaReportVendor" type="button" class="btn btn-primary waves-effect" 
        							        style="float:right !important;" onclick="generaReportVendor()">
											<spring:message text="??vendormanagement.generaReportVotazioni??" code="vendormanagement.generaReportVotazioni" />
									</button>
    							</div>
    							<br>
								<div id="pie-chart-vote" class="flot-chart-pie"></div>
								<div id="pie-chart-legend" class="flc-pie-vote hidden-xs"></div>
								<div id="flot-memo-vote" style="text-align:center;height:30px;width:70%;height:20px; margin-left:15%;"></div>
							</div>
							
							<div class="action-header palette-LightGreen-SNAM bg clearfix">
								<div class="ah-label hidden-xs palette-White text">Scegli il semestre di riferimento</div>
								<div class="ah-search">
									<input type="text" placeholder="Cerca nei documenti salvati"
										class="ahs-input"> <i
										class="ah-search-close zmdi zmdi-long-arrow-left"
										data-ma-action="ah-search-close"></i>
								</div>
								<ul class="ah-actions actions a-alt">

									<li class="dropdown"><a href="" data-toggle="dropdown"
										aria-expanded="true" id="dropdownNumRighe" style="width:100px !important;">
										<strong style="color: white; font-size: 140%; line-height: 20px;"
											id="semestreBadge">-</strong></a>
										<ul class="dropdown-menu dropdown-menu-right" id="semestriDisponibili">
											
										</ul>
									</li>
								</ul>
							</div>
					
						</div>
					</div>
					
					<div id="col-2 " class="col-lg-4 col-md-4 col-sm-12 col-sx-12 ">
						
						<div class="card">
							<div class="card-header cw-header palette-Green-SNAM bg">
								<div class="cwh-day">Invia Mail</div>
							</div>
							<div class="card-body" style="text-align:center !important;">
								<br>
								<ul class="ah-actions actions">
									<li style="width:60% !important; height: 10% !important;">
										<a href="javascript:void(0); hideMailError()" data-toggle="modal" data-target="#panelInviaSollecito" class="btn btn-lg btn-success"
										style="width:100% !important; height:50% !important; border-radius:5% !important">
		  									<i class="fa fa-envelope-o fa-2x pull-left"></i>
		  									<spring:message text="??vendormanagement.sollecita??" code="vendormanagement.sollecita"/>
		  									<spring:message text="??vendormanagement.conunamail??" code="vendormanagement.conunamail" />
		  								</a>
									</li>
								</ul>
							</div>
					
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>
	
	<jsp:include page="/parts/script-end.jsp"></jsp:include>
	
	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>

	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/vendormanagement/gestioneVotazioni.js"></script>
	
</body>
</html>
