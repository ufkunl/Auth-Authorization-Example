package com.ufkunl.authandauthorizationtemplate.mapper;

import com.ufkunl.authandauthorizationtemplate.dto.BaseDto;

import java.util.List;

public interface BaseMapper<T,DTO extends BaseDto> {

    public T dtoToEntity(DTO dto);

    public DTO entityToDto(T entity);

    public List<DTO> entityListToDtoList(List<T> entityList);

    public List<T> dtoListToEntityList(List<DTO> dtoList);
}
