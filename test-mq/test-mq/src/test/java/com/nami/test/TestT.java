package com.nami.test;

public class TestT {
	
	public static void main(String[] args) {
		Object object = new Object();
		Object object2 = new Object();
		//new Thread(()->doWait(object)).start();
		//new Thread(()->doNotify(object)).start();
		
		new Thread(()->lock1(object,object2)).start();
		new Thread(()->lock2(object,object2)).start();
	}
	
	
	
	public static void lock1(Object object,Object object2) {
		synchronized(object) {
			System.out.println("---lock1---1--");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("---lock1-----");
			synchronized (object2) {
				System.out.println("---lock1---2--");
			}
		}
		System.out.println("---lock1---3--");
	}
	
	public static void lock2(Object object,Object object2) {
		synchronized(object2) {
			System.out.println("---lock2---4--");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("---lock2-----");
			synchronized (object) {
				System.out.println("---lock2---5--");
			}
		}
		System.out.println("---lock2---6--");
	}
	
	public static void doWait(Object object) {
		synchronized(object) {
			try {
				System.out.println("-----1---");
				object.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("----2----");
		}
	}
	
	public static void doNotify(Object object) {
		synchronized(object) {
			System.out.println("----==3=----");
			object.notify();
			System.out.println("----==4=----");
		}
	}
}
