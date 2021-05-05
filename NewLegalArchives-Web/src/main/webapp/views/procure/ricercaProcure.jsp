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
		
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						<div class="card">
							<div class="card-header">
								 	
									<h1>
									<spring:message text="??procure.label.ricercaProcure??" code="procure.label.ricercaProcure" />	 
									</h1>
								 	<p class="text-left" style="display:block">
										<spring:message text="??atto.label.ricercaCustomAttoStart??" code="atto.label.ricercaCustomAttoStart" /> 
										<a data-toggle="modal" href="#modalRicercaProcure" class="">
										   <spring:message text="??atto.label.ricercaCustomAttoEnd??" code="atto.label.ricercaCustomAttoEnd" />
										</a>
									</p>
							</div>
							
							<div class="card-body">
								<div id="sezioneMessaggiApplicativi"></div>
								<div class="table-responsive">
									<table id="data-table-procure" class="table table-striped table-responsive"></table>
								</div>
							
							</div>
							
							<div class="modal-footer">
								<div class="col-sm-11"></div>
								<div class="col-sm-1">
										<button id="creaFascicoloEIncarico" disabled
											onclick="creaFascicoloEIncarico()"
											class="btn palette-Green-SNAM bg waves-effect"
											style="float: right; margin-right: 40px; margin-top: 10px;">
											<spring:message text="??procure.label.creaFascicolo??" code="procure.label.creaFascicolo" /></button>

									</div>
							</div>
							
							
						</div>
						
					</div>
					
				</div>
				
			</div>
			
			
			<form id="openProcure" action="./visualizza.action" method="get" style="display:none">	
			<engsecurity:token regenerate="false"/>
				<input type="hidden" name="id" id="id" value="">
				<input type="hidden" name="azione" id="azione" value="">
				<input type="submit" value="" style="display:none">
			</form>
	   
	   </section>
		
	</section>

    
    <div class="modal fade" id="panelConfirmDeleteProcure" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??procure.label.confermaCancellazioneProcure??"
						code="procure.label.confermaCancellazioneProcure" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnEliminaProcure"></label>
						<div class="col-md-8">
							<button id="btnEliminaProcure" name="btnEliminaProcure" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
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
	
	
	<div class="modal fade" id="panelGestionePermessiProcure" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title"> 
						<spring:message text="??procure.label.estendiPermessi??"
							code="procure.label.estendiPermessi" />
					</h4>
				</div>
				<div class="modal-body" style="min-height: 200px; overflow: auto;height: 80%;">
					<div class="form-group">
						<div class="col-md-12">
							<table class="table table-striped table-responsive table-hover">
								<tr>
									<th style="width:60%"><spring:message text="??fascicolo.label.utente??"
											code="fascicolo.label.utente" /></th>
									<th><spring:message text="??fascicolo.label.permessoLettura??"
											code="fascicolo.label.permessoLettura" /></th>
									<th><spring:message text="??fascicolo.label.permessoScrittura??"
											code="fascicolo.label.permessoScrittura" /></th>
								</tr>
									
								<tbody id="tBodyPermessiProcure"> 							
								</tbody>

							</table>
						</div>
					</div>
					 
					<!-- Button -->
					<div class="form-group"> 
						<div class="col-md-8">
							<button id="btnEstendiPermessi" name="btnEstendiPermessi" type="button" data-dismiss="modal"
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
	
    <jsp:include page="/views/procure/modal-ricerca.jsp"></jsp:include>
    <jsp:include page="/views/procure/modal-associa.jsp"></jsp:include>
    
    <jsp:include page="/parts/script-end.jsp"></jsp:include>
	
	<script src="<%=request.getContextPath()%>/portal/js/controller/ricercaProcure.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/controller/procure.js"></script>


	<script type="text/javascript">
	
		$('#panelConfirmDeleteProcure').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnEliminaProcure").attr(
						'onclick',
						'eliminaProcure('
								+ $(e.relatedTarget).data('idprocure')
								+ ')');
			});
		
		$('#panelGestionePermessiProcure').on('show.bs.modal', function(e) {
			caricaGrigliaPermessiProcure($(e.relatedTarget).data('idprocure'));
		    $(this).find("#btnEstendiPermessi").attr('onclick', "estendiPermessiProcure("+ $(e.relatedTarget).data('idprocure') +")");
		});
		
	     initTabellaRicercaProcure();
	</script>
	
</body>
</html>
