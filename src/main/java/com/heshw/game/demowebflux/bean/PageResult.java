package com.heshw.game.demowebflux.bean;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private long total;
    private int pages;
    private int pageSize;
    private int pageNum;
    private List<T> list;
}
