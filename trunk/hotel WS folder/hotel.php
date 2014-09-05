<?php
/*
ini_set('display_errors',1);
error_reporting(E_ERROR | E_WARNING | E_NOTICE | E_PARSE);
error_reporting(E_ERROR | E_PARSE);
*/
header("content-type: application/xml");



$tourId = $_POST['tourId'];
$city = $_POST['city'];
//$city = strtolower($city);
if ($tourId == '6117'){
	if ($city == 'macau'){
		$file = fopen("macau.xml","r");
	}
	else{
		$file = fopen("hongkong.xml","r");
	}
}
else{

	$file = fopen("delete.xml","r");
}


	while(! feof($file))
	  {
	  echo fgets($file);
	  }

?>