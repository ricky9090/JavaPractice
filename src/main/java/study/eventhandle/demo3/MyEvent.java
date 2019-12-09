package study.eventhandle.demo3;

public class MyEvent {

    public static final int ACTION_DOWN = 100;
    public static final int ACTION_MOVE = 101;
    public static final int ACTION_UP = 102;

    public static final int ACTION_CANCEL = 200;

    private final int action;
    public final int val; // 为简化Node处理事件逻辑，添加一个val变量做判断

    public int getAction() {
        return action;
    }

    public MyEvent(int action, int val) {
        this.action = action;
        this.val = val;
    }
}
