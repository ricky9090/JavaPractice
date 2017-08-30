package study.net;

import okhttp3.Request;

/**
 * Created by rickyxe on 2017/8/28.
 */
public class SimpleNetRequest {

    public static final String METHOD_GET = "get";
    public static final String METHOD_POST = "post";

    private String netMethod;
    private String netUrl;
    private SimpleNetRequestBody netRequestBody;

    public SimpleNetRequest() {
        netMethod = null;
        netUrl = null;
        netRequestBody = null;
    }

    public String getNetMethod() {
        return netMethod;
    }

    public void setNetMethod(String netMethod) {
        this.netMethod = netMethod;
    }

    public String getNetUrl() {
        return netUrl;
    }

    public void setNetUrl(String netUrl) {
        this.netUrl = netUrl;
    }

    public SimpleNetRequestBody getNetRequestBody() {
        return netRequestBody;
    }

    public void setNetRequestBody(SimpleNetRequestBody netRequestBody) {
        this.netRequestBody = netRequestBody;
    }

    public Request getRealRequest() {
        Request request;
        if (METHOD_GET.equals(netMethod)) {
            request = new Request.Builder().url(netUrl).get().build();
        } else {
            request = new Request.Builder().url(netUrl).post(null).build();
        }
        return request;
    }

    static class Builder {

        String innerMethod;
        String innerUrl;
        SimpleNetRequestBody innerSimpleNetRequestBody;

        public Builder() {
            innerMethod = null;
            innerUrl = null;
            innerSimpleNetRequestBody = null;
        }

        public Builder method(String method) {
            if (method == null || "".equals(method)) {
                innerMethod = METHOD_GET;
            } else {
                innerMethod = method;
            }
            return this;
        }

        public Builder url(String url) {
            innerUrl = url;
            return this;
        }

        public SimpleNetRequest build() {
            SimpleNetRequest request = new SimpleNetRequest();
            request.setNetUrl(innerUrl);
            request.setNetMethod(innerMethod);
            request.setNetRequestBody(innerSimpleNetRequestBody);

            return request;
        }
    }
}
