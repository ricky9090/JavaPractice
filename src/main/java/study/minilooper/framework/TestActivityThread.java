package study.minilooper.framework;


import study.minilooper.MainActivity;

import javax.swing.*;

/**
 * 模拟Android Looper，运行Activity
 */
public class TestActivityThread {

    public static final String TAG = "TestActivityThread";

    public static void main(String[] args) throws InterruptedException {

        //初始化Looper, MessageQueue
        TLog.d(TAG, "Application init");
        TLooper.prepare();

        //初始化 "Activity"
        JFrame frame = new JFrame();
        frame.setTitle("测试APP");
        frame.setSize(480, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        TActivity mainActivity = new MainActivity();
        mainActivity.setWindow(frame);

        // 触发OnCreate
        TMessage msg = new TMessage();
        msg.what = Const.ON_CREATE;
        mainActivity.activityHandler.sendMessage(msg);

        //Looper运行
        TLooper.loop();
    }
}
