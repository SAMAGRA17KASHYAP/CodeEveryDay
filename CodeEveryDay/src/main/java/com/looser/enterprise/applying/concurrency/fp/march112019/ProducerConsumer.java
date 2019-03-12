package com.looser.enterprise.applying.concurrency.fp.march112019;

public class ProducerConsumer {

	private static final int CAPACITY = 7000;
	private static int[] buffer = new int[CAPACITY];
	private volatile static int count = 0;
	private static Object lock = new Object();
	static class Producer {
		public void produce() throws InterruptedException {
			synchronized (lock) {
				if (isFull())
					lock.wait();
				buffer[count++] = 1;
				lock.notifyAll();
			}
		}
	}
	static class Consumer {
		public void consume() throws InterruptedException {
			synchronized (lock) {

				if (isEmpty())
					lock.wait();
				buffer[--count] = 0;
				lock.notifyAll();
			}
		}
	}

	public static boolean isEmpty() {
		return count == 0;
	}
	public static boolean isFull() {
		return count == buffer.length;
	}

	public static void main(String[] args) throws InterruptedException {
		Producer producer = new Producer();
		Consumer consumer = new Consumer();
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1_000; i++) {
				try {
					producer.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1_000; i++) {
				try {
					consumer.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t2.start();
		t1.start();
		t1.join();
		t2.join();
		System.out.println(count);
	}
}
