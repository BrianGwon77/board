package com.example.spring.Mapper.erp;

import com.example.spring.Dto.SearchConditionDto;
import com.example.spring.Dto.VisitDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ErpVisitMapper {
    public VisitDto select(String no);
    public List<VisitDto> selectList(SearchConditionDto searchConditionDto);
    public int insert(VisitDto visitDto);
    public int delete(String no);
    public int update(VisitDto visitDto);
}
