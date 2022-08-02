package com.org.walk.board;

import com.org.walk.util.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper extends EntityMapper<PostDto, PostEntity> {

    PostMapper mapper = Mappers.getMapper(PostMapper.class);

}
