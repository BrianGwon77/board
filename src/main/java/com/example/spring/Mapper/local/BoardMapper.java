package com.example.spring.Mapper.local;
import com.example.spring.Dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    public int insert(BoardDto boardDto);
    public int update(BoardDto boardDto);
    public int deleteOne(int bno);
    public int deleteAll();
    public BoardDto selectOne(int bno);
    public List<BoardDto> selectPage(Map map);
    public int getCount();
}
