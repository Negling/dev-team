package ua.devteam.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import java.util.ResourceBundle;

@Configuration
@ComponentScan(basePackages = "ua.devteam.dao.jdbc")
@PropertySource("classpath:properties/application.properties")
public class DataAccessConfiguration {

    private final Logger logger = LogManager.getLogger(DataAccessConfiguration.class);
    private final Environment env;

    @Autowired
    public DataAccessConfiguration(Environment env) {
        this.env = env;
    }

    @Bean(destroyMethod = "close")
    @Profile("prod")
    public DataSource dataSource() {
        DataSource ds = new DataSource();

        ds.setDriverClassName(env.getProperty("db.prod.driver"));
        ds.setUrl(env.getProperty("db.prod.url"));
        ds.setUsername(env.getProperty("db.prod.user"));
        ds.setPassword(env.getProperty("db.prod.password"));
        ds.setInitialSize(5);
        ds.setMaxActive(100);
        ds.setMaxIdle(30);
        ds.setMaxWait(10000);
        ds.setMinEvictableIdleTimeMillis(30000);
        ds.setTimeBetweenEvictionRunsMillis(20000);
        ds.setRemoveAbandonedTimeout(60);
        ds.setLogAbandoned(true);
        ds.setRemoveAbandoned(true);
        ds.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                "org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer");

        return ds;
    }

    @Bean
    @Profile("prod")
    public JdbcTemplate JdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    @Profile("prod")
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    @Profile({"test", "dev"})
    public javax.sql.DataSource databaseForTest() {
        return new EmbeddedDatabaseBuilder().setName("testDB")
                .setType(EmbeddedDatabaseType.H2)
                .ignoreFailedDrops(true)
                .setScriptEncoding("UTF-8")
                .addScript("scripts/sql/create_database.sql")
                .addScript("scripts/sql/generate_content.sql")
                .build();
    }

    @Bean(name = "dataSource", destroyMethod = "close")
    @Profile({"test", "dev"})
    public DataSource dataSourceForTest() {
        DataSource ds = new DataSource();

        ds.setDriverClassName(env.getProperty("db.test.driver"));
        ds.setUrl(env.getProperty("db.test.url"));
        ds.setUsername(env.getProperty("db.test.user"));
        ds.setPassword(env.getProperty("db.test.password"));
        ds.setInitialSize(5);
        ds.setMaxActive(100);
        ds.setMaxIdle(30);
        ds.setMaxWait(10000);
        ds.setMinEvictableIdleTimeMillis(30000);
        ds.setTimeBetweenEvictionRunsMillis(20000);
        ds.setRemoveAbandonedTimeout(60);
        ds.setLogAbandoned(true);
        ds.setRemoveAbandoned(true);
        ds.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                "org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer");

        return ds;
    }

    @Bean(name = "JdbcTemplate")
    @Profile({"test", "dev"})
    public JdbcTemplate jdbcTemplateForTest() {
        return new JdbcTemplate(dataSourceForTest());
    }

    @Bean(name = "transactionManager")
    @Profile({"test", "dev"})
    public PlatformTransactionManager transactionManagerForTest() {
        return new DataSourceTransactionManager(dataSourceForTest());
    }

    @Bean
    public ResourceBundle sqlProperties() {
        return ResourceBundle.getBundle("properties/sql_queries");
    }

    @PostConstruct
    public void tweakH2CompatibilityMode() {
        for (String activeProfile : env.getActiveProfiles()) {
            if (activeProfile.equals("test") || activeProfile.equals("dev")) {

                logger.info("Tweaking MYSQL compatibility mode for H2 database:");

                Mode.getInstance("MYSQL").convertInsertNullToZero = false;

                logger.info("Setting convertInsertNullToZero to false.");
            }
        }
    }
}
