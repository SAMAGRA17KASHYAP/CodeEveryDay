package com.looser.enterprise.applying.concurrency;

public class ProducerConsumer {
	private static int count = 0;
	private static int[] buffer = new int[100];

	public static Object lock = new Object();

	static class Producer {
		public void produce() throws InterruptedException {
			synchronized (lock) {
				if (isFull())
					lock.wait();
				buffer[count] = 1;
				count++;
				lock.notifyAll();
			}
		}

	}

	private static boolean isFull() {
		return buffer.length == count;
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

	private static boolean isEmpty() {
		return 0 == count;
	}

	public static void main(String[] args) throws Exception {
		Producer producer = new Producer();
		Consumer consumer = new Consumer();
		Runnable producerTask = () -> {
			for (int i = 0; i < 100; i++) {
				try {
					producer.produce();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			System.out.println("Done producer...");
		};
		Runnable consumerTask = () -> {
			for (int i = 0; i < 100; i++) {
				try {
					consumer.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Done consuming...");
		};
		Thread t1 = new Thread(producerTask);
		Thread t2 = new Thread(consumerTask);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println("Count::"+count);
	}

}
