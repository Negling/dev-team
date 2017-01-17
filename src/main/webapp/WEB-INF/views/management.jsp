<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="fragments/head.jspf" %>
    <link href="<spring:url value="/resources/css/management.css" />" rel="stylesheet">
    <title><spring:message code="managersCabinet.title"/></title>
</head>
<body>
<%@include file="fragments/navbar.jspf" %>

<div class="container content-container">
    <h2><spring:message code="managersCabinet.title"/></h2>
    <p><spring:message code="managersCabinet.description"/>.</p>

    <ul class="nav nav-tabs nav-justified">
        <li class="active"><a data-toggle="tab" href="#technicalTasks">
            <spring:message code="customer.technicalTasks"/>
        </a>
        </li>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <spring:message code="customer.Projects"/> <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li>
                    <a href="#formProjects" role="tab" data-toggle="tab">
                        <spring:message code="managersCabinet.formProject"/>
                    </a>
                </li>
                <li>
                    <a href="#runningProjects" role="tab" data-toggle="tab">
                        <spring:message code="cabinet.runningProjects"/>
                    </a>
                </li>
                <li>
                    <a href="#completeProjects" role="tab" data-toggle="tab">
                        <spring:message code="managersCabinet.completeProjects"/>
                    </a>
                </li>
            </ul>
        </li>
        <li><a data-toggle="tab" href="#users">
            <spring:message code="managersCabinet.customers"/></a>
        </li>
        <security:authorize access="hasAnyAuthority('Ultramanager', 'Admin')">
            <li><a data-toggle="tab" href="#developers">
                <spring:message code="managersCabinet.developers"/>
            </a></li>
        </security:authorize>
        <security:authorize access="hasAuthority('Admin')">
            <li><a data-toggle="tab" href="#managers">
                <spring:message code="managersCabinet.managers"/>
            </a></li>
        </security:authorize>
        <li><a data-toggle="tab" href="#settings">
            <spring:message code="cabinet.settings"/>
        </a></li>
    </ul>

    <div class="tab-content">
        <%--Manage Tehnical --%>
        <div id="technicalTasks" class="tab-pane fade in active">
            <%@include file="fragments/management/manage_technical_tasks.jsp" %>
        </div>
        <%--.end Manage new Projects--%>

        <%--Form Projects--%>
        <div id="formProjects" class="tab-pane fade">
            <%@include file="fragments/management/manage_form_project.jsp" %>
        </div>
        <%--end Form Projects--%>

        <%--Running Projects--%>
        <div id="runningProjects" class="tab-pane fade">
            <%@include file="fragments/management/manage_running_projects.jsp" %>
        </div>
        <%--end Running Projects--%>

        <%--Complete Projects--%>
        <div id="completeProjects" class="tab-pane fade">
            <%@include file="fragments/management/manage_complete_projects.jsp" %>
        </div>
        <%--end Complete Projects--%>

        <%--Users--%>
        <div id="users" class="tab-pane fade">
            <div class="row">
                <%--Edit user--%>
                <%--<div class="col-lg-12">
                    <h3 class="page-header lead">Manage Users</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi pharetra fringilla metus at
                        eleifend. Nunc elementum pellentesque elit. Phasellus vitae eros ac nibh aliquet tempor vitae
                        sodales orci. Fusce id efficitur velit. </p>
                    <div class="col-lg-6 no-padding">
                        <div class="input-group">
                            <span class="input-group-addon" id="search-addon">
                                <span class="glyphicon glyphicon-search">
                                </span>
                            </span>
                            <input type="text" class="form-control" placeholder="Search criteria" title="usersSearch"
                                   aria-describedby="search-addon">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <span class="glyphicon glyphicon-envelope">
                                    </span> By Email
                                </button>
                                <button class="btn btn-default" type="button">
                                    <span class="glyphicon glyphicon-phone-alt">
                                    </span> By Phone
                                </button>
                            </div>
                        </div>
                    </div>

                    &lt;%&ndash;Users data&ndash;%&gt;
                    <div class="col-lg-12">

                    </div>
                    &lt;%&ndash;end Users data&ndash;%&gt;

                </div>--%>
                <%--end edit User--%>

            </div>
        </div>
        <%--end Users--%>


        <%--Developers--%>
        <security:authorize access="hasAnyAuthority('Ultramanager', 'Admin')">
            <div id="developers" class="tab-pane fade">

            </div>
        </security:authorize>
        <%--end Developers--%>

        <%--Managers--%>
        <security:authorize access="hasAuthority('Admin')">
            <div id="managers" class="tab-pane fade">

            </div>
        </security:authorize>
        <%--end Managers--%>

        <%--Settings--%>
        <div id="settings" class="tab-pane fade">

        </div>
        <%--end Settings--%>

    </div>
    <%--.end Tab content--%>

</div>

<!-- Decline Technical Task Modal -->
<div class="modal fade" id="declineModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="general.reason"/>:</h4>
            </div>
            <div class="modal-body">
                <textarea id="declineManagerCommentary" class="form-control"
                          placeholder="<spring:message code="general.canBeEmpty"/>" rows="6"></textarea>
                <p id="declineId" class="no-display"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">
                    <spring:message code="general.back"/>
                </button>
                <button id="declineButton" type="button" class="btn btn-warning pull-right"
                        data-dismiss="modal">
                    <spring:message code="general.continue"/>
                </button>
            </div>
        </div>

    </div>
</div>

<%--end Modal--%>

<%@include file="fragments/errorsModal.jspf" %>

<p id="bind" class="no-display"><spring:message code="managersCabinet.bind"/></p>
<p id="unbind" class="no-display"><spring:message code="managersCabinet.unbind"/></p>
<p id="devPath" class="no-display"><spring:url value="/developer?id="/></p>

<script src="<spring:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
<script src="<spring:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<spring:url value="/resources/js/utils.js" />"></script>
<script src="<spring:url value="/resources/js/managers.js" />"></script>
</body>
</html>
