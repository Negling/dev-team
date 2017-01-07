<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<%@include file="fragments/head.jspf" %>
<link href="<spring:url value="/resources/css/management.css" />" rel="stylesheet">
<title>Management</title>
</head>
<body>
<%@include file="fragments/navbar.jspf" %>

<div class="container content-container">
    <h2><spring:message code="customersCabinet.cabinet"/></h2>
    <p>Here you can manage your personal information, create technical tasks, etc.</p>

    <ul class="nav nav-tabs nav-justified">
        <li class="active"><a data-toggle="tab" href="#technicalTasks">Technical Tasks</a></li>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">My Projects <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#formProjects" role="tab" data-toggle="tab">Form Project</a></li>
                <li><a href="#completeProjects" role="tab" data-toggle="tab">Complete Projects</a></li>
            </ul>
        </li>
        <li><a data-toggle="tab" href="#users">Users</a></li>
        <li><a data-toggle="tab" href="#developers">Developers</a></li>
        <li><a data-toggle="tab" href="#managers">Managers</a></li>
        <li><a data-toggle="tab" href="#settings">Settings</a></li>
    </ul>

    <div class="tab-content">
        <%--Manage Tehnical --%>
        <%@include file="fragments/managment/manage_technical_tasks.jspf" %>
        <%--.end Manage new Projects--%>

        <%--Form Projects--%>
        <%@include file="fragments/managment/manage_form_project.jspf" %>
        <%--end Form Projects--%>

        <%--Complete Projects--%>
        <div id="completeProjects" class="tab-pane fade">
            <div class="row">
                <core:choose>
                    <core:when test="${empty completeProjects}">
                        <div class="text-vertical-center col-lg-12">
                            <h3>No Complete Projects!</h3>
                        </div>
                    </core:when>
                    <core:otherwise>
                        <h3 class="page-header lead">Complete Projects</h3>
                        <div class="table-responsive">
                            <table class="table table-condensed">
                                <thead>
                                <tr>
                                    <th>Project Name</th>
                                    <th>Start Date</th>
                                    <th>End Date</th>
                                    <th>Status</th>
                                    <th>Price</th>
                                </tr>
                                </thead>
                                <tbody>
                                <core:forEach items="${completeProjects }" var="project">
                                    <tr>
                                        <td><a href="#">${project.name}</a></td>
                                        <td><fmt:formatDate value="${project.startDate}" pattern="d MMMM, yyyy"/></td>
                                        <td><fmt:formatDate value="${project.endDate}" pattern="d MMMM, yyyy"/></td>
                                        <td>${project.status}</td>
                                        <td>$ ${project.price}</td>
                                    </tr>
                                </core:forEach>
                                </tbody>
                            </table>
                        </div>
                    </core:otherwise>
                </core:choose>
            </div>
        </div>
        <%--end Complete Projects--%>

        <%--Users--%>
        <div id="users" class="tab-pane fade">
            <div class="row">
                <%--Edit user--%>
                <div class="col-lg-12">
                    <h3 class="page-header lead">Manage Users</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi pharetra fringilla metus at
                        eleifend. Nunc elementum pellentesque elit. Phasellus vitae eros ac nibh aliquet tempor vitae
                        sodales orci. Fusce id efficitur velit. </p>
                    <div class="col-lg-6 no-padding">
                        <div class="input-group">
                            <span class="input-group-addon" id="search-addon">
                                <span class="glyphicon glyphicon-search">
                                </span>
                            </span>
                            <input type="text" class="form-control" placeholder="Search criteria" title="usersSearch"
                                   aria-describedby="search-addon">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <span class="glyphicon glyphicon-envelope">
                                    </span> By Email
                                </button>
                                <button class="btn btn-default" type="button">
                                    <span class="glyphicon glyphicon-phone-alt">
                                    </span> By Phone
                                </button>
                            </div>
                        </div>
                    </div>

                    <%--Users data--%>
                    <div class="col-lg-12">

                    </div>
                    <%--end Users data--%>

                </div>
                <%--end edit User--%>

            </div>
        </div>
        <%--end Users--%>

        <%--Developers--%>
        <div id="developers" class="tab-pane fade">

        </div>
        <%--end Developers--%>

        <%--Managers--%>
        <div id="managers" class="tab-pane fade">

        </div>
        <%--end Managers--%>

        <%--Settings--%>
        <div id="settings" class="tab-pane fade">

        </div>
        <%--end Settings--%>

    </div>
    <%--.end Tab content--%>

</div>

<p id="bind" class="no-display">Bind to task</p>
<p id="unbind" class="no-display">Unbind</p>

<script src="<spring:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<script src="<spring:url value="/resources/js/managers.js" />"></script>
<script src="<spring:url value="/resources/js/utils.js" />"></script>
</body>
</html>
