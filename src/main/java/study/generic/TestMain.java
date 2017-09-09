package study.generic;

/**
 * Created by rickyxe on 2017/9/8.
 */
public class TestMain {

    public String name;

    // 打开用来测试newInstance报错走onFailure
    /*public TestMain(String name) {
        this.name = name;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printTestMainName() {
        System.out.println("Name of TestMain is: " + this.name);
    }

    public static void main(String[] args) {

        MyClient.handleEvent(new MyTestCallback());

        MyClient.handleEvent(new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                System.out.println("String is: " + s);
            }

            @Override
            public void onFailure(String errorMsg) {
                System.out.println(errorMsg);
            }
        });

    }
}
