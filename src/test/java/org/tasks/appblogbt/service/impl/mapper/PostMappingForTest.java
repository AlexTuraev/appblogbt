package org.tasks.appblogbt.service.impl.mapper;

import org.tasks.appblogbt.dao.model.PostEntity;
import org.tasks.appblogbt.dto.PostDto;
import org.tasks.appblogbt.service.mapping.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostMappingForTest implements PostMapping {

    @Override
    public PostDto toDto(PostEntity post) {
        if ( post == null ) {
            return null;
        }

        PostDto postDto = new PostDto();

        postDto.setId( post.getId() );
        postDto.setTitle( post.getTitle() );
        postDto.setContent( post.getContent() );
        postDto.setCountLike( post.getCountLike() );
        postDto.setTags( post.getTags() );
        postDto.setImageType( post.getImageType() );
        byte[] image = post.getImage();
        if ( image != null ) {
            postDto.setImage( Arrays.copyOf( image, image.length ) );
        }

        afterMapping( postDto );

        return postDto;
    }

    @Override
    public List<PostDto> toDto(List<PostEntity> post) {
        if ( post == null ) {
            return null;
        }

        List<PostDto> list = new ArrayList<PostDto>( post.size() );
        for ( PostEntity postEntity : post ) {
            list.add( toDto( postEntity ) );
        }

        return list;
    }

    @Override
    public PostEntity toModel(PostDto post) {
        if ( post == null ) {
            return null;
        }

        PostEntity postEntity = new PostEntity();

        postEntity.setId( post.getId() );
        postEntity.setTitle( post.getTitle() );
        postEntity.setContent( post.getContent() );
        postEntity.setCountLike( post.getCountLike() );
        postEntity.setTags( post.getTags() );
        postEntity.setImageType( post.getImageType() );
        byte[] image = post.getImage();
        if ( image != null ) {
            postEntity.setImage( Arrays.copyOf( image, image.length ) );
        }

        return postEntity;
    }
}