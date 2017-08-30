package study.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * Created by rickyxe on 2017/8/30.
 */
public class TestMain {

    public static void main(String[] args) {
        ICar benzCar = new Benz();
        CarHandler handler = new CarHandler(benzCar);

        ICar target = (ICar) Proxy.newProxyInstance(benzCar.getClass().getClassLoader(),
                benzCar.getClass().getInterfaces(),
                handler);
        target.speedUp();
        System.out.println();
        target.slowDown();
    }
}
