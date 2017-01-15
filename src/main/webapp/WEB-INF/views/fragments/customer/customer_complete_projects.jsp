<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="myTags" uri="http://dev-team.com/jsp/jstl" %>

<%--Projects History--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            <spring:message code="customersCabinet.completeProjectsLead"/>
            <button name="refresh" data-container-id="#completeProjectsTable" class="refresh-button pull-right"
                    data-path="/fragments/customer_complete_projects" type="button">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>
    <div id="completeProjectsTable" class="col-xs-12">
        <c:choose>
            <c:when test="${empty completeProjects}">
                <h3 class="text-center">
                    <spring:message code="customersCabinet.noCompleteProjects"/>
                </h3>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th><spring:message code="tables.projectName"/></th>
                        <th><spring:message code="tables.startDate"/></th>
                        <th><spring:message code="tables.endDate"/></th>
                        <th><spring:message code="tables.status"/></th>
                        <th><spring:message code="tables.cost"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${completeProjects}" var="project">
                        <tr>
                            <td>
                                <a href="<spring:url value="/project?id=${project.id}"/>">
                                    <c:out value="${project.name}"/>
                                </a>
                            </td>
                            <td><fmt:formatDate value="${project.startDate}" pattern="d MMMM, yyyy"/></td>
                            <td><fmt:formatDate value="${project.endDate}" pattern="d MMMM, yyyy"/></td>

                            <td class="<myTags:statusStyle status="project.status"/>">
                                <c:out value="${project.status}"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty project.totalProjectCost}">
                                        <spring:message code="tables.notAssigned"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatNumber type="number" pattern="#"
                                                          value="${project.totalProjectCost}"/> $
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
<%-- Projects History-End--%>