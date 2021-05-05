<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
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
	font-size: 15px;
	}
	.bootgrid-table th > .column-header-anchor > .text {
	display: block;
	margin: 0 6px 0 0;
	overflow: initial;
	-ms-text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
	text-overflow: inherit;
	white-space: normal!important;
	font-size: 16px!important;
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
	background-color: #e2e2e2;
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
	
	.btn-group > .btn:first-child:not(:last-child):not(.dropdown-toggle) {
    border-bottom-right-radius: 0;
    border-top-right-radius: 0;
	border:none;
    height: 36.5px;
	}
	 


</style>
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
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						<div class="card">
							<div class="card-header">
								 
									<h1>
									<spring:message text="??gestione.utenti.page.title??" code="gestione.utenti.page.title" />	 
									</h1>
									<!--
								 	<p class="text-left" style="display:block">
										<spring:message text="??atto.label.ricercaCustomAttoStart??" code="atto.label.ricercaCustomAttoStart" /> 
										<a data-toggle="modal" href="#modalRicercaAtto" class="">
											<spring:message text="??atto.label.ricercaCustomAttoEnd??" code="atto.label.ricercaCustomAttoEnd" />
										</a>
									</p>
									-->
							</div>
							<div class="card-body">
							
							<!-- Area Table Result-->
							
							<div class="table-responsive">
 
							 
								
					<table id='gestioneUtentiGrid'  class="table table-responsive" cellspacing="0" width="100%" style="display:none;">
					<thead>
						 
						<tr>
							<th data-column-id="userid" data-visible="false" data-visible-in-selection="false">ID</th>
							<th data-column-id="nominativo" data-sortable="false" data-width="65%"><spring:message text="??gestione.utenti.table.column.nominativo??" code="gestione.utenti.table.column.nominativo" /></th>
							<th data-column-id="assente" data-sortable="false" data-formatter="link" data-width="20%" data-visible-in-selection="false"></th>
							<th data-column-id="azioni" data-sortable="false" data-formatter="commands" data-width="15%" data-visible-in-selection="false"></th>
							 
						</tr>	

					</thead>
					</table>

								
								
								
								
								
						
							</div>
							<!-- Table Result-->
							
				 
							 
							</div>
						</div><!--/ fine card -->
					</div>
				</div>
				
				
			</div>
			
			
 		
			
			<!--MODALFASCICOLO-->


 
			<!--/ fine col-1 -->
		</section>
<!-- jsp__include p_age="/views/atto/modal-ricerca.jsp"></jsp__include--> 
	</section>
 

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp"></jsp:include>

 
	
						<script>
						$(document).ready(function() {
						$('#gestioneUtentiGrid').bootgrid({
							ajax: true,
							ajaxSettings: {
							method: "GET",
							cache: false
							},
							url: './cercautenti.action',
							formatters: {
										"commands": function(column, row)
											{	
											var assenteBtn='<spring:message text="??gestione.utenti.btn.assente??" code="gestione.utenti.btn.assente" />';
												if(row.assente=="T"){
												assenteBtn='<spring:message text="??gestione.utenti.btn.presente??" code="gestione.utenti.btn.presente" />';
											return "<button type=\"button\" onclick=\"setUtenteAssente('" + row.userid + "','F')\" class=\"btn btn-xs btn-default command-edit\" style=\"color:#018408;font-weight:bold;\" data-row-id=\"" + row.azioni + "\">&nbsp;&nbsp;"+assenteBtn+"&nbsp;&nbsp;</button> "; 
											  	 
												 }else{
											return "<button type=\"button\" onclick=\"setUtenteAssente('" + row.userid + "','T')\" class=\"btn btn-xs btn-default command-edit\" style=\"color:#ff0000;font-weight:bold;\" data-row-id=\"" + row.azioni + "\">&nbsp;&nbsp;"+assenteBtn+"&nbsp;&nbsp;</button> "; 
											  	 
												 } 
											  
											},
											"link": function(column, row)
											{	var assenteText='<spring:message text="??gestione.utenti.label.presente??" code="gestione.utenti.label.presente" />';
												if(row.assente=="T"){
													assenteText='<spring:message text="??gestione.utenti.label.assente??" code="gestione.utenti.label.assente" />';
											return "<a href=\"javascript:void(0)\" style=\"color:#ff0000;font-weight:bold;\" >"+assenteText+"</a> "; 
												 
												 }else{
											return "<a href=\"javascript:void(0)\"  style=\"color:#018408;font-weight:bold;\" >"+assenteText+"</a> "; 
												 
												 }
											
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
						$('#gestioneUtentiGrid').show();
					</script>
					

<script type="text/javascript">
  function setUtenteAssente(userid,assente){
	waitingDialog.show('Loading...');
	var d = new Date();
	$.post( "./isAssente.action", { userid: userid,assente:assente,d:d.getTime(),CSRFToken:legalSecurity.getToken()})
	  .done(function( data ) {
		if(data.split(" ").join("")=="OK"){
		 waitingDialog.hide();
		$('#gestioneUtentiGrid').bootgrid('reload');	 
				}
		else if(data.split(" ").join("")=="KO"){
		 waitingDialog.hide();
			
			alert("Errore operazione non eseguita!")
		}
		else if(data.split(" ").join("")=="UTENTE-NON-AUTORIZZATO"){
		 waitingDialog.hide();
			
			alert("UTENTE-NON-AUTORIZZATO!")
		}		
		  
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
		    
		});
  
  }
	</script>
</body>
 
</html>
