package test.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by rickyxe on 2017/8/28.
 */
public class SimpleNetClient {

    private OkHttpClient okHttpClient;

    public SimpleNetClient() {
        okHttpClient = new  OkHttpClient.Builder().build();
    }

    public <ENTITY> ENTITY getData(SimpleNetRequest request, Class<ENTITY> clazz) {
        if (request == null || okHttpClient == null) {
            return null;
        }

        Response response = null;

        try {
             response = okHttpClient.newCall(request.getRealRequest()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null && response.body() != null) {
            try {
                String resStr = response.body().string();
                Gson gson = new GsonBuilder().create();
                ENTITY resEntity = gson.fromJson(resStr, clazz);
                return resEntity;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public <ENTITY> void getDataAsync(SimpleNetRequest request, Class<ENTITY> entityClazz, RemoteHandler<ENTITY> handler) {
        if (handler == null) {
            return;
        }

        if (request == null || okHttpClient == null) {
            handler.onFailure("No request");
        }

        okHttpClient.newCall(request.getRealRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String msg = "";
                int length = e.getStackTrace().length;
                for (int i = 0; i < length; i++) {
                    msg += ("    " + e.getStackTrace()[i] + "\n");
                }
                handler.onFailure(e.getMessage() + ":\n" + msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response == null) {
                    handler.onFailure("No response");
                    return;
                }

                if (response.body() == null) {
                    handler.onFailure("Empty response");
                }

                String resStr = response.body().string();
                Gson gson = new GsonBuilder().create();
                ENTITY entity = null;
                try {
                    entity = gson.fromJson(resStr, entityClazz);
                    handler.onSuccess(entity, resStr);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    handler.onFailure("Type error");
                }
            }
        });
    }


}
