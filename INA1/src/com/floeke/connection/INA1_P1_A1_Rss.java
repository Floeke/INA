package com.floeke.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class which can search for a RSS-Feed on a given HttpUrlConnection, which can also print out said RSS-Feed
 * @author Floeke
 */
public class INA1_P1_A1_Rss {
	
	String rssFeedUrl;
	HttpURLConnection huc;
	
	/**
	 * Constructor
	 * @param huc Connection to a site which should contain a RSS-Feed (Not the Connection to the RSS-Feed itself!)
	 */
	public INA1_P1_A1_Rss(HttpURLConnection huc)
	{
		this.huc = huc;
	}
	 
	/**
	 * Searches for all items and Prints them out on the console
	 * @throws IOException Could not open a Connection to the given RSS-Feed
	 */
	public void searchItems() throws IOException
	{
		//Variables
		StringBuilder builder = new StringBuilder();
		HttpURLConnection rssConnection = (HttpURLConnection) new URL(rssFeedUrl).openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(rssConnection.getInputStream()));
		String currentLine;
		
		//Read the next line while it's not null
		while((currentLine = in.readLine()) != null)
		{
			
			//Enclosed item tags
			if(currentLine.contains("<item>"))
			{
				do
				{
					//TODO Add snipping for 1-line RSS-Feed
					
					//Enclosed title tags
					if(currentLine.contains("<title>"))
					{
						while(!currentLine.contains("</title>"))
						{
							currentLine = currentLine + in.readLine();
						}
						
						builder.append("\nTitel: " + currentLine.replace("<title>", "").replace("</title>", ""));
					}
					
					//Enclosed description tags
					if(currentLine.contains("<description>"))
					{
						while(!currentLine.contains("</description>"))
						{
							currentLine = currentLine + in.readLine();
						}
						
						builder.append("\nDescription: " + currentLine.replace("<description>", "").replace("</description>", ""));
					}
					
					//Enclosed category tags
					if(currentLine.contains("<category>"))
					{
						while(!currentLine.contains("</category>"))
						{
							currentLine = currentLine + in.readLine();
						}
						
						builder.append("\nKategorie: " + currentLine.replace("<category>", "").replace("</category>", ""));
					}
					
					//Enclosed pubDate tags
					if(currentLine.contains("<pubDate>"))
					{
						while(!currentLine.contains("</pubDate>"))
						{
							currentLine = currentLine + in.readLine();
						}
						
						builder.append("\nPublished: " + currentLine.replace("<pubDate>", "").replace("</pubDate>", ""));
					}
					
					//Read the next line
					if(!currentLine.contains("</item>"))
					{
						currentLine = in.readLine();
					}
									
				} while(!currentLine.contains("</item>"));
				
				builder.append("\n");
			}
		}
		
		in.close();
		System.out.println(builder.toString().replace("ä", "ae").replace("ö", "oe").replace("ü", "ue"));
	}
	
	
	/**
	 * Searches for an RssFeed to the given Connection
	 * @throws IOException Could not connect to InputSream
	 */
	public void searchForRssFeed() throws IOException
	{
		//Variables
		StringBuilder builder = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(huc.getInputStream()));
		String currentLine, subStr = null;
		
		//Read the next line while it's not null
		while((currentLine = in.readLine()) != null)
		{
			builder.append(currentLine);
			builder.append("\n");
			
			//Line contains keywords
			if(currentLine.contains("alternate") && currentLine.contains("link") && (currentLine.contains("rss") || currentLine.contains("xml")))
			{
				System.out.println(currentLine);
				int from = currentLine.indexOf("href=") + 6;
				int to = 0;
				
				//Further tests
				if(currentLine.contains(".rss"))
				{
					to = currentLine.indexOf(".rss") + 4;
				}
				if(currentLine.contains(".xml"))
				{
					to = currentLine.indexOf(".xml") + 4;
				}
				if(currentLine.contains("rss."))
				{
					to = currentLine.indexOf(">") - 2;
				}
				
				if(to < from || from < 0)
				{
					System.err.println("Could not find RSS-Feed");
					break;
				}
				subStr = currentLine.subSequence(from, to).toString();
				System.out.println("\n\n"+subStr);
				
				if(subStr != null)
					break;
				}
		}
		in.close();
		
		if(subStr==null)
		{
			System.err.println("♣ The site "+huc.toString().replaceAll("sun.net.www.protocol.http.HttpURLConnection:", "")+" does not contain a useable RSS-Feed ♣");
		}
		else {
			rssFeedUrl = subStr;
		}
		
		//System.out.println(builder.toString());
	}

}
