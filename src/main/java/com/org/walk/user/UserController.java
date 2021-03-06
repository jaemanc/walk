package com.org.walk.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"UserController"})
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;


    @GetMapping("/{keyword}")
    @ApiOperation(value = "get users", notes = "유저 검색")
    @ApiImplicitParam(name="keyword", value = "검색 키워드")
    public ResponseEntity<?> getUserList(
        @RequestParam String keyword
    ) {
        List<UserDto> users = null;
        try {

             users = userService.getUserList(keyword);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<UserDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
    }


    @PostMapping("")
    @ApiOperation(value = "post user", notes = "유저 등록")
    public ResponseEntity<UserDto> postUser(
        @RequestBody UserDto userdto
    ) {

        UserDto user = null;

        try {

            user = userService.postUser(userdto);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<UserDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<UserDto>(user, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get user", notes = "유저 조회")
    @ApiImplicitParam(name="id", dataType = "long", value = "사용자 아이디", example = "0")
    public ResponseEntity<?> getUser(
            @RequestParam long id
    ) {
        UserDto user = null;
        try {

            user = userService.getUser(id);

            if (ObjectUtils.isEmpty(user)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<UserDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "put user", notes = "유저 수정")
    @ApiImplicitParam(name = "id" , dataType= "long", value ="사용자 아이디", example = "0")
    public ResponseEntity<UserDto> putUser(
            @RequestParam long id
        , @RequestBody UserDto userDto
    ) {
        UserDto user = null;

        try {

           boolean flag = userService.isUser(id);

            if (!flag) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            userService.putUser(user);

            // isUser로 대체 필요.
            user = userService.getUser(userDto.getId());

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<UserDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<UserDto>(user, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "user delete" , notes = "사용자 삭제")
    @ApiImplicitParam(name = "id" , dataType= "long", value ="사용자 아이디", example = "0")
    public ResponseEntity<?> deleteUser(
            @RequestParam long id
    ){

        UserDto userDto = null;
        try {

            // isUser로 대체 필요.
            userDto = userService.getUser(id);

            if (ObjectUtils.isEmpty(userDto)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            userService.deleteUser(userDto);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<UserDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<UserDto>(HttpStatus.OK);

    }


}