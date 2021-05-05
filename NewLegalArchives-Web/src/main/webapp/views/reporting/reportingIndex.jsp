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
</div>
  
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
								<h1><spring:message text="??reporting.header.title??" code="reporting.header.title" /><br/> <small></small></h1>
								</div>
                                <div class="row">
							
 					<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
    
						<!-- Tabs -->
						<div class="card">
				
							<div class="card-body">
								<ul class="tab-nav tn-justified tn-icon" role="tablist">
								 <c:if test="${ isAll eq 'si' }">
									<li role="presentation"  style="position:relative" class="" >
									<a class="col-sx-<c:if test="${ isSegreteria eq 'no' }">3</c:if><c:if test="${ isSegreteria eq 'si' }">12</c:if>"
										href="#tab-1" aria-controls="tab-1" role="tab"
										data-toggle="tab"> 
											<small><spring:message text="??reporting.header.atto??" code="reporting.header.atto" /></small>
									</a> 
									
									</li>
									 
									<c:if test="${ isSegreteria eq 'no' }">
									<li role="presentation" style="position:relative" class="" >
									<a class="col-xs-3" href="#tab-2"
										aria-controls="tab-2" role="tab" data-toggle="tab"> 
											<small><spring:message text="??reporting.header.fascicolo??" code="reporting.header.fascicolo" /></small>
									</a>
									
									
									</li>
									
								    <li role="presentation"  style="position:relative" class="" >
									<a class="col-sx-3"
										href="#tab-3" aria-controls="tab-3" role="tab"
										data-toggle="tab"> 
											<small><spring:message text="??reporting.header.incarico??" code="reporting.header.incarico" /></small>
									</a> 
									
									</li>
									<li role="presentation" style="position:relative" class="">
									<a class="col-xs-3" href="#tab-4"
										aria-controls="tab-4" role="tab" data-toggle="tab"> 
											<small><spring:message text="??reporting.header.proforma??" code="reporting.header.proforma" /></small>
									</a>
									
									
									</li>
									</c:if>
								</c:if>
								</ul>
								
					<div class="tab-content p-20">
					 <c:if test="${ isAll eq 'si' }">	
					
					<div role="tabpanel" class="tab-pane animated fadeIn "
										id="tab-1">
											
								   <!--tab1 ATTO-->
								   
					 <jsp:include page="./ricerca-atto.jsp"></jsp:include>
						
									<!--/tab1 ATTO-->
						</div>

						<div role="tabpanel" class="tab-pane animated fadeIn " id="tab-2">
										<!-- tab2 FASCICOLO-->
										
						<jsp:include page="./ricerca-fascicoli.jsp"></jsp:include>

										<!--/tab2 FASCICOLO-->
							</div>

									<div role="tabpanel" class="tab-pane animated fadeIn "
										id="tab-3">
											
								   <!--tab3 INCARICHI-->
										
						<jsp:include page="./ricerca-incarichi.jsp"></jsp:include>

									<!--/tab3 INCARICHI-->
									</div>

						        <div role="tabpanel" class="tab-pane animated fadeIn "
										id="tab-4">
										<!-- tab4 PROFORMA-->

							
				  		<jsp:include page="./ricerca-proforma.jsp"></jsp:include>
								
 
										<!--/tab4 PROFORMA-->
									</div>
									
									
							 </c:if>
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
	 


<form id="export-form" action="./index.action" method="post">
 <engsecurity:token regenerate="false"/>
 <input type="hidden" name="export-proform" id="export-proform" value="">
</form>


<script>

function proformLetta(id,idsoc){
	waitingDialog.show('Loading...');
	$.post( "./letta.action", { idProf: id})
	  .done(function( data ) {
		if(data.split(" ").join("")=="OK"){
		 waitingDialog.hide();
		
		 //$(".collapse").collapse('hide');
		$('#notprocess-'+idsoc).bootgrid('reload');	 
				}
		else if(data.split(" ").join("")=="KO"){
		 waitingDialog.hide();
			
			alert("Errore operazione non eseguita!")
				}		
		  
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
		    
		});
	
}
	
 	function pulisciCampi(a){ 
	var form=a.parentNode.parentNode
	$(form).find('input').val("");
	$(form).find('select').prop("selectedIndex",0);
	
	$("#fascicoloSettoreGiuridico").attr("disabled","disabled");
	$("#fascicoloSettoreGiuridico").prop("selectedIndex",0);
	$("#tipoContenzioso").attr("disabled","disabled");
	$("#tipoContenzioso").prop("selectedIndex",0);
	
	$(".box-dinamik",form).remove();
	
	}	 
 
</script>

<script type="text/javascript">
function checkSettoreGiudizio(select){
	if($(select).val()!="tutti"){
		$("#fascicoloSettoreGiuridico").removeAttr("disabled");
		$("#tipoContenzioso").removeAttr("disabled");
	}else{
		$("#fascicoloSettoreGiuridico").attr("disabled","disabled");
		$("#fascicoloSettoreGiuridico").prop("selectedIndex",0);
		$("#tipoContenzioso").attr("disabled","disabled");
		$("#tipoContenzioso").prop("selectedIndex",0);
	}
}
</script>

</body>
</html>
