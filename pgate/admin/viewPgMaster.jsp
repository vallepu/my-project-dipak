<%@ page language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="app.Config"%>
<% Config.setAllConfig(); %>
<jsp:include page="header.jsp" flush="true" />
<script type="text/javascript" src="<%=Config.get("SITE_PATH")%>/admin/js/jquery.js"></script>
<script type="text/javascript">
	var SITE_PATH = '<%=Config.get("SITE_PATH")%>';
	function changeStatus(val,stat){
		 
		if(stat == "yes"){
			actstatus = "Activate";
            
		}else if(stat == "no"){
			actstatus = "InActivate";
			
		}
		
		if(confirm("Are you sure you want to " + actstatus + " this?")){
			$.ajax({
			  url: SITE_PATH+"/admin/ViewPgMaster",
			  global: false,
			  type: "POST",
			  data: ({id : val, stat : stat}),
			  dataType: "html",
			  success: function(msg){
                 alert(actstatus + "d Successfully.");
                 window.location = SITE_PATH+'/admin/ViewPgMaster';
			}
		});
	  }else{
		 return false;
	  }
	}
</script>
<div id="body">
<table width="100%" cellspacing="2" cellpadding="0" border="0">
	<tbody>
	<tr>
		<td width="1%" valign="top" align="left"></td>
		<td width="19%" valign="top" align="left">

			<!--  left menu  -->	
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody><tr>
					<td valign="top" align="left">
					<!-- Left Side Start -->
						<jsp:include page="menu.jsp" flush="true"></jsp:include> 
					<!-- Left Side Ends --> 
					</td>
				</tr>
			</tbody></table>
			<!--  left menu  -->	

		</td>
		<td width="79%" valign="top" align="center">
 	<table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
 	<tr>
 		<td class="classTd" colspan="4" align="right">
 			<a href="<%=Config.get("SITE_PATH")%>/admin/PgMasterServlet">Add Pg Master</a>
 		</td>
 	</tr>
	<tr class="menuMain" style="height: 38px;">
		<td> PG Id </td>
		<td> PG Name </td>
        <td> Status </td>
		<td> Link </td>
	</tr>
<%
	if (request.getAttribute ("resultArray") != null) { 
		int shrows = 1;
		HashMap objField = null;
		ArrayList rows = new ArrayList (); 

		rows = (ArrayList)request.getAttribute ("resultArray");
		Iterator i = rows.iterator();
		objField = new HashMap ();
		while (i.hasNext()){
			objField.putAll((HashMap)i.next());
			//2010-10-11 14:58:33
		
		String pgmId = objField.get("pg_id").toString();
			
		String showRows = null;
		if(shrows%2 == 0){
			showRows = "evenrow";
		}else{
			showRows = "oddrow";
		}
	%> 
		<form action="" method="post" id="frm" name="frm">
			<tr style="color: black;" class="<%= showRows  %>">
				<td> <%= objField.get("pg_id")%> </td>
				<td> <%= objField.get("pg_name")%> </td>
				<td  id="act_status_<%=pgmId %>"> 
                    <% 
                        String viewActive = objField.get("is_active").toString(); 
                        if(viewActive.equalsIgnoreCase("no")){
                    %>
                    InActive
                    <!--<a href="javascript:void();" onclick="changeStatus(<%=pgmId %>,'yes')">Activate</a>-->
                    <%  }else if(viewActive.equalsIgnoreCase("yes")){  %>
                    <!--<a href="javascript:void();" onclick="changeStatus(<%=pgmId %>,'no')">InActivate</a>-->
                    Active
                    <%  }  %> 
                </td>
				<td> 
					<a href="<%=Config.get("SITE_PATH")%>/admin/PgMasterServlet?pgmId=<%=pgmId %>" >
                        <img src="<%=Config.get("SITE_PATH")%>/admin/images/icon_edit.gif" border="0" alt="Edit" />
                    </a>
				</td>
			</tr>
	</form>
	<%
	shrows++;	}
		}else{
	%>

	<tr>
		<td colspan="8"> No Records Found! </td>
	</tr>
<%
	}
%>
</table>

</table>
  	<br>
  	<!--<center>
	<a href="#" class="current">1</a> &nbsp;<span class="paginate">Page:&nbsp;</span><select onchange="window.location='/corporatetravel/index.php?page='+this[this.selectedIndex].value+'&amp;ipp=10&amp;link=list-tr';return false" class="paginate"><option selected="" value="1">1</option>
	</select>
	 <span class="paginate">Items per page:&nbsp;</span><select onchange="window.location='/corporatetravel/index.php?page=1&amp;ipp='+this[this.selectedIndex].value+'&amp;link=list-tr';return false" class="paginate"><option value="All">All</option>
	<option value="10" selected="selected">10</option>
	<option value="20">20</option>
	<option value="30">30</option>
	<option value="50">50</option>
	</select>
	</center>-->
	<br>
							
</td>
</tr>
</tbody></table>




<jsp:include page="footer.jsp" flush="true" />