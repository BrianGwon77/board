package com.example.spring.Mapper.erp;

import com.example.spring.Dto.PostDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ErpPostMapper {
    public int update(PostDto postDto);
    public int delete(Map map);
    public int insert(PostDto postDto);
    public int selectCount(int bno);
    public int increaseViewCnt(int pno);
    public int increaseCommentCnt(int pno);
    public PostDto selectOne(Map map);
    public List<PostDto> selectPage(Map map);
}
