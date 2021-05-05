<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ page import="eng.la.util.costants.Costanti"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<!DOCTYPE html lang="${language}">
<!--[if IE 9 ]><html class="ie9"><![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Legal Archives</title>

<jsp:include page="/parts/script-init.jsp">
</jsp:include>

</head>
<body data-ma-header="teal">
	<jsp:include page="/parts/header.jsp">
	</jsp:include>
	<!-- SECION MAIN -->
	<section id="main">

		<jsp:include page="/parts/aside.jsp">
		</jsp:include>
		<!-- SECTION CONTENT -->
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						<div class="card">
							<div class="card-header">
								<h1>
									Votazioni
								</h1> 
							</div>
							<div class="card-body">
							
							<div class="container">	
		 						<div class="row">
		 							<div class="col-sm-2" ></div>
		 							<div class="col-sm-2" >Legale interno:</div>
		 							<div class="col-sm-8" >
		 								<span class="label label-default">${vendorManagementView.legaleInternoNominativoUtil}</span>
		 							</div>
		 						</div>
		 					</div>
							 	
							<br>
							<br>
							
			    <select size="5" cssClass="form-control">
			             <c:forEach
							items="${vendorManagementView.listaNazioni}"
							var="oggetto">
							<form:option value="${ oggetto.vo.codGruppoLingua }">
								<c:out value="${oggetto.vo.descrizione}"></c:out>
							</form:option>
						</c:forEach>				
							
				 </select>
							
	<div class="container">
	
		
		<c:if test="${vendorManagementView.nessunaVotazione eq true}">
			
				<div class="row">
				 	<div class="col-sm-12" style="text-align:center" >
				 		 <span class="label label-warning">Nessuna Votazione</span>
				 	</div>
				</div>
			 
		</c:if>
		<c:if test="${vendorManagementView.nessunaVotazione eq false}">
			
		
		
		 <div class="row">
		 	<div class="col-sm-1" ></div>
		 	
		 	<div class="col-sm-3" style="font-weight: normal; background-color:orange;">Semestre</div>
		    <div class="col-sm-1" style="font-weight: normal; background-color:orange;">Capacita</div>
		    <div class="col-sm-1" style="font-weight: normal; background-color:orange;">Competenza</div>
		    <div class="col-sm-1" style="font-weight: normal; background-color:orange;">Costi</div>
		    <div class="col-sm-1" style="font-weight: normal; background-color:orange;">Flessibilita</div>
		    <div class="col-sm-1" style="font-weight: normal; background-color:orange;">Tempi</div>
		    <div class="col-sm-1" style="font-weight: normal; background-color:orange;">Reperibilita</div>
		    
		     <div class="col-sm-1" style="font-weight: normal; background-color:orange;">TOTALE</div>
		    
		    <div class="col-sm-1" ></div>
		</div>
		
		
		<c:forEach 
		items="${vendorManagementView.sixweeksStr}"
		var="oggetto"
		varStatus="stato">
		
		 <div class="row">
		 	<div class="col-sm-1" style="font-weight: normal;"></div>
		 
		 	<div class="col-sm-3" style="font-weight: normal;">${oggetto.left} - ${oggetto.right}</div>
		    <div class="col-sm-1" style="font-weight: normal;">
		      	 ${vendorManagementView.listaSommeCapacita[stato.index]}
		    </div>
		    <div class="col-sm-1" style="font-weight: normal;">
		     	 ${vendorManagementView.listaSommeCompetenza[stato.index]}
		    </div>
		    <div class="col-sm-1" style="font-weight: normal;">
		     	 ${vendorManagementView.listaSommeCosti[stato.index]}
		    </div>
		    <div class="col-sm-1" style="font-weight: normal;">
		     	 ${vendorManagementView.listaSommeFlessibilita[stato.index]}
		    </div>
		    <div class="col-sm-1" style="font-weight: normal;">
		     	 ${vendorManagementView.listaSommeTempi[stato.index]}
		    </div>
		    <div class="col-sm-1" style="font-weight: normal;">
		     	 ${vendorManagementView.listaSommeReperibilita[stato.index]}
		    </div>
		    
		    <div class="col-sm-2" style="font-weight: normal;">
		    	 ${vendorManagementView.listaSommeTOTALE[stato.index]}
		    </div>
		    
		 </div>
		
       </c:forEach>
     

		 <div class="row">
		 	<div class="col-sm-1" ></div>
		 	
		 	<div class="col-sm-3" style="font-weight:bold; background-color:orange;"></div>
		    <div class="col-sm-1" style="font-weight:bold; background-color:orange;"></div>
		    <div class="col-sm-1" style="font-weight:bold; background-color:orange;"></div>
		    <div class="col-sm-1" style="font-weight:bold; background-color:orange;"></div>
		    <div class="col-sm-1" style="font-weight:bold; background-color:orange;"></div>
		    <div class="col-sm-1" style="font-weight:bold; background-color:orange;"></div>
		    <div class="col-sm-1" style="font-weight:bold; background-color:orange;"></div>
		    
		     <div class="col-sm-1" style="font-weight:bold; background-color:orange;">${vendorManagementView.totale}</div>
		    
		    <div class="col-sm-1" ></div>
		</div>
		
	</c:if>	

	</div><!-- container -->
	
	
<br>							
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>
	
	

	
	 

<script charset="UTF-8" type="text/javascript">
	</script>

	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/vendormanagement/visualizza.js"></script>
	
</body>
</html>
