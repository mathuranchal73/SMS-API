package com.sms;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class csvRead {

	public static void main(String args[]) 
	{
		CSVReader reader= null;
		
		try {
			reader= new CSVReader(new FileReader("Sample.csv"),',','"',1);
			 CSVWriter writer = new CSVWriter(new FileWriter("demo.csv"));
			String[] nextLine;
			
			while((nextLine=reader.readNext())!=null)
			{
				if(nextLine !=null)
				{
					System.out.println(Arrays.toString(nextLine));
					String [] record = "4,David,Miller,Australia,30".split(",");
					writer.writeNext(Arrays.toString(nextLine).split(","));
					writer.close();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
