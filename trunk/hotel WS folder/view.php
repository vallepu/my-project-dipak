<?php
function workArray($arr){
$arry = $arr;
//print_r($arr);
$arr = $arr[0];
global $dataId;
$PACKAGEID =  	$arr['PACKAGEID']; 
$COMBINATIONID =  	$arr['COMBINATIONID']; 
$HOTELID =  	$arr['HOTELID']; 
$FLEXIHOTELNAME =  	$arr['FLEXIHOTELNAME']; 
$cityid =  	$arr['cityid']; 
$CITYNAME =  	$arr['CITYNAME']; 
$Nights =  	$arr['Nights']; 
$HCATEGORYNAME =  	$arr['HCATEGORYNAME']; 
$ROOMNAME =  	$arr['ROOMNAME']; 
$SCHEMEID =  	$arr['SCHEMEID']; 
$PSCHEMENAME =  	$arr['PSCHEMENAME']; 
$DURATIONID =  	$arr['DURATIONID']; 
$ROOMCODE =  	$arr['ROOMCODE']; 
$PAXCODE =  	$arr['PAXCODE']; 
$PAXID =  	$arr['PAXID']; 
$COSTTYPE =  	$arr['COSTTYPE']; 
$COSTDESCRIPTION =  	$arr['COSTDESCRIPTION']; 
$PAXDESCRIPTION =  	$arr['PAXDESCRIPTION']; 
$CURRENCY =  	$arr['CURRENCY']; 
$AMOUNT =  	$arr['AMOUNT']; 
$surchg =  	$arr['surchg']; 
$IsHotelDefault =  	$arr['IsHotelDefault']; 
$IsRoomDefault =  	$arr['IsRoomDefault']; 
$COUNTRY_NAME =  	$arr['COUNTRY_NAME']; 
$id =  	$arr['id']; 

$opt = <<<OPT
<div id="ws_26">
<!-- Packadges Details Starts -->
 <div class="clearboth" id="hotelCityNameBlock_26" style="visibility: visible;">
<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td>
<div class="clearboth padt4 text14bold colordarkblue2 ucase">$CITYNAME</div>

<div class="clearboth padt6 greyborderbottomdotted" id="tableRoomToggle_1_26">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
 <tbody><tr>
  <td valign="top" width="18%">
    <div class="floatl padr8 greyborderrightdotted"><img src="hotel/hotel/hotel_files/036542_hb_a_003.jpg" alt="$FLEXIHOTELNAME" border="0" height="99" width="125">
    </div>
  </td>
  <td valign="top" width="48%">
    <div class="padl8">
      <div class="floatl text12bold ucase">$FLEXIHOTELNAME</div>
      <div class="floatr padt2" align="center"><img src="hotel/hotel_files/4star.gif" border="0" height="9" width="56"></div>
      <div class="clearl padt2 h60 text11 justify">The
modern hotel comprises 6 floors with a total of 45 double rooms and 24
single rooms. Amongst the facilities count a foyer with a 24-hour
reception desk, lifts, a currency exchange facility, a safe, a
cloakroom and a café. There is also a bar, a restaurant, a public
Internet terminal (for an additional fee) and a conference room
available for use. Laundry and room services round off the
offerings.&nbsp;<a href="#" class="linkbluetext">more</a></div>
      <div class="clearboth h25">
        <div class="floatl"><img src="hotel/hotel_files/incl_icon_1.gif" border="0" height="22" width="22"></div>
        <div class="floatl padl3"><img src="hotel/hotel_files/incl_icon_2.gif" border="0" height="22" width="21"></div>
        <div class="floatl padl3"><img src="hotel/hotel_files/incl_icon_3.gif" border="0" height="22" width="22"></div>
        <div class="floatl padl3"><img src="hotel/hotel_files/incl_icon_4.gif" border="0" height="22" width="22"></div>
        <div class="floatl padl3"><img src="hotel/hotel_files/incl_icon_5.gif" border="0" height="22" width="22"></div>
        <div class="floatl padl3"><img src="hotel/hotel_files/incl_icon_6.gif" border="0" height="22" width="21"></div>
        <div class="floatl padl3"><img src="hotel/hotel_files/incl_icon_7.gif" border="0" height="22" width="22"></div>
        <div class="floatl padl3"><img src="hotel/hotel_files/incl_icon_8.gif" border="0" height="22" width="22"></div>
      </div>
      <div class="clearboth"><img src="hotel/hotel_files/spacer.gif" border="0" height="8" width="1"></div>
    </div>
  </td>
  <td valign="top" width="1%"><img src="hotel/hotel_files/spacer.gif" border="0" height="1" width="1"></td>
  <td class="greyborderleftdotted" align="right" valign="top" width="32%">
    <div class="padl8 padr8" align="center">
	    <div class="clearboth padt6 text11"><strong>Total</strong> (1 room, 3  nights)</div>
    </div>
    </td>
  </tr>
</tbody></table>
</div>

<div>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tbody><tr>
    <td height="25" width="18%"><img src="hotel/hotel_files/spacer.gif" border="0" height="3" width="1"></td>
    <td valign="top" width="1%"><img src="hotel/hotel_files/spacer.gif" border="0" height="1" width="1"></td>
    <td width="48%">
	    <div class="padl8">
  		  <div class="clearboth text11bold">
		      <div class="floatl w35per">Room Type</div>
		      <div class="floatl">Price Difference per room per night</div>
		    </div>
	    </div>    
    </td>
    <td class="greyborderleftdotted" align="right" width="32%">
      <div id="totalPriceToggle_1_26" align="center">
        <div class="clearboth text11bold padb2">Total Price Difference per room per night</div>
      </div>    
    </td>
  </tr>
OPT;


	$q = getUniques('COMBINATIONID',$arry);

//$w =  ftch($q,$arry);

	foreach ($q as $nu){

//$dataId = "asdasd";

	$r = returnFiltered("COMBINATIONID",$nu,$arry);
	$w =  ftch($r,$arry);
/*
	echo "<pre>";
	print_r($w);
	echo "<hr>";
*/

$x = $w[0];

//$COMBINATIONID =  	$arr['COMBINATIONID']; 

//$a = returnFiltered('CITYNAME',$city,$Arr);
//$b =  ftch($a,$Arr);




//sum_subarrays_by_key( $tab, $key )



$ROOMCODE =  $x['ROOMCODE']; 
$AMOUNT =  	sum_subarrays_by_key($w,'AMOUNT'); //$x['AMOUNT']; 
$Nights =  	$x['Nights']; 
$surchg =  	$x['surchg']; 
$ROOMNAME =  	$x['ROOMNAME']; 
$CURRENCY =  	$x['CURRENCY']; 
$ttl = ($AMOUNT * $Nights);
$hotelPrice = "";

$hotelPrice = <<<HPRC
 <tr id="roomTypeToggle_1_26_1">
    <td height="25" width="18%">
    <img src="hotel/hotel_files/spacer.gif" border="0" height="8" width="1"></td>
        <td valign="top" width="1%"><img src="hotel/hotel_files/spacer.gif" border="0" height="1" width="1"></td>
    <td width="48%">
	<div class="padl8">
  		<div class="clearboth h5 text11bold">
		<div class="floatl w35per"><img src="hotel/hotel_files/spacer.gif" border="0" height="1" width="1"></div>
		</div>
		<div class="clearboth h20 text11 padt4">
		  <div class="floatl w35per">$ROOMNAME</div>
		<div class="floatl">$CURRENCY		$AMOUNT 
		<span class="colorgreylight"></span></div>
		</div>
	</div>    
    </td>
    <td class="greyborderleftdotted" id="td2TotalPriceToggle_1_26" align="right" width="32%">
<div align="center">
  <div class="clearboth padt4 text11">
    <table border="0" cellpadding="0" cellspacing="0" width="70%">
      <tbody><tr>
        <td align="right" width="50%">$CURRENCY	$ttl &nbsp;&nbsp;</td>
        <td align="left"><div class="clearboth colorred b">
          <input name="gds" onclick="selectedHotel('$COMBINATIONID','$HOTELID')" value="Standard" type="radio">
          Select</div></td>
      </tr>


    </tbody></table>
  </div>
</div>    
    </td>
  </tr>


HPRC;
	$dataId = $dataId + 1;
	$opt = $opt . $hotelPrice;
}


$opt = $opt . <<<OPT
  <tr id="greyBorderBottomToggle_1_26_1"><td colspan="4"><div class="greyborderbottomdotted"></div></td></tr>
</tbody></table>
</div>
OPT;




echo $opt;
}
?>

<?php

$table = '<table border="1" style="display:none" id="hdatagridMatrix"><tbody>';

$kk	= 0;
$str1	= '';
$str2	= '';
foreach ($Arr as $index => $data )
{
	foreach ($data as $key => $value)
	{
		if($kk ==0)
		{
			$str1 .= '<th>'.$key.'</th>';
		}
		
		$str2 .= '<td>'.($value != '' ? $value : '&nbsp;').'</td>';
	}
	$kk++;
	
	if($str1 != '')
	{
		$table	.= '<tr>'.$str1.'</tr>';
		$str1	= '';
	}
	
	if($str2 != '')
	{
		$table	.= '<tr>'.$str2.'</tr>';
		$str2	= '';
	}
}

echo $table.'</tbody></table>';
//print_r($Arr);


function returnFiltered($xkey,$xvalue,$arr){
	$ar = array();
	$i = 0;
		foreach ($arr as $key => $value)
			{
				if (strtolower($value[$xkey]) == strtolower($xvalue)){
						$ar[] = $i;
				}
			$i++;
			}
	return $ar;
}


function ftch($idx,$arr){
	$retArray = array();
		foreach ($idx as $key => $value){
			$retArray[] = $arr[$value];
		}
	return $retArray;
}

function getUniques($xkey,$arr){
	$ar = array();
	$i = 0;
		foreach ($arr as $key => $value)
			{
				$val = $value[$xkey];
				if (!(in_array($val,$ar))){
						$ar[] = $val;
				}
			$i++;
			}
	return $ar;
}


function sum_subarrays_by_key( $tab, $key ) {
        
        $sum = 0;
        
        foreach($tab as $sub_array) {
            $sum += $sub_array[$key];
        }
        
        return $sum;
        
    }
?>
<?php
$dataId = 0;
$a = returnFiltered('CITYNAME',$city,$Arr);
$b =  ftch($a,$Arr);
$c = getUniques('FLEXIHOTELNAME',$b);

foreach ($c as $x){
	$d = returnFiltered('FLEXIHOTELNAME',$x,$Arr);

	$e = ftch($d,$Arr);

		workArray($e);
		
		//Print Hotel Details
		$hotelPackageId = $e[0]['PACKAGEID'];
		$hotelId = $e[0]['HOTELID'];

		$hcategory = $e[0]['HCATEGORYNAME'];

	
}
?>