<%@ page language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE> Transaction Details </TITLE>
<style>
body{
	background:#736F6E;
}

table{
	width:90%; 
	background-color:#FFFFFF;
}

table td,th{
	background-color: #AFC7C7;
}

</style>
</HEAD>
 <BODY style="">

<table align="center" cellspacing="1px" cellpadding="3px">
	<tr>
		<th colspan="8"> <h3>Transaction Details</h3> </th>
	</tr>
	<tr>
		<th> Txn Id </th>
		<th> Transaction Date </th>
		<th> Payment Gateway </th>
		<th> Merchant Id </th>
		<th> Currency </th>
		<th> Txn Amount </th>
		<th> Client Name </th>
		<th> Status </th>
	</tr>
<%
	if (request.getAttribute ("resultArray") != null) { 
	
		HashMap objField = null;
		ArrayList rows = new ArrayList (); 

		rows = (ArrayList)request.getAttribute ("resultArray");
		Iterator i = rows.iterator();
		objField = new HashMap ();
		while (i.hasNext()){
			objField.putAll((HashMap)i.next());
			//2010-10-11 14:58:33
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdfDate.format(objField.get("created_date"));

	%> 

			<tr>
				<td> <%= objField.get("txn_id")%> </td>
				<td> <%= date%> </td>
				<td> <%= objField.get("pg_name")%> </td>
				<td> <%= objField.get("merchant_id")%> </td>
				<td> <%= objField.get("currency_no")%> </td>
				<td> <%= objField.get("txn_amount")%> </td>
				<td> <%= objField.get("client_name")%> </td>
				<td> <span style="color:#7E2217"> <%= objField.get("status")%></span> </td>
			</tr>

	<%
		}
	}else{
%>

	<tr>
		<td colspan="8"> No Transaction Records Found! </td>
	</tr>
<%
	}
%>
</table>
</BODY>
</HTML>