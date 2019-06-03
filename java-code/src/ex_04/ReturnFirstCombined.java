public class ReturnFirstCombined extends Thread {
	private static final int TIMEOUT = 16000;
    private int id;
    private int time;

    public ReturnFirstCombined(int id) throws InterruptedException {
        this.id = id;
        start();
    }

    public int getTime() {
        return time;
    }

    public void run() {
        try {
            this.time = ReturnFirstCombined.request();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static int request() throws InterruptedException {
        int time = (int) (1 + Math.random() * 30);
        ReturnFirst.sleep(time * 1000);
        return time;
    }

    static int gateway(int nthreads) throws InterruptedException {
        ReturnFirstCombined[] threads = new ReturnFirstCombined[nthreads];
        int i;
        int total = 0;
        for (i = 0; i < nthreads; i++) {
            threads[i] = new ReturnFirstCombined(i);
        }
        i = 0;
        long start = System.currentTimeMillis();
        do {
            if (!threads[i].isAlive()) {
                total += threads[i].getTime();
                i++;
            }
            if (System.currentTimeMillis() - start >= TIMEOUT) {
                return -1;
            }
        } while (i < nthreads);
		return total;
	}

	public static void main(String[] args) throws InterruptedException {
        int num = Integer.parseInt(args[0]);
        System.out.println("Number of threads: " + num);
		int id = gateway(num);
		if (id == -1) {
            System.out.println("No threads are out in less than 16 seconds");
            return;
		}
		System.out.println("Result: " + id);
	}
}

