package com.looser.enterprise.applying.advanced.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProducerConsumerUsingQueue {
	
	
	public static void main(String[] args) throws InterruptedException {
		 BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(50);
		
		class Consumer implements Callable<String>
		{

			@Override
			public String call() throws Exception {
				int count =0;
				while(count++ < 50)
				{
					queue.take();
				}
				return "Consumed "+ (count-1);
			}
			
		}
		
		class Producer implements Callable<String>
		{

			@Override
			public String call() throws Exception {
				int count =0;
				while(count++ < 50)
				{
					queue.put(count);
				}
				return "produced "+ (count-1);
			}
			
		}
		
		List<Callable<String>> producersAndConsumers = new ArrayList<>();
		
		producersAndConsumers = Stream.iterate(0, n->n+1)
								.limit(2)
								.map(x-> new Producer())
								.collect(Collectors.toList());
		producersAndConsumers.addAll(Stream.iterate(0, n->n+1)
		.limit(2)
		.map(x-> new Consumer())
		.collect(Collectors.toList()));
		
		ExecutorService service = Executors.newFixedThreadPool(4);
		try {
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
		
	}
	
}
