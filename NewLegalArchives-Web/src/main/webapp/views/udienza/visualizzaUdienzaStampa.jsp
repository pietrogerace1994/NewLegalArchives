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

<style>
	.form-control[disabled], .form-control[readonly], fieldset[disabled] .form-control {
	    background-color: #ffffff!important;
	    opacity: 1;}
	.form-control:not(.fc-alt) {
	    border-left: 0;
	    border-right: 0;
	    border: 0px !important;
	    appearance: none;
	    padding: 0;
	    paddingleft: 10px;
	}
	body{
	background-color:#ffffff;
	}
	.card-header{color:#000000!important;}
	.card-header.ch-dark h2, .card-header.ch-dark small{color:#000000!important;}
	
	[data-ma-header="teal"]:before, [data-ma-header="teal"] #header {
	    background-color: #ffffff!important;
		}
	
	#udienzaStampa td{ padding-top:5px;padding-bottom:5px;}
	#udienzaStampa label,input{ text-align:left;}	
</style>

</head>

<body data-ma-header="teal">

<!-- SECION MAIN -->
	<section id="main">
		<!-- SECTION CONTENT -->
		<section id="content">
		<div class="container">
			<div class="row">
				<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
	
					<div class="card">
						<div class="card-header ch-dark palette-Green-SNAM">
							 
					        <div id="box-stampa" style="width:100%;padding-bottom:20px;position:relative;"> 
					        	<a href="javascript:void(0)" onclick="javascript:window.print()" 
					        	style="position:absolute;top:10px;right: 20px;z-index: 1000;color: #000000;font-weight: bold;">
									<spring:message text="??udienza.button.stampa??" code="udienza.button.stampa" />
								</a>
					        </div>	
						</div>
						<div class="card-body">
						
							<!-- Area Dettaglio Udienza-->
				
							<div class="table-responsive">
					
								<div class="col-md-12">
									<div class="col-md-12">
										<div class="col-md-12" style="margin-bottom: 20px;margin-top: 20px;border-bottom: 1px">
											<h4><spring:message text="??udienza.label.dettaglioUdienza??" code="udienza.label.dettaglioUdienza" /></h4> 
										</div>
										
										
										<div class="col-md-12">
										
									        <c:if test="${ not empty listError }">
												<div class="alert alert-danger box-error">
													<c:forEach items="${listError}" var="value">
											         <c:out value="${value}"></c:out><br>
									   				</c:forEach>
												</div>
											</c:if>
									 	<div class="alert alert-danger box-error" style="display:none"></div>
									 		
									 		<!--INIT TABLE -->
											<fieldset>
												<table id="udienzaStampa" style="width: 100%" border="1px"> 
												
													<!-- NOME FASCICOLO -->
													<tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="statoScheda">
																<spring:message text="??udienza.label.fascicolo??" code="udienza.label.fascicolo" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${udienzaView.nomeFascicolo}"></c:out> 
															</div>
														</td>
													</tr>
											    	
											    	<!-- DATA CREAZIONE -->
											    	<tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="dataCreazione">
																<spring:message text="??udienza.label.dataCreazione??" code="udienza.label.dataCreazione" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${udienzaView.dataCreazione}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- DATA UDIENZA -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="statoScheda">
																<spring:message text="??udienza.label.dataUdienza??" code="udienza.label.dataUdienza" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${udienzaView.dataUdienza}"></c:out> 
															</div>
														</td>
													 </tr>
													 
													 <!-- OGGETTO -->
													 <tr>
														<td style="width:30%">			
															<label class="col-md-12 control-label" for="tipologia">
																<spring:message text="??udienza.label.oggetto??" code="udienza.label.oggetto" />
															</label>
													 	</td>
													    <td style="width:70%">		
															<div class="col-md-12">
																<c:out value="${udienzaView.descrizione}"></c:out> 
															</div>
														</td>
													 </tr>
													 
												</table> 
									        </fieldset>
					
											<div class="modal-footer">
												<div class="col-md-12 column">
										        	<div class="btn-group dropup pull-right ">
														<div class="btn-group pull-right space-to-left"> </div>
														<!-- pulsante esporta senza opzioni -->
														<div class="btn-group pull-right"> </div>
														<div class="btn-group pull-right"> </div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!--/ fine card -->
				</div>
			</div>
		</div>
		</section>
	</section>
 
<jsp:include page="/parts/script-end.jsp"></jsp:include>
<script src="<%=request.getContextPath()%>/portal/js/controller/fascicolo.js"></script>
<script src="<%=request.getContextPath()%>/portal/js/controller/udienza.js"></script>

</body>
</html>