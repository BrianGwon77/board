package com.example.spring.Service;

import com.example.spring.Dto.PostDto;

import java.util.List;

public interface PostService {
    int register(PostDto postDto);
    int update(PostDto postDto);
    PostDto selectOne(int bno, int pno);
    int delete(PostDto postDto);
    List<PostDto> selectPage(int limit, int offset);
    int selectCount(int bno);
}
