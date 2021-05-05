<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="eng.la.model.view.StatoFascicoloView"%>
<%@page import="java.util.List"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.business.AnagraficaStatiTipiService"%>
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
									<spring:message text="??fascicolo.label.cercaFascicoli??"
										code="fascicolo.label.cercaFascicoli" />
								</h1>
								<p class="visible-lg visible-md visible-xs visible-sm text-left">
									<spring:message text="??fascicolo.label.nonHaiTrovatoCercavi??"
										code="fascicolo.label.nonHaiTrovatoCercavi" />
									<a data-toggle="modal" href="#panelRicerca" class=""> <spring:message
											text="??fascicolo.label.affinaRicerca??"
											code="fascicolo.label.affinaRicerca" />
									</a>
								</p>
							</div>
							<div class="card-body">
								<div id="sezioneMessaggiApplicativi"></div>
								<div class="">
									<table id="tabellaRicercaFascicoli"
										class="table table-striped table-responsive">
									
									</table>
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
								<label class="col-md-4 control-label" for="tipologiaFascicolo"><spring:message
										text="??fascicolo.label.tipologiaFascicolo??"
										code="fascicolo.label.tipologiaFascicolo" /></label>
								<div class="col-md-4">
									<select id="tipologiaFascicoloCode" disabled
										class="form-control">
										<c:if
											test="${ fascicoloRicercaView.listaTipologiaFascicolo != null }">
											<c:forEach items="${fascicoloRicercaView.listaTipologiaFascicolo}"
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
								<label class="col-md-4 control-label" for="settoreGiuridico"><spring:message
										text="??fascicolo.label.settoreGiuridico??"
										code="fascicolo.label.settoreGiuridico" /></label>
								<div class="col-md-4">
									<select id="settoreGiuridicoCode" class="form-control">
										<option value="">
											<spring:message
												text="??fascicolo.label.selezionaSettoreGiuridico??"
												code="fascicolo.label.selezionaSettoreGiuridico" />
										</option>
										<c:if test="${ fascicoloRicercaView.listaSettoreGiuridico != null }">
											<c:forEach items="${fascicoloRicercaView.listaSettoreGiuridico}"
												var="oggetto">
												<option value="${ oggetto.vo.codGruppoLingua }">
													<c:out value="${oggetto.vo.nome}"></c:out>
												</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="societaAddebito"><spring:message
										text="??fascicolo.label.societaAddebito??"
										code="fascicolo.label.societaAddebito" /></label>
								<div class="col-md-4">
									<select id="societaAddebito" class="form-control">
										<option value="">
											<spring:message
												text="??fascicolo.label.societaAddebito??"
												code="fascicolo.label.societaAddebito" />
										</option>
										<c:if test="${ fascicoloRicercaView.listaSocieta != null }">
											<c:forEach items="${fascicoloRicercaView.listaSocieta}"
												var="oggetto">
												<option value="${ oggetto.vo.id }">
													<c:out value="${oggetto.vo.nome}"></c:out>
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
							<!-- Dal...AL -->
							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="legale_esterno">
									<spring:message text="??fascicolo.label.legaleEsterno??"
										code="fascicolo.label.legaleEsterno" />
								</label>
								<div class="col-md-4">
									<spring:message text="??fascicolo.label.inserisciNominativo??"
										var="insertNominativoLabel"
										code="fascicolo.label.inserisciNominativo" />
									<input id="txtLegaleEsterno" name="legale_esterno" type="text"
										placeholder="${insertNominativoLabel }"
										class="typeahead form-control input-md">
								</div>
							</div>
							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="controparte"><spring:message
										text="??fascicolo.label.controparte??"
										code="fascicolo.label.controparte" /></label>
								<div class="col-md-4">
									<spring:message text="??fascicolo.label.inserisciControparte??"
										var="insertControparteLabel"
										code="fascicolo.label.inserisciControparte" />
									<input id="txtControparte" name="controparte" type="text"
										placeholder="${insertControparteLabel }"
										class="typeahead form-control input-md">
								</div>
							</div>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="textinput"><spring:message
										text="??fascicolo.label.numeroFascicolo??"
										code="fascicolo.label.numeroFascicolo" /></label>
								<div class="col-md-4">

									<spring:message text="??fascicolo.label.inserisciNumero??"
										var="insertNumeroLabel"
										code="fascicolo.label.inserisciNumero" />
									<input id="txtNome" name="textinput" type="text"
										placeholder="${insertNumeroLabel }"
										class="form-control input-md" value="<%=request.getAttribute("nomeFascicolo") %>" >
								</div>
							</div>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="textinput"><spring:message
										text="??fascicolo.label.oggettoFascicolo??"
										code="fascicolo.label.oggettoFascicolo" /></label>
								<div class="col-md-4">

									<spring:message text="??fascicolo.label.inserisciOggetto??"
										var="insertOggettoLabel"
										code="fascicolo.label.inserisciOggetto" />
									<input id="txtOggetto" name="textinput" type="text"
										placeholder="${insertOggettoLabel }"
										class="form-control input-md">
								</div>
							</div>
							
								<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="textinput"><spring:message
										text="??fascicolo.label.legaleInterno??"
										code="fascicolo.label.legaleInterno" /></label>
								<div class="col-md-4">
									<select class="form-control" id="comboOwner">
										<option value="">
										</option>
										<%
										UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
										try{
											List<UtenteView> legaliInterni = utenteService.leggiUtenti();
											if( legaliInterni != null ){
												for( UtenteView legale : legaliInterni ){
											%>
											<option value="<%=legale.getVo().getUseridUtil()%>">
											 <%= legale.getVo().getNominativoUtil()%>
											</option>
											<%	} 
											}
										
										}catch(Throwable e){
											e.printStackTrace();
										}
										%>
									</select> 
								</div>
							</div>


							<div class="form-group">
								<label class="col-md-4 control-label" for="stato">
									<spring:message text="??fascicolo.label.stato??"
										code="proforma.label.stato" />
								</label>
								<div class="col-md-4"> 
									 <select class="form-control" id="comboStato">
										<option value="">
										</option>
										<%
										AnagraficaStatiTipiService service = (AnagraficaStatiTipiService) SpringUtil.getBean("anagraficaStatiTipiService");
										try{
											List<StatoFascicoloView> stati = service.leggiStatiFascicolo(request.getLocale().getLanguage().toUpperCase());
											if( stati != null ){
												for( StatoFascicoloView stato : stati ){
											%>
											<option value="<%=stato.getVo().getCodGruppoLingua()%>">
											 <%= stato.getVo().getDescrizione()%>
											</option>
											<%	}
											} 
										}catch(Throwable e){
											e.printStackTrace();
										}%>
									</select> 
								</div>
							</div>

							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltri" name="singlebutton" type="button"
										onclick="cercaFascicoli()" data-dismiss="modal"
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
 	
 	<jsp:include page="/views/agenda/modalAgenda.jsp"></jsp:include>
 	
	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>
 
 	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/agenda.js"></script>  
	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/agendaSelezioneFascicolo.js"></script>
	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/agendaCalendarWidget.js"></script>
	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>

	<script type="text/javascript">
		initTabellaRicercaFascicoli();
	</script>
	 
	
</body>
</html>
