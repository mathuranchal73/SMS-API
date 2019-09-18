package com.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Test {
	
	public static void main(final String args[]) {
		if(args.length!=1)
		{
			System.err.println("Incorrect URL");
		}
		
		ExecutorService executor= Executors.newSingleThreadExecutor();
		Callable<List<String>> callable;
		callable = new Callable<List<String>>()
		
		{
			@Override
			public List<String> call()
				throws IOException, MalformedURLException
				{
					List<String> lines= new ArrayList<>();
					URL url= new URL("https://www.gmail.com");
					HttpURLConnection con;
					con= (HttpURLConnection) url.openConnection();
					BufferedReader br= new BufferedReader(new InputStreamReader(con.getInputStream()));
					String line;
					while((line=br.readLine())!=null)
						lines.add(line);
						return lines;
				}
					
				};
				
				Future<List<String>> future= executor.submit(callable);
				try
				{
					List<String> lines=future.get(5,TimeUnit.SECONDS);
					for(String line:lines)
					{
						System.out.print(line);
						
					}
				}catch(ExecutionException ee) {
					System.err.println(ee.getMessage());
				}
				catch(InterruptedException | TimeoutException eite) {
					System.err.print("URL not responding"+eite.getMessage());
				}
				
				executor.shutdown();
	      
		
	}

}
