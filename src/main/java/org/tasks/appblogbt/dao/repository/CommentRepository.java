package org.tasks.appblogbt.dao.repository;

import org.tasks.appblogbt.dao.model.CommentEntity;

import java.util.List;

public interface CommentRepository {

    Integer countCommentByPostId(Long postId);

    void save(CommentEntity entity);

    List<CommentEntity> findByPostId(Long postId);

    void deleteById(long id);

    void updateById(long id, String content);
}
