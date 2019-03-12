package com.looser.enterprise.applying.concurrency.fp.march112019;

import java.util.concurrent.atomic.AtomicLong;

public class RaceCondition {

	public static LongWrapper lw = new LongWrapper();
	
	public  static void task() {
		for (int i = 0; i < 100000; i++) {
			lw.increment();
		}
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
		Thread[] t = new Thread[3];
		for (int i = 0; i < t.length; i++) {
			t[i] =	new Thread(RaceCondition::task);
		}
		for (int i = 0; i < t.length; i++) {
			t[i].start();
		}
		for (int i = 0; i < t.length; i++) {
			t[i].join();
		}
		System.out.println(lw.getValue());
	}
	
}
class LongWrapper {
	AtomicLong l = new AtomicLong()
			
			;

	public void increment() {
		l.incrementAndGet();
	}
	
	public Long getValue() {
		return l.get();
	}
}