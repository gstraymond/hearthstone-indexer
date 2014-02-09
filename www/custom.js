function setLang(lang) {
	localStorage['hs_lang'] = lang;
    document.location.href = '/';
}

// facetview 
jQuery(document).ready(function($) {
  $('.facet-view-simple').each(function() {
	  $(this).facetview({
	    search_url: __searchUrl,
	    search_index: 'hearthstone',
	    datatype: 'json',
	    facets: [
	        {'field': 'castingCost',	    'display': __castingCost},
	        {'field': 'clazz.exact',	    'display': __clazz},
	        {'field': 'attack',			    'display': __attack},
	        {'field': 'health',			    'display': __health},
	        {'field': 'minionType.exact',   'display': __minionType},
	        {'field': 'set.exact',		    'display': __set},
	        {'field': 'rarity.exact',	    'display': __rarity},
	        {'field': 'type.exact',		    'display': __type},
	        {'field': 'capabilities.exact',	'display': __capabilities},
	    ],
	    paging: { size: 12 },
	    default_operator: "AND",
	    pager_on_top: true,
	    sort: ['_uid'],
	    display_images: false,
        searchwrap_start: '<div id="facetview_results">',
        searchwrap_end: '</div>',
        resultwrap_start: '',
        resultwrap_end: '',
	    result_display: [
			[
		 	    {
		 	        "pre": "<a class='piclink' href='",
		        	"field": "image",
		 	        "post": "'>"
		    	},
		 	    {
		 	        "pre": "<img class='pic' src='",
		        	"field": "image"
		    	},
		 	    {
		 	        "pre": "' alt='",
		        	"field": "title",
		 	        "post": "'></a>"
		    	},
		    ]
	     ]
	  });
  });
});

$(document).ajaxComplete(function() {        
	// initialisation de la gallery des images des cartes
    $('.piclink').attr('rel', 'gallery');
    $('.piclink').fancybox();
    
    $('.capabilities').each(function() {
    	var desc = $(this).siblings('.description').text();
    	console.log(desc)
    	$(this).text().split(', ').forEach(function (capability) {
			var re = new RegExp(capability, 'g');
    		desc = desc.replace(re, '<strong>' + capability + '</strong>');
    	});
    	$(this).siblings('.description').html(desc);
    });
});