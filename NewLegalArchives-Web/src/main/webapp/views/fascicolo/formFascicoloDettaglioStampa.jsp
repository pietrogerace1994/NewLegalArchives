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
	
	#fascicoloDettaglioStampa td{ padding-top:5px;padding-bottom:5px;}
	#fascicoloDettaglioStampa label,input{ text-align:left;}	
</style>

</head>

<body data-ma-header="teal">

<!-- SECION MAIN -->
	<section id="main">
		<!-- SECTION CONTENT -->
		<section id="content">
		<div class="container">
			<div class="row">
				<div id="col-1" class="col-lg-12 col-sm-12 col-sm-12 col-sx-12">
					
					<form:form name="fascicoloForm" method="post" modelAttribute="fascicoloDettaglioView" action="stampaDettaglio.action"
						class="form-horizontal la-form">
						<engsecurity:token regenerate="false"/>

						<form:errors path="*" cssClass="alert alert-danger"
							element="div"></form:errors>
						<c:if test="${ not empty param.successMessage }">									
							<div class="alert alert-info">
								<spring:message code="messaggio.operazione.ok" text="??messaggio.operazione.ok??"></spring:message>
							</div>											
						</c:if>	
						<c:if test="${ not empty param.errorMessage }">									
							<div class="alert alert-danger">
								<spring:message code="${param.errorMessage}" text="??${param.errorMessage}??"></spring:message>
							</div>											
						</c:if>	
					
						<div class="card">
							<div class="card-header ch-dark palette-Green-SNAM">
								 
						        <div id="box-stampa" style="width:100%;padding-bottom:20px;position:relative;"> 
						        	<a href="javascript:void(0)" onclick="javascript:window.print()" 
						        	style="position:absolute;top:10px;right: 20px;z-index: 1000;color: #000000;font-weight: bold;">
										<spring:message text="??generic.label.stampa??" code="generic.label.stampa" />
									</a>
						        </div>	
							</div>
							<div class="card-body">
							
								<!-- Area Dettaglio Fascicolo-->
					
								<div class="table-responsive">
						
									<div class="col-sm-12">
										<div class="col-sm-12">
											<div class="col-sm-12" style="margin-bottom: 20px;margin-top: 20px;border-bottom: 1px">
												<h4><spring:message text="??fascicolo.label.dettaglioFascicolo??" code="fascicolo.label.dettaglioFascicolo" /></h4> 
											</div>
											
											<div class="col-sm-12">
												        
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
													<table id="fascicoloDettaglioStampa" style="width: 100%" border="1px"> 
												    	
												    	<!-- STATO FASCICOLO -->
												    	<tr>
															<td style="width:30%">			
																<label for=stato class="col-sm-12 control-label"><spring:message
																		text="??fascicolo.label.stato??"
																		code="fascicolo.label.stato" /></label>
														 	</td>
														    <td style="width:70%">		
																<div class="col-sm-12">
																	<form:input path="statoFascicolo" cssClass="form-control" readonly="true"/> 
																</div>
															</td>
														 </tr>
														 
														 <!--NUMERO FASCICOLO-->
												    	<tr>
															<td style="width:30%">			
																<label for=stato class="col-sm-12 control-label"><spring:message
																		text="??fascicolo.label.nome??"
																		code="fascicolo.label.nome" /></label>
														 	</td>
														    <td style="width:70%">		
																<div class="col-sm-12">
																	<form:input path="nome" cssClass="form-control" readonly="true"/> 
																</div>
															</td>
														 </tr>
														 
														 <!-- TIPOLOGIA -->
														 <tr>
															<td style="width:30%">			
																<label class="col-sm-12 control-label" for="tipologia">
																	<spring:message text="??fascicolo.label.tipologiaFascicolo??" code="fascicolo.label.tipologiaFascicolo" />
																</label>
														 	</td>
														    <td style="width:70%">		
																<div class="col-sm-12">
																	<form:select path="tipologiaFascicoloCode" disabled="true"
																		cssClass="form-control">
																		<form:option value="">
																			<spring:message
																				text="??fascicolo.label.selezionaTipologiaFascicolo??"
																				code="fascicolo.label.selezionaTipologiaFascicolo" />
																		</form:option>
	
																		<c:if
																			test="${ fascicoloDettaglioView.listaTipologiaFascicolo != null }">
																			<c:forEach
																				items="${fascicoloDettaglioView.listaTipologiaFascicolo}"
																				var="oggetto">
																				<form:option value="${ oggetto.vo.codGruppoLingua }">
																					<c:out value="${oggetto.vo.descrizione}"></c:out>
																				</form:option>
																			</c:forEach>
																		</c:if>
																	</form:select>
																</div>
															</td>
														 </tr>
														 
														 <c:if test="${ fascicoloDettaglioView.tipologiaFascicoloCode ne null }">
															<c:if
																test="${fascicoloDettaglioView.tipologiaFascicoloCode eq 'TFSC_1' }">
																<jsp:include page="/subviews/fascicolo/giudiziale_dettaglio_stampa.jsp"></jsp:include>
															</c:if>
															<c:if
																test="${fascicoloDettaglioView.tipologiaFascicoloCode eq 'TFSC_2' }">
																<jsp:include page="/subviews/fascicolo/stragiudiziale_dettaglio_stampa.jsp"></jsp:include>
															</c:if>
															<c:if
																test="${fascicoloDettaglioView.tipologiaFascicoloCode eq 'TFSC_4' }">
																<jsp:include page="/subviews/fascicolo/notarile_dettaglio_stampa.jsp"></jsp:include>
															</c:if>
				
														 </c:if>
														 
														 </table>
										        </fieldset>
						
												<div class="modal-footer"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--/ fine card -->
					</form:form>
				</div>
			</div>
		</div>
		</section>
	</section>
 
<jsp:include page="/parts/script-end.jsp"></jsp:include>

</body>
</html>