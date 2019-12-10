package study.minilooper.framework;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class TActivity {

    protected THandler activityHandler;

    // 用JFrame当作Activity的Window
    private JFrame window;

    public TActivity() {
        activityHandler = new THandler() {

            @Override
            public void handleMessage(TMessage msg) {
                switch (msg.what) {
                    case Const.ON_CREATE:
                        onCreate();
                        window.setVisible(true);
                        break;
                    case Const.ON_DESTORY:
                        onDestroy();
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public JFrame getWindow() {
        return window;
    }

    void setWindow(JFrame window) {
        this.window = window;
        window.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                TMessage msg = new TMessage();
                msg.what = Const.ON_DESTORY;
                activityHandler.sendMessage(msg);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    protected void onCreate() {
        TLog.d("TActivity", "onCreate");
    }

    protected void onDestroy() {
        TLog.d("TActivity", "onDestroy");
    }

}
