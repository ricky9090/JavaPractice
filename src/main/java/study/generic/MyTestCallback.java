package study.generic;

/**
 * Created by rickyxe on 2017/9/9.
 */
public class MyTestCallback implements MyCallback<TestMain>, FooInterface {
    @Override
    public void foo() {

    }

    @Override
    public void onSuccess(TestMain o) {
        o.printTestMainName();
    }

    @Override
    public void onFailure(String errorMsg) {
        System.out.println(errorMsg);
    }
}