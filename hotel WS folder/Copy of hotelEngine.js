var imgpath = "http://anisar.cnkdev.com/enquiry/resources/defaults/images/";

function createHotel(img){
var hfrm = createForm("hotelSearch")
//	hfrm.Calender = Calender()


hfrm.roomProps = function(roomNo){
		var rt = createTable()
		rt.roomNo = roomNo
		//rt.border = 1
		rt.width = "100\%"
		rt.cellSpacing = 0
		rt.cellPadding=0
		rt.align = "center"

			var RmTTl = createTable()
			RmTTl.width = "100\%"
			RmTTl.cellSpacing = 0
			RmTTl.newRow()
				var td = RmTTl.newTD()

				td.innerHTML = "Room " + roomNo
				RmTTl.insertLast(td)

				var td = RmTTl.newTD()
				td.width="81\%"
				td.innerHTML = "<hr/>"
				RmTTl.insertLast(td)

			RmTTl.newRow()
				var td = RmTTl.newTD()
				td.colSpan = "6"
				var roomType = hfrm.createSelect("roomtype" + roomNo)
				rt.roomType = roomType
				roomType.createOption("- Room Type -","")

					var roomstypes = "SGL_1;Single (1 Guest);1-0, DBL_1;Double for sole use (1 guest);1-0, DBL_2;Double (2 Guests);2-0, TWN_1;Twin for sole use (1 guest);1-0, TWN_2;Twin (2 Guests);2-0, TRP_3;Triple (3 Guests);3-0, QUD_4;Quad (4 Guests);4-0, DBL_1C;Double with 1 child (2 Guests + 1 Child);2-1, DBL_2C;Double with 2 children (2 Guests + 2 Child);2-2, TRP_1C;Triple with 1 child (3 Guests + 1 Child);3-1"
					roomtypes = roomstypes.split(", ")
					for (var i=0;i<roomtypes.length;i++)
					{
						var opt = roomtypes[i].split(";")
						roomType.createOption(opt[1],opt[0])
						roomType.options[roomType.options.length - 1].setAttribute("tag",opt[2])
					}


//				roomType.createOption("Double with 1 child (2 Guests + 1 Child)","DBL_2")

				
				
				td.appendChild(roomType)
				RmTTl.insertLast(td)



		rt.newRow()
		var td = rt.newTD()
		td.appendChild(RmTTl)
		rt.insertLast(td)



			var tab1 = createTable()
			//Adults and Children Table
			tab1.width = "90\%"
			rt.people = tab1
			tab1.cellSpacing="2"
			tab1.newRow()


			tab1.newRow()
				var td = tab1.newTD()
				td.innerHTML = "Adult"
				td.width = "30\%"
				tab1.insertLast(td)


				var td = tab1.newTD()
				td.width = "20\%"
				var noOfAdults = hfrm.createSelect("adult" + roomNo)
				rt.noOfAdults = noOfAdults 
				noOfAdults.fillNumbers(1,4)
				td.appendChild(noOfAdults)
				tab1.insertLast(td)

				var td = tab1.newTD()
				td.innerHTML = "Children"
				td.width = "30\%"
				tab1.insertLast(td)


				var td = tab1.newTD()
				td.width = "20\%"
				var noOfChildren = hfrm.createSelect("child" + roomNo)
				rt.noOfChildren = noOfChildren
				noOfChildren.fillNumbers(0,5)
				td.appendChild(noOfChildren)
				tab1.insertLast(td)
			tab1.newRow()
				var td = tab1.newTD()
				td.innerHTML = "Child 1 Age"
				td.style.display = "none"
				tab1.insertLast(td)

				var td = tab1.newTD()
				var childAge = hfrm.createSelect("child" + roomNo + "1")
				rt["child" + roomNo + "1"] =  childAge
				childAge.validate = 0
				td.style.display = "none"
				childAge.createOption("0","")
				childAge.fillNumbers(1,11)
				childAge.validate = 0
				td.appendChild(childAge)
				tab1.insertLast(td)


				var td = tab1.newTD()
				td.style.display = "none"
				td.innerHTML = "Child 2 Age"
				tab1.insertLast(td)

				var td = tab1.newTD()
				td.style.display = "none"
				var childAge = hfrm.createSelect("child" + roomNo + "2")
				childAge.createOption("0","")
				rt["child" + roomNo + "2"] =  childAge
				childAge.fillNumbers(1,11)
				td.appendChild(childAge)
				tab1.insertLast(td)




		rt.newRow()
		var td = rt.newTD()
		td.appendChild(tab1)
		rt.insertLast(td)



			var tab1 = createTable()
			//Extra Bed
			tab1.cellSpacing="1"
			tab1.cellPadding="0"
			tab1.newRow()


			tab1.newRow()
				var td = tab1.newTD()
				td.appendChild(hfrm.createInput("extrabed" + roomNo,"Y","checkbox"))
				tab1.insertLast(td)

				var td = tab1.newTD()
				var a = document.createElement("A")
				a.innerHTML = "Extra Bed"
				td.appendChild(a)
				tab1.insertLast(td)



		rt.newRow()
		var td = rt.newTD()
		td.appendChild(tab1)
		rt.insertLast(td)


rt.reset = function(){
rt.noOfChildren.selectedIndex = 0
rt.noOfChildren.onchange()
}

//UI Functions
rt.noOfChildren.onchange = function(){
var v = this.value
	for (var i=0;i<=this.options.length;i++)
	{
			var obj = rt["child" + rt.roomNo + i]

				if (obj)
				{
						if (i<=v)
						{
							obj.validate = 1
						}
						else{
						obj.validate = 0
						}
					}
				}

		var clls = rt.people.rows[2].cells

		for (var j=0;j<clls.length;j++)
		{
		clls[j].style.display = "none"
			if (j <= (v * 2 - 1))
			{
				if (document.all)
				{
					clls[j].style.display = "block"
				}
				else{
					clls[j].style.display = "table-cell"
				}
			}
		}


}


rt.roomType.onchange = function(){

var vals = this.options[this.selectedIndex].getAttribute("tag")
if (vals != undefined)
{
var vx = vals.split("-")
rt.noOfAdults.selectValue(vx[0])
rt.noOfChildren.selectValue(vx[1])
rt.noOfChildren.onchange()
}

}


rt.style.display = "none"
return rt;

}


		var t = createTable()
		t.width="100\%"
		t.className = "eng"
		t.align = "center"
			var tab1 = createTable()
			tab1.cellSpacing="0"
			tab1.newRow()
				var td = tab1.newTD()
				hfrm.domint = hfrm.createInput("domint","domestic","radio")
				td.appendChild(hfrm.domint)
				hfrm.domint.checked = true
				tab1.insertLast(td)
				var td = tab1.newTD()
				td.innerHTML = "Domestic"
				tab1.insertLast(td)
				var td = tab1.newTD()
				var rad = hfrm.createInput("domint","international","radio")
				td.appendChild(rad)
				tab1.insertLast(td)
				var td = tab1.newTD()
				td.innerHTML = "International"
				tab1.insertLast(td)

		t.newRow()
		var td = t.newTD()
		td.appendChild(tab1)
		t.insertLast(td)


			var tab1 = createTable()
			tab1.cellSpacing="0"
			tab1.newRow()
				var td = tab1.newTD()
				/*
				var country = hfrm.createSelect("country")
				country.validate = 3
				country.createOption("- Select Country -","")
				country.createOption("India","India - IN")
				country.createOption("Nepal","Nepal - NPL")
				*/
				var country = hfrm.createInput("country")
				td.appendChild(country)
				tab1.insertLast(td)

				var td = tab1.newTD()
				/*
				var city = hfrm.createSelect("city")
				city.validate = 3
				city.createOption(" - Select city - ","")
				city.createOption("Agra","Agra - ***")
				city.createOption("Delhi","Delhi - ***")
				city.createOption("Mumbai","Mumbai - ***")
				*/
				var city = hfrm.createInput("city")
				td.appendChild(city)
				tab1.insertLast(td)


		t.newRow()
		var td = t.newTD()
		td.appendChild(tab1)
		t.insertLast(td)


		t.newRow()
		var td = t.newTD()
		td.innerHTML = "<hr/>"
		t.insertLast(td)


			var tab1 = createTable()
			tab1.newRow()
			//tab1.border=1
			tab1.cellSpacing = 0
				var td = tab1.newTD()
				td.innerHTML = "check in date"
				tab1.insertLast(td)
				var td = tab1.newTD()
				td.innerHTML = "check out date"
				tab1.insertLast(td)

				var td = tab1.newTD()
				td.innerHTML = "&nbsp;"
				tab1.insertLast(td)

				var td = tab1.newTD()
				td.verticalAlign = "top"
				td.rowSpan = 2
				var dayCount = hfrm.createInput("noofnights")
				dayCount.className="drop"
				dayCount.value = "0"
				dayCount.size = 1
				hfrm.dayCount = dayCount
				td.appendChild(dayCount)
				tab1.insertLast(td)
				
				var td = tab1.newTD()
				td.verticalAlign = "top"
				var dayCountd = document.createElement("span")
				dayCountd.innerHTML = "Days"
				td.appendChild(dayCountd)
				
				
				tab1.insertLast(td)



				tab1.newRow()
				var td = tab1.newTD()
					hfrm.depDate = hfrm.createInput("checkin","","datePicker")
//					hfrm.depDate.value = parseDate(new Date())
					hfrm.depDate.size = 10
					hfrm.depDate.validate = "0"
					td.appendChild(hfrm.depDate)

				//From City Ends
				tab1.insertLast(td)

				var td = tab1.newTD()
					hfrm.leaveDate = hfrm.createInput("checkout","","datePicker")
					hfrm.leaveDate.size = 10
					hfrm.leaveDate.validate = "1"
//					hfrm.leaveDate.value = parseDate(new Date())

					hfrm.leaveDate.validateAgainst = hfrm.depDate
					td.appendChild(hfrm.leaveDate)

				hfrm.datetbl = tab1

				tab1.insertLast(td)
				var td = tab1.newTD()
				//td.innerHTML = "To"
				tab1.insertLast(td)


		t.newRow()
		var td = t.newTD()
		td.appendChild(tab1)	
		t.insertLast(td)

		t.newRow()
		var td = t.newTD()
		td.innerHTML = "<hr/>"
		t.insertLast(td)


			var tab1 = createTable()
			tab1.cellSpacing="0"
			tab1.newRow()
				var td = tab1.newTD()
				td.innerHTML = "Rooms &nbsp;"
				tab1.insertLast(td)
				var td = tab1.newTD()

				var td = tab1.newTD()
				var rooms = hfrm.createSelect("rooms")
				hfrm.rooms = rooms
				rooms.fillNumbers(1,3)
				td.appendChild(rooms)
				tab1.insertLast(td)

		t.newRow()
		var td = t.newTD()
		td.appendChild(tab1)
		t.insertLast(td)





		t.newRow()
		var td = t.newTD()
		td.align = "center"
		hfrm["room1"] = hfrm.roomProps(1)
		td.appendChild(hfrm["room1"])
		hfrm["room1"].style.display = "block"
		t.insertLast(td)

		hfrm["room2"] = hfrm.roomProps(2)
		td.appendChild(hfrm["room2"])
		t.insertLast(td)

		hfrm["room3"] = hfrm.roomProps(3)
		td.appendChild(hfrm["room3"])
		t.insertLast(td)

		t.newRow()
		var td = t.newTD()
		td.innerHTML = "<hr/>"
		t.insertLast(td)


		t.newRow()
		var td = t.newTD()
		td.align = "right"
		td.appendChild(hfrm.createInput("submity","submit","submit"))	
		t.insertLast(td)


//Input functions here


hfrm.showDays = function(){
						var dateObj = exDate()

						var x = dateDiff("d", dateObj.fromInd(hfrm.depDate.value), dateObj.fromInd(hfrm.leaveDate.value))
						hfrm.dayCount.value = x
}
/*
					hfrm.depDate.onchange = function(){
						var dateObj = exDate()
						var addDays = 1
						if (hfrm.dayCount.value > 1)
						{
							addDays = hfrm.dayCount.value
						}
						
						hfrm.leaveDate.value = parseDate(dateAdd("d", addDays, dateObj.fromInd(this.value)))
						hfrm.showDays()

					}


					hfrm.leaveDate.onchange = function(){
						hfrm.showDays()
					}

*/
/*
hfrm.dayCount.onchange = function(){
						var dateObj = exDate()
						hfrm.leaveDate.value = parseDate(dateAdd("d", this.value, dateObj.fromInd(hfrm.depDate.value)))
}

*/
hfrm.rooms.onchange = function(){
for (var i=1;i<this.options.length + 1;i++)
	{
		if (i<=this.value)
		{
			hfrm["room" + i].style.display = "block"
		}
		else{
			hfrm["room" + i].style.display = "none"
			hfrm["room" + i].reset()
		}
	}
}

hfrm.appendChild(t)


var defElems = "requestTest.php"

var ex = defElems.split(";")
for (var i=0;i<ex.length;i++)
{
	var l = ex[i].split("=")
	hfrm.appendChild(hfrm.createInput(l[0],l[1],"hidden"))
}


hfrm.appendChild(hfrm.createInput("tourId",'',"idden"))
hfrm.appendChild(hfrm.createInput("brocId",'',"idden"))




hfrm.setValidate()

/*
hfrm.submit = function(){
if (this.validate())
{
		alert("xxx")
		return false;
}

}

*/
hfrm.method="post"

//return form



var htlForm = document.createElement("DIV")
htlForm.hfrm = hfrm
//htlForm.style.display = "none"
htlForm.appendChild(hfrm)

if (document.getElementById("hotelEngine"))
{
	document.getElementById("hotelEngine").innerHTML = ""
	document.getElementById("hotelEngine").appendChild(htlForm)
}
else{
	document.body.appendChild(htlForm)
}


htlForm.hfrm.onsubmit = function()
{

	htlForm.hfrm.submit()
	return true;
	return false;
}



htlForm.hfrm.submit = function()
{

hfrm.action= "/enquiry/delete.xml" //"requestTest.php"
hfrm.action=htlForm.pxy
if (!(htlForm.pxy == undefined))
{
	hfrm.action=htlForm.pxy
}


return true;

	var validate = this.validate()
	if (validate == false)
	{
		return false;
	}
	var sfe = htlForm.hfrm.elements //searchForm.elements;
	var str = ""

	for (var i=0;i<sfe.length;i++)
	{
		str = str + "&" + sfe[i].name  +"=" + sfe[i].value
	}

htlForm.clhfrm = createForm()


for (var fi=0;fi<htlForm.hfrm.elements.length;fi++)
{
	var elm = htlForm.clhfrm.createInput(htlForm.hfrm.elements[fi].name)
	elm.value = htlForm.hfrm.elements[fi].value
	htlForm.clhfrm.appendChild(elm)
}

/*
var frm = htlForm.hfrm.elements
	var noRooms = frm.rooms.value
	alert("Booking \n Rooms : " + noRooms)
*/

//return true;

	var xmlhttp = getXMLHttpRequest();
	xmlhttp.open("POST",htlForm.hfrm.action,true);
//	alert(htlForm.hfrm.action)
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.setRequestHeader("Content-length", str.length);
	xmlhttp.setRequestHeader("Connection", "close");
	xmlhttp.onreadystatechange = function() {
	document.title = xmlhttp.readyState
		if (xmlhttp.readyState == 4) {
//		alert(xmlhttp.responseText)
//		var doc = newDoc(xmlhttp.responseText)
		//doc = xmlhttp.responseXML

//			alert(xmlhttp.responseText)
//			var x = htlForm.parseResults(doc)
			var x = document.createElement("DIV")
//				x.style.width = "50\%"
			x.innerHTML = xmlhttp.responseText
			if (htlForm.resultContainer)
			{
			htlForm.resultContainer.innerHTML = ""
			htlForm.resultContainer.appendChild(x)
			}

			else {
			//htlForm.dv = Overlay()
//			var x = doc
//alert(xmlhttp.responseText)
			htlForm.dv.aalert(x)
//				htlForm.dv.style.width = "75%"
			}

				var z = x.getElementsByTagName("input")

				for (var i=0;i<z.length;i++ )

					{
						if (z[i].getAttribute("name") == "hotel")
						{
							z[i].selectedHotel = function(a,b){
										var Able = document.getElementById('hdatagridMatrix')
										tableSQL(Able)
//										alert(a + "\n|" + b + "\n|" + "\n" + Able.innerHTML)
										Able.search('COMBINATIONID',a,true)
										var q = Able.resultSet	
										q.filter("HOTELID",b,true)
										q = Able.resultSet	

										if (document.hengine.book == 1)
										{
											document.hengine.doBook(q)
										}


										alert((q.recordCount()))







										alert("--------------------------")
							}
						}
					}


		}
	}
//	alert(str)
 	xmlhttp.send(str);

if (htlForm.resultContainer)
{
htlForm.resultContainer.innerHTML = "<img src="+imgpath+"busy.gif> Working...."
}

else {
//htlForm.dv = overLay()
htlForm.dv.innerHTML = ""
htlForm.dv.alert("<img src="+imgpath+"busy.gif> Working....")
}



//	alert("here")
	return false;
//return true;
 }





htlForm.parseResults = function(xmlDoc){
alert(xmlDoc.xml)
htlForm.searchId = getNodeValue(xmlDoc,"searchId")

var dv = document.createElement("TEXTAREA")
	dv.cols = "100"
	dv.rows = "10"
	dv.value = xmlDoc.xml
//	return dv

var deval = htlForm.deviate
if (deval == undefined)
{
	deval = 0
}
htlForm.deviate = deval

			var eresTbl =  createTable()
				//resTbl.style.background = "ivory"
			eresTbl.width = "100\%"
			eresTbl.cellSpacing = 0

			var detail = xmlDoc.getElementsByTagName("hoteldetail")
				for (var i=0;i<detail.length;i++)
				{
			var resTbl =  createTable()
			if (i>0)
			{
				resTbl.newRow()
				var TD = resTbl.newTD()
					TD.className = ""
					TD.style.textAlign="left"
					TD.innerHTML = "&nbsp;"
				resTbl.insertLast(TD)
			}


				//Mode this to a table

			var htlDesc = createTable()
				htlDesc.newRow()
				var TD = htlDesc.newTD()
					TD.className = "text11bold"
					TD.style.textAlign="left"
					TD.width = "400px"
					TD.innerHTML = getNodeValue(detail[i],"hotelname")
				htlDesc.insertLast(TD)

				var TD = htlDesc.newTD()
					TD.align="right"
			//		TD.className = "text11bold"
					TD.innerHTML = "<img src=http://cnklive.coxandkings.com/themes/images/holidays/fh/views/" + (getNodeValue(detail[i],"starrating") * 1) + "star.gif>"
				htlDesc.insertLast(TD)

				htlDesc.newRow()
				var TD = resTbl.newTD()
					TD.colSpan = 2
					TD.className = "text11"
					TD.style.textAlign="left"
					TD.innerHTML = getNodeValue(detail[i],"hoteldesc")
				htlDesc.insertLast(TD)


				
				
				
				resTbl.newRow()
				var TD = resTbl.newTD()
					TD.className = "greyborderrightdotted"
					TD.style.textAlign="left"
					TD.innerHTML = "<img width=125 height=99 src='" + getNodeValue(detail[i],"image") + "'>"
				resTbl.insertLast(TD)

				var TD = resTbl.newTD()
					TD.colSpan = 2
					TD.className = ""
					TD.width = "400px"
					TD.style.textAlign="left"
					TD.appendChild(htlDesc)
				var hDec = TD
				resTbl.insertLast(TD)


				var TD = resTbl.newTD()
					TD.colSpan = 2
					TD.className = "greyborderleftdotted"
					TD.style.textAlign="left"
			TD.innerHTML = "&nbsp;"
				resTbl.insertLast(TD)


				var dataforPriceTable = detail[i].getElementsByTagName("roomingPriceDetail")[0].getElementsByTagName("roominfo")
				var priceTbl = htlForm.priceTable(dataforPriceTable)
				hDec.appendChild(priceTbl)

//alert(resTbl.innerHTML)


					var diffText = "Total Price"
					if (deval > 0)
					{
						TD.innerHTML = TD.innerHTML + " Difference"
					}



				var TD = resTbl.newTD()
					TD.rowSpan = resTbl.rows.length
					TD.className = "text11"
					TD.innerHTML="<b>" + diffText + "</b><br>" + priceTbl.ttlValue

					var button = htlForm.selectButton()
						button.data = detail[i]
					TD.appendChild(button)
					resTbl.insertLast(TD)

				eresTbl.newRow()
				var x = eresTbl.newTD()
					x.appendChild(resTbl)
				eresTbl.insertLast(x)


			}


		return eresTbl;
}
//---------------------------------------------------------------------------------------------------------


htlForm.priceTable = function(dataforPriceTable){

				var priceTbl =  createTable()
			priceTbl.cellSpacing = 0
			priceTbl.width = "100\%"
			var detailPrice = dataforPriceTable 

				priceTbl.newRow()
				var TD = priceTbl.newTD()
					TD.className = "text11 padt6 vtop ctr b"
					TD.style.textAlign="left"
					TD.innerHTML = "&nbsp;"
				priceTbl.insertLast(TD)

				//priceTbl.newRow()
				var TD = priceTbl.newTD()
					TD.className = "text11 padt6 vtop ctr b"
					TD.style.textAlign="left"
					TD.innerHTML = "Room Type"
				priceTbl.insertLast(TD)


				var TD = priceTbl.newTD()
					TD.className = "text11 padt6 vtop ctr b"
					TD.style.textAlign="left"
					TD.innerHTML = "Price per Night"
				priceTbl.insertLast(TD)


var ttlValue = 0

				for (var j=0;j<detailPrice.length;j++)
				{

				priceTbl.newRow()
//alert("here " + j + " " + i)
				var TD = priceTbl.newTD()
					TD.className = "text11 padt6 vtop ctr b greyborderbottomdotted"
					TD.style.textAlign="left"
					TD.innerHTML = "&nbsp;"
				priceTbl.insertLast(TD)


				var TD = priceTbl.newTD()
					TD.className = "text11 greyborderbottomdotted padt6 vtop ctr"
					TD.style.textAlign="left"
					TD.innerHTML = "RoomType" //getNodeValue(detail[i],"roomtype")
				priceTbl.insertLast(TD)


				var TD = priceTbl.newTD()
					TD.className =	"text11 greyborderbottomdotted padt6 vtop ctr"
					TD.style.textAlign="left"
					var PNvalue = Math.round(getNodeValue(detailPrice[j],"pricepernight"),2)
//						alert(htlForm.deviate)
					PNvalue = PNvalue - (htlForm.deviate / getNodeValue(xmlDoc,"noOfNight"))
					//TD.innerHTML = getNodeValue(detail[i],"currency") + " " + PNvalue + " x " + getNodeValue(xmlDoc,"noOfNight")
					TD.innerHTML = "INR" + " " + PNvalue + " x " + getNodeValue(xmlDoc,"noOfNight")
				priceTbl.insertLast(TD)

ttlValue = ttlValue + (PNvalue * getNodeValue(xmlDoc,"noOfNight"))

/*
				var TD = priceTbl.newTD()
					TD.className =	"text11 greyborderbottomdotted padt6 vtop ctr greyborderleftdotted "
//					TD.style.background = "red"
					TD.style.verticalAlign = "middle"
					TD.style.textAlign="center"
					TD.innerHTML = "<span class=orange_heading>" + getNodeValue(detail[i],"currency") + " " + ((PNvalue * getNodeValue(xmlDoc,"noOfNight")) + "</span><br>")


*/
				priceTbl.insertLast(TD)
				}
				priceTbl.ttlValue = ttlValue
				return priceTbl;
}




htlForm.selectButton = function(){

					
					var slct = document.createElement("A")
					slct.href = "javascript:void(0)"
					//slct.data = detail[i]


if (htlForm.deviate > 0)
{


					slct.onclick = function(){
					//alert(this.data.xml)
						if (typeof(hotelSelected) == "function")
						{
						hotelSelected(this.data,htlForm.searchId)
								if (htlForm.dv)
								{
									htlForm.dv.hide();
								}
						}
						else{
							alert("Create a local function named hotelSelected")
							}
					}

					slct.innerHTML = "Select "
}
else{

					
					var book = document.createElement("A")
					book.href = "javascript:void(0)"
					//book.data = detail[i]

					book.onclick = function(){
							htlForm.book(this.data)
					}


					//TD.appendChild(book)
					book.innerHTML = "Book"
					slct = book

}

return slct;
}



htlForm.doBook = function(doc){
htlForm.hotelInfo = doc
//var doc = this.data

	var hotelTrackId = doc.getFld('HOTELID') //getNodeValue(xmlDoc,"hotelTrackId")
	var searchId = htlForm.searchId.toString()

	var roomCombId = doc.getFld('COMBINATIONID') //getNodeValue(xmlDoc.getElementsByTagName('roomdetail')[0],"roomCombId")
	alert(searchId)




	var frm = htlForm.clhfrm
	var noRooms = frm.rooms.value
	var noAdts = 0
	var noChd = 0
		for (var i=1;i<=noRooms;i++)
		{
				noAdts = noAdts + (frm["adult" + i].value * 1)
				noChd = noChd + (frm["child" + i].value * 1)
		}
//alert("noAdts:" + noAdts + "\nnoChd:" + noChd)

	var paxDetailsForm = createForm()
	htlForm.paxDetailsForm = paxDetailsForm
	var paxDetailsTable = createTable()


//Create Hidden Inputs for Bookings....
var roomDetails = htlForm.getRoomNodesforBooking(doc)

roomDetails.style.display = "none"

var numadults = paxDetailsForm.createInput("numadults")
	numadults.value = noAdts
roomDetails.appendChild(numadults)

var numchildren = paxDetailsForm.createInput("numchildren")
	numchildren.value = noChd
roomDetails.appendChild(numchildren)

var searchid = paxDetailsForm.createInput("searchId")
	searchid.value = searchId
roomDetails.appendChild(searchid)

var HotelTrackId = paxDetailsForm.createInput("HotelTrackId")
	HotelTrackId.value = hotelTrackId
roomDetails.appendChild(HotelTrackId)

var RoomCombId = paxDetailsForm.createInput("RoomCombId")
	RoomCombId.value = roomCombId
roomDetails.appendChild(RoomCombId)


//Ends

paxDetailsForm.appendChild(roomDetails)

			paxDetailsTable.fillIn = function(stitle,total){

			for (var i=1;i<total + 1;i++)
			{
				var TD = paxDetailsTable.newTH()
				TD.innerHTML = stitle + " " + i
				title = stitle.toLowerCase(); 
				paxDetailsTable.newRow()
				paxDetailsTable.insertLast(TD)
					var moreInfo = paxDetailsForm //createForm()
						paxDetailsTable.newRow()
						var TD = paxDetailsTable.newTD()
						TD.innerHTML = "Title"
						paxDetailsTable.insertLast(TD)
						var TD = paxDetailsTable.newTD()
						TD.innerHTML = "First Name"
						paxDetailsTable.insertLast(TD)
						var TD = paxDetailsTable.newTD()
						TD.innerHTML = "Last Name"
						paxDetailsTable.insertLast(TD)
						var TD = paxDetailsTable.newTD()
						TD.innerHTML = "Gender"
						paxDetailsTable.insertLast(TD)

						paxDetailsTable.newRow()
						var TD = paxDetailsTable.newTD()
						var titleSelect = moreInfo.createSelect(title + "title" + i)
							titleSelect.createOption("- Select -","")
							titleSelect.validate = 1
							var titleTypes = "MR;Mr., MS;Miss., MRS;Mrs."
						titleSelect.createOptionArray(titleTypes)
						TD.appendChild(titleSelect)
						paxDetailsTable.insertLast(TD)

						var TD = paxDetailsTable.newTD()
						var inp = moreInfo.createInput(title + "first" + i)
						inp.validate = 3
						TD.appendChild(inp)
						paxDetailsTable.insertLast(TD)

						var TD = paxDetailsTable.newTD()
						var inp = moreInfo.createInput(title + "last" + i)
						inp.validate = 3
						TD.appendChild(inp)
						paxDetailsTable.insertLast(TD)

						var TD = paxDetailsTable.newTD()
						var gender = moreInfo.createSelect(title + "gender" + i)
							gender.validate = 1
							gender.createOption("- Select -","")
							var titleTypes = "M;Male, F;Female"
							gender.createOptionArray(titleTypes)
						TD.appendChild(gender)

						paxDetailsTable.insertLast(TD)
// Row 2


						paxDetailsTable.newRow()
						var TD = paxDetailsTable.newTD()
						TD.innerHTML = "Diets"
						paxDetailsTable.insertLast(TD)
						var TD = paxDetailsTable.newTD()
						TD.innerHTML = "Birth Date [dd/mm/yyyy]"
						TD.style.textAlign = "left"
						TD.colSpan = 2
						paxDetailsTable.insertLast(TD)

						var TD = paxDetailsTable.newTD()
						TD.innerHTML = ""
						paxDetailsTable.insertLast(TD)

						paxDetailsTable.newRow()
						var TD = paxDetailsTable.newTD()
						var diets = moreInfo.createSelect(title + "diet" + i)
							diets.validate = 1
							diets.createOption("- Select -","")
							var titleTypes = "VEG;Vegetarian, NVeg;Non Vegetarian"
						diets.createOptionArray(titleTypes)
						
						TD.appendChild(diets)
						paxDetailsTable.insertLast(TD)

						var TD = paxDetailsTable.newTD()
						TD.solSpan = 2
						var dateSelect = moreInfo.createInput(title + "DOB" + i)
							dateSelect.validate = 8
						TD.appendChild(dateSelect)
						paxDetailsTable.insertLast(TD)


						var TD = paxDetailsTable.newTD()
						TD.innerHTML = ""
						paxDetailsTable.insertLast(TD)


//Row 3

						paxDetailsTable.newRow()
						var TD = paxDetailsTable.newTD()
						TD.innerHTML = "Passport No."
						paxDetailsTable.insertLast(TD)
						var TD = paxDetailsTable.newTD()
						TD.innerHTML = "Issue Date"
						TD.style.textAlign = "left"
						paxDetailsTable.insertLast(TD)
						var TD = paxDetailsTable.newTD()
						TD.innerHTML = "Expiry Date"
						TD.style.textAlign = "left"
						paxDetailsTable.insertLast(TD)
						var TD = paxDetailsTable.newTD()
						TD.innerHTML = "Place of issue"
						TD.style.textAlign = "left"
						paxDetailsTable.insertLast(TD)

						paxDetailsTable.newRow()
						var TD = paxDetailsTable.newTD()
						var passportno = moreInfo.createInput(title + "passportno" + i)
						TD.appendChild(passportno)
						paxDetailsTable.insertLast(TD)

						var TD = paxDetailsTable.newTD()
						var issusedate = moreInfo.createInput(title + "issusedate" + i)
						TD.appendChild(issusedate)
						paxDetailsTable.insertLast(TD)

						var TD = paxDetailsTable.newTD()
						var expirydate = moreInfo.createInput(title + "expirydate" + i)
						TD.appendChild(expirydate)
						paxDetailsTable.insertLast(TD)

						var TD = paxDetailsTable.newTD()
						var placeofissue = moreInfo.createInput(title + "placeofissue" + i)
						TD.appendChild(placeofissue)
						paxDetailsTable.insertLast(TD)


			}

			}

paxDetailsTable.fillIn("Adult",noAdts)
paxDetailsTable.fillIn("Child",noChd)

		paxDetailsTable.newRow()
		var td = paxDetailsTable.newTD()
		td.align = "right"
		var sub = paxDetailsForm.createInput("submit","submit","submit")
		td.appendChild(sub)	

		paxDetailsTable.insertLast(td)

paxDetailsForm.setValidate()

paxDetailsForm.appendChild(paxDetailsTable)





paxDetailsForm.onsubmit = function(){



	var validate = this.validate()
	if (validate == false)
	{
		return false;
	}	

this.method = "POST"
return true;	

	//alert(validate)
	
	var sfe = this.elements //searchForm.elements;
	var str = ""
	for (var i=0;i<sfe.length;i++)
	{
		str = str + "&" + sfe[i].name  +"=" + sfe[i].value
	}


	var xmlhttp = getXMLHttpRequest();
	xmlhttp.open("POST",this.action,true);
//	alert(htlForm.hfrm.action)
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.setRequestHeader("Content-length", str.length);
	xmlhttp.setRequestHeader("Connection", "close");
	xmlhttp.onreadystatechange = function() {


var x = document.createElement("DIV")
x.innerHTML = "Booking Complete"


	//document.title = xmlhttp.readyState
		if (xmlhttp.readyState == 4) {
			var doc = newDoc(xmlhttp.responseText)
			if (htlForm.resultContainer)
			{
			//htlForm.resultContainer.innerHTML = "Booking Complete!"
			htlForm.resultContainer.appendChild(x)
			}

			else {
			//htlForm.dv = Overlay()
//			var x = doc
			//htlForm.dv.alert("Booking Complete!")
			x.width ="700px"
			htlForm.dv.aalert(x)
			}

if (typeof(hotelBookingComplete) == "function")
{
hotelBookingComplete(htlForm.hotelInfo,doc)
}else{
alert("Create a function hotelBookingComplete(hotelDetail,bookingDetail)")

}



		}
	}
//	alert(str)
 	xmlhttp.send(str);

if (htlForm.resultContainer)
{
htlForm.resultContainer.innerHTML = "<img src="+imgpath+"busy.gif> Working...."
}

else {
//htlForm.dv = overLay()
htlForm.dv.innerHTML = ""
htlForm.dv.alert("<img src="+imgpath+"busy.gif> Working....")
}







//	alert("submitted")
	return false;

}



paxDetailsForm.action= "/enquiry/bookingsucess.xml" //"requestTest.php"

if (!(htlForm.paxpxy == undefined))
{
	paxDetailsForm.action=htlForm.paxpxy
}




//Port the booking form
			var x = paxDetailsForm

			if (htlForm.resultContainer)
			{
			htlForm.resultContainer.innerHTML = ""
			htlForm.resultContainer.appendChild(x)
			}

			else {
			//htlForm.dv = Overlay()
//			var x = doc
			htlForm.dv.aalert(x)
			}



//Fill the form

if (document.pax)
{
	var pax = document.pax
	var adtsDone = 1
	var chdsDone = 1






		for (var v=0;v<pax.length;v++)
		{
			var pxtype = pax[v][0].toLowerCase()

			if (pxtype == "adult")
			{
				var counter = adtsDone
			}
			else{
				var counter	= chdsDone
			}



if (paxDetailsForm[pxtype + "title" + counter])
{

			paxDetailsForm[pxtype + "title" + counter].selectValue(pax[v][1])

			paxDetailsForm[pxtype + "first" + counter].value = pax[v][2]
			paxDetailsForm[pxtype + "last" + counter].value = pax[v][3]
			paxDetailsForm[pxtype + "gender" + counter].selectValue(pax[v][4])
			paxDetailsForm[pxtype + "DOB" + counter].value = pax[v][5]
			paxDetailsForm[pxtype + "diet" + counter].selectValue(pax[v][6])
			
}			
			
			if (pxtype == "adult")
			{
				adtsDone++
			}
			else{
				chdsDone++
			}

		}
}



//alert(paxDetailsTable.outerHTML)

}



htlForm.getRoomNodesforBooking = function(doc,roomCombId){
var rooming = doc.getElementsByTagName("roomingPriceDetail")[0].getElementsByTagName("roominfo")
var dv = document.createElement("DIV")
for (var i=0;i<rooming.length;i++)
{

		var elm = htlForm.clhfrm.createInput("RoomCode" + (i + 1))
		elm.value = getNodeValue(rooming[i],"roomcode")
		dv.appendChild(elm)

}


		var elm = htlForm.clhfrm.createInput("NoRooms")
		elm.value = rooming.length
		dv.appendChild(elm)

return dv
//alert(dv.innerHTML)

}



//----------------------------------------------------------------------------------------------------------
htlForm.init = function(){
	//var frm = createHotelForm()
htlForm.resultContainer = document.getElementById("hsPlot")

	if (htlForm.resultContainer)
	{
	htlForm.resultContainer.appendChild(this)
	}

	else {
	htlForm.dv = overLay()
	htlForm.dv.aalert(this)
	}
return this;
}


return htlForm
}




function addPax(Adult,Mr,FirstName,LastName,GENDER,DOB,MEAL){

if (!(document.pax))
{
document.pax = new Array()
}

var pax = document.pax

pax[pax.length] = new Array(Adult,Mr,FirstName,LastName,GENDER,DOB,MEAL)

/*pax[0]

addPax("Adult","Mr","FirstName","LastName","GENDER","DOB","MEAL")
*/

//alert(pax[0][0])
}






//HTML Table to SQL - KK

function tableSQL(searchAble){

searchAble.resultSet = new Array()
searchAble.colmns = new Array()
var cs = searchAble.getElementsByTagName("TH")

	for (var i=0;i<cs.length;i++)
	{
		searchAble.colmns[i] = cs[i].innerHTML
	}

	searchAble.getColInd = function(colName){
	var cHeads = searchAble.colmns
	for (var i=0;i<cHeads.length;i++)
	{
		if (cHeads[i] == colName)
		{
			return i;
		}
	}
}


searchAble.newResultSet = function(){

var returnArray = new Array()

returnArray.recordCount = function(){
return this.length
}

returnArray.pointer = -1

returnArray.BOF = function(){
	if (returnArray.pointer == -1)
	{
		return true
	}
	return false;
}

returnArray.EOF = function(){
	if (returnArray.pointer >= (this.length -1))
	{
		return true
	}
	return false;
}

returnArray.nextRecord = function(){

	returnArray.pointer = returnArray.pointer + 1
}

returnArray.getFld = function(colName){

if (returnArray.BOF)
{
	returnArray.nextRecord()
}
var colIndx = searchAble.getColInd(colName)
return searchAble.rows[this[this.pointer]].getElementsByTagName("TD")[colIndx].innerHTML
}


returnArray.filter = function(colName,Value,like){
var nwArray = searchAble.newResultSet()
var colIndx = searchAble.getColInd(colName)
	var rw = searchAble.rows


for (var i=0;i<this.length;i++)
{
	var tgtText = rw[this[i]].getElementsByTagName("TD")[colIndx].innerHTML

//alert("T:" + tgtText + " - " + Value)
if (like)
{
	if (tgtText.indexOf(Value) > -1)
	{
		nwArray[nwArray.length] = this[i]		
	}
}
else{
	if (tgtText.value == Value.toString())
	{
	nwArray[nwArray.length] = this[i]		
	}
}

//HOTELID


//	alert(i)
}

searchAble.resultSet = nwArray
}



return returnArray;

}


searchAble.search = function(colName,value,like){
searchAble.resultSet = searchAble.newResultSet()
var colIndx = searchAble.getColInd(colName)
var rw = searchAble.rows

if ((colIndx == undefined))
{
	return false;
}

	
	for (var i=1;i<rw.length;i++)
	{

		var txt = rw[i].getElementsByTagName("TD")[colIndx].innerHTML

		if (like)
		{
			if (txt.indexOf(value) > -1)
			{
//				rw[i].style.background = "blue"
				searchAble.resultSet[searchAble.resultSet.length] = i
			}
		}
		else{
			if (txt == value)
			{
				searchAble.resultSet[searchAble.resultSet.length] = i
			}

		}
	}


}
return searchAble
}




//HTML TABLE SQL Ends




















function addPaxDetails(){
addPax('Adult','MR','NEERAJ','CHANDRA','M','4/11/1975','VEG')
addPax('Adult','MS','NUPUR','MAHAJAN','F','5/28/1979','VEG')
addPax('Child','MS','Okesh','Badhiye','M','5/28/1979','VEG')
}

addPaxDetails()


