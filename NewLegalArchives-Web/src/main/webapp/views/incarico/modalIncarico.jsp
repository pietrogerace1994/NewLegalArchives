<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!-- INIZIO - MODAL ADD EVENTO TO CALENDARIO -->	
<div class="modal fade"  role="dialog" id="modalIncarico" style="overflow-y: auto;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <b class="modal-title" style="font-size:90%;">
			<spring:message text="??calendar.label.selezionaFascicolo??" code="calendar.label.selezionaFascicolo" />
		</b>
      </div>
      <div class="modal-body"  style="font-size: 90%;">
        
        <div id="incaricoForm"  class="form-horizontal la-form">
        	
        	<div class="alert alert-danger hidden" id="errorMsgIncaricoDiv">
				<div id="errorMsgIncaricoFascicolo"><spring:message text="??calendar.error.fascicolo??" code="calendar.error.fascicolo" /></div>
			</div>
			<!-- LISTA FASCICOLI -->
			<div class="list-group-item media">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaFascicoli" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.listaFascicoli??" code="calendar.label.listaFascicoli" />
						</label>
						<div class="col-sm-8">
						
							<div class="media-body">
								<div class="row">
						        	<div class="col-sm-8" >
					  					<input type="text" name="incaricoFascicoliNome" id="incaricoFascicoliNome" class="form-control" value="">
				      		  		</div>
							        <div class="col-sm-3" >
							      		<button type="button" class="btn btn-primary waves-effect" id="btnIncaricoScegliFascicolo">
							      			<i class="fa fa-search" id="btnIncaricoScegliFascicoloIcon"></i>
							      		</button>						
							        </div>
				     			</div>
				    		</div>		
							<input type="hidden" name="incaricoFascicoliId" id="incaricoFascicoliId" value="">	
						</div>
					</div>
				</div>
			</div>
		</div>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary" style="background-color:orange !important;" id="btnIncaricoFascicoloSelect">
      		<spring:message text="??calendar.label.seleziona??" code="calendar.label.seleziona" />
      	</button>
        <button type="button" class="btn btn-primary" id="btn-fascicoloClose">
			<spring:message text="??calendar.label.chiudi??" code="calendar.label.chiudi" />
		</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- FINE - MODAL ADD EVENTO TO CALENDARIO -->		


<div class="modal fade" id="incaricoPanelCercaSelezionaPadreFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.cercaSelezionaFascicoli??"
							code="fascicolo.label.cercaSelezionaFascicoli" />
					</h4>
				</div>
				<div class="modal-body">
					 <div id="containerRicercaModaleIncaricoFascicoloPadre" class="col-md-16"></div>
				</div>
			</div>
		</div>
	</div>

    