<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
            <div class="fill slide-1" style="background-image:url('<spring:url value="/resources/images/site/slide_1.jpg"/>');"></div>
            <div class="carousel-caption">
                <h2>Caption 1</h2>
            </div>
        </div>
        <div class="item">
            <div class="fill" style="background-image:url('<spring:url value="/resources/images/site/slide_2.jpg"/>');"></div>
            <div class="carousel-caption">
                <h2>Caption 2</h2>
            </div>
        </div>
        <div class="item">
            <div class="fill" style="background-image:url('<spring:url value="/resources/images/site/slide_3.jpg"/>');"></div>
            <div class="carousel-caption">
                <h2>Caption 3</h2>
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
            <h1 class="page-header"> Who we are?</h1>
            <p class="lead">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi pharetra fringilla metus at
                eleifend. Nunc elementum pellentesque elit. Phasellus vitae eros ac nibh aliquet tempor vitae sodales orci. Fusce id
                efficitur velit. Pellentesque hendrerit ut ligula ac interdum. Suspendisse potenti. Sed in ipsum sit
                amet odio gravida varius non eget enim.
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
                <h2>Our Services</h2>
                <hr class="small">
                <div class="row">
                    <div class="col-md-3 col-sm-6">
                        <div class="service-item">
                                <span class="fa-stack fa-4x">
                                <i class="fa fa-circle fa-stack-2x"></i>
                                <i class="red fa fa-html5 fa-stack-1x text-primary"></i>
                            </span>
                            <h4>
                                <strong>Service Name</strong>
                            </h4>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6">
                        <div class="service-item">
                                <span class="fa-stack fa-4x">
                                <i class="fa fa-circle fa-stack-2x"></i>
                                <i class="fa fa-microchip fa-stack-1x text-primary"></i>
                            </span>
                            <h4>
                                <strong>Service Name</strong>
                            </h4>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6">
                        <div class="service-item">
                                <span class="fa-stack fa-4x">
                                <i class="fa fa-circle fa-stack-2x"></i>
                                <i class="black fa fa-database fa-stack-1x text-primary"></i>
                            </span>
                            <h4>
                                <strong>Service Name</strong>
                            </h4>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-6">
                        <div class="service-item">
                                <span class="fa-stack fa-4x">
                                <i class="fa fa-circle fa-stack-2x"></i>
                                <i class="green fa fa-bug fa-stack-1x text-primary"></i>
                            </span>
                            <h4>
                                <strong>Service Name</strong>
                            </h4>
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
                        </div>
                    </div>
                </div>
                <!-- /.row (nested) -->
            </div>
            <!-- /.col-lg-10 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.container -->
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
            <h2 class="text-center">Customer Reviews</h2>
            <hr>
        </div>
        <div class="col-sm-4">
            <img class="img-circle img-responsive img-center" src="<spring:url value="/resources/images/site/customer_1.jpg"/>" alt="">
            <h2>Review #1
                <small>Job Title</small>
            </h2>
            <p>These marketing boxes are a great place to put some information. These can contain summaries of what the
                company does, promotional information, or anything else that is relevant to the company. These will
                usually be below-the-fold.</p>
        </div>
        <div class="col-sm-4">
            <img class="img-circle img-responsive img-center" src="<spring:url value="/resources/images/site/customer_2.jpg"/>" alt="">
            <h2>Review #2
                <small>Job Title</small>
            </h2>
            <p>The images are set to be circular and responsive. Fusce dapibus, tellus ac cursus commodo, tortor mauris
                condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis
                euismod. Donec sed odio dui.</p>
        </div>
        <div class="col-sm-4">
            <img class="img-circle img-responsive img-center" src="<spring:url value="/resources/images/site/customer_3.jpg"/>" alt="">
            <h2>Review #3
                <small>Job Title</small>
            </h2>
            <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris
                condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis
                euismod. Donec sed odio dui.</p>
        </div>
    </div>
    <!-- /.row -->
</section>

<%--Footer--%>
<%@include file="fragments/footer.jspf" %>

</body>
</html>
