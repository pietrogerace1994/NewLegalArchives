<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!DOCTYPE html lang="${language}">
<!--formToken ????? -->
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Legal Archives</title>

<jsp:include page="/parts/script-init.jsp">
</jsp:include>

</head>
<body data-ma-header="teal">
	<jsp:include page="/parts/header.jsp">
	</jsp:include>

	<!-- SECION MAIN -->
	<section id="main">
		<jsp:include page="/parts/aside.jsp">
		</jsp:include>
		<!-- SECTION CONTENT -->
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						<div class="card">
							<div class="card-header ch-dark palette-Green-SNAM bg">
								<h2> <spring:message text="??fascicolo.label.contenutoFascicolo??"
											code="fascicolo.label.contenutoFascicolo" /></h2>
							</div>
							<div class="card-body" style="min-height: 400px;"> 
									<c:if test="${ not empty param.successMessage }">
										<div class="alert alert-info">
											<spring:message code="messaggio.operazione.ok"
												text="??messaggio.operazione.ok??"></spring:message>
										</div>
									</c:if>
									<c:if test="${ not empty param.errorMessage }">
										<div class="alert alert-danger">
											<spring:message code="${param.errorMessage}"
												text="??${param.errorMessage}??"></spring:message>
										</div>
									</c:if>
									<div id="treeview" class="treeview"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		
<!-- MODAL Dettaglio -->
<div class="modal fade" id="modalDettaglio" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content" style="height:570px"> 
			<div class="modal-header">
				<h4 class="modal-title"> </h4>
			</div>
			<div class="modal-body" style="overflow-x: auto;height:495px">
			<iframe id="bodyDettaglio" src="" style="width:100%;height:1700px;border:0px"></iframe>

			</div>
			 
		</div>
	</div>
</div>
<!-- FINE MODAL Dettaglio -->
		
	</section> 

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include> 
	


<script type="text/javascript">
function caricaAlberaturaContenuto(json){ 
		$('#treeview').treeview({	 
			showBorder : false,
			showTags : true,			
			highlightSelected : false,
	        enableLinks: true,
			onNodeSelected: openContextMenu,
			data : json
		});	
	    
}

function openContextMenu(event, node){
	var s = node.text.substring(node.text.indexOf('dropdown-menu-left" id="')+25,node.text.length);
	s = s.substring(0,s.indexOf('"><li>'));
	//$('#'+s).toggle();
	var obj = document.getElementById(s);
	var idObj = obj.getAttribute('data-idoggetto');
	var tipoObj = obj.getAttribute('data-tipooggetto'); 
}
	
function caricaAzioniNodoContenuto(tipoObj, idObj, isPenale){
	var ajaxUtil = new AjaxUtil();
	switch (tipoObj) {
	case "FASCICOLO": 
		caricaAzioniSuFascicoloContenuto(idObj,tipoObj);
		break;
	case "INCARICO":
		caricaAzioniSuIncaricoContenuto(idObj, tipoObj);
		break;
	case "ARBITRALE":
		caricaAzioniSuIncaricoArbitraleContenuto(idObj, tipoObj);
		break;

	case "PROFORMA":
		caricaAzioniSuProformaContenuto(idObj, tipoObj);
		break;
	case "DOCUMENTO":
		
		var containerAzioni = document.getElementById("containerLiAzioni" + tipoObj + idObj);
		
		containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
		containerAzioni.innerHTML="<li><a href=\"<%=request.getContextPath()%>/download?onlyfn=1&isp="+isPenale+"&uuid="+idObj+"\" class=\"edit\" target=\"_BLANK\">"+
		"<i class=\"fa fa-download\" aria-hidden=\"true\"></i> Download</a></li>";
		containerAzioni.innerHTML+="<li><a href=\"javascript:void(0);\"  data-toggle=\"modal\" data-target=\"#panelDeleteDocFascicolo\"  data-fascicoloid=\"${fascicoloRicercaView.fascicoloId}\" data-uuid=\""+idObj+"\">"+
		"<i class=\"fa fa-trash-o\" aria-hidden=\"true\"></i> Delete</a></li>";
 
		break;
	case "ATTO":
	caricaAzioniSuAtto(idObj, tipoObj);
		break;
	case "PROPOSTA_INCARICO":
		var containerAzioni = document.getElementById("containerLiAzioni" + tipoObj + idObj);
		
		containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
		containerAzioni.innerHTML="<li><a href=\"<%=request.getContextPath()%>/incarico/downloadNotaProposta.action?id="+idObj+"\" class=\"edit\" target=\"_BLANK\">"+
		"<i class=\"fa fa-download\" aria-hidden=\"true\"></i> Download</a></li>";
		break;
	case "LETTERA_INCARICO":
		var containerAzioni = document.getElementById("containerLiAzioni" + tipoObj + idObj);
		
		containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
		containerAzioni.innerHTML="<li><a href=\"<%=request.getContextPath()%>/incarico/downloadLetteraIncarico.action?id="+idObj+"\" class=\"edit\" target=\"_BLANK\">"+
		"<i class=\"fa fa-download\" aria-hidden=\"true\"></i> Download</a></li>";
		break;
	case "SCHEDA_VALUTAZIONE":
		var containerAzioni = document.getElementById("containerLiAzioni" + tipoObj + idObj);
		
		containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
		containerAzioni.innerHTML="<li><a href=\"<%=request.getContextPath()%>/proforma/downloadSchedaValutazione.action?id="+idObj+"\" class=\"edit\" target=\"_BLANK\">"+
		"<i class=\"fa fa-download\" aria-hidden=\"true\"></i> Download</a></li>";
		break;
	default:
		break;
	}
}	
	
function caricaAzioniSuFascicoloContenuto(id, tipo) {
	var containerAzioni = document.getElementById("containerLiAzioni" + tipo + id);
	
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "fascicolo/caricaAzioniFascicolo.action?onlyContent=1&idFascicolo=" + id;
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}	
	

function caricaAzioniSuAtto(id,tipo) {
	var containerAzioni = document.getElementById("containerLiAzioni" + tipo + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "atto/caricaAzioniAttoTriview.action?idAtto=" + id;
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}


function caricaAzioniSuProformaContenuto(id, tipo) {
	var containerAzioni = document.getElementById("containerLiAzioni" + tipo + id);
	
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "proforma/caricaAzioniProforma.action?onlyContent=1&idProforma=" + id;
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}		
function caricaAzioniSuIncaricoContenuto(id, tipo) {
	var containerAzioni = document.getElementById("containerLiAzioni" + tipo + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "incarico/caricaAzioniIncarico.action?onlyContent=1&idIncarico=" + id;
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}	

function caricaAzioniSuIncaricoArbitraleContenuto(id, tipo) {
	var containerAzioni = document.getElementById("containerLiAzioni" + tipo + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "incarico/caricaAzioniArbitrale.action?onlyContent=1&idIncarico=" + id;
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}
	
	
function loadTree(id){
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {  
		caricaAlberaturaContenuto(data);
		waitingDialog.hide(); 
	};
	
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
		visualizzaMessaggio(data);
		
	};

	

	var params = "id="+id; 
	var url = WEBAPP_BASE_URL + "fascicolo/leggiContenuto.action";
	ajaxUtil.ajax(url, params, "get", null, callBackFn, null, callBackFnErr);
}
</script>

<script type="text/javascript">
var idFascicolo = ${fascicoloRicercaView.fascicoloId};
loadTree(idFascicolo);
</script>

<script>
function openAtto(id,azione){
	return visualizzaDettaglio(id);
	/*
	var form = document.createElement("form");
	form.action=WEBAPP_BASE_URL+"atto/visualizza.action";
	form.method="GET";
	var inp=document.createElement("input");
	inp.type="hidden";
	inp.name="id";
	inp.value=id;
	form.appendChild(inp);
	var inz=document.createElement("input");
	inz.type="hidden";
	inz.name="azione";
	inz.value=azione;
	form.appendChild(inz);
	form.submit();
	*/
	}
	
	
function visualizzaDettaglio(id){
	var url="<%=request.getContextPath()%>/atto/visualizza.action?id="+id+"&azione=visualizza";
	url=legalSecurity.verifyToken(url);
	waitingDialog.show('Loading...');
	var ifr=$("#bodyDettaglio").attr("src",url);
	ifr.load(function(){
	
	var documents = (ifr[0].contentDocument)?ifr[0].contentDocument.body:ifr[0].contentWindow.document.body;
	 
	  $("header#header",documents).remove();
	  $("footer#footer",documents).remove();
	  $("section#main",documents).css("padding","0px");
	  $("div.modal-footer",documents).find("button.btn-danger").remove();
		// var a_="<a href=\"javascript:void(0)\" onclick=\"javascript:window.print()\" style=\"position:absolute;top:40px;right: 20px;z-index: 1000;color: #fff;font-weight: bold;\">stampa</a>";
		//$("section#content",documents).append(a_)
	  	 waitingDialog.hide();
		$("#modalDettaglio").modal("show");
})
	 

}	

</script>


<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>
	
</body>
</html>					
