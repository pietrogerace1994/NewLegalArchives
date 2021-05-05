<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>

<jsp:include page="/views/public-news/parts/news-header.jsp"></jsp:include>

	<!-- END HEADER & MENU -->
    <body data-ma-header="teal"><!-- #BeginLibraryItem "/Library/header.lbi" --> 
    
    <jsp:include page="/views/public-news/parts/body-header.jsp"></jsp:include>
    
    <!-- #EndLibraryItem --><!-- SECION MAIN -->


	<section id="main"><!-- #BeginLibraryItem "/Library/alerts.lbi" -->
	<aside id="s-main-menu" class="sidebar">
	<jsp:include page="/views/public-news/parts/news-menu.jsp"></jsp:include>
	</aside>
 <!-- #EndLibraryItem -->
 
			<!-- SECTION CONTENT -->
            <section id="content">
                <div class="container">
                	<div class="row">
                		<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12 connectedSortable">
                        
                        <!-- MY DOCS -->
                        
                         <!-- Tabs -->
                        <div class="card">
                            <div class="card-header cw-header palette-Amber bg">
                                <h2><!-- 27 febbraio - 3 marzo 2017--></h2>
                                <h1><!-- Rassegna parlamentare--></h1>
                            </div>
                            <div class="card-body">
                                    <div class="section highlight">
                                        <span><img src="<%=request.getContextPath()%>/portal-news/portal/img/ico_highlights.png"></span>
                                        <h3>
                                        <spring:message text="??publicNews.box.slider.titolo??"
										code="publicNews.box.slider.titolo" />
										</h3>
                                       <div class="lazy slider">
									   <c:if test="${not empty ltsArticoloSlider}">
                                      	<c:forEach items="${ltsArticoloSlider}" var="articolo">
										<div>
                                          <h5 onclick="portalenews.open_article('${articolo.id}')" class="pointer">${articolo.oggetto}</h5>
                                          <p>${articolo.contenutoBreve}</p>
                                        </div>
										</c:forEach>
                                      	</c:if>
                    
                                      </div>
                                    </div>
                                    
                                      	<c:if test="${ ltsArticlePageHome != null }">
                                      	<c:forEach items="${ltsArticlePageHome}" var="oggetto">
                                      <div class="section2 ${oggetto.cssClass}">
                                      <span><img src="<%=request.getContextPath()%>/portal-news/portal/img/${oggetto.categoriaPadre.icon}"></span>
                                      <h3>${oggetto.categoriaPadre.nomeCategoria}</h3>
                                        <c:if test="${ oggetto.ltsArticolo != null }">
                                        
                                      	<c:forEach items="${oggetto.ltsArticolo}" var="articolo">
                                       <div class="content-news newnews ${oggetto.categoriaPadre.css} <c:if test="${ oggetto.articoloSize lt 2 }">col-md-12 col-lg-12 </c:if> <c:if test="${ oggetto.articoloSize gt 1 }">col-md-6 col-lg-6 </c:if> col-sm-12">
                                               <c:if test="${ articolo.sottoCategoria.nomeCategoria == null }">
                                               <span>&nbsp;&nbsp;</span>
                                               </c:if>
                                               <span>${articolo.sottoCategoria.nomeCategoria}</span>
                                               
                                               <h5 onclick="portalenews.open_article('${articolo.id}')" class="pointer">${articolo.oggetto}</h5>
                                               <p>${articolo.contenutoBreve}</p>
                                               
                                               <p>&nbsp;&nbsp;</p>
                                               <p>&nbsp;&nbsp;</p>
                                               
                                                
                                        </div>
									 	
										
                                      	</c:forEach>
                                      	<c:if test="${ oggetto.articoloSize % 2 == 1 }">
									 	<div class="content-news newnews ${oggetto.categoriaPadre.css} <c:if test="${ oggetto.articoloSize lt 2 }">col-md-12 col-lg-12 </c:if> <c:if test="${ oggetto.articoloSize gt 1 }">col-md-6 col-lg-6 </c:if> col-sm-12" >
                                               <span>&nbsp;&nbsp;</span>
                                               <h5>&nbsp;&nbsp;</h5>
                                               <p>&nbsp;&nbsp;</p>
                                               <p>&nbsp;&nbsp;</p>
                                               <p>&nbsp;&nbsp;</p> 
                                        </div>
									 	</c:if>
                                      	</c:if>
                                     
                                      </div>
                                     
                                      	</c:forEach>
                                      	</c:if>
                                      	
                             
                            </div>                       
                        </div>
                        
                        </div><!--/ fine col-1 -->
                    </div><!-- / ROW-->
                </div><!-- CONTAINER -->
                 <jsp:include page="/views/public-news/parts/news-disclaimer.jsp"></jsp:include>
            </section><!-- SECTION CONTENT-->
            
   </section><!--/SECTION MAIN--><!-- #BeginLibraryItem "/Library/footer.lbi" -->	<!-- FOOTER -->
    	<!-- STRAT FOOTER -->
	<jsp:include page="/views/public-news/parts/news-footer.jsp"></jsp:include>

	<!-- END FOOTER --> 
	   
       </body>
    </html>	        
           