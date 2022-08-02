package com.org.walk.board;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {

    boolean isPost(long id) throws Exception;

    @Transactional
    PostDto getPost(long postId) throws Exception;

    List<PostDto> getPostList(String keyword, long boardId, Pageable pageable) throws Exception;

    PostDto postPost(PostDto postDto) throws Exception;

    void putPost(PostDto postDto) throws Exception;

    void deletePost(long postId) throws Exception;

}