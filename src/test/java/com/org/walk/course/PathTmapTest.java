package com.org.walk.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.walk.course.dto.CourseConfigDto;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.IOException;

public class PathTmapTest {

    public static void main(String[] args) throws IOException {

    String start = "126.844856,37.5407361";


    String goal = "126.8980711,37.5763214";
    String option = "trafast";

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
    String configFilePath = System.getProperty("user.home")+"/walkConfig.json";

    CourseConfigDto courseConfigDto = new ObjectMapper().readValue(new File(configFilePath),CourseConfigDto.class);


    String startX = start.substring(0,start.indexOf(","));
    String startY = start.substring(start.indexOf(",")+1,start.length());

    String endX = goal.substring(0,goal.indexOf(","));
    String endY = goal.substring(goal.indexOf(",")+1,goal.length());


    String response = client.get().uri(uriBuilder -> uriBuilder.path("")
                    .queryParam ("appKey", courseConfigDto.getClientId())
                    .queryParam("startX", startX)
                    .queryParam("startY", startY)
                    .queryParam("endX",endX)
                    .queryParam("endY",endY)
                    .queryParam("reqCoordType", "WGS84GEO")
                    .queryParam("resCoordType","EPSG3857")
                    .queryParam("startName","출발지")
                    .queryParam("endName","도착지")
                    .build())
            .retrieve() // 응답을 받게 하되,
            .bodyToMono(String.class)// 응답 값을 하나만,
            .block(); // 동기로 받는다.

        System.out.println(" return values... " + response);
    }

}
