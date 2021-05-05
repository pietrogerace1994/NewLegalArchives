<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>


<div id="box-secutity" style="display:none;">
	<form name="legalSecurityForm" id="legalSecurityForm" method="get" action="<%=request.getContextPath()%>/public/news/home.action" style="display:none">
		
		<input type="hidden" value="001" name="CSRFToken" id="CSRFToken">
	</form>
</div><!-- / LegalSecurityToken -->
<div class="smm-header">
                    <i class="zmdi zmdi-long-arrow-left" data-ma-action="sidebar-close"></i>
                </div>
                <ul class="main-menu">
                     <button type="button" onclick="portalenews.categoria_tutte()"  class="btn filtro btn-color0 active btn-sm m-t-10 waves-effect"></button>
                    <li class="active">
                        <a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/public/news/home.action','post')}"><img src="<%=request.getContextPath()%>/portal-news/portal/img/ico_home_hover.png"> Home</a>
                    </li>
                   <!-- CR Varianti 3 - 3.3.5.	Miglioramento della ricerca articoli pubblicati (Presidio Normativo) --> 
                  	<!--<c:if test="${ ltsCategoriaPadre != null }">
                    <button type="button" onclick="portalenews.categoria_tutte()"  class="btn filtro btn-color0 active btn-sm m-t-10 waves-effect"></button>
                   <li><a onclick="portalenews.categoria_tutte()" href="javascript:void(0)"><img src="<%=request.getContextPath()%>/portal-news/portal/img/ico_archivio.png"> Archivio newsletter</a></li>
					<c:forEach items="${ltsCategoriaPadre}" var="oggetto">
				<button type="button" id="${ oggetto.id }" onclick="portalenews.seleziona_categoria(this)" class="btn filtro btn-${oggetto.css} btn-sm m-t-10 waves-effect"></button>	
				<li><a id="${ oggetto.id }" onclick="portalenews.seleziona_categoria(this)" href="javascript:void(0)"><img src="<%=request.getContextPath()%>/portal-news/portal/img/<c:out value="${oggetto.icon}"></c:out>"> <c:out value="${oggetto.nomeCategoria}"></c:out></a></li>				
					</c:forEach>
				</c:if> -->
				
				 <button type="button" onclick="portalenews.categoria_tutte()"  class="btn filtro btn-color0 btn-sm m-t-10 waves-effect"></button>
				<li><a onclick="portalenews.categoria_tutte()" href="javascript:void(0)"><img src="<%=request.getContextPath()%>/portal-news/portal/img/ico_archivio.png"> Archivio newsletter</a></li>
				 <button type="button" onclick="portalenews.categoria_tutte()"  class="btn filtro btn-color0 btn-sm m-t-10 waves-effect"></button>
				<li><a onclick="portalenews.archivio_articoli()" href="javascript:void(0)"><img src="<%=request.getContextPath()%>/portal-news/portal/img/ico_archivio.png"> Archivio articoli</a></li>	
            
                </ul>
             <!-- /MAIN MENU --> <!-- #EndLibraryItem -->
             <!-- FORM -Archivio -->
            <form id="form-archivio-all" action="<%=request.getContextPath()%>/public/news/archivio.action" style="display: none;" method="post">
           <input type="hidden" value="001" name="CSRFToken" id="CSRFToken">
            <input type="hidden" id="cbanno" name="cbanno" value="0">
            <input type="hidden" id="cbpage" name="cbpage" value="1">
            <input type="submit" value="" style="display: none;">
            </form>   
            
			<!-- FORM -Archivio-Filtrato -->
            <form id="form-archivio-filtrato" action="<%=request.getContextPath()%>/public/news/archivio-filtrato.action" style="display: none;" method="post">
            <input type="hidden" value="001" name="CSRFToken" id="CSRFToken">
            <input type="hidden" id="archivio" name="archivio" value="">
            <input type="hidden" id="archanno" name="archanno" value="0">
            <input type="hidden" id="cbpage" name="cbpage" value="1">
            <input type="submit" value="" style="display: none;">
            </form>   
            
            			<!-- FORM -Archivio-Articoli-Filtrato -->
            <form id="form-archivio-filtrato-articoli" action="<%=request.getContextPath()%>/public/news/archivio-articoli-filtrato.action" style="display: none;" method="post">
            <input type="hidden" value="001" name="CSRFToken" id="CSRFToken">
            <input type="hidden" id="archivio" name="archivio" value="">
            <input type="hidden" id="archanno" name="archanno" value="0">
            <input type="hidden" id="cbpage" name="cbpage" value="1">
            <input type="hidden" id="cbsearch" name="cbsearch" value="">
            <input type="submit" value="" style="display: none;">
            </form>  
            
            <!-- FORM -Articles From NewsLetter -->
            <form id="form-articles-newsletter" action="<%=request.getContextPath()%>/public/news/articles-newsletter.action" style="display: none;" method="post">
            <input type="hidden" value="001" name="CSRFToken" id="CSRFToken">
            <input type="hidden" id="newsletterid" name="newsletterid" value="${newsletterid}">
            <input type="hidden" id="cbpage" name="cbpage" value="1">
            <input type="submit" value="" style="display: none;">
            </form>        
                 
        
        		<!-- FORM -Archivio-Filtrato -->
            <form id="form-archivio-filtrato-sottocategoria" action="<%=request.getContextPath()%>/public/news/archivio-filtrato-category.action" style="display: none;" method="post">
           <input type="hidden" value="001" name="CSRFToken" id="CSRFToken">
            <input type="hidden" id="archivio" name="archivio" value="0">
            <input type="hidden" id="archanno" name="archanno" value="0">
            <input type="hidden" id="archsottocategoria" name="archsottocategoria" value="0">
             <input type="hidden" id="cbpage" name="cbpage" value="1">
            <input type="submit" value="" style="display: none;">
            </form> 
                      
            
             <!-- FORM -Archivio-Articoli-Filtrato -->
            <form id="form-archivio-articoli-filtrato-sottocategoria" action="<%=request.getContextPath()%>/public/news/archivio-filtrato-articoli-category.action" style="display: none;" method="post">
           <input type="hidden" value="001" name="CSRFToken" id="CSRFToken">
            <input type="hidden" id="archivio" name="archivio" value="0">
            <input type="hidden" id="archanno" name="archanno" value="0">
            <input type="hidden" id="archsottocategoria" name="archsottocategoria" value="0">
             <input type="hidden" id="cbpage" name="cbpage" value="1">
             <input type="hidden" id="cbsearch" name="cbsearch" value="">
            <input type="submit" value="" style="display: none;">
            </form>
            
                      
            
     <!-- FORM -Search NewsLetter -->
            <form id="form-search-all" action="<%=request.getContextPath()%>/public/news/archivio.action" style="display: none;" method="post">
           <input type="hidden" value="001" name="CSRFToken" id="CSRFToken">
            <input type="hidden" id="cbsearch" name="cbsearch" value="">
            <input type="hidden" id="cbanno" name="cbanno" value="0">
            <input type="hidden" id="cbpage" name="cbpage" value="1">
            <input type="submit" value="" style="display: none;">
            </form>  
         
            
            <form id="form-search-archivio-articoli" action="<%=request.getContextPath()%>/public/news/archivio-articoli.action" style="display: none;" method="post">
           <input type="hidden" value="001" name="CSRFToken" id="CSRFToken">
            <input type="hidden" id="cbsearch" name="cbsearch" value="">
            <input type="hidden" id="cbanno" name="cbanno" value="0">
            <input type="hidden" id="cbpage" name="cbpage" value="1">
            <input type="hidden" id="categoria" name="categoria" value="">
            <input type="hidden" id="sottocategoria" name="sottocategoria" value="">
            <input type="submit" value="" style="display: none;">
            </form>   
         
            <!-- / ASIDE CONTENTS -->

			