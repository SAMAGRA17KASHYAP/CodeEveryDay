package com.looser.enterprise.applying.advanced.concurrency;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BarrierInAction {
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(2);//Note now barrier will never open as thread count is less
		try {
			List<Friend> friends = new ArrayList<>();
			CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> System.out.println("Latch temporaily opened"));
			friends.add(new Friend(cyclicBarrier));
			friends.add(new Friend(cyclicBarrier));
			friends.add(new Friend(cyclicBarrier));
			friends.add(new Friend(cyclicBarrier));
			List<Future<String>> result = new ArrayList<>();
			for (Friend friend : friends) {
				result.add(service.submit(friend));
			}
			result.forEach(x -> {
				try {
					System.out.println(x.get(1, TimeUnit.MICROSECONDS));
				} catch (TimeoutException e) {
					System.out.println("Timeout...");
					x.cancel(true);
				} catch (Exception e) {
					System.out.println(e);
					;
				}
			});
		} finally {
			service.shutdown();
		}

	}
	static class Friend implements Callable<String>
	{
		private CyclicBarrier cyclicBarrier;

		
		public Friend(CyclicBarrier cyclicBarrier) {
			super();
			this.cyclicBarrier = cyclicBarrier;
		}



		@Override
		public String call() throws Exception 
		{
			try
			{
				Random rand = new Random();
				Thread.sleep(rand.nextInt(10) *10);
				System.out.println("Reached cafe...");
				cyclicBarrier.await();
					
				System.out.println("Let us go to cinema...");
			}
			catch (Exception e) {
				System.out.println(e);
			}
			return "ok";
		}
		
	}
}
