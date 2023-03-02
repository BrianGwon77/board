package com.example.spring.Service;

import com.example.spring.Dto.CodeDto;
import com.example.spring.Dto.CommentDto;
import com.example.spring.Mapper.erp.ErpCommentMapper;
import com.example.spring.Mapper.netclient.NetClientAssetMapper;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    NetClientAssetMapper netClientAssetMapper;

    @Test
    public void selectComment() {
        Map<String, Object> resultMap = netClientAssetMapper.selectImage("NB00001120");
        byte[] arr = (byte[]) resultMap.get("FileContents");
        byte[] base64Arr = Base64.getEncoder().encode(arr);
        String base64ToString = new String(base64Arr);
        System.out.println("base64ToString=" + base64ToString);
    }
}