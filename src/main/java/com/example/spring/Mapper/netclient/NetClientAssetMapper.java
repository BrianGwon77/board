package com.example.spring.Mapper.netclient;

import com.example.spring.Dto.AssetDto;
import com.example.spring.Dto.AttachmentDto;
import com.example.spring.Dto.CodeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NetClientAssetMapper {
    public AssetDto select(String no);
    public int update(AssetDto assetDto);
    public List<CodeDto> selectCodeList(String codeType);
    public List<CodeDto> selectDeptList();
    public Map<String, Object> selectImage(String no);
}
