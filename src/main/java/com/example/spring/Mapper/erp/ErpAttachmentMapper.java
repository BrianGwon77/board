package com.example.spring.Mapper.erp;

import java.util.List;
import com.example.spring.Dto.AttachmentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ErpAttachmentMapper {
    public int insert(List<AttachmentDto> attachmentDtoList);
    public int delete(int ano);
    public int delete(int[] anoList);
    public int deleteAll(int pno);
    public List<AttachmentDto> selectList(int pno);
}
