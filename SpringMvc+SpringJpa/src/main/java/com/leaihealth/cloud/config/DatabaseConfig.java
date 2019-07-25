/**
 * ClassName: DatabaseConfig.java
 *
 * Version Information:
 *
 * Date: 2018/06/01
 *
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Database configuration for Spring Framework.
 *
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/01
 */
public abstract class DatabaseConfig {
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "ds.%s.driver";

    private static final String PROPERTY_NAME_DATABASE_URL = "ds.%s.url";

    private static final String PROPERTY_NAME_DATABASE_USERNAME = "ds.%s.username";

    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "ds.%s.password";

    private static final String PROPERTY_NAME_DATABASE_POOL_INITIAL_SIZE = "ds.%s.pool.initial_size";

    private static final String PROPERTY_NAME_DATABASE_POOL_MIN_IDLE = "ds.%s.pool.min_idle";

    private static final String PROPERTY_NAME_DATABASE_POOL_MAX_IDLE = "ds.%s.pool.max_idle";

    private static final String PROPERTY_NAME_DATABASE_POOL_MAX_TOTAL = "ds.%s.pool.max_total";

    private static final String PROPERTY_NAME_DATABASE_POOL_MAX_STATEMENTS = "ds.%s.pool.max_statements";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";

    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL = "hibernate.hbm2ddl.auto";

    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";

    @Autowired
    private Environment env;

    /**
     * Returns the name.
     *
     * @return the name
     */
    protected abstract String getName();

    /**
     * Returns the name of entity package.
     *
     * @return the name of entity package
     */
    protected abstract String[] getEntityPackageNames();

    /**
     * Returns the persistence unit name.
     *
     * @return the persistence unit name
     */
    protected abstract String getPersistenceUnitName();

    /**
     * Returns a new data source for Spring bean
     *
     * @return a new data source
     */
    public abstract DataSource dataSource();

    /**
     * Returns a new entity manager factory for Spring bean.
     *
     * @return a new entity manager factory for Spring bean
     */
    public abstract LocalContainerEntityManagerFactoryBean entityManagerFactory();

    /**
     * Creates a new data source based on the properties.
     *
     * @return a new data source
     */
    protected DataSource createDataSource() {
        final BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(this.env.getRequiredProperty(String.format(PROPERTY_NAME_DATABASE_DRIVER, this.getName())));
        dataSource.setUrl(this.env.getRequiredProperty(String.format(PROPERTY_NAME_DATABASE_URL, this.getName())));
        dataSource.setUsername(this.env.getRequiredProperty(String.format(PROPERTY_NAME_DATABASE_USERNAME, this.getName())));
        dataSource.setPassword(this.env.getRequiredProperty(String.format(PROPERTY_NAME_DATABASE_PASSWORD, this.getName())));
        dataSource.setPoolPreparedStatements(true);

        dataSource.setInitialSize(Integer.valueOf(this.env.getProperty(String.format(PROPERTY_NAME_DATABASE_POOL_INITIAL_SIZE, this.getName()), "3")));
        dataSource.setMinIdle(Integer.valueOf(this.env.getProperty(String.format(PROPERTY_NAME_DATABASE_POOL_MIN_IDLE, this.getName()), "3")));
        dataSource.setMaxIdle(Integer.valueOf(this.env.getProperty(String.format(PROPERTY_NAME_DATABASE_POOL_MAX_IDLE, this.getName()), "60")));
        dataSource.setMaxTotal(Integer.valueOf(this.env.getProperty(String.format(PROPERTY_NAME_DATABASE_POOL_MAX_TOTAL, this.getName()), "150")));
        dataSource.setMaxOpenPreparedStatements(Integer.valueOf(this.env.getProperty(String.format(PROPERTY_NAME_DATABASE_POOL_MAX_STATEMENTS, this.getName()), "1000")));

        // check 5 idle connections every 60 sec to evict idle over 30 min
        dataSource.setTimeBetweenEvictionRunsMillis(60 * DateUtils.MILLIS_PER_SECOND);
        dataSource.setMinEvictableIdleTimeMillis(30 * DateUtils.MILLIS_PER_MINUTE);
        dataSource.setNumTestsPerEvictionRun(5);

        return dataSource;
    }

    /**
     * Creates an entity manager factory.
     *
     * @return an entity manager factory
     */
    protected LocalContainerEntityManagerFactoryBean createEntityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(this.dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(this.getEntityPackageNames());
        entityManagerFactoryBean.setPersistenceUnitName(this.getPersistenceUnitName());

        final HibernateJpaVendorAdapter jpsVenderAdapter = new HibernateJpaVendorAdapter();
        jpsVenderAdapter.setGenerateDdl(true);
        entityManagerFactoryBean.setJpaVendorAdapter(jpsVenderAdapter);

        final Properties hibernateProperties = new Properties();
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, this.env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, this.env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL, this.env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, this.env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
        entityManagerFactoryBean.setJpaProperties(hibernateProperties);

        return entityManagerFactoryBean;
    }

    /**
     * Creates a transaction manager.
     *
     * @return a transaction manager
     */
    protected JpaTransactionManager createTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());
        return transactionManager;
    }
}
