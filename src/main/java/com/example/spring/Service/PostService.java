package com.example.spring.Service;

import com.example.spring.Dto.CommentDto;
import com.example.spring.Dto.FileDto;
import com.example.spring.Dto.PostDto;

import java.io.IOException;
import java.util.List;

public interface PostService {
    int register(PostDto postDto, FileDto fileDto) throws IOException;
    int update(PostDto postDto, FileDto fileDto) throws IOException;
    PostDto selectOne(int bno, int pno);
    int delete(PostDto postDto);
    List<PostDto> selectPage(int limit, int offset);
    int selectCount(int bno);

    /** 코멘트 관련 함수 **/
    public int deleteComment(int cno);
    public int insertComment(CommentDto commentDto);
    public List<CommentDto> selectCommentListByPost(int pno);
}
