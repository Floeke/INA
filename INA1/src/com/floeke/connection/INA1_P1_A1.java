package com.floeke.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
				try{
					INA1_P1_A1_Rss rssReader = new INA1_P1_A1_Rss(huc);
					rssReader.searchForRssFeed();
					rssReader.parse();
				} catch (Exception e)
				{
					System.out.println("Failed to parse!");
				}
				
			}
			
		} catch (MalformedURLException e) {
			System.out.println("♥ "+e.getMessage()+" is not a valid URL ♣\n");
		} catch (IOException e) {
			System.out.println("♥ "+e.getMessage()+" caused a I/O-ERROR ♣\n");
		}
		
		
	}

}
