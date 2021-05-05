<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
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
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						<div class="card">
							<div class="card-header">
								<h1>
									<spring:message text="Esporta Info FileNet"
										code="" />
								</h1> 
							</div>
							<div class="card-body">
								<div id="sezioneMessaggiApplicativi"></div>
								<div class="table-responsive">
									<ul class="tab-nav tn-justified tn-icon" role="tablist">
										<li role="presentation" class='active' ><a
											class="col-sx-4" href="#tab-1" aria-controls="tab-1"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-star icon-tab" aria-hidden="true"></i>-->
												<small><spring:message
														text="??incarico.label.incarichi??"
														code="incarico.label.incarichi" /></small>
										</a></li>
									</ul>

									<div class="tab-content p-20">
										<div role="tabpanel"
											class="tab-pane animated fadeIn in active"
											id="tab-1"> 
											
											<div class="modal-content">
												<div class="modal-body">
												
													<div class="form-group">
														<label for="procuraDesc" class="col-sm-2 control-label"><spring:message
																text="Carica Incarichi" code="" /></label>
														<div class="col-sm-7">
															<input readonly class="form-control" value=""/>
														</div>
														
														<div class="col-sm-1">		
															<button type="button" data-toggle="modal" 
																	data-target="#panelCaricaIncarichi"
																	class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																	style="float: left; position: relative !important;background-color: #d9d9d9;">
																	<i class="zmdi zmdi-plus icon-mini"></i>
															</button>
														</div>
														
														<%-- <div class="col-sm-8">
															<form id="formAggiungiProcura" method="post">
															<engsecurity:token regenerate="false"/>
															
																<!-- Button -->
																<div class="form-group">
																	<div class="col-md-12">
																		<button id="btnAggiungiProcura" name="btnAggiungiProcura"
																			type="button" onclick="aggiungiFileId()"
																			class="btn btn-primary" style="position:absolute; left:48%; top : -30px;">
																			<spring:message text="Genera Excel"
																				code="" />
																		</button>
																	</div>
																</div>
															</form>
														</div> --%>
												
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
	</section>
	
	<div class="modal fade" id="panelCaricaIncarichi" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="Carica Incarichi" code="" />
					</h4>
				</div>
				<div class="modal-body">
					<form id="formCaricaIncarichi" method="post"
						enctype="application/x-www-form-urlencoded" class="form-horizontal">
						<engsecurity:token regenerate="false"/>
	 					<div class="form-group">
							<div class="col-md-8">
								<input type="file" name="fileIncarichi" id="fileIncarichi" />
							</div>
						</div>
						<!-- Button -->
						<div class="form-group">
							<div class="col-md-8">
								<button id="btnAggiungiIncarichi" name="btnAggiungiIncarichi"
									data-dismiss="modal" type="button" onclick="aggiungiIncarichi()"
									class="btn btn-primary">
									<spring:message text="??incarico.label.ok??"
										code="incarico.label.ok" />
								</button>
								<button name="singlebutton" type="button" data-dismiss="modal"
									class="btn btn-warning">
									<spring:message text="??incarico.label.chiudi??"
										code="incarico.label.chiudi" />
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>

	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>
 	
</body>
</html>
