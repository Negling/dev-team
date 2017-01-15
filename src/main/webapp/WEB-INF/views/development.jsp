<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="fragments/head.jspf" %>
    <link href="<spring:url value="/resources/css/developers.css" />" rel="stylesheet">
    <title>Development</title>
</head>
<body>
<%@include file="fragments/navbar.jspf" %>

<div class="container content-container">
    <h2><spring:message code="customersCabinet.cabinet"/></h2>
    <p><spring:message code="customersCabinet.description"/></p>

    <ul class="nav nav-tabs nav-justified">
        <li class="active"><a data-toggle="tab" href="#taskUnderProgress">Active Task</a></li>
        <li><a data-toggle="tab" href="#history"><spring:message code="customersCabinet.history"/></a></li>
        <li><a data-toggle="tab" href="#settings"><spring:message code="customersCabinet.settings"/></a></li>
    </ul>

    <div class="tab-content">

        <%--Task Under Progress--%>
        <div id="taskUnderProgress" class="tab-pane fade in active">
            <%@include file="fragments/development/development_active_task.jsp" %>
        </div>
        <%--.end Task Under Progress--%>

        <%--History--%>
        <div id="history" class="tab-pane fade">
            <%@include file="fragments/development/development_tasks_history.jsp" %>
        </div>
        <%--History-End--%>

        <%--Settings--%>
        <div id="settings" class="tab-pane">
            <%@include file="fragments/development/development_settings.jsp" %>
        </div>
        <%--.end Settings--%>

    </div>
    <%--.end Tab content--%>
</div>

<%@include file="fragments/errorsModal.jspf" %>

<script src="<spring:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
<script src="<spring:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<spring:url value="/resources/js/utils.js" />"></script>
<script src="<spring:url value="/resources/js/developers.js" />"></script>
</body>
</html>
