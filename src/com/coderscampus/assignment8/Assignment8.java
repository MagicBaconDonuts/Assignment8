package com.coderscampus.assignment8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Assignment8 {
	private List<Integer> numbers = null;
	private AtomicInteger i = new AtomicInteger(0);
	private Map<Integer, Integer> numberAmount = new HashMap<>();
	private List<Integer> totalAmountOfNumbersPassed = new ArrayList<>(); 

	public Assignment8() {
		try {
			// Make sure you download the output.txt file for Assignment 8
			// and place the file in the root of your Java project
			numbers = Files.readAllLines(Paths.get("output.txt")).stream().map(n -> Integer.parseInt(n))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will return the numbers that you'll need to process from the list
	 * of Integers. However, it can only return 1000 records at a time. You will
	 * need to call this method 1,000 times in order to retrieve all 1,000,000
	 * numbers from the list
	 * 
	 * @return Integers from the parsed txt file, 1,000 numbers at a time
	 */
	public List<Integer> getNumbers() {
		int start, end;
		synchronized (i) {
			start = i.get();
			end = i.addAndGet(1000);

			System.out.println("Starting to fetch records " + start + " to " + (end));
		}
		// force thread to pause for half a second to simulate actual Http / API traffic
		// delay
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		List<Integer> newList = new ArrayList<>();
		IntStream.range(start, end).forEach(n -> {
			newList.add(numbers.get(n));
		});
		System.out.println("Done Fetching records " + start + " to " + (end));
		return newList;
	}

	public void countAllNumbers(List<Integer> tasks) {
			// go threw the array and check to see if the number that is passed is set as a
			// key for the hashmap
			// If not then set the value as the value passed, if so add a 1 to the value so
			// it keeps the count up
			tasks.stream().forEach(number -> checkNumberForKey(number));
			for(Map.Entry numberEntry : numberAmount.entrySet()) {
				System.out.println(numberEntry.getKey() + " : " + numberEntry.getValue());
			}
	}

	private void checkNumberForKey(Integer number) {
		if (numberAmount.containsKey(number)) {//this check to see if the key has been delcared
			//This only goes if the key has been declared
			// we want this to add 1 to the value for the key for that its equal to
//			totalAmountOfNumbersPassed.add(number);
			numberAmount.put(number, numberAmount.get(number) + 1);
		} else { // this only works if the key hasn't been declared
			// since the key hasn't been declared we want to declare a key by the number
			// that was passed and add 1 to the count(value)
			//since it hasnt been declared I have to set the key to the number that was passed.
			numberAmount.put(number, 1);// this sets the key to the number that was passed and puts 1 into it
		}
	}

}
