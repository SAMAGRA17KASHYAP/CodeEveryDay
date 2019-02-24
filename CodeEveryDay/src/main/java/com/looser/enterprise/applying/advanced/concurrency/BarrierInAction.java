package com.looser.enterprise.applying.advanced.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BarrierInAction {
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(2);//Note now barrier will never open as thread count is less
		List<Friend> friends = new ArrayList<>();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(4,()->System.out.println("Latch temporaily opened"));
		friends.add(new Friend(cyclicBarrier));
		friends.add(new Friend(cyclicBarrier));
		friends.add(new Friend(cyclicBarrier));
		friends.add(new Friend(cyclicBarrier));
		List<Future<String>> result = service.invokeAll(friends);
		result.forEach(x->{
			try {
				x.get();
			} catch (Exception e) {
				System.out.println(e);;
			}
		});
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
				cyclicBarrier.await(10,TimeUnit.SECONDS);
					
				System.out.println("Let us go to cinema...");
			}
			catch (Exception e) {
				System.out.println(e);
			}
			return "ok";
		}
		
	}
}
