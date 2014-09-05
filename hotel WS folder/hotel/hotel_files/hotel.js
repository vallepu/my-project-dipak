/*
 * Holiday UI IndexPage index.js 1.0
 *
 * Copyright (c) 2009 Cox & Kings India Ltd
 *
 * Depends:
 *	$.floating.js
*/
		$(document).ready(main);
		function main()
		{
		  $(".colorbox").colorbox();
			$("#floatbox").makeFloat({x: "current",y: "current"});
		}

/*
 * By default hide the filledCart div
*/
	$(function()
		{
		  $('#emptyCart').hide();
		  $('#backToHotelSearch').hide();
		  $('#sortByName').hide();
		  $('#newHotelBox').hide();
		  $('#filledCart').show();
		  $('#cityName_1').addClass('b');
		});



	$(function()
  {
	 var hotelCategory = $('#packageHotelClass').val();
	 var packageId = $('#packageTourId').val();
   for ( var i = 3; i <= 5; i++ )
   if (i != hotelCategory) {
   $('#hotelCategory_'+i+'_'+packageId).addClass('greybg_light4');
   $('#packageAmount_'+i+'_'+packageId).addClass('colorgreylight2');
   $('#packagePaxType_'+i+'_'+packageId).addClass('colorgreylight2');
   $('#packageTotalAmount_'+i+'_'+packageId).addClass('colorgreylight2');
   $('#packageTotalPrice_'+i+'_'+packageId).addClass('colorgreylight2');
   }
 });

	
	 function changeHotel (cityName, tabId, destinationId) {
	 tabs(tabId);
	  $('#selectedBox').fadeOut('slow');
	 	$('#indicator').show();
	 	$('#changeHotel_'+tabId).hide();
	  $('#cityTabId').val(tabId);
	 	$('#cityTabName').val(cityName);
	 	var packageId = $('#packageTourId').val();
	 	var hotelCategory = $('#packageHotelClass').val();
	 	var isB2C = $('#isB2C').val();
		$.ajax({
			url: "changehotel?packageId="+packageId+"&cityName="+cityName+"&hotelCategory="+hotelCategory+"&isB2C="+isB2C+"&id="+tabId+"&destinationId="+destinationId+"&sortBy=Null"+"&sortValue=Null",
      dataType: "text/html",
      success: (function(data) {
      $('#cityWsSearch_'+tabId).val(1);
      $('#selectedBox').fadeIn();
      $('#indicator').hide();
      $('#hotelResponse_'+tabId).html(data);
      $('#sortByName').show();
      })
		});
	};
	
	 function hotelSortBy(sortBy, sortValue) {
//	 tabs(tabId);
//	 	$('#indicator').show();
//	 	$('#changeHotel_'+tabId).hide();
	 	var tabId = $('#cityTabId').val();
	 	var cityName = $('#cityTabName').val();
	 	var packageId = $('#packageTourId').val();
	 	var hotelCategory = $('#packageHotelClass').val();
	 	var isB2C = $('#isB2C').val();
		$.ajax({
			url: "changehotel?packageId="+packageId+"&cityName="+cityName+"&hotelCategory="+hotelCategory+"&isB2C="+isB2C+"&id="+tabId+"&sortBy="+sortBy+"&sortValue="+sortValue,
      dataType: "text/html",
      success: (function(data) {
//      $('#indicator').hide();
      $('#hotelResponse_'+tabId).html(data);
      })
		});
	};
	
	
	function wsBox(id)
	{
   var totalBox = $('#gdsBox').val();
    $('#selectedBox').hide('medium');
    $('#chooseText').hide('medium');
    $('#backToHotelSearch').show();
    for (var g=1; g <= totalBox; g++)
      if (g != id)
      {
        $('#ws_'+g).hide('medium');
        $('#originalHotelBox').hide('medium');
      }
      else
      {
        $('#ws_'+g).show('slow');
        $('#wsBoxBorder').removeClass('greyborder');
        $('#ws_'+g).addClass('yellowbg_light');
        $('#newHotelBox').show('medium');
      }
	}
	

	function tabs (id) 
	{
    var totalLocation = $('#totalLocation').val();
    var checkWsSearch = $('#cityWsSearch_'+id).val();
	  var checkWsHotelFlag = $('#cityWsSelected_'+id).val();
    for ( var t = 1; t <= totalLocation; t++ )
  	  if (t != id)
  	  {
  	  	$('#cityName_'+t).removeClass('b');
		    $('#cityBlock_'+t).hide('slow');
		    $('#hotelResponse_'+t).hide();
			}
			else
			{
			  if (checkWsHotelFlag == 0) 
			  {
			    $('#selectedBox').show();
			    $('#ws_'+t).hide();
   		    $('#cityBlock_'+t).show('slow');
   		    $('#hotelResponse_'+t).show();
			  }
			  else
			  {
			    $('#ws_'+t).show();
   		    $('#cityBlock_'+t).hide('slow');
   		    $('#hotelResponse_'+t).show();
			  }
     		$('#cityName_'+t).addClass('b');
     }
  }
		


  function hotelReprice(tabId, countId, roomCountId)
  {
    $('#cityWsSelected_'+tabId).val(1);
    var packageCost = $('#packageTotalAmount').val();
    var onlyPackageCost = $('#onlyPackageAmount').val();
//    alert(packageCost);
    var packageCurrency = $('#packageCurrency').val();
    var hotelCityName = $('#packageHotelCity_'+tabId).val();
    var originalHotelRoomCost = $('#packageOriginalRoomCost_'+tabId).val();
    var newClientId = $('#gdsHotelClientId_'+countId).val();
    var newTrackId = $('#gdsHotelTrackId_'+countId).val();
    var newHotelId = $('#gdsHotelId_'+countId).val();
    var newHotelSearchId = $('#gdsHotelSearchId_'+countId).val();
    var newHotelName = $('#gdsHotelName_'+countId).val();  
    var newHotelRoomCost = $('#gdsRoomCost_'+tabId+'_'+countId+'_'+roomCountId).val();
    var newHotelRoomType = $('#gdsRoomType_'+tabId+'_'+countId+'_'+roomCountId).val();
    var newCombinationId = $('#gdsCombinationId_'+tabId+'_'+countId+'_'+roomCountId).val();
    var newHotelType = $('#gdsHotelType_'+tabId+'_'+countId+'_'+roomCountId).val();
    $('#packageHotelType_'+tabId).val(newHotelType);
    $('#packageNewHotelRoomType_'+tabId).val(newHotelRoomType);
    $('#packageNewRoomCost_'+tabId).val(newHotelRoomCost);
    $('#packageNewHotelClientId_'+tabId).val(newClientId);
    $('#packageNewHotelTrackId_'+tabId).val(newTrackId);
    $('#packageNewHotelId_'+tabId).val(newHotelId);
    $('#packageNewHotelSearchId_'+tabId).val(newHotelSearchId);
    $('#packageNewHotelName_'+tabId).val(newHotelName);
    $('#packageNewCombinationId_'+tabId).val(newCombinationId);
    var removeOldHotelCost = (parseInt(packageCost) - parseInt(originalHotelRoomCost));
    var newPackageCost = (parseInt(removeOldHotelCost) + parseInt(newHotelRoomCost));
    var removeOldHotelCostPackageOnly = (parseInt(onlyPackageCost) - parseInt(originalHotelRoomCost));
    var newOnlyPackageCost = (parseInt(removeOldHotelCostPackageOnly) + parseInt(newHotelRoomCost));
//    alert(newPackageCost)
    $('#newCity_'+tabId).html(''+hotelCityName+' - '+newHotelName);
    $('#packageAmount').html(''+packageCurrency+' '+newOnlyPackageCost);
    $('#newTotalPackageAmount').html(''+packageCurrency+' '+newPackageCost);
    $('#totalAmount').html('Total '+packageCurrency+' '+newPackageCost);
    $('#onlyPackageAmount').val(newOnlyPackageCost);
    $('#packageTotalAmount').val(newPackageCost);
    var roomTypeNo = $('#gdsRoomTypeNo_'+tabId+'_'+countId+'_'+roomCountId).val();
    var totalRoomTypeNo = $('#gdsTotalRoomType_'+tabId+'_'+countId).val();
    $('#totalPriceToggle_'+tabId+'_'+countId).hide();
    $('#totalRoomToggle_'+tabId+'_'+countId).hide();
    $('#tableRoomToggle_'+tabId+'_'+countId).removeClass('greyborderbottomdotted');
    for ( var i = 1; i <= totalRoomTypeNo; i++ )
  	  if (i == roomTypeNo)
  	  {
		    $('#roomTypeToggle_'+tabId+'_'+countId+'_'+i).show('slow');
			  $('#greyBorderBottomToggle_'+tabId+'_'+countId+'_'+i).show();
			}
			else
			{
			  $('#roomTypeToggle_'+tabId+'_'+countId+'_'+i).hide('slow');
			  $('#greyBorderBottomToggle_'+tabId+'_'+countId+'_'+i).hide();
      }

// Change the additional night price if hotel is different from the package hotel
    var checkAddNight = $('#pageAddNight').val();
    if (checkAddNight == 1)
    {
    var newHotelRoomPerNightCost = $('#gdsRoomPerNightAmount_'+tabId+'_'+countId+'_'+roomCountId).val();
    
    var cityAdditionalNight = $('#packageCityAddNight_'+tabId).val();
    var updateAdditionalNightAmount = (parseInt(newHotelRoomPerNightCost) * cityAdditionalNight);
    $('#addNightAmount_'+tabId).html(''+packageCurrency+' '+updateAdditionalNightAmount);
    $('#packageHotelAddNightCityAmount_'+tabId).val(updateAdditionalNightAmount);
    }
    

  }
		
		
		function backToHotelSearch () 
		{
	    var totalBox = $('#gdsBox').val();
		  $('#selectedBox').show('slow');
		  $('#chooseText').show('slow');
		  $('#backToHotelSearch').hide();
		  $('#newHotelBox').hide();
	    for ( var t = 1; t <= totalBox; t++ )
			$('#ws_'+t).show('slow');
			$('#originalHotelBox').show();
			$('#wsBoxBorder').addClass('greyborder');
		}

	function resetService ()
	{
		var resetPackageAmount = $('#resetPackageAmount').val();
    $('#onlyPackageAmount').val(resetPackageAmount);
    $('#packageTotalAmount').val(resetPackageAmount);
	 document.holidayForm.method="POST";
	 document.holidayForm.action="hotel";
	 document.holidayForm.submit();

	}

 function form_submit(form_action) 
	{
	
  document.holidayForm.method="POST";
    if (form_action == 'search') 
		{
     document.holidayForm.action="search";
		}
		else if  (form_action == 'flight') 
		{
     document.holidayForm.action="flight";
		}
			document.holidayForm.submit();
    } 



