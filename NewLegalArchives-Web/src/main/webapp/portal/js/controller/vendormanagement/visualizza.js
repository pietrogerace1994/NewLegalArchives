function caricaVotazioniProfEst(idProfEst) {
 	var div = document.getElementById("visProfEstVotazioni");
    div.innerHTML="";
    $("#visProfEstVotazioniGifLoading").removeClass("hidden");
    
    var fnCallbackSuccess = function(data){
    	$("#visProfEstVotazioniGifLoading").addClass("hidden");
    	div.innerHTML=data;
    };
    var ajaxUtil = new AjaxUtil();
    var url = WEBAPP_BASE_URL + "vendormanagement/visualizzaVotazioni.action?idProfEst="+idProfEst;
    url=legalSecurity.verifyToken(url);
    ajaxUtil.ajax(url, null, "GET", "text/html", fnCallbackSuccess, null, null)
}

function pulisciVotazioniProfEst() {
	$( "#visProfEstVotazioni" ).html('');
	$('#btnVotazioniApri').show();
	
	$("#btnVotazioniApri").removeClass("hidden");
}
		


$(document).ready(function() {

	$('#btnVotazioniApri').on('click', function() {
		
		var profest_ele = $("select[id$='professionistaEsternoId']");
		var profest_sel=profest_ele["0"].selectedIndex;
		if(profest_sel>=0) {
			var profestId=profest_ele["0"][profest_sel].value;
			caricaVotazioniProfEst(profestId);
		}
		
		$('#btnVotazioniApri').hide();
		
	});
	
});