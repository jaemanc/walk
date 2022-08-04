package com.org.walk.board;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@AllArgsConstructor
@NoArgsConstructor
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
