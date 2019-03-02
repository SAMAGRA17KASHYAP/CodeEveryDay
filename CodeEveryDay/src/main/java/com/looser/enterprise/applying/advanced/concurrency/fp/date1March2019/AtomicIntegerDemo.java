package com.looser.enterprise.applying.advanced.concurrency.fp.date1March2019;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {

	private static AtomicInteger atomic = new AtomicInteger();
	
	private static final int NO_OF_OPERATIONS = 50;
	
	static class Incrementor implements Callable<String>
	{

		@Override
		public String call() throws Exception {
			for (int i = 0; i < NO_OF_OPERATIONS; i++) {
				atomic.incrementAndGet();
			}
			return "";
		}
	
	}
	static class Decrementor implements Callable<String>
	{
		
		@Override
		public String call() throws Exception {
			for (int i = 0; i < NO_OF_OPERATIONS; i++) {
				atomic.decrementAndGet();
			}
			return "";
		}
		
	}
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(8);
		Future<String> submit1 = service.submit(new Incrementor());
		Future<String> submit2=service.submit(new Decrementor());
		Future<String> submit3=service.submit(new Incrementor());
		Future<String> submit4=service.submit(new Decrementor());
		submit1.get();
		submit2.get();
		submit3.get();
		submit4.get();
		System.out.println(atomic.get());
		service.shutdown();
	}
	
}
