package ex_05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Main
 */
public class Main {
    public static Map<String, Integer> concurrentHashMapObject = null;
	public static Map<String, Integer> synchronizedMapObject = null;
	public static List<Integer> copyOnWriteListObject = null;
    public static List<Integer> synchronizedListObject = null;
    
    public static void main(String[] args) {
		concurrentHashMapObject = new ConcurrentHashMap<String, Integer>();
		synchronizedMapObject = Collections.synchronizedMap(new HashMap<String, Integer>());
		copyOnWriteListObject = new CopyOnWriteArrayList<>();
		synchronizedListObject = Collections.synchronizedList(new ArrayList<Integer>());
    }
    

}