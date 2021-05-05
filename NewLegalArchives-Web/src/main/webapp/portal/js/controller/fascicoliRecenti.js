$(document).ready(function() {

//	var numRighe=$("#fascicoliRecentiNumRigheHidden").val();
	loadFascicoliRecenti(5);
});

function changeFascicoliRecentiMaxSize(newNumRighe) {
//	$("#fascicoliRecentiNumRigheHidden").val(newNumRighe);
	$("#fascicoliRecentiNumRigheBadge").html(newNumRighe);
	loadFascicoliRecenti(newNumRighe);
}

function loadFascicoliRecenti(numRighe) {
	
	console.log('loadFascicoliRecenti: '+numRighe);
	
	$("#divFascicoliRecenti").empty();
	$("#divFascicoliRecenti").html('<small class="lgi-text">Loading...</small>');

	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		//successo
		$("#divFascicoliRecenti").empty();
		var indexRest = data.getElementsByTagName("indexRest");
		var arrJsonStr = null;
		var n=indexRest["0"].childNodes.length;
		if(n==1) {
			$("#dropdownNumRighe").html('');
			
			var str = "";
			str += '<div class="list-group-item media">';
			str += '  <div class="media-body">  ';
			str += '    <small class="lgi-text">' + strings['index.label.budget.nessunFascicolo'] + '</small>';
			str += ' </div>';
			str += '	</div> ';
			
			$("#divFascicoliRecenti").html(str);
		}
		else if(n>1) {
			if(indexRest["0"].childNodes[1].innerHTML)
				arrJsonStr = indexRest["0"].childNodes[1].innerHTML;
			else if(indexRest["0"].childNodes["1"].textContent)
				arrJsonStr = indexRest["0"].childNodes["1"].textContent;
		 var arrJson = JSON.parse(arrJsonStr);
		
		 for (var i = 0; i < arrJson.length; i++) {
			
			var ele = arrJson[i];
			

			
			var str = "";
			str = ' <div class="list-group-item media">';
			str += '<div class="pull-right" >';
			str += '    <ul class="actions">';
			str += '    	<li class="dropdown">';
			str += '           <a href="#" onclick="caricaAzioniSuFascicoloContenuto('+ele.id+'); return false;" data-toggle="dropdown" aria-expanded="false"><i class="zmdi zmdi-more-vert"></i></a>';
			str += '  		   <ul id="containerAzioniFascicolo'+ele.id+'" class="dropdown-menu dropdown-menu-left" style="    position: relative;">';
			str += '           </ul>';
			str += '        </li>';
			str += '    </ul>';
			str += ' </div>  ';
			str += ' <div class="media-body">';
			str += '    <div class="lgi-heading">';
			str +=           ele.nome;
			str += '	</div>';
			str += '	<small class="lgi-text">'+ele.descrizione+'</small> ';
			str += ' </div>';
			str += '</div>';
			
			
			$(str).appendTo("#divFascicoliRecenti");
		 }
		}//if
		
	};
	var url = WEBAPP_BASE_URL + "index/loadFascicoliRecenti.action?numRighe="+numRighe;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
	
}

function goToCercaFascicolo(id) {
	console.log('goToCercaFascicolo()');
	waitingDialog.show('Loading...');
	var url=WEBAPP_BASE_URL+"fascicolo/cerca.action?id="+id;
	url=legalSecurity.verifyToken(url);
	window.open(url, "_self");
}
