package com.org.walk.user;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String address;

    private String phone;


    @QueryProjection
    public UserDto(long id, String name, String email, String address, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;

    }
}