
public class ReturnFirst extends Thread {
    public static final int TIMEOUT = 8000;
	private int id;

	public ReturnFirst(int id) throws InterruptedException {
		this.id = id;
		start();
	}

	public void run() {
		try {
			ReturnFirst.request();
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
		int i;
		ReturnFirst[] threads = new ReturnFirst[nthreads];
		for (i = 0; i < nthreads; i++) {
			threads[i] = new ReturnFirst(i);
		}
		i = 0;
		long start = System.currentTimeMillis();
		while (threads[i].isAlive()) {
			i += 1;
			if (System.currentTimeMillis() - start >= TIMEOUT) {
				return -1;
			}
			if (i == nthreads) {
				i = 0;
			}
		}
		return i;
	}

	public static void main(String[] args) throws InterruptedException {
        int num = Integer.parseInt(args[0]);
        System.out.println("Number of threads: " + num);
		int id = gateway(num);
		if (id == -1) {
            System.out.println("No threads are out in less than 8 seconds");
            return;
		}
		System.out.println("Result: " + id);
	}
}

