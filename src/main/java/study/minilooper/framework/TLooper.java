package study.minilooper.framework;


public final class TLooper {

    private static final String TAG = "TLooper";

    private static final ThreadLocal<TLooper> sThreadLocal = new ThreadLocal<TLooper>();
    //private Thread mThread;
    TMessageQueue mQueue;

    private TLooper() {
        TLog.d(TAG, "TLooper init");
        mQueue = new TMessageQueue();
        //mThread = Thread.currentThread();
    }

    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one looper may be created per thread");
        }
        sThreadLocal.set(new TLooper());
        TLog.d(TAG, "TLooper prepare completed");
    }

    public static TLooper myLooper() {
        return sThreadLocal.get();
    }

    public static void loop() {
        final TLooper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper");
        }
        TLog.d(TAG, "TLooper start loop");

        final TMessageQueue queue = me.mQueue;
        for (; ; ) {
            // 得到下一条消息
            TMessage msg = queue.next();
            if (msg == null) {
                // 空消息在Demo中忽略
            } else {
                if (msg.target != null) {
                    // 分发消息
                    msg.target.dispatchMessage(msg);
                }
            }

            try {
                // 没有实际nativePollOnce机制，采用固定一段时间间隔
                Thread.sleep(Const.TIME_GAP_60FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
