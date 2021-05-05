$(document).ready(function () {
	
	$("#btn-fascicoloClose").click(function(event){	
		pulisciCampiModal();
		chiudiModalFascicoloIncarico();
	});

	$('#modalIncarico').on('hide.bs.modal', function (e) {
		pulisciCampiModal();
	});
	
	$("#btnIncaricoFascicoloSelect").click(function(event){	
		//fascicolo
		var incaricoFascicoliId = $("#incaricoFascicoliId").val();
		
		var hasErrors=false;
		if(incaricoFascicoliId=="") {
			hasErrors=true;
			$("#errorMsgIncaricoFascicolo").removeClass("hidden");
		}
		
		if(hasErrors) {	
			$("#errorMsgIncaricoDiv").removeClass("hidden");
			 $("#modalIncarico").animate({ scrollTop: 0 }, "slow");
			return;
		}
		chiudiModalFascicoloIncarico();
		changeTipologia(incaricoFascicoliId);
		
	});
	
	changeTipologia(1);
	caricaStatisticheBudgetResponsabile();
});

function changeTipologia(tipo) {
	
	if(tipo == 1){
		$("#menu-torta").empty();
		 
		var str = "";
		str += "<a id=\"btn-utente\" class=\"btn btn-success\" style=\"width:90%; font-size: 90%\" href=\"javascript:void(0);\" onclick=\"changeTipologia(1)\">";
		str += "<i class=\"fa fa-toggle-on fa-2x\"></i>&nbsp; " + strings['fascicolo.label.utente'] + " </a>";
		str += "<br><br>";
		str += "<a id=\"btn-fascicolo\" class=\"btn btn-primary\" style=\"width:90%; font-size: 90%; background-color:#e6e6e6; border-color: #e6e6e6; color:black;\" href=\"javascript:void(0);\" onclick=\"apriModaleRicercaFascicolo()\">";
		str += "<i class=\"fa fa-toggle-off fa-2x\"></i>&nbsp; " + strings['calendar.label.fascicolo'] + " </a>";
		str += "<br>";
		
		$(str).appendTo("#menu-torta");
	}else{
		$("#menu-torta").empty();
		 
		var str = "";
		str += "<a id=\"btn-utente\" class=\"btn btn-primary\" style=\"width:90%; font-size: 90%; background-color:#e6e6e6; border-color: #e6e6e6; color:black;\" href=\"javascript:void(0);\" onclick=\"changeTipologia(1)\">";
		str += "<i class=\"fa fa-toggle-off fa-2x\"></i>&nbsp; " + strings['fascicolo.label.utente'] + " </a>";
		str += "<br><br>";
		str += "<a id=\"btn-fascicolo\" class=\"btn btn-success\" style=\"width:90%; font-size: 90%\" href=\"javascript:void(0);\" onclick=\"apriModaleRicercaFascicolo()\">";
		str += "<i class=\"fa fa-toggle-on fa-2x\"></i>&nbsp; " + strings['calendar.label.fascicolo'] + " </a>";
		str += "<br>";
		
		$(str).appendTo("#menu-torta");
	}
	
	caricaStatisticheBudget(tipo);
}


function caricaStatisticheBudget(tipo){
	
	$("#torta-budget").empty();
	$("#torta-budget").html('<small class="lgi-text">Loading...</small>');
	$("#torta-legend").empty();
	
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		$("#torta-budget").empty();
		$("#torta-legend").empty();
		
		if(data == ""){
			var str = "";
			str += ' <div class="media-body">  ';
			str += '    <small class="lgi-text">' + strings['index.label.budget.nessunDatoDisponibile'] + '</small>';
			str += ' </div>';
			
			$("#torta-budget").html(str);
		}
		else{
			
			var totale = 0;
			
			for (var i = 0; i < data.length; i++) {
				 
				 var ele = data[i];
				 totale = totale + ele.data;
			}
				
			
			if($('#torta-budget')[0]){
				
				var plotOverlayHandler = function(plot, cvs) {
					  if(!plot) { return; }
					  
					  var cvsWidth = plot.width() / 2;
					  var cvsHeight = plot.height() / 2;
					  var text=strings['index.label.budget.totale'];
					  var text2 = totale;
					  cvs.font = "15px sans-serif";
					  cvs.fillStyle = "gray";
					  cvs.textAlign = 'center';
					  cvs.fillText(text, cvsWidth, cvsHeight);
					  cvsHeight = cvsHeight + 15;
					  cvs.fillText(text2, cvsWidth, cvsHeight);
					  return cvs;
					};
				
				var plot = $.plot('#torta-budget', data, {
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
						container: '.flc-torta-budget',
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
				$("#torta-budget").showMemo();
			}
		}
	};
	var url = WEBAPP_BASE_URL + "index/caricaStatisticheBudget.action?tipo="+tipo;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

$.fn.showMemo = function () {
    $(this).bind("plothover", function (event, pos, item) {
        if (!item) { 
        	$("#flot-memo").empty();
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
        $("#flot-memo").html(html.join(''));
    });
}

function caricaStatisticheBudgetResponsabile(){
	
	$("#torta-responsabile-budget").empty();
	$("#torta-responsabile-budget").html('<small class="lgi-text">Loading...</small>');
	$("#torta-responsabile-legend").empty();
	
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		
		$("#torta-responsabile-budget").empty();
		$("#torta-responsabile-legend").empty();
		
		if(data == ""){
			var str = "";
			str += ' <div class="media-body">  ';
			str += '    <small class="lgi-text">' + strings['index.label.budget.nessunDatoDisponibile'] + '</small>';
			str += ' </div>';
			
			$("#torta-responsabile-budget").html(str);
		}
		else{
			
			var totale = 0;
			
			for (var i = 0; i < data.length; i++) {
				 
				 var ele = data[i];
				 totale = totale + ele.data;
			}
			
			if($('#torta-responsabile-budget')[0]){
				
				var plotOverlayHandler = function(plot, cvs) {
					  if(!plot) { return; }
					  
					  var cvsWidth = plot.width() / 2;
					  var cvsHeight = plot.height() / 2;
					  var text=strings['index.label.budget.totale'];
					  var text2 = totale;
					  cvs.font = "15px sans-serif";
					  cvs.fillStyle = "gray";
					  cvs.textAlign = 'center';
					  cvs.fillText(text, cvsWidth, cvsHeight);
					  cvsHeight = cvsHeight + 15;
					  cvs.fillText(text2, cvsWidth, cvsHeight);
					  return cvs;
				};
				
				var plot = $.plot('#torta-responsabile-budget', data, {
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
						container: '.flc-torta-responsabile-budget',
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
				$("#torta-responsabile-budget").showMemoResp();
			}
		}
	};
	var url = WEBAPP_BASE_URL + "index/caricaStatisticheBudgetResponsabile.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

$.fn.showMemoResp = function () {
    $(this).bind("plothover", function (event, pos, item) {
        if (!item) { 
        	$("#flot-memo-responsabile").empty();
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
        $("#flot-memo-responsabile").html(html.join(''));
    });
}

function apriModaleRicercaFascicolo(){
	pulisciCampiModal();
	pulisciErrors();
	apriModalFascicoloIncarico();
}

function apriModalFascicoloIncarico() {
	$("#incaricoFascicoliId").val('');
	
	$('#incaricoFascicoliNome').removeAttr("disabled");
	$('#btnIncaricoScegliFascicolo').removeClass();
	$('#btnIncaricoScegliFascicolo').addClass("btn btn-primary waves-effect");
	$('#btnIncaricoScegliFascicoloIcon').removeClass();
	$('#btnIncaricoScegliFascicoloIcon').addClass("fa fa-search");
	
	$('#modalIncarico').modal('show');
}
function chiudiModalFascicoloIncarico() {
	$('#modalIncarico').modal('hide');
}

function pulisciCampiModal() {
	$("#incaricoFascicoliNome").val('');
	$("#incaricoFascicoliId").val('');
}

function pulisciErrors() {
	if(!$("#errorMsgIncaricoFascicolo").hasClass("hidden"))
		$("#errorMsgIncaricoFascicolo").addClass("hidden");
	if(!$("#errorMsgIncaricoDiv").hasClass("hidden"))
		$("#errorMsgIncaricoDiv").addClass("hidden");
}