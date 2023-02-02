package com.example.spring.Service;

import com.example.spring.Dto.FileDto;
import com.example.spring.Dto.PageDto;
import com.example.spring.Dto.PostDto;
import com.example.spring.Exception.BadRequestException;
import com.example.spring.Exception.PasswordNotMatchedException;
import com.example.spring.Mapper.erp.ErpPostMapper;
import com.example.spring.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ErpPostMapper postMapper;

    @Override
    public int register(PostDto postDto, FileDto fileDto) {
        return postMapper.insert(postDto);
    }

    @Override
    public int update(PostDto postDto, FileDto fileDto) {
        return postMapper.update(postDto);
    }

    @Override
    public PostDto selectOne(int bno, int pno) {
        Map map = new HashMap();
        map.put("pno", pno);
        map.put("bno", bno);
        return postMapper.selectOne(map);
    }

    @Override
    public int delete(PostDto postDto) {

        PostDto selectedPost = selectOne(postDto.getBno(),  postDto.getPno());

        if (selectedPost.getPassword().equals(postDto.getPassword())){
            Map map = new HashMap();
            map.put("pno", postDto.getPno());
            map.put("bno", postDto.getBno());
            return postMapper.delete(map);
        }

        throw new PasswordNotMatchedException("패스워드가 일치하지 않습니다.");
    }

    @Override
    public List<PostDto> selectPage(int limit, int offset) {

        // limit & offest 값
        Map map = new HashMap();
        map.put("limit", limit);
        map.put("offset", offset);

        List<PostDto> postDtoList = postMapper.selectPage(map);

        if (postDtoList == null)
            throw new BadRequestException("페이지 요청 정보를 다시 확인해주세요.");

        return postDtoList;

    }

    @Override
    public int selectCount(int bno) {
        return postMapper.selectCount(bno);
    }

}
