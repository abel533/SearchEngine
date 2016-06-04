/*
 * Copyright (c) 2016 lcc523572741@qq.com
 */

package com.lcc.searchengine.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 华信
 *
 * @author lcc
 * @since 2016-05-19 21:30
 */
public class HuaxinPageProcesser implements PageProcessor {
    public static final String NEWS_LIST = "http://blog\\.csdn\\.net/isea533/article/list/\\d+$";
    public static final String NEWS_POST = "http://blog\\.csdn\\.net/isea533/article/details/\\d+$";
    public static final String NEWS_SITE = "http://blog\\.csdn\\.net/isea533.*";

    @Override
    public void process(Page page) {
        Document document = Jsoup.parse(page.getHtml().toString());
        if (page.getUrl().regex(NEWS_LIST).match()) {
            Elements elements = document.select(".link_title a");
            for (Element element : elements) {
                page.addTargetRequest(element.attr("href"));
            }
            page.setSkip(true);
        } else if (page.getUrl().regex(NEWS_POST).match()) {
            String title = document.select(".article_title .link_title").text();
            String url = page.getUrl().toString();
            String content = document.select("div#article_details").text();
            page.putField("title", title);
            page.putField("url", url);
            page.putField("content", content);
        } else if (page.getUrl().regex(NEWS_SITE).match()) {
            page.addTargetRequests(page.getHtml().links().regex(NEWS_SITE).all());
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain("blog.csdn.net").setRetryTimes(8).setSleepTime(500);
    }
}
