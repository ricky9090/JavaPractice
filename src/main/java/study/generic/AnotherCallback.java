package study.generic;

/**
 * Created by rickyxe on 2017/9/13.
 */
public class AnotherCallback extends MyAbstractCallback<TestMain> {

    @Override
    void onSuccess(TestMain testMain) {
        testMain.printTestMainName();
    }

    @Override
    void onFailure(String errorMsg) {
        System.out.println(errorMsg);
    }
}
