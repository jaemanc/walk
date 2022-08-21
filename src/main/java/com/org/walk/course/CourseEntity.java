package com.org.walk.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.org.walk.file.FileEntity;
import com.org.walk.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@Table(name="tb_course")
@DynamicUpdate
@ToString
@DynamicInsert
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    @Comment("코스 ID")
    private long courseId;

    @Column(name = "coordinates_id")
    @Comment("좌표 ID")
    private long coordinates_id;

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
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private UserEntity user;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="coordinates_id", insertable = false, updatable = false)
    private CoordinatesEntity coordinates;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private Set<FileEntity> files;


    @Builder
    public CourseEntity(long courseId, long coordinates_id, String courseName, String courseKeyword, long userId, Character isDeleted, String updater, Date updatedAt,Long fileId, UserEntity user, CoordinatesEntity coordinates, Set<FileEntity> files) {
        this.courseId = courseId;
        this.coordinates_id = coordinates_id;
        this.courseName = courseName;
        this.courseKeyword = courseKeyword;
        this.fileId = fileId;
        this.userId = userId;
        this.isDeleted = isDeleted;
        this.updater = updater;
        this.updatedAt = updatedAt;
        this.user = user;
        this.coordinates = coordinates;
        this.files = files;
    }
}