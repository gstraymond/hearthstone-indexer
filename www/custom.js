// facetview 
jQuery(document).ready(function($) {
  $('.facet-view-simple').each(function() {
	  $(this).facetview({
	    search_url: searchUrl,
	    search_index: 'hearthstone',
	    datatype: 'json',
	    facets: [
	        {'field': 'castingCost',	'display': 'Casting cost'},
	        {'field': 'clazz',			'display': 'Class'},
	        {'field': 'attack',			'display': 'Attack'},
	        {'field': 'health',			'display': 'Health'},
	        {'field': 'minionType',		'display': 'Minion Type'},
	        {'field': 'set',			'display': 'Set'},
	        {'field': 'rarity',			'display': 'Rarity'},
	        {'field': 'type',			'display': 'Type'},
	        {'field': 'capabilities',	'display': 'Capabilities'},
	    ],
	    paging: { size: 10 },
	    default_operator: "AND",
	    sort: ['_uid'],
	    result_display: [
			[
		 	    {
		 	        "pre": "<span class='mana'>",
	 	        	"field": "castingCost",
		 	        "post": "</span>&nbsp;"
	 	    	},
		 	    {
		 	        "pre": "<strong class='title'>",
		 	        "field": "title",
			 	    "post": "</strong>"
		 	    },
	 	    ],
	 	    [
		 	    {
		 	        "pre": "<span class='label label-info'>",
		        	"field": "clazz",
		 	        "post": "</span>&nbsp;"
		    	},
		 	    {
		 	        "pre": "<span class='label label-warning'>",
	 	        	"field": "set",
		 	        "post": "</span>&nbsp;"
	 	    	},
		 	    {
		 	        "pre": "<span class='",
	 	        	"field": "rarity",
	 	    	},
		 	    {
		 	        "pre": " rarity'>",
	 	        	"field": "rarity",
		 	        "post": "</span>&nbsp;"
	 	    	},
		 	    {
		 	        "pre": "<span class='label label-warning'>",
	 	        	"field": "type",
		 	        "post": "</span>&nbsp;"
	 	    	},
		 	    {
		 	        "pre": "<span class='label label-success'>",
	 	        	"field": "minionType",
		 	        "post": "</span>&nbsp;"
	 	    	},
		 	    {
		 	        "pre": "<span class='label label-success'>",
	 	        	"field": "elite",
		 	        "post": "</span>&nbsp;"
	 	    	},
		    ],
	 	    [
		 	    {
		 	        "pre": "<pre class='description'>",
		        	"field": "description",
		 	        "post": "</pre>"
		    	},
		    ],
		    [
		 	    {
		 	    	"pre": "&nbsp;<span class='attack'>",
		        	"field": "attack",
		        	"post": "</span>&nbsp;"
		    	},
		 	    {
		 	    	"pre": "<span class='health'>",
		        	"field": "health",
		        	"post": "</span>"
		    	},
		    ],
	 	    [
		 	    {
		 	        "pre": "<div class='capabilities' style='display:none'>",
		        	"field": "capabilities",
		 	        "post": "</div>"
		    	},
		    ],
	 	    [
		 	    {
		 	        "pre": "<a class='pic' href='",
		        	"field": "image",
		 	        "post": "'>Picture</a>"
		    	},
		    ]
	     ]
	  });
  });
});

$(document).ajaxComplete(function() {        
	// initialisation de la gallery des images des cartes
    $('a.pic').attr('rel', 'gallery');
    $('a.pic').fancybox();

    $('.thumbnail').click(function() {
    	$(this).siblings('a.pic').click();
    });
    
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