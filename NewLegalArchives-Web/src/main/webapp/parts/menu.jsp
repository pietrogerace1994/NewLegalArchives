<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="eng.la.persistence.CostantiDAO"%>
<%@page import="eng.la.business.TipologiaFascicoloService"%>
<%@page import="eng.la.business.UtenteService"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.model.view.TipologiaFascicoloView"%>
<%@page import="java.util.List"%>
<%@page import="eng.la.model.view.UtenteView"%>
<%@page import="eng.la.util.costants.Costanti"%>
<%@page import="java.util.Properties"%>
<%@page import="java.io.InputStream"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity"%>


<%
	UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
	String urlClick = utenteService.leggiUrlClick();
%>


<%
	UtenteView utenteConnesso = (UtenteView) request.getSession()
			.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
%>

<div class="smm-header">
	<i class="zmdi zmdi-long-arrow-left" data-ma-action="sidebar-close"></i>
</div>
<legarc:isAuthorized
	nomeFunzionalita="<%=CostantiDAO.NASCONDI_A_LEG_ARC_GESTORE_ANAGRAFICA_PROCURE%>">
	<ul class="smm-alerts">
		<li class="active" data-user-alert="sua-notifications"
			data-ma-action="sidebar-open" data-ma-target="user-alerts"><i
			class="fa fa-bell-o" aria-hidden="true"></i> <span
			id="badgeNotificheCampana" class="badge badge-notifiche"></span></li>
		<li id="linkFascicolo" data-user-alert="sua-tasks"
			data-ma-target="user-alerts" data-ma-action="sidebar-open"><i
			class="fa fa-tasks" aria-hidden="true"></i></li>
		<li id="liAgendaBtn" data-user-alert="sua-messages"
			data-ma-target="user-alerts"><i class="fa fa-calendar-o"
			aria-hidden="true"></i> <span id="badgeNotificheCampanaAgenda"
			class="badge badge-notifiche-agenda"></span></li>
		<li id="linkPecMail" data-user-alert="sua-mail"
			data-ma-action="sidebar-open" data-ma-target="user-alerts"><i
			class="fa fa-envelope" aria-hidden="true"></i> <span
			id="badgeNotifichePecMail" class="badge badge-notifiche-pecMail"></span>
		</li>
	</ul>
</legarc:isAuthorized>


<legarc:isAuthorized
	nomeFunzionalita="<%=CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE%>">
	<%
		if (!utenteConnesso.isAmministratore()) {
	%>
	<ul class="smm-alerts">
		<li style="background-color: transparent;" />
		<li style="background-color: transparent;" />
		<li style="background-color: transparent;" />
		<li style="background-color: transparent;" />
	</ul>
	<%
		}
	%>
</legarc:isAuthorized>

<%
	List<TipologiaFascicoloView> listaTipologieFascicoloMenu = (List<TipologiaFascicoloView>) request
			.getSession().getAttribute("listaTipologieFascicoloMenu");
	try {
		if (listaTipologieFascicoloMenu == null) {
			TipologiaFascicoloService tipologiaFascicoloService = (TipologiaFascicoloService) SpringUtil
					.getBean("tipologiaFascicoloService");
			listaTipologieFascicoloMenu = tipologiaFascicoloService.leggi(request.getLocale(), false);
			request.getSession().setAttribute("listaTipologieFascicoloMenu", listaTipologieFascicoloMenu);
		}
	} catch (Throwable e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
%>

<%
	if (utenteConnesso != null && utenteConnesso.isAmministrativo()) {
%>
<script>
		if(window.location.href.indexOf("proforma/dettaglio.action?id")<0)
		(function(){
		window.location.href="<%=request.getContextPath()%>/parcella/index.action";
		})();
</script>
<%
	}
%>


<!-- MAIN MENU -->
<ul class="main-menu">
	<li class="active"><a
		href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/index.jsp')}"><i
			class="zmdi zmdi-home"></i> Home</a></li>
	<%-- <li class="active"><a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/index.jsp')}"><i class="zmdi zmdi-home"></i> Home</a></li>
	 --%>
	 <!-- Aggiunta voce menu Tutorial MASSIMO CARUSO -->
	<li><a
		href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/training.jsp')}"><i
			class="fa fa-book" aria-hidden="true"></i>Tutorial</a> </li>
	<!-- FINE Aggiunta voce menu Tutorial MASSIMO CARUSO -->
	
	<!-- Aggiunta voce menu Invoice Manager RIDP6U9 -->
	
	<%if(utenteConnesso.isAmministratore()){ %>
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/invoiceManager.jsp')}"><i
				class="fa fa-files-o" aria-hidden="true"></i>Invoices Manager</a> </li>
	<%} %>
	<!-- FINE Aggiunta voce menu Invoice Manager RIDP6U9 -->
	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_FASCICOLI%>">
		<li class="sub-menu"><a href="" data-ma-action="submenu-toggle">
				<i class="fa fa-folder-open-o" aria-hidden="true"></i> <spring:message
					text="??menu.label.progetti??" code="menu.label.progetti" />
		</a>

			<ul>
				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.FUNZIONALITA_FASCICOLI_NUOVO%>">

						<a
							href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/progetto/crea.action')}">

							<spring:message text="??menu.label.creaProgetto??"
								code="menu.label.creaProgetto" />
						</a>
					</legarc:isAuthorized></li>
				<li><legarc:isAuthorized
						nomeFunzionalita="<%=Costanti.FUNZIONALITA_FASCICOLI_CERCA%>">
						<%-- <a href="<%=request.getContextPath()%>/progetto/ricerca.action"> --%>
						<a
							href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/progetto/ricerca.action')}">
							<spring:message text="??menu.label.cercaProgetti??"
								code="menu.label.cercaProgetti" />
						</a>
					</legarc:isAuthorized></li>
			</ul></li>
	</legarc:isAuthorized>

	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_FASCICOLI%>">
		<li class="sub-menu"><a href="" data-ma-action="submenu-toggle"><i
				class="fa fa-folder-open-o" aria-hidden="true"></i> <spring:message
					text="??menu.label.fascicoli??" code="menu.label.fascicoli" /></a>
			<ul>
				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_FASCICOLI_NUOVO%>">
					<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/fascicolo/crea.action')}"><spring:message
								text="??menu.label.crea??" code="menu.label.crea" /></a></li>
				</legarc:isAuthorized>
				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_FASCICOLI_CERCA%>">
					<c:if test="${not empty listaTipologieFascicoloMenu}">
						<c:forEach items="${ listaTipologieFascicoloMenu }"
							var="tipologiaFascicolo">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/fascicolo/cerca.action?tipologiaFascicoloCode=${tipologiaFascicolo.vo.codGruppoLingua}')}"><spring:message
										text="??menu.label.cerca??" code="menu.label.cerca" />
									${tipologiaFascicolo.vo.descrizione}</a></li>
							<%-- <li><a href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/fascicolo/cerca.action')}"><spring:message text="??menu.label.cerca??"
							      code="menu.label.cerca" /> ${tipologiaFascicolo.vo.descrizione}</a></li>			
							 --%>

							<%--  <input name="tipologiaFascicoloCode" type="hidden" value='${tipologiaFascicolo.vo.codGruppoLingua}'>  --%>
						</c:forEach>
					</c:if>
				</legarc:isAuthorized>

				<%
					if (!utenteConnesso.isAmministratore()) {
				%>
				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/riassegna/index.action')}"><spring:message
							text="??menu.label.riassegnazionefascicoli??"
							code="menu.label.riassegnazionefascicoli" /></a></li>
				<%
					}
				%>

				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_FASCICOLI_CERCA%>">
					<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/estendiPermessi/index.action')}"><spring:message
								text="??fascicolo.label.estendiPermessiFascicoli??"
								code="fascicolo.label.estendiPermessiFascicoli" /></a></li>
				</legarc:isAuthorized>

				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_FASCICOLI_CERCA%>">
					<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/udienza/cercaUdienza.action')}"><spring:message
								text="Udienze" code="menu.label.udienze" /></a></li>
				</legarc:isAuthorized>

			</ul></li>
	</legarc:isAuthorized>

	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_ATTI%>">
		<li class="sub-menu"><a href="javascript:void(0)"
			data-ma-action="submenu-toggle"> <i class="fa fa-gavel"
				aria-hidden="true"></i>
			<spring:message text="??menu.label.atti??" code="menu.label.atti" /></a>
			<ul>
				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_ATTI_NUOVO%>">
					<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/atto/crea.action')}"><spring:message
								text="??menu.label.crea??" code="menu.label.crea" /></a></li>
				</legarc:isAuthorized>
				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_ATTI_CERCA%>">
					<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/atto/ricerca.action')}"><spring:message
								text="??menu.label.cerca??" code="menu.label.cerca" /></a></li>
				</legarc:isAuthorized>
				<!--  aggiunta pulsante ricerca atti da validare MASSIMO CARUSO -->
				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_ATTI_VALIDA%>">
					<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/atto/valida.action')}"><spring:message
								text="??menu.label.valida??" code="menu.label.valida" /></a></li>
				</legarc:isAuthorized>
			</ul></li>
	</legarc:isAuthorized>

	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_INCARICHI%>">
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/incarico/cerca.action')}"><i
				class="fa fa-user" aria-hidden="true"></i> <spring:message
					text="??menu.label.incarichi??" code="menu.label.incarichi" /> </a></li>
	</legarc:isAuthorized>

	<li class="sub-menu"><legarc:isAuthorized
			nomeFunzionalita="<%=Costanti.FUNZIONALITA_PROFORMA%>">
			<a href="" data-ma-action="submenu-toggle"> <i
				class="fa fa-line-chart" aria-hidden="true"></i> <spring:message
					text="??menu.label.gestioneCosti??" code="menu.label.gestioneCosti" />
			</a>
			<ul>
				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/proforma/cerca.action')}">
						<spring:message text="??menu.label.proforma??"
							code="menu.label.proforma" />
				</a></li>

				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/stanziamenti/index.action')}">
						<spring:message text="??menu.label.stanziamenti??"
							code="menu.label.stanziamenti" />
				</a></li>

			</ul>
		</legarc:isAuthorized></li>

	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_SCHEDE_FONDO_RISCHI%>">

		<li class="sub-menu"><a href="" data-ma-action="submenu-toggle">
				<i class="fa fa-pie-chart" aria-hidden="true"></i> <spring:message
					text="??menu.label.pfr??" code="menu.label.pfr" />
		</a>

			<ul>
				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_AMMINISTRAZIONE%>">
					<li><a href="javascript:void(0)"> <a
							href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/schedaFondoRischi/visualizzaPFR.action')}">
								<spring:message text="??menu.label.prospettoFondoRischi??"
									code="menu.label.prospettoFondoRischi" />
						</a></li>
				</legarc:isAuthorized>
				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/schedaFondoRischi/cercaPFR.action')}">
						<spring:message text="??menu.label.schedeFondoRischi??"
							code="menu.label.schedeFondoRischi" />
				</a></li>
			</ul></li>
	</legarc:isAuthorized>

	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_ESTRAZIONE_DATI%>">
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/reporting/index.action')}"><i
				class="fa fa-braille" aria-hidden="true"></i> <spring:message
					text="??menu.label.estrazioneDati??"
					code="menu.label.estrazioneDati" /></a></li>
	</legarc:isAuthorized>



	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_REPORT%>">

		<li><a href="<%=urlClick%>" target="_blank"><i
				class="fa fa-tachometer" aria-hidden="true"></i> <spring:message
					text="??menu.label.repor??" code="menu.label.repor" /> </a></li>
	</legarc:isAuthorized>

	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_ARCHIVI%>">
		<li class="sub-menu"><a href="" data-ma-action="submenu-toggle">
				<i class="fa fa-database" aria-hidden="true"></i>
			<spring:message text="??menu.label.archivi??"
					code="menu.label.archivi" />
		</a>
			<ul>

				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_ARCHIVI_PARTE_CORRELATA%>">
					<li class="sub-menu"><a href=""
						data-ma-action="submenu-toggle"> <spring:message
								text="??menu.label.partecorrelata??"
								code="menu.label.partecorrelata" /></a>
						<ul>
							<%-- 	<li class="sub-menu"><a href="" data-ma-action="submenu-toggle"><i
								class="fa fa-folder-open-o" aria-hidden="true"></i> Gestione</a>
									<ul>
										<li><a href="<%=request.getContextPath()%>/parteCorrelata/crea.action">Crea</a></li>
										<li><a href="<%=request.getContextPath()%>/parteCorrelata/modificaCancella.action">Modifica/cancellazione</a></li>
									</ul>
								</li>--%>
							<%-- 						<li><a href="<%=request.getContextPath()%>/parteCorrelata/elenco.action">Visualizza</a></li> --%>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/parteCorrelata/gestioneParteCorrelata.action')}"><spring:message
										text="??menu.label.gestione??" code="menu.label.gestione" /></a></li>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/parteCorrelata/ricercaParteCorrelata.action')}"><spring:message
										text="??menu.label.cerca??" code="menu.label.cerca" /></a></li>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/parteCorrelata/storicoParteCorrelata.action')}"><spring:message
										text="??menu.label.storico??" code="menu.label.storico" /></a></li>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/parteCorrelata/uploadMassivoPartiCorrelate.action')}"><spring:message
										text="??parteCorrelata.label.uploadMassivo??" code="parteCorrelata.label.uploadMassivo" /></a></li>
						</ul></li>
				</legarc:isAuthorized>

				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_ARCHIVI_AUTORITA_GIUDIZIARIA%>">
					<li class="sub-menu"><a href=""
						data-ma-action="submenu-toggle"> <spring:message
								text="??menu.label.autoritagiudiziaria??"
								code="menu.label.autoritagiudiziaria" /></a>
						<ul>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/autoritaGiudiziaria/creazioneAutoritaGiudiziaria.action')}"><spring:message
										text="??menu.label.gestione??" code="menu.label.gestione" /></a></li>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/autoritaGiudiziaria/ricercaAutoritaGiudiziaria.action')}"><spring:message
										text="??menu.label.cerca??" code="menu.label.cerca" /></a></li>
						</ul></li>
				</legarc:isAuthorized>

				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_ARCHIVI_DUE_DILIGENCE%>">
					<li class="sub-menu"><a href=""
						data-ma-action="submenu-toggle"> <spring:message
								text="??menu.label.duediligence??"
								code="menu.label.duediligence" /></a>
						<ul>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/duediligence/creazioneDueDiligence.action')}"><spring:message
										text="??menu.label.gestione??" code="menu.label.gestione" /></a></li>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/duediligence/ricercaDueDiligence.action')}"><spring:message
										text="??menu.label.cerca??" code="menu.label.cerca" /></a></li>
						</ul></li>
				</legarc:isAuthorized>

				<legarc:isAuthorized
					nomeFunzionalita="<%=CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE%>">
					<li class="sub-menu"><a href=""
						data-ma-action="submenu-toggle"> <i class="fa fa-book"
							aria-hidden="true"></i> <spring:message
								text="??menu.label.anagraficaProcure??"
								code="menu.label.anagraficaProcure" />
					</a>

						<ul>
							<li class="sub-menu"><a href=""
								data-ma-action="submenu-toggle"> <spring:message
										text="??menu.label.repertorio.attribuzioni.standard??"
										code="menu.label.repertorio.attribuzioni.standard" /></a>
								<ul>
									<li><legarc:isAuthorized
											nomeFunzionalita="<%=Costanti.REPERTORIO_STANDARD_W%>">
											<a
												href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/repertorioStandard/crea.action')}">
												<spring:message
													text="??menu.label.repertorio.attribuzioni.crea??"
													code="menu.label.repertorio.attribuzioni.crea" />
											</a>
										</legarc:isAuthorized></li>
									<li><legarc:isAuthorized
											nomeFunzionalita="<%=Costanti.REPERTORIO_STANDARD_R%>">
											<a
												href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/repertorioStandard/ricerca.action')}">
												<spring:message
													text="??menu.label.repertorio.attribuzioni.cerca??"
													code="menu.label.repertorio.attribuzioni.cerca" />
											</a>
										</legarc:isAuthorized></li>
								</ul></li>
							<li class="sub-menu"><a href=""
								data-ma-action="submenu-toggle"> <spring:message
										text="??menu.label.repertorio.poteri.codificati??"
										code="menu.label.repertorio.poteri.codificati" /></a>
								<ul>
									<li><legarc:isAuthorized
											nomeFunzionalita="<%=Costanti.REPERTORIO_POTERI_W%>">
											<a
												href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/repertorioPoteri/crea.action')}">
												<spring:message text="??menu.label.repertorio.poteri.crea??"
													code="menu.label.repertorio.poteri.crea" />
											</a>
										</legarc:isAuthorized></li>
									<li><legarc:isAuthorized
											nomeFunzionalita="<%=Costanti.REPERTORIO_POTERI_R%>">
											<a
												href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/repertorioPoteri/ricerca.action')}">
												<spring:message
													text="??menu.label.repertorio.poteri.cerca??"
													code="menu.label.repertorio.poteri.cerca" />
											</a>
										</legarc:isAuthorized></li>
								</ul></li>
							<li class="sub-menu"><a href=""
								data-ma-action="submenu-toggle"> <spring:message
										text="??menu.label.tipoProcure??"
										code="menu.label.tipoProcure" /></a>
								<ul>
									<legarc:isAuthorized
										nomeFunzionalita="<%=Costanti.TIPO_PROCURE_R%>">
										<li><a
											href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/tipoProcure/visualizzaTipoProcure.action')}">
												<spring:message text="??menu.label.visualizza??"
													code="menu.label.visualizza" />
										</a></li>
									</legarc:isAuthorized>
									<legarc:isAuthorized
										nomeFunzionalita="<%=Costanti.TIPO_PROCURE_W%>">
										<li><a
											href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/tipoProcure/gestioneTipoProcure.action')}">
												<spring:message text="??menu.label.gestione??"
													code="menu.label.gestione" />
										</a></li>
									</legarc:isAuthorized>
								</ul></li>
						</ul></li>
				</legarc:isAuthorized>



				<legarc:isAuthorized
					nomeFunzionalita="<%=CostantiDAO.LEG_ARC_GESTORE_ARCHIVIO_PROCURE%>">
					<li class="sub-menu"><a href=""
						data-ma-action="submenu-toggle"> <i
							class="fa fa-folder-open-o" aria-hidden="true"></i> <spring:message
								text="??menu.label.gestioneProcure??"
								code="menu.label.gestioneProcure" />
					</a>

						<ul>
							<li><legarc:isAuthorized
									nomeFunzionalita="<%=Costanti.PROCURE_CONFERITE_W%>">
									<a
										href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/procure/crea.action')}">
										<spring:message text="??menu.label.creaProcure??"
											code="menu.label.creaProcure" />
									</a>
								</legarc:isAuthorized></li>
							<li><legarc:isAuthorized
									nomeFunzionalita="<%=Costanti.PROCURE_CONFERITE_R%>">
									<a
										href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/procure/ricerca.action')}">
										<spring:message text="??menu.label.cercaProcure??"
											code="menu.label.cercaProcure" />
									</a>
								</legarc:isAuthorized></li>
						</ul></li>


				</legarc:isAuthorized>


				<legarc:isAuthorized
					nomeFunzionalita="<%=CostantiDAO.LEG_ARC_GESTORE_AFFSOC%>">
					<li class="sub-menu"><a href=""
						data-ma-action="submenu-toggle"> <spring:message
								text="??menu.label.affari.societari??"
								code="menu.label.affari.societari" /></a>
						<ul>
							<li><legarc:isAuthorized
									nomeFunzionalita="<%=Costanti.AFFARI_SOCIETARI_W%>">
									<a
										href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/affariSocietari/crea.action')}">
										<spring:message text="??menu.label.affari.societari.crea??"
											code="menu.label.affari.societari.crea" />
									</a>
								</legarc:isAuthorized></li>
							<li><legarc:isAuthorized
									nomeFunzionalita="<%=Costanti.AFFARI_SOCIETARI_R%>">
									<a
										href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/affariSocietari/ricerca.action')}">
										<spring:message text="??menu.label.affari.societari.cerca??"
											code="menu.label.affari.societari.cerca" />
									</a>
								</legarc:isAuthorized></li>
						</ul></li>

					<li class="sub-menu"><a href=""
						data-ma-action="submenu-toggle"> <spring:message
								text="??menu.label.organo.sociale??"
								code="menu.label.organo.sociale" /></a>
						<ul>
							<li><legarc:isAuthorized
									nomeFunzionalita="<%=Costanti.ORGANO_SOCIALE_W%>">
									<a
										href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/organoSociale/crea.action')}">
										<spring:message text="??menu.label.organo.sociale.crea??"
											code="menu.label.organo.sociale.crea" />
									</a>
								</legarc:isAuthorized></li>
							<li><legarc:isAuthorized
									nomeFunzionalita="<%=Costanti.ORGANO_SOCIALE_R%>">
									<a
										href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/organoSociale/ricerca.action')}">
										<spring:message text="??menu.label.organo.sociale.cerca??"
											code="menu.label.organo.sociale.cerca" />
									</a>
								</legarc:isAuthorized></li>
								<!--  Aggiunta voce di menu export organi sociali RIDP6U9 -->
								<li><legarc:isAuthorized
									nomeFunzionalita="<%=Costanti.ORGANO_SOCIALE_R%>">
									<a
										href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/organoSociale/export.action')}">
										<spring:message text="??menu.label.organo.sociale.export??"
											code="menu.label.organo.sociale.export" />
									</a>
								</legarc:isAuthorized></li>
								<!--  FINE Aggiunta voce di menu export organi sociali RIDP6U9 -->
						</ul></li>
				</legarc:isAuthorized>

				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_ARCHIVI_PRESIDIO_NORMATIVO%>">
					<li class="sub-menu"><a href=""
						data-ma-action="submenu-toggle"> <spring:message
								text="??menu.label.presidionormativo??"
								code="menu.label.presidionormativo" />
					</a>
						<ul>

							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/presidionormativo/crea.action')}"><spring:message
										text="??menu.label.utenti??" code="menu.label.utenti" /></a></li>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/presidionormativo/mailingList.action')}"><spring:message
										text="??menu.label.creamailinglist??"
										code="menu.label.creamailinglist" /></a></li>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/articolo/crea.action')}"><spring:message
										text="??menu.label.nuovoArticolo??"
										code="menu.label.nuovoArticolo" /></a></li>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/articolo/cerca.action')}"><spring:message
										text="??menu.label.cercaArticoli??"
										code="menu.label.cercaArticoli" /></a></li>

							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/newsletter/crea.action')}"><spring:message
										text="??menu.label.creaNewsletter??"
										code="menu.label.creaNewsletter" /></a></li>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/newsletter/cerca.action')}"><spring:message
										text="??menu.label.cercaNewsletter??"
										code="menu.label.cercaNewsletter" /></a></li>
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/catMailingList/crea.action')}"><spring:message
										text="??menu.label.gestioneCategorie??"
										code="menu.label.gestioneCategorie" /></a></li>

						</ul></li>
				</legarc:isAuthorized>
			</ul></li>
	</legarc:isAuthorized>

	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA%>">
		<li class="sub-menu"><a href="" data-ma-action="submenu-toggle">
				<i class="fa fa-book" aria-hidden="true"></i> <spring:message
					text="??menu.label.anagrafica??" code="menu.label.anagrafica" />
		</a>
			<ul>
				<li class="sub-menu"><a href="" data-ma-action="submenu-toggle">
						<spring:message text="??menu.label.nazione??"
							code="menu.label.nazione" />
				</a>
					<ul>
						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_VISUALIZZA%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/nazione/visualizzaNazioni.action')}">
									<spring:message text="??menu.label.visualizza??"
										code="menu.label.visualizza" />
							</a></li>
						</legarc:isAuthorized>
						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_GESTIONE_NAZIONE%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/nazione/gestioneNazioni.action')}">
									<spring:message text="??menu.label.gestione??"
										code="menu.label.gestione" />
							</a></li>
						</legarc:isAuthorized>
					</ul></li>
				<li class="sub-menu"><a href="" data-ma-action="submenu-toggle">
						<spring:message text="??menu.label.specializzazione??"
							code="menu.label.specializzazione" />
				</a>
					<ul>
						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_VISUALIZZA%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/specializzazione/visualizzaSpecializzazioni.action')}">
									<spring:message text="??menu.label.visualizza??"
										code="menu.label.visualizza" />
							</a></li>
						</legarc:isAuthorized>
						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_GESTIONE%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/specializzazione/gestioneSpecializzazioni.action')}">
									<spring:message text="??menu.label.gestione??"
										code="menu.label.gestione" />
							</a></li>
						</legarc:isAuthorized>
					</ul></li>
				<li class="sub-menu"><a href="" data-ma-action="submenu-toggle">
						<spring:message text="??menu.label.nazione??"
							code="menu.label.societa" />
				</a>
					<ul>

						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_VISUALIZZA%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/societa/visualizzaSocieta.action')}">
									<spring:message text="??menu.label.visualizza??"
										code="menu.label.visualizza" />
							</a></li>
						</legarc:isAuthorized>
						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_GESTIONE%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/societa/gestioneSocieta.action')}">
									<spring:message text="??menu.label.gestione??"
										code="menu.label.gestione" />
							</a></li>
						</legarc:isAuthorized>
					</ul></li>
				<li class="sub-menu"><a href="" data-ma-action="submenu-toggle">
						<spring:message text="??menu.label.materia??"
							code="menu.label.materia" />
				</a>
					<ul>
						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_VISUALIZZA%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/materia/visualizzaMateria.action')}">
									<spring:message text="??menu.label.visualizza??"
										code="menu.label.visualizza" />
							</a></li>
						</legarc:isAuthorized>
						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_GESTIONE%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/materia/gestioneMateria.action')}">
									<spring:message text="??menu.label.gestione??"
										code="menu.label.gestione" />
							</a></li>
						</legarc:isAuthorized>
					</ul></li>
				<li class="sub-menu"><a href="" data-ma-action="submenu-toggle">
						<spring:message text="??menu.label.professionistaEsterno??"
							code="menu.label.professionistaEsterno" />
				</a>
					<ul>
						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_NUOVO_PROFESSIONISTA%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/professionistaEsterno/crea.action')}">
									<spring:message text="??menu.label.crea??"
										code="menu.label.crea" />
							</a></li>
						</legarc:isAuthorized>

						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_VISUALIZZA%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/professionistaEsterno/visualizzaProfEst.action')}">
									<spring:message text="??menu.label.visualizza??"
										code="menu.label.visualizza" />
							</a></li>
						</legarc:isAuthorized>
						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_GESTIONE%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/professionistaEsterno/gestioneProfEst.action')}">
									<spring:message text="??menu.label.gestione??"
										code="menu.label.gestione" />
							</a></li>
						</legarc:isAuthorized>


					</ul></li>
				<li class="sub-menu"><a href="" data-ma-action="submenu-toggle">
						<spring:message text="??menu.label.centroDiCosto??"
							code="menu.label.centroDiCosto" />
				</a>
					<ul>
						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_VISUALIZZA%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/centroDiCosto/visualizzaCentroDiCosto.action')}">
									<spring:message text="??menu.label.visualizza??"
										code="menu.label.visualizza" />
							</a></li>
						</legarc:isAuthorized>
						<legarc:isAuthorized
							nomeFunzionalita="<%=Costanti.FUNZIONALITA_ANAGRAFICA_GESTIONE%>">
							<li><a
								href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/centroDiCosto/gestioneCentroDiCosto.action')}">
									<spring:message text="??menu.label.gestione??"
										code="menu.label.gestione" />
							</a></li>
						</legarc:isAuthorized>
					</ul></li>
				<li><a href="javascript:reportUtentiAlesocr()"> <spring:message
							text="??menu.label.report.utentiAlesocr??"
							code="menu.label.report.utentiAlesocr" />
				</a></li>
			</ul></li>
	</legarc:isAuthorized>

	<!-- /AMMINISTRAZIONE -->
	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_AMMINISTRAZIONE%>">
		<li class="sub-menu"><a href="javascript:void(0)"
			data-ma-action="submenu-toggle"> <i class="fa fa-cog"
				aria-hidden="true"></i>
			<spring:message text="??menu.label.amministrazione??"
					code="menu.label.amministrazione" /></a>
			<ul>

				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/riassegna/index.action')}"><spring:message
							text="??menu.label.riassegnazionefascicoli??"
							code="menu.label.riassegnazionefascicoli" /></a></li>

				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/configurazioneWorkflow/configurazione.action')}"><spring:message
							text="??menu.label.configurazioneworkflows??"
							code="menu.label.configurazioneworkflows" /></a></li>

				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/audit/cerca.action')}"><spring:message
							text="??menu.label.auditingmonitoraggioaccessi??"
							code="menu.label.auditingmonitoraggioaccessi" /></a></li>

				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/gestioneUtenti/index.action')}"><spring:message
							text="??menu.label.gestioneUtentiAssenti??"
							code="menu.label.gestioneUtentiAssenti" /></a></li>
			</ul></li>
	</legarc:isAuthorized>


	<!-- VENDORMANAGEMENT -->
	<li class="sub-menu"><legarc:isAuthorized
			nomeFunzionalita="<%=Costanti.FUNZIONALITA_INCARICHI%>">
			<a href="javascript:void(0)" data-ma-action="submenu-toggle"> <i
				class="fa fa-hand-peace-o" aria-hidden="true"></i> Vendor Management
			</a>
			<ul>
				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/vendormanagement/votazioni.action')}">
						<spring:message text="??vendormanagement.valutazioni??"
							code="vendormanagement.valutazioni" />
				</a></li>
				<legarc:isAuthorized
					nomeFunzionalita="<%=Costanti.FUNZIONALITA_VENDOR_MANAGEMENT%>">
					<li><a
						href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/vendormanagement/gestioneVotazioni.action')}">
							<spring:message text="??menu.label.gestionevalutazioni??"
								code="menu.label.gestionevalutazioni" />
					</a></li>
				</legarc:isAuthorized>
			</ul>
		</legarc:isAuthorized></li>
	

	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_ARCHIVI_PROTOCOLLO%>">
		<li><a
			href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/protocollo/gestioneProtocollo.action')}">
				<spring:message text="??protocollo.label.pagProtocollo??"
					code="protocollo.label.pagProtocollo" /> <i class="fa fa-list-alt"
				aria-hidden="true"></i>
		</a></li>
	</legarc:isAuthorized>
	
	<!-- BEAUTY CONTEST -->
	<legarc:isAuthorized
		nomeFunzionalita="<%=Costanti.FUNZIONALITA_BEAUTY_CONTEST%>">

		<li class="sub-menu"><a href="" data-ma-action="submenu-toggle">
				<i class="fa fa-trophy" aria-hidden="true"></i> <spring:message
					text="??menu.label.beautyContest??" code="menu.label.beautyContest" />
		</a>

			<ul>
				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/beautyContest/crea.action')}">
						<spring:message text="??menu.label.creaBeautyContest??"
							code="menu.label.creaBeautyContest" />
				</a></li>
				<li><a
					href="javascript:{legalSecurity.attachForm('<%=request.getContextPath()%>/beautyContest/cerca.action')}">
						<spring:message text="??menu.label.cercaBeautyContest??"
							code="menu.label.cercaBeautyContest" />
				</a></li>
			</ul></li>
	</legarc:isAuthorized>

</ul>
<!-- /MAIN MENU -->


<script type="text/javascript">
	function reportUtentiAlesocr() {
		console.log("genera il report per gli utenti alesocr");
		waitingDialog.show('Loading...');
		var url = WEBAPP_BASE_URL + "/reporting/usersAlesocr.action";
		url = legalSecurity.verifyToken(url);
		downloadReporUtentiAlesocr(url);
	}

	function downloadReporUtentiAlesocr(urlToSend) {

		var req = new XMLHttpRequest();
		req.open("GET", urlToSend, true);
		req.responseType = "document";
		req.onload = function(event) {
			var link = document.createElement('a');
			link.href = urlToSend;
			link.click();
			waitingDialog.hide();
		};
		req.send();
	}
</script>
