package ex_02;

public class Receiver implements Runnable {

    private Messenger messenger;
    private int msgQuantity;

    public Receiver(Messenger messenger, int msgQuantity) {
        this.messenger = messenger;
        this.msgQuantity = msgQuantity;
    }

    @Override
    public void run() {
        for (int i = 0; i < msgQuantity; i++) {
            String message = messenger.takeMessage();
            System.out.println("Take: " + message);
        }
    }
}
