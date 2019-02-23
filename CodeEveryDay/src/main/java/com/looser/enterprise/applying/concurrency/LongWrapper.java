package com.looser.enterprise.applying.concurrency;

public class LongWrapper {
	
	 private long l =0;
	
	 
	 
	public synchronized void increment()
	{
		l = l+1;
	}
	
	public Long getValue()
	{
		return l;
	}
}
