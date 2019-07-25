/**
 * ClassName: Converter.java
 *
 * Version Information:
 *
 * Date: 2018/06/06
 *
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.converter;

import com.leaihealth.cloud.vo.ResultPage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Joseph S. Kuo
 * @since 3.5.0, 2018/06/06
 */
public interface Converter<T, U> {
    public abstract U convert(T entity);

    default public List<U> toV(final List<T> input) {
        final List<U> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(input)) {
            for (final T source : input) {
                result.add(this.convert(source));
            }
        }
        return result;
    }

    default public List<U> toV(final Set<T> input) {
        final List<U> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(input)) {
            for (final T source : input) {
                result.add(this.convert(source));
            }
        }
        return result;
    }
    /*default public List<U> toV(final Page<T> input) {
        final List<U> result = new ArrayList<>();
            for (final T source : input.getContent()) {
                result.add(this.convert(source));
            }
        return result;
    }*/

    default ResultPage<U> toV(final Page<T> input) {
        ResultPage<U>  page = new ResultPage();
        final List<U> result = new ArrayList<>();
        for (final T source : input.getContent()) {
            result.add(this.convert(source));
        }
        page.setContent(result);
        page.setNumber(input.getNumber());
        page.setTotalElements(input.getTotalElements());
        page.setTotalPages(input.getTotalPages());
        page.setNumberOfElements(input.getNumberOfElements());
        return page;
    }

}
