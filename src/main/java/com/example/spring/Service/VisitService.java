package com.example.spring.Service;

import com.example.spring.Dto.SearchConditionDto;
import com.example.spring.Dto.VisitDto;
import com.example.spring.Mapper.erp.ErpVisitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitService {

    @Autowired
    ErpVisitMapper visitMapper;

    public VisitDto selectVisit(String no) {
        return visitMapper.select(no);
    }

    public List<VisitDto> selectVisitList(SearchConditionDto searchConditionDto)
    {
        return visitMapper.selectList(searchConditionDto);
    }

    public int insertVisit(VisitDto visitDto) {
        return visitMapper.insert(visitDto);
    }

    public int deleteVisit(String no) {
        return visitMapper.delete(no);
    }

    public int updateVisit(VisitDto visitDto) {
        return visitMapper.update(visitDto);
    }

}
