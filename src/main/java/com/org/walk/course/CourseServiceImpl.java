package com.org.walk.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.walk.course.dto.CourseConfigDto;
import com.org.walk.course.dto.CourseDto;
import com.org.walk.course.mapper.CourseMapper;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContext;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.io.File;
import java.net.http.HttpHeaders;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepositoryCustom courseRepositoryCustom;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<CourseDto> getCourseList(String keyword, Pageable pageable) throws Exception {

        List<CourseDto> courseList = courseRepositoryCustom.searchCourseByKeywordPaging(keyword,pageable);

        return courseList;
    }

    @Override
    public CourseDto getCourse(long id) throws Exception {
        return null;
    }

    @Override
    public CourseDto postCourse(CourseDto courseDto) throws Exception {

        CourseEntity courseEntity = CourseMapper.mapper.toEntity(courseDto);

        courseRepository.save(courseEntity);

        courseDto = CourseMapper.mapper.toDto(courseEntity);

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
    public String getWalkPathApi(String start, String goal, String option) throws Exception {

        String response = null;


        return response;
    }
}
