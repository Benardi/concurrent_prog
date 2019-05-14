package ex_02;

import java.util.Random;

public class Sender implements Runnable {

    private Messager messager;
    private int msgQuantity;
    private int id;

    public Sender(int id, Messager messager, int msgQuantity) {
        this.id = id;
        this.messager = messager;
        this.msgQuantity = msgQuantity;
    }

    @Override
    public void run() {
        for (int i = 0; i < msgQuantity; i++) {
            messager.putMessage(createMsg());
        }
    }

    private String createMsg() {
        Random random = new Random();
        return String.format("msg %d from sender %d", random.nextInt(10), id);
    }
}
