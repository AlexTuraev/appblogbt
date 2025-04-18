package org.tasks.appblogbt.service.impl;

import org.springframework.stereotype.Service;
import org.tasks.appblogbt.dao.repository.CommentRepository;
import org.tasks.appblogbt.dto.CommentDto;
import org.tasks.appblogbt.service.CommentService;
import org.tasks.appblogbt.service.mapping.CommentMapper;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;

    public CommentServiceImpl(CommentRepository repository, CommentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Integer getCountCommentByPostId(Long postId) {
        return repository.countCommentByPostId(postId);
    }

    @Override
    public void addComment(CommentDto commentDto) {
        repository.save(mapper.toEntity(commentDto));
    }

    @Override
    public List<CommentDto> findByPostId(Long postId) {
        return repository.findByPostId(postId)
                .stream().map(mapper::toDto).toList();
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateById(long id, String content) {
        repository.updateById(id, content);
    }
}
