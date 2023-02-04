package com.example.spring.Service;

import com.example.spring.Dto.*;
import com.example.spring.Exception.BadRequestException;
import com.example.spring.Exception.PasswordNotMatchedException;
import com.example.spring.Mapper.erp.ErpAttachmentMapper;
import com.example.spring.Mapper.erp.ErpCommentMapper;
import com.example.spring.Mapper.erp.ErpPostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final ErpCommentMapper erpCommentMapper;
    private final ErpAttachmentMapper attachmentMapper;

    @Value("${spring.board.folder-path}")
    String folderPath;

    @Value("${spring.board.storage-name}")
    String storageName;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int register(PostDto postDto, FileDto fileDto) throws IOException {

        // 1. 게시글 정보등록
        postMapper.insert(postDto);

        if (fileDto.getUploadFiles() != null) {
            // 2. 파일 서버 업로드
            List<AttachmentDto> attachmentDtoList = uploadFiles(postDto.getPno(), fileDto.getUploadFiles());
            // 3. AttachmentDto DB 등록
            attachmentMapper.insertList(attachmentDtoList);
        }

        return 0;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PostDto postDto, FileDto fileDto) throws IOException {

        // 1. 게시글 정보등록
        postMapper.update(postDto);

        if (fileDto.getDeleteFiles() != null) {
            // 2. 파일 서버 업로드
            List<AttachmentDto> attachmentDtoList = uploadFiles(postDto.getPno(), fileDto.getUploadFiles());
            // 3. AttachmentDto DB 등록
            attachmentMapper.insertList(attachmentDtoList);
        }

        if (fileDto.getDeleteFiles() != null) {
            // 4. 삭제대상 파일 제거
            attachmentMapper.deleteList(fileDto.getDeleteFiles());
        }

        return 0;

    }

    @Override
    public PostDto selectOne(int bno, int pno) {
        Map map = new HashMap();
        map.put("pno", pno);
        map.put("bno", bno);
        postMapper.increaseViewCnt(pno);
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

    /** 코멘트 관련 함수 **/
    @Override
    public int deleteComment(int cno, String password) {

        int rowCnt = 0;

        CommentDto commentDto = erpCommentMapper.select(cno);

        if (commentDto.getPassword().equals(password)) {
            rowCnt = erpCommentMapper.delete(cno);
        }

        return rowCnt;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertComment(CommentDto commentDto) {
        postMapper.increaseCommentCnt(commentDto.getPno());
        return erpCommentMapper.insert(commentDto);
    }

    @Override
    public CommentDto selectComment(int cno) {
        return erpCommentMapper.select(cno);
    }

    @Override
    public List<CommentDto> selectCommentListByPost(int pno) {
        return erpCommentMapper.selectListByPost(pno);
    }

    @Override
    public List<AttachmentDto> selectAttachmentByPost(int pno) {
        return attachmentMapper.selectListByPost(pno);
    }

    @Override
    public AttachmentDto selectAttachment(int ano) {
        AttachmentDto attachmentDto = attachmentMapper.select(ano);
        return attachmentDto;
    }

    /** 코멘트 관련 함수 **/

    private List<AttachmentDto> uploadFiles(int pno, MultipartFile[] uploadFiles) throws IOException {

        List<AttachmentDto> attachmentDtoList = new ArrayList<AttachmentDto>();

        for (int i = 0; i < uploadFiles.length; i++) {

            MultipartFile file = uploadFiles[i];

            UUID uuid = UUID.randomUUID();
            String[] uuids = uuid.toString().split("-");

            int fileSize = (int) file.getSize();
            String fileName = uuids[0];
            String fileOriginName = file.getOriginalFilename();
            String extension = fileOriginName.substring(fileOriginName.lastIndexOf("."), fileOriginName.length());
            String filePath = folderPath + File.separator + fileName + extension;

            File newFile = new File(filePath);

            file.transferTo(newFile);

            AttachmentDto attachmentDto = new AttachmentDto();
            attachmentDto.setPno(pno);
            attachmentDto.setFile_name(fileName + extension);
            attachmentDto.setStorage_name(storageName);
            attachmentDto.setFile_size(fileSize);
            attachmentDto.setFile_origin_name(fileOriginName);

            attachmentDtoList.add(attachmentDto);

        }

        return attachmentDtoList;
    }
}
