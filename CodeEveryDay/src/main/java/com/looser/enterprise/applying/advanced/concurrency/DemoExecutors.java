package com.looser.enterprise.applying.advanced.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoExecutors {
	public static void main(String[] args) {
		runningNewThreadEveryTime();
		System.out.println("=======================================");
		runningTaskAsyncUsingExecutor();
	}

	private static void runningTaskAsyncUsingExecutor() {
		Runnable runnable = ()-> {
			System.out.println("From runnable:"+Thread.currentThread().getName());
		};
		ExecutorService service = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 10; i++) {
			service.execute(runnable);
		}
		service.shutdown();
	}

	private static void runningNewThreadEveryTime() {
		Runnable runnable = ()-> {
			System.out.println("From runnable:"+Thread.currentThread().getName());
		};
		for (int i = 0; i < 10; i++) {
			new Thread(runnable).start();
		}
	}
}
