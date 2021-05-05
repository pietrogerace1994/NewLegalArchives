<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.TipoCategDocumentaleView"%>
<%@page import="java.util.List"%>
<%@page import="eng.la.business.AnagraficaStatiTipiService"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<%
	try {
		AnagraficaStatiTipiService anagraficaStatiTipiService = (AnagraficaStatiTipiService) SpringUtil.getBean("anagraficaStatiTipiService");
		List<TipoCategDocumentaleView> listaCategoriaDocumentale = anagraficaStatiTipiService.leggiTipoCategDocumentale(request.getLocale().getLanguage().toUpperCase());
		request.getSession().setAttribute("listaCategoriaDocumentale",listaCategoriaDocumentale);
	}
catch (Throwable e) {
	e.printStackTrace();
}
%>
<!-- FASCICOLI -->

	<!-- PANEL CONFIRM DELETE -->

	<div class="modal fade" id="panelConfirmDeleteFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??fascicolo.label.confermaCancellazione??"
						code="fascicolo.label.confermaCancellazione" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
						<div class="col-md-8">
							<button id="btnElimina" name="btnElimina" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- PANEL CONFIRM DELETE FINE -->
	<!-- PANEL CONFIRM RICHIEDI CHIUSURA -->

	<div class="modal fade" id="panelConfirmRichiediChiusuraFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??fascicolo.label.confermaRichiestaChiusura??"
						code="fascicolo.label.confermaRichiestaChiusura" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnRichiediChiusura"></label>
						<div class="col-md-8">
							<button id="btnRichiediChiusura" name="btnRichiediChiusura" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- PANEL CONFIRM RICHIEDI CHIUSURA-->


	<!-- PANEL UPLOAD DOC -->

	<div class="modal fade" id="panelAggiungiDocFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.aggiungiDocumento??"
							code="fascicolo.label.aggiungiDocumento" />
					</h4>
				</div>
				<div class="modal-body">
					<form id="formAggiungiDocumento" method="post" enctype="application/x-www-form-urlencoded"  class="form-horizontal">
                        <engsecurity:token regenerate="false"/>
						<div class="form-group"> 
							<div class="col-md-8">
								<select id="categoriaDocumentaleCode" class="form-control">
									<option value="">
										<spring:message
											text="??fascicolo.label.selezionaCategoriaDocumentale??"
											code="fascicolo.label.selezionaCategoriaDocumentale" />
									</option>
									<c:if test="${ listaCategoriaDocumentale != null }">
										<c:forEach items="${listaCategoriaDocumentale}"
											var="oggetto">
											<option value="${ oggetto.vo.codGruppoLingua }">
												<c:out value="${oggetto.vo.nome}"></c:out>
											</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>
						<div class="form-group"> 
							<div class="col-md-8">
								<input type="file" name="file" id="file" />
							</div>
						</div>
						<!-- Button -->
						<div class="form-group"> 
							<div class="col-md-8">
								<button id="btnAggiungiDocumento" name="btnAggiungiDocumento" data-dismiss="modal"
									type="button" onclick="aggiungiDocumento()"
									class="btn btn-primary">
									<spring:message text="??fascicolo.label.ok??"
										code="fascicolo.label.ok" />
								</button>
								<button name="singlebutton" type="button" data-dismiss="modal"
									class="btn btn-warning">
									<spring:message text="??fascicolo.label.chiudi??"
										code="fascicolo.label.chiudi" />
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- PANEL UPLOAD DOC FINE -->
	<!-- PANEL CONFIRM COMPLETA FASICOLO -->

	<div class="modal fade" id="panelConfirmCompletaFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??fascicolo.label.confermaCompletaFascicolo??"
						code="fascicolo.label.confermaCompletaFascicolo" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnCompletaFascicolo"></label>
						<div class="col-md-8">
							<button id="btnCompletaFascicolo" name="btnCompletaFascicolo" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM RIPORTA IN COMPLETATO FASICOLO -->

	<div class="modal fade" id="panelConfirmRiportaInCompletatoFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??fascicolo.label.confermaRiportaInCompletatoFascicolo??"
						code="fascicolo.label.confermaRiportaInCompletatoFascicolo" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnRiportaInCompletatoFascicolo"></label>
						<div class="col-md-8">
							<button id="btnRiportaInCompletatoFascicolo" name="btnRiportaInCompletatoFascicolo" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- PANEL CONFIRM RIAPRI FASCICOLO -->

	<div class="modal fade" id="panelConfirmRiapriFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??fascicolo.label.confermaRiapriFascicolo??"
						code="fascicolo.label.confermaRiapriFascicolo" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnRiapriFascicolo"></label>
						<div class="col-md-8">
							<button id="btnRiapriFascicolo" name="btnRiapriFascicolo" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- PANEL CONFIRM ARCHIVIAZIONE -->

	<div class="modal fade" id="panelConfirmArchiviaFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title"> 
						<spring:message text="??fascicolo.label.archiviaFascicolo??"
							code="fascicolo.label.archiviaFascicolo" />
					</h4>
				</div>
				<div class="modal-body" style="min-height: 200px;">
					<div class="form-group">
						<label class="col-md-4 control-label" for="numeroArchivio"><spring:message text="??fascicolo.label.numeroArchivio??"
							code="fascicolo.label.numeroArchivio" /></label>
						<div class="col-md-8">
							<input id="txtNumeroArchivio" type='number'
								class="form-control" placeholder="">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="numeroArchivioContenitore"><spring:message text="??fascicolo.label.numeroArchivioContenitore??"
							code="fascicolo.label.numeroArchivioContenitore" /></label>
						<div class="col-md-8">
							<input id="txtNumeroArchivioContenitore" type="number"
								class="form-control" placeholder="">
						</div>
					</div>
					<!-- Button -->
					<div class="form-group"> 
						<div class="col-md-8">
							<button id="btnArchiviaFascicolo" name="btnArchiviaFascicolo" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> 
	
	<!-- PANEL GESTIONE PERMESSI-->

	<div class="modal fade" id="panelGestionePermessiFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title"> 
						<spring:message text="??fascicolo.label.estendiPermessi??"
							code="fascicolo.label.estendiPermessi" />
					</h4>
				</div>
				<div class="modal-body" style="min-height: 200px; overflow: auto;height: 80%;">
					<div class="form-group">
						<div class="col-md-12">
							<table class="table table-striped table-responsive table-hover">
								<tr>
									<th style="width:60%"><spring:message text="??fascicolo.label.utente??"
											code="fascicolo.label.utente" /></th>
									<th><spring:message text="??fascicolo.label.permessoLettura??"
											code="fascicolo.label.permessoLettura" /></th>
									<th><spring:message text="??fascicolo.label.permessoScrittura??"
											code="fascicolo.label.permessoScrittura" /></th>
								</tr>
									
								<tbody id="tBodyPermessi"> 							
								</tbody>

							</table>
						</div>
					</div>
					 
					<!-- Button -->
					<div class="form-group"> 
						<div class="col-md-8">
							<button id="btnEstendiPermessi" name="btnEstendiPermessi" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		<div class="modal fade" id="panelGestionePermessiProgetto" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title"> 
						<spring:message text="??fascicolo.label.estendiPermessi??"
							code="progetto.label.estendiPermessi" />
					</h4>
				</div>
				<div class="modal-body" style="min-height: 200px; overflow: auto;height: 80%;">
					<div class="form-group">
						<div class="col-md-12">
							<table class="table table-striped table-responsive table-hover">
								<tr>
									<th style="width:60%"><spring:message text="??fascicolo.label.utente??"
											code="fascicolo.label.utente" /></th>
									<th><spring:message text="??fascicolo.label.permessoLettura??"
											code="fascicolo.label.permessoLettura" /></th>
									<th><spring:message text="??fascicolo.label.permessoScrittura??"
											code="fascicolo.label.permessoScrittura" /></th>
								</tr>
									
								<tbody id="tBodyPermessiProgetto"> 							
								</tbody>

							</table>
						</div>
					</div>
					 
					<!-- Button -->
					<div class="form-group"> 
						<div class="col-md-8">
							<button id="btnEstendiPermessi" name="btnEstendiPermessi" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="panelGestioneAssociaFascicoloAProgetto" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title"> 
						<spring:message text="??fascicolo.label.associaAProgettoi??"
							code="fascicolo.label.associaAProgetto" />
					</h4>
				</div>
				<div class="modal-body" style="min-height: 200px; overflow: auto;height: 80%;">

				<div id="pageAssociaFascicoloAProgetto">
					</div>
					 <jsp:include page="/views/progetto/popup-modal-ricerca.jsp"></jsp:include>

				</div>
			</div>
		</div>
	</div>
	
	
	
	<div class="modal fade" id="panelCambiaOwnerFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title"> 
						<spring:message text="??fascicolo.label.cambiaOwner??"
							code="fascicolo.label.cambiaOwner" />
					</h4>
				</div>
				<div class="modal-body" style="min-height: 200px;">
					<div class="form-group">
						<label class="col-md-4 control-label" for="owner"><spring:message text="??fascicolo.label.ownerCorrente??"
							code="fascicolo.label.ownerCorrente" /></label>
						<div class="col-md-8">
							<input type='text' readonly="readonly"
								class="form-control" placeholder="" value="${sessionScope.currentSessionUtil.nominativo}" >
							<input id="txtOwner" type='hidden' readonly="readonly"
								class="form-control" placeholder="" value="${sessionScope.currentSessionUtil.userId}" >
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="nuovoOwner"><spring:message text="??fascicolo.label.nuovoOwner??"
							code="fascicolo.label.nuovoOwner" /></label>
						<div class="col-md-8">
							<select id="comboNuovoOwner" class="form-control" >
								<option value=""></option>
								
							</select>
						</div>
					</div>
					<!-- Button -->
					<div class="form-group"> 
						<div class="col-md-8">
							<button id="btnNuovoOwner" name="btnNuovoOwner" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> 
	
	<!-- PANEL GESTIONE PERMESSI-->

	<div class="modal fade" id="panelGestionePermessiBeautyContest" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title"> 
						<spring:message text="??fascicolo.label.estendiPermessi??"
							code="beautyContest.label.estendiPermessi" />
					</h4>
				</div>
				<div class="modal-body" style="min-height: 200px; overflow: auto;height: 80%;">
					<div class="form-group">
						<div class="col-md-12">
							<table class="table table-striped table-responsive table-hover">
								<tr>
									<th style="width:60%"><spring:message text="??beautyContest.label.utente??"
											code="beautyContest.label.utente" /></th>
									<th><spring:message text="??beautyContest.label.permessoLettura??"
											code="beautyContest.label.permessoLettura" /></th>
									<th><spring:message text="??beautyContest.label.permessoScrittura??"
											code="beautyContest.label.permessoScrittura" /></th>
								</tr>
									
								<tbody id="tBodyPermessiBc"> 							
								</tbody>

							</table>
						</div>
					</div>
					 
					<!-- Button -->
					<div class="form-group"> 
						<div class="col-md-8">
							<button id="btnEstendiPermessiBc" name="btnEstendiPermessiBc" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL Dettaglio BeautyContestReply-->

	<div class="modal fade" id="panelDettaglioRispostaBeautyContest" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title"> 
						<spring:message text="??beautyContest.label.dettaglioBeautyContestReply??"
							code="beautyContest.label.dettaglioBeautyContestReply" />
					</h4>
				</div>
				<div class="modal-body" style="min-height: 200px; overflow: auto;height: 70%;">
					
					<p id='containerBCR'></p>
					
				</div>
				<!-- Button -->
				<div class="modal-footer"> 
					<div class="col-md-12">
						<button name="singlebutton" type="button" data-dismiss="modal"
							class="btn btn-warning">
							<spring:message text="??fascicolo.label.chiudi??"
								code="fascicolo.label.chiudi" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/fascicolo.js"></script>
		
	<script
		src="<%=request.getContextPath()%>/portal/js/controller/incarico.js"></script>
		
	<script
		src="<%=request.getContextPath()%>/portal/js/controller/progetto.js"></script>

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/proforma.js"></script>
		
	<script
		src="<%=request.getContextPath()%>/portal/js/controller/rubrica.js"></script>
	
	<script
		src="<%=request.getContextPath()%>/portal/js/controller/schedaFondoRischi.js"></script>	
		
	<script
	src="<%=request.getContextPath()%>/portal/js/controller/udienza.js"></script>
	
	<script
	src="<%=request.getContextPath()%>/portal/js/controller/beautyContest.js"></script>
	
	<!-- DARIO ****************************************************************************************** -->
    <script	src="<%=request.getContextPath()%>/portal/js/controller/lista_assegnatari.js"></script>
	<!-- ************************************************************************************************ -->
	
	
	<script type="text/javascript">
		$('#panelConfirmDeleteFascicolo').on('show.bs.modal', function(e) {
		    $(this).find("#btnElimina").attr('onclick', 'eliminaFascicolo('+ $(e.relatedTarget).data('idfascicolo') +')');
		});
	</script>
	
	<script type="text/javascript">
		$('#panelConfirmRichiediChiusuraFascicolo').on('show.bs.modal', function(e) {
		    $(this).find("#btnRichiediChiusura").attr('onclick', 'richiediChiusuraFascicolo('+ $(e.relatedTarget).data('idfascicolo') +')');
		});
	</script>
	
	<script type="text/javascript">
		$('#panelAggiungiDocFascicolo').on('show.bs.modal', function(e) {
		    $(this).find("#btnAggiungiDocumento").attr('onclick', "aggiungiDocumento("+ $(e.relatedTarget).data('idfascicolo') +")");
		});
	</script>
	 
	<script type="text/javascript">
		$('#panelConfirmCompletaFascicolo').on('show.bs.modal', function(e) {
		    $(this).find("#btnCompletaFascicolo").attr('onclick', "completaFascicolo("+ $(e.relatedTarget).data('idfascicolo') +")");
		});
	</script>
	
	<script type="text/javascript">
		$('#panelConfirmRiportaInCompletatoFascicolo').on('show.bs.modal', function(e) {
		    $(this).find("#btnRiportaInCompletatoFascicolo").attr('onclick', "riportaInCompletatoFascicolo("+ $(e.relatedTarget).data('idfascicolo') +")");
		});
	</script>
	
	<script type="text/javascript">
		$('#panelConfirmRiapriFascicolo').on('show.bs.modal', function(e) {
		    $(this).find("#btnRiapriFascicolo").attr('onclick', "riapriFascicolo("+ $(e.relatedTarget).data('idfascicolo') +")");
		});
	</script>
	
	<script type="text/javascript">
		$('#panelConfirmArchiviaFascicolo').on('show.bs.modal', function(e) {
		    $(this).find("#btnArchiviaFascicolo").attr('onclick', "archiviaFascicolo("+ $(e.relatedTarget).data('idfascicolo') +")");
		});
	</script>
	
	<script type="text/javascript">
		$('#panelGestionePermessiFascicolo').on('show.bs.modal', function(e) {
			caricaGrigliaPermessiFascicolo($(e.relatedTarget).data('idfascicolo'));
		    $(this).find("#btnEstendiPermessi").attr('onclick', "estendiPermessiFascicolo("+ $(e.relatedTarget).data('idfascicolo') +")");
		});
	</script>
	
	<script type="text/javascript">
		$('#panelGestioneAssociaFascicoloAProgetto').on('show.bs.modal', function(e) {
			caricaPopupSelezioneProgetto($(e.relatedTarget).data('idfascicolo'),$(e.relatedTarget).data('nomeprogetto'));
		    $(this).find("#btnAssociaFascicoloAProgetto").attr('onclick', "associaFascicoloAProgetto("+ $(e.relatedTarget).data('idfascicolo') +")");
		});
	</script>
	
	<script type="text/javascript">
		$('#panelGestionePermessiProgetto').on('show.bs.modal', function(e) {
			caricaGrigliaPermessiProgetto($(e.relatedTarget).data('idprogetto'));
		    $(this).find("#btnEstendiPermessi").attr('onclick', "estendiPermessiProgetto("+ $(e.relatedTarget).data('idprogetto') +")");
		});
	</script>
	
	<script type="text/javascript">
		$('#panelCambiaOwnerFascicolo').on('show.bs.modal', function(e) {
			caricaComboOwnerRiassegnaFascicolo($(e.relatedTarget).data('idfascicolo'));
		    $(this).find("#btnNuovoOwner").attr('onclick', "cambiaOwnerFascicolo("+ $(e.relatedTarget).data('idfascicolo') +")");
		});
	</script>
	
	<script type="text/javascript">
		$('#panelGestionePermessiBeautyContest').on('show.bs.modal', function(e) {
			caricaGrigliaPermessiBeautyContest($(e.relatedTarget).data('idbc'));
		    $(this).find("#btnEstendiPermessiBc").attr('onclick', "estendiPermessiBeautyContest("+ $(e.relatedTarget).data('idbc') +")");
		});
	</script>
	
	<script type="text/javascript">
	$('#panelDettaglioRispostaBeautyContest').on('show.bs.modal', function(e) {
		caricaDettaglioBeautyContestReply($(e.relatedTarget).data('idbcr'));
		});
	</script>
	
	<!-- INCARICO -->
	
	<!-- PANEL CONFIRM DELETE -->

	<div class="modal fade" id="panelConfirmDeleteIncarico" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??incarico.label.confermaCancellazioneIncarico??"
						code="incarico.label.confermaCancellazioneIncarico" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnEliminaIncarico"></label>
						<div class="col-md-8">
							<button id="btnEliminaIncarico" name="btnEliminaIncarico" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM DELETE SCHEDA FONDO RISCHI-->

	<div class="modal fade" id="panelConfirmDeleteSchedaFondoRischi" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??schedaFondoRischi.label.confermaCancellazioneSchedaFondoRischi??"
						code="schedaFondoRischi.label.confermaCancellazioneSchedaFondoRischi" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnEliminaScheda"></label>
						<div class="col-md-8">
							<button id="btnEliminaScheda" name="btnEliminaScheda" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM DELETE BEAUTY CONTEST -->

	<div class="modal fade" id="panelConfirmDeleteBeautyContest" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??beautyContest.label.confermaCancellazioneBeautyContest??"
						code="beautyContest.label.confermaCancellazioneBeautyContest" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnEliminaBc"></label>
						<div class="col-md-8">
							<button id="btnEliminaBc" name="btnEliminaBc" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- PANEL CONFIRM DELETE UDIENZA -->

	<div class="modal fade" id="panelConfirmDeleteUdienza" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??udienza.label.confermaCancellazioneUdienza??"
						code="udienza.label.confermaCancellazioneUdienza" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnEliminaUdienza"></label>
						<div class="col-md-8">
							<button id="btnEliminaUdienza" name="btnEliminaUdienza" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal fade" id="panelConfirmDeleteProgetto" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??progetto.label.confermaCancellazioneProgetto??"
						code="progetto.label.confermaCancellazioneProgetto" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnEliminaProgetto"></label>
						<div class="col-md-8">
							<button id="btnEliminaProgetto" name="btnEliminaProgetto" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- PANEL CONFIRM DELETE FINE -->

	<!-- PANEL CONFIRM RICHIEDI AVVIO WF -->

	<div class="modal fade" id="panelConfirmAvviaWorkFlowIncarico" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<!-- DARIO*********************************************************** -->
						<div  name='lista_assegnatari'>
						</div>
						 <input name="wf_user_code_sel" value=""  style="display:none"/>
						 <input name="wf_code_sel" value=""  style="display:none"/>
					<!-- **************************************************************** -->
					<spring:message
						text="??incarico.label.confermaRichiestaAvvioWorkflow??"
						code="incarico.label.confermaRichiestaAvvioWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediAvvioWorkflowIncarico"></label>
						<div class="col-md-8">
							<!-- DARIO cambiato name da btnRichiediAvvioWorkflowIncarico a BTN_START_WORKFLOW********-->
							<button id="btnRichiediAvvioWorkflowIncarico"
								name="BTN_START_WORKFLOW" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM RICHIEDI AVVIO WF -->
	
	<div class="modal fade" id="panelConfirmAvviaWorkFlowSchedaFondoRischi" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<!-- DARIO*********************************************************** -->
						<div  name='lista_assegnatari'>
						</div>
						 <input name="wf_user_code_sel" value=""  style="display:none"/>
						 <input name="wf_code_sel" value=""  style="display:none"/>
					<!-- **************************************************************** -->
					<spring:message
						text="??schedaFondoRischi.label.confermaRichiestaAvvioWorkflow??"
						code="schedaFondoRischi.label.confermaRichiestaAvvioWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediAvvioWorkflowSchedaFondoRischi"></label>
						<div class="col-md-8">
							<!-- DARIO cambiato name da btnRichiediAvvioWorkflowSchedaFondoRischi a BTN_START_WORKFLOW********-->
							<button id="btnRichiediAvvioWorkflowSchedaFondoRischi"
								name="BTN_START_WORKFLOW" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM RICHIEDI AVVIO WF -->
	
	<div class="modal fade" id="panelConfirmAvviaWorkFlowBeautyContest" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<!-- DARIO*********************************************************** -->
						<div  name='lista_assegnatari'>
						</div>
						 <input name="wf_user_code_sel" value=""  style="display:none"/>
						 <input name="wf_code_sel" value=""  style="display:none"/>
					<!-- **************************************************************** -->
					<spring:message
						text="??beautyContest.label.confermaRichiestaAvvioWorkflow??"
						code="beautyContest.label.confermaRichiestaAvvioWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediAvvioWorkflowBeautyContest"></label>
						<div class="col-md-8">
							<!-- DARIO cambiato name da btnRichiediAvvioWorkflowBeautyContest a BTN_START_WORKFLOW********-->
							<button id="btnRichiediAvvioWorkflowBeautyContest"
								name="BTN_START_WORKFLOW" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
 

	<!-- PANEL CONFIRM DELETE Arbitrale -->

	<div class="modal fade" id="panelConfirmDeleteArbitrale" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??incarico.label.confermaCancellazioneArbitrale??"
						code="incarico.label.confermaCancellazioneArbitrale" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
						<div class="col-md-8">
							<button id="btnEliminaArbitrale" name="btnEliminaArbitrale" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- PANEL CONFIRM DELETE FINE -->

	<!-- PANEL CONFIRM RICHIEDI AVVIO WF Arbitrale -->
	
	<div class="modal fade" id="panelConfirmAvviaWorkFlowArbitrale" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<!-- DARIO*********************************************************** -->
						<div  name='lista_assegnatari'>
						</div>
						 <input name="wf_user_code_sel" value=""  style="display:none"/>
						 <input name="wf_code_sel" value=""  style="display:none"/>
					<!-- **************************************************************** -->
					<spring:message
						text="??incarico.label.confermaRichiestaAvvioWorkflow??"
						code="incarico.label.confermaRichiestaAvvioWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediAvvioWorkflowArbitrale"></label>
						<div class="col-md-8">
							<!-- DARIO cambiato name da btnRichiediAvvioWorkflowArbitrale a BTN_START_WORKFLOW********-->
							<button id="btnRichiediAvvioWorkflowArbitrale"
								name="BTN_START_WORKFLOW" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM RICHIEDI AVVIO WF -->
	
	<div class="modal fade" id="panelConfirmAvviaWorkFlowProforma" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<!-- DARIO*********************************************************** -->
					<div name='lista_assegnatari'>
					</div>
					 <input name="wf_user_code_sel" value=""  style="display:none"/>
					 <input name="wf_code_sel" value=""  style="display:none"/>
					<!-- **************************************************************** -->
					<spring:message
						text="??proforma.label.confermaRichiestaAvvioWorkflow??"
						code="proforma.label.confermaRichiestaAvvioWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediAvvioWorkflowProforma"></label>
						<div class="col-md-8">
							<!-- DARIO cambiato name da btnRichiediAvvioWorkflowProforma a BTN_START_WORKFLOW********-->
							<button id="btnRichiediAvvioWorkflowProforma"
								name="BTN_START_WORKFLOW" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM RICHIEDI RIPORTA BOZZA WF --> 
	<div class="modal fade" id="panelConfirmRiportaWorkFlowBozzaIncarico" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message
						text="??incarico.label.confermaRichiestaRiportaBozzaWorkflow??"
						code="incarico.label.confermaRichiestaRiportaBozzaWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediRiportaBozzaWorkflowIncarico"></label>
						<div class="col-md-8">
							<button id="btnRichiediRiportaBozzaWorkflowIncarico"
								name="btnRichiediRiportaBozzaWorkflowIncarico" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM RICHIEDI RIPORTA BOZZA WF --> 
	<div class="modal fade" id="panelConfirmRiportaWorkFlowBozzaSchedaFondoRischi" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message
						text="??schedaFondoRischi.label.confermaRichiestaRiportaBozzaWorkflow??"
						code="schedaFondoRischi.label.confermaRichiestaRiportaBozzaWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediRiportaBozzaWorkflowSchedaFondoRischi"></label>
						<div class="col-md-8">
							<button id="btnRichiediRiportaBozzaWorkflowSchedaFondoRischi"
								name="btnRichiediRiportaBozzaWorkflowSchedaFondoRischi" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM RICHIEDI RIPORTA BOZZA WF --> 
	<div class="modal fade" id="panelConfirmRiportaWorkFlowBozzaBeautyContest" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message
						text="??beautyContest.label.confermaRichiestaRiportaBozzaWorkflow??"
						code="beautyContest.label.confermaRichiestaRiportaBozzaWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediRiportaBozzaWorkflowBeautyContest"></label>
						<div class="col-md-8">
							<button id="btnRichiediRiportaBozzaWorkflowBeautyContest"
								name="btnRichiediRiportaBozzaWorkflowBeautyContest" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM RICHIEDI RIPORTA BOZZA WF ARBITRALE --> 
	<div class="modal fade" id="panelConfirmRiportaWorkFlowBozzaArbitrale" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message
						text="??incarico.label.confermaRichiestaRiportaBozzaWorkflow??"
						code="incarico.label.confermaRichiestaRiportaBozzaWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediRiportaBozzaWorkflow"></label>
						<div class="col-md-8">
							<button id="btnRichiediRiportaBozzaWorkflowArbitrale"
								name="btnRichiediRiportaBozzaWorkflowArbitrale" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM ARRETRA WF ARBITRALE --> 
	<div class="modal fade" id="panelConfirmArretraWorkFlowArbitrale" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message
						text="??incarico.label.confermaRichiestaArretraWorkflow??"
						code="incarico.label.confermaRichiestaArretraWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediRiportaBozzaWorkflow"></label>
						<div class="col-md-8">
							<button id="btnRichiediArretraWorkflowArbitrale"
								name="btnRichiediArretraWorkflowArbitrale" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> 

<!-- PANEL CONFIRM ARRETRA WF INCARICO --> 
	<div class="modal fade" id="panelConfirmArretraWorkFlowIncarico" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message
						text="??incarico.label.confermaRichiestaArretraWorkflow??"
						code="incarico.label.confermaRichiestaArretraWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediRiportaBozzaWorkflowIncarico"></label>
						<div class="col-md-8">
							<button id="btnRichiediArretraWorkflowIncarico"
								name="btnRichiediArretraWorkflowIncarico" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> 
	
	
	<!-- PANEL CONFIRM ARRETRA WF SCHEDA FONDO RISCHI --> 
	<div class="modal fade" id="panelConfirmArretraWorkFlowSchedaFondoRischi" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message
						text="??schedaFondoRischi.label.confermaRichiestaArretraWorkflow??"
						code="schedaFondoRischi.label.confermaRichiestaArretraWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediArretraWorkflowSchedaFondoRischi"></label>
						<div class="col-md-8">
							<button id="btnRichiediArretraWorkflowSchedaFondoRischi"
								name="btnRichiediArretraWorkflowSchedaFondoRischi" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM ARRETRA WF BEAUTY CONTEST --> 
	<div class="modal fade" id="panelConfirmArretraWorkFlowBeautyContest" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message
						text="??beautyContest.label.confermaRichiestaArretraWorkflow??"
						code="beautyContest.label.confermaRichiestaArretraWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediArretraWorkflowBeautyContest"></label>
						<div class="col-md-8">
							<button id="btnRichiediArretraWorkflowBeautyContest"
								name="btnRichiediArretraWorkflowBeautyContest" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$('#panelConfirmDeleteIncarico').on(
				'show.bs.modal',
				function(e) {
					$(this).find("#btnEliminaIncarico").attr(
							'onclick',
							'eliminaIncarico('
									+ $(e.relatedTarget).data('idincarico')
									+ ')');
				});
		
		$('#panelConfirmDeleteSchedaFondoRischi').on(
				'show.bs.modal',
				function(e) {
					$(this).find("#btnEliminaScheda").attr(
							'onclick',
							'eliminaScheda('
									+ $(e.relatedTarget).data('idscheda')
									+ ')');
				});
		
		$('#panelConfirmDeleteUdienza').on(
				'show.bs.modal',
				function(e) {
					$(this).find("#btnEliminaUdienza").attr(
							'onclick',
							'eliminaUdienza('
									+ $(e.relatedTarget).data('idudienza')
									+ ')');
				});
		
		$('#panelConfirmDeleteBeautyContest').on(
				'show.bs.modal',
				function(e) {
					$(this).find("#btnEliminaBc").attr(
							'onclick',
							'eliminaBc('
									+ $(e.relatedTarget).data('idbc')
									+ ')');
				});
		
		
		$('#panelConfirmDeleteProgetto').on(
				'show.bs.modal',
				function(e) {
					$(this).find("#btnEliminaProgetto").attr(
							'onclick',
							'eliminaProgetto('
									+ $(e.relatedTarget).data('idprogetto')
									+ ')');
				});
		
		
		$('#panelConfirmDeleteArbitrale').on(
				'show.bs.modal',
				function(e) {
					$(this).find("#btnEliminaArbitrale").attr(
							'onclick',
							'eliminaArbitrale('
									+ $(e.relatedTarget).data('idincarico')
									+ ')');
				});
	</script>

	<script type="text/javascript">
	//DARIO *******************************************************************************************
	/* $('#panelConfirmAvviaWorkFlowIncarico').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediAvvioWorkflowIncarico").attr(
						'onclick',
						'avviaWorkFlowIncarico('
								+ $(e.relatedTarget).data('idincarico')
								+ ')');
			}); */
	
	$('#panelConfirmAvviaWorkFlowIncarico').on(
			'show.bs.modal',
			function(e) {
				
				gestisci_tasto_confirm_workflow($(this), function(resp_code,flag_resp,code){
					
					avviaWorkFlowIncarico(code ,resp_code);
					
				});
				
				
				
			});		
			
			
	
	//**************************************************************************************************
	
	/* DARIO ********************************************************************* 
	$('#panelConfirmAvviaWorkFlowArbitrale').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediAvvioWorkflowArbitrale").attr(
						'onclick',
						'avviaWorkFlowIncaricoArbitrale('
								+ $(e.relatedTarget).data('idincarico')
								+ ')');
			}); */
	
	$('#panelConfirmAvviaWorkFlowArbitrale').on(
			'show.bs.modal',
			function(e) {
				
				gestisci_tasto_confirm_workflow($(this), function(resp_code,flag_resp,code){
					
					avviaWorkFlowIncaricoArbitrale(code , resp_code);
					
				});
				
				
				
			});	
	
	///******************************************************************************
	$('#panelConfirmRiportaWorkFlowBozzaIncarico').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediRiportaBozzaWorkflowIncarico").attr(
						'onclick',
						'riportaInBozzaWorkFlowIncarico('
								+ $(e.relatedTarget).data('idincarico')
								+ ')');
			});
	
	$('#panelConfirmRiportaWorkFlowBozzaArbitrale').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediRiportaBozzaWorkflowArbitrale").attr(
						'onclick',
						'riportaInBozzaWorkFlowIncaricoArbitrale('
								+ $(e.relatedTarget).data('idincarico')
								+ ')');
			});
	
	$('#panelConfirmArretraWorkFlowIncarico').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediArretraWorkflowIncarico").attr(
						'onclick',
						'arretraWorkFlowIncarico('
								+ $(e.relatedTarget).data('idincarico')
								+ ')');
			});
	
	$('#panelConfirmArretraWorkFlowArbitrale').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediArretraWorkflowArbitrale").attr(
						'onclick',
						'arretraWorkFlowIncaricoArbitrale('
								+ $(e.relatedTarget).data('idincarico')
								+ ')');
			});
	
	
	/* DARIO ************************************************************************ 
	$('#panelConfirmAvviaWorkFlowSchedaFondoRischi').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediAvvioWorkflowSchedaFondoRischi").attr(
						'onclick',
						'avviaWorkFlowSchedaFondoRischi('
								+ $(e.relatedTarget).data('idscheda')
								+ ')');
			}); */
	$('#panelConfirmAvviaWorkFlowSchedaFondoRischi').on(
			'show.bs.modal',
			function(e) {
				
				gestisci_tasto_confirm_workflow($(this), function(resp_code,flag_resp,code){
					
					avviaWorkFlowSchedaFondoRischi(code , resp_code);
					
				});
				
				
				
			});	
	//*********************************************************************************
	
	//DARIO *********************************************************************
	/* $('#panelConfirmAvviaWorkFlowBeautyContest').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediAvvioWorkflowBeautyContest").attr(
						'onclick',
						'avviaWorkFlowBeautyContest('
								+ $(e.relatedTarget).data('idbc')
								+ ')');
			}); */
	$('#panelConfirmAvviaWorkFlowBeautyContest').on(
			'show.bs.modal',
			function(e) {
				
				gestisci_tasto_confirm_workflow($(this), function(resp_code,flag_resp,code){
					
					avviaWorkFlowBeautyContest(code , resp_code);
					
				});
				
				
				
			});	
	
	//******************************************************************************
	
	//new script
	
	/* 
	DARIO *************************************************************** 
	$('#panelConfirmAvviaWorkFlowProforma').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediAvvioWorkflowProforma").attr(
						'onclick',
						'avviaWorkFlowProforma('
								+ $(e.relatedTarget).data('idproforma')
								+ ')');
			}); */
	
	$('#panelConfirmAvviaWorkFlowProforma').on(
			'show.bs.modal',
			function(e) {

				gestisci_tasto_confirm_workflow($(this), function(resp_code,flag_resp,code){
					
					avviaWorkFlowProforma(code , resp_code);
					
				});
					
				
				
			});	
	//******************************************************************************

	
	$('#panelConfirmRiportaWorkFlowBozzaSchedaFondoRischi').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediRiportaBozzaWorkflowSchedaFondoRischi").attr(
						'onclick',
						'riportaInBozzaWorkFlowSchedaFondoRischi('
								+ $(e.relatedTarget).data('idscheda')
								+ ')');
			});
	
	$('#panelConfirmArretraWorkFlowSchedaFondoRischi').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediArretraWorkflowSchedaFondoRischi").attr(
						'onclick',
						'arretraWorkFlowSchedaFondoRischi('
								+ $(e.relatedTarget).data('idscheda')
								+ ')');
			});
	
	$('#panelConfirmRiportaWorkFlowBozzaBeautyContest').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediRiportaBozzaWorkflowBeautyContest").attr(
						'onclick',
						'riportaInBozzaWorkFlowBeautyContest('
								+ $(e.relatedTarget).data('idbc')
								+ ')');
			});
	
	$('#panelConfirmArretraWorkFlowBeautyContest').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediArretraWorkflowBeautyContest").attr(
						'onclick',
						'arretraWorkFlowBeautyContest('
								+ $(e.relatedTarget).data('idbc')
								+ ')');
			});
	</script>
	


<!-- PROFORMA -->
<!-- PANEL CONFIRM DELETE -->

	<div class="modal fade" id="panelConfirmDeleteProforma" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??proforma.label.confermaCancellazione??"
						code="proforma.label.confermaCancellazione" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
						<div class="col-md-8">
							<button id="btnEliminaProforma" name="btnEliminaProforma" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- PANEL CONFIRM DELETE FINE -->
	 
	<!--  old panel -->    
	
	
	<!-- PANEL CONFIRM RICHIEDI RIPORTA BOZZA WF --> 
	<div class="modal fade" id="panelConfirmRiportaWorkFlowBozzaProforma" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message
						text="??proforma.label.confermaRichiestaRiportaBozzaWorkflow??"
						code="proforma.label.confermaRichiestaRiportaBozzaWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediRiportaBozzaWorkflowProforma"></label>
						<div class="col-md-8">
							<button id="btnRichiediRiportaBozzaWorkflowProforma"
								name="btnRichiediRiportaBozzaWorkflowProforma" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	  
<!-- PANEL CONFIRM ARRETRA WF PROFORMA --> 
	<div class="modal fade" id="panelConfirmArretraWorkFlowProforma" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message
						text="??proforma.label.confermaRichiestaArretraWorkflow??"
						code="proforma.label.confermaRichiestaArretraWorkflow" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label"
							for="btnRichiediRiportaBozzaWorkflowProforma"></label>
						<div class="col-md-8">
							<button id="btnRichiediArretraWorkflowProforma"
								name="btnRichiediArretraWorkflowProforma" type="button"
								data-dismiss="modal" onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> 
	
	<!-- RUBRICA -->
<!-- PANEL CONFIRM DELETE -->

	<div class="modal fade" id="panelConfirmDeleteRubrica" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??rubrica.label.confermaCancellazione??"
						code="rubrica.label.confermaCancellazione" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
						<div class="col-md-8">
							<button id="btnEliminaRubrica" name="btnEliminaRubrica" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM CATEGORIA -->

	<div class="modal fade" id="panelConfirmDeleteCategoria" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??categoria.label.confermaCancellazione??"
						code="categoria.label.confermaCancellazione" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
						<div class="col-md-8">
							<button id="btnEliminaCategoria" name="btnEliminaCategoria" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<!-- PANEL CONFIRM DELETE EMAIL-->

	<div class="modal fade" id="panelConfirmDeleteEmail" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??email.label.confermaCancellazione??"
						code="email.label.confermaCancellazione" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
						<div class="col-md-8">
							<button id="btnEliminaEmail" name="btnEliminaEmail" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL CONFIRM DELETE Newsletter-->

	<div class="modal fade" id="panelConfirmDeleteNewsletter" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??newsletter.label.confermaCancellazione??"
						code="newsletter.label.confermaCancellazione" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
						<div class="col-md-8">
							<button id="btnEliminaNewsletter" name="btnEliminaNewsletter" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL ATTIVA Newsletter-->

	<div class="modal fade" id="panelAttivaNewsletter" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.attenzione??"
							code="fascicolo.label.attenzione" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??newsletter.label.vuoiAttivare??"
						code="newsletter.label.vuoiAttivare" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
						<div class="col-md-8">
							<button id="btnAttivaNewsletter" name="btnAttivaNewsletter" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		<!-- PANEL INVIA ANTEPRIMA Newsletter-->

	<div class="modal fade" id="panelInviaAnteprimaNewsletter" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??newsletter.label.inviaanteprima??"
							code="newsletter.label.inviaanteprima" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??newsletter.label.vuoiInviaAnteprima??"
						code="newsletter.label.vuoiInviaAnteprima" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
						<div class="col-md-8">
							<button id="btnInviaAnteprimaNewsletter" name="btnInviaAnteprimaNewsletter" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
			<!-- PANEL INVIA  Newsletter-->

	<div class="modal fade" id="panelInviaNewsletter" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??newsletter.label.invia??"
							code="newsletter.label.invia" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??newsletter.label.vuoiInvia??"
						code="newsletter.label.vuoiInvia" />

					<!-- Button -->
					<div class="form-group">
						<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
						<div class="col-md-8">
							<button id="btnInviaNewsletter" name="btnInviaNewsletter" type="button" data-dismiss="modal"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL Anteprima Newsletter-->

	<div class="modal fade" id="panelAnteprimaNewsletter" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-info">
					<h4 class="modal-title">
						<spring:message text="??newsletter.label.anteprima??"
							code="newsletter.label.anteprima" />
					</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				</div>
				<div class="modal-body">
					<div id="contenutoAnteprima" style="height: 78%; overflow-y: auto;"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- PANEL INVIA  Sollecito Mail -->

	<div class="modal fade" id="panelInviaSollecito" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header alert alert-warning">
					<h4 class="modal-title">
						<spring:message text="??newsletter.label.invia??"
							code="newsletter.label.invia" />
					</h4>
				</div>
				<div class="modal-body">
					<spring:message text="??notificavalutazioni.label.vuoiinviare??"
						code="notificavalutazioni.label.vuoiinviare" />
					
					<br><br>
					
					<!-- EMAIL -->
					<div id="votazioniFormMailErrorDiv" class="alert alert-danger" style="display:none">
						<div id="errorMail">L'indirizzo mail non &egrave; corretto</div>
					</div>
					<div id="divEmail">
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="email" class="col-sm-3 control-label"><spring:message
											text="??notificavalutazioni.label.aggiungiUlterioriDestinatari??" code="notificavalutazioni.label.aggiungiUlterioriDestinatari" /></label>
									<div class="col-sm-4">
										<input id="email0" name="email[0]" class="form-control" type="text" value = ""/>
									</div>
									<button type="button" onclick="addEmail()" 
										class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float"
										style="margin-top:-10px !important;">
										<span class="glyphicon glyphicon-plus"></span>
 									</button>
								</div>
							</div>
						</div>
					</div>
					<br><br><br><br>
					<!-- Button -->
					<div class="form-group">
						<label class="col-md-5 control-label" for="btnInviaSollecito"></label>
						<div class="col-md-7">
							<button id="btnInviaSollecito" name="btnInviaSollecito" type="button"
								onclick="" class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning" onclick="svuotaListaMail();hideMailError();">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
					<br>
				</div>
			</div>
		</div>
	</div>

	<!-- PANEL CONFIRM DELETE DOC -->
	
	<div class="modal fade" id="panelDeleteDocFascicolo" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.eliminaDocumento??"
							code="fascicolo.label.eliminaDocumento" />
					</h4>
				</div>
				<div class="modal-body">
					<form id="formEliminaDocumento" method="post" enctype="application/x-www-form-urlencoded"  class="form-horizontal">
                      <engsecurity:token regenerate="false"/>
						<div class="form-group"> 
							<div class="col-md-8">
							 		<spring:message
											text="??fascicolo.label.confermaEliminaDocumentoFascicolo??"
											code="fascicolo.label.confermaEliminaDocumentoFascicolo" />
								 
							</div>
						</div>
						 
						<!-- Button -->
						<div class="form-group"> 
							<div class="col-md-8">
								<button id="btnEliminaDocumento" name="btnEliminaDocumento" data-dismiss="modal"
									type="button"  
									class="btn btn-primary">
									<spring:message text="??fascicolo.label.ok??"
										code="fascicolo.label.ok" />
								</button>
								<button name="singlebutton" type="button" data-dismiss="modal"
									class="btn btn-warning">
									<spring:message text="??fascicolo.label.chiudi??"
										code="fascicolo.label.chiudi" />
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		$('#panelConfirmDeleteProforma').on(
				'show.bs.modal',
				function(e) {
					$(this).find("#btnEliminaProforma").attr(
							'onclick',
							'eliminaProforma('
									+ $(e.relatedTarget).data('idproforma')
									+ ')');
				});
		
	 
	</script>

<script type="text/javascript">
	$('#panelConfirmDeleteNewsletter').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnEliminaNewsletter").attr(
						'onclick',
						'eliminaNewsletter(' + $(e.relatedTarget).data('idnewsletter')
								+ ')');
			});
	
	
	$('#panelAttivaNewsletter').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnAttivaNewsletter").attr(
						'onclick',
						'attivaNewsletter(' + $(e.relatedTarget).data('idnewsletter')
								+ ')');
			});
	
	
	
	$('#panelInviaAnteprimaNewsletter').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnInviaAnteprimaNewsletter").attr(
						'onclick',
						'inviaAnteprimaNewsletter(' + $(e.relatedTarget).data('idnewsletter')
								+ ')');
			});
	
	$('#panelInviaNewsletter').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnInviaNewsletter").attr(
						'onclick',
						'inviaNewsletter(' + $(e.relatedTarget).data('idnewsletter')
								+ ')');
			});
	
	$('#panelInviaSollecito').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnInviaSollecito").attr(
						'onclick',
						'sollecitaMail()');
			});
</script>

<script type="text/javascript">
	$('#panelConfirmDeleteEmail').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnEliminaEmail").attr(
						'onclick',
						'eliminaEmail('
								+ $(e.relatedTarget).data('idemail') + ')');
			});
	
///////////////////////////////////////////////////////////////////////////////	
	$('#panelAnteprimaNewsletter').on(
			'show.bs.modal',
			function(e) {
				var ajaxUtil = new AjaxUtil();
				var idnewsletter = $(e.relatedTarget).data('idnewsletter');
				waitingDialog.show();
				$("#contenutoAnteprima").html("");
				var callBackFn = function(data, stato) { 
					waitingDialog.hide();

					$("#contenutoAnteprima").html(data);
				};
				var callBackFnErr = function(data, stato) { 
					visualizzaMessaggio(data);
					waitingDialog.hide(); 
				};
				var url = WEBAPP_BASE_URL + "newsletter/anteprimaNewsletter.action?id=" + idnewsletter;
				ajaxUtil.ajax(url, "", "get", "text/html", callBackFn, null, callBackFnErr);
			});
</script>
<script type="text/javascript">
	$('#panelConfirmDeleteRubrica').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnEliminaRubrica").attr(
						'onclick',
						'eliminaRubrica('
								+ $(e.relatedTarget).data('idrubrica') + ')');
			});
</script>

<script type="text/javascript">
	$('#panelConfirmDeleteCategoria').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnEliminaCategoria").attr(
						'onclick',
						'eliminaCategoria('
								+ $(e.relatedTarget).data('idcategoria') + ')');
			});
</script>

<script type="text/javascript">
	
	//old script
		
	$('#panelConfirmRiportaWorkFlowBozzaProforma').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediRiportaBozzaWorkflowProforma").attr(
						'onclick',
						'riportaInBozzaWorkFlowProforma('
								+ $(e.relatedTarget).data('idproforma')
								+ ')');
			});
	 
	$('#panelConfirmArretraWorkFlowProforma').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnRichiediArretraWorkflowProforma").attr(
						'onclick',
						'arretraWorkFlowProforma('
								+ $(e.relatedTarget).data('idproforma')
								+ ')');
			});
	
	$('#panelDeleteDocFascicolo').on(
			'show.bs.modal',
			function(e) {
				$(this).find("#btnEliminaDocumento").attr(
						'onclick',
						'eliminaDocumento('+ $(e.relatedTarget).data('fascicoloid')+ ', "' + $(e.relatedTarget).data('uuid') + '")');
			});
	
	$('#btnAggiungiDocumento').prop('disabled', true);
	
	$('#categoriaDocumentaleCode').on('change', function() {
		if(this.options.selectedIndex!=0)
			$('#btnAggiungiDocumento').prop('disabled', false);
		else
			$('#btnAggiungiDocumento').prop('disabled', true);
		});
 
	</script>