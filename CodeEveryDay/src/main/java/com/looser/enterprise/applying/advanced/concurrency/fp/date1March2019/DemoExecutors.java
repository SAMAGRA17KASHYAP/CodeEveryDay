package com.looser.enterprise.applying.advanced.concurrency.fp.date1March2019;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoExecutors {
	public static void main(String[] args) {
		runAsyncSingleThread();
		System.out.println("-------><-----------");
		runAsyncFixedThread();
	}
	
	public static void runAsyncSingleThread()
	{
		Runnable runnable = ()->System.out.println("Runnable::"+Thread.currentThread().getName());
		ExecutorService service = Executors.newSingleThreadExecutor();
		for(int i=0;i<10;i++)
		{
			service.execute(runnable);
		}
		service.shutdown();
	}
	public static void runAsyncFixedThread()
	{
		Runnable runnable = ()->System.out.println("Runnable::"+Thread.currentThread().getName());
		ExecutorService service = Executors.newFixedThreadPool(10);
		for(int i=0;i<10;i++)
		{
			service.execute(runnable);
		}
		service.shutdown();
	}
}
