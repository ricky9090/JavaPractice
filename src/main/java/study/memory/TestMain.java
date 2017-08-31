package study.memory;

/**
 * Created by rickyxe on 2017/8/31.
 */
public class TestMain {

    public void testMethod() {
        LoopObject test = new LoopObject();
    }

    public static void main(String[] args) {
        new LoopObject();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i< 5; i++) {
                    System.gc();
                    try {
                        Thread.sleep(500);
                        System.out.println("Loop1: " + i + " GC counts: " + LoopObject.gcCount);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();

        System.out.println("Thread 1 has run");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i< 10; i++) {
                    System.gc();
                    try {
                        Thread.sleep(500);
                        System.out.println("Loop2: " + i + " GC counts2: " + LoopObject.gcCount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    System.out.println("GC countsTotal: " + LoopObject.gcCount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
