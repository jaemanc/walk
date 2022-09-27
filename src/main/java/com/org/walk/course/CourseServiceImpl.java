package com.org.walk.course;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.walk.course.dto.CourseConfigDto;
import com.org.walk.course.dto.CourseDto;
import com.org.walk.course.dto.CoursePathDto;
import com.org.walk.course.dto.CoursePostDto;
import com.org.walk.course.mapper.CoordinatesMapper;
import com.org.walk.course.mapper.CourseMapper;
import com.org.walk.file.FileEntity;
import com.org.walk.file.FileService;
import com.org.walk.file.FileServiceImpl;
import com.org.walk.file.dto.FileDto;
import com.org.walk.file.mapper.FileMapper;
import com.org.walk.user.UserEntity;
import com.org.walk.user.mapper.UserMapper;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContext;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.io.File;
import java.net.http.HttpHeaders;
import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepositoryCustom courseRepositoryCustom;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CoordinatesRepository coordinatesRepository;

    @Autowired
    private FileServiceImpl fileService;

    @Override
    public List<CourseDto> getCourseList(String searchType, String searchValue, String startDate, String endDate, Pageable pageable) throws Exception{

        List<CourseDto> courseList = courseRepositoryCustom.searchCourseByKeywordPaging(searchType, searchValue, startDate, endDate, pageable);

        Set<FileEntity> files = new HashSet<>();
        for (int i=0;  i<courseList.size(); i++ ) {
            File file = fileService.getPreviewFile(courseList.get(i).getCourseId());

            if (file == null) {
                continue;
            }
            courseList.get(i).setFile(file);
        }

        return courseList;
    }

    @Override
    public CourseDto getCourse(long id) throws Exception {
        return null;
    }

    @Override
    public CourseDto postCourse(CoursePostDto coursePostDto) throws Exception {
         /*
            1. tb_coordinates - post
            2. tb_course - post  ( tb_coordinates의 ciirdubates_id 값이 필요함 )
         */

        // 좌표 값부터 등록
        CoordinatesEntity coordinatesEntity = CoordinatesEntity.builder()
                .destLatitude(coursePostDto.getCoordinates().getDestLatitude())
                .destLongitude(coursePostDto.getCoordinates().getDestLongitude())
                .startLatitude(coursePostDto.getCoordinates().getStartLatitude())
                .startLongitude(coursePostDto.getCoordinates().getStartLongitude())
                .transitRoute(coursePostDto.getCoordinates().getTransitRoute())
                .requiredTime(coursePostDto.getTime())
                .distance(coursePostDto.getDistance())
                .build();

        coordinatesEntity = coordinatesRepository.save(coordinatesEntity);

        CourseEntity courseEntity = CourseEntity.builder()
                .courseName(coursePostDto.getCourseName())
                .courseKeyword(coursePostDto.getCourseKeyword())
                .coordinates_id(coordinatesEntity.getCoordinatesId())
                .userId(coursePostDto.getUserId())
                .createrId(coursePostDto.getUserId())
                .isDeleted('N')
                .build();

        // course 둥록
        courseRepository.save(courseEntity);

        courseEntity = courseRepositoryCustom.getCourse(courseEntity.getCourseId());

        CourseDto courseDto = CourseMapper.mapper.toDto(courseEntity);

        return courseDto;
    }

    @Override
    public void putCourse(CourseDto courseDto) throws Exception {

    }

    @Override
    public void deleteCourse(long id) throws Exception {

    }

    @Override
    public String getDirectionsApi(String start, String goal, String option) throws Exception {

        String uriPath = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";

        HttpClient httpClient = HttpClient.create().secure(t -> {
            try {
                t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build());
            } catch (SSLException e) {
                System.out.println(e);
            }
        });

        WebClient client = WebClient.builder()
                .baseUrl(uriPath)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        // git에 올라가면 안됨. cloud config 등으로 바꿔야 할 수도 있음.
        String configFilePath = System.getProperty("user.home")+"/walkConfig.json";

        CourseConfigDto courseConfigDto = new ObjectMapper().readValue(new File(configFilePath),CourseConfigDto.class);

        String response = client.get().uri(uriBuilder -> uriBuilder.path("")
                .queryParam("start", start)
                .queryParam("goal", goal)
                .queryParam("option",option)
                .build())
                .header("X-NCP-APIGW-API-KEY-ID",courseConfigDto.getClientId())
                .header("X-NCP-APIGW-API-KEY",courseConfigDto.getClientSecret())
                .retrieve() // 응답을 받게 하되,
                .bodyToMono(String.class)// 응답 값을 하나만,
                .block(); // 동기로 받는다.

        System.out.println(" return values... " + response);

        return response;
    }

    @Override
    public CoursePathDto getWalkPathApi(String start, String goal) throws Exception {

        String uriPath = "https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&format=json&callback=result";

        HttpClient httpClient = HttpClient.create().secure(t -> {
            try {
                t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build());
            } catch (SSLException e) {
                System.out.println(e);
            }
        });

        WebClient client = WebClient.builder()
                .baseUrl(uriPath)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        // git에 올라가면 안됨. cloud config 등으로 바꿔야 할 수도 있음.
        String configFilePath = new CourseConfigDto().getUserHomePath()+"/walkConfig.json";

        CourseConfigDto courseConfigDto = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).readValue(new File(configFilePath),CourseConfigDto.class);

        String startY = start.substring(0,start.indexOf(","));
        String startX = start.substring(start.indexOf(",")+1,start.length());

        String endY = goal.substring(0,goal.indexOf(","));
        String endX = goal.substring(goal.indexOf(",")+1,goal.length());

        String response = client.get().uri(uriBuilder -> uriBuilder.path("")
                        .queryParam ("appKey", courseConfigDto.getClientId())
                        .queryParam("startX", startX)
                        .queryParam("startY", startY)
                        .queryParam("endX",endX)
                        .queryParam("endY",endY)
                        .queryParam("reqCoordType", "WGS84GEO")
                        .queryParam("resCoordType","WGS84GEO")
                        .queryParam("startName","출발지")
                        .queryParam("endName","도착지")
                        .build())
                .retrieve() // 응답을 받게 하되,
                .bodyToMono(String.class)// 응답 값을 하나만,
                .block(); // 동기로 받는다.

        System.out.println(" 리턴 값 : " + response);

        if (response.contains("Exceeded limit on max bytes to buffer")) {
            System.out.println(" 너무 먼 거리는 찾을 수가 없어.. 그거는 도와줄 수가 없어...");
            return null;
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        JSONArray jsonVo = (JSONArray) jsonObject.get("features");

        List<String> transitCoordinates = new ArrayList<>();
        Long allTime = 0L;
        Long totalDistance = 0L;
        for (int i = 0; i < jsonVo.size(); i++) {

            JSONObject jsonObj = (JSONObject) jsonParser.parse(((JSONObject) jsonVo.get(i)).toJSONString());
            JSONObject geometry = (JSONObject) jsonObj.get("geometry");
            JSONObject coordis = (JSONObject) jsonParser.parse(geometry.toJSONString());
            String temp = coordis.get("coordinates").toString();

            temp = temp.replace("[", "").replace("]", "");
            String[] ttemp = temp.split(",");

            for (int j = 0 ; j < ttemp.length; j ++) {
                transitCoordinates.add(ttemp[j]);
            }

            JSONObject times = (JSONObject) jsonObj.get("properties");
            JSONObject timeVal = (JSONObject) jsonParser.parse(times.toJSONString());

            // 초 단위.
            if (!ObjectUtils.isEmpty(timeVal.get("totalTime"))) {
                allTime = Long.valueOf(String.valueOf(timeVal.get("totalTime")));
            }

            // M 단위.
            if (!ObjectUtils.isEmpty(timeVal.get("totalDistance"))) {
                totalDistance = Long.valueOf(String.valueOf(timeVal.get("totalDistance")));
            }

        }


        return new CoursePathDto(transitCoordinates, allTime, totalDistance);
    }
}
