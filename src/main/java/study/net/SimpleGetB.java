package study.net;

/**
 * Created by rickyxe on 2017/8/29.
 */
public class SimpleGetB {

    /**
     * 主要实现了同步请求方法
     *
     */
    public static void main(String[] args) {
        SimpleNetClient netClient = new SimpleNetClient();
        SimpleNetRequest request = new SimpleNetRequest.Builder()
                .method(SimpleNetRequest.METHOD_GET)
                .url("http://127.0.0.1:8888")
                .build();
        Person person = netClient.getData(request, Person.class);
        System.out.println(person);
    }
}
