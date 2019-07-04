package ex_03.combined_return;

public class CombinedMain {

    private static int gateway() {
        int num_replicas = 5;

        System.out.println(String.format("Number of threads: %d", num_replicas));

        Thread threads[] = new Thread[num_replicas];
        for (int i = 0; i < num_replicas; i++) {
            Thread t = new Thread(new CombinedRequester(i));
            threads[i] = t;
            t.start();
        }

        for(Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return CombinedRequester.getTime();
    }

    public static void main(String[] args) {
        int result = gateway();
        System.out.println(String.format("Result: %d", result));
    }
}
