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





        System.out.println(" entity to Dto 변환합니다>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //PostDto postDto = PostMapper.mapper.toDto(postEntity);

        // 왜 post 하위의 user 및 board를 가져오냐고..?
        // map struct를 써서 그런 이슈가 있는듯..? dto에서 쓰고자하면 entity의 값을 lazy 로딩해서 가져오니까..


        System.out.println(" entity to Dto 변환합니다>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");


        return null;
    }

    @Override
    public List<PostDto> getPostList(String keyword, long boardId, Pageable pageable) throws Exception {

        List<PostDto> postList = postRepositoryCustom.getPostList(keyword,boardId,pageable);

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
