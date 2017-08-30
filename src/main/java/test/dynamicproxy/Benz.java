package test.dynamicproxy;

/**
 * Created by rickyxe on 2017/8/30.
 */
public class Benz implements ICar {
    @Override
    public void speedUp() {
        System.out.println("Benz speedUp");
    }

    @Override
    public void slowDown() {
        System.out.println("Benz slowDown");
    }
}
