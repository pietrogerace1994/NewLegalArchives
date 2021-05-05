<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<div class="modal fade" id="modalRicercaRepertorioPoteri" tabindex="-1"
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
						<label for="codice" class="col-md-3 control-label">
							<spring:message text="??repertorioPoteri.label.codice??"
								code="repertorioPoteri.label.codice" />
						</label>
						<div class="col-md-9">
							<input id="codice" name="codice"
								type="text" class="form-control" value="">
						</div>
					</div>

					<div class="form-group col-md-12">
						<label for="descrizione" class="col-md-3 control-label">
							<spring:message text="??repertorioPoteri.label.descrizione??"
								code="repertorioPoteri.label.descrizione" />
						</label>
						<div class="col-md-9">
							<input id="descrizione" name="descrizione"
								type="text" class="form-control" value="">
						</div>
					</div>

					<div class="form-group col-md-12">
						<label for="testo" class="col-md-3 control-label">
							<spring:message text="??repertorioPoteri.label.testo??"
								code="repertorioPoteri.label.testo" />
						</label>
						<div class="col-md-9">
							<input id="testo" name="testo" type="text"
								class="form-control" value="">
						</div>
					</div>


					<div class="form-group col-md-12">
						<label for="idCategoria" class="col-sm-3 control-label"><spring:message
								text="??repertorioPoteri.label.idCategoria??"
								code="repertorioPoteri.label.idCategoria" /></label>
						<div class="col-sm-9">
							<select id="idCategoria" class="form-control">
								<option value="">
									<spring:message text="??repertorioPoteri.label.selezionaCategoria??"
										code="repertorioPoteri.label.selezionaCategoria" />
								</option>
								<c:if test="${ repertorioPoteriView.listaCategorie != null }">
									<c:forEach items="${repertorioPoteriView.listaCategorie}" var="oggetto">

										<option value="${ oggetto.id }">
											<c:out value="${oggetto.descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					
					<div class="form-group col-md-12">
						<label for="idSubcategoria" class="col-sm-3 control-label"><spring:message
								text="??repertorioPoteri.label.idSubcategoria??"
								code="repertorioPoteri.label.idSubcategoria" /></label>
						<div class="col-sm-9">
							<select id="idSubcategoria" class="form-control">
								<option value="">
									<spring:message text="??repertorioPoteri.label.selezionaSubCategoria??"
										code="repertorioPoteri.label.selezionaSubCategoria" />
								</option>
								<c:if test="${ repertorioPoteriView.listaSubCategorie != null }">
									<c:forEach items="${repertorioPoteriView.listaSubCategorie}" var="oggetto2">

										<option value="${ oggetto2.id }">
											<c:out value="${oggetto2.descrizione}"></c:out>
										</option>

									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>


				</fieldset>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="applyRepertorioPoteriSearchFilter()">
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

