package com.zappos.backoffice.mapper;

/**
 * Convert One domain object to the other
 * @author spark
 *
 * @param <S> source type domain object
 * @param <T> target type domain object
 */
public interface DomainMapper<S, T> {
    public T map(S source);
}
