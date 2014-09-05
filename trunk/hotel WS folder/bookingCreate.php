<?php



$searchId    = $_POST['searchId'];
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
	for (var i=1;i<=$NoRooms;i++){
		echo i;
	}
?>
			<!-- from the loop -->
                        <RoomDetails>
                                <RoomCombId>STGCKVRCMB004693</RoomCombId>
                                <RoomNo>1</RoomNo>
                                <RoomCode>TWN_2</RoomCode>
                        </RoomDetails>
			<!-- loop ends -->
                </HotelInformation>
                <PaxInfo>
                        <numadults>2</numadults>
                        <numchildren>0</numchildren>
                </PaxInfo>

                <AdultDetails>
			<!-- from the loop -->
                        <AdultInfo>
                                <AdultNo>1</AdultNo>
                                <Title>Mr</Title>
                                <FirstName>Test</FirstName>
                                <LastName>Test</LastName>
                                <Gender>M</Gender>
                                <Diet>Veg</Diet>
                                <BirthDate>05/08/1982</BirthDate>
                                <passportno>55555555</passportno>
                                <issusedate>11/01/2000</issusedate>
                                <expirydate>01/01/2015</expirydate>
                                <placeofissue>maharastra.</placeofissue>
                        </AdultInfo>
                        <AdultInfo>
                                <AdultNo>2</AdultNo>
                                <Title>Mr</Title>
                                <FirstName>Test1</FirstName>
                                <LastName>Test</LastName>
                                <Gender>M</Gender>
                                <Diet>Veg</Diet>
                                <BirthDate>21/05/1982</BirthDate>
                                <passportno>55555555</passportno>
                                <issusedate>02/05/1996</issusedate>
                                <expirydate>07/07/2006</expirydate>
                                <placeofissue>maharastra</placeofissue>
                        </AdultInfo>
			<!-- loop Ends -->
                </AdultDetails>
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
