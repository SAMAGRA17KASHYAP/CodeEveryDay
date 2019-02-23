package com.looser.enterprise.applying.concurrency;

import java.util.stream.Stream;

public class RaceCondition {
	public static void main(String[] args) throws Exception {
		LongWrapper longWrapper = new LongWrapper();
		Runnable runnable = () -> {
			for (int i = 0; i < 10000; i++)
				longWrapper.increment();
		};

		Thread[] t = new Thread[10000];
		for (int i = 0; i < 10000; i++) {
			t[i] = new Thread(runnable);
			t[i].start();
		}

		Stream.iterate(0, n -> n + 1).limit(10000).forEach((x) -> {
			try {
				t[x].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println("RaceCondition.main():::" + longWrapper.getValue());
	}
}
