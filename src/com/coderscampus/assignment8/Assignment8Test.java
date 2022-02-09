package com.coderscampus.assignment8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

class Assignment8Test {

	@Test
	public void getData () {
		Assignment8 assignment = new Assignment8();
		ExecutorService pool = Executors.newCachedThreadPool();
		List<Integer> allNumbers = Collections.synchronizedList(new ArrayList<>());
		List<CompletableFuture<Void>> tasks = new ArrayList<>();
        for (int i=0; i<1000; i++) {
        	
        	CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> assignment.getNumbers(),
            		pool).thenAccept(numbers -> allNumbers.addAll(numbers));
        	tasks.add(task);
        }
        while(tasks.stream().filter(e -> e.isDone()).count() < 1000) {
        }
        System.out.println("The Number of records in synlist is " + allNumbers.size());
        assignment.countAllNumbers(allNumbers);
	}

}
