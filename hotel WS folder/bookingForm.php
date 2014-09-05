<?php
//print_r($_POST)
//header("content-type: application/xml");

?>
<?php


$searchId = $_POST['searchId'];
$HotelTrackId    = $_POST['HotelTrackId'];
$RoomCombId    = $_POST['RoomCombId'];
$NoRooms    = $_POST['NoRooms'];
$numadults    = $_POST['numadults'];
$numchildren    = $_POST['numchildren'];


$xml = <<<XML
<BookingRequest>
<clientinfo>
                <companycode>CNK RevXML</companycode>
                <username>bhavesh.gandhi@coxandkings.com</username>
                <password>test</password>
</clientinfo>

        <BookingDetails>
                <ProvisionalId></ProvisionalId>
                <searchId>$searchId</searchId>
                <HotelInformation>
                        <HotelTrackId>$HotelTrackId</HotelTrackId>

XML;

?>
<?php
$more = "";
for ($i=1;$i<=$NoRooms;$i++){
//		echo $i;

$more = $more.<<<MORE
                       <RoomDetails>
                                <RoomCombId>$RoomCombId</RoomCombId>
                                <RoomNo>$i</RoomNo>
                                <RoomCode>
MORE;
$more = $more.$_POST["RoomCode$i"];
$more = $more.<<<MORE
</RoomCode>
	
                        </RoomDetails>
MORE;
	}
	$xml = $xml.$more.<<<XML
                </HotelInformation>
                <PaxInfo>
                        <numadults>$numadults</numadults>
                        <numchildren>$numchildren</numchildren>
                </PaxInfo>

               
XML;
//echo $xml."<hr>";
?>
<?php
$more = " <AdultDetails>";
for ($i=1;$i<=$numadults;$i++){
   $adulttitle = $_POST["adulttitle$i"];
   $adultfirst = $_POST["adultfirst$i"];
   $adultlast =  $_POST["adultlast$i"];
   $adultgender =  $_POST["adultgender$i"];
   $adultdiet =  $_POST["adultdiet$i"];
   $adultDOB =  $_POST["adultDOB$i"];
   $adultpassportno =  $_POST["adultpassportno$i"];
   $adultissusedate =  $_POST["adultissusedate$i"];
   $adultexpirydate =  $_POST["adultexpirydate$i"];
   $adultplaceofissue =  $_POST["adultplaceofissue$i"];

$more = $more.<<<MORE
                     <AdultInfo>
                                <AdultNo>$i</AdultNo>
                                <Title>$adulttitle</Title>
                                <FirstName>$adultfirst</FirstName>
                                <LastName>$adultlast</LastName>
                                <Gender>$adultgender</Gender>
                                <Diet>$adultdiet</Diet>
                                <BirthDate>$adultDOB</BirthDate>
                                <passportno>$adultpassportno</passportno>
                                <issusedate>$adultissusedate</issusedate>
                                <expirydate>$adultexpirydate</expirydate>
                                <placeofissue>$adultplaceofissue</placeofissue>
                        </AdultInfo>
MORE;
}
$more = $more." </AdultDetails>";

$xml = $xml.$more;


if ($numchildren > 0){
$more = "<ChildDetails>";
for ($i=1;$i<=$numchildren;$i++){
   $childtitle = $_POST["childtitle$i"];
   $childfirst = $_POST["childfirst$i"];
   $childlast =  $_POST["childlast$i"];
   $childgender =  $_POST["childgender$i"];
   $childdiet =  $_POST["childdiet$i"];
   $childDOB =  $_POST["childDOB$i"];
   $childpassportno =  $_POST["childpassportno$i"];
   $childissusedate =  $_POST["childissusedate$i"];
   $childexpirydate =  $_POST["childexpirydate$i"];
   $childplaceofissue =  $_POST["childplaceofissue$i"];

$more = $more.<<<MORE
                     <ChildInfo>
                                <ChildNo>$i</ChildNo>
                                <Title>$childtitle</Title>
                                <FirstName>$childfirst</FirstName>
                                <LastName>$childlast</LastName>
                                <Gender>$childgender</Gender>
                                <Diet>$childdiet</Diet>
                                <BirthDate>$childDOB</BirthDate>
                                <passportno>$childpassportno</passportno>
                                <issusedate>$childissusedate</issusedate>
                                <expirydate>$childexpirydate</expirydate>
                                <placeofissue>$childplaceofissue</placeofissue>
                        </ChildInfo>
MORE;
}
$more = $more." </ChildDetails>";
$xml = $xml.$more;

}



$closing = <<<CLOSE
        </BookingDetails>
<Billingdetails>
<title>Mr.</title>
<firstname>Test</firstname>
<lastname>Test</lastname>
<billingaddress>home</billingaddress>
<addressone>a-6/9,mahesh nagar,s.v.road,go </addressone>
<addresstwo> a-6/9,mahesh nagar,s.v.road,go </addresstwo>
<city>Vashi</city>
<pincode>400012</pincode>
<state>maharastra</state>
<country>india</country>
<emailaddress>ezeego@ezeego1.com</emailaddress>
<telephoneno>0221234567</telephoneno>
<mobileno>9812345678</mobileno>
</Billingdetails>
<Deliverydetails>
<title>Mr</title>
<firstname>Test</firstname>
<lastname>Test</lastname>
<deliveryaddress>Home</deliveryaddress>
<addressone>a-6/9,mahesh nagar,s.v.road,go</addressone>
<addresstwo>a-6/9,mahesh nagar,s.v.road,go</addresstwo>
<city>ghatkopar</city>
<pincode>400078</pincode>
<state>maharastra</state>
<country>india</country>
<emailaddress>ezeego@ezeego1.com</emailaddress>
<telephoneno>0221234567</telephoneno>
<mobileno>981234567</mobileno>
</Deliverydetails>
</BookingRequest>
CLOSE;
$xml = $xml.$closing;

//echo $xml;



$client = new SoapClient('http://staging.coxandkings.com/axis/services/wssearchhoteldetails?wsdl');
$searchResponse = $client->doHotelBookingAndGenerateBookingRef($xml);

/*
 echo "<pre>";
 print_r($client->__getFunctions());
 echo "</pre>";
*/

if (is_soap_fault($searchResponse)) {
    trigger_error("SOAP Fault: (faultcode: {$searchResponse->faultcode}, faultstring: {$searchResponse->faultstring})", E_USER_ERROR);

}

else {

echo $searchResponse;
$search = new SimpleXMLElement($searchResponse);

/*
 echo "<pre>";
 print_r($search);
 echo "</pre>";
*/ 
}



?>
