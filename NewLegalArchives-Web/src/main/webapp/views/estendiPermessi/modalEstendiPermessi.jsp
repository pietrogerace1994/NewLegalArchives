<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<!-- MODAL ESTENSIONE PERMESSI -->

<div class="modal fade" id="gestionePermessiFascicolo" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header alert alert-warning">
				<h4 class="modal-title"> 
					<spring:message text="??fascicolo.label.estendiPermessi??"
						code="fascicolo.label.estendiPermessi" />
				</h4>
			</div>
			<div class="modal-body" style="min-height: 150px; overflow: auto;height: 20%;">
				<div class="form-group">
					<div class="col-md-12">
					
						<div class="col-md-1"></div>
						
						<div class="col-md-10">
							<div id="vuoiRimuovere" style="display:none">
								<h2 class="modal-title"> 
									<spring:message text="??estendiPermessi.label.vuoiRimuovere??" 
									code="estendiPermessi.label.vuoiRimuovere" />
								</h2>
							</div>
							
							<div id="vuoiAggiungere" style="display:none">
								<h2 class="modal-title"> 
									<spring:message text="??estendiPermessi.label.vuoiAggiungere??" 
									code="estendiPermessi.label.vuoiAggiungere" />
								</h2>
							</div>
						</div>
						<div class="col-md-1"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-12"><br><br></div>
				</div>	
				 
				<!-- Button -->
				<div class="form-group"> 
					<div class="col-md-5"></div>
					<div class="col-md-3">
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
					<div class="col-md-4"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- FINE MODAL ESTENSIONE PERMESSI -->	