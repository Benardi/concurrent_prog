import java.lang.management.ThreadMXBean;
/**
 * Main
 */
class Mem implements Runnable {
    private long mem;
    public Mem(long mem) {
        this.mem = mem;
    }

    public void run() {
        long currentMemory = Runtime.getRuntime().totalMemory();
        System.out.println(currentMemory - this.mem);
    }
}

public class Main {
    static Mem mem;


    public static void main(String[] args) {
        long currentMemory = Runtime.getRuntime().totalMemory();
        mem = new Mem(currentMemory);
        Thread t = new Thread(mem);
        t.run();      
    }
}