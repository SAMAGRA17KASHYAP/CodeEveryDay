package com.looser.enterprise.applying.advanced.concurrency.async;

import java.util.concurrent.CompletableFuture;

public class FirstCompletableFutureAsync {
	public static void main(String[] args) throws InterruptedException {
		CompletableFuture.runAsync(()->System.out.println("I am running asynchronously "+Thread.currentThread().getName()));
		
		/**
		 * By Default task submitted to CompletableFuture run in Daemon Thread
		 * -ForkJoinPool.commonPool.
		 * Thread belongs to this pool are Daemon and will exit
		 * That is why we need Thread.sleep method
		 * */
		Thread.sleep(1000);
	}
}
