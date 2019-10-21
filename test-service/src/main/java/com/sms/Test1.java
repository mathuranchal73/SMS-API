package com.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test1{
	
	public static void main(String args[])throws IOException {
		
		List<Student> list= new ArrayList<Student>();
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		for(int i=0;i<=2;i++)
		{
			Student student= new Student();
		System.out.println("Enter the Student"+i+"name");
		student.setName(br.readLine());

		System.out.println("Enter the Student"+i+"Roll No");
		student.setRollno(Integer.parseInt(br.readLine()));
		System.out.println("Enter the Student"+i+"Address");
		student.setAddress(br.readLine());
		
		list.add(student);
		}
		
	
		
		System.out.println("Sorted by Roll No");
		Collections.sort(list, new Sortbyroll());
		list.forEach(System.out::println);
		
		System.out.println("Sorted by Name");
		Collections.sort(list, new Sortbyname());
		list.forEach(System.out::println);
		
		System.out.println("Sorted by both Name and Roll No");
		Collections.sort(list, new Student.StudentSortingComparator());
		list.forEach(System.out::println);
		
		

		
		
	}

}
