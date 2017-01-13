<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<%@include file="fragments/head.jspf" %>
<link href="<spring:url value="/resources/css/registration.css" />" rel="stylesheet">
<title><spring:message code="general.register"/></title>
</head>
<body>
<%@include file="fragments/navbar.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h2 class="dark-grey">Terms and Conditions</h2>
            <p>
                By clicking on "Register" you agree to The Company's' Terms and Conditions
            </p>
            <p>
                While rare, prices are subject to change based on exchange rate fluctuations -
                should such a fluctuation happen, we may request an additional payment. You have the option to request a
                full refund or to pay the new price. (Paragraph 13.5.8)
            </p>
            <p>
                Should there be an error in the description or pricing of a product, we will provide you with a full
                refund (Paragraph 13.5.6)
            </p>
            <p>
                Acceptance of an order by us is dependent on our suppliers ability to provide the product. (Paragraph
                13.5.6)
            </p>
        </div>
        <div class="col-md-6">
            <sform:form action="/registration" method="POST" modelAttribute="customerRegistrationForm">
                <div class="form-group col-12-lg">
                    <h2><spring:message code="registration.createAccount"/></h2>
                </div>
                <div class="form-group col-md-6">
                    <label class="control-label" for="firstName">
                        <spring:message code="registration.firstName"/>:
                    </label>
                    <input id="firstName" name="firstName" type="text" maxlength="20" class="form-control"
                           value="<c:out value="${customerRegistrationForm.firstName}"/>">
                    <sform:errors cssClass="errors-span" path="firstName"/>
                </div>
                <div class="form-group col-md-6">
                    <label class="control-label" for="lastName">
                        <spring:message code="registration.lastName"/>:
                    </label>
                    <input id="lastName" name="lastName" type="text" maxlength="20" class="form-control"
                           value="<c:out value="${customerRegistrationForm.lastName}"/>">
                    <sform:errors cssClass="errors-span" path="lastName"/>
                </div>
                <div class="col-lg-12 hidden-sm hidden-xs">
                    <hr class="hr">
                </div>
                <div class="form-group col-md-6">
                    <label class="control-label" for="phoneNumber">
                        <spring:message code="registration.phone"/>:
                    </label>
                    <input id="phoneNumber" name="phoneNumber" type="text" maxlength="13" class="form-control"
                           placeholder="+388143783094"
                           value="<c:out value="${customerRegistrationForm.phoneNumber}"/>"/>
                    <sform:errors cssClass="errors-span" path="phoneNumber"/>
                </div>
                <div class="form-group col-md-6">
                    <label class="control-label" for="confirmedPhoneNumber">
                        <spring:message code="registration.confirmPhone"/>:
                    </label>
                    <input id="confirmedPhoneNumber" name="confirmedPhoneNumber" type="text" maxlength="13"
                           class="form-control"
                           value="<c:out value="${customerRegistrationForm.confirmedPhoneNumber}"/>">
                    <sform:errors cssClass="errors-span" path="confirmedPhoneNumber"/>
                </div>
                <div class="col-lg-12 hidden-sm hidden-xs">
                    <hr class="hr">
                </div>
                <div class="form-group col-md-6">
                    <label class="control-label" for="email">
                        <spring:message code="general.email"/>:
                    </label>
                    <input id="email" name="email" type="email" maxlength="30" class="form-control"
                           value="<c:out value="${customerRegistrationForm.email}"/>">
                    <sform:errors cssClass="errors-span" path="email"/>
                </div>
                <div class="form-group col-md-6">
                    <label class="control-label" for="confirmedEmail">
                        <spring:message code="registration.confirmEmail"/>:
                    </label>
                    <input id="confirmedEmail" name="confirmedEmail" type="email" maxlength="30" class="form-control"
                           value="<c:out value="${customerRegistrationForm.confirmedEmail}"/>">
                    <sform:errors cssClass="errors-span" path="confirmedEmail"/>
                </div>
                <div class="col-lg-12 hidden-sm hidden-xs">
                    <hr class="hr">
                </div>
                <div class="form-group col-md-6">
                    <label class="control-label" for="password">
                        <spring:message code="general.password"/>:
                    </label>
                    <input id="password" name="password" type="password" maxlength="18" class="form-control"
                           placeholder="<spring:message code="registration.passwordPlaceholder"/>">
                    <sform:errors cssClass="errors-span" path="password"/>
                </div>
                <div class="form-group col-md-6">
                    <label class="control-label" for="confirmedPassword">
                        <spring:message code="registration.confirmPassword"/>:
                    </label>
                    <input id="confirmedPassword" name="confirmedPassword" type="password" maxlength="18"
                           class="form-control">
                    <sform:errors cssClass="errors-span" path="confirmedPassword"/>
                </div>
                <div class="col-lg-12 hidden-sm hidden-xs">
                    <hr>
                </div>
                <div class="col-md-12">
                    <button type="submit" class="btn btn-success btn-block">
                        <spring:message code="registration.createAccountBTN"/>
                    </button>
                </div>
                <div class="col-lg-12">
                    <hr class="hr hr-last">
                </div>
                <div class="col-lg-12">
                    <h4><spring:message code="registration.alreadyRegistered"/>
                        <a href="<spring:url value="/login"/>">
                            <spring:message code="general.login"/>
                        </a>
                    </h4>
                </div>
            </sform:form>
        </div>
    </div>
</div>

<%@include file="fragments/footer.jspf" %>
</body>
</html>