<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../fragments/head.jspf" %>
<link href="<spring:url value="/resources/css/managers-parlor.css" />" rel="stylesheet">
<title>Parlor</title>
</head>
<body>
<%@include file="../fragments/navbar.jspf" %>

<div class="container content-container">
    <h2><spring:message code="customersParlor.parlor"/></h2>
    <p>Here you can manage your personal information, create technical tasks, etc.</p>

    <ul class="nav nav-tabs nav-justified">
        <li class="active"><a data-toggle="tab" href="#manageNewProjects">Projects Requests</a></li>
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
        <%--Manage new Projects--%>
        <div id="manageNewProjects" class="tab-pane fade in active">
            <p class="text-center lead">Here you can grab new requests.</p>
            <div class="row">

                <%--Project Requests--%>
                <div id="projectRequestsAccordion" class="panel-group col-lg-8 col-lg-offset-2">
                    <core:choose>
                        <core:when test="${empty projectRequests}">
                            <div class="text-vertical-center col-lg-12">
                                <h3>No new Requests!</h3>
                            </div>
                        </core:when>
                        <core:otherwise>
                            <core:forEach items="${projectRequests}" var="project" varStatus="loop">
                                <div class="panel panel-default">
                                    <div class="project-heading panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#projectRequestsAccordion"
                                               href="#projectRequest${project.id}">${project.name}</a>
                                        </h4>
                                    </div>
                                    <core:set var="accordionCollapseClass" value="panel-collapse collapse"/>
                                    <core:if test="${loop.index == 0}">
                                        <core:set var="accordionCollapseClass" value="panel-collapse collapse in"/>
                                    </core:if>

                                        <%--Project Body--%>
                                    <div id="projectRequest${project.id}" class="${accordionCollapseClass}">
                                        <div class="panel-body">

                                            <div class="col-lg-12">
                                                <h4 class="page-header"><strong>Project Description</strong>
                                                </h4>
                                                <p class="description">${project.description}</p>
                                                <h3 class="text-center page-header"><strong>Tasks</strong></h3>

                                                    <%--Tasks--%>
                                                <div id="tasksAccordion${project.id}" class="panel-group">
                                                    <core:forEach items="${project.customerTasks}" var="task">
                                                        <div class="panel panel-default">
                                                            <div class="task-heading panel-heading">
                                                                <h4 class="panel-title">
                                                                    <a data-toggle="collapse"
                                                                       data-parent="#tasksAccordion${project.id}"
                                                                       href="#project${project.id}task${task.id}">${task.name}</a>
                                                                </h4>
                                                            </div>
                                                            <div id="project${project.id}task${task.id}"
                                                                 class="panel-collapse collapse">
                                                                <div class="panel-body">
                                                                    <div class="table-responsive">
                                                                        <table class="text-center table table-condensed">
                                                                            <thead>
                                                                            <tr>
                                                                                <th class="text-center">Specialization
                                                                                </th>
                                                                                <th class="text-center">Rank</th>
                                                                                <th class="text-center">Quantity</th>
                                                                            </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                            <core:forEach
                                                                                    items="${task.taskDevelopersRequests}"
                                                                                    var="taskRequest">
                                                                                <tr>
                                                                                    <td>${taskRequest.specialization}</td>
                                                                                    <td>${taskRequest.rank}</td>
                                                                                    <td>${taskRequest.quantity}</td>
                                                                                </tr>
                                                                            </core:forEach>
                                                                            </tbody>
                                                                        </table>
                                                                    </div>
                                                                    <hr>
                                                                    <h4>
                                                                        <strong>Task Description</strong></h4>
                                                                    <p class="description">${task.description}</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </core:forEach>
                                                </div>
                                                    <%--end Tasks--%>

                                                <button class="btn btn-success submit-project-btn pull-right"
                                                        name="grabProject" value="${project.id}" type="button">
                                                    Grab
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                        <%--end Project Body--%>

                                </div>
                            </core:forEach>
                        </core:otherwise>
                    </core:choose>
                </div>
                <%--end Project Requests--%>

            </div>
        </div>
        <%--.end Manage new Projects--%>

        <%--Form Projects--%>
        <div id="formProjects" class="tab-pane fade">
            <div class="row">
                <div class="col-lg-12">
                    <div class="col-lg-12">
                        <h3 class="page-header lead">Form New Project</h3>
                    </div>
                    <%--<div class="col-lg-3">

                    </div>--%>
                </div>

                <%--Projects--%>
                <div class="col-lg-9">
                    <div id="pendingProjectsAccordion" class="panel-group">
                        <core:choose>
                            <core:when test="${empty pendingProjects}">
                                <h3 class="text-center">No New Requests!</h3>
                            </core:when>
                            <core:otherwise>
                                <core:forEach items="${pendingProjects}" var="project" varStatus="loop">
                                    <div class="panel panel-default">
                                        <div class="project-heading panel-heading">
                                            <h4 class="panel-title">
                                                <a id="project${project.id}Link"
                                                   data-toggle="collapse"
                                                   data-parent="#pendingProjectsAccordion"
                                                   href="#pendingProject${project.id}">${project.name}</a>
                                            </h4>
                                        </div>
                                            <%--Project Body--%>
                                        <div id="pendingProject${project.id}" class="panel-collapse collapse">
                                            <div class="panel-body">

                                                <div class="col-lg-12">
                                                    <h3 class="text-center page-header"><strong>Tasks</strong>
                                                    </h3>

                                                        <%--Tasks--%>
                                                    <div id="newProjectTasksAccordion${project.id}"
                                                         class="panel-group">
                                                        <core:forEach items="${project.customerTasks}"
                                                                      var="task">
                                                            <div class="panel panel-default">
                                                                <div class="task-heading panel-heading">
                                                                    <h4 class="panel-title">
                                                                        <a id="project${project.id}Task${task.id}Link"
                                                                           data-toggle="collapse"
                                                                           data-parent="#newProjectTasksAccordion${project.id}"
                                                                           href="#activeProject${project.id}Task${task.id}">${task.name}</a>
                                                                    </h4>
                                                                </div>
                                                                <div id="activeProject${project.id}Task${task.id}"
                                                                     class="panel-collapse collapse">

                                                                        <%--Task Body--%>
                                                                    <div class="panel-body">
                                                                        <h4>
                                                                            <strong>Task Description</strong>
                                                                        </h4>
                                                                        <p class="description">${task.description}</p>

                                                                            <%--developers Request--%>
                                                                        <div class="col-lg-12">
                                                                            <h4 class="text-center">
                                                                                <strong>Requested
                                                                                    Developers
                                                                                </strong>
                                                                            </h4>
                                                                            <hr>
                                                                            <div class="table-responsive">
                                                                                <table id="project${project.id}Task${task.id}Requested"
                                                                                       class="text-center table table-condensed">
                                                                                    <thead>
                                                                                    <tr>
                                                                                        <th class="text-center">
                                                                                            Specialization
                                                                                        </th>
                                                                                        <th class="text-center">Rank
                                                                                        </th>
                                                                                        <th class="text-center">Quantity
                                                                                        </th>
                                                                                    </tr>
                                                                                    </thead>
                                                                                    <tbody><core:forEach
                                                                                            items="${task.taskDevelopersRequests}"
                                                                                            var="taskRequest">
                                                                                        <tr>
                                                                                            <td>${taskRequest.specialization}</td>
                                                                                            <td>${taskRequest.rank}</td>
                                                                                            <td>${taskRequest.quantity}</td>
                                                                                        </tr>
                                                                                    </core:forEach></tbody>
                                                                                </table>
                                                                            </div>
                                                                        </div>
                                                                            <%--end developers Request--%>

                                                                            <%--assigned Developers--%>
                                                                        <div class="col-lg-12">
                                                                            <h4 class="text-center">
                                                                                <strong>Assigned Developers</strong>
                                                                            </h4>
                                                                            <hr>
                                                                            <div class="table-responsive">
                                                                                <h5 class="text-center">
                                                                                    <strong>No developers
                                                                                        assigned for
                                                                                        this
                                                                                        task!</strong>
                                                                                </h5>
                                                                                <table id="project${project.id}Task${task.id}Hired"
                                                                                       class="table table-condensed no-display">
                                                                                    <thead>
                                                                                    <tr>
                                                                                        <th class="text-center">
                                                                                            Name
                                                                                        </th>
                                                                                        <th class="text-center">
                                                                                            Specialization
                                                                                        </th>
                                                                                        <th class="text-center">Rank
                                                                                        </th>
                                                                                        <th class="text-center"></th>
                                                                                    </tr>
                                                                                    </thead>
                                                                                    <tbody class="text-center"></tbody>
                                                                                </table>
                                                                            </div>
                                                                        </div>
                                                                            <%--end assigned Developers--%>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </core:forEach>
                                                    </div>
                                                        <%--end Tasks--%>

                                                    <h4 class="page-header"><strong>Project
                                                        Description</strong>
                                                    </h4>
                                                    <p class="description">${project.description}</p>

                                                        <%--Price--%>
                                                    <div class="col-lg-12 no-padding">
                                                        <label>Price:</label>
                                                        <div class="input-group w-200">
                                                            <input id="pendingProject${project.id}" type="number"
                                                                   class="form-control text-right"
                                                                   value="0">
                                                            <span class="input-group-addon">.00 $</span>
                                                        </div>
                                                    </div>

                                                        <%--Accept/Decline--%>
                                                    <div class="col-lg-12 no-padding mt-10">
                                                        <button type="button" name="declineProject"
                                                                value="${project.id}"
                                                                class="btn btn-danger w-200 mt-10">Decline
                                                        </button>
                                                        <button name="acceptProject"
                                                                value="${project.id}"
                                                                class="btn btn-success pull-right w-200 mt-10" disabled>
                                                            Accept
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                            <%--end Project Body--%>
                                    </div>
                                </core:forEach>
                            </core:otherwise>
                        </core:choose>
                    </div>
                </div>
                <%--end Projects--%>

                <%--Developers Modal button--%>
                <div class="col-lg-3">
                    <button type="button" class="btn btn-success pull-left mb-10"
                            data-toggle="modal"
                            data-target="#bindDeveloperModal">Search for Available Developers
                    </button>
                    <h4 class="dark-grey"><strong>Terms and Conditions:</strong></h4>
                    <p>
                        By clicking on "Register" you agree to The Company's' Terms and Conditions
                    </p>
                    <p>
                        While rare, prices are subject to change based on exchange rate fluctuations -
                        should such a fluctuation happen, we may request an additional payment. You have the option to
                        request a
                        full refund or to pay the new price. (Paragraph 13.5.8)
                    </p>
                </div>
                <%--end Developers Modal button--%>
            </div>
        </div>
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
                                        <td><fmt:formatDate value="${project.startDate}"
                                                            pattern="d MMMM, yyyy"/></td>
                                        <td><fmt:formatDate value="${project.endDate}"
                                                            pattern="d MMMM, yyyy"/></td>
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
                        <select id="activeProjectSelect"
                                class="form-control"
                                title="Active Project">
                            <core:forEach items="${pendingProjects}" var="project">
                                <option value="${project.id}">${project.name}</option>
                            </core:forEach>
                        </select>
                    </div>
                    <div class="col-lg-12">
                        <label>Select Task:</label>
                        <select id="activeTaskSelect"
                                class="form-control"
                                title="Active Task">
                            <core:set value="${pendingProjects[0]}" var="curProject"/>
                            <core:forEach items="${pendingProjects[0].customerTasks}" var="task">
                                <option value="project${curProject.id}Task${task.id}">${task.name}</option>
                            </core:forEach>
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
                        <th></th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
                <hr>
                <div class="row">
                    <div class="col-lg-4">
                        <label>Specialization:</label>
                        <select class="form-control"
                                title="specialization"
                                id="developerSpecialization">
                            <core:forEach
                                    items="${specializations}"
                                    var="specialization">
                                <option>${specialization}</option>
                            </core:forEach>
                        </select>
                    </div>
                    <div class="col-lg-4">
                        <label>Rank:</label>
                        <select class="form-control"
                                title="rank"
                                id="developerRank">
                            <core:forEach
                                    items="${ranks}"
                                    var="rank">
                                <option>${rank}</option>
                            </core:forEach>
                        </select>
                    </div>
                    <div class="col-lg-4">
                        <label>Last name:</label>
                        <div class="input-group">
                            <span class="input-group-addon">
                            <span class="glyphicon glyphicon-search"></span>
                            </span>
                            <input type="text"
                                   class="form-control"
                                   title="lastName"
                                   id="developerLastName">
                        </div>
                    </div>
                </div>
            </div>
            <%--end Modal body--%>

            <div class="modal-footer">
                <button type="button"
                        id="searchDevsBtn"
                        class="btn btn-primary">
                    Search
                </button>
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<%--end Modal--%>

<p id="bind" class="no-display">Bind to task</p>
<p id="unbind" class="no-display">Unbind</p>

<script src="<spring:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<script src="<spring:url value="/resources/js/managers-parlor.js" />"></script>
<script src="<spring:url value="/resources/js/logout.js" />"></script>
</body>
</html>
