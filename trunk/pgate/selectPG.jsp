<%@ page language="java" session="true"%>
<%@ page import="java.util.*, java.lang.*"%>
<%@ page import="app.Config"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<HTML>
<HEAD>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=UTF-8">
<TITLE>TRANSACTION DETAILS</TITLE>
<script language="javascript" type="text/javascript">
	var SITE_PATH = '<%=Config.get("SITE_PATH")%>';
	function onClk_Submit(btnEle){
        frmEle = document.getElementById(btnEle);
        ActivateWait();
		frmEle.actionchanged.value="Submit";
		//frmEle.method = "POST";
		//frmEle.action = SITE_PATH + "/payServlet";
		frmEle.submit();
	}
</script>
</HEAD>
<BODY bgcolor='#83a1C6' style="overflow-x:hidden;">
<jsp:include page="header.jsp" flush="true" />
<div id="formDiv" style="display:none;">
	<div style="height:50px;float:left;width:100%;">&nbsp;</div>
	<div id="msg" style="float:left; width:100%; text-align:center;">
		Please wait, We are redirecting you to Payment Gateway.<br /> 
		Thank you for your Patience......<br />
	</div>
	<div style="height: 50px; float: left; width: 100%;">&nbsp;</div>
</div>
<div id="pageContent" style="width:100%; text-align:center;">
<%    
	if (request.getAttribute ("resultArray") != null) {
		
		ArrayList<Object> rows = (ArrayList) request.getAttribute ("resultArray");
        
        int totalElement = rows.size();
        int counter = 0;
		Iterator i = rows.iterator();
		HashMap<String,Object> objField = new HashMap<String,Object> ();
%>      
          <table style="padding-bottom: 10px; padding-top: 10px;" border="0" align="center" width="70%">
          <tr><td>
<%		
		while (i.hasNext()){
			counter++;
			objField.putAll((HashMap)i.next());
			String action = Config.get("SITE_PATH") + "/payServlet";
			//Modified by Asma on 21032011 for a consolidate Online & Offline Payment options 
				String pgId = objField.get("pg_id").toString();
			%>
				<form method="post" action="<%=action%>" name="frmRelated" id="frm<%=objField.get("caption")%>" title="paymentGateway">
			
                <div name="frmRelatedDiv" id="frmRelatedDiv" style="width:100%;">
                    <table border="0" align="center" width="100%">
                    <tr>
                        <td style="width: 150px;  text-align: right; padding-right: 10px;" align="left"> 
                            <input type="hidden" id="actionchanged" name="actionchanged" value="">
                            <input type="hidden" name="txnMode" value="<%= objField.get("caption")%>">
                            <input type="hidden" name="clientId" size="15" value="<%=request.getParameter("clientId")%>">
    	            		<input type="hidden" name="orderNo" size="15" value="<%=request.getParameter("orderNo")%>">
    	            		<input type="hidden" name="txnAmount" size="15" value="<%=request.getParameter("txnAmount")%>">
    	            		<input type="hidden" name="currency" size="15" value="<%=request.getParameter("currency")%>">
    	            		<input type="hidden" id="actionchanged" name="actionchanged" value="">
    	            		<input type="hidden" name="pgId" size="15" value="<%=pgId%>">
    	            		<% if(pgId.equalsIgnoreCase("0")){
    	            			String pgMode = "OFFLINE";
    	            		%>
    	            		<input type="hidden" name="pgMode" size="15" value="<%=pgMode%>">
    	            		<% }else{ 
    	            			String pgMode = "ONLINE";
    	            		%>
    	            		<input type="hidden" name="pgMode" size="15" value="<%=pgMode%>">
    	            		<%  }  %>
                           <input style="display: none;" id="inputButton" type="radio" name="btnSub" value="Pay" onClick="JavaScript:onClk_Submit('frm<%=objField.get("caption")%>');">
                           <a href="javascript:void(0);" onClick="JavaScript:onClk_Submit('frm<%=objField.get("caption")%>');">
                                <img border="0" alt="<%= objField.get("caption")%>" src="<%= Config.get("SITE_PATH")%>/admin/images/png/<%= objField.get("image_caption")%>.png">
                           </a>
                       </td>
                        <td style="text-align: left;"> 
                            <%= objField.get("caption")%>
                        </td>
                    </tr>
                    </table>
                </div>
           	</form>
            <%
            
            if(counter%2 == 0){
               out.println("</td></tr><tr><td>");
            }else{
            	out.println("</td><td>");
            }
		}
	}
%>

</td></tr></table> 
</div>
<script language="javascript" type="text/javascript">
function ActivateWait(){
	var formElem = document.getElementsByName("frmRelatedDiv");
    for(var i=0; i < formElem.length; i++){
            formElem[i].style.display = "none";
    }
    document.getElementById("formDiv").style.display = "block";
}
function checkIfSingle(){
	var formElem = document.getElementsByTagName("FORM");
	if ( document.readyState == "complete" && formElem.length == 1) {
        onClk_Submit(formElem[0].id);
	}else{
		setTimeout( checkIfSingle, 50);
	}
}
//window.onload = checkIfSingle();
</script>
<jsp:include page="footer.jsp" flush="true" />
</BODY>
</HTML>