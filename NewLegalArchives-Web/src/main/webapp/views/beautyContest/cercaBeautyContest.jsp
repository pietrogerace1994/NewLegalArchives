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
									<spring:message text="??beautyContest.label.cercaBeautyContest??"
										code="beautyContest.label.cercaBeautyContest" />
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
											
											<table id="tabellaRicercaBeautyContest"
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
					<engsecurity:token regenerate="false"/>
						<fieldset>
							<div class="form-group">
								<label class="col-md-4 control-label" for="titolo"><spring:message
										text="??beautyContest.label.titolo??"
										code="beautyContest.label.titolo" /></label>
								<div class="col-md-4">
									<input id="txtTitolo" type='text' class="form-control"
										placeholder="">
								</div>
							</div>

							<!-- DAL...AL -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="dataDal"><spring:message
										text="??fascicolo.label.dataDal??"
										code="fascicolo.label.dataDal" /></label>
								<div class="col-md-4">
									<input id="txtDataDal" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="dataAl"><spring:message
										text="??fascicolo.label.dataAl??"
										code="fascicolo.label.dataAl" /></label>
								<div class="col-md-4">
									<input id="txtDataAl" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="stato"><spring:message
										text="??beautyContest.label.statoBeautyContest??"
										code="beautyContest.label.statoBeautyContest" /></label>
								<div class="col-md-4">
									<select id="statoBeautyContestCode" class="form-control">
										<option value="">
											<spring:message
												text="??beautyContest.label.selezionaStatoBeautyContest??"
												code="beautyContest.label.selezionaStatoBeautyContest" />
										</option>
										<c:if test="${ beautyContestView.listaStatoBeautyContest != null }">
											<c:forEach items="${beautyContestView.listaStatoBeautyContest}"
												var="oggetto">
												<option value="${ oggetto.vo.codGruppoLingua }">
													<c:out value="${oggetto.vo.descrizione}"></c:out>
												</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>	
							
							<div class="form-group">
								<label class="col-md-4 control-label" for="centroDiCosto"><spring:message
										text="??beautyContest.label.centroDiCosto??"
										code="beautyContest.label.centroDiCosto" /></label>
								<div class="col-md-4">
									<input id="txtCentroDiCosto" type='text' class="form-control"
										placeholder="">
								</div>
							</div>	
							
							
							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltri" name="singlebutton" type="button"
										onclick="cercaBeautyContest()" data-dismiss="modal"
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
	function stampaBeautyContest(id){
		var url="<%=request.getContextPath()%>/beautyContest/stampa.action?id="+id+"&azione=visualizza";
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
		initTabellaRicercaBeautyContest();
	</script>
 

</body>
</html>
