package com.org.walk.board.mapper;

import com.org.walk.board.PostEntity;
import com.org.walk.board.dto.PostDto;
import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper extends EntityMapper<PostDto, PostEntity> {

    PostMapper mapper = Mappers.getMapper(PostMapper.class);

}
