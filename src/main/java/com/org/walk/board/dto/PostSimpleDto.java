package com.org.walk.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostSimpleDto {

    private Long boardId;

    private String boardName;

    private Long postId;

    private String postTitle;

    private String postMsg;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdAt;

    private long createrId;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date updated_at;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
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
