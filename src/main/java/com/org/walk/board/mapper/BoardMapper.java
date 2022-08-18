package com.org.walk.board.mapper;

import com.org.walk.board.BoardEntity;
import com.org.walk.board.dto.BoardDto;
import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMapper extends EntityMapper<BoardDto, BoardEntity> {

    BoardMapper mapper = Mappers.getMapper(BoardMapper.class);

}
