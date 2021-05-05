<%@page import="eng.la.util.costants.Costanti"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<c:set var="displayE" value="none"></c:set>
<c:if test="${ not empty beautyContestReplyView.offertaEconomicaDoc }"> 
	<c:set var="displayE" value="inline"></c:set>
</c:if>

<c:set var="displayT" value="none"></c:set>
<c:if test="${ not empty beautyContestReplyView.offertaTecnicaDoc }"> 
	<c:set var="displayT" value="inline"></c:set>
</c:if>

<div class="container">
	<div class="row">
		<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

			<div class="card">
				
				<div class="card-body">
					
					<form:form name="beautyContestReplyForm" method="post"
						modelAttribute="beautyContestReplyView" action="salva.action"
						class="form-horizontal la-form">
						<engsecurity:token regenerate="false"/>
						
						<c:if test="${ not empty param.errorMessage }">
							<div class="alert alert-danger">
								<spring:message code="${param.errorMessage}"
									text="??${param.errorMessage}??"></spring:message>
							</div>
						</c:if>
						
						<form:errors path="*" cssClass="alert alert-danger" htmlEscape="false" element="div"></form:errors>
						
						<div id="sezioneMessaggiApplicativi"></div>
						
						<!-- DATA INVIO -->
						<div class="list-group lg-alt">
							<div class="list-group-item media">
								<div class="media-body media-body-datetimepiker">
									<div class="form-group">
										<label for="dataInvio" class="col-sm-2 control-label"><spring:message
												text="??beautyContest.label.dataInvio??"
												code="beautyContest.label.dataInvio" /></label>
										<div class="col-sm-10">
											<form:input path="dataInvio" cssClass="form-control" disabled="true"/>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<!-- DESCRIZIONE OFFERTA TECNICA-->
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="descrizioneSow" class="col-sm-2 control-label">
										<spring:message text="??beautyContest.label.descrizioneRisposta??" code="beautyContest.label.descrizioneRisposta" />
									</label>
									<div class="col-sm-10">
										<form:textarea path="descrizioneOffertaTecnica" cssClass="form-control" rows="8" cols="70" id="descrizioneOffertaTecnica" disabled="true"/>
									</div>
								</div>
							</div>
						</div>
						
						
						<!--OFFERTA ECONOMICA-->
						<div class="list-group-item media">
							<div class="media-body">
								<div class="form-group">
									<label for="offertaEconomicadesc" class="col-sm-2 control-label">
										<spring:message text="??beautyContest.label.offertaEconomica??" code="beautyContest.label.offertaEconomica" />
									</label>
									<div class="col-sm-10">
										<span class="input-symbol-euro"> 
											<input readonly name="offertaEconomica" id="offertaEconomica"
											style="width: 20%; margin-left: 5px;" placeholder=""
											type="number"  value="${beautyContestReplyView.offertaEconomica}" min="0" step="1.00"
											data-number-to-fixed="2" data-number-stepfactor="100"
											class="form-control currency"/>
										</span>
									</div>
								</div>
							</div>
						</div>
						
						<!-- Offerta Tecnica File -->
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="headingProcura">
								<h3 class="panel-title">
									<button type="button" data-toggle="collapse"
										data-target="#boxOffertaTecnica"
										class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini collapsed"
										style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
										aria-expanded="false">
										<i class="zmdi zmdi-collection-text icon-mini"></i>
									</button>
									<a id="offertaTecnicaGraffa"
										style="display: ${displayT}; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
										aria-expanded="true"> <i class="fa fa-paperclip "></i>
									</a> <a data-toggle="collapse" data-parent="#accordion"
										href="#boxOffertaTecnica" aria-expanded="false"
										aria-controls="boxProcura"> <spring:message
											text="??beautyContest.label.offertaTecnica??"
											code="beautyContest.label.offertaTecnica" />
									</a>

								</h3>
							</div>
							<div id="boxOffertaTecnica" class="panel-collapse collapse"
								role="tabpanel" aria-labelledby="headingProcura">
								<jsp:include page="/subviews/beautyContest/offertaTecnica.jsp">
								</jsp:include>
							</div>
						</div>
												
						
						<!-- Offerta Economica File -->
						<div class="panel panel-default">
							<div class="panel-heading" role="tab"
								id="headingVerificaAnticorruzione">
								<h3 class="panel-title">
									<button type="button" data-toggle="collapse"
										data-target="#boxOffertaEconomica"
										class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini collapsed"
										style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
										aria-expanded="false">
										<i class="zmdi zmdi-collection-text icon-mini"></i>
									</button>
									<a id="offertaEconomicaGraffa"
										style="display: ${displayE}; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
										aria-expanded="true"> <i class="fa fa-paperclip "></i>
									</a> <a data-toggle="collapse" data-parent="#accordion"
										href="#boxOffertaEconomica" aria-expanded="false"
										aria-controls="boxVerificaAnticorruzione"> <spring:message
											text="??beautyContest.label.offertaEconomica??"
											code="beautyContest.label.offertaEconomica" />
									</a>
								</h3>
							</div>
							<div id="boxOffertaEconomica"
								class="panel-collapse collapse" role="tabpanel"
								aria-labelledby="headingOffertaEconomica">
								<jsp:include
									page="/subviews/beautyContest/offertaEconomica.jsp">
								</jsp:include>
							</div>
						</div>
												
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>