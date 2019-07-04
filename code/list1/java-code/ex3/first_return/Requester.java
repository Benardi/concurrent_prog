package ex_03.first_return;

import java.util.Random;

public class Requester implements Runnable {

    private int id;
    private static volatile int time = 0;

    public  Requester(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        Random random = new Random();
        int sleepTime = 1 + random.nextInt(29);

        try {
            System.out.printf("Sleep time of thread %d: %ds\n", id, sleepTime);
            Thread.sleep(sleepTime * 1000);
            this.updateTime(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateTime(int time) {
        Requester.time = time;
    }

    public static int getTime() {
        return time;
    }
}
