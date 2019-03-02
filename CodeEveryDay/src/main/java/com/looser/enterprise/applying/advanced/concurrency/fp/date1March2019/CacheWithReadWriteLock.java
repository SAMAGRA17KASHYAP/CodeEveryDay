package com.looser.enterprise.applying.advanced.concurrency.fp.date1March2019;

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
	private  final Map<Integer,String> map = new HashMap<>();

	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock readLock = lock.readLock(); 
	private Lock writeLock = lock.writeLock(); 
	
	private void put(Integer key, String value)
	{
		try {
			writeLock.lock();
			map.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}
	private String get(Integer key)
	{
		try {
			readLock.lock();
			return map.get(key);
		} finally {
			readLock.unlock();
		}
	}
	public static void main(String[] args) {
		CacheWithReadWriteLock cacheWithReadWriteLock = new CacheWithReadWriteLock(); 
		class Producer implements Callable<String>
		{
			Random rand = new Random();

			@Override
			public String call() throws Exception {
				while(true)
				{
					Integer i = rand.nextInt(4545);
					cacheWithReadWriteLock.put(i, Long.toString(i));
					if(cacheWithReadWriteLock.get(i)==null)
						System.out.println("Not found key");
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
