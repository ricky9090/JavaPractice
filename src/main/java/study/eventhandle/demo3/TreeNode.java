package study.eventhandle.demo3;

import java.util.ArrayList;
import java.util.List;

/**
 * 整理触屏交互的业务逻辑(最简单情况下)：<br>
 * <ul>
 *     <li>1.交互这一过程，触摸事件按时间排列，应有顺序：DOWN -> MOVE -> UP</li>
 *     <li>2.每一个交互，应该有一个控件进行响应 -> TouchTarget</li>
 *     <li>3.假设交互过程中，响应控件固定唯一，那么在DOWN事件时，应该确定这个Target。</li>
 *     <li>4.确定Target后，DOWN后续的事件直接分发给这个Target</li>
 *     <li>5.对于容器节点(对应ViewGroup)，如果没有子节点响应事件，那么它自身进行事件响应。</li>
 *     <li>6.事件从根(容器)节点往下分发，TouchTarget可以存储在根节点中。<br>
 *         (每个容器节点各自存储其下方的Target，不是把最终Target存储在顶层根节点中)</li>
 * </ul>

 */
public class TreeNode extends Node {

    public final List<Node> children = new ArrayList<>();

    public Node touchTarget;

    @Override
    public boolean dispatchEvent(MyEvent event) {
        if (event.getAction() == MyEvent.ACTION_DOWN) {
            // 重置target
            if (touchTarget != null) {
                touchTarget = null;
            }

            // Down事件，寻找Target(#1,#2,#3)
            if (children.size() > 0) {
                for (Node node: children) {
                    if (node.dispatchEvent(event)) {
                        touchTarget = node;
                        System.out.println("当前节点#" + this.val + ",分发到子节点#" + node.val);
                        return true;
                    }
                }
            }
        }

        // 为了避免下面逻辑清除touchTarget，导致最后一行无法给子节点分发事件
        // 此处重新赋值一个变量
        final Node target = touchTarget;

        /*
        没有子控件要响应，这个节点当作普通的Node，不向下分发，自己处理。(#4)
         */
        if (target == null) {
            return super.dispatchEvent(event);
        }

        // UP事件清除target
        if (event.getAction() == MyEvent.ACTION_UP) {
            touchTarget = null;
        }

        // 分发给目标节点(#5)
        return target.dispatchEvent(event);
    }
}
