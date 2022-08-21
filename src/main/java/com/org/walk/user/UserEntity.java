package com.org.walk.user;

import com.org.walk.file.FileEntity;
import com.org.walk.file.mapper.FileMapper;
import com.org.walk.user.dto.UserDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Builder
@Getter
@ToString(exclude = "password")
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_user")
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="password", length = 255)
    private String password;

    @Column(name="name", length = 20)
    private String name;

    @Column(name="address", length = 100)
    private String address;

    @Column(name="phone", length = 20)
    private String phone;

    @Column(name="email", length = 50)
    private String email;

    @CreatedDate
    @Column(name="created_at")
    private Date createdAt;

    @Column(name="login_yn")
    @ColumnDefault("N")
    private Character loginYn;

    @LastModifiedDate
    @Column(name="last_login")
    private Date lastLogin;

    @Column(name="date_birth")
    private Date dateBirth;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private Set<FileEntity> file;

    public UserEntity(Long userId, String password, String name, String address, String phone, String email, Set<FileEntity> files, Character loginYn, Date lastLogin, Date dateBirth) {
    }

    @Builder
    public static UserEntity createUser(Long userId, String password, String name, String address, String phone, String email, Set<FileEntity> files, Character loginYn, Date lastLogin, Date dateBirth){
        return new UserEntity(userId, password, name, address, phone, email, files, loginYn, lastLogin, dateBirth);
    }

    public void updateUser(UserDto userDto) {
        if (!ObjectUtils.isEmpty(userDto.getPassword())) {
            this.password = userDto.getPassword();
        }
        if (!ObjectUtils.isEmpty(userDto.getAddress())) {
            this.address = userDto.getAddress();
        }
        if (!ObjectUtils.isEmpty(userDto.getName())) {
            this.name = userDto.getName();
        }
        if (!ObjectUtils.isEmpty(userDto.getPhone())) {
            this.phone = userDto.getPhone();
        }
        if (!ObjectUtils.isEmpty(userDto.getEmail())) {
            this.email = userDto.getEmail();
        }
        if (!ObjectUtils.isEmpty(userDto.getFile())) {
            this.file = FileMapper.mapper.toEntitySet(userDto.getFile());
        }
        if (!ObjectUtils.isEmpty(userDto.getLoginYn())) {
            this.loginYn = userDto.getLoginYn();
        }
        if (!ObjectUtils.isEmpty(userDto.getLastLogin())) {
            this.lastLogin = userDto.getLastLogin();
        }
        if (!ObjectUtils.isEmpty(userDto.getDateBirth())) {
            this.dateBirth = userDto.getDateBirth();
        }
    }
}