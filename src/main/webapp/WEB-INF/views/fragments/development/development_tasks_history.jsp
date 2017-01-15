<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="myTags" uri="http://dev-team.com/jsp/jstl" %>

<%--History--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            Development History
            <button name="refresh" data-container-id="#historyTable" type="button" class="refresh-button pull-right"
                    data-path="/fragments/development_tasks_history">
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
                            <td class="<myTags:statusStyle status="${completeTask.status}"/>">
                                <c:out value="${completeTask.status}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%--History-End--%>
