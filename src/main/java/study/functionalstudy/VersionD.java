package study.functionalstudy;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import java.util.Arrays;
import java.util.List;

/**
 * 字符串列表变换，使用RxJava方式实现
 */
public class VersionD {
    public static void main(String[] args) {
        List<String> orgList = Arrays.asList("neal", "s", "stu", "j", "rich",
                "bob", "aiden", "j", "ethan", "liam",
                "mason", "noah", "lucas", "jacob", "jayden", "jack");

        VersionD d = new VersionD();
        System.out.println(d.cleanName(orgList));
    }

    public String cleanName(List<String> nameList) {
        String[] array = nameList.toArray(new String[nameList.size()]);
        final StringBuilder builder = new StringBuilder();
        Observable.fromArray(array).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return s.length() > 1;
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                String after = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
                return after;
            }
        }).reduce(new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) throws Exception {
                return s + "," + s2;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                builder.append(s);
            }
        });

        return builder.toString();
    }
}
