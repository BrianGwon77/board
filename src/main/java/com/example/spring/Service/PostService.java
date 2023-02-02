package com.example.spring.Service;

import com.example.spring.Dto.FileDto;
import com.example.spring.Dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    int register(PostDto postDto, FileDto fileDto) throws IOException;
    int update(PostDto postDto, FileDto fileDto);
    PostDto selectOne(int bno, int pno);
    int delete(PostDto postDto);
    List<PostDto> selectPage(int limit, int offset);
    int selectCount(int bno);
}
