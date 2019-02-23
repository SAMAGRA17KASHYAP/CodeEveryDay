package com.looser.enterprise.applying.concurrency;

public class FirstRunnable {

	public static void main(String[] args) {
		Runnable runnable = ()->{
			System.out.println("I am runnning in :"+Thread.currentThread().getName());
		};
		
		Thread t = new Thread(runnable);
		t.setName("Samagra Thread");
		t.start();
	}
	
}
