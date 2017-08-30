package test.net;

/**
 * Created by rickyxe on 2017/8/29.
 */
public interface RemoteHandler<ENTITY> {

    void onSuccess(ENTITY entity, String rawEntityStr);
    void onFailure(String errMsg);
}
