package org.tasks.appblogbt.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.tasks.appblogbt.configuration.TestMapperConfig;
import org.tasks.appblogbt.dao.model.CommentEntity;
import org.tasks.appblogbt.dao.repository.CommentRepository;
import org.tasks.appblogbt.dto.CommentDto;
import org.tasks.appblogbt.service.CommentService;
import org.tasks.appblogbt.service.mapping.CommentMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CommentServiceImpl.class)
@Import(TestMapperConfig.class)
class CommentServiceImplTest {

    @MockitoBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    private final long POST_ID = 1L;

    @Test
    void getCountCommentByPostId() {
        when(commentRepository.countCommentByPostId(POST_ID)).thenReturn(10);
        int actual = commentService.getCountCommentByPostId(POST_ID);
        assertEquals(10, actual);
    }

    @Test
    void findByPostId() {
        List<CommentEntity> comments = List.of(
                new CommentEntity(2L, "comment1", POST_ID),
                new CommentEntity(5L, "comment2", POST_ID),
                new CommentEntity(10L, "comment3", POST_ID)
        );

        List<CommentDto> expectedDtos = List.of(
                new CommentDto(2L, "comment1", POST_ID),
                new CommentDto(5L, "comment2", POST_ID),
                new CommentDto(10L, "comment3", POST_ID)
        );

        when(commentRepository.findByPostId(POST_ID)).thenReturn(comments);

        List<CommentDto> actualDtos = commentService.findByPostId(POST_ID);
        assertEquals(3, actualDtos.size());
        assertEquals(expectedDtos, actualDtos);
    }

}