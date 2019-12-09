package study.eventhandle.demo3;

public class Node {

    public int val;

    /**
     * 分发事件，函数定义仿照Android View<br>
     * true代表事件被处理，反之false
     * @param event MyEvent
     * @return true if event handled, false otherwise
     */
    public boolean dispatchEvent(MyEvent event) {
        if (event.val == this.val) {
            System.out.println("节点#" + val + "处理事件");
            return true;
        }
        return false;
    }
}
