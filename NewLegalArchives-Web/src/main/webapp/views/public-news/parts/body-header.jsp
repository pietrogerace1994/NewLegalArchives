<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<header id="header" class="media">
            <div class="pull-left h-logo">
                <a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/public/news/home.action','post')}" class="hidden-xs">
                    <spring:message text="??publicNews.header.titolo??"
										code="publicNews.header.titolo" />
                </a>
                
                <div class="menu-collapse" data-ma-action="sidebar-open" data-ma-target="main-menu">
                    <div class="mc-wrap">
                        <div class="mcw-line top palette-White bg"></div>
                        <div class="mcw-line center palette-White bg"></div>
                        <div class="mcw-line bottom palette-White bg"></div>
                    </div>
                </div>
            </div>
</header><!-- #EndLibraryItem --><!-- SECION MAIN -->
