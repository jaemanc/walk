package com.org.walk.board.mapper;

import com.org.walk.board.PostEntity;
import com.org.walk.board.dto.PostSimpleDto;
import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostSimpleMapper extends EntityMapper<PostSimpleDto, PostEntity> {

    PostSimpleMapper mapper = Mappers.getMapper(PostSimpleMapper.class);

}
