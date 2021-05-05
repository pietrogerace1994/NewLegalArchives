<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="tipoJoinVenture" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.tipoJoinVenture??" code="fascicolo.label.tipoJoinVenture" /></label>
			<div class="col-sm-10">
				<spring:message
					text="??fascicolo.label.joinVenture??" code="fascicolo.label.joinVenture" var="joinVentureLabel" />
				<spring:message
					text="??fascicolo.label.compravendita??" code="fascicolo.label.compravendita" var="compravenditaLabel" />
 				<form:radiobutton path="tipoJoinVenture" value="SI" label="${joinVentureLabel }"/>
 				<form:radiobutton path="tipoJoinVenture" value="NO" label="${compravenditaLabel }"/>
			</div>
		</div>
	</div>
</div>

<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="partnerId" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.partner??" code="fascicolo.label.partner" /></label>
			<div class="col-sm-10">

				<form:select path="partnerId" cssClass="form-control">
					<form:option value="">
						<spring:message text="??fascicolo.label.selezionaPartner??"
							code="fascicolo.label.selezionaPartner" />
					</form:option>

					<c:if test="${ fascicoloView.listaSocieta != null }">
						<c:forEach items="${fascicoloView.listaSocieta}" var="oggetto">
							<form:option value="${ oggetto.vo.id }">
								<c:out value="${oggetto.vo.nome}"></c:out>
							</form:option>
						</c:forEach>
					</c:if>
				</form:select>
			</div>
		</div>
	</div>
</div>

<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="target" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.target??" code="fascicolo.label.target" /></label>
			<div class="col-sm-10">
 				<form:input path="target" cssClass="form-control"/>
			</div>
		</div>
	</div>
</div>
