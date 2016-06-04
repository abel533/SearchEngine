/*
 * Copyright (c) 2016 lcc523572741@qq.com
 */

package com.lcc.searchengine.controller;

import com.lcc.searchengine.lucene.LuceneUtil;
import com.lcc.searchengine.model.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 搜索首页，查询
 *
 * @author lcc
 */
@RestController
public class IndexController {

    /**
     * 查询数据
     *
     * @param q
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping({"", "index", "query"})
    public ModelAndView query(String q,
                              @RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer rows) {
        ModelAndView result = new ModelAndView();
        if (StringUtils.isNotEmpty(q)) {
            result.setViewName("result");
            PageInfo pageInfo = LuceneUtil.query(q, page, rows);
            result.addObject("q", q);
            result.addObject("page", pageInfo);
        } else {
            result.setViewName("index");
        }
        return result;
    }

}
