<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!--  MODAL ATTO MODIFICA --> 
	<div class="modal fade" id="modalUpdateAtto" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Registra Atto</h4>
				</div>
				<div class="modal-body">
					<form  action="./registraAtto.action" method="post" class="form-horizontal">
						<engsecurity:token regenerate="false"/>
						<fieldset>
					  <div class="col-md-12 column"><h3>Seleziona un Destinatario</h3></div>
						 	<!-- Text input-->
																																									<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="destinatario"><spring:message text="??atto.label.destinatario??" code="atto.label.destinatario" /></label>
									<div class="col-md-8">
										<select id="destinatario" name="destinatario"
											class="form-control isupdate">
											<option></option>
											<c:if test="${ listaDestinatario != null }">
											<c:forEach items="${listaDestinatario}" var="oggetto">
												<option value="${ oggetto.matricolaUtil }" >
													<c:out value="${oggetto.nominativoUtil}"></c:out>
												</option>
											</c:forEach>
											</c:if>
											
										</select>
									</div>
								</div>
						
								<!-- Text input-->
	

						</fieldset>
						
							 <div class="col-md-12 column">
     <div class="btn-group dropup pull-right ">
	<div class="btn-group pull-right space-to-left">
		<button id="save" type="submit"
			class="btn btn-primary dropdown-toggle"
			style="margin-left: 5px">
			<i class="fa fa-save"></i> Salva
		</button>
	</div>
	<!-- pulsante esporta senza opzioni -->
		<div class="btn-group pull-right">
			<button type="button" 
				class="btn btn-success" data-dismiss="modal">
				Annulla & Chiudi<i class="fa"></i>
			</button>
		 
		</div>
	</div>
</div>
						
						
						
					</form>
				</div>
				<div class="modal-footer">

					</div>
				</div>
			</div>
		</div>
<!-- FINE MODAL ATTO MODIFICA --> 		

