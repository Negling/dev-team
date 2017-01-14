<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--Create Project--%>
<div class="row">
    <div id="formTechnicalTask"
         class="bordered-container col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
        <h3 class="text-center page-header">
            <strong>
                <spring:message code="customersCabinet.projectData"/>
            </strong>
        </h3>

        <%--technical task name--%>
        <div class="form-group">
            <label class="control-label" for="technicalTaskName">
                <spring:message code="entity.projectName"/>:
            </label>

            <input id="technicalTaskName" name="projectName" type="text" class="form-control"
                   placeholder="<spring:message code="entity.projectNamePlaceholder"/>">
        </div>
        <%--technical task description--%>
        <div class="form-group">
            <label class="control-label" for="technicalTaskDescription">
                <spring:message code="entity.projectDescription"/>:
            </label>
            <textarea id="technicalTaskDescription" name="projectDescription" class="form-control" rows="3"
                      placeholder="<spring:message code="entity.projectDescriptionPlaceholder"/>"></textarea>
        </div>

        <%--Tasks--%>
        <h3 class="text-center page-header">
            <strong><spring:message code="entity.tasks"/></strong>
        </h3>
        <div id="tasks" class="panel-group">
        </div>
        <div class="text-center">
            <button class="btn btn-primary btn-sm add-task-btn" type="button" data-toggle="modal"
                    data-target="#addTaskModal">
                <spring:message code="customersCabinet.addTask"/>
            </button>
        </div>
        <button id="submitTechnicalTask" class="btn btn-success submit-project-btn" type="submit">
            <spring:message code="general.submit"/>
        </button>
    </div>
</div>

<%--Task modal--%>
<div id="addTaskModal" class="text-center modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="no-borders modal-header">

                <%--task name--%>
                <h4 class="modal-title">
                    <strong>
                        <spring:message code="entity.taskName"/>
                    </strong>
                </h4>
                <input id="taskName" class="form-control" type="text" maxlength="50"
                       placeholder="<spring:message code="entity.projectNamePlaceholder"/>">
            </div>

            <%--devs--%>
            <div class="no-borders modal-body">
                <h4 class="text-center">
                    <strong>
                        <spring:message code="entity.developers"/>
                    </strong>
                </h4>
                <hr>
                <%--devs table--%>
                <table class="table table-condensed table-responsive">
                    <thead>
                    <tr>
                        <th class="text-center">
                            <spring:message code="entity.specialization"/>
                        </th>
                        <th class="text-center">
                            <spring:message code="entity.rank"/>
                        </th>
                        <th class="text-center">
                            <spring:message code="entity.quantity"/>
                        </th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <select class="form-control" title="Specialization">
                                <c:forEach items="${specializations}" var="specialization">
                                    <option>
                                        <c:out value="${specialization}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select class="form-control" title="Rank">
                                <c:forEach items="${ranks}" var="rank">
                                    <option>
                                        <c:out value="${rank}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input class="form-control" type="number" min="1" max="100" value="1" placeholder="Max 100"
                                   title="Quantity">
                        </td>
                        <td class="w-100"></td>
                    </tr>
                    </tbody>
                </table>
                <button id="addDevButton" class="btn btn-default btn-block">
                    <span class="glyphicon glyphicon-plus"></span>
                </button>
            </div>
            <%--task description--%>
            <div class="no-borders modal-footer">
                <h4 class="text-center">
                    <strong>
                        <spring:message code="entity.taskDescription"/>
                    </strong>
                </h4>
                <textarea id="taskDescription" class="form-control" rows="6"
                          placeholder="<spring:message code="entity.projectDescriptionPlaceholder"/>"></textarea>
                <button id="taskBackButton" type="button" class="w-100 pull-left mt-10 btn btn-primary"
                        data-dismiss="modal">
                    <spring:message code="general.back"/>
                </button>
                <button id="addTaskButton" type="button" class="w-100 mt-10 btn btn-success" disabled>
                    <spring:message code="general.add"/>
                </button>
            </div>
        </div>
    </div>
</div>

<%--Hidden task prototype--%>
<div id="taskPrototype" class="no-display panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-parent="#tasks" data-toggle="collapse"></a>
        </h4>
    </div>
    <div class="panel-collapse collapse">
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-condensed table-responsive">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="entity.specialization"/>
                        </th>
                        <th>
                            <spring:message code="entity.rank"/>
                        </th>
                        <th class="text-center">
                            <spring:message code="entity.quantity"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <p title="taskDescription" class="data-description"></p>
            <button type="button" class="mt-10 pull-left btn btn-danger btn-sm">
                <spring:message code="customersCabinet.dropTask"/>
            </button>
        </div>
    </div>
</div>

<%--end Create Project--%>
