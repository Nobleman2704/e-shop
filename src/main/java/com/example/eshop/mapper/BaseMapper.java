package com.example.eshop.mapper;
import java.util.List;

/**
 * @author Asadbek
 * @param <E> Entity type parameter
 * @param <RQ> Request DTO parameter
 * @param <RS> Response Dto parameter
 */

public interface BaseMapper <E, RQ, RS>{
    E toEntity(RQ request);
    RS toDto(E entity);
    List<RS> toDto(List<E> entities);
}
