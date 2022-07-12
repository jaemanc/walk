package com.org.walk.file;

import com.org.walk.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

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
    private Long fileId;

    @Column(name="file_category")
    private String fileCategory;

    @Column(name="file_size")
    private Long fileSize;

    @CreatedDate
    @Column(name="upload_date")
    private Date uploadDate;

    @Column(name="file_loc")
    private String fileLoc;

    @Column(name="is_deleted")
    private Character isDeleted;

    @Column(name="user_id")
    private Long userId;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private UserEntity user;

    public FileEntity(long fileId, String fileCategory, Long fileSize, Date uploadDate, String fileLoc, Character isDeleted, Long userId) {
    }

    @Builder
    public static FileEntity createFile(Long fileId, String fileCategory, Long fileSize, Date uploadDate, String fileLoc
            , char isDeleted, Long userId) {
        return new FileEntity(fileId, fileCategory, fileSize, uploadDate, fileLoc, isDeleted, userId);
    }


}
