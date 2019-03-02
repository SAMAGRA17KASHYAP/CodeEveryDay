package com.looser.enterprise.applying.advanced.concurrency.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class FirstCompletableFutureWithSupplier {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(1);
		Supplier<String> supplier = () -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "Thread Name " + Thread.currentThread().getName();
		};
		CompletableFuture<String> supplyAsync = CompletableFuture
				.supplyAsync(supplier, service);
		String result = supplyAsync.join();

		System.out.println(result);
		supplyAsync.obtrudeValue("This has to change");
		//supplyAsync.complete("This has to change");
		result = supplyAsync.join();

		System.out.println(result);
		service.shutdown();
	}
}
