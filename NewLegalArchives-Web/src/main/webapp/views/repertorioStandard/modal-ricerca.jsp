<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<div class="modal fade" id="modalRicercaRepertorioStandard"
	tabindex="-1" role="dialog" aria-hidden="true">
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
						<label for="nome" class="col-sm-2 control-label"><spring:message
								text="??repertorioStandard.label.nome??"
								code="repertorioStandard.label.nome" /></label>
						<div class="col-sm-10">
							<input id="nome" class="form-control" maxlength="200"
								id="txtNome" value="${repertorioStandardView.getVo().nome}" />
						</div>
					</div>

					<div class="form-group col-md-12">
						<label for="nota" class="col-sm-2 control-label"><spring:message
								text="??repertorioStandard.label.nota??"
								code="repertorioStandard.label.nota" /></label>
						<div class="col-sm-10">
							<input id="nota" class="form-control" maxlength="1000"
								id="txtNota" value="${repertorioStandardView.getVo().nota}" />
						</div>
					</div>


					<div class="form-group col-md-12">
						<label for="idSocieta" class="col-sm-2 control-label"><spring:message
								text="??repertorioStandard.label.societa??"
								code="repertorioStandard.label.societa" /></label>
						<div class="col-sm-10">
							<select id="idSocieta" 
								class="form-control" name="societaSelezionata">
								<option value="">
									<spring:message
										text="??repertorioStandard.label.selezionaSocieta??"
										code="repertorioStandard.label.selezionaSocieta" />
								</option>
								<c:if test="${ repertorioStandardView.lstSocieta != null }">
									<c:forEach items="${repertorioStandardView.lstSocieta}"
										var="oggetto">

										<option value="${ oggetto }">
											<c:out value="${ oggetto }"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="form-group col-md-12">
						<label for="idPosizioneOrganizzativa"
							class="col-sm-2 control-label"><spring:message
								text="??repertorioStandard.label.posizioneOrganizzativa??"
								code="repertorioStandard.label.posizioneOrganizzativa" /></label>
						<div class="col-sm-10">
							<select id="idPosizioneOrganizzativa" 
								class="form-control">
								<option value="">
									<spring:message
										text="??repertorioStandard.label.selezionaPosizioneOrganizzativa??"
										code="repertorioStandard.label.selezionaPosizioneOrganizzativa" />
								</option>
								<c:if
									test="${ repertorioStandardView.listaPosizioneOrganizzativa != null }">
									<c:forEach
										items="${repertorioStandardView.listaPosizioneOrganizzativa}"
										var="oggetto_po">

										<option value="${ oggetto_po.id }">
											<c:out value="${oggetto_po.descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>



					<div class="form-group col-md-12">
						<label for="idPrimoLivelloAttribuzioni"
							class="col-sm-2 control-label"><spring:message
								text="??repertorioStandard.label.primoLivelloAttribuzioni??"
								code="repertorioStandard.label.primoLivelloAttribuzioni" /></label>
						<div class="col-sm-10">
							<select id="idPrimoLivelloAttribuzioni" 
								class="form-control">
								<option value="">
									<spring:message
										text="??repertorioStandard.label.selezionaPrimoLivelloAttribuzioni??"
										code="repertorioStandard.label.selezionaPrimoLivelloAttribuzioni" />
								</option>
								<c:if
									test="${ repertorioStandardView.listaPrimoLivelloAttribuzioni != null }">
									<c:forEach
										items="${repertorioStandardView.listaPrimoLivelloAttribuzioni}"
										var="oggetto_pl">

										<option value="${ oggetto_pl.id }">
											<c:out value="${oggetto_pl.descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="form-group col-md-12">
						<label for="idSecondoLivelloAttribuzioni"
							class="col-sm-2 control-label"><spring:message
								text="??repertorioStandard.label.secondoLivelloAttribuzioni??"
								code="repertorioStandard.label.secondoLivelloAttribuzioni" /></label>
						<div class="col-sm-10">
							<select id="idSecondoLivelloAttribuzioni" 
								class="form-control">
								<option value="">
									<spring:message
										text="??repertorioStandard.label.selezionaSecondoLivelloAttribuzioni??"
										code="repertorioStandard.label.selezionaSecondoLivelloAttribuzioni" />
								</option>
								<c:if
									test="${ repertorioStandardView.listaSecondoLivelloAttribuzioni != null }">
									<c:forEach
										items="${repertorioStandardView.listaSecondoLivelloAttribuzioni}"
										var="oggetto_sl">

										<option value="${ oggetto_sl.id }">
											<c:out value="${oggetto_sl.descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>



				</fieldset>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="applyRepertorioStandardSearchFilter()">
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

