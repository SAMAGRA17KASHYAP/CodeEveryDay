package com.looser.enterprise.applying.advanced.concurrency.async.completable.chaining.model;

public class Email {

	long id;

	public Email() {
		super();
	}

	public Email(long id) {
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
		return "Email [id=" + id + "]";
	}
	
	
}
