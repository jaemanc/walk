package com.org.walk.board;

import com.org.walk.user.UserDto;
import com.org.walk.user.UserEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostOnlyDto {

    private Long boardId;

    private Long postId;

    private String postTitle;

    private String postMsg;

    private Date createdAt;

    private long createrId;

    private Date updated_at;

    private Date deletedAt;

    private Character isDeleted;

    @QueryProjection
    public PostOnlyDto(Long boardId, Long postId, String postTitle, String postMsg, Date createdAt, long createrId, Date updated_at, Date deletedAt, Character isDeleted) {
        this.boardId = boardId;
        this.postId = postId;
        this.postTitle = postTitle;
        this.postMsg = postMsg;
        this.createdAt = createdAt;
        this.createrId = createrId;
        this.updated_at = updated_at;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
    }
}
