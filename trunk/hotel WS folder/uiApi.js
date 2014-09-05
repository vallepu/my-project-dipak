//*/ =============================== Creat Tabel function Start here ====>><<
function createTable(tbl)
{
	if (tbl == undefined)
	{
		var tbl = document.createElement("TABLE")
	}


	tbl.newRow = function(){
		if (document.all)
		{
		tbl.insertRow()
		}
		else{
		tbl.insertRow(tbl.rows.length)
		}
	}

	tbl.newTD = function(){
	var TD = document.createElement("TD")
	return TD;
	}

	tbl.newTH = function(){
	var TD = document.createElement("TH")
	return TD;
	}

	tbl.insertLast = function(TD){
		var num = tbl.rows.length - 1
			if (num < 0)
			{
			num = 0
			}

	try
	{
	tbl.rows[num].appendChild(TD)		
	}
	catch (e)
	{
		//alert(tbl.innerHTML + "\n" + e  + "\n" + TD.innerHTML)
	}


	}


	tbl.findMyIndex = function(obj){
	var elm
	if (document.all)
	{
		elm = obj.parentElement
	}
	else{
		elm = obj.parentNode
	}

	if (obj.tagName == "TD")
	{
	var junx = elm.cells
	}

	if (obj.tagName == "TR")
	{
	var junx = elm.rows
	}

	for (var i=0;i<junx.length;i++)
	{
	if (obj == junx[i])
		{
			return i
		}
	}

}


return tbl;
}
//*/ =============================== Creat Tabel function Ends here ====>><<



//*/ =============================== Function for finding the parent element Start ====>><<
function findPar(obj,ElmType){
	//alert("obj : " + obj)
	var parE = obj
	while (parE.tagName != ElmType)
	{
		if (document.all)
		{
				parE = parE.parentElement
		}
		else{
				parE = parE.parentNode
		}
	}
	return parE
}
//*/ =============================== Function for finding the parent element Ends ====>><<

//*/ =============================== Function overLay Start ====>><<
function overLay(){
//alert("here overlay");

var dv = document.createElement("DIV")
	document.body.style.overflow = "hidden"
	dv.style.width = document.body.clientWidth
	dv.style.height = document.body.clientHeight
	//dv.style.border = "red solid 1px"
	dv.style.position = "absolute"
	dv.style.top = document.body.scrollTop //"0px"
	dv.style.left = "0px"
	dv.style.background = "URL(http://uat.cnkdev.com/enquiry/resources/defaults/images/trans.png)"

dv.hide = function(){
	document.body.removeChild(this)
	document.body.style.overflow = ""
}

dv.ondblclick = function(){
	dv.hide()
}

dv.closeButton = function(img){

	var a = document.createElement("A")

	if ((img == "") || (img == undefined))
	{
		a.innerHTML = "X"
	}
	
	else{
		a.innerHTML = "<img border=0 alt='Close' src='" + img + "'>"
	}

	a.onclick = function(){
		dv.hide()
	}
	
	a.href = "javascript:void(0)"
	a.title = "Close"
	return a;

}



dv.formUI = function(obj){

	var t = createTable()
	t.className = "dvFrame"
	t.newRow()

	var td = t.newTH()
	td.innerHTML = "Cox & Kings"
	t.insertLast(td)
	
	var td = t.newTD()
	td.appendChild(dv.closeButton("http://obadhiye.cnkdev.com/close.gif"))
	td.style.textAlign = "right"
	t.insertLast(td)

	t.newRow()
	var td = t.newTD()
	td.colSpan = 2
	td.appendChild(obj)
	t.insertLast(td)
	dv.appendChild(t)

	t.relocate = function(){
		this.style.left = ((document.body.offsetWidth - t.offsetWidth)/2) + "px"
		this.style.top = ((document.body.clientHeight - t.offsetHeight)/2) + "px"
	}
	
	t.relocate()
	return t;

}

dv.alert = function(show){

	var x = createTable()

	x.newRow()
	
	var td = x.newTD()
	x.className = "dvInner"
//	x.style.width = "200px";
	x.style.height = "100px";

	td.innerHTML = show
	x.insertLast(td)
	dv.formUI(x)
}

dv.aalert = function(show){
	dv.innerHTML = "";
	var x = document.createElement("div")
//	x.style.width="75\%"
	x.className = "dvInner"
	x.appendChild(show)
	var l = dv.formUI(x)
	if ((document.body.clientHeight - x.offsetHeight) < 50)
	{
			x.style.height= document.body.clientHeight - 50
			x.style.overflowY = "scroll"
			x.style.background = "white"
			l.style.width = "75\%"
			l.relocate()
	}

}


document.body.appendChild(dv)
return dv;
}
//*/ =============================== Function overLay Ends ====>><<

//*/ =============================== Function Creat Form Starts ====>>--------------------------------------------------------------<<
function createForm(frm){

if (frm == undefined || typeof(frm) == "string"){
	if (document.all)
	{
		var frmy = document.createElement("<FORM name='" + frm + "'>")
		frmy.id = frm
	}
	else
	{
		var frmy = document.createElement("FORM")
		frmy.name = frm
		frmy.id = frm
	}
	 frm = frmy
}

//alert(frm)


frm.createInput = function(ename,evalue,etype,defText){
	if (document.all)
	{
		var elm = document.createElement("<INPUT name='" + ename + "' type='" + etype + "'>")
		elm.id = ename
	}
	else
	{
		var elm = document.createElement("INPUT")
		elm.name = ename
		elm.id = ename
		elm.type = etype
	}

	
	if (!(evalue == undefined))
	{
	elm.value = evalue
	}


	if (!(defText == undefined))
	{
		defText = defText
		elm.value = defText
	}

	elm.defText = defText

	if (etype == "radio")
	{
		elm.getValue = function()
		{			
			var x = frm[this.name]
			for (var i=0;i<x.length;i++)
			{
				if (x[i].checked)
				{
					return x[i].value
				}
			}

		}
	}
	
	if (etype == "datePicker"){
		elm.onclick = function(){
			if (!(this.Calender))
			{
				this.Calender = Calender()
			}
			this.Calender.drawCal(this, this.value)
		}
	}
	return elm
}

frm.createSelect = function(ename){
	if (document.all)
	{
		var elm = document.createElement("<SELECT name='" + ename + "'>")
		elm.id = ename
	}
	else{
		var elm = document.createElement("SELECT")
		elm.name = ename
		elm.id = ename
	}

	elm.createOption = function(eTitle,eValue){
		var option = document.createElement("OPTION")
		option.value = eValue
		if (document.all)
		{
			option.innerText = eTitle
		}
		else
		{
			option.text = eTitle
		}
		elm.appendChild(option)
	}
	

	elm.createOptionArray = function(arr){
		arr = arr.split(", ")
			for (var i=0;i<arr.length;i++)
				{
					var opt = arr[i].split(";")
					elm.createOption(opt[1],opt[0])
				}
	}



	elm.fillNumbers = function(numStart,numEnd){
		for (var i=numStart;i<=numEnd;i++)
			{
				elm.createOption(i,i)
			}
	}

	elm.selectValue = function(value){
		var v = elm.options
		for (var i=0;i<v.length;i++)
		{
			if (v[i].value == value)
			{
				elm.selectedIndex = i
			}
		}
			if (typeof(elm.onchange) == "function")
			{
		elm.onchange()
			}
	}
return elm
}

frm.setValidate = function(){
	var elms = frm.elements
	for (var i=0;i<elms.length;i++)
	{
		if (!(elms[i].getAttribute("validate") == undefined))
		{
			frm.onsubmit = function(){
				//alert (frm.validate());

			return frm.validate()
			}

			var typ = elms[i].getAttribute("type");
			var typName = elms[i].getAttribute("radioType");

				if(typ=="email"){

				elms[i].checkValidate = function(){

				var str=this.value
					var str = str.split("@")
					if (str[1] == undefined)
					{
						return false;
					}


					if ((str[0].length < 1) || ((str[1].indexOf(".") < 3) || ((str[1].length -str[1].indexOf(".")) < 3)))
					{
						return false;
					}

					
					return true;
					}
				
				}
			
			
				if (typ == "datePicker"){
					elm = elms[i]
					elm.checkValidate = function(){

						if (isDate(this.value))
						{
							//alert("this is the date");
							return true;
						}
						else
						{
							//alert("this is not date");
							return false;
						}						
						return false;
						}

				}

				if((typ=="text") || (typ==undefined) || (typ=="select-one")){
				elms[i].checkValidate = function(){
					var str=this.value

					str = str.toString()
					//alert(str.length + " < " + this.validate)
					//alert(str.value + " <  valdidate value")
					if (str.value == '')
					{
						return false;
					}
					else if (str.length < this.getAttribute("validate"))
					{
						return false;
					}
					return true;
					}				
				}

				//====================================================\\
				if(typ=="select"){
				elms[i].checkValidate = function(){
					var str=this.value

					str = str.toString();
					//alert(str);
					//alert(str.length + " < " + this.validate)
					//alert(str.value + " <  valdidate value")
					if (str.value == '' || str.length=='0')
					{
						return false;
					}
					/*/
					else if (str.length < this.getAttribute("validate"))
					{
						return false;
					}
					//*/
					return true;
					}				
				}

				//====================================================\\
				if(typ=="radio"){

					elms[i].checkValidate = function(){
		
						var radio = this;

						//alert ("Value of Radio ::-> " + radio);

						var x = findPar(radio,"FORM");
						var radGroup = x[radio.name];

							for (var k=0; k < radGroup.length ; k++)
							{
								if (radGroup[k].checked == true)
								{
									var checkedVal = true;
									break;
								}
							}
							if (checkedVal == true)
							{
									return true;
							}
							else
							{
								return false;
							}
							return false;
					}
				}
				//====================================================\\

				//====================================================\\
				/*/
				if(typName){
alert ("Value of Radio ::-> " + typName)
alert ("Velms[i] Radio ::-> " + elms[i])

					//elms[i].checkValidate = function(){
alert ("Value of Radio2 : :  -  > ");
						var radio = this;
alert ("Value of Radio2 ::-> " + radio);

						var x = findPar(radio,"FORM");
						var radGroup = x[radio.name];
alert ("Value of  x ::-> " + x);
alert ("Value of radGroup ::-> " + radGroup);



/* /							for (var k=0; k < radGroup.length ; k++)
							{
								if (radGroup[k].checked == true)
								{
									var checkedVal = true;
									break;
								}
							}
							if (checkedVal == true)
							{
									return true;
							}
							else
							{
								return false;
							}
							return false;
//* /
					}
				//}
				//*/
				//====================================================\\



				if (typ == "numb" || typ == "number" || typ == "int"){

					elms[i].checkValidate = function()
					{
					var str=this.value
						//alert ("str condition -->> " + str.toString().search(/^-?[0-9]+$/));

						//alert ("str parseInt -->> " + parseInt(str));
						if (str.toString().search(/^-?[0-9]+$/))
						{
							return false;
						}
						else 
						{
							if (str.length < this.getAttribute("validate"))
							{
								return false;
							}
							else
							{
							return true;
							}				
						}
					}
				}
			}
			//break
		//alert(elms[i].name + "\n" + elms[i].checkValidate)
		}

	}

frm.validate = function(){
elems = frm.elements

var errorNode = "Please Check the Fields Bellow";

for (var i=0;i<elems.length;i++)
{
	if (elems[i].checkValidate != undefined)
	{

		var oops = elems[i].checkValidate()
		if (!oops)
		{
			if (!elems[i].value)
			{
				errorNode = errorNode + "\n- Please enter ";
			}
			else
			{
				errorNode = errorNode + "\n- Please enter valid ";
			}

			if (elems[i].getAttribute("titleName") == null)
			{
				elems[i].setAttribute("titleName",elems[i].name)
			}


			errorNode = errorNode + elems[i].getAttribute("titleName");
			if (elems[i].getAttribute("condition"))
			{
				errorNode = errorNode + " (" +elems[i].getAttribute("condition")+")";
			}			
			/*/		Flashing object
				document.flashingObj = elems[i];
				document.flashingTime = 50;
				ticker()
			//*/
			//alert (errorNode)
			//return false;
		}
	}
//var k = i;
}
if (errorNode && errorNode != "Please Check the Fields Bellow")
{
	alert (errorNode)
	//elems[k].focus();
	return false;
}
else
{
	return true;
}
//elems[i].focus();


}
	return frm
}
//*/ =============================== Function Creat Form Ends ====>>--------------------------------------------------------------<<



function ConvToTab(tbl){
tbl = createTable(tbl)
tbl.firstRow = tbl.rows[0].cells
tbl.content = tbl.rows[1].cells

if (tbl.getAttribute("defaultTab") == undefined)
{
tbl.active = 0
}
else{
if (tbl.getAttribute("defaultTab") < tbl.firstRow.length)
{
tbl.active = tbl.getAttribute("defaultTab")
}


}


tbl.showTab = function(obj){

for (var i=0;i<tbl.firstRow.length;i++)
{
	tbl.firstRow[i].className = "inactive"

		if (tbl.content[i])
		{
			tbl.content[i].style.display = "none"
		}
}

	tbl.firstRow[(tbl.findMyIndex(obj))].className = "active"
if (document.all)
{
	tbl.content[(tbl.findMyIndex(obj))].style.display = "block"
}
else{
	tbl.content[(tbl.findMyIndex(obj))].style.display = "table-cell"
}

}


for (var i=0;i<tbl.firstRow.length;i++)
{
	var cll = tbl.firstRow[i]
		if (cll.getAttribute("defaultTab") == "true")
		{
			tbl.active = 0
		}

		cll.onclick = function(){
			tbl.showTab(this)
		}

		if (tbl.content[i])
		{
			tbl.content[i].colSpan = tbl.firstRow.length
			tbl.content[i].style.display = "none"
		}

}

tbl.showTab(tbl.firstRow[tbl.active])
}


function findPosX(obj)
  {
    var curleft = 0;
    if(obj.offsetParent)
        while(1) 
        {
          curleft += obj.offsetLeft;
          if(!obj.offsetParent)
            break;
          obj = obj.offsetParent;
        }
    else if(obj.x)
        curleft += obj.x;
    return curleft;
  }

  function findPosY(obj)
  {
    var curtop = 0;
    if(obj.offsetParent)
        while(1)
        {
          curtop += obj.offsetTop;
          if(!obj.offsetParent)
            break;
          obj = obj.offsetParent;
        }
    else if(obj.y)
        curtop += eval(obj.y);
    return curtop + 18;
  }


// Flashing Script here
function ticker(){

if (!(document.flashingObj == undefined))
{

	if (document.flashBannner == undefined)
	{
		document.flashBannner = document.createElement("DIV")
		FB = document.flashBannner
		document.body.appendChild(FB)
	}

if (document.flashingTime < 100)
{
		document.flashBannner.style.display = "block"
		FB.style.position = "absolute"
		FB.style.background = "#CCFFFF"
		FB.style.border = "red solid 1px"

		FB.style.padding = "2px"
		//alert ("here");
		//alert (document.flashingObj.title);
		//alert (document.flashingObj.getAttribute("titleName"));
		FB.innerHTML = "<b> Please enter valid info for " + document.flashingObj.getAttribute("titleName") + "</b>";
		FB.style.left = findPosX(document.flashingObj)
		FB.style.top = findPosY(document.flashingObj) + 2
}


//alert(document.flashingObj.style.background)
	if (document.flashingObj.style.backgroundColor == "yellow")
	{
		document.flashingObj.style.backgroundColor = "white"
	}
	else{
		document.flashingObj.style.backgroundColor = "yellow"
	}

		document.flashingTime = document.flashingTime + 50
}
	if (document.flashingTime <= 500)
	{
	setTimeout("ticker()",document.flashingTime)
	}
	else{
		document.flashBannner.style.display = "none"
	}

}

function Inint_AJAX() {
try { return new ActiveXObject("Msxml2.XMLHTTP");  } catch(e) {} //IE
try { return new ActiveXObject("Microsoft.XMLHTTP"); } catch(e) {} //IE
try { return new XMLHttpRequest();          } catch(e) {} //Native Javascript
// alert("XMLHttpRequest not supported");
return null;
};

function newDoc(txt){
if (document.all)
  {
  xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
  xmlDoc.async="false";
  xmlDoc.loadXML(txt);
  return xmlDoc; 
  }
else
  {
  parser=new DOMParser();
  xmlDoc=parser.parseFromString(txt,"text/xml");
  return xmlDoc;
  }
}


var dtCh= "/";
var minYear=1900;
var maxYear=2100;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12)

	var pos1=dtStr.indexOf(dtCh)
	
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	
	var strDay=dtStr.substring(0,pos1)
	
	var strMonth=dtStr.substring(pos1+1,pos2)
	
	var strYear=dtStr.substring(pos2+1)
	
	strYr=strYear
	
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)

	if (pos1==-1 || pos2==-1){
		//alert("The date format should be : mm/dd/yyyy")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		//alert("Please enter a valid month")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		//alert("Please enter a valid day")
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		//alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		//alert("Please enter a valid date")
		return false
	}
return true
}



//Common Funcs

function getXMLDoc(data){
var xmlDoc
//alert("LOADING XMLDOC" + data)
	if (document.all)
	{
		xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
    xmlDoc.async=false;
    xmlDoc.load(data);

	}
	else{
	var parser=new DOMParser();
	var xmlDoc=parser.parseFromString(data,"text/xml");

	}
//alert("DOC \n ------------ \n" + xmlDoc.xml)
	return xmlDoc;
}

function getXMLHttpRequest() {
      var _req;
      // branch for native XMLHttpRequest object (safari/mozilla)
      if (window.XMLHttpRequest) {
        _req = new XMLHttpRequest();
      }
      // branch for IE/Windows ActiveX version
      else if (window.ActiveXObject) {
        _req = new ActiveXObject("Microsoft.XMLHTTP");
      }

      return _req;
	  
    }

function getNodeValue(doc,nodeName){

try{
	if (document.all)
	{
	return doc.getElementsByTagName(nodeName)[0].text
	}
	else{
	return doc.getElementsByTagName(nodeName)[0].textContent
	}
}
catch(e){
//alert(e + "\n" + nodeName)
}

}
