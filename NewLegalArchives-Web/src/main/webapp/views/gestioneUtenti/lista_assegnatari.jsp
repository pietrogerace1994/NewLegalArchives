<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>


<!-- ******************************************* -->
<!-- DARIO ************************************* -->
<!-- ******************************************* -->

<h4><b><spring:message text="??listaAssegnatari.label.selezionareDestinatario??"
					 code="listaAssegnatari.label.selezionareDestinatario" /></b></h4>
 
 <div name="wf_assegnatari_list" style=" display: block;  min-height: 70px; max-height: 140px;  overflow-y: auto; -ms-overflow-style: -ms-autohiding-scrollbar;">
	 <table class="table table-striped table-condensed table-hover" >
	    <tbody>
	    
	    	<c:if test="${!empty assegnatari}">
 
 				<c:forEach items="${assegnatari}" var="utente">
 
 
 					<c:set var="icon_class" scope="page" 
 								value="${utente.isTopResponsabile()?'zmdi-account-circle':
 										 utente.isPrimoRiporto()?'zmdi-account':
 										 utente.isResponsabile()?'zmdi-account-o':'zmdi-accounts-outline'}"/>
 					 					
 					<tr data-resp = "${empty utente.vo.getResponsabileUtil() ? 'N':utente.vo.getResponsabileUtil()}" 
 						data-code="${utente.vo.getMatricolaUtil()}">
	        			<td>${utente.vo.getMatricolaUtil()}</td>
	       				<%-- <td>${utente.vo.getNominativoUtil()}</td> --%>
	       				
	       				<td>${fn:replace(utente.vo.getNominativoUtil(), ',', ' ')}</td>
	       				
	       				<td style='text-align:center;'><span class="zmdi ${icon_class}"></td>
	      			</tr>
 					
 
 				</c:forEach>
 
 			</c:if>
	      
	   	</tbody>
	  </table>
	</div>  
 <br>
 
 
 <!-- <div name="wf_assegnatari_list" style=" display: block;  min-height: 70px; max-height: 140px;  overflow-y: auto; -ms-overflow-style: -ms-autohiding-scrollbar;">

	 <table class="table table-striped table-condensed table-hover">
	   
	    <tbody>
	    
	      <tr data-resp = "Y" data-code="1">
	        <td>John</td>
	        <td>Doe</td>
	        <td>john@example.com</td>
	      </tr>
	      
	      <tr data-resp = "N" data-code="2" class="info">
	        <td>Mary</td>
	        <td>Moe</td>
	        <td>mary@example.com</td>
	      </tr>
	      
	      <tr data-resp = "Y" data-code="3">
	        <td>July</td>
	        <td>Dooley</td>
	        <td>july@example.com</td>
	      </tr>
	      
	       <tr data-resp = "Y" data-code="11">
	        <td>July</td>
	        <td>Dooley</td>
	        <td>july@example.com</td>
	      </tr>
	      
	       <tr data-resp = "Y" data-code="12">
	        <td>July</td>
	        <td>Dooley</td>
	        <td>july@example.com</td>
	      </tr>
	      
	       <tr data-resp = "N" data-code="13">
	        <td>July</td>
	        <td>Dooley</td>
	        <td>july@example.com</td>
	      </tr>
	      
	       <tr data-resp = "Y" data-code="21">
	        <td>July</td>
	        <td>Dooley</td>
	        <td>july@example.com</td>
	      </tr>
	      
	       <tr data-resp = "N" data-code="22">
	        <td>July</td>
	        <td>Dooley</td>
	        <td>july@example.com</td>
	      </tr>
	      
	       <tr data-resp = "Y" data-code="23">
	        <td>July</td>
	        <td>Dooley</td>
	        <td>july@example.com</td>
	      </tr>
	   	</tbody>
	  </table>
	</div>   -->
 
 