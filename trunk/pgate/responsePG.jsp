<%@ page language="java" session="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE>Payment Gateway</TITLE>
<script type="text/javascript">function send() {document.frmResponse.submit();}</script>
</HEAD>
<BODY>
    
<jsp:include page="header.jsp" flush="true" />

<div id="formDiv">
	<div style="height:50px;float:left;width:100%;">&nbsp;</div>
	<div id="msg" style="float:left; width:100%; text-align:center;">
		Please wait, while we Redirect you.....<br /> 
	<br />
	</div>
</div>

<form name="frmResponse" id="frmResponse" method="POST" action="<%=request.getAttribute("respUrl")%>">
	<input type="hidden" name="STATUS" value="<%=request.getAttribute("status")%>">
	<input type="hidden" name="TXN_ID" value="<%=request.getAttribute("txnId")%>">
	<input type="hidden" name="MSG_DESC" value="<%=request.getAttribute("respComment")%>">
	<input type="hidden" name="ORDER_ID" value="<%=request.getAttribute("orderId")%>">
	<input type="hidden" name="TXN_TYPE" value="<%=request.getAttribute("txnType")%>"><!-- Offline/ONLINE -->
    <input type="hidden" name="TXN_MODE" value="<%=request.getAttribute("txnMode")%>"><!-- ICICI/Cheque/DD/Eserve -->
	<input type="hidden" name="APP_PARAM1" value="<%=request.getAttribute("appParam1")%>">
    <input type="hidden" name="APP_PARAM2" value="<%=request.getAttribute("appParam2")%>">
    <input type="hidden" name="APP_PARAM3" value="<%=request.getAttribute("appParam3")%>">
    <input type="hidden" name="TXN_AMOUNT" value="<%=request.getAttribute("txnAmount")%>">
    <input type="hidden" name="TXN_CURRENCY" value="<%=request.getAttribute("currency")%>">
</form>


<jsp:include page="footer.jsp" flush="true" />
<script language="javascript">
    document.frmResponse.submit();
    function checkIfSingle(){
        if ( document.readyState == "complete") {
            send();
        }else{
            setTimeout( checkIfSingle, 50);
        }
    }
    document.onload = checkIfSingle();
</script>
</BODY>
</HTML>