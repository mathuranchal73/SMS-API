package com.sms;

import java.util.Comparator;

public class Student  {
	
	int rollno; 
    String name;
    String address;
    
	public int getRollno() {
		return rollno;
	}
	public void setRollno(int rollno) {
		this.rollno = rollno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Student [rollno=" + rollno + ", name=" + name + ", address=" + address + "]";
	} 
	
	static class StudentSortingComparator implements Comparator<Student> {
		
		@Override
		public int compare(Student s1,Student s2) {
			//1st Level Comparison
			int NameCompare=s1.getName().compareTo(s2.getName());
			int rollCompare=s1.getRollno()-s2.getRollno();
			
			if(NameCompare==0) {
				return((rollCompare==0)?NameCompare:rollCompare);
			}
			else
				return NameCompare;
			
		}
		
	}

	
	
    
    
}
