<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mobile Recommendation</title>

<script type="text/javascript">
	function display() {

		var qa = document.getElementById('item').value;
		
		if (qa == 'QA') {
			var count = 0;
			var res = document.getElementById('cou').value;
			count = res;
			var temp = 0;
			for (var i = 0; i < count - 1; i++) {
				var mydiv = document.getElementById("myDiv");
				var space = document.createTextNode(' ');
				var button = document.createElement('input');
				button.type = 'button';
				temp = (i + 1);
				button.value = 'Answer' + temp;
				button.id = temp;
				button.onclick = function() {
					focus(this.id);
				};
				mydiv.appendChild(button);
				mydiv.appendChild(space);
			}
		}
	}

	function focus(id) {
		var move = '#output' + id + '';
		//alert(move);
		window.location.hash = move;

		setBgColorById(move, teal);
		setColorById(move, lime);

	}
</script>
</head>
<body onload="display();">

	<s:form action="Trim">
		<%
			String hidden = (String) request.getParameter("uid");
				if (hidden != null && !hidden.equalsIgnoreCase("null")) {
					session.setAttribute("uid", hidden);
				}
				String item = (String) request.getParameter("item");
				if (item != null) {
					session.setAttribute("item", item);
				}
		%>

		<s:hidden name="count" value="%{#session.uid}"></s:hidden>
		<s:hidden name="item" id="item" value="%{#session.item}"></s:hidden>
		<table align="center">
			<tr>
				<td><s:submit type="submit" value="Content+Code" href="%{url}"
						name="submit" id="test" /></td>
			</tr>
			<tr>
				<td><s:submit type="submit" value="Content" href="%{url}"
						name="submit" /></td>
			</tr>
			<tr>
				<td><s:submit type="submit" value="Code" href="%{url}"
						name="submit" /></td>
			</tr>
			<tr>
				<td><s:url id="resultPage" namespace="/" action="GetInfo"></s:url>
					<s:a href="%{resultPage}">Go Back</s:a></td>
			</tr>
		</table>
		<s:url id="url" namespace="/" value="/Trim" />
		<br />
		<s:iterator value="figureCount">
			<li><pre
					style="white-space: pre-wrap; width: 75%; border: 1px solid lightgrey; background: ivory; color: red;">
		<s:property />
				</pre></li>
		</s:iterator>
		<br />
		<div id="myDiv"></div>
		<%
			int count = 0;
		%>
		<s:iterator value="display">
			<li><pre
					style="white-space: pre-wrap; width: 75%; border: 1px solid lightgrey; background: ivory; color: blue;">
		<span id="output<%=count%>"><s:property /></span>
		<span id="count"><s:hidden id="cou"
							value="%{getDisplay().size()}" /></span>
				</pre></li>
			<%
				count++;
			%>
		</s:iterator>

	</s:form>
</body>
</html>