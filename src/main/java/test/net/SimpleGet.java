package test.net;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by rickyxe on 2017/8/28.
 */
public class SimpleGet {

    /**
     * 简单的通过get方法请求url获取数据，分别使用了同步与异步调用，并没有做范型封装
     */
    public void getData() {
        OkHttpClient netClient = new OkHttpClient.Builder().build();

        Request request = new Request.Builder().url("http://127.0.0.1:8888").get().build();
        try {
            Response res  = netClient.newCall(request).execute();
            netClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response != null && response.body() != null) {
                        System.out.println("async request: " + response.body().string());

                    }
                }
            });

            if (res != null && res.body() != null) {
                String result = res.body().string();
                System.out.println("sync request: " + result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SimpleGet test = new SimpleGet();
        test.getData();
    }
}
