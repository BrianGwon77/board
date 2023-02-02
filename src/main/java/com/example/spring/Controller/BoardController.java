package com.example.spring.Controller;

import com.example.spring.Dto.FileDto;
import com.example.spring.Dto.PageDto;
import com.example.spring.Dto.PostDto;
import com.example.spring.Exception.BadRequestException;
import com.example.spring.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final PostService postService;

    private final int CREATE = 0;
    private final int MODIFY = 1;

    @GetMapping("/list")
    public String list( @RequestParam(required=true) int bno,
                        @RequestParam(required=false, defaultValue = "1") int page,
                        @RequestParam(required=false, defaultValue = "10") int pageSize,
                       Model model){

        // 시작 글 번호
        int offset = (page-1)*pageSize + 1;

        // 마지막 글 번호
        int limit = page * pageSize;

        // 해당 페이지의 게시글 목록
        List<PostDto> postDtoList = postService.selectPage(limit, offset);

        // 전체 게시글 갯수
        int totalCount = postService.selectCount(bno);

        // 페이징 정보
        PageDto pageDto = new PageDto(page, pageSize, totalCount);

        model.addAttribute("pageDto", pageDto);
        model.addAttribute("postDtoList", postDtoList);

        return "/board/post-list";

    }

    @GetMapping("/write")
    public String write(Model model){
        PostDto postDto = new PostDto();
        model.addAttribute("postDto", postDto);
        model.addAttribute("mode", CREATE);
        return "/board/post-write";
    }

    @PostMapping("/write.do")
    public String write_do(@Validated PostDto postDto,
                           BindingResult bindingResult,
                           FileDto fileDto,
                           @RequestParam(name="mode", defaultValue = "0") String modeString,
                           Model model) throws IOException {

        /** 
         * 사용자 입력에 오류가 존재하지 않을 경우 수행
         * **/

        int mode = Integer.parseInt(modeString);

        if (!bindingResult.hasErrors()) {

            /** 작성 모드일 경우 register 호출 **/
            if (mode == CREATE) {
                postService.register(postDto, fileDto);
            }

            /** 수정 모드일 경우 modify 호출 **/
            else {
                postService.update(postDto, fileDto);
            }

            return "redirect:/board/list?bno=" + postDto.getBno();
        }

        return "/board/post-write";
    }

    @GetMapping("/certificate")
    public String certificate(PostDto postDto, Model model){
        model.addAttribute("postDto", postDto);
        return "/board/certificate";
    }

    @GetMapping("/read")
    public String read(int bno, int pno, Model model){
        PostDto postDto = postService.selectOne(bno, pno);
        model.addAttribute("postDto", postDto);
        return "/board/post-read";
    }

    @PostMapping("/certificate.do")
    public String certificate_do(PostDto postDto, Model model) {

        PostDto selectedPostDto = postService.selectOne(postDto.getBno(), postDto.getPno());

        if (selectedPostDto.getWriter().equals(postDto.getWriter()) && selectedPostDto.getPassword().equals(postDto.getPassword())) {
            model.addAttribute("postDto", selectedPostDto);
            model.addAttribute("mode", MODIFY);
            return "/board/post-write";
        }

        model.addAttribute("postDto", postDto);
        return "/board/certificate";
    }
}
