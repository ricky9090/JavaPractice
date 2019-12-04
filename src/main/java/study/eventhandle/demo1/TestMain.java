package study.eventhandle.demo1;

/**
 * 尝试以数据结构为起点，理解Android事件分发机制<br>
 * Node, TreeNode 相当于 View 与 ViewGroup
 * <p>
 * 1.假设在树中寻找特定节点(val 值相同)。找到则返回 true，反之 false<br>
 * 2.逐层遍历，递归调用子节点 find 方法
 * <p>
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
