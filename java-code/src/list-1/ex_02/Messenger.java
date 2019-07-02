package ex_02;

import java.util.ArrayDeque;
import java.util.Queue;

class Messenger implements Channel {

    private final Queue<String> messages;
    private int capacity;

    public Messenger(int capacity) {
        messages = new ArrayDeque<>();
        this.capacity = capacity;
    }

    private boolean isEmpty() {
        return messages.size() == 0;
    }

    private boolean isFull() {
        return  messages.size() == capacity;
    }

    @Override
    public void putMessage(String message) {
        synchronized (messages) {
            while(isFull()) {
                try {
                    messages.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            messages.add(message);
            System.out.println("Put: " + message);
            messages.notifyAll();
        }
    }

    @Override
    public String takeMessage() {
        synchronized (messages) {
            String message = null;
            try {
                while(isEmpty()) {
                    this.messages.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            message = messages.remove();
            System.out.println("Take: " + message);
            this.messages.notifyAll();
            return message;
        }

    }
}