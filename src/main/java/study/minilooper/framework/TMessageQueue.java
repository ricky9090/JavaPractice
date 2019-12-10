package study.minilooper.framework;

import java.util.ArrayList;


public class TMessageQueue {

    public final ArrayList<THandler> handlerList = new ArrayList<THandler>();

    public TMessage mHeadMsg = new TMessage();
    public TMessage mLastMsg = mHeadMsg;
    public TMessage currentMsg = mHeadMsg;

    public TMessageQueue() {

    }

    public TMessage next() {
        synchronized (this) {
            final TMessage result = currentMsg;
            if (currentMsg != null) {
                currentMsg = currentMsg.next;
            }
            return result;
        }
    }

    public void enqueueMessage(TMessage msg) {
        synchronized (this) {
            if (this.mLastMsg == null) {
                throw new RuntimeException("Message Queue invalid");
            }

            this.mLastMsg.next = msg;
            msg.next = null;
            this.mLastMsg = msg;
            if (this.currentMsg == null) {
                this.currentMsg = this.mLastMsg;
            }
        }
    }

}
