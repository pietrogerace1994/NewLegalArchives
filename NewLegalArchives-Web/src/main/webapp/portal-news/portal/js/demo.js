$(window).load(function(){
    //Welcome Message (not for login page)
    function notify(message, type){
        $.growl({
            message: message
        },{
            type: type,
            allow_dismiss: false,
            label: 'Chiudi',
            className: 'btn-xs btn-inverse',
            placement: {
                from: 'bottom',
                align: 'right'
            },
            delay: 2500,
            animate: {
                    enter: 'animated fadeInRight',
                    exit: 'animated fadeOutRight'
            },
            offset: {
                x: 30,
                y: 30
            }
        });
    };

    if (!$('.login-content')[0]) {
      //  notify('Bentornato Alessandro Rossi', 'inverse');
    }
});

$(document).ready(function() {
	
	/*--------------------------------------
        Drag & Drop Widget
    ---------------------------------------*/
	$(".connectedSortable").sortable({
        placeholder: "sort-highlight",
        connectWith: ".connectedSortable",
        handle: ".card-header",
        forcePlaceholderSize: true,
        zIndex: 999999
    }).disableSelection();
    $(".connectedSortable .card-header").css("cursor", "move");
      
	
    /*--------------------------------------
        Header Color
    ---------------------------------------*/
    $('body').on('click', '.hc-trigger', function() {
        $(this).parent().toggleClass('toggled');
    });
    
    $('body').on('click', '.hc-item', function() {
        var v = $(this).data('ma-header-value');

        $('.hc-item').removeClass('selected');
        $(this).addClass('selected');


        $('body').attr('data-ma-header', v);
    });

    /*--------------------------------------
        Animation
     ---------------------------------------*/
    $('body').on('click', '.animation-demo .btn', function(){
        var animation = $(this).text();
        var cardImg = $(this).closest('.card').find('img');
        if (animation === "hinge") {
            animationDuration = 2100;
        }
        else {
            animationDuration = 1200;        }

        cardImg.removeAttr('class');
        cardImg.addClass('animated '+animation);

        setTimeout(function(){
            cardImg.removeClass(animation);
        }, animationDuration);
    });


    /*--------------------------------------
         Notifications & Dialogs
     ---------------------------------------*/
    /*
     * Notifications
     */
    function notify(from, align, icon, type, animIn, animOut){
        $.growl({
            icon: icon,
            title: ' Bootstrap Growl ',
            message: 'Turning standard Bootstrap alerts into awesome notifications',
            url: ''
        },{
            element: 'body',
            type: type,
            allow_dismiss: true,
            placement: {
                from: from,
                align: align
            },
            offset: {
                x: 30,
                y: 30
            },
            spacing: 10,
            z_index: 1031,
            delay: 2500,
            timer: 1000,
            url_target: '_blank',
            mouse_over: false,
            animate: {
                enter: animIn,
                exit: animOut
            },
            icon_type: 'class',
            template: '<div data-growl="container" class="alert" role="alert">' +
            '<button type="button" class="close" data-growl="dismiss">' +
            '<span aria-hidden="true">&times;</span>' +
            '<span class="sr-only">Close</span>' +
            '</button>' +
            '<span data-growl="icon"></span>' +
            '<span data-growl="title"></span>' +
            '<span data-growl="message"></span>' +
            '<a href="#" data-growl="url"></a>' +
            '</div>'
        });
    };

    $('.notifications > div > .btn').click(function(e){
        e.preventDefault();
        var nFrom = $(this).attr('data-from');
        var nAlign = $(this).attr('data-align');
        var nIcons = $(this).attr('data-icon');
        var nType = $(this).attr('data-type');
        var nAnimIn = $(this).attr('data-animation-in');
        var nAnimOut = $(this).attr('data-animation-out');

        notify(nFrom, nAlign, nIcons, nType, nAnimIn, nAnimOut);
    });


    /*
     * Dialogs
     */
      
	
	//Save Search/Report
	 $('#save-search').click(function(){
        swal({   
			title: "Salva la ricerca",   
			text: "Dai un nome alla ricerca e per trovarla poi facilmente nei tuoi preferiti",   
			type: "input",   
			showCancelButton: true,   
			closeOnConfirm: false,   
			animation: "slide-from-top",   
			inputPlaceholder: "Write something" }, 
				function(inputValue){   
					if (inputValue === false) return false; if (inputValue === "") { swal.showInputError("Dai un nome alla tua ricerca"); return false} swal("Perfetto! La ricerca: " + inputValue, "è stata salvata"); });
    });
	//Edit/Delete buttons
	 $('.delete').click(function(){
			swal({   
				title: "Sicuro di voler cancellare il file?",   
				text: "You will not be able to recover this imaginary file!",   
				type: "warning", 
				animation:false,  
				showCancelButton: true,   
				confirmButtonColor: "#DD6B55",   
				confirmButtonText: "cancella",   
				closeOnConfirm: false }, function(){   
					swal("File cancellato!", "Your imaginary file has been deleted.", "success"); 
			});
	 });

	
	

    /*--------------------------------------
        Components
     ---------------------------------------*/
    $('body').on('click', '#btn-color-targets > .btn', function(){
        var color = $(this).data('target-color');
        $('#modalColor').attr('data-modal-color', color);
    });
	
	
	

	
	
	
	
	
	
});

