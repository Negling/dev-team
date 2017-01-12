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
        <li><a data-toggle="tab" href="#activeProjects"><spring:message code="customersCabinet.activeProjects"/></a>
        </li>
        <li><a data-toggle="tab" href="#history"><spring:message code="customersCabinet.history"/></a></li>
        <li><a data-toggle="tab" href="#settings"><spring:message code="customersCabinet.settings"/></a></li>
    </ul>

    <div class="tab-content">

        <div id="createProject" class="tab-pane fade in active">
            <div class="row">
                <div id="formTechnicalTask" class="bordered-container col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                    <h3 class="text-center page-header">
                        <strong>
                            <spring:message code="customersCabinet.projectData"/>
                        </strong>
                    </h3>

                    <%--technical task name--%>
                    <div class="form-group">
                        <label class="control-label" for="technicalTaskName">
                            <spring:message code="entity.projectName"/>:
                        </label>

                        <input id="technicalTaskName" name="projectName" type="text" class="form-control"
                               placeholder="<spring:message code="entity.projectNamePlaceholder"/>">
                    </div>
                    <%--technical task description--%>
                    <div class="form-group">
                        <label class="control-label" for="technicalTaskDescription">
                            <spring:message code="entity.projectDescription"/>:
                        </label>
                        <textarea id="technicalTaskDescription" name="projectDescription" class="form-control" rows="3"
                                  placeholder="<spring:message code="entity.projectDescriptionPlaceholder"/>"></textarea>
                    </div>

                    <%--Tasks--%>
                    <h3 class="text-center page-header">
                        <strong><spring:message code="entity.tasks"/></strong>
                    </h3>
                    <div id="tasks" class="panel-group">
                    </div>
                    <div class="text-center">
                        <button class="btn btn-primary btn-sm add-task-btn" type="button" data-toggle="modal"
                                data-target="#addTaskModal">
                            <spring:message code="customersCabinet.addTask"/>
                        </button>
                    </div>
                    <button id="submitTechnicalTask" class="btn btn-success submit-project-btn" type="submit">
                        <spring:message code="general.submit"/>
                    </button>
                </div>
            </div>
        </div>

        <%--New Checks--%>
        <div id="newChecks" class="tab-pane">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header lead">New Checks
                        <button name="refresh" data-container-id="#newChecksAccordion" type="button"
                                class="refresh-button pull-right">
                            <span class="glyphicon glyphicon-refresh"></span>
                        </button>
                    </h3>
                </div>

                <div id="newChecksAccordion" class="panel-group col-lg-6 col-lg-offset-3 col-md-8 col-md-offset-2">
                    <c:choose>
                        <c:when test="${empty newChecks}">
                            <h3 class="text-center">No New Checks!</h3>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${newChecks}" var="newCheck" varStatus="loop">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#newChecksAccordion"
                                               href="#check${newCheck.projectId}">
                                                <c:out value="${newCheck.projectName}"/>
                                            </a>
                                        </h4>
                                    </div>

                                        <%--Check Body--%>
                                    <div id="check${newCheck.projectId}"
                                         class="panel-collapse collapse${loop.index == 0 ? ' in' : ''}">
                                        <div class="panel-body">

                                                <%--devs hire cost--%>
                                            <div class="data-description check-description col-xs-12"
                                                 data-toggle="tooltip" data-placement="top" data-delay="0"
                                                 title="<spring:message code="entity.checkDevHireCost"/>">
                                                <p class="pull-left">
                                                    Developers cost:
                                                </p>
                                                <p class="pull-right">
                                                    <c:out value="${newCheck.developersCost}"/> $
                                                </p>
                                            </div>

                                                <%--services cost--%>
                                            <div class="data-description check-description col-xs-12"
                                                 data-toggle="tooltip" data-placement="top" data-delay="0"
                                                 title="<spring:message code="entity.checkServicesCost"/>">
                                                <p class="pull-left">
                                                    Services cost:
                                                </p>
                                                <p class="pull-right">
                                                    <c:out value="${newCheck.servicesCost}"/> $
                                                </p>
                                            </div>

                                                <%--taxes--%>
                                            <div class="data-description check-description col-xs-12"
                                                 data-toggle="tooltip" data-placement="top" data-delay="0"
                                                 title="<spring:message code="entity.checkTaxesCost"/>">
                                                <p class="pull-left">
                                                    Taxes cost:
                                                </p>
                                                <p class="pull-right">
                                                    <c:out value="${newCheck.taxes}"/> $
                                                </p>
                                            </div>

                                                <%--total project cost--%>
                                            <div class="data-description check-description col-xs-12"
                                                 data-toggle="tooltip" data-placement="top" data-delay="0"
                                                 title="<spring:message code="entity.checkTotalCost"/>">
                                                <p class="pull-left">
                                                    Total:
                                                </p>
                                                <p class="pull-right">
                                                    <c:out value="${newCheck.totalProjectCost}"/> $
                                                </p>
                                            </div>

                                            <button name="declineCheckButton" class="btn btn-danger pull-left"
                                                    type="button" value="${newCheck.projectId}">
                                                Decline
                                            </button>
                                            <button name="confirmCheckButton" class="btn btn-success pull-right"
                                                    value="${newCheck.projectId}" type="button">
                                                Confirm Payment
                                            </button>
                                        </div>
                                    </div>
                                        <%--end Check Body--%>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <%--.end New Checks--%>

        <%--Considered Checks--%>
        <div id="consideredChecks" class="tab-pane">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header lead">Checks History
                        <button name="refresh" data-container-id="#completeChecksTable" type="button"
                                class="refresh-button pull-right">
                            <span class="glyphicon glyphicon-refresh"></span>
                        </button>
                    </h3>
                </div>
            </div>

            <div id="completeChecksTable" class="col-xs-12 no-padding">
                <c:choose>
                    <c:when test="${empty completeChecks}">
                        <h3 class="text-center">Your checks history is empty!</h3>
                    </c:when>
                    <c:otherwise>
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Project Name</th>
                                <th>Devs Cost</th>
                                <th>Services Cost</th>
                                <th>Taxes</th>
                                <th>Total</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${completeChecks}" var="completeCheck">
                                <tr>
                                    <td><c:out value="${completeCheck.projectName}"/></td>
                                    <td><c:out value="${completeCheck.developersCost}"/> $</td>
                                    <td><c:out value="${completeCheck.servicesCost}"/> $</td>
                                    <td><c:out value="${completeCheck.taxes}"/> $</td>
                                    <td><c:out value="${completeCheck.totalProjectCost}"/> $</td>
                                    <td class="${completeCheck.status == 'Paid' ? 'lime' : 'red'}">
                                        <c:out value="${completeCheck.status}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <%--.end Considered Checks--%>

        <%--Active projects--%>
        <div id="activeProjects" class="tab-pane fade">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header lead">
                        <spring:message code="customersCabinet.activeProjectsLead"/>
                        <button name="refresh" data-container-id="#activeProjectsTable"
                                class="refresh-button pull-right">
                            <span class="glyphicon glyphicon-refresh"></span>
                        </button>
                    </h3>
                </div>
                <div id="activeProjectsTable" class="col-xs-12 no-padding">
                    <c:choose>
                        <c:when test="${empty activeProjects}">
                            <h3 class="text-center">None of your project is running in the moment.</h3>
                        </c:when>
                        <c:otherwise>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Appointment</th>
                                    <th>Price</th>
                                    <th>Start Date</th>
                                    <th>End Date</th>
                                    <th>Status</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>Fix up site content layout</td>
                                    <td>100 $</td>
                                    <td>14.09.2014</td>
                                    <td>14.10.2014</td>
                                    <td>Completed</td>
                                </tr>
                                <tr>
                                    <td>Create time managing application</td>
                                    <td>30 000 $</td>
                                    <td>14.09.2014</td>
                                    <td>18.09.2014</td>
                                    <td>Canceled</td>
                                </tr>
                                <tr>
                                    <td>Do stuff</td>
                                    <td>19.99 $</td>
                                    <td>14.09.2016</td>
                                    <td>Unknown</td>
                                    <td>Pending</td>
                                </tr>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <%--Active Projects-End--%>

        <%--History--%>
        <div id="history" class="tab-pane fade">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header lead">
                        <spring:message code="customersCabinet.historyLead"/>
                        <button name="refresh" data-container-id="#historyTable" type="button"
                                class="refresh-button pull-right">
                            <span class="glyphicon glyphicon-refresh"></span>
                        </button>
                    </h3>
                </div>
                <div id="historyTable" class="col-xs-12 no-padding">
                    <c:choose>
                        <c:when test="${empty history}">
                            <h3 class="text-center">You haven't submitted any projects yet.</h3>
                        </c:when>
                        <c:otherwise>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Appointment</th>
                                    <th>Price</th>
                                    <th>Start Date</th>
                                    <th>End Date</th>
                                    <th>Status</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>Fix up site content layout</td>
                                    <td>100 $</td>
                                    <td>14.09.2014</td>
                                    <td>14.10.2014</td>
                                    <td>Completed</td>
                                </tr>
                                <tr>
                                    <td>Create time managing application</td>
                                    <td>30 000 $</td>
                                    <td>14.09.2014</td>
                                    <td>18.09.2014</td>
                                    <td>Canceled</td>
                                </tr>
                                <tr>
                                    <td>Do stuff</td>
                                    <td>19.99 $</td>
                                    <td>14.09.2016</td>
                                    <td>Unknown</td>
                                    <td>Pending</td>
                                </tr>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <%--History-End--%>

        <%--Settings--%>
        <div id="settings" class="tab-pane">
            <p class="lead">Here you can edit your information. If you want to change some field directly, just
                leave others empty.</p>
            <div class="row">
                <div class="col-lg-6">

                    <form>
                        <label for="email">E-mail</label>
                        <div class="input-group">
                            <span class="input-group-addon">user@email.com</span>
                            <input id="email" type="email" class="form-control" placeholder="New mail">
                        </div>
                        <label for="phone">Phone number</label>
                        <div class="input-group">
                            <span class="input-group-addon">+3 8814-378-3094</span>
                            <input id="phone" type="text" class="form-control" placeholder="New phone number">
                        </div>
                        <label for="password">Password</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input id="password" type="password" class="form-control" placeholder="New password">
                        </div>
                        <div class="input-group">
                            <button type="button" class="btn btn-success w-200" data-toggle="modal"
                                    data-target="#myModal">Confirm
                            </button>
                        </div>


                    </form>
                </div>
                <%--.end column--%>
                <div class="col-lg-6">
                    <div class="alert settings-alert alert-danger alert-dismissable fade in">
                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        <strong>Danger!</strong> This alert box could indicate a dangerous action.
                    </div>
                </div>
            </div>
        </div>
        <%--.end Settings--%>

    </div>
    <%--.end Tab content--%>
</div>

<%--Modals--%>
<!-- Password modal -->
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">To continue please enter your current password</h4>
            </div>
            <div class="modal-body">
                <input id="currentPassword" type="password" class="form-control"
                       placeholder="Current password">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Submit</button>
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