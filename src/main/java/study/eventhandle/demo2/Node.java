package study.eventhandle.demo2;

public class Node {

    public int val;

    /**
     * 分发事件，函数定义仿照Android View
     * True代表事件被处理，反之false
     * @param event
     * @return
     */
    public boolean dispatchEvent(MyEvent event) {
        return true;
    }
}
