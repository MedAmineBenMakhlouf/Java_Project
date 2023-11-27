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
		<a href="/showMyProduct">Go Back</a>
		<h1>
			Welcome
			<c:out value="${loggedUser.username }"></c:out>
			your balance is now
			<c:out value="${loggedUser.wallet.balance }"></c:out>
		</h1>

		<p>
			Category :
			<c:out value="${product.category }"></c:out>
		</p>
		<p>
			Product Name :
			<c:out value="${product.name }"></c:out>
		</p>

		<p>
			Description :
			<c:out value="${product.description }"></c:out>
		</p>

		<c:if test="${product.typeProduct==true }">
			<p>
				Type Auction :
				<c:if test="${product.typeProduct==true }"> For Auction</c:if>
				<c:if test="${product.typeProduct==false }">Not for Auction</c:if>
			</p>
			<p>
				Start Auction :
				<c:out value="${product.startTime }"></c:out>
			</p>
			<p>
				End Auction :
				<c:out value="${product.endTime }"></c:out>
			</p>
		</c:if>
		<p>
			Price:
			<c:out value="${product.price }"></c:out>
		</p>
		<c:if test="${lastParticipation !=0 }">

			<p>
				<strong>Bid is become</strong>
				<c:out value="${lastParticipation }"></c:out>
				<c:if test="${comparison==true }">
				and the biding is come to the end
				</c:if>
			</p>
		</c:if>
		<c:if test="${lastParticipator != null }">
			<form action="/sell/${lastParticipator.id}" method="post">
				<button>A LA TRE</button>
			</form>
		</c:if>
		<c:forEach items="${product.files }" var="file">
			<c:url var="imageUrl" value="/uploads/${file.path}" />
			<%-- 			<a href="/downloadFile/${file.path }">  --%>
			<img src="${imageUrl}" alt="User Image" width="100px" height="100px" />
			<!-- 			</a> -->
		</c:forEach>
		<p>

			<c:if test="${ product.user.id == user_id}">
				<c:if test="${empty product.participations }">
					<a href="/product/${product.id }/edit">Edit</a>
				</c:if>
			</c:if>
			<%-- 			<c:choose> --%>

			<%-- 				<c:when test="${empty product.participations}"> --%>
			<%-- 					<a href="/product/${product.id }/edit">Edit</a> --%>
			<%-- 				</c:when> --%>
			<%-- 				<c:otherwise> --%>
			<!-- 					<strong>Your product is on bid you can't edit it</strong> -->
			<%-- 				</c:otherwise> --%>

			<%-- 				<c:when test="${empty product.participations}"> --%>
			<%-- 					<a href="/product/${product.id }/edit">Edit</a> --%>
			<%-- 				</c:when> --%>
			<%-- 				<c:otherwise> --%>
			<!-- 					<strong>Your product is on bid you can't edit it</strong> -->
			<%-- 				</c:otherwise> --%>
			<%-- 			</c:choose> --%>
		</p>
	</div>
	<table class="table">
		<thead>
			<tr>
				<th>Proposed By</th>
				<th>Proposed price</th>
				<th>proposed at</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${product.traceParticipations}" var="participation">
				<tr>
					<td><c:out value="${participation.buyer.username }"></c:out></td>
					<td><c:out value="${participation.amount }"></c:out></td>
					<td><fmt:formatDate value="${participation.createdAt }"
							pattern="dd MMMM yyyy" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


</body>
</html>