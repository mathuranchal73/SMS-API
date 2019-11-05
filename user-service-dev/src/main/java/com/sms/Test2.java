package com.sms;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Test2 {
	
	public static void main(String args[]) {
		
		ExecutorService executor= Executors.newSingleThreadExecutor();
		
		
		Callable<Integer> task= ()->{
			
			try {
				TimeUnit.SECONDS.sleep(1);
				return 123;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				throw new IllegalStateException("task interrupted", e);
			}
			
			
		};
		
		Future<Integer> future= executor.submit(task);

		System.out.println("Future Done?"+future.isDone());
		try {  
			System.out.println("Future Done?"+future.isDone());
			
			Integer result= future.get();
			System.out.println("Result"+result);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
