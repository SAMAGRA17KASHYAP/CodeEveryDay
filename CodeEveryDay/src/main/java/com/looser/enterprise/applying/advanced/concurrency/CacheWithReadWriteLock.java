package com.looser.enterprise.applying.advanced.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheWithReadWriteLock {

	Map<Long,String> cache = new HashMap<>();
	
	ReadWriteLock readWriteLock  = new ReentrantReadWriteLock();	  
	Lock read = readWriteLock.readLock();
	Lock write = readWriteLock.writeLock();
	
	
	public void put(Long key, String value) {
		try {
			write.lock();
			cache.put(key, value);
		} finally {
			write.unlock();
		}
	}
	
	public String get(Long key)
	{
		try {
			read.lock();
			return cache.get(key);
		}
		finally {
			read.unlock();
		}
		
	}
	
	public static void main(String[] args) {
		CacheWithReadWriteLock  cache = new CacheWithReadWriteLock();
		class Producer implements Callable<String>
		{
			private Random rand = new Random();
			@Override
			public String call() throws Exception {
				while(true)
				{
					long key = rand.nextInt(1_000);
					cache.put(key, Long.toString(key));
					if(cache.get(key) == null)
						System.out.println("No key found for::"+key);
				}
			}
			
		}
		
		ExecutorService service = Executors.newFixedThreadPool(4);
		
		service.submit(new Producer());
		service.submit(new Producer());
		service.submit(new Producer());
		service.submit(new Producer());
		
		service.shutdown();
	}
}
