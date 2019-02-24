package com.looser.enterprise.applying.advanced.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerWithLocks {
	private static final int BUFFER_SIZE = 100;
	private static int[] buffer = new int[BUFFER_SIZE];
	private static int count = 0;
	private static Lock lock = new ReentrantLock();
	private static Condition isEmpty = lock.newCondition();
	private static Condition isFull = lock.newCondition();
	private static Future<String> consumer;
	private static Future<String> producer;

	static class Consumer implements Callable<String> {

		@Override
		public String call() throws Exception {
			int i = 0;
			while (i < 50) {
				try {
					lock.lock();
					if (isEmpty())
						isEmpty.await();
					buffer[--count] = 0;
					isFull.signalAll();
				} finally {
					lock.unlock();
				}
				i++;
			}
			return "Completed Consuming..." +i;
		}

	}

	static class Producer implements Callable<String> {

		@Override
		public String call() throws Exception {
			int i = 0;
			while (i < 50) {
				try {
					lock.lock();
					if (isFull()) {
						isFull.await();
					}
					buffer[count++] = 1;
					isEmpty.signalAll();
				} finally {
					lock.unlock();
				}
				i++;
			}
			return "Completed production ..."+i;
		}

	}

	private static boolean isFull() {
		return count == buffer.length;
	}

	private static boolean isEmpty() {
		return count == 0;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		List<Callable<String>> producersAndConsumers  = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			producersAndConsumers.add(new Consumer());
		}
		for (int i = 0; i < 4; i++) {
			producersAndConsumers.add(new Producer());
		}
		ExecutorService service = null;
		try {
			// Try changing no of thread to 4
			// Nothing will progress as 4 consumers will be stuck and no producer thread os there
			service = Executors.newFixedThreadPool(8);
			List<Future<String>> futureResult = service.invokeAll(producersAndConsumers);
		
			futureResult.forEach(x->{
				try {
					System.out.println(x.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			});
		} finally {
			service.shutdown();
		}
		
		

		System.out.println("Count::" + count);
	}
}
