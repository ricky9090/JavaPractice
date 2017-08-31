package study.memory;

/**
 * Created by rickyxe on 2017/8/31.
 */
public class LoopObject {

    public static int gcCount = 0;
    public static int objCount = 0;

    public LoopObject() {
        synchronized (LoopObject.class) {
            if (objCount > 0) {
                throw new UnsupportedOperationException("Can not create more object");
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize invoke");
        super.finalize();
        synchronized (LoopObject.class) {
            gcCount++;
            objCount = 0;
            new LoopObject();
        }

    }
}
