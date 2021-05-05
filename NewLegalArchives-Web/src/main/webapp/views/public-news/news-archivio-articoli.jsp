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
											<spring:message
												text="??publicNews.box.header.titoloArticoli??"
												code="publicNews.box.header.titoloArticoli" />
								</h1>
                            </div>
                            <div class="card-body">
                                    <div class="section ricerca card-padding">
                                        
                                           <div class="col-md-12">
                                              <div class="form-group fg-line col-md-10"> 
                                                  <label for="search"></label>

													<input class="form-control input-sm" id="search"
														placeholder="<spring:message code="publicNews.box.header.cercaArticoli" />..."
														type="text" value="<c:if test="${!empty testoCercato}"><c:out value="${testoCercato}" /></c:if>">
										</div>
                                              <div class="form-group fg-line col-md-2"> 
                                               <button type="button" onclick="portalenews.filtra_search_articoli()" class="btn cerca bgm-black btn-sm m-t-10 waves-effect" id="tastoCerca">
												<spring:message text="??publicNews.box.label.cerca??"
										code="publicNews.box.label.cerca" />
												</button>
                                              </div>
                                           </div>

                                           <div class="col-md-2">
                                           <div class="form-group fg-line col-md-2"> 
          	   								 <c:if test="${ ltsAnni != null && not empty ltsAnni }">
                                            <label> <spring:message text="??publicNews.box.label.anno??"
										code="publicNews.box.label.anno" /> </label>
                                            <select name="anno" id="anno">
                                            <c:forEach items="${ltsAnni}" var="anno">
                                             <option value="${anno}" <c:if test="${ annoSelezionato eq anno }"> selected</c:if>>${anno}</option>
                                            </c:forEach> 
                                            </select> 
                                            </c:if>
                                          </div>
                                          </div>
                                          <div class="col-md-10">
                                           <div class="form-group fg-line col-md-12 filtri row"> 
                                           <label> <spring:message text="??publicNews.box.label.categoria??"
										code="publicNews.box.label.categoria" /> </label>
                                           
                                           	<c:if test="${!empty ltsCategoriaPadre }">
                                           	<c:set var="vCat" value="" />
                                           	<c:forEach items="${ltsCategoriaPadre}" var="oggetto">
                                           		<c:if test="${ oggetto.id == idCatPadre}">
                                           			<c:set var="vCat" value="1" />
                                           		</c:if>
                                           	</c:forEach>
                                           	<c:choose>
                                           		<c:when test="${vCat == '' }">
                                           		 <button type="button" onclick="portalenews.archivio_articoli()"  class="btn filtro btn-color0 btn-sm m-t-10 waves-effect active">tutte</button>
                                           		</c:when>
                                           		<c:otherwise>
                                           		 <button type="button" onclick="portalenews.archivio_articoli()"  class="btn filtro btn-color0 btn-sm m-t-10 waves-effect">tutte</button>
                                           		</c:otherwise>
                                           	</c:choose>
												<c:forEach items="${ltsCategoriaPadre}" var="oggetto">
													<c:choose>
														<c:when test="${oggetto.id == idCatPadre }">
														<button type="button" id="${ oggetto.id }" onclick="portalenews.seleziona_categoria_articoli(this)" class="btn filtro btn-${oggetto.css} btn-sm m-t-10 waves-effect active"><c:out value="${oggetto.nomeCategoria}"></c:out></button>	
														</c:when>
														<c:otherwise>
														<button type="button" id="${ oggetto.id }" onclick="portalenews.seleziona_categoria_articoli(this)" class="btn filtro btn-${oggetto.css} btn-sm m-t-10 waves-effect"><c:out value="${oggetto.nomeCategoria}"></c:out></button>	
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:if>
                                     
                                             
                                          </div>
										<!-- SottoCategoria -->
										<div class="form-group fg-line col-md-10 row">
										<input type="hidden" id="idCatPadre" value="${idCatPadre}">
											<c:if
												test="${ ltsSottoCategorie != null && not empty ltsSottoCategorie }">
												<label> <spring:message
														text="??publicNews.box.label.sottocategoria??"
														code="publicNews.box.label.sottocategoria" />
												</label>
												<select id="sottoCategoria" name="sottoCategoria" onchange="portalenews.filtra_sottocategoria_articoli(this)">
													<option value="0"></option>
													<c:forEach items="${ltsSottoCategorie}" var="oggetto">
														<option value="${ oggetto.id }"
															<c:if test="${ sottoCategoriaSelezionata eq oggetto.id }"> selected</c:if>>
															<c:out value="${oggetto.nomeCategoria}"></c:out>
														</option>
													</c:forEach>
												</select>
											</c:if>
										</div>
										</div>

								
                                      
                                    </div>
                                    <div class="section2">
                                        <div class="col-md-12">
                                       
                                            <c:if test="${!empty articoliCustom }">
                                            	
											   <c:if test="${ ltsArticoli != null }">
												<c:forEach items="${ltsArticoli}" var="article">

													<div
														class="content-news color5 col-md-12 col-lg-12 col-sm-12 bordered_bottom">
														<c:choose>
															<c:when test="${!empty sottoCategoriaSelezionata }">
															<span>${article.sottoCategoria.nomeCategoria}</span>
															</c:when>
															<c:otherwise>
															<span>${article.categoria.nomeCategoria}</span>
															</c:otherwise>
														</c:choose>
														<h5 onclick="portalenews.open_article('${article.id}')" class="pointer">${article.oggetto}</h5>
														<p>${article.contenutoBreve}</p>
													</div>

												</c:forEach>
												</c:if>
											 
												
                                            </c:if>
										<c:if test="${ empty ltsArticoli }">
											<div
													class="content-news color5 col-md-12 col-lg-12 col-sm-12 bordered_bottom">
													<p>
														<spring:message code="publicNews.box.zero.risultati" />
													</p>
												</div>
										</c:if>

									</div>
                                        
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
											       <li class="first" aria-disabled="false"><a data-page="1" class="button pagination-archive-articoli">«</a></li>
                                                   <li class="prev" aria-disabled="false"><a data-page="${paginaSelezionata-1}" class="button pagination-archive-articoli">&lt;</a></li>
												</c:otherwise>
												</c:choose>
												<c:if test="${ articoliCustom != null }">
                                            	<c:forEach var="npg" begin="1" end="${totalePagina}">
                                            	 	<li class="page-1 <c:if test="${ paginaSelezionata eq npg }"> active</c:if>" aria-disabled="false" aria-selected="true"><a data-page="${npg}" class="button pagination-archive-articoli">${npg}</a></li>
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
											    	<li class="next" aria-disabled="false"><a data-page="${paginaSelezionata+1}" class="button pagination-archive-articoli">&gt;</a></li>
                                                	<li class="last" aria-disabled="false"><a data-page="${totalePagina}" class="button pagination-archive-articoli">»</a></li>
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
                       
                        <!-- FORM -Archivio-Filtrato -->
                        <form id="form-archivio-filtrato" action="<%=request.getContextPath()%>/public/news/archivio-filtrato.action" style="display: none;" method="post">
                        <input type="hidden" value="001" name="CSRFToken" id="CSRFToken">
                        <input id="archivio" name="archivio" value="">
                        <input type="submit" value="" style="display: none;">
                        </form>
                        
                        
                    </div><!-- / ROW-->
                </div><!-- CONTAINER -->
                </div>
                <jsp:include page="/views/public-news/parts/news-disclaimer.jsp"></jsp:include>
            </section><!-- SECTION CONTENT-->
            
 <script type="text/javascript">
 function openArchivio(a){
	 
	var form= document.getElementById("form-archivio-filtrato");
	form.archivio.value=a.id;
	form.submit();
	
 }
 $(document).ready(function() {
	 $('#search').keyup(function(e){
		    if(e.keyCode == 13)
		    {
		        $('#tastoCerca').trigger("click");
		    }
		}); 
 });
 
 </script>         
          
     </section><!--/SECTION MAIN--><!-- #BeginLibraryItem "/Library/footer.lbi" -->	
 	<!-- FOOTER -->
	<jsp:include page="/views/public-news/parts/news-footer.jsp"></jsp:include>

	<!-- END FOOTER -->  
	    </body>
    </html>	