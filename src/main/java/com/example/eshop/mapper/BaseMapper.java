package com.example.eshop.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 *
 * @author Asadbek
 * @param <E> Entity type parameter
 * @param <RQ> Request DTO parameter
 * @param <RS> Response Dto parameter
 */

public interface BaseMapper <E, RQ, RS>{
    E toEntity(RQ request);
    RS toDto(E entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mergeFromDtoToEntity(RQ request, @MappingTarget E entity);
    List<RS> toDto(List<E> entities);
}
