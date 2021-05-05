<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<div class="modal fade" id="modalRicercaOrganoSociale" tabindex="-1"
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
						<label for="idSocietaAffari" class="col-sm-3 control-label"><spring:message
								text="??organoSociale.label.idSocietaAffari??"
								code="organoSociale.label.idSocietaAffari" /></label>
						<div class="col-sm-9">
							<select id="idSocietaAffari" class="form-control">
								<option value="">
									<spring:message text="??organoSociale.label.selezionaSocietaAffari??"
										code="organoSociale.label.selezionaSocietaAffari" />
								</option>
								<c:if test="${ organoSocialeView.listaSocietaAffari != null }">
									<c:forEach items="${organoSocialeView.listaSocietaAffari}" var="oggetto2">

										<option value="${ oggetto2.id }">
											<c:out value="${oggetto2.descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label for="tipoOrganoSociale" class="col-sm-3 control-label"><spring:message
								text="??organoSociale.label.tipoOrganoSociale??"
								code="organoSociale.label.tipoOrganoSociale" /></label>
						<div class="col-sm-9">
							<select id="tipoOrganoSociale" class="form-control">
								<option value="">
									<spring:message text="??organoSociale.label.selezionatipoOrganoSociale??"
										code="organoSociale.label.selezionatipoOrganoSociale" />
								</option>
								<c:if test="${ organoSocialeView.listaOrganoSociale != null }">
									<c:forEach items="${organoSocialeView.listaOrganoSociale}" var="oggetto">

										<option value="${ oggetto.id }">
											<c:out value="${oggetto.descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label for="cognome" class="col-md-3 control-label">
							<spring:message text="??organoSociale.label.cognome??"
								code="organoSociale.label.cognome" />
						</label>
						<div class="col-md-9">
							<input id="cognome" name="cognome"
								type="text" class="form-control" value="">
						</div>
					</div>

					<div class="form-group col-md-12">
						<label for="nome" class="col-md-3 control-label">
							<spring:message text="??organoSociale.label.nome??"
								code="organoSociale.label.nome" />
						</label>
						<div class="col-md-9">
							<input id="nome" name="nome"
								type="text" class="form-control" value="">
						</div>
					</div>
					
					<div class="form-group col-md-12" id="divIncarica">

						<label for="incarica" class="col-sm-2 control-label"> <spring:message
								text="??affariSocietari.label.incarica??"
								code="affariSocietari.label.incarica" />
						</label>
						<div class="col-sm-10">
							<label class="col-sm-1 control-label"> <spring:message
									text="??affariSocietari.label.si??"
									code="affariSocietari.label.si" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="incarica" id="incarica" value="SI">
							</div>
							
							<label class="col-sm-1 control-label"> <spring:message
									text="??affariSocietari.label.no??"
									code="affariSocietari.label.no" />
							</label>
							<div class="col-sm-1">
								<input type="radio" name="incarica" id="incarica" value="NO">
							</div>

						</div>
					</div>

					<div class="form-group col-md-12"  id="DivGruppoSnam">

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




				</fieldset>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="applyOrganoSocialeSearchFilter()">
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

