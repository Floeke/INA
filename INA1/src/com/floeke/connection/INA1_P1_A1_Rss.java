package com.floeke.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class INA1_P1_A1_Rss {
	
	String rssFeedUrl;
	HttpURLConnection huc;
	
	public INA1_P1_A1_Rss(HttpURLConnection huc)
	{
		this.huc = huc;
	}
	
	public void parse() throws ParserConfigurationException, MalformedURLException, SAXException, IOException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new URL(rssFeedUrl).openStream());
		OutputStreamWriter outWriter = new OutputStreamWriter(System.out, "UTF-8");
		new INA1_P1_A1_DOMEcho(new PrintWriter(outWriter, true)).echo(doc);
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
			
			if(currentLine.contains("alternate"))
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
				subStr = currentLine.subSequence(from, to).toString();
				System.out.println("\n\n"+subStr);
				break;
				}
		}
		in.close();
		
		if(subStr==null)
		{
			System.out.println("♣ The site "+huc.toString().replaceAll("sun.net.www.protocol.http.HttpURLConnection:", "")+" does not contain a useable RSS-Feed ♣");
		}
		else {
			rssFeedUrl = subStr;
		}
		
		//System.out.println(builder.toString());
	}

}
