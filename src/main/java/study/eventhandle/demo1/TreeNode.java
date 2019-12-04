package study.eventhandle.demo1;

import java.util.ArrayList;
import java.util.List;

public class TreeNode extends Node {

    public final List<Node> children = new ArrayList<>();

    @Override
    public boolean dispatchFind(int targetVal) {
        /*
        这个if代码块类似于ViewGroup中的拦截事件
        在"寻找节点"情景中，先于子节点的遍历调用
        事件处理场景下，则应移到子节点遍历后
         */
        if (super.dispatchFind(targetVal)) {
            return true;
        }

        boolean result = false;
        if (children.size() > 0) {
            for (Node node: children) {
                boolean find = node.dispatchFind(targetVal);
                result = (result || find);
            }
        }

        return result;
    }
}
