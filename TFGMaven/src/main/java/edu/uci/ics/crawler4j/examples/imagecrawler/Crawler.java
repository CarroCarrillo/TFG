package edu.uci.ics.crawler4j.examples.imagecrawler;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js"
                                                           + "|mp3|mp3|zip|gz))$");
    private String childURL;
    private String domain;
    
    public Crawler(String domain, String childURL){
    	super();
    	this.domain = domain;
    	this.childURL = childURL;
    }
    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         /*String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && href.startsWith("http://www.cervantesvirtual.com/");
                */
    	 return true;
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         System.out.println("URL2: " + url);
         System.out.println("Padre URL2: " + page.getWebURL().getParentUrl());
         System.out.println("Dominio " + domain);
         if(url.equals(domain)){
        	 System.out.println("�SON IGUALES!");
         }
         System.out.println("Hijo: " + this.childURL);

         /*if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();

             System.out.println(html);
             
         }*/
    }
}