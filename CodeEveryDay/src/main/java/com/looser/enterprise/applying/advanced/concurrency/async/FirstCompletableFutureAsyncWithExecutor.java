package com.looser.enterprise.applying.advanced.concurrency.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstCompletableFutureAsyncWithExecutor {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		CompletableFuture.runAsync(()->System.out.println("I am running asynchronously "+Thread.currentThread().getName()),service);
		service.shutdown();
	}
}
