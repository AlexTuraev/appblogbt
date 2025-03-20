package org.tasks.appblogbt.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.tasks.appblogbt.configuration.TestMapperConfig;
import org.tasks.appblogbt.dao.model.PostEntity;
import org.tasks.appblogbt.dao.repository.BlogRepository;
import org.tasks.appblogbt.dto.PostDto;
import org.tasks.appblogbt.service.BlogService;
import org.tasks.appblogbt.service.CommentService;
import org.tasks.appblogbt.service.mapping.PostMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BlogServiceImpl.class})
@Import(TestMapperConfig.class)
class BlogServiceImplTest {

    @MockitoBean
    private BlogRepository  blogRepository;

    @MockitoBean
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private PostMapping mapper;

    private List<PostEntity> entities;
    private List<PostDto>  postDtos;

    private final int COUNT_COMMENT1 = 2;
    private final int COUNT_COMMENT2 = 3;

    @BeforeEach
    void setUp() {
        entities = List.of(
                new PostEntity(1L, "title1", "content1", 10, "tags1", null, null),
                new PostEntity(2L, "title2", "content2", 15, "tags2", null, null));

        postDtos = List.of(
                new PostDto(1L, "title1", "content1", 10, "tags1", null, null, null, COUNT_COMMENT1),
                new PostDto(2L, "title2", "content2", 15, "tags2", null, null, null, COUNT_COMMENT2)
        );
    }

    @Test
    void getAllPost() {
        when(blogRepository.findAll("", 2, 0)).thenReturn(entities);
        when(commentService.getCountCommentByPostId(1L)).thenReturn(COUNT_COMMENT1);
        when(commentService.getCountCommentByPostId(2L)).thenReturn(COUNT_COMMENT2);

        List<PostDto> actual = blogService.getAllPost("", 2, 0);

        assertFalse(actual.isEmpty());
        assertEquals(postDtos, actual);

    }

    @Test
    void getById() {
        when(blogRepository.findById(1L)).thenReturn(Optional.of(entities.get(0)));
        when(blogRepository.findById(50L)).thenReturn(Optional.empty());

        PostDto expected = postDtos.get(0);
        expected.setCountComment(null);
        PostDto actualDto = blogService.getById(1L);
        assertEquals(expected, actualDto);

        actualDto = blogService.getById(50L);
        assertTrue(Objects.isNull(actualDto));
    }

}