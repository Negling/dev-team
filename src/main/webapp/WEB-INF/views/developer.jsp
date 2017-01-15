<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="fragments/head.jspf" %>
    <link href="<spring:url value="/resources/css/header-footer.css" />" rel="stylesheet">
    <title><c:out value="${developer.firstName}"/> <c:out value="${developer.lastName}"/></title>
</head>
<body>
<%@include file="fragments/navbar.jspf" %>

<div class="container">
    <div class="row">

        <%--Project Body--%>
        <div class="col-md-8 col-md-offset-2 bordered-container mt-10">
            <div class="col-md-6">
                <h4>
                    <strong><spring:message code="settings.firstName"/></strong>
                </h4>
                <p class="data-description">
                    <c:out value="${developer.firstName}"/>
                </p>
                <h4>
                    <strong><spring:message code="entity.specialization"/></strong>
                </h4>
                <p class="data-description">
                    <c:out value="${developer.specialization}"/>
                </p>
                <h4>
                    <strong><spring:message code="entity.salary"/></strong>
                </h4>
                <p class="data-description">
                    <fmt:formatNumber type="number" pattern="#" value="${developer.hireCost}"/> $
                </p>
            </div>

            <div class="col-md-6">
                <h4>
                    <strong><spring:message code="settings.lastName"/></strong>
                </h4>
                <p class="data-description">
                    <c:out value="${developer.lastName}"/>
                </p>
                <h4>
                    <strong><spring:message code="entity.rank"/></strong>
                </h4>
                <p class="data-description">
                    <c:out value="${developer.rank}"/>
                </p>
                <h4>
                    <strong><spring:message code="tables.status"/></strong>
                </h4>
                <p class="data-description <myTags:statusStyle status="${developer.status}"/>">
                    <c:out value="${developer.status}"/>
                </p>
            </div>

            <c:if test="${not empty currentTask}">
                <div class="col-xs-12 no-padding">
                    <h3 class="text-center page-header">
                        <strong><spring:message code="tables.currentTask"/></strong>
                    </h3>

                    <div class="col-md-6">
                        <h4>
                            <strong><spring:message code="entity.taskName"/></strong>
                        </h4>
                        <p class="data-description">
                            <c:out value="${currentTask.taskName}"/>
                        </p>
                    </div>
                    <div class="col-md-6">
                        <h4>
                            <strong><spring:message code="tables.status"/></strong>
                        </h4>
                        <p class="data-description <myTags:statusStyle status="${currentTask.status}"/>">
                            <c:out value="${currentTask.status}"/>
                        </p>
                    </div>
                </div>
            </c:if>

            <div class="col-xs-12">
                <h3 class="text-center page-header">
                    <strong><spring:message code="entity.background"/></strong>
                </h3>

                <c:choose>
                    <c:when test="${empty developersBackground}">
                        <h4 class="text-center">
                            <spring:message code="entity.emptyBackground"/>
                        </h4>
                    </c:when>
                    <c:otherwise>
                        <table class="table">
                            <thead>
                            <tr>
                                <th class="text-center"><spring:message code="entity.taskName"/></th>
                                <th class="text-center"><spring:message code="entity.specialization"/></th>
                                <th class="text-center"><spring:message code="entity.rank"/></th>
                                <th class="text-center"><spring:message code="development.hoursSpentShort"/></th>
                                <th class="text-center"><spring:message code="tables.status"/></th>
                            </tr>
                            </thead>
                            <tbody class="text-center">
                            <c:forEach items="${developersBackground}" var="taskData">
                                <tr>
                                    <td><c:out value="${taskData.taskName}"/></td>
                                    <td><c:out value="${taskData.specialization}"/></td>
                                    <td><c:out value="${taskData.rank}"/></td>
                                    <td><c:out value="${taskData.hoursSpent}"/></td>
                                    <td class="<myTags:statusStyle status="${taskData.status}"/>">
                                        <c:out value="${taskData.status}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<%@include file="fragments/footer.jspf" %>
</body>
</html>
