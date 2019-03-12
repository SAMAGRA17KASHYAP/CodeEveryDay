package com.looser.enterprise.applying.advanced.concurrency.fp.march12_2019;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoExecutors {
	public static void runningFixedSizeExecutors() {
		ExecutorService service = null;
		try {
			service = Executors.newFixedThreadPool(3);
			Runnable runnable = () -> {
				System.out.println("Executing from ::"+Thread.currentThread().getName());
			};	
			
			for (int i = 0; i < 10; i++) {
				service.execute(runnable);
			}
			
		} finally {
			service.shutdown();
		}
	}
	
	public static void runningRunnable() {
		Runnable runnable = ()->System.out.println("Executing from::"+ Thread.currentThread().getName());
		for (int i = 0; i < 10; i++) {
			new Thread(runnable).start();
		}
	}
	
	public static void runningSingleThreadPool() {
		Runnable runnable = ()->System.out.println("Executing from::"+ Thread.currentThread().getName());
		ExecutorService service = null;
		try {
			service = Executors.newSingleThreadExecutor();
			for (int i = 0; i < 10; i++) {
				service.submit(runnable);
			}
		} finally {
			service.shutdown();
		}
		
	}
	
	public static void main(String[] args) {
		runningFixedSizeExecutors();
		System.out.println("==========================");
		runningRunnable();
		System.out.println("==========================");
		runningSingleThreadPool();
		System.out.println("==========================");
	}
}
