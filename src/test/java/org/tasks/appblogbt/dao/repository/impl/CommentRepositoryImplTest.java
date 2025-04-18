package org.tasks.appblogbt.dao.repository.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.tasks.appblogbt.configuration.DbTestConfiguration;
import org.tasks.appblogbt.dao.model.CommentEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Import(DbTestConfiguration.class)
class CommentRepositoryImplTest {

    @Autowired
    CommentRepositoryImpl commentRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute(
                """
                drop table if exists comment;
                drop table if exists post;
                -- ---------------------------------- --
                create table if not exists post(
                                                   id bigserial primary key,
                                                   title varchar(256) not null,
                    content text,
                    count_like int default 0,
                    tags text,
                    image_type varchar(50),
                    image bytea
                    );
                
                insert into post(title, content, tags, count_like) values ('title1', 'content1', 'tag1', 10);
                insert into post(title, content, tags, count_like) values ('title2', 'content2', 'tag1', 8);
                insert into post(title, content, tags, count_like) values ('title3', 'content3', 'tag2', 5);
                
                create table if not exists comment(
                                                      id bigserial primary key,
                --                                       id bigint primary key,
                                                      content text,
                                                      post_id bigint,
                                                      foreign key (post_id) references post(id) on delete cascade
                    );
        """);

        jdbcTemplate.execute("""
                insert into comment(content, post_id) values ('Комментарий 1_1', 1);
                insert into comment(content, post_id) values ('Комментарий 1_2', 1);
                insert into comment(content, post_id) values ('Комментарий 1_3', 1);
                insert into comment(content, post_id) values ('Комментарий 2_1', 2);
                insert into comment(content, post_id) values ('Комментарий 2_2', 2);
                insert into comment(content, post_id) values ('Комментарий 2_3', 2);
                insert into comment(content, post_id) values ('Комментарий 2_4', 2);
        """);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from comment");
    }

    @Test
    void countCommentByPostId() {
        int actual = commentRepository.countCommentByPostId(1L);
        assertEquals(3, actual);

        actual = commentRepository.countCommentByPostId(2L);
        assertEquals(4, actual);

        actual = commentRepository.countCommentByPostId(3L);
        assertEquals(0, actual);
    }

    @Test
    void save() {
    }

    @Test
    void findByPostId() {
        List<CommentEntity> actual = commentRepository.findByPostId(3L);
        assertTrue(actual.isEmpty());

        actual = commentRepository.findByPostId(1L);
        assertEquals(3, actual.size());

        assertEquals(1L, actual.get(0).getPostId());
        assertEquals(1L, actual.get(1).getPostId());
        assertEquals(1L, actual.get(2).getPostId());
    }

    @Test
    void deleteById() {
        List<CommentEntity> comments = commentRepository.findByPostId(1L);
        assertFalse(comments.isEmpty());
        long id = comments.getFirst().getId();

        commentRepository.deleteById(id);
        comments = commentRepository.findByPostId(1L);
        assertEquals(2, comments.size());
    }

    @Test
    void updateById() {
        List<CommentEntity> comments = commentRepository.findByPostId(2L);
        assertFalse(comments.isEmpty());

        long id = comments.getFirst().getId();
        final String NEW_CONTENT = "new content";

        commentRepository.updateById(id, NEW_CONTENT);

        comments = commentRepository.findByPostId(2L).stream()
                .filter(comment -> comment.getContent().equals(NEW_CONTENT))
                .toList();
        assertEquals(1, comments.size());
        assertEquals(2L, comments.getFirst().getPostId());
    }

}