package com.org.walk.board;

import com.org.walk.user.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QBoardEntity qBoardEntity = QBoardEntity.boardEntity;
    private final QPostEntity qPostEntity = QPostEntity.postEntity;
    private final QUserEntity qUserEntity = QUserEntity.userEntity;

    public List<PostDto> getPostList(String keyword, long boardId, Pageable pageable) {

        // 제목 / 작성자 / 게시글 내용 기준으로 조회.

        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null) {
            if ( boardId > 0 ) {
                builder.and(qPostEntity.boardId.eq(boardId));
            }
            builder.or(qPostEntity.postTitle.contains(keyword));
            builder.or(qPostEntity.postMsg.contains(keyword));
            builder.or(qPostEntity.user.name.contains(keyword));
        }


        return queryFactory
                .select(new QPostDto(
                        qPostEntity.boardId
                        ,qPostEntity.postId
                        ,qPostEntity.postTitle
                        ,qPostEntity.postMsg
                        ,qPostEntity.createdAt
                        ,qPostEntity.createrId
                        ,qPostEntity.updated_at
                        ,qPostEntity.deletedAt
                        ,qPostEntity.isDeleted
                ))
                .from(qPostEntity)
                .where(builder)
                .fetch();

    }

    public Boolean exist(long id) {

        Integer fetchOne = queryFactory
                .selectOne()
                .from(qPostEntity)
                .where(qPostEntity.postId.eq(id))
                .fetchFirst();

        return fetchOne != null;
    }

    public PostEntity getPost(long postId) {

        return queryFactory.select(qPostEntity)
                .from(qPostEntity)
                .leftJoin(qPostEntity.user, qUserEntity)
                .fetchJoin()
                .distinct()
                .where(qPostEntity.postId.eq(postId))
                .fetchOne();

    }


}