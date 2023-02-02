package com.example.spring.Mapper.erp;

import com.example.spring.Dto.CommentDto;
import com.example.spring.Dto.PostDto;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ErpCommentMapper {
    public int delete(int cno);
    public int deleteAll(int pno);
    public int insert(CommentDto commentDto);
    public int selectCount(int pno);
    public List<CommentDto> selectByPost(int pno);
}
