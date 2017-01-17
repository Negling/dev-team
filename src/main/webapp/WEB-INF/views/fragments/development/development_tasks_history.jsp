<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="myTags" uri="http://dev-team.com/jsp/jstl" %>

<%--History--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            <spring:message code="developersCabinet.tasksHistoryLead"/>
            <button name="refresh" data-container-id="#historyTable" type="button" class="refresh-button pull-right"
                    data-path="/fragments/development_tasks_history">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>

    <div id="historyTable" class="col-xs-12">
        <c:choose>
            <c:when test="${empty completeTasks}">
                <h3 class="text-center">
                    <spring:message code="developersCabinet.noBackground"/>
                </h3>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th><spring:message code="entity.taskName"/></th>
                        <th><spring:message code="development.hoursSpentShort"/></th>
                        <th><spring:message code="tables.status"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${completeTasks}" var="completeTask">
                        <tr>
                            <td><c:out value="${completeTask.taskName}"/></td>
                            <td><c:out value="${completeTask.hoursSpent}"/></td>
                            <td style="<myTags:statusStyle status="${completeTask.status}"/>">
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
