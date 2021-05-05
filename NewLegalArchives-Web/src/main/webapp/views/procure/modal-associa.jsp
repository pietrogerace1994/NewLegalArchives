<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<div class="modal fade" id="modalAssociaFascicolo" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	      <div class="modal-header alert alert-warning">
	      	<h4 class="modal-title"> 
				Associa Procura a Fascicolo Esistente
			</h4>
	      </div>
	      <div class="modal-body"  style="min-height: 200px; overflow: auto;height: 30%;">
				<div class="form-group">
					<div class="col-md-12">
						<label for="associaFascicoli" class="col-sm-4 control-label" style="text-align:right;">
							<spring:message text="??procure.label.listaFascicoli??" code="procure.label.listaFascicoli" />
						</label>
						<div class="col-sm-8">
						
							<div class="media-body">
							 <div class="row">
						      <div class="col-sm-8" >
							      	<select name="fascicoliDaAssociare" id="fascicoliDaAssociare" class="form-control" >
						      </div>
						     </div>
						    </div>		
					
							<input type="hidden"  name="procuraId" id="procuraId" value="">	
						</div>
					</div>
				</div>
				
				<!-- Button -->
				<br><br>
				<div class="form-group"> 
					<div class="col-md-8">
						<button type="button" class="btn btn-primary" style="background-color:orange !important;" id="btnAgendaAdd" onclick="associaProcuraFascicolo()">
							<spring:message text="??procure.label.associa??" code="procure.label.associa" />
						</button>
				        <button type="button" class="btn btn-primary" id="btnAgendaClose" onclick="chiudiModale()">
							<spring:message text="??procure.label.chiudi??" code="procure.label.chiudi" />
						</button>						
					</div>
				</div>
	      </div>
	    </div>
    </div>
</div>

