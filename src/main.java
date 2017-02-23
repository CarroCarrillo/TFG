import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.examples.imagecrawler.ImageCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

public class main {

	public static void main(String[] args) {
		ImageCrawler ic = new ImageCrawler();
		WebURL url = new WebURL();
		url.setURL("http://google.com");
		Page referringPage = new Page(url);
		ic.shouldVisit(referringPage, url);
		ic.visit(referringPage);
		ic.run();

	}

}
