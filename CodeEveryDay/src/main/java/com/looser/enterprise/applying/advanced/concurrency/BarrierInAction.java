package com.looser.enterprise.applying.advanced.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BarrierInAction {
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(4);
		List<Friend> friends = new ArrayList<>();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(4,()->System.out.println("Latch temporaily opened"));
		friends.add(new Friend(cyclicBarrier));
		friends.add(new Friend(cyclicBarrier));
		friends.add(new Friend(cyclicBarrier));
		friends.add(new Friend(cyclicBarrier));
		service.invokeAll(friends);
		service.shutdown();

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
				Thread.sleep(rand.nextInt(1_0) *100);
				System.out.println("Reached cafe...");
				cyclicBarrier.await();
				System.out.println("Let us go to cinema...");
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return "ok";
		}
		
	}
}
