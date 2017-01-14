<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="fragments/head.jspf" %>
    <link href="<spring:url value="/resources/css/customers-cabinet.css" />" rel="stylesheet">
    <title><spring:message code="customersCabinet.cabinet"/></title>
</head>
<body>
<%@include file="fragments/navbar.jspf" %>

<div class="container content-container">
    <h2><spring:message code="customersCabinet.cabinet"/></h2>
    <p><spring:message code="customersCabinet.description"/></p>

    <ul class="nav nav-tabs nav-justified">
        <li class="active">
            <a data-toggle="tab" href="#createProject">
                <spring:message code="customersCabinet.createProject"/></a>
        </li>
        <li>
            <a data-toggle="tab" href="#runningProjects">
                <spring:message code="customersCabinet.runningProjects"/>
            </a>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <spring:message code="customersCabinet.checks"/> <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li>
                    <a href="#newChecks" role="tab" data-toggle="tab">
                        <spring:message code="customersCabinet.newChecks"/>
                    </a>
                </li>
                <li><a href="#consideredChecks" role="tab" data-toggle="tab">
                    <spring:message code="customersCabinet.considered"/></a>
                </li>
            </ul>
        </li>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <spring:message code="customersCabinet.history"/> <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li>
                    <a href="#technicalTasksHistory" role="tab" data-toggle="tab">
                        <spring:message code="customer.technicalTasks"/>
                    </a>
                </li>
                <li>
                    <a href="#completeProjects" role="tab" data-toggle="tab">
                        <spring:message code="customer.Projects"/>
                    </a>
                </li>
            </ul>
        </li>
        <li><a data-toggle="tab" href="#settings"><spring:message code="customersCabinet.settings"/></a></li>
    </ul>

    <div class="tab-content">

        <%--Create project--%>
        <div id="createProject" class="tab-pane fade in active">
            <%@include file="fragments/customer/customer_create_project.jsp" %>
        </div>
        <%--end Create project--%>

        <%--Active projects--%>
        <div id="runningProjects" class="tab-pane fade">
            <%@include file="fragments/customer/customer_running_projects.jsp" %>
        </div>
        <%--Active Projects-End--%>

        <%--New Checks--%>
        <div id="newChecks" class="tab-pane">
            <%@include file="fragments/customer/customer_new_checks.jsp" %>
        </div>
        <%--.end New Checks--%>

        <%--Considered Checks--%>
        <div id="consideredChecks" class="tab-pane">
            <%@include file="fragments/customer/customer_considered_checks.jsp" %>
        </div>
        <%--.end Considered Checks--%>

        <%--Tehcnical Tasks History--%>
        <div id="technicalTasksHistory" class="tab-pane fade">
            <%@include file="fragments/customer/customer_technical_tasks.jsp" %>
        </div>
        <%--Tehcnical Tasks History-End--%>

        <%--Projects History--%>
        <div id="completeProjects" class="tab-pane fade">
            <%@include file="fragments/customer/customer_complete_projects.jsp" %>
        </div>
        <%-- Projects History-End--%>

        <%--Settings--%>
        <div id="settings" class="tab-pane">
            <%@include file="fragments/customer/customer_settings.jsp" %>
        </div>
        <%--.end Settings--%>

    </div>
    <%--.end Tab content--%>
</div>

<%@include file="fragments/errorsModal.jspf" %>

<p id="deleteButtonText" class="no-display"><spring:message code="general.delete"/></p>

<%@include file="fragments/footer.jspf" %>

<script src="<spring:url value="/resources/js/customers-cabinet.js" />"></script>
</body>
</html>