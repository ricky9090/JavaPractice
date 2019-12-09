package study.eventhandle.demo2;

public class MyEvent {

    public static final int ACTION_DOWN = 100;
    public static final int ACTION_MOVE = 101;
    public static final int ACTION_UP = 102;

    public static final int ACTION_CANCEL = 200;

    private final int action;

    public int getAction() {
        return action;
    }

    public MyEvent(int action) {
        this.action = action;
    }
}
