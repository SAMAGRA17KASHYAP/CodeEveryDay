package com.looser.enterprise.applying.advanced.concurrency.async.completable.chaining.exception.handling;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.looser.enterprise.applying.advanced.concurrency.async.completable.chaining.model.User;

public class ExceptionDemo {
	public static void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Supplier<List<Long>> getIDs = () -> {
			sleep(300);
			throw new IllegalArgumentException();
			// return Arrays.asList(1L,2L,3L);
		};

		Function<List<Long>, List<User>> fetchUsers = (ids) -> {
			sleep(400);
			return ids.stream().map(x -> new User(x))
					.collect(Collectors.toList());
		};

		Consumer<List<User>> displayer = (x) -> x.forEach(System.out::println);

		CompletableFuture<List<Long>> getIDsCF = CompletableFuture
				.supplyAsync(getIDs);
		CompletableFuture<List<Long>> exceptionally = getIDsCF.exceptionally(e->Arrays.asList());
		CompletableFuture<List<User>> fetchUsersCF = exceptionally
				.thenApply(fetchUsers);
		CompletableFuture<Void> displayerCF = fetchUsersCF
				.thenAccept(displayer);

		try {
			getIDsCF.join();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} ;
		sleep(1000);
		System.out.println("GetID::" + getIDsCF.isDone() + "::"
				+ getIDsCF.isCompletedExceptionally());
		System.out.println("fetch Usets::" + fetchUsersCF.isDone() + "::"
				+ fetchUsersCF.isCompletedExceptionally());
		System.out.println("displayerCF::" + displayerCF.isDone() + "::"
				+ displayerCF.isCompletedExceptionally());

	}
}
