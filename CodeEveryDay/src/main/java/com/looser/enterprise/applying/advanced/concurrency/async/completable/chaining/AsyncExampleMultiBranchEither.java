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

import com.looser.enterprise.applying.advanced.concurrency.async.completable.chaining.model.Email;
import com.looser.enterprise.applying.advanced.concurrency.async.completable.chaining.model.User;

public class AsyncExampleMultiBranchEither {
	public static void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] argv) {
		ExecutorService service = Executors.newFixedThreadPool(1);
		ExecutorService service2 = Executors.newFixedThreadPool(1);
		Supplier<List<Long>> supplyIDs = () -> {
			System.out.println(
					"supplyID:Running in :" + Thread.currentThread().getName());
			sleep(500);
			return Arrays.asList(1L, 2L, 3L);
		};

		Function<List<Long>, CompletableFuture<List<User>>> fetchUsers = ids -> {
			sleep(400);
			System.out.println("fetchUsers:Running in :"
					+ Thread.currentThread().getName());
			Supplier<List<User>> listOfUserSupplier = () -> {
				System.out.println("listOfUserSupplier:Running in :"
						+ Thread.currentThread().getName());

				return ids.stream().map(x -> new User(x))
						.collect(Collectors.toList());
			};
			// return
			// CompletableFuture.supplyAsync(listOfUserSupplier,service2);
			return CompletableFuture.supplyAsync(listOfUserSupplier);
		};

		Function<List<Long>, CompletableFuture<List<User>>> fetchUsers2 = ids -> {
			sleep(4000);
			System.out.println("fetchUsers2:Running in :"
					+ Thread.currentThread().getName());
			Supplier<List<User>> listOfUserSupplier = () -> {
				System.out.println("listOfUserSupplier2:Running in :"
						+ Thread.currentThread().getName());

				return ids.stream().map(x -> new User(x))
						.collect(Collectors.toList());
			};
			// return
			// CompletableFuture.supplyAsync(listOfUserSupplier,service2);
			return CompletableFuture.supplyAsync(listOfUserSupplier);
		};

		CompletableFuture<List<Long>> getIDs = CompletableFuture
				.supplyAsync(supplyIDs);
		/**
		 * thenCompose vs thenComposeAsync
		 * fetchUsers and fetchUsers2 task are submitted in non async
		 * fashion so despite fetchUsers2 takes 4000ms in some cases
		 * fetchUsers2 will complete first
		 * 
		 * */
//		CompletableFuture<List<User>> usersCF = getIDs.thenCompose(fetchUsers);
//		CompletableFuture<List<User>> usersCF2 = getIDs.thenCompose(fetchUsers2);
		CompletableFuture<List<User>> usersCF = getIDs.thenComposeAsync(fetchUsers);
		CompletableFuture<List<User>> usersCF2 = getIDs.thenComposeAsync(fetchUsers2);
		
		usersCF.thenRun(()->System.out.println("user 1 completed"));
		usersCF2.thenRun(()->System.out.println("user 2 completed"));
		usersCF.acceptEither(usersCF2, (user)->System.out.println(user ));
		sleep(10_000);
		service.shutdown();
		service2.shutdown();
	}

}
