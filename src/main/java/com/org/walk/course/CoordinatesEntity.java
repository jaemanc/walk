package com.org.walk.course;

import com.org.walk.file.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@Table(name="tb_coordinates")
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class CoordinatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coordinates_id")
    private Long coordinatesId;

    @Comment("위도_출발")
    @Column(name = "start_latitude")
    private String startLatitude;

    @Comment("경도_출발")
    @Column(name = "start_longitude")
    private String startLongitude;

    @Comment("위도_도착")
    @Column(name = "dest_latitude")
    private String destLatitude;

    @Comment("경도_도착")
    @Column(name = "dest_longitude")
    private String destLongitude;

    @Comment("경유_경로")
    @Column(name = "transit_route", columnDefinition = "text")
    private String transitRoute;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="coordinates_id", referencedColumnName = "coordinates_id")
    private Set<FileEntity> files;

    @Comment("소요_시간")
    @Column(name = "required_time")
    private Long requiredTime;

    @Comment("총_거리")
    @Column(name = "distance")
    private Long distance;

    @Builder
    public CoordinatesEntity(Long coordinatesId, String startLatitude, String startLongitude, String destLatitude, String destLongitude, String transitRoute, Set<FileEntity> files, Long requiredTime, long distance) {
        this.coordinatesId = coordinatesId;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.destLatitude = destLatitude;
        this.destLongitude = destLongitude;
        this.transitRoute = transitRoute;
        this.files = files;
        this.requiredTime = requiredTime;
        this.distance = distance;
    }

}
