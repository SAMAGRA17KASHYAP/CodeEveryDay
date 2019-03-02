package com.looser.enterprise.applying.advanced.concurrency.concurrentmap.model;

import java.util.Objects;

public class Actor {
	
	private String firstName,lastName;

	public Actor(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 67 *hash + Objects.hashCode(firstName);
		hash = 67 *hash + Objects.hashCode(lastName);
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj.getClass() != this.getClass())
			return false;
		Actor act =(Actor)obj;
		if(!Objects.equals(firstName,act.getFirstName() ))
			return false;
		return Objects.equals(lastName,act.getLastName() );
	}
	
	@Override
	public String toString() {
		return "Actor[ fristName: "+firstName+" ,Lastname:"+lastName+" ]";
	}
}
