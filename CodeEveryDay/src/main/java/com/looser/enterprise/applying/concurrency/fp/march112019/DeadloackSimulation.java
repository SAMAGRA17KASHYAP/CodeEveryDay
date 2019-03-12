package com.looser.enterprise.applying.concurrency.fp.march112019;

public class DeadloackSimulation {

	private Object obj1 = new Object();
	private Object obj2 = new Object();

	private void task1()  {
		synchronized (obj1) {
			System.out
					.println("DeadloackSimulation.task1() acquired lock on obj1"
							+ Thread.currentThread().getName());
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (obj2) {
				System.out.println(
						"DeadloackSimulation.task1() acquired lock on obj2"
								+ Thread.currentThread().getName());

			}
		}
	}
	private void task2()  {
		synchronized (obj2) {
			System.out
					.println("DeadloackSimulation.task2() acquired lock on obj2"
							+ Thread.currentThread().getName());
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (obj1) {
				System.out.println(
						"DeadloackSimulation.task2() acquired lock on obj1"
								+ Thread.currentThread().getName());

			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		DeadloackSimulation ds = new DeadloackSimulation();
		Runnable r1 = ds::task1;
		Runnable r2 = ds::task2;
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		
		try {
			t1.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			t2.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		t1.join();
		t2.join();
	}

}
