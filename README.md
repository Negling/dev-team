# Dev-Team

In system were implemented and maintained next functionality:
<ul>
<li><b>Customer</b> presents <b>Technical Task(TT)</b>, which contain list of <b>Tasks</b> with designation of developers quantity and qualification.</li>
<li><b>Manager</b> considers <b>TT</b> and forms <b>Project</b>, by appointing available <b>Developers</b> with requested qualification, whereupon <b>Project</b> cost is calculating, and <b>Customer</b> receives <b>Check</b>.</li>
<li><b>Developer</b> has opportunity to note amount of hours, spent on project.</li>
</ul>

<b>Front-End</b><br />
For creating GUI, and client-server interactions, next frameworks and technologies were used:<br />
HTML5, CSS, Bootstrap, JavaScript, jQuery, AJAX(JSON), JSP, Spring TagLib, JSTL.

<b>Back-End</b><br />
Project architecture built with next frameworks and tools:<br />
Spring(MVC, Security, JDBC, AOP), Jackson, JSR-303 API, Log4j, Lombok, Tomcat JDBC Connection Pool, Maven.

As RDBMS used MySQL, also for development profile and integration tests were used embedded H2 DB.
Current profile can be found/replaced in 'configuration/WebInitializer'.
Totally system configured to support 3 different active profiles: 'prod' - production profile, 'dev' - development profile, 'test' - profile for tests.
Deployment scripts for MySQL can be found in 'sql' directory, and database connection options in 'resources/properties/application.properties' file.

<b>Tests environment</b> <br />
JUnit, Mockito.




