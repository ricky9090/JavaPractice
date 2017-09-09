package study.generic;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by rickyxe on 2017/9/9.
 */
public class MyClient {

    public static <ENTITY> void handleEvent(MyCallback<ENTITY> callback) {
        Type[] interfaces = callback.getClass().getGenericInterfaces();
        callback.getClass().getInterfaces();
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

    private static <ENTITY> void onSuccess(MyCallback<ENTITY> callback, ENTITY entity) {
        System.out.println("log success");
        callback.onSuccess(entity);
    }

    private static <ENTITY> void onFailure(MyCallback<ENTITY> callback, String msg) {
        System.out.println("log failure");
        callback.onFailure(msg);
    }
}
