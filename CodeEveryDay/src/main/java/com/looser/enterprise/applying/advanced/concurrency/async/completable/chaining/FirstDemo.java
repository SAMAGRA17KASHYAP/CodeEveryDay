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

public class FirstDemo {

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

		Function<List<Long>, CompletableFuture<List<Email>>> fetchEmail = ids -> {
			sleep(500);
			Supplier<List<Email>> listOfEmail = () -> {
				return ids.stream().map(x -> new Email(x))
						.collect(Collectors.toList());
			};
			return CompletableFuture.supplyAsync(listOfEmail);
		};

		Consumer<List<User>> displayer = users -> users.forEach(x -> {
			System.out.println(
					"Running in ::" + Thread.currentThread().getName());
			System.out.println(x);
		});
		CompletableFuture<List<Long>> getIDs = CompletableFuture
				.supplyAsync(supplyIDs);
		CompletableFuture<List<User>> usersCF = getIDs.thenCompose(fetchUsers);
		CompletableFuture<List<Email>> emailCF = getIDs.thenCompose(fetchEmail);
		
		usersCF.thenAcceptBothAsync(emailCF, (user,email)->System.out.println(user + " - "+email));
		sleep(2_000);
		service.shutdown();
		service2.shutdown();
	}

}
