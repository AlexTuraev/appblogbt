package org.tasks.appblogbt.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.tasks.appblogbt.service.impl.mapper.CommentMapperForTest;
import org.tasks.appblogbt.service.impl.mapper.PostMappingForTest;
import org.tasks.appblogbt.service.mapping.CommentMapper;
import org.tasks.appblogbt.service.mapping.PostMapping;

@TestConfiguration
public class TestMapperConfig {

    @Bean
    public PostMapping postMapper() {
        return new PostMappingForTest();
    }

    @Bean
    public CommentMapper commentMapper() {
        return new CommentMapperForTest();
    }

}
