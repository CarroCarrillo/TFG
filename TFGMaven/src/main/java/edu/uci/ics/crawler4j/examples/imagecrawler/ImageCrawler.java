package edu.uci.ics.crawler4j.examples.imagecrawler;
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.common.io.Files;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.crawler.exceptions.PageBiggerThanMaxSizeException;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.URLCanonicalizer;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Yasser Ganjisaffar
 */

/*
 * This class shows how you can crawl images on the web and store them in a
 * folder. This is just for demonstration purposes and doesn't scale for large
 * number of images. For crawling millions of images you would need to store
 * downloaded images in a hierarchy of folders
 */
public class ImageCrawler extends WebCrawler {

    private static final Pattern filters = Pattern.compile(
        ".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" +
        "|rm|smil|wmv|swf|wma|zip|rar|gz|doc|html|xml|php|shtml))$");

    private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");

    private static File storageFolder;
    private static String[] crawlDomains;
    private boolean imParent = false;

    public static void configure(String[] domain, String storageFolderName) {
        crawlDomains = domain;
        
        if(storageFolderName != null){
	        storageFolder = new File(storageFolderName);
	        if (!storageFolder.exists()) {
	            storageFolder.mkdirs();
	        }
        }
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if(!imParent){
        if (filters.matcher(href).matches()) {
            return false;
        }

        if (imgPatterns.matcher(href).matches()) {
            return true;
        }

  
        if (href.startsWith("http://www.cervantesvirtual.com")) {
            return true;
        }
        
        return false;
        }else{
        	return true;
        }
    }

    @Override
    public void visit(Page page) {
    	System.out.println("Nueva Página");
    	if(!imParent){
    		if(shouldVisit(page, page.getWebURL()) && page.getWebURL().getParentUrl() != null){
		        String url = page.getWebURL().getURL();
		        
		    	// Nombre único para almacenar esta imagen
		        String extension = url.substring(url.lastIndexOf('.'));
		        String hashedName = UUID.randomUUID() + extension;
		        
		        int index = extension.indexOf("/"); //Para la extensiones que les siguen subcarpetas
		        int indexQ = extension.indexOf("?"); //Para parámetros en las URLs
		
				if(index < 0 && indexQ < 0){
		        
			        String imageName = getImageName(url);
			        WebURL parentWebUrl = new WebURL();
		            
		            parentWebUrl.setURL(page.getWebURL().getParentUrl());
		            parentWebUrl.imParent = true;
		            imParent = true;
		            Page parentPage = processPage(parentWebUrl);
		            
		            if (parentPage.getParseData() instanceof HtmlParseData) {
		                HtmlParseData htmlParseData = (HtmlParseData) parentPage.getParseData();
		                String html = htmlParseData.getHtml();
		               
		                Document document = Jsoup.parse(html);
		                Elements pngs = document.select("[src*=" + imageName + "], [href*=" + imageName + "]");
		                Elements parents = pngs.parents();
		                Document parentsParsed = Jsoup.parse(parents.html());
		                String h2 = parentsParsed.getElementsByTag("h2").last().text();
		                String title = pngs.attr("title");
		                String alt = pngs.attr("alt");
		                String h1 = parentsParsed.getElementsByTag("h1").last().text();
		                /*System.out.println(imageName);
		                System.out.println(pngs.toString());
		                */
		                System.out.println("Parent page: " + parentPage.getWebURL().getURL());
		                System.out.println("Page:        " + page.getWebURL().getURL());
		                System.out.println("Nombre: " + imageName);
		                System.out.println("H1: " + h1);
		                System.out.println("H2: " + h2);
			            System.out.println("Título: " + title);
			            System.out.println("Alt: " + alt);
				        System.out.println("Extensión: " + extension);
		            }		        
		         
			        // Almacenar la imagen
			        String filename = storageFolder.getAbsolutePath() + "/" + hashedName;
			        try {     
			            Files.write(page.getContentData(), new File(filename));
			            System.out.println("Stored: " + url);
			        } catch (IOException iox) {
			        	System.out.println("Failed to write file: " + filename + " " + iox);
			        }
				}
	    	}
    	}
    	imParent = false;
    }
    
    public String getImageName(String url){
        String imageName = url.substring(url.lastIndexOf('/'));
        if(imageName.equals("/")){
        	imageName = url.substring(0, url.lastIndexOf('/'));
        	imageName = imageName.substring(imageName.lastIndexOf('/'));
        }
        return imageName;
    }
}