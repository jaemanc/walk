package com.org.walk.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class UserDummies {

    @Test
    void insertUserDummies() {

        System.out.println(" 더미 유저 등록 시작 ");
        IntStream.rangeClosed(1,300).forEach(i ->{
            UserEntity userEntity = UserEntity.builder()
                    .name("name"+i)
                    .address("addr"+i)
                    .phone("phone"+i)
                    .email("email"+i+"@gmail.com")
                    .password("passwd"+i)
                    .build();
        });
        System.out.println(" 더미 유저 등록 완료");
    }
}
