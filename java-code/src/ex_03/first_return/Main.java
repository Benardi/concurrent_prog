package ex_03.first_return;

public class Main {

    private static int gateway() {
        int num_replicas = 5;

        System.out.println(String.format("Number of threads: %d", num_replicas));

        for (int i = 0; i < num_replicas; i++) {
            Thread t = new Thread(new Requester(i));
            t.start();
        }

        while (true) {
            if(Requester.getTime() > 0) {
                return Requester.getTime();
            }
        }
    }

    public static void main(String[] args) {
        int result = gateway();
        System.out.println(String.format("Result: %d", result));
    }
}
