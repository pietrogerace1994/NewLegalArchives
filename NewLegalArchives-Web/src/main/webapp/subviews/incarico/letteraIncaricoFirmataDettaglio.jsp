<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>
<c:set var="isp" value="0"></c:set>
<c:if test="${ incaricoDettaglioView.penale }"> 
	<c:set var="isp" value="1"></c:set>
</c:if>

<!-- LETTERA INCARICO FIRMATA-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="procuraDesc" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.letterafirmata??" code="incarico.label.letterafirmata" /></label>
			<div class="col-sm-7">
				<c:if test="${ not empty incaricoDettaglioView.letteraFirmataDoc && not empty incaricoDettaglioView.letteraFirmataDoc.uuid}">
					<script>
					
					window.addEventListener("load", function(){  
						$( "#LetteraFirmataDownload" ).html('<a id="letteraFirmata" href="<%=request.getContextPath() %>/download?uuid=${incaricoDettaglioView.letteraFirmataDoc.uuid}&isp=${isp}" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;" aria-expanded="true"> <i class="fa fa-pencil-square " title="Lettera d\'Incarico Firmata presente"></i></a>');
					}); 
					</script>
					<input readonly class="form-control" value="${ incaricoDettaglioView.letteraFirmataDoc.nomeFile }"/>
				</c:if>
				<c:if test="${ empty incaricoDettaglioView.letteraFirmataDoc }">
					<script>

					window.addEventListener("load", function(){  
						$( "#LetteraFirmataDownload" ).html('');
					}); 
					</script>
					<input readonly class="form-control" value=""/>
				</c:if>
			</div>
			
			<c:if
				test="${ not empty incaricoDettaglioView.letteraFirmataDoc && not empty incaricoDettaglioView.letteraFirmataDoc.uuid }">
		    <div class="col-sm-1">
				<a
					href="<%=request.getContextPath() %>/download?uuid=${incaricoDettaglioView.letteraFirmataDoc.uuid}&isp=${isp}"
					class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
					style="float: left; position: relative !important;" target="_BLANK">
					<i class="zmdi zmdi-download icon-mini"></i>
				</a>
			
			</div>	
			</c:if>		
		</div>
	</div>
</div>