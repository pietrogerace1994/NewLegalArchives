<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<!-- RICERCA INCARICHI-->

<div class="row">
                    
                 
						<form action="<%=request.getContextPath()%>/reporting/export-incarico.action" method="post" >
						<engsecurity:token regenerate="false"/>
						<fieldset>

						<div class="form-group">
							<label class="col-md-12 control-label" for="data_creazione_da"><spring:message text="??reporting.incarico.dataCreazioneDa??" code="reporting.incarico.dataCreazioneDa" />  </label>
							<div class="col-md-12">
								 <input id="data_creazione_da" name="data_creazione_da"
									type="text" placeholder="" class="typeahead form-control input-md date-picker"> 
									<span class="help-block"></span>
							</div>
						</div>
						 
						 <div class="form-group">
							<label class="col-md-12 control-label" for="data_creazione_a"><spring:message text="??reporting.incarico.dataCreazioneA??" code="reporting.incarico.dataCreazioneA" /></label>
							<div class="col-md-12">
								 <input id="data_creazione_a" name="data_creazione_a"
									type="text" placeholder="" class="typeahead form-control input-md date-picker"> 
									<span class="help-block"></span>
							</div>
						</div>
						 
						<div class="form-group">
						<label class="col-md-12 control-label" for="incaricoProfessionistaEsterno"><spring:message text="??reporting.incarico.professionista??" code="reporting.incarico.professionista" /> </label>
						<div class="col-md-12">
						<select id="incaricoProfessionistaEsterno" name="incaricoProfessionistaEsterno" class="form-control">
						<option>Tutti</option>
						<c:if test="${ ltsProfessionistaEsterno != null }">
							<c:forEach items="${ltsProfessionistaEsterno}" var="oggetto">
							<option value="${ oggetto.id }">
							<c:out value="${oggetto.nome}"></c:out>&nbsp;&nbsp;<c:out value="${oggetto.cognome}"></c:out>
							</option>
							</c:forEach>
						</c:if>
						</select>
						</div>
						</div>
							
						<div class="form-group">
							<label class="col-md-12 control-label" for="statoIncarico"><spring:message text="??reporting.incarico.stato??" code="reporting.incarico.stato" /></label>
							<div class="col-md-12">
							<select id="statoIncarico" name="statoIncarico" class="form-control">
							<option>TUTTI</option>
							<c:if test="${ statoIncarico != null }">
							<c:forEach items="${statoIncarico}" var="oggetto">
								<option value="${ oggetto.codGruppoLingua }">
									<c:out value="${oggetto.descrizione}"></c:out>
								</option>
							</c:forEach>
							</c:if>
							</select>
							</div>
						</div>
						
 
						
							</fieldset> 
							<div class="modal-footer">
								<button style="float:left" type="button" class="btn btn-primary waves-effect" onclick="pulisciCampi(this)"><spring:message text="??reporting.incarico.btn.clear??" code="reporting.incarico.btn.clear" /></button>
								<button type="submit" class="btn btn-primary" ><spring:message text="??reporting.incarico.btn.applicaFiltri??" code="reporting.incarico.btn.applicaFiltri" /> </button>
								 
							</div>
							</form>
					 
							</div>	


<!-- FINE RICERCA INCARICHI -->	