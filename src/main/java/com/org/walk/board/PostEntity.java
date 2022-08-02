package com.org.walk.board;

import com.org.walk.user.UserEntity;
import com.org.walk.user.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_post")
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {

    @Column(name="board_id")
    private Long boardId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="post_id")
    private Long postId;

    @Column(name="post_title", length = 100)
    private String postTitle;

    @Column(name="post_msg", columnDefinition = "varchar(10000)")
    private String postMsg;

    @CreatedDate
    @Column(name="created_at")
    private Date createdAt;

    @Column(name="creater_id")
    private long createrId;

    @LastModifiedDate
    @Column(name="updated_at")
    private Date updated_at;

    @Column(name="deleted_at")
    @ColumnDefault("N")
    private Date deletedAt;

    @Column(name="is_deleted")
    private Character isDeleted;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="creater_id", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="board_id", insertable = false, updatable = false)
    private BoardEntity board;

    public void updatePost(PostDto postDto) {

        if (!ObjectUtils.isEmpty(postDto.getBoardId())){
            this.boardId = postDto.getBoardId();
        }
        if (!ObjectUtils.isEmpty(postDto.getPostTitle())){
            this.postTitle = postDto.getPostTitle();
        }
        if (!ObjectUtils.isEmpty(postDto.getPostMsg())){
            this.postMsg = postDto.getPostMsg();
        }
        if (!ObjectUtils.isEmpty(postDto.getDeletedAt())){
            this.deletedAt = postDto.getDeletedAt();
        }
        if (!ObjectUtils.isEmpty(postDto.getIsDeleted())){
            this.isDeleted = postDto.getIsDeleted();
        }
    }
}