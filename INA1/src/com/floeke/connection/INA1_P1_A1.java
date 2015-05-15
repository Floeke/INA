package com.floeke.connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class INA1_P1_A1 {
	
	public static HttpURLConnection huc;
	
	public static void main(String []args)
	{
		System.out.println("Please enter a URL: ");
		Scanner scanner = new Scanner(System.in);
		String urlString = scanner.next();
		scanner.close();
		
		if(!urlString.startsWith("http://"))
		{
			urlString = "http://" + urlString;
		}
		
		System.out.println("\nTrying to connect to "+urlString+" please wait...\n");
		try {
			HttpURLConnection huc = (HttpURLConnection) new URL(urlString).openConnection();
			huc.setRequestMethod("GET");
			
			if(huc.getResponseCode() != HttpURLConnection.HTTP_OK)
			{
				System.out.println("Server-Status is not OK! Server-Message: " + huc.getResponseMessage());
			} else {
					INA1_P1_A1_Rss rssReader = new INA1_P1_A1_Rss(huc);
					rssReader.searchForRssFeed();
					rssReader.searchItems();
					}		
			
		} catch (MalformedURLException e) {
			System.err.println("♥ "+urlString+" is not a valid URL! ♣\n");
		} catch (IOException e) {
			System.err.println("♥ "+e.getMessage()+" caused a I/O-ERROR! ♣\n");
		}		
	}

}
