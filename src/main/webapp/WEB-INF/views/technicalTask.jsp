<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="fragments/head.jspf" %>
    <link href="<spring:url value="/resources/css/header-footer.css" />" rel="stylesheet">
    <title><c:out value="${technicalTask.name}"/></title>
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
                    <c:out value="${technicalTask.name}"/>
                </p>
            </div>

            <div class="col-md-12">
                <h4 class="page-header text-center">
                    <strong>
                        <spring:message code="customersCabinet.projectData"/>
                    </strong>
                </h4>
            </div>

            <div class="col-md-6">
                <h4>
                    <strong><spring:message code="tables.currentStatus"/></strong>
                </h4>
                <p class="<myTags:statusStyle status="${technicalTask.status}"/> data-description">
                    <c:out value="${technicalTask.status}"/>
                </p>
            </div>

            <div class="col-md-6">
                <h4>
                    <strong><spring:message code="tables.tasksAmount"/></strong>
                </h4>
                <p class="data-description">
                    <c:out value="${technicalTask.operations.size()}"/>
                </p>
            </div>

            <div class="col-md-12">
                <h4 class="page-header">
                    <strong><spring:message code="entity.projectDescription"/></strong>
                </h4>
                <p class="data-description">
                    <c:out value="${technicalTask.description}"/>
                </p>
            </div>

            <c:if test="${not empty technicalTask.managerCommentary}">
                <div class="col-md-12">
                    <h4 class="page-header">
                        <strong><spring:message code="entity.managerCommentary"/></strong>
                    </h4>
                    <p class="data-description">
                        <c:out value="${technicalTask.managerCommentary}"/>
                    </p>
                </div>
            </c:if>

            <div class="col-lg-12">
                <h3 class="text-center page-header">
                    <strong><spring:message code="entity.tasks"/></strong>
                </h3>

                <%--Tasks--%>
                <div id="tasksAccordion" class="panel-group">
                    <c:forEach items="${technicalTask.operations}" var="task">
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

                                    <div class="col-md-12">
                                        <h4 class="page-header">
                                            <strong>
                                                <spring:message code="entity.taskDescription"/>
                                            </strong>
                                        </h4>
                                        <p class="data-description">
                                            <c:out value="${task.description}"/>
                                        </p>
                                    </div>

                                    <h4 class="page-header text-center">
                                        <strong>
                                            <spring:message code="entity.requestedDevelopers"/>
                                        </strong>
                                    </h4>

                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th class="text-center"><spring:message code="entity.specialization"/></th>
                                            <th class="text-center"><spring:message code="entity.rank"/></th>
                                            <th class="text-center"><spring:message code="entity.quantity"/></th>
                                        </tr>
                                        </thead>
                                        <tbody class="text-center">
                                        <c:forEach items="${task.requestsForDevelopers}" var="requestForDevs">
                                            <tr>
                                                <td><c:out value="${requestForDevs.specialization}"/></td>
                                                <td><c:out value="${requestForDevs.rank}"/></td>
                                                <td><c:out value="${requestForDevs.quantity}"/></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
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
