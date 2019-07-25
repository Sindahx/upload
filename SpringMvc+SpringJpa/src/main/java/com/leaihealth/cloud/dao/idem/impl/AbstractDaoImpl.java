/**
 * ClassName: AbstractDaoImpl.java
 * <p>
 * Version Information:
 * <p>
 * Date: 2018/06/01
 * <p>
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.dao.idem.impl;

import com.leaihealth.cloud.config.IdemDatabaseConfig;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QPageRequest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

/**
 * The implementation of {@link }.
 *
 * @param <T>  the type of entity
 * @param <> the type of ID
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/01
 */
public class AbstractDaoImpl<T> {
    @Autowired
    @Qualifier(IdemDatabaseConfig.ENTITYMANAGER_BEAN_NAME)
    public EntityManager entityManager;

    private Querydsl getQuerydsl() {
        @SuppressWarnings("unchecked") final Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return new Querydsl(this.entityManager, new PathBuilderFactory().create(clazz));
    }

    /**
     * Returns a new JPA Query instance for QueryDsl.
     *
     * @return a new JPA query instance
     */
    protected JPAQuery<T> createQuery() {
        return new JPAQuery<>(this.entityManager);
    }

    protected JPAQueryFactory createQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }

    protected Page<T> getPaginatedResult(final JPAQuery<T> query, final EntityPathBase<T> qEntity, final Pageable pageable) {
        if (pageable == null) {
            return this.getPaginatedResult(query, qEntity, new QPageRequest(0, Integer.MAX_VALUE));
        }

        final long total = query.clone(this.entityManager).fetchCount();
        final JPQLQuery<T> pagedQuery = this.getQuerydsl().applyPagination(pageable, query);
        final List<T> content = total > pageable.getOffset() ? pagedQuery.select(qEntity).fetch() : Collections.emptyList();

        return new PageImpl<>(content, pageable, total);
    }

    protected List<T> getListedResult(final JPAQuery<T> query) {
        final List<T> contents = query.fetch();
        return contents == null ? Collections.emptyList() : contents;
    }

    public Query getQuery(String sql){

        Query query =entityManager.createNativeQuery(sql);
        query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query;
    }

    public Query getQuery(String sql,Class type){
        Query query =entityManager.createNativeQuery(sql);
        query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.aliasToBean(type));
        return query;
    }
}
