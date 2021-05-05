<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!-- MODAL RICERCA -->

<c:if test="${ utenteNominativo != null }">
				<input type="hidden" id="utenteNominativo" value="${utenteNominativo}">
				<input type="hidden" id="legaleInterno" value="${utenteMatricola}">
</c:if>
		
<div class="modal fade" id="modalRicercaRiassegnaFascicolo" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title"><spring:message text="??atto.label.modalMiglioraLaRicerca??" code="atto.label.modalMiglioraLaRicerca" /></h4>
			</div>
						
<fieldset>
		<div class="modal-body">
	


		<div class="form-group">
		<c:if test="${ utenteNominativo == null }">	
			<label class="col-md-12 control-label" for="legaleInterno"><spring:message text="??assegna.label.legaleinterno??" code="assegna.label.legaleinterno" /> </label>
			<div class="col-md-12">
			<select id="legaleInterno" name="legaleInterno" class="form-control">
				<option></option>
					<c:if test="${ legaleInterno != null }">
				<c:forEach items="${legaleInterno}" var="oggetto">
			<option value="${ oggetto.matricolaUtil }"><c:out value="${oggetto.nominativoUtil}"></c:out></option>
				</c:forEach>
				</c:if>
				</select>
		</c:if>
			</div>
		</div>
		<!-- Select Basic -->
		<div class="form-group">
			<label class="col-md-12 control-label" for="nomeFascicolo"><spring:message text="??assegna.label.nomefascicolo??" code="assegna.label.nomefascicolo" /> </label>
			<div class="col-md-12">
				 <input id="nomeFascicolo" name="nomeFascicolo"
					type="text" placeholder="inserisci il Nome Fascicolo" class="typeahead form-control input-md"> 
					<span class="help-block"></span>
			</div>
		</div>
		<!-- Select Basic -->
		<!-- Select Basic -->
		<div class="form-group">
			<label class="col-md-12 control-label" for="tipologiaFascicolo"><spring:message text="??assegna.label.tipofascicolo??" code="assegna.label.tipofascicolo" /> </label>
			<div class="col-md-12">
				<select id="tipologiaFascicolo" name="tipologiaFascicolo" class="form-control">
					<option value=""></option>
					<c:if test="${ tipologiaFascicolo != null }">
					<c:forEach items="${tipologiaFascicolo}" var="oggetto">
					<option value="${ oggetto.id }"><c:out value="${oggetto.descrizione}"></c:out></option>
					</c:forEach>
					</c:if>
				</select>
			</div>
		</div>
		<!-- Select Basic -->
				<!-- Select Basic -->
		<div class="form-group">
			<label class="col-md-12 control-label" for="settoreGiuridico"><spring:message text="??assegna.label.settoregiuridico??" code="assegna.label.settoregiuridico" /> </label>
			<div class="col-md-12">
				<select id="settoreGiuridico" name="settoreGiuridico" class="form-control">
					<option value=""></option>
					<c:if test="${ settoreGiuridico != null }">
					<c:forEach items="${settoreGiuridico}" var="oggetto">
					<option value="${ oggetto.id }"><c:out value="${oggetto.nome}"></c:out></option>
					</c:forEach>
					</c:if>
				</select>
			</div>
		</div>
		<!-- Select Basic -->
		<!-- Select Basic -->
		<div class="form-group">
			<label class="col-md-12 control-label" for="selectbasic"><spring:message text="??assegna.label.societa??" code="assegna.label.societa" /></label>
			<div class="col-md-12">
				<select id="societa" name="societa" class="form-control">
					<option value=""></option>
					<c:if test="${ listaSocieta != null }">
					<c:forEach items="${listaSocieta}" var="oggetto">
					<option value="${ oggetto.id }"><c:out value="${oggetto.ragioneSociale}"></c:out></option>
					</c:forEach>
					</c:if>
				</select>
			</div>
		</div>
		<!-- Select Basic -->

		
		 

			</fieldset> 
			<div class="modal-footer">
				<button style="float:left" type="button" class="btn btn-primary waves-effect" onclick="pulisciCampi()"><spring:message text="??assegna.label.puliscicampi??" code="assegna.label.puliscicampi" /></button>
				<button type="button" class="btn btn-primary" onclick="filtraRicercaAssegnaFascicolo()"><spring:message text="??assegna.label.applicafiltri??" code="assegna.label.applicafiltri" /></button>
				<button type="button" class="btn btn-warning" data-dismiss="modal"><spring:message text="??assegna.label.chiudi??" code="assegna.label.chiudi" /></button>
			</div>
			
	 
			</div>
			 
		</div>
	</div>
</div>


<!-- FINE MODAL RICERCA -->	