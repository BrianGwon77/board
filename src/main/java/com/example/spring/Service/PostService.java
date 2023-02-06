package com.example.spring.Service;

import com.example.spring.Dto.AttachmentDto;
import com.example.spring.Dto.CommentDto;
import com.example.spring.Dto.FileDto;
import com.example.spring.Dto.PostDto;

import java.io.IOException;
import java.util.List;

public interface PostService {

    /** 포스트 관련 함수 **/
    int register(PostDto postDto, FileDto fileDto) throws IOException;
    int update(PostDto postDto, FileDto fileDto) throws IOException;
    int selectCommentCountByPost(int pno);
    PostDto selectOne(int bno, int pno);
    int delete(PostDto postDto);
    List<PostDto> selectPage(int limit, int offset, int bno);
    int selectCount(int bno);

    /** 코멘트 관련 함수 **/
    public int deleteComment(int cno, String password);
    public int insertComment(CommentDto commentDto);
    CommentDto selectComment(int cno);
    public List<CommentDto> selectCommentListByPost(int pno);

    /** 첨부파일 관련 함수 **/
    public AttachmentDto selectAttachment(int ano);
    public List<AttachmentDto> selectAttachmentByPost(int pno);
    public List<String> getIconList();

}
