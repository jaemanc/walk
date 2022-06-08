package com.org.walk.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString(exclude = "password")
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String password;

    private String name;

    private String address;

    private String phone;

    private String email;

    @Builder
    public static UserEntity createUser(long id, String password, String name, String address, String phone, String email){
        return new UserEntity(id, password, name, address, phone, email);
    }

    public void updateUser(UserDto userDto) {
        this.password = userDto.getPassword();
        this.address = userDto.getAddress();
        this.name = userDto.getName();
        this.phone = userDto.getPhone();
        this.email = userDto.getEmail();
    }

}
