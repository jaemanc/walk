package com.org.walk.file;

import com.org.walk.course.CourseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_file")
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="file_id")
    @Comment("파일 ID")
    private Long fileId;

    @Column(name="file_size")
    @Comment("파일 사이즈")
    private Long fileSize;

    @CreatedDate
    @Column(name="upload_date")
    @Comment("업로드 날짜")
    private Date uploadDate;

    @Column(name="file_loc", length = 150)
    @Comment("파일 위치")
    private String fileLoc;

    @Column(name="is_deleted")
    @Comment("삭제 여부")
    private Character isDeleted;

    @Column(name="user_id")
    @Comment("사용자 ID")
    private Long userId;

    @Column(name="file_latitude")
    @Comment("파일 위도")
    private String fileLatitude;

    @Column(name="file_longitude")
    @Comment("파일 경도")
    private String fileLongitude;

    @Column(name="coordinates_id")
    @Comment("좌표 ID")
    private Long coordinatesId;

    @Column(name="course_id")
    @Comment("코스 ID")
    private Long courseId;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="course_id", insertable = false, updatable = false)
    private CourseEntity course;

    public FileEntity(Long fileId, Long fileSize, Date uploadDate, String fileLoc, Character isDeleted, Long userId, String fileLatitude, String fileLongitude, Long coordinatesId, Long courseId) {
    }

    @Builder
    public FileEntity FileEntity(Long fileId, Long fileSize, Date uploadDate, String fileLoc, Character isDeleted, Long userId, String fileLatitude, String fileLongitude, Long coordinatesId, Long courseId) {
        return new FileEntity(fileId, fileSize, uploadDate, fileLoc, isDeleted, userId, fileLatitude, fileLongitude, coordinatesId, courseId);
    }

}
