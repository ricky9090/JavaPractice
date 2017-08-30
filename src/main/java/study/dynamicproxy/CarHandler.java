package study.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by rickyxe on 2017/8/30.
 */
public class CarHandler implements InvocationHandler {

    private final ICar target;

    public CarHandler(ICar icar) {
        target = icar;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("speedUp")) {
            System.out.println("CarHandler before speedUp");
        }

        Object result = method.invoke(target, args);

        if (method.getName().equals("slowDown")) {
            System.out.println("CarHandler after slowDown");
        }

        return result;
    }
}
