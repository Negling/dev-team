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
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header lead">
                        Current Task
                        <button name="refresh" data-container-id="#currentTask" type="button"
                                class="refresh-button pull-right">
                            <span class="glyphicon glyphicon-refresh"></span>
                        </button>
                    </h3>
                </div>

                <%--Technical Tasks--%>
                <div id="currentTask" class="panel-group col-lg-8 col-lg-offset-2 col-xs-12">
                    <c:choose>
                        <c:when test="${empty activeTaskData}">
                            <div class="text-center">
                                <h3>You aren't assigned on task!</h3>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="bordered-container col-lg-12">
                                <h4><strong>Task Name:</strong></h4>
                                <p class="data-description"><c:out value="${activeTaskData.taskName}"/></p>

                                <h4><strong>Task Description:</strong></h4>
                                <p class="data-description">
                                    <c:out value="${activeTaskData.taskDescription}"/>
                                </p>

                                <div class="form-group col-xs-4 no-padding">
                                    <label class="control-label" for="hoursSpent">
                                        Hours Spent:
                                    </label>
                                    <input id="hoursSpent" type="number" class="form-control" value="0" min="1"
                                           max="999">
                                </div>
                                <button id="markComplete" type="button" value="${activeTaskData.projectTaskId}"
                                        class="btn btn-success pull-right mt-25">
                                    Mark As Complete
                                </button>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <%--end Technical Tasks--%>
            </div>
        </div>
        <%--.end Task Under Progress--%>

        <%--History--%>
        <div id="history" class="tab-pane fade">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header lead">
                        Development History
                        <button name="refresh" data-container-id="#historyTable" type="button"
                                class="refresh-button pull-right">
                            <span class="glyphicon glyphicon-refresh"></span>
                        </button>
                    </h3>
                </div>

                <div id="historyTable" class="col-xs-12">
                    <c:choose>
                        <c:when test="${empty completeTasks}">
                            <h3 class="text-center">You haven't complete any task yet!</h3>
                        </c:when>
                        <c:otherwise>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Task Name</th>
                                    <th>Hours Spent</th>
                                    <th>Status</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${completeTasks}" var="completeTask">
                                    <tr>
                                        <td><c:out value="${completeTask.taskName}"/></td>
                                        <td><c:out value="${completeTask.hoursSpent}"/></td>
                                        <td><c:out value="${completeTask.status}"/></td>
                                    </tr>
                                </c:forEach>
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
