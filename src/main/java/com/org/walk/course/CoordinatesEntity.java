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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @Column(name = "stopover_latitude", precision=9, scale=7 , columnDefinition = "decimal(9,7)")
    private long stopoverLatitude;

    @Comment("경도_경유")
    @Column(name = "stopover_longitude", precision=10, scale=7 , columnDefinition = "decimal(10,7)")
    private long stopoverLongitude;

    @Builder
    public CoordinatesEntity(long coordinatesId, long startLatitude, long startLongitude, long destLatitude, long destLongitude, long stopoverLatitude, long stopoverLongitude, Set<FileEntity> files) {
        this.coordinatesId = coordinatesId;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.destLatitude = destLatitude;
        this.destLongitude = destLongitude;
        this.stopoverLatitude = stopoverLatitude;
        this.stopoverLongitude = stopoverLongitude;
        this.files = files;
    }

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="coordinates_id", referencedColumnName = "coordinates_id")
    private Set<FileEntity> files;

}
