package study.eventhandle.demo2;

import java.util.ArrayList;
import java.util.List;

/**
 * demo2尝试改变Node中方法为dispatchEvent，添加MyEvent类。<br>
 * 将业务逻辑向事件分发靠拢。<br>
 * 事件处理的逻辑并非有效逻辑，通过注释做了些说明。<br>
 * 进一步的实现参考 demo3
 */
public class TreeNode extends Node {

    public final List<Node> children = new ArrayList<>();

    @Override
    public boolean dispatchEvent(MyEvent event) {
        boolean result = false;

        /*
        触摸屏交互，事件顺序应该为
        DOWN -> MOVE -> UP
        先处理DOWN事件
         */
        if (event.getAction() == MyEvent.ACTION_DOWN) {

            /*
            子Node返回True，即返回True
            这个逻辑实际不符合触摸交互业务逻辑，只是先在Action Down里填一些废代码
             */
            if (children.size() > 0) {
                for (Node node: children) {
                    if (node.dispatchEvent(event)) {
                        return true;
                    }
                }
            }
        }


        /*
        其他处理，这里只是写一些无效代码
         */
        if (super.dispatchEvent(event)) {
            return true;
        }

        return result;
    }
}
