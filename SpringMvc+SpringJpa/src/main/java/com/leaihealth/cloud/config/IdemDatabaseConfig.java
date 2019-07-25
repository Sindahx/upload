/**
 * ClassName: MainDatabaseConfig.java
 *
 * Version Information:
 *
 * Date: 2018/06/01
 *
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.leaihealth.cloud.dao.idem.impl.GenericDaoImpl;

/**
 * Main database configuration
 *
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/01
 */
@Configuration
//@PropertySource("file:../conf/idemService.properties")
@PropertySource("file:${icem.home}/idemService.properties")
@EnableTransactionManagement
@EnableJpaRepositories(
        repositoryBaseClass = GenericDaoImpl.class,
        basePackages = IdemDatabaseConfig.REPOSITORY_PACKAGES_TO_SCAN,
        entityManagerFactoryRef = IdemDatabaseConfig.ENTITYMANAGER_BEAN_NAME,
        transactionManagerRef = IdemDatabaseConfig.TRANSACTION_MANAGER_BEAN_NAME)
public class IdemDatabaseConfig extends DatabaseConfig {
    private static final String NAME = "idem";

    /**
     * The entity manager package.
     */
    public static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "com.leaihealth.cloud.entity.idem";

    /**
     * The repository package.
     */
    public static final String REPOSITORY_PACKAGES_TO_SCAN = "com.leaihealth.cloud.dao.idem";

    /**
     * The name of transaction manager bean.
     */
    public static final String TRANSACTION_MANAGER_BEAN_NAME = NAME + "JpaTransactionManager";

    /**
     * The name of entity manager bean.
     */
    public static final String ENTITYMANAGER_BEAN_NAME = NAME + "LocalContainerEntityManagerFactoryBean";

    private static final String PERSISTENCE_UNIT_NAME = NAME + "DataPersistenceUnit";

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getName() {
        return NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getEntityPackageNames() {
        return new String[] {
                // TODO: 測試完成後請移除
                ENTITYMANAGER_PACKAGES_TO_SCAN, "com.leaihealth.cloud.entity.test"
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPersistenceUnitName() {
        return PERSISTENCE_UNIT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Primary
    @Bean(name = NAME + "DataSource", destroyMethod = "")
    public DataSource dataSource() {
        return this.createDataSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = ENTITYMANAGER_BEAN_NAME)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return this.createEntityManagerFactory();
    }

    /**
     * Returns a transaction manager for Spring bean.
     *
     * @return a transaction manager for Spring bean
     */
    @Primary
    @Bean(name = TRANSACTION_MANAGER_BEAN_NAME)
    public JpaTransactionManager transactionManager() {
        return this.createTransactionManager();
    }
}
