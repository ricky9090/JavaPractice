package study.net;

/**
 * Created by rickyxe on 2017/8/29.
 */
public class SimpleGetC {

    /**
     * 主要实现异步请求方法
     *
     */
    public static void main(String[] args) {
        SimpleNetClient netClient = new SimpleNetClient();
        SimpleNetRequest request = new SimpleNetRequest.Builder()
                .method(SimpleNetRequest.METHOD_GET)
                .url("http://127.0.0.1:8888")
                .build();
        netClient.getDataAsync(request, Person.class, new RemoteHandler<Person>() {
            @Override
            public void onSuccess(Person person, String rawEntityStr) {
                System.out.println(person);
            }

            @Override
            public void onFailure(String errMsg) {
                System.out.println(errMsg);
            }
        });


    }
}
