package com.looser.enterprise.applying.advanced.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrintOddEven {

	static int i = 0;
	static Object lock = new Object();

	static class PrintEven implements Callable<String> {

		@Override
		public String call() throws Exception {
			synchronized (lock) {
				while( i < 100 ) {
					if (i % 2 == 1)
						lock.wait();
					System.out.println("From Even ::" + i++);
					lock.notifyAll();
				}
			}

			return "ok ...";
		}

	}

	static class PrintOdd implements Callable<String> {

		@Override
		public String call() throws Exception {
			synchronized (lock) {
				while (  i < 100 ) {
					if (i % 2 == 0) {
						lock.wait();
					}
					System.out.println("From Odd ::" + i++);
					lock.notifyAll();
				}
			}

			return "ok ...";
		}

	}
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(2);
		try
		{
			service.submit(new PrintOdd());
			service.submit(new PrintEven());
		}
		finally
		{
			service.shutdown();
		}
	}
}
