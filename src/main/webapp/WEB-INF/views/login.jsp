<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<%@include file="fragments/head.jspf" %>
<link href="<spring:url value="/resources/css/login.css" />" rel="stylesheet">
<title><spring:message code="general.login"/></title>
</head>
<body>
<%@include file="fragments/navbar.jspf" %>

<div class="container">
    <div class="row">
        <security:authorize access="isAuthenticated()">
            <div class="spacer text-center col-lg-6 col-lg-offset-3">
                <div class="col-lg-12">
                    <div class="alert alert-success">
                        <c:if test="${not empty registered}">
                            <spring:message code="login.successfullyRegistered"/>
                        </c:if>
                        <spring:message code="login.alreadyLogged"/>
                    </div>
                </div>
            </div>
        </security:authorize>
        <security:authorize access="isAnonymous()">
            <div class="spacer text-center col-lg-6 col-lg-offset-3">
                <c:if test="${not empty registered}">
                    <div class="col-lg-12">
                        <div class="alert alert-success alert-dismissable fade in">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <spring:message code="login.successfullyRegistered"/>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="col-lg-12">
                        <div class="alert alert-danger alert-dismissable fade in">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <strong><spring:message code="general.error"/>!</strong>
                            <spring:message code="login.badCredentials"/>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty logout}">
                    <div class="col-lg-12">
                        <div class="alert alert-success alert-dismissable fade in">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <strong><spring:message code="general.success"/>!</strong>
                            <spring:message code="login.loggedOut"/>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="col-sm-4 col-sm-offset-4">
                <sform:form action="/login" method="post">
                    <div class="text-center form-group col-12-lg">
                        <h2><spring:message code="general.login"/></h2>
                    </div>
                    <div class="form-group col-md-12">
                        <div class="input-group">
                            <spring:message code="general.email" var="emailPlaceholder"/>
                            <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                            <input type="text" class="form-control" name="email" placeholder="${emailPlaceholder}"
                                   maxlength="20">
                        </div>
                    </div>
                    <div class="form-group col-md-12">
                        <div class="input-group">
                            <spring:message code="general.password" var="passwordPlaceholder"/>
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input type="password" class="form-control" name="password"
                                   placeholder="${passwordPlaceholder}"
                                   maxlength="25">
                        </div>
                    </div>
                    <div class="col-md-12 hidden-sm hidden-xs">
                        <hr class="hr hr-first">
                    </div>
                    <div class="col-md-12">
                        <button id="signupSubmit" type="submit" class="btn btn-success btn-block">
                            <spring:message code="general.submit"/>
                        </button>
                    </div>
                    <div class="col-md-12">
                        <hr class="hr hr-last">
                    </div>
                    <div class="col-lg-12">
                        <h4><spring:message code="login.newMember"/>?
                            <a href="${pageContext.request.contextPath}/registration">
                                <spring:message code="general.register"/>
                            </a>
                        </h4>
                    </div>
                </sform:form>
            </div>
        </security:authorize>
    </div>
</div>

<%@include file="fragments/footer.jspf" %>

</body>
</html>
