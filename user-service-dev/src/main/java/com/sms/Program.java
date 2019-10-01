package com.sms;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;


public class Program {
	
	public static void main(String args[])throws Exception
	{
		
		CsvToBean csv= new CsvToBean();
		String csvFileName="demo.csv";
		CSVReader csvReader= new CSVReader(new FileReader(csvFileName),',','"',1);
		int SG_male=0;
		int US_male=0;
		int IND_male=0;
		int SG_female=0;
		int US_female=0;
		int IND_female=0;
		double SG_male_income=0.0;
		double US_male_income=0.0;
		double IND_male_income=0.0;
		double SG_female_income=0.0;
		double US_female_income=0.0;
		double IND_female_income=0.0;
		
		double SG_male_income_avg=0.0;
		double IND_male_income_avg=0.0;
		double US_male_income_avg=0.0;
		
		double SG_female_income_avg=0.0;
		double IND_female_income_avg=0.0;
		double US_female_income_avg=0.0;
		
		double SG_conversion_rate=0.72;
		double IND_conversion_rate=0.014;
		
		
		List list=csv.parse(setColumnMapping(),csvReader);
		List<Person> person1_list= new ArrayList<>();
		for(Object object:list)
		{
			Person person= (Person)object;
			person1_list.add(person);
		}
		
		
		for(Person person:person1_list)
		{
			
			
			if(person.getNationality().startsWith("Singapore"))
			{
				if(person.getGender().startsWith("Male"))
				{
					
					++SG_male;
					SG_male_income=SG_male_income+person.getIncome();
					
				}
				
				else
				{
					++SG_female;
					SG_female_income=SG_female_income+person.getIncome();
					
				
				}
			}
			else if(person.getNationality().startsWith("US"))
			{
				if(person.getGender().startsWith("Male"))
				{
					++US_male;
					US_male_income=US_male_income+person.getIncome();
					
				}
				
				else
				{
					++US_female;
					US_female_income=US_female_income+person.getIncome();
					
				}
			}
			else if(person.getNationality().startsWith("Indian"))
			{
				if(person.getGender().startsWith("Male"))
				{
					++IND_male;
					IND_male_income=IND_male_income+person.getIncome();
					
				}
				
				else
					{
						++IND_female;
						IND_female_income=IND_female_income+person.getIncome();
						
						
						
					}
				
			}
			
			SG_male_income_avg=(SG_male_income/SG_male)*SG_conversion_rate;
			SG_female_income_avg=(SG_female_income/SG_female)*SG_conversion_rate;
			US_male_income_avg=(US_male_income/US_male);
			US_female_income_avg=(US_female_income/US_female);
			IND_male_income_avg=(IND_male_income/IND_male)*IND_conversion_rate;
			IND_female_income_avg=(IND_female_income/IND_female)*IND_conversion_rate;
		}
		
	
		List<Person> person_list= new ArrayList<>();
		
		person_list.add(new Person("Male","Singapore",SG_male_income_avg,"USD"));
		person_list.add(new Person("Female","Singapore",SG_female_income_avg,"USD"));
		person_list.add(new Person("Male","Indian",IND_male_income_avg,"USD"));
		person_list.add(new Person("Female","Indian",IND_female_income_avg,"USD"));
		person_list.add(new Person("Male","US",US_male_income_avg,"USD"));
		person_list.add(new Person("Female","US",US_female_income_avg,"USD"));
		//Collections.reverse(person_list);
		//Collections.sort(person_list, new Person());
		person_list.sort(Comparator.comparing(Person::getGender).thenComparing(Person::getIncome).reversed());
				
		person_list.forEach(person->{
			System.out.println(person);
		});
		
		person_list.forEach(System.out::println);
		
		 FileWriter writer = new 
                 FileWriter("demo1.csv"); 
		 StatefulBeanToCsvBuilder<Person> builder= 
                 new StatefulBeanToCsvBuilder(writer); 
     StatefulBeanToCsv beanWriter =  
   builder.withMappingStrategy(setColumnMapping()).build(); 

     // Write list to StatefulBeanToCsv object 
     beanWriter.write(person_list); 

     // closing the writer object 
     writer.close();
		 
		System.out.println(person_list);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ColumnPositionMappingStrategy setColumnMapping()
	{
		ColumnPositionMappingStrategy strategy= new ColumnPositionMappingStrategy();
		strategy.setType(Person.class);
		String[] columns= new String[] {"gender","nationality","income","currency"};
		strategy.setColumnMapping(columns);
		return strategy;
	}


}



	
