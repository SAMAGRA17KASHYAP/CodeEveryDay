package com.looser.enterprise.understanding.generics.model;

public class Partner extends Person{

	private final String logo;

	public Partner(String name, int age, String logo) {
		super(name, age);
		this.logo = logo;
	}

	public String getLogo() {
		return logo;
	}

	@Override
	public String toString() {
		return "Partner [logo=" + logo + "]";
	}
	
	
}
