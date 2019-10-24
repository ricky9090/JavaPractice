package study.functionalstudy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 字符串列表变换，使用Java 8 Stream API
 */
public class VersionB {
    public static void main(String[] args) {
        List<String> orgList = Arrays.asList("neal", "s", "stu", "j", "rich",
                "bob", "aiden", "j", "ethan", "liam",
                "mason", "noah", "lucas", "jacob", "jayden", "jack");

        VersionB b = new VersionB();
        Optional<String> result = b.cleanName(orgList);
        if (result.isPresent()) {
            System.out.println(result.get());
        }
    }

    /**
     * 1.筛选出长度大于1的字符串
     * 2.首字母变为大写
     * 3.字符串使用","拼接
     */
    public Optional<String> cleanName(List<String> nameList) {
        Stream<String> input = nameList.stream();

        Optional<String> result = input.filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() > 1;
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                String after = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
                return after;
            }
        }).reduce(new BinaryOperator<String>() {
            @Override
            public String apply(String s, String s2) {
                return s + "," + s2;
            }
        });

        return result;
    }

    /**
     * 使用Java 8 Lambda语法
     */
    public Optional<String> cleanNameJava8(List<String> nameList) {
        Stream<String> input = nameList.stream();

        Optional<String> result = input.filter(s ->  {
                return s.length() > 1;
        }).map(s ->  {
                String after = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
                return after;

        }).reduce((s, s2) ->  {
                return s + "," + s2;
        });

        return result;
    }
}
