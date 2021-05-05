/**
 * 	
 */

var portalenews = {
	seleziona_categoria : function(a,p) {
		n=p||'1';
		form = document.getElementById("form-archivio-filtrato");
		form.archivio.value = a.id;
		form.archanno.value=$("#anno")[0]?$("#anno").val():'0';
		form.cbpage.value=n;
		form.submit();
	},
	seleziona_categoria_articoli : function(a,p) {
		n=p||'1';
		form = document.getElementById("form-archivio-filtrato-articoli");
		form.archivio.value = a.id;
		form.archanno.value=$("#anno")[0]?$("#anno").val():'0';
		form.cbsearch.value=$("#search").val();
		form.cbpage.value=n;
		form.submit();
	},
	categoria_tutte : function(p) {
		
		n=p||'1';
		form = document.getElementById("form-archivio-all");
		form.cbanno.value=$("#anno")[0]?$("#anno").val():'0';
		form.cbpage.value=n;
		form.submit();
	},
	archivio_articoli : function(p) {
		
		n=p||'1';
		form = document.getElementById("form-search-archivio-articoli");
		form.cbanno.value=$("#anno")[0]?$("#anno").val():'0';
		form.cbpage.value=n;
		form.submit();
	},
	open_newsletter : function(id, p) {
		n=p|0||'1';
		form = document.getElementById("form-articles-newsletter");
		form.newsletterid.value =id;
		form.cbpage.value=n;
		form.submit();
	},
	open_article:function(id){
		location.href="./dettaglio.action?article="+id;
	},
	filtra_sottocategoria:function(a,p){
		n=p||'1';
		form = document.getElementById("form-archivio-filtrato-sottocategoria");
		form.archivio.value = $("#idCatPadre").val();
		form.archanno.value=$("#anno")[0]?$("#anno").val():'0';
		form.archsottocategoria.value=$(a).val();
		form.cbpage.value=n;
		form.submit();
	},
	filtra_sottocategoria_articoli:function(a,p){
		n=p||'1';
		form = document.getElementById("form-archivio-articoli-filtrato-sottocategoria");
		form.archivio.value = $("#idCatPadre").val();
		form.archanno.value=$("#anno")[0]?$("#anno").val():'0';
		form.archsottocategoria.value=$(a).val();
		form.cbsearch.value=$('#search').val();
		form.cbpage.value=n;
		form.submit();
	},
	filtra_sottocategoriaFromDettaglio:function(a){
		 
		form = document.getElementById("form-archivio-filtrato-sottocategoria");
		form.archivio.value = $("#idCatPadre").val();
		form.archanno.value=$("#anno")[0]?$("#anno").val():'0';
		form.archsottocategoria.value=a;
		form.cbpage.value=1;
		form.submit();
	},
	filtra_sottocategoriaFromDettaglio_articoli:function(a){
		 
		form = document.getElementById("form-archivio-articoli-filtrato-sottocategoria");
		form.archivio.value = $("#idCatPadre").val();
		form.archanno.value=$("#anno")[0]?$("#anno").val():'0';
		form.archsottocategoria.value=a;
		form.cbpage.value=1;
		form.submit();
	},
	filtra_categoria_anno:function(a,p){
		n=p||'1';
		form = document.getElementById("form-archivio-filtrato");
		form.archivio.value = $("#idCatPadre").val();
		form.archanno.value=$(a).val();
		form.cbpage.value=n;
		form.submit();
	},
	filtra_categoria_anno_articoli:function(a,p){
		n=p||'1';
		form = document.getElementById("form-archivio-filtrato-articoli");
		form.archivio.value = $("#idCatPadre").val();
		form.archanno.value=$(a).val();
		form.cbpage.value=n;
		form.submit();
	},
	filtra_search:function(p){
		n=p||'1';
		form = document.getElementById("form-search-all");
		if(form){
		form.cbsearch.value=$("#search").val();
		form.cbpage.value=n;
		form.submit();
		}
	},
	filtra_search_articoli:function(p){
		n=p||'1';
		form = document.getElementById("form-search-archivio-articoli");
		if(form){
		form.cbsearch.value=$("#search").val();
		var filtroSelezionato = "";
		var sottoCategoria = ""
		if ($('.filtro').length > 0) {
			for (var  i =0; i<$('.filtro').length; i++) {
				var attr = $($('.filtro')[i]).attr('id');
				if (typeof attr !== typeof undefined && attr !== false && $($('.filtro')[i]).hasClass('active')) {
					filtroSelezionato = $($('.filtro')[i]).attr('id');
					break;
				}
			}
		}
		if ($('#sottoCategoria').length>0) {
			sottoCategoria = $('#sottoCategoria').val();
		}
		form.categoria.value = filtroSelezionato;
		form.sottocategoria.value = sottoCategoria;
		form.cbpage.value=n;
		form.submit();
		}
	},
	initPaginationArchivio:function(){
		if($('.pagination-archive')[0])
		$('.pagination-archive').each(function(){
			
			$(this).unbind().bind('click',function(e){
				portalenews.categoria_tutte($(this).attr("data-page"));
			})
			
		})
		
	},
	initPaginationArchivioArticoli:function(){
		if($('.pagination-archive-articoli')[0])
		$('.pagination-archive-articoli').each(function(){
			
			$(this).unbind().bind('click',function(e){
				portalenews.archivio_articoli($(this).attr("data-page"));
			})
			
		})
		
	},
	initPaginationArchivioFiltrato:function(){
		if($('.pagination-archive-filtrato')[0])
		$('.pagination-archive-filtrato').each(function(){
			
			$(this).unbind().bind('click',function(e){
				
				portalenews.seleziona_categoria({id:$("#idCatPadre").val()},$(this).attr("data-page"));
			})
			
		})
		
	},
	initPaginationArchivioArticoliFiltrato:function(){
		if($('.pagination-archive-articoli-filtrato')[0])
		$('.pagination-archive-articoli-filtrato').each(function(){
			
			$(this).unbind().bind('click',function(e){
				
				portalenews.seleziona_categoria_articoli({id:$("#idCatPadre").val()},$(this).attr("data-page"));
			})
			
		})
		
	},
	initPaginationNewsLettArticle:function(){
		if($('.pagination-news-article')[0])
		$('.pagination-news-article').each(function(){
			
			$(this).unbind().bind('click',function(e){
				
				portalenews.open_newsletter($("#newsletterid").val(),$(this).attr("data-page"));
			})
			
		})
		
	}
}

$(document).ready(function(){
	
	portalenews.initPaginationArchivio();
	
	portalenews.initPaginationArchivioArticoli();
	
	portalenews.initPaginationArchivioFiltrato();
	
	portalenews.initPaginationNewsLettArticle();
	
	portalenews.initPaginationArchivioArticoliFiltrato();
})

