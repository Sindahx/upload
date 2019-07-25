/**
 * ClassName: GenericDao.java
 *
 * Version Information:
 *
 * Date: 2018/06/01
 *
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.dao.idem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * The customized generic repository.
 *
 * @param <T> the entity type
 * @param <ID> the type of primary ID
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/01
 */
@NoRepositoryBean
public interface GenericDao<T, ID extends Serializable> extends JpaRepository<T, ID> {
    // do nothing

//    List<T> findAll();
}
