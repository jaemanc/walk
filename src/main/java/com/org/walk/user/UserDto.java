package com.org.walk.user;

import com.org.walk.file.FileDto;
import com.org.walk.file.FileEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

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

    @QueryProjection
    public UserDto(long userId, String name, String email, String address, String phone, Set<FileDto> files) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.files = files;
    }
}