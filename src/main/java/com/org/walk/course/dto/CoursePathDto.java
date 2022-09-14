package com.org.walk.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoursePathDto {
    // 좌표 경로 polyline용.
    List<String> coordinateValue;

    // 소요 시간.
    Long time;

    // 거리
    Long distance;

}