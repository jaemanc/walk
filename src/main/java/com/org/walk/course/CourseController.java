package com.org.walk.course;

import com.org.walk.course.dto.CoordinatesDto;
import com.org.walk.course.dto.CourseDto;
import com.org.walk.course.dto.CoursePathDto;
import com.org.walk.course.dto.CoursePostDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

@RequestMapping("/course")
@Api(tags={"CourseController"})
@Controller
public class CourseController {

    private final Logger log_error = LogManager.getLogger("com.error");

    @Autowired
    CourseServiceImpl courseService;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/search")
    @ApiOperation(value = "get courses search", notes = "코스 검색 조회")
    public ResponseEntity<?> getCourseList(
            @RequestParam(required = false, defaultValue = "ALL") String searchType,
            @RequestParam(required = false, defaultValue = "") String searchValue,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(page=0, size =10) Pageable pageable
    ) {

        List<CourseDto> courseList = null;

        try {

            courseList = courseService.getCourseList(searchType,searchValue, startDate, endDate, pageable);

            if (ObjectUtils.isEmpty(courseList)) {
                return new ResponseEntity<List<CourseDto>>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CourseDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<CourseDto>>(courseList, HttpStatus.OK);
    }

    @GetMapping("/car-path/{start}/{goal}/{option}")
    @ApiOperation(value = "get courses", notes = "naver 길 찾기 api 호출 (차량) ")
    @ApiImplicitParams({
        @ApiImplicitParam(name="start", value = "출발 위치 y , x"),
        @ApiImplicitParam(name="goal", value = " 도착 위치 y , x"),
        @ApiImplicitParam(name="option", value = " 옵션", defaultValue = "trafast")
    })
    public ResponseEntity<?> getDirectionsApi(
        @PathVariable(required = true) String start,
        @PathVariable(required = true) String goal,
        @PathVariable(required = false) String option
    ) {
        try {

            // 1. naver map api 호출
            if(!StringUtils.hasText(start) || !StringUtils.hasText(goal) ) {
                // 없거나, 위도, 경도 값이 터무니 없이 큰 경우
                // ex) 우리나라 위도,경도에 해당하지 않는 경우.
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // 2. 결과 값을 client로 리턴.  naver api는 자동차 기준 경로를 제공한다.
            String walkResponse = courseService.getDirectionsApi(start, goal, option);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/walk-path")
    @ApiOperation(value = "get walk Path", notes = "tmap 길 찾기 api 호출 (도보) ")
    @ApiImplicitParams({
            @ApiImplicitParam(name="start", value = "출발 위치 y , x"),
            @ApiImplicitParam(name="goal", value = " 도착 위치 y , x"),
    })
    public ResponseEntity<?> getWalkPathApi(
            String start,
            String goal
    ) {

        CoursePathDto coursePathDto = null;

        try {
            if(!StringUtils.hasText(start) || !StringUtils.hasText(goal) ) {
                // 없거나, 위도, 경도 값이 터무니 없이 큰 경우
                // ex) 우리나라 위도,경도에 해당하지 않는 경우.
                // 위도 : 33 ~ 43 / 경도 124 ~ 132

                int _start = Integer.parseInt(start);
                int _goal = Integer.parseInt(goal);
                if ( _start > 43 || _start < 33 || _goal < 124 || _goal > 132) {
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            coursePathDto = courseService.getWalkPathApi(start, goal);

            if (coursePathDto == null) {
                // 너무 먼 거리를 입력해도 다음과 같음.
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception t) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pinrtStream = new PrintStream(out);
            t.printStackTrace(pinrtStream);
            System.out.println(out.toString());
            log_error.error(t.getStackTrace());
            return new ResponseEntity<>(coursePathDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(coursePathDto, HttpStatus.OK);
    }



    @PostMapping(value = "")
    @ApiOperation(value = "post course", notes = "코스 등록")
    public ResponseEntity<?> postCourse(
            @RequestBody CoursePostDto coursePostDto
    ) {

        CourseDto courseDto = null;
        try {

            courseDto = courseService.postCourse(coursePostDto);

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

    @GetMapping(value = "/coordinates/{id}")
    @ApiOperation(value = "get course",  notes ="코스 좌표 조회")
    @ApiImplicitParam(name="id", required = true, dataType = "long", value ="coordinates ID", example = "0")
    public ResponseEntity<CoordinatesDto> getCoordinates(
            @PathVariable Long id
    ) {

        CoordinatesDto coordinatesDto = null;

        try {

            coordinatesDto = courseService.getCoordinates(id);

            if (ObjectUtils.isEmpty(coordinatesDto)) {
                return new ResponseEntity<CoordinatesDto>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CoordinatesDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CoordinatesDto>(coordinatesDto, HttpStatus.OK);
    }

    @PostMapping(value = "/dummy")
    @ApiOperation(value = "course dummy..." , notes = " 코스 더미 등록 ")
    public ResponseEntity<?> dummyCourse (
    ){

        try {

            for (int i = 0 ; i < 1000; i ++ ) {
                CourseDto courseDto = new CourseDto();

                courseDto.setCourseKeyword("Course_keyword_"+i);
                courseDto.setCourseName("Course_name_"+i);
                courseDto.setIsDeleted('N');
                courseDto.setCoordinatesId(1);
                courseDto.setUserId(24);

                // File 먼저 있고 --> course 등록해야하므로,,,, 음...
                // courseDto.setFileId();
                // courseService.postCourse(courseDto);
            }

        } catch ( Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

}