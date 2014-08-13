<%@ page import="app.Config"%>
<%Config.setAllConfig();%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE> Payment GateWay </TITLE>
	<script language="javascript">
		var SITE_PATH = '<%=Config.get("SITE_PATH")%>';
	</script>
</HEAD>
<BODY>

<jsp:include page="cl-cnk/header.jsp" flush="true" />

<form action="<%=Config.get("SITE_PATH")%>/pgServlet" method="post">
<table align="center" border=0 style="height:250px">


    <!--<tr>
        <td>
            Transaction Type
        </td>
        <td>
            <select name="txnType">
                <option value="ONLINE">Online</option>
                <option value="OFFLINE">Offline</option>
            </select>
        </td>
    </tr>-->

	<tr>
		<td>
			Response Expected
		</td>
		<td>
			<select name="rplStatus">
				<option value="">SELECT </option>
				<option value="APPROVED">APPROVED</option>
				<option value="FAILURE">FAILURE</option>
			</select>
		</td>
	</tr>
	
	<tr>
		<td>
			Client Id
		</td>
		<td>
			<input type="text" name="clientId" value="1">
		</td>
	</tr>

	<tr>
		<td>
			Txn Amount
		</td>
		<td>
			<input type="text" name="txnAmount" value="2">
		</td>
	</tr>

	<tr>
		<td>
			Currency
		</td>
		<td>
			<input type="text" name="currency" value="INR">
		</td>
	</tr>

	<tr>
		<td>
			Order No
		</td>
		<td>
			<input type="text" name="orderNo" value="00001">
		</td>
	</tr>

	<tr>
		<td>
			App Param 1
		</td>
		<td>
			<input type="text" name="appParam1" value="">
		</td>
	</tr>

<tr>
        <td>
            App Param 2
        </td>
        <td>
            <input type="text" name="appParam2" value="">
        </td>
    </tr>
    
    <tr>
        <td>
            App Param 3
        </td>
        <td>
            <input type="text" name="appParam3" value="">
        </td>
    </tr>

	<tr>
		<td colspan=2 align="center">
			<input type="submit">
		</td>
	</tr>

</table>

</form>


<jsp:include page="cl-cnk/footer.jsp" flush="true" />
 </BODY>
</HTML>
