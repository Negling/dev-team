<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--Form Projects--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            <spring:message code="managersCabinet.formProjectLead"/>
            <button name="refreshProjects" data-container-id="#pendingProjectsAccordion"
                    class="refresh-button pull-right"
                    type="button" data-path="/fragments/manage_form_project">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>

    <%--Projects--%>
    <div id="pendingProjectsAccordion" class="panel-group col-lg-9">
        <c:choose>
            <c:when test="${empty pendingProjects}">
                <h3 class="text-center">
                    <spring:message code="managersCabinet.noUnformedProjects"/>
                </h3>
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
                                        <strong><spring:message code="entity.tasks"/></strong>
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
                                                                <spring:message code="entity.taskDescription"/>
                                                            </strong>
                                                        </h4>
                                                        <p class="data-description">
                                                            <c:out value="${task.description}"/>
                                                        </p>

                                                            <%--developers Request--%>
                                                        <div class="col-lg-12">
                                                            <h4 class="text-center">
                                                                <strong>
                                                                    <spring:message code="entity.requestedDevelopers"/>
                                                                </strong>
                                                            </h4>
                                                            <hr>
                                                            <div class="table-responsive">
                                                                <table id="task<c:out value="${task.id}"/>Requested"
                                                                       class="text-center table table-condensed">
                                                                    <thead>
                                                                    <tr>
                                                                        <th class="text-center">
                                                                            <spring:message
                                                                                    code="entity.specialization"/>
                                                                        </th>
                                                                        <th class="text-center">
                                                                            <spring:message code="entity.rank"/>
                                                                        </th>
                                                                        <th class="text-center">
                                                                            <spring:message code="entity.quantity"/>
                                                                        </th>
                                                                    </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    <c:forEach var="taskRequest"
                                                                               items="${task.requestsForDevelopers}">
                                                                        <tr>
                                                                            <td>
                                                                                <c:out value="${taskRequest.specialization}"/>
                                                                            </td>
                                                                            <td>
                                                                                <c:out value="${taskRequest.rank}"/>
                                                                            </td>
                                                                            <td>
                                                                                <c:out value="${taskRequest.quantity}"/>
                                                                            </td>
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
                                                                <strong>
                                                                    <spring:message code="tables.assignedDevelopers"/>
                                                                </strong>
                                                            </h4>
                                                            <hr>
                                                            <div class="table-responsive">
                                                                <h5 class="${not empty task.tasksDevelopmentData ?
                                                                        'text-center no-display':'text-center'}">
                                                                    <strong>
                                                                        <spring:message
                                                                                code="managersCabinet.noAssignedDevs"/>
                                                                    </strong>
                                                                </h5>
                                                                <table id="task<c:out value="${task.id}"/>Hired"
                                                                       class="table table-condensed
                                                                        ${not empty task.tasksDevelopmentData ? '':' no-display'}">
                                                                    <thead>
                                                                    <tr>
                                                                        <th class="text-center">
                                                                            <spring:message code="tables.name"/>
                                                                        </th>
                                                                        <th class="text-center">
                                                                            <spring:message
                                                                                    code="entity.specialization"/>
                                                                        </th>
                                                                        <th class="text-center">
                                                                            <spring:message code="entity.rank"/>
                                                                        </th>
                                                                        <th class="text-center">
                                                                            <spring:message code="tables.hireCost"/>
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
                                                                            <td>
                                                                                <c:out value="${taskData.specialization}"/>
                                                                            </td>
                                                                            <td>
                                                                                <c:out value="${taskData.rank}"/>
                                                                            </td>
                                                                            <td>
                                                                                <fmt:formatNumber type="number"
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
                                                                                    <spring:message
                                                                                            code="managersCabinet.unbind"/>
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
                                        <strong><spring:message code="entity.projectDescription"/></strong>
                                    </h4>
                                    <p class="data-description">
                                        <c:out value="${project.description}"/>
                                    </p>

                                        <%--Project check--%>
                                    <h4 class="page-header text-center">
                                        <strong><spring:message code="tables.projectCheck"/></strong>
                                    </h4>
                                    <div class="col-lg-12 no-padding">
                                        <div class="col-lg-6 project-cost-input">
                                            <label><spring:message code="tables.devsCost"/>:</label>
                                            <div class="input-group">
                                                <input id="project<c:out value="${project.id}"/>DevsCost" type="number"
                                                       class="form-control text-right" value="0" title="devsCost"
                                                       readonly>
                                                <span class="input-group-addon">.00 $</span>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 project-cost-input">
                                            <label><spring:message code="tables.servicesCost"/>:</label>
                                            <div class="input-group">
                                                <input id="project<c:out value="${project.id}"/>Services" type="number"
                                                       title="servicesCost"
                                                       class="form-control text-right" value="0"
                                                       data-project-id="<c:out value="${project.id}"/>">
                                                <span class="input-group-addon">.00 $</span>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 project-cost-input">
                                            <label><spring:message code="tables.taxesCost"/>(+20%):</label>
                                            <div class="input-group">
                                                <input id="project<c:out value="${project.id}"/>Taxes" type="number"
                                                       class="form-control text-right" value="0" title="taxes" readonly>
                                                <span class="input-group-addon">.00 $</span>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 project-cost-input">
                                            <label><spring:message code="tables.totalCost"/>:</label>
                                            <div class="input-group">
                                                <input id="project<c:out value="${project.id}"/>TotalCost" type="number"
                                                       class="form-control text-right" value="0" title="totalCost"
                                                       readonly>
                                                <span class="input-group-addon">.00 $</span>
                                            </div>
                                        </div>
                                    </div>

                                        <%--Accept/Decline--%>
                                    <div class="col-lg-12 no-padding mt-10">
                                        <button type="button" name="declineProject"
                                                value="<c:out value="${project.id}"/>"
                                                data-entity-type="project" class="btn btn-danger w-200 mt-10">
                                            <spring:message code="general.decline"/>
                                        </button>
                                        <button name="acceptProject" value="<c:out value="${project.id}"/>"
                                                class="btn btn-success pull-right w-200 mt-10">
                                            <spring:message code="general.accept"/>
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
            <spring:message code="managersCabinet.searchForAvailableDevs"/>
        </button>
        <p>
            <spring:message code="managersCabinet.searchForAvailableDevsDecs"/>
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
                <h4 class="modal-title">
                    <spring:message code="managersCabinet.devsBindingSettings"/>:
                </h4>
                <hr>
                <div class="row">
                    <div class="col-lg-12">
                        <label><spring:message code="managersCabinet.selectProject"/>:</label>
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
                        <label><spring:message code="managersCabinet.selectTask"/>:</label>
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
                <h4><spring:message code="general.searchResults"/>:</h4>
                <h3 id="noResults" class="text-center"><spring:message code="general.noResults"/></h3>
                <table id="devsResultTable" class="table no-display">
                    <thead>
                    <tr>
                        <th><spring:message code="tables.name"/></th>
                        <th><spring:message code="tables.hireCost"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
                <hr>
                <div class="row">
                    <div class="col-lg-4">
                        <label><spring:message code="entity.specialization"/>:</label>
                        <select class="form-control" title="specialization" id="developerSpecialization">
                            <c:forEach items="${specializations}" var="specialization">
                                <option><c:out value="${specialization}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-lg-4">
                        <label><spring:message code="entity.rank"/>:</label>
                        <select class="form-control" title="rank" id="developerRank">
                            <c:forEach items="${ranks}" var="rank">
                                <option><c:out value="${rank}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-lg-4">
                        <label><spring:message code="registration.lastName"/>:</label>
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
                    <spring:message code="general.search"/>
                </button>
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">
                    <spring:message code="general.back"/>
                </button>
            </div>
        </div>
    </div>
</div>
<%--end Form Projects--%>