package com.floeke.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class INA1_P1_A1_Rss {
	
	String rssFeedUrl;
	HttpURLConnection huc;
	
	public INA1_P1_A1_Rss(HttpURLConnection huc)
	{
		this.huc = huc;
	}
	 
	public void searchItems() throws IOException
	{
		StringBuilder builder = new StringBuilder();
		HttpURLConnection rssConnection = (HttpURLConnection) new URL(rssFeedUrl).openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(rssConnection.getInputStream()));
		String currentLine;
		
		while((currentLine = in.readLine()) != null)
		{
			
			if(currentLine.contains("<item>"))
			{
				while(!currentLine.contains("</item>"))
				{
					if(currentLine.contains("<title>"))
					{
						while(!currentLine.contains("</title>"))
						{
							currentLine = currentLine + in.readLine();
						}
						
						builder.append("\nTitel: " + currentLine.replace("<title>", "").replace("</title>", ""));
					}
					
					if(currentLine.contains("<description>"))
					{
						while(!currentLine.contains("</description>"))
						{
							currentLine = currentLine + in.readLine();
						}
						
						builder.append("\nDescription: " + currentLine.replace("<description>", "").replace("</description>", ""));
					}
					
					if(currentLine.contains("<category>"))
					{
						while(!currentLine.contains("</category>"))
						{
							currentLine = currentLine + in.readLine();
						}
						
						builder.append("\nKategorie: " + currentLine.replace("<category>", "").replace("</category>", ""));
					}
					
					
					if(currentLine.contains("<pubDate>"))
					{
						while(!currentLine.contains("</pubDate>"))
						{
							currentLine = currentLine + in.readLine();
						}
						
						builder.append("\nPublished: " + currentLine.replace("<pubDate>", "").replace("</pubDate>", ""));
					}
					
					
					currentLine = in.readLine();
									
				}
				
				builder.append("\n");
			}
		}
		
		System.out.println(builder.toString().replace("ä", "ae").replace("ö", "oe").replace("ü", "ue"));
	}
	
	
	public void searchForRssFeed() throws IOException
	{
		StringBuilder builder = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(huc.getInputStream()));
		String currentLine, subStr = null;
		
		while((currentLine = in.readLine()) != null)
		{
			builder.append(currentLine);
			builder.append("\n");
			if(currentLine.contains("alternate") && currentLine.contains("link rel=") && (currentLine.contains("rss") || currentLine.contains("xml")))
			{
				System.out.println(currentLine);
				int from = currentLine.indexOf("href=") + 6;
				int to=0;
				if(currentLine.contains(".rss"))
				{
					to = currentLine.indexOf(".rss") + 4;
				}
				if(currentLine.contains(".xml"))
				{
					to = currentLine.indexOf(".rss") + 4;
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
