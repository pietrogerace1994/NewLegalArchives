<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld" %>
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
		
        <!-- Vendor CSS --> 
        <link href="vendors/fullcalendar-2.8.0/fullcalendar.min.css" rel="stylesheet">
        <link href="vendors/animate/animate.min.css" rel="stylesheet">
        <link href="vendors/material-design-iconic-font/css/material-design-iconic-font.min.css" rel="stylesheet">
        <link href="vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css" rel="stylesheet">                
        <link href="vendors/google-material-color/dist/palette.css" rel="stylesheet">
        <link href="vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <link href="vendors/bootgrid/jquery.bootgrid.css" rel="stylesheet">  
		<link href="vendors/sweetalert-master/dist/sweetalert.css" rel="stylesheet">                 
        <link href="vendors/bootstrap-datetimepicker-master/build/css/bootstrap-datetimepicker.min.css" rel="stylesheet"> 
        <link href="vendors/bootstrap-tagsinput-latest/src/bootstrap-tagsinput.css" rel="stylesheet">                  
<!--         <link href='https://fonts.googleapis.com/css?family=Roboto+Condensed|Open+Sans+Condensed:300' 
        integrity="sha256-3qW4ateC6558tGLRWsq+qmD3CFV1pLK//L4zLXqP4Ys= sha384-DwByqcKVpUMGlJMGwM7TjV1R64OCDNosD2IlwJoGVcYtvTya5C0NuugoKJ0O4s4U sha512-SM0JWwSt2nFeJDW7KbPB3QmedizkG+Dlml3w3r64UXSzuZB0FkXhXWrSkqRtyvV05FM3vw1QWLMOdcbEi1B1cQ==" 
		crossorigin="anonymous"
        rel='stylesheet' type='text/css'> -->
        
        <!-- CSS -->
        <link href="portal/css/legal.css" rel="stylesheet">
        <link href="portal/css/legal-custom.css" rel="stylesheet"> 
          <link href="portal/css/themes.css" rel="stylesheet">   
        
    </head>
    <body data-ma-header="teal"><!-- #BeginLibraryItem "/Library/header.lbi" --> <header id="header" class="media">
    	<jsp:include page="/parts/header.jsp">
		</jsp:include>
    
            <div class="pull-left h-logo">
                <a href="index.html" class="hidden-xs">
                    Legal Archives   
                    <small>your daily desktop</small>
                </a>
                
                <div class="menu-collapse" data-ma-action="sidebar-open" data-ma-target="main-menu">
                    <div class="mc-wrap">
                        <div class="mcw-line top palette-White bg"></div>
                        <div class="mcw-line center palette-White bg"></div>
                        <div class="mcw-line bottom palette-White bg"></div>
                    </div>
                </div>
            </div>
            <ul class="pull-right h-menu">
            	<li class="hm-search-trigger">
                    <a href="" data-ma-action="search-open">
                        <i class="hm-icon zmdi zmdi-search"></i>
                    </a>
                </li>
                <li class="dropdown hidden-xs hidden-sm h-apps">
                    <a data-toggle="dropdown" href="">
                     <button class="btn btn-success btn-lg btn-icon-text">cerca in</button>
					</a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href=""><i class="palette-Red-400 bg fa fa-folder-open-o" aria-hidden="true"></i><small>Fascicoli</small></a></li>
                        <li><a href=""><i class="palette-Green-SNAM bg fa fa-gavel"></i><small>Atti</small></a></li>
                        <li><a href=""><i class="palette-Light-Blue bg fa fa-user"></i><small>Incarichi</small></a></li>
                        <li><a href=""><i class="palette-Orange-400 bg fa fa-line-chart"></i><small>Costi</small></a></li>
                        <li><a href=""><i class="palette-Green-SNAM bg fa fa-archive"></i><small>Archivi</small></a></li>
                        <li><a href=""><i class="palette-Blue-Grey bg fa fa-modx"></i><small>Tutto</small></a></li>
                    </ul>
                </li>
                <li class="dropdown hidden-xs">
                    <a data-toggle="dropdown" href=""><i class="hm-icon fa fa-cogs"></i></a>
                    <ul class="dropdown-menu dm-icon pull-right">
                        <li class="hidden-xs"><a data-action="fullscreen" href=""><i class="zmdi zmdi-fullscreen"></i> modalit?? schermo intero</a></li>
                        <li><a data-action="clear-localstorage" href=""><i class="zmdi zmdi-delete"></i> Cancella Cronologia del Browser</a></li>                      
                        <li><a href=""><i class="zmdi zmdi-settings"></i> Personalizza il tema</a>
                        	<div class="header-colors">
                                <div class="hc-item palette-Teal bg selected" data-ma-header-value="teal"></div>
                                <div class="hc-item palette-Blue bg" data-ma-header-value="blue"></div>
                                <div class="hc-item palette-Cyan bg" data-ma-header-value="cyan-600"></div>
                                <div class="hc-item palette-Green bg" data-ma-header-value="green"></div>
                                <div class="hc-item palette-LightGreen-SNAM bg" data-ma-header-value="lightgreen"></div>
                                <div class="hc-item palette-Blue-Grey bg" data-ma-header-value="bluegrey"></div>
                                <div class="hc-item palette-Orange bg" data-ma-header-value="orange"></div>
                                <div class="hc-item palette-Purple-400 bg" data-ma-header-value="purple-400"></div>
                                <div class="hc-item palette-Red-400 bg" data-ma-header-value="red-400"></div>
                                <div class="hc-item palette-Pink-400 bg" data-ma-header-value="pink-400"></div>
                                <div class="hc-item palette-Brown bg" data-ma-header-value="brown"></div>
                                <div class="hc-item palette-Grey-600 bg" data-ma-header-value="grey-600"></div>
							</div>                        
                        </li>
                    </ul>
                </li>
                <li class="hm-alerts" data-user-alert="sua-messages" data-ma-action="sidebar-open" data-ma-target="user-alerts">
                    <a href=""><i class="hm-icon zmdi zmdi-notifications"></i></a>
                </li>
            </ul>
            <div class="media-body h-search">
                <form class="p-relative" onSubmit="javascript:document.location.href=ricerca_fasciolo.html" style="margin-top:5px">
                    <input type="text" class="hs-input" placeholder="Cerca Atti, Fascicoli, Parcelle, Incarichi, Fornitori"> 
                    <i class="zmdi zmdi-search hs-reset" data-ma-action="search-clear"></i>
                </form>
            </div>
        </header><!-- #EndLibraryItem --><!-- SECION MAIN -->
        <section id="main"><!-- #BeginLibraryItem "/Library/alerts.lbi" -->

<aside id="s-user-alerts" class="sidebar">
                <ul class="tab-nav tn-justified tn-icon m-t-10" data-tab-color="teal">                    
                    <li><a class="sua-notifications" href="#sua-notifications" 	data-toggle="tab"><i class="fa fa-bell-o" aria-hidden="true"></i></a></li>
                    <li><a class="sua-tasks" 		href="#sua-tasks" 			data-toggle="tab"><i class="fa fa-tasks" aria-hidden="true"></i></a></li>
                    <li><a class="sua-messages" 	href="#sua-messages" 		data-toggle="tab"><i class="fa fa-calendar-o" aria-hidden="true"></i></a></li>
                    <li><a class="sua-shotcuts" 	href="#sua-shortcuts" 		data-toggle="tab"><i class="fa fa-plus" aria-hidden="true"></i></a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane fade" id="sua-messages">
                        <ul class="sua-menu list-inline list-unstyled palette-Light-Blue bg">
                            <li><a href=""><i class="fa fa-flag-checkered" aria-hidden="true"></i> seleziona tutti</a></li>
                            <li><a href=""><i class="fa fa-eye" aria-hidden="true"></i> vedi tutte</a></li>
                            <li><a href="" data-ma-action="sidebar-close"><i class="fa fa-times-circle" aria-hidden="true"></i> chiudi</a></li>
                        </ul>
                        <div class="list-group lg-alt c-overflow">
                            <a href="" class="list-group-item media">                               
                                <div class="media-body">
                                    <div class="lgi-heading">Udienza al Tribunale di Lodi <small class="pull-right">2 luglio 2016 ore 14.00</small></div>
                                    <small class="lgi-text">Processo Snam RG contro A2A</small>
                                </div>
                            </a>
                            <a href="" class="list-group-item media">
                                <div class="media-body">
                                     <div class="lgi-heading">Udienza al Tribunale di Milano <small class="pull-right">5 luglio 2016 ore 09.30</small></div>
                                    <small class="lgi-text">Processo contro il Sig.re Bilgeri</small>
                                </div>
                            </a>
                            <a href="" class="list-group-item media">
                                <div class="media-body">
                                    <div class="lgi-heading">CDA Snam <small class="pull-right">15 luglio 2016 ore 11.00</small></div>
                                    <small class="lgi-text">Donec congue tempus ligula, varius hendrerit mi hendrerit sit amet. Duis ac quam sit amet leo feugiat iaculis</small>
                                </div>
                            </a>
                            <a href="" class="list-group-item media">
                                <div class="media-body">
                                     <div class="lgi-heading">Udienza al Tribunale di Bergamo <small class="pull-right">01 agosto 2016 ore 10.30</small></div>
                                    <small class="lgi-text">Processo contro il Sig.re Mastroianni</small>
                                </div>
                            </a>
                        </div>  
                         <a href="#" id="add-events" class="btn btn-float btn-danger m-btn">
                            <i class="zmdi zmdi-plus"></i>
                        </a>                    
                    </div>
                    <div class="tab-pane fade" id="sua-notifications">
                        <ul class="sua-menu list-inline list-unstyled palette-Orange bg">
                            <li><a href=""><i class="fa fa-microphone-slash" aria-hidden="true"></i> non disturbare</a></li>
                            <li><a href=""><i class="fa fa-eye" aria-hidden="true"></i> vedi tutte</a></li>
                            <li><a href="" data-ma-action="sidebar-close"><i class="fa fa-times-circle" aria-hidden="true"></i> chiudi</a></li>
                        </ul>
                        <div class="list-group lg-alt c-overflow">
                            <a href="#" class="list-group-item media">                            
                                <div class="media-body">
                                    <div class="lgi-heading">30 giugno 2016</div>
                                    <small class="lgi-text"><strong>In attesa di Approvazione</strong><br/> Parcella N??23456 ??? Incarico Avv. Fabio Todarello <br/> Fascicolo 1001</small>
                                    <div class="notifiche-action"><button class="btn btn-icon"><span class="zmdi zmdi-check"></span></button> <button class="btn btn-icon command-edit"><span class="zmdi zmdi-edit"></span></button></div>
                                </div>
                            </a>

                            <a href="#" class="list-group-item media">                            
                                <div class="media-body">
                                    <div class="lgi-heading">25 giugno 2016</div>
                                    <small class="lgi-text"><strong>In attesa di Approvazione</strong><br/> Incarico Avv. Fabrizio Ciccone <br/> Fascicolo 1000</small>
                                    <div class="notifiche-action"><button class="btn btn-icon"><span class="zmdi zmdi-check"></span></button> <button class="btn btn-icon command-edit"><span class="zmdi zmdi-edit"></span></button></div>
                                </div>
                            </a>

                            <a href="#" class="list-group-item media"> 
                                <div class="media-body">
                                    <div class="lgi-heading">21 giugno 2016</div>
                                    <small class="lgi-text"><strong>E' stato inserito nella Rubrica Fornitori</strong><br/> l'Avv. Mario Rossi</small>
                                </div>
                           </a>
                            
                            <a href="#" class="list-group-item media"> 
                                <div class="media-body">
                                    <div class="lgi-heading">20 giugno 2016</div>
                                    <small class="lgi-text">Il Fascicolo 2999 ?? stato aperto</small>
                                </div>
                            </a>
                                                      
                        </div>
                    </div>                   
                    <div class="tab-pane fade" id="sua-tasks">
                        <ul class="sua-menu list-inline list-unstyled palette-Green-SNAM bg">
                            <li><a href=""><i class="fa fa-flag-checkered" aria-hidden="true"></i> completati</a></li>
                            <li><a href=""><i class="fa fa-check-square-o" aria-hidden="true"></i> seleziona tutti</a></li>
                            <li><a href="" data-ma-action="sidebar-close"><i class="fa fa-times-circle" aria-hidden="true"></i> chiudi</a></li>
                        </ul>

                        <div class="list-group lg-alt c-overflow">
                         <a href="#" id="open-fascicolo" class="">
                        	<div class="list-group-item">
                                <div class="lgi-heading m-b-5">Fascicolo N.234570 <small class="pull-right">entro il 28 luglio</small></div>
                                <div class="progress">
                                    <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                                        <span class="sr-only">80% Complete (danger)</span>
                                    </div>
                                </div>
                            </div>
                         </a>
                            <div class="list-group-item">
                                <div class="lgi-heading m-b-5">Fascicolo N.234567 <small class="pull-right">entro il 30 giugno</small></div>
                                <div class="progress">
                                    <div class="progress-bar" role="progressbar" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100" style="width: 30%">
                                        <span class="sr-only">30% Complete (success)</span>
                                    </div>
                                </div>
                            </div>
                            <div class="list-group-item">
                                <div class="lgi-heading m-b-5">Fascicolo N.234568 <small class="pull-right">entro il 30 giugno</small></div>
                                <div class="progress">
                                    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                                        <span class="sr-only">80% Complete (success)</span>
                                    </div>
                                </div>
                            </div>
                            <div class="list-group-item">
                                <div class="lgi-heading m-b-5">Fascicolo N.234569 <small class="pull-right">entro il 2 luglio</small></div>

                                <div class="progress">
                                    <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                                        <span class="sr-only">20% Complete</span>
                                    </div>
                                </div>
                            </div>
                            
                        </div>                       
                    </div>
                    <div class="tab-pane fade" id="sua-shortcuts">
                       <ul class="sua-menu list-inline list-unstyled palette-Green-SNAM bg">                           
                            <li><a href="" data-ma-action="sidebar-close"><i class="fa fa-times-circle" aria-hidden="true"></i> chiudi</a></li>
                        </ul>
                           <!-- <a href="" class="list-group-item media">
                                <div class="media-body">
                                     <div class="lgi-heading">Compila lettera d'incarico</div>
                                    <small class="lgi-text">Inserisci in Legal Archives una nuova lettera</small>
                                </div>
                            </a> -->                     
                            <a href="" class="list-group-item media">
                                <div class="media-body">
                                     <div class="lgi-heading">Fast Track</div>
                                    <small class="lgi-text">Avvia l'iter di un codice 700</small>
                                </div>
                            </a>  
                                           
                    </div>
                    
                </div>
            </aside> <!-- #EndLibraryItem --><aside id="s-main-menu" class="sidebar">
				<div class="smm-header">
                    <i class="zmdi zmdi-long-arrow-left" data-ma-action="sidebar-close"></i>
                </div>
                <ul class="smm-alerts">                    
                    <li class="active" data-user-alert="sua-notifications" data-ma-action="sidebar-open" data-ma-target="user-alerts">
                        <i class="fa fa-bell-o" aria-hidden="true"></i>
                    </li>
                    <li data-user-alert="sua-tasks" data-ma-action="sidebar-open" data-ma-target="user-alerts">
                        <i class="fa fa-tasks" aria-hidden="true"></i>
                    </li>
                    <li data-user-alert="sua-messages" data-ma-action="sidebar-open" data-ma-target="user-alerts">
                       <i class="fa fa-calendar-o" aria-hidden="true"></i>
                    </li>
                    <li data-user-alert="sua-shortcuts" data-ma-action="sidebar-open" data-ma-target="user-alerts">
                       <i class="fa fa-plus" aria-hidden="true"></i>
                    </li>
               </ul><!-- #BeginLibraryItem "/Library/nav.lbi" --><!-- MAIN MENU -->
                <ul class="main-menu">
                    <li class="active">
                        <a href="index.html"><i class="zmdi zmdi-home"></i> Home</a>
                    </li>
                    <li class="sub-menu">
                        <a href="" data-ma-action="submenu-toggle"><i class="fa fa-folder-open-o" aria-hidden="true"></i> Fascicoli</a>
						<ul>
                            <li><a href="alternative-header.html">Aperti</a></li>
                            <li><a href="colored-header.html">Chiusi</a></li>
                        </ul>
                    </li>
                    <li><a href="typography.html"><i class="fa fa-gavel" aria-hidden="true"></i> Atti</a></li>                    
                    <li><a href="#"><i class="fa fa-user" aria-hidden="true"></i> Incarichi</a></li>
                    <li><a href="#"><i class="fa fa-line-chart" aria-hidden="true"></i> Gestione Costi</a></li>
                    <li><a href="#"><i class="fa fa-tachometer" aria-hidden="true"></i> Report</a></li>
                    <li><a href="archive.html"><i class="fa fa-archive" aria-hidden="true"></i> Archivi</a></li>
                </ul>
             <!-- /MAIN MENU --> <!-- #EndLibraryItem --></aside>
            <!-- / ASIDE CONTENTS -->

			<!-- SECTION CONTENT -->
            <section id="content">
                <div class="container">
                	<div class="row">
                		<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
                        	<div class="card">
                        		<div class="card-header"><h1>Ricerca Fascicolo<br/> <small>Hai cercato: fascicolo N.123456 Snam RG contro A2A </small></h2>
                            
                            
                            <p class="visible-lg visible-md text-left">Non hai trovato quello che cercavi? Prova a <a data-toggle="modal" href="#modalWider" class="">inserire ulteriori parametri per affinare la ricerca</a></p>    	
                        	<!-- Modal Large -->
                            <div class="modal fade" id="modalWider" tabindex="-1" role="dialog" aria-hidden="true">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header"><h4 class="modal-title">Migliora la ricerca</h4></div>
                                        <div class="modal-body">
                                        	<form class="form-horizontal">
                                                        <fieldset>
                                                        <!-- Form Name 
                                                        <legend>Migliora la ricerca</legend>-->
                                                            <!-- Text input-->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="legale_esterno">Legale esterno incaricato</label>  
                                                              <div class="col-md-4">
                                                              <input id="legale_esterno" name="legale_esterno" type="text" placeholder="inserisci il nominativo" class="typeahead form-control input-md">
                                                              <span class="help-block">durante la compilazione ti verranno proposte dei riferimenti</span>  
                                                              </div>
                                                            </div>
                                                            <!-- Text input-->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="controparte">Controparte</label>  
                                                              <div class="col-md-4">
                                                              <input id="controparte" name="controparte" type="text" placeholder="Inserisci il riferimento della controparte" class="typeahead form-control input-md">
                                                              <span class="help-block">durante la compilazione del campo CONTROPARTE ti verranno proposti </span>  
                                                              </div>
                                                            </div>
                                                            <!-- Select Basic -->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="selectbasic">Tipo azione</label>
                                                              <div class="col-md-4">
                                                                <select id="selectbasic" name="selectbasic" class="form-control">
                                                                  <option value="1">Accertamenti</option>
                                                                  <option value="2">Recupero crediti</option>
                                                                  <option value="">altro</option>
                                                                </select>
                                                              </div>
                                                            </div>
                                                            <!-- Select Basic -->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="selectbasic">Tipologia Atto</label>
                                                              <div class="col-md-4">
                                                                <select id="selectbasic" name="selectbasic" class="form-control">
                                                                  <option value="1">Option one</option>
                                                                  <option value="2">Option two</option>
                                                                </select>
                                                              </div>
                                                            </div>

                                                            <!-- DAL...AL -->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="selectbasic">Dal</label>
                                                              	<div class="col-md-4">                                                             
                                            						<input type='text' class="form-control date-time-picker" placeholder="dal...">
                                        						</div>
                                                            </div>
                                                             <div class="form-group">
                                                              <label class="col-md-4 control-label" for="selectbasic">Al</label>
                                                              	<div class="col-md-4">                                                             
                                            						<input type='text' class="form-control date-time-picker" placeholder="al...">
                                        						</div>
                                                            </div>
                                                            <!-- Dal...AL -->
                                                            <!-- Text input-->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="textinput">Numero Fascicolo</label>  
                                                              <div class="col-md-4">
                                                              <input id="textinput" name="textinput" type="text" placeholder="Inserisci il numero del fascicolo" class="form-control input-md">
                                                              </div>
                                                            </div>
                                                            
                                                            <!-- Button 
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="singlebutton"></label>
                                                              <div class="col-md-8">
                                                                <button id="singlebutton" name="singlebutton" class="btn btn-primary">Affina la tua ricerca</button>
                                                                <button id="singlebutton" name="singlebutton" class="btn btn-warning">Chiudi</button>
                                                              </div>
                                                            </div> -->                                                           
                                                            
                                                        </fieldset>
                                          </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-primary">Applica Filtri</button>
                                            <button type="button" class="btn btn-warning" data-dismiss="modal">Chiudi</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                                    
                                    
                                    
 
                                    
									<div class="panel-group visible-sm" id="accordion">
    									<div class="panel panel-default" id="panel1">
       										 <div class="panel-heading">
            									<h3 class="panel-title"><a data-toggle="collapse" data-target="#collapseOne" href="#collapseOne">Non hai trovato quello che stavi cercando? <strong>Prova a inserire pi?? opzioni di ricerca</strong> <i class="fa fa-plus-circle pull-right"></i></a></h3>
											</div>
        									<div id="collapseOne" class="panel-collapse collapse">
            									<div class="panel-body">                                                
                                                    <form class="form-horizontal">
                                                        <fieldset>
                                                        <!-- Form Name -->
                                                        <legend>Migliora la ricerca</legend>
                                                            <!-- Text input-->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="legale_esterno">Legale esterno incaricato</label>  
                                                              <div class="col-md-4">
                                                              <input id="legale_esterno" name="legale_esterno" type="text" placeholder="inserisci il nominativo" class="typeahead form-control input-md">
                                                              <span class="help-block">durante la compilazione ti verranno proposte dei riferimenti</span>  
                                                              </div>
                                                            </div>
                                                            <!-- Text input-->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="controparte">Controparte</label>  
                                                              <div class="col-md-4">
                                                              <input id="controparte" name="controparte" type="text" placeholder="Inserisci il riferimento della controparte" class="typeahead form-control input-md">
                                                              <span class="help-block">durante la compilazione del campo CONTROPARTE ti verranno proposti </span>  
                                                              </div>
                                                            </div>
                                                            <!-- Select Basic -->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="selectbasic">Tipo azione</label>
                                                              <div class="col-md-4">
                                                                <select id="selectbasic" name="selectbasic" class="form-control">
                                                                  <option value="1">Accertamenti</option>
                                                                  <option value="2">Recupero crediti</option>
                                                                  <option value="">altro</option>
                                                                </select>
                                                              </div>
                                                            </div>
                                                            <!-- Select Basic -->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="selectbasic">Tipologia Atto</label>
                                                              <div class="col-md-4">
                                                                <select id="selectbasic" name="selectbasic" class="form-control">
                                                                  <option value="1">Option one</option>
                                                                  <option value="2">Option two</option>
                                                                </select>
                                                              </div>
                                                            </div>
                                                            <!-- DAL...AL -->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="selectbasic">Dal</label>
                                                              	<div class="col-md-4">                                                             
                                            						<input type='text' class="form-control date-time-picker" placeholder="dal...">
                                        						</div>
                                                            </div>
                                                             <div class="form-group">
                                                              <label class="col-md-4 control-label" for="selectbasic">Al</label>
                                                              	<div class="col-md-4">                                                             
                                            						<input type='text' class="form-control date-time-picker" placeholder="al...">
                                        						</div>
                                                            </div>
                                                            <!-- Dal...AL -->
                                                            <!-- Text input-->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="textinput">Numero Fascicolo</label>  
                                                              <div class="col-md-4">
                                                              <input id="textinput" name="textinput" type="text" placeholder="Inserisci il numero del fascicolo" class="form-control input-md">
                                                              </div>
                                                            </div>
                                                            
                                                            <!-- Button -->
                                                            <div class="form-group">
                                                              <label class="col-md-4 control-label" for="singlebutton"></label>
                                                              <div class="col-md-8">
                                                                <button id="singlebutton" name="singlebutton" class="btn btn-primary">Affina la tua ricerca</button>
                                                                <button id="singlebutton" name="singlebutton" class="btn btn-warning">Chiudi</button>
                                                              </div>
                                                            </div>                                                            
                                                            
                                                        </fieldset>
                                                    </form>
                                                
                                                
                                                
                                             

                                                
                                                
                                                
                                                
                                                </div>
        									</div>
                               			</div>
                                   </div>
   


                    
                                
                                
                                </div>
						<div class="table-responsive">
                            <table id="data-table-selection" class="table table-striped table-responsive">
                                <thead>
                                    <tr>
                                        <th data-column-id="id">Numero Fascicolo</th>
                                        <th data-column-id="01">Legale Esterno Incaricato</th>
                                        <th data-column-id="02">Controparte</th>
                                        <th data-column-id="03">Anno</th>
                                        <th data-column-id="04">Tipologia Atto</th>
                                        <th data-column-id="05">Tipo Azione</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>10010</td>
                                        <td>Giuseppe Gallo</td>
                                        <td>ACEA</td>
                                        <td>2016</td>
                                        <td>atto di chiamata in causa</td>
                                        <td>contratto/distribuzione</td>
                                    </tr>
                                    <tr>
                                        <td>110238</td>
                                        <td>Giuseppe Caia</td>
                                        <td>RM Elettroservice Sas</td>
                                        <td>2016</td>
                                        <td>Atto di pignoramento o presso terzi</td>
                                        <td>Esecuzione ignoramento</td>
                                    </tr>
                                    <tr>
                                        <td>10239</td>
                                        <td>Fabio Todarello </td>
                                        <td>Roma capitale</td>
                                        <td>2016</td>
                                        <td>-</td>
                                        <td>Obbligazione Fonti</td>
                                    </tr>
                                     <tr>
                                        <td>9999</td>
                                        <td>Fabio Todarello</td>
                                        <td>Natural Gas S.r.l, Eni SpA</td>
                                        <td>2016</td>
                                        <td>Ricorso per consulenza tecnica preventiva Ex ATR 696-BIS</td>
                                        <td>Procedimenti Cautelari</td>
                                    </tr>                                    
                                </tbody>
                            </table>
                        </div>

 <div class="col-md-12 column">
             <div class="btn-group dropup pull-right ">
            	<!-- pulsante esporta senza opzioni -->
                <div class="btn-group pull-right space-to-left">
                    <button id="save-search" type="button" class="btn btn-primary dropdown-toggle" style="margin-left:5px"><i class="fa fa-save"></i> Salva ricerca</button>
                </div>
             	<!-- pulsante esporta senza opzioni -->
                        	
                <!-- pulsante esporta con opzioni -->
                <div class="btn-group pull-right">
                    <button type="button" data-toggle="dropdown" class="btn btn-success dropdown-toggle">Esporta Ricerca <i class="fa fa-arrow-circle-down"></i></button>
                    <ul class="dropdown-menu">
                       	<li><a href="#">Excel</a></li>
                        <li><a href="#">CSV</a></li>
                        <li><a href="#">...</a></li>
                    </ul>
        		</div>
                 <!-- /pulsante esporta con opzioni -->
              </div>           
            </div>      





							</div><!-- CARD -->
            			</div><!--/ fine col-2 -->
                    </div><!-- / ROW-->
                </div><!-- CONTAINER -->
            </section><!-- SECTION CONTENT-->
          
          
</section><!--/SECTION MAIN--><!-- #BeginLibraryItem "/Library/footer.lbi" -->	<!-- FOOTER -->
            <footer id="footer">
            	<div class="container-fluid">
                	<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
                			Copyright &copy; 2015 Legal Archives
                            <ul class="f-menu">
                                <li><a href="">Home</a></li>
                                <li><a href="">Fascicoli</a></li>
                                <li><a href="">Atti</a></li>
                                <li><a href="">Incarichi</a></li>
                                <li><a href="">Gestione Costi</a></li>
                                <li><a href="">Report</a></li>
                                <li><a href="">Archivi</a></li>
                            </ul>
                    </div>
                </div>
            </footer>
          <!--/ FOOTER --><!-- #EndLibraryItem --><!-- Older IE warning message -->
        <!--[if lt IE 9]>
            <div class="ie-warning">
                <h1 class="c-white">Warning!!</h1>
                <p>You are using an outdated version of Internet Explorer, please upgrade <br/>to any of the following web browsers to access this website.</p>
                <div class="iew-container">
                    <ul class="iew-download">
                        <li>
                            <a href="http://www.google.com/chrome/">
                                <img src="img/browsers/chrome.png" alt="">
                                <div>Chrome</div>
                            </a>
                        </li>
                        <li>
                            <a href="https://www.mozilla.org/en-US/firefox/new/">
                                <img src="img/browsers/firefox.png" alt="">
                                <div>Firefox</div>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.opera.com">
                                <img src="img/browsers/opera.png" alt="">
                                <div>Opera</div>
                            </a>
                        </li>
                        <li>
                            <a href="https://www.apple.com/safari/">
                                <img src="img/browsers/safari.png" alt="">
                                <div>Safari</div>
                            </a>
                        </li>
                        <li>
                            <a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie">
                                <img src="img/browsers/ie.png" alt="">
                                <div>IE (New)</div>
                            </a>
                        </li>
                    </ul>
                </div>
                <p>Sorry for the inconvenience!</p>
            </div>   
        <![endif]-->

        <!-- Javascript Libraries -->
        <script src="/vendors/jquery/jquery.min.js"></script>
        <script src="/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="/vendors/jquery/jquery-ui.min.js"></script>        
        <script src="/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.js"></script>
        
        <script src="/vendors/jquery/waves.min.js"></script>
        <script src="/vendors/jquery/bootstrap-growl.min.js"></script>
        <script src="/vendors/moment/moment.min.js"></script>
        <script src="/vendors/fullcalendar-2.8.0/fullcalendar.min.js"></script>
        <script src="/vendors/bootgrid/jquery.bootgrid.js"></script>
        
    	<script src="/vendors/bootstrap-datetimepicker-master/src/js/bootstrap-datetimepicker.js"></script>
               
    <script src="vendors/jquery/typeahead.bundle.js"></script>
   	<script src="vendors/sweetalert-master/dist/sweetalert.min.js"></script>        
 
 
	<script src="vendors/bootstrap-tagsinput-latest/src/bootstrap-tagsinput.js"></script>  
        

<!-- Placeholder for IE9 -->
        <!--[if IE 9 ]>
        <script src="vendors/jquery-placeholder/jquery.placeholder.min.js"></script>
        <![endif]-->
        
        
        <script src="/portal/js/functions.js"></script>
        <script src="/portal/js/actions.js"></script>
        <script src="/portal/js/demo.js"></script>

        <!--<script src="js/charts.js"></script>
        <script src="js/functions.js"></script>
        <script src="js/actions.js"></script>
        <script src="js/demo.js"></script>-->
		
		<footer>
			<jsp:include page="/parts/footer.jsp">
			</jsp:include>
		</footer>
		<jsp:include page="/parts/script-end.jsp">
		</jsp:include>

	<script src="<%=request.getContextPath()%>/portal/js/controller/parteCorrelata.js"></script>

    </body>
  
  </html>