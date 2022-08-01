package com.org.walk.util;

import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

public interface EntityMapper<D, E> {

    E toEntity(final D dto);

    D toDto(final E entity);

    List<E> toEntityList(final List<D> dto);

    Set<E> toEntitySet(final Set<D> dto);

    List<D> toDtoList(final List<E> Entity);

    Set<D> toDtoSet(final Set<E> Entity);


}
