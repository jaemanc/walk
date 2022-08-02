package com.org.walk.course;

import com.org.walk.file.FileEntity;
import com.org.walk.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@Table(name="tb_course")
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    private Date updatedAt;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private UserEntity user;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="coordinates_id", insertable = false, updatable = false)
    private CoordinatesEntity coordinates;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private Set<FileEntity> files;

    @Builder
    public CourseEntity(long courseId, long coordinates_id, String courseName, String courseKeyword, long userId, Character isDeleted, String updater, Date updatedAt, UserEntity user, CoordinatesEntity coordinates, Set<FileEntity> files) {
        this.courseId = courseId;
        this.coordinates_id = coordinates_id;
        this.courseName = courseName;
        this.courseKeyword = courseKeyword;
        this.userId = userId;
        this.isDeleted = isDeleted;
        this.updater = updater;
        this.updatedAt = updatedAt;
        this.user = user;
        this.coordinates = coordinates;
        this.files = files;
    }
}