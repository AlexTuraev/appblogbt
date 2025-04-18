package org.tasks.appblogbt.dao.repository;

import org.tasks.appblogbt.dao.model.PostEntity;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {
    Integer getCountAll(String search);

    List<PostEntity> findAll(String search, int pageSize, int pageNumber);

    void save(PostEntity model);

    Optional<PostEntity> findById(long id);

    void deleteById(long id);

    void addLike(long id, boolean like);
}
