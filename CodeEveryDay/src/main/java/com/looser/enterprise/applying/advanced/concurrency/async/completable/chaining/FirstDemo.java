package com.looser.enterprise.applying.advanced.concurrency.async.completable.chaining;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.looser.enterprise.applying.advanced.concurrency.async.completable.chaining.model.User;

public class FirstDemo {

	public static void sleep(int n)
	{
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] argv)
	{
		ExecutorService service =Executors.newFixedThreadPool(1);
		Supplier<List<Long>>  supplyIDs = ()->{
			System.out.println("supplyID:Running in :"+Thread.currentThread().getName());
			sleep(500);
			return Arrays.asList(1L,2L,3L);
		};
		
		Function<List<Long>,CompletableFuture<List<User>>> fetchUsers = ids->{
			sleep(800);
			System.out.println("fetchUsers:Running in :"+Thread.currentThread().getName());
			Supplier<List<User>> listOfUserSupplier =()->ids.stream().map(x->new User(x)).collect(Collectors.toList());
			return CompletableFuture.supplyAsync(listOfUserSupplier);
		};
		
		Consumer<List<User>> displayer = users -> users.forEach(x->{
			System.out.println("Running in ::"+Thread.currentThread().getName());
			System.out.println(x);});
		
		CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(supplyIDs)
							.thenCompose(fetchUsers)
							.thenAcceptAsync(displayer,service);
		
		sleep(2_000);
		service.shutdown();
	}
	
}
