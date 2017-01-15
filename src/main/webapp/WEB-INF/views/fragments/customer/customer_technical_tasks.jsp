<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="myTags" uri="http://dev-team.com/jsp/jstl" %>

<%--Tehcnical Tasks History--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            <spring:message code="customersCabinet.technicalTasksLead"/>
            <button name="refresh" data-container-id="#technicalTasksHistoryTable" class="refresh-button pull-right"
                    data-path="/fragments/customer_technical_tasks" type="button">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>
    <div id="technicalTasksHistoryTable" class="col-md-8">
        <c:choose>
            <c:when test="${empty technicalTasks}">
                <h3 class="text-center">
                    <spring:message code="customersCabinet.noTechnicalTasks"/>
                </h3>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th><spring:message code="tables.projectName"/></th>
                        <th><spring:message code="tables.status"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${technicalTasks}" var="technicalTask">
                        <tr>
                            <td><a href="<spring:url value="/technicalTask?id=${technicalTask.id}"/>">
                                <c:out value="${technicalTask.name}"/></a>
                            </td>
                            <td class="<myTags:statusStyle status="${technicalTask.status}"/>">
                                <c:out value="${technicalTask.status}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="col-md-4 pt-10">
        <h3>
            <spring:message code="customersCabinet.moreDetails"/>
        </h3>
    </div>
</div>
<%--Tehcnical Tasks History-End--%>