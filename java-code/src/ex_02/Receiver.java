package ex_02;

public class Receiver implements Runnable {

    private Messager messager;
    private int msgQuantity;

    public Receiver(Messager messager, int msgQuantity) {
        this.messager = messager;
        this.msgQuantity = msgQuantity;
    }

    @Override
    public void run() {
        for (int i = 0; i < msgQuantity; i++) {
            String message = messager.takeMessage();
            System.out.println("Take: " + message);
        }
    }
}
