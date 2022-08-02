package com.org.walk.board;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    // User와 같이 딸려있는 file을 추가해야한다..?
    // 그렇다면 모두 같이 조회할 필요가 없는데 다 조회를 하는 꼴이된다.
    /*
    @EntityGraph(attributePaths = {"user","board","file"})
    PostEntity getPostByPostId(Long postId);
    */
}
