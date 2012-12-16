import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/********************************************************************
 * Class: News
 * Purpose: holds Kettering news
/*******************************************************************/
public class News {

	private List<NewsItem> news;
	
	/********************************************************************
	 * Constructor: News
	 * Purpose: create default news object
	/*******************************************************************/
	public News(){
		
		String baseURL = "https://www.kettering.edu";
		this.news = new ArrayList<NewsItem>();
		
		try{
			
			DefaultHttpClient client = new DefaultHttpClient();
			
			// Execute
			HttpGet newsGet = new HttpGet("https://www.kettering.edu/news/current-news");
			HttpResponse response = client.execute(newsGet);
			String html = HTMLParser.parse(response);
			
			// Write to file
			PrintWriter printer = new PrintWriter("artifacts/news/news.html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored \"news.html\".");
			
			Elements articles = Jsoup.parse(html).getElementsByClass("more");
			
			int count = 0;
			
			for(Element article : articles){
				if(article.attr("href") != null){
					
					count++;
					
					// Execute
					response = client.execute(new HttpGet(baseURL + article.attr("href")));
					html = HTMLParser.parse(response);
					
					// Write to file
					printer = new PrintWriter("artifacts/news/article" + count + ".html");
					printer.print(html);	    	
					printer.close();
					
					System.out.println("Successfully stored \"article" + count + ".html\".");
					
					Document doc = Jsoup.parse(html);
					
					// Correct format ?
					if(doc.getElementsByClass("news").size() > 0 && doc.getElementsByClass("info").size() > 0 && doc.getElementsByClass("intro").size() > 0 && doc.getElementsByClass("intro").get(0).nextElementSibling() != null){
						
						// Set properties
						String title = doc.getElementsByClass("news").get(0).text();
						String headline = doc.getElementsByClass("intro").get(0).text();
						String date = doc.getElementsByClass("date").get(0).text();
						String body = doc.getElementsByClass("intro").get(0).nextElementSibling().toString();
						
						// Add
						this.news.add(new NewsItem(title, headline, date, body));
					}
				}	
			}
		}
		
		catch(Exception e){ this.news = new ArrayList<NewsItem>(); }
		
	}
	
	
	/********************************************************************
	 * Method: getPage
	 * Purpose: returns page result
	/*******************************************************************/
	public List<NewsItem> getPage(int page){
		
		
		String baseURL = "https://www.kettering.edu";
		List<NewsItem> newsPage = new ArrayList<NewsItem>();
		
		if(page < 2) return newsPage;
		
		try{
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet newsGet = new HttpGet("https://www.kettering.edu/news/current-news?page=" + --page);
			
			HttpResponse response = client.execute(newsGet);
			String html = HTMLParser.parse(response);
			
			// Write to file
			PrintWriter printer = new PrintWriter("artifacts/news/newsPage" + page + ".html");
			printer.print(html);	    	
			printer.close();
			
			System.out.println("Successfully stored \"newsPage" + page + ".html\".");
			
			Elements articles = Jsoup.parse(html).getElementsByClass("more");
			
			for(Element article : articles){
				if(article.attr("href") != null){
					
					// Execute
					response = client.execute(new HttpGet(baseURL + article.attr("href")));
					
					Document doc = Jsoup.parse(HTMLParser.parse(response));
					
					// Correct format ?
					if(doc.getElementsByClass("news").size() > 0 && doc.getElementsByClass("info").size() > 0 && doc.getElementsByClass("intro").size() > 0 && doc.getElementsByClass("intro").get(0).nextElementSibling() != null){
						
						// Set properties
						String title = doc.getElementsByClass("news").get(0).text();
						String headline = doc.getElementsByClass("intro").get(0).text();
						String date = doc.getElementsByClass("date").get(0).text();
						String body = doc.getElementsByClass("intro").get(0).nextElementSibling().toString();
						
						// Add
						newsPage.add(new NewsItem(title, headline, date, body));
					}
				}	
			}
			
			return newsPage;
			
		}
		
		catch(Exception e){ return new ArrayList<NewsItem>(); }
		
		
	}
	
	
	/********************************************************************
	 * Accessors: getNews
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public List<NewsItem> getNews(){ return this.news; }
	
}


/********************************************************************
 * Class: NewsItem
 * Purpose: holds one news article
/******************************************************************/
class NewsItem{
	
	// Properties
	private String title;
	private String headline;
	private String date;
	private String body;
	
	/********************************************************************
	 * Constructor: NewsItem
	 * Purpose: creates one news article
	/*******************************************************************/
	public NewsItem(String title, String headline, String date, String body){
		
		// Set properties
		this.title = title;
		this.headline = headline;
		this.date = date;
		this.body = body;
	}
	
	
	/********************************************************************
	 * Accessors: getTitle getHeadline, getDate, getBody
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getTitle(){ return this.title; }
	public String getHeadline(){ return this.headline; }
	public String getDate(){ return this.date; }
	public String getBody(){ return this.body; }
	
	
	
	/********************************************************************
	 * Method: toString
	 * Purpose: format object into a string
	/*******************************************************************/
	public String toString(){ 
		
		return this.title + "\n" + this.date + "\n" + this.headline + "\n" + Jsoup.parse(this.body).text();
	}

}

