package com.looser.enterprise.applying.advanced.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PlayingWithCallableAndFutures {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Callable<String> callable = () -> {
			Thread.sleep(1000);
			return "Executing in " + Thread.currentThread().getName();
		};

		ExecutorService service = Executors.newFixedThreadPool(4);

		List<Future<String>> futureResult = new ArrayList<>();
		try {
			for (int i = 0; i < 10; i++) {
				futureResult.add(service.submit(callable));
			}

			for (Future<String> future : futureResult) {
				System.out.println(future.get());
			}
		} finally {
			service.shutdown();
		}
	}
}
