<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
	<TITLE> Payment Gateway </TITLE>
</HEAD>

<BODY>

	<%@ page language="java" session="true"%>
	<br><br>

	<table border="1" align="center" width="70%">
	<tr>
		<th colspan=2 bgcolor="#9999FF" ><p style= "color:blue">Output parameters are... </th>
	</tr>
	
	<tr>
		<td>
			Txn Status
		</td>
		<td>
			<center><FONT color="red"><b> <%=request.getParameter("STATUS") %></b> </FONT></center>
		</td>
	</tr>

	<tr>
		<td>
			Txn Id
		</td>
		<td>
			<center><FONT color="red"><b> <%=request.getParameter("TXN_ID") %></b> </FONT></center>
		</td>
	</tr>

	<tr>
		<td>
			Reason
		</td>
		<td>
			<center><FONT color="red"><b> <%=request.getParameter("MSG_DESC") %></b> </FONT></center>
		</td>
	</tr>
	
	<tr>	
		<td>
			Order Id
		</td>
		<td>
			<center><FONT color="red"><b> <%=request.getParameter("ORDER_ID") %></b> </FONT></center>
		</td>
	</tr>

	<tr>	
		<td>
			Txn Type
		</td>
		<td>
			<center><FONT color="red"><b> <%=request.getParameter("TXN_TYPE") %></b></FONT></center>
		</td>
	</tr>

    <tr>    
        <td>
            Txn Mode
        </td>
        <td>
            <center><FONT color="red"><b> <%=request.getParameter("TXN_MODE") %></b></FONT></center>
        </td>
    </tr>

	<tr>	
		<td>
			App Param 1
		</td>
		<td>
			<center><FONT color="red"><b> <%=request.getParameter("APP_PARAM1") %> </b></FONT></center>
		</td>
	</tr>

<tr>    
        <td>
            App Param 2
        </td>
        <td>
            <center><FONT color="red"><b> <%=request.getParameter("APP_PARAM2") %> </b></FONT></center>
        </td>
    </tr>


    <tr>    
        <td>
            App Param 3
        </td>
        <td>
            <center><FONT color="red"><b> <%=request.getParameter("APP_PARAM3") %> </b></FONT></center>
        </td>
    </tr>
    
    <tr>    
        <td>
            TXN_CURRENCY
        </td>
        <td>
            <center><FONT color="red"><b> <%=request.getParameter("TXN_CURRENCY") %> </b></FONT></center>
        </td>
    </tr>
    
    <tr>    
        <td>
          TXN_AMOUNT
        </td>
        <td>
            <center><FONT color="red"><b> <%=request.getParameter("TXN_AMOUNT") %> </b></FONT></center>
        </td>
    </tr>




	</table>
	<br><br>

</BODY>
</HTML>
