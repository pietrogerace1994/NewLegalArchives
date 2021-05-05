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
									<spring:message text="??repertorioPoteri.label.ricercaRepertorioPoteri??" code="repertorioPoteri.label.ricercaRepertorioPoteri" />	 
									</h1>
								 	<p class="text-left" style="display:block">
										<spring:message text="??atto.label.ricercaCustomAttoStart??" code="atto.label.ricercaCustomAttoStart" /> 
										<a data-toggle="modal" href="#modalRicercaRepertorioPoteri" class="">
										   <spring:message text="??atto.label.ricercaCustomAttoEnd??" code="atto.label.ricercaCustomAttoEnd" />
										</a>
									</p>
							</div>
							
							<div class="card-body">
								<div id="sezioneMessaggiApplicativi"></div>
								<div class="table-responsive">
									<table id="data-table-repertoriopoteri" class="table table-striped table-responsive"></table>
								</div>
							
							</div>
							
						</div>
						
					</div>
					
				</div>
				
			</div>
			
			
			<form id="openRepertorioPoteri" action="./visualizza.action" method="get" style="display:none">	
			<engsecurity:token regenerate="false"/>
			<input type="hidden" name="id" id="id" value="">
			<input type="hidden" name="azione" id="azione" value="">
			<input type="submit" value="" style="display:none">
			
		</form>
			
	   
	   </section>
		
	   <jsp:include page="/views/repertorioPoteri/modal-ricerca.jsp"></jsp:include>
	   
	</section>

    <footer>
    <div class="modal fade" id="panelConfirmDeleteRepertorioPoteri" tabindex="-1"
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
					<spring:message text="??repertorioPoteri.label.confermaCancellazioneRepertorioPoteri??"
						code="repertorioPoteri.label.confermaCancellazioneRepertorioPoteri" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnEliminaRepertorioPoteri"></label>
						<div class="col-md-8">
							<button id="btnEliminaRepertorioPoteri" name="btnEliminaRepertorioPoteri" type="button"
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
    
    
	   <jsp:include page="/parts/footer.jsp"></jsp:include>
    </footer>
   
    <jsp:include page="/parts/script-end.jsp"></jsp:include>
	
	<script src="<%=request.getContextPath()%>/portal/js/controller/ricercaRepertorioPoteri.js"></script>
	<script src="<%=request.getContextPath()%>/portal/js/controller/repertorioPoteri.js"></script>

	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>
	

	<script type="text/javascript">
	
		$('#panelConfirmDeleteRepertorioPoteri').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnEliminaRepertorioPoteri").attr(
						'onclick',
						'eliminaRepertorioPoteri('
								+ $(e.relatedTarget).data('idrepertoriopoteri')
								+ ')');
			});
	     initTabellaRicercaRepertorioPoteri();
	</script>


</body>
</html>
