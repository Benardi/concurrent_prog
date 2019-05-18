package ex_02;

public class Main {

    public static void main(String[] args) {
        Messenger messenger = new Messenger(3);
        Receiver receiver = new Receiver(messenger,20);
        Sender sender = new Sender(1, messenger, 10);
        Sender secondSender = new Sender(2, messenger, 10);

        Thread receiverThread = new Thread(receiver);
        Thread senderThread = new Thread(sender);
        Thread secondSenderThread = new Thread(secondSender);

        senderThread.start();
        secondSenderThread.start();
        receiverThread.start();
    }
}


