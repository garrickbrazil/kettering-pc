import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

/********************************************************************
 * Class: News
 * Purpose: holds Kettering news
/*******************************************************************/
public class News {

	private NewsJSON news;
	
	/********************************************************************
	 * Constructor: News
	 * Purpose: create default news object
	/*******************************************************************/
	public News(){
		
		try{
			DefaultHttpClient client = new DefaultHttpClient();
			
			// Execute
			HttpGet newsGet = new HttpGet("https://drupal.kettering.edu:8443/kumobile/rest/news");
			HttpResponse response = client.execute(newsGet);
			String json = HTMLParser.parse(response);
			
			// Set JSON
			this.news =  new Gson().fromJson(json, NewsJSON.class);
			
		}
		
		catch(Exception e){ this.news = new NewsJSON(); }
		
	}
	
	
	/********************************************************************
	 * Accessors: getNews
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public List<NewsItem> getNews(){ return this.news.getItems(); }
	
	
}

/********************************************************************
 * Class: NewsJSON
 * Purpose: holds JSON news list
/*******************************************************************/
class NewsJSON{
	
	// Properties
	private List<NewsItem> entries;

	/********************************************************************
	 * Constructor: NewsJSON
	 * Purpose: creates a default news JSON
	/*******************************************************************/
	public NewsJSON(){
		this.entries = new ArrayList<NewsItem>();
	}
	
	// Accessors
	public List<NewsItem> getItems(){ return this.entries; }
	
}

/********************************************************************
 * Class: NewsItem
 * Purpose: holds JSON news item
/*******************************************************************/
class NewsItem{
	
	// Properties
	private String headline;
	private String summary;
	private String body;
	
	
	/********************************************************************
	 * Accessors: getHeadline, getSummary, getBody
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getHeadline(){ return this.headline; }
	public String getSummary(){ return this.summary; }
	public String getBody(){ return this.body; }
	
	
}

