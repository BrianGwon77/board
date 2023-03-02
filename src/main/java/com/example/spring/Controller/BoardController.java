package com.example.spring.Controller;

import com.example.spring.Dto.*;
import com.example.spring.Exception.BadRequestException;
import com.example.spring.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
import org.apache.tomcat.jni.Directory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final PostService postService;

    private final int CREATE = 0;
    private final int UPDATE = 1;
    private final int DELETE = 2;

    private final Environment env;

    @GetMapping("/list")
    public String list(@RequestParam(required = true) int bno,
                       @RequestParam(required = false, defaultValue = "1") int page,
                       @RequestParam(required = false, defaultValue = "10") int pageSize,
                       @RequestParam(required = false, defaultValue = "1") int type,
                       @RequestParam(required = false, defaultValue = "") String keyword,
                       Model model) {

        // 시작 글 번호
        int offset = (page - 1) * pageSize + 1;

        // 마지막 글 번호
        int limit = page * pageSize;

        // 해당 페이지의 게시글 목록
        List<PostDto> postDtoList = postService.selectPage(limit, offset, bno, type, keyword);

        // 전체 게시글 갯수
        int totalCount = postDtoList.size();

        // 페이징 정보
        PageDto pageDto = new PageDto(page, pageSize, totalCount);

        model.addAttribute("pageDto", pageDto);
        model.addAttribute("postDtoList", postDtoList);

        return "/board/post-list";

    }

    @GetMapping("/list-mobile")
    public String list_mobile(@RequestParam(required = true) int bno,
                       @RequestParam(required = false, defaultValue = "1") int page,
                       @RequestParam(required = false, defaultValue = "10") int pageSize,
                       @RequestParam(required = false, defaultValue = "1") int type,
                       @RequestParam(required = false, defaultValue = "") String keyword,
                       Model model) {

        // 시작 글 번호
        int offset = (page - 1) * pageSize + 1;

        // 마지막 글 번호
        int limit = page * pageSize;

        // 해당 페이지의 게시글 목록
        List<PostDto> postDtoList = postService.selectPage(limit, offset, bno, type, keyword);

        // 전체 게시글 갯수
        int totalCount = postDtoList.size();

        // 페이징 정보
        PageDto pageDto = new PageDto(page, pageSize, totalCount);

        model.addAttribute("pageDto", pageDto);
        model.addAttribute("postDtoList", postDtoList);

        return "/board/mobile-post-list";

    }

    @GetMapping("/write")
    public String write(Model model) {
        PostDto postDto = new PostDto();
        List<String> iconList = postService.getIconList();
        model.addAttribute("postDto", postDto);
        model.addAttribute("iconList", iconList);
        model.addAttribute("mode", CREATE);
        return "/board/post-write";
    }

    @GetMapping("/image")
    public String image(String content) {
        return "/board/image-test";
    }

    @PostMapping("/image.do")
    public void image_do(String content) {
        System.out.println("content=" + content);
    }

    @PostMapping("/write.do")
    public String write_do(@Validated PostDto postDto, BindingResult bindingResult, FileDto fileDto, int mode, Model model) throws IOException {

        if (!bindingResult.hasErrors()) {

            if (mode == 0) {
                postService.register(postDto, fileDto);
            } else {
                postService.update(postDto, fileDto);
            }
            return "redirect:/board/list?bno=" + postDto.getBno();
        }
        return "/board/post-write";
    }

    @GetMapping("/certificate")
    public String certificate(PostDto postDto, int mode, Model model) {
        model.addAttribute("postDto", postDto);
        model.addAttribute("mode", mode);
        return "/board/certificate";
    }

    @GetMapping("/read")
    public String read(int bno, int pno, Model model) {

        // 1. 게시글 정보
        PostDto postDto = postService.selectOne(bno, pno);

        // 2. 작성된 댓글 목록 정보
        List<CommentDto> commentDtoList = postService.selectCommentListByPost(pno);

        // 3. 사용자 작성 댓글 정보 - Empty 객체
        CommentDto commentDto = new CommentDto();

        // 4. 첨부파일 정보
        List<AttachmentDto> attachmentDtoList = postService.selectAttachmentByPost(pno);

        // 5. 아이콘 리스트
        List<String> iconList = postService.getIconList();

        model.addAttribute("postDto", postDto);
        model.addAttribute("commentDto", commentDto);
        model.addAttribute("commentDtoList", commentDtoList);
        model.addAttribute("attachmentDtoList", attachmentDtoList);
        model.addAttribute("iconList", iconList);

        return "/board/post-read";

    }

    @PostMapping("/write/comment.do")
    public String writeComment(CommentDto commentDto, HttpServletRequest req) {
        postService.insertComment(commentDto);
        return "redirect:" + req.getHeader("referer");
    }

    @GetMapping("/download")
    public void download(@RequestHeader("User-Agent") String userAgent, int ano, HttpServletResponse response) throws IOException {

        // 1. 첨부파일 조회
        AttachmentDto attachmentDto = postService.selectAttachment(ano);

        String folderPath = env.getProperty("spring.storage." + attachmentDto.getStorage_name());

        File f = new File(folderPath + File.separator, attachmentDto.getFile_name());
        // file 다운로드 설정

        String downloadName;

        if (userAgent.contains("Trident"))
            downloadName = URLEncoder.encode(attachmentDto.getFile_origin_name(), "UTF-8").replaceAll("\\+", "");
        else if (userAgent.contains("Edge"))
            downloadName = URLEncoder.encode(attachmentDto.getFile_origin_name(), "UTF-8");
        else
            downloadName = new String(attachmentDto.getFile_origin_name().getBytes("UTF-8"), "ISO-8859-1");

        response.setContentType("application/download; utf-8");
        response.setContentLength((int) f.length());
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-disposition", "attachment;filename=\"" + downloadName + "\"");
        // response 객체를 통해서 서버로부터 파일 다운로드
        OutputStream os = response.getOutputStream();
        // 파일 입력 객체 생성
        FileInputStream fis = new FileInputStream(f);
        FileCopyUtils.copy(fis, os);

    }

    @PostMapping("/delete/comment.do")
    @ResponseBody
    public Object deleteComment(@RequestBody Map<String, String> map, HttpServletRequest req) {

        int cno = Integer.parseInt(map.get("cno"));
        String password = map.get("password");

        int rowCnt = postService.deleteComment(cno, password);

        Map result = new HashMap();
        result.put("rowCnt", rowCnt);

        if (rowCnt > 0)
            result.put("result", "success");
        else
            result.put("result", "failure");

        return result;

    }

    @PostMapping("/certificate.do")
    public String certificate_do(PostDto postDto, int mode, Model model) {

        PostDto selectedPostDto = postService.selectOne(postDto.getBno(), postDto.getPno());

        if (selectedPostDto.getWriter().equals(postDto.getWriter()) && selectedPostDto.getPassword().equals(postDto.getPassword())) {

            if (mode == UPDATE) {
                List<AttachmentDto> attachmentDtoList = postService.selectAttachmentByPost(selectedPostDto.getPno());
                List<String> iconList = postService.getIconList();
                model.addAttribute("postDto", selectedPostDto);
                model.addAttribute("attachmentDtoList", attachmentDtoList);
                model.addAttribute("iconList", iconList);
                model.addAttribute("mode", UPDATE);
                return "/board/post-write";
            }

            else {
                postService.delete(postDto);
                return "/board/list?bno=" + postDto.getBno();
            }
        }

        model.addAttribute("postDto", postDto);
        return "/board/certificate";
    }


}
