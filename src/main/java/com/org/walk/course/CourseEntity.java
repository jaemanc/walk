package com.org.walk.course;

import com.org.walk.file.FileEntity;
import com.org.walk.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_course")
@DynamicUpdate
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

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private UserEntity user;

    @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="coordinates_id", insertable = false, updatable = false)
    private CoordinatesEntity coordinates;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private Set<FileEntity> files;

}