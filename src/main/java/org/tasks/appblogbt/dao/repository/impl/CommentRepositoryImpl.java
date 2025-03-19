package org.tasks.appblogbt.dao.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.tasks.appblogbt.dao.model.CommentEntity;
import org.tasks.appblogbt.dao.repository.CommentRepository;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String COUNT_COMMENT_BY_ID_QUERY= "select count(*) from public.comment where post_id = ?";

    private final String SAVE_COMMENT_QUERY = "insert into public.comment (content, post_id) values (?, ?)";

    private final String FIND_BY_POST_ID_QUERY= "select id, content, post_id from public.comment where post_id = ? order by id";

    private final String DELETE_COMMENT_BY_ID_QUERY= "delete from public.comment where id = ?";

    private final String UPDATE_COMMENT_BY_ID = "update public.comment set content = ?, post_id = post_id where id = ?";

    @Override
    public Integer countCommentByPostId(Long postId) {
        return jdbcTemplate.query(COUNT_COMMENT_BY_ID_QUERY, (rs, rowNum) -> rs.getInt(1), postId).getFirst();
    }

    @Override
    public void save(CommentEntity entity) {
        jdbcTemplate.update(SAVE_COMMENT_QUERY, entity.getContent(), entity.getPostId());
    }

    @Override
    public List<CommentEntity> findByPostId(Long postId) {
        return jdbcTemplate.query(FIND_BY_POST_ID_QUERY,
                (rs, rowNum) -> new CommentEntity(
                        rs.getLong("id"),
                        rs.getString("content"),
                        rs.getLong("post_id")
                ),
                postId);
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_COMMENT_BY_ID_QUERY, id);
    }

    @Override
    public void updateById(long id, String content) {
        jdbcTemplate.update(UPDATE_COMMENT_BY_ID, content, id);
    }

}
