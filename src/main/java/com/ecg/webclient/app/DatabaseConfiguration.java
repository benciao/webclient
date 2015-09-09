package com.ecg.webclient.app;

import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Konfiguration der Persistenz.
 * 
 * @author arndtmar
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.ecg.webclient.feature.administration.persistence.repo")
public class DatabaseConfiguration
{
    private static final String PROPERTY_NAME_DATABASE_DRIVER                = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD              = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL                   = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME              = "db.username";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT              = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL             = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entity.package.to.scan";

    @Resource
    private Environment         env;

    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory()
    {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN);
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();
        factory.setJpaProperties(hibProperties());

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager()
    {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    private Properties hibProperties()
    {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,
                env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL,
                env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        return properties;
    }
}
