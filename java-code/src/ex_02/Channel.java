package ex_02;

public interface Channel {
    void putMessage(String message);
    String takeMessage();
}
