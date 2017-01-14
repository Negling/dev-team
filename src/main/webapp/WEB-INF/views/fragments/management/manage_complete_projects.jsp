<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--Complete Projects--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            Complete Projects
            <button name="refresh" data-container-id="#completeProjectsTable" class="refresh-button pull-right"
                    type="button" data-path="/fragments/manage_complete_projects">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>
    <div id="completeProjectsTable">
        <c:choose>
            <c:when test="${empty completeProjects}">
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
                        <th>End Date</th>
                        <th>Status</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${completeProjects }" var="project">
                        <tr>
                            <td><a href="<spring:url value="/project?id=${project.id}"/>">
                                <c:out value="${project.name}"/></a>
                            </td>
                            <td><fmt:formatDate value="${project.startDate}" pattern="d MMMM, yyyy"/></td>
                            <td><fmt:formatDate value="${project.endDate}" pattern="d MMMM, yyyy"/></td>
                            <td class="${project.status == 'Complete' ? 'lime' : 'red'}">
                                <c:out value="${project.status}"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty project.totalProjectCost}">
                                        Not Assigned
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatNumber type="number" pattern="#" value="${project.totalProjectCost}"/> $
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%--end Complete Projects--%>