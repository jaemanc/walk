package com.org.walk.board;

import com.org.walk.board.dto.PostDto;
import com.org.walk.board.dto.PostListResponseDto;
import com.org.walk.board.dto.PostSimpleDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {

    boolean isPost(long id) throws Exception;

    @Transactional
    PostSimpleDto getPost(long postId) throws Exception;

    List<PostListResponseDto> getPostListSearch(String keyword, long boardId, Pageable pageable) throws Exception;

    @Transactional
    PostDto postPost(PostDto postDto) throws Exception;

    @Transactional
    void putPost(PostDto postDto) throws Exception;

    @Transactional
    void deletePost(long postId) throws Exception;

    List<PostSimpleDto> getPostList(Pageable pageable) throws Exception;

}