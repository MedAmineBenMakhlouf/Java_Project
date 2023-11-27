<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Product</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!-- Chart.js -->

<!-- change to match your file/naming structure -->
</head>
<body>

	<div class="ms-4 me-4">
		<div class="row">
			<div class="col-8 d-flex justify-content-start align-items-center gap-3">
				<div>
					<a href="/showMyProduct">My listing</a>
				</div>
				<div>
					<a href="/product">Add to your list</a>
				</div>
				<div>
					<a href="">My Sells</a>
				</div>
				<div>
					<a href="/dashboardUser">Back as buyer</a>
				</div>
				<div>
					<a href="/configWallet">config Wallet</a>
				</div>
				<div>
					<a href="/logout">logout</a>
				</div>
			</div>
			<div class="col-4 d-flex justify-content-end align-items-center gap-5">
			Your balance: <c:out value="${wallet.balance }"></c:out>
			</div>
		</div>
		<div class="row mt-5">
			<div class="col-6">
				<div class="row">
					<div class="col-6">
						<canvas id="myChart" width="20" height="20"></canvas>
					</div>
				</div>
			</div>
			<div class="col-6">
				<div class="row">
					<div class="col-6">
						<canvas id="mySecondChart" width="20" height="20"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		var ctx = document.getElementById('myChart').getContext('2d');
		var names = JSON.parse('${jsonNames}');
	    var prices = JSON.parse('${jsonPrices}');
		
		var myChart = new Chart(ctx,
				{
					type : 'pie', // Change this to 'line', 'pie', etc.
					data : {
						labels : names,
						datasets : [ {
							label : '# of sell',
							data : prices,
							backgroundColor : [ 'rgba(255, 99, 132, 0.2)',
									'rgba(54, 162, 235, 0.2)'],
							borderColor : [ 'rgba(255, 99, 132, 1)',
									'rgba(54, 162, 235, 1)'],
							borderWidth : 1
						} ]
					},
					options : {
						scales : {
							y : {
								beginAtZero : true
							}
						}
					}
				});
	</script>
	<script>
		var ctx = document.getElementById('mySecondChart').getContext('2d');
		var myChart = new Chart(ctx,
				{
					type : 'pie', // Change this to 'line', 'pie', etc.
					data : {
						labels : [ 'Red', 'Blue', 'Yellow', 'Green', 'Purple',
								'Orange' ],
						datasets : [ {
							label : '# of sell',
							data : [ 12, 19, 3, 5, 2, 3 ],
							backgroundColor : [ 'rgba(255, 99, 132, 0.2)',
									'rgba(54, 162, 235, 0.2)',
									'rgba(255, 206, 86, 0.2)',
									'rgba(75, 192, 192, 0.2)',
									'rgba(153, 102, 255, 0.2)',
									'rgba(255, 159, 64, 0.2)' ],
							borderColor : [ 'rgba(255, 99, 132, 1)',
									'rgba(54, 162, 235, 1)',
									'rgba(255, 206, 86, 1)',
									'rgba(75, 192, 192, 1)',
									'rgba(153, 102, 255, 1)',
									'rgba(255, 159, 64, 1)' ],
							borderWidth : 1
						} ]
					},
					options : {
						scales : {
							y : {
								beginAtZero : true
							}
						}
					}
				});
	</script>

</body>
</html>