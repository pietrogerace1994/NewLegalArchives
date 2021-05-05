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
							<div class="card-header">
								 
									<h1>
									<spring:message text="??atto.label.ricercaAttoNonAssociatiAFascicolo??" code="atto.label.ricercaAttoNonAssociatiAFascicolo" />	 
									</h1>
								 	<p class="text-left" style="display:block">
										<spring:message text="??atto.label.ricercaCustomAttoStart??" code="atto.label.ricercaCustomAttoStart" /> 
										<a data-toggle="modal" href="#modalRicercaAtto" class="">
											<spring:message text="??atto.label.ricercaCustomAttoEnd??" code="atto.label.ricercaCustomAttoEnd" />
											</a>
									</p>
							</div>
							<div class="card-body">
							
							<!-- Area Table Result-->
							
							<div class="table-responsive">
 
								<table id="data-table-atto"
									class="table table-striped table-responsive">
									
								</table>
						
							</div>
							<!-- Table Result-->
							
							<div class="col-md-12 column">
								<div class="btn-group dropup pull-right"> 
									<!-- pulsante esporta senza opzioni -->
									<div class="btn-group pull-right space-to-left" style="display: none;">
										<button id="save-search" type="button"
											class="btn btn-primary dropdown-toggle"
											style="margin-left: 5px">
											<i class="fa fa-save"></i>
											<spring:message text="??atto.button.saveRicerca??" code="atto.button.saveRicerca" />
										</button>
									</div>
									<!-- pulsante esporta senza opzioni -->

									<!-- pulsante esporta con opzioni -->
									<div class="btn-group pull-right">
										<button type="button" data-toggle="dropdown"
											class="btn btn-success dropdown-toggle">
											<spring:message text="??atto.button.esportaRicerca??" code="atto.button.esportaRicerca" /> <i class="fa fa-arrow-circle-down"></i>
										</button>
										<ul class="dropdown-menu">
											<li><a href="javascript:void(0)" onclick="downloadListaAtti()"><spring:message text="??atto.button.excell??" code="atto.button.excell" /></a></li>
										</ul>
									</div>
									<!-- /pulsante esporta con opzioni ./ricerca.action?export=1-->
								</div>
							</div>
							<!-- Fine Area Table Result-->
							</div>
						</div><!--/ fine card -->
					</div>
				</div>
				
				
			</div>
			
			
<form id="openAtto" action="./visualizza.action" method="get" style="display:none">	
<engsecurity:token regenerate="false"/>
<input type="hidden" name="id" id="id" value="">
<input type="hidden" name="azione" id="azione" value="">
<input type="submit" value="" style="display:none">
</form>			
			
			<!--MODALFASCICOLO-->


<jsp:include page="/views/atto/modal-assegna-fascicolo.jsp"></jsp:include> 
			<!--/ fine col-1 -->
		</section>
<jsp:include page="/views/atto/modal-ricerca.jsp"></jsp:include> 
	</section>
 <jsp:include page="/views/atto/modal-atto-registra.jsp"></jsp:include>

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp"></jsp:include>


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
function stampaAtto(id){
	var url="<%=request.getContextPath()%>/atto/stampa.action?id="+id+"&azione=visualizza";
	url=legalSecurity.verifyToken(url);
	waitingDialog.show('Loading...');
	var ifr=$("#bodyDettaglioStampa").attr("src",url);
	ifr.load(function(){
	  	 waitingDialog.hide();
		$("#modalDettaglioStampa").modal("show");
})
}
</script>


<script src="<%=request.getContextPath()%>/portal/js/controller/fascicolo.js"></script>
<script src="<%=request.getContextPath()%>/portal/js/controller/atto.js?a=1"></script>

<script type="text/javascript">
     initTabellaRicercaAtto();
</script>

<script>

    var $table = $('#table-assegna-fascicolo');
    $(function () {
        $('#modal-assegna-fascicolo').on('shown.bs.modal', function () {
            $table.bootstrapTable('resetView');
        });
    });
   
</script>
</body>
</html>
