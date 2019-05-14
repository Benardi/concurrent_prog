package ex_02;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class Messager implements Channel {

    private Queue<String> messages;
    private Semaphore empty;
    private Semaphore full;

    public Messager(int capacity) {
        messages = new ArrayDeque<>();
        empty = new Semaphore(capacity);
        full = new Semaphore(0);
    }

    @Override
    public void putMessage(String message) {
            try {
                empty.acquire();
                synchronized (this) {
                    messages.add(message);
                    System.out.println("Put: " + message);
                }
                full.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    @Override
    public String takeMessage() {
        String message = null;
            try {
                full.acquire();
                synchronized (this) {
                    message = messages.remove();
                }
                empty.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return message;
    }
}