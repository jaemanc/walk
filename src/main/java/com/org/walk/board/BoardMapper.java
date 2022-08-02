package com.org.walk.board;

import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMapper extends EntityMapper<BoardDto, BoardEntity> {

    BoardMapper mapper = Mappers.getMapper(BoardMapper.class);

}
