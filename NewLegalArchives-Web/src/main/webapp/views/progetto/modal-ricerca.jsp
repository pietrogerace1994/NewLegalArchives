<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<div class="modal fade" id="modalRicercaProgetto" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		
			<div class="modal-header">
				<h4 class="modal-title">
				  <spring:message text="??generic.label.modalMiglioraLaRicerca??" code="generic.label.modalMiglioraLaRicerca" />
				</h4>
			</div>
			
			<div class="modal-body">
		 
				<fieldset>
					<div class="form-group col-md-12">
						<label for="dataAperturaDal" class="col-md-3 control-label">
							<spring:message text="??progetto.label.dataAperturaDal??"
								code="progetto.label.dataAperturaDal" />
						</label>
						<div class="col-md-9">
							<input id="dataAperturaDal" name="dataAperturaDal"
								type="text" class="form-control date-picker" value=""> 
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label for="dataAperturaAl" class="col-md-3 control-label">
							<spring:message text="??progetto.label.dataAperturaAl??"
								code="progetto.label.dataAperturaAl" />
						</label>
						<div class="col-md-9">
							<input id="dataAperturaAl" name="dataAperturaAl"
								type="text" class="form-control date-picker" value=""> 
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label for="dataChiusuraDal" class="col-md-3 control-label">
							<spring:message text="??progetto.label.dataChiusuraDal??"
								code="progetto.label.dataChiusuraDal" />
						</label>
						<div class="col-md-9">
							<input id="dataChiusuraDal" name="dataChiusuraDal"
								type="text" class="form-control date-picker" value=""> 
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label for="dataChiusuraAl" class="col-md-3 control-label">
							<spring:message text="??progetto.label.dataChiusuraAl??"
								code="progetto.label.dataChiusuraAl" />
						</label>
						<div class="col-md-9">
							<input id="dataChiusuraAl" name="dataChiusuraAl"
								type="text" class="form-control date-picker" value=""> 
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label class="col-md-3 control-label" for="oggetto">
						   <spring:message text="??progetto.label.oggetto??" code="progetto.label.oggetto" />
						</label>
						<div class="col-md-9">
							<input id="oggetto" name="oggetto"
								type="text" placeholder="inserisci l'oggetto"
								class="typeahead form-control input-md"> 
							<span class="help-block"></span>
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label class="col-md-3 control-label" for="nome">
						   <spring:message text="??progetto.label.nome??" code="progetto.label.nome" />
						</label>
						<div class="col-md-9">
							<input id="nome" name="nome"
								type="text" placeholder="inserisci il nome"
								class="typeahead form-control input-md"> 
							<span class="help-block"></span>
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label class="col-md-3 control-label" for="descrizione">
						   <spring:message text="??progetto.label.descrizione??" code="progetto.label.descrizione" />
						</label>
						<div class="col-md-9">
							<input id="descrizione" name="descrizione"
								type="text" placeholder="inserisci la descrizione"
								class="typeahead form-control input-md"> 
							<span class="help-block"></span>
						</div>
					</div>
					
			</fieldset> 
			
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="applyProjectSearchFilter()">
				    <spring:message text="??generic.label.applicaFiltri??" code="generic.label.applicaFiltri" />
				</button>
				
				<button type="button" class="btn btn-warning" data-dismiss="modal">
					<spring:message text="??generic.label.chiudi??" code="generic.label.chiudi" />
				</button>
			</div>
			
	 
		</div>
			 
	</div>
	
   </div>
   
</div>

