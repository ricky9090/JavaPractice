package study.eventhandle.demo3;

/**
 * @see study.eventhandle.demo3.TreeNode
 */
public class TestMain {
    public static void main(String[] args) {
        TreeNode root = buildTestTree();

        test1(root);
        System.out.println();
        test2(root);
        System.out.println();
        test3(root);
    }

    /**
     * Node #6
     * <pre>
     * ------ DOWN ------
     * 节点#6处理事件
     * 当前节点#2,分发到子节点#6
     * 当前节点#1,分发到子节点#2
     * ------ MOVE ------
     * 节点#6处理事件
     * ------ UP ------
     * 节点#6处理事件
     * </pre>
     */
    public static void test1(TreeNode root) {
        MyEvent event1 = new MyEvent(MyEvent.ACTION_DOWN, 6);
        MyEvent event2 = new MyEvent(MyEvent.ACTION_MOVE, 6);
        MyEvent event3 = new MyEvent(MyEvent.ACTION_UP, 6);
        System.out.println("------ DOWN ------");
        root.dispatchEvent(event1);
        System.out.println("------ MOVE ------");
        root.dispatchEvent(event2);
        System.out.println("------ UP ------");
        root.dispatchEvent(event3);
    }

    /**
     * Node #2
     * <pre>
     * ------ DOWN ------
     * 节点#2处理事件
     * 当前节点#1,分发到子节点#2
     * ------ MOVE ------
     * 节点#2处理事件
     * ------ UP ------
     * 节点#2处理事件
     * </pre>
     */
    public static void test2(TreeNode root) {
        MyEvent event1 = new MyEvent(MyEvent.ACTION_DOWN, 2);
        MyEvent event2 = new MyEvent(MyEvent.ACTION_MOVE, 2);
        MyEvent event3 = new MyEvent(MyEvent.ACTION_UP, 2);
        System.out.println("------ DOWN ------");
        root.dispatchEvent(event1);
        System.out.println("------ MOVE ------");
        root.dispatchEvent(event2);
        System.out.println("------ UP ------");
        root.dispatchEvent(event3);
    }

    /**
     * Node #3
     * <pre>
     * ------ DOWN ------
     * 节点#3处理事件
     * 当前节点#1,分发到子节点#3
     * ------ MOVE ------
     * 节点#3处理事件
     * ------ UP ------
     * 节点#3处理事件
     * </pre>
     */
    public static void test3(TreeNode root) {
        MyEvent event1 = new MyEvent(MyEvent.ACTION_DOWN, 3);
        MyEvent event2 = new MyEvent(MyEvent.ACTION_MOVE, 3);
        MyEvent event3 = new MyEvent(MyEvent.ACTION_UP, 3);
        System.out.println("------ DOWN ------");
        root.dispatchEvent(event1);
        System.out.println("------ MOVE ------");
        root.dispatchEvent(event2);
        System.out.println("------ UP ------");
        root.dispatchEvent(event3);
    }

    /**
     * 构造测试树
     * <pre>
     *          #1
     *          +
     *        /  \
     *       #2  #3
     *    ---+---
     *   /   |   \
     *  #4  #5  #6
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
