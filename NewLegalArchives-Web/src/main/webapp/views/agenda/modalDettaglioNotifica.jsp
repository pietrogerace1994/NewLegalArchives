<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<!-- INIZIO - MODAL DETTAGLIO NOTIFICA -->	
<div class="modal fade"  role="dialog" id="modalAgendaDettaglioNotifica" style="overflow-y: auto;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <b class="modal-title" style="font-size:90%;">
			<spring:message text="??calendar.label.dettaglioNotifica??" code="calendar.label.dettaglioNotifica" />
		</b>
      </div>
      <div class="modal-body"  style="font-size: 90%;">
        
        
        <form name="agendaDettaglioNotificaForm" 
             id="agendaDettaglioNotificaForm"  class="form-horizontal la-form">
        
        
        	<input type="hidden" name="agendaDettaglioId" id="agendaDettaglioId" >
        	<input type="hidden" name="agendaDettaglioTipologiaHidden" id="agendaDettaglioTipologiaHidden" >
			
			<!-- TIPOLOGIA -->
			<div class="list-group-item media">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaDettaglioTipologia" class="col-sm-2 control-label">
							<spring:message text="??calendar.label.tipologia??" code="calendar.label.tipologia" />
						</label>
						<div class="col-sm-10">
							
							<input type="text"
								name="agendaDettaglioTipologia" id="agendaDettaglioTipologia"
								class="form-control" disabled>

						</div>
					</div>
				</div>
			</div>
			
			<!-- DATA -->
			<div class="list-group-item media">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaDettaglioData" class="col-sm-2 control-label">
							<spring:message text="??calendar.label.data??" code="calendar.label.data" />
						</label>
						<div class="col-sm-10">
							
							<input type="text"
								name="agendaDettaglioData" id="agendaDettaglioData"
								class="form-control" disabled>

						</div>
					</div>
				</div>
			</div>
			
			
        	<!-- OGGETTO -->
			<div class="list-group-item media">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaDettaglioOggetto" class="col-sm-2 control-label">
							<spring:message text="??calendar.label.oggetto??" code="calendar.label.oggetto" />
						</label>
						<div class="col-sm-10">
							
							<input type="text"
								name="agendaDettaglioOggetto" id="agendaDettaglioOggetto"
								class="form-control" disabled>

						</div>
					</div>
				</div>
			</div>
			
			<!-- DESCRIZIONE -->
			<div class="list-group-item media">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaDettaglioDescrizione" class="col-sm-2 control-label">
							<spring:message text="??calendar.label.descrizione??" code="calendar.label.descrizione" />
						</label>
						<div class="col-sm-10">
							
							<input type="text"
								name="agendaDettaglioDescrizione" id="agendaDettaglioDescrizione"
								class="form-control" disabled>

						</div>
					</div>
				</div>
			</div>
			
			
			<!-- FASCICOLO -->
			<div class="list-group-item media">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaDettaglioFascicolo" class="col-sm-2 control-label">
							<spring:message text="??calendar.label.fascicolo??" code="calendar.label.fascicolo" />
						</label>
						<div class="col-sm-10">
							
							<input type="text"
								name="agendaDettaglioFascicoloNome" id="agendaDettaglioFascicoloNome"
								class="form-control" 
								disabled
								value=""
							>
							
							<input type="hidden"
								name="agendaDettaglioFascicoloId" id="agendaDettaglioFascicoloId"
								value=""
							>

						</div>
					</div>
				</div>
			</div>
			
		</form>
        
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary" style="background-color: red !important;" id="btnAgendaDettaglioCancella">
			<spring:message text="??calendar.label.cancella??" code="calendar.label.cancella" />
		</button>
      	<button type="button" class="btn btn-primary" style="background-color: orange !important;" id="btnAgendaDettaglioApriFascicolo">
			<spring:message text="??calendar.label.apriFascicolo??" code="calendar.label.apriFascicolo" />
		</button>
        <button type="button" class="btn btn-primary" id="btnAgendaDettaglioClose">
			<spring:message text="??calendar.label.chiudi??" code="calendar.label.chiudi" />
		</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- FINE - MODAL DETTAGLIO NOTIFICA -->	



    