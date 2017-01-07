<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="fragments/head.jspf" %>
    <link href="<spring:url value="/resources/css/main-page.css"/>" rel="stylesheet">
    <title>Dev-Team</title>
</head>
<body>
<%--Navbar--%>
<%@include file="fragments/navbar.jspf" %>

<!-- Header Carousel -->
<header id="carousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <li data-target="#carousel" data-slide-to="0" class="active"></li>
        <li data-target="#carousel" data-slide-to="1"></li>
        <li data-target="#carousel" data-slide-to="2"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner">
        <div class="item active">
            <div class="fill slide-1"
                 style="background-image:url('<spring:url value="/resources/images/site/slide_1.jpg"/>');"></div>
            <div class="carousel-caption">
                <h2><spring:message code="main.carousel1"/></h2>
            </div>
        </div>
        <div class="item">
            <div class="fill"
                 style="background-image:url('<spring:url value="/resources/images/site/slide_2.jpg"/>');"></div>
            <div class="carousel-caption">
                <h2><spring:message code="main.carousel2"/></h2>
            </div>
        </div>
        <div class="item">
            <div class="fill"
                 style="background-image:url('<spring:url value="/resources/images/site/slide_3.jpg"/>');"></div>
            <div class="carousel-caption">
                <h2><spring:message code="main.carousel3"/></h2>
            </div>
        </div>
    </div>

    <!-- Controls -->
    <a class="left carousel-control" href="#carousel" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left"></span>
    </a>
    <a class="right carousel-control" href="#carousel" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right"></span>
    </a>
</header>

<%--Description--%>
<section class="description container">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><spring:message code="main.whoWeAre"/></h1>
            <p class="lead">
                <spring:message code="main.whoWeAreDescription"/>
            </p>
            <hr>
        </div>
    </div>
</section>

<!-- Services -->
<!-- The circle icons use Font Awesome's stacked icon classes. For more information, visit http://fontawesome.io/examples/ -->
<section id="services" class="services bg-primary">
    <div class="container">
        <div class="row text-center">
            <div class="col-lg-10 col-lg-offset-1">
                <h2>
                    <spring:message code="main.servicesHead"/>
                </h2>
                <hr class="small">
                <div class="row">
                    <div class="col-md-3 col-sm-6">
                        <div class="service-item">
                                <span class="fa-stack fa-4x">
                                <i class="fa fa-circle fa-stack-2x"></i>
                                <i class="red fa fa-html5 fa-stack-1x text-primary"></i>
                            </span>
                            <h4>
                                <strong>
                                    <spring:message code="main.design"/>
                                </strong>
                            </h4>
                            <p class="text-left">
                                <spring:message code="main.designDescription"/>
                            </p>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6">
                        <div class="service-item">
                                <span class="fa-stack fa-4x">
                                <i class="fa fa-circle fa-stack-2x"></i>
                                <i class="fa fa-microchip fa-stack-1x text-primary"></i>
                            </span>
                            <h4>
                                <strong>
                                    <spring:message code="main.backend"/>
                                </strong>
                            </h4>
                            <p class="text-left">
                                <spring:message code="main.backendDescription"/>
                            </p>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6">
                        <div class="service-item">
                                <span class="fa-stack fa-4x">
                                <i class="fa fa-circle fa-stack-2x"></i>
                                <i class="black fa fa-database fa-stack-1x text-primary"></i>
                            </span>
                            <h4>
                                <strong>
                                    <spring:message code="main.database"/>
                                </strong>
                            </h4>
                            <p class="text-left">
                                <spring:message code="main.databaseDescription"/>
                            </p>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6">
                        <div class="service-item">
                                <span class="fa-stack fa-4x">
                                <i class="fa fa-circle fa-stack-2x"></i>
                                <i class="green fa fa-bug fa-stack-1x text-primary"></i>
                            </span>
                            <h4>
                                <strong>
                                    <spring:message code="main.bugfix"/>
                                </strong>
                            </h4>
                            <p class="text-left">
                                <spring:message code="main.bugfixDescription"/>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Callout -->
<aside class="callout">
    <div class="text-vertical-center">
        <%--<h1>Vertically Centered Text</h1>--%>
    </div>
</aside>

<section class="container">
    <div class="row">
        <div class="col-lg-12">
            <h2 class="text-center">
                <spring:message code="main.reviews"/>
            </h2>
            <hr>
        </div>
        <div class="col-sm-4">
            <img class="img-circle img-responsive img-center"
                 src="<spring:url value="/resources/images/site/customer_1.jpg"/>" alt="">
            <h3><spring:message code="main.customer1"/>
                <small>
                    <spring:message code="main.customer1Job"/>
                </small>
            </h3>
            <p>
                <spring:message code="main.customer1Review"/>
            </p>
        </div>
        <div class="col-sm-4">
            <img class="img-circle img-responsive img-center"
                 src="<spring:url value="/resources/images/site/customer_2.jpg"/>" alt="">
            <h3><spring:message code="main.customer2"/>
                <small>
                    <spring:message code="main.customer2Job"/>
                </small>
            </h3>
            <p>
                <spring:message code="main.customer2Review"/>
            </p>
        </div>
        <div class="col-sm-4">
            <img class="img-circle img-responsive img-center"
                 src="<spring:url value="/resources/images/site/customer_3.jpg"/>" alt="">
            <h3><spring:message code="main.customer3"/>
                <small>
                    <spring:message code="main.customer3Job"/>
                </small>
            </h3>
            <p>
                <spring:message code="main.customer3Review"/>
            </p>
        </div>
    </div>
    <!-- /.row -->
</section>

<%--Footer--%>
<%@include file="fragments/footer.jspf" %>

</body>
</html>
