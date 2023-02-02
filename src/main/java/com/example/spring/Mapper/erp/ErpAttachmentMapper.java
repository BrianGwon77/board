package com.example.spring.Mapper.erp;

import com.example.spring.Dto.AttachmentDto;
import com.example.spring.Dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ErpAttachmentMapper {
    public int insertList(List<AttachmentDto> attachmentDtoList);
    public int deleteList(String[] deleteFiles);
    public int deleteListByPost(int pno);
    public List<AttachmentDto> selectListByPost(int pno);
}
