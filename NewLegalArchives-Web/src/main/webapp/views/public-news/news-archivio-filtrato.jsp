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
                <div class="container internal-page">
                	<div class="row">
                		<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12 connectedSortable">
                        
                        <!-- MY DOCS -->
                        
                         <!-- Tabs -->
                        <div class="card">
                            <div class="card-header cw-header palette-Amber bg">
                                <h1><img src="<%=request.getContextPath()%>/portal-news/portal/img/ico_archivio_header.png"> 
								<spring:message text="??publicNews.box.header.titolo??" code="publicNews.box.header.titolo" />
								</h1>
                            </div>
                            <div class="card-body">
                                    <div class="section ricerca card-padding">
                                         <!--form role="form"-->
                                           <div class="col-md-12">
                                              <div class="form-group fg-line col-md-10"> 
                                                  <label for="search"></label>
                                                  <input class="form-control input-sm" id="search" placeholder="Cerca newsletter..." type="text">
                                              </div>
                                              <div class="form-group fg-line col-md-2"> 
                                               <button type="submit"  onclick="portalenews.filtra_search()"  class="btn cerca bgm-black btn-sm m-t-10 waves-effect"><spring:message text="??publicNews.box.label.cerca??" code="publicNews.box.label.cerca" /></button>
                                              </div>
                                           </div>
                                            <div class="col-md-2">
                                               <div class="form-group fg-line col-md-2"> 
                                            <c:if test="${ ltsAnni != null && not empty ltsAnni }">
                                            <label><spring:message text="??publicNews.box.label.anno??" code="publicNews.box.label.anno" /></label>
                                            <select name="anno" id="anno" onchange="portalenews.filtra_categoria_anno(this)">
                                            <c:forEach items="${ltsAnni}" var="anno">
                                             <option value="${anno}" <c:if test="${ annoSelezionato eq anno }"> selected</c:if>>${anno}</option>
                                            </c:forEach> 
                                            </select> 
                                            </c:if>
                                              </div>
                                            </div>
                                            <div class="col-md-10">
                                                   <div class="form-group fg-line col-md-12 filtri row"> 
                                                   <label>
										<spring:message text="??publicNews.box.label.categoria??" code="publicNews.box.label.categoria" />			
													</label>
                                                   
                                             <c:if test="${ ltsCategoriaPadre != null }">
                                           	<button type="button" onclick="portalenews.categoria_tutte()" class="btn filtro btn-color0 active btn-sm m-t-10 waves-effect">tutte</button>
												<c:forEach items="${ltsCategoriaPadre}" var="oggetto">
												  
											 <button type="button" id="${ oggetto.id }" onclick="portalenews.seleziona_categoria(this)" class="btn filtro btn-${oggetto.css} btn-sm m-t-10 waves-effect <c:if test="${ idCatPadre eq oggetto.id }"> active </c:if>"><c:out value="${oggetto.nomeCategoria}"></c:out></button>	
											 
												</c:forEach>
											</c:if>
                                                
                                                  </div>
                                                  <!-- SottoCategoria -->
                                                    <div class="form-group fg-line col-md-12 row"> 
                                                    <input type="hidden" id="idCatPadre" value="${idCatPadre}">
                                                     <c:if test="${ ltsSottoCategorie != null && not empty ltsSottoCategorie }">
                                                    <label>
													<spring:message text="??publicNews.box.label.sottocategoria??" code="publicNews.box.label.sottocategoria" />
													</label>
                                                     <select id="sottoCategoria" name="sottoCategoria" onchange="portalenews.filtra_sottocategoria(this)">
														<option value="0"></option>
														<c:forEach items="${ltsSottoCategorie}" var="oggetto">
															<option value="${ oggetto.id }" <c:if test="${ sottoCategoriaSelezionata eq oggetto.id }"> selected</c:if>>
																<c:out value="${oggetto.nomeCategoria}"></c:out>
															</option>
														</c:forEach>
                                                        </select>
                                                    </c:if>
                                                   </div>
                                           </div>
                                            
                                       <!-- form-->
                                    </div>
                                    <div class="section2 elenco-news">
                                            <ul>
                                            
                             <c:if test="${ ltsNewsletterArticles != null && not empty ltsNewsletterArticles}">

									<c:forEach
										items="${ltsNewsletterArticles}"
										var="oggetto">
										<li><a href="javascript:void(0)" onclick="portalenews.open_newsletter('${oggetto.newsletter.id}')"> <img
												src="<%=request.getContextPath()%>/portal-news/portal/img/lista_rassegna_bianca.png"><span>
												<c:if test="${not empty oggetto.newsletter.dataCreazione}">
					                            <fmt:formatDate pattern="dd/MM/yyyy" value="${oggetto.newsletter.dataCreazione}"/>
					                            </c:if>
												 
													- </span> ${oggetto.newsletter.titolo}
										</a> <c:if test="${ oggetto.articolos != null }">
												<c:forEach items="${oggetto.articolos}" var="article">
													<div class="content-news color5 col-md-12 col-lg-12 col-sm-12">
														<span>
												<c:choose>
												<c:when test = "${not empty article.sottoCategoria && not empty article.sottoCategoria.nomeCategoria}">
												    ${article.sottoCategoria.nomeCategoria}
												</c:when>
												<c:otherwise>
												 ${article.categoria.nomeCategoria}
												</c:otherwise>
												</c:choose>
														</span>
														<h5 onclick="portalenews.open_article('${article.id}')" class="pointer">${article.oggetto}</h5>
														<p>${article.contenutoBreve}</p>
													</div>

												</c:forEach>
											</c:if></li>

									</c:forEach>

								</c:if>
                                            
                               
                                            </ul>
                                        </div>  
                                        
                                    <div class="row">
                                                <div class="col-sm-12">
                                             <ul class="pagination">
												<c:choose>
												<c:when test = "${paginaSelezionata lt 2}">
												    <li class="first disabled" aria-disabled="true"><a data-page="first" class="button">«</a></li>
                                                    <li class="prev disabled" aria-disabled="true"><a data-page="prev" class="button">&lt;</a></li>
												</c:when>
												<c:otherwise>
											       <li class="first" aria-disabled="false"><a data-page="1" class="button pagination-archive-filtrato">«</a></li>
                                                   <li class="prev" aria-disabled="false"><a data-page="${paginaSelezionata-1}" class="button pagination-archive-filtrato">&lt;</a></li>
												</c:otherwise>
												</c:choose>
												<c:if test="${ ltsNewsletterArticles != null && not empty  ltsNewsletterArticles }">
                                            	<c:forEach var="npg" begin="1" end="${totalePagina}">
                                            	 <li class="page-1 <c:if test="${ paginaSelezionata eq npg }"> active</c:if>" aria-disabled="false" aria-selected="true"><a data-page="${npg}" class="button pagination-archive-filtrato">${npg}</a></li>
                                            	</c:forEach>
                                            	</c:if>
                                                 <c:choose>
												 <c:when test = "${totalePagina lt 2}">
												<li class="next disabled" aria-disabled="true"><a data-page="next" class="button">&gt;</a></li>
                                                <li class="last disabled" aria-disabled="true"><a data-page="last" class="button">»</a></li>
												 </c:when>
												 <c:when test = "${totalePagina eq paginaSelezionata}">
												<li class="next disabled" aria-disabled="true"><a data-page="next" class="button">&gt;</a></li>
                                                <li class="last disabled" aria-disabled="true"><a data-page="last" class="button">»</a></li>
												 </c:when>
												 <c:otherwise>
											    <li class="next" aria-disabled="false"><a data-page="${paginaSelezionata+1}" class="button pagination-archive-filtrato">&gt;</a></li>
                                                <li class="last" aria-disabled="false"><a data-page="${totalePagina}" class="button pagination-archive-filtrato">»</a></li>
												 </c:otherwise>
												 </c:choose>   
											   </ul>
                                                </div>
                                        </div>
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
          
          
     </section><!--/SECTION MAIN--><!-- #BeginLibraryItem "/Library/footer.lbi" -->	
 	<!-- FOOTER -->
	<jsp:include page="/views/public-news/parts/news-footer.jsp"></jsp:include>

	<!-- END FOOTER --> 
	    </body>
    </html>	 