package com.org.walk.user;

import com.org.walk.file.FileEntity;
import com.org.walk.file.FileMapper;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    @Column(name="user_id")
    private Long userId;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private Set<FileEntity> files;

    @Builder
    public static UserEntity createUser(long userId, String password, String name, String address, String phone, String email, Set<FileEntity> files){
        return new UserEntity(userId, password, name, address, phone, email, files);
    }

    public void updateUser(UserDto userDto) {
        this.password = userDto.getPassword();
        this.address = userDto.getAddress();
        this.name = userDto.getName();
        this.phone = userDto.getPhone();
        this.email = userDto.getEmail();
        this.files = FileMapper.mapper.toEntitySet(userDto.getFiles());
    }

}
