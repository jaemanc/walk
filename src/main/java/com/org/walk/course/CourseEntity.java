package com.org.walk.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.org.walk.file.FileEntity;
import com.org.walk.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name="tb_course"
    ,indexes = {@Index(name="index_course_id", columnList = "course_id")
                ,@Index(name="index_coordinates_id", columnList = "coordinates_id")}
)
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    @Comment("코스 ID")
    private long courseId;

    @Column(name = "coordinates_id")
    @Comment("좌표 ID")
    private long coordinatesId;

    @Column(name = "course_name", length = 50)
    @Comment("코스 이름")
    private String courseName;

    @Column(name = "course_keyword", length = 100)
    @Comment("코스 키워드")
    private String courseKeyword;

    @Column(name = "user_id")
    @Comment("사용자 ID")
    private long userId;

    @Column(name="is_deleted")
    @Comment("삭제 여부")
    private Character isDeleted;

    @CreatedDate
    @Column(name="created_at")
    @Comment("업로드 날짜")
    private Date createdAt;

    @Column(name="creater_id")
    private long createrId;

    @Column(name="updater", length = 20)
    @Comment("수정자")
    private String updater;

    @LastModifiedDate
    @Column(name="updated_at")
    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date updatedAt;

    @Column(name="file_id")
    @Comment("파일 ID")
    private Long fileId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "user_id", insertable = false, updatable = false, unique = false)
    private UserEntity user;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="coordinates_id", insertable = false, updatable = false)
    private CoordinatesEntity coordinates;

    @Builder
    public CourseEntity(long courseId, long coordinatesId, String courseName, String courseKeyword, long userId, Character isDeleted,
                        Date createdAt, long createrId, String updater, Date updatedAt,Long fileId, UserEntity user, CoordinatesEntity coordinates
    ) {
        this.courseId = courseId;
        this.coordinatesId = coordinatesId;
        this.courseName = courseName;
        this.courseKeyword = courseKeyword;
        this.fileId = fileId;
        this.userId = userId;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.createrId = createrId;
        this.updater = updater;
        this.updatedAt = updatedAt;
        this.user = user;
        this.coordinates = coordinates;
    }

    public void updateCourseFile(Long fileId) {
        if (!ObjectUtils.isEmpty(fileId)) {
            this.fileId = fileId;
        }
    }


}