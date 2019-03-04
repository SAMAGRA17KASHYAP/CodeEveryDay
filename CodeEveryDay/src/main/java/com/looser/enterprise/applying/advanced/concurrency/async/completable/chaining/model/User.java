package com.looser.enterprise.applying.advanced.concurrency.async.completable.chaining.model;

public class User {

	long id;

	public User() {
		super();
	}

	public User(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}
	
	
}
