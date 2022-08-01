package com.org.walk.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.org.walk.file.FileDto;
import com.org.walk.file.FileEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
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

    private Set<FileDto> files;

    private Character loginYn;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date lastLogin;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date dateBirth;

    @QueryProjection
    public UserDto(Long userId, String name, String email, String address, String phone, Set<FileDto> files, Character loginYn, Date lastLogin, Date dateBirth) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.files = files;
        this.loginYn = loginYn;
        this.lastLogin = lastLogin;
        this.dateBirth = dateBirth;
    }
}