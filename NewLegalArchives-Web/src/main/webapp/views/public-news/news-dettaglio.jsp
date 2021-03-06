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

			<!-- SECTION CONTENT -->
            <section id="content">
                <div class="container">
                	<div class="row">
                		<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12 connectedSortable">
                        
                        <!-- MY DOCS -->
                        
                         <!-- Tabs -->
                        <div class="card">
                            <div class="card-header cw-header palette-Amber bg">
                                <h2>
                                <c:if test="${not empty articolo.dataCreazione}">
	                            <fmt:formatDate pattern="dd/MM/yyyy" value="${articolo.dataCreazione}"/>
	                            </c:if>
                                </h2>
                                <h1><!--${newsletter.titolo }--></h1>
                            </div>
                            <div class="card-body">
                            <c:if test="${not empty articolo.dataCreazione}">
                            <input type="hidden" id="anno" value="<fmt:formatDate pattern="yyyy" value="${articolo.dataCreazione }"/>">
                            </c:if>
                            <input type="hidden" id="idCatPadre" value="${articolo.categoria.id}">
                                    <div class="section2 dettaglio">
                                            <h3 id="${articolo.categoria.id}" onclick="portalenews.seleziona_categoria(this)" class="pointer" >${articolo.categoria.nomeCategoria }</h3>
                                           <div class="content-news color2 col-md-12 col-lg-12 col-sm-12">
                                               <span onclick="portalenews.filtra_sottocategoriaFromDettaglio('${articolo.sottoCategoria.id }')" class="pointer">${articolo.sottoCategoria.nomeCategoria }</span>
                                               <h5>${articolo.oggetto }</h5>
                                               <div class=" col-md-12 col-lg-12 col-sm-12">
                                             
                                               <div class=" col-md-12 col-lg-12 col-sm-12">
                                               ${articolo.contenuto }
                                               </div>
                                             
                                             
                                        </div>
                                        
                                        </div>
                            </div> 
                              
                             <c:if test="${not empty allegati}">
                              <div class=" col-md-12 col-lg-12 col-sm-12" style="font-size:11px;background: #d3f1ff;">Scarica Allegati</div>
                              <div class="section2 dettaglio" style="padding-top: 15px;">
                                <c:forEach items="${allegati}" var="oggetto">
<<<<<<< .mine
                                <!-- MODIFICA LA ROCCA -->
                                <!-- download?onlyfn=1 PRIMA DELLA MODIFICA--> 
                                <a href="<%=request.getContextPath()%>/download?public=1&onlyfn=1&isp=0&uuid=${oggetto.uuid}"  class="waves-effect">
||||||| .r83485
                                <a href="<%=request.getContextPath()%>/download?onlyfn=1&isp=0&uuid=${oggetto.uuid}"  class="waves-effect">
=======
                                <a href="<%=request.getContextPath()%>/download?public=1&onlyfn=1&isp=0&uuid=${oggetto.uuid}"  class="waves-effect">
>>>>>>> .r87886
                                <button type="button" class="btn filtro btn-color6 btn-sm m-t-10 waves-effect">
                                  ${oggetto.nomeFile }  
                                </button>
                                </a> 
                               </c:forEach> 
                             
                                </div>  
                             </c:if> 
                                                 
                        </div>
                            <a href="#" title="torna all'inizio del contenuto" class="ScrollTop js-scrollTop js-scrollTo" style="display: block;">
                                    <span>top</span><img src="<%=request.getContextPath()%>/portal-news/portal/img/top.png">
                                </a>
                        </div><!--/ fine col-1 -->
                    </div><!-- / ROW-->
                </div><!-- CONTAINER -->
                </div>
                <jsp:include page="/views/public-news/parts/news-disclaimer.jsp"></jsp:include>
            </section><!-- SECTION CONTENT-->
          
        </section><!--/SECTION MAIN--><!-- #BeginLibraryItem "/Library/footer.lbi" -->	<!-- FOOTER -->   
    	<!-- STRAT FOOTER -->
	<jsp:include page="/views/public-news/parts/news-footer.jsp"></jsp:include>

	<!-- END FOOTER -->  
	    </body>
    </html>	