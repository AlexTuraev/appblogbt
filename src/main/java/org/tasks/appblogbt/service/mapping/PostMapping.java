package org.tasks.appblogbt.service.mapping;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.tasks.appblogbt.dao.model.PostEntity;
import org.tasks.appblogbt.dto.PostDto;

import java.util.Base64;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapping {

    PostDto toDto(PostEntity post);
    List<PostDto> toDto(List<PostEntity> post);
    PostEntity toModel(PostDto post);

    @AfterMapping
    default void afterMapping(@MappingTarget PostDto postDto) {
        if(postDto.getImage() != null && postDto.getImageType() != null) {
            postDto.setBase64Image("data:" + postDto.getImageType() + ";base64," + Base64.getEncoder().encodeToString(postDto.getImage()));
        }
    }

}
