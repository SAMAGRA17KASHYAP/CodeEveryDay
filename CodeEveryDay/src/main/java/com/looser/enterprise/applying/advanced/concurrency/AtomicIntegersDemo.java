package com.looser.enterprise.applying.advanced.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegersDemo {

	private static final int NO_OF_OPERATIONS = 1_000;
	
	private static AtomicInteger atomic = new AtomicInteger();
	
	static class Incrementer implements Callable<String>
	{

		@Override
		public String call() throws Exception {
			for (int i = 0; i < NO_OF_OPERATIONS; i++) {
				atomic.incrementAndGet();
			}
			
			return "ok...";
		}
		
	}
	static class Decrementer implements Callable<String>
	{
		
		@Override
		public String call() throws Exception {
			for (int i = 0; i < NO_OF_OPERATIONS; i++) {
				atomic.decrementAndGet();
			}
			
			return "ok...";
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(8);
		try {
			List<Callable<String>> tasks = new ArrayList<>();
			tasks.add(new Incrementer());
			tasks.add(new Incrementer());
			tasks.add(new Incrementer());
			tasks.add(new Incrementer());
			tasks.add(new Decrementer());
			tasks.add(new Decrementer());
			tasks.add(new Decrementer());
			tasks.add(new Decrementer());
			service.invokeAll(tasks);
		} finally {
			service.shutdown();
		}
		
		System.out.println(atomic.get());
		
		
	}
	
}
