<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.UtenteView"%>
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
								<spring:message text="??vendormanagement.valutazioni??" code="vendormanagement.valutazioni" />
								</h1> 
							</div>
							<div class="card-body">
								
								<table id="tabellaVotazioniIncarico"
												class="table table-striped table-responsive">
								</table>
								
							</div>
							
							<div class="modal-footer">
								
								<% UtenteView utenteConnesso = (UtenteView ) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
								if(!utenteConnesso.isGestoreVendor()){%>
								
								<div class="col-sm-9"></div>
								<div class="col-sm-1">
									<button id="votaIncarichi" disabled onclick="votazioniMultiple()"
										class="btn palette-Green-SNAM bg waves-effect">
										<spring:message text="??vendormanagement.vota??" code="vendormanagement.vota" />
									</button>
								</div>
								<div class="col-sm-2">
									<button id="confermaIncarichi" disabled data-target="#panelConfirmVotazioni" data-toggle="modal"
										class="btn palette-Green-SNAM bg waves-effect">
										<spring:message text="??vendormanagement.confermaVotazioni??" code="vendormanagement.confermaVotazioni" />
									</button>
								</div>
								<%} else{ %>
									<div class="col-sm-12"></div>
								<% } %>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>
	
	<!-- PANEL CONFIRM VOTAZIONE -->

	<div class="modal fade" id="panelConfirmVotazione" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??vendormanagement.confermaVotazioneDomanda??"
						code="vendormanagement.confermaVotazioneDomanda" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnConfermaVotazione"></label>
						<div class="col-md-8">
							<button id="btnConfermaVotazione" name="btnConfermaVotazione" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
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
	
	<!-- PANEL CONFIRM VOTAZIONI -->

	<div class="modal fade" id="panelConfirmVotazioni" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??vendormanagement.confermaVotazioniDomanda??"
						code="vendormanagement.confermaVotazioniDomanda" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnConfermaVotazione"></label>
						<div class="col-md-8">
							<button id="btnConfermaVotazioni" name="btnConfermaVotazioni" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
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
	
	<jsp:include page="/views/vendormanagement/modalSchedaValutazione.jsp"></jsp:include>    	
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>
	
	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	
	<script type="text/javascript">
		$('#panelConfirmVotazione').on('show.bs.modal', function(e) {
		    $(this).find("#btnConfermaVotazione").attr('onclick', "confermaVotazione("+ $(e.relatedTarget).data('idincarico') +")");
		});
	</script>
	
	<script type="text/javascript">
		$('#panelConfirmVotazioni').on('show.bs.modal', function(e) {
		    $(this).find("#btnConfermaVotazioni").attr('onclick', "confermaVotazioni()");
		});
	</script>

	<script charset="UTF-8" type="text/javascript">

		<c:if test="${ empty vendorManagementView.jsonArrayIncarichiAutorizzati }">
			var jsonArrayIncarichiAutorizzati = new Array();
		</c:if>
		<c:if test="${ not empty vendorManagementView.jsonArrayIncarichiAutorizzati }">
			var jsonArrayIncarichiAutorizzati = JSON.parse('${vendorManagementView.jsonArrayIncarichiAutorizzati}');
		</c:if>
		
		<c:if test="${ empty vendorManagementView.jsonArrayIncarichiAutorizzatiById }">
			var jsonArrayIncarichiAutorizzatiById = new Array();
		</c:if>
		<c:if test="${ not empty vendorManagementView.jsonArrayIncarichiAutorizzatiById }">
			var jsonArrayIncarichiAutorizzatiById = JSON.parse('${vendorManagementView.jsonArrayIncarichiAutorizzatiById}');
		</c:if>
		
	</script>

	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/vendormanagement/votazioni.js"></script>
	
	<script charset="UTF-8" type="text/javascript">
		votazioni_initTabellaIncarichiAutorizzati();
	</script>
	
	<script charset="UTF-8" type="text/javascript">
		var g_arrAssiPercentuale = new Array();
		var g_arrAssiDescrizione = new Array();
		var g_arrAssiDescrizioneShort = new Array();
		
		g_arrAssiPercentuale["AUTOREVOLEZZA"] = '<%= Costanti.ASSE_PERCENTUALE_AUTOREVOLEZZA %>';
		g_arrAssiPercentuale["CAPACITA"] = '<%= Costanti.ASSE_PERCENTUALE_CAPACITA %>' ;
		g_arrAssiPercentuale["COMPETENZA"] = '<%= Costanti.ASSE_PERCENTUALE_COMPETENZA %>' ;
		g_arrAssiPercentuale["COSTI"] = '<%= Costanti.ASSE_PERCENTUALE_COSTI %>';
		g_arrAssiPercentuale["FLESSIBILITA"] = '<%= Costanti.ASSE_PERCENTUALE_FLESSIBILITA  %>';
		g_arrAssiPercentuale["TEMPI"] = '<%= Costanti.ASSE_PERCENTUALE_TEMPI %>';
		g_arrAssiPercentuale["REPERIBILITA"] = '<%= Costanti.ASSE_PERCENTUALE_REPERIBILITA %>';
		
		g_arrAssiDescrizione["AUTOREVOLEZZA"] = '<%= Costanti.ASSE_DESCRIZIONE_AUTOREVOLEZZA %>';
		g_arrAssiDescrizione["CAPACITA"] = '<%= Costanti.ASSE_DESCRIZIONE_CAPACITA %>';
		g_arrAssiDescrizione["COMPETENZA"] = '<%= Costanti.ASSE_DESCRIZIONE_COMPETENZA %>';
		g_arrAssiDescrizione["COSTI"] = '<%= Costanti.ASSE_DESCRIZIONE_COSTI %>';
		g_arrAssiDescrizione["FLESSIBILITA"] = '<%= Costanti.ASSE_DESCRIZIONE_FLESSIBILITA %>';
		g_arrAssiDescrizione["TEMPI"] = '<%= Costanti.ASSE_DESCRIZIONE_TEMPI %>';
		g_arrAssiDescrizione["REPERIBILITA"] = '<%= Costanti.ASSE_DESCRIZIONE_REPERIBILITA %>';
		
		g_arrAssiDescrizioneShort["AUTOREVOLEZZA"] = '<%= Costanti.ASSE_DESCRIZIONE_SHORT_AUTOREVOLEZZA %>';
		g_arrAssiDescrizioneShort["CAPACITA"] = '<%= Costanti.ASSE_DESCRIZIONE_SHORT_CAPACITA %>';
		g_arrAssiDescrizioneShort["COMPETENZA"] = '<%= Costanti.ASSE_DESCRIZIONE_SHORT_COMPETENZA %>';
		g_arrAssiDescrizioneShort["COSTI"] = '<%= Costanti.ASSE_DESCRIZIONE_SHORT_COSTI %>';
		g_arrAssiDescrizioneShort["FLESSIBILITA"] = '<%= Costanti.ASSE_DESCRIZIONE_SHORT_FLESSIBILITA %>';
		g_arrAssiDescrizioneShort["TEMPI"] = '<%= Costanti.ASSE_DESCRIZIONE_SHORT_TEMPI %>';
		g_arrAssiDescrizioneShort["REPERIBILITA"] = '<%= Costanti.ASSE_DESCRIZIONE_SHORT_REPERIBILITA %>';
	
	</script>
 

</body>
</html>
