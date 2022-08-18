package com.org.walk.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostSimpleDto {

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
    public PostSimpleDto(Long boardId, Long postId, String postTitle, String postMsg, Date createdAt, long createrId, Date updated_at, Date deletedAt, Character isDeleted) {
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
