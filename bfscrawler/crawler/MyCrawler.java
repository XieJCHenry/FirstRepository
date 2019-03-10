package crawler;

import java.util.Set;

public class MyCrawler {

	private void initCrawlerWithSeeds(String[] seeds) {
		for (int i = 0; i < seeds.length; i++)
			LinkQueue.addUnVisitedUrl(seeds[i]);
	}

	public void crawling(String[] seeds) {
		LinkFilter filter = new LinkFilter() {
			public boolean accept(String url) {
				// TODO Auto-generated method stub
				if (url.startsWith(""))
					return true;
				else
					return false;
			}
		};
		initCrawlerWithSeeds(seeds);
		while (!LinkQueue.todoListIsEmpty() && LinkQueue.getVisitedUrlNum() <= 1000) {
			String visitUrl = LinkQueue.getUnVisitedUrl();
			if (visitUrl == null)
				continue;
			DownLoadFile downLoader = new DownLoadFile();
			downLoader.downloadPage(visitUrl, "C:\\Users\\a1098\\Desktop");
			LinkQueue.addVisitedUrl(visitUrl);
			Set<String> links = HtmlParserTool.extractLinks(visitUrl, filter);
			for (String s : links)
				LinkQueue.addUnVisitedUrl(s);
		}
	}

	public static void main(String[] args) {
		MyCrawler crawler = new MyCrawler();
		crawler.crawling(new String[] { "http://news.sohu.com/" });
	}
}
