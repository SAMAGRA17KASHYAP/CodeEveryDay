package com.looser.enterprise.applying.concurrency;

public class SimulateDeadlock {

	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	
	public static void method1 ()throws Exception
	{
		synchronized (lock1) {
			System.out.println("SimulateDeadlock.method1()::Lock1");
			Thread.sleep(10000);
			synchronized (lock2) {
				System.out.println("SimulateDeadlock.method1()::Lock2");
				
			}
		}
	}
	public static void method2()throws Exception
	{
		synchronized (lock2) {
			System.out.println("SimulateDeadlock.method1()::Lock2");
			Thread.sleep(10000);
			synchronized (lock1) {
				System.out.println("SimulateDeadlock.method1()::Lock1");
				
			}
		}
	}
	public static void main(String[] args) throws Exception {
		Thread t1  = new Thread(() -> {
			try {
				method1();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		Thread t2  = new Thread(() -> {
			try {
				method2();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}
}
