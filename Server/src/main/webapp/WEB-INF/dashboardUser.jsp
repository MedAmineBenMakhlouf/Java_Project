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
	<div class="container mt-3">
		<c:if test="${loggedUser.file_path !=null }">
			<c:url var="imageUrl" value="/uploads/${loggedUser.file_path}" />
			<img src="${imageUrl}" alt="User Image" width="100px" height="100px" />
		</c:if>
		<c:if test="${loggedUser.file_path ==null }">
			<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
				fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
  <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
  <path fill-rule="evenodd"
					d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z" />
</svg>
		</c:if>
		<c:out value="${loggedUser.username }"></c:out>
		<div class="d-flex justify-content-end align-items-center gap-5">
			Your balance:
			<c:out value="${loggedUser.wallet.balance }"></c:out>
		</div>
		<div class="row ">
			<div class="col-6 d-flex justify-content-start gap-3">
				<a href="/sellerDashboard">Seller dashboard</a> <a href="/logout">logout</a>
				<a href="/user/${loggedUser.id }/edit">Edit Profile</a>
			</div>
		</div>
		<div class="mt-4">

			<h2>All Products</h2>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Name</th>
						<th>Start Auction</th>
						<th>End Auction</th>
						<th>Sell with Auction</th>
						<th>Posted By</th>
						<th>participate to the auction</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${allProducts }" var="product">
						<tr>

							<td><a href="/product/${product.id }/show"> <c:out
										value="${product.name }"></c:out></a></td>

							<td><fmt:formatDate value="${product.startTime }"
									pattern="dd MMM, yyyy" /></td>
							<td><fmt:formatDate value="${product.endTime }"
									pattern="dd MMM, yyyy" /></td>
							<c:if test="${product.typeProduct==true}">
								<td>For Auction</td>

							</c:if>
							<c:if test="${product.typeProduct==false}">
								<td>Not for Auction</td>
							</c:if>
							
							<c:if test="${product.user.id!=user_id}">
								<td><a href="/user/${product.user.id}/show"> <c:out
											value="${product.user.username }"></c:out></a></td>
							</c:if>
							
							
							<c:if test="${product.user.id==user_id}">
								<td>Your product</td>
							</c:if>
							<c:if test="${product.user.id!=user_id && product.typeProduct==true && wallet.balance>0 && product.status == false}">

								<td><a href="/product/${product.id}/participate">
										Participate</a></td>
							</c:if>
							<c:if test="${product.user.id!=user_id && product.typeProduct==false && wallet.balance>0 && product.status == false}">

								<td>
								<form action="/product/${product.id}/buy" method="post">
								
							 	<button>Buy</button>
								</form>
							</c:if>

							<c:if test="${wallet.balance==0 }">
								<td>Your wallet is empty</td>
							</c:if>
							<c:if test="${product.status==true }">
								<td>sold</td>
							</c:if>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>