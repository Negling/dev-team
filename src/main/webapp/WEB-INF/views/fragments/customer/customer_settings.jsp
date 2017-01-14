<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--Settings--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead">
            <spring:message code="customersCabinet.settingsLead"/>
        </h3>
    </div>
    <div class="col-md-6 col-md-offset-3 settings-container">

        <div class="col-md-6">
            <label for="firstName"><spring:message code="settings.firstName"/>:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input id="firstName" type="text" class="form-control" maxlength="20"
                       value="<c:out value="${customer.firstName}"/>">
            </div>
        </div>

        <div class="col-md-6">
            <label for="lastName"><spring:message code="settings.lastName"/>:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input id="lastName" type="text" class="form-control" maxlength="20"
                       value="<c:out value="${customer.lastName}"/>">
            </div>
        </div>

        <div class="col-md-12">
            <label for="email"><spring:message code="general.email"/>:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                <input id="email" type="email" class="form-control" maxlength="30" value="<c:out value="${customer.email}"/>">
            </div>
        </div>

        <div class="col-md-12">
            <label for="phone"><spring:message code="settings.phoneNumber"/>:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
                <input id="phone" type="text" class="form-control" maxlength="13"
                       value="<c:out value="${customer.phoneNumber}"/>">
            </div>
        </div>

        <div class="col-md-6">
            <label for="password"><spring:message code="settings.newPassword"/>:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="password" type="password" class="form-control" maxlength="18">
            </div>
        </div>

        <div class="col-md-6">
            <label for="rePassword"><spring:message code="settings.confirmNewPassword"/>:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="rePassword" type="password" class="form-control" maxlength="18">
            </div>
        </div>

        <button type="button" class="btn btn-success pull-right" data-toggle="modal"
                data-target="#passwordModal"><spring:message code="general.continue"/>
        </button>
    </div>
</div>

<!-- Password modal -->
<div id="passwordModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><spring:message code="general.passwordRequired"/></h4>
            </div>
            <div class="modal-body">
                <input id="currentPassword" type="password" class="form-control"
                       placeholder="<spring:message code="general.currentPassword"/>">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">
                    <spring:message code="general.submit"/>
                </button>
            </div>
        </div>
    </div>
</div>
<%--.end Settings--%>