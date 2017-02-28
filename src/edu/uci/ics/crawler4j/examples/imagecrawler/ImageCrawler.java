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
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;

import com.google.common.io.Files;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
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
    private static String lastUrl;

    private static File storageFolder;
    private static String[] crawlDomains;
    private Page parent;

    public static void configure(String[] domain, String storageFolderName) {
        crawlDomains = domain;

        storageFolder = new File(storageFolderName);
        if (!storageFolder.exists()) {
            storageFolder.mkdirs();
        }
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        
        if (filters.matcher(href).matches()) {
            return false;
        }

        if (imgPatterns.matcher(href).matches()) {
            return true;
        }

        for (String domain : crawlDomains) {
            if (href.startsWith(domain)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        String parentURL = page.getWebURL().getParentUrl();
        System.out.println("Padre " + parentURL);
        System.out.println(page.getWebURL().getParentUrl());
        System.out.println("Padre " + parentURL);
        System.out.println("Hijo: " + url);

        // Nombre �nico para almacenar esta imagen
        String extension = url.substring(url.lastIndexOf('.'));
        String hashedName = UUID.randomUUID() + extension;
        
        int index = extension.indexOf("/"); //Para la extensiones que les siguen subcarpetas
        int indexQ = extension.indexOf("?"); //Para par�metros en las URLs

		if(index < 0 && indexQ < 0){
	        System.out.println(extension);
	        // Almacenar la imagen
	        String filename = storageFolder.getAbsolutePath() + "/" + hashedName;
	        try {     
	            Files.write(page.getContentData(), new File(filename));
	            logger.info("Stored: {}", url);
	        } catch (IOException iox) {
	            logger.error("Failed to write file: " + filename, iox);
	        }
		}
		
		this.parent = page;
    }
    
    /*public void getMetadata(String parentURL, String url){
        String[] crawlDomains = {parentURL};
        String rootFolder = "C:/Users/Usuario/workspace/TFG/data";
        
        CrawlConfig config = new CrawlConfig();
    	config.setMaxDepthOfCrawling(0);
    	config.setCrawlStorageFolder(rootFolder);
    	config.setMaxDownloadSize(-1);
    	
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        try{
        	//Controller controller = new Controller(config, pageFetcher, robotstxtServer, crawlDomains[0], url);
        }
        catch(Exception e){
        	 logger.error("Failed get metadata: " + e);
        }
        
    }*/
}