package ex_02;

import java.util.Random;

public class Sender implements Runnable {

    private Messenger messenger;
    private int msgQuantity;
    private int id;

    public Sender(int id, Messenger messenger, int msgQuantity) {
        this.id = id;
        this.messenger = messenger;
        this.msgQuantity = msgQuantity;
    }

    @Override
    public void run() {
        for (int i = 0; i < msgQuantity; i++) {
            messenger.putMessage(createMsg());
        }
    }

    private String createMsg() {
        Random random = new Random();
        return String.format("msg %d from sender %d", random.nextInt(10), id);
    }
}
