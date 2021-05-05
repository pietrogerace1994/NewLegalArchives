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
									<spring:message text="??schedaFondoRischi.label.cercaSchedaFondoRischi??"
										code="schedaFondoRischi.label.cercaSchedaFondoRischi" />
								</h1> 
							</div>
							<div class="card-body">
								<div id="sezioneMessaggiApplicativi"></div>
								<div class="table-responsive">
									
									<div class="tab-content p-20">
										<div role="tabpanel" class="tab-pane animated fadeIn in active" id="tab-1"> 
											
											<p class="visible-lg visible-md visible-xs visible-sm text-left text-left">
												<spring:message text="??fascicolo.label.nonHaiTrovatoCercavi??"
													code="fascicolo.label.nonHaiTrovatoCercavi" />
												<a data-toggle="modal" href="#panelRicerca" class=""> <spring:message
														text="??fascicolo.label.affinaRicerca??"
														code="fascicolo.label.affinaRicerca" />
												</a>
											</p>
											
											<table id="tabellaRicercaSchedaFondoRischi"
												class="table table-striped table-responsive">

											</table>
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


	<!-- PANEL RICERCA MODALE -->
	<div class="modal fade" id="panelRicerca" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.miglioraRicerca??"
							code="fascicolo.label.miglioraRicerca" />
					</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<fieldset>
							<div class="form-group">
								<label class="col-md-4 control-label" for="nomeFascicolo"><spring:message
										text="??schedaFondoRischi.label.nomeFascicolo??"
										code="schedaFondoRischi.label.nomeFascicolo" /></label>
								<div class="col-md-4">
									<input id="txtNomeFascicolo" type='text' class="form-control"
										placeholder="">
								</div>
							</div>
							
							<!-- DAL...AL -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic"><spring:message
										text="??fascicolo.label.dataDal??"
										code="fascicolo.label.dataDal" /></label>
								<div class="col-md-4">
									<input id="txtDataDal" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic"><spring:message
										text="??fascicolo.label.dataAl??"
										code="fascicolo.label.dataAl" /></label>
								<div class="col-md-4">
									<input id="txtDataAl" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							
							<!-- STATO SCHEDA -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="statoIncarico"><spring:message
										text="??schedaFondoRischi.label.statoSchedaFondoRischi??"
										code="schedaFondoRischi.label.statoSchedaFondoRischi" /></label>
								<div class="col-md-4">
									<select id="statoSchedaFondoRischiCode" class="form-control">
										<option value="">
											<spring:message
												text="??schedaFondoRischi.label.selezionaStatoSchedaFondoRischi??"
												code="schedaFondoRischi.label.selezionaStatoSchedaFondoRischi" />
										</option>
										<c:if test="${ schedaFondoRischiView.listaStatoSchedaFondoRischi != null }">
											<c:forEach items="${schedaFondoRischiView.listaStatoSchedaFondoRischi}"
												var="oggetto">
												<option value="${ oggetto.vo.codGruppoLingua }">
													<c:out value="${oggetto.vo.descrizione}"></c:out>
												</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>		
							

							<!-- TIPOLOGIA SCHEDA -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="statoIncarico"><spring:message
										text="??schedaFondoRischi.label.tipologia??"
										code="schedaFondoRischi.label.tipologia" /></label>
								<div class="col-md-4">
									<select id="tipologiaSchedaFondoRischiCode" class="form-control">
										<option value="">
											<spring:message
												text="??schedaFondoRischi.label.selezionaTipologia??"
												code="schedaFondoRischi.label.selezionaTipologia" />
										</option>
										<c:if test="${ schedaFondoRischiView.listaTipologiaScheda != null }">
											<c:forEach items="${schedaFondoRischiView.listaTipologiaScheda}"
												var="oggetto">
												<option value="${ oggetto.vo.codGruppoLingua }">
													<c:out value="${oggetto.vo.descrizione}"></c:out>
												</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>	
							
							
							<!-- RISCHIO SOCCOMBENZA SCHEDA -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="statoIncarico"><spring:message
										text="??schedaFondoRischi.label.rischioSoccombenza??"
										code="schedaFondoRischi.label.rischioSoccombenza" /></label>
								<div class="col-md-4">
									<select id="rischioSoccombenzaSchedaFondoRischiCode" class="form-control">
										<option value="">
											<spring:message
												text="??schedaFondoRischi.label.selezionaRischioSoccombenza??"
												code="schedaFondoRischi.label.selezionaRischioSoccombenza" />
										</option>
										<c:if test="${ schedaFondoRischiView.listaRischioSoccombenza != null }">
											<c:forEach items="${schedaFondoRischiView.listaRischioSoccombenza}"
												var="oggetto">
												<option value="${ oggetto.vo.codGruppoLingua }">
													<c:out value="${oggetto.vo.descrizione}"></c:out>
												</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>		
							
							<%-- <div class="form-group">
								<label class="col-md-4 control-label" for="controparte"><spring:message
										text="??schedaFondoRischi.label.controparte??"
										code="schedaFondoRischi.label.controparte" /></label>
								<div class="col-md-4">
									<input id="txtControparte" type='text' class="form-control"
										placeholder="">
								</div>
							</div> --%>
							
							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltri" name="singlebutton" type="button"
										onclick="cercaSchedaFondoRischi()" data-dismiss="modal"
										class="btn btn-primary">
										<spring:message text="??fascicolo.label.eseguiRicerca??"
											code="fascicolo.label.eseguiRicerca" />
									</button>
									<button name="singlebutton" type="button" data-dismiss="modal"
										class="btn btn-warning">
										<spring:message text="??fascicolo.label.chiudi??"
											code="fascicolo.label.chiudi" />
									</button>
								</div>
							</div>
							
							
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- FINE PANEL RICERCA MODALE -->
	
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
	function stampaScheda(id){
		var url="<%=request.getContextPath()%>/schedaFondoRischi/stampa.action?id="+id+"&azione=visualizza";
		waitingDialog.show('Loading...');
		var ifr=$("#bodyDettaglioStampa").attr("src",url);
		ifr.load(function(){
		  	 waitingDialog.hide();
			$("#modalDettaglioStampa").modal("show");
		})
	}
	</script>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>


	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>
 	

	<script type="text/javascript">
		initTabellaRicercaSchedaFondoRischi();
	</script>
 

</body>
</html>
