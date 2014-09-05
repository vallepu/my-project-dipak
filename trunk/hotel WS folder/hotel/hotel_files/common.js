/*
 * Holiday UI IndexPage index.js 1.0
 *
 * Copyright (c) 2009 Cox & Kings India Ltd
 *
 * Depends:
 *	$.common.js
*/

	 function content (packageId, brochureId, contentType, product) {
	 var packageClass = $('#packageHotelClass').val();
		$.ajax({
			url: "content?packageId="+packageId+"&brochureId="+brochureId+"&contentType="+contentType+"&product="+product+"&packageClass="+packageClass,
      dataType: "text/html",
      success: (function(data) {
      $('#contentDetail').html(data);
      })
		});
	};
	
	
	function countryguide (section, countryName)
	{
	$('#country').val(countryName);
	travelguide(section);
	}
	
	 function travelguide (section) {
	 var countryName = $('#country').val();
	 $('#countryName').html(countryName);
		$.ajax({
			url: "travelguidedetail?countryName="+countryName+"&section="+section,
      dataType: "text/html",
      success: (function(data) {
      $('#contentDetail').html(data);
      })
		});
	};
	

  function toggle(showtab, hidetab)
  {
    $('#'+hidetab).hide();
    $('#'+showtab).show();
  }
	
	function show(tab)
	{
	$('#'+tab).show();
	}
	
// hover effect
$(document).ready(function() {
  $('div.demo-show h3').add('div.demo-show2 h3').hover(function() {
    $(this).addClass('hover');
  }, function() {
    $(this).removeClass('hover');
  });
});

// independently show and hide
$(document).ready(function() {
  $('div.demo-show:eq(0) > div').hide();  
  $('div.demo-show:eq(0) > h3').click(function() {
    $(this).next().slideToggle('fast');
  });
});

// one showing at a time

$(document).ready(function() {
  $('div.demo-show:eq(1) > div:gt(0)').hide();  
  $('div.demo-show:eq(1) > h3').click(function() {
    $(this).next('div:hidden').slideDown('fast')
    .siblings('div:visible').slideUp('fast');
  });
});


//simultaneous showing and hiding

$(document).ready(function() {
  $('div.demo-show2:eq(0) > div').hide();
  $('div.demo-show2:eq(0) > h3').click(function() {
    $(this).next('div').slideToggle('fast')
    .siblings('div:visible').slideUp('fast');
  });
});

//queued showing and hiding
$(document).ready(function() {
  $('div.demo-show2:eq(1) > div').hide();  
  $('div.demo-show2:eq(1) > h3').click(function() {
    var $nextDiv = $(this).next();
    var $visibleSiblings = $nextDiv.siblings('div:visible');

    if ($visibleSiblings.length ) {
      $visibleSiblings.slideUp('fast', function() {
        $nextDiv.slideToggle('fast');
      });
    } else {
       $nextDiv.slideToggle('fast');
    }
  });
});


function showTab(layerName) {
$('#'+layerName).removeClass('tablft_off tabrt_off');
$('#'+layerName).addClass('tablft_on tabrt_on');

}

function hideTab(layerName) {
$('#'+layerName).removeClass('tablft_on tabrt_on');
$('#'+layerName).addClass('tablft_off tabrt_off');
}

function tabs(tab_name) {
var on_tab = $('#selectedTab').val();
hideTab(on_tab);
showTab(tab_name);
on_tab=tab_name;
}