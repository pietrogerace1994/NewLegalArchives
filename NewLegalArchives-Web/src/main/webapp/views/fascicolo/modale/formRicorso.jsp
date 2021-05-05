<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>
 
	  
<form:form name="fascicoloForm" method="post"
	modelAttribute="fascicoloView" action="salva.action" 
	cssClass="form-horizontal la-form">
	<engsecurity:token regenerate="false"/>
	<!-- RICORSO -->
	<div class="form-group">
		<div class="col-md-12">
			<label for="comboRicorso" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.ricorso??"
					code="fascicolo.label.ricorso" /></label>
			<div class="col-sm-10">
				<form:select path="ricorsoCode" id="comboRicorso"
					class="form-control" onchange="selezionaRicorso(this.value)">
					<form:option value="">
						<spring:message
							text="??fascicolo.label.selezionaRicorso??"
							code="fascicolo.label.selezionaRicorso" />
					</form:option>
					<c:if test="${ fascicoloView.listaRicorso!= null }">
						<c:forEach items="${fascicoloView.listaRicorso}" var="oggetto">
							<form:option value="${ oggetto.vo.codGruppoLingua }">
								<c:out value="${oggetto.vo.descrizione}"></c:out>
							</form:option>
						</c:forEach>
					</c:if>
				</form:select>
			</div>
		</div>
	</div> 
	 
	<!-- ORGANO GIUDICANTE -->
	<div class="form-group">
		<div class="col-md-12">
			<label for="comboOrganoGiudicante" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.organoGiudicante??"
					code="fascicolo.label.organoGiudicante" /></label>
			<div class="col-sm-10">
				<form:select id="comboOrganoGiudicante" path="organoGiudicanteCode"
					cssClass="form-control">
					<form:option value="">
						<spring:message
							text="??fascicolo.label.selezionaOrganoGiudicante??"
							code="fascicolo.label.selezionaOrganoGiudicante" />
					</form:option>
					<c:if test="${ fascicoloView.listaOrganoGiudicante!= null }">
						<c:forEach items="${fascicoloView.listaOrganoGiudicante}" var="oggetto">
							<form:option value="${ oggetto.vo.codGruppoLingua }">
								<c:out value="${oggetto.vo.nome}"></c:out>
							</form:option>
						</c:forEach>
					</c:if>
				</form:select>
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="col-md-12">
			<label for="foro" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.foro??" code="fascicolo.label.foro" /></label>
			<div class="col-sm-10">
				<form:input cssClass="form-control" path="foro" id="txtForo" />
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="col-md-12">
			<label for="numeroRegistroCausa" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.numeroRegistroCausa??"
					code="fascicolo.label.numeroRegistroCausa" /></label>
			<div class="col-sm-10">
				<form:input path="numeroRegistroCausa" id="numeroRegistroCausa" cssClass="form-control" />
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="col-md-12">
			<label for="note" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.note??" code="fascicolo.label.note" /></label>
			<div class="col-sm-10">
				<form:textarea path="note" id="note" cols="70" rows="8"
					cssClass="form-control" />
			</div>
		</div>
	</div>

	
</form:form>
<script type="text/javascript">
if ($("#txtForo")) {
	autocompleteForo("txtForo");
}
</script>	 
