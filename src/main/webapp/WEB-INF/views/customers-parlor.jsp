<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../fragments/head.jspf" %>
<link href="<spring:url value="/resources/css/customers-parlor.css" />" rel="stylesheet">
<title>Parlor</title>
</head>
<body>
<%@include file="../fragments/navbar.jspf" %>

<div class="container content-container">
    <h2><spring:message code="customersParlor.parlor"/></h2>
    <p>Here you can manage your personal information, create technical tasks, etc.</p>

    <ul class="nav nav-tabs nav-justified">
        <li class="active"><a data-toggle="tab" href="#createProject">Create Project</a></li>
        <li><a data-toggle="tab" href="#activeTasks">Active Tasks</a></li>
        <li><a data-toggle="tab" href="#history">History</a></li>
        <li><a data-toggle="tab" href="#settings">Settings</a></li>
    </ul>

    <div class="tab-content">
        <div id="createProject" class="tab-pane fade in active">
            <div class="row">
                <div class="new-project col-lg-8 col-lg-offset-2">
                    <form id="projectForm" class="form-horizontal">
                        <div class="text-center col-lg-12">
                            <h3 class="page-header"><strong><spring:message code="customersParlor.general"/></strong>
                            </h3>
                        </div>
                        <div class="col-lg-12 form-group">
                            <label class="control-label col-lg-2" for="projectName">Project name:</label>
                            <div class="col-lg-10">
                                <input id="projectName" name="projectName" type="text" class="form-control"
                                       placeholder="Minimum 10 characters">
                            </div>
                        </div>
                        <div class="col-lg-12 form-group">
                            <label class="control-label col-lg-2" for="projectDescription">Project description:</label>
                            <div class="col-lg-10">
                                <textarea id="projectDescription" name="projectDescription" class="form-control"
                                          placeholder="Minimum 20 characters" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="text-center col-lg-12">
                            <h3 class="page-header"><strong>Tasks</strong></h3>
                        </div>
                        <div class="col-lg-12">
                            <div id="tasks" class="panel-group">
                            </div>
                        </div>
                        <div class="text-center col-lg-12">
                            <button class="btn btn-primary btn-sm add-task-btn" type="button" data-toggle="modal"
                                    data-target="#addTaskModal">Add Task
                            </button>
                        </div>
                        <button id="submitProject" type="submit" class="btn btn-success submit-project-btn"
                                disabled="disabled">Submit
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <%--Active projects--%>
        <div id="activeTasks" class="tab-pane fade">
            <p class="lead">All projects which is currently under progress.</p>
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Appointment</th>
                            <th>Price</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Fix up site content layout</td>
                            <td>100 $</td>
                            <td>14.09.2014</td>
                            <td>14.10.2014</td>
                            <td>Completed</td>
                        </tr>
                        <tr>
                            <td>Create time managing application</td>
                            <td>30 000 $</td>
                            <td>14.09.2014</td>
                            <td>18.09.2014</td>
                            <td>Canceled</td>
                        </tr>
                        <tr>
                            <td>Do stuff</td>
                            <td>19.99 $</td>
                            <td>14.09.2016</td>
                            <td>Unknown</td>
                            <td>Pending</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%--Active Projects-End--%>

        <%--History--%>
        <div id="history" class="tab-pane fade">
            <p class="lead">Here you can track all your orders.</p>
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Appointment</th>
                            <th>Price</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Fix up site content layout</td>
                            <td>100 $</td>
                            <td>14.09.2014</td>
                            <td>14.10.2014</td>
                            <td>Completed</td>
                        </tr>
                        <tr>
                            <td>Create time managing application</td>
                            <td>30 000 $</td>
                            <td>14.09.2014</td>
                            <td>18.09.2014</td>
                            <td>Canceled</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%--History-End--%>

        <%--Settings--%>
        <div id="settings" class="tab-pane">
            <p class="lead">Here you can edit your information. If you want to change some field directly, just
                leave others empty.</p>
            <div class="row">
                <div class="col-lg-6">

                    <form>
                        <label for="email">E-mail</label>
                        <div class="input-group">
                            <span class="input-group-addon">user@email.com</span>
                            <input id="email" type="email" class="form-control" placeholder="New mail">
                        </div>
                        <label for="phone">Phone number</label>
                        <div class="input-group">
                            <span class="input-group-addon">+3 8814-378-3094</span>
                            <input id="phone" type="text" class="form-control" placeholder="New phone number">
                        </div>
                        <label for="password">Password</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input id="password" type="password" class="form-control" placeholder="New password">
                        </div>
                        <div class="input-group">
                            <button type="button" class="btn btn-success w-200" data-toggle="modal"
                                    data-target="#myModal">Confirm
                            </button>
                        </div>


                    </form>
                </div>
                <%--.end column--%>
                <div class="col-lg-6">
                    <div class="alert settings-alert alert-danger alert-dismissable fade in">
                        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        <strong>Danger!</strong> This alert box could indicate a dangerous action.
                    </div>
                </div>
            </div>
        </div>
        <%--.end Settings--%>
    </div>
    <%--.end Tab content--%>
</div>

<%--Modals--%>
<!-- Password modal -->
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">To continue please enter your current password</h4>
            </div>
            <div class="modal-body">
                <input id="currentPassword" type="password" class="form-control"
                       placeholder="Current password">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Submit</button>
            </div>
        </div>
    </div>
</div>

<%--Task modal--%>
<div id="addTaskModal" class="text-center modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="no-borders modal-header">
                <h4 class="modal-title"><strong>Task Name</strong></h4>
                <input id="addTaskName" class="form-control" type="text" placeholder="Minimum 10 characters">
            </div>
            <div class="no-borders modal-body">
                <h4 class="text-center"><strong>Developers</strong></h4>
                <hr>
                <table class="table table-condensed table-responsive">
                    <thead>
                    <tr>
                        <th class="text-center">Specialization</th>
                        <th class="text-center">Rank</th>
                        <th class="text-center">Quantity</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><select class="form-control" title="Specialization">
                            <option>Designer</option>
                            <option>Web-Dev</option>
                            <option>Backend</option>
                            <option>Database</option>
                        </select></td>
                        <td><select class="form-control" title="Rank">
                            <option>Senior</option>
                            <option>Middle</option>
                            <option>Junior</option>
                        </select></td>
                        <td><input class="form-control" type="number" min="1" max="100" value="1" placeholder="Max 100"
                                   title="Quantity">
                        </td>
                        <td class="w-100"></td>
                    </tr>
                    </tbody>
                </table>
                <button id="addDevButton" class="btn btn-default btn-block"><span
                        class="glyphicon glyphicon-plus"></span></button>
            </div>
            <div class="no-borders modal-footer">
                <h4 class="text-center"><strong>Description</strong></h4>
                <textarea id="addTaskDescription" class="form-control" placeholder="Minimum 20 characters"
                          rows="6"></textarea>
                <button id="taskBackButton" type="button" class="w-100 pull-left mt-10 btn btn-primary">Back
                </button>
                <button id="addTaskButton" type="button" class="w-100 mt-10 btn btn-success" disabled="disabled">Add
                </button>
            </div>
        </div>
    </div>
</div>

<%--Hidden task prototype--%>
<div id="taskPrototype" class="no-display panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse"></a>
        </h4>
    </div>
    <div class="panel-collapse collapse">
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-condensed table-responsive">
                    <thead>
                    <tr>
                        <th>Specialization</th>
                        <th>Rank</th>
                        <th>Quantity</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <textarea class="form-control" placeholder="Task Description" rows="6"
                      readonly></textarea>
            <input title="task-name"
                   hidden>
            <button type="button" class="mt-10 pull-left btn btn-danger btn-sm">Drop Task
            </button>
        </div>
    </div>
</div>

<%@include file="../fragments/footer.jspf" %>

<script src="<spring:url value="/resources/js/customers-parlor.js" />"></script>
</body>
</html>