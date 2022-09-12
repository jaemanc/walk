package com.org.walk.course.dto;

import com.org.walk.file.FileEntity;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoordinatesDto {

    private long coordinatesId;

    private long startLatitude;

    private long startLongitude;

    private long destLatitude;

    private long destLongitude;

    private long stopoverLatitude;

    private long stopoverLongitude;

    private Set<FileEntity> files;

}
