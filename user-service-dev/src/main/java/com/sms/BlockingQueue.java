package com.sms;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueue {
	
	
	
	public static void main(String args[])throws InterruptedException{
		
		//Define capacity of ArrayBlockingQueue
		int capacity=5;
		
		 
		
		LinkedBlockingQueue<String> blockingQueue= new LinkedBlockingQueue<String>(capacity);
		
		// Add elements to ArrayBlockingQueue using put method 
		
		try {
			for(int i=0;i<=7;i++)
			{
				blockingQueue.add("Anchal"+i);
				 System.out.println("queue contains "
			             + blockingQueue); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		finally {
			for(int j=blockingQueue.size();j>-2;j--) {
		String removedItem=blockingQueue.take();
		System.out.println("Item Removed: "+removedItem);
		System.out.println("Size of Queue: "+blockingQueue.size());
		System.out.println("Updated Queue: "+blockingQueue);
			}
		}
      
	}

}
