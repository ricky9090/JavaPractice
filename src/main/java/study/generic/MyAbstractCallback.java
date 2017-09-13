package study.generic;

/**
 * Created by rickyxe on 2017/9/13.
 */
public abstract class MyAbstractCallback<ENTITY> {

    abstract void onSuccess(ENTITY entity);
    abstract void onFailure(String errorMsg);
}
