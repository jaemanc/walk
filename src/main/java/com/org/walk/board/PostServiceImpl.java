package com.org.walk.board;

import com.org.walk.board.dto.PostDto;
import com.org.walk.board.dto.PostListResponseDto;
import com.org.walk.board.dto.PostSimpleDto;
import com.org.walk.board.mapper.PostMapper;
import com.org.walk.board.mapper.PostSimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepositoryCustom postRepositoryCustom;

    @Autowired
    PostRepository postRepository;

    @Override
    public boolean isPost(long id) throws Exception {

        return postRepositoryCustom.exist(id);
    }

    @Override
    public PostSimpleDto getPost(long postId) throws Exception {

        // PostEntity postEntity = postRepository.getPostByPostId(postId);
        PostEntity postEntity = postRepositoryCustom.getPost(postId);

        PostSimpleDto postSimpleDto = PostSimpleMapper.mapper.toDto(postEntity);

        return postSimpleDto;
    }

    @Override
    public List<PostListResponseDto> getPostListSearch(String keyword, long boardId, Pageable pageable) throws Exception {

        List<PostListResponseDto> postList = postRepositoryCustom.getPostListSearch(keyword,boardId,pageable);

        return postList;
    }

    @Override
    public PostDto postPost(PostDto postDto) throws Exception {

        PostEntity postEntity = PostMapper.mapper.toEntity(postDto);

        postRepository.save(postEntity);


        return PostMapper.mapper.toDto(postEntity);
    }

    @Override
    public void putPost(PostDto postDto) throws Exception {

        PostEntity postEntity = postRepositoryCustom.getPost(postDto.getPostId());

        if (postEntity != null ) {
            postEntity.updatePost(postDto);
            postRepository.save(postEntity);
        }
    }

    @Override
    public void deletePost(long postId) throws Exception {

        postRepository.deleteById(postId);

    }

    @Override
    public List<PostSimpleDto> getPostList(Pageable pageable) throws Exception {

        List<PostSimpleDto> postSimpleDtoList = postRepositoryCustom.getPostList(pageable);

        return postSimpleDtoList;
    }
}
