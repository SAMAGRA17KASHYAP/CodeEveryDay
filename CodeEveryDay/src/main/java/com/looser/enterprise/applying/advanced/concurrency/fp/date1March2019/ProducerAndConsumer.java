package com.looser.enterprise.applying.advanced.concurrency.fp.date1March2019;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProducerAndConsumer {

	private static final int BUFFER_COUNT = 50;
	private static int[] buffer = new int[BUFFER_COUNT];
	private static int count = 0;
	private static Lock lock = new ReentrantLock();
	private static Condition emptyCondition = lock.newCondition();
	private static Condition fullConditon = lock.newCondition();

	static class Producer implements Callable<String> {
		@Override
		public String call() throws Exception {
			try {
				lock.lock();
				if (isFull())
					fullConditon.await();
				buffer[count++] = 1;
				emptyCondition.signal();

			} finally {
				lock.unlock();
			}
			return "ok...";
		}

	}
	static class Consumer implements Callable<String> {
		@Override
		public String call() throws Exception {
			try {
				lock.lock();
				if (isEmpty())
					emptyCondition.await();
				buffer[--count] = 0;
				fullConditon.signal();

			} finally {
				lock.unlock();
			}
			return "ok...";
		}

	}
	static boolean isFull() {
		return count == BUFFER_COUNT;
	}

	static boolean isEmpty() {
		return count == 0;
	}
	public static void main(String[] args) throws InterruptedException {
		List<Callable<String>> producer = Stream.iterate(0, n -> n + 1).limit(4).map(x -> new Producer())
				.collect(Collectors.toList());
		List<Callable<String>> consumer = Stream.iterate(0, n -> n + 1).limit(4).map(x -> new Consumer())
				.collect(Collectors.toList());
		ExecutorService service = Executors.newFixedThreadPool(8);
		List<Future<String>> result1 = service.invokeAll(producer);
		result1.addAll(service.invokeAll(consumer));
		result1.forEach(x->{
			try {
				System.out.println(x.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
		
		service.shutdown();
		System.out.println(count);
	}
}
