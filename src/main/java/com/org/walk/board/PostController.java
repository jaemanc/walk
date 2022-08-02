package com.org.walk.board;

import com.org.walk.user.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"PostController"})
@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostServiceImpl postService;

    @GetMapping("/search/{keyword}")
    @ApiOperation(value="get post list", notes="게시글 검색" )
    @ApiImplicitParam(name="keyword", value="검색 키워드")
    public ResponseEntity<?> getPostList(
            @PathVariable String keyword
            , long boardId
            , @PageableDefault(page=0, size =10) Pageable pageable

    ) {

        List<PostDto> postList = null;

        try {

             postList = postService.getPostList(keyword, boardId, pageable);

             if (postList.size() < 1) {
                return new ResponseEntity<List<PostDto>>(HttpStatus.NO_CONTENT);
             }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<PostDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<PostDto>>(postList, HttpStatus.OK );
    }

    @GetMapping("/{postId}")
    @ApiOperation(value="get post", notes="게시글 조회")
    public ResponseEntity<PostDto> getPost(
            @PathVariable long postId
    ){

        PostDto postDto = null;

        try {

            postDto = postService.getPost(postId);

            if(ObjectUtils.isEmpty(postDto)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<PostDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK );

    }

    @PostMapping("")
    @ApiOperation(value="post Post", notes="게시글 등록")
    public ResponseEntity<PostDto> postPost(
            @RequestBody PostDto postDto
    ) {
        try {

            postDto = postService.postPost(postDto);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<PostDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK );
    }

    @PutMapping("/{postId}")
    @ApiOperation(value="put post", notes="게시글 수정")
    public ResponseEntity<PostDto> putPost(
            @PathVariable long postId
            ,@RequestBody PostDto postDto
    ) {
        try {

            if ( !postService.isPost(postId) ) {
                return new ResponseEntity<PostDto>(HttpStatus.NOT_FOUND);
            }

            postService.putPost(postDto);

            postDto = postService.getPost(postId);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<PostDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK );
    }

    @DeleteMapping("/{postId}")
    @ApiOperation(value="delete post", notes="게시글 삭제")
    public ResponseEntity<?> deletePost(
            @PathVariable long postId
    ) {
        try {

            if ( !postService.isPost(postId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            postService.deletePost(postId);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>( HttpStatus.OK );
    }



}





