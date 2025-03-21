package org.tasks.appblogbt.dao.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.tasks.appblogbt.configuration.DbTestConfiguration;
import org.tasks.appblogbt.dao.repository.BlogRepository;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
//@DataJpaTest
@SpringBootTest
@Import(DbTestConfiguration.class)
class BlogRepositoryImplTest {

    @Autowired
    private BlogRepositoryImpl blogRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCountAll() {
        int actual = blogRepository.getCountAll(null);
        assertEquals(5, actual);

        actual = blogRepository.getCountAll("");
        assertEquals(5, actual);

        actual = blogRepository.getCountAll("tag1");
        assertEquals(2, actual);

        actual = blogRepository.getCountAll("tag2");
        assertEquals(1, actual);

        actual = blogRepository.getCountAll("tag3");
        assertEquals(2, actual);

        actual = blogRepository.getCountAll("tag4");
        assertEquals(0, actual);
    }

    @Test
    void findAll() {
    }

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void addLike() {
    }
}