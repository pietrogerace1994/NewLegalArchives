<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<div class="table-responsive" style="clear: both;">
	<table class="table table-striped table-responsive">
		<thead>
			<tr style="border: 1px solid #e0e0e0">
				<th data-column-id="01" style="width: 50px">
					<button type="button" data-toggle="collapse"
						data-target="#boxStudioLegale"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
						style="float: left; position: relative !important;">
						<i class="zmdi zmdi-collection-text icon-mini"></i>
					</button>
					<label for="fax" class="col-sm-2 control-label"><spring:message
															text="??professionistaEsterno.label.studioLegale??" code="professionistaEsterno.label.studioLegale" /></label>
				</th>
			</tr>
		</thead>
		<tbody id="boxStudioLegale" class="collapse in">
			<tr><td>
				<div id="nuovoStudioLegale" class="${param.initialClass}">
					<div class="list-group-item media" >
						<div class="media-body">
							<div class="form-group">
								<label for="studioLegaleDenominazione" class="col-sm-2 control-label"><spring:message
										text="??professionistaEsterno.label.studioLegale.denominazione??" 
										code="professionistaEsterno.label.studioLegale.denominazione" /></label>
								<div class="col-sm-10">
									<form:input path="studioLegaleDenominazione" cssClass="form-control" readonly="${param.readOnly}"/>
								</div>
							</div>
						</div>
					</div>
					
					<div class="list-group-item media" >
						<div class="media-body">
							<div class="form-group">
								<label for="studioLegaleIndirizzo" class="col-sm-2 control-label"><spring:message
										text="??professionistaEsterno.label.studioLegale.indirizzo??" 
										code="professionistaEsterno.label.studioLegale.indirizzo" /></label>
								<div class="col-sm-10">
									<form:input path="studioLegaleIndirizzo" cssClass="form-control" readonly="${param.readOnly}"/>
								</div>
							</div>
						</div>
					</div>
					
					<div class="list-group-item media" >
						<div class="media-body">
							<div class="form-group">
								<label for="studioLegaleCap" class="col-sm-2 control-label"><spring:message
										text="??professionistaEsterno.label.studioLegale.cap??" 
										code="professionistaEsterno.label.studioLegale.cap" /></label>
								<div class="col-sm-10">
									<form:input path="studioLegaleCap" cssClass="form-control" readonly="${param.readOnly}"/>
								</div>
							</div>
						</div>
					</div>
					
					<div class="list-group-item media" >
						<div class="media-body">
							<div class="form-group">
								<label for="studioLegaleCitta" class="col-sm-2 control-label"><spring:message
										text="??professionistaEsterno.label.studioLegale.citta??" 
										code="professionistaEsterno.label.studioLegale.citta" /></label>
								<div class="col-sm-10">
									<form:input path="studioLegaleCitta" cssClass="form-control" readonly="${param.readOnly}"/>
								</div>
							</div>
						</div>
					</div>

					<div class="list-group-item media" >
						<div class="media-body">
							<div class="form-group">
								<label for="studioLegaleEmail" class="col-sm-2 control-label"><spring:message
										text="??professionistaEsterno.label.studioLegale.email??" 
										code="professionistaEsterno.label.studioLegale.email" /></label>
								<div class="col-sm-10">
									<form:input path="studioLegaleEmail" cssClass="form-control" readonly="${param.readOnly}"/>
								</div>
							</div>
						</div>
					</div>	

					<div class="list-group-item media" >
						<div class="media-body">
							<div class="form-group">
								<label for="studioLegaleTelefono" class="col-sm-2 control-label"><spring:message
										text="??professionistaEsterno.label.studioLegale.telefono??" 
										code="professionistaEsterno.label.studioLegale.telefono" /></label>
								<div class="col-sm-10">
									<form:input path="studioLegaleTelefono" cssClass="form-control" readonly="${param.readOnly}"/>
								</div>
							</div>
						</div>
					</div>
					
					<div class="list-group-item media" >
						<div class="media-body">
							<div class="form-group">
								<label for="studioLegaleFax" class="col-sm-2 control-label"><spring:message
										text="??professionistaEsterno.label.studioLegale.fax??" 
										code="professionistaEsterno.label.studioLegale.fax" /></label>
								<div class="col-sm-10">
									<form:input path="studioLegaleFax" cssClass="form-control" readonly="${param.readOnly}"/>
								</div>
							</div>
						</div>
					</div>
					
					<c:if test="${param.readOnly == null}">
						<div class="list-group-item media" >
							<div class="media-body">
								<div class="form-group">
									<label for="studioLegaleNazioneCode" class="col-sm-2 control-label"><spring:message
											text="??professionistaEsterno.label.studioLegale.nazione??" 
											code="professionistaEsterno.label.studioLegale.nazione" /></label>
									<div class="col-sm-10">
										<form:select size="5" path="studioLegaleNazioneCode"
										cssClass="form-control" readonly="${param.readOnly}">
										<c:if test="${ professionistaEsternoView.listaNazioni != null }">
											<c:forEach
												items="${professionistaEsternoView.listaNazioni}"
												var="oggetto">
												<form:option value="${ oggetto.vo.codGruppoLingua }">
													<c:out value="${oggetto.vo.descrizione}"></c:out>
												</form:option>
											</c:forEach>
										</c:if>
									</form:select>
									</div>
								</div>
							</div>
						</div>
					</c:if>	
					<c:if test="${param.readOnly == 'true'}">
						<div class="list-group-item media" >
							<div class="media-body">
								<div class="form-group">
									<label for="studioLegaleNazioneCode" class="col-sm-2 control-label"><spring:message
											text="??professionistaEsterno.label.studioLegale.nazione??" 
											code="professionistaEsterno.label.studioLegale.nazione" /></label>
									<div class="col-sm-10">
										<form:input path="studioLegaleNazioneDescrizione" cssClass="form-control" readonly="${param.readOnly}"/>
									</div>
								</div>
							</div>
						</div>
					</c:if>	

					<div class="list-group-item media" >
						<div class="media-body">
							<div class="form-group">
								<label for="studioLegaleCodiceSap" class="col-sm-2 control-label"><spring:message
										text="??professionistaEsterno.label.studioLegale.codiceSap??" 
										code="professionistaEsterno.label.studioLegale.codiceSap" /></label>
								<div class="col-sm-10">
									<form:input path="studioLegaleCodiceSap" cssClass="form-control" readonly="${param.readOnly}"/>
								</div>
							</div>
						</div>
					</div>
					
					<div class="list-group-item media" >
						<div class="media-body">
							<div class="form-group">
								<label for="studioLegalePartitaIva" class="col-sm-2 control-label"><spring:message
										text="??professionistaEsterno.label.studioLegale.partitaIva??" 
										code="professionistaEsterno.label.studioLegale.partitaIva" /></label>
								<div class="col-sm-10">
									<form:input path="studioLegalePartitaIva" cssClass="form-control" readonly="${param.readOnly}"/>
								</div>
							</div>
						</div>
					</div>											
					
				</div>
			</td></tr>
		</tbody>
	</table>
</div>



