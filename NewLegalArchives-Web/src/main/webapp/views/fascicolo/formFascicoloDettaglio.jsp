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
		<!-- SECTION CONTENT -->
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						<div class="card">
							<div class="card-header ch-dark palette-Green-SNAM bg">

								<h2>
									<spring:message text="??fascicolo.label.dettaglioFascicolo??"
										code="fascicolo.label.dettaglioFascicolo" />
								</h2>

							</div>
							<div class="card-body">

								<form:form name="fascicoloForm" method="post"
									modelAttribute="fascicoloDettaglioView" action="dettaglio.action"
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
									
									<form:hidden path="fascicoloId" />
									<form:hidden path="op" id="op" value="dettaglioFascicolo" />
									<form:hidden path="tabAttiva" id="tabAttiva" />
									 
									<c:set var="tab1StyleAttiva" scope="page" value=""/>
									<c:set var="tab2StyleAttiva" scope="page" value=""/>
									<c:set var="tab3StyleAttiva" scope="page" value=""/>
									<c:if test="${fascicoloDettaglioView.tipologiaFascicoloCode eq null or fascicoloDettaglioView.tipologiaFascicoloCode eq '' }">									
									 	<c:set var="tab3StyleAttiva" scope="page" value="tab-hidden"/>
										<c:set var="tab2StyleAttiva" scope="page" value="tab-hidden"/>
									</c:if>
									<c:if test="${fascicoloDettaglioView.tipologiaFascicoloCode ne null and fascicoloDettaglioView.tipologiaFascicoloCode ne '' }">									
										<c:set var="tab2StyleAttiva" scope="page" value=""/>
										<c:set var="tab3StyleAttiva" scope="page" value=""/>
									</c:if>
									
									<c:if test="${fascicoloDettaglioView.tabAttiva eq '1' }">
										<c:set var="tab1StyleAttiva" scope="page" value="active"/>										
									</c:if>
									<c:if test="${fascicoloDettaglioView.tabAttiva eq '2' }">
										<c:set var="tab2StyleAttiva" scope="page" value="active"/>
									</c:if>
									<c:if test="${fascicoloDettaglioView.tabAttiva eq '3' }">
										<c:set var="tab3StyleAttiva" scope="page" value="active"/>
									</c:if>

									<ul class="tab-nav tn-justified tn-icon" role="tablist">
										<li role="presentation" class='${tab1StyleAttiva }' onclick="document.getElementById('tabAttiva').value='1'"><a
											class="col-sx-4" href="#tab-1" aria-controls="tab-1"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-star icon-tab" aria-hidden="true"></i>-->
												<small><spring:message
														text="??fascicolo.label.datiIniziali??"
														code="fascicolo.label.datiIniziali" /></small>
										</a></li>
										<li role="presentation" class='${tab2StyleAttiva }' onclick="document.getElementById('tabAttiva').value='2'"><a class="col-xs-4" href="#tab-2"
											aria-controls="tab-2" role="tab" data-toggle="tab"> <!--<i class="fa fa-search icon-tab" aria-hidden="true"></i>-->
												<small><spring:message
														text="??fascicolo.label.datiPrincipali??"
														code="fascicolo.label.datiPrincipali" /></small>
										</a></li>
										<li role="presentation" class='${tab3StyleAttiva }' onclick="document.getElementById('tabAttiva').value='3'"><a class="col-xs-4" href="#tab-3"
											aria-controls="tab-3" role="tab" data-toggle="tab"> <!--<i class="fa fa-tags icon-tab" aria-hidden="true"></i>-->
												<small><spring:message
														text="??fascicolo.label.altriDati??"
														code="fascicolo.label.altriDati" /></small>
										</a></li>
									</ul>

									<div class="tab-content p-20">
										<div role="tabpanel"
											class="tab-pane animated fadeIn in ${tab1StyleAttiva }" id="tab-1">

											<div class="list-group lg-alt">
												<!--STATO FASCICOLO-->
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label for=stato class="col-sm-2 control-label"><spring:message
																	text="??fascicolo.label.stato??"
																	code="fascicolo.label.stato" /></label>
															<div class="col-sm-10">
																<form:input path="statoFascicolo" cssClass="form-control" readonly="true"/>
															</div>
														</div>
													</div>
												</div>
											</div>

											<div class="list-group lg-alt">
												<!--NUMERO FASCICOLO-->
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label for="nome" class="col-sm-2 control-label"><spring:message
																	text="??fascicolo.label.nome??"
																	code="fascicolo.label.nome" /></label>
															<div class="col-sm-10">
																<form:input path="nome" cssClass="form-control" readonly="true"/>
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- TIPOLOGIA FASCICOLO -->
											<div class="list-group lg-alt">

												<!--TIPOLOGIA FASCICOLO-->
												<div class="list-group-item media">
													<div class="media-body">
														<div class="form-group">
															<label for="tipologiaFascicolo"
																class="col-sm-2 control-label"><spring:message
																	text="??fascicolo.label.tipologiaFascicolo??"
																	code="fascicolo.label.tipologiaFascicolo" /></label>
															<div class="col-sm-10">
																<form:select path="tipologiaFascicoloCode" disabled="true"
																	onchange="selezionaTipologiaFascicolo(this.value)"
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
														</div>
													</div>
												</div>
											</div>
										</div>

										<c:if test="${ fascicoloDettaglioView.tipologiaFascicoloCode ne null }">
											<c:if
												test="${fascicoloDettaglioView.tipologiaFascicoloCode eq 'TFSC_1' }">
												<jsp:include page="/subviews/fascicolo/giudiziale_dettaglio.jsp"></jsp:include>
											</c:if>
											<c:if
												test="${fascicoloDettaglioView.tipologiaFascicoloCode eq 'TFSC_2' }">
												<jsp:include page="/subviews/fascicolo/stragiudiziale_dettaglio.jsp"></jsp:include>
											</c:if>
											<c:if
												test="${fascicoloDettaglioView.tipologiaFascicoloCode eq 'TFSC_4' }">
												<jsp:include page="/subviews/fascicolo/notarile_dettaglio.jsp"></jsp:include>
											</c:if>

										</c:if>

									</div>
								</form:form>
 
								 
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--/ fine col-1 -->
		</section>

	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include> 
	 

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/fascicolo.js"></script>
		
	
	<!-- MODAL Dettaglio Stampa -->
	<div class="modal fade" id="modalDettaglioStampa" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content" style="height:570px"> 
				<div class="modal-header">
					<h4 class="modal-title"> </h4>
				</div>
				<div class="modal-body" style="overflow-x: auto;height:90%">
				<iframe id="bodyDettaglioStampa" src="" style="width:100%;height:100%;border:0px"></iframe>
	
				</div>
				 
			</div>
		</div>
	</div>
	<!-- FINE MODAL Dettaglio Stampa -->	
	<script>
	function stampaFascicoloDettaglio(id){
		var url="<%=request.getContextPath()%>/fascicolo/stampaDettaglio.action?id="+id+"&azione=visualizza";
url=legalSecurity.verifyToken(url);
		waitingDialog.show('Loading...');
		var ifr=$("#bodyDettaglioStampa").attr("src",url);
		ifr.load(function(){
		  	 waitingDialog.hide();
			$("#modalDettaglioStampa").modal("show");
		})
	}
	</script>
		
</body>
</html>
