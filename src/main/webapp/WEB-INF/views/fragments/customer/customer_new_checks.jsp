<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--New Checks--%>
<div class="row">
    <div class="col-lg-12">
        <h3 class="page-header lead"><spring:message code="customersCabinet.newChecksLead"/>
            <button name="refresh" data-container-id="#newChecksAccordion" data-path="/fragments/customer_new_checks"
                    type="button" class="refresh-button pull-right">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </h3>
    </div>
    <div id="newChecksAccordion" class="no-margin no-padding">
        <div class="panel-group ${empty newChecks ? 'col-xs-12' : 'col-lg-6 col-lg-offset-3 col-md-8 col-md-offset-2'}">
            <c:choose>
                <c:when test="${empty newChecks}">
                    <h3 class="text-center"><spring:message code="customersCabinet.noNewChecks"/></h3>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${newChecks}" var="newCheck" varStatus="loop">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#newChecksAccordion"
                                       href="#check<c:out value="${newCheck.projectId}"/>">
                                        <c:out value="${newCheck.projectName}"/>
                                    </a>
                                </h4>
                            </div>

                                <%--Check Body--%>
                            <div id="check<c:out value="${newCheck.projectId}"/>"
                                 class="panel-collapse collapse${loop.index == 0 ? ' in' : ''}">
                                <div class="panel-body">

                                        <%--devs hire cost--%>
                                    <div class="data-description check-description col-xs-12"
                                         data-toggle="tooltip" data-placement="top" data-delay="0"
                                         title="<spring:message code="entity.checkDevHireCost"/>">
                                        <p class="pull-left">
                                            <spring:message code="tables.devsCost"/>:
                                        </p>
                                        <p class="pull-right">
                                            <fmt:formatNumber type="number" pattern="#"
                                                              value="${newCheck.developersCost}"/>
                                            $
                                        </p>
                                    </div>

                                        <%--services cost--%>
                                    <div class="data-description check-description col-xs-12"
                                         data-toggle="tooltip" data-placement="top" data-delay="0"
                                         title="<spring:message code="entity.checkServicesCost"/>">
                                        <p class="pull-left">
                                            <spring:message code="tables.servicesCost"/>:
                                        </p>
                                        <p class="pull-right">
                                            <fmt:formatNumber type="number" pattern="#"
                                                              value="${newCheck.servicesCost}"/> $
                                        </p>
                                    </div>

                                        <%--taxes--%>
                                    <div class="data-description check-description col-xs-12"
                                         data-toggle="tooltip" data-placement="top" data-delay="0"
                                         title="<spring:message code="entity.checkTaxesCost"/>">
                                        <p class="pull-left">
                                            <spring:message code="tables.taxesCost"/>:
                                        </p>
                                        <p class="pull-right">
                                            <fmt:formatNumber type="number" pattern="#" value="${newCheck.taxes}"/> $
                                        </p>
                                    </div>

                                        <%--total project cost--%>
                                    <div class="data-description check-description col-xs-12"
                                         data-toggle="tooltip" data-placement="top" data-delay="0"
                                         title="<spring:message code="entity.checkTotalCost"/>">
                                        <p class="pull-left">
                                            <spring:message code="tables.totalCost"/>:
                                        </p>
                                        <p class="pull-right">
                                            <fmt:formatNumber type="number" pattern="#"
                                                              value="${newCheck.totalProjectCost}"/> $
                                        </p>
                                    </div>

                                    <button name="declineCheckButton" class="btn btn-danger pull-left" type="button"
                                            value="<c:out value="${newCheck.projectId}"/>">
                                        <spring:message code="general.decline"/>
                                    </button>
                                    <button name="confirmCheckButton" class="btn btn-success pull-right"
                                            value="<c:out value="${newCheck.projectId}"/>" type="button">
                                        <spring:message code="general.confirmCheck"/>
                                    </button>
                                </div>
                            </div>
                                <%--end Check Body--%>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<%--.end New Checks--%>