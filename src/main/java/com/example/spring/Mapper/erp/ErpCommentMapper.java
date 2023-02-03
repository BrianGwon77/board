package com.example.spring.Mapper.erp;

import com.example.spring.Dto.CommentDto;
import com.example.spring.Dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ErpCommentMapper {
    public int insert(CommentDto commentDto);
    public int delete(int cno);
    public int deleteListByPost(int pno);
    public CommentDto select(int cno);
    public List<CommentDto> selectListByPost(int pno);
}
