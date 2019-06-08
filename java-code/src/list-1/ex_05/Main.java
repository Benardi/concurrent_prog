
// package ex_05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
* Main
*/
public class Main {
	public final static int MAX_THREAD_POOL_SIZE = 10;
	public final static int ENTRIES_SIZE = 50;
	
	public static Map<String, Integer> concurrentHashMapObject = null;
	public static Map<String, Integer> synchronizedMapObject = null;
	public static List<Integer> copyOnWriteListObject = null;
	public static List<Integer> synchronizedListObject = null;
	
	public static void main(String[] args) throws InterruptedException {
		List<Integer> multiplierRange = IntStream.rangeClosed(1,10)
			.boxed().collect(Collectors.toList());
		List<Integer> writeLevels = IntStream.rangeClosed(1,4)
			.boxed().collect(Collectors.toList());
		List<Integer> rounds = IntStream.rangeClosed(1,15)
			.boxed().collect(Collectors.toList());
		copyOnWriteListObject = new CopyOnWriteArrayList<>();
		synchronizedListObject = Collections.synchronizedList(new ArrayList<Integer>());
		concurrentHashMapObject = new ConcurrentHashMap<String, Integer>();
		synchronizedMapObject = Collections.synchronizedMap(new HashMap<String, Integer>());
		
		rounds.forEach(r -> {			
			writeLevels.forEach(wl -> {			
				multiplierRange.forEach(multiplier -> {
					try {
						testMap(concurrentHashMapObject, multiplier, wl);
						testMap(synchronizedMapObject, multiplier, wl);
						testList(synchronizedListObject, multiplier, wl);
						testList(copyOnWriteListObject, multiplier, wl);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				});
			});
		});
	}
	
	public static void testList(final List<Integer> testCollection, int entriesMultiplier, int writeLevel) throws InterruptedException {
		for (int poolSize = 1; poolSize <= MAX_THREAD_POOL_SIZE; poolSize+=3) {
			
			ExecutorService executorServer = Executors.newFixedThreadPool(poolSize);
			int size = ENTRIES_SIZE * entriesMultiplier;
			long initialTime = System.nanoTime();
			
			for (int j = 0; j < poolSize; j++) {
				executorServer.execute(() -> {
					for (int i = 0; i < size; i++) {
						
						Integer random = (int) Math.ceil(Math.random() * 550000);
						if (writeLevel == 4) {
							testCollection.add(i, random);
							testCollection.add(i, random);
							testCollection.add(i, random);
							testCollection.add(i, random);
						}

						if (writeLevel == 3) {
							testCollection.add(i, random);
							testCollection.add(i, random);
							testCollection.add(i, random);
							testCollection.get(i);
						}

						if (writeLevel == 2) {
							testCollection.add(i, random);
							testCollection.add(i, random);
							testCollection.get(i);
							testCollection.get(i);
						}

						if (writeLevel == 1) {
							testCollection.add(i, random);
							testCollection.get(i);
							testCollection.get(i);
							testCollection.get(i);
						}
					}
				});
			}
			
			executorServer.shutdown();
			executorServer.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
			
			long finalTime = System.nanoTime();
			long totalTime = finalTime - initialTime;
			System.out.printf("%s,%d,%d,%d,%d" + System.lineSeparator(),
				testCollection.getClass().getSimpleName(),
				size, totalTime, poolSize, writeLevel
			);
		}
	}
	
	public static void testMap(final Map<String, Integer> testCollection, int entriesMultiplier, int writeLevel) throws InterruptedException {
		for (int poolSize = 1; poolSize <= MAX_THREAD_POOL_SIZE; poolSize+=3) {
			
			ExecutorService executorServer = Executors.newFixedThreadPool(poolSize);
			
			int size = ENTRIES_SIZE * entriesMultiplier;
			long initialTime = System.nanoTime();
			
			for (int j = 0; j < poolSize; j++) {
				executorServer.execute(() -> {
					for (int i = 0; i < size; i++) {
						Integer random = (int) Math.ceil(Math.random() * 550000);
						if (writeLevel == 4) {
							testCollection.put(String.valueOf(random), random);
							testCollection.put(String.valueOf(random), random);
							testCollection.put(String.valueOf(random), random);
							testCollection.put(String.valueOf(random), random);
						}

						if (writeLevel == 3) {
							testCollection.put(String.valueOf(random), random);
							testCollection.put(String.valueOf(random), random);
							testCollection.put(String.valueOf(random), random);
							testCollection.get(String.valueOf(random));
						}

						if (writeLevel == 2) {
							testCollection.put(String.valueOf(random), random);
							testCollection.put(String.valueOf(random), random);
							testCollection.get(String.valueOf(random));
							testCollection.get(String.valueOf(random));
						}

						if (writeLevel == 1) {
							testCollection.put(String.valueOf(random), random);
							testCollection.get(String.valueOf(random));
							testCollection.get(String.valueOf(random));
							testCollection.get(String.valueOf(random));
						}
						

						testCollection.put(String.valueOf(random), random);
						testCollection.get(String.valueOf(random));
					}
				}
				);
			}
			
			executorServer.shutdown();
			executorServer.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
			
			long finalTime = System.nanoTime();
			long totalTime = finalTime - initialTime;
			System.out.printf("%s,%d,%d,%d,%d" + System.lineSeparator(), 
				testCollection.getClass().getSimpleName(),
				size, totalTime, poolSize, writeLevel
			);
		}
	}
}

