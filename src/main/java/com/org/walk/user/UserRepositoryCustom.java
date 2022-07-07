package com.org.walk.user;

import com.org.walk.file.QFileEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QUserEntity qUserEntity = QUserEntity.userEntity;
    private final QFileEntity qFileEntity = QFileEntity.fileEntity;

    public List<UserDto> searchUserByKeyword(String keyword) {

        QUserEntity userEntity = QUserEntity.userEntity;
        BooleanBuilder builder = new BooleanBuilder();

        if (keyword.contains("@")) {
            builder.and(userEntity.email.like(keyword));
        }

        String pattern = "^\\d{3}-\\d{3,4}-\\d{4}$";

        if (keyword.matches(pattern)) {
            builder.and(userEntity.phone.like(keyword));
        }

        if (StringUtils.hasText(keyword)) {
            builder.and(userEntity.name.like(keyword));
        }

        return queryFactory
                .select(new QUserDto(
                        userEntity.userId
                        ,userEntity.name
                        ,userEntity.email
                        ,userEntity.address
                        ,userEntity.phone
                        ,null
                ))
                .from(userEntity)
                .where(builder)
                .fetch();
    }

    // 기존 exists 메서드는 count쿼리가 나가므로,
    // row 1 조회시 종료하는 쿼리 직접 구현.
    public Boolean exist(long id) {

        Integer fetchOne = queryFactory
                .selectOne()
                .from(qUserEntity)
                .where(qUserEntity.userId.eq(id))
                .fetchFirst();

        return fetchOne != null;
    }

    public List<UserEntity> getUserFiles(long id) {

        // N+1 문제 발생 위험이있음.
        /*List<UserEntity> user = queryFactory.selectFrom(qUserEntity)
                .where(qUserEntity.id.eq(id))
                .innerJoin(qUserEntity.files, qFileEntity)
                .limit(5)
                .fetch();*/

        // fetch 결과가 1이 나오기 때문에 메모리 적재 위험이 없음.
        List<UserEntity> user = queryFactory.selectFrom(qUserEntity)
                .leftJoin(qUserEntity.files, qFileEntity)
                .where(qFileEntity.user.userId.eq(id))
                .limit(5)
                .fetch();

        return user;
    }


}










