<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="myTags" uri="http://dev-team.com/jsp/jstl" %>

<%--Considered Checks--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead"><spring:message code="customersCabinet.checksHistoryLead"/>
            <button name="refresh" data-container-id="#completeChecksTable" class="refresh-button pull-right"
                    data-path="/fragments/customer_considered_checks" type="button">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>

    <div id="completeChecksTable" class="col-xs-12 no-padding">
        <c:choose>
            <c:when test="${empty completeChecks}">
                <h3 class="text-center">
                    <spring:message code="customersCabinet.noChecks"/>
                </h3>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                    <tr>
                        <th><spring:message code="tables.projectName"/></th>
                        <th class="text-center"><spring:message code="tables.devsCost"/></th>
                        <th class="text-center"><spring:message code="tables.servicesCost"/></th>
                        <th class="text-center"><spring:message code="tables.taxesCost"/></th>
                        <th class="text-center"><spring:message code="tables.totalCost"/></th>
                        <th><spring:message code="tables.status"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${completeChecks}" var="completeCheck">
                        <tr>
                            <td><a href="<spring:url value="/project?id=${completeCheck.projectId}"/>">
                                <c:out value="${completeCheck.projectName}"/>
                            </a>
                            </td>
                            <td class="text-center">
                                <fmt:formatNumber type="number" pattern="#" value="${completeCheck.developersCost}"/> $
                            </td>
                            <td class="text-center">
                                <fmt:formatNumber type="number" pattern="#" value="${completeCheck.servicesCost}"/> $
                            </td>
                            <td class="text-center">
                                    <fmt:formatNumber type="number" pattern="#" value="${completeCheck.taxes}"/>
                                $
                            <td class="text-center">
                                <fmt:formatNumber type="number" pattern="#" value="${completeCheck.totalProjectCost}"/>
                                $
                            </td>
                            <td style="<myTags:statusStyle status="${completeCheck.status}"/>">
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