<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--Manage Tehnical Tasks--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            Here you can grab new requests.
            <button name="refresh" data-container-id="#technicalTasksAccordion" class="refresh-button pull-right"
                    type="button" data-path="/fragments/manage_technical_tasks">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>

    <%--Technical Tasks--%>
    <div id="technicalTasksAccordion" class="panel-group col-lg-8 col-lg-offset-2">
        <c:choose>
            <c:when test="${empty technicalTasks}">
                <div class="text-vertical-center col-lg-12">
                    <h3>No new Technical Tasks!</h3>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach items="${technicalTasks}" var="technicalTask" varStatus="loop">
                    <div class="panel panel-default">
                        <div class="project-heading panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#technicalTasksAccordion"
                                   href="#technicalTask<c:out value="${technicalTask.id}"/>">
                                    <c:out value="${technicalTask.name}"/></a>
                            </h4>
                        </div>

                            <%--Technical Task Body--%>
                        <div id="technicalTask<c:out value="${technicalTask.id}"/>"
                             class="panel-collapse collapse${loop.index == 0 ? ' in' : ''}">
                            <div class="panel-body">

                                <div class="col-lg-12">
                                    <h4 class="page-header"><strong>Technical Task Description</strong>
                                    </h4>
                                    <p class="data-description">
                                        <c:out value="${technicalTask.description}"/>
                                    </p>
                                    <h3 class="text-center page-header"><strong>Operations</strong></h3>

                                        <%--Operations--%>
                                    <div id="operationsAccordion<c:out value="${technicalTask.id}"/>"
                                         class="panel-group">
                                        <c:forEach items="${technicalTask.operations}" var="task">
                                            <div class="panel panel-default">
                                                <div class="task-heading panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse"
                                                           data-parent="#operationsAccordion<c:out value="${technicalTask.id}"/>"
                                                           href="#operation<c:out value="${task.id}"/>">
                                                            <c:out value="${task.name}"/>
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="operation<c:out value="${task.id}"/>"
                                                     class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div class="table-responsive">
                                                            <table class="text-center table table-condensed">
                                                                <thead>
                                                                <tr>
                                                                    <th class="text-center">Specialization
                                                                    </th>
                                                                    <th class="text-center">Rank</th>
                                                                    <th class="text-center">Quantity</th>
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                <c:forEach items="${task.requestsForDevelopers}"
                                                                           var="requestForDevelopers">
                                                                    <tr>
                                                                        <td>
                                                                            <c:out value="${requestForDevelopers.specialization}"/>
                                                                        </td>
                                                                        <td>
                                                                            <c:out value="${requestForDevelopers.rank}"/>
                                                                        </td>
                                                                        <td>
                                                                            <c:out value="${requestForDevelopers.quantity}"/>
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <hr>
                                                        <h4>
                                                            <strong>Operation Description</strong></h4>
                                                        <p class="data-description">
                                                            <c:out value="${task.description}"/>
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                        <%--end Operations--%>


                                    <button name="declineTechnicalTask" class="btn btn-danger pull-left w-200"
                                            type="button" data-entity-type="technicalTask"
                                            value="<c:out value="${technicalTask.id}"/>">
                                        Decline
                                    </button>
                                    <button class="btn btn-success pull-right w-200" name="acceptTechnicalTask"
                                            value="<c:out value="${technicalTask.id}"/>" type="button">
                                        Form as Project
                                    </button>
                                </div>
                            </div>
                        </div>
                            <%--end Technical Task Body--%>

                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <%--end Technical Tasks--%>
</div>
<%--.end Manage Technical Tasks--%>