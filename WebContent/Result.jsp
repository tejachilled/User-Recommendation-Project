<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type='text/css'>
pre {
	display: inline;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mobile Recommendation</title>
</head>
<script type="text/javascript">
var option2,option1,finaloption,finaloption2;
function display() {
	
	 option2 = document.getElementById('myoption').innerHTML;
	
	 option1 ='<%=session.getAttribute("youroption")%>';
	
		//alert('option1: ' + option1);
		//alert(' : option2: ' + option2);
		if (option2 != "") {
			finaloption = option2;
			displayTable();
		} else {
			finaloption = option1;
			displayTable();
		}
	}

	function displayTable() {
		//alert(' finaloption: ' + finaloption);
		if (finaloption == "Questions") {
			finaloption = 1;
			finaloption2 = 2;
			
		} else if (finaloption == "Answers") {
			finaloption = 0;
			finaloption2 = 2;
		}
		//alert(' finaloption: ' + finaloption);
		 var tbl = document.getElementById("tblMain");
		// alert('table: '+tbl);
		// alert('table.rows.length: '+tbl.rows.length);
		// alert('table.rows[i].cells.length: '+tbl.rows[0].cells.length);
         if (tbl != null) {
           
             for (var i = 0; i < tbl.rows.length; i++) {
                 for (var j = 0; j < tbl.rows[i].cells.length; j++) {
                	// alert(j);
                     tbl.rows[i].cells[j].style.display = "";
                     if (j == finaloption)
                         tbl.rows[i].cells[j].style.display = "none";
                     if (j == finaloption2)
                         tbl.rows[i].cells[j].style.display = "none";
                 }
             }
         }
	}
</script>
<body onload="display();">
	<form id="newForm" name="newForm">
		<%
			String youroption = (String) request.getAttribute("youroption");
			if (youroption != null) {
				session.setAttribute("youroption", youroption);
			}
		%>

		<h4>
			You selected: <span id="myoption"><s:property
					value="youroption" /></span>

		</h4>
		<br />
		<s:url id="fileDownload" namespace="/" action="download"></s:url>
		<h4>
			Download file -
			<s:a href="%{fileDownload}">Click here</s:a>
		</h4>
		<br />
		<%
			int counter = 1;
		%>
		<table border="1" align="center" id="tblMain">
			<tr>
				<td align="center">Questions</td>
				<td align="center">Answers</td>
				<td align="center">Questions and Answers</td>
				<td align="center">Title</td>
			</tr>
			<s:iterator value="titles">
				<tr>
					<td width="10%" id="q"><a
						href="MainContent.jsp?uid=<%=counter - 1%>&item=Q"> Question <%=counter%>
					</a></td>
					<td width="10%" id="a"><a
						href="MainContent.jsp?uid=<%=counter - 1%>&item=A"> Answers </a></td>
					<td width="20%"><a
						href="MainContent.jsp?uid=<%=counter - 1%>&item=QA"> Question +
							Answers </a></td>
					<td width="50%"><s:property /></td>
				</tr>
				<%
					counter++;
				%>
			</s:iterator>
		</table>

	</form>
</body>
</html>