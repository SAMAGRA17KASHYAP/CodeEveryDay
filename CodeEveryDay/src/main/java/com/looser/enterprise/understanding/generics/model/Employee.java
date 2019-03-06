package com.looser.enterprise.understanding.generics.model;

public class Employee extends Person {

	private final String employeeID;

	public Employee(String name, int age, String employeeID) {
		super(name, age);
		this.employeeID = employeeID;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + "]";
	}
	
	
}
