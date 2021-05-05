/*
 * Detact Mobile Browser
 */
if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
   $('html').addClass('ismobile');
}

function cambialingua(ln){
	/*var currentUrl = location.href;
	if( currentUrl.indexOf("language=") != -1 ){
		var index = currentUrl.indexOf("language=");
		var oldParamLanguage = currentUrl.substring( index , index+14);
		currentUrl = currentUrl.replace(oldParamLanguage, "");
	}
	var newUrl = currentUrl.indexOf("?") != -1 ? currentUrl+"&language="+ln : currentUrl+"?language="+ln;
	location.href=newUrl;*/
	location.href=WEBAPP_BASE_URL+"?language="+ln;
}

$(window).load(function () {
    /* --------------------------------------------------------
        Page Loader
     ---------------------------------------------------------*/
    if(!$('html').hasClass('ismobile')) {
        if($('.page-loader')[0]) {
      /*      setTimeout (function () {
                $('.page-loader').fadeOut();
            }, 500);*/

        }
    }
})

$(document).ready(function(){

    /* --------------------------------------------------------
        Scrollbar
    ----------------------------------------------------------*/
    function scrollBar(selector, theme, mousewheelaxis) {
        $(selector).mCustomScrollbar({
            theme: theme,
            scrollInertia: 100,
            axis:'yx',
            mouseWheel: {
                enable: true,
                axis: mousewheelaxis,
                preventDefault: true
            }
        });
    }

    if (!$('html').hasClass('ismobile')) {
        //On Custom Class
        if ($('.c-overflow')[0]) {
            scrollBar('.c-overflow', 'minimal-dark', 'y');
        }
    }

    /* --------------------------------------------------------
        Top Search
    ----------------------------------------------------------*/
    
    /* Bring search reset icon when focused */
    $('body').on('focus', '.hs-input', function(){
        $('.h-search').addClass('focused');
    });
    
    /* Take off reset icon if input length is 0, when blurred */
    $('body').on('blur', '.hs-input', function(){
        var x = $(this).val();
        
        if (!x.length > 0) {
            $('.h-search').removeClass('focused');
        } 
    });


    /* --------------------------------------------------------
        User Alerts
    ----------------------------------------------------------*/
    $('body').on('click', '[data-user-alert]', function(e) {
        e.preventDefault();        
        var u = $(this).data('user-alert');
        $('.'+u).tab('show');
        
    });


    /* ---------------------------------------------------------
         Todo Lists
     ----------------------------------------------------------*/
    if($('#todo-lists')[0]) {

        //Pre checked items
        $('#todo-lists .acc-check').each(function () {
            if($(this).is(':checked')) {
                $(this).closest('.list-group-item').addClass('checked');
            }
        });

        //On check
        $('body').on('click', '#todo-lists .acc-check', function () {
           if($(this).is(':checked')) {
               $(this).closest('.list-group-item').addClass('checked');
           }
            else {
               $(this).closest('.list-group-item').removeClass('checked');
           }
        });
    }

	/* Data Grid */
  	if($('#data-table-command')[0]) {
			$("#data-table-command").bootgrid({
                    css: {
                        icon: 'zmdi icon',
                        iconColumns: 'zmdi-view-module',
                        iconDown: 'zmdi-expand-more',
                        iconRefresh: 'zmdi-refresh',
                        iconUp: 'zmdi-expand-less'
                    },
                    formatters: {
                        "commands": function(column, row) {
                            return "<button type=\"button\" class=\"btn btn-icon command-edit waves-effect waves-circle\" data-row-id=\"" + row.id + "\"><span class=\"zmdi zmdi-edit\"></span></button> " + 
                                "<button type=\"button\" class=\"btn btn-icon command-delete waves-effect waves-circle\" data-row-id=\"" + row.id + "\"><span class=\"zmdi zmdi-delete\"></span></button>";
                        }
                    }
                });
  }
  
  
   /*
     * Tables
     */
	if($('#data-table-selection')[0]) { 
		$("#data-table-selection").bootgrid({
                    css: {
                        icon: 'zmdi icon',
                        iconColumns: 'zmdi-view-module',
                        iconDown: 'zmdi-expand-more',
                        iconRefresh: 'zmdi-refresh',
                        iconUp: 'zmdi-expand-less'
                    },
                    selection: true,
                    multiSelect: true,
                    rowSelect: true,
                    keepSelection: true
                });
	}
  	if($('#data-table')[0]) {  
  		
		$('#data-table').bootstrapTable({
                method: 'get',
                url: 'data/ricerca-fascicolo-responsabili.json',
                cache: false,
                height: 600,
                striped: true,
                pagination: true,
                pageSize: 50,
                pageList: [10, 25, 50, 100, 200],
                search: true,
                showColumns: true,
                showRefresh: false,
				showToggle:true,
                minimumCountColumns: 2,
                clickToSelect: true,
				onClickRow: function (row) {
                    document.location.href='apri-fascicolo.html';
                },
                columns: [
					{
						field: 'id',
						title: 'id',
						align: 'left',
						valign: 'top',
						sortable: true
					
					},{
						field: 'Numero-Fascicolo',
						title: 'Numero Fascicolo',
						align: 'left',
						valign: 'top',
						sortable: true
					
					},{
						field: 'Legale-Esterno-Incaricato',
						title: 'Legale Esterno Incaricato',
						align: 'left',
						valign: 'top',
						sortable: true
					},{
						field: 'Controparte',
						title: 'Controparte',
						align: 'left',
						valign: 'top',
						sortable: true
					},{
						field: 'Dal',
						title: 'Dal',
						align: 'left',
						valign: 'top',
						sortable: true
					},{
						field: 'Al',
						title: 'Al',
						align: 'left',
						valign: 'top',
						sortable: true
					},{
					
						field: 'Tipologia-Atto',
						title: 'Tipologia Atto',
						align: 'left',
						valign: 'top',
						sortable: true
					}
				]
            });			
			function totalTextFormatter(data) {
				return 'Total';
			}	
	
	}




    

     /*
     * Auto Hight Textarea
     */
    if ($('.auto-size')[0]) {
	   autosize($('.auto-size'));
    }

    /*
    * Profile Menu
    */
    $('body').on('click', '.profile-menu > a', function(e){
        e.preventDefault();
        $(this).parent().toggleClass('toggled');
	    $(this).next().slideToggle(200);
    });

    /*
     * Text Feild
     */

    //Add blue animated border and remove with condition when focus and blur
    if($('.fg-line')[0]) {
        $('body').on('focus', '.fg-line .form-control', function(){
            $(this).closest('.fg-line').addClass('fg-toggled');
        })

        $('body').on('blur', '.form-control', function(){
            var p = $(this).closest('.form-group, .input-group');
            var i = p.find('.form-control').val();

            if (p.hasClass('fg-float')) {
                if (i.length == 0) {
                    $(this).closest('.fg-line').removeClass('fg-toggled');
                }
            }
            else {
                $(this).closest('.fg-line').removeClass('fg-toggled');
            }
        });
    }

    //Add blue border for pre-valued fg-flot text feilds
    if($('.fg-float')[0]) {
        $('.fg-float .form-control').each(function(){
            var i = $(this).val();

            if (!i.length == 0) {
                $(this).closest('.fg-line').addClass('fg-toggled');
            }

        });
    }

    /*
     * Tag Select
     */
    if($('.chosen')[0]) {
        $('.chosen').chosen({
            width: '100%',
            allow_single_deselect: true
        });
    }

    /*
     * Input Slider
     */
    //Basic
    if($('.input-slider')[0]) {
        $('.input-slider').each(function(){
            var isStart = $(this).data('is-start');

            $(this).noUiSlider({
                start: isStart,
                range: {
                    'min': 0,
                    'max': 100,
                }
            });
        });
    }

    //Range slider
    if($('.input-slider-range')[0]) {
	$('.input-slider-range').noUiSlider({
	    start: [30, 60],
	    range: {
		    'min': 0,
		    'max': 100
	    },
	    connect: true
	});
    }

    //Range slider with value
    if($('.input-slider-values')[0]) {
	$('.input-slider-values').noUiSlider({
	    start: [ 45, 80 ],
	    connect: true,
	    direction: 'rtl',
	    behaviour: 'tap-drag',
	    range: {
		    'min': 0,
		    'max': 100
	    }
	});

	$('.input-slider-values').Link('lower').to($('#value-lower'));
        $('.input-slider-values').Link('upper').to($('#value-upper'), 'html');
    }

    /*
     * Input Mask
     */
    if ($('input-mask')[0]) {
        $('.input-mask').mask();
    }

    /*
     * Color Picker
     */
    if ($('.color-picker')[0]) {
	    $('.color-picker').each(function(){
            var colorOutput = $(this).closest('.cp-container').find('.cp-value');
            $(this).farbtastic(colorOutput);
        });
    }

    /*
     * HTML Editor
     */
    if ($('.html-editor')[0]) {
	  
    } 
    //Air Mode
    if($('.html-editor-airmod')[0]) {
        $('.html-editor-airmod').summernote({
            airMode: true
        });
    }
    /*
     * Date Time Picker
     */
    //Date Time Picker
    if ($('.date-time-picker')[0]) {
	   $('.date-time-picker').datetimepicker({
   	    	format: 'DD/MM/YYYY HH:mm:ss' 
	   });
	   
    }
    //Time
    if ($('.time-picker')[0]) {
    	$('.time-picker').datetimepicker({
    	    format: 'LT'
    	});
    }
    //Date
    if ($('.date-picker')[0]) {
    	$('.date-picker').datetimepicker({
    	    format: 'DD/MM/YYYY'
    	});
    } 
    //Year
    if ($('.date-YYYY-picker')[0]) {
    	$('.date-YYYY-picker').datetimepicker({
    	    format: 'YYYY'
    	});
    }
    /*
     * Form Wizard
     */
    if ($('.form-wizard-basic')[0]) {
    	$('.form-wizard-basic').bootstrapWizard({
    	    tabClass: 'fw-nav',
            'nextSelector': '.next',
            'previousSelector': '.previous'
    	});
    }
    /*
     * Bootstrap Growl - Notifications popups
     */
    function notify(message, type){
        $.growl({
            message: message
        },{
            type: type,
            allow_dismiss: false,
            label: 'Cancel',
            className: 'btn-xs btn-inverse',
            placement: {
                from: 'top',
                align: 'right'
            },
            delay: 2500,
            animate: {
                    enter: 'animated bounceIn',
                    exit: 'animated bounceOut'
            },
            offset: {
                x: 20,
                y: 85
            }
        });
    };

    /*
     * Waves Animation
     */
    (function(){
         Waves.attach('.btn:not(.btn-icon):not(.btn-float)');
         Waves.attach('.btn-icon, .btn-float', ['waves-circle', 'waves-float']);
        Waves.init();
    })();

    /*
     * Lightbox
     */
    if ($('.lightbox')[0]) {
        $('.lightbox').lightGallery({
            enableTouch: true
        });
    }
    /*
     * Link prevent
     */
    $('body').on('click', '.a-prevent', function(e){
        e.preventDefault();
    });

    /*
     * Collaspe Fix
     */
    if ($('.collapse')[0]) {
        //Add active class for opened items
        $('.collapse').on('show.bs.collapse', function (e) {
            $(this).closest('.panel').find('.panel-heading').addClass('active');
        });
        $('.collapse').on('hide.bs.collapse', function (e) {
            $(this).closest('.panel').find('.panel-heading').removeClass('active');
        });
        //Add active class for pre opened items
        $('.collapse.in').each(function(){
            $(this).closest('.panel').find('.panel-heading').addClass('active');
        });
    }

    /*
     * Tooltips
     */
    if ($('[data-toggle="tooltip"]')[0]) {
        $('[data-toggle="tooltip"]').tooltip();
    }

    /*
     * Popover
     */
    if ($('[data-toggle="popover"]')[0]) {
        $('[data-toggle="popover"]').popover();
    }
    /*
     * Login
     */
    if ($('.login')[0]) {
       
    }
    /*
     * Fullscreen Browsing
     */
    if ($('[data-action="fullscreen"]')[0]) {
	var fs = $("[data-action='fullscreen']");
	fs.on('click', function(e) {
	    e.preventDefault();
	    //Launch
	    function launchIntoFullscreen(element) {

		if(element.requestFullscreen) {
		    element.requestFullscreen();
		} else if(element.mozRequestFullScreen) {
		    element.mozRequestFullScreen();
		} else if(element.webkitRequestFullscreen) {
		    element.webkitRequestFullscreen();
		} else if(element.msRequestFullscreen) {
		    element.msRequestFullscreen();
		}
	    }

	    //Exit
	    function exitFullscreen() {

		if(document.exitFullscreen) {
		    document.exitFullscreen();
		} else if(document.mozCancelFullScreen) {
		    document.mozCancelFullScreen();
		} else if(document.webkitExitFullscreen) {
		    document.webkitExitFullscreen();
		}
	    }

	    launchIntoFullscreen(document.documentElement);
	    fs.closest('.dropdown').removeClass('open');
	});
    }
    /*
     * Profile Edit Toggle
     */
    if ($('[data-pmb-action]')[0]) {
        $('body').on('click', '[data-pmb-action]', function(e){
            e.preventDefault();
            var d = $(this).data('pmb-action');

            if (d === "edit") {
                $(this).closest('.pmb-block').toggleClass('toggled');
            }

            if (d === "reset") {
                $(this).closest('.pmb-block').removeClass('toggled');
            }


        });
    }
    /*
     * IE 9 Placeholder
     */
    if($('html').hasClass('ie9')) {
        $('input, textarea').placeholder({
            customClass: 'ie9-placeholder'
        });
    }
    /*
     * Print
     */
    if ($('[data-action="print"]')[0]) {
        $('body').on('click', '[data-action="print"]', function(e){
            e.preventDefault();
            window.print();
        })
    }
    /*
     * Typeahead Auto Complete
     */
  /*   if($('.typeahead')[0]) {
          var controparteArray = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
            'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
            'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
            'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota',
            'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire',
            'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
            'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island',
            'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
            'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'
          ];
		  var legaliArray = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
            'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
            'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
            'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota',
            'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire',
            'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
            'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island',
            'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
            'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'
          ];
        var ListaControparte = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.whitespace,
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            local: controparteArray
        });
		 var ListaLegaleEsterno = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.whitespace,
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            local: legaliArray
        });*/
		/* Controparte  
        $('#controparte.typeahead').typeahead({
            hint: true,
            highlight: true,
            minLength: 1
        },
        {
          name: 'ListaControparte',
          source: ListaControparte
        });
		/* Legale Esterno  		
		$('#legale_esterno.typeahead').typeahead({
            hint: true,
            highlight: true,
            minLength: 1
        },
        {
          name: 'ListaLegaleEsterno',
          source: ListaLegaleEsterno
        });
    }*/
    /*
     * Skin Change
     */
    $('body').on('click', '[data-skin]', function() {
        var currentSkin = $('[data-current-skin]').data('current-skin');
        var skin = $(this).data('skin');

        $('[data-current-skin]').attr('data-current-skin', skin)

    });

});

function goToAncora(ancora){ 
	window.addEventListener('load', function(){
		var aTag = $("a[name='"+ ancora +"']");
	    $('html,body').animate({scrollTop: aTag.offset().top},'slow');	
	});
    
}

function showGraffa(id){
	document.getElementById(id).style.display="inline";
}

function hideGraffa(id){
	document.getElementById(id).style.display="none";
}


$(document).ready(function(){
	$(':input[type="number"]').keydown(function(event) {
		if ( event.keyCode==188 || event.keyCode==109 ||  event.keyCode==189 ||  event.keyCode==187 ||  event.keyCode==187 ) { 
		   return false;
		}
	});
});

