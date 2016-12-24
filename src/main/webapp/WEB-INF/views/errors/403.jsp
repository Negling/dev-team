<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../fragments/head.jspf" %>
<link href="<spring:url value="/resources/css/error.css" />" rel="stylesheet">
<title><spring:message code="errorPage.error"/></title>
</head>
<body>
<%@include file="../fragments/navbar.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-lg-12 text-center">
            <h2><spring:message code="errorPage.noAccess"/></h2>
            <img class="error-image" src="<spring:url value="/resources/images/errors/policeman.jpg"/>">
        </div>
    </div>
</div>

<%@include file="../fragments/footer.jspf" %>
</body>
</html>
