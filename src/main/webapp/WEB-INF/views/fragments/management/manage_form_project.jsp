<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--Form Projects--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            Form Technical Task as Project
            <button name="refreshProjects" data-container-id="#pendingProjectsAccordion" class="refresh-button pull-right"
                    type="button" data-path="/fragments/manage_form_project">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>

    <%--Projects--%>
    <div id="pendingProjectsAccordion" class="panel-group col-lg-9">
        <c:choose>
            <c:when test="${empty pendingProjects}">
                <h3 class="text-center">No New Requests!</h3>
            </c:when>
            <c:otherwise>
                <c:forEach items="${pendingProjects}" var="project">
                    <div class="panel panel-default">
                        <div class="project-heading panel-heading">
                            <h4 class="panel-title">
                                <a id="project<c:out value="${project.id}"/>Link" data-toggle="collapse"
                                   data-parent="#pendingProjectsAccordion"
                                   href="#pendingProject<c:out value="${project.id}"/>">
                                    <c:out value="${project.name}"/>
                                </a>
                            </h4>
                        </div>
                            <%--Project Body--%>
                        <div id="pendingProject<c:out value="${project.id}"/>" class="panel-collapse collapse"
                             data-project-id="<c:out value="${project.id}"/>">
                            <div class="panel-body">

                                <div class="col-lg-12">
                                    <h3 class="text-center page-header">
                                        <strong>Tasks</strong>
                                    </h3>

                                        <%--Tasks--%>
                                    <div id="tasksAccordion<c:out value="${project.id}"/>" class="panel-group">
                                        <c:forEach items="${project.tasks}" var="task">
                                            <div class="panel panel-default">
                                                <div class="task-heading panel-heading">
                                                    <h4 class="panel-title">
                                                        <a id="task<c:out value="${task.id}"/>Link"
                                                           data-toggle="collapse"
                                                           data-parent="#tasksAccordion<c:out value="${project.id}"/>"
                                                           data-task-id="<c:out value="${task.id}"/>"
                                                           href="#task<c:out value="${task.id}"/>">
                                                            <c:out value="${task.name}"/>
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="task<c:out value="${task.id}"/>"
                                                     class="panel-collapse collapse">

                                                        <%--Task Body--%>
                                                    <div class="panel-body">
                                                        <h4>
                                                            <strong>
                                                                Task Description
                                                            </strong>
                                                        </h4>
                                                        <p class="data-description">
                                                            <c:out value="${task.description}"/>
                                                        </p>

                                                            <%--developers Request--%>
                                                        <div class="col-lg-12">
                                                            <h4 class="text-center">
                                                                <strong>
                                                                    Requested Developers
                                                                </strong>
                                                            </h4>
                                                            <hr>
                                                            <div class="table-responsive">
                                                                <table id="task<c:out value="${task.id}"/>Requested"
                                                                       class="text-center table table-condensed">
                                                                    <thead>
                                                                    <tr>
                                                                        <th class="text-center">
                                                                            Specialization
                                                                        </th>
                                                                        <th class="text-center">
                                                                            Rank
                                                                        </th>
                                                                        <th class="text-center">
                                                                            Quantity
                                                                        </th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <c:forEach var="taskRequest"
                                                                               items="${task.requestsForDevelopers}">
                                                                        <tr>
                                                                            <td><c:out
                                                                                    value="${taskRequest.specialization}"/></td>
                                                                            <td><c:out
                                                                                    value="${taskRequest.rank}"/></td>
                                                                            <td><c:out
                                                                                    value="${taskRequest.quantity}"/></td>
                                                                        </tr>
                                                                    </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                            <%--end developers Request--%>

                                                            <%--assigned Developers--%>
                                                        <div class="col-lg-12">
                                                            <h4 class="text-center">
                                                                <strong>Assigned Developers</strong>
                                                            </h4>
                                                            <hr>
                                                            <div class="table-responsive">
                                                                <h5 class="${not empty task.tasksDevelopmentData ?
                                                                        'text-center no-display':'text-center'}">
                                                                    <strong>
                                                                        No developers assigned for this task!
                                                                    </strong>
                                                                </h5>
                                                                <table id="task<c:out value="${task.id}"/>Hired"
                                                                       class="table table-condensed
                                                                        ${not empty task.tasksDevelopmentData ? '':' no-display'}">
                                                                    <thead>
                                                                    <tr>
                                                                        <th class="text-center">
                                                                            Name
                                                                        </th>
                                                                        <th class="text-center">
                                                                            Specialization
                                                                        </th>
                                                                        <th class="text-center">Rank
                                                                        </th>
                                                                        <th class="text-center">Hire Cost
                                                                        </th>
                                                                        <th class="text-center"></th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody class="text-center">
                                                                    <c:forEach items="${task.tasksDevelopmentData}"
                                                                               var="taskData">
                                                                        <tr>
                                                                            <td>
                                                                                <a href="<spring:url value="/developer?id=${taskData.developerId}"/>">
                                                                                    <c:out value="${taskData.developerFirstName}"/>
                                                                                    <c:out value="${taskData.developerLastName}"/>
                                                                                </a>
                                                                            </td>
                                                                            <td><c:out
                                                                                    value="${taskData.specialization}"/></td>
                                                                            <td><c:out
                                                                                    value="${taskData.rank}"/></td>
                                                                            <td><fmt:formatNumber type="number"
                                                                                                  pattern="#"
                                                                                                  value="${taskData.hireCost}"/>
                                                                                $
                                                                            </td>
                                                                            <td>
                                                                                <button class="btn btn-info btn-sm"
                                                                                        name="unbindDeveloper"
                                                                                        data-project-id="<c:out value="${project.id}"/>"
                                                                                        data-developer-id="<c:out value="${taskData.developerId}"/>"
                                                                                        data-developer-hire-cost="<c:out value="${taskData.hireCost}"/>"
                                                                                        type="button">
                                                                                    Unbind
                                                                                </button>
                                                                            </td>
                                                                        </tr>
                                                                    </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                            <%--end assigned Developers--%>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                        <%--end Tasks--%>

                                    <h4 class="page-header">
                                        <strong>Project Description</strong>
                                    </h4>
                                    <p class="data-description">
                                        <c:out value="${project.description}"/>
                                    </p>

                                        <%--Project check--%>
                                    <h4 class="page-header text-center">
                                        <strong>Project Check</strong>
                                    </h4>
                                    <div class="col-lg-12 no-padding">
                                        <div class="col-lg-6 project-cost-input">
                                            <label>Devs Hire Cost:</label>
                                            <div class="input-group">
                                                <input id="project<c:out value="${project.id}"/>DevsCost" type="number"
                                                       class="form-control text-right" value="0" readonly>
                                                <span class="input-group-addon">.00 $</span>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 project-cost-input">
                                            <label>Services:</label>
                                            <div class="input-group">
                                                <input id="project<c:out value="${project.id}"/>Services" type="number"
                                                       title="servicesCost"
                                                       class="form-control text-right" value="0"
                                                       data-project-id="<c:out value="${project.id}"/>">
                                                <span class="input-group-addon">.00 $</span>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 project-cost-input">
                                            <label>Taxes(+20%):</label>
                                            <div class="input-group">
                                                <input id="project<c:out value="${project.id}"/>Taxes" type="number"
                                                       class="form-control text-right" value="0" readonly>
                                                <span class="input-group-addon">.00 $</span>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 project-cost-input">
                                            <label>Total Project Cost:</label>
                                            <div class="input-group">
                                                <input id="project<c:out value="${project.id}"/>TotalCost" type="number"
                                                       class="form-control text-right" value="0" readonly>
                                                <span class="input-group-addon">.00 $</span>
                                            </div>
                                        </div>
                                    </div>

                                        <%--Accept/Decline--%>
                                    <div class="col-lg-12 no-padding mt-10">
                                        <button type="button" name="declineProject"
                                                value="<c:out value="${project.id}"/>"
                                                data-entity-type="project" class="btn btn-danger w-200 mt-10">
                                            Decline
                                        </button>
                                        <button name="acceptProject" value="<c:out value="${project.id}"/>"
                                                class="btn btn-success pull-right w-200 mt-10">
                                            Accept
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                            <%--end Project Body--%>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <%--end Projects--%>

    <%--Developers Modal button--%>
    <div class="col-lg-3">
        <button type="button" class="btn btn-success pull-left mb-10" data-toggle="modal"
                data-target="#bindDeveloperModal">
            Search for Available Developers
        </button>
        <h4 class="dark-grey"><strong>Terms and Conditions:</strong></h4>
        <p>
            By clicking on "Register" you agree to The Company's' Terms and Conditions
        </p>
        <p>
            While rare, prices are subject to change based on exchange rate fluctuations -
            should such a fluctuation happen, we may request an additional payment. You have the option to
            request a
            full refund or to pay the new price. (Paragraph 13.5.8)
        </p>
    </div>
    <%--end Developers Modal button--%>
</div>

<!-- Bind Developer to Task Modal -->
<div class="modal fade" id="bindDeveloperModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Developers Binding Settings: </h4>
                <hr>
                <div class="row">
                    <div class="col-lg-12">
                        <label>Select Project:</label>
                        <select id="activeProjectSelect" class="form-control" title="Active Project">
                            <c:forEach items="${pendingProjects}" var="project" varStatus="status">
                                <option value="<c:out value="${project.id}"/>">
                                    <c:out value="${project.name}"/>
                                </option>
                                <c:if test="${status.last}">
                                    <c:set var="projectTasks" value="${project.tasks}"/>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-lg-12">
                        <label>Select Task:</label>
                        <select id="activeTaskSelect" class="form-control" title="Active Task">
                            <c:forEach items="${projectTasks}" var="task">
                                <option value="<c:out value="${task.id}"/>">
                                    <c:out value="${task.name}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <%--Modal body--%>
            <div class="modal-body">
                <h4>Search results:</h4>
                <h3 id="noResults" class="text-center">No results found!</h3>
                <table id="devsResultTable" class="table no-display">
                    <thead>
                    <tr>
                        <th>First Name and Last Name</th>
                        <th>Cost</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
                <hr>
                <div class="row">
                    <div class="col-lg-4">
                        <label>Specialization:</label>
                        <select class="form-control" title="specialization" id="developerSpecialization">
                            <c:forEach items="${specializations}" var="specialization">
                                <option><c:out value="${specialization}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-lg-4">
                        <label>Rank:</label>
                        <select class="form-control" title="rank" id="developerRank">
                            <c:forEach items="${ranks}" var="rank">
                                <option><c:out value="${rank}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-lg-4">
                        <label>Last name:</label>
                        <div class="input-group">
                            <span class="input-group-addon">
                            <span class="glyphicon glyphicon-search"></span>
                            </span>
                            <input type="text" class="form-control" title="lastName" id="developerLastName">
                        </div>
                    </div>
                </div>
            </div>
            <%--end Modal body--%>

            <div class="modal-footer">
                <button type="button" id="searchDevsBtn" class="btn btn-primary">
                    Search
                </button>
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<%--end Form Projects--%>