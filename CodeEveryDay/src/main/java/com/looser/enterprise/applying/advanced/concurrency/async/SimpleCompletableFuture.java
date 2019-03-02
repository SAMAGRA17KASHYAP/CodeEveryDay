package com.looser.enterprise.applying.advanced.concurrency.async;

import java.util.concurrent.CompletableFuture;

public class SimpleCompletableFuture {

	public static void main(String[] args) {
		
		CompletableFuture<Void> cf = new CompletableFuture<>();
		Runnable task = ()->{
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
			}
			cf.complete(null);
		};
		CompletableFuture.runAsync(task);
		Void nil = cf.join();
		System.out.println("This is chaos");
		
	}
	
}
