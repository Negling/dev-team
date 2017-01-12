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

    <ul id="navTab" class="nav nav-tabs nav-justified">
        <li class="active"><a data-toggle="tab" href="#technicalTasks">
            <span class="badge"><c:out value="${empty technicalTasks ? '' : technicalTasks.size()}"/></span>
            Technical Tasks
        </a>
        </li>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <span class="badge"><c:out value="${empty pendingProjects ? '' : pendingProjects.size()}"/></span>
                Projects <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li>
                    <a href="#formProjects" role="tab" data-toggle="tab">
                        Form Project
                        <c:if test="${not empty pendingProjects}">
                            <span class="glyphicon glyphicon-exclamation-sign"></span>
                        </c:if>
                    </a>
                </li>
                <li>
                    <a href="#runningProjects" role="tab" data-toggle="tab">
                        Running Project
                    </a>
                </li>
                <li><a href="#completeProjects" role="tab" data-toggle="tab">Complete Projects</a></li>
            </ul>
        </li>
        <li><a data-toggle="tab" href="#users">Users</a></li>
        <security:authorize access="hasAnyAuthority('Ultramanager', 'Admin')">
            <li><a data-toggle="tab" href="#developers">
                Developers
            </a></li>
        </security:authorize>
        <security:authorize access="hasAuthority('Admin')">
            <li><a data-toggle="tab" href="#managers">
                Managers
            </a></li>
        </security:authorize>
        <li><a data-toggle="tab" href="#settings">
            Settings
        </a></li>
    </ul>

    <div class="tab-content">
        <%--Manage Tehnical --%>
        <div id="technicalTasks" class="tab-pane fade in active">
            <%@include file="fragments/managment/manage_technical_tasks.jspf" %>
        </div>
        <%--.end Manage new Projects--%>

        <%--Form Projects--%>
        <div id="formProjects" class="tab-pane fade">
            <%@include file="fragments/managment/manage_form_project.jspf" %>
        </div>
        <%--end Form Projects--%>

        <%--Running Projects--%>
        <div id="runningProjects" class="tab-pane fade">
        </div>
        <%--end Running Projects--%>

        <%--Complete Projects--%>
        <div id="completeProjects" class="tab-pane fade">
            <%@include file="fragments/managment/manage_complete_projects.jspf" %>
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
        <security:authorize access="hasAnyAuthority('Ultramanager', 'Admin')">
            <div id="developers" class="tab-pane fade">

            </div>
        </security:authorize>
        <%--end Developers--%>

        <%--Managers--%>
        <security:authorize access="hasAuthority('Admin')">
            <div id="managers" class="tab-pane fade">

            </div>
        </security:authorize>
        <%--end Managers--%>

        <%--Settings--%>
        <div id="settings" class="tab-pane fade">

        </div>
        <%--end Settings--%>

    </div>
    <%--.end Tab content--%>

</div>

<!-- Decline Technical Task Modal -->
<div class="modal fade" id="declineModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Reason:</h4>
            </div>
            <div class="modal-body">
                <textarea id="declineManagerCommentary" class="form-control" placeholder="Can be empty."
                          rows="6"></textarea>
                <p id="declineId" class="no-display"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
                <button id="declineButton" type="button" class="btn btn-success pull-right"
                        data-dismiss="modal">Continue
                </button>
            </div>
        </div>

    </div>
</div>

<!-- Bind Developer to Task Modal -->
<div class="modal fade" id="bindDeveloperModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Developers Binding Settings: </h4>
                <hr>
                <div class="row">
                    <div class="col-lg-12">
                        <label>Select Project:</label>
                        <select id="activeProjectSelect" class="form-control" title="Active Project">
                            <c:forEach items="${pendingProjects}" var="project" varStatus="status">
                                <option value="<c:out value="${project.id}"/>">
                                    <c:out value="${project.name}"/>
                                </option>
                                <c:if test="${status.last}">
                                    <c:set var="projectTasks" value="${project.tasks}"/>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-lg-12">
                        <label>Select Task:</label>
                        <select id="activeTaskSelect" class="form-control" title="Active Task">
                            <c:forEach items="${projectTasks}" var="task">
                                <option value="<c:out value="${task.id}"/>">
                                    <c:out value="${task.name}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <%--Modal body--%>
            <div class="modal-body">
                <h4>Search results:</h4>
                <h3 id="noResults" class="text-center">No results found!</h3>
                <table id="devsResultTable" class="table no-display">
                    <thead>
                    <tr>
                        <th>First Name and Last Name</th>
                        <th>Cost</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
                <hr>
                <div class="row">
                    <div class="col-lg-4">
                        <label>Specialization:</label>
                        <select class="form-control" title="specialization" id="developerSpecialization">
                            <c:forEach items="${specializations}" var="specialization">
                                <option><c:out value="${specialization}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-lg-4">
                        <label>Rank:</label>
                        <select class="form-control" title="rank" id="developerRank">
                            <c:forEach items="${ranks}" var="rank">
                                <option><c:out value="${rank}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-lg-4">
                        <label>Last name:</label>
                        <div class="input-group">
                            <span class="input-group-addon">
                            <span class="glyphicon glyphicon-search"></span>
                            </span>
                            <input type="text" class="form-control" title="lastName" id="developerLastName">
                        </div>
                    </div>
                </div>
            </div>
            <%--end Modal body--%>

            <div class="modal-footer">
                <button type="button" id="searchDevsBtn" class="btn btn-primary">
                    Search
                </button>
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<%--end Modal--%>

<%@include file="fragments/errorsModal.jspf" %>

<p id="bind" class="no-display">Bind to task</p>
<p id="unbind" class="no-display">Unbind</p>

<script src="<spring:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
<script src="<spring:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<spring:url value="/resources/js/utils.js" />"></script>
<script src="<spring:url value="/resources/js/managers.js" />"></script>
</body>
</html>
