package com.org.walk.board;

import com.org.walk.board.dto.PostDto;
import com.org.walk.board.dto.PostListResponseDto;
import com.org.walk.board.dto.PostSimpleDto;
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

    @GetMapping("/search")
    @ApiOperation(value="get post list", notes="게시글 검색" )
    @ApiImplicitParam(name="keyword", value="검색 키워드" , required = false, defaultValue = "")
    public ResponseEntity<?> getPostList(
            @RequestParam(required = false) String keyword
            , @RequestParam(required = false, defaultValue = "0") long boardId
            , @PageableDefault(page=0, size =10) Pageable pageable

    ) {

        List<PostListResponseDto> postList = null;

        try {

            System.out.println(pageable.getOffset() + " / " + pageable.getPageSize() + " / " + pageable.getPageNumber());
            System.out.println(pageable.toString()+ " / ");

             postList = postService.getPostList(keyword, boardId, pageable);

             if (postList.size() < 1) {
                return new ResponseEntity<List<PostListResponseDto>>(HttpStatus.NO_CONTENT);
             }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<PostListResponseDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<PostListResponseDto>>(postList, HttpStatus.OK );
    }

    @GetMapping("/{postId}")
    @ApiOperation(value="get post", notes="게시글 조회")
    public ResponseEntity<PostSimpleDto> getPost(
            @PathVariable long postId
    ){

        PostSimpleDto postSimpleDto = null;

        try {

            postSimpleDto = postService.getPost(postId);

            if(ObjectUtils.isEmpty(postSimpleDto)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<PostSimpleDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<PostSimpleDto>(postSimpleDto, HttpStatus.OK );

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
    public ResponseEntity<?> putPost(
            @PathVariable long postId
            ,@RequestBody PostDto postDto
    ) {

        PostSimpleDto postSimpleDto = null;

        try {

            if ( !postService.isPost(postId) ) {
                return new ResponseEntity<PostDto>(HttpStatus.NOT_FOUND);
            }

            postDto.setPostId(postId);
            postService.putPost(postDto);

            postSimpleDto = postService.getPost(postId);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<PostSimpleDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(postSimpleDto, HttpStatus.OK);
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
