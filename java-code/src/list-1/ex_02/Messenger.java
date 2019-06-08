package ex_02;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class Messenger implements Channel {

    private Queue<String> messages;
    private Semaphore isFull;
    private Semaphore isEmpty;

    public Messenger(int capacity) {
        messages = new ArrayDeque<>();
        isFull = new Semaphore(capacity);
        isEmpty = new Semaphore(0);
    }

    @Override
    public void putMessage(String message) {
            try {
                isFull.acquire();
                synchronized (this) {
                    messages.add(message);
                    System.out.println("Put: " + message);
                }
                isEmpty.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    @Override
    public String takeMessage() {
        String message = null;
            try {
                isEmpty.acquire();
                synchronized (this) {
                    message = messages.remove();
                }
                isFull.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return message;
    }
}