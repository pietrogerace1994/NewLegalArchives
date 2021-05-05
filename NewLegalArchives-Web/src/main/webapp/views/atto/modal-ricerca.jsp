<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!-- MODAL RICERCA -->

<div class="modal fade" id="modalRicercaAtto" tabindex="-1" role="dialog" aria-hidden="true">
	
	
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title"><spring:message text="??atto.label.modalMiglioraLaRicerca??" code="atto.label.modalMiglioraLaRicerca" /></h4>
			</div>
			<div class="modal-body">
		 
	 
			<input type="hidden" id="operazioneCorrente" name="operazioneCorrente" value="affinaricerca" >
					<fieldset>
					 
		<!-- Text input-->
		<div class="form-group col-md-12">
			<label class="col-md-2 control-label"
				for="legale_esterno"><spring:message text="??atto.label.numeroAtto??" code="atto.label.numeroAtto" /></label>
			<div class="col-md-10">
				<input id="numeroProtocollo" name="numeroProtocollo"
					type="text" placeholder="inserisci il Numero Atto"
					class="typeahead form-control input-md"> <span
					class="help-block"></span>
			</div>
		</div>
		<!-- Text input-->
		<div class="form-group col-md-12">
			<label class="col-md-2 control-label" for="controparte"><spring:message text="??atto.label.categoria??" code="atto.label.categoria" /></label>
			<div class="col-md-10">
			<select id="idCategoriaAtto" name="idCategoriaAtto" class="form-control">
				<option></option>
					<c:if test="${ listaCategorie != null }">
				<c:forEach items="${listaCategorie}" var="oggetto">
			<option value="${ oggetto.id }"><c:out value="${oggetto.nome}"></c:out></option>
				</c:forEach>
				</c:if>
				</select>
			</div>
		</div>
		<!-- Select Basic -->
		<div class="form-group col-md-12">
			<label class="col-md-2 control-label" for="selectbasic"><spring:message text="??atto.label.societa??" code="atto.label.societa" /></label>
			<div class="col-md-10">
				<select id="idSocieta" name="idSocieta" class="form-control">
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
		<div class="form-group col-md-12">
			<label class="col-md-2 control-label" for="selectbasic"><spring:message text="??atto.label.tipoAtto??" code="atto.label.tipoAtto" /></label>
			<div class="col-md-10">
				<input id="tipoAtto" name="tipoAtto" value="">
			</div>
		</div>

		<!-- DAL...AL -->
		<div class="form-group col-md-12">
			<label class="col-md-2 control-label" for="selectbasic">Dal</label>
			<div class="col-md-10">
				<input type='text'  id="dataDal" name="dataDal"
					class="form-control date-picker" value="">
			</div>
		</div>
					<div class="form-group col-md-12">
						<label class="col-md-2 control-label" for="selectbasic">Al</label>
						<div class="col-md-10">
							<input type='text' id="dataAl" name="dataAl"
								class="form-control date-picker" value="">
						</div>
					</div>
					<!-- Dal...AL -->

					<!-- ALTRI UFFICI -->
					<div class="form-group col-md-12">
						<label class="col-md-2 control-label" for="altriuffici"> <spring:message
								text="??atto.label.altriUffici??" code="atto.label.altriUffici" />
						</label>
						<div class="col-md-4">
							<select class="form-control" id="altriUffici">
								<option value="false">NO</option>
								<option value="true">SI</option>
							</select>
						</div>
					</div>



				</fieldset> 
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="initFiltraRicercaAtto()">Applica Filtri</button>
				<button type="button" class="btn btn-warning" data-dismiss="modal">Chiudi</button>
			</div>
			
	 
			</div>
			 
		</div>
	</div>
	
</div>


<!-- FINE MODAL RICERCA -->	
