package study.generic;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by rickyxe on 2017/9/9.
 */
public class MyClient {

    private static MyClient singleInstance = new MyClient();

    public static MyClient getInstance() {
        return singleInstance;
    }

    /**
     * 处理接口类型的回调函数
     * @param callback
     * @param <ENTITY>
     */
    public <ENTITY> void handleEvent(MyCallback<ENTITY> callback) {
        Type[] interfaces = callback.getClass().getGenericInterfaces();
        Type target = null;

        // 实际的对象可能实现了多个接口，需要从其中找到MyCallback
        for (int i = 0; i < interfaces.length; i++) {
            // 范型接口在数组中是ParameterizedTypeImpl类型
            if (interfaces[i] instanceof ParameterizedTypeImpl) {
                ParameterizedTypeImpl impl = (ParameterizedTypeImpl) interfaces[i];

                if (impl.getRawType().getName().equals(MyCallback.class.getName())) {
                    // target就是实现了MyCallback的范型接口
                    target = interfaces[i];
                }
            }
        }

        if (target != null) {
            try {
                // 找到声明的具体类型
                ParameterizedType parameterized = (ParameterizedType) target;
                Type actualType = parameterized.getActualTypeArguments()[0];

                Object obj = Class.forName(actualType.getTypeName()).newInstance();
                ENTITY result = (ENTITY) obj;
                onSuccess(callback, result);
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(callback, e.getLocalizedMessage());
            }

        }
    }

    private <ENTITY> void onSuccess(MyCallback<ENTITY> callback, ENTITY entity) {
        System.out.println("log success");
        callback.onSuccess(entity);
    }

    private <ENTITY> void onFailure(MyCallback<ENTITY> callback, String msg) {
        System.out.println("log failure");
        callback.onFailure(msg);
    }

    // --------------------------------------------

    /**
     * 处理类形式的回调函数
     * @param callback
     * @param <ENTITY>
     */
    public <ENTITY> void handleEvent(MyAbstractCallback<ENTITY> callback) {
        // 单继承，父类只有一个
        Type superclass = callback.getClass().getGenericSuperclass();

        // 如果实现类没有范型信息，直接返回
        if (!(superclass instanceof ParameterizedTypeImpl)) {
            return;
        }

        try {
            // 找到声明的具体类型
            ParameterizedType parameterized = (ParameterizedType) superclass;
            Type actualType = parameterized.getActualTypeArguments()[0];

            Object obj = Class.forName(actualType.getTypeName()).newInstance();
            ENTITY result = (ENTITY) obj;
            onSuccessB(callback, result);
        } catch (Exception e) {
            e.printStackTrace();
            onFailureB(callback, e.getLocalizedMessage());
        }
    }

    private <ENTITY> void onSuccessB(MyAbstractCallback<ENTITY> callback, ENTITY entity) {
        System.out.println("log success");
        callback.onSuccess(entity);
    }

    private <ENTITY> void onFailureB(MyAbstractCallback<ENTITY> callback, String msg) {
        System.out.println("log failure");
        callback.onFailure(msg);
    }
}
