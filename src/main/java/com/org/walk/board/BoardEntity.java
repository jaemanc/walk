package com.org.walk.board;

import com.org.walk.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Getter
@NoArgsConstructor
@Table(name="tb_board")
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long boardId;

    @Column(name="board_name")
    private String boardName;

    @Column(name="is_deleted")
    private Character isDeleted;

    @CreatedDate
    @Column(name="created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private Date updatedAt;

    @Builder
    public BoardEntity(Long boardId, String boardName, Character isDeleted, Date createdAt, Date updatedAt) {
        this.boardId = boardId;
        this.boardName = boardName;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
