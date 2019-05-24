package ex_03.combined_return;

import ex_03.first_return.Requester;

public class CombinedRequester extends Requester {

    public CombinedRequester(int id) {
        super(id);
    }

    public void updateTime(int time) {
        int currentTime = CombinedRequester.getTime();
        super.updateTime(currentTime + time);
    }
}
