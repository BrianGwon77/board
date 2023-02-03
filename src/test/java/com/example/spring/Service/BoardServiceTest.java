package com.example.spring.Service;

import com.example.spring.Dto.CommentDto;
import com.example.spring.Mapper.erp.ErpCommentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    ErpCommentMapper commentMapper;

    @Test
    public void selectComment() {
        List<CommentDto> commentDtoList = commentMapper.selectListByPost(11);
        System.out.println("commentDtoList=" + commentDtoList);
    }
}