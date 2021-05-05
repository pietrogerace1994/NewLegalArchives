<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!-- INIZIO - MODAL ADD EVENTO TO CALENDARIO -->	
<div class="modal fade"  role="dialog" id="modalAgenda" style="overflow-y: auto;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <b class="modal-title" style="font-size:90%;">
			<spring:message text="??fascicolo.label.aggiungiEventoScadenza??" code="fascicolo.label.aggiungiEventoScadenza" />
		</b>
      </div>
      <div class="modal-body"  style="font-size: 90%;">
        
        <div class="alert alert-success hidden" id="errorMsgAgendaSuccess">
			<spring:message 
				text="??messaggio.operazione.ok??"
				code="messaggio.operazione.ok" />
		</div>
		
        
        <form name="agendaForm" id="agendaForm"  class="form-horizontal la-form">
        
        	<div class="alert alert-danger hidden" id="errorMsgAgendaDiv">
        		<div id="errorMsgAgendaTipologia"><spring:message text="??calendar.error.tipologia??" code="calendar.error.tipologia" /></div>
				<div id="errorMsgAgendaFascicolo"><spring:message text="??calendar.error.fascicolo??" code="calendar.error.fascicolo" /></div>
				
				<div id="errorMsgAgendaEventoData"><spring:message text="??calendar.error.data??" code="calendar.error.data" /></div>
				<div id="errorMsgAgendaEventoOggetto"><spring:message text="??calendar.error.oggetto??" code="calendar.error.oggetto" /></div>
				<div id="errorMsgAgendaEventoDescrizione"><spring:message text="??calendar.error.descrizione??" code="calendar.error.descrizione" /></div>
				<div id="errorMsgAgendaEventoDelayAvviso"><spring:message text="??calendar.error.delay??" code="calendar.error.delay" /></div>
				<div id="errorMsgAgendaEventoFrequenzaAvviso"><spring:message text="??calendar.error.frequenza??" code="calendar.error.frequenza" /></div>
				
				<div id="errorMsgAgendaScandenzaDataInserimento"><spring:message text="??calendar.error.dataInserimento??" code="calendar.error.dataInserimento" /></div>
				<div id="errorMsgAgendaScandenzaOggetto"><spring:message text="??calendar.error.oggetto??" code="calendar.error.oggetto" /></div>
				<div id="errorMsgAgendaScandenzaDescrizione"><spring:message text="??calendar.error.descrizione??" code="calendar.error.descrizione" /></div>
				<div id="errorMsgAgendaScandenzaTempoAdisposizione"><spring:message text="??calendar.error.tempoDisposizione??" code="calendar.error.tempoDisposizione" /></div>
				<div id="errorMsgAgendaScandenzaTipo"><spring:message text="??calendar.error.tipo??" code="calendar.error.tipo" /></div>
				<div id="errorMsgAgendaScandenzaDelayAvviso"><spring:message text="??calendar.error.delay??" code="calendar.error.delay" /></div>
				<div id="errorMsgAgendaScandenzaFrequenzaAvviso"><spring:message text="??calendar.error.frequenza??" code="calendar.error.frequenza" /></div>
				<div id="errorMsgAgendaScandenzaDataScandenza"><spring:message text="??calendar.error.dataScadenza??" code="calendar.error.dataScadenza" /></div>
			
			
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
							      	<input type="text" name="agendaFascicoliNome" id="agendaFascicoliNome" class="form-control" value="">
						      </div>
						      <div class="col-sm-3" >
						      		<button type="button" class="btn btn-primary waves-effect" id="btnAgendaScegliFascicolo">
						      			<i class="fa fa-search" id="btnAgendaScegliFascicoloIcon"></i>
						      		</button>						
						      </div>
						     </div>
						    </div>		
					
							<input
						    type="hidden" 
							name="agendaFascicoliId" id="agendaFascicoliId" value="">	
						</div>
					</div>
				</div>
			</div>
			
			<!-- TIPOLOGIA -->
			<div class="list-group-item media">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaTipologia" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.tipologia??" code="calendar.label.tipologia" />
						</label>
						<div class="col-sm-8">
							
					<select 
						size="1" 
						name="agendaTipologia" id="agendaTipologia"
						class="form-control">
							<option value="0"></option> 
							<option value="1">
								<spring:message text="??calendar.label.evento??" code="calendar.label.evento" />
							</option>
							<option value="2">
								<spring:message text="??calendar.label.scadenza??" code="calendar.label.scadenza" />
							</option>
					</select> 

						</div>
					</div>
				</div>
			</div>
			
			<!-- *** campi Evento *** -->
				
			<!-- Evento - DATA -->
			<div class="list-group-item media" id="agendaEventoDataDiv" style="display: none">
				<div class="media-body media-body-datetimepiker">
					<div class="form-group">
						<label for="agendaEventoData" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.data??" code="calendar.label.data" />
						</label>
						<div class="col-sm-8">
							
					<input  type="text"
						name="agendaEventoData" id="agendaEventoData"
						class="form-control date-picker">

						</div>
					</div>
				</div>
			</div>
			
			<!-- Evento - OGGETTO -->
			<div class="list-group-item media" id="agendaEventoOggettoDiv" style="display: none">
				<div class="media-body media-body-datetimepiker">
					<div class="form-group">
						<label for="agendaEventoOggetto" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.oggetto??" code="calendar.label.oggetto" />
						</label>
						<div class="col-sm-8">
							
					<input  type="text"
						name="agendaEventoOggetto" id="agendaEventoOggetto"
						class="form-control">

						</div>
					</div>
				</div>
			</div>
			
			<!-- Evento - DESCRIZIONE -->
			<div class="list-group-item media" id="agendaEventoDescrizioneDiv" style="display: none">
				<div class="media-body media-body-datetimepiker">
					<div class="form-group">
						<label for="agendaEventoDescrizione" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.descrizione??" code="calendar.label.descrizione" />
						</label>
						<div class="col-sm-8">
							
					<input  type="text"
						name="agendaEventoDescrizione" id="agendaEventoDescrizione"
						class="form-control">

						</div>
					</div>
				</div>
			</div>
			
			<!-- Evento - DELAY_AVVISO -->
			<div class="list-group-item media" id="agendaEventoDelayAvvisoDiv" style="display: none">
				<div class="media-body media-body-datetimepiker">
					<div class="form-group">
						<label for="agendaEventoDelayAvviso" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.delay??" code="calendar.label.delay" />
						</label>
						<div class="col-sm-8">
									
						
						<input type="text"
						name="agendaEventoDelayAvviso" id="agendaEventoDelayAvviso"
						class="form-control">
							 

						</div>
					</div>
				</div>
			</div>
			
			<!-- Evento - FREQUENZA_AVVISO -->
			<div class="list-group-item media" id="agendaEventoFrequenzaAvvisoDiv" style="display: none">
				<div class="media-body media-body-datetimepiker">
					<div class="form-group">
						<label for="agendaEventoFrequenzaAvviso" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.frequenza??" code="calendar.label.frequenza" />
						</label>
						<div class="col-sm-8">
							
					
						<select 
						size="1" 
						name="agendaEventoFrequenzaAvviso" id="agendaEventoFrequenzaAvviso"
						class="form-control">
							<option value="0"></option> 
							<option value="1">6</option>
							<option value="2">12</option>
							<option value="3">24</option>
						</select> 
						

						</div>
					</div>
				</div>
			</div>
			
			<!-- *** campi Scandenza *** -->
			
			<!-- Scandenza - DATA INSERIMENTO -->
			<div class="list-group-item media" id="agendaScandenzaDataInserimentoDiv" style="display: none">
				<div class="media-body media-body-datetimepiker">
					<div class="form-group">
						<label for="agendaScandenzaDataInserimento" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.dataDecorsoTermini??" code="calendar.label.dataDecorsoTermini" />
						</label>
						<div class="col-sm-8">
							
					<input  type="text"
						name="agendaScandenzaDataInserimento" id="agendaScandenzaDataInserimento"
						class="form-control date-picker">

						</div>
					</div>
				</div>
			</div>
			
			<!-- Scandenza - TEMPO A DISPOSIZIONE -->
			<div class="list-group-item media" id="agendaScandenzaTempoAdisposizioneDiv" style="display: none">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaScandenzaTempoAdisposizione" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.tempoDisposizione??" code="calendar.label.tempoDisposizione" />
						</label>
						<div class="col-sm-8">
							<input  type="text"
								name="agendaScandenzaTempoAdisposizione" id="agendaScandenzaTempoAdisposizione"
								class="form-control">

						</div>
					</div>
				</div>
			</div>
			
			<!-- Scandenza - TEMPO A DISPOSIZIONE PRIMA O DOPO -->
			<div class="list-group-item media" id="agendaScandenzaTempoAdisposizionePrimaDopoDiv" style="display: none">
				<div class="media-body">
					<div class="form-group">
						<div class="col-sm-6 control-label"></div>
						<label for="prima" class="col-sm-2 control-label">
							<spring:message text="??calendar.label.prima??" code="calendar.label.prima" />
						</label> 
						<div class="col-sm-1 control-label">
							<input type="radio" name="primadopo" value="0" onclick="selectRadioPrimaDopo(this.value)">
						</div>
						<label for="dopo" class="col-sm-2 control-label">
							<spring:message text="??calendar.label.dopo??" code="calendar.label.dopo" />
						</label>
						<div class="col-sm-1 control-label">
							<input type="radio" name="primadopo" value="1"  onclick="selectRadioPrimaDopo(this.value)">
						</div>
					</div>
				</div>
			</div>
			
			<!-- Scandenza - TIPO -->
			<div class="list-group-item media" id="agendaScandenzaTipoDiv" style="display: none">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaScandenzaTipo" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.tipo??" code="calendar.label.tipo" />
						</label>
						<div class="col-sm-8">
							
					<select 
						size="1" 
						name="agendaScandenzaTipo" id="agendaScandenzaTipo"
						class="form-control"></select>
						
						</div>
					</div>
				</div>
			</div>
			
			<!-- Scandenza - OGGETTO -->
			<div class="list-group-item media" id="agendaScandenzaOggettoDiv" style="display: none">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaScandenzaOggetto" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.oggetto??" code="calendar.label.oggetto" />
						</label>
						<div class="col-sm-8">
							
					<input  type="text"
						name="agendaScandenzaOggetto" id="agendaScandenzaOggetto"
						class="form-control">

						</div>
					</div>
				</div>
			</div>
			
			<!-- Scandenza - DESCRIZIONE -->
			<div class="list-group-item media" id="agendaScandenzaDescrizioneDiv" style="display: none">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaScandenzaDescrizione" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.descrizione??" code="calendar.label.descrizione" />
						</label>
						<div class="col-sm-8">
							
					<input  type="text"
						name="agendaScandenzaDescrizione" id="agendaScandenzaDescrizione"
						class="form-control">

						</div>
					</div>
				</div>
			</div>
			
			<!-- Scandenza - DELAY AVVISO -->
			<div class="list-group-item media" id="agendaScandenzaDelayAvvisoDiv" style="display: none">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaScandenzaDelayAvviso" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.delay??" code="calendar.label.delay" />
						</label>
						<div class="col-sm-8">
							
					<input  type="text"
						name="agendaScandenzaDelayAvviso" id="agendaScandenzaDelayAvviso"
						class="form-control">

						</div>
					</div>
				</div>
			</div>
			
			<!-- Scandenza - FREQUENZA AVVISO -->
			<div class="list-group-item media" id="agendaScandenzaFrequenzaAvvisoDiv" style="display: none">
				<div class="media-body">
					<div class="form-group">
						<label for="agendaScandenzaFrequenzaAvviso" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.frequenza??" code="calendar.label.frequenza" />
						</label>
						<div class="col-sm-8">
							
						<select 
						size="1" 
						name="agendaScandenzaFrequenzaAvviso" id="agendaScandenzaFrequenzaAvviso"
						class="form-control">
							<option value="0"></option> 
							<option value="1">6</option>
							<option value="2">12</option>
							<option value="3">24</option>
						</select> 

						</div>
					</div>
				</div>
			</div>
			
			<!-- Scandenza - DATA SCANDENZA -->
			<div class="list-group-item media" id="agendaScandenzaDataScandenzaDiv" style="display: none">
				<div class="media-body media-body-datetimepiker">
					<div class="form-group">
						<label for="agendaScandenzaDataScandenza" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.dataScadenza??" code="calendar.label.dataScadenza" />
						</label>
						<div class="col-sm-8">
							
					<input  type="text"
						name="agendaScandenzaDataScandenza" id="agendaScandenzaDataScandenza"
						class="form-control date-picker"
						readonly>

						</div>
					</div>
				</div>
			</div>
			
			<!-- Scandenza - TEMPO A DISPOSIZIONE PRIMA O DOPO -->
			<div class="list-group-item media" id="agendaScandenzaDataScandenzaModificaDiv" style="display: none">
				<div class="media-body">
					<div class="form-group">
						<div class="col-sm-7 control-label"></div>
						<label for="modificaDataScadenza" class="col-sm-4 control-label">
							<spring:message text="??calendar.label.modificaScadenza??" code="calendar.label.modificaScadenza" />
						</label> 
						<div class="col-sm-1 control-label">
							<input type="checkbox" id="agendaScandenzaDataScandenzaModifica" onclick="modificaDataScadenza()">
						</div>
					</div>
				</div>
			</div>
			
		</form>
        
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary" style="background-color:orange !important;" id="btnAgendaAdd">
			<spring:message text="??calendar.label.aggiungi??" code="calendar.label.aggiungi" />
		</button>
        <button type="button" class="btn btn-primary" id="btnAgendaClose">
			<spring:message text="??calendar.label.chiudi??" code="calendar.label.chiudi" />
		</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- FINE - MODAL ADD EVENTO TO CALENDARIO -->		


<div class="modal fade" id="agendaPanelCercaSelezionaPadreFascicolo" tabindex="-1"
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
					 <div id="containerRicercaModaleFascicoloPadre" class="col-md-16"></div>
				</div>
			</div>
		</div>
	</div>

    