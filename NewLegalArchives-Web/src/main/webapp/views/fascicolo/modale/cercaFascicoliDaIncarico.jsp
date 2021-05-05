<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<div id="col-1" class="col-lg-16 col-md-16 col-sm-16 col-sx-16">

	<div class="card">
		<div class="card-header">
			<p class="visible-lg visible-md visible-xs visible-sm text-left">
				<spring:message text="??fascicolo.label.nonHaiTrovatoCercavi??"
					code="fascicolo.label.nonHaiTrovatoCercavi" />
				<a data-toggle="collapse" href="#panelRicercaDaIncarico" class="" id="affinaRicercaLinkIncarico"> <spring:message
						text="??fascicolo.label.affinaRicerca??"
						code="fascicolo.label.affinaRicerca" />
				</a>
			</p>
			
			<!-- PANEL RICERCA MODALE -->
			<div class="collapse" id="panelRicercaDaIncarico" >
				<div>
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">
								<spring:message text="??fascicolo.label.miglioraRicerca??"
									code="fascicolo.label.miglioraRicerca" />
							</h4>
						</div>
						<div class="modal-body">
							<form class="form-horizontal">
								<fieldset>
									<div class="form-group">
										<label class="col-md-4 control-label" for="tipologiaFascicolo"><spring:message
												text="??fascicolo.label.tipologiaFascicolo??"
												code="fascicolo.label.tipologiaFascicolo" /></label>
										<div class="col-md-4">
											<select id="tipologiaFascicoloCodeModalIncarico" onchange="selezionaTipologiaFascicoloRicerca(this.value)"
												class="form-control">
												<c:if
													test="${ fascicoloRicercaView.listaTipologiaFascicolo != null }">
													<option value="">
														<c:out value=" - "></c:out>
													</option>
													<c:forEach
														items="${fascicoloRicercaView.listaTipologiaFascicolo}"
														var="oggetto">
														<option value="${ oggetto.vo.codGruppoLingua }">
															<c:out value="${oggetto.vo.descrizione}"></c:out>
														</option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label" for="settoreGiuridico"><spring:message
												text="??fascicolo.label.settoreGiuridico??"
												code="fascicolo.label.settoreGiuridico" /></label>
										<div class="col-md-4">
											<select id="settoreGiuridicoCodeModalIncarico" class="form-control">
												<option value="">
													<spring:message
														text="??fascicolo.label.selezionaSettoreGiuridico??"
														code="fascicolo.label.selezionaSettoreGiuridico" />
												</option>
												<c:if
													test="${ fascicoloRicercaView.listaSettoreGiuridico != null }">
													<c:forEach
														items="${fascicoloRicercaView.listaSettoreGiuridico}"
														var="oggetto">
														<option value="${ oggetto.vo.codGruppoLingua }">
															<c:out value="${oggetto.vo.nome}"></c:out>
														</option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
									<!-- DAL...AL -->
									<div class="form-group">
										<label class="col-md-4 control-label" for="selectbasic"><spring:message
												text="??fascicolo.label.dataDal??"
												code="fascicolo.label.dataDal" /></label>
										<div class="col-md-4">
											<input id="txtDataDalModalIncarico" type='text'
												class="form-control date-picker" placeholder="">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label" for="selectbasic"><spring:message
												text="??fascicolo.label.dataAl??" code="fascicolo.label.dataAl" /></label>
										<div class="col-md-4">
											<input id="txtDataAlModalIncarico" type='text'
												class="form-control date-picker" placeholder="">
										</div>
									</div>
									<!-- Dal...AL -->
									<!-- Text input-->
									<div class="form-group">
										<label class="col-md-4 control-label" for="legale_esterno">
											<spring:message text="??fascicolo.label.legaleEsterno??"
												code="fascicolo.label.legaleEsterno" />
										</label>
										<div class="col-md-4">
											<spring:message text="??fascicolo.label.inserisciNominativo??"
												var="insertNominativoLabel"
												code="fascicolo.label.inserisciNominativo" />
											<input id="txtLegaleEsternoModalIncarico" name="legale_esterno" type="text"
												placeholder="${insertNominativoLabel }"
												class="typeahead form-control input-md">
										</div>
									</div>
									<!-- Text input-->
									<div class="form-group">
										<label class="col-md-4 control-label" for="controparte"><spring:message
												text="??fascicolo.label.controparte??"
												code="fascicolo.label.controparte" /></label>
										<div class="col-md-4">
											<spring:message text="??fascicolo.label.inserisciControparte??"
												var="insertControparteLabel"
												code="fascicolo.label.inserisciControparte" />
											<input id="txtControparteModalIncarico" name="controparte" type="text"
												placeholder="${insertControparteLabel }"
												class="typeahead form-control input-md">
										</div>
									</div>

									<!-- Text input-->
									<div class="form-group">
										<label class="col-md-4 control-label" for="textinput"><spring:message
												text="??fascicolo.label.numeroFascicolo??"
												code="fascicolo.label.numeroFascicolo" /></label>
										<div class="col-md-4">

											<spring:message text="??fascicolo.label.inserisciNumero??"
												var="insertNumeroLabel"
												code="fascicolo.label.inserisciNumero" />
											<input id="txtNomeModalIncarico" name="textinput" type="text"
												placeholder="${insertNumeroLabel }"
												class="form-control input-md">
										</div>
									</div>

									<!-- Text input-->
									<div class="form-group">
										<label class="col-md-4 control-label" for="textinput"><spring:message
												text="??fascicolo.label.oggettoFascicolo??"
												code="fascicolo.label.oggettoFascicolo" /></label>
										<div class="col-md-4">

											<spring:message text="??fascicolo.label.inserisciOggetto??"
												var="insertOggettoLabel"
												code="fascicolo.label.inserisciOggetto" />
											<input id="txtOggettoModalIncarico" name="textinput" type="text"
												placeholder="${insertOggettoLabel }"
												class="form-control input-md">
										</div>
									</div>

									<!-- Button -->
									<div class="form-group">
										<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
										<div class="col-md-8">
											<button id="btnApplicaFiltriIncarico" name="singlebutton" type="button"
												onclick="cercaFascicoliDaIcarico();document.getElementById('affinaRicercaLinkIncarico').click(); "
												class="btn btn-primary">
												<spring:message text="??fascicolo.label.eseguiRicerca??"
													code="fascicolo.label.eseguiRicerca" />
											</button>
										</div>
									</div>

								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="card-body">
			<div id="sezioneMessaggiApplicativi"></div>
			<div class="table-responsive">
				<table id="tabellaRicercaFascicoliDaIncarico"
					class="table table-striped table-responsive">

				</table>
				
				<c:if test="${param.multiple eq 'incarico' }">
					<button id="btnSelezionaFascicoloPadreAgenda" name="btnSelezionaFascicoloPadreAgenda" type="button"
						onclick="incarico_selezionaFascicolo()" data-toggle="collapse"  data-target="#panelRicercaDaIncarico"
						class="btn btn-primary"
						style="background-color:orange !important; display: none; visibility: hidden;">
						Seleziona Fascicolo
					</button>
					<button id="btnIncaricoSelFascicoloChiudi" name="btnIncaricoSelFascicoloChiudi" type="button" onclick="incarico_selezionaFascicoloChiudi()" data-toggle="collapse" class="btn btn-primary" >Chiudi</button>
				</c:if>
				
			</div>
		</div>
	</div>
</div>



