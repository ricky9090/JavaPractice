package study.functionalstudy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

public class MapperTest {

    public static void main(String[] args) {
        List<String> testArray = Arrays.asList("abc", "def", "ghi");

        Stream<String> result = testArray.stream()
                .map(new Function<String, String[]>() {
                    @Override
                    public String[] apply(String s) {
                        System.out.println("Map: " + s);
                        return s.split("");
                    }
                }).flatMap(new Function<String[], Stream<String>>() {
                    @Override
                    public Stream<String> apply(String[] strings) {
                        System.out.println("FlatMap: " + strings[0] + "," + strings[1] + "," + strings[2]);
                        return Stream.of(strings);
                    }
                });

        System.out.println("--------------------");
        Optional<String> str = result.map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                System.out.println("Map 2: " + s);
                return s.toUpperCase();
            }
        }).reduce(new BinaryOperator<String>() {
            @Override
            public String apply(String s, String s2) {
                System.out.println("Reduce: " + s + ", " + s2);
                return s + s2;
            }
        });

        System.out.println(str.get());


    }
}
