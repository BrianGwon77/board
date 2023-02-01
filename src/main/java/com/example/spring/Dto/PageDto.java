package com.example.spring.Dto;

import lombok.Data;

@Data
public class PageDto {

    private int page;
    private int pageSize;
    private int navi;
    private final int naviSize = 5;
    private int beginPage;
    private int endPage;
    private int totalCount;
    private boolean showPrev;
    private boolean showNext;

    public PageDto() {}
    public PageDto(int page, int pageSize, int totalCount) {

        this.page = page;
        this.pageSize= pageSize;
        this.totalCount = totalCount;

        this.navi = (int)Math.ceil(((double)page/naviSize));
        this.beginPage = (navi-1)*naviSize + 1;
        this.endPage = Math.min(naviSize*navi, (int)Math.ceil(((double)totalCount/pageSize)));
        this.showPrev = (page != 1) ? true : false;
        this.showNext = (page != (int)Math.ceil(((double)totalCount/pageSize))) ? true : false;

    }
}
