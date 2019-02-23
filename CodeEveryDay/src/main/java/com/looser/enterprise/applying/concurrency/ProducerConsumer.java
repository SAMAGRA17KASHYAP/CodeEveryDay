package com.looser.enterprise.applying.concurrency;

public class ProducerConsumer {
	int count = 0;
	int[] buffer = new int[100];

	public Object lock = new Object();

	class Producer {
		public void produce() {
			synchronized (lock) {
				while (isFull())
					;
				buffer[count] = 1;
				count++;
			}
		}

		private boolean isFull() {
			return buffer.length == count;
		}
	}

	class Consumer {
		public void consume() {
			synchronized (lock) {
				while (isEmpty())
					;
				buffer[--count] = 0;
			}
		}

		private boolean isEmpty() {
			return 0 == count;
		}
	}
}
