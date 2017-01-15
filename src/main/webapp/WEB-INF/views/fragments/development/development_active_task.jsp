<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--Task Under Progress--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            Current Task
            <button name="refresh" data-container-id="#currentTask" type="button" class="refresh-button pull-right"
                    data-path="/fragments/development_active_task">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>

    <%--Technical Tasks--%>
    <div id="currentTask" class="panel-group col-lg-8 col-lg-offset-2 col-xs-12">
        <c:choose>
            <c:when test="${empty activeTaskData}">
                <div class="text-center">
                    <h3>You aren't assigned on task!</h3>
                </div>
            </c:when>
            <c:otherwise>
                <div class="bordered-container col-lg-12">
                    <h4><strong>Task Name:</strong></h4>
                    <p class="data-description"><c:out value="${activeTaskData.taskName}"/></p>

                    <h4><strong>Task Description:</strong></h4>
                    <p class="data-description">
                        <c:out value="${activeTaskData.taskDescription}"/>
                    </p>

                    <div class="form-group col-xs-4 no-padding">
                        <label class="control-label" for="hoursSpent">
                            Hours Spent:
                        </label>
                        <input id="hoursSpent" type="number" class="form-control" value="0" min="1"
                               max="999">
                    </div>
                    <button id="markComplete" type="button" value="<c:out value="${activeTaskData.projectTaskId}"/>"
                            class="btn btn-success pull-right mt-25">
                        Mark As Complete
                    </button>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <%--end Technical Tasks--%>
</div>
<%--.end Task Under Progress--%>
