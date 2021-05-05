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

<jsp:include page="/parts/script-init.jsp">
</jsp:include>

</head>

<body data-ma-header="teal">
<fmt:setLocale value="it_IT"/>
<jsp:include page="/parts/script-end-amministrativo.jsp"></jsp:include>
	<header id="header" class="media">
            <div class="pull-left h-logo">
                <a href="index.html" class="hidden-xs">
                    Legal Archives   
                    <small>your daily desktop</small>
                </a>
                
        </header><!-- #EndLibraryItem -->
		<!-- SECION MAIN -->

<style type="text/css">
						
	.bootgrid-table td {
	overflow: hidden;
	-ms-text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
	text-overflow: ellipsis;
	white-space: normal!important;
	}
	.text-left {
	text-align: left;
	font-size: 14px;
	}
	.bootgrid-table th > .column-header-anchor > .text {
	display: block;
	margin: 0 6px 0 0;
	overflow: initial;
	-ms-text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
	text-overflow: inherit;
	white-space: normal!important;
	font-size: 12px!important;
	font-weight: bold!important;
	text-transform: capitalize;
	}
	button.command-edit{ padding-top: 6%!important;
	padding-bottom: 6%!important;
	padding-left:15px;
	padding-right:15px;
	  
	}
	input[type="checkbox"], input[type="radio"] {
	box-sizing: border-box;
	padding: 0;
	height: 15px!important;
	width: 35px;
	float: left;
	margin-right: 15px !important;
		}
	.bootgrid-header .actions {
			box-shadow: none;
			position: absolute;
			right: 60px;
			top: -15px;
		}	
	.bootgrid-header .actions .btn-group .dropdown-menu {
	padding: 10px 0px!important;}
	
	.bootgrid-header .actions .btn-group .dropdown-menu .dropdown-item {
	padding: 3px 20px!important;}
	
	.btn-default:hover:hover, 
	.btn-default:focus:hover 
	 
	 {
	color: #333333;
	background-color: #ff9800;
	border-color: transparent;
	}
	.panel-title > a {
		display: block;
		font-weight: bold;
		font-size: 16px !important;
	}
	.search{
	display: none!important;
	}
	
	.bootgrid-table th > .column-header-anchor > .text {
		display: block;
		margin: 0 20px 0 0;
	}
	
	
	.bootgrid-table th > .column-header-anchor > .icon {
		color: #057ae0;
	}

</style>		
	
	<!-- LegalSecurityToken -->
	<div id="box-secutity" style="display:none;">
    	<form:form name="legalSecurityForm" id="legalSecurityForm" method="get" action="index.action" cssStyle="display:none">
            <engsecurity:token regenerate="false"/>
        </form:form>
    </div><!-- / LegalSecurityToken -->
	

	<!-- SECION MAIN -->
	<section id="main" style="padding: 0 20px 30px 20px !important;">
	
		<!-- SECTION CONTENT -->
		           
                <div class="container">
                	<div class="row">
                		<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
                        	<div class="card">
                        		<div class="card-header">
								<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12" style="border-bottom:2px solid #26af2c">
								<h1><spring:message text="??parcella.label.headertitle??" code="parcella.label.headertitle" /><br/> <small></small></h1>
								</div>
                                <div class="row">
							
 					<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
    
						<!-- Tabs -->
						<div class="card">
				
							<div class="card-body">
								<ul class="tab-nav tn-justified tn-icon" role="tablist">
									<li role="presentation"  style="position:relative" class="<c:out value="${classNonProcessato}"></c:out>" onclick="activeProcessate(0)"><a class="col-sx-6"
										href="#tab-1" aria-controls="tab-1" role="tab"
										data-toggle="tab"> <!--<i class="fa fa-star icon-tab" aria-hidden="true"></i>-->
											<small><spring:message text="??parcella.nonprocessate.label??" code="parcella.nonprocessate.label" /></small>
									</a> 
									<button type="button" id="btn-nonprocessate" style="position:absolute;right:25px;top: 14px;" onclick="exportXsl('nonprocessate')" class="btn btn-xs btn-default"><span class="fa fa-arrow-circle-down"></span>&nbsp;<spring:message text="??parcella.nonprocessate.export??" code="parcella.nonprocessate.export" /></button>
									</li>
									<li role="presentation" style="position:relative" class="<c:out value="${classProcessato}"></c:out>" onclick="activeProcessate(1)"><a class="col-xs-6" href="#tab-2"
										aria-controls="tab-2" role="tab" data-toggle="tab"> <!--<i class="fa fa-search icon-tab" aria-hidden="true"></i>-->
											<small><spring:message text="??parcella.processate.label??" code="parcella.processate.label" /></small>
									</a>
									
									<button type="button" id="btn-processate"  style="position:absolute;right:25px;top: 14px;" disabled="disabled"  onclick="exportXsl('processate')" class="btn btn-xs btn-default"><span class="fa fa-arrow-circle-down"></span>&nbsp;<spring:message text="??parcella.processate.export??" code="parcella.processate.export" /></button>
									</li>
								
								</ul>
								
					<div class="tab-content p-20">
					 	
						<div role="tabpanel" class="tab-pane animated fadeIn  <c:out value="${classNonProcessato}"></c:out>"
										id="tab-1">
											
										
										<!--tab1 -->
										
					 <c:if test="${ not empty socNonProcessate }">
					 	 
					<c:forEach items="${socNonProcessate}" var="rows" varStatus="status">
				
                    <div class="panel-group" role="tablist" aria-multiselectable="true">
                                <div class="panel panel-collapse">
                                    <div class="panel-heading" role="tab" id="headingOne-${status.count}">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne-${status.count}" aria-expanded="true" aria-controls="collapseOne-${status.count}">
                                              Societ&agrave;&nbsp;&nbsp;<c:out value="${rows.ragioneSociale}"></c:out>
                                            </a>
                                        </h4>
                                    </div>
                          <div id="collapseOne-${status.count}" class="collapse" role="tabpanel" aria-labelledby="headingOne-${status.count}">
                          <div class="panel-body">
				
												                          
						<!--TABLE Società  Centro di Costo, Voce di Conto, Paese, Controparte, Legale Esterno, Totale Autorizzato, Valuta, Data inserimento, Data Autorizzazione-->	
									 	
				
					
					<table id='notprocess-<c:out value="${rows.id}"></c:out>'  class="table table-responsive" cellspacing="0" width="100%">
					<thead>
						 
						<tr>
							<th data-column-id="id" data-visible="false" data-visible-in-selection="false">ID</th>
							<th data-column-id="titolo" data-sortable="true" data-formatter="link" data-width="15%"><spring:message text="??parcella.th.titolo??" code="parcella.th.titolo" /></th>
							<th data-column-id="commands" data-sortable="true" data-formatter="commands" data-width="15%" data-visible-in-selection="false">&nbsp;</th>
							<th data-column-id="centrodicosto" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.cdc??" code="parcella.th.cdc" /></th>
							<th data-column-id="voceconto" data-sortable="true"  data-width="10%"><spring:message text="??parcella.th.vdc??" code="parcella.th.vdc" /></th>
							<th data-column-id="paese" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.paese??" code="parcella.th.paese" /></th>
							<th data-column-id="controparte" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.controparte??" code="parcella.th.controparte" /></th>
							<th data-column-id="legaleesterno" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.legaleesterno??" code="parcella.th.legaleesterno" /></th>
							<th data-column-id="totale" data-formatter="numberFormat" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.totaleautorizzato??" code="parcella.th.totaleautorizzato" /></th>
							<th data-column-id="valuta" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.valuta??" code="parcella.th.valuta" /></th>
							<th data-column-id="datainserimento" data-sortable="true" data-width="10%"> <spring:message text="??parcella.th.datainserimento??" code="parcella.th.datainserimento" /></th>
							<th data-column-id="dataautorizzazione" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.dataautorizzazione??" code="parcella.th.dataautorizzazione" /></th>
							 
						</tr>	

					</thead>
					</table>
					
						<script>
						$(document).ready(function() {
						$('#notprocess-<c:out value="${rows.id}"></c:out>').bootgrid({
							ajax: true,
							ajaxSettings: {
							method: "GET",
							cache: false
							},
							url: './cercaparcella.action?idSoc=<c:out value="${rows.id}"></c:out>&isProcessato=false',
							formatters: {
										"commands": function(column, row)
											{			
												return "<button type=\"button\" onclick=\"proformLetta(" + row.id + ",<c:out value="${rows.id}"></c:out>)\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\"" + row.id + "\"><span class=\"fa fa-eye\"></span>&nbsp;&nbsp;<spring:message text="??parcella.btn.letto??" code="parcella.btn.letto" />&nbsp;&nbsp;</button> "; 
											  
											},
											"link": function(column, row)
											{			
												return "<a onclick=\"visualizzaDettaglioBtnLetta(" + row.id + ",<c:out value="${rows.id}"></c:out>)\" href=\"javascript:void(0)\" >"+row.titolo+"</a> "; 
											},
											"numberFormat":function(column, row) {
											if(!isNaN(row.totale)){
											var multiplier = Math.pow(10, 2);
											return (Math.round(row.totale * multiplier) / multiplier).toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
											}else{ return row.totale;} 
											}
							}
							});
						});
					</script>
					
					
											
										
						<!--FINE TABLE Società -->	

									  </div>
                                    </div>
                                </div>
					
						  
						  </div>
                   
                   </c:forEach>
                     </c:if>    

					 
					 		<!--Navigatore-->	
								 	
									<div class="row">
					 				<div class="col-sm-6">
									<ul class="pager">
									<li><a href="javascript:void(0)" onclick="getPageNonProcessata(0)">&nbsp;&nbsp;Previous&nbsp;&nbsp;</a></li>
									<li>&nbsp;&nbsp;Showing&nbsp;&nbsp;to&nbsp;&nbsp;<a id="current-nonprocessate" href="javascript:void(0)"><c:out value="${paginaCorrenteNP}"></c:out></a>&nbsp;&nbsp;of&nbsp;&nbsp;<a id="total-nonprocessate" href="javascript:void(0)"><c:out value="${totaleNumeroPagineNP}"></c:out></a>&nbsp;&nbsp;</li>
									<li><a href="javascript:void(0)" onclick="getPageNonProcessata(1)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Next&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
									</ul>
									</div>
									</div>
									
									<!--Navigatore-->
					 
					 

									<!--/tab1 -->
									</div>

						<div role="tabpanel" class="tab-pane animated fadeIn <c:out value="${classProcessato}"></c:out>"
										id="tab-2">
										<!-- tab2 -->

								
										
					 <c:if test="${ not empty socProcessate }">
					 	 
					<c:forEach items="${socProcessate}" var="rows" varStatus="status">
				
                    <div class="panel-group" role="tablist" aria-multiselectable="true">
                                <div class="panel panel-collapse">
                                    <div class="panel-heading" role="tab" id="headingOne-${status.count}">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo-${status.count}" aria-expanded="true" aria-controls="collapseTwo-${status.count}">
                                              Societ&agrave;&nbsp;&nbsp;<c:out value="${rows.ragioneSociale}"></c:out>
                                            </a>
                                        </h4>
                                    </div>
                          <div id="collapseTwo-${status.count}" class="collapse" role="tabpanel" aria-labelledby="headingTwo-${status.count}">
                          <div class="panel-body">
				
												                          
						<!--TABLE Società  Centro di Costo, Voce di Conto, Paese, Controparte, Legale Esterno, Totale Autorizzato, Valuta, Data inserimento, Data Autorizzazione-->	
									 	
					 
					
					<table id='process-<c:out value="${rows.id}"></c:out>'  class="table table-responsive" cellspacing="0" width="100%">
					<thead>
						 
						<tr>
							<th data-column-id="id" data-visible="false" data-visible-in-selection="false">ID</th>
							<th data-column-id="titolo" data-sortable="true" data-formatter="link" data-width="15%"><spring:message text="??parcella.th.titolo??" code="parcella.th.titolo" /></th>
							
							<th data-column-id="centrodicosto" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.cdc??" code="parcella.th.cdc" /></th>
							<th data-column-id="voceconto" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.vdc??" code="parcella.th.vdc" /></th>
							<th data-column-id="paese" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.paese??" code="parcella.th.paese" /></th>
							<th data-column-id="controparte" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.controparte??" code="parcella.th.controparte" /></th>
							<th data-column-id="legaleesterno" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.legaleesterno??" code="parcella.th.legaleesterno" /></th>
							<th data-column-id="totale" data-formatter="numberFormat" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.totaleautorizzato??" code="parcella.th.totaleautorizzato" /></th>
							<th data-column-id="valuta" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.valuta??" code="parcella.th.valuta" /></th>
							<th data-column-id="datainserimento" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.datainserimento??" code="parcella.th.datainserimento" /></th>
							<th data-column-id="dataautorizzazione" data-sortable="true" data-width="10%"><spring:message text="??parcella.th.dataautorizzazione??" code="parcella.th.dataautorizzazione" /></th>
							 
						</tr>	

					</thead>
					</table>
					<script>
						$(document).ready(function() {
						$('#process-<c:out value="${rows.id}"></c:out>').bootgrid({
							ajax: true,
							ajaxSettings: {
							method: "GET",
							cache: false
							},
							url: './cercaparcella.action?idSoc=<c:out value="${rows.id}"></c:out>&isProcessato=true',
							formatters: {
											"link": function(column, row)
											{			
												return "<a onclick=\"visualizzaDettaglio(" + row.id + ")\" href=\"javascript:void(0)\" >"+row.titolo+"</a> "; 
											},
											"numberFormat":function(column, row) {
												if(!isNaN(row.totale)){
												var multiplier = Math.pow(10, 2);
												return (Math.round(row.totale * multiplier) / multiplier).toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
												}else{ return row.totale;} 
												}
							}
							});
						});
					</script>
					
					 						
										
						<!--FINE TABLE Società -->	

									  </div>
                                    </div>
                                </div>
					
						  
						  </div>
                   
                   </c:forEach>
                     </c:if>    

					 
					 	  <!--Navigatore-->	
								 	
								    <div class="row">
					 				<div class="col-sm-6">
									<ul class="pager">
									<li><a href="javascript:void(0)" onclick="getPageProcessata(0)">&nbsp;&nbsp;Previous&nbsp;&nbsp;</a></li>
									<li>&nbsp;&nbsp;Showing&nbsp;&nbsp;to&nbsp;&nbsp;<a id="current-processate" href="javascript:void(0)"><c:out value="${paginaCorrente}"></c:out></a>&nbsp;&nbsp;of&nbsp;&nbsp;<a id="total-processate" href="javascript:void(0)"><c:out value="${totaleNumeroPagine}"></c:out></a>&nbsp;&nbsp;</li>
									<li><a href="javascript:void(0)" onclick="getPageProcessata(1)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Next&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
									</ul>
									</div>
									</div>
									
							<!--Navigatore-->
					 
					 

								

										<!--/tab2 -->
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
 
	
		<footer id="footer">
          	<div class="container-fluid">
              	<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
              			Copyright &copy; 2015 Legal Archives
                          <ul class="f-menu">
                              <li></li>
                              <li></li>
                              <li></li>
                          </ul>
                  </div>
              </div>
       </footer>
	 


<form id="export-form" action="./index.action" method="post">
<engsecurity:token regenerate="false"/>
<input type="hidden" name="export-proform" id="export-proform" value="">
</form>


<!-- MODAL Dettaglio -->
<div class="modal fade" id="modalDettaglio" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content" style="height:570px"> 
			<div class="modal-header">
				<h4 class="modal-title">Dettaglio</h4>
			</div>
			<div class="modal-body" style="overflow-x: auto;height:495px">
			<iframe id="bodyDettaglio" src="" style="width:100%;height:700px;border:0px"></iframe>

			</div>
			 
		</div>
	</div>
</div>
<!-- FINE MODAL Dettaglio -->
	

<c:if test="${ not empty classProcessato && classProcessato eq 'active' }">
	
	<script>
	(function(){
	$("#btn-processate").removeAttr("disabled")
	$("#btn-nonprocessate").attr("disabled","disabled")
	})()
	</script>
			
</c:if>
<script>
var GLOBAL_LETTA=0;
$('.search').hide();
	function getPageProcessata(isNext){
	var curr_=Number($("#current-processate").text());
	var total_=Number($("#total-processate").text());
	if(isNext)
	curr_=curr_+1;
	else
	curr_=curr_-1;
	if(curr_<1)
	curr_=1;
	if(curr_<=total_ && 1<total_){
	location.href="<%=request.getContextPath()%>/parcella/index.action?pCurr="+curr_;
	}
	}

	function getPageNonProcessata(isNext){
	var curr_=Number($("#current-nonprocessate").text());
	var total_=Number($("#total-nonprocessate").text());

	if(isNext)
	curr_=curr_+1;
	else
	curr_=curr_-1;
	if(curr_<1)
	curr_=1;
	if(curr_<=total_ && 1<total_){
	location.href="<%=request.getContextPath()%>/parcella/index.action?npCurr="+curr_;
	}


	}

function proformLetta(id,idsoc){
	waitingDialog.show('Loading...');
	$.post( "./letta.action", { idProf: id})
	  .done(function( data ) {
		if(data.split(" ").join("")=="OK"){
		 waitingDialog.hide();
		 GLOBAL_LETTA=1;
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
	
function activeProcessate(a){
	if(a){
	$("#btn-processate").removeAttr("disabled")
	$("#btn-nonprocessate").attr("disabled","disabled")
	if(GLOBAL_LETTA){
	 var url="<%=request.getContextPath()%>/parcella/index.action?letta=1";
	 location.href=url;
	}
	
	}else{
	$("#btn-nonprocessate").removeAttr("disabled")
	$("#btn-processate").attr("disabled","disabled")
	}
	}

	function exportXsl(tipo){
	$("#export-proform").val(tipo);
	$("#export-form").submit();
	}					

function visualizzaDettaglio(id){
	var url="<%=request.getContextPath()%>/proforma/dettaglio.action?id="+id+"&stampa=Y";
	url=legalSecurity.verifyToken(url);
	waitingDialog.show('Loading...');
	var ifr=$("#bodyDettaglio").attr("src",url);
	ifr.load(function(){

	 var documents = (ifr[0].contentDocument)?ifr[0].contentDocument.body:ifr[0].contentWindow.document.body;
	  $("header#header",documents).remove();
	  $("footer#footer",documents).remove();
	  $("#box-stampa",documents).html("");
	  $("section#main",documents).css("padding","0px");
	  var a_="<a href=\"\" onclick=\"javascript:window.print()\" style=\"position:absolute;top:40px;right: 20px;z-index: 1000;color: #fff;font-weight: bold;\">stampa</a>";
		$("#box-stampa",documents).append(a_)
	  	 waitingDialog.hide();
		$("#modalDettaglio").modal("show");
})
	 

}	

function visualizzaDettaglioBtnLetta(id,idSoc){
	var url="<%=request.getContextPath()%>/proforma/dettaglio.action?id="+id+"&stampa=Y";
	url=legalSecurity.verifyToken(url);
	waitingDialog.show('Loading...');
	var ifr=$("#bodyDettaglio").attr("src",url);
	ifr.load(function(){

	 var documents = (ifr[0].contentDocument)?ifr[0].contentDocument.body:ifr[0].contentWindow.document.body;
	  $("header#header",documents).remove();
	  $("footer#footer",documents).remove();
	  $("#box-stampa",documents).html("");
	  $("section#main",documents).css("padding","0px");
		var a_="<a href=\"\" onclick=\"javascript:window.print()\" style=\"position:absolute;top:40px;right: 90px;z-index: 1000;color: #fff;font-weight: bold;\">stampa</a>";
	    var btnLetta_="<a href=\"\" onclick=\"javascript:top.proformLetta("+id+","+idSoc+")\" style=\"position:absolute;top:40px;right: 20px;z-index: 1000;color: #fff;font-weight: bold;\">Letta</a>";
		$("#box-stampa",documents).append(a_)
		$("#box-stampa",documents).append(btnLetta_)
	  	 waitingDialog.hide();
		$("#modalDettaglio").modal("show");
})
	 

}	 
 
</script>

<c:if test="${ not empty visualizzaProformaId && visualizzaProformaId!='' }">
<script>
var idprf='<c:out value="${visualizzaProformaId}"></c:out>';

(function(a){
	visualizzaDettaglio(a);	
})(idprf)

</script>

</c:if>

</body>
</html>
