package edu.uci.ics.crawler4j.examples.imagecrawler;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;

public class MyCrawlerFactory implements CrawlController.WebCrawlerFactory<WebCrawler> {

    private String parentHtml;

    public MyCrawlerFactory(String parentHtml) {
    	this.parentHtml = parentHtml;
    }

    @Override
    public WebCrawler newInstance() {
        return new ImageCrawler();
    }
}
