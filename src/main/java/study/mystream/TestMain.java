package study.mystream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

public class TestMain {
    public static void main(String[] args) {
        TestMain testMain = new TestMain();
        testMain.test1();
        testMain.test2();
    }

    private void test1() {
        System.out.println();
        System.out.println("#############");
        System.out.println();
        List<String> test = new ArrayList<>();
        test.add("red"); //3
        test.add("blue"); //4
        test.add("green"); //5

        Optional<Integer> result = test.stream().map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                System.out.println("map String uppercase: " + s);
                return s.toUpperCase();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                System.out.println("map String to length: " + s);
                return s.length();
            }
        }).reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                System.out.println("reduce: " + integer + ", " + integer2);
                return integer + integer2;
            }
        });
        System.out.println(result.get());

        System.out.println();
        System.out.println("------------");
        System.out.println();

        MyStream<String> testStream = MyStream.of(test);

        Integer testResult = testStream.map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                System.out.println("map String uppercase: " + s);
                return s.toUpperCase();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                System.out.println("map String to length: " + s);
                return s.length();
            }
        }).reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                System.out.println("reduce: " + integer + ", " + integer2);
                return integer + integer2;
            }
        });
        System.out.println(testResult);

    }

    private void test2() {
        System.out.println();
        System.out.println("#############");
        System.out.println();
        List<String> test = new ArrayList<>();
        test.add("red"); //3
        test.add("blue"); //4
        test.add("green"); //5

        Optional<Integer> result = test.stream().map(new Function<String, String[]>() {
            @Override
            public String[] apply(String s) {
                System.out.println("map String to array: " + s);
                return s.split("");
            }
        }).flatMap(new Function<String[], Stream<String>>() {
            @Override
            public Stream<String> apply(String[] strings) {
                System.out.println("flatmap String[] to stream: " + strings);
                return Stream.of(strings);
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                System.out.println("map String to length: " + s);
                return s.length();
            }
        }).reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                System.out.println("reduce: " + integer + ", " + integer2);
                return integer + integer2;
            }
        });
        System.out.println(result.get());

        System.out.println();
        System.out.println("------------");
        System.out.println();

        MyStream<String> testStream = MyStream.of(test);

        Integer testResult = testStream.map(new Function<String, String[]>() {
            @Override
            public String[] apply(String s) {
                System.out.println("map String to array: " + s);
                return s.split("");
            }
        }).flatMap(new Function<String[], MyStream<String>>() {
            @Override
            public MyStream<String> apply(String[] strings) {
                System.out.println("flatmap String[] to stream: " + strings);
                return MyStream.of(strings);
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                System.out.println("map String to length: " + s);
                return s.length();
            }
        }).reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                System.out.println("reduce: " + integer + ", " + integer2);
                return integer + integer2;
            }
        });
        System.out.println(testResult);
    }
}
