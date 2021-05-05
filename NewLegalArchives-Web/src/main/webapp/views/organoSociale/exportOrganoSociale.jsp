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

</head>

<body data-ma-header="teal">
	<jsp:include page="/parts/header.jsp">
	</jsp:include>

	<!-- SECION MAIN -->
	<section id="main">
		<jsp:include page="/parts/aside.jsp">
		</jsp:include>
		
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						<div class="card">
							<div class="card-header">
									<h1>
									<spring:message text="??organoSociale.label.exportOrganoSociale??" code="organoSociale.label.exportOrganoSociale" />	 
									</h1>
								 	<!-- <p class="text-left" style="display:block">
										<spring:message text="??atto.label.ricercaCustomAttoStart??" code="atto.label.ricercaCustomAttoStart" /> 
										<a data-toggle="modal" href="#modalRicercaOrganoSociale" class="">
										   <spring:message text="??atto.label.ricercaCustomAttoEnd??" code="atto.label.ricercaCustomAttoEnd" />
										</a>
									</p>-->
							</div>
							
							<div class="card-body">
								<div id="sezioneMessaggiApplicativi"></div>
								<!-- <div class="table-responsive">
									<table id="data-table-organosociale" class="table table-striped table-responsive"></table>
								</div> -->
								<div class="table-responsive">
									<ul class="tab-nav tn-justified tn-icon" role="tablist">
										<li role="presentation" class='active' ><a
											class="col-sx-4" href="#tab-1" aria-controls="tab-1"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-star icon-tab" aria-hidden="true"></i>-->
												<small>Export Massivo</small>
										</a></li>
										
									</ul>
									<div class="tab-content p-20">
										<div role="tabpanel"
											class="tab-pane animated fadeIn in active"
											id="tab-1"> 
											
											<div class="modal-content">
												<div class="modal-body">
												
													<div class="form-group">
														
															<label for="uploadInvoices" class="col-sm-2 control-label">
																Export Organi Sociali
															</label>
														
															<div class="col-sm-1">	
																<form action="<%=request.getContextPath()%>/organoSociale/exportOrganoSociale.action" method="post" >
																<!-- <button type="button"
																		class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																		style="float: left; position: relative !important;background-color: #d9d9d9;"
																		onclick="exportOrganoSociale()">
																		<i class="zmdi zmdi-download icon-mini"></i>
																</button>-->
																	<button type="submit"
																			class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																			style="float: left; position: relative !important;background-color: #d9d9d9;">
																			<i class="zmdi zmdi-download icon-mini"></i>
																	</button>
																</form>
															</div>
														
									
													</div>
												</div>
											</div> 
										</div>
									</div>		
								</div>
								
							</div>
							
						</div>
						
					</div>
					
				</div>
				
			</div>
			
			
			
			
	   
	   </section>
		
	   <jsp:include page="/views/organoSociale/modal-ricerca.jsp"></jsp:include>
	   
	</section>

    <footer>
    <div class="modal fade" id="panelConfirmDeleteOrganoSociale" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??organoSociale.label.confermaCancellazioneOrganoSociale??"
						code="organoSociale.label.confermaCancellazioneOrganoSociale" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnEliminaOrganoSociale"></label>
						<div class="col-md-8">
							<button id="btnEliminaOrganoSociale" name="btnEliminaOrganoSociale" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
    
    
	   <jsp:include page="/parts/footer.jsp"></jsp:include>
    </footer>
   
    <jsp:include page="/parts/script-end.jsp"></jsp:include>
	
	<script src="<%=request.getContextPath()%>/portal/js/controller/ricercaOrganoSociale.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/controller/organoSociale.js"></script>

	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>
	

	<script type="text/javascript">
	
		$('#panelConfirmDeleteOrganoSociale').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnEliminaOrganoSociale").attr(
						'onclick',
						'eliminaOrganoSociale('
								+ $(e.relatedTarget).data('idorganosociale')
								+ ')');
			});
	     initTabellaRicercaOrganoSociale();
	</script>


</body>
</html>
