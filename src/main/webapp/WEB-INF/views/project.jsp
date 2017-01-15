<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="fragments/head.jspf" %>
    <link href="<spring:url value="/resources/css/header-footer.css" />" rel="stylesheet">
    <title><c:out value="${project.name}"/></title>
</head>
<body>
<%@include file="fragments/navbar.jspf" %>

<div class="container">
    <div class="row">
        <%--Project Body--%>
        <div class="col-md-8 col-md-offset-2 bordered-container mt-10">
            <div class="col-md-12">
                <h3 class="page-header lead no-margin">
                    <strong>
                        <spring:message code="tables.projectName"/>
                    </strong>
                </h3>
                <p class="data-description">
                    <c:out value="${project.name}"/>
                </p>
            </div>

            <div class="col-md-12">
                <h4 class="page-header text-center">
                    <strong>
                        <spring:message code="customersCabinet.projectData"/>
                    </strong>
                </h4>
            </div>

            <div class="col-md-4">
                <h4>
                    <strong><spring:message code="tables.startDate"/></strong>
                </h4>
                <p class="data-description">
                    <fmt:formatDate value="${project.startDate}" pattern="d MMMM, yyyy"/>
                </p>
                <h4>
                    <strong><spring:message code="tables.currentStatus"/></strong>
                </h4>
                <p class="<myTags:statusStyle status="${project.status}"/> data-description">
                    <c:out value="${project.status}"/>
                </p>
            </div>

            <div class="col-md-4">
                <h4>
                    <strong><spring:message code="tables.endDate"/></strong>
                </h4>
                <p class="data-description">
                    <c:choose>
                        <c:when test="${empty project.endDate}">
                            <spring:message code="tables.running"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:formatDate value="${project.endDate}" pattern="d MMMM, yyyy"/>
                        </c:otherwise>
                    </c:choose>
                </p>
                <h4>
                    <strong><spring:message code="tables.tasksAmount"/></strong>
                </h4>
                <p class="data-description">
                    <c:out value="${project.tasks.size()}"/>
                </p>
            </div>

            <div class="col-md-4">
                <h4>
                    <strong><spring:message code="tables.projectCost"/></strong>
                </h4>
                <p class="data-description">
                    <c:choose>
                        <c:when test="${empty project.totalProjectCost}">
                            <spring:message code="tables.notAssigned"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:formatNumber type="number" pattern="#" value="${project.totalProjectCost}"/> $
                        </c:otherwise>
                    </c:choose>
                </p>
            </div>

            <div class="col-md-12">
                <h4 class="page-header">
                    <strong><spring:message code="entity.projectDescription"/></strong>
                </h4>
                <p class="data-description">
                    <c:out value="${project.description}"/>
                </p>
            </div>

            <c:if test="${not empty project.managerCommentary}">
                <div class="col-md-12">
                    <h4 class="page-header">
                        <strong><spring:message code="entity.managerCommentary"/></strong>
                    </h4>
                    <p class="data-description">
                        <c:out value="${project.managerCommentary}"/>
                    </p>
                </div>
            </c:if>

            <div class="col-lg-12">
                <h3 class="text-center page-header">
                    <strong><spring:message code="entity.tasks"/></strong>
                </h3>

                <%--Tasks--%>
                <div id="tasksAccordion" class="panel-group">
                    <c:forEach items="${project.tasks}" var="task">
                        <div class="panel panel-default">
                            <div class="task-heading panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" href="#task<c:out value="${task.id}"/>"
                                       data-parent="#tasksAccordion">
                                        <c:out value="${task.name}"/>
                                    </a>
                                </h4>
                            </div>
                            <div id="task<c:out value="${task.id}"/>" class="panel-collapse collapse">

                                    <%--Task Body--%>
                                <div class="panel-body">

                                    <div class="col-md-6">
                                        <h4>
                                            <strong><spring:message code="tables.taskStatus"/></strong>
                                        </h4>
                                        <p class="<myTags:statusStyle status="${task.taskStatus}"/> data-description">
                                            <c:out value="${task.taskStatus}"/>
                                        </p>
                                    </div>
                                    <div class="col-md-6">
                                        <h4>
                                            <strong><spring:message code="tables.developersInvolved"/></strong>
                                        </h4>
                                        <p class="data-description">
                                            <c:out value="${task.tasksDevelopmentData.size()}"/>
                                        </p>
                                    </div>

                                    <div class="col-md-6">
                                        <h4>
                                            <strong><spring:message code="development.hoursSpent"/></strong>
                                        </h4>
                                        <p class="data-description">
                                            <c:out value="${task.totalHoursSpent}"/>
                                        </p>
                                    </div>

                                    <div class="col-md-12">
                                        <h4 class="page-header">
                                            <strong>
                                                <spring:message code="entity.taskDescription"/>
                                            </strong>
                                        </h4>
                                        <p class="data-description">
                                            <c:out value="${task.description}"/>
                                        </p>

                                        <security:authorize url="/manage">

                                            <%--assigned Developers--%>
                                            <h4 class="page-header text-center">
                                                <strong><spring:message code="tables.assignedDevelopers"/></strong>
                                            </h4>
                                            <table class="table">
                                                <thead>
                                                <tr>
                                                    <th class="text-center"><spring:message code="tables.name"/></th>
                                                    <th class="text-center"><spring:message
                                                            code="entity.specialization"/></th>
                                                    <th class="text-center"><spring:message code="entity.rank"/></th>
                                                    <th class="text-center"><spring:message
                                                            code="tables.hireCost"/></th>
                                                    <th><spring:message code="tables.status"/></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${task.tasksDevelopmentData}" var="taskData">
                                                    <tr>
                                                        <td>
                                                            <a href="<spring:url value="/developer?id=${taskData.developerId}"/>">
                                                                <c:out value="${taskData.developerFirstName}"/>
                                                                <c:out value="${taskData.developerLastName}"/>
                                                            </a>
                                                        </td>
                                                        <td class="text-center"><c:out value="${taskData.specialization}"/></td>
                                                        <td class="text-center"><c:out value="${taskData.rank}"/></td>
                                                        <td class="text-center">
                                                            <fmt:formatNumber type="number" pattern="#" value="${taskData.hireCost}"/> $
                                                        </td>
                                                        <td class="<myTags:statusStyle status="${taskData.status}"/>">
                                                            <c:out value="${taskData.status}"/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                            <%--end assigned Developers--%>
                                        </security:authorize>
                                    </div>
                                </div>
                                    <%--End Task Body--%>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <%--end Tasks--%>
            </div>
        </div>
        <%--end Project Body--%>
    </div>
</div>

<%@include file="fragments/footer.jspf" %>
</body>
</html>