package edu.uci.ics.crawler4j.examples.imagecrawler;
import edu.uci.ics.crawler4j.crawler.Configurable;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller extends Configurable{
	
	public Controller(CrawlConfig config, PageFetcher pageFetcher,
            RobotstxtServer robotstxtServer, String domain) throws Exception {
		super(config);

        int numberOfCrawlers = 1;
        System.out.println("Dominio " + domain);
        /*
         * Instantiate the controller for this crawl.
         */
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        /*controller.addSeed("http://www.cervantesvirtual.com/images/");
        controller.addSeed("http://www.cervantesvirtual.com/areas/");*/
        controller.addSeed(domain);

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         * 
         */
        /*MyCrawlerFactory factory = new MyCrawlerFactory(metadata, repository);
        controller.startNonBlocking(factory, numberOfCrawlers);*/
        controller.start(Crawler.class, numberOfCrawlers);
    }
}