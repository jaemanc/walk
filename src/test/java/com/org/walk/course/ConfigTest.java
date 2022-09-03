package com.org.walk.course;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.walk.course.dto.CourseConfigDto;

import java.io.File;

public class ConfigTest {

    public static void main(String[] args) {

        String userHomeDir = System.getProperty("user.home");

        CourseConfigDto configTester = new CourseConfigDto();

        System.out.println("해치웠나..?! "+configTester.getUserHomePath());

        String configFilePath = userHomeDir+"/walkConfig.json";

        try {
            CourseConfigDto courseConfigDto = new ObjectMapper().readValue(new File(configFilePath),CourseConfigDto.class);
            System.out.println(new ObjectMapper().writeValueAsString(courseConfigDto));
        } catch ( Exception e) {

            e.printStackTrace();

        }

    }
}
