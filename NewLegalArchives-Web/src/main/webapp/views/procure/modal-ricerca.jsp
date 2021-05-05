<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<div class="modal fade" id="modalRicercaProcure" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">

			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message text="??generic.label.modalMiglioraLaRicerca??"
						code="generic.label.modalMiglioraLaRicerca" />
				</h4>
			</div>

			<div class="modal-body">

				<fieldset>
					<div class="form-group col-md-12">
						<label for="dataConferimentoDal" class="col-md-3 control-label">
							<spring:message text="??procure.label.dataConferimentoDal??"
								code="procure.label.dataConferimentoDal" />
						</label>
						<div class="col-md-9">
							<input id="dataConferimentoDal" name="dataConferimentoDal"
								type="text" class="form-control date-picker" value="">
						</div>
					</div>

					<div class="form-group col-md-12">
						<label for="dataConferimentoAl" class="col-md-3 control-label">
							<spring:message text="??procure.label.dataConferimentoAl??"
								code="procure.label.dataConferimentoAl" />
						</label>
						<div class="col-md-9">
							<input id="dataConferimentoAl" name="dataConferimentoAl"
								type="text" class="form-control date-picker" value="">
						</div>
					</div>

					<div class="form-group col-md-12">
						<label for="dataRevocaDal" class="col-md-3 control-label">
							<spring:message text="??procure.label.dataRevocaDal??"
								code="procure.label.dataRevocaDal" />
						</label>
						<div class="col-md-9">
							<input id="dataRevocaDal" name="dataRevocaDal" type="text"
								class="form-control date-picker" value="">
						</div>
					</div>

					<div class="form-group col-md-12">
						<label for="dataRevocaAl" class="col-md-3 control-label">
							<spring:message text="??procure.label.dataRevocaAl??"
								code="procure.label.dataRevocaAl" />
						</label>
						<div class="col-md-9">
							<input id="dataRevocaAl" name="dataRevocaAl" type="text"
								class="form-control date-picker" value="">
						</div>
					</div>

					<div class="form-group col-md-12">
						<label class="col-md-3 control-label" for="numeroRepertorio">
							<spring:message text="??procure.label.numeroRepertorio??"
								code="procure.label.numeroRepertorio" />
						</label>
						<div class="col-md-9">
							<input id="numeroRepertorio" name="numeroRepertorio" type="text"
								placeholder="inserisci il Numero Repertorio"
								class="typeahead form-control input-md"> <span
								class="help-block"></span>
						</div>
					</div>

					<div class="form-group col-md-12">
						<label class="col-md-3 control-label" for="nomeProcuratore">
							<spring:message text="??procure.label.nomeProcuratore??"
								code="procure.label.nomeProcuratore" />
						</label>
						<div class="col-md-9">
							<input id="nomeProcuratore" name="nomeProcuratore" type="text"
								placeholder="inserisci il nomeProcuratore"
								class="typeahead form-control input-md"> <span
								class="help-block"></span>
						</div>
					</div>


					<div class="form-group col-md-12">
						<label for="tipologia" class="col-sm-3 control-label"><spring:message
								text="??procure.label.tipologia??"
								code="procure.label.tipologia" /></label>
						<div class="col-sm-9">
							<select id="tipologia" class="form-control">
								<option value="">
									<spring:message text="??procure.label.selezionaTipologia??"
										code="procure.label.selezionaTipologia" />
								</option>
								<c:if test="${ procureView.listaTipologie != null }">
									<c:forEach items="${procureView.listaTipologie}" var="oggetto3">

										<option value="${ oggetto3.vo.id }">
											<c:out value="${oggetto3.vo.descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label class="col-md-3 control-label" for="utente">
							<spring:message text="??procure.label.utente??"
								code="procure.label.utente" />
						</label>
						<div class="col-md-9">
							<input id="utente" name="utente" type="text"
								placeholder="inserisci il nome utente"
								class="typeahead form-control input-md"> <span
								class="help-block"></span>
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label for="idPosizioneOrganizzativa" class="col-sm-3 control-label"><spring:message
								text="??procure.label.posizioneOrganizzativa??"
								code="procure.label.posizioneOrganizzativa" /></label>
						<div class="col-sm-9">
							<select id="idPosizioneOrganizzativa" class="form-control">
								<option value="">
									<spring:message text="??procure.label.seleziona??"
										code="procure.label.seleziona" />
								</option>
								<c:if test="${ procureView.listaPosizioneOrganizzativa != null }">
									<c:forEach items="${procureView.listaPosizioneOrganizzativa}" var="oggetto">

										<option value="${ oggetto.vo.id }">
											<c:out value="${oggetto.vo.descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label for="idLivelloAttribuzioniI" class="col-sm-3 control-label"><spring:message
								text="??procure.label.livelloAttribuzioniI??"
								code="procure.label.livelloAttribuzioniI" /></label>
						<div class="col-sm-9">
							<select id="idLivelloAttribuzioniI" class="form-control">
								<option value="">
									<spring:message text="??procure.label.seleziona??"
										code="procure.label.seleziona" />
								</option>
								<c:if test="${ procureView.listaPosizioneOrganizzativa != null }">
									<c:forEach items="${procureView.listaPosizioneOrganizzativa}" var="oggetto">

										<option value="${ oggetto.vo.id }">
											<c:out value="${oggetto.vo.descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label for="idLivelloAttribuzioniII" class="col-sm-3 control-label"><spring:message
								text="??procure.label.livelloAttribuzioniII??"
								code="procure.label.livelloAttribuzioniII" /></label>
						<div class="col-sm-9">
							<select id="idLivelloAttribuzioniII" class="form-control">
								<option value="">
									<spring:message text="??procure.label.seleziona??"
										code="procure.label.seleziona" />
								</option>
								<c:if test="${ procureView.listaPosizioneOrganizzativa != null }">
									<c:forEach items="${procureView.listaPosizioneOrganizzativa}" var="oggetto">

										<option value="${ oggetto.vo.id }">
											<c:out value="${oggetto.vo.descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="form-group col-md-12">
						<label for="professionista" class="col-sm-3 control-label"><spring:message
								text="??procure.label.professionista??"
								code="incarico.label.professionista" /></label>
						<div class="col-sm-9">
							<select id="idNotaio" class="form-control">
								<option value="">
									<spring:message
										text="??procure.label.selezionaProfessionista??"
										code="incarico.label.selezionaProfessionista" />
								</option>
								<c:if test="${ procureView.listaProfessionista != null }">
									<c:forEach items="${procureView.listaProfessionista}"
										var="oggetto">
										<c:if
											test="${oggetto.vo.statoEsitoValutazioneProf.codGruppoLingua eq 'TEVP_1'}">
											<option value="${ oggetto.vo.id }">
												<c:out
													value="${oggetto.vo.studioLegale.denominazione} ${oggetto.cognomeNome}"></c:out>
											</option>
										</c:if>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="form-group col-md-12">
						<label for="societaAppartenenza" class="col-sm-3 control-label"><spring:message
								text="??procure.label.societaAppartenenza??"
								code="procure.label.societaAppartenenza" /></label>
						<div class="col-sm-9">
							<select id="idSocieta" class="form-control">
								<option value="">
									<spring:message text="??procure.label.selezionaSocieta??"
										code="procure.label.selezionaSocieta" />
								</option>
								<c:if test="${ procureView.listaSocieta != null }">
									<c:forEach items="${procureView.listaSocieta}" var="oggetto2">

										<option value="${ oggetto2.vo.id }">
											<c:out value="${oggetto2.vo.nome}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>


				</fieldset>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="applyProcureSearchFilter()">
						<spring:message text="??generic.label.applicaFiltri??"
							code="generic.label.applicaFiltri" />
					</button>

					<button type="button" class="btn btn-warning" data-dismiss="modal">
						<spring:message text="??generic.label.chiudi??"
							code="generic.label.chiudi" />
					</button>
				</div>


			</div>

		</div>

	</div>

</div>

