package com.example.spring.Service;

import com.example.spring.Dto.FileDto;
import com.example.spring.Dto.PageDto;
import com.example.spring.Dto.PostDto;
import com.example.spring.Exception.BadRequestException;
import com.example.spring.Exception.PasswordNotMatchedException;
import com.example.spring.Mapper.erp.ErpAttachmentMapper;
import com.example.spring.Mapper.erp.ErpPostMapper;
import com.example.spring.Service.PostService;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ErpPostMapper postMapper;
    private final ErpAttachmentMapper attachmentMapper;

    @Value("${spring.file-upload-path}")
    private final String folderPath;

    /** 게시글 등록 **/
    @Override
    @Transactional(rollbackFor=Exception.class)
    public int register(PostDto postDto, FileDto fileDto) throws IOException {

        // 1. 게시글 정보 등록
        postMapper.insert(postDto);
        // 2. 파일을 서버 내 업로드
        MultipartFile[] uploadFileList = fileDto.getUploadFiles();

        for (int i = 0; i < uploadFileList.length; i++) {

            MultipartFile file = uploadFileList[i];

            UUID randomUUID = UUID.randomUUID();
            String[] uuids = randomUUID.toString().split("-");

            String fileName = uuids[0];
            String fileOriginName = file.getOriginalFilename();
            String extension = fileOriginName.substring(fileOriginName.lastIndexOf('.'), fileOriginName.length());
            String filePath = folderPath + File.separator + fileName + extension;

            File newFile = new File(filePath);

            file.transferTo(newFile);

        }
        // 3. 파일 AttachmentDto 정보 DB 저장
        return 0;
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
