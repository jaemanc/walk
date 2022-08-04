package com.org.walk.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public PostDto getPost(long postId) throws Exception {

        // PostEntity postEntity = postRepository.getPostByPostId(postId);
        PostEntity postEntity = postRepositoryCustom.getPost(postId);


        System.out.println(" entity to Dto 변환 시, lazy loading 이 실행됨. ");
        //PostDto postDto = PostMapper.mapper.toDto(postEntity);


        return null;
    }

    @Override
    public List<PostListResponseDto> getPostList(String keyword, long boardId, Pageable pageable) throws Exception {

        List<PostListResponseDto> postList = postRepositoryCustom.getPostList(keyword,boardId,pageable);

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

        Optional<PostEntity> result = postRepository.findById(postDto.getPostId());

        if (result.isPresent() ) {

            PostEntity postEntity = result.get();
            postEntity.updatePost(postDto);

            postRepository.save(postEntity);
        }
    }

    @Override
    public void deletePost(long postId) throws Exception {

        postRepository.deleteById(postId);

    }
}
