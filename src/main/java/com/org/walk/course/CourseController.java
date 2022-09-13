package com.org.walk.course;

import com.org.walk.course.dto.CourseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    private final Logger log_user = LogManager.getLogger("com.user");

    @Autowired
    CourseServiceImpl courseService;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/search/{keyword}")
    @ApiOperation(value = "get courses", notes = "코스 조회")
    @ApiImplicitParam(name="keyword", value = "검색 키워드")
    public ResponseEntity<?> getCourseList(
            @PathVariable(required = false) String keyword
            , @PageableDefault(page=0, size =10) Pageable pageable
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

            System.out.println(" 사용자 리퀘스트 값 체크 합니다. : " + start + " / " + goal + " / " + option );


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

        List<String> walk = null;

        try {
            System.out.println("start :: " + start + " / goal :: " + goal);
            if(!StringUtils.hasText(start) || !StringUtils.hasText(goal) ) {
                // 없거나, 위도, 경도 값이 터무니 없이 큰 경우
                // ex) 우리나라 위도,경도에 해당하지 않는 경우.
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // 1. Tmap ( 도보 길찾기 API 호출)
            walk = courseService.getWalkPathApi(start, goal);

            if (walk == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception t) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pinrtStream = new PrintStream(out);
            t.printStackTrace(pinrtStream);
            System.out.println(out.toString());
            log_error.error(t.getStackTrace());
            return new ResponseEntity<>(walk, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(walk, HttpStatus.OK);
    }



    @PostMapping
    @ApiOperation(value = "post course", notes = "코스 등록")
    public ResponseEntity<CourseDto> postCourse(
            @RequestBody CourseDto courseDto
    ) {

        try {

            courseDto = courseService.postCourse(courseDto);

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

    @PostMapping(value = "/dummy")
    @ApiOperation(value = "course dummy..." , notes = " 코스 더미 등록 ")
    public ResponseEntity<?> dummyCourse (
    ){


        try {

            for (int i = 0 ; i < 1000; i ++ ) {
//                CourseEntity courseEntity = CourseEntity.builder()
//                        .name("name"+i)
//                        .address("addr"+i)
//                        .phone("phone"+i)
//                        .email("email"+i+"@gmail.com")
//                        .password("passwd"+i)
//                        .build();

                CourseDto courseDto = new CourseDto();

                courseDto.setCourseKeyword("Course_keyword_"+i);
                courseDto.setCourseName("Course_name_"+i);
                courseDto.setIsDeleted('N');
                courseDto.setCoordinates_id(1);
                courseDto.setUserId(24);

                // File 먼저 있고 --> course 등록해야하므로,,,, 음...
                // courseDto.setFileId();
                courseService.postCourse(courseDto);
            }

        } catch ( Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }



}