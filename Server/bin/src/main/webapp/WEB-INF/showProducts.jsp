<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<!-- change to match your file/naming structure -->
</head>
<body>
	<div class="container">
		<h1>
			welcome
			<c:out value="${loggedUser.username }"></c:out>
		</h1>
		<div class="mt-4">
			<h2>Your auctions products</h2>
			<a href="/sellerDashboard">Go Back</a>
			<a href="product">Add Product</a>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Name</th>
						<th>description</th>
						<th>Start Auction</th>
						<th>End Auction</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${productsByUser }" var="product">
						<c:if test="${product.typeProduct==true }">
							<tr>
								<td><a href="/product/${product.id }/show"> <c:out
											value="${product.name }"></c:out></a></td>
								<td><c:out value="${product.description }"></c:out></td>
								<td><fmt:formatDate value="${product.startTime }"
										pattern="MMM dd, yyyy" /></td>
								<td><fmt:formatDate value="${product.endTime }"
										pattern="MMM dd, yyyy" /></td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="mt-5">
			<h2>Products Without auctions</h2>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Name</th>
						<th>Description</th>
						<th>price</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${productsByUser }" var="product">
						<c:if test="${product.typeProduct==false }">
							<tr>
								<td><a href="/product/${product.id }/show"> <c:out
											value="${product.name }"></c:out></a></td>
								<td><c:out value="${product.description }"></c:out></td>
								<td><c:out value="${product.price }"></c:out></td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>