<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<spring:message  text="??incarico.label.percentualeSuccessoBonus??" var="percentualeSuccessoBonus"
	 code="incarico.label.percentualeSuccessoBonus" />

<input type="hidden" id="dis" value="${incaricoView.disabled}">
						
	<!--Luogo data-->
<br>
	<div class="list-group-item media">
		<div class="media-body">
			<div class="col-md-6" style="
    padding-left: 0px;
">
				<label for="data">San Donato Milanese, <c:out
						value="${incaricoView.dataProtocollo}"></c:out></label><br> <label
					for="unita"><c:out
						value="${incaricoView.unitaOrganizzativa}"></c:out></label> -
			Prot.
			<form:input path="protocollo" cssClass="form-control disabilitaaut"
				style="
    width: 100px;
    display: initial;
"   /> 
		</div>
			<div class="col-md-6" style="padding-left: 0px;">
			Egregio/Gentile<br>
				<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_4'}">
			Dott./Dott.ssa	
				</c:if>
				<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua ne 'TFSC_4'}">
			Avv.	
				</c:if>
			<c:out value="${incaricoView.professionistaSelezionato.vo.nome}"></c:out> <c:out value="${incaricoView.professionistaSelezionato.vo.cognome}"></c:out><br>
			<c:out value="${incaricoView.professionistaSelezionato.vo.studioLegale.denominazione}"></c:out> 
			<c:out value="${incaricoView.professionistaSelezionato.vo.studioLegale.indirizzo}" ></c:out> 
			<c:out value="${incaricoView.professionistaSelezionato.vo.studioLegale.cap}" ></c:out>
			<c:out value="${incaricoView.professionistaSelezionato.vo.studioLegale.citta}" ></c:out>
			</div>
		</div>
	</div>

<!-- OGGETTO PROTOCOLLO-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="oggetto" class="col-sm-2 control-label" style="font-weight: 800;text-align: left;width: 70px;">Oggetto: </label>
			<div class="col-sm-10">
				<form:input path="oggettoProtocollo" cssClass="form-control disabilitaaut" />
			</div>
		</div>
	</div>
</div>

<!-- -->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<div class="col-sm-10">
			Egregio/Gentile 
				<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_4'}">
			Dott./Dott.ssa	
				</c:if>
				<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua ne 'TFSC_4'}">
			Avvocato	
				</c:if>
			</div>
		</div>
	</div>
</div>

<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<div class="col-sm-10">
				<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_2' || 
							  incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_4' ||
							  incaricoView.fascicoloRiferimento.vo.settoreGiuridico.codGruppoLingua eq 'TSTT_2'
				}">
			facendo seguito alle conversazioni e intese intercorse,<br><br>	
				</c:if>
				
				E' presente accordo quadro?<form:checkbox path="isQuadro" value="" onclick="addQuadro();" id="quadroC" cssClass="disabilitaaut"/>
				
				<div id="quadro" style="display: none;">
				<br>con espresso richiamo all&rsquo;accordo quadro con Lei sottoscritto n.<form:input path='numQuadro' cssClass='form-control disabilitaaut' style='width: 100px;display: initial;' />
				</div>
				<br>si formalizzano con la presente i termini e le modalit&agrave;
				che dovranno essere osservate nell&rsquo;espletamento
				dell&rsquo;incarico citato nell&rsquo;oggetto conferitoLe
				nell&rsquo;interesse di<br>

				<c:forEach items="${incaricoView.listaSocietaAddebitoAggiunteDesc}"
					var="descrizione">
					${descrizione}<br>
				</c:forEach>
				<br>
				<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_2' ||
							  incaricoView.fascicoloRiferimento.vo.settoreGiuridico.codGruppoLingua eq 'TSTT_2'
				}">
				Attivit&agrave; riguardanti il presente incarico:<br><br>
				
				<div class="col-sm-10">
					<form:textarea path="attivita" cssClass="form-control disabilitaaut" id="attivita" />
				</div>
				</c:if>
			</div>
		</div>
	</div>
</div>

<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<div class="col-sm-10">
			<ol class="rightParentesis">
				<li>Ogni conflitto di interesse, anche potenziale, dovr&agrave;
						essere immediatamente portato all&rsquo;attenzione del legale
						interno incaricato, che nel caso di specie &eacute;<br>
						${incaricoView.utenteConnesso}<br> 

				Rimane comunque inteso (i) che Lei avr&agrave; l&rsquo;obbligo di astenersi
							dal prestare attivit&agrave; professionale quando questa determini un
							conflitto con gli interessi di Snam o societ&agrave; del gruppo Snam e
							(ii) che Lei si impegner&agrave; a far s&iacute; che tale obbligo di astensione
							sia osservato anche dai collaboratori di cui potr&agrave; avvalersi
							nell&rsquo;espletamento dell&rsquo;Incarico nei limiti di quanto sotto
							riportato<br>
					</li>
					<li>
					Tutta la corrispondenza relativa all&rsquo;incarico in oggetto dovr&agrave; essere indirizzata al legale interno.<br>
					</li>
					<li>Il legale interno dovr&agrave; essere tempestivamente e
						periodicamente informato di ogni evento rilevante relativo
						all&rsquo;incarico.<br> 
						<c:if
							test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_1'
							&&
							incaricoView.fascicoloRiferimento.vo.settoreGiuridico.codGruppoLingua ne 'TSTT_2'
							}">
Inoltre, qualsiasi atto processuale, 
comunicazione e/o documento da depositare avanti ad Autorit&agrave; giudiziarie dovr&agrave; essere preventivamente 
e con congruo anticipo sottoposto al legale interno. Parimenti, copia di ogni documento 
o atto prodotto in giudizio e rilevante ai fini della tutela della posizione della Societ&agrave; 
dovr&agrave; essere tempestivamente inviato al legale interno. Infine, eventuali proposte relative 
ad accordi transattivi dovranno essere immediatamente portate a conoscenza del legale interno 
e da quest&rsquo;ultimo espressamente approvate.
				<br></c:if>
In particolare, sar&agrave; Sua cura comunicare al legale interno gli estremi della polizza di assicurazione 
da Lei stipulata per i rischi derivanti dall&rsquo;esercizio dell&rsquo;attivit&agrave; professionale. 
Inoltre, qualsiasi atto processuale, comunicazione e/o documento da depositare avanti 
ad Autorit&agrave; giudiziarie dovr&agrave; essere preventivamente e con congruo anticipo sottoposto al legale interno.
				<br>
Nello svolgimento dell&rsquo;Incarico:
<ol class="itwo">
<li>Lei svolger&agrave; le attivit&agrave; necessarie in qualit&agrave; di prestatore d&rsquo;opera intellettuale e di 
<form:input path="qualifica" cssClass="form-control disabilitaaut"style="width: 100px;display: initial;" />  dello Studio ${incaricoView.professionistaSelezionato.vo.studioLegale.denominazione}.  
Potr&agrave; utilizzare collaboratori a condizione che siano soci, partner o collaboratori dello Studio ${incaricoView.professionistaSelezionato.vo.studioLegale.denominazione} e fermo restando la Sua responsabilit&agrave; per lo svolgimento e l&rsquo;esito delle attivit&agrave;.
</li><li>	A richiesta del Legale Interno, un suo collaboratore si recher&agrave; presso gli uffici della Societ&agrave; per svolgere le attivit&agrave; su istruzioni e con il supporto continuativo dello Studio, restando inteso che non prester&agrave; continuativamente la propria assistenza presso gli uffici della Societ&agrave;.
</li><li>	Lei, fermo restando la Sua autonomia professionale, si consulter&agrave; con il Legale Interno per ogni evento o aspetto rilevante dell&rsquo;oggetto dell&rsquo;Incarico al fine di raccogliere le opportune istruzioni. 
</li><li>	Sar&agrave; Suo obbligo primario e fondamentale mantenere la massima riservatezza e il segreto sull&rsquo;attivit&agrave; prestata e su tutte le informazioni che siano fornite a Lei o ai suoi collaboratori o di cui Lei o i suoi collaboratori veniate a conoscenza in dipendenza dal presente incarico. 
La informiamo inoltre che, in conformit&agrave; con la legge applicabile Lei potr&agrave;, laddove ne sussistano i presupposti, essere iscritto nel registro delle persone che hanno accesso ad informazioni privilegiate di Snam
</li>
</ol>
				<br>			
					</li>
					
					<li>
					Sezione Compensi:<br>
					<form:textarea path="infoCompenso" cssClass="form-control disabilitaaut" id="infoCompenso" />
														<div class="list-group lg-alt" style="visibility: collapse;">
											<!--VALUTA-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label class="col-md-2 control-label" for="valuta"><spring:message
																text="??proforma.label.valuta??"
																code="proforma.label.valuta" /></label>
														<div class="col-md-10">
															<c:if test="${ not empty incaricoView.listaTipoValuta  }">
																<c:forEach items="${incaricoView.listaTipoValuta}"
																	var="oggetto">
																	<form:radiobutton disabled="true" path="valutaId"
																		value="${oggetto.vo.id }" label="${oggetto.vo.nome }" />
																</c:forEach>
															</c:if>
														</div>
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<!--COMPENSO-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label class="col-md-2 control-label" for="compenso"><spring:message
																text="??incarico.label.compensototale??"
																code="incarico.label.compensototale" /></label>
														<div class="col-md-10">
															<div class="input-group">
															<span class="input-symbol-euro">
																<input type="number" value="${incaricoView.compenso}" min="0" step="1.00"
																	data-number-to-fixed="2" data-number-stepfactor="100"
																	class="form-control currency disabilitaaut" id="compenso" name="compenso"/></span>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>


										<!--BONUS-->
										<div class="list-group lg-alt" id="bonus">
											<div class="list-group-item media">
												<div class="media-body">
												<c:if test="${incaricoView.sizeBonus gt 0}">
												<input type="hidden" id="sizeB" value="${incaricoView.sizeBonus}">
												<c:forEach items="${ incaricoView.bonusIn }" var="bonus" varStatus="i">
												<div class="form-group bonusdiv">
														<label class="col-md-2 control-label"><spring:message
																text="??incarico.label.bonus??"
																code="incarico.label.bonus" /></label>
														<div class="col-xs-4">
														<span class="input-symbol-euro">
															<input name="bonus[${i.index}].importo"
																placeholder="Importo" type="number"  min="0"
																step="1.00" data-number-to-fixed="2"
																data-number-stepfactor="100"
																class="form-control currency bonus disabilitaaut" value="${bonus.importo}"/></span>
														</div>
														<div class="col-xs-4">
															<input type="text" class="form-control descrizione disabilitaaut" 
																name="bonus[${i.index}].descrizione" placeholder="Descrizione"
																value="${bonus.descrizione}" />
														</div>
														<c:if test="${i.index eq 0}">
														<div class="col-xs-1">
															<button type="button"
																class="btn palette-Green-SNAM bg waves-effect waves-float disabilitaaut"
																id="addButtonB">
																<i class="fa fa-plus disabilitaaut"></i>
															</button>
														</div>
														</c:if>
														<c:if test="${i.index gt 0}">
														<div class="col-xs-1">
															<button type="button"
																class="btn palette-Green-SNAM bg waves-effect waves-float"
																 id="removeButtonB">
																<i class="fa fa-minus disabilitaaut"></i>
															</button>
														</div>
														</c:if>
													</div>
												</c:forEach>	
												</c:if>
												<c:if test="${incaricoView.sizeBonus eq 0}">
												<input type="hidden" id="sizeB" value="1">
													<div class="form-group">
														<label class="col-md-2 control-label"><spring:message
																text="??incarico.label.bonus??"
																code="incarico.label.bonus" /></label>
														<div class="col-xs-4">
														<span class="input-symbol-euro">
															<input name="bonus[0].importo"
																placeholder="Importo" type="number" value="0" min="0"
																step="1.00" data-number-to-fixed="2"
																data-number-stepfactor="100"
																class="form-control currency bonus disabilitaaut" /></span>
														</div>
														<div class="col-xs-4">
															<input type="text" class="form-control descrizione disabilitaaut"
																name="bonus[0].descrizione" placeholder="Descrizione"
																 />
														</div>
														<div class="col-xs-1">
															<button type="button"
																class="btn palette-Green-SNAM bg waves-effect waves-float disabilitaaut"
																id="addButtonB">
																<i class="fa fa-plus"></i>
															</button>
														</div>
													</div>
												</c:if>
													<div class="form-group bonusdiv hide" id="bonusTemplate">
														<label class="col-md-2 control-label"><spring:message
																text="??incarico.label.bonus??"
																code="incarico.label.bonus" /></label>
														<div class="col-xs-4">
														<span class="input-symbol-euro">
															<input type="number" value="0" min="0"
																step="1.00" data-number-to-fixed="2"
																data-number-stepfactor="100"
																class="form-control currency bonus disabilitaaut" name=""
																id="impB"
																placeholder="Importo" /></span>
														</div>
														<div class="col-xs-4">
															<input type="text" class="form-control descrizione disabilitaaut"
																name="" placeholder="Descrizione" id="descB" />
														</div>
														<div class="col-xs-1">
															<button type="button"
																class="btn palette-Green-SNAM bg waves-effect waves-float disabilitaaut"
																 id="removeButtonB">
																<i class="fa fa-minus"></i>
															</button>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="list-group lg-alt" id="importi">
											<!--IMPORTI-->
											<div class="list-group-item media">
												<div class="media-body">
												<!-- Aggiunta anno fine incarico MASSIMO CARUSO -->
												<c:set var="annoFinale" value="${incaricoView.saldoAnno}"  />
												<c:if test="${incaricoView.sizeAcconti gt 0}">
												<input type="hidden" id="sizeA" value="${incaricoView.sizeAcconti}">
												<c:forEach items="${ incaricoView.accontiIn }" var="acconti" varStatus="y">
												    <!-- Aggiunta controllo per anno fine incarico MASSIMO CARUSO -->
												    <c:if test="${acconti.importo eq -1}" >
												    <c:set var="annoFinale" value="${acconti.anno }"  />
												    </c:if>
												    <c:if test="${acconti.importo ne -1}" >
													<div class="form-group" importi-index="${y.index}">
														<label class="col-md-2 control-label"><spring:message
																text="??incarico.label.acconto??"
																code="incarico.label.acconto" /></label>
														<div class="col-xs-4">
														<span class="input-symbol-euro">
															<input name="acconto[${y.index}].importo"
																placeholder="Importo" type="number" value="${acconti.importo}" min="0"
																step="1.00" data-number-to-fixed="2"
																data-number-stepfactor="100"
																class="form-control currency importo disabilitaaut" /></span>
														</div>
														<div class="col-xs-4">
															<input type="text" class="form-control disabilitaaut"
																name="acconto[${y.index}].anno" placeholder="Anno"
															    value="${acconti.anno}"/>
														</div>
														<c:if test="${y.index eq 0}">
														<div class="col-xs-1">
															<button type="button"
																class="btn palette-Green-SNAM bg waves-effect waves-float disabilitaaut"
																id="addButton">
																<i class="fa fa-plus"></i>
															</button>
														</div>
														<div class="col-xs-1">
															<button type="button"
																class="btn palette-Green-SNAM bg waves-effect waves-float disabilitaaut"
																id="removeButton" style="margin-left: -15px;">
																<i class="fa fa-minus"></i>
															</button>
														</div>
														</c:if>
													</div>
													</c:if>
													</c:forEach>	
													</c:if>
													<c:if test="${incaricoView.sizeAcconti eq 0}">
													<input type="hidden" id="sizeA" value="0">
													<div class="form-group">
														<label class="col-md-2 control-label"><spring:message
																text="??incarico.label.acconto??"
																code="incarico.label.acconto" /></label>
														<div class="col-xs-4">
														<span class="input-symbol-euro">
															<input name="acconto[0].importo"
																placeholder="Importo" type="number" value="0" min="0"
																step="1.00" data-number-to-fixed="2"
																data-number-stepfactor="100"
																class="form-control currency importo disabilitaaut" required /></span>
														</div>
														<div class="col-xs-4">
															<input type="text" class="form-control disabilitaaut"
																name="acconto[0].anno" placeholder="Anno"
																value="${acconti.anno}"/>
														</div>
														<div class="col-xs-1">
															<button type="button"
																class="btn palette-Green-SNAM bg waves-effect waves-float disabilitaaut"
																id="addButton">
																<i class="fa fa-plus"></i>
															</button>
														</div>
														<div class="col-xs-1">
															<button type="button"
																class="btn palette-Green-SNAM bg waves-effect waves-float disabilitaaut"
																id="removeButton" style="margin-left: -15px;">
																<i class="fa fa-minus"></i>
															</button>
														</div>
														</div>
													</c:if>
													<div class="form-group hide" id="importiTemplate">
														<label class="col-md-2 control-label"><spring:message
																text="??incarico.label.acconto??"
																code="incarico.label.acconto" /></label>
														<div class="col-xs-4">
														<span class="input-symbol-euro">
															<input type="number" value="0" min="0"
																step="1.00" data-number-to-fixed="2"
																data-number-stepfactor="100"
																class="form-control currency importo disabilitaaut" name=""
																placeholder="Importo" id="impI" required /></span>
														</div>
														<div class="col-xs-4">
															<input type="text" class="form-control"
																name="" id="annI" placeholder="Anno"
															 />
														</div>
														<div class="col-xs-1">
														<!-- Aggiunta bottone di rimozione nel template MASSIMO CARUSO -->
														<button type="button"
																class="btn palette-Green-SNAM bg waves-effect waves-float disabilitaaut"
																id="removeButton" style="margin-left: -15px;">
																<i class="fa fa-minus"></i>
															</button>
														</div>
													</div>
													<div class="form-group">
														<label class="col-md-2 control-label"><spring:message
																text="??incarico.label.saldo??"
																code="incarico.label.saldo" /></label>
														<div class="col-xs-4">
														<span class="input-symbol-euro">
															<input type="text" class="form-control saldo"
																name="saldoImporto" value="0" readonly="true"  style="font-weight: bolder;"/></span>
														</div>
														<div class="col-xs-4">
														    <!-- Aggiunta anno fine incarico MASSIMO CARUSO -->
															<input type="text" class="form-control" name="saldoAnno"
																value="${annoFinale}"
																placeholder="<spring:message
																text="??incarico.label.finecausa??"
																code="incarico.label.finecausa" id="saldoAnno"/>" />
														</div>
														<div class="col-xs-1"></div>
													</div>
												</div>
											</div>
										</div>
					</li>
					<li>
					La notula &quot;pro forma&quot; delle competenze del Vostro studio dovr&agrave; essere redatta in forma analitica con 
					l&rsquo;indicazione della data di conferimento e dell&rsquo;oggetto dell&rsquo;incarico, 
					nonch&eacute; delle attivit&agrave; professionali espletate nel periodo di riferimento.<br>
					<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_1'
					&&
					incaricoView.fascicoloRiferimento.vo.settoreGiuridico.codGruppoLingua ne 'TSTT_2'
					}">
					Vi precisiamo inoltre che i compensi per la domiciliazione, come per altre consulenze (periti, ecc.) che dovessero, 
					con l&rsquo;approvazione del legale interno, rendersi necessarie, dovranno essere anticipati dal Vostro studio e inseriti, 
					previa puntuale e specifica indicazione, nella parcella immediatamente successiva.
					</c:if>
					La notula &quot;pro forma&quot; e la parcella successiva, dovranno essere inserite tramite il sistema informatico 
					LEGAL (es. https://legal-external.snam.it/PortaleLegaliEsterni), attraverso le credenziali in Vostro possesso o
					che vi stanno pervenendo, e  
    				dovranno essere intestate 
    				
    				<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) gt 1}">
    				alle societ&agrave; assistite che, nella fattispecie, sono: 
    				</c:if>
    				<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) eq 1}">
    				alla societ&agrave; assistita che, nella fattispecie, &egrave;: 
    				</c:if>
    				<br>
    				<c:forEach items="${incaricoView.listaSocietaAddebitoAggiunteDescLet}"
					var="oggetto">
					${oggetto.nome}
					<br>
					</c:forEach>
					Dovranno inoltre essere indicate le coordinate bancarie necessarie per effettuare il pagamento.
					</li>
					<li>
					<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_1'
					&&
					incaricoView.fascicoloRiferimento.vo.settoreGiuridico.codGruppoLingua ne 'TSTT_2'
					}">
					L&rsquo;adempimento dell&rsquo;incarico vale quale integrale accettazione dei contenuti della presente, 
					anche per conto dell&rsquo;eventuale domiciliatario, 
					cos&igrave; come il Vostro impegno a rispettare rigorosamente il Codice Deontologico forense, 
					cos&igrave; come le regole etiche da applicarsi nelle attivit&agrave; professionali contenute 
					in ogni altro eventuale codice professionale o raccolta di regole applicabili alla Sua professione
					</c:if>
					<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_2'
					||
					incaricoView.fascicoloRiferimento.vo.settoreGiuridico.codGruppoLingua eq 'TSTT_2'
					}">
					L&rsquo;accettazione del presente incarico vale quale integrale accettazione dei contenuti in esso indicati, 
					cos&igrave; come il Suo impegno a rispettare rigorosamente il Codice Deontologico forense, 
					cos&igrave; come le regole etiche da applicarsi nelle attivit&agrave; professionali contenute in 
					ogni altro eventuale codice professionale o raccolta di regole applicabili alla Sua professione
					</c:if>
					<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_4'}">
					l&rsquo;adempimento dell&rsquo;incarico vale quale integrale accettazione dei contenuti della presente, 
					cos&igrave; come le regole etiche da applicarsi nelle attivit&agrave; professionali contenute 
					in ogni altro eventuale codice professionale o raccolta di regole applicabili alla Sua professione
					</c:if>
					</li>
					<li>
					Nella qualit&agrave; di Prestatori d&rsquo;opera intellettuale dichiarate di conoscere, 
					e Vi impegnate a rispettare, le Leggi Anticorruzione<sup>1</sup>,  
					il Codice Etico e il &quot;Modello 231&quot; di 
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) gt 1}">
					Snam S.p.A.
					</c:if>
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) eq 1}">
					<c:out value="${incaricoView.listaSocietaAddebitoAggiunteDescLet[0].ragioneSociale}"/>
					</c:if>
					(consultabili e stampabili sul sito internet
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) gt 1}">
					${incaricoView.snamretegasWebsite}
					</c:if>
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) eq 1}">
					<c:out value="${incaricoView.listaSocietaAddebitoAggiunteDescLet[0].sitoWeb}"/>
					</c:if>
					) e la &quot;Procedura Anticorruzione&quot; di SNAM 
					(consultabile e stampabile sul sito internet ${incaricoView.snamWebsite}).
					Con riferimento all&rsquo;esecuzione delle attivit&agrave; oggetto del presente Contratto, 
					nella qualit&agrave; di Prestatori d&rsquo;opera intellettuale Vi impegnate altres&igrave;:
					<ol type="a">
					<li>
					ad astenerVi da dare o promettere - nonch&eacute; a comunicare senza indugio a 
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) gt 1}">
					Snam S.p.A.
					</c:if>
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) eq 1}">
					<c:out value="${incaricoView.listaSocietaAddebitoAggiunteDescLet[0].ragioneSociale}"/>
					</c:if>
					qualsiasi dazione, 
					promessa e/o richiesta di - denaro, provvigioni, emolumenti e altre utilit&agrave; a Pubblici Ufficiali<sup>2</sup>  e/o ad amministratori, 
					sindaci, dipendenti o collaboratori di Snam e/o Controllate<sup>3</sup> , ivi compresi regali, intrattenimenti, 
					viaggi o qualsiasi altro tipo di beneficio, anche non patrimoniale, oltre i limiti di quanto ammesso dal 
					Codice Etico di 
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) gt 1}">
					Snam S.p.A.
					</c:if>
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) eq 1}">
					<c:out value="${incaricoView.listaSocietaAddebitoAggiunteDescLet[0].ragioneSociale}"/>
					</c:if>
					e dalla &quot;Procedura Anticorruzione&quot; di SNAM;
					</li>
					<li>
					in ogni caso a comunicare senza indugio qualsiasi richiesta o tentata richiesta o dazione o 
					promessa di quanto indicato sub (a), indipendentemente da ogni valutazione sulla conformit&agrave; o 
					meno al Codice Etico di 
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) gt 1}">
					Snam S.p.A.
					</c:if>
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) eq 1}">
					<c:out value="${incaricoView.listaSocietaAddebitoAggiunteDescLet[0].ragioneSociale}"/>
					</c:if>
					e alla &quot;Procedura Anticorruzione&quot; di SNAM;
					</li>
					<li>
					ad astenerVi da concludere accordi direttamente con Personale del Gruppo SNAM<sup>4</sup>  
					o suoi Familiari<sup>5</sup>  o societ&agrave; ad essi riconducibili.
					</li>
					</ol>
					
					Tali comunicazioni dovranno essere indirizzate alla casella di posta elettronica 
					${incaricoView.emailSegnalazioni}.

					Fermo ogni diritto e rimedio spettante a 
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) gt 1}">
					Snam S.p.A.
					</c:if>
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) eq 1}">
					<c:out value="${incaricoView.listaSocietaAddebitoAggiunteDescLet[0].ragioneSociale}"/>
					</c:if>
					per legge e/o per contratto, 
					e ferma ogni responsabilit&agrave; del Prestatore d&rsquo;Opera Intellettuale verso 
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) gt 1}">
					Snam S.p.A.
					</c:if>
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) eq 1}">
					<c:out value="${incaricoView.listaSocietaAddebitoAggiunteDescLet[0].ragioneSociale}"/>
					</c:if>
					 e 
					l&rsquo;obbligo di tenere indenne e manlevata 
				   <c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) gt 1}">
					Snam S.p.A.
					</c:if>
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) eq 1}">
					<c:out value="${incaricoView.listaSocietaAddebitoAggiunteDescLet[0].ragioneSociale}"/>
					</c:if>
				    da ogni eventuale responsabilit&agrave; verso terzi, 
					a 
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) gt 1}">
					Snam S.p.A.
					</c:if>
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) eq 1}">
					<c:out value="${incaricoView.listaSocietaAddebitoAggiunteDescLet[0].ragioneSociale}"/>
					</c:if>
					 &egrave; riservato il diritto di sospendere l&rsquo;esecuzione dell&rsquo;incarico o di recedere unilateralmente, 
					anche in corso di esecuzione, in presenza di notizie, anche di stampa, circa circostanze di fatto o procedimenti 
					giudiziari da cui possa ragionevolmente desumersi l&rsquo;inosservanza della presente clausola.
					</li>
					<li>
						I dati e le informazioni di cui si verr&agrave; a conoscenza per lo svolgimento 
						dell&rsquo;incarico sono da considerarsi strettamente riservati e confidenziali e 
						il relativo uso deve essere limitato ai soli fini dell&rsquo;espletamento dell&rsquo;incarico. 
						I dati e le informazioni saranno trattati in conformit&agrave; a quanto disposto dal d. lgs. n. 196/2003, 
						ed in ottemperanza agli obblighi di legge in materia di tutela della privacy.  
					</li>
					<li>
					il presente incarico &egrave; regolato dalla Legge Italiana e tutte le modifiche ad esso apportate dovranno essere effettuate 
					e provate per iscritto. Qualsiasi controversia dovesse insorgere in merito all&rsquo;interpretazione, esecuzione, 
					validit&agrave; o efficacia del presente incarico sar&agrave; di competenza esclusiva del Foro di Milano
					</li>
					<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_1'
					&&
					incaricoView.fascicoloRiferimento.vo.settoreGiuridico.codGruppoLingua ne 'TSTT_2'
					}">
					<li>
					Qualora si rendesse necessaria la nomina di un domiciliatario, la sua individuazione dovr&egrave; rispondere ai principi del Codice Etico 
					di 
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) gt 1}">
					Snam S.p.A.
					</c:if>
					<c:if test="${fn:length(incaricoView.listaSocietaAddebitoAggiunteDescLet) eq 1}">
					<c:out value="${incaricoView.listaSocietaAddebitoAggiunteDescLet[0].ragioneSociale}"/>
					</c:if>
					, ai principi deontologici nonché ai principi contenuti nella clausola 7) che precede
					</li>
					</c:if>
				</ol>
				Nel restare a disposizione per ogni necessario chiarimento, 
				Vi ringraziamo per la Vostra collaborazione e Vi inviamo i nostri pi&ugrave; cordiali saluti.<br>
				______________________________<br><br>
				<ol type="1">
				<li>
				<sub style="line-height: inherit;">
				Per &quot;Leggi Anticorruzione&quot; si intendono: il Codice Penale italiano, 
				la Legge 6 novembre 2012 n. 190, il Decreto Legislativo n. 231 del 2001 
				e le altre disposizioni applicabili, l&rsquo;UK Bribery Act, le altre leggi 
				di diritto pubblico e commerciale contro la corruzione vigenti nel mondo 
				e i trattati internazionali anticorruzione, quali la Convenzione dell&rsquo;Organizzazione 
				per la Cooperazione e lo Sviluppo Economico sulla lotta alla corruzione dei pubblici 
				ufficiali stranieri nelle operazioni economiche internazionali e la Convenzione delle Nazioni Unite contro la corruzione.
				</sub>
				</li>
				<li>
				<sub style="line-height: inherit;">
				Per &quot;Pubblici Ufficiali&quot; si intendono: 
				a)	chiunque ricopra una carica pubblica funzione legislativa, giudiziaria o amministrativa;
				b)	chiunque agisca in veste ufficiale in nome, per conto o nell&rsquo;interesse di (i) una pubblica amministrazione sovranazionale, 
				nazionale, regionale o locale, (ii) un&rsquo;agenzia, un dipartimento, un ufficio o un organo di una pubblica amministrazione, 
				sovranazionale, nazionale, regionale o locale, (iii) un&rsquo;impresa di proprietà, controllata o partecipata da una pubblica 
				amministrazione, (iv) un&rsquo;organizzazione pubblica internazionale, e o (v) un partito politico, un membro di un partito 
				politico o un candidato a una carica politica;
				c)  qualunque incaricato di un pubblico servizio;
				d) qualunque Familiare (v. successiva nota 5) di un Pubblico Ufficiale o altro soggetto, persona fisica o ente, 
				che agisca su suggerimento, richiesta o disposizione o a vantaggio di alcuno dei soggetti o enti di cui alle lettere 
				da a) a c) sopra indicate. 
				</sub>
				</li>
				<li>
				<sub style="line-height: inherit;">
				Per &quot;Controllate&quot; si intendono: ogni ente direttamente o indirettamente controllato 
				(in base ai Principi Contabili Internazionali - IFRS 10 &quot;Bilancio consolidato&quot; 
				e successive modifiche e integrazioni) da Snam o da una Controllata, a seconda dei casi, in Italia o all&rsquo;estero. 
				</sub>
				</li>
				<li>
				<sub style="line-height: inherit;">
				Per &quot;Personale del Gruppo Snam&quot; si intendono: gli amministratori, dirigenti, 
				membri degli organi sociali, dipendenti di Snam e delle Controllate.
				</sub>
				</li>
				<li>
				<sub style="line-height: inherit;">
				Per &quot;Familiari&quot; si intendono: il coniuge; i nonni, genitori, fratelli e sorelle, figli, nipoti, zii e primi cugini del soggetto e del suo coniuge; 
				il coniuge di ognuna di tali persone; e ogni altro soggetto che condivide con gli stessi l&rsquo;abitazione.
				</sub>
				</li>
  				</ol>
  				<br>
			</div>
		</div>
	</div>
</div>

<!-- SOCIETA DI ADDEBITO-->
<!-- 
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="societaAddebito" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.societaAddebito??"
					code="incarico.label.societaAddebito" /></label>
			<div class="col-sm-10">
				<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive">
						<thead>
							<tr style="border: 1px solid #e0e0e0">
								<th data-column-id="01" style="width: 50px">
									<button type="button" data-toggle="collapse"
										data-target="#boxSocietaAddebito"
										class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
										style="float: left; position: relative !important;">
										<i class="zmdi zmdi-collection-text icon-mini"></i>
									</button>
								</th>
								<th data-column-id="id"><spring:message
										text="??incarico.label.societaAddebito??"
										code="incarico.label.societaAddebito" /></th>

							</tr>
						</thead>
						<tbody id="boxSocietaAddebito" class="collapse in">
							<c:if
								test="${ incaricoView.listaSocietaAddebitoAggiunteDesc != null }">
								<c:forEach
									items="${fascicoloView.listaSocietaAddebitoAggiunteDesc}"
									var="descrizione">
									<tr>
										<td colspan="2">${descrizione}</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>
</div>
 -->
	<!--NOME PROFESSIONISTA-->
<!-- 
<div class="list-group lg-alt">
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="fascicolo" class="col-sm-2 control-label"><spring:message
						text="??incarico.label.nomeProfessionista??"
						code="incarico.label.nomeProfessionista" /></label>
				<div class="col-sm-10">
					<input class="form-control" readonly
						value="${incaricoView.professionistaSelezionato.vo.nome}" />
				</div>
			</div>
		</div>
	</div>
</div>
 -->
 
	<!--COGNOME PROFESSIONISTA-->
	<!-- 
<div class="list-group lg-alt">
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="fascicolo" class="col-sm-2 control-label"><spring:message
						text="??incarico.label.cognomeProfessionista??"
						code="incarico.label.cognomeProfessionista" /></label>
				<div class="col-sm-10">
					<input class="form-control" readonly
						value="${incaricoView.professionistaSelezionato.vo.cognome}" />
				</div>
			</div>
		</div>
	</div>
</div>

 -->
 
	<!--INDIRIZZO STUDIO PROFESSIONISTA-->
 <!-- 
<div class="list-group lg-alt">
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="fascicolo" class="col-sm-2 control-label"><spring:message
						text="??incarico.label.indirizzoStudioProfessionista??"
						code="incarico.label.indirizzoStudioProfessionista" /></label>
				<div class="col-sm-10">
					<input class="form-control" readonly
						value="${incaricoView.professionistaSelezionato.vo.studioLegale.indirizzo}" />
				</div>
			</div>
		</div>
	</div>
</div>
 -->

	<!--CF PIVA STUDIO PROFESSIONISTA-->
	<!-- 
<div class="list-group lg-alt">
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="fascicolo" class="col-sm-2 control-label"><spring:message
						text="??incarico.label.cfPivaStudioProfessionista??"
						code="incarico.label.cfPivaStudioProfessionista" /></label>
				<div class="col-sm-10">
					<input class="form-control" readonly
						value="${incaricoView.professionistaSelezionato.vo.studioLegale.partitaIva}" />
				</div>
			</div>
		</div>
	</div>
</div>

 -->
<!-- PROTOCOLLO-->

<form:hidden path="letteraIncaricoId"/>

<!-- COMPENSO-->
<%-- <div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="compenso" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.compenso??" code="incarico.label.compenso" /></label>
			<div class="col-sm-10">
				<form:input path="compenso" cssClass="form-control" />
			</div>
		</div>
	</div>
</div> --%>

<!-- STAGIUDIZIALE -->
<%-- <c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_2'}">
	<!-- MESI COMPENSO STRAGIUDIZIALE-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="mesiCompensoStragiudiziale" class="col-sm-2 control-label"><spring:message
						text="??incarico.label.mesiCompensoStragiudiziale??" code="incarico.label.mesiCompensoStragiudiziale" /></label>
				<div class="col-sm-10">
					<form:input path="mesiCompensoStragiudiziale" cssClass="form-control" />
				</div>
			</div>
		</div>
	</div>
	
</c:if>  --%>

<!-- GIUDIZIALE -->
<%-- <c:if test="${ incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_1'}">
	<!-- CIVILE -->	
	<c:if test="${incaricoView.fascicoloRiferimento.vo.settoreGiuridico.codGruppoLingua eq 'TSTT_1'}">
		<!-- BONUS CIVILE 1-->
		<div class="list-group-item media">
			<div class="media-body">
				<div class="form-group">
					<label for="bonusCivile1" class="col-sm-2 control-label"><spring:message
							text="??incarico.label.bonusCivile1??" code="incarico.label.bonusCivile1" /></label>
					<div class="col-sm-10">
						<input type="number"  name="bonusCivile1" class="form-control" value="${incaricoView.bonusCivile1 }" />
					</div>
				</div>
			</div>
		</div> --%>
		
		<!-- BONUS CIVILE 2-->
		<%-- <div class="list-group-item media">
			<div class="media-body">
				<div class="form-group">
					<label for="bonusCivile2" class="col-sm-2 control-label"><spring:message
							text="??incarico.label.bonusCivile2??" code="incarico.label.bonusCivile2" /></label>
					<div class="col-sm-5">
						<input type="number"  name="bonusCivile2" class="form-control"  value="${incaricoView.bonusCivile2 }" />
					</div>
					<div class="col-sm-5"> 
						<form:select path="esitoBonusCivile2" cssClass="form-control">
							<form:option value="" label="${percentualeSuccessoBonus }"></form:option>
							<form:option value="10" label="10%"></form:option>
							<form:option value="20" label="20%"></form:option>
							<form:option value="30" label="30%"></form:option>
							<form:option value="40" label="40%"></form:option>
							<form:option value="50" label="50%"></form:option>
							<form:option value="60" label="60%"></form:option>
							<form:option value="70" label="70%"></form:option>
							<form:option value="80" label="80%"></form:option>
							<form:option value="90" label="90%"></form:option>
						</form:select>
					</div>
				</div>
			</div>
		</div>  --%>
		
		<!-- BONUS CIVILE 3-->
<%-- 		<div class="list-group-item media">
			<div class="media-body">
				<div class="form-group">
					<label for="bonusCivile3" class="col-sm-2 control-label"><spring:message
							text="??incarico.label.bonusCivile3??" code="incarico.label.bonusCivile3" /></label>
					<div class="col-sm-5">
						<input type="number"  name="bonusCivile3" class="form-control"  value="${incaricoView.bonusCivile3 }" /> 
					</div>
					<div class="col-sm-5"> 
						<form:select path="esitoBonusCivile3" cssClass="form-control">
							<form:option value="" label="${percentualeSuccessoBonus }"></form:option>
							<form:option value="10" label="10%"></form:option>
							<form:option value="20" label="20%"></form:option>
							<form:option value="30" label="30%"></form:option>
							<form:option value="40" label="40%"></form:option>
							<form:option value="50" label="50%"></form:option>
							<form:option value="60" label="60%"></form:option>
							<form:option value="70" label="70%"></form:option>
							<form:option value="80" label="80%"></form:option>
							<form:option value="90" label="90%"></form:option>
						</form:select>
					</div> 
				</div>
			</div>
		</div>
		 
		
	</c:if> --%>
	<!-- AMMINISTRATIVO -->	
	<%-- <c:if test="${incaricoView.fascicoloRiferimento.vo.settoreGiuridico.codGruppoLingua eq 'TSTT_3'}">
	
		<!-- BONUS AMMINISTRATIVO 1-->
		<div class="list-group-item media">
			<div class="media-body">
				<div class="form-group">
					<label for="bonusAmministrativo1" class="col-sm-2 control-label"><spring:message
							text="??incarico.label.bonusAmministrativo1??" code="incarico.label.bonusAmministrativo1" /></label>
					<div class="col-sm-10">
						<input type="number"  name="bonusAmministrativo1" class="form-control"  value="${incaricoView.bonusAmministrativo1 }" />
					</div>
				</div>
			</div>
		</div>
		
		<!-- BONUS AMMINISTRATIVO 1-->
		<div class="list-group-item media">
			<div class="media-body">
				<div class="form-group">
					<label for="bonusAmministrativo2" class="col-sm-2 control-label"><spring:message
							text="??incarico.label.bonusAmministrativo2??" code="incarico.label.bonusAmministrativo2" /></label>
					<div class="col-sm-10">
						<input type="number"  name="bonusAmministrativo2" class="form-control"  value="${incaricoView.bonusAmministrativo2 }" />
					</div>
				</div>
			</div>
		</div>
		
	</c:if> --%>
	<!-- ARBITRALE-->	
	<%-- <c:if test="${incaricoView.fascicoloRiferimento.vo.settoreGiuridico.codGruppoLingua eq 'TSTT_6'}">
		<!-- BONUS ARBITRALE 1-->
		<div class="list-group-item media">
			<div class="media-body">
				<div class="form-group">
					<label for="bonusArbitrale1" class="col-sm-2 control-label"><spring:message
							text="??incarico.label.bonusArbitrale1??" code="incarico.label.bonusArbitrale1" /></label>
					<div class="col-sm-10">
						<input type="number"  name="bonusArbitrale1" class="form-control" value="${incaricoView.bonusArbitrale1 }"/>
					</div>
				</div>
			</div>
		</div> --%>
		
		<!-- BONUS ARBITRALE 2-->
		<%-- <div class="list-group-item media">
			<div class="media-body">
				<div class="form-group">
					<label for="bonusArbitrale2" class="col-sm-2 control-label"><spring:message
							text="??incarico.label.bonusArbitrale2??" code="incarico.label.bonusArbitrale2" /></label>
					<div class="col-sm-5">
						<input type="number"  name="bonusArbitrale2" class="form-control"  value="${incaricoView.bonusArbitrale2 }"/>
					</div>
					<div class="col-sm-5"> 
						<form:select path="esitoBonusArbitrale2" cssClass="form-control">
							<form:option value="" label="${percentualeSuccessoBonus }"></form:option>
							<form:option value="10" label="10%"></form:option>
							<form:option value="20" label="20%"></form:option>
							<form:option value="30" label="30%"></form:option>
							<form:option value="40" label="40%"></form:option>
							<form:option value="50" label="50%"></form:option>
							<form:option value="60" label="60%"></form:option>
							<form:option value="70" label="70%"></form:option>
							<form:option value="80" label="80%"></form:option>
							<form:option value="90" label="90%"></form:option>
						</form:select>
					</div> 
				</div>
			</div>
		</div> --%>
		 
		
		<!-- BONUS ARBITRALE 3-->
		<%-- <div class="list-group-item media">
			<div class="media-body">
				<div class="form-group">
					<label for="bonusArbitrale3" class="col-sm-2 control-label"><spring:message
							text="??incarico.label.bonusArbitrale3??" code="incarico.label.bonusArbitrale3" /></label>
					<div class="col-sm-5">
						<input type="number"  name="bonusArbitrale3" class="form-control"  value="${incaricoView.bonusArbitrale3 }"/>
					</div>
					<div class="col-sm-5"> 
						<form:select path="esitoBonusArbitrale3" cssClass="form-control">
							<form:option value="" label="${percentualeSuccessoBonus }"></form:option>
							<form:option value="10" label="10%"></form:option>
							<form:option value="20" label="20%"></form:option>
							<form:option value="30" label="30%"></form:option>
							<form:option value="40" label="40%"></form:option>
							<form:option value="50" label="50%"></form:option>
							<form:option value="60" label="60%"></form:option>
							<form:option value="70" label="70%"></form:option>
							<form:option value="80" label="80%"></form:option>
							<form:option value="90" label="90%"></form:option>
						</form:select>
					</div> 
				</div>
			</div>
		</div> --%>
		 
			
<%-- 	</c:if>
</c:if> --%>


	


