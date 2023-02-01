//package com.example.spring.Service;
//
//import com.example.spring.Dto.BoardDto;
//import com.example.spring.Mapper.local.BoardMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class BoardService {
//
//    private final BoardMapper boardMapper;
//
//    public int register(BoardDto boardDto){
//        return boardMapper.insert(boardDto);
//    }
//
//    public int delete(int bno){
//        return boardMapper.deleteOne(bno);
//    }
//
//    public int delete(){
//        return boardMapper.deleteAll();
//    }
//
//    public BoardDto select(int bno){
//        return boardMapper.selectOne(bno);
//    }
//
//    public List<BoardDto> select(Map map){
//        return boardMapper.selectPage(map);
//    }
//
//    public int update(BoardDto boardDto){
//        return boardMapper.update(boardDto);
//    }
//
//}
