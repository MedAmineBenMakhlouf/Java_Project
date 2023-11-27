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
	<div class="container mt-5 mb-5">
		<div class=" row ">
			<div class="col-6 d-flex justify-content-between">
				<h2>
					<c:out value="${product.name }"></c:out>
				</h2>
				<a href="/dashboardUser">Go Back</a>
			</div>
			<div class="col-6 d-flex justify-content-end">
				You still have:
				<c:out value="${loggedUser.wallet.balance }"></c:out>
			</div>
		</div>
		<div class="row">
			<div class="row">
				<div class="col-12">
					<p>
						<strong>Product Name:</strong>
						<c:out value="${product.name }"></c:out>
					</p>
					<p>
						<strong>Description:</strong>
						<c:out value="${product.description }"></c:out>
					</p>
					<p>
						<strong>Start Biding:</strong>
						<fmt:formatDate value="${product.startTime }"
							pattern="dd MMMM yyyy" />
					</p>
					<p>
						<strong>End Biding:</strong>
						<fmt:formatDate value="${product.endTime }" pattern="dd MMMM yyyy" />
					</p>
					<p>
						<strong>Start Price:</strong>
						<c:out value="${product.price }"></c:out>
					</p>
					<c:if test="${lastParticipation == 0}">
						<p>NO BIDING YET</p>
					</c:if>
					<c:if test="${lastParticipation != 0}">
						<p>
							the biding is came to:
							<c:out value="${lastParticipation }"></c:out>
						</p>
					</c:if>
				</div>
			</div>
			<c:if test="${user_id != product.user.id}">
				<div class="row">
					<div class="col-6 justify-content-start">
						<c:if test="${comparison ==false }">
							<c:if test="${product.status==false}">
								<div>
									<form:form action="/product/${product.id }/addAuction"
										method="post" modelAttribute="participation">
										<form:input type="number" name="amount" min="0" step="0.001"
											path="amount" />
										<form:errors path="amount" class="text-danger" />
										<button class="btn btn-danger">Bid</button>
									</form:form>
								</div>
							</c:if>
						</c:if>

						<c:if test="${product.status==true }">
							<p>The biding wfé hahahahaha</p>

							<c:if test="${lastParticipator.buyer.id == user_id }">
								<p>Congrats! You win the bid</p>
							</c:if>

							<c:if test="${lastParticipator.buyer.id != user_id }">

								<c:out value="${lastParticipator.buyer.username}">
								</c:out> win the bid
												</c:if>
						</c:if>



					</div>
				</div>
			</c:if>
			<div class="row">
				<c:forEach items="${product.files }" var="file">
					<div class="col-6">
						<c:url var="imageUrl" value="/uploads/${file.path}" />
						<img src="${imageUrl}" alt="${file.path }" width="300px"
							height="300px" />
					</div>
				</c:forEach>
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
					<c:forEach items="${product.traceParticipations}"
						var="participation">
						<tr>
							<td><c:out value="${participation.buyer.username }"></c:out>
							</td>
							<td><c:out value="${participation.amount }"></c:out></td>
							<td><fmt:formatDate value="${participation.createdAt }"
									pattern="dd MMMM yyyy" /> at <fmt:formatDate
									value="${participation.createdAt }" pattern="HH:mm" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>