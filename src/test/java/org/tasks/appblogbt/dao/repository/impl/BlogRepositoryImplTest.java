package org.tasks.appblogbt.dao.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.tasks.appblogbt.configuration.DbTestConfiguration;
import org.tasks.appblogbt.dao.model.PostEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Import(DbTestConfiguration.class)
class BlogRepositoryImplTest {

    @Autowired
    private BlogRepositoryImpl blogRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<PostEntity> posts = List.of(
            new PostEntity(null, "title1", "content1", 10, "tag1", null, null),
            new PostEntity(null, "title2", "content2", 8, "tag1", null, null),
            new PostEntity(null, "title3", "content3", 5, "tag2", null, null)
    );

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("delete from post");
        jdbcTemplate.execute("""
            insert into post(title, content, tags, count_like) values ('title1', 'content1', 'tag1', 10);
            insert into post(title, content, tags, count_like) values ('title2', 'content2', 'tag1', 8);
            insert into post(title, content, tags, count_like) values ('title3', 'content3', 'tag2', 5);
        """);
    }

    @Test
    void getCountAll() {
        assertEquals(2, blogRepository.getCountAll("tag1"));
        assertEquals(1, blogRepository.getCountAll("tag2"));
        assertEquals(0, blogRepository.getCountAll("tag3"));
        assertEquals(3, blogRepository.getCountAll(null));
        assertEquals(3, blogRepository.getCountAll(""));
    }

    @Test
    void findAll() {
        List<PostEntity> actual = setNullId(blogRepository.findAll("tag1", 2, 0));
        List<PostEntity> expected = List.of(posts.get(0), posts.get(1));
        assertEquals(2, actual.size());
        assertEquals(expected, actual);

        actual = setNullId(blogRepository.findAll("tag1", 1, 0));
        expected = List.of(posts.getFirst());
        assertEquals(1, actual.size());
        assertEquals(expected, actual);

        actual = setNullId(blogRepository.findAll("tag1", 1, 1));
        expected = List.of(posts.get(1));
        assertEquals(1, actual.size());
        assertEquals(expected, actual);

        actual = setNullId(blogRepository.findAll("tag2", 10, 0));
        expected = List.of(posts.get(2));
        assertEquals(1, actual.size());
        assertEquals(expected, actual);

        actual = blogRepository.findAll("tag2", 10, 1);
        assertTrue(actual.isEmpty());

        actual = setNullId(blogRepository.findAll(null, 10, 0));
        expected = new ArrayList<>(posts);
        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("providePostEntities")
    void save(PostEntity newPost) {
//        jdbcTemplate.execute("delete from post");
        blogRepository.save(newPost);
        PostEntity savedPost = blogRepository.findAll(null, 10, 0).stream()
                .filter(post -> post.getTitle().equals(newPost.getTitle()))
                .findFirst()
                .orElse(null);

        assertNotNull(savedPost);
        assertEquals(newPost.getTitle(), savedPost.getTitle());
        assertEquals(newPost.getContent(), savedPost.getContent());
        assertEquals(newPost.getTags(), savedPost.getTags());
        assertEquals(0, savedPost.getCountLike()); // если id=null, то новый пост, лайков еще нет

        savedPost.setContent("new modified content");
        savedPost.setTags("new modified tags");
        blogRepository.save(savedPost);
        PostEntity modifiedSavedPost = blogRepository.findAll(null, 10, 0).stream()
                .filter(post -> post.getId().equals(savedPost.getId()))
                .findFirst()
                .orElse(null);
        assertNotNull(modifiedSavedPost);
        assertEquals("new modified content", modifiedSavedPost.getContent());
        assertEquals("new modified tags", modifiedSavedPost.getTags());
        assertEquals(savedPost.getTitle(), modifiedSavedPost.getTitle());
        assertEquals(savedPost.getId(), modifiedSavedPost.getId());
        assertEquals(0, modifiedSavedPost.getCountLike()); // количество лайков по-прежнему не можем менять
    }

    @Test
    void findById() {
        PostEntity entity = getFirstPost();
        PostEntity actual = blogRepository.findById(entity.getId()).orElse(null);
        assertNotNull(actual);
        assertEquals(entity.getId(), actual.getId());
        assertEquals(entity.getTitle(), actual.getTitle());
        assertEquals(entity.getContent(), actual.getContent());
        assertEquals(entity.getCountLike(), actual.getCountLike());

        actual = blogRepository.findById(-1L).orElse(null);
        assertNull(actual);
    }

    @Test
    void deleteById() {
        PostEntity entity = getFirstPost();
        assertNotNull(entity);

        long id = entity.getId();
        blogRepository.deleteById(entity.getId());
        PostEntity deletedPost = blogRepository.findAll(null, 1, 0).stream()
                .filter(post -> post.getId().equals(id))
                .findFirst().orElse(null);
        assertNull(deletedPost);

        deletedPost = blogRepository.findById(id).orElse(null);
        assertNull(deletedPost);
    }

    @Test
    void addLike() {
        PostEntity entity = getFirstPost();
        assertNotNull(entity);

        blogRepository.addLike(entity.getId(), true);
        PostEntity actual = blogRepository.findById(entity.getId()).orElse(null);
        assertNotNull(actual);
        assertEquals(entity.getCountLike() + 1, actual.getCountLike());

        blogRepository.addLike(entity.getId(), false);
        actual = blogRepository.findById(entity.getId()).orElse(null);
        assertNotNull(actual);
        assertEquals(entity.getCountLike(), actual.getCountLike());

        for(int i=entity.getCountLike(); i >= -2; i--) {
            blogRepository.addLike(entity.getId(), false);
        }
        actual = blogRepository.findById(entity.getId()).orElse(null);
        assertNotNull(actual);
        assertEquals(0, actual.getCountLike());
    }

    private List<PostEntity> setNullId(List<PostEntity> entities) {
        return entities.stream().map(postEntity -> {
            postEntity.setId(null);
            return postEntity;
        }).toList();
    }

    private static Stream<PostEntity> providePostEntities() {
        return Stream.of(
                new PostEntity(null, "title4", "content4", 10, "tag1", null, null),
                new PostEntity(null, "title5", "content5", 8, "tag1", null, null),
                new PostEntity(null, "title6", "content6", 5, "tag2", null, null),
                new PostEntity(null, "title7", "content7", 5, "tag2", null, null)
        );
    }

    private PostEntity getFirstPost() {
        return blogRepository.findAll(null, 1, 0).stream()
                .findFirst().orElse(null);
    }

}