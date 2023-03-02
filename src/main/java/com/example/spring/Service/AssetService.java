package com.example.spring.Service;

import com.example.spring.Dto.AssetDto;
import com.example.spring.Dto.CodeDto;

import java.util.List;
import java.util.Map;

public interface AssetService {
    AssetDto selectAsset(String no);
    int updateAsset(AssetDto assetDto);
    List<CodeDto> selectCodeList(String codeType);
    List<CodeDto> selectDeptList();
    String selectImage(String no);
}

