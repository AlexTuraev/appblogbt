package org.tasks.appblogbt.dao.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.tasks.appblogbt.dao.model.PostEntity;
import org.tasks.appblogbt.dao.repository.BlogRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class BlogRepositoryImpl implements BlogRepository {

    private final JdbcTemplate jdbcTemplate;

    public BlogRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String FIND_COUNT_ALL_QUERY = "select count(*) from public.post";
    private final String FIND_COUNT_ALL_QUERY_LIKE_TAG = "select count(*) from public.post where tags like ?";

//    private final String FIND_ALL_PAGING_QUERY = "select id, title, content, count_like, tags, image_type, image from public.post order by id offset ? limit ?";
    private final String FIND_ALL_PAGING_QUERY = "select id, title, content, count_like, tags, image_type, image from public.post order by id limit ? offset ?";
//    private final String FIND_ALL_PAGING_QUERY_LIKE_TAG = "select id, title, content, count_like, tags, image_type, image from public.post where tags like ? order by id offset ? limit ?";
    private final String FIND_ALL_PAGING_QUERY_LIKE_TAG = "select id, title, content, count_like, tags, image_type, image from public.post where tags like ? order by id limit ? offset ?";

    private final String FIND_BY_ID_QUERY = "select id, title, content, count_like, tags, image_type, image from public.post where id = ?";
    private final String SAVE_POST_QUERY = "insert into public.post (title, content, tags, image_type, image) values (?, ?, ?, ?, ?)";
    private final String DELETE_BY_ID_QUERY = "delete from public.post where id = ?";
    private final String UPDATE_LIKE_BY_ID = "update public.post set count_like = greatest(count_like+?, 0) where id = ?";

    private final String UPDATE_POST_BY_ID = "update public.post set title = ?, content = ?, tags = ?, image_type = ?, image = ?, count_like = count_like where id = ?";

    @Override
    public Integer getCountAll(String search) {
        return (search == null || search.isEmpty()) ?
                jdbcTemplate.queryForObject(FIND_COUNT_ALL_QUERY, Integer.class) :
                jdbcTemplate.queryForObject(FIND_COUNT_ALL_QUERY_LIKE_TAG, Integer.class, "%" + search + "%");
    }

    @Override
    public List<PostEntity> findAll(String search, int pageSize, int pageNumber) {
        return (search == null || search.isEmpty()) ?
                jdbcTemplate.query(FIND_ALL_PAGING_QUERY, rowMapperPostEntity, pageSize, pageNumber*pageSize) :
                jdbcTemplate.query(FIND_ALL_PAGING_QUERY_LIKE_TAG, rowMapperPostEntity, "%" + search + "%", pageSize, pageNumber*pageSize);
    }

    @Override
    public void save(PostEntity model) {
        if (model.getId() == null) {
            jdbcTemplate.update(SAVE_POST_QUERY,
                    model.getTitle(), model.getContent(), model.getTags(), model.getImageType(), model.getImage());
        }
        else {
            jdbcTemplate.update(UPDATE_POST_BY_ID,
                    model.getTitle(), model.getContent(), model.getTags(), model.getImageType(), model.getImage(), model.getId());
        }
    }

    @Override
    public Optional<PostEntity> findById(long id) {
        List<PostEntity> entities = jdbcTemplate.query(FIND_BY_ID_QUERY, rowMapperPostEntity, id);
        return entities.isEmpty() ? Optional.empty() : Optional.ofNullable(entities.getFirst());
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }

    @Override
    public void addLike(long id, boolean isLike) {
        int deltaLike = isLike ? 1 : -1;
        jdbcTemplate.update(UPDATE_LIKE_BY_ID, deltaLike, id);
    }

    RowMapper<PostEntity> rowMapperPostEntity = (rs, rowNum) -> new PostEntity(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getInt("count_like"),
            rs.getString("tags"),
            rs.getString("image_type"),
            rs.getBytes("image")
    );

}