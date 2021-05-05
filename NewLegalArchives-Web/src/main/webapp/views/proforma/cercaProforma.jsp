<%@page import="eng.la.model.view.StatoProformaView"%>
<%@page import="eng.la.model.view.SocietaView"%>
<%@page import="java.util.List"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.business.AnagraficaStatiTipiService"%>
<%@page import="eng.la.business.SocietaService"%>
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

<style type="text/css">
.fixed-table-loading {
    background: #fff url(../vendors/jquery/loading.gif)no-repeat 58% 0.7% !important;
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
									<spring:message text="??proforma.label.cercaProforma??"
										code="proforma.label.cercaProforma" />
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
								<div class="table-responsive">
									<table id="tabellaRicercaProforma"
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
									<spring:message text="??proforma.label.nomeProforma??"
										code="proforma.label.nomeProforma" />
								</label>
								<div class="col-md-4"> 
									<input id="txtNomeProforma" name="nomeProforma" type="text" 
										class="typeahead form-control input-md">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="nomeFascicolo">
									<spring:message text="??proforma.label.nomeFascicolo??"
										code="proforma.label.nomeFascicolo" />
								</label>
								<div class="col-md-4"> 
									<input id="txtNomeFascicolo" name="nomeFascicolo" type="text" 
										class="typeahead form-control input-md">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="nomeIncarico">
									<spring:message text="??proforma.label.nomeIncarico??"
										code="proforma.label.nomeIncarico" />
								</label>
								<div class="col-md-4"> 
									<input id="txtNomeIncarico" name="nomeIncarico" type="text" 
										class="typeahead form-control input-md">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-4 control-label" for="statoCode">
									<spring:message text="??proforma.label.statoCode??"
										code="proforma.label.statoCode" />
								</label>
								<div class="col-md-4"> 
									 <select class="form-control" id="comboStato">
										<option value="">
										</option>
										<%
										try{
											AnagraficaStatiTipiService service = (AnagraficaStatiTipiService) SpringUtil.getBean("anagraficaStatiTipiService");
											List<StatoProformaView> stati = service.leggiStatiProforma(request.getLocale().getLanguage().toUpperCase());
											if( stati != null ){
												for( StatoProformaView stato : stati ){
											%>
											<option value="<%=stato.getVo().getCodGruppoLingua()%>">
											 <%= stato.getVo().getDescrizione()%>
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
								<label class="col-md-4 control-label" for="societaAddebito">
									<spring:message text="??proforma.label.societaAddebito??"
										code="proforma.label.societaAddebito" />
								</label>
								<div class="col-md-4"> 
									 <select class="form-control" id="comboSocietaAddebito">
										<option value="-1">
										</option>
										<%
										try{
											SocietaService societaService = (SocietaService) SpringUtil.getBean("societaService");
											List<SocietaView> societaList = societaService.leggi(true);
											if( societaList != null ){
												for( SocietaView societa : societaList ){
											%>
											<option value="<%=societa.getVo().getId()%>">
											 <%= societa.getVo().getNome()%>
											</option>
											<%	}
											} 
										}catch(Throwable e){
											e.printStackTrace();
										}	%>
										
									</select> 
								</div>
							</div>
							
								<div class="form-group">
								<label class="col-md-4 control-label" for="fatturato">
									<spring:message text="??proforma.label.fattura??"
										code="proforma.label.fattura" />
								</label>
								<div class="col-md-4"> 
									 <select class="form-control" id="fatturato" onchange="chekContabillizata(this)">
										<option value=""></option>
										<option value="SI">SI</option>
									</select> 
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-4 control-label" for="contabilizzato">
									<spring:message text="??proforma.label.contabilizzata??"
										code="proforma.label.contabilizzata" /> 
								</label>
								<div class="col-md-4"> 
									 <select class="form-control" id="contabilizzato" disabled="disabled">
										<option value=""></option>
										<option value="SI">SI</option>
										<option value="NO">NO</option>
									</select> 
								</div>
							</div>
							
							
							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltri" name="singlebutton" type="button"
										onclick="cercaProforma()" data-dismiss="modal"
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
 

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include> 
 	
	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>
 	
	<script type="text/javascript">
		initTabellaRicercaProforma(); 
	</script>
	<script type="text/javascript">
	function chekContabillizata(obj){
		if(obj){
			if(obj.value=="NO" || obj.value==""){
			$("#contabilizzato").val("")
			$("#contabilizzato").attr("disabled","disabled");
			}else{
				$("#contabilizzato").removeAttr("disabled");	
			}
			
		}
	}
	</script>
	 
</body>
</html>