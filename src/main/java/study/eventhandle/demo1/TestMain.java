package study.eventhandle.demo1;

/**
 * 尝试以数据结构为起点，理解Android事件分发机制<br>
 * Node, TreeNode 相当于 View 与 ViewGroup
 * <p>
 * 1.假设在树中寻找特定节点(val 值相同)。找到则返回 true，反之 false<br>
 * 2.逐层遍历，递归调用子节点 find 方法
 * <p>
 *
 * <h2>思路：</h2>
 * Android事件分发原理，网上很多文章采用解析系统源码的方法，配以大量类图，时序图，逻辑框图。<p>
 * 而Android系统经过多年发展，源代码越来越复杂，一些系统的核心机制是没有很大变化的。<p>
 * 例如比较ViewGroup源码，2.3版本代码3700+行，4.0版本增加到5700+，最新9.0版本达到8700+。<p>
 * 其中事件分发的核心函数之一dispatchTouchEvent，2.3版本119行，9.0版本213行<p>
 * 可见现实中随着系统功能增加，直接分析源码的工作量直线上升，难度越来越大。<p>
 * 而<b>事件分发的核心机制</b>，分析9.0版本有效，分析早期的2.3版本同样有效。<p>
 * <p>
 * 通过Android源码分析出的事件分发“原理”，以及各种类图流程图，是Android源码（甚至特定版本）强绑定的。
 * 而移动端事件分发的原理，则应该是抽象的，不与源代码强相关。<p>
 * <p>
 * 事件分发的核心机制，在UI层面来看，类似树形数据结构中节点的查找与处理。<p>
 * 回归原点，从基本的数据结构中的查找出发，逐渐引入触控事件等概念，就应该能得到事件分发的核心逻辑。<p>
 * 将这一结果与系统源码逐版本比较，就不难理解新功能对核心机制的影响，以及其代码重构的动机
 */
public class TestMain {

    /*
    TODO 构造模拟几种事件 DOWN, MOVE, UP, CANCEL
    TODO 更改分发方法，传入事件类型
    TODO 分发方法中处理不同类型事件
     */


    public static void main(String[] args) {
        TreeNode root = buildTestTree();

        System.out.println(root.dispatchFind(1));
        System.out.println(root.dispatchFind(2));
        System.out.println(root.dispatchFind(3));
        System.out.println(root.dispatchFind(4));
        System.out.println(root.dispatchFind(5));
        System.out.println(root.dispatchFind(6));
        System.out.println(root.dispatchFind(7));  // false
    }

    /**
     * 构造测试树
     * <pre>
     *        root
     *         +
     *       /  \
     *      a    b
     *    --+--
     *   /  |  \
     *  c   d   e
     * </pre>
     **/
    public static TreeNode buildTestTree() {

        TreeNode root = new TreeNode();
        root.val = 1;

        TreeNode a = new TreeNode();
        a.val = 2;

        Node b = new Node();
        b.val = 3;

        Node c = new Node();
        c.val = 4;

        Node d = new Node();
        d.val = 5;

        Node e = new Node();
        e.val = 6;

        root.children.add(a);
        root.children.add(b);

        a.children.add(c);
        a.children.add(d);
        a.children.add(e);

        return root;
    }
}
