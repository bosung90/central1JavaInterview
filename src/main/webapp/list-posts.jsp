<%@ page import="com.central1.interview.Database" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
</head>
<body>

<div class="container">
	<div class="row">
		<h3>Posts</h3>
	</div>
	<div class="row">

		<table class="table table-striped table-hover">
			<thead>
			<tr>
				<th>Id</th>
				<th>User</th>
				<th>Message</th>
			</tr>
			</thead>
			<tbody>
			<%
				List<Database.Post> allPosts = Database.getInstance().getAllPosts();
				for ( Database.Post p : allPosts )
				{
			%>
			<tr>
				<td><%=p.getId()%></td>
				<td><%=p.getEmail()%></td>
				<td><%=p.getMessage()%></td>
			</tr>
			<%}%>
			</tbody>
		</table>
		<p><a href="/">Post Again</a></p>
	</div>
</div>

</body>
</html>
