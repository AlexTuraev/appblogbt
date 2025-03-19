package org.tasks.appblogbt.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.tasks.appblogbt.dao.model.PostEntity;
import org.tasks.appblogbt.dao.repository.BlogRepository;
import org.tasks.appblogbt.dto.PagingDto;
import org.tasks.appblogbt.dto.PostDto;
import org.tasks.appblogbt.service.BlogService;
import org.tasks.appblogbt.service.CommentService;
import org.tasks.appblogbt.service.mapping.PostMapping;

import java.io.IOException;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Value("${spring.servlet.multipart.max-file-size:10485760}")
    private int maxFileSize;

    private final int DEFAULT_PAGE_SIZE = 3;
    private final int DEFAULT_PAGE_NUMBER = 0;

    private final BlogRepository blogRepository;
    private final PostMapping mapper;
    private final CommentService commentService;

    public BlogServiceImpl(BlogRepository blogRepository, PostMapping mapper, CommentService commentService) {
        this.blogRepository = blogRepository;
        this.mapper = mapper;
        this.commentService = commentService;
    }

    @Override
    public List<PostDto> getAllPost(String search, Integer pageSize, Integer pageNumber) {
        List<PostEntity> entities = blogRepository.findAll(search, pageSize, pageNumber);
        List<PostDto> postDtos = mapper.toDto(entities);
        postDtos.forEach(postDto -> postDto.setCountComment(commentService.getCountCommentByPostId(postDto.getId())));
        return postDtos;
    }

    @Override
    public void save(PostDto postDto, MultipartFile file) throws IOException {
        if (isFileSizeValid(file)) {
            PostEntity postEntity = mapper.toModel(postDto);
            postEntity.setImage(file.getBytes());
            postEntity.setImageType(file.getContentType());
            blogRepository.save(postEntity);
        }
        else {
            throw new RuntimeException("Слишком большой файл. Размер не более: " + maxFileSize);
        }

    }

    @Override
    public PostDto getById(long id) {
        return blogRepository.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public void deleteById(long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Model getAllPostModel(Model model, String search, Integer pageSize, Integer pageNumber) {
        int pageLimit = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        int pageNo = pageNumber == null ? DEFAULT_PAGE_NUMBER : pageNumber-1;

        List<PostDto> posts =  getAllPost(search, pageLimit, pageNo);
        Integer total = blogRepository.getCountAll(search);

        model.addAttribute("posts", posts);
        model.addAttribute("paging", new PagingDto(pageLimit, pageNo+1, total));
        return model;
    }

    @Override
    public void addLike(long id, boolean like) {
        blogRepository.addLike(id, like);
    }

    private boolean isFileSizeValid(MultipartFile file) {
        return file.getSize() <= maxFileSize;
    }

}
