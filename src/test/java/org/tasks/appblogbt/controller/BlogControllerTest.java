package org.tasks.appblogbt.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tasks.appblogbt.dto.CommentDto;
import org.tasks.appblogbt.dto.PostDto;
import org.tasks.appblogbt.service.CommentService;
import org.tasks.appblogbt.service.impl.BlogServiceImpl;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BlogController.class)
class BlogControllerTest {

    @MockitoBean
    private BlogServiceImpl blogService;

    @MockitoBean
    CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    private List<PostDto> postDtoList;

    private List<CommentDto> commentDtoList;

    @BeforeEach
    void setUp() {
        postDtoList = List.of(
                new PostDto(1L, "title1", "content1", 10, "tags1", null, null, null, 2),
                new PostDto(1L, "title1", "content1", 10, "tags1", null, null, null, 3)
        );

        commentDtoList = List.of(
                new CommentDto(1L, "comment_content1", 1L),
                new CommentDto(2L, "comment_content2", 1L)
        );
    }

    @Test
    void createOrUpdatePost() throws Exception {
        MockMultipartFile fileImage = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test.jpg".getBytes());

        mockMvc.perform(multipart("/blog").file(fileImage)
                        .param("title", "new title")
                        .param("content", "new content")
                        .param("tags", "new tags")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog"));
    }

    @Test
    void getPost() throws Exception {
        Long id = 1L;
        when(blogService.getById(id)).thenReturn(postDtoList.get(0));
        when(commentService.findByPostId(id)).thenReturn(commentDtoList);

        mockMvc.perform(get("/blog/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("article"));
    }

    @Test
    void deleteById() throws Exception {
        Long id = 1L;

        mockMvc.perform(post("/blog/" + id)
                        .param("_method", "delete")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog"));
    }

    @Test
    void addComment() throws Exception {
        Long id = 1L;
        when(blogService.getById(id)).thenReturn(postDtoList.get(0));
        when(commentService.findByPostId(id)).thenReturn(commentDtoList);

        mockMvc.perform(post("/blog/comment")
                        .param("content", "some comment")
                        .param("postId", id.toString())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("article"));
    }

    @Test
    void addLike() throws Exception {
        Long id = 1L;

        mockMvc.perform(post("/blog/" + id + "/like")
                        .param("like", "true")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog/" + id));
    }

    @Test
    void getEditPage() throws Exception {
        Long id = 1L;
        when(blogService.getById(id)).thenReturn(postDtoList.get(0));

        mockMvc.perform(post("/blog/" + id + "/geteditpostpage"))
                .andExpect(status().isOk())
                .andExpect(view().name("editpost"));
    }

    @Test
    void deleteCommentById() throws Exception {
        CommentDto comment = commentDtoList.get(0);

        when(blogService.getById(comment.getPostId())).thenReturn(postDtoList.get(0));
        when(commentService.findByPostId(comment.getPostId())).thenReturn(commentDtoList);

        mockMvc.perform(post("/blog/comment/" + comment.getId())
                        .param("_method", "delete")
                        .param("postId", comment.getPostId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("article"));
    }

    @Test
    void editCommentById() throws Exception {
        CommentDto comment = commentDtoList.get(0);
        when(blogService.getById(comment.getPostId())).thenReturn(postDtoList.get(0));
        when(commentService.findByPostId(comment.getPostId())).thenReturn(commentDtoList);

        mockMvc.perform(post("/blog/comment/" + comment.getId())
                        .param("_method", "put")
                        .param("postId", comment.getPostId().toString())
                        .param("content", "new modified comment"))
                .andExpect(status().isOk())
                .andExpect(view().name("article"));
    }
}