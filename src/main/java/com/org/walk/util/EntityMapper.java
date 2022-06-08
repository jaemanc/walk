package com.org.walk.util;

public interface EntityMapper<D, E> {

    E toEntity(final D dto);

    D toDto(final E entity);

}
