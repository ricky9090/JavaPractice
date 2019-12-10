package study.minilooper.framework;

public class THandler {

    public static final String TAG = "THandler";

    private final TLooper mLooper;
    private final TMessageQueue mQueue;


    public THandler() {
        mLooper = TLooper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException("Can't create handler before MyLooper.prepare()");
        }
        TLog.d(TAG, "Get looper succeed, " + Thread.currentThread());
        mQueue = mLooper.mQueue;
        synchronized (mQueue) {
            if (!mQueue.handlerList.contains(this)) {
                mQueue.handlerList.add(this);
            }
            TLog.d(TAG, "Attach to looper's Queue succeed");
        }
    }

    public void dispatchMessage(TMessage msg) {
        if (msg.what == Const.ON_DESTORY) {
            synchronized (mQueue) {
                mQueue.handlerList.remove(this);
            }
        }
        handleMessage(msg);
    }

    public void handleMessage(TMessage msg) {
    }

    public void sendMessage(TMessage msg) {
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }

}
