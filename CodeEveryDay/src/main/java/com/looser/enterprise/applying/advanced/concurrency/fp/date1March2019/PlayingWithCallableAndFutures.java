package com.looser.enterprise.applying.advanced.concurrency.fp.date1March2019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayingWithCallableAndFutures {
	public static void main(String[] args) {

		doThroughCallable();

	}

	public static void doThroughCallable() {
		ExecutorService service = Executors.newFixedThreadPool(4);
		try {
			Callable<String> callable = () -> "Callable "
					+ Thread.currentThread().getName();
			List<Future<String>> futureList = Stream.iterate(0, n -> n + 1)
					.limit(10).map(x -> service.submit(callable))
					.collect(Collectors.toList());
			futureList.forEach(x -> {
				try {
					System.out.println(x.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			});

		} finally {
			service.shutdown();
		}
	}
}
