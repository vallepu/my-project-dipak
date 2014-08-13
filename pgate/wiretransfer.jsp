<%@ page language="java" session="true"%>
<%@ page import="java.util.*"%>
<%@ page import="app.Config"%>

<%Config.setAllConfig();%>
<% if(request.getParameter("error") != null){ %>


	<table align="center" border=1 style="height:30px;" cellpadding="2" cellspacing="0">
        <tr><td style="font-size: medium; color: red">Error: <%=(String)request.getParameter("error")%></td></tr>
    </table>
<%  }  %>


<%
    String txnMode = (String) session.getAttribute("txnMode");
	//String txnMode = request.getParameter("txnMode");
	String currency = (String) session.getAttribute("currency");
	String txnAmount = (String) session.getAttribute("txnAmount");
%>
<script type="text/javascript">
var SITE_PATH = '<%=Config.get("SITE_PATH")%>';
function validate(frmObj){

    var error = "Error:-\n";
    var isError = false;
    
    if(frmObj.txn_no.value == ""){
        isError = true;
        error += "Enter Swift Transfer Reference No.\n";
    }  

    if(frmObj.bank_name.value == ""){
        isError = true;
        error += "Enter Remitting Bank\n";
    }

    if(frmObj.issue_date.value == ""){
        isError = true;
        error += "Enter Remittance Date\n";
    }

    if(isError){
        alert(error);
        return false;
    }else{
    	return true;
    }  
}


</script>
<div style="height: 15px;">&nbsp;</div>
<form method="post" action="<%=Config.get("SITE_PATH")%>/OfflineServlet" onsubmit="return validate(this);" name="frm_offpayment">
<table align="center" border="0" style="height:250px;" cellpadding="2" cellspacing="0">

	<!--<tr>
		<td>
			Mode of Payment
		</td>
		<td>
			<select name="pg_name ">
            <% if(txnMode == null){ %>
        		<option value="">SELECT</option>
				<option value="Cheque" >Cheque</option>
				<option value="Demand Draft">Demand Draft</option>
             <% } else{  %>   
                <option value="<%=txnMode%>"><%=txnMode%></option>
             <%} %>
		</select>
		</td>
	</tr>-->
	
	<input type="hidden" value="<%=txnMode%>" name="txnMode" />
	
	<tr>
		<td>
			Swift Transfer Reference No.
		</td>
		<td>
			<input type="text" name="txn_no" value="">
		</td>
	</tr>
	<tr>
		<td>
			Remitting Bank
		</td>
		<td>
			<input type="text" name="bank_name" value="">
		</td>
	</tr>

    <tr>
        <td>
            Remittance Date
        </td>
        <td>
            <input name="issue_date" id="issue_date" type="text" readonly="" href="javascript:void(0)" onfocus="" value="">
			&nbsp;<a style="text-decoration: none;" href="javascript:void(0)" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_offpayment.issue_date);return false;" hidefocus="">
			<img border="0" align="absMiddle" alt="Calender Pop Up" src="http://static.coxandkings.co.in/cnk_html/images/cal.gif" name="popcal"></a>
        </td>
    </tr>
    
    <tr>
        <td>
            Currency
        </td>
        <td>
        	<input type="text" name="currency" value="<%=currency%>" readonly="readonly">
            <!--<select name="currency">
                <option value="">SELECT</option>
            <%        
                HashMap<String,Object> objField = new HashMap<String,Object> ();
                
                ArrayList<Object> rows = (ArrayList) request.getAttribute("arrCurrencyList");
            
                Iterator i = rows.iterator();
                while(i.hasNext()){
                    objField.putAll((HashMap)i.next());
                    
                	String currency_code = objField.get("currency_code").toString();
                    String currency_no = objField.get("currency_no").toString();
            %>
                    <option value="<%=currency_no%>"><%=currency_no%></option>
            <%
               }
            %>    
            </select>-->
        </td>
    </tr>


    <tr>
        <td>
            Amount
        </td>
        <td>
            <input type="text" name="amount" value="<%=txnAmount%>" readonly="readonly">
        </td>
    </tr>

	<tr>
		<td colspan=2 align="center">
			<input name="submit" value="Pay" type="submit">
		</td>
	</tr>

</table>

</form>
<iframe width=132 height=142 name="gToday:contrast:agenda.js" id="gToday:contrast:agenda.js" src="<%=Config.get("SITE_PATH")%>/admin/js/calendar/themes/Contrast/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<div style="height: 15px;">&nbsp;</div>