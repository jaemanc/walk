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
    private long coordinatesId;

    @Comment("위도_출발")
    @Column(name = "start_latitude", precision=9, scale=7 , columnDefinition = "decimal(9,7)")
    private long startLatitude;

    @Comment("경도_출발")
    @Column(name = "start_longitude", precision=10, scale=7 , columnDefinition = "decimal(10,7)")
    private long startLongitude;

    @Comment("위도_도착")
    @Column(name = "dest_latitude", precision=9, scale=7 , columnDefinition = "decimal(9,7)")
    private long destLatitude;

    @Comment("경도_도착")
    @Column(name = "dest_longitude", precision=10, scale=7 , columnDefinition = "decimal(10,7)")
    private long destLongitude;

    @Comment("위도_경유")
    @Column(name = "stopover_latitude", columnDefinition = "varchar(10000)")
    private String stopoverLatitude;

    @Comment("경도_경유")
    @Column(name = "stopover_longitude", columnDefinition = "varchar(10000)")
    private String stopoverLongitude;

    @Comment("경유_경로")
    @Column(name = "transit_route", columnDefinition = "varchar(10000)")
    private String transitRoute;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="coordinates_id", referencedColumnName = "coordinates_id")
    private Set<FileEntity> files;

    @Comment("소요_시간")
    @Column(name = "required_time")
    private Date requiredTime;

    @Builder
    public CoordinatesEntity(long coordinatesId, long startLatitude, long startLongitude, long destLatitude, long destLongitude, String stopoverLatitude, String stopoverLongitude, String transitRoute, Set<FileEntity> files, Date requiredTime) {
        this.coordinatesId = coordinatesId;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.destLatitude = destLatitude;
        this.destLongitude = destLongitude;
        this.stopoverLatitude = stopoverLatitude;
        this.stopoverLongitude = stopoverLongitude;
        this.transitRoute = transitRoute;
        this.files = files;
        this.requiredTime = requiredTime;

    }

}
