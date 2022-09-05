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

class PathTest {

    public static void main(String[] args) throws IOException {

        //  요청 전체 주소 예시, : https:/naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start=126.8990034,27.1234&goal=126.8990034,27.224

        String uriPath = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";
        String start = "126.844856,37.5407361";

        String startX = start.substring(0,start.indexOf(","));
        System.out.println("여기여..? "  + startX);

        String goal = "126.8980711,37.5763214";
        String option = "trafast";

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


        String userHomeDir = System.getProperty("user.home");

        CourseConfigDto configTester = new CourseConfigDto();

        System.out.println("해치웠나..?! "+configTester.getUserHomePath());

        String configFilePath = userHomeDir+"/walkConfig.json";

            CourseConfigDto courseConfigDto = new ObjectMapper().readValue(new File(configFilePath),CourseConfigDto.class);
            System.out.println(new ObjectMapper().writeValueAsString(courseConfigDto));

        // X-NCP-APIGW-API-KEY-ID
        String clientId = "f6y05rASDFASDFBJNKASRGIUGHASRFGIUGHAWEFUHIAWERFHUWAERFUHIAWERGIUOH;UJUBIKSARGIBUESFUHIKSADFGBJKLSADFJBHSDFJHKSDFJBKLSDFAJHKLSDFAJBKLSDF";

        // X-NCP-APIGW-API-KEY
        String clientSecret = "4Vwk44bP7GMM94F4713543df46FenbJpwauOT2f2541XGy235GP5XwsV3f51JemCeq";

        String response = client.get().uri(uriBuilder -> uriBuilder.path("")
                .queryParam("start", start)
                .queryParam("goal", goal)
                .queryParam("option",option)
                .build())
                .header("X-NCP-APIGW-API-KEY-ID",courseConfigDto.getClientId())
                .header("X-NCP-APIGW-API-KEY",courseConfigDto.getClientSecret())
                .retrieve() // 응답을 받게 하되,
                .bodyToMono(String.class)// 응답 값을 하나만,
                .block(); // 동기로 받으려 한다.

        System.out.println(" response : " + response);
    }
}