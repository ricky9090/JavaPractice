package study.generic;

/**
 * Created by rickyxe on 2017/9/9.
 */
public interface MyCallback<ENTITY> {

    void onSuccess(ENTITY entity);
    void onFailure(String errorMsg);
}
