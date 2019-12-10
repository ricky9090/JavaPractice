package study.minilooper;


import study.minilooper.framework.Const;
import study.minilooper.framework.TActivity;
import study.minilooper.framework.THandler;
import study.minilooper.framework.TMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @see study.minilooper.framework.TestActivityThread
 */
public class MainActivity extends TActivity {

    public static final String TAG = "MainActivity";

    private int _what = Const.ONE;

    private JButton button;
    private JLabel label;

    // User Handler
    private THandler mHandler = new THandler() {

        @Override
        public void handleMessage(TMessage msg) {
            switch (msg.what) {
                case Const.ONE:
                    label.setText("ONE");
                    break;
                case Const.TWO:
                    label.setText("TWO");
                    break;
                case Const.THREE:
                    label.setText("THREE");
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate() {
        super.onCreate();


        button = new JButton("Press");
        label = new JLabel("Hello World");
        BorderLayout layout = new BorderLayout();
        getWindow().setLayout(layout);
        getWindow().add(label, BorderLayout.CENTER);
        getWindow().add(button, BorderLayout.SOUTH);

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TMessage msg = new TMessage();
                msg.what = _what;
                mHandler.sendMessage(msg);

                if (_what < Const.THREE) {
                    _what += 1;
                } else {
                    _what = Const.ONE;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
