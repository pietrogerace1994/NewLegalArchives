<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ page import="eng.la.util.costants.Costanti"%>
<%@taglib uri="http://leg-arc/tags" prefix="legarc"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<div class="container">
	<div class="row">
		<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">



			<div class="container">


				<c:if test="${vendorManagementView.nessunaVotazione eq true}">

					<div class="row">
						<div class="col-sm-12" style="text-align: center">
							<span class="label label-warning">Nessuna Votazione</span>
						</div>
					</div>

				</c:if>

				<c:if test="${vendorManagementView.nessunaVotazione eq false}">

					<c:forEach items="${vendorManagementView.listTabelleSemestriView}"
						var="objTabelleSemestriView">
						
						<div class="row">
							<div class="col-sm-12" style="text-align: center">
								<span class="label label-primary">${objTabelleSemestriView.nazioneDesc}</span>
								&nbsp;&nbsp;
								<span class="label label-info">${objTabelleSemestriView.specializzazioneDesc}</span>
							</div>
						</div>
						
												

						<div class="row">
							<div class="col-sm-1"></div>

							<div class="col-sm-1"
								style="font-weight: normal; background-color: orange; text-align:center;padding:0px;">Semestre</div>
							<div class="col-sm-1"
								style="font-weight: normal; background-color: orange; text-align:center; padding:0px;">Autorevolezza</div>
							<div class="col-sm-1"
								style="font-weight: normal; background-color: orange; text-align:center;padding:0px;">Capacita</div>
							<div class="col-sm-1"
								style="font-weight: normal; background-color: orange; text-align:center;padding:0px;">Competenza</div>
							<div class="col-sm-1"
								style="font-weight: normal; background-color: orange; text-align:center;padding:0px;">Costi</div>
							<div class="col-sm-1"
								style="font-weight: normal; background-color: orange; text-align:center;padding:0px;">Flessibilita</div>
							<div class="col-sm-1"
								style="font-weight: normal; background-color: orange; text-align:center;padding:0px;">Tempi</div>
							<div class="col-sm-1"
								style="font-weight: normal; background-color: orange; text-align:center;padding:0px;">Reperibilita</div>
							<div class="col-sm-1"
								style="font-weight: normal; background-color: orange; text-align:center;padding:0px;">% Voti</div>
							<div class="col-sm-1"
								style="font-weight: normal; background-color: orange; text-align:center;padding:0px;">TOTALE</div>
							<div class="col-sm-1"></div>
						</div>


						<c:forEach items="${objTabelleSemestriView.semestre}"
							var="oggetto" varStatus="stato">

							<div class="row">
								<div class="col-sm-1" style="font-weight: normal;">
								
								<legarc:isAuthorized nomeFunzionalita="<%=Costanti.FUNZIONALITA_VENDOR_MANAGEMENT %>">
									<button  type="button" data-toggle="collapse" data-target="#${oggetto.id}"
									class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini" 
									style="float:right; position: relative !important; width: 20px !important; height: 20px !important; padding: 1px 0px 15px 0px !important;">
										<i class="zmdi zmdi-eye icon-mini" ></i>
									</button>
								</legarc:isAuthorized>
								
								</div>

								<div class="col-sm-1" style="font-weight: normal; text-align:center;">
									${oggetto.semestreStr}</div>
								<div class="col-sm-1" style="font-weight: normal; text-align:center;">
									${oggetto.mediaAutorevolezza}</div>
								<div class="col-sm-1" style="font-weight: normal; text-align:center;">
									${oggetto.mediaCapacita}</div>
								<div class="col-sm-1" style="font-weight: normal; text-align:center;">
									${oggetto.mediaCompetenze}</div>
								<div class="col-sm-1" style="font-weight: normal; text-align:center;">
									${oggetto.mediaCosti}</div>
								<div class="col-sm-1" style="font-weight: normal; text-align:center;">
									${oggetto.mediaFlessibilita}</div>
								<div class="col-sm-1" style="font-weight: normal; text-align:center;">
									${oggetto.mediaTempi}</div>
								<div class="col-sm-1" style="font-weight: normal; text-align:center;">
									${oggetto.mediaReperibilita}</div>
								<div class="col-sm-1" style="font-weight: normal; text-align:center;">
									${oggetto.percentualeVotanti}</div>
								<div class="col-sm-1" style="font-weight: normal; text-align:center;">
									${oggetto.mediaTotale}</div>
								<div class="col-sm-1"></div>
							</div>
							
							<legarc:isAuthorized nomeFunzionalita="<%=Costanti.FUNZIONALITA_VENDOR_MANAGEMENT %>">
							<div id="${oggetto.id}" class="collapse">
								
								
								<c:forEach items="${oggetto.votazioni}" var="votazione" varStatus="stato">

									<div class="row">
										<div class="col-sm-1" style="font-weight: normal; border-bottom: 0px solid #eeeeee !important;"></div>

										<div class="col-sm-1" style="font-weight: normal; text-align:center; border-bottom: 1px solid #eeeeee !important;">
											<fmt:formatDate pattern="dd/MM/yy" value="${votazione.vo.dataValutazione}"/></div>
										<div class="col-sm-1" style="font-weight: normal; text-align:center; border-bottom: 1px solid #eeeeee !important;">
											${votazione.vo.valutazioneAutorevolezza}</div>
										<div class="col-sm-1" style="font-weight: normal; text-align:center; border-bottom: 1px solid #eeeeee !important;">
											${votazione.vo.valutazioneCapacita}</div>
										<div class="col-sm-1" style="font-weight: normal; text-align:center; border-bottom: 1px solid #eeeeee !important;">
											${votazione.vo.valutazioneCompetenze}</div>
										<div class="col-sm-1" style="font-weight: normal; text-align:center; border-bottom: 1px solid #eeeeee !important;">
											${votazione.vo.valutazioneCosti}</div>
										<div class="col-sm-1" style="font-weight: normal; text-align:center; border-bottom: 1px solid #eeeeee !important;">
											${votazione.vo.valutazioneFlessibilita}</div>
										<div class="col-sm-1" style="font-weight: normal; text-align:center; border-bottom: 1px solid #eeeeee !important;">
											${votazione.vo.valutazioneTempi}</div>
										<div class="col-sm-1" style="font-weight: normal; text-align:center; border-bottom: 1px solid #eeeeee !important;">
											${votazione.vo.valutazioneReperibilita}</div>
										<div class="col-sm-1" style="font-weight: normal; text-align:center; border-bottom: 1px solid #eeeeee !important;">&nbsp;</div>
										<div class="col-sm-1" style="font-weight: normal; text-align:center; border-bottom: 1px solid #eeeeee !important;">
											${votazione.vo.valutazione}</div>
										<div class="col-sm-1" style="font-weight: normal; border-bottom: 0px solid #eeeeee !important;"></div>
									</div>

								</c:forEach>
								
							</div> 
							</legarc:isAuthorized>

						</c:forEach>


						<div class="row">
							<div class="col-sm-1"></div>

							<div class="col-sm-1"
								style="font-weight: bold; background-color: orange; text-align:center;"></div>
							<div class="col-sm-1"
								style="font-weight: bold; background-color: orange; text-align:center;"></div>
							<div class="col-sm-1"
								style="font-weight: bold; background-color: orange; text-align:center;"></div>
							<div class="col-sm-1"
								style="font-weight: bold; background-color: orange; text-align:center;"></div>
							<div class="col-sm-1"
								style="font-weight: bold; background-color: orange; text-align:center;"></div>
							<div class="col-sm-1"
								style="font-weight: bold; background-color: orange; text-align:center;"></div>
							<div class="col-sm-1"
								style="font-weight: bold; background-color: orange; text-align:center;"></div>
							<div class="col-sm-1"
								style="font-weight: bold; background-color: orange; text-align:center;"></div>
							<div class="col-sm-1"
								style="font-weight: bold; background-color: orange; text-align:center;"></div>
							<div class="col-sm-1"
								style="font-weight: bold; background-color: orange; text-align:center;">${objTabelleSemestriView.totale}</div>
							<div class="col-sm-1"></div>
						</div>

					</c:forEach>




				</c:if>

			</div><!-- container -->
		</div>
	</div>
</div>
