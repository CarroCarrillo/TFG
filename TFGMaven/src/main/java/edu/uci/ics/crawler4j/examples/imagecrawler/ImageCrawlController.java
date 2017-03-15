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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * @author Yasser Ganjisaffar
 */
public class ImageCrawlController {
    private static final Logger logger = LoggerFactory.getLogger(ImageCrawlController.class);

    public static void main(String[] args) throws Exception {
        String rootFolder = "C:/Users/Usuario/workspace/TFG/TFGMaven/data";
        int numberOfCrawlers = 7;
        String storageFolder = "C:/Users/Usuario/workspace/TFG/TFGMaven/data";

        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder(rootFolder);

    /*
     * Ya que nuestras imágenes son contenido binario, necesitamos
     * establecer este parámetro a verdadero para que las tenga en cuenta.
     */
        config.setIncludeBinaryContentInCrawling(true);

        String[] crawlDomains = {"http://www.cervantesvirtual.com"};

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        for (String domain : crawlDomains) {
            controller.addSeed(domain);
        }

        ImageCrawler.configure(crawlDomains, storageFolder);

        controller.startNonBlocking(ImageCrawler.class, numberOfCrawlers);
    }
}