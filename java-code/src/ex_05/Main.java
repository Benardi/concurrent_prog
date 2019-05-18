
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

/**
 * Main
 */
public class Main {
    public final static int THREAD_POOL_SIZE = 10;
	public final static int ENTRIES_SIZE = 1000;


    public static Map<String, Integer> concurrentHashMapObject = null;
	public static Map<String, Integer> synchronizedMapObject = null;
	public static List<Integer> copyOnWriteListObject = null;
    public static List<Integer> synchronizedListObject = null;
    
    public static void main(String[] args) throws InterruptedException {
		concurrentHashMapObject = new ConcurrentHashMap<String, Integer>();
        synchronizedMapObject = Collections.synchronizedMap(new HashMap<String, Integer>());


        copyOnWriteListObject = new CopyOnWriteArrayList<>();
        testList(copyOnWriteListObject);

        synchronizedListObject = Collections.synchronizedList(new ArrayList<Integer>());
        testList(synchronizedListObject);
    }
    
    public static void testList(final List<Integer> testSet) throws InterruptedException {
		for (int i = 0; i < THREAD_POOL_SIZE; i++) {

			long initialTime = System.nanoTime();

			ExecutorService executorServer = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

			for (int j = 0; j < THREAD_POOL_SIZE; j++) {
				executorServer.execute(new Runnable() {
					public void run() {
						for (int i = 0; i < ENTRIES_SIZE; i++) {
							Integer random = (int) Math.ceil(Math.random() * 550000);

							testSet.add(i, random);
							Integer notImportantNumber = testSet.get(i);
						}
					}
				});
			}

			executorServer.shutdown();
			executorServer.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);

			long finalTime = System.nanoTime();
			long totalTime = (finalTime - initialTime) / 1000000L;
			System.out.println(totalTime);
		}
	}
}