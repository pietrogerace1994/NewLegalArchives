<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>


<div class="modal fade" id="modalRicercaAffariSocietari" tabindex="-1"
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
						<label for="denominazione" class="col-sm-2 control-label"><spring:message
								text="??affariSocietari.label.denominazione??"
								code="affariSocietari.label.denominazione" /></label>
						<div class="col-sm-10">
							<input id="denominazione" class="form-control" maxlength="200"
								id="txtDenominazione"
								value="${affariSocietariView.getVo().denominazione}" />
						</div>
					</div>


					<div class="form-group col-md-12">

						<label for="modelloDiGovernance" class="col-sm-2 control-label">
							<spring:message
								text="??affariSocietari.label.modelloDiGovernance??"
								code="affariSocietari.label.modelloDiGovernance" />
						</label>
						<div class="col-sm-10" id="divModelloDiGovernance">
							<label class="col-sm-2 control-label"> <spring:message
									text="??affariSocietari.label.tutti??"
									code="affariSocietari.label.tutti" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="modelloDiGovernance" id="modelloDiGovernance" value="">
							</div>
							<label class="col-sm-2 control-label"> <spring:message
									text="??affariSocietari.label.monistico??"
									code="affariSocietari.label.monistico" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="modelloDiGovernance" id="modelloDiGovernance" value="M">
							</div>

							<label class="col-sm-2 control-label"> <spring:message
									text="??affariSocietari.label.dualistico??"
									code="affariSocietari.label.dualistico" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="modelloDiGovernance" id="modelloDiGovernance" value="D">
							</div>

							<label class="col-sm-2 control-label"> <spring:message
									text="??affariSocietari.label.tradizionale??"
									code="affariSocietari.label.tradizionale" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="modelloDiGovernance" id="modelloDiGovernance" value="T">
							</div>


						</div>
					</div>


					<div class="form-group col-md-12"  id="divQuotazione">

						<label for="quotazione" class="col-sm-2 control-label"> <spring:message
								text="??affariSocietari.label.quotazione??"
								code="affariSocietari.label.quotazione" />
						</label>
						<div class="col-sm-10">
							<label class="col-sm-1 control-label"> <spring:message
									text="??affariSocietari.label.tutti??"
									code="affariSocietari.label.tutti" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="quotazione" id="quotazione" value="">
							</div>
							<label class="col-sm-1 control-label"> <spring:message
									text="??affariSocietari.label.si??"
									code="affariSocietari.label.si" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="quotazione" id="quotazione" value="SI">
							</div>

							<label class="col-sm-1 control-label"> <spring:message
									text="??affariSocietari.label.no??"
									code="affariSocietari.label.no" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="quotazione" id="quotazione" value="NO">
							</div>



						</div>
					</div>


					<div class="form-group col-md-12"  id="divStorico">

						<label for="storico" class="col-sm-2 control-label"> <spring:message
								text="??affariSocietari.label.storico??"
								code="affariSocietari.label.storico" />
						</label>
						<div class="col-sm-10">
							<label class="col-sm-1 control-label"> <spring:message
									text="??affariSocietari.label.si??"
									code="affariSocietari.label.si" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="storico" id="storico" value="SI">
							</div>
							
							<label class="col-sm-1 control-label"> <spring:message
									text="??affariSocietari.label.no??"
									code="affariSocietari.label.no" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="storico" id="storico" value="NO">
							</div>



						</div>
					</div>
					
					
					<div class="form-group col-md-12"  id="divGruppoSnam">

						<label for="gruppoSNAM" class="col-sm-2 control-label"> <spring:message
								text="??affariSocietari.label.gruppoSnam??"
								code="affariSocietari.label.gruppoSnam" />
						</label>
						<div class="col-sm-10">
							<label class="col-sm-1 control-label"> <spring:message
									text="??affariSocietari.label.si??"
									code="affariSocietari.label.si" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="gruppoSnam" id="gruppoSnam" value="SI">
							</div>
							<label class="col-sm-1 control-label"> <spring:message
									text="??affariSocietari.label.no??"
									code="affariSocietari.label.no" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="gruppoSnam" id="gruppoSnam" value="NO">
							</div>
						</div>
					</div>



					<div class="form-group col-md-12">
						<label for="idNazione"
							class="col-sm-2 control-label"><spring:message
								text="??affariSocietari.label.nazione??"
								code="affariSocietari.label.nazione" /></label>
						<div class="col-sm-10">
							<select id="idNazione" class="form-control">
								<option value="">
									<spring:message
										text="??affariSocietari.label.selezionaNazione??"
										code="affariSocietari.label.selezionaNazione" />
								</option>
								<c:if
									test="${ affariSocietariView.listaNazioni != null }">
									<c:forEach
										items="${affariSocietariView.listaNazioni}"
										var="oggetto_sl">

										<option value="${ oggetto_sl.getVo().id }">
											<c:out value="${oggetto_sl.getVo().descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
				</fieldset>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="applyAffariSocietariSearchFilter()">
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

