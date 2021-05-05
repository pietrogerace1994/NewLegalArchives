<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
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
                        		<div class="card-header"><h1><spring:message code="fascicolo.label.cronFascicolo"text="??fascicolo.label.cronFascicolo??"></spring:message><br/><br> <small>${fascicoloRicercaView.nome}</small></h1>
                                	<div class="row">
 										<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
                    <br> 
                    <div class="panel-group" role="tablist" aria-multiselectable="true">
                                <div class="panel panel-collapse">
                                    <div class="panel-heading" role="tab" id="headingOne">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                               <spring:message text="??fascicolo.label.cronApertoIl??" code="fascicolo.label.cronApertoIl" /> ${fascicoloRicercaView.dataApertura}                                              
											<spring:message text="??fascicolo.label.cronDa??" code="fascicolo.label.cronDa" /> ${fascicoloRicercaView.legaleInterno} <span class="label label-success pull-right"><i class="fa fa-check-circle"></i></span> 
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseOne" class="collapse" role="tabpanel" aria-labelledby="headingOne">
                                        <div class="panel-body">
                                        <c:if test="${ not empty fascicoloRicercaView.dataUltimaModifica}">
                                        <span>
											<spring:message code="fascicolo.label.cronUltimaModifica"text="??fascicolo.label.cronUltimaModifica??"></spring:message> ${fascicoloRicercaView.dataUltimaModifica} 
										<br></span>
										</c:if>
                                            <a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/fascicolo/dettaglio.action?id=${fascicoloRicercaView.fascicoloId}')}">
                                            <spring:message text="??fascicolo.label.cronVediDettaglio??" code="fascicolo.label.cronVediDettaglio" />
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                
                                <c:forEach items="${fascicoloRicercaView.listaIncarichiCron}"var="oggetto" varStatus="theCounti">
                                <div class="panel panel-collapse">
                                    <div class="panel-heading" role="tab" id="headingThree">
                                        <h4 class="panel-title">
                                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree${theCounti.index}" aria-expanded="false" aria-controls="collapseThree${theCount.index+1}">
                                            <spring:message text="??fascicolo.label.cronIncaricoAutorizzatoIl??" code="fascicolo.label.cronIncaricoAutorizzatoIl" /> ${oggetto.dataAutorizzazione} 
                                            <c:if test="${not empty oggetto.utenteAutorizzante}">
                                             <spring:message text="??fascicolo.label.cronDa??" code="fascicolo.label.cronDa" /> ${ oggetto.utenteAutorizzante }
                                             </c:if>
                                             <span class="label label-success pull-right"><i class="fa fa-check-circle"></i></span>														
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseThree${theCounti.index}" class="collapse" role="tabpanel" aria-labelledby="headingThree">
                                        <div class="panel-body">
                                         <spring:message text="??fascicolo.label.cronIncaricoAutorizzatoAlLegaleEsterno??" code="fascicolo.label.cronIncaricoAutorizzatoAlLegaleEsterno" />  ${ oggetto.utenteAutorizzato }	 <a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/professionistaEsterno/visualizzaProfEst.action')}"><spring:message text="??fascicolo.label.cronIncaricoVediDettaglioLegale??" code="fascicolo.label.cronIncaricoVediDettaglioLegale" /></a>
                                        </div>
                                    </div>
                            	 </div>
                                </c:forEach>
                                   
                                    
                                    
                                <c:forEach items="${fascicoloRicercaView.listaProformaCron}"var="oggetto" varStatus="theCount">
                                <div class="panel panel-collapse">
                                    <div class="panel-heading" role="tab" id="headingFour">
                                        <h4 class="panel-title">
                                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFour${theCount.index}" aria-expanded="false" aria-controls="collapseFour${theCount.index}">
                                           	<spring:message text="??fascicolo.label.proforma??" code="fascicolo.label.proforma" /> N° ${theCount.index+1}
                                             <c:if test="${oggetto.autorizzato}"> 
                                             <spring:message text="??fascicolo.label.autorizzataIl??" code="fascicolo.label.autorizzataIl" /> ${oggetto.dataAutorizzazione} 
                                             <span class="label label-success pull-right">
                                             <i class="fa fa-check-circle"></i></span>
                                             </c:if>
                                             <c:if test="${!oggetto.autorizzato}"> 
                                             	<c:if test="${oggetto.stato =='B'}"> 
                                            		<spring:message text="??fascicolo.label.inBozza??" code="fascicolo.label.inBozza" />
                                            	</c:if>
                                             	<c:if test="${oggetto.stato =='APAP'}"> 
                                            		<spring:message text="??fascicolo.label.inAttesaDiPreApprovazione??" code="fascicolo.label.inAttesaDiPreApprovazione" />
                                            	</c:if>
                                             	<c:if test="${oggetto.stato =='APAP1'}"> 
                                            		<spring:message text="??fascicolo.label.inAttesaDiApprovazione??" code="fascicolo.label.inAttesaDiApprovazione" />
                                            	</c:if>
                                             	<c:if test="${oggetto.stato =='APAP2'}"> 
                                            		<spring:message text="??fascicolo.label.inAttesaDiApprovazione2??" code="fascicolo.label.inAttesaDiApprovazione2" />
                                            	</c:if>
                                             	<c:if test="${oggetto.stato =='AAUT'}"> 
                                            		<spring:message text="??fascicolo.label.inAttesaDiAutorizzazione??" code="fascicolo.label.inAttesaDiAutorizzazione" />
                                            	</c:if>                                            	                                            	                                     	                                            	 
                                             <span class="label label-danger pull-right">
                                             <i class="fa fa-times-circle"></i></span>
                                             </c:if>
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseFour${theCount.index}" class="collapse" role="tabpanel" aria-labelledby="headingFour">
                                        <div class="panel-body">
                                            <a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/proforma/dettaglio.action?id=${oggetto.idProforma}')}">
                                            <spring:message text="??fascicolo.label.cronVediDettaglio??" code="fascicolo.label.cronVediDettaglio" />
                                            </a>
                                        </div>
                                    </div>
                            	</div>
                                </c:forEach>
                                    

                             <%-- <a href="#modalDefault" id="add-comments" data-toggle="modal" class="btn btn-float btn-info m-btn" style="margin-bottom:30px;margin-right:15px;"> <i class="fa fa-envelope"></i> </a> </div>
                                <!-- Modal Default -->
                           		<div class="modal fade" id="modalDefault" tabindex="-1" role="dialog" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                        </div>
                                        <div class="modal-body">
                                            
                                            <form class="form-horizontal">
                                            	<!-- Form Name -->
													<legend>Fascicolo N.123456 Snam RG contro A2A</legend>
														<div class="input-group">
                                        					<span class="input-group-addon"><i class="fa fa-user"></i></span>
                                        						<div class="fg-line">
                                                					<input type="text" class="form-control" placeholder="invia un messaggio a">
                                        						</div>
                                    					</div>
                                                        <p></p>
                                                        <div class="input-group">
                                                        	<span class="input-group-addon"><i class="fa fa-list-ol"></i></span>
                                                            <div class="fg-line">
                                                                <div class="select">
                                                                    <select class="form-control">
                                                                        <option>Select an Option</option>
                                                                        <option>Aperto</option>
                                                                        <option>Modificato</option>
                                                                        <option>Approvazione incarico</option>
                                                                        <option>Approvazione Proforma N.1</option>
                                                                        <option>Approvazione Proforma N.2</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <p></p>
                                                        <div class="input-group">
                                                        	<span class="input-group-addon"><i class="fa fa-envelope-o"></i></span>
                                                            <div class="fg-line">
                                                                <textarea class="form-control auto-size" placeholder="Inserisci il messaggio da inviare"></textarea>
                                                            </div>
                                                        </div>
 --%>
                                                   


                                                
                                            </form>

                                            
                                            
                                            
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-link">Invia</button>
                                            <button type="button" class="btn btn-link" data-dismiss="modal">Chiudi</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                           

                                                
                                        
                                        
                                        
                                        
                                        
                                        </div>
                                     
 
 
        
        
                                  
                                        
                                       

                    
                                        
                                        
                                        
                                        
                                   
                                	</div>                            
                            </div><!-- CARD -->
            			</div><!--/ fine col-2 -->
                    </div><!-- / ROW-->
                </div><!-- CONTAINER -->
                
                
          </section> 

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include> 

    </body>
  </html>
