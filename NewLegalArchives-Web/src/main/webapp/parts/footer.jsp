<%@page import="java.util.Properties"%>
<%@page import="java.io.InputStream"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.util.CurrentSessionUtil"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


                
<footer id="footer">
              	<div class="visible-md-block visible-lg-block" style="color:transparent;position:absolute;top:0px;left:0px;z-index:100">
					<%
										String version = "n.d.";
										InputStream is = null;
										try{
											Properties p = new Properties();
									        is = request.getServletContext().getResourceAsStream("/META-INF/maven/eng.la/NewLegalArchives-Web/pom.properties");
									        if (is != null) {
									            p.load(is);
									            version = p.getProperty("version");
									        } 
										}catch(Throwable e){
											e.printStackTrace();
										}finally{
											if( is != null ) is.close(); 
										}
										%>
					<%="Versione: " + version +" - Nodo: "+ System.getProperty("server.name") %> - <%=((CurrentSessionUtil)SpringUtil.getBean("currentSessionUtil")).toString() %>
				</div>

            	<div class="container-fluid">
            	<!-- engsecurity VA -->
            	 
            	
                	<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12" style="position:inherit !important;">
                			Copyright &copy; 2015 Legal Archives
                            <ul class="f-menu">
                                <li><a href="/" onclick="return false;">Home</a></li>
                                <li><a href="/" onclick="return false;">Fascicoli</a></li>
                                <li><a href="/" onclick="return false;">Atti</a></li>
                                <li><a href="/" onclick="return false;">Incarichi</a></li>
                                <li><a href="/" onclick="return false;">Gestione Costi</a></li>
                                <li><a href="/" onclick="return false;">Report</a></li>
                                <li><a href="/" onclick="return false;">Archivi</a></li>
                                <li><a href="/" onclick="return false;">Anagrafica</a></li>
                            </ul>
                      
                    </div>
                </div>
            </footer>
          <!--/ FOOTER --><!-- #EndLibraryItem --><!-- Modal Default -->
			<div class="modal fade" id="modalDefault" tabindex="-1" role="dialog" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header"><h4 class="modal-title">Condividi</h4></div>
                        <div class="modal-body">
                        		<p class="text-left">Indica l'indirizzo email o il nome della persona con cui vuoi condividere il file</p>
                                    <form class="form-horizontal">
                                    <engsecurity:token regenerate="false"/>
                                            <fieldset>
                                            <!-- Button Drop Down -->
                                            <div class="form-group">
                                              <label class="col-md-2 control-label" for="buttondropdown"></label>
                                              <div class="col-md-10">
                                                <div class="input-group">
                                                  <input id="buttondropdown" name="buttondropdown" class="form-control" placeholder="inserisci il nome o l'indirizzo di posta" type="text">
                                                  <div class="input-group-btn">
                                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                                      Action
                                                      <span class="caret"></span>
                                                    </button>
                                                    <ul class="dropdown-menu pull-right">
                                                      <!-- <li><a href="javascript:{legalSecurity.attachForm('#')}">via notifica</a></li> -->
                                                      <li><a href="#">via notifica</a></li>
                                                     <!--  <li><a href="javascript:{legalSecurity.attachForm('#')}">via posta elettroniva</a></li>  -->                                                
                                                      <li><a href="#">via posta elettroniva</a></li> 
                                                    </ul>
                                                  </div>
                                                </div>
                                              </div>                                   
                                            </div>
                                            
                                          </fieldset>
                                            </form>
       
                                           
                                           
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-link">Condividi</button>
                          <button type="button" class="btn btn-link" data-dismiss="modal">Chiudi</button>
                        </div>
                    </div>
              </div>
	</div>  

    
    <!-- Per AGENDA -->    
    <jsp:include page="/views/agenda/modalDettaglioNotifica.jsp"></jsp:include>     

<!-- Page Loader -->
  <!--      <div class="page-loader palette-Teal bg">
            <div class="preloader pl-xl pls-white">
                <svg class="pl-circular" viewBox="25 25 50 50">
                    <circle class="plc-path" cx="50" cy="50" r="20"/>
                </svg>
            </div>
        </div>-->
<!--/ Page Loader -->

