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
									<spring:message text="??incarico.label.cercaIncarico??"
										code="incarico.label.cercaIncarico" />
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
										<li role="presentation" class='' ><a
											class="col-xs-4" href="#tab-2" aria-controls="tab-2"
											role="tab" data-toggle="tab"> <!--<i class="fa fa-search icon-tab" aria-hidden="true"></i>-->
												<small><spring:message
														text="??incarico.label.arbitrale??"
														code="incarico.label.arbitrale" /></small>
										</a></li>
									</ul>

									<div class="tab-content p-20">
										<div role="tabpanel"
											class="tab-pane animated fadeIn in active"
											id="tab-1"> 
											
											<p class="visible-lg visible-md visible-xs visible-sm text-left text-left">
												<spring:message text="??fascicolo.label.nonHaiTrovatoCercavi??"
													code="fascicolo.label.nonHaiTrovatoCercavi" />
												<a data-toggle="modal" href="#panelRicerca" class=""> <spring:message
														text="??fascicolo.label.affinaRicerca??"
														code="fascicolo.label.affinaRicerca" />
												</a>
											</p>
											
											<table id="tabellaRicercaIncarico"
												class="table table-striped table-responsive">

											</table>
										</div> 
										<div role="tabpanel"
											class="tab-pane animated fadeIn in "
											id="tab-2">  
											
											<p class="visible-lg visible-md visible-xs visible-sm text-left text-left">
												<spring:message text="??fascicolo.label.nonHaiTrovatoCercavi??"
													code="fascicolo.label.nonHaiTrovatoCercavi" />
												<a data-toggle="modal" href="#panelRicercaArbitrale" class=""> <spring:message
														text="??fascicolo.label.affinaRicerca??"
														code="fascicolo.label.affinaRicerca" />
												</a>
											</p>
											
											<table id="tabellaRicercaArbitrale"
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
								<label class="col-md-4 control-label" for="nomeIncarico"><spring:message
										text="??incarico.label.nome??"
										code="incarico.label.nome" /></label>
								<div class="col-md-4">
									<input id="txtNomeIncarico" type='text' class="form-control"
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
							<div class="form-group">
								<label class="col-md-4 control-label" for="statoIncarico"><spring:message
										text="??incarico.label.statoIncarico??"
										code="incarico.label.statoIncarico" /></label>
								<div class="col-md-4">
									<select id="statoIncaricoCode" class="form-control">
										<option value="">
											<spring:message
												text="??incarico.label.selezionaStatoIncarico??"
												code="incarico.label.selezionaStatoIncarico" />
										</option>
										<c:if test="${ incaricoRicercaView.listaStatoIncarico != null }">
											<c:forEach items="${incaricoRicercaView.listaStatoIncarico}"
												var="oggetto">
												<option value="${ oggetto.vo.codGruppoLingua }">
													<c:out value="${oggetto.vo.descrizione}"></c:out>
												</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>		
							<!-- Dal...AL -->
							
							<div class="form-group">
								<label class="col-md-4 control-label" for="nomeFascicolo"><spring:message
										text="??incarico.label.nomeFascicolo??"
										code="incarico.label.nomeFascicolo" /></label>
								<div class="col-md-4">
									<input id="txtNomeFascicolo" type='text' class="form-control"
										placeholder="">
								</div>
							</div>
							
							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltri" name="singlebutton" type="button"
										onclick="cercaIncarico()" data-dismiss="modal"
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
  

	<!-- PANEL RICERCA MODALE ARBITRALE -->
	<div class="modal fade" id="panelRicercaArbitrale" tabindex="-1" role="dialog"
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
								<label class="col-md-4 control-label" for="nomeIncarico"><spring:message
										text="??incarico.label.nomeCollegioArbitrale??"
										code="incarico.label.nomeCollegioArbitrale" /></label>
								<div class="col-md-4">
									<input id="txtNomeCollegioArbitrale" type='text' class="form-control"
										placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="statoIncaricoArbitrale"><spring:message
										text="??incarico.label.statoIncaricoArbitrale??"
										code="incarico.label.statoIncaricoArbitrale" /></label>
								<div class="col-md-4">
									<select id="statoIncaricoArbitraleCode" class="form-control">
										<option value="">
											<spring:message
												text="??incarico.label.selezionaStatoIncarico??"
												code="incarico.label.selezionaStatoIncarico" />
										</option>
										<c:if test="${ incaricoRicercaView.listaStatoIncarico != null }">
											<c:forEach items="${incaricoRicercaView.listaStatoIncarico}"
												var="oggetto">
												<option value="${ oggetto.vo.codGruppoLingua }">
													<c:out value="${oggetto.vo.descrizione}"></c:out>
												</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>		
							<!-- DAL...AL -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic"><spring:message
										text="??fascicolo.label.dataDal??"
										code="fascicolo.label.dataDal" /></label>
								<div class="col-md-4">
									<input id="txtDataDalArbitrale" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic"><spring:message
										text="??fascicolo.label.dataAl??"
										code="fascicolo.label.dataAl" /></label>
								<div class="col-md-4">
									<input id="txtDataAlArbitrale" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							<!-- Dal...AL -->
							
							<div class="form-group">
								<label class="col-md-4 control-label" for="nomeFascicolo"><spring:message
										text="??incarico.label.nomeFascicolo??"
										code="incarico.label.nomeFascicolo" /></label>
								<div class="col-md-4">
									<input id="txtNomeFascicoloArbitrale" type='text' class="form-control"
										placeholder="">
								</div>
							</div>
							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltriArbitrale" name="singlebutton" type="button"
										onclick="cercaIncaricoArbitrale()" data-dismiss="modal"
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


	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>


	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>
 	

	<script type="text/javascript">
		initTabellaRicercaIncarico();
		initTabellaRicercaIncaricoArbitrale();
	</script>
 

</body>
</html>
