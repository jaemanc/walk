package com.org.walk.board;

import com.org.walk.board.dto.PostListResponseDto;
import com.org.walk.board.dto.PostSimpleDto;
import com.org.walk.user.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
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

    public List<PostListResponseDto> getPostListSearch(String keyword, long boardId, Pageable pageable) {

        // 제목 / 작성자 / 게시글 내용 기준으로 조회.

        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null) {
            builder.or(qPostEntity.postTitle.contains(keyword));
            builder.or(qPostEntity.postMsg.contains(keyword));
            builder.or(qPostEntity.user.name.contains(keyword));
        }

        if ( boardId > 0 ) {
            builder.and(qPostEntity.boardId.eq(boardId));
        }

        builder.and(qPostEntity.isDeleted.eq('N'));

        List<PostListResponseDto> result =
                queryFactory
                .select(Projections.fields(PostListResponseDto.class
                        ,qPostEntity.board.boardName.as("boardName")
                        ,qPostEntity.boardId
                        ,qPostEntity.createrId
                        ,qPostEntity.postId
                        ,qPostEntity.postTitle
                        ,qPostEntity.postMsg
                        ,qPostEntity.createdAt
                        ,qPostEntity.user.name.as("name")
                ))
                .from(qPostEntity)
                .join(qBoardEntity).on(qPostEntity.boardId.eq(qBoardEntity.boardId))
                .join(qUserEntity).on(qPostEntity.createrId.eq(qUserEntity.userId))
                .fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qPostEntity.createdAt.desc())
                .fetch();

        long allCount = queryFactory.selectFrom(qPostEntity).where(builder).fetchCount();

        result.forEach(i -> i.setAllCount(allCount));

        return result;

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

    public List<PostSimpleDto> getPostList(Pageable pageable) {

        return queryFactory
                .select(Projections.fields(PostSimpleDto.class
                        ,qPostEntity.board.boardName.as("boardName")
                        ,qPostEntity.boardId
                        ,qPostEntity.createrId
                        ,qPostEntity.postId
                        ,qPostEntity.postTitle
                        ,qPostEntity.postMsg
                        ,qPostEntity.createdAt
                        ,qPostEntity.user.name.as("name")
                ))
                .from(qPostEntity)
                .join(qBoardEntity).on(qPostEntity.boardId.eq(qBoardEntity.boardId))
                .join(qUserEntity).on(qPostEntity.createrId.eq(qUserEntity.userId))
                .fetchJoin()
                .where(qPostEntity.isDeleted.eq('N'))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qPostEntity.createdAt.desc())
                .fetch();
    }

    public long getPostListCount() {

        return queryFactory
                .selectFrom(qPostEntity)
                .where(qPostEntity.isDeleted.eq('N'))
                .fetchCount();
    }

}