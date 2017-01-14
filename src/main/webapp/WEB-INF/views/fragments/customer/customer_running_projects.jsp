<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--Active projects--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            <spring:message code="customersCabinet.runningProjectsLead"/>
            <button name="refresh" data-container-id="#activeProjectsTable" class="refresh-button pull-right"
                    data-path="/fragments/customer_running_projects" type="button">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>
    <div id="activeProjectsTable" class="col-xs-12">
        <c:choose>
            <c:when test="${empty runningProjects}">
                <h3 class="text-center">
                    <spring:message code="customersCabinet.noRunningProjects"/>
                </h3>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th><spring:message code="tables.projectName"/></th>
                        <th><spring:message code="tables.startDate"/></th>
                        <th><spring:message code="tables.totalCost"/></th>
                        <th><spring:message code="tables.status"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${runningProjects}" var="project">
                        <tr>
                            <td><a href="#"><c:out value="${project.name}"/></a></td>
                            <td><fmt:formatDate value="${project.startDate}" pattern="d MMMM, yyyy"/></td>
                            <td><fmt:formatNumber type="number" pattern="#" value="${project.totalProjectCost}"/> $
                            </td>
                            <td class="${project.status == 'Pending' ? 'blue' : 'lime'}">
                                <c:out value="${project.status}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%--Active Projects-End--%>