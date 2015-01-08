<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Mobile recommendation</title>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>

<style>
    .datepicker{
     
    }
  </style>
 
  <script>
  $(function() {
    $( ".datepicker" ).datepicker();
  });
  </script>


</head>
<body>
	<form action="GetInfo.action" method="post">
		<h4>
			Hi
			<s:property value="names" />
			<br />
		</h4>
		<h4>Please select your choice from below</h4>
		<div align="center" style="max-width: 380px; border: 2px solid #ccc;">
			<table>
				<tr>
					<td><s:checkboxlist name="youroption" list="options" /></td>
				</tr>
			</table>
		</div>
		<br />	
		From Date:  <input type="text" class="datepicker" name ="fDate"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		To Date:  <input type="text" class="datepicker" name ="tDate"/>
		<br/>
		<br/>
		<br/>
		<div style="left: 30px; width: 1240px; height: 50px;">
			<input type="submit" value="submit" />
		</div>
	</form>
</body>
</html>