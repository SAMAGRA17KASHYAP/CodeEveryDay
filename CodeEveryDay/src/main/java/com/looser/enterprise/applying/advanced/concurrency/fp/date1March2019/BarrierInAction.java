package com.looser.enterprise.applying.advanced.concurrency.fp.date1March2019;

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
		ExecutorService service =  Executors.newFixedThreadPool(4);
		try {
			List<Friend> friendList = new ArrayList<>();
			CyclicBarrier barrier = new CyclicBarrier(4,()->System.out.println("Everyone Reached"));
			friendList.add(new Friend(barrier));
			friendList.add(new Friend(barrier));
			friendList.add(new Friend(barrier));
			friendList.add(new Friend(barrier));
			
			
			List<Future<String>> futureList = new ArrayList<>();
			for (Friend friend : friendList) {
				futureList.add(service.submit(friend));
			}
			futureList.forEach(x->{
				try {
					System.out.println(x.get(1, TimeUnit.MICROSECONDS));
				} catch (TimeoutException e) {
					System.out.println("eee"+e.getMessage());
					x.cancel(true);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} finally {
			service.shutdown();
		}
		
	}
	
	static class Friend implements Callable<String>
	{
		CyclicBarrier barrier;

		public Friend(CyclicBarrier barrier) {
			super();
			this.barrier = barrier;
		}
		
		public String call() throws Exception
		{
			try {
				System.out.println("Left from home"+ Thread.currentThread().getName());
				Random rand = new Random();
				Thread.sleep(10000000+rand.nextInt(1000));
				barrier.await();
				System.out.println("Let's go to movie "+Thread.currentThread().getName());
				
			}catch(Exception e)
			{
				System.out.println(e);
			}
			
			return "ok";
		}
		
		
	}
}
