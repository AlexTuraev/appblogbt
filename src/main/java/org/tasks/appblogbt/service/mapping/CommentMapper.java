package org.tasks.appblogbt.service.mapping;

import org.mapstruct.Mapper;
import org.tasks.appblogbt.dao.model.CommentEntity;
import org.tasks.appblogbt.dto.CommentDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(CommentEntity entity);
    List<CommentDto> toDto(List<CommentEntity> entities);
    CommentEntity toEntity(CommentDto dto);

}
