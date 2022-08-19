package com.org.walk.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.org.walk.user.dto.UserDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {

    private Long boardId;

    private Long postId;

    private String postTitle;

    private String postMsg;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdAt;

    private long createrId;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date updated_at;

    private Date deletedAt;

    private Character isDeleted;

    private UserDto user;

    private BoardDto board;

    @QueryProjection
    public PostDto(Long boardId, Long postId, String postTitle, String postMsg, Date createdAt, long createrId, Date updated_at, Date deletedAt, Character isDeleted, BoardDto board) {
        this.boardId = boardId;
        this.postId = postId;
        this.postTitle = postTitle;
        this.postMsg = postMsg;
        this.createdAt = createdAt;
        this.createrId = createrId;
        this.updated_at = updated_at;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
        this.board = board;
    }
}
