package com.careydevelopment.twitterautomation.util;

import java.util.ArrayList;
import java.util.List;

import com.careydevelopment.twitterautomation.domain.Blog;

public class BlogHelper {

	public static List<Blog> getBlogs() {
		List<Blog> blogs = new ArrayList<Blog>();
		Blog b = new Blog();
		
		b.setBlogName("UPROXX");
		b.setBlurbStarter("class=\"post-body\">");
		b.setRssUrl("http://feeds.feedburner.com/uproxx/springboard");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("BroBible");
		b.setBlurbStarter("<div class=\"post-body\">");
		b.setRssUrl("http://feeds.feedburner.com/BroBible");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Mediaite");
		b.setBlurbStarter("<div id=\"post-body\">");
		b.setRssUrl("http://www.mediaite.com/feed/");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("HuffPo");
		b.setBlurbStarter("<!-- Entry Text -->");
		b.setBlurbEnder("<!-- Follow Tag Links Bottom -->");
		b.setRssUrl("http://www.huffingtonpost.com/feeds/index.xml");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("HuffPo");
		b.setBlurbStarter("property='article:body' content='");
		b.setBlurbEnder("'>");
		b.setRssUrl("http://www.huffingtonpost.com/feeds/index.xml");
		blogs.add(b);

		b=new Blog();
		b.setBlogName("HuffPo");
		b.setBlurbStarter("<div class='entry-component__content'>");
		b.setRssUrl("http://www.huffingtonpost.com/feeds/index.xml");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("The Daily Beast");
		b.setBlurbStarter("article-body-content\">");
		b.setRssUrl("http://feeds.feedburner.com/thedailybeast/articles");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Breitbart");
		b.setBlurbStarter("<div class=\"entry-content\">");
		b.setBlurbEnder("<!--entry-content-->");
		b.setRssUrl("http://feeds.feedburner.com/breitbart");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Clash Daily");
		b.setBlurbStarter("class=\"entry-content clearfix\" itemprop=\"articleBody\"");
		b.setRssUrl("http://clashdaily.com/feed/");
		b.setImageFromUrl(true);
		blogs.add(b);		
		
		b=new Blog();
		b.setBlogName("Elite Daily");
		b.setBlurbStarter("itemprop=\"articleBody\">");
		b.setRssUrl("http://elitedaily.com/feed/");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("ThinkProgress");
		b.setBlurbStarter("itemprop=\"articleBody\">");
		b.setRssUrl("http://thinkprogress.org/feed/");
		b.setImageFromUrl(true);
		blogs.add(b);

		b=new Blog();
		b.setBlogName("Daily Kos");
		b.setBlurbStarter("itemprop=\"articleBody\">");
		b.setRssUrl("http://feeds.dailykos.com/dailykos/index.xml");
		b.setImageFromUrl(true);
		blogs.add(b);

		b=new Blog();
		b.setBlogName("Gawker");
		b.setBlurbStarter("class=\"first-text\"");
		b.setRssUrl("http://gawker.com/rss");
		blogs.add(b);				
		
		b=new Blog();
		b.setBlogName("The Daily Mail");
		b.setBlurbStarter("DMS.Article.init('top');");
		b.setRssUrl("http://www.dailymail.co.uk/home/index.rss");
		b.setImageFromUrl(true);
		blogs.add(b);		
		
		b=new Blog();
		b.setBlogName("Jezebel");
		b.setBlurbStarter("class=\"first-text\"");
		b.setRssUrl("http://jezebel.com/rss");
		blogs.add(b);	
		
		b=new Blog();
		b.setBlogName("The New York Post");
		b.setBlurbStarter("<div class=\"entry-content\">");
		b.setRssUrl("http://www.nypost.com/rss/all_section.xml");
		blogs.add(b);	
		
		b=new Blog();
		b.setBlogName("The New York Daily News");
		b.setBlurbStarter("=\"articleBody\">");
		b.setRssUrl("http://feeds.nydailynews.com/nydnrss");
		blogs.add(b);			
		
		
		b=new Blog();
		b.setBlogName("The Daily Caller");
		b.setBlurbStarter("'post article-content' id=thepost>");
		b.setRssUrl("http://dailycaller.com/rss");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("TheBlaze");
		b.setBlurbStarter("itemprop=\"articleBody\">");
		b.setRssUrl("http://www.theblaze.com/stories/feed");
		b.setImageFromUrl(true);
		blogs.add(b);
		
		/*b=new Blog();
		b.setBlogName("Opposing Views");
		b.setBlurbStarter("<div class=\"article-body\">");
		b.setRssUrl("http://feeds.feedburner.com/opposingviews/main");
		blogs.add(b);	*/
		
		/*b=new Blog();
		b.setBlogName("Reason");
		b.setBlurbStarter("<div class=\"entry postcontent\">");
		b.setRssUrl("http://feeds.feedburner.com/reason/AllArticles");
		blogs.add(b);	
		
		b=new Blog();
		b.setBlogName("ProBlogger");
		b.setBlurbStarter("<div class=\"entry-content\">");
		b.setRssUrl("http://feeds.feedburner.com/ProbloggerHelpingBloggersEarnMoney");
		blogs.add(b);	*/
		
		b=new Blog();
		b.setBlogName("Business Insider");
		b.setBlurbStarter("<div class=\"media-body\">");
		b.setRssUrl("http://www.businessinsider.com/rss");
		blogs.add(b);
		
		/*b=new Blog();
		b.setBlogName("Copyblogger");
		b.setBlurbStarter("<div class=\"pf-content\">");
		b.setRssUrl("http://feeds.copyblogger.com/Copyblogger");
		blogs.add(b);*/
		
		b=new Blog();
		b.setBlogName("Entrepreneur");
		b.setBlurbStarter("<div class=\"bodycopy\"");
		b.setRssUrl("http://feeds.feedburner.com/entrepreneur/latest");
		blogs.add(b);
			
		b=new Blog();
		b.setBlogName("Harvard Business Review");
		b.setBlurbStarter("<div class=\"article\"");
		b.setRssUrl("http://feeds.harvardbusiness.org/harvardbusiness");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Hubspot Blog");
		b.setBlurbStarter("<span class=\"content-post__body\">");
		b.setRssUrl("http://blog.hubspot.com/marketing/rss.xml");
		blogs.add(b);

		b=new Blog();
		b.setBlogName("Lifehacker");
		b.setBlurbStarter("class=\"first-text\"");
		b.setRssUrl("http://feeds.gawker.com/lifehacker/full/");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Marketing Land");
		b.setBlurbStarter("class=\"body-content\">");
		b.setRssUrl("http://feeds.marketingland.com/mktingland");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Mashable");
		b.setBlurbStarter("<section class='article-content'>");
		b.setRssUrl("http://feeds.mashable.com/Mashable");
		blogs.add(b);		
		
		b=new Blog();
		b.setBlogName("Moz");
		b.setBlurbStarter("<div class=\"post-content\">");
		b.setRssUrl("http://feeds.feedburner.com/seomoz");
		blogs.add(b);

		b=new Blog();
		b.setBlogName("Online Marketing Blog");
		b.setBlurbStarter("<div class=\"entry-content\">");
		b.setRssUrl("http://feeds.feedburner.com/OnlineMarketingSEOBlog");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Search Engine Journal");
		b.setBlurbStarter("itemprop=\"articleBody\"");
		b.setRssUrl("http://feeds2.feedburner.com/quicksprout");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Search Engine Land");
		b.setBlurbStarter("class=\"body-content\">");
		b.setRssUrl("http://feeds.searchengineland.com/searchengineland");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Social Media Examiner");
		b.setBlurbStarter("<div id=\"single\">");
		b.setRssUrl("http://www.socialmediaexaminer.com/feed/");
		blogs.add(b);		
		
		b=new Blog();
		b.setBlogName("Social Media Today");
		b.setBlurbStarter("property=\"content:encoded\"");
		b.setRssUrl("http://feeds.feedburner.com/socialmediatoday_allposts");
		blogs.add(b);		

		b=new Blog();
		b.setBlogName("TechCrunch");
		b.setBlurbStarter("<!-- Begin: Wordpress Article Content -->");
		b.setRssUrl("http://feeds.feedburner.com/TechCrunch/");
		blogs.add(b);	
		
		b=new Blog();
		b.setBlogName("Buffer");
		b.setBlurbStarter("<div class=\"entry-content\">");
		b.setRssUrl("http://blog.bufferapp.com/feed/");
		blogs.add(b);		
		
		b=new Blog();
		b.setBlogName("The Kissmetrics Blog");
		b.setBlurbStarter("<div class=\"entry-content\">");
		b.setRssUrl("http://feeds.feedburner.com/KISSmetrics");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Complex");
		b.setBlurbStarter("itemprop=\"articleBody\">");
		b.setRssUrl("http://assets.complex.com/feeds/channels/all.xml");
		blogs.add(b);

		b=new Blog();
		b.setBlogName("The Daily Dot");
		b.setBlurbStarter("<div class=\"article-body\">");
		b.setRssUrl("http://www.dailydot.com/feed/summary/latest/");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Gizmodo");
		b.setBlurbStarter("class=\"first-text\"");
		b.setRssUrl("http://feeds.gawker.com/gizmodo/full");
		blogs.add(b);		

		b=new Blog();
		b.setBlogName("io9");
		b.setBlurbStarter("class=\"first-text\"");
		b.setRssUrl("http://feeds.gawker.com/io9/full");
		blogs.add(b);		

		b=new Blog();
		b.setBlogName("Kotaku");
		b.setBlurbStarter("class=\"first-text\"");
		b.setRssUrl("http://feeds.gawker.com/kotaku/full");
		blogs.add(b);	

		b=new Blog();
		b.setBlogName("Laughing Squid");
		b.setBlurbStarter("<main class=\"content\" id=\"main-content\">");
		b.setRssUrl("http://laughingsquid.com/feed/");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Viral Nova");
		b.setBlurbStarter("<div class=\"content\">");
		b.setRssUrl("http://www.viralnova.com/feed/");
		blogs.add(b);
				
		b=new Blog();
		b.setBlogName("Wired");
		b.setBlurbStarter("itemprop=\"articleBody\">");
		b.setRssUrl("http://feeds.wired.com/wired/index");
		blogs.add(b);			

		/*b=new Blog();
		b.setBlogName("Consumerist");
		b.setBlurbStarter("<div id=\"content\" class=\"hfeed\">");
		b.setRssUrl("http://consumerist.com/feed/");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Greatest Health Articles");
		b.setBlurbStarter("itemprop=\"articleBody\">");
		b.setRssUrl("http://feeds.feedblitz.com/greatist");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Michael Hyatt's Intentional Leadership");
		b.setBlurbStarter("<div class=\"entry-content\">");
		b.setRssUrl("http://feeds2.feedburner.com/michaelhyatt");
		blogs.add(b);		

		b=new Blog();
		b.setBlogName("Money Talks News");
		b.setBlurbStarter("<div class=\"stories catside-block\">");
		b.setRssUrl("http://www.moneytalksnews.com/feed/");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Nerd Fitness");
		b.setBlurbStarter("<div class=\"entry-content\">");
		b.setRssUrl("http://www.nerdfitness.com/feed/");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Next Avenue");
		b.setBlurbStarter("<div class=\"entry-content\">");
		b.setRssUrl("http://www.nextavenue.org/feeds/articles-rss.xml");
		blogs.add(b);		
		*/
		b=new Blog();
		b.setBlogName("Psychology Today");
		b.setBlurbStarter("<div class=\"subtext insertArea--origin\">");
		b.setRssUrl("https://www.psychologytoday.com/rss.xml");
		blogs.add(b);		
		
		b=new Blog();
		b.setBlogName("The Simple Dollar");
		b.setBlurbStarter("<div class=\"post\">");
		b.setRssUrl("http://feeds.feedburner.com/thesimpledollar");
		blogs.add(b);	

		b=new Blog();
		b.setBlogName("Wealth Pilgrim");
		b.setBlurbStarter("entry-content\">");
		b.setRssUrl("http://feeds2.feedburner.com/WealthPilgrim");
		blogs.add(b);	
		
		b=new Blog();
		b.setBlogName("WebMD Health");
		b.setBlurbStarter("<div id=\"textArea\" class=\"copyNormal\">");
		b.setRssUrl("http://rssfeeds.webmd.com/rss/rss.aspx?RSSSource=RSS_PUBLIC");
		blogs.add(b);	
		
		b=new Blog();
		b.setBlogName("Wise Bread");
		b.setBlurbStarter("<!-- midarticle: first half -->");
		b.setRssUrl("http://feeds.killeraces.com/wisebread");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Dude Manual");
		b.setBlurbStarter("entry-content");
		b.setRssUrl("http://dudemanual.com/feed/");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Man's Life");
		b.setBlurbStarter("<div class=\"post-content\">");
		b.setRssUrl("http://manslife.com/feed/");
		blogs.add(b);
		

		b=new Blog();
		b.setBlogName("Hollywood Reporter");
		b.setBlurbStarter("class=\"blog-post-body\">");
		b.setRssUrl("http://feeds.feedburner.com/thr/news");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("Maxim");
		b.setBlurbStarter("property=\"content:encoded\">");
		b.setRssUrl("http://www.maxim.com/rss-feeds.xml");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("The Federalist");
		b.setBlurbStarter("<div class=\"entry-content");
		b.setRssUrl("http://thefederalist.com/feed/");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("CNS News");
		b.setBlurbStarter("<div class=\"content\">");
		b.setRssUrl("http://cnsnews.com/feeds/all");
		blogs.add(b);
		
	
		b=new Blog();
		b.setBlogName("Oddity Central");
		b.setBlurbStarter("<div class=\"entry\">");
		b.setRssUrl("http://feeds.feedburner.com/OddityCentral");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("National Review");
		b.setBlurbStarter("<time class=");
		b.setRssUrl("https://www.nationalreview.com/rss.xml");
		b.setImageFromUrl(true);
		blogs.add(b);

		
		/*b=new Blog();
		b.setBlogName("WebPro News");
		b.setBlurbStarter("<div class=entry>");
		b.setRssUrl("http://www.webpronews.com/feed");
		blogs.add(b);	*/	
		

		b=new Blog();
		b.setBlogName("Quick Sprout");
		b.setBlurbStarter("<div class=\"entry-content\">");
		b.setRssUrl("http://feeds2.feedburner.com/quicksprout");
		blogs.add(b);


		b=new Blog();
		b.setBlogName("Fast Company");
		b.setBlurbStarter("class=\"body ");
		b.setRssUrl("http://feeds.feedburner.com/fastcompany/headlines");
		blogs.add(b);
		
		b=new Blog();
		b.setBlogName("BizPac Review");
		b.setBlurbStarter("<div class=\"entry-content\">");
		b.setRssUrl("http://www.bizpacreview.com/feed");
		b.setImageFromUrl(true);
		blogs.add(b);
		
		//uses captcha
		//b=new Blog();
		//b.setBlogName("Latest Science News");
		//b.setBlurbStarter("id=first class=lead>");
		//b.setRssUrl("http://feeds.sciencedaily.com/sciencedaily");
		//blogs.add(b);	
		
		//too many redirects
		//b=new Blog();
		//b.setBlogName("New Scientist");
		//b.setBlurbStarter("<div class=\"article-copy\">");
		//b.setRssUrl("http://feeds.newscientist.com/science-news");
		//blogs.add(b);
		
		return blogs;
	}
}
