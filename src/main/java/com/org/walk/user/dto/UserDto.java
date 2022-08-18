package com.org.walk.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.org.walk.file.dto.FileDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserDto {

    private Long userId;

    private String name;

    private String email;

    private String password;

    private String address;

    private String phone;

    private Set<FileDto> file;

    private Character loginYn;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date lastLogin;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date dateBirth;

    @QueryProjection
    public UserDto(Long userId, String name, String email, String address, String phone, Set<FileDto> file, Character loginYn, Date lastLogin, Date dateBirth) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.file = file;
        this.loginYn = loginYn;
        this.lastLogin = lastLogin;
        this.dateBirth = dateBirth;
    }
}