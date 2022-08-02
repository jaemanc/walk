package com.org.walk.course;

import com.org.walk.user.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.inject.Named;
import java.util.List;

@RequestMapping("/course")
@Api(tags={"CourseController"})
@Controller
public class CourseController {

    private final Logger log_error = LogManager.getLogger("com.error");
    private final Logger log_user = LogManager.getLogger("com.user");

    @Autowired
    CourseServiceImpl courseService;

    @GetMapping("/search/{keyword}")
    @ApiOperation(value = "get courses", notes = "코스 조회")
    @ApiImplicitParam(name="keyword", value = "검색 키워드")
    public ResponseEntity<?> getCourseList(
            @PathVariable String keyword,
            @PageableDefault(page=0, size =10) Pageable pageable
    ) {

        List<CourseDto> courseList = null;

        try {
            courseList = courseService.getCourseList(keyword, pageable);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CourseDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<CourseDto>>(courseList, HttpStatus.OK);

    }


    @PostMapping
    @ApiOperation(value = "post course", notes = "코스 등록")
    public ResponseEntity<CourseDto> postCourse(
            @RequestBody CourseDto courseDto
    ) {

        try {



        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CourseDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "get course",  notes ="코스 조회")
    @ApiImplicitParam(name="id", required = true, dataType = "long", value ="코스 아이디", example = "0")
    public ResponseEntity<CourseDto> getCourse(
            @PathVariable long id
    ) {

        CourseDto courseDto = null;

        try {



        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CourseDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);

    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "put course",  notes ="코스 수정")
    @ApiImplicitParam(name="id", required = true, dataType = "long", value ="코스 아이디", example = "0")
    public ResponseEntity<CourseDto> putCourse(
            @PathVariable long id,
            @RequestBody CourseDto courseDto
    ) {


        try {



        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CourseDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "delete course",  notes ="코스 삭제")
    @ApiImplicitParam(name="id", required = true, dataType = "long", value ="코스 아이디", example = "0")
    public ResponseEntity<CourseDto> deleteCourse(
            @PathVariable long id
    ) {

        CourseDto courseDto = null;

        try {



        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CourseDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);

    }





}
