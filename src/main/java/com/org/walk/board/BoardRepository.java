package com.org.walk.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository  extends JpaRepository<BoardEntity, Long> {

    BoardEntity getBoardEntitiesByBoardId(Long boardId);

}
