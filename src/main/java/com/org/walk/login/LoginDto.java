package com.org.walk.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDto {

    private Long userId;

    private String name;

    private String email;

    private String address;

    private String phone;

    private Character loginYn;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date lastLogin;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date dateBirth;

    @QueryProjection
    @Builder
    public LoginDto(Long userId, String name, Character loginYn, Date lastLogin) {
        this.userId = userId;
        this.name = name;
        this.loginYn = loginYn;
        this.lastLogin = lastLogin;
    }


}
