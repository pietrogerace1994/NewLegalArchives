var semestreScelto = "";

$(document).ready(function () {
	
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		$("#semestreBadge").html(data);
		semestreScelto = data;
		caricaStatisticheVotazioni(data);
		caricaSemestriDisponibili();
	};
	var url = WEBAPP_BASE_URL + "vendormanagement/calcolaSemestreCorrente.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
});

function changeSemestre(semestre) {
	$("#semestreBadge").html(semestre);
	semestreScelto = semestre;
	caricaStatisticheVotazioni(semestre);
}

function caricaStatisticheVotazioni(semestre){
	
	$("#pie-chart-vote").empty();
	$("#pie-chart-vote").html('<small class="lgi-text">Loading...</small>');
	$("#pie-chart-legend").empty();
	
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		$("#pie-chart-vote").empty();
		$("#pie-chart-legend").empty();
		
		if(data == null){
			var str = "";
			str += ' <div class="media-body">  ';
			str += '    <small class="lgi-text">Nessun dato disponibile</small>';
			str += ' </div>';
			
			$("#pie-chart-vote").html(str);
		}
		else{
			
			var totale = 0;
			
			for (var i = 0; i < data.length; i++) {
				 
				 var ele = data[i];
				 totale = totale + ele.data;
			}
			
			if($('#pie-chart-vote')[0]){
				
				var plotOverlayHandler = function(plot, cvs) {
					  if(!plot) { return; }
					  
					  var cvsWidth = plot.width() / 2;
					  var cvsHeight = plot.height() / 2;
					  var text="Totale";
					  var text2 = totale;
					  cvs.font = "15px sans-serif";
					  cvs.fillStyle = "gray";
					  cvs.textAlign = 'center';
					  cvs.fillText(text, cvsWidth, cvsHeight);
					  cvsHeight = cvsHeight + 15;
					  cvs.fillText(text2, cvsWidth, cvsHeight);
					  return cvs;
				};
				
				var plot = $.plot('#pie-chart-vote', data, {
					series: {
						pie: {
							show: true,
							innerRadius: 0.3,
							stroke: { 
								width: 2,
							},
						},
					},
					legend: {
						container: '.flc-pie-vote',
						backgroundOpacity: 0.5,
						noColumns: 0,
						backgroundColor: "white",
						lineWidth: 0
					},
					grid: {
						hoverable: true,
						clickable: true
					},
					tooltip: true,
					tooltipOpts: {
						content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
						shifts: {
							x: 20,
							y: 0
						},
						defaultTheme: false,
						cssClass: 'flot-tooltip'
					}
				});
				plot.hooks.drawOverlay.push( plotOverlayHandler );
				$("#pie-chart-vote").showMemoVote();
			}
		}
	};
	var url = WEBAPP_BASE_URL + "vendormanagement/calcolaStatisticaVotazioni.action?semestre="+semestre;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

$.fn.showMemoVote = function () {
    $(this).bind("plothover", function (event, pos, item) {
        if (!item) { 
        	$("#flot-memo-vote").empty();
        	return; }
        console.log(item.series.data)
        var html = [];
        var percent = parseFloat(item.series.percent).toFixed(2);        

        html.push("<div style=\"border:1px solid grey;background-color:",
             item.series.color,
             "\">",
             "<span style=\"color:white\">",
             item.series.label,
             " : ",
             item.series.data[0][1],
             " (", percent, "%)",
             "</span>", 
             "</div>");
        $("#flot-memo-vote").html(html.join(''));
    });
}


function caricaSemestriDisponibili() {
	
	$("#semestriDisponibili").empty();
	$("#semestriDisponibili").html('<small class="lgi-text">Loading...</small>');

	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		$("#semestriDisponibili").empty();
		 var arrJson = JSON.parse(data);
		
		 for (var i = 0; i < arrJson.length; i++) {
			
			var ele = arrJson[i];
			
			var str = "";
			str = "<li><a href=\"#\" onclick=\"changeSemestre('"+ele.semestre+"')\">" +ele.semestre+ "</a></li>";
			
			$(str).appendTo("#semestriDisponibili");
		 }
	};
	var url = WEBAPP_BASE_URL + "vendormanagement/caricaSemestriDisponibili.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
	
}

function sollecitaMail(){
	console.log("Invio di mail di sollecito alle votazioni");
	
	if(!votazioniControlloMail()) {
		
		$('#panelInviaSollecito').modal('hide');
		
		var listaMailAggiuntive = '';
		var textemails = document.querySelectorAll("input[name^='email[']");
		
		if(textemails.length > 0){
			
			for (i = 0; i < textemails.length; i++) {
				listaMailAggiuntive = listaMailAggiuntive + textemails[i].value + ',';
			}
		}
		
		waitingDialog.show('Loading...');
		var ajaxUtil = new AjaxUtil();
		var callBackFn = function(data, stato) { 
			visualizzaMessaggio(data);
			svuotaListaMail();
			hideMailError();
			waitingDialog.hide();
		};
		var callBackFnErr = function(data, stato) { 
			visualizzaMessaggio(data);
			svuotaListaMail();
			hideMailError();
			waitingDialog.hide(); 
		};
		var url = WEBAPP_BASE_URL + "vendormanagement/sollecitaMail.action?mailAggiuntive="+listaMailAggiuntive;
		url=legalSecurity.verifyToken(url);
		ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
	}
}

function addEmail(){
	var max = findMax();
	max = max +1;
	$('#divEmail').append('<div class="list-group-item media" id="addEmail_'+ max +'"><div class="media-body"><div class="form-group"><label class="col-sm-3 control-label"> </label>'+
			'<div class="col-sm-4">'+
			'<input id="email['+ max +']" name="email['+ max +']" class="form-control" type="text">'+
		'</div>'+
		'<button type="button" onclick="removeEmail(this.id)" id="'+ max +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float" style="margin-top:-10px !important;">'+
			'<span class="glyphicon glyphicon-minus"></span>'+
		'</button></div></div></div>' );
}

function removeEmail(idCliccato){
	$('#addEmail_'+ idCliccato).remove();
}

function findMax(){
	
	var textemails = document.querySelectorAll("input[name^='email[']");
	if(textemails.length == 1){
		return 0;
	}
	
	var ar = new Array();
	$('[id^="email["]').each(
		 function(){
			ar.push(
					parseInt( $(this).attr('id').replace('email[','').replace(']','') )
				  );
		   });
	return Math.max.apply( Math, ar );
}

function svuotaListaMail(){
	$("[id^='addEmail_']").remove();
	document.getElementById("email0").value = '';
}

function votazioniControlloMail() {
	
	hideMailError();
	
	// check
	var errors=false;
	var notError=false;
	
	
	var listaMailAggiuntive = '';
	var textemails = document.querySelectorAll("input[name^='email[']");
	
	if(textemails.length > 0){
		
		for (i = 0; i < textemails.length; i++) {
			
			var email = textemails[i].value;
			
			if(email != ''){
				
				if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)){
					notError = true;
				}
				else{
					errors = true;
				}
			}
		}
	}
	if(errors) {
		$("#votazioniFormMailErrorDiv").show();
		$("#errorMail").show();
		$("#panelInviaSollecito").animate({ scrollTop: 0 }, "slow");
	}
	return errors;
}

function hideMailError(){
	$("#votazioniFormMailErrorDiv").hide();
	$("#errorMail").hide();
}

function generaReportVendor(){
	console.log("genera il report VENDOR per il semestre: " + semestreScelto);
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "vendormanagement/generaReportVendor.action?semestre="+semestreScelto;
	url=legalSecurity.verifyToken(url);
	downloadFile(url);
}

function downloadFile(urlToSend) {
	
    var req = new XMLHttpRequest();
    req.open("GET", urlToSend, true);
    req.responseType = "document";
    req.onload = function (event) {
        var link=document.createElement('a');
        link.href=urlToSend;
        link.target="_blank";
        link.click();
        waitingDialog.hide();
    };
    req.send();
}
