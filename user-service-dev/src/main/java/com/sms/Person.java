package com.sms;

import java.util.Comparator;

public class Person implements Comparator<Person>
{
	private String gender;
	private String nationality;
	private double income;
	private String currency;
	
	
	

	
	public Person(String gender, String nationality, double income, String currency) {
		super();
		this.gender = gender;
		this.nationality = nationality;
		this.income = income;
		this.currency = currency;
	}

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Override
	public String toString() {
		return "Person [gender=" + gender + ", nationality=" + nationality + ", income=" + income + ", currency="
				+ currency + "]";
	}

	/**@Override
	public int compareTo(Person person) {
		
		return nationality.compareTo(person.nationality);
	}**/

	@Override
	public int compare(Person arg0, Person arg1) {
		if(arg0.income>arg1.income)
			return 1;
		else if(arg0.income==arg1.income)
			return 0;
		else
			return -1;
	}
	
}
