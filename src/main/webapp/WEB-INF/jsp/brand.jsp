<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <meta http-equiv="refresh" content="300"> -->
<title>Brands Inventory Summary</title>

<link rel="stylesheet" type="text/css" href="webjars/jquery-ui/1.11.1/jquery-ui.theme.css">
<link rel="stylesheet" type="text/css" href="webjars/datatables/1.10.12/css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css" href="webjars/datatables.net-dt/1.10.12/css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css" href="css/footer.css">
<link rel="stylesheet" type="text/css" href="css/tabs.css">
<link rel="stylesheet" type="text/css" href="css/table.css">
<script type="text/javascript" src="webjars/jquery/1.11.3/jquery.js"></script>
<script type="text/javascript" src="webjars/jquery-ui/1.11.1/jquery-ui.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="webjars/datatables.net/1.10.12/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/commons.js"></script>
<script type="text/javascript" src="js/brand.js"></script>
</head>
<body>
	<div class="content">
		<div style="height: 30px">
			<div style="font-size: 12px; float: left; width: 75%">
				<br>
			</div>
		</div>
		<table id="brands" class="display"
			style="border-collapse: collapse; border-spacing: 0; width: 100%">
			<thead>
				<tr>
					<th><input name="select_all" value="1" type="checkbox"></th>
					<th>Brand Id</th>
					<th>Brand Name</th>
					<th>Quantity</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th></th>
					<th>Brand Id</th>
					<th>Brand Name</th>
					<th>Quantity</th>
				</tr>
			</tfoot>
		</table>
	</div>
</body>
</html>