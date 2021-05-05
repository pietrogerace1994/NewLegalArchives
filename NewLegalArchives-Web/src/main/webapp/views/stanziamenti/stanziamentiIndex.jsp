<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<!DOCTYPE html lang="${language}">
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Legal Archives</title>

<jsp:include page="/parts/script-init.jsp"></jsp:include>

</head>

<body data-ma-header="teal">
 <div id="box-secutity" style="display:none;">
<form:form name="legalSecurityForm" id="legalSecurityForm" method="get" action="index.action" cssStyle="display:none">
	<engsecurity:token regenerate="false"/>
</form:form>
</div><!-- / LegalSecurityToken -->

	<header id="header" class="media">
            <div class="pull-left h-logo">
           	<a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>')}" class="hidden-xs">
			<img alt="" src="<%=request.getContextPath()%>/portal/image/logo.png" class="logoImage">
			<span> </span>
			</a>
           </div>    
        </header><!-- #EndLibraryItem -->
		<!-- SECION MAIN -->

<style type="text/css">

	.text-left {
	text-align: left;
	font-size: 14px;
	}
	
	button.command-edit{ padding-top: 6%!important;
	padding-bottom: 6%!important;
	padding-left:15px;
	padding-right:15px;
	}
	
	.panel-title > a {
		display: block;
		font-weight: bold;
		font-size: 16px !important;
	}
	.search{
	display: none!important;
	}
	ul.tab-nav li.active {
    background: #006643;
	}
	ul.tab-nav li small, .small {
    font-size: 160%;
	}
	ul.tab-nav li.active small, .small {
    font-size: 160%;
    font-weight: bold;
	color:#ffffff;
	}
	ul.tab-nav li :hover {
    background: #006643;
    color: #ffffff;
    font-weight: bold;
	}
	.tab-nav:not([data-tab-color]) > li > a:after {
    background: #ffc107;
	}
	.tab-nav li > a:after {
    content: "";
    height: 4px;
    }
	.btn-primary {
    color: #ffffff;
    background-color: #006643;
	}
	
	.btn-primary:hover {
	color: #000000!important;
	background-color: #ffc107!important;
	}
	
	h1, .h1{
	font-weight: bold;
    color: #0d524c;
	}
	
	label{ text-transform: uppercase; font-size:14px; font-weight: normal!important; color:#0d524c; }
	
	.form-control{ font-size:16px;color:#222222; }
	
	
</style>		



	<!-- SECION MAIN -->
<section id="main">
	<jsp:include page="/parts/aside.jsp">
</jsp:include>
<!-- SECTION CONTENT -->
<section id="content">    
                <div class="container">
                	<div class="row">
                		<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
                        	<div class="card">
                        		<div class="card-header">
								<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12" style="border-bottom:3px solid #006643">
								<h1><spring:message text="??stanziamenti.header.title??" code="stanziamenti.header.title" /><br/> <small></small></h1>
								</div>
                                <div class="row">
							
 					<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
    
						<!-- Tabs -->
						<div class="card">
				
							<div class="card-body">
								<c:if test="${ not empty errore }">
										<div class="alert alert-danger">
											<spring:message code="${errore}"
												text="??${errore}??"></spring:message>
										</div>
									</c:if> 
								<ul class="tab-nav tn-justified tn-icon" role="tablist">
								
									<li role="presentation"  style="position:relative" class="active" >
									<a class="col-md-12 col-sx-12"
										href="javascript:{legalSecurity.attachForm('./index.action')}"> 
											<small><spring:message text="??stanziamenti.tab.title??" code="stanziamenti.tab.title" /></small>
									</a> 
									
									</li>
									 
							
								</ul>
								
					<div class="tab-content p-20">
					 
					
					<div role="tabpanel" class="tab-pane animated fadeIn in active"
										id="tab-1">
											
								   <!--tab1 -->
								   
					 <!-- STANZIAMENTI-->
		
						<c:if test="${ empty errore }">				
						<div class="row">
						<form action="<%=request.getContextPath()%>/stanziamenti/export-report.action" method="post" onsubmit="return flushForm()">
						<engsecurity:token regenerate="false"/>
						<fieldset>

						
							<div class="form-group" style="padding:45px;padding-bottom:100px;">
							<label class="col-md-12 control-label" for="annoFinanziario"><spring:message text="??stanziamenti.label.anno??" code="stanziamenti.label.anno" /> </label>
							<div class="col-md-12">
							 
							 <input id="annoFinanziario" name="annoFinanziario"
									type="text" placeholder="Year (0000)" class="typeahead form-control input-md date-YYYY-picker"> 
									<span class="help-block"></span>
							
							</div>
						 
							</div>
							
							<c:if test="${not empty listRespons }">	
							<div class="col-md-12" style="padding:20px;text-align:center;">
							<label id="listRespons" class="col-md-12 control-label" style="color:red;"><spring:message text="??${listRespons}??" code="${listRespons}" /> </label>
							</div>
							</c:if>
							 
							
							</fieldset> 
							<div class="modal-footer" style="padding:15px">
								<button style="float:left" type="button" class="btn btn-primary waves-effect" onclick="pulisciCampi(this)"><spring:message text="??stanziamenti.btn.clear??" code="stanziamenti.btn.clear" /></button>
								<button type="submit" class="btn btn-primary" ><spring:message text="??stanziamenti.btn.applicaFiltri??" code="stanziamenti.btn.applicaFiltri" /> </button>
								 
							</div>
								</form>
					 
							</div>
					 	</c:if> 
				

<!-- FINE  STANZIAMENTI-->	
						
									<!--/tab1 -->
						</div>
 	
							 
						</div>
								 
							</div>						  
						 
							 </div>
                                <!-- Modal Default -->
 
                                        
                                        </div>
      
                                	</div>                            
                            </div><!-- CARD -->
            			</div><!--/ fine col-2 -->
                    </div><!-- / ROW-->
                </div><!-- CONTAINER -->

	</section>
 </section>
	
<footer>
	<jsp:include page="/parts/footer.jsp"></jsp:include>
</footer>
<jsp:include page="/parts/script-end.jsp"></jsp:include>
	 


<script>
	
function flushForm(){
	$("#listRespons").html("");
	return true;
	}
	
 	function pulisciCampi(a){ 
	var form=a.parentNode.parentNode
	$(form).find('input').val("");
	$(form).find('select').prop("selectedIndex",0);
	
	$("#listRespons").html("");
	
	}	 
 
</script>


</body>
</html>
