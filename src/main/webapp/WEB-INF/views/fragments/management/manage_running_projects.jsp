<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--Running Projects--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            Running Projects
            <button name="refresh" data-container-id="#runningProjectsTable" class="refresh-button pull-right"
                    type="button" data-path="/fragments/manage_running_projects">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>
    <div id="runningProjectsTable">
        <c:choose>
            <c:when test="${empty runningProjects}">
                <div class="text-vertical-center col-lg-12">
                    <h3>No Complete Projects!</h3>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Project Name</th>
                        <th>Start Date</th>
                        <th>Status</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${runningProjects }" var="project">
                        <tr>
                            <td><a href="<spring:url value="/project?id=${project.id}"/>">
                                <c:out value="${project.name}"/></a>
                            </td>
                            <td><fmt:formatDate value="${project.startDate}" pattern="d MMMM, yyyy"/></td>
                            <td class="${project.status == 'Pending' ? 'blue' : 'lime'}">
                                <c:out value="${project.status}"/>
                            </td>
                            <td>
                                <fmt:formatNumber type="number" pattern="#" value="${project.totalProjectCost}"/> $
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%--end Running Projects--%>