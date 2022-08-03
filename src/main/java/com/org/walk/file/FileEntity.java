package com.org.walk.file;

import com.org.walk.course.CoordinatesEntity;
import com.org.walk.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_file")
@DynamicUpdate
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="file_id")
    @Comment("파일 ID")
    private Long fileId;

    @Column(name="file_category" ,length = 100)
    @Comment("파일 카테고리")
    private String fileCategory;

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

    @Column(name="updater", length = 20)
    @Comment("수정자")
    private String updater;

    @LastModifiedDate
    @Column(name="updated_at")
    private Date updatedAt;

    @Column(name="user_id")
    @Comment("사용자 ID")
    private Long userId;

    @Column(name="coordinates_id")
    @Comment("좌표 ID")
    private Long coordinatesId;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="coordinates_id", insertable = false, updatable = false)
    private CoordinatesEntity coordinates;

    public FileEntity(long fileId, String fileCategory, Long fileSize, Date uploadDate, String fileLoc, Character isDeleted, Long userId) {
    }

    @Builder
    public static FileEntity createFile(Long fileId, String fileCategory, Long fileSize, Date uploadDate, String fileLoc
            , char isDeleted, Long userId) {
        return new FileEntity(fileId, fileCategory, fileSize, uploadDate, fileLoc, isDeleted, userId);
    }


}
