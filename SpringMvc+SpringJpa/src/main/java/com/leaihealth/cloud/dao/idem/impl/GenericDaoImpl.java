/**
 * ClassName: GenericDaoImpl.java
 *
 * Version Information:
 *
 * Date: 2018/06/01
 *
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.dao.idem.impl;

import com.leaihealth.cloud.dao.idem.GenericDao;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * The implementation of {@link GenericDao}.
 *
 * @param <T> the type of entity
 * @param <ID> the type of ID
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/01
 */
@NoRepositoryBean
public class GenericDaoImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements GenericDao<T, ID> {

    private final EntityManager entityManager;

    
    /**
     * Constructor.
     *
     * @param entityInformation the entity information
     * @param theEntityManager the entity manager
     */
    public GenericDaoImpl(final JpaEntityInformation<T, ?> entityInformation, final EntityManager theEntityManager) {
        super(entityInformation, theEntityManager);
        this.entityManager = theEntityManager;
    }

}
