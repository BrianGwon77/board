package com.example.spring.Service;

import com.example.spring.Dto.AssetDto;
import com.example.spring.Dto.CodeDto;
import com.example.spring.Mapper.netclient.NetClientAssetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    NetClientAssetMapper netClientAssetMapper;

    @Override
    public AssetDto selectAsset(String no) {
        return netClientAssetMapper.select(no);
    }

    @Override
    public int updateAsset(AssetDto assetDto) {
        return netClientAssetMapper.update(assetDto);
    }

    @Override
    public List<CodeDto> selectCodeList(String codeType) {
        return netClientAssetMapper.selectCodeList(codeType);
    }

    @Override
    public List<CodeDto> selectDeptList() {
        return netClientAssetMapper.selectDeptList();
    }

    @Override
    public String selectImage(String no) {
        Map<String, Object> resultMap = netClientAssetMapper.selectImage(no);
        if (resultMap != null) {
            byte[] arr = (byte[]) resultMap.get("image");
            byte[] base64Arr = Base64.getEncoder().encode(arr);
            return new String(base64Arr);
        }

        return "";
    }
}
