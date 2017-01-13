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

    <ul id="navTab" class="nav nav-tabs nav-justified">
        <li class="active">
            <a data-toggle="tab" href="#createProject">
                <spring:message code="customersCabinet.createProject"/></a>
        </li>
        <li>
            <a data-toggle="tab" href="#runningProjects">
                <span class="badge"><c:out value="${empty runningProjects ? '' : runningProjects.size()}"/></span>
                <spring:message code="customersCabinet.runningProjects"/>
            </a>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <span class="badge"><c:out value="${empty newChecks ? '' : newChecks.size()}"/></span>
                <spring:message code="customersCabinet.checks"/> <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li>
                    <a href="#newChecks" role="tab" data-toggle="tab">
                        <spring:message code="customersCabinet.newChecks"/>
                        <c:if test="${not empty newChecks}">
                            <span class="glyphicon glyphicon-exclamation-sign"></span>
                        </c:if>
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
            <%@include file="fragments/customer/customer_create_project.jspf" %>
        </div>
        <%--end Create project--%>

        <%--Active projects--%>
        <div id="runningProjects" class="tab-pane fade">
            <%@include file="fragments/customer/customer_running_projects.jspf" %>
        </div>
        <%--Active Projects-End--%>

        <%--New Checks--%>
        <div id="newChecks" class="tab-pane">
            <%@include file="fragments/customer/customer_new_checks.jspf" %>
        </div>
        <%--.end New Checks--%>

        <%--Considered Checks--%>
        <div id="consideredChecks" class="tab-pane">
            <%@include file="fragments/customer/customer_considered_checks.jspf" %>
        </div>
        <%--.end Considered Checks--%>

        <%--Tehcnical Tasks History--%>
        <div id="technicalTasksHistory" class="tab-pane fade">
            <%@include file="fragments/customer/customer_technical_tasks.jspf" %>
        </div>
        <%--Tehcnical Tasks History-End--%>

        <%--Projects History--%>
        <div id="completeProjects" class="tab-pane fade">
            <%@include file="fragments/customer/customer_complete_projects.jspf" %>
        </div>
        <%-- Projects History-End--%>

        <%--Settings--%>
        <div id="settings" class="tab-pane">
            <%@include file="fragments/customer/customer_settings.jspf" %>
        </div>
        <%--.end Settings--%>

    </div>
    <%--.end Tab content--%>
</div>

<%--Modals--%>
<!-- Password modal -->
<div id="passwordModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><spring:message code="general.passwordRequired"/></h4>
            </div>
            <div class="modal-body">
                <input id="currentPassword" type="password" class="form-control"
                       placeholder="<spring:message code="general.currentPassword"/>">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">
                    <spring:message code="general.submit"/>
                </button>
            </div>
        </div>
    </div>
</div>

<%--Task modal--%>
<div id="addTaskModal" class="text-center modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="no-borders modal-header">

                <%--task name--%>
                <h4 class="modal-title">
                    <strong>
                        <spring:message code="entity.taskName"/>
                    </strong>
                </h4>
                <input id="taskName" class="form-control" type="text" maxlength="50"
                       placeholder="<spring:message code="entity.projectNamePlaceholder"/>">
            </div>

            <%--devs--%>
            <div class="no-borders modal-body">
                <h4 class="text-center">
                    <strong>
                        <spring:message code="entity.developers"/>
                    </strong>
                </h4>
                <hr>
                <%--devs table--%>
                <table class="table table-condensed table-responsive">
                    <thead>
                    <tr>
                        <th class="text-center">
                            <spring:message code="entity.specialization"/>
                        </th>
                        <th class="text-center">
                            <spring:message code="entity.rank"/>
                        </th>
                        <th class="text-center">
                            <spring:message code="entity.quantity"/>
                        </th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <select class="form-control" title="Specialization">
                                <c:forEach items="${specializations}" var="specialization">
                                    <option>
                                        <c:out value="${specialization}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select class="form-control" title="Rank">
                                <c:forEach items="${ranks}" var="rank">
                                    <option>
                                        <c:out value="${rank}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input class="form-control" type="number" min="1" max="100" value="1" placeholder="Max 100"
                                   title="Quantity">
                        </td>
                        <td class="w-100"></td>
                    </tr>
                    </tbody>
                </table>
                <button id="addDevButton" class="btn btn-default btn-block">
                    <span class="glyphicon glyphicon-plus"></span>
                </button>
            </div>
            <%--task description--%>
            <div class="no-borders modal-footer">
                <h4 class="text-center">
                    <strong>
                        <spring:message code="entity.taskDescription"/>
                    </strong>
                </h4>
                <textarea id="taskDescription" class="form-control" rows="6"
                          placeholder="<spring:message code="entity.projectDescriptionPlaceholder"/>"></textarea>
                <button id="taskBackButton" type="button" class="w-100 pull-left mt-10 btn btn-primary"
                        data-dismiss="modal">
                    <spring:message code="general.back"/>
                </button>
                <button id="addTaskButton" type="button" class="w-100 mt-10 btn btn-success" disabled>
                    <spring:message code="general.add"/>
                </button>
            </div>
        </div>
    </div>
</div>

<%@include file="fragments/errorsModal.jspf" %>

<%--Hidden task prototype--%>
<div id="taskPrototype" class="no-display panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-parent="#tasks" data-toggle="collapse"></a>
        </h4>
    </div>
    <div class="panel-collapse collapse">
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-condensed table-responsive">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="entity.specialization"/>
                        </th>
                        <th>
                            <spring:message code="entity.rank"/>
                        </th>
                        <th class="text-center">
                            <spring:message code="entity.quantity"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <p title="taskDescription" class="data-description"></p>
            <button type="button" class="mt-10 pull-left btn btn-danger btn-sm">
                <spring:message code="customersCabinet.dropTask"/>
            </button>
        </div>
    </div>
</div>

<p id="deleteButtonText" class="no-display"><spring:message code="general.delete"/></p>

<%@include file="fragments/footer.jspf" %>

<script src="<spring:url value="/resources/js/customers-cabinet.js" />"></script>
</body>
</html>